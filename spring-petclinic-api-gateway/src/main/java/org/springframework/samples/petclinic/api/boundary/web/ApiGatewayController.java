/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.api.boundary.web;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.samples.petclinic.api.application.CustomersServiceClient;
import org.springframework.samples.petclinic.api.application.VisitsServiceClient;
import org.springframework.samples.petclinic.api.application.VetsServiceClient;
import org.springframework.samples.petclinic.api.dto.OwnerDetails;
import org.springframework.samples.petclinic.api.dto.Visits;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/gateway")
public class ApiGatewayController {

    private final CustomersServiceClient customersServiceClient;

    private final VisitsServiceClient visitsServiceClient;

    private final VetsServiceClient vetsServiceClient;

    private final ReactiveCircuitBreakerFactory cbFactory;

    public ApiGatewayController(CustomersServiceClient customersServiceClient,
                                VisitsServiceClient visitsServiceClient,
                                VetsServiceClient vetsServiceClient,
                                ReactiveCircuitBreakerFactory cbFactory) {
        this.customersServiceClient = customersServiceClient;
        this.visitsServiceClient = visitsServiceClient;
        this.vetsServiceClient = vetsServiceClient;
        this.cbFactory = cbFactory;
    }

    @GetMapping(value = "owners/{ownerId}")
    public Mono<OwnerDetails> getOwnerDetails(final @PathVariable int ownerId) {
        return customersServiceClient.getOwner(ownerId)
            .flatMap(owner ->
                visitsServiceClient.getVisitsForPets(owner.getPetIds())
                    .transform(it -> {
                        ReactiveCircuitBreaker cb = cbFactory.create("getOwnerDetails");
                        return cb.run(it, throwable -> emptyVisitsForPets());
                    })
                    .map(addVisitsToOwner(owner))
            );

    }

    private Function<Visits, OwnerDetails> addVisitsToOwner(OwnerDetails owner) {
        return visits -> {
            owner.pets()
                .forEach(pet -> pet.visits()
                    .addAll(visits.items().stream()
                        .filter(v -> v.petId() == pet.id())
                        .toList())
                );
            return owner;
        };
    }

    private Mono<Visits> emptyVisitsForPets() {
        return Mono.just(new Visits(List.of()));
    }

    @PostMapping("vets")
    public Mono<ResponseEntity<Object>> addVet(@RequestBody Object vet) {
        return vetsServiceClient.createVet(vet)
            .map(savedVet -> ResponseEntity.status(201).body(savedVet));
    }

    @GetMapping("vets/specialties")
    public Mono<ResponseEntity<Object>> getSpecialties() {
        return vetsServiceClient.getSpecialties()
            .map(ResponseEntity::ok);
    }

    @PutMapping("vets/{vetId}")
    public Mono<ResponseEntity<Object>> updateVet(@PathVariable Integer vetId, @RequestBody Object vet) {
        return vetsServiceClient.updateVet(vetId, vet)
            .map(ResponseEntity::ok);
    }

    @DeleteMapping("vets/{vetId}")
    public Mono<ResponseEntity<Void>> deleteVet(@PathVariable Integer vetId) {
        return vetsServiceClient.deleteVet(vetId)
            .thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("owners/{ownerId}")
    public Mono<ResponseEntity<Void>> deleteOwner(@PathVariable int ownerId) {
        return customersServiceClient.deleteOwner(ownerId)
            .thenReturn(ResponseEntity.noContent().build());
    }
}

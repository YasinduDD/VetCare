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
package org.springframework.samples.petclinic.api.application;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class VetsServiceClient {
    private final WebClient.Builder webClientBuilder;

    public VetsServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Object> createVet(Object vet) {
        return webClientBuilder.build().post()
            .uri("http://vets-service/vets")
            .bodyValue(vet)
            .retrieve()
            .bodyToMono(Object.class);
    }

    public Mono<Object> getSpecialties() {
        return webClientBuilder.build().get()
            .uri("http://vets-service/vets/specialties")
            .retrieve()
            .bodyToMono(Object.class);
    }

    public Mono<Object> updateVet(Integer vetId, Object vet) {
        return webClientBuilder.build().put()
            .uri("http://vets-service/vets/" + vetId)
            .bodyValue(vet)
            .retrieve()
            .bodyToMono(Object.class);
    }

    public Mono<Void> deleteVet(Integer vetId) {
        return webClientBuilder.build().delete()
            .uri("http://vets-service/vets/" + vetId)
            .retrieve()
            .bodyToMono(Void.class);
    }
} 
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
package org.springframework.samples.petclinic.vets.web;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.samples.petclinic.vets.model.VetRepository;
import org.springframework.samples.petclinic.vets.model.Specialty;
import org.springframework.samples.petclinic.vets.model.SpecialtyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/vets")
@RestController
class VetResource {

    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;

    VetResource(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping
    @Cacheable("vets")
    public List<Vet> showResourcesVetList() {
        return vetRepository.findAll();
    }

    @GetMapping("/specialties")
    public List<Specialty> getSpecialties() {
        return specialtyRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Vet> addVet(@RequestBody Vet vet) {
        Vet savedVet = vetRepository.save(vet);
        return ResponseEntity.status(201).body(savedVet);
    }

    @PutMapping("/{vetId}")
    public ResponseEntity<Vet> updateVet(@PathVariable Integer vetId, @RequestBody Vet vet) {
        if (!vetRepository.existsById(vetId)) {
            return ResponseEntity.notFound().build();
        }
        vet.setId(vetId);
        Vet updatedVet = vetRepository.save(vet);
        return ResponseEntity.ok(updatedVet);
    }

    @DeleteMapping("/{vetId}")
    public ResponseEntity<Void> deleteVet(@PathVariable Integer vetId) {
        if (!vetRepository.existsById(vetId)) {
            return ResponseEntity.notFound().build();
        }
        vetRepository.deleteById(vetId);
        return ResponseEntity.noContent().build();
    }
}

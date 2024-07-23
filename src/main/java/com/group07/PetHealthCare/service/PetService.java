package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.PetCreationRequest;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Pet;
import com.group07.PetHealthCare.pojo.Species;
import com.group07.PetHealthCare.respositytory.CustomerRepository;
import com.group07.PetHealthCare.respositytory.PetRepository;
import com.group07.PetHealthCare.respositytory.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private CustomerRepository customerRepository;
    public Pet addPet(PetCreationRequest request){
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setAge(request.getAge());

        Species species = speciesRepository.findById(request.getSpeciesID()).orElseThrow(() -> new RuntimeException("Species not found"));
        Customer customer = customerRepository.findById(request.getCustomerID()).orElseThrow(() -> new RuntimeException("Customer not found"));

        pet.setSpecies(species);
        pet.setCustomer(customer);
        return petRepository.save(pet);
    }
}

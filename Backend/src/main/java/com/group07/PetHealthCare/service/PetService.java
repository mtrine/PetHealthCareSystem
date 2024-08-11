package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.PetCreationRequest;
import com.group07.PetHealthCare.dto.request.PetUpdateRequest;
import com.group07.PetHealthCare.dto.response.PetResponse;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Pet;
import com.group07.PetHealthCare.pojo.Species;
import com.group07.PetHealthCare.respositytory.ICustomerRepository;
import com.group07.PetHealthCare.respositytory.IPetRepository;
import com.group07.PetHealthCare.respositytory.ISpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PetService {
    @Autowired
    private IPetRepository IPetRepository;

    @Autowired
    private ISpeciesRepository ISpeciesRepository;

    @Autowired
    private ICustomerRepository ICustomerRepository;
    public PetResponse addPet(PetCreationRequest request){
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setAge(request.getAge());
        pet.setGender(request.getGender());
        Species species = ISpeciesRepository.findById(request.getSpeciesID()).orElseThrow(() -> new RuntimeException("Species not found"));
        Customer customer = ICustomerRepository.findById(request.getCustomerID()).orElseThrow(() -> new RuntimeException("Customer not found"));

        pet.setSpecies(species);
        pet.setCustomer(customer);
        return IPetRepository.save(pet);
    }

    public Set<PetResponse> getPetsByCustomerId(String customerId) {
        Customer customer = ICustomerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Set<Pet> pets = customer.getPets();

        return pets;
    }

    public PetResponse updatePet(String id, PetUpdateRequest request) {
        Pet pet = IPetRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
        pet.setName(request.getName());
        pet.setAge(request.getAge());
        pet.setGender(request.getGender());

        Species species = ISpeciesRepository.findById(request.getSpeciesID()).orElseThrow(() -> new RuntimeException("Species not found"));
        Customer customer = ICustomerRepository.findById(request.getCustomerID()).orElseThrow(() -> new RuntimeException("Customer not found"));

        pet.setSpecies(species);
        pet.setCustomer(customer);
        return IPetRepository.save(pet);
    }

    public void deletePet(String id) {
        Pet pet = IPetRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
        IPetRepository.delete(pet);
    }


}

package com.udacity.jdnd.course3.critter.service;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;
    public Pet savePet(Pet pet){
        Pet savedPet= petRepository.save(pet);
        Customer customer = (Customer) savedPet.getCustomer();
        List customerPets = customer.getPets();
        if(customerPets== null){
            customerPets = new ArrayList<>();
        }
        customerPets.add(savedPet);
        customer.setPets(customerPets);
        customerRepository.save(customer);
        return savedPet;
    }
    public Pet findById(long petId){
        Optional<Pet> pet = petRepository.findById(petId);
        if(pet.isPresent()){
            return pet.get();
        } else {
            throw new ObjectNotFoundException(300,"Pet with id: "+ petId + " not found");
        }
    }
    public Customer getOwnerByPetId(Long petId){
        Pet found = petRepository.findByPetId(petId);
        return customerService.findById(found.getCustomer().getId());
    }
    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }
    public List<Pet> getPetsByOwnerId(Long ownerId){
        Customer savedCustomer = customerService.findById(ownerId);
        return petRepository.findByCustomer(savedCustomer);
    }
}
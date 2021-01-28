package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.checkerframework.checker.units.qual.A;
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

    public Customer getOwnerByPetId(Long petId){
        Customer found = petRepository.findByPetId(petId);
        return customerService.findById(found.getId());
    }



    public List<Pet> getPetsByOwnerId(Long ownerId){
        Customer savedCustomer = customerService.findById(ownerId);
        return petRepository.findByCustomer(savedCustomer);
//        Optional<Pet> pets = petRepository.findByOwnerId(ownerId);
//        if(owner.isPresent()){
//            return owner.get();
//        } else {
//            throw new ObjectNotFoundException(300,"Employee with id: "+employeeId + " not found");
//        }
    }
}
/*
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public void save(Customer customer){
        customerRepository.persist(customer);
    }
}*/
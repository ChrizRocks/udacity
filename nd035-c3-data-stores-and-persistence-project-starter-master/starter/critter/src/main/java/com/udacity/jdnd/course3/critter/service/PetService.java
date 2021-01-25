package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public void save(Pet pet){
        petRepository.save(pet);
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
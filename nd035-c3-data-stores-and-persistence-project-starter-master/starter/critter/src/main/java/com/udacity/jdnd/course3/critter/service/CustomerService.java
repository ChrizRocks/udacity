package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public void save(Customer customer){
        customerRepository.save(customer);
    }

    public void delete(Customer customer){
        customerRepository.delete(customer);
    }

    public List<Customer> findAllCustomers(){
        //List<Customer> customerList =
        return customerRepository.findAll();
    }

    public Customer findById(long customerId){
        Optional<Customer> owner = customerRepository.findById(customerId);
        if(owner.isPresent()){
            return owner.get();
        } else {
            throw new ObjectNotFoundException(300,"Customer with id: "+customerId + " not found");
        }
    }
}

package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Customer findByPets(List<Pet> pets);
    /*@PersistenceContext
    EntityManager entityManager;

    public void persist(Customer customer){
        entityManager.persist(customer);
    }

    public Customer find(Long id){
        return entityManager.find(Customer.class,id);
    }

    public Customer merge(Customer customer){
        return entityManager.merge(customer);
    }

    public void delete(Long id){
        Customer customer = entityManager.find(Customer.class, id);
        entityManager.remove(customer);
    }*/

}
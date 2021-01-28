package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
@Repository
@Transactional
public interface PetRepository extends JpaRepository<Pet, Long> {

/*

    @PersistenceContext
    EntityManager entityManager;

    public void persist(Pet pet){
        entityManager.persist(pet);
    }

    public Pet find(Long id){
       return entityManager.find(Pet.class,id);
    }

    public void delete(Long id){
        entityManager.remove(entityManager.find(Pet.class,id));
    }

    public Pet merge(Pet pet){
        return entityManager.merge(pet);
    }
*/
}

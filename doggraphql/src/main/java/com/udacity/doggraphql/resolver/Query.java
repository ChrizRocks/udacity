package com.udacity.doggraphql.resolver;


import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.udacity.doggraphql.entity.Dog;
import com.udacity.doggraphql.exception.DogNotFoundException;
import com.udacity.doggraphql.repository.DogRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * type Query {
 *     findAllDogs: [Dog]!
 *     findDogById(id: ID!): String!
 * } Das ist die Grundlage fuer die zu implementierenden Methoden.
 */
@Component
public class Query implements GraphQLQueryResolver {
    private DogRepository dogRepository;

    public Query(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Iterable<Dog> findAllDogs() {
        return dogRepository.findAll();
    }

    public Dog findDogById(Long id){
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isPresent()){
            return optionalDog.get();
        } else {
            throw new DogNotFoundException("Dog not found!",id);
        }
    }

}

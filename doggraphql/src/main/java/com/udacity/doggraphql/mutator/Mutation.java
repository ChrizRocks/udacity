package com.udacity.doggraphql.mutator;


import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.udacity.doggraphql.entity.Dog;
import com.udacity.doggraphql.exception.BreedNotFoundException;
import com.udacity.doggraphql.repository.DogRepository;
import com.udacity.doggraphql.exception.DogNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {
    private DogRepository dogRepository;

    public Mutation(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    ////    addNewDog(name: String!, breed: String!, origin: String!) : Dog!
    public Dog newDog(String name, String breed, String origin) {
        Dog dog = newDog(name,breed,origin);
        dogRepository.save(dog);
        return dog;
    }

    public boolean deleteDogBreed(String breed) {
        boolean deleted= false;
        Iterable<Dog> allDogs = dogRepository.findAll();
        for (Dog d: allDogs){
            if(d.getBreed().equals(breed)){
                dogRepository.delete(d);
                deleted=true;
            }
        }
        if(!deleted){
            throw new BreedNotFoundException("Breed Not Found!",breed);
        }
        return deleted;
    }



    public Dog updateDogName(String newName, Long id) {
        Optional<Dog> optionalDog =
                dogRepository.findById(id);

        if(optionalDog.isPresent()) {
            Dog dog = optionalDog.get();
            dog.setName(newName);
            dogRepository.save(dog);
            return dog;
        } else {
            throw new DogNotFoundException("Dog not found!", id);
        }


    }
//    deleteDogBreed(breed: String!) : Boolean
//    updateDogName(id: ID!, newName: String!) : Dog!
//    addNewDog(name: String!, breed: String!, origin: String!) : Dog!
}

package com.udacity.doggraphql.repository;

import com.udacity.doggraphql.entity.Dog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DogRepository extends CrudRepository<Dog, Long> {
    //not needed because CrudeRepository supplies them.
}

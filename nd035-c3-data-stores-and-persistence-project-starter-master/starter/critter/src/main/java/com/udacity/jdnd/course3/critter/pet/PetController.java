package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet newPet = convertDTOtoPet(petDTO);
        petRepository.save(newPet);
        return petDTO;

        /*Pet savePet = new Pet();
        savePet.setId(petDTO.getId());
        savePet.setBirthDate(petDTO.getBirthDate());
        savePet.setName(petDTO.getName());
        //savePet.setOwner(petDTO.getOwnerId());
        savePet.setNotes(petDTO.getNotes());
        savePet.setType(petDTO.getType());
        petRepository.persist(savePet);
        return petDTO;*/
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO nPetDTO = convertPetToDTO(petRepository.findById(petId));
        return nPetDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }

    private Pet convertDTOtoPet(PetDTO petDTO){
        Pet retPet = new Pet();
        retPet.setId(petDTO.getId());
        retPet.setBirthDate(petDTO.getBirthDate());
        retPet.setName(petDTO.getName());
        retPet.setCustomer(customerService.findById(petDTO.getOwnerId()));
        //retPet.setCustomer(customerRepository.find(pe));
        //retPet.setCustomer(petDTO.getOwnerId());
        //retPet.setCustomer(petDTO.getOwnerId());
        retPet.setNotes(petDTO.getNotes());
        retPet.setType(petDTO.getType());
        return retPet;
    }

    private PetDTO convertPetToDTO (Optional<Pet> pet){
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.get().getId());
        petDTO.setName(pet.get().getName());
        petDTO.setNotes(pet.get().getNotes());
        petDTO.setBirthDate(pet.get().getBirthDate());
        petDTO.setType(pet.get().getType());
        petDTO.setOwnerId(pet.get().getCustomer().getId());
        return petDTO;
    }
}

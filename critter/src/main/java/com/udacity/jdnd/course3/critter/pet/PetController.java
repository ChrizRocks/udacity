package com.udacity.jdnd.course3.critter.pet;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.UserController;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    PetService petService;


//    @PostMapping("/{petId}")
//    public PetDTO savePetWithId(@RequestBody PetDTO petDTO){
//        Pet newPet = convertDTOtoPet(petDTO);
//        petRepository.save(newPet);
//        return petDTO;
//    }


    /*
    {
  "type": "CAT",
  "name": "Kilo",
  "birthDate": "2019-12-16T04:43:57.995Z",
  "notes": "HI KILO",
  "ownerId": 1
}
     */
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        //Pet newPet = convertDTOtoPet(petDTO);
        //petRepository.save(newPet);
        Customer savedCustomer = null;
        Pet newPet = new Pet();
        newPet.setType(petDTO.getType());
        newPet.setName(petDTO.getName());
        newPet.setBirthDate(petDTO.getBirthDate());
        newPet.setNotes(petDTO.getNotes());
        //newPet.setId(petDTO.getId());
        try {
            savedCustomer = customerService.findById(petDTO.getOwnerId());
        } catch (Exception e){
            e.printStackTrace();
        }
        newPet.setCustomer(savedCustomer);
        Pet savedPet = petService.savePet(newPet);
        if(petDTO.getId()==null) {
            petDTO.setId(savedPet.getId());
        }
        //newPet.setCustomer();

        return petDTO;

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO nPetDTO = convertPetToDTO(petRepository.findById(petId));
        return nPetDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> savedPets = petService.getAllPets();
        List<PetDTO> ret = new ArrayList<>();
        PetDTO tmp;
        if(savedPets!=null) {
            for (Pet pet : savedPets) {
                tmp = new PetDTO();
                BeanUtils.copyProperties(pet,tmp);
                tmp.setOwnerId(pet.getCustomer().getId());
                ret.add(tmp);

            }
        }
        return ret;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwnerId(ownerId);
        List<PetDTO> petDTOList = new ArrayList<>();
        if(pets.size()<1){
            return petDTOList;
        } else {
            for(Pet pet : pets){
                PetDTO tmp = new PetDTO();
                BeanUtils.copyProperties(pet, tmp);
                tmp.setOwnerId(ownerId);
                petDTOList.add(tmp);
            }
            return petDTOList;
        }
    }

    private Pet convertDTOtoPet(PetDTO petDTO){
        /*
        Customer newCustomer = new Customer();
        newCustomer.setName(customerDTO.getName());
        newCustomer.setNotes(customerDTO.getNotes());
        newCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        //newCustomer.setPetIds(customerDTO.getPetIds());
        Customer savedCustomer = customerService.save(newCustomer);
        customerDTO.setId(savedCustomer.getId());
        //customerService.save(newCustomer);
        return customerDTO;
         */

        Pet retPet = new Pet();
        retPet.setBirthDate(petDTO.getBirthDate());
        retPet.setName(petDTO.getName());
        retPet.setNotes(petDTO.getNotes());
        retPet.setType(petDTO.getType());
        Pet savedPet = petService.savePet(retPet);
        petDTO.setId(savedPet.getId());

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

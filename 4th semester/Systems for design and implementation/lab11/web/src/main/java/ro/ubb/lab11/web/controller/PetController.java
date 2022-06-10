package ro.ubb.lab11.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.lab11.core.model.Pet;
import ro.ubb.lab11.core.model.Wizard;
import ro.ubb.lab11.core.service.IPetService;
import ro.ubb.lab11.web.converter.PetConverter;
import ro.ubb.lab11.web.dto.PetDTO;
import ro.ubb.lab11.web.dto.PetsDTO;
import ro.ubb.lab11.web.dto.WizardsDTO;

import java.util.List;

@RestController
public class PetController {
    public static final Logger logger = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private IPetService petService;
    @Autowired
    private PetConverter petConverter;

    @RequestMapping(value = "/pets", method = RequestMethod.GET)
    PetsDTO getAllPets() {
        logger.trace("getAllPets: ");
        List<Pet> pets = petService.getAllPets();
        PetsDTO petsDTO = new PetsDTO(petConverter.convertModelsToDTOs(pets));
        logger.trace("getAllPets: " + pets);
        return petsDTO;
    }

    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    PetDTO addPet(@RequestBody PetDTO PetDTO) {
        logger.trace("addPet - PetDTO: " + PetDTO);
        var pet = petConverter.convertDtoToModel(PetDTO);
        var result = petService.addPet(pet.getName(), pet.getBreed(), pet.getWid());
        var resultModel = petConverter.convertModelToDto(result);
        logger.trace("addPet - Pet added");
        return resultModel;
    }

    @RequestMapping(value = "/pets/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/pets/{id}", method = RequestMethod.PUT)
    PetDTO updatePet(@PathVariable Long id, @RequestBody PetDTO petDTO) {
        logger.trace("updatePet - method entered - PetDTO: " + petDTO);
        var pet = petConverter.convertDtoToModel(petDTO);
        var result = petService.updatePet(id, pet.getName(), pet.getBreed(), pet.getWid());
        var resultModel = petConverter.convertModelToDto(result);
        logger.trace("updatePet - Pet updated");
        return resultModel;
    }
    @RequestMapping(value = "/pets/sort")
    PetsDTO getPetsSortedByName() {
        logger.trace("findAllByOrderByNameAsc - method entered");
        List<Pet> pets = petService.findAllByOrderByWidAsc();
        PetsDTO petsDTO = new PetsDTO(petConverter.convertModelsToDTOs(pets));
        logger.trace("findAllByOrderByNameAsc: " + pets);
        return petsDTO;
    }

    @RequestMapping(value = "/pets/filter/{name}")
    PetsDTO findPetByName(@PathVariable String name) {
        logger.trace("findWizardByName - name = {}", name);
        List<Pet> pets = petService.findPetsByName(name);
        PetsDTO dtos = new PetsDTO(petConverter.convertModelsToDTOs(pets));
        return dtos;
    }
}

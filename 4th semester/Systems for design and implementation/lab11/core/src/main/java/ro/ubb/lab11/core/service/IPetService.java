package ro.ubb.lab11.core.service;

import ro.ubb.lab11.core.model.Pet;
import ro.ubb.lab11.core.model.Wizard;

import java.util.List;

public interface IPetService {
    List<Pet> getAllPets();
    Pet addPet(String name, String breed, Long wid);
    void deletePet(Long id);
    Pet updatePet(Long id, String name, String breed, Long wid);
    List<Pet> findPetsByName(String name);
    List<Pet> findAllByOrderByWidAsc();
}

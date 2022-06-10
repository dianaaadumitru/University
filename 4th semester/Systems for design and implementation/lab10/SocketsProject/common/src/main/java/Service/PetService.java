package Service;

import Model.ValidatorException;
import Model.Pet;

//import Model

import java.util.List;
import java.util.concurrent.Future;

public interface PetService {
    String ADD = "addPet";
    String DELETE = "deletePet";
    String UPDATE = "updatePet";
    String GET = "getPets";

    Future<Pet> addPet(Long id, String name, String breed, Long wid) throws ValidatorException;
    Future<Pet> deletePet(Long id);
    Future<Pet> updatePet(Long id, String name, String breed, Long wid);
    Future<List<Pet>> getPets();
}

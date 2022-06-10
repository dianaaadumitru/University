package ro.ubb.lab11.core.repository;

import ro.ubb.lab11.core.model.Pet;
import ro.ubb.lab11.core.model.Wizard;

import java.util.List;

public interface IPetRepository extends IRepository<Pet, Long>{
    List<Pet> findAllByOrderByWidAsc();
    List<Pet> findAllByName(String name);
}

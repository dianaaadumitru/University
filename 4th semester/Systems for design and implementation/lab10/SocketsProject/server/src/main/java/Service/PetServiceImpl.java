package Service;

import Model.Pet;
import Model.ValidatorException;
import Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class PetServiceImpl implements PetService {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private PetRepository petRepository;

    public PetServiceImpl(ExecutorService executorService, PetRepository repo) {
        this.executorService = executorService;
        this.petRepository = repo;
    }

    @Override
    public Future<Pet> addPet(Long id, String name, String breed, Long wid) throws ValidatorException {
        return executorService.submit(() -> this.petRepository.save(new Pet(id, name, breed, wid)));
    }

    @Override
    public Future<Pet> deletePet(Long id) {
        return executorService.submit(() -> this.petRepository.delete(id));
    }

    @Override
    public Future<Pet> updatePet(Long id, String name, String breed, Long wid) {
        return executorService.submit(() -> this.petRepository.update(new Pet(id, name, breed, wid)));
    }

    @Override
    public Future<List<Pet>> getPets() {
        return executorService.submit(this.petRepository::findAll);
    }
}


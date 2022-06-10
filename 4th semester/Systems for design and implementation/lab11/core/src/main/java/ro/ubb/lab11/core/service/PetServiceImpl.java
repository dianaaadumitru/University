package ro.ubb.lab11.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lab11.core.exceptions.MagicException;
import ro.ubb.lab11.core.model.Pet;
import ro.ubb.lab11.core.model.validators.PetValidator;
import ro.ubb.lab11.core.repository.IPetRepository;
import ro.ubb.lab11.core.repository.IWizardRepository;

import java.util.List;

@Service
public class PetServiceImpl implements IPetService{
    public static final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

    @Autowired
    private PetValidator validator;
    @Autowired
    IPetRepository petRepository;
    @Autowired
    private IWizardRepository wizardRepository;

    @Override
    public List<Pet> getAllPets() {
        logger.trace("getAllPets: ");
        List<Pet> pets = petRepository.findAll();
        return pets;
    }

    @Override
    public Pet addPet(String name, String breed, Long wid) {
        logger.trace("addPet - name: " + name + " - breed: " + breed + " - wizardId: " + wid);
        wizardRepository.findById(wid).ifPresentOrElse( (wizard) -> {}, () -> {
            throw new MagicException("Wizard does not exist!");
        });
        Pet pet = new Pet(name, breed, wid);
        validator.validate(pet);
        var result = petRepository.save(pet);
        logger.trace("addPet - method finished");
        return result;
    }

    @Override
    public void deletePet(Long id) {
        logger.trace("deletPet - id: " + id);
        petRepository.findById(id)
                .ifPresentOrElse((pet) -> petRepository.deleteById(id),
                        () -> {
                            throw new MagicException("Pet does not exist!");
                        });
        logger.trace("deletePet - method finished");
    }

    @Override
    @Transactional
    public Pet updatePet(Long id, String name, String breed, Long wid) {
        logger.trace("updatePet - id: " + id + " - name: " + name + " - breed: " + breed + " - wizardId: " + wid);
        Pet pet = new Pet(name, breed, wid);
        pet.setId(id);
        validator.validate(pet);
        petRepository.findById(id)
                .ifPresentOrElse((pet1) -> {
                    wizardRepository.findById(wid).orElseThrow(() -> new MagicException("Wizard doesn't exist!"));
                    pet1.setName(name);
                    pet1.setBreed(breed);
                    pet1.setWid(wid);
                }, () -> {
                    throw new MagicException("Pet does not exist!");
                });
        logger.trace("Pet - method finished");
        return pet;
    }

    @Override
    public List<Pet> findPetsByName(String name) {
        var result = petRepository.findAllByName(name);
        return result;
    }

    @Override
    public List<Pet> findAllByOrderByWidAsc() {
        logger.trace("findAllByOrderByWidAsc: ");
        var pets = petRepository.findAllByOrderByWidAsc();
        logger.trace("findAllByOrderByWidAsc - result = {}", pets);
        return pets;
    }

}

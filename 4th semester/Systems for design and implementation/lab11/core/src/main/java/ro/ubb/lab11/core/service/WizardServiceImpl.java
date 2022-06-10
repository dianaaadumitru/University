package ro.ubb.lab11.core.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lab11.core.exceptions.MagicException;
import ro.ubb.lab11.core.model.Wizard;
import ro.ubb.lab11.core.model.validators.WizardValidator;
import ro.ubb.lab11.core.repository.ICastedSpellRepository;
import ro.ubb.lab11.core.repository.IPetRepository;
import ro.ubb.lab11.core.repository.IWizardRepository;

import java.util.List;

@Service
public class WizardServiceImpl implements IWizardService{
    public static final Logger logger = LoggerFactory.getLogger((WizardServiceImpl.class));

    @Autowired
    private IWizardRepository wizardRepo;
    @Autowired
    private WizardValidator validator;
    @Autowired
    private ICastedSpellRepository CastedSpellRepository;
    @Autowired
    private IPetRepository petRepository;

    @Override
    public List<Wizard> getAllWizards() {
        logger.trace("Get all wizards method: ");
        List<Wizard> wizards = wizardRepo.findAll();
        logger.trace("allWizards: " + wizards);
        return wizards;
    }

    @Override
    public Wizard addWizard(String name, Integer age, String pet) {
        logger.trace("addWizard - name: " + name + " - age: " + age + " - pet: " + pet);
        Wizard newWizard = new Wizard(name, age, pet);
        validator.validate(newWizard);
        var wizard = wizardRepo.save(newWizard);
        logger.trace("AddWizard - method finished");
        return wizard;
    }

    @Override
    public void deleteWizard(Long id) {
        logger.trace("deleteWizard - method entered - id: " + id);
        CastedSpellRepository.findAll().stream()
                .filter(castedSpell -> castedSpell.getWizardId().equals(id))
                .findAny()
                .ifPresent(castedSpell -> {
                    throw new MagicException("The wizard has a casted spell!");
                });
        petRepository.findAll().stream()
                .filter(pet -> pet.getWid().equals(id))
                .findAny()
                .ifPresent(pet -> {
                    throw new MagicException("The wizard has a pet!");
                });

        wizardRepo.findById(id).ifPresentOrElse((wizard) -> wizardRepo.deleteById(id), () ->{
            throw new MagicException("Wizard does not exist!");
        });
        logger.trace("deleteWizard - method finished");
    }

    @Override
    @Transactional
    public Wizard updateWizard(Long id, String name, Integer age, String pet) {
        logger.trace("updateWizard - id: " + id + " - name: " + name + " - age: " + age + " - pet: " + pet);
        Wizard wizard = new Wizard(name, age, pet);
        wizard.setId(id);
        validator.validate(wizard);
        wizardRepo.findById(id).ifPresentOrElse((wizard1) -> {
            wizard1.setName(name);
            wizard1.setAge(age);
            wizard1.setPet(pet);
        }, () -> {
            throw new MagicException("Wizard does not exist!");
        });
        logger.trace("updateWizard - method finished");
        return wizard;
    }

    @Override
    public List<Wizard> findWizardsByName(String name) {
        logger.trace("findWizardsByName - name = {}", name);
        var result = wizardRepo.findAllByName(name);
        logger.trace("findWizardsByName - method finished - result = {}", result);
        return result;
    }

    @Override
    public List<Wizard> findAllByOrderByAgeAsc() {
        return wizardRepo.findAllByOrderByAgeAsc();
    }
}

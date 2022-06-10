package ro.ubb.lab11.core.service;


import ro.ubb.lab11.core.model.Wizard;

import java.util.List;

public interface IWizardService {
    List<Wizard> getAllWizards();
    Wizard addWizard(String name, Integer age, String pet);
    void deleteWizard(Long id);
    Wizard updateWizard(Long id, String name, Integer age, String pet);
    List<Wizard> findWizardsByName(String name);
    List<Wizard> findAllByOrderByAgeAsc();
}

package ro.ubb.lab11.core.repository;

import ro.ubb.lab11.core.model.Wizard;

import java.util.List;

public interface IWizardRepository extends IRepository<Wizard, Long>{
    List<Wizard> findAllByName(String name);
    List<Wizard> findAllByOrderByAgeAsc();

}

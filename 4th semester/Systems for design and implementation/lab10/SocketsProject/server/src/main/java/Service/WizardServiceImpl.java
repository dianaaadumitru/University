package Service;

import Model.ValidatorException;
import Model.Wizard;
import Repository.WizardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class WizardServiceImpl implements WizardService {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private WizardRepository wizardRepository;

    public WizardServiceImpl(ExecutorService executorService, WizardRepository repo) {
        this.executorService = executorService;
        this.wizardRepository = repo;
    }

    @Override
    public Future<Wizard> addWizard(Long id, String name, Integer age, String pet) throws ValidatorException {
        return executorService.submit(() -> this.wizardRepository.save(new Wizard(id, name, age, pet)));
    }

    @Override
    public Future<Wizard> deleteWizard(Long id) {
        return executorService.submit(() -> this.wizardRepository.delete(id));
    }

    @Override
    public Future<Wizard> updateWizard(Long id, String name, Integer age, String pet) {
        return executorService.submit(() -> this.wizardRepository.update(new Wizard(id, name, age, pet)));
    }

    @Override
    public Future<List<Wizard>> getWizards() {
        return executorService.submit(this.wizardRepository::findAll);
    }
}

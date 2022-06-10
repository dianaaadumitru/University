package Service;

import java.util.List;
import java.util.concurrent.Future;

import Model.ValidatorException;
import Model.Wizard;

public interface WizardService {
    String ADD = "addWizard";
    String DELETE = "deleteWizard";
    String UPDATE = "updateWizard";
    String GET = "getWizards";

    Future<Wizard> addWizard(Long id, String name, Integer age, String pet) throws ValidatorException;
    Future<Wizard> deleteWizard(Long id);
    Future<Wizard> updateWizard(Long id, String name, Integer age, String pet);
    Future<List<Wizard>> getWizards();
}

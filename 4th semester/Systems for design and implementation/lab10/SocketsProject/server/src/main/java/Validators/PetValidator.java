package Validators;

import Model.Pair;
import Model.Pet;
import Model.ValidatorException;
import Repository.WizardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PetValidator  implements Validator<Pet>{
    @Autowired
    private WizardRepository wizardRepository;

    public PetValidator(WizardRepository wr){
        this.wizardRepository=wr;
    }

    @Override
    public void validate(Pet entity) throws ValidatorException {
        Stream.of(new Pair<>(entity.getName().equals(""), "Name cannot be empty!"),
                        new Pair<>(entity.getBreed().equals(""), "Breed cannot be empty!"))
                .forEach(invalidSituation -> {
                    if(invalidSituation.getLeftPart()) {
                        var rightPart = invalidSituation.getRightPart();
                        try {
                            throw new ValidatorException(rightPart);
                        } catch (ValidatorException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        var result=this.wizardRepository.findAll().stream()
                .filter(wizard->wizard.getId().equals(entity.getWizardId()))
                .collect(Collectors.toList());
        if(result.size()==0){
            throw new ValidatorException("Wizard id not valid");
        }
    }
}

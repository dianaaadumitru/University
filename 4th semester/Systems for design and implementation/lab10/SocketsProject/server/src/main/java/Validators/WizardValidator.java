package Validators;

import Model.Pair;
import Model.ValidatorException;
import Model.Wizard;

import java.util.stream.Stream;

public class WizardValidator implements Validator<Wizard> {
    @Override
    public void validate(Wizard entity) throws ValidatorException {
        Stream.of(new Pair<>(entity.getName().equals(""), "Name cannot be empty string"),
                        new Pair<>(entity.getPet().equals(""), "Pet cannot be empty string!"),
                        new Pair<>(entity.getAge() <= 0, "Age cannot be negative!")).filter(Pair::getLeftPart)
                .forEach(invalidSituation -> {
                    var rightPart = invalidSituation.getRightPart();
                    try {
                        throw new ValidatorException(rightPart);
                    } catch (ValidatorException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
package ro.ubb.lab11.core.model.validators;


import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.exceptions.ValidatorException;
import ro.ubb.lab11.core.model.Pair;
import ro.ubb.lab11.core.model.Wizard;

import java.util.stream.Stream;

@Component
public class WizardValidator implements Validator<Wizard>{
    @Override
    public void validate(Wizard wizard) throws ValidatorException {
        Stream.of(new Pair<>(wizard.getName().isEmpty(), "Wizard name must not be empty"),
                        new Pair<>(wizard.getAge()< 0, "Age must be a positive number"),
                        new Pair<>(wizard.getPet().isEmpty(), "Pet must not pe empty"))
                .filter(Pair::getLeft)
                .forEach((b) -> {
                    throw new ValidatorException(b.getRight());
                });
    }
}

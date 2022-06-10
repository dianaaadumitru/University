package ro.ubb.lab11.core.model.validators;


import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.exceptions.ValidatorException;
import ro.ubb.lab11.core.model.CastedSpell;
import ro.ubb.lab11.core.model.Pair;

import java.util.stream.Stream;

@Component
public class CastedSpellValidator implements Validator<CastedSpell>{
    @Override
    public void validate(CastedSpell castedSpell) throws ValidatorException {
        Stream.of(new Pair<>(castedSpell.getWizardId() <= 0, "Wizard id should be positive."),
                        new Pair<>(castedSpell.getSpellId() <= 0, "Spell id should be positive."),
                        new Pair<>(castedSpell.getDetails().isEmpty(), "Details should not be empty."))
                .filter(Pair::getLeft)
                .forEach((b) -> {
                    throw new ValidatorException(b.getRight());
                });
    }
}

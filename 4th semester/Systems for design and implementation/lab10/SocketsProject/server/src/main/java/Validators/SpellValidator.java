package Validators;

import Model.Pair;
import Model.Spell;
import Model.ValidatorException;

import java.util.stream.Stream;

public class SpellValidator implements Validator<Spell>{
    @Override
    public void validate(Spell entity) throws ValidatorException {
        Stream.of(new Pair<>(entity.getName().equals(""), "Name cannot be empty!"),
                        new Pair<>(entity.getDescription().equals(""), "Description cannot be empty!"))
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
    }
}
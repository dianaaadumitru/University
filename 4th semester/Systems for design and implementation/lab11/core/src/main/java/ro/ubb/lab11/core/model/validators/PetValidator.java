package ro.ubb.lab11.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.exceptions.ValidatorException;
import ro.ubb.lab11.core.model.Pair;
import ro.ubb.lab11.core.model.Pet;

import java.util.stream.Stream;

@Component
public class PetValidator implements Validator<Pet>{
    @Override
    public void validate(Pet entity) throws ValidatorException {
        Stream.of(new Pair<>(entity.getName().equals(""), "Name cannot be empty!"),
                        new Pair<>(entity.getBreed().equals(""), "Breed cannot be empty!"))
                .filter(Pair::getLeft)
                .forEach((b) -> {
                    throw new ValidatorException(b.getRight());
                });
    }
}

package Validators;

import Model.ValidatorException;

public interface Validator<T> {
    /**
     * Validates an entity
     * */
    void validate(T entity) throws ValidatorException;
}
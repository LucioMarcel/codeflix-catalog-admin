package io.luciomarcel.catalog.admin.domain.validation.handler;

import io.luciomarcel.catalog.admin.domain.exceptions.DomainException;
import io.luciomarcel.catalog.admin.domain.validation.Error;
import io.luciomarcel.catalog.admin.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(final  ValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try{
            return aValidation.validate();
        } catch(Exception ex) {
            throw  DomainException.with(new Error(ex.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}

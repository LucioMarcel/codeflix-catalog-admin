package io.luciomarcel.catalog.admin.domain.exceptions;

import io.luciomarcel.catalog.admin.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStackTraceException{

    protected final List<Error> errors;

    protected DomainException(final String aMessage, List<Error> anErrors){
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(Error anError) {
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(List<Error> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}

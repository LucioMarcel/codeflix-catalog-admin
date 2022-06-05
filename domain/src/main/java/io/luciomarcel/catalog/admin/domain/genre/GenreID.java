package io.luciomarcel.catalog.admin.domain.genre;

import java.util.Objects;
import java.util.UUID;

import io.luciomarcel.catalog.admin.domain.Identifier;

public class GenreID extends Identifier{

    private final String value;

    private  GenreID(final String value) {
        Objects.requireNonNull(value, "'value' should not be null");
        this.value = value;
    }

    public static GenreID unique() {
        return GenreID.from(UUID.randomUUID());
    }

    public static GenreID from(final String anId){
        return new GenreID(anId);
    }
    public static GenreID from(final UUID anId){
        return new GenreID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreID that = (GenreID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

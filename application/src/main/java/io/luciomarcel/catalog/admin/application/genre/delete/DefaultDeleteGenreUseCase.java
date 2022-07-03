package io.luciomarcel.catalog.admin.application.genre.delete;

import java.util.Objects;

import io.luciomarcel.catalog.admin.domain.genre.GenreGateway;
import io.luciomarcel.catalog.admin.domain.genre.GenreID;

public class DefaultDeleteGenreUseCase extends DeleteGenreUseCase {

    private final GenreGateway genreGateway;
        
    public DefaultDeleteGenreUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public void execute(final String anId) {
        this.genreGateway.deleteById(GenreID.from(anId));
    }
    
}

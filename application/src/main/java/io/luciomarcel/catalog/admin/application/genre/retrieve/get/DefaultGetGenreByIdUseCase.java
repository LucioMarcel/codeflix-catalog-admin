package io.luciomarcel.catalog.admin.application.genre.retrieve.get;

import java.util.Objects;

import io.luciomarcel.catalog.admin.domain.exceptions.NotFoundException;
import io.luciomarcel.catalog.admin.domain.genre.Genre;
import io.luciomarcel.catalog.admin.domain.genre.GenreGateway;
import io.luciomarcel.catalog.admin.domain.genre.GenreID;

public class DefaultGetGenreByIdUseCase extends GetGenreByIdUseCase {

    private final GenreGateway genreGateway;
    
    public DefaultGetGenreByIdUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public GenreOutput execute(final String anId) {
        final var aGenreId = GenreID.from(anId);
        return this.genreGateway.findById(aGenreId)
            .map(GenreOutput::from)
            .orElseThrow(() -> NotFoundException.with(Genre.class, aGenreId));
    }
    
}

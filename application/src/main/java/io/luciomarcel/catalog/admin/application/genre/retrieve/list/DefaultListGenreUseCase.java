package io.luciomarcel.catalog.admin.application.genre.retrieve.list;

import java.util.Objects;

import io.luciomarcel.catalog.admin.domain.genre.GenreGateway;
import io.luciomarcel.catalog.admin.domain.pagination.Pagination;
import io.luciomarcel.catalog.admin.domain.pagination.SearchQuery;

public class DefaultListGenreUseCase extends ListGenreUsecase{

    private final GenreGateway genreGateway;


    public DefaultListGenreUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public Pagination<GenreListOutput> execute(final SearchQuery aQuery) {
        return this.genreGateway.findAll(aQuery)
            .map(GenreListOutput::from);
    }
    
}

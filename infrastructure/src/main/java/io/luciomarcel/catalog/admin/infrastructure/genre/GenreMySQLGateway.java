package io.luciomarcel.catalog.admin.infrastructure.genre;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import io.luciomarcel.catalog.admin.domain.genre.Genre;
import io.luciomarcel.catalog.admin.domain.genre.GenreGateway;
import io.luciomarcel.catalog.admin.domain.genre.GenreID;
import io.luciomarcel.catalog.admin.domain.pagination.Pagination;
import io.luciomarcel.catalog.admin.domain.pagination.SearchQuery;
import io.luciomarcel.catalog.admin.infrastructure.genre.persistence.GenreJpaEntity;
import io.luciomarcel.catalog.admin.infrastructure.genre.persistence.GenreRepository;

@Component
public class GenreMySQLGateway implements GenreGateway{

    private final GenreRepository genreRepository;

    public GenreMySQLGateway(final GenreRepository genreRepository) {
        this.genreRepository = Objects.requireNonNull(genreRepository);
    }
    
    @Override
    public Genre create(Genre aGenre) {
        return save(aGenre);
    }

    @Override
    public void deleteById(GenreID anId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Optional<Genre> findById(GenreID anId) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Genre update(Genre aGenre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pagination<Genre> findAll(SearchQuery aQuery) {
        // TODO Auto-generated method stub
        return null;
    }

    private Genre save(Genre aGenre) {
        return this.genreRepository.save(GenreJpaEntity.from(aGenre)).toAggregate();
    }
    
}

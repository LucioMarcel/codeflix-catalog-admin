package io.luciomarcel.catalog.admin.domain.genre;

import java.util.Optional;

import io.luciomarcel.catalog.admin.domain.pagination.Pagination;
import io.luciomarcel.catalog.admin.domain.pagination.SearchQuery;

public interface GenreGateway {
    	
	Genre create(Genre aGenre);

    void deleteById(GenreID anId);

    Optional<Genre> findById(GenreID anId);

    Genre update(Genre aGenre);

    Pagination<Genre> findAll(SearchQuery aQuery);
    
}
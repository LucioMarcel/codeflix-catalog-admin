package io.luciomarcel.catalog.admin.application.genre.retrieve.list;

import java.time.Instant;
import java.util.List;

import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.domain.genre.Genre;

public record GenreListOutput(
    String name,
    boolean isActive,
    List<String> categories,
    Instant createdAt,
    Instant deletedAt
) {

    public static  GenreListOutput from(final Genre aGenre){
        return new GenreListOutput(
            aGenre.getName(),
            aGenre.isActive(),
            aGenre.getCategories().stream()
                .map(CategoryID::getValue).toList(),
            aGenre.getCreatedAt(),
            aGenre.getDeletedAt()
        );
    }
    
}

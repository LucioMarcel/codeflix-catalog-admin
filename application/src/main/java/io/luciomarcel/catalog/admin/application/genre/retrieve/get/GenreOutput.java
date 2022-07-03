package io.luciomarcel.catalog.admin.application.genre.retrieve.get;

import java.time.Instant;
import java.util.List;

import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.domain.genre.Genre;

public record GenreOutput(
    String id,
    String name,
    boolean isActive,
    List<String> categories,
    Instant createdAt,
    Instant updatedAt,
    Instant deletedAt
) {

    public static GenreOutput from(final Genre aGenre) {
        return new GenreOutput(
            aGenre.getId().getValue(), 
            aGenre.getName(),
            aGenre.isActive(),
            aGenre.getCategories().stream()
                .map(CategoryID::getValue)
                .toList(),
            aGenre.getCreatedAt(),
            aGenre.getUpdatedAt(), 
            aGenre.getDeletedAt()
        );    
    }

}

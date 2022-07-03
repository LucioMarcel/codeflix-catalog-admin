package io.luciomarcel.catalog.admin.application.genre.update;

import io.luciomarcel.catalog.admin.domain.genre.Genre;

public record UpdateGenreOutput(String id) {

    public static UpdateGenreOutput from(final Genre aGenre){
        return new UpdateGenreOutput(aGenre.getId().getValue());
    }

}

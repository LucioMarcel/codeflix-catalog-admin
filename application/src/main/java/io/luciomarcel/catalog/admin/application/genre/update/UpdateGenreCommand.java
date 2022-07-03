package io.luciomarcel.catalog.admin.application.genre.update;

import java.util.List;

public record UpdateGenreCommand(
    String id,
    String name,
    boolean isActive,
    List<String> categpories
) {

    public static UpdateGenreCommand with(
        final String id,
        final String name,
        final Boolean isActive,
        final List<String> categpories
    ){
        return new UpdateGenreCommand(id, name, isActive != null ? isActive : true, categpories);
    }
    
}

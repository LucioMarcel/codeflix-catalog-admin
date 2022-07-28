package com.fullcycle.admin.catalogo.infrastructure.genre.models;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenreListResponse(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("is_active") Boolean active,
    @JsonProperty("cretaed_at") Instant cretaedAt,
    @JsonProperty("deleted_at") Instant deletedAt
) {
    
}

package io.luciomarcel.catalog.admin.infrastructure.category.models;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryListResponse(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("is_active") Boolean active,
    @JsonProperty("created_at")Instant createdAt,
    @JsonProperty("updated_at") Instant deletedAt
) {
    
}
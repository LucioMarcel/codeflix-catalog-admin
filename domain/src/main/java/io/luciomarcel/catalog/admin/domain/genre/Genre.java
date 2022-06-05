package io.luciomarcel.catalog.admin.domain.genre;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.luciomarcel.catalog.admin.domain.AggregateRoot;
import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.domain.exceptions.NotificationException;
import io.luciomarcel.catalog.admin.domain.validation.ValidationHandler;
import io.luciomarcel.catalog.admin.domain.validation.handler.Notification;

public class Genre extends AggregateRoot<GenreID> {

    private String name;
    private boolean active;
    private List<CategoryID> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    protected Genre(
        final GenreID anId, 
        final String aName, 
        final boolean isActive, 
        final List<CategoryID> categories,
        final Instant aCreatedAt, 
        final Instant anUpdatedAt, 
        final Instant aDeletedAt
    ) {
        super(anId);
        this.name = aName;
        this.active = isActive;
        this.categories = categories;
        this.createdAt = aCreatedAt;
        this.updatedAt = anUpdatedAt;
        this.deletedAt = aDeletedAt;

        final var notification = Notification.create();
        validate(notification);

        if (notification.hasErrors()) {
            throw new NotificationException("Failed to create a Aggregate Genre", notification);
        }   
    }

    public static Genre newGenre(final String aName,final boolean isActive) {
        final var anId = GenreID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;

        return new Genre(anId,aName,isActive,new ArrayList<>(),now,now,deletedAt        );
    }

    public static Genre with(
        final GenreID anId, 
        final String aName, 
        final boolean isActive,
        final List<CategoryID> categories,
        final Instant aCreatedAt, 
        final Instant anUpdatedAt, 
        final Instant aDeletedAt
    ) {
        return new Genre(anId, aName, isActive, categories, aCreatedAt, anUpdatedAt, aDeletedAt);
    }

    public static Genre with(Genre aGenre) {
        return new Genre(
            aGenre.getId(), 
            aGenre.getName(), 
            aGenre.isActive(), 
            new ArrayList<>(aGenre.getCategories()), 
            aGenre.getCreatedAt(),
            aGenre.getUpdatedAt(), 
            aGenre.getDeletedAt());
    }

    @Override
    public void validate(ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public String getName() {
        return this.name;
    }

    public boolean isActive() {
        return this.active;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(this.categories);
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }
    
}

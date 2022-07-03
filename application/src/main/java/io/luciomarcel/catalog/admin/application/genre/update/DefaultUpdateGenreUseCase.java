package io.luciomarcel.catalog.admin.application.genre.update;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import io.luciomarcel.catalog.admin.domain.Identifier;
import io.luciomarcel.catalog.admin.domain.category.CategoryGateway;
import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.domain.exceptions.NotFoundException;
import io.luciomarcel.catalog.admin.domain.exceptions.NotificationException;
import io.luciomarcel.catalog.admin.domain.genre.Genre;
import io.luciomarcel.catalog.admin.domain.genre.GenreGateway;
import io.luciomarcel.catalog.admin.domain.genre.GenreID;
import io.luciomarcel.catalog.admin.domain.validation.Error;
import io.luciomarcel.catalog.admin.domain.validation.ValidationHandler;
import io.luciomarcel.catalog.admin.domain.validation.handler.Notification;

public  class DefaultUpdateGenreUseCase extends UpdateGenreUsecase {

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public DefaultUpdateGenreUseCase(final CategoryGateway categoryGateway, final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public UpdateGenreOutput execute(final UpdateGenreCommand aCommand) {
        final var anId = GenreID.from(aCommand.id());
        final var name = aCommand.name();
        final var isActive = aCommand.isActive();
        final var categories = toCategoryId(aCommand.categpories());

        final var aGenre = this.genreGateway.findById(anId)
            .orElseThrow(notFound(anId));

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.validate(() ->aGenre.update(name, isActive, categories));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not update Aggregate Genre %s".formatted(aCommand.id()), notification);
        }

        return UpdateGenreOutput.from(this.genreGateway.update(aGenre));
    }

    private ValidationHandler validateCategories(final List<CategoryID> ids) {
        final var notification = Notification.create();
        if (ids == null || ids.isEmpty()){
            return notification;
        }

        final var retrievedIds = categoryGateway.existsByIds(ids);

        if (ids.size() != retrievedIds.size()){
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMressage = missingIds.stream()
                .map(CategoryID::getValue)
                .collect(Collectors.joining(", "));

            notification.append(new Error("Some categories could not be found: %s".formatted(missingIdsMressage)));
        }
        
        return notification;
    }

    private List<CategoryID> toCategoryId(List<String> categories){
        return categories.stream()
            .map(CategoryID::from)
            .toList();
    }

    private Supplier<NotFoundException> notFound(final Identifier anId) {
        return () -> NotFoundException.with(Genre.class, anId);
    }
    
}

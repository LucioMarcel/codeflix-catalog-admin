package io.luciomarcel.catalog.admin.application.genre.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.luciomarcel.catalog.admin.domain.category.CategoryGateway;
import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.domain.exceptions.NotificationException;
import io.luciomarcel.catalog.admin.domain.genre.Genre;
import io.luciomarcel.catalog.admin.domain.genre.GenreGateway;
import io.luciomarcel.catalog.admin.domain.validation.Error;
import io.luciomarcel.catalog.admin.domain.validation.ValidationHandler;
import io.luciomarcel.catalog.admin.domain.validation.handler.Notification;

public class DefaultCreateGenreUseCase extends CreateGenreUseCase{

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;


    public DefaultCreateGenreUseCase(final CategoryGateway categoryGateway, final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public CreateGenreOutput execute(CreateGenreCommand aCommand) {
        final var aName = aCommand.name();
        final var isActive = aCommand.isActive();
        final var categories = toCategoryID(aCommand.categories());

        final var notification = Notification.create();

        notification.append(validateCategories(categories));
        final var aGenre = notification.validate(() -> Genre.newGenre(aName, isActive));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not create Aggregate Genre", notification);
        }

        aGenre.addCategories(categories);
        
        return CreateGenreOutput.from(this.genreGateway.create(aGenre));
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

    private List<CategoryID> toCategoryID(List<String> categories) {
        return categories.stream()
            .map(CategoryID::from)
            .toList();
    }
    
}

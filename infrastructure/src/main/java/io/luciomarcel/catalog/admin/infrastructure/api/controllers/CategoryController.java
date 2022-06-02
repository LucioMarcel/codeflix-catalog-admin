package io.luciomarcel.catalog.admin.infrastructure.api.controllers;

import io.luciomarcel.catalog.admin.application.category.create.CreateCategoryCommand;
import io.luciomarcel.catalog.admin.application.category.create.CreateCategoryOutput;
import io.luciomarcel.catalog.admin.application.category.create.CreateCategoryUseCase;
import io.luciomarcel.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import io.luciomarcel.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import io.luciomarcel.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import io.luciomarcel.catalog.admin.application.category.update.UpdateCategoryCommand;
import io.luciomarcel.catalog.admin.application.category.update.UpdateCategoryOutput;
import io.luciomarcel.catalog.admin.application.category.update.UpdateCategoryUseCase;
import io.luciomarcel.catalog.admin.domain.category.CategorySearchQuery;
import io.luciomarcel.catalog.admin.domain.category.Pagination;
import io.luciomarcel.catalog.admin.domain.validation.handler.Notification;
import io.luciomarcel.catalog.admin.infrastructure.api.CategoryAPI;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CategoryApiOutput;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CreateCategoryApiInput;
import io.luciomarcel.catalog.admin.infrastructure.category.models.UpdateCategoryApiInput;
import io.luciomarcel.catalog.admin.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private UpdateCategoryUseCase updateCategoryUseCase;
    private DeleteCategoryUseCase deleteCategoryUseCase;

    private ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase,
            final ListCategoriesUseCase listCategoriesUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryApiInput input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        //final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        // Pode-se usar a sintaxe acimna (método de referência) ou a sintaxe abaixo

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id() )).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return this.listCategoriesUseCase
                .execute(new CategorySearchQuery(page,perPage, search,sort,direction));
    }

    @Override
    public CategoryApiOutput getById(final String id) {
        //return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
        //mesama coisa da linha acima, só que autilizando a propriedade da classe utilitária ao invés do método/
        return CategoryApiPresenter.present.apply(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryApiInput input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }
}

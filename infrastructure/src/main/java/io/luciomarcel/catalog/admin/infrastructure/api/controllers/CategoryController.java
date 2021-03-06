package io.luciomarcel.catalog.admin.infrastructure.api.controllers;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.luciomarcel.catalog.admin.application.category.create.CreateCategoryCommand;
import io.luciomarcel.catalog.admin.application.category.create.CreateCategoryOutput;
import io.luciomarcel.catalog.admin.application.category.create.CreateCategoryUseCase;
import io.luciomarcel.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import io.luciomarcel.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import io.luciomarcel.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import io.luciomarcel.catalog.admin.application.category.update.UpdateCategoryCommand;
import io.luciomarcel.catalog.admin.application.category.update.UpdateCategoryOutput;
import io.luciomarcel.catalog.admin.application.category.update.UpdateCategoryUseCase;
import io.luciomarcel.catalog.admin.domain.pagination.SearchQuery;
import io.luciomarcel.catalog.admin.domain.pagination.Pagination;
import io.luciomarcel.catalog.admin.domain.validation.handler.Notification;
import io.luciomarcel.catalog.admin.infrastructure.api.CategoryAPI;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CategoryListResponse;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CategoryResponse;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CreateCategoryRequest;
import io.luciomarcel.catalog.admin.infrastructure.category.models.UpdateCategoryRequest;
import io.luciomarcel.catalog.admin.infrastructure.category.presenters.CategoryApiPresenter;

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
    public ResponseEntity<?> createCategory(CreateCategoryRequest input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        //final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        // Pode-se usar a sintaxe acimna (m??todo de refer??ncia) ou a sintaxe abaixo

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id() )).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listCategoriesUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(CategoryApiPresenter::present);
    }

    public CategoryResponse getById(final String id) {
        //return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
        //mesama coisa da linha acima, s?? que autilizando a propriedade da classe utilit??ria ao inv??s do m??todo/
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest input) {
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

package io.luciomarcel.catalog.admin.application.category.retrieve.get;

import java.util.Objects;
import java.util.function.Supplier;

import io.luciomarcel.catalog.admin.domain.category.Category;
import io.luciomarcel.catalog.admin.domain.category.CategoryGateway;
import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.domain.exceptions.NotFoundException;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {
    private CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var anCategoryID = CategoryID.from(anIn);
        return this.categoryGateway.findById(anCategoryID)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(anCategoryID));
    }

    private Supplier<NotFoundException> notFound(final CategoryID anId) {
        return () -> NotFoundException.with(Category.class,anId);
    }
}
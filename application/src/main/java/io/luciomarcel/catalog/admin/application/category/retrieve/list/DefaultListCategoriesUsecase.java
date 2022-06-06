package io.luciomarcel.catalog.admin.application.category.retrieve.list;

import io.luciomarcel.catalog.admin.domain.category.CategoryGateway;
import io.luciomarcel.catalog.admin.domain.pagination.SearchQuery;
import io.luciomarcel.catalog.admin.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUsecase extends ListCategoriesUseCase{

    private CategoryGateway categoryGateway;

    public DefaultListCategoriesUsecase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}

package io.luciomarcel.catalog.admin.application.category.retrieve.list;

import io.luciomarcel.catalog.admin.domain.pagination.SearchQuery;
import io.luciomarcel.catalog.admin.domain.pagination.Pagination;
import io.luciomarcel.catalog.admin.application.UseCase;

public abstract class ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {
}

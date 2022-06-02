package io.luciomarcel.catalog.admin.application.category.retrieve.list;

import io.luciomarcel.catalog.admin.domain.category.CategorySearchQuery;
import io.luciomarcel.catalog.admin.domain.category.Pagination;
import io.luciomarcel.catalog.admin.application.UseCase;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}

package io.luciomarcel.catalog.admin.application.genre.retrieve.list;

import io.luciomarcel.catalog.admin.application.UseCase;
import io.luciomarcel.catalog.admin.domain.pagination.Pagination;
import io.luciomarcel.catalog.admin.domain.pagination.SearchQuery;

public abstract class ListGenreUsecase extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}

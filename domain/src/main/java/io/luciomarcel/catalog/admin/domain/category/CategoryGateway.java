package io.luciomarcel.catalog.admin.domain.category;

import java.util.List;
import java.util.Optional;

import io.luciomarcel.catalog.admin.domain.pagination.Pagination;
import io.luciomarcel.catalog.admin.domain.pagination.SearchQuery;

public interface CategoryGateway {

    Category create(Category aCategory);

    void deleteById(CategoryID anId);

    Optional<Category> findById(CategoryID anId);

    Category update(Category aCategory);

    Pagination<Category> findAll(SearchQuery aQuery);

    List<CategoryID> existsByIds(Iterable<CategoryID> ids);
}

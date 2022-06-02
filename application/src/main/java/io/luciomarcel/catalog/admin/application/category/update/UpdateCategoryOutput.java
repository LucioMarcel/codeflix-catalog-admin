package io.luciomarcel.catalog.admin.application.category.update;

import io.luciomarcel.catalog.admin.domain.category.Category;

public record UpdateCategoryOutput(
        String id
) {
    public static  UpdateCategoryOutput from(final String anId){
        return new UpdateCategoryOutput(anId);
    }

    public static  UpdateCategoryOutput from(final Category aCategory){
        return new UpdateCategoryOutput(aCategory.getId().getValue());
    }
}

package io.luciomarcel.catalog.admin.infrastructure.category.presenters;

import java.util.function.Function;

import io.luciomarcel.catalog.admin.application.category.retrieve.get.CategoryOutput;
import io.luciomarcel.catalog.admin.application.category.retrieve.list.CategoryListOutput;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CategoryResponse;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CategoryListResponse;

//implementação de classe utilitária usando interface
public interface CategoryApiPresenter {

    static CategoryResponse present(CategoryOutput output){
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    //Outra maneira de escrever o método acima, usando uma propriedade
    Function<CategoryOutput,CategoryResponse> present = output -> new CategoryResponse(
            output.id().getValue(),
            output.name(),
            output.description(),
            output.isActive(),
            output.createdAt(),
            output.updatedAt(),
            output.deletedAt()
    );

    public static CategoryListResponse present(final CategoryListOutput output){
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }

}

package io.luciomarcel.catalog.admin.infrastructure.category.presenters;

import io.luciomarcel.catalog.admin.application.category.retrieve.get.CategoryOutput;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CategoryApiOutput;

import java.util.function.Function;

//implementação de classe utilitária usando interface
public interface CategoryApiPresenter {

    static CategoryApiOutput present(CategoryOutput output){
        return new CategoryApiOutput(
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
    Function<CategoryOutput,CategoryApiOutput> present = output -> new CategoryApiOutput(
            output.id().getValue(),
            output.name(),
            output.description(),
            output.isActive(),
            output.createdAt(),
            output.updatedAt(),
            output.deletedAt()
    );

}

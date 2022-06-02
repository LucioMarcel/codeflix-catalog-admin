package io.luciomarcel.catalog.admin.infrastructure.configuration.usecases;

import io.luciomarcel.catalog.admin.application.category.create.CreateCategoryUseCase;
import io.luciomarcel.catalog.admin.application.category.create.DefaultCreateCategoryUseCase;
import io.luciomarcel.catalog.admin.application.category.delete.DefaultDeleteCategoryUsecase;
import io.luciomarcel.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import io.luciomarcel.catalog.admin.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import io.luciomarcel.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import io.luciomarcel.catalog.admin.application.category.retrieve.list.DefaultListCategoriesUsecase;
import io.luciomarcel.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import io.luciomarcel.catalog.admin.application.category.update.DefaultUpdateCategoryUseCase;
import io.luciomarcel.catalog.admin.application.category.update.UpdateCategoryUseCase;
import io.luciomarcel.catalog.admin.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUsecase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUsecase(categoryGateway);
    }

}

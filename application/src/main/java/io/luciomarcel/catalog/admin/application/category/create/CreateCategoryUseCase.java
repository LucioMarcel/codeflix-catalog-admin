package io.luciomarcel.catalog.admin.application.category.create;

import io.luciomarcel.catalog.admin.domain.validation.handler.Notification;
import io.luciomarcel.catalog.admin.application.UseCase;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {

}

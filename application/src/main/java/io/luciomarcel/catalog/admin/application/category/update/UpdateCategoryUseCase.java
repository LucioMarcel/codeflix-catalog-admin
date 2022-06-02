package io.luciomarcel.catalog.admin.application.category.update;

import io.luciomarcel.catalog.admin.domain.validation.handler.Notification;
import io.luciomarcel.catalog.admin.application.UseCase;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification,UpdateCategoryOutput>> {
}

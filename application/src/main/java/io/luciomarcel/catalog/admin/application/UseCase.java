package io.luciomarcel.catalog.admin.application;

import io.luciomarcel.catalog.admin.domain.category.Category;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);

}
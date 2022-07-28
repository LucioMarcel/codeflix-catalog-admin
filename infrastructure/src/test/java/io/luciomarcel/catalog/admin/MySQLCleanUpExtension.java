package io.luciomarcel.catalog.admin;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.luciomarcel.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import io.luciomarcel.catalog.admin.infrastructure.genre.persistence.GenreRepository;

public class MySQLCleanUpExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) throws Exception {
        // final var  repositories = SpringExtension
        //         .getApplicationContext(context)
        //         .getBeansOfType(CrudRepository.class)
        //         .values();

        final var appContext = SpringExtension.getApplicationContext(context);

        cleanUp(List.of(
            appContext.getBean(GenreRepository.class),
            appContext.getBean(CategoryRepository.class)
        ));


        //  final var em = appContext.getBean(TestEntityManager.class);
        //  em.flush();
        //  em.clear();
    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
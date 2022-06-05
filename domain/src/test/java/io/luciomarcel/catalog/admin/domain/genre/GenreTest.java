package io.luciomarcel.catalog.admin.domain.genre;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.luciomarcel.catalog.admin.domain.exceptions.NotificationException;

public class GenreTest {

    @Test
    public void givenValidParams_whenCallNewGenre_thenShouldInstantiateAGenre() {
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = 0;

        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        Assertions.assertNotNull(actualGenre);
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories().size());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenInvalidNullName_whenCallNewGenreAndValidate_thenShouldReceiveAnError() {
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidEmptyName_whenCallNewGenreAndValidate_thenShouldReceiveAnError() {
        final String expectedName = " ";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

       final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidNameWithLengthGreaterThean255_whenCallNewGenreAndValidate_thenShouldReceiveAnError() {
        final String expectedName = """
            Pensando mais a longo prazo, o desenvolvimento contínuo de distintas formas de codificação pode nos levar a considerar a reestruturação dos procolos comumente utilizados em
            redes legadas. É importante questionar o quanto o uso de servidores em datacenter otimiza o uso dos processadores da garantia da disponibilidade. Evidentemente, a utilização 
            de recursos de hardware dedicados apresenta tendências no sentido de aprovar a nova topologia da gestão de risco. Neste sentido, o consenso sobre a utilização da orientação a 
            objeto não pode mais se dissociar dos paradigmas de desenvolvimento de software. No nível organizacional, o comprometimento entre as equipes de implantação ainda não 
            demonstrou convincentemente que está estável o suficiente das ferramentas OpenSource.
            """;
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

}

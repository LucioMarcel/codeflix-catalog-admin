package io.luciomarcel.catalog.admin.domain.category;

import io.luciomarcel.catalog.admin.domain.exceptions.DomainException;
import io.luciomarcel.catalog.admin.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory(){
        final var expectedName =  "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expecteIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName,  actualCategory.getName());
        Assertions.assertEquals(expectedDescription,  actualCategory.getDescription());
        Assertions.assertEquals(expecteIsActive,  actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName =  null;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expecteIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName =  "Fi ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should must be between 3 and 255 characters";
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expecteIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName =  """
                Pensando mais a longo prazo, o desenvolvimento contínuo de distintas formas de codificação pode nos levar a considerar a reestruturação dos procolos comumente utilizados em
                redes legadas. É importante questionar o quanto o uso de servidores em datacenter otimiza o uso dos processadores da garantia da disponibilidade. Evidentemente, a utilização 
                de recursos de hardware dedicados apresenta tendências no sentido de aprovar a nova topologia da gestão de risco. Neste sentido, o consenso sobre a utilização da orientação a 
                objeto não pode mais se dissociar dos paradigmas de desenvolvimento de software. No nível organizacional, o comprometimento entre as equipes de implantação ainda não 
                demonstrou convincentemente que está estável o suficiente das ferramentas OpenSource.
                """;

        final var expectedErrorMessage = "'name' should must be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expecteIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDerscription_whenCallNewCategoryAndValidate_thenShouldReceiveOk(){
        final String expectedName =  "Filmes";
        final var expectedDescription = "  ";
        final var expecteIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expecteIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName,  actualCategory.getName());
        Assertions.assertEquals(expectedDescription,  actualCategory.getDescription());
        Assertions.assertEquals(expecteIsActive,  actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveOk(){
        final String expectedName =  "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = false;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expecteIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName,  actualCategory.getName());
        Assertions.assertEquals(expectedDescription,  actualCategory.getDescription());
        Assertions.assertEquals(expecteIsActive,  actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidAcrtiveCategory_whenCallDeactivate_theReturnCategoryInactivated(){
        final String expectedName =  "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = false;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getUpdatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualCategory  = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName,  actualCategory.getName());
        Assertions.assertEquals(expectedDescription,  actualCategory.getDescription());
        Assertions.assertEquals(expecteIsActive,  actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_theReturnCategoryActivated(){
        final String expectedName =  "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getUpdatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());

        final var actualCategory  = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName,  actualCategory.getName());
        Assertions.assertEquals(expectedDescription,  actualCategory.getDescription());
        Assertions.assertEquals(expecteIsActive,  actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final String expectedName =  "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = true;

        final var aCategory = Category.newCategory("Film", "A categoria", false);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getUpdatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expecteIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName,  actualCategory.getName());
        Assertions.assertEquals(expectedDescription,  actualCategory.getDescription());
        Assertions.assertEquals(expecteIsActive,  actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() {
        final String expectedName =  "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = false;

        final var aCategory = Category.newCategory("Film", "A categoria", true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var createdAt = aCategory.getUpdatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expecteIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName,  actualCategory.getName());
        Assertions.assertEquals(expectedDescription,  actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
        Assertions.assertEquals(expecteIsActive,  actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());

    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() {
        final String expectedName =  null;
        final var expectedDescription = "A categoria mais assistida";
        final var expecteIsActive = true;

        final var aCategory = Category.newCategory("Filmes", "A categoria", expecteIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getUpdatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expecteIsActive);

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName,  actualCategory.getName());
        Assertions.assertEquals(expectedDescription,  actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(aCategory.getDeletedAt());
    }

}


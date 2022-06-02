package io.luciomarcel.catalog.admin.application.category.update;

import io.luciomarcel.catalog.admin.domain.category.Category;
import io.luciomarcel.catalog.admin.domain.category.CategoryGateway;
import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }


    @Test
    public void givenAvalidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory =
                Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).findById(eq(expectedId));

        verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription,aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(),aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));

    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException(){
        final var aCategory =
                Category.newCategory("Film", null, true);

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = true;
        final var expectedId = aCategory.getId();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsTActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        final  var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount,notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage,notification.firstError().message());

        verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAvalidInactivateCommand_whenCallsUpdateCategory_shouldReturnInactivateCategoryId() {
        final var aCategory =
                Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = false;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsTActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).findById(eq(expectedId));

        verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription,aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsTActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(),aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.nonNull(aUpdatedCategory.getDeletedAt())
        ));

    }

    @Test
    public void givenAvalidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var aCategory =
                Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = true;
        final var expectedId = aCategory.getId();

        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsTActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenThrow(new IllegalStateException("Gateway error"));

        final  var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount,notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage,notification.firstError().message());

        verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription,aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsTActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(),aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithinvalidId_whenCallsUpdateCategory_shouldReturnNotFoundException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID 123 was not found";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsTActive
        );

        when(categoryGateway.findById(eq(CategoryID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));

        verify(categoryGateway, times(0)).update(any());

    }

}

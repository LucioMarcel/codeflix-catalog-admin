package io.luciomarcel.catalog.admin.application.genre.update;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.luciomarcel.catalog.admin.application.UseCaseTest;
import io.luciomarcel.catalog.admin.domain.category.CategoryGateway;
import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.domain.exceptions.NotificationException;
import io.luciomarcel.catalog.admin.domain.genre.Genre;
import io.luciomarcel.catalog.admin.domain.genre.GenreGateway;

public class UpdateGenreUsecaseTest extends UseCaseTest{

    @InjectMocks
    private DefaultUpdateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateGenre_thenShouldReturnGenreId(){
        final var aGenre = Genre.newGenre("acao", true);
        
        // given
        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
            expectedId.getValue(),
            expectedName, 
            expectedIsActive, 
            asString(expectedCategories));

        Mockito.when(genreGateway.findById(Mockito.any()))
            .thenReturn(Optional.of(Genre.with(aGenre)));

        Mockito.when(genreGateway.update(Mockito.any()))
            .thenAnswer(AdditionalAnswers.returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, times(1)).findById(Mockito.eq(expectedId));
        
        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdateGenre -> 
            Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedName, aUpdateGenre.getName())
                && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                && Objects.isNull(aUpdateGenre.getDeletedAt())
        ));

    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsUpdateGenre_thenShouldReturnGenreId(){
        final var aGenre = Genre.newGenre("acao", true);
        
        // given
        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
            CategoryID.from("123"),
            CategoryID.from("456")
        );

        final var aCommand = UpdateGenreCommand.with(
            expectedId.getValue(),
            expectedName, 
            expectedIsActive, 
            asString(expectedCategories));

        Mockito.when(genreGateway.findById(Mockito.any()))
            .thenReturn(Optional.of(Genre.with(aGenre)));

        Mockito.when(categoryGateway.existsByIds(Mockito.any()))
                .thenReturn(expectedCategories);            

        Mockito.when(genreGateway.update(Mockito.any()))
            .thenAnswer(AdditionalAnswers.returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, times(1)).findById(Mockito.eq(expectedId));

        Mockito.verify(categoryGateway, times(1)).existsByIds(expectedCategories);
        
        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdateGenre -> 
            Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedName, aUpdateGenre.getName())
                && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                && Objects.isNull(aUpdateGenre.getDeletedAt())
        ));

    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsUpdateGenre_thenShouldReturnGenreId(){
        final var aGenre = Genre.newGenre("acao", true);
        
        // given
        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
            expectedId.getValue(),
            expectedName, 
            expectedIsActive, 
            asString(expectedCategories));

        Mockito.when(genreGateway.findById(Mockito.any()))
            .thenReturn(Optional.of(Genre.with(aGenre)));

        Mockito.when(genreGateway.update(Mockito.any()))
            .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Assertions.assertNull(aGenre.getDeletedAt());
        Assertions.assertTrue(aGenre.isActive());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, times(1)).findById(Mockito.eq(expectedId));
        
        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdateGenre -> 
            Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedName, aUpdateGenre.getName())
                && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                && Objects.nonNull(aUpdateGenre.getDeletedAt())
        ));

    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateGenre_thenShouldReturnNotificationException(){
        final var aGenre = Genre.newGenre("acao", true);
        
        // given
        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateGenreCommand.with(
            expectedId.getValue(),
            expectedName, 
            expectedIsActive, 
            asString(expectedCategories));

        Mockito.when(genreGateway.findById(Mockito.any()))
            .thenReturn(Optional.of(Genre.with(aGenre)));

        // when
        final var actualException = assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(genreGateway, times(1)).findById(Mockito.eq(expectedId));

        Mockito.verify(categoryGateway, times(0)).existsByIds(Mockito.any());
        
        Mockito.verify(genreGateway, times(0)).update(Mockito.any());
    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateGenreAndSomeCategoriesDoesNotExists_thenShouldReturnNotificationException(){
        final var filmes = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("789");
        final var aGenre = Genre.newGenre("acao", true);
        
        // given
        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes, series, documentarios);

        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be null";
        final var expectedErrorCount = 2;

        final var aCommand = UpdateGenreCommand.with(
            expectedId.getValue(),
            expectedName, 
            expectedIsActive, 
            asString(expectedCategories));

        Mockito.when(genreGateway.findById(Mockito.any()))
            .thenReturn(Optional.of(Genre.with(aGenre)));

        Mockito.when(categoryGateway.existsByIds(Mockito.any()))
            .thenReturn(List.of(filmes));

        // when
        final var actualException = assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());

        Mockito.verify(genreGateway, times(1)).findById(Mockito.eq(expectedId));

        Mockito.verify(categoryGateway, times(1)).existsByIds(expectedCategories);
        
        Mockito.verify(genreGateway, times(0)).update(Mockito.any());
    }

    private List<String> asString(final List<CategoryID> ids) {
        return ids.stream()
            .map(CategoryID::getValue)
            .toList();
    }
        
}

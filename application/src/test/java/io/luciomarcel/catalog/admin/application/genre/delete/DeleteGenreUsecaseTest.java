package io.luciomarcel.catalog.admin.application.genre.delete;

import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.luciomarcel.catalog.admin.application.UseCaseTest;
import io.luciomarcel.catalog.admin.domain.genre.Genre;
import io.luciomarcel.catalog.admin.domain.genre.GenreGateway;
import io.luciomarcel.catalog.admin.domain.genre.GenreID;

public class DeleteGenreUsecaseTest extends UseCaseTest{

    @InjectMocks
    private DefaultDeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_thenShouldDeleteGenre(){
        //given
        final var aGenre = Genre.newGenre("Ação", true);
        final var expectedId = aGenre.getId();

        Mockito.doNothing()
            .when(genreGateway).deleteById(Mockito.any());

        //when 
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //when 
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAnInvalidGenreId_whenCallsDeleteGenre_thenShouldBeoK(){
        //given
        final var expectedId = GenreID.from("123");

        Mockito.doNothing()
            .when(genreGateway).deleteById(Mockito.any());

        //when 
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //when 
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAnValidGenreId_whenCallsDeleteGenreAndGatewwayThrownsUnexpectedError_thenShouldReceiveException(){
        //given
        final var aGenre = Genre.newGenre("Ação", true);
        final var expectedId = aGenre.getId();

        Mockito.doThrow(new IllegalStateException("Gateway Error"))
            .when(genreGateway).deleteById(Mockito.any());

        //when 
        Assertions.assertThrows(IllegalStateException.class, () -> {
            useCase.execute(expectedId.getValue());
        });

        //when 
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }
    
}

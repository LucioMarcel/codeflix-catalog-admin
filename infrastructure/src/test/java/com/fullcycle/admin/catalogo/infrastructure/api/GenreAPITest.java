package com.fullcycle.admin.catalogo.infrastructure.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.google.common.base.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admin.catalogo.ControllerTest;
import com.fullcycle.admin.catalogo.application.genre.create.CreateGenreOutput;
import com.fullcycle.admin.catalogo.application.genre.create.CreateGenreUseCase;
import com.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import com.fullcycle.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;

@ControllerTest(controllers = GenreAPI.class)
public class GenreAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateGenreUseCase createGenreUseCase;

    @Test
    private void givenAValidCommand_whenCallsCreateGenre_thenReturnGenreId() throws Exception {
        //given
        final var expectedName = "Ação";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;
        final var expectedId = "123";

        final var aCommand = new CreateGenreRequest(expectedName, expectedCategories, expectedIsActive);

        when(createGenreUseCase.execute(any()))
            .thenReturn(CreateGenreOutput.from(expectedId));

        //when
        final var aRequest = MockMvcRequestBuilders.post("/genres")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(aRequest)
            .andDo(MockMvcResultHandlers.print());

        //then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.header().string("Location" ,"/genres/" + expectedId))
            .andExpect(MockMvcResultMatchers.header().string("Content-Type" ,MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(expectedId)));

        Mockito.verify(createGenreUseCase, times(1)).execute(Mockito.argThat(cmd -> 
            Objects.equal(expectedName, cmd.name())
                && Objects.equal(expectedCategories, cmd.categories())
                && Objects.equal(expectedIsActive, cmd.isActive())
        ));

    }

    @Test
    private void givenAInvalidCommand_whenCallsCreateGenre_thenShouldReturnNotification() throws Exception {
        //given
        final String expectedName = null;
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = new CreateGenreRequest(expectedName, expectedCategories, expectedIsActive);

        when(createGenreUseCase.execute(any()))
            .thenThrow(new NotificationException("Erro", Notification.create(new Error(expectedErrorMessage))));

        //when
        final var aRequest = MockMvcRequestBuilders.post("/genres")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(aRequest)
            .andDo(MockMvcResultHandlers.print());

        //then
        response.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
            .andExpect(MockMvcResultMatchers.header().string("Location" , Matchers.nullValue()))
            .andExpect(MockMvcResultMatchers.header().string("Content-Type" ,MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]", Matchers.equalTo(expectedErrorMessage)));

        Mockito.verify(createGenreUseCase, times(1)).execute(Mockito.argThat(cmd -> 
            Objects.equal(expectedName, cmd.name())
                && Objects.equal(expectedCategories, cmd.categories())
                && Objects.equal(expectedIsActive, cmd.isActive())
        ));
    }
    
}

package io.luciomarcel.catalog.admin.e2e.category;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.luciomarcel.catalog.admin.E2ETest;
import io.luciomarcel.catalog.admin.domain.category.CategoryID;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CategoryResponse;
import io.luciomarcel.catalog.admin.infrastructure.category.models.CreateCategoryRequest;
import io.luciomarcel.catalog.admin.infrastructure.category.models.UpdateCategoryRequest;
import io.luciomarcel.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import io.luciomarcel.catalog.admin.infrastructure.configuration.json.Json;

@E2ETest
@Testcontainers
public class CategoryE2ETest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Container
    private static final MySQLContainer MYSQL_CONTAINER = 
        new MySQLContainer("mysql:latest")
        .withPassword("123456")
        .withUsername("root")
        .withDatabaseName("adm_videos");

    @DynamicPropertySource
    public static void setDataSourceProperties(final DynamicPropertyRegistry registry) {
        final var mappedPort = MYSQL_CONTAINER.getMappedPort(3306);
        System.out.printf("Container is running on port %s\n", mappedPort);
        registry.add("mysql.port", () -> mappedPort);
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToCreateANewCategoryWithValidValues() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = true;

        final var actualId = givenACategoryWith(expectedName, expectedDescription, expectedIsTActive);

        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsTActive, actualCategory.isActive());
        Assertions.assertNotNull( actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToGetAategoryByItsIdentifier() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = true;

        final var actualId = givenACategoryWith(expectedName, expectedDescription, expectedIsTActive);

        final var actualCategory = retrieveAVCategory(actualId.getValue());

        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsTActive, actualCategory.active());
        Assertions.assertNotNull( actualCategory.createdAt());
        Assertions.assertNotNull(actualCategory.updatedAt());
        Assertions.assertNull(actualCategory.deletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToUpdatetAategoryByItsIdentifier() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        final var actualId = givenACategoryWith("Movies", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = true;

        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsTActive);

        final var aRequest = put("/categories/" + actualId.getValue())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(Json.writeValueAsString(aRequestBody));

       this.mvc.perform(aRequest)
            .andExpect(status().isOk());

        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsTActive, actualCategory.isActive());
        Assertions.assertNotNull( actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToInactivateACategoryByItsIdentifier() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = false;

        final var actualId = givenACategoryWith(expectedName, expectedDescription, true);

        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsTActive);

        final var aRequest = put("/categories/" + actualId.getValue())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(Json.writeValueAsString(aRequestBody));

       this.mvc.perform(aRequest)
            .andExpect(status().isOk());

        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsTActive, actualCategory.isActive());
        Assertions.assertNotNull( actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToActivateACategoryByItsIdentifier() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsTActive = true;

        final var actualId = givenACategoryWith(expectedName, expectedDescription, false);

        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsTActive);

        final var aRequest = put("/categories/" + actualId.getValue())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(Json.writeValueAsString(aRequestBody));

       this.mvc.perform(aRequest)
            .andExpect(status().isOk());

        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsTActive, actualCategory.isActive());
        Assertions.assertNotNull( actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToSeeATreatedErrorByGettingANotFoudCategory() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());


        final var aRequest = MockMvcRequestBuilders.get("/categories/123")
            .accept(MediaType.APPLICATION_JSON) 
            .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(aRequest)
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(jsonPath("$.message", equalTo("Category with ID 123 was not found")));
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToNavigateToAllCategories() throws Exception{
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        givenACategoryWith("Filmes", null, true);
        givenACategoryWith("Documentários", null, true);
        givenACategoryWith("Séries", null, true);

        listCategories(0,1)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.current_page", equalTo(0)))
            .andExpect(jsonPath("$.per_page", equalTo(1)))
            .andExpect(jsonPath("$.total", equalTo(3)))
            .andExpect(jsonPath("$.items", hasSize(1)))
            .andExpect(jsonPath("$.items[0].name", equalTo("Documentários")));
            
            listCategories(1,1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(1)))
                .andExpect(jsonPath("$.per_page", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(3)))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].name", equalTo("Filmes")));

            listCategories(2,1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(2)))  
                .andExpect(jsonPath("$.per_page", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(3)))
                .andExpect(jsonPath("$.items", hasSize(1))) 
                .andExpect(jsonPath("$.items[0].name", equalTo("Séries")));

            listCategories(3,1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(3)))
                .andExpect(jsonPath("$.per_page", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(3)))
                .andExpect(jsonPath("$.items", hasSize(0)));
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToSearchBetweenAllCategories() throws Exception{
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        givenACategoryWith("Filmes", null, true);
        givenACategoryWith("Documentários", null, true);
        givenACategoryWith("Séries", null, true);

        listCategories(0, 1, "fil")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.current_page", equalTo(0)))
            .andExpect(jsonPath("$.per_page", equalTo(1)))
            .andExpect(jsonPath("$.total", equalTo(1)))
            .andExpect(jsonPath("$.items", hasSize(1)))
            .andExpect(jsonPath("$.items[0].name", equalTo("Filmes")));
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToSortAllCategoriesByDescriptionDesc() throws Exception{
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        givenACategoryWith("Filmes", "C", true);
        givenACategoryWith("Documentários", "Z", true);
        givenACategoryWith("Séries", "A", true);

        listCategories(0, 3, "", "description", "desc")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.current_page", equalTo(0)))
            .andExpect(jsonPath("$.per_page", equalTo(3)))
            .andExpect(jsonPath("$.total", equalTo(3)))
            .andExpect(jsonPath("$.items", hasSize(3)))
            .andExpect(jsonPath("$.items[0].name", equalTo("Documentários")))
            .andExpect(jsonPath("$.items[1].name", equalTo("Filmes")))
            .andExpect(jsonPath("$.items[2].name", equalTo("Séries")));
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToDeletetACategoryByItsIdentifier() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        final var actualId = givenACategoryWith("Filmes", null, true);

        this.mvc.perform(MockMvcRequestBuilders.delete("/categories/" + actualId.getValue())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        Assertions.assertFalse(this.categoryRepository.existsById(actualId.getValue()));
    }

    private ResultActions listCategories(final int page, final int perPage, final String search) throws Exception {
        return listCategories(page, perPage, search, "", "");
    }

    private ResultActions listCategories(final int page, final int perPage) throws Exception {
        return listCategories(page, perPage, "", "", "");
    }

    private ResultActions listCategories(
        final int page, 
        final int perPage, 
        final String search, 
        final String sort, 
        final String direction
    ) throws Exception{
        final var aRequest = MockMvcRequestBuilders.get("/categories")
            .param("page", String.valueOf(page))
            .param("perPage", String.valueOf(perPage))
            .param("search", search)
            .param("sort", sort)
            .param("direction", direction)
            .accept(MediaType.APPLICATION_JSON) 
            .contentType(MediaType.APPLICATION_JSON);

        return this.mvc.perform(aRequest);
    }
    
    
    private CategoryID givenACategoryWith(final String expectedName, final String expectedDescription, final boolean expectedIsTActive) throws Exception {
        final var aRequestBody = new CreateCategoryRequest(expectedName, expectedDescription, expectedIsTActive);

        final var aRequest = MockMvcRequestBuilders.post("/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(Json.writeValueAsString(aRequestBody));

        final var actualId = this.mvc.perform(aRequest)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn()
            .getResponse().getHeader("Location")
            .replace("/categories/", "");

        return CategoryID.from(actualId);
      }

      private CategoryResponse retrieveAVCategory(final String anId) throws Exception {
        final var aRequest = MockMvcRequestBuilders.get("/categories/" + anId)
            .accept(MediaType.APPLICATION_JSON) 
            .contentType(MediaType.APPLICATION_JSON);

        final var json =  this.mvc.perform(aRequest)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse().getContentAsString();

        return Json.readValue(json, CategoryResponse.class);
      }

}

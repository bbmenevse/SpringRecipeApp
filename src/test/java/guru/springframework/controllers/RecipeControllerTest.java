package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Recipe;

import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;

    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService,unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ControllerExceptionHandler()).build();
    }


    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/view"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        command.setDirections("Left Right, shake up and down");

        UnitOfMeasureCommand uomCommand=new UnitOfMeasureCommand();
        uomCommand.setId(2L);
        uomCommand.setDescription("Fish");

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);
        when(unitOfMeasureService.findCommandByID(any())).thenReturn(uomCommand);

        // Random ingredient array
        String ingredientArrayJson = "[{\"amount\":\"1\",\"description\":\"Ingredient 1\",\"unitOfMeasure\":\"2\"}," +
                "{\"amount\":\"2\",\"description\":\"Ingredient 2\",\"unitOfMeasure\":\"2\"}]";


        MockMultipartFile file = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "Some image data".getBytes());

        mockMvc.perform(multipart("/recipe/") // Use multipart() for multipart request
                        .file(file) // Add the file to the request
                        .param("id", "")
                        .param("description", "some string")
                        .param("ingredientArray",ingredientArrayJson)
                        .param("url","https://www.google.com/search?q=url&oq=url&aqs=chrome..69i57j0i67i650l4j69i60l3.295j0j7&sourceid=chrome&ie=UTF-8")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:view/2"));
    }
    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/form"))
                .andExpect(model().attributeExists("recipe","uomList"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(1L);
    }

    //
    // Exception Tests
    //

    @Test
    public void testNumberFormatExceptionHandle() throws Exception {
        mockMvc.perform(get("/recipe/delete/asd"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("exceptions/400error"));


    }



}
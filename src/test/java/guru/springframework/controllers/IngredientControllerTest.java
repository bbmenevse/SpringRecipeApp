package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;

    @Mock
    RecipeService recipeService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService,ingredientService,unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/ingredient/index/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/index"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientService.findCommandById(anyLong())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/ingredient/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/view"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testCreateIngredientForm() throws Exception {

        //When
        when(unitOfMeasureService.getUnitOfMeasures()).thenReturn(new ArrayList<>());

        //Then
        mockMvc.perform(get("/recipe/ingredient/create/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/form"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(unitOfMeasureService, times(1)).getUnitOfMeasures();
    }
    @Test
    public void testSaveIngredient() throws Exception{

        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/ingredient/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:index/" + command.getRecipeId()));

    }

    @Test
    public void testDeleteIngredient() throws Exception{

        //Given
        long ingredientId = 1L;
        long recipeId = 123L;

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setRecipe(recipe);

        //When
        when(ingredientService.findById(anyLong())).thenReturn(ingredient);

        //Then
        mockMvc.perform(get("/recipe/ingredient/delete/{id}", ingredientId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipe/ingredient/index/" + recipeId));

        verify(ingredientService, times(1)).deleteById(eq(Long.valueOf(ingredientId)));
    }



}
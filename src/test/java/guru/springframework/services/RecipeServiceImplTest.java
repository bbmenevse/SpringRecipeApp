package guru.springframework.services;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest{

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;




    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService=new RecipeServiceImpl(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);

    }

    @Test
    public void testGetRecipes() {
        Recipe recipe=new Recipe();
        HashSet recipeData=new HashSet();
        recipeData.add(recipe);

        //Return the data created up there when the "recipeRepository.findAll()" called.
        when(recipeRepository.findAll()).thenReturn(recipeData);
        Set<Recipe> recipeSet= new HashSet<>();
        recipeSet=recipeService.getRecipes();

        assertEquals(recipeSet.size(),1);

        // How many times RecipeRepository was invoked.
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    public void testRelationshipWithRecipe() {
        Recipe recipe = new Recipe();
        Category category = new Category();
        recipe.getCategories().add(category);
        recipe.setId(2L);
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        Recipe savedRecipe = recipeRepository.findById(recipe.getId()).orElse(null);

        // Perform assertions
        assertNotNull(savedRecipe);
        assertEquals(1, savedRecipe.getCategories().size());
    }
}
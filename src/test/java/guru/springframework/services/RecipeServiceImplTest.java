package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class RecipeServiceImplTest extends TestCase {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService=new RecipeServiceImpl(recipeRepository);
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
}
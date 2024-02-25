package guru.springframework.repositories;

import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class RecipeRepositoryTest {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp()
    {
        Category category1 = new Category();

        Ingredient ingredient1= new Ingredient();
        ingredient1.setDescription("Random ingredient");

        Recipe recipe1= new Recipe();
        recipe1.setDescription("Random recipe");
        recipe1.getCategories().add(category1);

        ingredient1.setRecipe(recipe1);
        recipe1.getIngredients().add(ingredient1);

        recipeRepository.save(recipe1);

        for(Recipe recipe:recipeRepository.findAll())
        {

            for (Ingredient ingredient: recipe.getIngredients())
            {
                System.out.println(" Ingredient : "+ingredient.getDescription());
            }
        }

    }


    @Test
    public void IntegrityTest()
    {

        for(Recipe recipe:recipeRepository.findAll())
        {

            System.out.println("inside test id: "+ recipe.getDescription());
        }

        Optional<Recipe> savedRecipe = recipeRepository.findById(1L);

        if(savedRecipe.isEmpty())
        {
            fail();
        }
        //assertNotNull(savedRecipe);
        //assertEquals(1, savedRecipe.get().getIngredients().size());

        Ingredient ingredient = ingredientRepository.findById(1L).orElse(null);
        assertNotNull(ingredient);
    }

}
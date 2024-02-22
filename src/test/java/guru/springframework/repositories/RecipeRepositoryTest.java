package guru.springframework.repositories;

import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@ActiveProfiles("test")
class RecipeRepositoryTest {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;



    @BeforeEach
    public void setUp()
    {
        Recipe recipe= new Recipe();
        recipe.setId(1L);
        recipe.setDescription("Fine by me");

        Recipe recipe1 =new Recipe();
        recipe1.setId(2L);
        recipe1.setDescription("By all means");

        Recipe recipe2 = new Recipe();
        recipe2.setId(3L);
        recipe2.setDescription("Go ahead");

        Recipe recipe3 = new Recipe();
        recipe3.setId(4L);
        recipe3.setDescription("Random memory access");

        Recipe recipe4 = new Recipe();
        recipe4.setId(5L);
        recipe4.setDescription("Save the Earth");

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2L);

        recipe.getIngredients().add(ingredient1);
        recipe1.getIngredients().add(ingredient);

        HashSet<Recipe> recipeIterable= new HashSet<>();
        recipeIterable.add(recipe);
        recipeIterable.add(recipe1);
        recipeIterable.add(recipe2);
        recipeIterable.add(recipe3);
        recipeIterable.add(recipe4);
        recipeRepository.saveAll(recipeIterable);
    }


    @Test
    public void IntegrityTest()
    {

        Recipe savedRecipe = recipeRepository.findById(1L).orElse(null);
        assertNotNull(savedRecipe);
        assertEquals(1, savedRecipe.getIngredients().size());

        Ingredient ingredient = ingredientRepository.findById(1L).orElse(null);
        assertNotNull(ingredient);
    }

}
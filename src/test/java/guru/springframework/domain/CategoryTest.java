package guru.springframework.domain;
import guru.springframework.repositories.RecipeRepository;
import junit.framework.TestCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class CategoryTest extends TestCase {

    @Mock
    RecipeRepository recipeRepository;
    private Category category;

    public CategoryTest(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @BeforeEach
    public void setUp()
    {
        category = new Category();
    }

    public void testGetId() {

        category.setId(1L);
        category.setDescription("Test Description");

        assertEquals(1L, category.getId());
        assertEquals("Test Description", category.getDescription());
    }

    @Test
    public void testEqualsAndHashCodeMethods() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setDescription("Test Description");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setDescription("Test Description");

        // Assert that the objects are equal and have the same hash code
        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());
    }



}
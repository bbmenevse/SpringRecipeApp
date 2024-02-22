package guru.springframework.domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest{
    private Category category;

    public CategoryTest() {
    }

    @BeforeEach
    public void setUp()
    {
        category = new Category();
    }

    @Test
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
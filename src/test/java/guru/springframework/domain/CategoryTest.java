package guru.springframework.domain;
import junit.framework.TestCase;
import org.junit.Before;

public class CategoryTest extends TestCase {

    private Category category;

    @Before
    public void setUp()
    {
        category = new Category();
    }

    public void testGetId() {
        category.setId(5L);
        assertEquals(5L,category.getId());
    }

    public void testGetDescription() {

    }

    public void testGetRecipes() {
    }
}
package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(9L);
        uom1.setDescription("Bottle");

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(10L);
        uom2.setDescription("Big bottle");

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setId(11L);
        uom3.setDescription("Small bottle");


        unitOfMeasureRepository.save(uom1);

        unitOfMeasureRepository.save(uom2);

        unitOfMeasureRepository.save(uom3);

    }

    @Test
    void findByDescriptionAndId() {


        Optional<UnitOfMeasure> uoMeasure = unitOfMeasureRepository.findByDescription("Bottle");

        Optional<UnitOfMeasure> uoMeasure2 = unitOfMeasureRepository.findById(2L);

        if(uoMeasure.isPresent())
        {
            assertEquals("Bottle",uoMeasure.get().getDescription());
        }
        else{
            fail();
        }

        // This one is set in RecipeBootstrap class.
        if(uoMeasure2.isPresent())
        {
            assertEquals("Tablespoon",uoMeasure2.get().getDescription());
        }
        else{
            fail();
        }
    }

    @Test
    void deleteById() {

        unitOfMeasureRepository.deleteById(2L);

        Optional<UnitOfMeasure> uoMeasure = unitOfMeasureRepository.findById(2L);

        if(uoMeasure.isPresent())
        {
            fail();
        }
    }




}
package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.Test
    void findByDescription() {
        Optional<UnitOfMeasure> uoMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon",uoMeasure.get().getDescription());
    }
}
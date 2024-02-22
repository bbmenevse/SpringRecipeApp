package guru.springframework.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import guru.springframework.json.UnitOfMeasureJsonDeserializer;
import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;

@Data
@Entity
@JsonDeserialize(using = UnitOfMeasureJsonDeserializer.class)
public class UnitOfMeasure {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitOfMeasure that = (UnitOfMeasure) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}

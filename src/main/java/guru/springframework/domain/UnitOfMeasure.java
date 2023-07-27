package guru.springframework.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import guru.springframework.json.UnitOfMeasureJsonDeserializer;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@JsonDeserialize(using = UnitOfMeasureJsonDeserializer.class)
public class UnitOfMeasure {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String description;



}

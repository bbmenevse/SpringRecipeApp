package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class UnitOfMeasure {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String description;

}

package guru.springframework.domain;

import jakarta.persistence.*;
import lombok.*;



@Data
@EqualsAndHashCode(exclude = "recipe")
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "notes")
    private Recipe recipe;

    @Column(columnDefinition = "TEXT")
    private String notes;

}

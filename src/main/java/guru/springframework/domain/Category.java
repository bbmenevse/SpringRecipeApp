package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "recipes")
@Entity
public class Category {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;

    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();

}

package guru.springframework.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;

    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}

package guru.springframework.commands;

import guru.springframework.enums.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private Set<CategoryCommand> categories = new HashSet<>();

    /*
    public List<IngredientCommand> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public void setIngredients(List<IngredientCommand> ingredients) {
        this.ingredients = new HashSet<>(ingredients) ;
    }
*/
    public void addIngredient(IngredientCommand ingredient) {
        ingredients.add(ingredient);
    }


}

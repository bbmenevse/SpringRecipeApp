package guru.springframework.commands;

import guru.springframework.enums.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.constraints.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    @NotBlank
    @Size(min=3,max=999)
    private String description;
    @Min(1)
    @Max(999)
    private Integer prepTime;
    @Min(1)
    @Max(999)
    private Integer cookTime;
    @Min(1)
    @Max(100)
    private Integer servings;
    private String source;
    @URL
    @NotBlank
    private String url;
    private String directions;
    private NotesCommand notes;
    private List<IngredientCommand> ingredients =new ArrayList<>();
    private Byte[] image;
    private Difficulty difficulty;
    private Set<CategoryCommand> categories = new HashSet<>();
}

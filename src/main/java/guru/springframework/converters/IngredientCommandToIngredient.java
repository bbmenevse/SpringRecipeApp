package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUOM uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUOM uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        if(source.getRecipe()!=null)
        {
            ingredient.setRecipe(source.getRecipe());
        }
        else if(source.getRecipeId()!=null){
            final Recipe recipeTemp = new Recipe();
            recipeTemp.setId(source.getRecipeId());
            recipeTemp.getIngredients().add(ingredient);
            ingredient.setRecipe(recipeTemp);
        }
        else
        {
            System.err.println("No recipe was found associated with the ingredient named "+ ingredient.getDescription());
        }
        ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));

        return ingredient;
    }

}

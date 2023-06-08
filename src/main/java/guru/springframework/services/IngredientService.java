package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientService {


    Ingredient findById(long id);

    IngredientCommand findCommandById(Long id);

    Set<Ingredient> getIngredients();

    void deleteById(Long id);

    List<Ingredient> getIngredientsByRecipeID(Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand command);
}

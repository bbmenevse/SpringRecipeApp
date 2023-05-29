package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

import java.util.Set;

public interface IngredientService {


    Ingredient findById(long id);

    IngredientCommand findCommandById(Long id);

    Set<Ingredient> getIngredients();
}

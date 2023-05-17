package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

public interface IngredientService {


    Ingredient findById(long id);

    IngredientCommand findCommandById(Long id);

}

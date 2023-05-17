package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.repositories.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientRepository ingredientRepository,IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientRepository = ingredientRepository;
        this.ingredientCommandToIngredient=ingredientCommandToIngredient;
    }
    @Override
    public Ingredient findById(long id) {
        Ingredient ingredient=ingredientRepository.findById(id).orElse(null);
        return ingredient;
    }

    @Override
    @Transactional
    public IngredientCommand findCommandById(Long id) {
        return ingredientToIngredientCommand.convert(findById(id));
    }
}

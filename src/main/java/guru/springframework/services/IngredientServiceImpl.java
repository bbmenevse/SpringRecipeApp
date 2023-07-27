package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.repositories.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public IngredientCommand findCommandById(Long id) {
        return ingredientToIngredientCommand.convert(findById(id));
    }

    @Override
    public Set<Ingredient> getIngredients() {
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientRepository.findAll().iterator().forEachRemaining(ingredientSet::add);
        return ingredientSet;
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Ingredient detachedIngredient = ingredientCommandToIngredient.convert(command);
        Ingredient savedIngredient = ingredientRepository.save(detachedIngredient);
        log.debug("Saved IngredientId:" + savedIngredient.getId());
        return ingredientToIngredientCommand.convert(savedIngredient);
    }

    @Override
    public void deleteById(Long idToDelete) {
        ingredientRepository.deleteById(idToDelete);
    }


    @Override
    public List<Ingredient> getIngredientsByRecipeID(Long id) {
        //Set<Ingredient> ingredientSet = new HashSet<>();
        List<Ingredient> ingredientSet = new ArrayList<>();
        ingredientRepository.findAll().iterator().forEachRemaining((ingredient -> {if(ingredient.getRecipe().getId()==id)
        ingredientSet.add(ingredient);}));
        return ingredientSet;
    }

}

package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public RecipeController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @RequestMapping("recipe/view/{id}")
    public String showRecipe(@PathVariable Long id, Model model){
        model.addAttribute("recipe",recipeService.findById(id));
        return "recipe/view";
    }

    @GetMapping("recipe/new")
    public String createRecipe(Model model)
    {
        model.addAttribute("recipe",new RecipeCommand());
        model.addAttribute("ingredients", new IngredientCommand());
        model.addAttribute("uomList", unitOfMeasureService.getUnitOfMeasures());
        return "recipe/form";
    }

    @PostMapping("recipe")
    public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand)
    {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:view/" + savedCommand.getId();
    }

    @GetMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable Long id, Model model)
    {
        /*
        RecipeCommand recipeCommand = recipeService.findCommandById(id); // Get the recipe command
        for (IngredientCommand ingredientCommand : recipeCommand.getIngredients()) {
            ingredientCommand.setRecipe(recipeCommand);
        }
        */
        model.addAttribute("recipe",recipeService.findCommandById(id));
        model.addAttribute("uomList",unitOfMeasureService.getUnitOfMeasures());
        return "recipe/form";
    }

    @RequestMapping("recipe/delete/{id}")
    public String deleteRecipe(@PathVariable String id) {
        // Using DeleteMapping raises a "Request method 'GET' not supported" problem.
        // This works but, it should not be used as it is. It is open to be exploited.
        // Using Ajax/Javascript on the index.html would be a better solution in a real project (probably?).
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }


}

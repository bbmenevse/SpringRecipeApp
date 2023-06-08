package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(RecipeService recipeService,IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService=ingredientService;
    }

    @RequestMapping("recipe/ingredient/index/{id}")
    public String showIngredient(@PathVariable Long id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(id));
        //Recipe recipe = recipeService.findById(id);
        return "/recipe/ingredient/index";
    }

    @RequestMapping("recipe/ingredient/view/{id}")
    public String showIngredientByRecipe(@PathVariable Long id, Model model)
    {
        model.addAttribute("ingredient",ingredientService.findCommandById(id));
        //Ingredient ingredient = ingredientService.findById(id);
        return "/recipe/ingredient/view";
    }

    @GetMapping("")
    public String createIngredient(Model model)
    {
        model.addAttribute("ingredients", new IngredientCommand());
        return "";
    }

    @GetMapping("")
    public String updateIngredient(@PathVariable Long id, Model model)
    {
        model.addAttribute("ingredient",ingredientService.findCommandById(id));
        return "";
    }

    @PostMapping("recipe")
    public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand)
    {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:view/" + savedCommand.getId();
    }

    @RequestMapping("recipe/ingredient/delete/{id}")
    public String deleteIngredient(@PathVariable String id) {

        long ID = ingredientService.findById(Long.valueOf(id)).getRecipe().getId();
        ingredientService.deleteById(Long.valueOf(id));
        return "redirect:/recipe/ingredient/index/"+ID;
    }


}

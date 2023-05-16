package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "/recipe/ingredient/index";
    }

    @RequestMapping("recipe/ingredient/view/{id}")
    public String showIngredientByRecipe(@PathVariable Long id, Model model)
    {
        model.addAttribute("ingredient",ingredientService.findCommandById(id));
        return "/recipe/ingredient/view";
    }


}

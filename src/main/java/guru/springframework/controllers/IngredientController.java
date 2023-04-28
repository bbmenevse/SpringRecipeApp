package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    @Autowired
    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }



    @RequestMapping("recipe/ingredient/view/{id}")
    public String showRecipe(@PathVariable Long id, Model model){
        model.addAttribute("recipe",recipeService.findById(id));
        return "recipe/ingredient/view";
    }


}

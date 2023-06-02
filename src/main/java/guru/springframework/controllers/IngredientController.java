package guru.springframework.controllers;

import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
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

    @RequestMapping("recipe/ingredient/delete/{id}")
    public String deleteIngredient(@PathVariable String id) {
        // Using DeleteMapping raises a "Request method 'GET' not supported" problem.
        // This works but, it should not be used as it is. It is open to be exploited.
        // Using Ajax/Javascript on the index.html would be a better solution in a real project (probably?).

        long ID = ingredientService.findById(Long.valueOf(id)).getRecipe().getId();
        System.out.println(" Recipe id : " + ID);
        System.out.println("\n");
        System.out.println("Ingredient id : "+id);
        ingredientService.deleteById(Long.valueOf(id));
        return "redirect:/recipe/ingredient/index/"+ID;
    }


}

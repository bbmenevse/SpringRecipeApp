package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipeService recipeService,IngredientService ingredientService,UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService=ingredientService;
        this.unitOfMeasureService=unitOfMeasureService;
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

    @GetMapping("recipe/ingredient/create/{id}")
    public String createIngredient(@PathVariable Long id, Model model)
    {

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(id);

        model.addAttribute("uomList",unitOfMeasureService.getUnitOfMeasures());
        model.addAttribute("ingredient", ingredientCommand);
        return "/recipe/ingredient/form";
    }


    @GetMapping("recipe/ingredient/update/{id}")
    public String updateIngredient(@PathVariable Long id, Model model)
    {
        model.addAttribute("ingredient",ingredientService.findCommandById(id));
        model.addAttribute("uomList",unitOfMeasureService.getUnitOfMeasures());

        return "/recipe/ingredient/form";
    }

    @PostMapping("recipe/ingredient")
    public String saveIngredient(@ModelAttribute IngredientCommand ingredientCommand)
    {
        // Recipe comes null from the form so: I create a temporary recipe and set its id to the original.
        // Fixing this in IngredientCommand may be better?
        if(ingredientCommand.getRecipe()==null)
        {
            final Recipe recipeTemp = new Recipe();
            recipeTemp.setId(ingredientCommand.getRecipeId());
            ingredientCommand.setRecipe(recipeTemp);
        }
            if(ingredientCommand.getRecipeId()!=ingredientCommand.getRecipe().getId())
        {
            ingredientCommand.getRecipe().setId(ingredientCommand.getRecipe().getId());
        }
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        return "redirect:index/" + savedCommand.getRecipeId();
    }

    @RequestMapping("recipe/ingredient/delete/{id}")
    public String deleteIngredient(@PathVariable String id) {

        long ID = ingredientService.findById(Long.valueOf(id)).getRecipe().getId();
        ingredientService.deleteById(Long.valueOf(id));
        return "redirect:/recipe/ingredient/index/"+ID;
    }


}

package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService)
    {
        this.recipeService=recipeService;
    }

    @RequestMapping("recipe/view/{id}")
    public String showRecipe(@PathVariable Long id, Model model){
        model.addAttribute("recipe",recipeService.findById(id));
        return "recipe/view";
    }

    @RequestMapping("recipe/new")
    public String createRecipe(Model model)
    {
        model.addAttribute("recipe",new RecipeCommand());

        return "recipe/form";
    }

    @PostMapping()
    @RequestMapping("recipe")
    public String saveRecipe(@ModelAttribute RecipeCommand command)
    {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:view/" + savedCommand.getId();
    }

    @RequestMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable Long id,Model model)
    {
        model.addAttribute("recipe",recipeService.findCommandById(id));
        return "recipe/form";
    }


}

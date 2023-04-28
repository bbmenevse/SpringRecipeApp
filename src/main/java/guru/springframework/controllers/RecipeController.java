package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("recipe/new")
    public String createRecipe(Model model)
    {
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/form";
    }

    @PostMapping("recipe")
    public String saveRecipe(@ModelAttribute RecipeCommand command)
    {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:view/" + savedCommand.getId();
    }

    @GetMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable Long id,Model model)
    {
        model.addAttribute("recipe",recipeService.findCommandById(id));
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

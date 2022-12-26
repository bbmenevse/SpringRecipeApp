package guru.springframework.controllers;
import guru.springframework.services.RecipeService;
import guru.springframework.services.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    private final RecipeService recipeService;

    @Autowired
    public IndexController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index","/index"})
    public String getIndexPage(Model model)
    {
        model.addAttribute("recipes",recipeService.getRecipes());

        return "index";
    }
}

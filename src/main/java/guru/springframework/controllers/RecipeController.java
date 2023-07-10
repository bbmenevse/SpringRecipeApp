package guru.springframework.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;

import guru.springframework.domain.Ingredient;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    private final UnitOfMeasureService unitOfMeasureService;


    public RecipeController(RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;

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
    public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand,@RequestParam("imageFile") MultipartFile file,@RequestParam("ingredientArray") String ingredientArrayJson)
    {


        ObjectMapper objectMapper = new ObjectMapper();
        Ingredient[] ingredients= null;


        try {
            ingredients = objectMapper.readValue(ingredientArrayJson,Ingredient[].class);
        } catch (IOException e) {
            System.out.println("Exception happened while parsing Json");
            // Handle JSON parsing exception
            e.printStackTrace();
        }
        //System.out.println(Arrays.toString(ingredients));
        System.out.println(ingredientArrayJson);


        //Saving Recipe
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
        //Check if an image was submitted
        if(!file.isEmpty())
        {
            //Save image
            recipeService.saveImageFile(savedCommand.getId(),file);
        }


        return "redirect:view/" + savedCommand.getId();
    }

    @GetMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable Long id, Model model)
    {
        model.addAttribute("recipe",recipeService.findCommandById(id));
        model.addAttribute("uomList",unitOfMeasureService.getUnitOfMeasures());
        return "recipe/form";
    }

    @RequestMapping("recipe/delete/{id}")
    public String deleteRecipe(@PathVariable String id) {
        // Using DeleteMapping raises a "Request method 'GET' not supported" problem.
        // This works but, it should not be used as it is. It is open to be exploited.
        // Using Ajax/Javascript on the index.html would be a better solution in a real project, probably?
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @GetMapping("recipe/recipeimage/{id}")
    public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
        System.out.println(id);
        RecipeCommand recipeCommand = recipeService.findCommandById(id);
        if (recipeCommand.getImage() != null) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeCommand.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }


}

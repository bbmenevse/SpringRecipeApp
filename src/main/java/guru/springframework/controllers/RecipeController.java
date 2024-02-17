package guru.springframework.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;

import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @SuppressWarnings("squid:S3626")
    @GetMapping("recipe/new")
    public String createRecipe(Model model)
    {
        //Since I use the recipe/form for both create and edit, I use hidden id on form.
        //But id on new RecipeCommand is null, and when thymeleaf tries to convert that as Long, it generates a warning.
        model.addAttribute("recipe",new RecipeCommand());
        model.addAttribute("ingredients",new IngredientCommand());
        model.addAttribute("uomList", unitOfMeasureService.getUnitOfMeasures());
        return "recipe/form";
    }

    @PostMapping("recipe")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult, @RequestParam("imageFile") MultipartFile file, @RequestParam(name="ingredientArray") String ingredientArrayJson)
    {


        if(bindingResult.hasErrors())
        {
            return "recipe/form";
        }


        ObjectMapper objectMapper = new ObjectMapper();
        Ingredient[] ingredients= null;


        try {
            ingredients = objectMapper.readValue(ingredientArrayJson,Ingredient[].class);
        } catch (IOException e) {
            System.out.println("Exception happened while parsing Json");
            // Handle JSON parsing exception
            e.printStackTrace();
        }

        //System.out.println(ingredientArrayJson);


        for(Ingredient ingredient: ingredients)
        {

            final IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setDescription(ingredient.getDescription());
            ingredientCommand.setAmount(ingredient.getAmount());
            ingredientCommand.setRecipeId(recipeCommand.getId());
            // Create new UnitofMeasureCommand object
            final UnitOfMeasureCommand uomCommand = unitOfMeasureService.findCommandByID(ingredient.getUnitOfMeasure().getId());
            ingredient.getUnitOfMeasure().getId();
            uomCommand.setDescription(ingredient.getUnitOfMeasure().getDescription());
            ingredientCommand.setUnitOfMeasure(uomCommand);

            // Add the ingredientCommands to the list
            recipeCommand.getIngredients().add(ingredientCommand);
        }
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
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @GetMapping("recipe/recipeimage/{id}")
    public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
        //System.out.println("image id : "+id);
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

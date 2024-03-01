package guru.springframework.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    // As long as ControllerAdvice has an exception handling with Exception.class
    // It has priority and handled over there. The code doesn't reach here.
    @RequestMapping("/error")
    public String handleError() {

        //System.out.println("Deneme");
        return "exceptions/generalError";
    }



}

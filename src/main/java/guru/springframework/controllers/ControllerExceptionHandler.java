package guru.springframework.controllers;

import guru.springframework.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        return genericExceptionMethod(exception, "404 Not Found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadRequest(Exception exception) {
        return genericExceptionMethod(exception, "400 Bad Request");
    }

    private ModelAndView genericExceptionMethod(Exception exception, String response) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("exceptions/400error");
        modelAndView.addObject("response",response);
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}

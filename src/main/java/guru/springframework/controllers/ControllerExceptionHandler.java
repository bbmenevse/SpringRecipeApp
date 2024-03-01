package guru.springframework.controllers;

import guru.springframework.exceptions.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    //Used for all errors except the ones I catch on controller
    //This has higher priority then CustomErrorController class
    public ModelAndView handleException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exceptions/generalError");
        return modelAndView;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        return genericExceptionMethod(exception, "404 Not Found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ModelAndView handleBadRequest(Exception exception) {
        return genericExceptionMethod(exception, "400 Bad Request");
    }

    // If the Spring class for HttpRequestMethodNotSupportedException is called, It will catch it
    // If I define it myself, I will need to throw it where it needs to be thrown
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleMethodNotAllowed(Exception exception) {
        return genericExceptionMethod(exception, "405 Method Not Allowed");
    }

    private ModelAndView genericExceptionMethod(Exception exception, String response) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("exceptions/400error");
        modelAndView.addObject("response",response);
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}

package guru.springframework.controllers;

import guru.springframework.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ModelAndView handleNotFound(Exception exception,HttpServletResponse response) {
        // Wanted to see If I can check status and stuff with response
        // Commented out since I don't need them at this time
        //response.setStatus(HttpStatus.NOT_FOUND.value());
        return genericExceptionMethod(exception, "404 Not Found",response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class, BadRequestException.class})
    public ModelAndView handleBadRequest(Exception exception, HttpServletResponse response) {
        //response.setStatus(HttpStatus.BAD_REQUEST.value());
        return genericExceptionMethod(exception, "400 Bad Request",response);
    }

    // If the Spring class for HttpRequestMethodNotSupportedException is called, It will catch it
    // If I define it myself, I will need to throw it where it needs to be thrown
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleMethodNotAllowed(Exception exception,HttpServletResponse response) {
        //response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        return genericExceptionMethod(exception, "405 Method Not Allowed",response);
    }

    private ModelAndView genericExceptionMethod(Exception exception, String message,HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exceptions/400error");
        modelAndView.addObject("message",message);
        modelAndView.addObject("exception", exception);
        //modelAndView.setStatus(HttpStatusCode.valueOf(response.getStatus()));
        return modelAndView;
    }

}

package guru.springframework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends RuntimeException{

    public MethodNotAllowedException()
    {

        super();
        //System.out.println("inside empty constructor");
    }

    public MethodNotAllowedException(String message)
    {
        super(message);
        //System.out.println("inside with only message");
    }

    public MethodNotAllowedException(String message,Throwable cause)
    {
        super(message,cause);
        //System.out.println("inside exception with cause");
    }
}

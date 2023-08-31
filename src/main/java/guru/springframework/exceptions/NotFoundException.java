package guru.springframework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    public NotFoundException()
    {

        super();
        //System.out.println("inside empty constructor");
    }

    public NotFoundException(String message)
    {
        super(message);
        //System.out.println("inside with only message");
    }

    public NotFoundException(String message,Throwable cause)
    {
        super(message,cause);
        //System.out.println("inside exception with cause");
    }


}

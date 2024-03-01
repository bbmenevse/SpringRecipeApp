package guru.springframework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    public BadRequestException()
    {
        super();
        //System.out.println("inside empty constructor");
    }

    public BadRequestException(String message)
    {
        super(message);
        //System.out.println("inside with only message");
    }

    public BadRequestException(String message,Throwable cause)
    {
        super(message,cause);
        //System.out.println("inside exception with cause");
    }

}

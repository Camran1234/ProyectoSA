package com.spring.tiketsys.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class TicketException extends Exception{

    public TicketException() {}

    public TicketException(String message){
        super("Forbidden Mistake: "+message);
    }
}

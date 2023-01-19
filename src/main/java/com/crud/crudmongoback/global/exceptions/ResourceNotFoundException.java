package com.crud.crudmongoback.global.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{

    //con esta clase manejor la excpecion generada
    public ResourceNotFoundException(String message){
        super(message);
    }
}

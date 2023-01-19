package com.crud.crudmongoback.global.exceptions;

import com.crud.crudmongoback.global.dto.MessageDto;
import com.crud.crudmongoback.global.utils.Operations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalException {

    //para mostrar un mejor error en el status de la solicitud http
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageDto> throwNotFoundException(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageDto(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    //para que el nombre del producto sea unico
    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<MessageDto> throwAttributeException(AttributeException e){
        return ResponseEntity.badRequest()
                .body(new MessageDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    //para que cuando se intente actualizar, eliminar un producto que no existe
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> generalException(Exception e){
        return ResponseEntity.internalServerError()
                .body(new MessageDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    //para que cuando se intente crear un producto con el nombre vacio y/o el precio mal
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDto> validationException(MethodArgumentNotValidException e){
        //se crea la lista
        List<String> messages = new ArrayList<>();
        //obtenemos todos los erroes, se recorre la lista y se agrega los defaul messages
        e.getBindingResult().getAllErrors().forEach((err) -> {
                messages.add(err.getDefaultMessage());
        });
        return ResponseEntity.badRequest()
                .body(new MessageDto(HttpStatus.BAD_REQUEST, Operations.trimBrackets(messages.toString())));
    }
}

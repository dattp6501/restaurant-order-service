package com.dattp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dattp.order.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;

@RestControllerAdvice
public class HandlerRequestException {
    @ExceptionHandler(value =BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerBindException(BindException e){
        return new ResponseDTO(HttpStatus.BAD_REQUEST.value(),e.getAllErrors().get(0).getDefaultMessage(),null);
    }

    @ExceptionHandler(value =BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerBindException(BadRequestException e){
        return new ResponseDTO(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
    }

    @ExceptionHandler(value =MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerBindException(MethodArgumentNotValidException e){
        return new ResponseDTO(HttpStatus.BAD_REQUEST.value(),e.getAllErrors().get(0).getDefaultMessage(),null);
    }

    @ExceptionHandler(value = JsonParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerJSONParseException(JsonParseException e){
        return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null);
    }

    @ExceptionHandler(value =Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO handlerException(Exception e){
        return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),null);
    }
}
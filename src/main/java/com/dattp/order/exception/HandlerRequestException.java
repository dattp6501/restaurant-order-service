package com.dattp.order.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dattp.order.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;

@RestControllerAdvice
@Log4j2
public class HandlerRequestException {
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handlerBindException(BindException e){
        return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .contentType(MediaType.APPLICATION_JSON)
          .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(),e.getAllErrors().get(0).getDefaultMessage(),null));
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handlerBindException(BadRequestException e){
        return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .contentType(MediaType.APPLICATION_JSON)
          .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handlerBindException(MethodArgumentNotValidException e){
        return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .contentType(MediaType.APPLICATION_JSON)
          .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(),e.getAllErrors().get(0).getDefaultMessage(),null));
    }

    @ExceptionHandler(value = JsonParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handlerJSONParseException(JsonParseException e){
        return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .contentType(MediaType.APPLICATION_JSON)
          .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public void handlerException(HttpMessageNotReadableException e){
        log.error("==================> handlerException:HttpMessageNotReadableException:{}", e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDTO> handlerException(Exception e){
        return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .contentType(MediaType.APPLICATION_JSON)
          .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),null));
    }
}
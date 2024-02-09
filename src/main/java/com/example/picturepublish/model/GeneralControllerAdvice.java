package com.example.picturepublish.model;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerMapping;

@RestControllerAdvice
public class GeneralControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GeneralControllerAdvice.class);


    @ExceptionHandler(value = Exception.class)

    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception e) {

        ErrorResponse response = new ErrorResponse();
        response.setErrorMsg("Server error " + e); // or whatever you want
        response.setErrorCode(response.getErrorCode()); // or whatever you want
        request.removeAttribute(
                HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
package com.cocomo.common;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Global exception handler
 * @author Haoming Zhang
 * @date 2/2/2019 20:31
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Exception handler for 404 status code.
     * @author Haoming Zhang
     * @date 2/2/2019 22:30
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleNotFoundException(HttpServletRequest request) {
        log.error("{} can not be found.", request.getRequestURI());
        return new ResponseEntity<>(Const.ErrorMessages.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for invalid input.
     * @author Haoming Zhang
     * @date 3/1/19 16:14
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, JsonMappingException.class})
    public ResponseEntity handleInvalidInput(HttpServletRequest request) {
        log.error("Request {} contains invalid input.", request.getRequestURI());
        return new ResponseEntity<>(Const.ErrorMessages.INVALID_PUT, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for 500 status code.
     * @author Haoming Zhang
     * @date 2/2/2019 22:30
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex,
                                          HttpServletRequest request) {
        log.error("{} Exception", request.getRequestURI(), ex);
        return new ResponseEntity<>(Const.ErrorMessages.INTERNAL_SERVER_ERROR +
                                            ": " + ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package org.wm.authservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wm.authservice.api.dto.response.ApplicationExceptionResponse;


@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationExceptionResponse> globalExceptionResponse(Exception ex){
        log.error(ex.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApplicationExceptionResponse("Sorry :(", HttpStatus.BAD_REQUEST.value()));
    }

}

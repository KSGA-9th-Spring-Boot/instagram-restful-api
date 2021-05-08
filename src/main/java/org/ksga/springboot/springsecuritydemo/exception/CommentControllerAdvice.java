package org.ksga.springboot.springsecuritydemo.exception;

import org.ksga.springboot.springsecuritydemo.payload.response.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentControllerAdvice {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public Response<String> resourceNullPointerException(ResourceNotFoundException ex) {
        return Response
                .<String>exception()
                .setPayload(ex.getMessage());
    }

}

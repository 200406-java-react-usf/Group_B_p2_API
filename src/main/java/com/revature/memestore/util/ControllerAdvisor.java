package com.revature.memestore.util;

import com.revature.memestore.exceptions.*;
import com.revature.memestore.web.dtos.ErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Component
@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler
    public ErrorResponse handleMemeStoreException(MemeStoreException e, HttpServletResponse resp){
        ErrorResponse err = new ErrorResponse(e);

        if(e instanceof AuthenticationException){
            resp.setStatus(401);
        } else if (e instanceof AuthorizationException){
            resp.setStatus(403);
        } else if(e instanceof BadRequestException){
            resp.setStatus(400);
        }else if (e instanceof InternalServerException){
            resp.setStatus(500);
        }else if (e instanceof ResourceNotFoundException){
            resp.setStatus(404);
        }else if (e instanceof ResourcePersistenceException){
            resp.setStatus(409);
        }

        return err;
    }
}

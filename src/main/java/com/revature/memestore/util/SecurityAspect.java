package com.revature.memestore.util;

import com.revature.memestore.exceptions.AuthorizationException;
import com.revature.memestore.web.dtos.Principal;
import com.revature.memestore.web.security.Secured;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class SecurityAspect {

    private HttpServletRequest request;

    @Autowired
    public SecurityAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Around("@annotation(com.revature.memestore.web.security.Secured)")
    public Object secureEndpoint(ProceedingJoinPoint pjp) throws Throwable{
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured controllerAnnotation = method.getAnnotation(Secured.class);
        List<String> allowedRoles = Arrays.asList(controllerAnnotation.allowedRoles());
        Principal principal = (Principal) request.getAttribute("principal");
        if(principal == null){
            throw new AuthorizationException("An authenticated request was made to a protected endpoint.");
        }
        if(!allowedRoles.contains(principal.getRole().toString())){
            throw new AuthorizationException("A forbidden request was made by " + principal.getUsername());
        }

        return pjp.proceed();

    }

}

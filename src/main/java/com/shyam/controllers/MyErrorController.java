package com.shyam.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MyErrorController  implements ErrorController {
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object errorCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMsg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        if (errorCode != null && Integer.valueOf(errorCode.toString()) == HttpStatus.FORBIDDEN.value()) {
            return "error/403";
        }

        if (errorCode != null && Integer.valueOf(errorCode.toString()) == HttpStatus.NOT_FOUND.value()) {
            return "error/404";
        }
        
        if (errorCode != null && Integer.valueOf(errorCode.toString()) == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            System.out.println(errorMsg);
            return "error/500";
        }

        return "error/index";
    }
    
}

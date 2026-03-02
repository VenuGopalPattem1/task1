package com.vg.shortner.advice;

// import org.springframework.stereotype.Component;

// @Component
public class UrlNotFound extends RuntimeException{
    public UrlNotFound(String msg){
        super(msg);
    }
}

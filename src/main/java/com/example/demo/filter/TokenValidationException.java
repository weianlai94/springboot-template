package com.example.demo.filter;

public class TokenValidationException extends RuntimeException{

    private Integer code;


    public TokenValidationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}

package com.appsdeveloperblog.app.ws.exceptions;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class AuthenticationException extends RuntimeException{

    private static final long serialVersionUID = -284410626462358806L;

    public AuthenticationException(String message){
        super(message);
    }
}

package com.appsdeveloperblog.app.ws.exceptions;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class NoRecordFoundException extends RuntimeException{

    private static final long serialVersionUID = 1788140691462562924L;

    public NoRecordFoundException(String message) {
        super(message);
    }
}

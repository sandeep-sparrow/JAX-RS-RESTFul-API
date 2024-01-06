package com.appsdeveloperblog.app.ws.exceptions;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class MissingRequiredFieldException extends RuntimeException {

    private static final long serialVersionUID = 2612606937101271300L;

    public MissingRequiredFieldException(String errorMessage) {
        super(errorMessage);
    }
}

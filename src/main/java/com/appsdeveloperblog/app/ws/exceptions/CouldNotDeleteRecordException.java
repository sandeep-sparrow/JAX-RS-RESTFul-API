package com.appsdeveloperblog.app.ws.exceptions;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class CouldNotDeleteRecordException extends RuntimeException {

    private static final long serialVersionUID = -984560718931879709L;

    public CouldNotDeleteRecordException(String message) {
        super(message);
    }
}

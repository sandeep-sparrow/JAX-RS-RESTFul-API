package com.appsdeveloperblog.app.ws.exceptions;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class CouldNoCreateUserRecordException extends RuntimeException {

    private static final long serialVersionUID = 7805812588605996198L;

    public CouldNoCreateUserRecordException(String message) {
        super(message);
    }
}

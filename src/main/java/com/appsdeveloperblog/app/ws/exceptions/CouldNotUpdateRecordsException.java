package com.appsdeveloperblog.app.ws.exceptions;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class CouldNotUpdateRecordsException extends RuntimeException {

    private static final long serialVersionUID = -294756258903617404L;

    public CouldNotUpdateRecordsException(String message) {
        super(message);
    }
}

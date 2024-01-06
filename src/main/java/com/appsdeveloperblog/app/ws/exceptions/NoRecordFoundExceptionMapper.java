package com.appsdeveloperblog.app.ws.exceptions;

import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/*
 *
 * @author: Sandeep prajapati
 *
 */
@Provider
public class NoRecordFoundExceptionMapper implements ExceptionMapper<NoRecordFoundException> {

    @Override
    public Response toResponse(NoRecordFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ErrorMessages.NO_RECORD_FOUND.name(),
                "https://www.apple.com");

        return Response.status(Response.Status.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .entity(errorMessage)
                .build();
    }
}

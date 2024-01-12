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
public class CouldNotUpdateRecordsExceptionMapper implements ExceptionMapper<CouldNotUpdateRecordsException>
{

    @Override
    public Response toResponse(CouldNotUpdateRecordsException exception)
    {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ErrorMessages.COULD_NOT_UPDATE_RECORD.name(),
                "https://google.com");

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();
    }
}

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
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    @Override
    public Response toResponse(AuthenticationException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ErrorMessages.AUTHENTICATION_FAILED.name(),
                "https://google.com");

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorMessage)
                .build();
    }
}

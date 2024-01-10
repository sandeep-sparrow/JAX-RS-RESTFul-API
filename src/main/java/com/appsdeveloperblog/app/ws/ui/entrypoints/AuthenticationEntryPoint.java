package com.appsdeveloperblog.app.ws.ui.entrypoints;

import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.impl.AuthenticationServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.LoginCredentials;
import com.appsdeveloperblog.app.ws.ui.model.response.AuthenticationDetails;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/*
 *
 * @author: Sandeep prajapati
 *
 */
@Path("/authentication")
public class AuthenticationEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public AuthenticationDetails userLogin(LoginCredentials loginCredentials){
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        UserDTO authenticatedUser = authenticationService.authenticate(loginCredentials.getUserName(), loginCredentials.getPassword());

        // Rest access token
        authenticationService.resetSecurityCredentials(loginCredentials.getPassword(), authenticatedUser);

        // generate Access token
        String accessToken = authenticationService.issueAccessToken(authenticatedUser);

        // build JSON response
        AuthenticationDetails authenticationDetails = new AuthenticationDetails();
        authenticationDetails.setId(authenticatedUser.getUserId());
        authenticationDetails.setToken(accessToken);

        return authenticationDetails;
    }
}

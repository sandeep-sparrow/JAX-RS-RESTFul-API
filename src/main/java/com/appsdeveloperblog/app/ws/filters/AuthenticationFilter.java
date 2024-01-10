package com.appsdeveloperblog.app.ws.filters;

import com.appsdeveloperblog.app.ws.annotations.Secured;
import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.service.impl.UsersServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *
 * @author: Sandeep prajapati
 *
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        // Extract Authorization header details
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")){
            throw new AuthenticationException("Authorization header is missing or invalid");
        }

        //Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();

        //Extract user id
        String userId = containerRequestContext.getUriInfo().getPathParameters().getFirst("userId");

        validateToken(token, userId);
    }

    private void validateToken(String token, String userId) throws AuthenticationException {

        // Get user profile details
        UsersService usersService = new UsersServiceImpl();
        UserDTO userProfile = usersService.getUserByUserId(userId);

        // Assemble access token using 2 parts. One from DB and one from http request.
        String completeToken = userProfile.getToken() + token;

        // Create access token material out of the userId received and salt kept in database
        String securedPassword = userProfile.getEncryptedPassword();
        String salt = userProfile.getSalt();
        String accessTokenMaterial = userId + salt;
        byte[] encryptedAccessToken = null;

        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(securedPassword, accessTokenMaterial);
        } catch (InvalidKeySpecException exception) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, exception);
            throw new AuthenticationException("Failed to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Compare 2 access token
        if(!encryptedAccessTokenBase64Encoded.equals(completeToken)) {
            throw new AuthenticationException("Invalid access token");
        }
    }
}

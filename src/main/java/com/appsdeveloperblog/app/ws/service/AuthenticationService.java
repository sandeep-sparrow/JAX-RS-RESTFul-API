package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public interface AuthenticationService {

    UserDTO authenticate(String userName, String password) throws AuthenticationException;
    String issueAccessToken(UserDTO userProfile) throws AuthenticationException;
    void resetSecurityCredentials(String password, UserDTO authenticatedUser);

}

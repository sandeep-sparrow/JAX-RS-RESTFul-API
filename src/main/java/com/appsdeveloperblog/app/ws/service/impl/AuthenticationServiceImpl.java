package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    DAO database;

    @Override
    public UserDTO authenticate(String userName, String password) throws AuthenticationException {

        // Provided user is existing / registered or not?
        UsersService usersService = new UsersServiceImpl();
        UserDTO storedUser = usersService.getUserByUserName(userName);

        if(storedUser == null) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }

        // provided password is correct or not? and encrypt the provided password
        String encryptedPassword = new UserProfileUtils()
                .generateSecurePassword(password, storedUser.getSalt());

        boolean authenticated = false;

        if(encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
            if(userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {
                authenticated = true;
            }
        }

        if(!authenticated) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }

        return storedUser;
    }

    @Override
    public String issueAccessToken(UserDTO userProfile) {
        String returnValue;

        String newSaltPrefix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltPrefix;

        byte[] encryptedAccessToken;
        try{
            encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch(InvalidKeySpecException exception){
            Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, exception);
            throw new AuthenticationException(ErrorMessages.FAILED_ISSUE_ACCESS_TOKEN.getErrorMessage());
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();

        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        userProfile.setToken(tokenToSaveToDatabase);

        storeAccessToken(userProfile);

        return returnValue;
    }

    private void storeAccessToken(UserDTO userProfile) {
        this.database = new MySQLDAO();

        // connect to database
        try {
            database.openConnection();
            database.updateUser(userProfile);
        } finally {
            database.closeConnection();
        }
    }
}

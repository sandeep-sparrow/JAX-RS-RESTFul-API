package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.CouldNoCreateUserRecordException;
import com.appsdeveloperblog.app.ws.exceptions.CouldNotDeleteRecordException;
import com.appsdeveloperblog.app.ws.exceptions.CouldNotUpdateRecordsException;
import com.appsdeveloperblog.app.ws.exceptions.NoRecordFoundException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

import java.util.List;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class UsersServiceImpl implements UsersService {

    UserProfileUtils userProfileUtils = new UserProfileUtils();
    DAO database;

    public UsersServiceImpl(){
        this.database = new MySQLDAO();
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        // Prepare returnValue
        UserDTO returnValue = null;

        // Validate the required field
        userProfileUtils.validateRequiredField(user);

        // Check if user already exists
        UserDTO existingUser = this.getUserByUserName(user.getEmail());
        if (existingUser != null) {
            throw new CouldNoCreateUserRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }

        // Generate secure public userId, salt, secure password
        String userId = userProfileUtils.generateUserId();
        String salt = userProfileUtils.getSalt(30);
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);

        // set Values to user
        user.setUserId(userId);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);

        // Record data into a database
        returnValue = this.saveUser(user);;

        // Return back the user profile
        return returnValue;
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserDTO userDTO = null;

        if(userId == null || userId.isEmpty()) {
            return userDTO;
        }

        // Connect to database
        try {
            this.database.openConnection();
            userDTO = this.database.getUserByUserId(userId);
        } catch(Exception exception) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        } finally {
            this.database.closeConnection();
        }

        return userDTO;
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        UserDTO userDTO = null;

        if(userName == null || userName.isEmpty()) {
            return userDTO;
        }

        // Connect to database
        try {
            this.database.openConnection();
            userDTO = this.database.getUserByUserName(userName);
        } finally {
            this.database.closeConnection();
        }

        return userDTO;
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        List<UserDTO> returnValue = null;

        // Connect to database
        try {
            this.database.openConnection();
            returnValue = this.database.getUsers(start, limit);
        } finally {
            this.database.closeConnection();
        }

        return returnValue;
    }

    @Override
    public UserDTO updateUserDetails(UserDTO userDetails) {

        // Connect to database
        try {
            this.database.openConnection();
            this.database.updateUser(userDetails);
        } catch (Exception exception) {
            throw new CouldNotUpdateRecordsException(exception.getMessage());
        } finally {
            this.database.closeConnection();
        }

        return userDetails;
    }

    @Override
    public void deleteUser(UserDTO userDTO) {

        // Connect to database
        try {
            this.database.openConnection();
            this.database.deleteUser(userDTO);
        } catch (Exception exception) {
            throw new CouldNotDeleteRecordException(exception.getMessage());
        } finally {
            this.database.closeConnection();
        }
    }

    private UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;

        // Connect to database
        try {
            this.database.openConnection();
            returnValue = this.database.saveUser(user);
        } finally {
            this.database.closeConnection();
        }

        return returnValue;
    }
}

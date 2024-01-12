package com.appsdeveloperblog.app.ws.io.dao;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

import java.util.List;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public interface DAO {

    public void openConnection();
    public List<UserDTO> getUsers(int start, int limit);
    public UserDTO getUserByUserId(String userId);
    public UserDTO getUserByUserName(String userName);
    public UserDTO saveUser(UserDTO user);
    public void updateUser(UserDTO user);
    public void closeConnection();
}

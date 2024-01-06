package com.appsdeveloperblog.app.ws.io.dao;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public interface DAO {

    public void openConnection();
    public UserDTO getUserByUserId(String userId);
    public UserDTO getUserByUserName(String userName);
    public UserDTO saveUser(UserDTO user);
    public void closeConnection();

}

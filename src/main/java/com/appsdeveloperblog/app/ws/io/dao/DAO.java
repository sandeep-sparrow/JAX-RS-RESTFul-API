package com.appsdeveloperblog.app.ws.io.dao;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public interface DAO {

    public void openConnection();
    public UserDTO getUserByUserName(String userName);
    UserDTO saveUser(UserDTO user);
    public void closeConnection();

}

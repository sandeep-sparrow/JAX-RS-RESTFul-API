package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public interface UsersService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserByUserId(String userId);

    UserDTO getUserByUserName(String userName);
}

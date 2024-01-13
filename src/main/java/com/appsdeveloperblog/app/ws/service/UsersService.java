package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

import java.util.List;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public interface UsersService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserByUserId(String userId);

    UserDTO getUserByUserName(String userName);

    List<UserDTO> getUsers(int start, int limit);

    UserDTO updateUserDetails(UserDTO userDTO);

    void deleteUser(UserDTO userDTO);
}

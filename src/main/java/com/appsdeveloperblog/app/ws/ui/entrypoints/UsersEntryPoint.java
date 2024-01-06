package com.appsdeveloperblog.app.ws.ui.entrypoints;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.CreateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserProfileRest;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.service.impl.UsersServiceImpl;
import org.springframework.beans.BeanUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/*
 *
 * @author: Sandeep prajapati
 *
 */
@Path("/users")
public class UsersEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest CreateUser(CreateUserRequestModel requestModel){
        UserProfileRest returnValue = new UserProfileRest();

        // Prepare user DTO
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(requestModel, userDTO);

        // Create new User
        UsersService userService = new UsersServiceImpl();
        UserDTO createdUserProfile = userService.createUser(userDTO);

        // Prepare a response
        BeanUtils.copyProperties(createdUserProfile, returnValue);

        return returnValue; // Return back the user profile.
    }

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest getUserProfile(@PathParam("userId") String userId){
        UserProfileRest returnValue = new UserProfileRest();

        UsersService userService = new UsersServiceImpl();
        UserDTO userDTO = userService.getUserByUserId(userId);

        BeanUtils.copyProperties(userDTO, returnValue);

        return returnValue;  // Return back the user profile.
    }
}

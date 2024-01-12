package com.appsdeveloperblog.app.ws.ui.entrypoints;

import com.appsdeveloperblog.app.ws.annotations.Secured;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.CreateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserProfileRest;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.service.impl.UsersServiceImpl;
import org.springframework.beans.BeanUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

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

    @Secured
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
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserProfileRest> getUsers(@DefaultValue("0") @QueryParam("start") int start,
                                          @DefaultValue("50") @QueryParam("limit") int limit){

        UsersService usersService = new UsersServiceImpl();
        List<UserDTO> users = usersService.getUsers(start, limit);

        // Prepare return value
        List<UserProfileRest> returnValue  = new ArrayList<UserProfileRest>();

        for(UserDTO user : users){
            UserProfileRest userProfileRest = new UserProfileRest();
            BeanUtils.copyProperties(user, userProfileRest);
            userProfileRest.setHref("/users/" + user.getUserId());
            returnValue.add(userProfileRest);
        }
        return returnValue;
    }

    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest updateUser(@PathParam("userId") String userId,
                                      UpdateUserRequestModel userDetails){
        UserProfileRest returnValue = new UserProfileRest();

        UsersService userService = new UsersServiceImpl();

        // Get Stored user
        UserDTO storedUserDetails = userService.getUserByUserId(userId);

        // set values
        if(userDetails.getFirstName() != null && userDetails.getLastName() != null){
            storedUserDetails.setFirstName(userDetails.getFirstName());
            storedUserDetails.setLastName(userDetails.getLastName());
        }

        // Update User
        UserDTO updatedUserProfile = userService.updateUserDetails(storedUserDetails);

        // Prepare a response
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue; // Return back the user profile.
    }
}

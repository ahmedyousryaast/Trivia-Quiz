package com.trivia.user.rest.boundary;

import com.trivia.user.boundary.UserService;
import com.trivia.user.entity.User;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ahmed
 */
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndPoint {
    @EJB
    private UserService userService;
    
    @GET
    public Response getAllUsers(){
        return Response.ok(userService.findAll()).build();
    }
    
    
    @POST
    public Response signUp(User user){
        User returned = userService.signUp(user);     
        if(!(returned.getUserId()== null)){
            return Response.ok(returned).build();
        }else{
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    
    @POST
    @Path("logging")
    public Response logIn(User user){
        return Response.ok(userService.logUserIn(user)).build();
            
    }
    
    @POST
    @Path("delete")
    public Response deleteUser(User user){
        userService.removeUser(user);
        return Response.ok().build();
    }
    
    @POST
    @Path("promote")
    public Response promoteUser(User user){
        userService.promoteUser(user);
        return Response.ok().build();
    }
    
}

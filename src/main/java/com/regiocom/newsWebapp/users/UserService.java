package com.regiocom.newsWebapp.users;


import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.regiocom.newsWebapp.users.roles.Role;
import com.regiocom.newsWebapp.users.roles.RoleRepository;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Root resource (exposed at "userservice" path)
 */
@Path("userservice")
public class UserService {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	UserRepository userRepo = new UserRepository();
	RoleRepository roleRepo = new RoleRepository();
	
	/*
	 * Get all users
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return userRepo.getUsers();
	}
	
	/*
	 * login user
	 * Input: username and password
	 * Output: Ok or error
	 */
	
	@Path("/login/{username}/{password}")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@PathParam("username")String username ,@PathParam("password")String password){
		ObjectNode json = mapper.createObjectNode();
		User user = userRepo.getUser(username,password);
	    
		if(user!= null) {
			json.put("message", "login successfully");
			json.put("status", "ok");
			return Response.status(Response.Status.OK).entity(json).build();
		}else {
			json.put("message" ,"Invalid login");
			json.put("status", "error");
			return Response.status(Response.Status.UNAUTHORIZED).entity(json).build();
		}
	}
	
	/*
	 * Register new user
	 * Input: username , password and repassword
	 * Output: ok or error
	 */
	
	@Path("/register/{username}/{password}/{repassword}")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(@PathParam("username")String username ,@PathParam("password")String password,@PathParam("repassword")String repassword) {
		ObjectNode json = mapper.createObjectNode();
		int count = 0;
		User user = null;
		
		if(userRepo.isUserExists(username)) {
			json.put("message", "User already exists.");
			json.put("status", "error");
	        return Response.status(Response.Status.CONFLICT).entity(json).build();
		}
		
		if(password.equals(repassword)) {
			user = new User();
			user.setName(username);
			user.setPassword(password);
			
			count = userRepo.createUser(user);
		}
		
		if(count > 0) {
			json.put("message", "User Registeration Successful.");
			json.put("status", "ok");
	        return Response.status(Response.Status.CREATED).entity(json).build();
		}else {
			json.put("message", "Incorrect Registration.");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}

	}
	
	/*
	 * Get all roles
	 */
	
	@Path("/roles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Role> getRoles() {
		return roleRepo.getRoles();
	}
	
	/*
	 * Create role only by admin
	 * Input: userId , newRole
	 * Output: ok or error
	 */
	
	@Path("/createRole/{userId}/{newRole}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRole(@PathParam("userId")int userId, @PathParam("newRole")String name) {
		ObjectNode json = mapper.createObjectNode();
		int count = 0;
		
		User user = userRepo.getUserById(userId);
		
		if(user.getRole() != 1){ // only admin allow
			json.put("message",  "Access denied: Only admin has the right to create role");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();
		}
		
		if(roleRepo.isRoleExists(name)) {
			json.put("message", "Role already exists.");
			json.put("status", "error");
	        return Response.status(Response.Status.CONFLICT).entity(json).build();
		}
		
		Role role = new Role();
		role.setName(name);
		count = roleRepo.createRole(role);
		
		if(count > 0) {
			json.put("message", "Role create successfully");
			json.put("status", "ok");
			
	        return Response.status(Response.Status.CREATED).entity(json).build();
		}else {
			json.put("message",  "Error while creating role");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}
	}
	
	/*
	 * Assign role only by admin
	 * Input: userId , targetUserId, roleId
	 * Output: assign success or error
	 */
	@Path("/assignRole/{userId}/{targetUserId}/{roleId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignRole(@PathParam("userId")int userId,@PathParam("targetUserId")int targetUserId, @PathParam("roleId")int roleId) {
		ObjectNode json = mapper.createObjectNode();
		int count = 0;
		
		User user = userRepo.getUserById(userId);
		
		if(user.getRole() != 1){ // only admin allow
			json.put("message",  "Access denied: Only admin has the right to assign role");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();	
		}
		
		User targetUser = userRepo.getUserById(targetUserId);
		Role role = roleRepo.getRoleById(roleId);
		
		if(targetUser == null || role == null ) {
			json.put("message", "Invalid user or role.");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}
		
		count = userRepo.updateRoleById(targetUserId,roleId);
		
		if(count > 0) {
			json.put("message","Role assign successfully");
			json.put("status", "ok");
	        return Response.status(Response.Status.OK).entity(json).build();
		}else {
			json.put("message" ,"Error while assigning role to user");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}
		
	}

}

package com.regiocom.newsWebapp.contents;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.regiocom.newsWebapp.contents.pictures.Picture;
import com.regiocom.newsWebapp.contents.pictures.PictureRepository;
import com.regiocom.newsWebapp.users.User;
import com.regiocom.newsWebapp.users.UserRepository;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Root resource (exposed at "newsservice" path)
 */
@Path("newsservice")
public class NewsService {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	NewsRepository newsRepo = new NewsRepository(); 
	PictureRepository pictureRepo = new PictureRepository();
	UserRepository userRepo = new UserRepository();
	
	
	/*
	 * Get News based on user role
	 * Input: userId
	 * Output: list of news
	 */
	@Path("/News/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNews(@PathParam("userId")int userId) {
		User user = userRepo.getUserById(userId);
		List<News> listOfNew = newsRepo.getNews(user);
		for(News news: listOfNew) {
			List<Picture>pictures = pictureRepo.getPictures(news.getId());
			news.setPictures(pictures);
		}
		return listOfNew;
	}
	
	/*
	 * Create news by user (Admin ,Publisher)
	 * Input: userId , News 
	 * Output: if data is valid user create successfully otherwise error occur
	 */
	
	@Path("/createNews/{userId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNews(@PathParam("userId")int userId,News news) {
		ObjectNode json = mapper.createObjectNode();
		int count = 0;
		
		User user = userRepo.getUserById(userId);
		
		if(user.getRole() == 3){ // reader not allow 
			json.put("message",  "Access denied: Only admin and publisher has the right to create news");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();	
		}
		
		if(news != null) {
			news.setCreadtedBy(user.getId());
			count = newsRepo.createNews(news);
			if(count > 0){
				for(Picture pic : news.getPictures()){
					pic.setNewsId(news.getId());
					pictureRepo.createPictures(pic);
				}
			}
		}
		
		if(count > 0) {
			json.put("message", "News create successfully");
			json.put("status", "ok");
	        return Response.status(Response.Status.CREATED).entity(json).build();
		}else {
			json.put("message" , "Error while creating news");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}
	}
	
	/*
	 * Update news by user (Admin ,Publisher)
	 * Input: userId , News 
	 * Output: if data is valid user update successfully otherwise error occur
	 */
	
	@Path("/updateNews/{userId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateNews(@PathParam("userId")int userId, News news) {
		ObjectNode json = mapper.createObjectNode();
		int count = 0;
		
		User user = userRepo.getUserById(userId);
		
		if(user.getRole() == 3){ // reader not allow 
			json.put("message",  "Access denied: Only admin and publisher has the right to update news");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();	
		}
		
		if(user.getRole() == 2 && user.getId()!= news.getCreadtedBy()) { // publisher not allow to edit/update news of other publisher/admin
			json.put("message",  "Access denied: Not allow to update others created news");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();	
		}
		
		if(news != null) {
			count = newsRepo.updateNews(news);
			if(count > 0){
				pictureRepo.deletePicture(news.getId());
				for(Picture pic : news.getPictures()){
					pic.setNewsId(news.getId());
					pictureRepo.createPictures(pic);
				}
			}
		}
		
		if(count > 0) {
			json.put("message", "News Update successfully");
			json.put("status", "ok");
	        return Response.status(Response.Status.CREATED).entity(json).build();
		}else {
			json.put("message" , "Error while Updating news");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}

	}
	
	/*
	 * Delete news by user (Admin ,Publisher)
	 * Input: userId , newsId
	 * Output: if newsId is valid user delete successfully otherwise error occur
	 */
	
	@Path("/deleteNews/{userId}/{newsId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteNews(@PathParam("userId")int userId,@PathParam("newsId")int newsId) {
		ObjectNode json = mapper.createObjectNode();
		int count = 0;
		
		User user = userRepo.getUserById(userId);
		News news = newsRepo.getNewsById(newsId);
		
		if(user.getRole() == 3){ // reader not allow 
			json.put("message",  "Access denied: Only admin and publisher has the right to delete news");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();	
		}
		
		if(user.getRole() == 2 && user.getId() != news.getCreadtedBy()) { // publisher not allow to delete news of other publisher/admin
			json.put("message",  "Access denied: Not allow to delete others created news");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();	
		}
		
		count = deleteNews(news);
		
		if(count > 0) {
			json.put("message", "News delete successfully");
			json.put("status", "ok");
	        return Response.status(Response.Status.CREATED).entity(json).build();
		}else {
			json.put("message" , "Error while deleting news");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}
	}
	
	/*
	 * Get Read News based
	 * Output: list of news
	 */
	@Path("/readNews")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> readNews() {
		List<News> listOfNew = newsRepo.getReadNews();
		for(News news: listOfNew) {
			List<Picture>pictures = pictureRepo.getPictures(news.getId());
			news.setPictures(pictures);
		}
		return listOfNew;
	}
	
	/*
	 * Publish news by user (Admin ,Publisher)
	 * Input: userId , newsId 
	 * Output: if newsId is valid user publish successfully otherwise error occur
	 */
	
	@Path("/publishNews/{userId}/{newsId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response publishNews(@PathParam("userId")int userId,@PathParam("newsId")int newsId) {
		ObjectNode json = mapper.createObjectNode();
		int count = 0;
		
		User user = userRepo.getUserById(userId);
		News news = newsRepo.getNewsById(newsId);
		
		if(user.getRole() == 3){ // reader not allow 
			json.put("message",  "Access denied: Only admin and publisher has the right to publish news");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();	
		}
		
		if(user.getRole() == 2 && user.getId() != news.getCreadtedBy()) { // publisher not allow to publish news of other publisher/admin
			json.put("message",  "Access denied: Not allow to publish others created news");
			json.put("status", "error");
	        return Response.status(Response.Status.FORBIDDEN).entity(json).build();	
		}
		
		count = newsRepo.publishNews(news.getId());
		
		if(count > 0) {
			json.put("message", "News publish successfully");
			json.put("status", "ok");
	        return Response.status(Response.Status.CREATED).entity(json).build();
		}else {
			json.put("message" , "Error while publishing news");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}
	}
	
	/*
	 * Set read status
	 * Input: accountId , newsId
	 * Output: if status update return status "Ok" otherwise "error"
	 */
	
	@Path("/setReadStatus/{userId}/{newsId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response setReadStatus(@PathParam("userId")int userId, @PathParam("newsId")int newsId) {
		ObjectNode json = mapper.createObjectNode();
		int count = 0;
		
		User user = userRepo.getUserById(userId);
		News news = newsRepo.getNewsById(newsId);
		
		if(user.getRole()==3 && !news.isPublish()) { // reader only allow to read publish news
			json.put("message" , "Reader are not allow to read unpublish news");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}
		
		if(user.getRole()==2 && user.getId()!= news.getCreadtedBy() && !news.isPublish()) { // publisher only allow to read his own unpublish news
			json.put("message" , "publisher are not allow to read others unpublish created news");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}
		
		count = newsRepo.markNewsRead(news.getId(), user.getId());
		
		if(count > 0) {
			json.put("message", "News mark as read successfully");
			json.put("status", "ok");
	        return Response.status(Response.Status.CREATED).entity(json).build();
		}else {
			json.put("message" , "Error while mark news as read");
			json.put("status", "error");
	        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
		}	
	}
	
	/*
	 * Get pictures 
	 * Input: userId , newsId
	 * Output: list of pictures
	 */

	@Path("/pictures/{userId}/{newsId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Picture> getPictures(@PathParam("userId")int userId, @PathParam("newsId")int newsId) {
		List<Picture> pictures = null;
		
		User user = userRepo.getUserById(userId);
		News news = newsRepo.getNewsById(newsId);
		
		if(user != null && news != null) {
			pictures = pictureRepo.getPictures(news.getId(),user);
		}

	    return pictures;
	}
	
	/*
	 * GET news for a single role-accountID combination
	 * Input: userId , roleId
	 * Output: return list of news based on single role-userId combination
	 */
	
	@Path("/unreadNews/{userId}/{roleId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getUnreadNews(@PathParam("userId")int userId,@PathParam("roleId")int roleId) {
		List<String> userIds = userRepo.getUserIdsByRole(roleId); // get all users having same role
		List<News> listOfNew = newsRepo.getUnreadNewsBySingleRoleUserId(userId,userIds);
		for(News news: listOfNew) {
			List<Picture>pictures = pictureRepo.getPictures(news.getId());
			news.setPictures(pictures);
		}
		return listOfNew;
	}
	
	private int deleteNews(News news) {
		int count = 0;
		if(news == null)return count;
		
		newsRepo.deleteNewsRead(news.getId());
		pictureRepo.deletePicture(news.getId());
		count = newsRepo.deleteNews(news.getId());
		
		return count;
	}
}

package com.regiocom.newsWebapp.contents.pictures;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.regiocom.newsWebapp.users.User;

public class PictureRepository {
	
	Connection conn = null;
	
	public PictureRepository(){
		String url = "jdbc:mysql://localhost:3307/newsdb";
		String username = "root";
		String password = "ali12345";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Picture> getPictures(int newsId){
		List<Picture> pictures = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from news_pictures where newsId="+newsId;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				Picture pic = new Picture();
				pic.setNewsId(rs.getInt(1));
				pic.setName(rs.getString(2));
				pic.setImagePath(rs.getString(3));
				
				pictures.add(pic);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return pictures;
	}
	
	public List<Picture> getPictures(int newsId, User user){
		List<Picture> pictures = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		
		String query = "SELECT n.id,p.name,p.imagePath FROM news_pictures as p\r\n"
				+ "INNER JOIN news as n ON p.newsId = n.id where n.id="+newsId;
		
		if(user.getRole() == 2) // publisher
			query += " and n.id Not In ( Select n.id from news where n.createdBy!="+user.getId()+" and n.isPublish=false)"; 
		
		if(user.getRole() == 3) // reader 
			query += " and n.isPublish=true";
			
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				Picture pic = new Picture();
				pic.setNewsId(rs.getInt(1));
				pic.setName(rs.getString(2));
				pic.setImagePath(rs.getString(3));
				
				pictures.add(pic);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return pictures;
	}
	
	public int createPictures(Picture picture) {
		PreparedStatement st = null;
		int affectedRows = 0;
		
		String query = "insert into news_pictures (newsId,name,imagePath) values(?,?,?)";
		
		try {
			st = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, picture.getNewsId());
			st.setString(2, picture.getName());
			st.setString(3, picture.getImagePath());
			
			affectedRows = st.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return affectedRows;
	}
	
	public int deletePicture(int newsId){	
		Statement st = null;
		int affectedRows = 0;
		
		String query = "Delete from news_pictures where newsId="+newsId;
		
		try {
			st = conn.createStatement();
			affectedRows = st.executeUpdate(query);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return affectedRows;
	}

}

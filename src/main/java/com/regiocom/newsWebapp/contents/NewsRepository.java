package com.regiocom.newsWebapp.contents;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.regiocom.newsWebapp.users.User;

public class NewsRepository {
	
	Connection conn = null;
	
	public NewsRepository(){
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
	
	public int createNews(News news) {
		ResultSet rs = null;
		PreparedStatement st = null;
		int affectedRows = 0;
		
		String query = "insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values(?,?,?,?,?,?,?)";
		
		try {
			st = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			st.setString(1, news.getTitle());
			st.setString(2, news.getText());
			st.setDate(3, java.sql.Date.valueOf(news.getCreationDate()));
			st.setDate(4, java.sql.Date.valueOf(news.getValidFrom()));
			st.setDate(5, java.sql.Date.valueOf(news.getValidTo()));
			st.setInt(6, news.getCreadtedBy());
			st.setBoolean(7, news.isPublish());
			
			affectedRows = st.executeUpdate();
			rs = st.getGeneratedKeys();
			
			if(rs.next())
				news.setId(rs.getInt(1));
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return affectedRows;
	}
	
	public int updateNews(News news) {
		PreparedStatement st = null;
		int affectedRows = 0;
		
		String query = "Update news Set title=?, description=?, creationDate=?, validFrom=?, validTo=?, createdBy=?, isPublish=?  where id=?";
		
		try {
			st = conn.prepareStatement(query);
			st.setString(1, news.getTitle());
			st.setString(2, news.getText());
			st.setDate(3, java.sql.Date.valueOf(news.getCreationDate()));
			st.setDate(4, java.sql.Date.valueOf(news.getValidFrom()));
			st.setDate(5, java.sql.Date.valueOf(news.getValidTo()));
			st.setInt(6, news.getCreadtedBy());
			st.setBoolean(7, news.isPublish());
			
			st.setInt(8, news.getId());
			
			affectedRows = st.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return affectedRows;
	}
	
	public int deleteNews(int id){	
		Statement st = null;
		int affectedRows = 0;
		
		String query = "Delete from news where id="+id;
		
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
	
	public int markNewsRead(int id, int userId){
		PreparedStatement st = null;
		int affectedRows = 0;
		
		String query = "insert into news_read (newsId,userId,status,readDate) values(?,?,?,?)";
		
		try {
			st = conn.prepareStatement(query);
			st.setInt(1, id);
			st.setInt(2, userId);
			st.setBoolean(3, true);
			st.setDate(4, new Date(new java.util.Date().getTime()));
			
			affectedRows = st.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return affectedRows;	
	}
	
	public int deleteNewsRead(int id){	
		Statement st = null;
		int affectedRows = 0;
		
		String query = "Delete from news_read where newsId="+id;
		
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
	
	public List<News> getNews(User user){
		List<News> ListOfNews = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from news";
		
		if(user.getRole() == 3) // reader
			query += " where isPublish = true";
		if(user.getRole() == 2) // publisher
			query += " where id Not In ( Select id from news where createdBy!="+user.getId()+" and isPublish=false)";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				News news = new News();
				news.setId(rs.getInt(1));
				news.setTitle(rs.getString(2));
				news.setText(rs.getString(3));
				news.setCreationDate(rs.getDate(4).toString());
				news.setValidFrom(rs.getDate(5).toString());
				news.setValidTo(rs.getDate(6).toString());
				news.setCreadtedBy(rs.getInt(7));
				news.setPublish(rs.getBoolean(8));	
				
				ListOfNews.add(news);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return ListOfNews;
	}
	
	public List<News> getReadNews(){
		List<News> ListOfNews = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		
		String query = "SELECT * FROM news where id IN (Select distinct newsId from news_read)";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				News news = new News();
				news.setId(rs.getInt(1));
				news.setTitle(rs.getString(2));
				news.setText(rs.getString(3));
				news.setCreationDate(rs.getDate(4).toString());
				news.setValidFrom(rs.getDate(5).toString());
				news.setValidTo(rs.getDate(6).toString());
				news.setCreadtedBy(rs.getInt(7));
				news.setPublish(rs.getBoolean(8));	
				
				ListOfNews.add(news);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return ListOfNews;
	}
	
	public News getNewsById(int id){
		News news = null;
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from news where id="+id;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				news = new News();
				news.setId(rs.getInt(1));
				news.setTitle(rs.getString(2));
				news.setText(rs.getString(3));
				news.setCreationDate(rs.getDate(4).toString());
				news.setValidFrom(rs.getDate(5).toString());
				news.setValidTo(rs.getDate(6).toString());
				news.setCreadtedBy(rs.getInt(7));
				news.setPublish(rs.getBoolean(8));	
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return news;
	}

	public int publishNews(int id){
		Statement st = null;
		int affectedRows = 0;
		
		String query = "Update news Set isPublish=true where id="+id;
		
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
	
	public List<News> getUnreadNewsBySingleRoleUserId(int userId, List<String> userIds){
		List<News> ListOfNews = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		
		// only publish news are available to read..
		
		// get unread news by single role- user id
		
		String query = "Select distinct id,title,description,creationDate,validFrom,validTo,createdBy,isPublish from (\r\n"
					+ "	(Select * from news where id NOT IN (Select distinct newsId from news_read where userId IN ("+String.join(",", userIds)+")) ORDER BY id)\r\n"
					+ "	union\r\n"
					+ "	(Select * from news where id NOT IN (Select distinct newsId from news_read where userId = "+userId+") ORDER BY id)\r\n"
					+ ")as ns ORDER BY creationDate DESC limit 10;";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				News news = new News();
				news.setId(rs.getInt(1));
				news.setTitle(rs.getString(2));
				news.setText(rs.getString(3));
				news.setCreationDate(rs.getDate(4).toString());
				news.setValidFrom(rs.getDate(5).toString());
				news.setValidTo(rs.getDate(6).toString());
				news.setCreadtedBy(rs.getInt(7));
				news.setPublish(rs.getBoolean(8));	
				
				ListOfNews.add(news);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return ListOfNews;
	}
}

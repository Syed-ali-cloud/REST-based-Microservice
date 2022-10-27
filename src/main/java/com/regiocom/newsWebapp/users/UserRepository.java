package com.regiocom.newsWebapp.users;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class UserRepository {
	
	Connection conn = null;
	
	public UserRepository(){
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
	
	public int createUser(User user) {
		ResultSet rs = null;
		PreparedStatement st = null;
		int affectedRows = 0;
		
		String query = "insert into users (name,password) values(?,?)";
		
		try {
			st = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			st.setString(1, user.getName());
			st.setString(2, user.getPassword());
			
			affectedRows = st.executeUpdate();
			rs = st.getGeneratedKeys();
			
			if(rs.next())
				user.setId(rs.getInt(1));
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return affectedRows;
	}
	
	public int updateRoleById(int userId, int roleId) {
		Statement st = null;
		int affectedRows = 0;
		
		String query = "Update users Set role="+roleId+" where id="+userId;
		
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
	
	public User getUser(String name, String password){	
		User user = null;
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from users where name='"+name+"' and password='"+password+"'";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setRole(rs.getInt(4));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return user;
	}
	
	public User getUserById(int id){	
		User user = null;
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from users where id="+id;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setRole(rs.getInt(4));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return user;
	}
	
	public boolean isUserExists(String username){	
		ResultSet rs = null;
		PreparedStatement st = null;
		
		String query = "select * from users where name = ?";
		
		try {
			st = conn.prepareStatement(query);
			st.setString(1, username);
			rs = st.executeQuery();
			
			return rs.next();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return false;
	}
	
	public List<User> getUsers(){
		List<User> users = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from users";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setRole(rs.getInt(4));
				
				users.add(user);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return users;
	}
	
	public List<String> getUserIdsByRole(int roleId){
		List<String> userIds = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from users where role="+roleId;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				userIds.add(rs.getInt(1)+"");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return userIds;
	}
	
}

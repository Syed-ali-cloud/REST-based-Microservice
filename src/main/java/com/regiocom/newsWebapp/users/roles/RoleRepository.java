package com.regiocom.newsWebapp.users.roles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {

	Connection conn = null;
	
	public RoleRepository(){
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
	
	public int createRole(Role role) {
		ResultSet rs = null;
		PreparedStatement st = null;
		int affectedRows = 0;
		
		String query = "insert into roles (name) values(?)";
		
		try {
			st = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			st.setString(1, role.getName());
			
			affectedRows = st.executeUpdate();
			rs = st.getGeneratedKeys();
			
			if(rs.next())
				role.setId(rs.getInt(1));
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return affectedRows;
	}
	
	public Role getRoleById(int id){
		Role role = null;
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from roles where id="+id;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				role = new Role();
				role.setId(rs.getInt(1));
				role.setName(rs.getString(2));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return role;
	}
	
	public List<Role> getRoles(){
		List<Role> roles = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		
		String query = "select * from roles";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt(1));
				role.setName(rs.getString(2));
				
				roles.add(role);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if(st!=null)try {st.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return roles;
	}

	public boolean isRoleExists(String name){	
		ResultSet rs = null;
		PreparedStatement st = null;
		
		String query = "select * from roles where name = ?";
		
		try {
			st = conn.prepareStatement(query);
			st.setString(1, name);
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
}

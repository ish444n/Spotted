package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.models.User;

// class to retrieve and update user profiles - including bio and other details
@WebServlet("/user")
public class UserProfile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	// returns requested user as a json object, or 404 if the user doesn't exist
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get the user
		User user = userFromRequest(request);
		
		// if the user doesn't exist, tell the client that
		if(user == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		} else {
			// turn user into a json
			
		}
		
    }
	
	// helper function to retrieve the user from the table
	protected User userFromRequest(HttpServletRequest request) {
		// grab the userid
		String userid = request.getParameter("userId");
		
		// set JDBC vars
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // get user info and check if it matches login
        try {
	        conn = StudySpotsDAO.getConnection();
	        
	        // build statement
	        String sql = "SELECT * FROM UserTable WHERE UserID = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, userid);
	        
	        // execute & get results
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	        	// get the user params to build the user
	        	String username = rs.getString("Username");
	        	String password = rs.getString("Password");
	        	String email = rs.getString("Email");
	        	String bio = rs.getString("Bio");
	        	// String image = rs.get_____("Image"); not required for user rn, also not in database (04/12/2024)
            
	        	// build the user object and return em
	        	User user = new User(Integer.parseInt(userid), username, email, password, bio);
	        	return user;
	        
	        } else {
            	return null;
            }
        } catch (SQLException sqle) {
        	System.out.println("Error while getting user details: " + sqle.getMessage());
        	return null;
        }
	}
}

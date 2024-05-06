package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.models.*;
import com.models.Image;

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
		}
		
		// otherwise, continue making user json
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convert the User object to JSON using GSON
        String json = user.toJson();

        // Write the JSON to the response
        response.getWriter().write(json);
    }
	
	// helper function to retrieve the user from the table
	protected User userFromRequest(HttpServletRequest request) {
		// grab the userid
		String userid = request.getParameter("userId");
		
		// set JDBC vars
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // initialize the user
        User user = null;
        
        // get user info and check if it matches login
        try {
	        conn = SpottedDriver.getConnection();
	        
	        // build statement to get user info
	        String userSql = "SELECT * FROM UserTable WHERE UserID = ?";
	        stmt = conn.prepareStatement(userSql);
	        stmt.setString(1, userid);
	        
	        // execute & get results
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	        	// get the user params to build the user
	        	String username = rs.getString("Username");
	        	String password = rs.getString("Password");
	        	String email = rs.getString("Email");
	        	String bio = rs.getString("Bio");
	        	
	        	// we know user exists so now lets get their bookmarked spots
	        	// List<StudySpot> bookmarked = StudySpotsDAO.getUserBookmarked(userid); -- not sure why this randomly stopped working
	        	List<StudySpot> bookmarked = new ArrayList<>();
	        	
	        	// create the user object
	        	user = new User(Integer.parseInt(userid), username, email, password, bio, bookmarked);
	        }
        } catch(NumberFormatException nfe) {
        	System.out.println("Number format exception in UserFromRequest: " + nfe.getMessage());
        } catch (SQLException sqle) {
        	System.out.println("Error while getting user details: " + sqle.getMessage());
        } finally { 
        	// close resources 
        	try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // return the user
        return user;
	}
}

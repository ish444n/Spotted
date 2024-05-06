package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/SignIn")
public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // process login and return request
    // expects username and password parameters
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		
		// get the email and username from login page
		String username = request.getParameter("username");
        String password = request.getParameter("password");        
        
        System.out.println("Attempting to log user + " + username + " in...");
        
        // set JDBC vars
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // get user info and check if it matches login
        try {
	        conn = SpottedDriver.getConnection();
	
	        // build statement
	        String sql = "SELECT UserID FROM UserTable WHERE Username = ? AND Password = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, username);
	        stmt.setString(2, password);
	
	        // execute & get results
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	        	// credentials are valid, so set response status to 'ok'
	            response.setStatus(HttpServletResponse.SC_OK);   
	            
	            // return the userID
	            int userId = rs.getInt("userID");
	            response.getWriter().write(String.valueOf(userId));
            } else {
                // Login failed, so set response status to 'SC_UNAUTHORIZED'
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    
	            response.getWriter().write("Login failed: invalid username or password");
            }
        } catch(SQLException sqle) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    
        	System.out.println("Error in connecting to database");
        	System.out.println(sqle.getMessage());
        	response.getWriter().write("Database error: " + sqle.getMessage());
        } catch(IOException ioe) {
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	System.out.println("Error in writing response!");
        	System.out.println("Error: " + ioe.getMessage());
        	response.getWriter().write("IOE error: " + ioe.getMessage());

        } finally {
            // close resources
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
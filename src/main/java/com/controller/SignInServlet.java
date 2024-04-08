package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/SignIn")
public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // process login and return request
    // expects username and password parameters
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the email and username from login page
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // set JDBC vars
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // get user info and check if it matches login
        try {
	        conn = StudySpotsDAO.getConnection();
	
	        // build statement
	        String sql = "SELECT * FROM UserTable WHERE Username = ? AND Password = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, username);
	        stmt.setString(2, password);
	
	        // execute & get results
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	        	// If credentials are valid, so set response status to 'ok'
	            response.setStatus(HttpServletResponse.SC_OK);    
	            response.getWriter().write("Login successful");
            } else {
                // Login failed, so set response status to 'SC_UNAUTHORIZED'
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    
	            response.getWriter().write("Login failed: invalid username or password");
            }
        } catch(SQLException sqle) {
        	System.out.println("Error in connecting to database");
        	System.out.println(sqle.getMessage());
        } catch (Exception e) {
            throw new ServletException("Login error", e);
        } finally {
            // close resources
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
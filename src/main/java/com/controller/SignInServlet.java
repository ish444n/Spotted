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

@WebServlet("/SignInServlet")
public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the email and username from login page
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // set JDBC vars
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // TODO: WRITE THE DATABASE UTILS
        try {
	        conn = DatabaseUtils.getConnection(); // !!! PSEUDOCODE
	
	        // build statement
	        String sql = "SELECT * FROM User WHERE Username = ? AND Password = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, username);
	        stmt.setString(2, password);
	
	        // execute & get results
	        rs = stmt.executeQuery();
	        if (rs.next()) {
                // Login success
                // TODO: Process success
            } else {
                // Login failed
                // TODO: Process failure
            }
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
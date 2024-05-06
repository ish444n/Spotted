package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUp")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // receives username, password, and email
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the info from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        System.out.println("made it to servlet");
        System.out.println("Email : " + email);
        System.out.println("username : " + username);
        System.out.println("password : " + password);

        // setup jdbc vars
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;
        
        try(Connection conn = SpottedDriver.getConnection();){
        	// get connection
            conn.setAutoCommit(false); // start transaction
            
            // check if the username or email is already in use
            String checkSql = "SELECT COUNT(*) AS userCount FROM UserTable WHERE Username = ? OR Email = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);

            rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt("userCount") > 0) {
                // user already exists within db
            	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    
	            response.getWriter().write("Username or email already in use.");
            } else {
                // insert new user into the table
                String insertSql = "INSERT INTO UserTable (Username, Email, Password) VALUES (?, ?, ?)";
                insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, username);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password);

                // send success / failure back to the client
                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                	// sign up went well
                	conn.commit(); // commit the transaction
                	response.setStatus(HttpServletResponse.SC_OK);    
    	            response.getWriter().write("Sign up successful");
                } else {
                	// sign up failed -- send unauthorized and undo changes
                	conn.rollback();
                	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    
    	            response.getWriter().write("Registration error");
                }
            }
        } catch (SQLException sqle) {
        	System.out.println("SQLE in signup: " + sqle.getMessage());
        } finally {
            // close resources
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (checkStmt != null) checkStmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (insertStmt != null) insertStmt.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}

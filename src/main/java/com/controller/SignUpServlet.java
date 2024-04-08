package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the info from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // setup jdbc vars
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;
        
        try {
        	// TODO: WRITE THE DATABASE UTILS CLASS
            conn = StudySpotsDAO.getConnection(); // !! PSUEDOCODE
            
            // check if the username or email is already in use
            String checkSql = "SELECT COUNT(*) AS userCount FROM User WHERE Username = ? OR Email = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);

            rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt("userCount") > 0) {
                // user already exists within db
                // TODO: WRITE LOGIC FOR DUPLICATE USERS
                
            } else {
                // insert new user into the table
                String insertSql = "INSERT INTO User (Username, Email, Password) VALUES (?, ?, ?)";
                insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, username);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password);

                
                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                	// sign up went well
                    // TODO: Redirect to login page on successful sign up
                } else {
                	// sign up failed
                    // TODO: Process failed sign up attempt
                }
            }
        } catch (Exception e) {
            throw new ServletException("Registration error", e);
        } finally {
            // close resources
            try {
                if (rs != null) rs.close();
                if (checkStmt != null) checkStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

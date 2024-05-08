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

/**
 * Servlet implementation class AddBookmarkServlet
 */
@WebServlet("/AddBookmark")
public class AddBookmarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBookmarkServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    // takes in a userID and a StudySpotID/LocationID, and adds them to the user's bookmarks
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
		
		// get the parameters
		String userID = request.getParameter("userID");
		String locationID = request.getParameter("LocationID");
		
		System.out.println("Trying to bookmark " + userID + " " + locationID);
		
				
		// check if already bookmarked
		String checksql = "SELECT * FROM BookmarkedSpotsTable WHERE UserID = ? AND StudySpotID = ?";
		try (Connection conn = SpottedDriver.getConnection();
        		PreparedStatement checkStmt = conn.prepareStatement(checksql)) {
			
			// set the parameters in the prepared statement
            checkStmt.setString(1, userID);
            checkStmt.setString(2, locationID);

            // execute and check existence
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                // user already exists within db
            	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    
	            response.getWriter().write("Already Bookmarked!");
	            return;
            }
		} catch (SQLException sqle) {
			System.err.println("Error in checking bookmark: " + sqle.getMessage());
		}
		
		// insert the locationID and the userID
		String insertsql = "INSERT INTO BookmarkedSpotsTable (UserID, StudySpotID) VALUES (?, ?)";
		try (Connection conn = SpottedDriver.getConnection();
        		PreparedStatement insertStmt = conn.prepareStatement(insertsql)) {
			
			// stop autocommit
            conn.setAutoCommit(false);
            
			// set the parameters in the prepared statement
            insertStmt.setString(1, userID);
            insertStmt.setString(2, locationID);

            // send success / failure back to the client
	        int rowsAffected = insertStmt.executeUpdate();
	        if (rowsAffected > 0) {
	        	// sign up went well
	        	conn.commit(); // commit the transaction
	        	response.setStatus(HttpServletResponse.SC_OK);    
	            response.getWriter().write("Bookmark Successful");
	        } else {
	        	// sign up failed -- send unauthorized and undo changes
	        	conn.rollback();
	        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    
	            response.getWriter().write("Bookmark failed!");
	            System.out.println("Bookmark failed!");
	        }
		} catch (SQLException sqle) {
			System.err.println("Error in checking bookmark: " + sqle.getMessage());
		}
		
	}

}

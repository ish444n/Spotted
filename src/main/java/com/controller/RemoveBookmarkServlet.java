package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveBookmark
 */
@WebServlet("/RemoveBookmark")
public class RemoveBookmarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveBookmarkServlet() {
        super();
    }

    @Override
    // takes the userID and LocationID and removes the bookmark
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get the parameters
    	String userID = request.getParameter("userID");
    	String LocationID = request.getParameter("LocationID");
    	
    	// check if they exist
    	if(userID == null || LocationID == null) {
    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    		response.getWriter().write("Need non-null parameters");
    		return;
    	}
    	
    	// try to remove the bookmark
    	String sql = "DELETE FROM BookmarkedSpotsTable WHERE UserID = ? AND StudySpotID = ?";
    	try (Connection conn = SpottedDriver.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql);) {
    		
    		// set the parameters
    		pstmt.setString(1, userID);
    		pstmt.setString(2, LocationID);
    		
    		// execute statement
    		int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted == 0) {
            	System.out.println("Failed to remove a bookmark!");
            	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            	response.getWriter().write("The bookmark requested does not exist.");
            	return;
            }
            
            // otherwise we ball
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Successfully removed bookmark");
    	} catch (SQLException sqle) {
    		System.err.println("Failed to get bookmark from the database: " + sqle.getMessage());
    	}
	}

}

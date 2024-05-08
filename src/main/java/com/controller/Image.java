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
 * Servlet implementation class Image
 */
@WebServlet("/Image")
public class Image extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get image id
		String ImageID = request.getParameter("ImageID");
		
		// get the image path
		String sql = "SELECT ImagePath FROM ImagesTable WHERE ImageID = ?";
		
		try(Connection conn = SpottedDriver.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			
			pstmt.setString(1, ImageID);
			// execute the query
			
            try (ResultSet rs = pstmt.executeQuery()) {
            	if(!rs.next()) {
            		response.setStatus(HttpServletResponse.SC_FOUND);
            		response.getWriter().write("Image not found!");
            		return;
            	}
            	
            	// get the path
                String path = rs.getString("ImagePath");
                
                // send the response back
                response.getWriter().write(path);
                response.setStatus(HttpServletResponse.SC_OK);
            }
			
		} catch (SQLException sqle) {
			System.err.println("Failed to fetch image path");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}

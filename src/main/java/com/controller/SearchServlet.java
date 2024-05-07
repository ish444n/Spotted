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

import com.google.gson.Gson;
import com.models.StudySpot;


/**
 * Servlet implementation class Search
 */
@WebServlet(name = "SearchServlet", urlPatterns = { "/Search" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// does nothing
    public SearchServlet() {
        super();
    }

    // takes study spot name and filter options -- returns a json containing all the study spot objects
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set response type to json
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");		
		String returnJson = "";
		
		
		// get params
		String query = request.getParameter("query");
		String sortBy = request.getParameter("sortBy"); // {alpha, newest}
		
		// check that params are valid
		if(query == null || sortBy == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Invalid Parameters!");
			return;
		}
		
		// build query
		String sql = "SELECT LocationID, Name FROM StudySpotsTable WHERE Name LIKE ?";
		
		// add the sortby statement
		if(sortBy.equals("alphabetical")) {
			sql += "ORDER BY Name";
		} else if(sortBy.equals("newest")) {
			sql += "ORDER BY LocationID DESC";
		} else {
			// invalid option
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Invalid Parameters!");
			return;
		}
		
		// create the PreparedStatement from the dynamically built SQL string
        try (Connection conn = SpottedDriver.getConnection();
        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            // set the parameters in the prepared statement
            pstmt.setString(1, "%" + query + "%");

            // execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                // create all the studyspot objects and append to JSON
            	List<StudySpot> spots = new ArrayList<>();
                while (rs.next()) {
                    StudySpot currSpot = StudySpotsServlet.fetchStudySpot(rs.getInt("LocationID"));
                    spots.add(currSpot);
                }
                
                // create the list of studyspots
                Gson gson = new Gson();
                returnJson = gson.toJson(spots);
                
                // send the response back
                response.getWriter().write(returnJson);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (SQLException sqle) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			System.out.println("Error in fetching study spots: " + sqle.getMessage());
		}
		
	}

}

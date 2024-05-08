package com.controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UploadSpotService extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		String name = request.getParameter("name");
	    String description = request.getParameter("details");
	    String latitude = request.getParameter("latitude");
	    String longitude = request.getParameter("longitude");
	    int lat = Integer.parseInt(latitude), longi = Integer.parseInt(longitude);
	    try (Connection conn = SpottedDriver.getConnection()) {
	    	int specID = uploadSpecification(conn, request);
	    	PreparedStatement ps = conn.prepareStatement("INSERT INTO StudySpotsTable (Name, Description, SpecID, Latitude, Longitude) VALUES (?, ?, ?, ?, ?)");
	    	ps.setString(1, name);
	    	ps.setString(2, description);
	    	ps.setInt(3, specID);
	    	ps.setInt(4, lat);
	    	ps.setInt(5, longi);
	    	ps.executeUpdate();
	    	 int locationID = -1;
	    	
		try (ResultSet rs = ps.getGeneratedKeys()) {
			if (rs.next()) {
				locationID = rs.getInt(1); 
		    }
		}
	 
    	conn.commit(); // Commit transaction
        response.getWriter().write(locationID);
        response.setStatus(HttpServletResponse.SC_CREATED);
    
	    } catch (SQLException e) {
	    	 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	         response.getWriter().write("Database error: " + e.getMessage());
		}
	}
	

	public static int uploadSpecification(Connection conn, HttpServletRequest request) throws SQLException {
		boolean if_waterFountain = false, if_restroom = false, if_microwaves = false, if_fridge = false, if_outlets = false, if_ac = false, if_wifi = false;
		int seatingC = 0;
		String waterFountain = request.getParameter("waterFountains");
		if(waterFountain.equals("yes")) {
			if_waterFountain = true;
		}
		String restroom = request.getParameter("restrooms");
		if(restroom.equals("yes")) {
			if_restroom = true;
		}
		String Microwaves = request.getParameter("microwaves");
		if(Microwaves.equals("yes")) {
			if_microwaves = true;
		}
		String refrigerators = request.getParameter("refrigerators");
		if(refrigerators.equals("yes")) {
			if_fridge = true;
		}
		String outlets = request.getParameter("outlets");
		if(outlets.equals("yes")) {
			if_outlets = true;
		}
		String ac = request.getParameter("ac");
		if(ac.equals("yes")) {
			if_ac = true;
		}
	
		String WiFi = request.getParameter("wifi");
		if(WiFi.equals("yes")) {
			if_wifi = true;
		}
		String SeatingCapacity = request.getParameter("seatingCapacity");
		seatingC = Integer.parseInt(SeatingCapacity);
		String noiseLevel = request.getParameter("noiseLevel");
		
	    try (PreparedStatement ps = conn.prepareStatement(
	            "INSERT INTO SpecsTable (waterFountain, restroom, Microwaves, refrigerators, outlets, ac, WiFi, SeatingCapacity, noiseLevel) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)",
	            PreparedStatement.RETURN_GENERATED_KEYS)) {
	        
	        ps.setBoolean(1, if_waterFountain);
	        ps.setBoolean(2, if_restroom);
	        ps.setBoolean(3, if_microwaves);
	        ps.setBoolean(4, if_fridge);
	        ps.setBoolean(5, if_outlets);
	        ps.setBoolean(6, if_ac);
	        ps.setBoolean(7, if_wifi);
	        ps.setInt(8, seatingC);
	        ps.setString(9, noiseLevel);
	        ps.executeUpdate();

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	        }
	    }
	    return -1;
	}

}

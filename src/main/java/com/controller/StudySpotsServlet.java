package com.controller;

import com.models.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.models.StudySpot;

public class StudySpotsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/StudySpots?user=root&password=root";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		String action = request.getParameter("action");

		if (action != null && action.equals("fetch")) {
			int spotId = Integer.parseInt(request.getParameter("id"));
			try {
				StudySpot spot = fetchStudySpot(spotId);
				String json = new Gson().toJson(spot);
				response.getWriter().write(json);
			} catch (SQLException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Error fetching study spot: " + e.getMessage());
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Invalid request");
		}
	}

	private StudySpot fetchStudySpot(int spotId) throws SQLException {
		StudySpot spot = null;
		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM StudySpotsTable WHERE LocationID = ?")) {
			ps.setInt(1, spotId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int locationID = rs.getInt("LocationID");
				String name = rs.getString("Name");
				String description = rs.getString("Description");

				Specification specs = fetchSpecification(conn, rs.getInt("SpecID"));
				List<Review> reviews = fetchReviews(conn, locationID);
				List<Image> pictures = fetchImages(conn, locationID);

				spot = new StudySpot(locationID, name, description, specs, reviews, pictures);
			}
			return spot;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	//changed to public static for bookmarked spots servlet
	public static Specification fetchSpecification(Connection conn, int specId) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM SpecsTable WHERE SpecID = ?");
		ps.setInt(1, specId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return new Specification(rs.getInt("SpecID"), rs.getInt("waterFountains"), rs.getInt("restrooms"),
					rs.getInt("microwaves"), rs.getInt("refrigerators"), rs.getInt("outlets"), rs.getInt("AC"),
					rs.getInt("WiFi"), rs.getInt("SeatingCapacity"), rs.getInt("noiseLevel"),
					rs.getString("openingHours"));
		}
		return null;
	}

	//changed to public static for bookmarked spots servlet
	public static List<Review> fetchReviews(Connection conn, int locationID) throws SQLException {
		List<Review> reviews = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM ReviewTable WHERE LocationID = ?");
		ps.setInt(1, locationID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			reviews.add(new Review(rs.getInt("reviewID"), rs.getInt("userID"), rs.getInt("locationID"),
					rs.getDouble("starRating"), rs.getString("details")));
		}
		return reviews;
	}
	
	//changed to public static for bookmarked spots servlet
	public static List<Image> fetchImages(Connection conn, int locationID) throws SQLException {
		List<Image> images = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM ImagesTable WHERE LocationID = ?");
		ps.setInt(1, locationID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			images.add(new Image(rs.getString("ImagePath"), rs.getInt("ImageID"), rs.getInt("LocationID")));
		}
		return images;
	}

	@Override
	public void init() throws ServletException {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ServletException("JDBC Driver not found: " + e.getMessage());
		}
	}

// Optional: Implement doPost if you need to handle POST requests
}

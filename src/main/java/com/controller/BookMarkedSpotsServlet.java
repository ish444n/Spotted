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

public class BookMarkedSpotsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/StudySpots?user=root&password=root";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		String action = request.getParameter("action");

		if (action != null && action.equals("fetch")) {
			int userId = Integer.parseInt(request.getParameter("id"));
			try {
				List<StudySpot> spots = fetchBookmarkedSpotsForUser(userId);
				String json = new Gson().toJson(spots);
				response.getWriter().write(json);
			} catch (SQLException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Error fetching bookmarked study spots: " + e.getMessage());
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Invalid request");
		}
	}

	private List<StudySpot> fetchBookmarkedSpotsForUser(int userId) throws SQLException {
		List<StudySpot> spots = new ArrayList<>();;
		try (Connection conn = DriverManager.getConnection(DB_URL);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM BookmarkedSpotsTable WHERE UserID = ?")) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int StudySpotID = rs.getInt("StudySpotID");
				String name = rs.getString("Name");
				String description = rs.getString("Description");

				Specification specs = StudySpotsServlet.fetchSpecification(conn, rs.getInt("SpecID"));
				List<Review> reviews = StudySpotsServlet.fetchReviews(conn, StudySpotID);
				List<Image> pictures = StudySpotsServlet.fetchImages(conn, StudySpotID);

				spots.add(new StudySpot(StudySpotID, name, description, specs, reviews, pictures));
			}
		
			return spots;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
/*
	private Specification fetchSpecification(Connection conn, int specId) throws SQLException {
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

	private List<Review> fetchReviews(Connection conn, int locationID) throws SQLException {
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

	private List<Image> fetchImages(Connection conn, int locationID) throws SQLException {
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
*/
// Optional: Implement doPost if you need to handle POST requests
}

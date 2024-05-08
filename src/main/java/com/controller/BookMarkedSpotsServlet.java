package com.controller;

import com.models.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.models.StudySpot;
import com.models.Image;

@WebServlet("/Bookmarks")
public class BookMarkedSpotsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


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
		try (Connection conn = SpottedDriver.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM BookmarkedSpotsTable WHERE UserID = ?")) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int StudySpotID = rs.getInt("StudySpotID");
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				int longitude = rs.getInt("Latitude");
				int latitude = rs.getInt("Longitude");
				Specification specs = StudySpotsServlet.fetchSpecification(conn, rs.getInt("SpecID"));
				List<Review> reviews = StudySpotsServlet.fetchReviews(conn, StudySpotID);
				List<Image> pictures = StudySpotsServlet.fetchImages(conn, StudySpotID);

				spots.add(new StudySpot(StudySpotID, name, description, specs, reviews, pictures, latitude, longitude));
			}
		
			return spots;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}

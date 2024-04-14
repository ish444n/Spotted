package com.controller;
import com.models.UserPreferences;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class UserPreferencesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// change later
	private static final String DB_URL = "jdbc:mysql://localhost:3306/UserPreferences?user=root&password=root";
	
	// get user preferencces 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        UserPreferences preferences = getUserPreferences(userId);
        Gson gson = new Gson ();
        if (preferences != null) {
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(preferences)); 
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
	
	// update user preferences
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int comfortAndConvenience = Integer.parseInt(request.getParameter("comfortAndConvenience"));
        int preferredStudySpaceSize = Integer.parseInt(request.getParameter("preferredStudySpaceSize"));
        int noiseLevel = Integer.parseInt(request.getParameter("noiseLevel"));
        boolean prefers24_7 = Boolean.parseBoolean(request.getParameter("prefers24_7"));
        boolean connectivity = Boolean.parseBoolean(request.getParameter("connectivity"));

        UserPreferences preferences = new UserPreferences(userId, comfortAndConvenience, preferredStudySpaceSize, noiseLevel, prefers24_7, connectivity);
        updatePreferences(preferences);
        response.setStatus(HttpServletResponse.SC_OK);
    }

	private UserPreferences getUserPreferences(int userId) {
        // Connect to DB and retrieve preferences
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UserPreferences WHERE userId = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserPreferences(
                    rs.getInt("userId"),
                    rs.getInt("comfortAndConvenience"),
                    rs.getInt("preferredStudySpaceSize"),
                    rs.getInt("noiseLevel"),
                    rs.getBoolean("prefers24_7"),
                    rs.getBoolean("connectivity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	private void updatePreferences(UserPreferences preferences) {
        // Update preferences in DB
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("UPDATE UserPreferences SET comfortAndConvenience = ?, preferredStudySpaceSize = ?, noiseLevel = ?, prefers24_7 = ?, connectivity = ? WHERE userId = ?")) {

            stmt.setInt(1, preferences.getComfortAndConvenience());
            stmt.setInt(2, preferences.getPreferredStudySpaceSize());
            stmt.setInt(3, preferences.getNoiseLevel());
            stmt.setBoolean(4, preferences.getPrefers24_7());
            stmt.setBoolean(5, preferences.getConnectivity());
            stmt.setInt(6, preferences.getUserId());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

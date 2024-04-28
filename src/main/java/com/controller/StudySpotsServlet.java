package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.models.StudySpot;

public class StudySpotsServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/StudySpots?user=root&password=root";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortBy = request.getParameter("sortBy");

        String sql = "SELECT * FROM StudySpotsTable WHERE Name LIKE ?";
        switch (sortBy) {
            case "newest":
                sql += " ORDER BY StudySpotID";
                break;
            case "closest":
                sql += " ORDER BY Coordinates"; // Assuming you have a field for this
                break;
            default:
                sql += " ORDER BY Name";
                break;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();

            Gson gson = new Gson();
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(rsToList(rs)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<StudySpot> rsToList(ResultSet rs) throws SQLException {
        List<StudySpot> list = new ArrayList<>();
        while (rs.next()) {
            StudySpot spot = new StudySpot();
            spot.setLocationID(rs.getInt("LocationID"));
            spot.setName(rs.getString("Name"));
            spot.setDescription(rs.getString("Description"));
            list.add(spot);
        }
        return list;
    }

}

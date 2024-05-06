package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// class to return the database connection
public class SpottedDriver {
	static {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/StudySpots?useSSL=false&allowPublicKeyRetrieval=true";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	// returns the database connection
	public static Connection getConnection() {
	    try {
	        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
	    } catch (SQLException e) {
	        e.printStackTrace();  // Log or handle the error more gracefully as needed
	        throw new RuntimeException("Failed to connect to the database", e);
	    }
	}
}

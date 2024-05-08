package com.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "Spotted/src/main/webapp/assets/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        String keyDirectory = "eclipse-workspace/";
        String extractedPath = "";
        
        int endIndex = appPath.indexOf(keyDirectory) + keyDirectory.length();
        
        if(endIndex != -1) { 
            extractedPath = appPath.substring(0, endIndex);
            System.out.println("Extracted Path: " + extractedPath);
        } else {
            System.out.println("Key directory not found in the path.");
        }

        
        String savePath = extractedPath + SAVE_DIR;

        // creates save directory if it doesnt exists
        File fileSaveDir = new File(savePath);
        
        System.out.println("appPath: " +appPath + "\nsavePath: " +savePath );
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

//        for (Part part : request.getParts()) {
//            String fileName = extractFileName(part);
//            // refines the fileName in case it is an absolute path
//            fileName = new File(fileName).getName();
//            part.write(savePath + File.separator + fileName);
//            System.out.println("fileName: " +fileName );
//        }


        // set JDBC vars
        PreparedStatement uploadStmt = null;
        
        // put into DB
        try {
        	// get connection
        	Connection conn = SpottedDriver.getConnection();
            conn.setAutoCommit(false); // start transaction
        	
          for (Part part : request.getParts()) {
	          String fileName = extractFileName(part);
	          // refines the fileName in case it is an absolute path
	          fileName = new File(fileName).getName();
	          part.write(savePath + File.separator + fileName);
	          System.out.println("fileName: " +fileName );
	          
	          
	          //database stuff
	          String sql = "INSERT INTO ImagesTable VALUES (LocationID, ImagePath) VALUES (?, ?)";
	          uploadStmt = conn.prepareStatement(sql);
	          uploadStmt.setInt(1, Integer.parseInt(request.getParameter("locationid")));
	          uploadStmt.setString(2, fileName);
	          
	          // send success / failure back to the client
	          int rowsAffected = uploadStmt.executeUpdate();
	          if (rowsAffected > 0) {
	        	  // upload went well
	        	  conn.commit(); // commit the transaction
	        	  response.setStatus(HttpServletResponse.SC_OK);    
				  response.getWriter().write("Upload successful");
	          } else {
	        	  // upload failed -- send unauthorized and undo changes
	        	  conn.rollback();
	        	  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    
	        	  response.getWriter().write("Upload error");
	          }
          }
            
        } catch (SQLException sqle) {
        	System.out.println("SQLE in upload: " + sqle.getMessage());
        } finally {
            // close resources
            try { if (uploadStmt != null) uploadStmt.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        

        response.getWriter().print("Upload has been done successfully!");
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}

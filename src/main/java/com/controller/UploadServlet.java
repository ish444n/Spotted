package com.controller;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class UploadServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "/webapp/assets";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }

        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // constructs the directory path to store upload file
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields (i.e., the file field)
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);

                        // saves the file on disk
                        item.write(storeFile);

                        // Here you might want to store the filePath in your database
                        saveImagePathInDatabase(filePath);
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
        }
        // redirects to the message page
        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }

    private void saveImagePathInDatabase(String filePath) {
        // Here you should write code to save the file path in your database.
        // Example:
        // Connection connection = null;
        // PreparedStatement statement = null;
        // try {
        //     connection = dataSource.getConnection();
        //     statement = connection.prepareStatement("INSERT INTO ImagesTable (ImagePath) VALUES (?)");
        //     statement.setString(1, filePath);
        //     statement.executeUpdate();
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // } finally {
        //     if (statement != null) statement.close();
        //     if (connection != null) connection.close();
        // }
    }
}

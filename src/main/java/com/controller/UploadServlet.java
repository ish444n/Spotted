package com.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.File;

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

        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            // refines the fileName in case it is an absolute path
            fileName = new File(fileName).getName();
            part.write(savePath + File.separator + fileName);
            System.out.println("fileName: " +fileName );
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

/*
Course Name: CS490BK - Server Side Web Applications.
Student Name: Pratheeksha Ravindra Naik
Student Number: 200406215
Assignment Number: 1
Question: 4
Date Completion: 21 June 2019

 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pratheeksha Ravindra Naik
 */

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {

    // file extwnsion      
    private final String extension = ".pdf";
    // set of array buffer to download file    
    private final int ARBITARY_SIZE = 1048;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Obtain the name of the file from parameters
        String paramValue = request.getParameter("fileName");
        String fileName = "";
        // check the value of "filename" parameter        
        if (paramValue != null) {
            paramValue = paramValue.trim();
            // if the parameter exists
            // handle if file has extension or not            
            if (paramValue.length() > 0) {
                fileName += fixFileName(paramValue, extension);
            } else {
                // if value of filename is empty
                // return error message                
                PrintWriter out = response.getWriter();
                RequestDispatcher req = getServletContext().getRequestDispatcher("/index.html");
                out.println("<title>Blank Filename</title>");
                out.println("<h1>Please enter valid filename</h1>"); //Invalid at runtime
                req.include(request, response);
            }
        }
        
        String path = "/WEB-INF/pdf/" + fileName;
        // Check if the file exists
        if (getServletContext().getRealPath(path) != null) {
            OutputStream out;
            // Download file if it esists
            try (FileInputStream in = new FileInputStream(new File(getServletContext().getRealPath(path)))) {
                out = response.getOutputStream();
                // set response headers
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                
                // start download
                byte[] buffer = new byte[ARBITARY_SIZE];
                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }
            out.close();
        } else {
            // if the file does not exists
            // retturn error "file does not exists"
            PrintWriter out = response.getWriter();
            RequestDispatcher req = getServletContext().getRequestDispatcher("/Download.html");
            out.println("<title>Invalid Filename</title>");
            out.println("<h1>Filename: " + fileName + " not valid</h1>");
            req.include(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // Fix name of the file to download    
    // to handle presense of extension
    // ".pdf" in filename
    static String fixFileName(String fileName, String extension) {
        if (fileName.contains(extension)) {
            return fileName;
        }
        return fileName + "" + extension;
    }
}

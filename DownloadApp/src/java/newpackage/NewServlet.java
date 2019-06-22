/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aditi
 */
public class NewServlet extends HttpServlet {

    private final String extension = ".pdf";
    private final int ARBITARY_SIZE = 1048;
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         
        //Obtain the name of the file 
        String paramValue = request.getParameter("fileName");
        String fileName = "";
        if (paramValue != null) {
            paramValue = paramValue.trim();
            if (paramValue.length() > 0) {
                fileName += fixFileName(paramValue, extension);
            } else {
                PrintWriter out = response.getWriter();
                RequestDispatcher req = getServletContext().getRequestDispatcher("/index.html");
                out.println("<title>Blank Filename</title>");
                out.println("<h1>Please enter valid filename</h1>"); //Invalid at runtime
                req.include(request, response);
            }
        }
        
        // Download file        
        
            String path = "/WEB-INF/pdf/" + fileName;
            System.out.println(getServletContext().getRealPath(path) );
            File file = new File(getServletContext().getRealPath(path));
           
            if (file.exists()) {
                OutputStream out;
            try ( FileInputStream in = new FileInputStream(file);) {
                out = response.getOutputStream();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                byte[] buffer = new byte[ARBITARY_SIZE];
                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }
            out.close();
            } else {
                 PrintWriter out = response.getWriter();
            RequestDispatcher req = getServletContext().getRequestDispatcher("/index.html");
            out.println("<title>Invalid Filename</title>");
            out.println("<h1>Filename: " + fileName + " not valid</h1>"); 
            req.include(request, response);
            }
         
        
    }
     
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         doGet(request,response);
    }
    
    // Fix name of the file to download    
    static String fixFileName(String fileName, String extension) {
        String returnFileName = "";
        if (fileName.contains(extension)) {
            return returnFileName;
        } else {
            returnFileName += fileName + "" + extension;
        }
        return returnFileName;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aditi
 */
public class ChangePasswordServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userName = request.getParameter("uName");
        String oldPassword = request.getParameter("oldPass");
        String newPassword = request.getParameter("newPass");
        String confirmNewPassword = request.getParameter("confirmNewPass");
        String message = "Password Change - Unsuccessful.";
        
        response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>"); 
            out.println("<title> ChangePasswordServlet </title>");
            out.println("</head>");
            out.println("<body>");
            
            
        HttpSession session = request.getSession();
        
        Hashtable users = (Hashtable) session.getAttribute("users");
if (users == null) {
			users = new Hashtable();
			users.put("ann", "aaa");
			users.put("john", "jjj");
			users.put("mark", "mmm");
			// setting user attribute in session
			session.setAttribute("users", users);
		}
else{
    
    String originalPassword = (String) users.get(userName);
			if (originalPassword != null) {
			if (originalPassword.equals(oldPassword)) {
					// check if new password and confirm new password matches
					if ((confirmNewPassword).equals(newPassword)) {
						// sets success message
						request.setAttribute("message", "Password changed successful");
						message = "Password changed successfully.";
						// update new password in users object.
						users.put(request.getParameter("username"), request.getParameter("newpassword"));
						session.setAttribute("users", users);

					}
				}
                        }
}
//        response.setContentType("text/html");
//            PrintWriter out = response.getWriter();
//            out.println("<html>");
//            out.println("<head>"); 
//            out.println("<title> ChangePasswordServlet </title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println(message);
         
        
//        //If valid change in LoginServlet.
//        if (newPassword.equals(confirmNewPassword)){
//            out.println("<h3>Password Changed Successfully. </h3>");
//          //  request.getRequestDispatcher("/LoginServlet").forward(request, response);
//            
//        }else{
//            //Password unsuccessful.
//    out.println("<h3>Password Unsuccessful</h3>");
//        }
//        

out.println("<h3>");
out.println(message);
out.println("</h3>");
    request.getRequestDispatcher("/index.html").include(request, response);
    out.println("</body>");
    out.println("</html>");

    
        }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 *
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet
{
	//Hashtable users = new Hashtable();

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException
	{
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException
	{
		String userid = req.getParameter("userid");
                //String Pass = (String) req.getAttribute("newPassword");
		String password = req.getParameter("password");
                
                HttpSession session = req.getSession();
                Hashtable users = (Hashtable) session.getAttribute("users");
                
                if(users==null){
                    users = new Hashtable();
                users.put("ann", "aaa");
		users.put("john", "jjj");
		users.put("mark", "mmm");
                }
                
		if(userid != null && password != null && password.equals(users.get(userid)) )
		{
			//set userid in request to pass it on to AccountServlet
			req.setAttribute("userid", userid);

			//userid,password verified. Forward request to AccountServlet.
			//observe the use of absolute path.
			req.getRequestDispatcher("/AccountServlet").forward(req, res);
			return;
		}
		else
		{
			//Unable to login. Send login.html to the user.
			//observe the use of relative path.
			req.getRequestDispatcher("/login.html").forward(req, res);
			return;
		}

	}
//	public void init()
//	{
////		users.put("ann", "aaa");
////		users.put("john", "jjj");
////		users.put("mark", "mmm");
//	}
}

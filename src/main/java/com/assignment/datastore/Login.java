package com.assignment.datastore;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * 
 * @author Archit
 *
 */
@SuppressWarnings("serial")
public class Login extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Query q = new Query("User");
		PreparedQuery pq = datastore.prepare(q);
		Iterable<Entity> entityList = pq.asIterable();
		boolean flag = false;
		for (Entity entity : entityList) {
			String dbusername = entity.getProperty("email").toString();
			String dbpassword = entity.getProperty("password").toString();
			if (username != null && username.equalsIgnoreCase(dbusername) && password != null
					&& password.equalsIgnoreCase(dbpassword)) {
				flag = true;
				break;
			}
		}
		if (flag) {
			res.setContentType("text/html");
			res.getWriter().println("Login Successfull! <br/>");
		} else {
			res.setContentType("text/html");
			res.getWriter().println("Login Unsuccessfull! <br/>");
		}
	}

}
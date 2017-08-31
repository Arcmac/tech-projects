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
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * 
 * @author Archit
 *
 */
@SuppressWarnings("serial")
public class Login extends HttpServlet {

	@SuppressWarnings("deprecation")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Query q = new Query("User").addFilter("email", FilterOperator.EQUAL, username).addFilter("password",
				FilterOperator.EQUAL, password);
		PreparedQuery pq = datastore.prepare(q);
		Entity singleEntity = pq.asSingleEntity();
		if (singleEntity != null) {
			res.setContentType("text/html");
			res.getWriter().println("Login Successfull! <br/>");
		} else {
			res.setContentType("text/html");
			res.getWriter().println("Login Unsuccessfull! <br/>");
		}
	}
}
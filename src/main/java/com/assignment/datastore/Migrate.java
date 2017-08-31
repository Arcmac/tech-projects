package com.assignment.datastore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.InsertAllRequest.RowToInsert;
import com.google.cloud.bigquery.Table;

/**
 * 
 * @author Archit
 *
 */
@SuppressWarnings("serial")
public class Migrate extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

		BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("User");
		PreparedQuery pq = datastore.prepare(q);
		Iterable<Entity> entityList = pq.asIterable();
		Table table = bigquery.getTable("User", "User");
		List<RowToInsert> rows = new ArrayList<>();
		Map<String, Object> row1 = new HashMap<>();
		for (Entity entity : entityList) {
			Object name = entity.getProperty("name");
			String email = entity.getProperty("email").toString();
			Object password = entity.getProperty("password");
			Object phone = entity.getProperty("phone");
			Object dob = entity.getProperty("dob");
			Object address = entity.getProperty("address");
			Object gender = entity.getProperty("gender");
			row1.put("email", email);
			if (name != null) {
				row1.put("name", name);
			}
			if (password != null) {
				row1.put("password", password.toString());
			}
			if (dob != null) {
				row1.put("dob", dob.toString());
			}
			if (gender != null) {
				row1.put("gender", gender.toString());
			}
			if (address != null) {
				row1.put("address", address.toString());
			}
			if (phone != null) {
				row1.put("phone", phone.toString());
			}
			rows.add(RowToInsert.of(email, row1));
		}
		table.insert(rows);
		res.setContentType("text/plain");
		res.getWriter().println("Migration done. ");
	}
}

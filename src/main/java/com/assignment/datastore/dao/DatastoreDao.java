package com.assignment.datastore.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class DatastoreDao {

	final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public void createUsers(Entity user) {
		datastore.put(user);
	}

	public Iterable<Entity> getUsers() {
		Query q = new Query("User");
		PreparedQuery pq = datastore.prepare(q);
		return pq.asIterable();
	}

	@SuppressWarnings("deprecation")
	public Entity getAuthenticUser(String username, String password) {
		Query q = new Query("User").addFilter("email", FilterOperator.EQUAL, username).addFilter("password",
				FilterOperator.EQUAL, password);
		PreparedQuery pq = datastore.prepare(q);
		return pq.asSingleEntity();
	}

}

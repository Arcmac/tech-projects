package com.assignment.datastore.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@Controller
public class MigrateController {

	@RequestMapping(value = "/upload")
	public ModelAndView uploadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
		List<BlobKey> blobKeys = blobs.get("file");

		if (blobKeys == null || blobKeys.isEmpty()) {
			return new ModelAndView("index","msg","File could not be uploaded!");
		} else {
			com.assignment.datastore.service.DatastoreService service = new com.assignment.datastore.service.DatastoreService();
			service.createUsers(blobKeys.get(0).getKeyString());
			return new ModelAndView("index","msg","File uploaded successfully!");
		}
	}

	@RequestMapping(value = "/login")
	public ModelAndView checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		com.assignment.datastore.service.DatastoreService service = new com.assignment.datastore.service.DatastoreService();
		Entity singleEntity = service.getAuthenticUsers(username, password);
		if (singleEntity != null) {
			return new ModelAndView("index","msg","Authentication Successfull!");
		} else {
			return new ModelAndView("index","msg","Authentication Unuccessfull! Please check your username/password and tyy again.");
		}
	}

	@RequestMapping(value = "/migrate")
	public ModelAndView migrate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		com.assignment.datastore.service.DatastoreService service = new com.assignment.datastore.service.DatastoreService();
		service.migrateToBigquery();
		return new ModelAndView("index","msg","Migration Done!");
	}
}

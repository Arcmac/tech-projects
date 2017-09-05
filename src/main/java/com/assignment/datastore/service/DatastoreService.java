package com.assignment.datastore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.assignment.datastore.dao.DatastoreDao;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.datastore.Entity;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.InsertAllRequest.RowToInsert;
import com.google.cloud.bigquery.Table;

public class DatastoreService {
	
	final DatastoreDao  datastoreDao = new DatastoreDao();

	public void createUsers(String blobK) throws IOException {
		BlobKey blobKey = new BlobKey(blobK);
		XSSFWorkbook workbook = new XSSFWorkbook(new BlobstoreInputStream(blobKey));
		XSSFSheet worksheet = workbook.getSheetAt(0);
		for (int rows = 1; rows < worksheet.getPhysicalNumberOfRows(); rows++) {
			XSSFRow row1 = worksheet.getRow(rows);
			Entity user = null;
			if (row1.getCell(2) != null) {
				user = new Entity("User",row1.getCell(2).getStringCellValue());
				user.setProperty("email", row1.getCell(2).getStringCellValue());
				if (row1.getCell(0) != null) {
					user.setProperty("name", row1.getCell(0).getStringCellValue());
				}
				if (row1.getCell(1) != null) {
					user.setProperty("dob", row1.getCell(1).getDateCellValue());
				}
				if (row1.getCell(3) != null) {
					user.setProperty("password", row1.getCell(3).getStringCellValue());
				}
				if (row1.getCell(4) != null) {
					user.setProperty("phone", row1.getCell(4).getNumericCellValue());
				}
				if (row1.getCell(5) != null) {
					user.setProperty("gender", row1.getCell(5).getStringCellValue());
				}
				if (row1.getCell(6) != null) {
					user.setProperty("address", row1.getCell(6).getStringCellValue());
				}
				datastoreDao.createUsers(user);
			}
		}
		workbook.close();
	}

	public void migrateToBigquery() {
		Iterable<Entity> entityList = datastoreDao.getUsers();
		BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
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
	}

	public Entity getAuthenticUsers(String username, String password) {
		return datastoreDao.getAuthenticUser(username, password);
	}
}

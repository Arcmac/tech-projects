package com.assignment.datastore;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * 
 * @author Archit
 *
 */
@SuppressWarnings("serial")
public class Serve extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String blobK = req.getParameter("blob-key");
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
					user.setProperty("dob", row1.getCell(1).getStringCellValue());
				}
				if (row1.getCell(3) != null) {
					user.setProperty("password", row1.getCell(3).getStringCellValue());
				}
				if (row1.getCell(4) != null) {
					user.setProperty("phone", row1.getCell(4).getStringCellValue());
				}
				if (row1.getCell(5) != null) {
					user.setProperty("gender", row1.getCell(5).getStringCellValue());
				}
				if (row1.getCell(6) != null) {
					user.setProperty("address", row1.getCell(6).getStringCellValue());
				}
				datastore.put(user);
			}
		}
		res.sendRedirect("/login.jsp");
	}
}
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>

<%@page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
<link type="text/css" rel="stylesheet" href="/stylesheets/font-awesome.min.css" />
<script type="text/javascript" src="/javascripts/main.js"></script>
<script type="text/javascript" src="/javascripts/util.js"></script>
<script type="text/javascript" src="/javascripts/jquery.min.js"></script>
<script type="text/javascript" src="/javascripts/main.js"></script>
<script type="text/javascript" src="/javascripts/main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google Datastore Migrate API</title>
</head>
	<c:if test="${not empty msg}">
		<script type="text/javascript">
    alert("${msg}");        
</script>
	</c:if>
	<body>
	<br/>
	<h3>Upload your CSV file here</h3>
	<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
	<form action="<%= blobstoreService.createUploadUrl("/upload") %>"
		method="post" enctype="multipart/form-data">
		<input type="file" name="file" required> <input type="submit"
			value="Submit">
	</form>
	<hr>
	<br />
	<h3>Check User Login:</h3>
	<form action="/login" method="post">
		Username:<input type="text" name="username" style="width: 50%;" required /><br />
		Password:<input type="password" name="password" style="width: 50%;"/><br /> <input
			type="submit" value="login" />
	</form>
<hr><br/>
	<h3>Migrate Data to Bigquery</h3>
	<form action="/migrate" method="get">
		<input type="submit" value="Migrate" />
	</form>
</body>
</html>
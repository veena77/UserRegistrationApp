<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/app.js"></script>
</head>

<body>
<h1 style="text-align:center">Register here</h1>
<table>

<form:form  action="userAccReg" method="POST" modelAttribute="user" >
<tr><td>First Name::</td>
<td><form:input path="firstName"/></td></tr>
<tr><td>Last Name::</td>
<td> <form:input path="lastName"/></td></tr>
<tr><td>EmailId::</td>
<td><form:input path="emailId"/></td></tr>

<tr><td>Phone No::</td>
<td><form:input path="phoneNo"/></td></tr>
<tr>
<td>Date-Of-Birth</td>
<td><form:input  path="dob" type="date"  /></td></tr>
<tr>
<td>Gender</td>
<td>
<form:radiobutton path="Gender" value="M" id="gender"/> Male
<form:radiobutton path="Gender" value="F" id="gender"/> Female
</td></tr>
<tr>
<td>Country::</td>
<td><form:select path="countryId">
<form:option value="">-Select-</form:option>
<form:options items="${countryMap}"/>
</form:select></td></tr>
<tr>
<td>State::</td>
<td><form:select path="stateId" >
<form:option value="">-Select-</form:option>
</form:select></td></tr>
<tr>
<td>City::</td>
<td><form:select path="cityId">
<form:option value="">-Select-</form:option>
</form:select></td></tr>
<tr>
<td><input type="reset" value="Reset"/><input type="submit" value="Save"/></td>
</tr>


</form:form>
</table>
</body>
</html>
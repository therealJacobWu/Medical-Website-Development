<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="edu.ncsu.csc.itrust.enums.Role" %>

<html>
<head>
<title>FOR TESTING PURPOSES ONLY</title>
<link href="/iTrust/css/datepicker.css" type="text/css" rel="stylesheet" />
<script src="/iTrust/js/DatePicker.js" type="text/javascript"></script>
<style>
body, td{
	font-family: sans-serif;
	font-size: 8pt;
}
</style>
</head>
<body>

<%
    String startDate = "";
    String endDate = "";
%>

<form action="/iTrust/util/transactionLog.jsp" method="post">
    <select name="rolesStr">
        <%
			String userRole = (String) session.getAttribute("userRole");
            String selected = "";
            for (Role eth : Role.values()) {
                selected = (eth.getUserRolesString().equals(userRole)) ? "selected=selected" : "";
        %>
        <option value="<%= eth.getUserRolesString()%>" <%= StringEscapeUtils.escapeHtml("" + (selected)) %>><%= StringEscapeUtils.escapeHtml("" + (eth.getUserRolesString())) %></option>
        <%
            }
        %>
    </select>


    <select name="rolesStr2">
        <%
            String selected2 = "";
            for (Role eth : Role.values()) {
				selected2 = (eth.getUserRolesString().equals(userRole)) ? "selected=selected" : "";
        %>
        <option value="<%= eth.getUserRolesString()%>" <%= StringEscapeUtils.escapeHtml("" + (selected2)) %>><%= StringEscapeUtils.escapeHtml("" + (eth.getUserRolesString())) %></option>
        <%
            }
        %>
    </select>

    <input name="startDate" value="<%= StringEscapeUtils.escapeHtml("" + (startDate)) %>" size="10"/>
    <input type=button value="Select Date" onclick="displayDatePicker('startDate');"/>
    <input name="endDate" value="<%= StringEscapeUtils.escapeHtml("" + (endDate)) %>" size="10"/>
    <input type=button value="Select Date" onclick="displayDatePicker('endDate');"/>

    TRANSACTION TYPE NAMES

    <input type="submit">View</input>
</form>

<h1>Test Utilities</h1>
A few clarifications:
<ul>
	<li>The <b>Type</b> is the name of the Java enum (from <code>edu.ncsu.csc.itrust.enums.TransactionType</code>)</li>
	<li>The <b>Code</b> is the actual key that gets stored in the database, defined in the Transaction Type enum. Here's the <a href="#transactioncodes">table of transaction codes</a></a></li>
	<li>The <b>Description</b> is plain-English description of that logging type
</ul>
<table border=1>
	<tr>
		<th>ID></th>
		<th>Time Logged</th>
		<th>Type</th>
		<th>Code</th>
		<th>Description</th>
		<th>Logged in User MID</th>
		<th>Secondary MID</th>
		<th>Extra Info</th>
	</tr>
	<%
		
		List<TransactionBean> list = DAOFactory.getProductionInstance().getTransactionDAO().getAllTransactions();
		for (TransactionBean t : list) {
	%>
	<tr>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionID())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTimeLogged())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().name())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().getCode())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().getDescription())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getLoggedInMID())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getSecondaryMID())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getAddedInfo())) %></td>
	</tr>
	<%
	}
	%>
</table>
<h1><a href="/iTrust">Back to iTrust</a></h1>
<h1>Transaction Code Reference</h1>
<a name="transactioncodes"></a>
List is automatically generated from the <code>edu.ncsu.csc.itrust.enums.TransactionType</code> enum.
<table border=1>
<tbody>
<tr>
<th>Type</th>
<th>Code</th>
<th>Description</th>
</tr>
<%
for(TransactionType type : TransactionType.values()){
	%><tr><td><%=type.name()%></td><%
	%><td><%=type.getCode()%></td><%
	%><td><%=type.getDescription()%></td></tr><%
}
%>
</tbody>
</table>
<h1><a href="/iTrust">Back to iTrust</a></h1>
</body>
</html>

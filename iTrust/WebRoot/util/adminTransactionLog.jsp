<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="edu.ncsu.csc.itrust.enums.Role" %>
<%@ page import="edu.ncsu.csc.itrust.dao.mysql.AuthDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="edu.ncsu.csc.itrust.exception.ITrustException" %>

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
	String loggedInUserField = request.getParameter("loggedInUserField");
	String secondaryUserField = request.getParameter("secondaryUserField");

    String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");


	List<TransactionBean> allTransactions = DAOFactory.getProductionInstance().getTransactionDAO().getAllTransactions();
	AuthDAO authDAO = DAOFactory.getProductionInstance().getAuthDAO();
	List<TransactionBean> filteredTransactions = new ArrayList<TransactionBean>();


	if ("POST".equalsIgnoreCase(request.getMethod())) {
	    try {
			int transactionType = Integer.parseInt(request.getParameter("transactionType"));

			for (TransactionBean t : allTransactions) {
				if (t.getLoggedInMID() != 0 && !authDAO.getUserRole(t.getLoggedInMID()).getUserRolesString().equals(loggedInUserField)) {
					continue;
				}

				if (t.getSecondaryMID() != 0 && !authDAO.getUserRole(t.getSecondaryMID()).getUserRolesString().equals(secondaryUserField)) {
					continue;
				}

				if (t.getTransactionType().getCode() != transactionType) {
					continue;
				}

				filteredTransactions.add(t);
			}

			allTransactions = filteredTransactions;
			// distinguish between view and summarize
			if (request.getParameter("view") != null) {

			} else if (request.getParameter("summarize") != null) {

			} else {
				// not possible
			}
		} catch (ITrustException | NullPointerException | NumberFormatException e) {

		}
	}
%>

<form action="/iTrust/util/adminTransactionLog.jsp" method="post">
	<h1>Filter Transactions:</h1>
	<label>Logged-In User Field</label><br/>
	<select name="loggedInUserField">
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
    </select><br/>


	<label>Secondary User Field</label><br/>
	<select name="secondaryUserField">
        <%
            String selected2 = "";
            for (Role eth : Role.values()) {
				selected2 = (eth.getUserRolesString().equals(userRole)) ? "selected=selected" : "";
        %>
        <option value="<%= eth.getUserRolesString()%>" <%= StringEscapeUtils.escapeHtml("" + (selected2)) %>><%= StringEscapeUtils.escapeHtml("" + (eth.getUserRolesString())) %></option>
        <%
            }
        %>
    </select><br/>

	<label>Transaction Start Date</label><br/>
    <input name="startDate" value="<%= StringEscapeUtils.escapeHtml("" + (startDate)) %>" size="10"/>
    <input type=button value="Select Date" onclick="displayDatePicker('startDate');"/><br/>
	<label>Transaction End Date</label><br/>
    <input name="endDate" value="<%= StringEscapeUtils.escapeHtml("" + (endDate)) %>" size="10"/>
    <input type=button value="Select Date" onclick="displayDatePicker('endDate');"/><br/>

	<label>Transaction Type</label><br/>
	<select name="transactionType">
		<%
			String selected3 = "";
			for (TransactionType transType: TransactionType.values()) {
				selected3 = "";
		%>
		<option value="<%= transType.getCode()%>" <%= StringEscapeUtils.escapeHtml("" + (selected3)) %>>
			<%= StringEscapeUtils.escapeHtml("(" + transType.getCode() + ") " + transType.name()) %></option>
		<%
			}
		%>
	</select><br/>

	<label>Filter transactions</label><br/>
	<input type="submit" name="view" value="View"><br/>
	<input type="submit" name="summarize" value="Summarize"><br/>
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
		for (TransactionBean t : allTransactions) {
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

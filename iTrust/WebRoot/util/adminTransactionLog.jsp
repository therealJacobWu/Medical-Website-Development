<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="edu.ncsu.csc.itrust.enums.Role" %>
<%@ page import="edu.ncsu.csc.itrust.dao.mysql.AuthDAO" %>
<%@ page import="edu.ncsu.csc.itrust.exception.ITrustException" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.*" %>


<html>
<head>
<title>FOR TESTING PURPOSES ONLY</title>
<link href="/iTrust/css/datepicker.css" type="text/css" rel="stylesheet" />
<script src="/iTrust/js/DatePicker.js" type="text/javascript"></script>
<script src="/iTrust/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="/iTrust/js/highcharts.js" type="text/javascript"></script>
<script src="/iTrust/js/highcharts-more.js" type="text/javascript"></script>
<style>
body, td{
	font-family: sans-serif;
	font-size: 8pt;
}
</style>
</head>
<body>

<%! public static final long NO_SECONDARY_MID = 0L;
%><%
	String loggedInUserField = request.getParameter("loggedInUserField");
	String secondaryUserField = request.getParameter("secondaryUserField");

	// Default date is empty string, not null
    String startDate = request.getParameter("startDate");
    if (startDate == null) {
        startDate = "";
    }
	String endDate = request.getParameter("endDate");
    if (endDate == null) {
        endDate = "";
    }

    List<TransactionBean> allTransactions = DAOFactory.getProductionInstance().getTransactionDAO().getAllTransactions();
	AuthDAO authDAO = DAOFactory.getProductionInstance().getAuthDAO();
	List<TransactionBean> filteredTransactions = new ArrayList<TransactionBean>();

	int transactionType = 0;
	try {
	    transactionType = Integer.parseInt(request.getParameter("transactionType"));
	} catch (Exception e ) { }

	boolean showView = (request.getParameter("view") != null);
	boolean showSummary = (request.getParameter("summarize") != null);

	HashMap<String, Integer> chart1 = new HashMap<>();
	HashMap<String, Integer> chart2 = new HashMap<>();
    LinkedHashMap<String, Integer> chart3 = new LinkedHashMap<>();
    HashMap<Integer, Integer> chart4 = new HashMap<>();
    // Initialize dictionary
	for (Role role : Role.values()){
		String loggedInUserRole = role.getUserRolesString();
		chart1.put(loggedInUserRole, 0);
		chart2.put(loggedInUserRole, 0);
	}
	// Count # of 0 secondaryUserRoles as well
	chart2.put("0", 0);

	if ("POST".equalsIgnoreCase(request.getMethod())) {
	    try {
			for (TransactionBean t : allTransactions) {
				if (!loggedInUserField.equals("") && t.getLoggedInMID() != 0 &&
					!authDAO.getUserRole(t.getLoggedInMID()).getUserRolesString().equals(loggedInUserField)) {
					continue;
				}

				if (!secondaryUserField.equals("") && t.getSecondaryMID() != 0 &&
					!authDAO.getUserRole(t.getSecondaryMID()).getUserRolesString().equals(secondaryUserField)) {
					continue;
				}

				if (transactionType != -1 && t.getTransactionType().getCode() != transactionType) {
					continue;
				}

				// SimpleDatePicker returns date in this format
				DateFormat dateParser = new SimpleDateFormat("MM/dd/yy");
				try {
					if (!startDate.equals("") && t.getTimeLogged().before(dateParser.parse(startDate))) {
						continue;
					}

					if (!endDate.equals("") && t.getTimeLogged().after(dateParser.parse(endDate))) {
						continue;
					}
				} catch (ParseException e) {
				    continue;
				}
				filteredTransactions.add(t);

				// Count stats for the transaction t for the four charts
				String loggedInUserRole = authDAO.getUserRole(t.getLoggedInMID()).getUserRolesString();
				String secondaryUserRole;
				secondaryUserRole = (t.getSecondaryMID() == NO_SECONDARY_MID)
					? "0" : authDAO.getUserRole(t.getSecondaryMID()).getUserRolesString();

				chart1.put(loggedInUserRole, chart1.get(loggedInUserRole) + 1);
				chart2.put(secondaryUserRole, chart2.get(secondaryUserRole) + 1);

				DateFormat chart3DateKey = new SimpleDateFormat("MMM YYYY");
				DateFormat inputDateKey = new SimpleDateFormat("yy-MM-dd");
				Date chart3Date;
				try {
                    chart3Date = inputDateKey.parse(t.getTimeLogged().toString());
                } catch (ParseException e) {
				    continue;
                }

                String chart3Key = chart3DateKey.format(chart3Date);
				if (!chart3.containsKey(chart3Key)) {
				    chart3.put(chart3Key, 0);
                }
				chart3.put(chart3Key, chart3.get(chart3Key) + 1);

                if (!chart4.containsKey(t.getTransactionType().getCode())) {
                    chart4.put(t.getTransactionType().getCode(), 0);
                }
                chart4.put(t.getTransactionType().getCode(),
                           chart4.get(t.getTransactionType().getCode()) + 1);
			}

			allTransactions = filteredTransactions;
		} catch (ITrustException | NullPointerException | NumberFormatException e) {
		}
	}
%>

<form action="/iTrust/util/adminTransactionLog.jsp" method="post">
	<h1>Filter Transactions:</h1>
	<label>Logged-In User Field</label><br/>
	<select name="loggedInUserField">
        <% if (loggedInUserField != null && loggedInUserField.equals("")) { %>
            <option value="" selected="selected">N/A</option>
        <% } else { %>
            <option value="">N/A</option>
        <% } %>

        <%
            String selected;
            for (Role eth : Role.values()) {
                selected = (eth.getUserRolesString().equals(loggedInUserField)) ? "selected=selected" : "";
        %>
        <option value="<%= eth.getUserRolesString()%>" <%= StringEscapeUtils.escapeHtml("" + (selected)) %>><%= StringEscapeUtils.escapeHtml("" + (eth.getUserRolesString())) %></option>
        <% } %>
    </select><br/>


	<label>Secondary User Field</label><br/>
	<select name="secondaryUserField">
        <% if (secondaryUserField != null && secondaryUserField.equals("")) { %>
            <option value="" selected="selected">N/A</option>
        <% } else { %>
            <option value="">N/A</option>
        <% } %>

        <%
            String selected2;
            for (Role eth : Role.values()) {
				selected2 = (eth.getUserRolesString().equals(secondaryUserField)) ? "selected=selected" : "";
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
        <option value="-1">N/A</option>
		<%
			String selected3;
			for (TransactionType transType: TransactionType.values()) {
				selected3 = (transType.getCode() == transactionType) ? "selected=selected" : "";
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

<% if (!showView && showSummary) { %>
    <h1>Graph Summaries</h1>
	<div id="container1">
	</div>

    <div id="container2">
    </div>

    <div id="container3">
    </div>

	<script type="text/javascript">
		$('#container1').highcharts({
			chart: { type: 'bar' },
    		title: { text: "Transaction Summary" },
    		xAxis: { categories: <%= new JSONArray(chart1.keySet().toString()).toString() %> },
    		yAxis: {
    			title: { text: "Number of Transactions" }
    		},
    		series: [
    		    {
				    name: "Number of Transactions Per Logged In User Role",
				    data: <%= chart1.values().toString() %>
			    },
			    {
			        name: "Number of Transactions Per Secondary User Role",
			        data: <%= chart2.values().toString() %>
			    }
			]
		});
	</script>

    <script type="text/javascript">
		$('#container2').highcharts({
			chart: { type: 'bar' },
    		title: { text: "Transaction Summary" },
    		xAxis: { categories: <%= new JSONArray(chart3.keySet().toString()).toString() %> },
    		yAxis: {
    			title: { text: "Number of Transactions" }
    		},
    		series: [
    		    {
				    name: "Number of Transactions Per Date",
				    data: <%= chart3.values().toString() %>
			    }
			]
		});
	</script>

    <script type="text/javascript">
		$('#container3').highcharts({
			chart: { type: 'bar' },
    		title: { text: "Transaction Summary" },
    		xAxis: { categories: <%= new JSONArray(chart4.keySet().toString()).toString() %> },
    		yAxis: {
    			title: { text: "Number of Transactions" }
    		},
    		series: [
    		    {
				    name: "Number of Transactions Per Transaction Type",
				    data: <%= chart4.values().toString() %>
			    }
			]
		});
	</script>
<% } else { %>
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
		<tr class="transaction_row">
			<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionID())) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + (t.getTimeLogged())) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().name())) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().getCode())) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().getDescription())) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + (t.getLoggedInMID())) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + (t.getSecondaryMID())) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + (t.getAddedInfo())) %></td>
		</tr>
        <% } %>
	</table>
<% } %>
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

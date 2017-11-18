<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPrescriptionRenewalNeedsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean" %>
<%@page import="edu.ncsu.csc.itrust.beans.PrescriptionBean" %>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean" %>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO" %>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO" %>
<%@ page import="com.google.common.collect.ImmutableList" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.util.ArrayList" %>

<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Patient Visualization";


    List<String> attributes = ImmutableList.of("age", "number of visits", "number of appointments", "icState",
                                               "CauseOfDeath", "BloodType", "Religion", "Language");

    String[] selectedAttributes = request.getParameterValues("attribute");

    JSONArray chartData = new JSONArray();

    if (selectedAttributes != null) {
        int i = 0;
        for (String attribute : selectedAttributes) {
            // Call DAO to get all patients by attribute (use SQL to group by, order by, etc)
            JSONObject attr = new JSONObject();
            // Add quotes around title so that when we remove the quotes later, this is still considered a string.
            attr.put("name", "'Chart Name: " + attribute + "'");
            attr.put("data", new int[]{1 + i, 2 + i, 3 + i});

            chartData.put(attr);
            i += 1;
        }
    }
    String highchartsChartData = chartData.toString().replace("\"", "");

    String inputType = request.getParameter("histogram") != null && request.getParameter("histogram").equals("true") ? "radio": "checkbox";
%>

<%@include file="/header.jsp"%>

<div>
    <form method="post" action="/iTrust/auth/hcp/viewPatientVisualization.jsp">
        <%
            for (String attr : attributes) {
                %>
                    <div>
                        <input type="<%= inputType %>" id="<%= attr %>" name="attribute" value="<%= attr %>">
                        <label for="<%= attr %>"><%= attr %></label>
                    </div>
                <%
            }
        %>
        <button type="submit">View visualization</button>
    </form>

    <div id="container">

    </div>

    <script src="/iTrust/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="/iTrust/js/highcharts.js" type="text/javascript"></script>
    <script src="/iTrust/js/highcharts-more.js" type="text/javascript"></script>

    <% if (selectedAttributes != null && selectedAttributes.length != 0) { %>
        <script type="text/javascript">
            $("#container").highcharts({
                chart: { type: 'line' },
                title: { text: "Transaction Summary" },
                yAxis: {
                    title: { text: "Number of Transactions" }
                },
                series: <%= highchartsChartData %>,
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle'
                }
            });
        </script>
    <% } %>
</div>

<%@include file="/footer.jsp"%>

<script>


</script>
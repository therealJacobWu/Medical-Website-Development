<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="com.google.common.collect.ImmutableList"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Patient Visualization";


    List<String> attributes = ImmutableList.of("age", "number of visits", "number of appointments", "icState",
                                               "CauseOfDeath", "BloodType", "Religion", "Language");

    String[] selectedAttributes = request.getParameterValues("attribute");

    JSONArray chartData = new JSONArray();


    if (request.getParameter("histogram") != null && request.getParameter("histogram").equals("true")) {
        Map<String, Object> histogramConstruct = new HashMap<String, Object>();
        histogramConstruct.put("type", "'histogram'");
        //histogramConstruct.put("xAxis", 1);
        //histogramConstruct.put("yAxis", 1);
        histogramConstruct.put("baseSeries", 1);
        chartData.put(new JSONObject(histogramConstruct));
    }

    if (selectedAttributes != null) {
        int i = 0;
        for (String attribute : selectedAttributes) {
            // Call DAO to get all patients by attribute (use SQL to group by, order by, etc)
            JSONObject attr = new JSONObject();
            List<Integer> attributeData = new ArrayList<Integer>();
            // TODO based on attribute, get the data
            if (attribute.equals("age")) {
               attributeData = prodDAO.getPatientDAO().getAllPatientAges();
            }


            // Add quotes around title so that when we remove the quotes later, this is still considered a string.
            attr.put("name", "'Chart Name: " + attribute + "'");
            attr.put("data", attributeData.toArray());

            chartData.put(attr);
            i += 1;
        }
    }
    String highchartsChartData = chartData.toString().replace("\"", "");

    String inputType = request.getParameter("histogram") != null && request.getParameter("histogram").equals("true") ? "radio": "checkbox";
%>

<%@include file="/header.jsp"%>

<div>
    <form method="post">
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
    <!--<script src="/iTrust/js/highcharts.js" type="text/javascript"></script>
    <script src="/iTrust/js/highcharts-more.js" type="text/javascript"></script>
    <script src="/iTrust/js/histogram-bellcurve.js" type="text/javascript"></script>-->
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/histogram-bellcurve.js"></script>

    <% if (selectedAttributes != null && selectedAttributes.length != 0) { %>

        <% if (request.getParameter("histogram") != null && request.getParameter("histogram").equals("true")) { %>
            <script type="text/javascript">
                $("#container").highcharts({
                    title: { text: 'Distribution of <%= selectedAttributes[0] %>' },
                    series: <%= highchartsChartData %>
                });
            </script>
        <% } else { %>
            <script type="text/javascript">
                $("#container").highcharts({
                    chart: { type: 'line' },
                    title: { text: "Line chart visualization" },
                    yAxis: {
                        title: { text: "Something something" }
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
    <% } %>
</div>

<%@include file="/footer.jsp"%>

<script>


</script>
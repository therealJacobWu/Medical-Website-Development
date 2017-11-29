<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="com.google.common.collect.ImmutableList"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject" %>
<%@ page import="java.util.*" %>

<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Patient Visualization";

    List<String> attributes = ImmutableList.of("age", "number of visits", "number of appointments", "icState",
                                               "CauseOfDeath", "BloodType", "Religion", "Language");
    String inputType = request.getParameter("histogram") != null && request.getParameter("histogram").equals("true") ? "radio": "checkbox";

    String[] selectedAttributes = request.getParameterValues("attribute");
    String highchartsChartData = null;
    String xAxisTitle = "";
    String yAxisTitle = "";

    if (selectedAttributes != null && selectedAttributes.length > 2) {
        %>
        <div class="error">
            You can only select two attributes!
        </div>
        <%
    } else {
        JSONArray chartData = new JSONArray();

        boolean isHistogram = request.getParameter("histogram") != null && request.getParameter("histogram").equals("true");
        if (isHistogram) {
            // Histogram needs an extra JSON object
            Map<String, Object> histogramConstruct = new HashMap<String, Object>();
            histogramConstruct.put("type", "'histogram'");
            //histogramConstruct.put("xAxis", 1);
            //histogramConstruct.put("yAxis", 1);
            histogramConstruct.put("baseSeries", 1);
            chartData.put(new JSONObject(histogramConstruct));
        }

        Map<String, Integer> attribute1Dataset = null;
        Map<String, Integer> attribute2Dataset = null;

        if (selectedAttributes != null) {
            for (String attribute : selectedAttributes) {
                JSONObject attr = new JSONObject();
                Map<String, Integer> attributeData = new HashMap<>();
                if (attribute.equals("age")) {
                    attributeData = prodDAO.getPatientDAO().getAllPatientAges();
                } else if (attribute.equals("number of visits")) {
                    attributeData = prodDAO.getPatientDAO().getAllPatientVisits();
                }

                if (attribute1Dataset == null) {
                    attribute1Dataset = attributeData;
                    xAxisTitle = attribute;
                } else if (attribute2Dataset == null) {
                    attribute2Dataset = attributeData;
                    yAxisTitle = attribute;
                }

                attr.put("name", "'Chart Name: " + attribute + "'");
                attr.put("data", attributeData.values());
                if (isHistogram) {
                    attr.put("visible", "false");
                }

                chartData.put(attr);
            }
        }

        if (isHistogram) {
            highchartsChartData = chartData.toString().replace("\"", "");
        } else if (attribute1Dataset != null && attribute2Dataset != null) {
            List<List<Integer>> scatterData = new ArrayList<>();

            for (Map.Entry<String, Integer> entry1 : attribute1Dataset.entrySet()) {
                final String MID = entry1.getKey();

                if (attribute2Dataset.containsKey(MID)) {
                    scatterData.add(new ArrayList<>(Arrays.asList(entry1.getValue(), attribute2Dataset.get(MID))));
                }
            }

            highchartsChartData = scatterData.toString();
        }
    }
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
    <% } else if (highchartsChartData != null) { %>
            <script type="text/javascript">
                $("#container").highcharts({
                    chart: { type: 'scatter' },
                    title: { text: "<%= yAxisTitle %> vs <%= xAxisTitle %>" },
                    xAxis: {
                        title: { text: "<%= xAxisTitle %>" }
                    },
                    yAxis: {
                        title: { text: "<%= yAxisTitle %>" }
                    },
                    series: [{
                        data: <%= highchartsChartData %>
                    }]
                });
            </script>
        <% } %>
    <% } %>
</div>

<%@include file="/footer.jsp"%>

<script>


</script>
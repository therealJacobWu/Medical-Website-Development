<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="com.google.common.collect.ImmutableList"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject" %>
<%@ page import="java.util.*" %>
<%@ page import="edu.ncsu.csc.itrust.dao.mysql.PatientMetricVisualizationDAO" %>

<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Patient Visualization";

    List<String> attributes = ImmutableList.of("age", "number of visits", "icState",
                                               "CauseOfDeath", "BloodType", "Religion", "Language");

    String[] selectedAttributes = request.getParameterValues("attribute");
    List<String> attributesDataString = new ArrayList<>();
    PatientMetricVisualizationDAO pmvDao = prodDAO.getPatientMetricVisualizationDAO();

    if (selectedAttributes != null) {
        for (String attribute : selectedAttributes) {
            JSONArray chartData = new JSONArray();

            Map<String, Integer> attributeData = new HashMap<>();
            if (attribute.equals("age")) {
                attributeData = pmvDao.getAllPatientAges();
            } else if (attribute.equals("number of visits")) {
                attributeData = pmvDao.getAllPatientVisits();
            } else if (attribute.equals("icState")) {
                attributeData = pmvDao.getAllPatientStates();
            } else if (attribute.equals("CauseOfDeath")) {
                attributeData = pmvDao.getAllPatientDeath();
            } else if (attribute.equals("Language")) {
                attributeData = pmvDao.getAllPatientsLanguage();
            } else if (attribute.equals("BloodType")) {
                attributeData = pmvDao.getAllPatientsBlood();
            } else if (attribute.equals("Religion")) {
                attributeData = pmvDao.getAllPatientsReligion();
            }

            for (Map.Entry<String, Integer> row : attributeData.entrySet()) {
                JSONObject attr = new JSONObject();
                attr.put("name", "'" + row.getKey() + "'");
                attr.put("data", Arrays.asList(row.getValue()));
                chartData.put(attr);
            }
            attributesDataString.add(chartData.toString().replace("\"", ""));
        }
    }
%>

<%@include file="/header.jsp"%>

<div>
    <form method="post" id="viewPatientVisualization">
        <%
            for (String attr : attributes) {
                %>
                    <div>
                        <input type="checkbox" id="<%= attr %>" name="attribute" value="<%= attr %>">
                        <label for="<%= attr %>"><%= attr %></label>
                    </div>
                <%
            }
        %>
        <input type="submit" id="view_visualize">View visualization</input>
    </form>


    <script src="/iTrust/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/histogram-bellcurve.js"></script>

    <% if (selectedAttributes != null && selectedAttributes.length != 0) { %>
        <div id="charts">
        </div>
        <% for (int i = 0; i < attributesDataString.size(); i++) { %>
            <script type="text/javascript">
                $('#charts').append("<div id='container<%= i %>'></div>");
                $("#container<%= i %>").highcharts({
                    chart: { type: 'column' },
                    title: { text: 'Distribution of <%= selectedAttributes[i] %>' },
                    yAxis: { title: { text: 'Count' } },
                    xAxis: { title: { text: '<%= selectedAttributes[i] %>' } },
                    plotOptions: {
                        series: {
                            cursor: 'pointer',
                            point: {
                                events: {
                                    click: function() {
                                        debugger;
                                        window.location = "/iTrust/auth/hcp/viewPatientAttribute.jsp?attribute=" + this.series.xAxis.userOptions.title.text + "&value=" + this.series.userOptions.name;
                                    }
                                }
                            }
                        }
                    },
                    series: <%= attributesDataString.get(i) %>
                });
            </script>
        <% }
    } %>
</div>

<%@include file="/footer.jsp"%>

<script>


</script>
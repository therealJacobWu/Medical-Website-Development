<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.beans.DiagnosisStatisticsBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewDiagnosisStatisticsAction"%>
<%@page import="edu.ncsu.csc.itrust.charts.DiagnosisTrendData" %>
<%@ page import="org.json.JSONArray" %>


<script src="/iTrust/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="/iTrust/js/highcharts.js" type="text/javascript"></script>
<script src="/iTrust/js/highcharts-more.js" type="text/javascript"></script>

<%
    List<String> xAxisLabels = new ArrayList<>();
    List<Long> regionData = new ArrayList<>();
    List<Long> stateData = new ArrayList<>();
    List<Long> globalData = new ArrayList<>();

    for (int weekNumb = dsBeans.size()-1; weekNumb >= 0 ; weekNumb--) {
        xAxisLabels.add("week - "+ (weekNumb + 1));
        regionData.add(dsBeans.get(weekNumb).getRegionStats());
        stateData.add(dsBeans.get(weekNumb).getStateStats());
        globalData.add(dsBeans.get(weekNumb).getGlobalStats());
    }
%>


<div id="container1">
</div>
<script type="text/javascript">
    $('#container1').highcharts({
        chart: { type: 'bar' },
        title: { text: "Diagnoses Summary for previous 8 weeks" },
        xAxis: { categories: <%= new JSONArray(xAxisLabels.toString().toString())%> },
        yAxis: {
            title: { text: "Number of Diagnoses" }
        },
        series: [
            {
                name: "Diagnoses in region",
                data: <%=regionData.toString()%>
            },
            {
                name: "Diagnoses in state",
                data: <%=stateData.toString()%>
            },
            {
                name: "All Diagnoses",
                data: <%=globalData.toString()%>
            }
        ]
    });
</script>


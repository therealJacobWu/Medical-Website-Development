<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="com.google.common.collect.ImmutableList"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject" %>
<%@ page import="java.util.*" %>
<%@ page import="edu.ncsu.csc.itrust.dao.mysql.PatientMetricVisualizationDAO" %>
<%@ page import="edu.ncsu.csc.itrust.beans.PatientBean" %>

<%@include file="/global.jsp"%>

<%
    List<PatientBean> matchingPatients = prodDAO.getPatientMetricVisualizationDAO()
                                                .getAllPatientWithAttr(request.getParameter("attribute"),
                                                                        request.getParameter("value"));
%>


<h1>Showing all patients that have <%= request.getParameter("attribute") %> with value <%= request.getParameter("value") %></h1>
<table width="99%">
    <tr>
        <th>MID</th>
    </tr>
    <% for (PatientBean b : matchingPatients) { %>
        <tr>
            <td><%= b.getMID() %></td>
        </tr>
    <% } %>
</table>
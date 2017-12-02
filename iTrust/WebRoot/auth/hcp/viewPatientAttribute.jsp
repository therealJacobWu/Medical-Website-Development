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
    pageTitle = "iTrust - View Patient Attributes";
    List<PatientBean> matchingPatients = prodDAO.getPatientMetricVisualizationDAO()
                                                .getAllPatientWithAttr(request.getParameter("attribute"),
                                                                        request.getParameter("value"));
%>

<%@include file="/header.jsp"%>

<h1>Showing all patients that have <%= request.getParameter("attribute") %> with value <%= request.getParameter("value") %></h1>
<table width="99%" class="fTable">
    <tr>
        <th>MID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Gender</th>
    </tr>
    <% for (PatientBean b : matchingPatients) { %>
        <tr>
            <td><%= b.getMID() %></td>
            <td><%= b.getFirstName() %> <%= b.getLastName() %></td>
            <td><%= b.getEmail() %></td>
            <td><%= b.getGender()%></td>
        </tr>
    <% } %>
</table>

<%@include file="/footer.jsp"%>

<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@ page import="edu.ncsu.csc.itrust.action.base.ViewCauseOfDeathStatisticsAction" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.ncsu.csc.itrust.enums.Gender" %>
<%@ page import="java.util.Collections" %>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View Cause Of Death";

%>

<%@include file="/header.jsp" %>

<%
    //log the page view
    loggingAction.logEvent(TransactionType.DEATH_TRENDS_VIEW, loggedInMID.longValue(), 0, "");

    ViewCauseOfDeathStatisticsAction deathStatistics = new ViewCauseOfDeathStatisticsAction(prodDAO);

    //get form data
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String gender = request.getParameter("gender");

    List<String> causeOfDeath = Collections.EMPTY_LIST;



    //try to get the statistics. If there's an error, print it. If null is returned, it's the first page load
    if (startDate != null && endDate != null) {
        try {
            causeOfDeath = deathStatistics.getCauseOfDeathStatistics(startDate, endDate, loggedInMID.longValue(), gender);
        } catch (FormValidationException e) {
            e.printHTML(pageContext.getOut());
        }
    }

    if (startDate == null)
        startDate = "";
    if (endDate == null)
        endDate = "";
    if (gender == null)
        gender = "";
%>
<br />
<form action= "viewCauseOfDeath.jsp" method="post" id="formMain">
    <table class="fTable" align="center" id="causeOfDeathStatisticsSelectionTable">
        <tr>
            <th colspan="4">Cause Of Death Statistics</th>
        </tr>
        <tr class="subHeader">
            <td>Gender:</td>
            <td colspan="3">
                <select name="gender" style="font-size:10" >
                    <option value="All" selected>All</option>
                    <%for(Gender g : Gender.values()) { %>
                    <option value="<%=g.toString()%>"><%=g.toString()%></option>
                    <%}%>
                </select>
            </td>
        </tr>
        <tr class="subHeader">
            <td>Start Date:</td>
            <td>
                <input name="startDate" value="<%= StringEscapeUtils.escapeHtml("" + (startDate)) %>" size="10">
                <input type=button value="Select Date" onclick="displayDatePicker('startDate');">
            </td>
            <td>End Date:</td>
            <td>
                <input name="endDate" value="<%= StringEscapeUtils.escapeHtml("" + (endDate)) %>" size="10">
                <input type=button value="Select Date" onclick="displayDatePicker('endDate');">
            </td>
        </tr>
        <tr>
            <td colspan="4" style="text-align: center;"><input type="submit" id="select" value="View Statistics">
            </td>
        </tr>
    </table>

</form>

<br />

<% if (!causeOfDeath.isEmpty()) { %>



<table class="fTable" align="center" id="causeOfDeathStatisticsTable">
    <tr>
        <th>Cause Of Death</th>
    </tr>
    <%for(String cause : causeOfDeath) { %>
    <tr style="text-align:center;">
        <td><%= cause %></td>
    </tr>
    <%}%>

</table>

<br />

<% } %>
<br />
<br />
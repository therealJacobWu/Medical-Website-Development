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


<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - My Patients with Potential Prescription-Renewal Needs";


    List<String> attributes = ImmutableList.of("age", "number of visits", "number of appointments", "icState",
                                               "CauseOfDeath", "BloodType", "Religion", "Language");

    String[] selectedAttributes = request.getParameterValues("attributes");

    for (String attribute : selectedAttributes) {
        // Call DAO to get all patients by attribute (use SQL to group by, order by, etc)
    }
%>

<%@include file="/header.jsp"%>

<div>
    <form method="post" action="/iTrust/auth/hcp/viewPatientVisualization.jsp">
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
        <button type="submit">View visualization</button>
    </form>
</div>

<%@include file="/footer.jsp"%>

<script>


</script>
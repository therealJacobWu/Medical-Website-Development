<%@ page import="edu.ncsu.csc.itrust.action.SendRemindersAction" %>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - Send Appointment Reminders";
%>

<%@include file="/header.jsp" %>

<div align=center>
    <%
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            SendRemindersAction action = new SendRemindersAction(prodDAO);

            if (request.getParameter("number") != null) {
                try {
                    int n = Integer.parseInt(request.getParameter("number"));
                    action.sendReminders(n);
    %>
                    <h1>Sent reminders successfully!</h1>
    <%
                } catch (NumberFormatException e) {

                }
            }


        }
    %>
    <form method="post" action="sendReminderMessages.jsp">
        <p>Use this form to enter an integer to send reminder messages to each patients with any upcoming appointment within the next n days from today.</p>

        <label>n days</label>
        <input type="number" name="number"/>
        <input type="submit" value="Send Appointment Reminders"><br/>
    </form>
</div>
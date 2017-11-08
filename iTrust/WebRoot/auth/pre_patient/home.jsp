<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - Pre PatientHome";
    loggingAction.logEvent(TransactionType.HOME_VIEW, loggedInMID, 0, "Pre-Registered Patient Home");
%>

<%@include file="/header.jsp" %>

<div style="text-align: center;">
    <h2>Welcome Pre-Registered Patient <%= StringEscapeUtils.escapeHtml("" + (userName )) %>!</h2>
</div>

<%@include file="/footer.jsp" %>

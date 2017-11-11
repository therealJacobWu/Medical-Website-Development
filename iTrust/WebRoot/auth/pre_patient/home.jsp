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

    <p>
        You have successfully pre-registered. However, you need an HCP to activate your account in order
        to fully use the iTrust system.

        In the meantime, please wait.
    </p>
</div>

<%@include file="/footer.jsp" %>

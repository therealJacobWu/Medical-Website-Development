<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.ViewMyMessagesAction"%>

<%@page import="edu.ncsu.csc.itrust.beans.MessageBean"%>
<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View My Reminder Messages ";
%>

<%@include file="/header.jsp" %>

<div align=center>
    <h2>My Reminder Messages</h2>

    <%
        ViewMyMessagesAction action = new ViewMyMessagesAction(prodDAO, loggedInMID.longValue());
        List<MessageBean> messages = null;

        messages = action.getAllReminderMessages();

        session.setAttribute("reminderMessages", messages);
    %>

    <br />

    <%if(messages.size() > 0) { %>
    <table class="fancyTable">
        <tr>
            <th>Sender</th>
            <th>Subject</th>
            <th>Received</th>
            <th></th>
        </tr>
        <%		int index = 0;
            for(MessageBean message : messages) {
                if(message.getRead() == 0) {%>
        <tr style="font-weight: bold;" <%=(index%2 == 1)?"class=\"alt\"":"" %>>
            <td><%= StringEscapeUtils.escapeHtml("" + ( action.getName(message.getFrom()) )) %></td>
            <td><%= StringEscapeUtils.escapeHtml("" + ( message.getSubject() )) %></td>
            <td><%= StringEscapeUtils.escapeHtml("" + ( message.getSentDate() )) %></td>
            <td><a href="messageOutbox.jsp?reminder_msg=<%= StringEscapeUtils.escapeHtml("" + ( index )) %>">Read</a></td>
        </tr>
        <% 			   } else { %>
        <tr <%=(index%2 == 1)?"class=\"alt\"":"" %>>
            <td><%= StringEscapeUtils.escapeHtml("" + ( action.getName(message.getFrom()) )) %></td>
            <td><%= StringEscapeUtils.escapeHtml("" + ( message.getSubject() )) %></td>
            <td><%= StringEscapeUtils.escapeHtml("" + ( message.getSentDate() )) %></td>
            <td><a href="messageOutbox.jsp?reminder_msg=<%= StringEscapeUtils.escapeHtml("" + ( index )) %>">Read</a></td>
        </tr>
        <% 			  } %>
        <%			index ++; %>
        <%		} %>
    </table>
    <%	} else { %>
    <div>
        <i>No reminder messages to read.</i>
    </div>
    <%	} %>
    <br />
</div>

<%@include file="/footer.jsp" %>

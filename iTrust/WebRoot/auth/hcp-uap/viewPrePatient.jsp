<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@ page import="edu.ncsu.csc.itrust.beans.HealthRecord" %>
<%@ page import="java.util.Map" %>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View All Pre-registered Patients";
%>

<%@include file="/header.jsp" %>

<%

    List<PatientBean> prePatients = prodDAO.getPatientDAO().getPrePatient();

    /*Map<String, String[]> paras = request.getParameterMap();
    for(Map.Entry<String, String[]> pair: paras.entrySet()) {
        System.out.println("key: " +  pair.getKey().toString());
        for(String values : pair.getValue()) {
            System.out.println("values: " + values);
        }
    }*/

    //Process clicked "Activate" buttons
    if (request.getParameter("ACT") != null) {
        try {
            int index = Integer.parseInt(request.getParameter("ACT"));
            long mid = prePatients.get(index).getMID();
            prodDAO.getPatientDAO().activatePrePatient(mid);
            loggingAction.logEvent(TransactionType.ACTIVATE_PREREGISTERED_PATIENT, loggedInMID, mid, "");

        } catch (NumberFormatException e){
            //Should not happen
        }
    }

    //Process clicked "Deactivate" buttons
    if (request.getParameter("DEA") != null) {
        try {
            int index = Integer.parseInt(request.getParameter("DEA"));
            long mid = prePatients.get(index).getMID();
            prodDAO.getPatientDAO().deactivatePrePatient(mid);
            loggingAction.logEvent(TransactionType.DEACTIVATE_PREREGISTERED_PATIENT, loggedInMID, mid, "");
        } catch (NumberFormatException e){
            //Should not happen
        }
    }

    prePatients = prodDAO.getPatientDAO().getPrePatient();
    List<HealthRecord> prePatientRecords = new ArrayList<>();
    for (int i = 0; i < prePatients.size(); i++) {
        long prePatientId = prePatients.get(i).getMID();
        List<HealthRecord> records = prodDAO.getHealthRecordsDAO().getAllHealthRecords(prePatientId);
        if (records.size() == 0) {
            prePatientRecords.add(null);
        } else {
            HealthRecord lastRecord = records.get(records.size() - 1);
            prePatientRecords.add(lastRecord);
        }
    }


%>
<script src="/iTrust/DataTables/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script type="text/javascript">
    jQuery.fn.dataTableExt.oSort['lname-asc']  = function(x,y) {
        var a = x.split(" ");
        var b = y.split(" ");
        return ((a[1] < b[1]) ? -1 : ((a[1] > b[1]) ?  1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['lname-desc']  = function(x,y) {
        var a = x.split(" ");
        var b = y.split(" ");
        return ((a[1] < b[1]) ? 1 : ((a[1] > b[1]) ?  -1 : 0));
    };
</script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#patientList").dataTable( {
            "aaColumns": [ [2,'dsc'] ],
            "aoColumns": [ { "sType": "lname" }, null, null],
            "bStateSave": true,
            "sPaginationType": "full_numbers"
        });
    });
</script>
<style type="text/css" title="currentStyle">
    @import "/iTrust/DataTables/media/css/demo_table.css";
</style>

<br />
<h2>Pre-registered Patients</h2>
<form action="viewPrePatient.jsp" method="post">
    <table class="display fTable" id="patientList" align="center">

        <thead>


        <tr class="">
            <th>Patient</th>
            <th>Address</th>
            <th>Last Visit</th>
            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <%
            List<PatientBean> patients = new ArrayList<>();
            int index = 0;
            for (PatientBean prePatient : prePatients) {
                patients.add(prePatient);
        %>
        <tr>
            <td >
                <a id="MID_<%= StringEscapeUtils.escapeHtml("" + (prePatient.getMID()))%>" href="editPHR.jsp?patient=<%= StringEscapeUtils.escapeHtml("" + (index)) %>">


                    <%= StringEscapeUtils.escapeHtml("" + (prePatient.getFirstName() + " " + prePatient.getLastName())) %>


                </a>
            </td>
            <td ><%= StringEscapeUtils.escapeHtml("" + (prePatient.getStreetAddress1() + " " + prePatient.getStreetAddress2())) %></td>
            <td >
                <%
                    if(prePatientRecords.get(index) != null && prePatientRecords.get(index).getVisitDate() != null) {
                %>
                <%= StringEscapeUtils.escapeHtml(prePatientRecords.get(index).getVisitDateStr()) %>
                <%
                    }
                %>
            </td>
            <td>
                <button id="ACT_<%= StringEscapeUtils.escapeHtml("" + (prePatient.getMID()))%>" type="submit" value="<%= StringEscapeUtils.escapeHtml("" + (index))%>"  name="ACT">Activate</button>
            </td>
            <td>
                <button id="DEA_<%= StringEscapeUtils.escapeHtml("" + (prePatient.getMID()))%>" type="submit" value="<%= StringEscapeUtils.escapeHtml("" + (index)) %>" name="DEA">Deactivate</button>
            </td>
        </tr>
        <%
                index ++;
            }
            session.setAttribute("patients", patients);
        %>
        </tbody>
    </table>
</form>
<br />
<br />

<%@include file="/footer.jsp" %>

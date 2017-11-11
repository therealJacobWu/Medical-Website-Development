<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.action.AddPrepatientAction"%>
<%@ page import="edu.ncsu.csc.itrust.beans.HealthRecord" %>
<%@ page import="edu.ncsu.csc.itrust.beans.PatientBean" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@include file="/global.jsp" %>

<%
    // Check required fields
    String fName = request.getParameter("firstName");
    if (fName != null && fName.equals("")) {
        response.sendRedirect("/iTrust/login.jsp?preRegisterError=A first name must be entered");
        return;
    }

    String lName = request.getParameter("lastName");
    if (lName != null && lName.equals("")) {
        response.sendRedirect("/iTrust/login.jsp?preRegisterError=A last name must be entered");
        return;
    }

    String email = request.getParameter("email");
    if (email != null && email.equals("")) {
        response.sendRedirect("/iTrust/login.jsp?preRegisterError=An email must be entered");
        return;
    }

    PatientBean existing = prodDAO.getPatientDAO().getPatient(email);
    if (existing != null) {
        response.sendRedirect("/iTrust/login.jsp?preRegisterError=A login already exists with that email.");
        return;
    }

    String password = request.getParameter("password");
    if (password != null && password.equals("")) {
        response.sendRedirect("/iTrust/login.jsp?preRegisterError=A password must be entered.");
        return;
    }

    String confirmPassword = request.getParameter("confirmPassword");
    if (confirmPassword != null && confirmPassword.equals("")) {
        response.sendRedirect("/iTrust/login.jsp?preRegisterError=A confirmation password must be entered.");
        return;
    }

    if (!password.equals(confirmPassword)) {
        response.sendRedirect("/iTrust/login.jsp?preRegisterError=Passwords do not match. Please try again.");
        return;
    }

    // Construct Patient
    PatientBean p = new BeanBuilder<PatientBean>().build(request.getParameterMap(), new PatientBean());
    // Add to database
    long userMID = new AddPrepatientAction(prodDAO).addPrepatient(p);

    HealthRecord hr = new HealthRecord();

    // Set date to be today
    hr.setOfficeVisitDateStr(p.getDateOfBirthStr());

    if (request.getParameter("height") != null && !request.getParameter("height").equals("")) {
        hr.setHeight(Double.parseDouble(request.getParameter("height")));
    }
    if (request.getParameter("weight") != null && !request.getParameter("weight").equals("")) {
        hr.setWeight(Double.parseDouble(request.getParameter("weight")));
    }

    if (request.getParameter("smoker") != null && request.getParameter("smoker").equals("true")) {
        hr.setSmoker(2);
    } else { // Default is no smoking
        hr.setSmoker(9);
    }

    hr.setPatientID(userMID);
    prodDAO.getHealthRecordsDAO().add(hr);

    // Log transaction
    loggingAction.logEvent(TransactionType.PATIENT_PREREGISTER, userMID, 0, "");

    response.sendRedirect("/iTrust/login.jsp?preRegisterError=You have pre-registered. Log in with your MID: " + userMID);
%>
package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ViewCauseOfDeathStatisticsAction {

    private PatientDAO patientDAO;

    public ViewCauseOfDeathStatisticsAction(DAOFactory factory) {
        this.patientDAO = factory.getPatientDAO();
    }

    public List<String> getCauseOfDeathStatistics(String startDate, String endDate, long hcpId, String gender)
        throws DBException, FormValidationException
    {
        Date lower;  //lower, which is parsed to startDate
        try {
            lower = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
        } catch (ParseException e) {
            throw new FormValidationException("Enter dates in MM/dd/yyyy");
        }

        Date upper;  //upper, which is parsed to endDate
        try {
            upper = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
        } catch (ParseException e) {
            throw new FormValidationException("Enter dates in MM/dd/yyyy");
        }

        List<String> causeOfDeath = Collections.emptyList();
        if (gender.toLowerCase().equals("all")) {
            causeOfDeath = patientDAO.getCauseOfDeath(hcpId, formatDate(lower), formatDate(upper));
        } else {
            String formattedGender = gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase();
            Gender actualGender = Gender.parse(formattedGender);
            causeOfDeath = patientDAO.getCauseOfDeath(actualGender, hcpId, formatDate(lower), formatDate(upper));
        }
        return causeOfDeath;
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}

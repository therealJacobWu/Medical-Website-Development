package edu.ncsu.csc.itrust.action;


import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.AddPatientValidator;

/**
 * Used for Add Patient page (addPatient.jsp). This just adds an empty patient, creates a random password for
 * that patient.
 *
 * Very similar to {@link AddOfficeVisitAction}
 *
 *
 */
public class AddPrepatientAction {
    private PatientDAO patientDAO;
    private AuthDAO authDAO;
    private long loggedInMID;

    /**
     * Just the factory and logged in MID
     *
     * @param factory
     */
    public AddPrepatientAction(DAOFactory factory) {
        this.patientDAO = factory.getPatientDAO();
        this.authDAO = factory.getAuthDAO();
    }

    /**
     * Creates a new pre-patient and returns the new MID.
     *
     * @param p
     * @return
     * @throws FormValidationException
     * @throws DBException
     */
    public long addPrepatient(PatientBean p) throws FormValidationException, DBException {
        new AddPatientValidator().validate(p);
        long newMID = patientDAO.addEmptyPatient();
        p.setMID(newMID);

        authDAO.addUser(newMID, Role.PRE_PATIENT, p.getPassword());
        patientDAO.editPatient(p);
        return newMID;
    }
}

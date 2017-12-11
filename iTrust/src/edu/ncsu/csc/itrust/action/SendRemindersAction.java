package edu.ncsu.csc.itrust.action;

import com.google.common.annotations.VisibleForTesting;
import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.EMailValidator;
import edu.ncsu.csc.itrust.validate.MessageValidator;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Handles sending appointment reminders to patients who have upcoming appointments bounded by a certain number of days
 */
public class SendRemindersAction {
    private EmailUtil emailer;
    private PatientDAO patientDAO;
    private PersonnelDAO personnelDAO;
    private MessageDAO messageDAO;
    private EMailValidator emailVal;
    private ApptDAO apptDAO;
    private final String REMINDER_MSG =
            "You have an appointment on %s with Dr. %s";
    private final String REMINDER_SBJ =
            "Reminder: upcoming appointment in %d day(s).";
    private final String REMINDER_SDR = "System Reminder";
    private final String REMINDER_EML_BDY =
            "You have received a new message from %s in iTrust. To view it, go to " +
            "\"http://localhost:8080/iTrust/auth/hcp/messageInbox.jsp" +
            "\" and log in to iTrust using your username and password.";

    /**
     * Sets up defaults
     *
     * @param factory The DAOFactory used to create the DAOs used in this action.
     */
    public SendRemindersAction(DAOFactory factory) {
        this.patientDAO = factory.getPatientDAO();
        this.personnelDAO = factory.getPersonnelDAO();
        this.apptDAO = factory.getApptDAO();
        this.emailer = new EmailUtil(factory);
        this.messageDAO = factory.getMessageDAO();
        this.emailVal = new EMailValidator();
    }

    private Timestamp endDate(Timestamp start, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(start.getTime()));
        calendar.add(Calendar.DAY_OF_WEEK, n);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * Send reminders for appointments that are happening within
     * n days from now.
     * @param n
     * @throws ITrustException
     * @throws SQLException
     * @throws FormValidationException
     */
    public void sendReminders(int n) throws ITrustException, SQLException, FormValidationException{
        Timestamp start = new Timestamp(System.currentTimeMillis());
        sendReminders(n, start);
    }

    /**
     * Sender reminders that are happening from within n days of start.
     * @param n
     * @param start
     * @throws ITrustException
     * @throws SQLException
     * @throws FormValidationException
     */
    @VisibleForTesting
    public void sendReminders(int n, Timestamp start) throws ITrustException, SQLException, FormValidationException {
        Timestamp end = endDate(start, n);
        List<ApptBean> appts = apptDAO.getAllAppointmentsWithinRange(start, end);
        long days;
        for (ApptBean appt : appts) {
            // calculate the number of days until the appointment
            days = (appt.getDate().getTime () / (1000 * 3600 * 24)) -
                    (start.getTime () / (1000 * 3600 * 24));
            MessageBean mBean = initMessageBean(appt,days);
            messageDAO.addReminderMessage(mBean);
            sendReminderEmail(mBean);
        }
    }

    private MessageBean initMessageBean(ApptBean appt, long days) throws ITrustException {
        MessageBean message = new MessageBean();
        String dateString = new SimpleDateFormat("HH.mm.ss, yyyy.MM.dd")
                .format( new Date(appt.getDate().getTime()));

        message.setBody(String.format(REMINDER_MSG, dateString, personnelDAO.getName(appt.getHcp())));
        message.setSubject(String.format(REMINDER_SBJ,days));

        message.setRead(0);
        message.setTo(appt.getPatient());
        return message;
    }

    private void sendReminderEmail(MessageBean mBean)throws ITrustException, SQLException, FormValidationException {
        PatientBean receiver = patientDAO.getPatient(mBean.getTo());
        Email email = new Email();
        email.setFrom("noreply@itrust.com");
        email.setBody(String.format(REMINDER_EML_BDY, REMINDER_SDR));
        email.setSubject(String.format("A new message from %s", REMINDER_SDR));
        emailVal.validate(mBean);
        List<String> toList = new ArrayList<String>();
        toList.add(receiver.getEmail());
        email.setToList(toList);
        emailer.sendEmail(email);
    }
}

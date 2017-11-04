package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.SendRemindersAction;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by thechaseduncan on 11/3/17.
 */
public class SendRemindersActionTest extends TestCase {
    private DAOFactory factory;
    private ApptDAO apptDAO;
    private MessageDAO messageDAO;
    private PatientDAO patientDAO;
    private SendRemindersAction srAction;
    private TestDataGenerator gen;

    private PatientBean pb;
    private ApptBean a1;
    private ApptBean a2;
    private ApptBean a3;
    private Timestamp beforeA1Date;
    private Timestamp a1Date;
    private Timestamp a2Date;
    private Timestamp a3Date;

    long doctorMID = 9000000000L;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.appointmentType();


        this.factory = TestDAOFactory.getTestInstance();
        this.messageDAO = new MessageDAO(this.factory);
        this.apptDAO = factory.getApptDAO();
        this.patientDAO = factory.getPatientDAO();
        this.srAction = new SendRemindersAction(factory);
        this.pb = new PatientBean();
        Date date = new Date();

        // Round down to the nearest second since SQL only stores to that precision
        long dateMillis = date.getTime() / 1000 * 1000;
        beforeA1Date = new Timestamp(dateMillis - 1000 * 60);
        a1Date = new Timestamp(dateMillis);
        a2Date = new Timestamp(dateMillis + 1000 * 3600 * 24 * 15);
        a3Date = new Timestamp(dateMillis + 1000 * 3600 * 24 * 45);

        long newMID = patientDAO.addEmptyPatient();
        pb.setMID(newMID);
        pb.setEmail("pb@pb.pb");
        patientDAO.editPatient(pb, doctorMID);

        a1 = new ApptBean();
        a1.setDate(a1Date);
        a1.setApptType("Ultrasound");
        a1.setHcp(doctorMID);
        a1.setPatient(newMID);

        a2 = new ApptBean();
        a2.setDate(a2Date);    //15 minutes later
        a2.setApptType("Ultrasound");
        a2.setHcp(doctorMID);
        a2.setPatient(newMID);

        a3 = new ApptBean();
        a3.setDate(a3Date);    //45 minutes later
        a3.setApptType("Ultrasound");
        a3.setHcp(doctorMID);
        a3.setPatient(newMID);
    }

    public void testSendReminders() throws ITrustException, SQLException, FormValidationException {

        apptDAO.scheduleAppt(a1);
        this.srAction.sendReminders(3, beforeA1Date);
        List<MessageBean> mbList = this.messageDAO.getAllReminderMessages();

        assertEquals(1, mbList.size());
    }
}

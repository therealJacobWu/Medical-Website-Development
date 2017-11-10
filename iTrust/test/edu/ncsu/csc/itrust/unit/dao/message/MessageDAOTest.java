package edu.ncsu.csc.itrust.unit.dao.message;

import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MessageDAOTest extends TestCase {
    private DAOFactory factory = TestDAOFactory.getTestInstance();
    private MessageDAO messageDAO = factory.getMessageDAO();

    private MessageBean m1;
    private MessageBean m2;

    private final long mid = 9000000000L;

    @Override
    protected void setUp() throws Exception {
        TestDataGenerator gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.messages();

        // month is zero-based
        GregorianCalendar cal1 = new GregorianCalendar(2010, 1, 2, 13, 3, 0);
        GregorianCalendar cal2 = new GregorianCalendar(2010, 0, 28, 17, 58, 0);

        m1 = new MessageBean();
        m1.setMessageId(6);
        m1.setTo(mid);
        m1.setSentDate(new Timestamp(cal1.getTimeInMillis()));
        //m1.setBody("I would like to schedule an appointment before my throat gets any worse. Thanks!");

        m2 = new MessageBean();
        m2.setMessageId(8);
        m2.setFrom(mid);
        m2.setSentDate(new Timestamp(cal2.getTimeInMillis()));
        //m2.setBody("Hey, I checked on that!");
    }

    public void testGetMessagesFor() throws Exception {
        List<MessageBean> results = messageDAO.getMessagesFor(mid);
        assertEquals(results.size(), 14);

        assertEquals(results.get(0).getSentDate(), m1.getSentDate());
    }

    public void testGetMessagesTimeAscending() throws Exception {
        List<MessageBean> results = messageDAO.getMessagesTimeAscending(mid);
        assertEquals(results.size(), 14);

        assertEquals(results.get(13).getSentDate(), m1.getSentDate());
    }

    public void testGetMessagesFrom() throws Exception {
        List<MessageBean> results = messageDAO.getMessagesFrom(mid);
        assertEquals(results.size(),
                2);

        assertEquals(results.get(0).getSentDate(), m2.getSentDate());
    }

    public void testGetMessagesFromTimeAscending() throws Exception {
        List<MessageBean> results = messageDAO.getMessagesFromTimeAscending(mid);
        assertEquals(results.size(), 2);

        assertEquals(results.get(1).getSentDate(), m2.getSentDate());
    }
}

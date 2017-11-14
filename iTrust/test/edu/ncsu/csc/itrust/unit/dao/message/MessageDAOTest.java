package edu.ncsu.csc.itrust.unit.dao.message;

import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

public class MessageDAOTest extends TestCase {
    private DAOFactory factory = TestDAOFactory.getTestInstance();
    private MessageDAO messageDAO = factory.getMessageDAO();

    private Timestamp date1;
    private Timestamp date2;

    private final long mid = 9000000000L;

    @Override
    protected void setUp() throws Exception {
        TestDataGenerator gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.messages();

        // month is zero-based
        GregorianCalendar cal1 = new GregorianCalendar(2010, 1, 2, 13, 3, 0);
        GregorianCalendar cal2 = new GregorianCalendar(2010, 0, 28, 17, 58, 0);

        date1 = new Timestamp(cal1.getTimeInMillis());
        date2 = new Timestamp(cal2.getTimeInMillis());
    }

    public void testGetMessagesFor() throws Exception {
        List<MessageBean> results = messageDAO.getMessagesFor(mid);
        assertEquals(results.size(), 14);

        assertEquals(results.get(0).getSentDate(), date1);
    }

    public void testGetMessagesTimeAscending() throws Exception {
        List<MessageBean> results = messageDAO.getMessagesTimeAscending(mid);
        assertEquals(results.size(), 14);

        assertEquals(results.get(13).getSentDate(), date1);
    }

    public void testGetMessagesFrom() throws Exception {
        List<MessageBean> results = messageDAO.getMessagesFrom(mid);
        assertEquals(results.size(),
                2);

        assertEquals(results.get(0).getSentDate(), date2);
    }

    public void testGetMessagesFromTimeAscending() throws Exception {
        List<MessageBean> results = messageDAO.getMessagesFromTimeAscending(mid);
        assertEquals(results.size(), 2);

        assertEquals(results.get(1).getSentDate(), date2);
    }
}

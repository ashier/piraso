package ard.piraso.api.io;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.MessageEntry;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Test for {@link PirasoEntryReader} class.
 */
public class PirasoEntryReaderTest {

    @Test
    public void testRemoveListener() throws Exception {
        EntryReadListener listener = mock(EntryReadListener.class);

        PirasoEntryReader reader = new PirasoEntryReader(new ByteArrayInputStream("".getBytes()));

        reader.addListener(listener);
        assertEquals(1, reader.getListeners().size());

        reader.removeListener(listener);
        assertTrue(reader.getListeners().isEmpty());

        reader.addListener(listener);
        assertEquals(1, reader.getListeners().size());

        reader.clearListeners();
        assertTrue(reader.getListeners().isEmpty());
    }

    @Test
    public void testInvalidDateAndClassNameRead() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<piraso id=\"1\">\n" +
                "<entry class-name=\"ard.piraso.api.entry.MessageEntry\" date=\"invalid\" id=\"id_1\">{\"message\":\"message\",\"elapseTime\":null}</entry>\n" +
                "<entry class-name=\"invalidClassName\" date=\"1319349832439\" id=\"id_1\">{\"message\":\"message\",\"elapseTime\":null}</entry>\n" +
                "<not-entry>ignored</not-entry>" +
                "</piraso>";

        final List<Entry> entriesRead = new ArrayList<Entry>();
        final List<Long> idsRead = new ArrayList<Long>();
        final List<Date> datesRead = new ArrayList<Date>();

        PirasoEntryReader reader = new PirasoEntryReader(new ByteArrayInputStream(xml.getBytes()));
        reader.addListener(new EntryReadListener() {
            public void readEntry(EntryReadEvent evt) {
                datesRead.add(evt.getDate());
                idsRead.add(evt.getRequestId());
                entriesRead.add(evt.getEntry());
            }
        });

        reader.start();

        // no entry was read since all passed entries are invalid.
        assertTrue(entriesRead.isEmpty());
        assertTrue(idsRead.isEmpty());
        assertTrue(datesRead.isEmpty());
    }

    @Test
    public void testValidRead() throws Exception {
        final Long expectedRequestId = 1l;

        final MessageEntry expectedEntry1 = new MessageEntry(1l, "message_1");
        final MessageEntry expectedEntry2 = new MessageEntry(1l, "message_2");
        expectedEntry1.setRequestId(expectedRequestId);
        expectedEntry2.setRequestId(expectedRequestId);

        final String expectedId = "1";
        final String expectedWatchedAddr = "test";

        String xml = createXML(expectedId, expectedWatchedAddr, new PerformWrite() {
            public void doWrite(PirasoEntryWriter writer) throws Exception {
                writer.write(expectedEntry1);
                writer.write(expectedEntry2);
            }
        });

        final List<Entry> entriesRead = new ArrayList<Entry>();
        final List<Long> idsRead = new ArrayList<Long>();
        final List<Date> datesRead = new ArrayList<Date>();

        PirasoEntryReader reader = new PirasoEntryReader(new ByteArrayInputStream(xml.getBytes()));
        reader.addListener(new EntryReadListener() {
            public void readEntry(EntryReadEvent evt) {
                datesRead.add(evt.getDate());
                idsRead.add(evt.getRequestId());
                entriesRead.add(evt.getEntry());
            }
        });

        reader.start();

        assertEquals(expectedId, reader.getId());
        assertEquals(expectedWatchedAddr, reader.getWatchedAddr());
        assertEquals(2, entriesRead.size());
        assertEquals(2, idsRead.size());
        assertEquals(2, datesRead.size());

        assertEquals(expectedEntry1, entriesRead.get(0));
        assertEquals(expectedEntry2, entriesRead.get(1));
        assertEquals(expectedRequestId, idsRead.get(0));
        assertEquals(expectedRequestId, idsRead.get(1));
    }

    private String createXML(String id, String monitor, PerformWrite performer) throws Exception {
        StringWriter buf = new StringWriter();

        PirasoEntryWriter writer = new PirasoEntryWriter(id, monitor, new PrintWriter(buf));
        performer.doWrite(writer);
        writer.close();

        return buf.toString();
    }

    private static interface PerformWrite {
        public void doWrite(PirasoEntryWriter writer) throws Exception;
    }
}

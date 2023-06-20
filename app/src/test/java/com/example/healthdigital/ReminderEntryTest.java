package com.example.healthdigital;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ReminderEntryTest {
    private ReminderEntry reminderEntry;

    @Before
    public void setUp() {
        reminderEntry = new ReminderEntry();
    }

    @Test
    public void testGettersAndSetters() {
        String title = "Meeting";
        String notes = "Discuss project updates";
        String location = "Conference Room";
        Date date = new Date();

        reminderEntry.setTitle(title);
        reminderEntry.setNotes(notes);
        reminderEntry.setLocation(location);
        reminderEntry.setDate(date);

        assertEquals(title, reminderEntry.getTitle());
        assertEquals(notes, reminderEntry.getNotes());
        assertEquals(location, reminderEntry.getLocation());
        assertEquals(date, reminderEntry.getDate());
    }

    @Test
    public void testToString() {
        String title = "Meeting";
        String location = "Conference Room";

        reminderEntry.setTitle(title);
        reminderEntry.setLocation(location);

        String expectedString = title + " " + location;
        assertEquals(expectedString, reminderEntry.toString());
    }

    @Test
    public void testToStringWithoutLocation() {
        String title = "Task";

        reminderEntry.setTitle(title);

        assertEquals(title + "  ", reminderEntry.toString());
    }
}
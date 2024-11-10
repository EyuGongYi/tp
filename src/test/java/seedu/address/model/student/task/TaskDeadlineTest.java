package seedu.address.model.student.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class TaskDeadlineTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskDeadline(null));
    }

    @Test
    public void constructor_invalidTaskDeadline_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskDeadline(""));
    }

    @Test
    public void isValidFormat() {
        // null deadline
        assertThrows(NullPointerException.class, () -> TaskDeadline.isValidFormat(null));

        // valid format for comparison
        assertTrue(TaskDeadline.isValidFormat("2036-02-29")); // Should be true in a leap year
        assertTrue(TaskDeadline.isValidFormat("2035-02-28"));
        assertTrue(TaskDeadline.isValidFormat("2035-12-31"));
        assertTrue(TaskDeadline.isValidFormat("2035-01-01"));
        assertTrue(TaskDeadline.isValidFormat("2035-42-99"));
        assertTrue(TaskDeadline.isValidFormat("2035-42-01"));
        assertTrue(TaskDeadline.isValidFormat("2035-01-99"));

        // invalid format
        assertFalse(TaskDeadline.isValidFormat(""));
        assertFalse(TaskDeadline.isValidFormat(" "));
        assertFalse(TaskDeadline.isValidFormat("tomorrow"));
        assertFalse(TaskDeadline.isValidFormat("2035"));
        assertFalse(TaskDeadline.isValidFormat("2035-1-1"));
    }

    @Test
    public void isValidTaskDeadline() {
        // null deadline
        assertThrows(NullPointerException.class, () -> TaskDeadline.isValidDate(null));

        // valid date for comparison
        assertTrue(TaskDeadline.isValidTaskDeadline("2036-02-29")); // Should be true in a leap year
        assertTrue(TaskDeadline.isValidTaskDeadline("2035-02-28"));
        assertTrue(TaskDeadline.isValidTaskDeadline("2035-12-31"));
        assertTrue(TaskDeadline.isValidTaskDeadline("2035-01-01"));

        // invalid deadline
        assertFalse(TaskDeadline.isValidTaskDeadline(""));
        assertFalse(TaskDeadline.isValidTaskDeadline(" "));
        assertFalse(TaskDeadline.isValidTaskDeadline("tomorrow"));
        assertFalse(TaskDeadline.isValidTaskDeadline("2035"));
        assertFalse(TaskDeadline.isValidTaskDeadline("2035-1-1"));

        // invalid date: February 31 does not exist
        assertFalse(TaskDeadline.isValidTaskDeadline("2035-02-31")); // Should be false

        // other invalid dates
        assertFalse(TaskDeadline.isValidTaskDeadline("2035-04-31")); // April has only 30 days
        assertFalse(TaskDeadline.isValidTaskDeadline("2035-06-31")); // June has only 30 days
        assertFalse(TaskDeadline.isValidTaskDeadline("2035-09-31")); // September has only 30 days

        // Dates are in the past, before today.
        assertFalse(TaskDeadline.isValidTaskDeadline("1899-12-31"));
        assertFalse(TaskDeadline.isValidTaskDeadline("1111-02-28"));
        assertFalse(TaskDeadline.isValidTaskDeadline("2022-12-31"));
        String yesterday = LocalDate.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertFalse(TaskDeadline.isValidTaskDeadline(yesterday));
    }

    @Test
    public void isValidDate() {
        // null deadline
        assertThrows(NullPointerException.class, () -> TaskDeadline.isValidDate(null));

        // valid date for comparison
        assertTrue(TaskDeadline.isValidDate("2024-02-29")); // Should be true in a leap year
        assertTrue(TaskDeadline.isValidDate("2023-02-28"));
        assertTrue(TaskDeadline.isValidDate("2026-12-31"));
        assertTrue(TaskDeadline.isValidDate("2024-01-01"));

        // invalid deadline
        assertFalse(TaskDeadline.isValidDate(""));
        assertFalse(TaskDeadline.isValidDate(" "));
        assertFalse(TaskDeadline.isValidDate("tomorrow"));
        assertFalse(TaskDeadline.isValidDate("2024"));
        assertFalse(TaskDeadline.isValidDate("2024-1-1"));

        // invalid date: February 31 does not exist
        assertFalse(TaskDeadline.isValidDate("2024-02-31")); // Should be false

        // other invalid dates
        assertFalse(TaskDeadline.isValidDate("2024-04-31")); // April has only 30 days
        assertFalse(TaskDeadline.isValidDate("2024-06-31")); // June has only 30 days
        assertFalse(TaskDeadline.isValidDate("2024-09-31")); // September has only 30 days
    }

    @Test
    public void equals() {
        TaskDeadline taskDeadline = new TaskDeadline("2024-01-01");

        // same values -> returns true
        assertTrue(taskDeadline.equals(new TaskDeadline("2024-01-01")));

        // same object -> returns true
        assertTrue(taskDeadline.equals(taskDeadline));

        // null -> returns false
        assertFalse(taskDeadline.equals(null));

        // different types -> returns false
        assertFalse(taskDeadline.equals(5.0f));

        // different values -> returns false
        assertFalse(taskDeadline.equals(new TaskDeadline("2023-01-01")));
    }

    @Test
    public void hashcode_equivalents() {
        TaskDeadline taskDeadline = new TaskDeadline("2024-12-25");

        // same date -> return true
        assertEquals(taskDeadline.hashCode(), (new TaskDeadline("2024-12-25")).hashCode());

        // different values -> return false
        assertFalse(taskDeadline.hashCode() == (new TaskDeadline("2023-12-25")).hashCode());
    }
}

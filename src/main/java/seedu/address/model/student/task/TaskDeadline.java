package seedu.address.model.student.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Task's taskDeadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDeadline {
    public static final String MESSAGE_CONSTRAINTS = "Task deadline should be in the format YYYY-MM-DD";
    public static final String MESSAGE_CONSTRAINTS_PAST_DATE = "Deadlines cannot be in the past."
            + " Please choose today's date or a future date.";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final LocalDate taskDeadline;

    /**
     * Constucts a {@code TaskDeadline}
     * @param taskDeadline A valid task deadline.
     */
    public TaskDeadline(String taskDeadline) {
        requireNonNull(taskDeadline);
        checkArgument(isValidDate(taskDeadline), MESSAGE_CONSTRAINTS);
        this.taskDeadline = LocalDate.parse(taskDeadline, formatter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskDeadline)) {
            return false;
        }

        TaskDeadline otherTaskDeadline = (TaskDeadline) other;
        return taskDeadline.equals(otherTaskDeadline.taskDeadline);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        boolean isValidDeadline;
        try {
            LocalDate parsedDate = LocalDate.parse(test, formatter);

            // LocalDate.parse automatically adjusts the input date to last valid date of month,
            // as long as the day (DD) is less than or equal to 31.
            // This behavior is not intended; we expect an error for invalid dates.
            // Therefore, we perform an equality check to ensure that the formatted parsedDate matches the input date.
            isValidDeadline = parsedDate.format(formatter).equals(test);
        } catch (DateTimeParseException e) {
            isValidDeadline = false;
        }
        return isValidDeadline;
    }

    /**
     * Returns true if a given string is a valid date and is from today and onwards
     */
    public static boolean isValidTaskDeadline(String test) {
        if (!isValidDate(test)) {
            return false;
        } else {
            LocalDate parsedDate = LocalDate.parse(test, formatter);
            return !parsedDate.isBefore(LocalDate.now());
        }
    }

    @Override
    public int hashCode() {
        return taskDeadline.hashCode();
    }

    @Override
    public String toString() {
        // NOTE: this string is how it is saved into the json
        return taskDeadline.format(formatter);
    }

    public String toDescription() {
        return taskDeadline.format(DateTimeFormatter.ofPattern("d MMM uuuu"));
    }

    public int compareTo(TaskDeadline other) {
        return taskDeadline.compareTo(other.taskDeadline);
    }
}

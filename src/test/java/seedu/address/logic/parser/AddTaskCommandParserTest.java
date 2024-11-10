package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_CONTAIN_EXTRA_PREFIX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_TIME_SUN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.student.Name;
import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskDeadline;
import seedu.address.model.student.task.TaskDescription;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withTaskDescription(VALID_TASK_DESCRIPTION_PROJECT)
                .withTaskDeadline(VALID_TASK_DEADLINE).build();

        assertParseSuccess(parser, NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                new AddTaskCommand(new Name(VALID_NAME_BOB), expectedTask));

        // multiple spaces in name
        String multiSpaceName = " " + PREFIX_NAME + "Bob   Choo   ";
        assertParseSuccess(parser, multiSpaceName + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                new AddTaskCommand(new Name(VALID_NAME_BOB), expectedTask));

        // different casing in name
        String differentCasingName = " " + PREFIX_NAME + "BOB ChoO";
        assertParseSuccess(parser, differentCasingName + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                new AddTaskCommand(new Name(VALID_NAME_BOB), expectedTask));

        // multiple spaces and different casing in name
        String validName = " " + PREFIX_NAME + "   BoB    cHoO  ";
        assertParseSuccess(parser, validName + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                new AddTaskCommand(new Name(VALID_NAME_BOB), expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                expectedMessage);

        // missing task prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TASK_DESCRIPTION_PROJECT + TASK_DEADLINE_DESC_BOB,
                expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TASK_DESCRIPTION_PROJECT + VALID_TASK_DEADLINE,
                expectedMessage);
    }

    @Test
    public void parse_extraPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_CONTAIN_EXTRA_PREFIX, AddTaskCommand.MESSAGE_USAGE);

        String validExpectedStudentString = NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB;

        //have Phone Prefix
        assertParseFailure(parser, PHONE_DESC_BOB + validExpectedStudentString,
                expectedMessage);

        //have E-Contact Prefix
        assertParseFailure(parser, EMERGENCY_CONTACT_DESC_BOB + validExpectedStudentString,
                expectedMessage);

        //have Address Prefix
        assertParseFailure(parser, ADDRESS_DESC_BOB + validExpectedStudentString,
                expectedMessage);

        //have Subject Prefix
        assertParseFailure(parser, SUBJECT_DESC_ENGLISH + validExpectedStudentString,
                expectedMessage);

        //have Level Prefix
        assertParseFailure(parser, LEVEL_DESC_S1_EXPRESS + validExpectedStudentString,
                expectedMessage);

        //have Note Prefix
        assertParseFailure(parser, NOTE_DESC_AMY + validExpectedStudentString,
                expectedMessage);

        //have Task Index Prefix
        assertParseFailure(parser, TASK_INDEX_DESC + validExpectedStudentString,
                expectedMessage);

        //have LessonTime Prefix
        assertParseFailure(parser, LESSON_TIME_SUN_DESC + validExpectedStudentString,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // empty task description
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TASK_DESC + TASK_DEADLINE_DESC_BOB,
                TaskDescription.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + INVALID_DEADLINE_DESC,
                TaskDeadline.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                "random text " + NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedFields_failure() {
        String validTaskString = NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple tasks
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_AMY + validTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DESCRIPTION));

        // multiple deadlines
        assertParseFailure(parser, validTaskString + " d/2024-11-30",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DEADLINE));
    }
}

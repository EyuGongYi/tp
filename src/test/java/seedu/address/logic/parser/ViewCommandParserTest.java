package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_CONTAIN_EXTRA_PREFIX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_TIME_SUN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_INDEX_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudents.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_nameSpecified_success() {
        ViewCommand expectedCommand = new ViewCommand(AMY.getName());
        assertParseSuccess(parser, NAME_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_extraPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_CONTAIN_EXTRA_PREFIX, ViewCommand.MESSAGE_USAGE);

        String validExpectedStudentString = NAME_DESC_BOB;

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
        assertParseFailure(parser, NOTE_DESC_BOB + validExpectedStudentString,
                expectedMessage);

        //have Task Description Prefix
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_BOB + validExpectedStudentString,
                expectedMessage);

        //have Task Deadline Prefix
        assertParseFailure(parser, TASK_DEADLINE_DESC_BOB + validExpectedStudentString,
                expectedMessage);

        //have Task Index Prefix
        assertParseFailure(parser, TASK_INDEX_DESC + validExpectedStudentString,
                expectedMessage);

        //have LessonTime Prefix
        assertParseFailure(parser, LESSON_TIME_SUN_DESC + validExpectedStudentString,
                expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedMessage);

        // non-empty preamble
        assertParseFailure(parser, " asdasdadcad" + NAME_DESC_AMY, expectedMessage);

        // duplicate prefixes
        assertParseFailure(parser, NAME_DESC_AMY + " " + NAME_DESC_BOB,
                "Multiple values specified for the following single-valued field(s): n/");
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_CONTAIN_EXTRA_PREFIX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_TIME_SUN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UpdateCommand.UpdateStudentDescriptor;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Subject;
import seedu.address.testutil.UpdateStudentDescriptorBuilder;

public class TagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private final TagCommandParser parser = new TagCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {

        String userInput = NAME_DESC_BOB + SUBJECT_DESC_MATH + LEVEL_DESC_S1_EXPRESS;

        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder()
                .withSubjects(VALID_SUBJECT_MATH)
                .withLevel(VALID_LEVEL_S1_EXPRESS)
                .build();

        TagCommand expectedCommand = new TagCommand(new Name(VALID_NAME_BOB), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_onlySubjectPresent_success() {

        String userInput = NAME_DESC_BOB + SUBJECT_DESC_MATH;
        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder()
                .withSubjects(VALID_SUBJECT_MATH)
                .build();

        TagCommand expectedCommand = new TagCommand(new Name(VALID_NAME_BOB), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_onlyLevelPresent_success() {

        String userInput = NAME_DESC_BOB + LEVEL_DESC_S1_EXPRESS;
        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder()
                .withLevel(VALID_LEVEL_S1_EXPRESS)
                .build();

        TagCommand expectedCommand = new TagCommand(new Name(VALID_NAME_BOB), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleSubjects_success() {

        String userInput = NAME_DESC_BOB + SUBJECT_DESC_MATH + SUBJECT_DESC_ENGLISH;
        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder()
                .withSubjects(VALID_SUBJECT_MATH, VALID_SUBJECT_ENGLISH)
                .build();

        TagCommand expectedCommand = new TagCommand(new Name(VALID_NAME_BOB), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        //No name specified
        assertParseFailure(parser, SUBJECT_DESC_MATH + LEVEL_DESC_S1_EXPRESS, MESSAGE_INVALID_FORMAT);

        //No Subject and no Level specified
        assertParseFailure(parser, NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Invalid name being parsed as preamble
        assertParseFailure(parser, "1ohoh" + NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);

        //Invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/somestring", MESSAGE_INVALID_FORMAT);

        // non-empty preamble
        assertParseFailure(parser, TagCommand.COMMAND_WORD + " asdasdadcad" + NAME_DESC_BOB,
                MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_extraPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_CONTAIN_EXTRA_PREFIX, TagCommand.MESSAGE_USAGE);

        String validExpectedStudentString = NAME_DESC_BOB + SUBJECT_DESC_MATH + LEVEL_DESC_S1_EXPRESS;

        //have Phone Prefix
        assertParseFailure(parser, PHONE_DESC_BOB + validExpectedStudentString,
                expectedMessage);

        //have E-Contact Prefix
        assertParseFailure(parser, EMERGENCY_CONTACT_DESC_BOB + validExpectedStudentString,
                expectedMessage);

        //have Address Prefix
        assertParseFailure(parser, ADDRESS_DESC_BOB + validExpectedStudentString,
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
    public void parse_invalidValue_failure() {
        // Invalid Name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        //Invalid Subject
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS);

        //Invalid Level
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_LEVEL_DESC, Level.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validLevelInvalidSubject_failure() {
        String command = " n/John Doe l/S2 NA s/Chem";

        assertParseFailure(parser, command, Subject.getValidSubjectMessage(new Level("S2 NA")));
    }

    @Test
    public void parse_invalidLevelInvalidSubject_failure() {
        String command = " n/John Doe s/Chem";

        assertParseFailure(parser, command, Subject.MESSAGE_CONSTRAINTS);
    }

}

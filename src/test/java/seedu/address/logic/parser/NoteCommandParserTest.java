package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY_WITH_MULTISPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NoteCommand;
import seedu.address.model.person.Note;

public class NoteCommandParserTest {
    private NoteCommandParser parser = new NoteCommandParser();
    private final String nonEmptyNote = "Some note.";

    @Test
    public void parse_nameSpecified_success() {
        // have note
        String userInput = NAME_DESC_AMY + " " + PREFIX_NOTE + nonEmptyNote;
        NoteCommand expectedCommand = new NoteCommand(AMY.getName(), new Note(nonEmptyNote));
        assertParseSuccess(parser, userInput, expectedCommand);
        // no note
        userInput = NAME_DESC_AMY + " " + PREFIX_NOTE;
        expectedCommand = new NoteCommand(AMY.getName(), new Note(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_argumentsCorrectly_success() {
        NoteCommand expectedCommand = new NoteCommand(AMY.getName(), new Note(nonEmptyNote));

        // Name does not have multi-spaced names
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY_WITH_MULTISPACE + " " + PREFIX_NOTE + nonEmptyNote;
        assertParseSuccess(parser, userInput, expectedCommand);

        // Name does not have preceding or trailing spaces
        userInput = NAME_DESC_AMY + "      " + PREFIX_NOTE + nonEmptyNote;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, NoteCommand.COMMAND_WORD, expectedMessage);
        // no name
        assertParseFailure(parser, NoteCommand.COMMAND_WORD + " " + nonEmptyNote, expectedMessage);
    }
}

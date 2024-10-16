package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.ViewTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.ui.UiState;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);
    private final Model model;

    /**
     * Constructs an {@code AddressBookParser} with the given {@code Model}.
     */
    public AddressBookParser(Model model) {
        this.model = model;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new AddCommandParser().parse(arguments);

        case UpdateCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new UpdateCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new FindCommandParser().parse(arguments);

        case NoteCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new NoteCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new HelpCommand();

        case TagCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Details);
            return new TagCommandParser().parse(arguments);
            
        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case ViewTasksCommand.COMMAND_WORD:
            model.getUiState().setState(UiState.State.Tasks);
            return new ViewTasksCommand();
            
        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

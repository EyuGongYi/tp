package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_CONTAIN_EXTRA_PREFIX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;
import static seedu.address.logic.parser.ParserUtil.isAnyPrefixPresent;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new {@code ViewCommand} object
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code ViewCommand}
     * and returns a {@code ViewCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                PREFIX_ADDRESS, PREFIX_NOTE, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_TASK_DESCRIPTION,
                PREFIX_TASK_DEADLINE, PREFIX_TASK_INDEX, PREFIX_LESSON_TIME);

        boolean isExtraPrefix = isAnyPrefixPresent(argMultimap, PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                PREFIX_ADDRESS, PREFIX_NOTE, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_TASK_DESCRIPTION,
                PREFIX_TASK_DEADLINE, PREFIX_TASK_INDEX, PREFIX_LESSON_TIME);

        if (isExtraPrefix) {
            throw new ParseException(String.format(MESSAGE_CONTAIN_EXTRA_PREFIX, ViewCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        return new ViewCommand(name);
    }
}

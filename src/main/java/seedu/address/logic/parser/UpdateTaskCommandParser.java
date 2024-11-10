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
import static seedu.address.logic.parser.ParserUtil.parseName;
import static seedu.address.logic.parser.ParserUtil.parseTaskIndex;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateTaskCommand;
import seedu.address.logic.commands.UpdateTaskCommand.UpdateTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateTaskCommandParser implements Parser<UpdateTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TASK_INDEX, PREFIX_TASK_DESCRIPTION,
                        PREFIX_TASK_DEADLINE,
                        PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                        PREFIX_ADDRESS, PREFIX_NOTE, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_LESSON_TIME);

        boolean isExtraPrefix = isAnyPrefixPresent(argMultimap, PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                PREFIX_ADDRESS, PREFIX_NOTE, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_LESSON_TIME);

        if (isExtraPrefix) {
            throw new ParseException(String.format(MESSAGE_CONTAIN_EXTRA_PREFIX, UpdateTaskCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TASK_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTaskCommand.MESSAGE_USAGE));
        }

        Name name = parseName(argMultimap.getValue(PREFIX_NAME).get());
        Index taskIndex = parseTaskIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TASK_INDEX, PREFIX_TASK_DESCRIPTION,
                PREFIX_TASK_DEADLINE);

        UpdateTaskDescriptor updateTaskDescriptor = new UpdateTaskDescriptor();

        if (argMultimap.getValue(PREFIX_TASK_DESCRIPTION).isPresent()) {
            updateTaskDescriptor.setTaskDescription(
                    ParserUtil.parseTaskDescription(argMultimap.getValue(PREFIX_TASK_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_TASK_DEADLINE).isPresent()) {
            updateTaskDescriptor.setTaskDeadline(
                    ParserUtil.parseTaskDeadline(argMultimap.getValue(PREFIX_TASK_DEADLINE).get()));
        }

        if (!updateTaskDescriptor.isAnyFieldUpdated()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateTaskCommand(name, taskIndex, updateTaskDescriptor);
    }

}

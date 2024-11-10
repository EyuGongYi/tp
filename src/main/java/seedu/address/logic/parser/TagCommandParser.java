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

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UpdateCommand.UpdateStudentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;
import seedu.address.model.student.Subject;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECT, PREFIX_LEVEL,
                        PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT, PREFIX_ADDRESS, PREFIX_NOTE,
                        PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DEADLINE, PREFIX_TASK_INDEX,
                        PREFIX_LESSON_TIME);
        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LEVEL);

        boolean isExtraPrefix = isAnyPrefixPresent(argMultiMap, PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                PREFIX_ADDRESS, PREFIX_NOTE, PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DEADLINE, PREFIX_TASK_INDEX,
                PREFIX_LESSON_TIME);

        if (isExtraPrefix) {
            throw new ParseException(String.format(MESSAGE_CONTAIN_EXTRA_PREFIX, TagCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultiMap, PREFIX_NAME) || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        Name studentToTag;
        if (argMultiMap.getValue(PREFIX_NAME).isPresent()) {
            studentToTag = ParserUtil.parseName(argMultiMap.getValue(PREFIX_NAME).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        UpdateStudentDescriptor editStudentTags = new UpdateStudentDescriptor();

        if (argMultiMap.getValue(PREFIX_LEVEL).isPresent()) {
            editStudentTags.setLevel(
                    ParserUtil.parseLevel(
                            argMultiMap.getValue(PREFIX_LEVEL).get()));

        }

        if (argMultiMap.getValue(PREFIX_SUBJECT).isPresent()) {
            try {
                editStudentTags.setSubjects(
                        ParserUtil.parseSubjects(
                                argMultiMap.getAllValues(PREFIX_SUBJECT)));
            } catch (ParseException e) {
                if (editStudentTags.getLevel().isPresent()) {
                    throw new ParseException(Subject.getValidSubjectMessage(editStudentTags.getLevel().get()));
                }
                throw new ParseException(e.getMessage());
            }
        }

        if (argMultiMap.getValue(PREFIX_SUBJECT).isEmpty()) {
            if (argMultiMap.getValue(PREFIX_LEVEL).isEmpty()) {

                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
            }
        }

        return new TagCommand(studentToTag, editStudentTags);
    }
}

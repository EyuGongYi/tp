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
import static seedu.address.logic.parser.ParserUtil.isAnyPrefixPresent;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Subject;
import seedu.address.model.student.predicate.LevelContainsKeywordsPredicate;
import seedu.address.model.student.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.student.predicate.SubjectContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LEVEL, PREFIX_SUBJECT,
                        PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                        PREFIX_ADDRESS, PREFIX_NOTE, PREFIX_TASK_DESCRIPTION,
                        PREFIX_TASK_DEADLINE, PREFIX_TASK_INDEX, PREFIX_LESSON_TIME);

        boolean isExtraPrefix = isAnyPrefixPresent(argMultimap, PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                PREFIX_ADDRESS, PREFIX_NOTE, PREFIX_TASK_DESCRIPTION,
                PREFIX_TASK_DEADLINE, PREFIX_TASK_INDEX, PREFIX_LESSON_TIME);

        if (isExtraPrefix) {
            throw new ParseException(String.format(MESSAGE_CONTAIN_EXTRA_PREFIX, FindCommand.MESSAGE_USAGE));
        }

        boolean isByName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean isByLevel = argMultimap.getValue(PREFIX_LEVEL).isPresent();
        boolean isBySubject = argMultimap.getValue(PREFIX_SUBJECT).isPresent();

        if ((isByName && isByLevel) || (isByName && isBySubject)
                || (isByLevel && isBySubject) || (!isByName && !isByLevel && !isBySubject)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LEVEL, PREFIX_SUBJECT);

        if (isByName) {
            String toFind = argMultimap.getValue(PREFIX_NAME).get();

            // Code below conducts checks usually done in parse,
            // but ParserUtil is not used as Name objects are not needed.
            requireNonNull(toFind);
            String[] trimmedNames = toFind.trim().split("\\s+");
            if (Arrays.stream(trimmedNames).anyMatch(name -> !Name.isValidName(name))) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }

            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(trimmedNames)));
        } else if (isByLevel) {
            String toFind = argMultimap.getValue(PREFIX_LEVEL).get();

            // Code below conducts checks usually done in parse,
            // but ParserUtil is not used as Level object is not needed.
            requireNonNull(toFind);
            String trimmedLevel = toFind.trim();
            if (!Level.isValidLevelName(trimmedLevel)) {
                throw new ParseException(Level.MESSAGE_CONSTRAINTS);
            }

            // Remove spaces within and make all upper case
            trimmedLevel = String.join(" ", trimmedLevel.split("\\s+")).toUpperCase();

            String[] trimmedLevels = {trimmedLevel};
            return new FindCommand(new LevelContainsKeywordsPredicate(Arrays.asList(trimmedLevels)));
        } else {
            String toFind = argMultimap.getValue(PREFIX_SUBJECT).get();

            // Code below conducts checks usually done in parse,
            // but ParserUtil is not used as Subject objects are not needed.
            requireNonNull(toFind);
            String[] trimmedSubjects = toFind.trim().split("\\s+");
            if (Arrays.stream(trimmedSubjects).anyMatch(subject -> !Subject.isValidSubjectName(subject))) {
                throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
            }

            return new FindCommand(new SubjectContainsKeywordsPredicate(Arrays.asList(trimmedSubjects)));
        }
    }
}

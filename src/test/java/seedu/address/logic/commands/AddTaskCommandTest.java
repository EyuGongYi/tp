package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.task.Task;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddTaskCommand.
 */
public class AddTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullNameOrTask_throwsNullPointerException() {
        Task validTask = new TaskBuilder().build();
        Name validName = new Name("John Doe");

        // Test null task
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(validName, null));
        // Test null name
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null, validTask));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        // Get an existing person from the typical address book
        Person person = model.getAddressBook().getPersonList().get(0);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Task validTask = new TaskBuilder().build();
        Name validName = person.getName();

        PersonBuilder copiedPerson = new PersonBuilder(person);
        Person updatedPerson = copiedPerson.build();
        updatedPerson.getTaskList().add(validTask);

        expectedModel.setPerson(person, updatedPerson);

        AddTaskCommand addTaskCommand = new AddTaskCommand(validName, validTask);
        String expectedMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS,
                validTask.getTaskDescription(), validName, validTask.getTaskDeadline());

        assertCommandSuccess(addTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        // Get an existing person from the typical address book
        Person person = model.getAddressBook().getPersonList().get(0);
        Task validTask = new TaskBuilder().build();
        Name validName = person.getName();
        AddTaskCommand addTaskCommand = new AddTaskCommand(validName, validTask);

        PersonBuilder copiedPerson = new PersonBuilder(person);
        Person updatedPerson = copiedPerson.build();
        updatedPerson.getTaskList().add(validTask);

        model.setPerson(person, updatedPerson);

        assertThrows(CommandException.class,
                AddTaskCommand.MESSAGE_DUPLICATE_TASK, () -> addTaskCommand.execute(model));
    }

    @Test
    public void equals() {
        Task task1 = new TaskBuilder().withTaskDescription("Homework").build();
        Task task2 = new TaskBuilder().withTaskDescription("Chores").build();
        Name name1 = new Name("John Doe");
        Name name2 = new Name("Jane Doe");
        AddTaskCommand addTask1Command = new AddTaskCommand(name1, task1);
        AddTaskCommand addTask2Command = new AddTaskCommand(name2, task2);

        // same object -> returns true
        assertTrue(addTask1Command.equals(addTask1Command));

        // same values -> returns true
        AddTaskCommand addTask1CommandCopy = new AddTaskCommand(name1, task1);
        assertTrue(addTask1Command.equals(addTask1CommandCopy));

        // different types -> returns false
        assertFalse(addTask1Command.equals(1));

        // null -> returns false
        assertFalse(addTask1Command.equals(null));

        // different task and name -> returns false
        assertFalse(addTask1Command.equals(addTask2Command));
    }
}

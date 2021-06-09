package by.sadko.training.command;

/**
 * Interface describes behavior of the commands provider object
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see BasicCommandProvider,AppCommand,AppCommandName
 */
public interface AppCommandProvider {

    void registerCommand(AppCommandName commandName, AppCommand command);

    void removeCommand(AppCommandName commandName);

    AppCommand getCommand(String commandName);
}

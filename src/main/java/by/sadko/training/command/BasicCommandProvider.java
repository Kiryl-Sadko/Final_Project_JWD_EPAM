package by.sadko.training.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class of the basic implementation AppCommandProvider
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommandProvider,AppCommand,AppCommandName
 */
public class BasicCommandProvider implements AppCommandProvider {

    private static final Logger LOGGER = LogManager.getLogger(BasicCommandProvider.class);
    private final Map<AppCommandName, AppCommand> commandMap;

    /**
     * Constructor initializes new object of the command provider
     */
    public BasicCommandProvider() {
        commandMap = new EnumMap<>(AppCommandName.class);
    }

    /**
     * Constructor initializes new object of the command provider
     *
     * @param commandMap - map which contains commands by command's name
     */
    public BasicCommandProvider(Map<AppCommandName, AppCommand> commandMap) {
        this.commandMap = commandMap;
    }

    /**
     * Registers command in map by commandName
     */
    @Override
    public void registerCommand(AppCommandName commandName, AppCommand command) {

        if ((commandMap.containsKey(commandName))) {
            String name = commandName.name();
            LOGGER.info("The command {} already exists and has been changed", name);
        }
        commandMap.put(commandName, command);
    }

    /**
     * Removes command from map by command's name
     *
     * @param commandName - name of the command in map
     */
    @Override
    public void removeCommand(AppCommandName commandName) {

        if (!commandMap.containsKey(commandName)) {
            String name = commandName.name();
            LOGGER.info("This command {} is not founded in the command provider", name);
        }
        commandMap.remove(commandName);
    }

    /**
     * Returns command from map by command's name
     *
     * @param commandName - name of the command in map
     * @return command by command's name
     */
    @Override
    public AppCommand getCommand(String commandName) {

        AppCommandName appCommandName = AppCommandName.fromString(commandName);
        return commandMap.get(appCommandName);
    }
}

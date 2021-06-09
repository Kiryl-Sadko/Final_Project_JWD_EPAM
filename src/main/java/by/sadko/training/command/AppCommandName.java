package by.sadko.training.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ENUM includes command's names
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommandProvider
 */
public enum AppCommandName {

    INDEX,

    LOGIN_DISPLAY,
    LOGIN_SUBMIT,
    LOGOUT,
    SIGN_UP_DISPLAY,
    SIGN_UP_SUBMIT,

    CONTRACT_DISPLAY_ALL,
    CONTRACT_DISPLAY_CREATION,
    CONTRACT_CREATE,
    CONTRACT_PAY,
    CONTRACT_DELETE,
    CONTRACT_MAKE_DISCOUNT,

    PRODUCT_DISPLAY_ALL,
    PRODUCT_DISPLAY_CREATION,
    PRODUCT_CREATE,
    PRODUCT_DELETE,
    PRODUCT_DISPLAY_EDITION,
    PRODUCT_EDIT,

    MATERIAL_DISPLAY_ALL,
    MATERIAL_DISPLAY_CREATION,
    MATERIAL_CREATE,
    MATERIAL_DELETE,
    MATERIAL_DISPLAY_EDITION,
    MATERIAL_EDIT,

    WALLET_DISPLAY,
    WALLET_REFILL_BALANCE,

    WORKLOAD_DISPLAY_ALL,

    DISPLAY_ABOUT_US,

    COMMAND_NOT_FOUND,
    NO_PERMISSION;

    private static final Logger LOGGER = LogManager.getLogger(AppCommandName.class);

    public static AppCommandName fromString(String value) {

        AppCommandName[] values = AppCommandName.values();
        for (AppCommandName commandName : values) {

            if (commandName.name().equalsIgnoreCase(value)) {

                LOGGER.info("Command name is {}", commandName);
                return commandName;
            }
        }
        return COMMAND_NOT_FOUND;
    }
}

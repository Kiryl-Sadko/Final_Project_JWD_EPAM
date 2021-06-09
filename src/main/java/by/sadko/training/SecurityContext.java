package by.sadko.training;

import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class of the security context.
 * Should be a singleton
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public class SecurityContext {

    private static final Logger LOGGER = LogManager.getLogger(SecurityContext.class);

    private static final AtomicBoolean IS_INSTANCE_EXIST = new AtomicBoolean(false);
    private static final Lock INSTANCE_LOCK = new ReentrantLock();

    private static SecurityContext instance;
    private final Properties properties = new Properties();
    private final Map<String, UserAccount> userAccountMap = new ConcurrentHashMap<>(1000);
    private final ThreadLocal<String> currentSessionIdStorage = new ThreadLocal<>();

    private SecurityContext() {

        try (InputStream propertiesStream = SecurityContext.class.getResourceAsStream("/security.properties")) {

            properties.load(propertiesStream);
            LOGGER.info("Security context is initialized");

        } catch (IOException ioException) {
            LOGGER.error("Failed to read security properties");
            throw new IllegalStateException(ioException.getMessage(), ioException);
        }
    }

    public static SecurityContext getInstance() {

        if (!IS_INSTANCE_EXIST.get()) {
            INSTANCE_LOCK.lock();
            try {
                if (instance == null) {
                    instance = new SecurityContext();
                    IS_INSTANCE_EXIST.set(true);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return instance;
    }

    /**
     * Checking permission of a current user to command execution
     *
     * @param commandName - command
     * @return boolean result of the permissions
     */
    public boolean canExecute(AppCommandName commandName) {

        UserAccount currentUser = getCurrentUser();
        return canExecute(currentUser, commandName);
    }

    /**
     * Checking permission of a current user to command execution
     *
     * @param user        - current user
     * @param commandName - command
     * @return boolean result of the permissions
     */
    public boolean canExecute(UserAccount user, AppCommandName commandName) {

        String commandRoles = properties.getProperty("command." + commandName.name());
        List<String> allowedRoles = Optional.ofNullable(commandRoles).map(s -> Arrays.asList(s.split(",")))
                .orElseGet(ArrayList::new);

        boolean isRoleMatch = false;
        if (user != null) {
            for (String role : allowedRoles) {
                isRoleMatch = Optional.of(user.getUserRoles().stream()
                        .anyMatch(userRole -> userRole.name().equalsIgnoreCase(role)))
                        .orElseGet(() -> false);
                if (isRoleMatch) {
                    break;
                }
            }
        }
        return allowedRoles.isEmpty() || isRoleMatch;
    }

    /**
     * Registration user session in context
     *
     * @param userAccount - user account
     */
    public void logIn(UserAccount userAccount) {

        String currentSessionId = getCurrentSessionId();
        userAccountMap.put(currentSessionId, userAccount);
    }

    /**
     * Removing user session from context
     */
    public void logOut() {

        String currentSessionId = getCurrentSessionId();
        userAccountMap.remove(currentSessionId);
    }

    /**
     * Checking user in context
     *
     * @return boolean result
     */
    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    /**
     * Returns session id of the current user
     *
     * @return - session id
     */
    public String getCurrentSessionId() {
        return currentSessionIdStorage.get();
    }

    /**
     * Setting current session id
     *
     * @param sessionId - session id
     */
    public void setCurrentSessionId(String sessionId) {
        currentSessionIdStorage.set(sessionId);
    }

    /**
     * Returns current user by current session id
     *
     * @return current session id
     */
    public UserAccount getCurrentUser() {

        String currentSessionId = getCurrentSessionId();
        return userAccountMap.get(currentSessionId);
    }
}

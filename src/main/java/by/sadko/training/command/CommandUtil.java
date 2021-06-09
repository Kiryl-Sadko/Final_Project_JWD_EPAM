package by.sadko.training.command;

import by.sadko.training.SecurityContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static by.sadko.training.util.AppConstants.*;

/**
 * Util class with common methods
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommandProvider,AppCommand,AppCommandName
 */
public class CommandUtil {

    private static final Logger LOGGER = LogManager.getLogger(CommandUtil.class);

    private CommandUtil() {
    }

    /**
     * Returns command from request
     *
     * @param request - request
     * @return command
     */
    public static String getCommandFromRequest(HttpServletRequest request) {

        Object attributeCommand = request.getAttribute(PARAM_COMMAND);
        return (attributeCommand != null) ? String.valueOf(attributeCommand) : request.getParameter(PARAM_COMMAND);
    }

    /**
     * Setting request attribute - quantity of the pages. Which is depends on the size of the contract list
     *
     * @param request - request
     * @param list    - contract list
     */
    public static void setPageQuantity(HttpServletRequest request, List<?> list) {

        int contractQuantity = list.size();
        int pages = contractQuantity / 5;

        if (contractQuantity % 5 > 0) {
            pages++;
        }

        request.setAttribute(PAGE_QUANTITY, pages);
    }

    /**
     * Setting request attribute - page number.
     *
     * @param request - request
     * @return page number
     */
    public static int setPageNumber(HttpServletRequest request) {

        Optional<String> optionalPageNumber = Optional.ofNullable(request.getParameter(PAGE_NUMBER));
        int pageNumber = Integer.parseInt(optionalPageNumber.orElseGet(() -> "1"));

        request.setAttribute(PAGE_NUMBER, pageNumber);

        return pageNumber;
    }

    /**
     * Extracts profit ration of the contract from properties file
     *
     * @return profit ratio of the contract
     */
    public static double getProfitRatio() {

        Properties properties = new Properties();

        try (InputStream propertiesStream = SecurityContext.class.getResourceAsStream("/contract.properties")) {
            properties.load(propertiesStream);

        } catch (IOException ioException) {
            LOGGER.error("Failed to read contract properties file");
            throw new IllegalStateException(ioException.getMessage(), ioException);
        }

        String profit = properties.getProperty("profit_ratio");
        return Double.parseDouble(profit);
    }
}

package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ProductService;
import by.sadko.training.util.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the deletion product command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ProductService
 */
public class DeleteProductCommand implements AppCommand {

    private static final Logger LOGGER = LogManager.getLogger(DeleteProductCommand.class);

    private final ProductService productService;

    /**
     * Initializing of the product deletion command
     *
     * @param productService - service of the product objects
     */
    public DeleteProductCommand(ProductService productService) {

        this.productService = productService;
    }

    /**
     * Executes deletion of the product from data base
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long productId = Long.parseLong(request.getParameter(AppConstants.PARAM_PRODUCT_ID));
        boolean isDeleted = productService.delete(productId);

        if (isDeleted) {
            LOGGER.info("Product id={} is deleted", productId);
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.PRODUCT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=Product id=" + productId + " is deleted";

        } else {
            LOGGER.info("Product id={} isn't deleted", productId);
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.PRODUCT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=Product id=" + productId + " isn't deleted, try letter";
        }
    }
}

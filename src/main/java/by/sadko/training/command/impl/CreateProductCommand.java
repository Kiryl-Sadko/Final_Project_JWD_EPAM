package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.Product;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.OperationService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.TechnologicalProcessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the product creation command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ProductService,TechnologicalProcessService,OperationService
 */
public class CreateProductCommand implements AppCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateProductCommand.class);

    private final ProductService productService;
    private final TechnologicalProcessService technologicalProcessService;
    private final OperationService operationService;

    /**
     * Initializing of the command
     *
     * @param productService              - service of the product objects
     * @param technologicalProcessService - service of the technological process objects
     * @param operationService            - service of the operation objects
     */
    public CreateProductCommand(ProductService productService, TechnologicalProcessService technologicalProcessService,
                                OperationService operationService) {

        this.productService = productService;
        this.technologicalProcessService = technologicalProcessService;
        this.operationService = operationService;
    }

    /**
     * Executes product creation in data base
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        String productName = request.getParameter(PARAM_PRODUCT_NAME);
        double productWeight = Double.parseDouble(request.getParameter(PARAM_PRODUCT_WEIGHT));
        long materialId = Long.parseLong(request.getParameter(PARAM_MATERIAL));

        List<Operation> operationQueue = new ArrayList<>();
        String operationString = request.getParameter(PARAM_OPERATION_QUEUE);

        if (!operationString.isEmpty()) {

            String[] operationIdArray = operationString.trim().split(",");
            for (String id : operationIdArray) {

                long operationId = Long.parseLong(id);
                Operation operation = operationService.getById(operationId);
                operationQueue.add(operation);
            }

        } else {

            String operationSelect = request.getParameter(PARAM_OPERATION);
            long operationId = Long.parseLong(operationSelect);
            Operation operation = operationService.getById(operationId);
            operationQueue.add(operation);
        }


        Long technologicalProcessId = technologicalProcessService.getByOperationOrCreate(operationQueue);

        Product product = new Product(productName, productWeight, materialId, technologicalProcessId);
        Long productId = null;

        try {
            productId = productService.create(product, operationQueue);

        } catch (DAOException daoException) {
            LOGGER.info("A product with name '{}' exists", product.getName());

            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.PRODUCT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=" + "Product with name '" + productName + "' already exists";
        }

        return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.PRODUCT_DISPLAY_ALL
                + "&" + PARAM_COMMAND_RESULT + "=" + "Product id=" + productId + " is created";
    }
}

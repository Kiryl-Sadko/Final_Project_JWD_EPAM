package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.Product;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.OperationService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.TechnologicalProcessService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the product edition command with fields <b>productService</b>, <b>operationService</b>
 * and <b>processService</b>
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ProductService,OperationService,TechnologicalProcessService
 */
public class EditProductCommand implements AppCommand {

    private final ProductService productService;
    private final OperationService operationService;
    private final TechnologicalProcessService processService;

    /**
     * Initialization of the product edition command
     *
     * @param productService   - service of the product objects
     * @param operationService - service of the operation objects
     * @param processService   - service of the process objects
     */
    public EditProductCommand(ProductService productService, OperationService operationService,
                              TechnologicalProcessService processService) {

        this.productService = productService;
        this.operationService = operationService;
        this.processService = processService;
    }

    /**
     * Executes product edition
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long productId = Long.parseLong(request.getParameter(PARAM_PRODUCT_ID));
        Product product = productService.getById(productId);

        Optional.ofNullable(request.getParameter(PARAM_PRODUCT_NAME))
                .filter(s -> !s.isEmpty())
                .ifPresent(product::setName);

        Optional.ofNullable(request.getParameter(PARAM_PRODUCT_WEIGHT))
                .filter(s -> !s.isEmpty())
                .ifPresent(s -> product.setWeight(Double.parseDouble(s)));

        Optional.ofNullable(request.getParameter(PARAM_MATERIAL))
                .filter(s -> !s.isEmpty())
                .ifPresent(s -> product.setMaterialID(Long.parseLong(s)));

        setOperationQueue(request, product);

        boolean isEdited = productService.edit(product);
        if (isEdited) {

            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.PRODUCT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=" + "Product id=" + productId + " is edited";

        } else {
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.PRODUCT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=" + "Product with name '" + product.getName() + "' already exists";

        }
    }

    /**
     * Setting operation queue to product
     *
     * @param request - request
     * @param product - product
     */
    private void setOperationQueue(HttpServletRequest request, Product product) throws DAOException {

        List<Operation> operationQueue = new ArrayList<>();
        String operationString = request.getParameter(PARAM_OPERATION_QUEUE);

        if (!operationString.isEmpty()) {

            String[] operationIdArray = operationString.trim().split(",");
            for (String str : operationIdArray) {

                long operationId = Long.parseLong(str);
                Operation operation = operationService.getById(operationId);
                operationQueue.add(operation);
            }

        } else {
            Optional<String> optionalOperationId = Optional.ofNullable(request.getParameter(PARAM_OPERATION))
                    .filter(s -> !s.isEmpty());

            if (optionalOperationId.isPresent()) {

                long operationId = Long.parseLong(optionalOperationId.get());
                Operation operation = operationService.getById(operationId);
                operationQueue.add(operation);
            }
        }

        if (!operationQueue.isEmpty()) {

            Long technologicalProcessId = processService.getByOperationOrCreate(operationQueue);
            product.setTechnologicalProcessID(technologicalProcessId);
        }
    }
}

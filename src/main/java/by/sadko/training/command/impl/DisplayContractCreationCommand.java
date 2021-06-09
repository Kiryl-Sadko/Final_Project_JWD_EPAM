package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.entity.Product;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.sadko.training.util.AppConstants.PARAM_PRODUCT_LIST;

/**
 * Class of the displaying contract creation command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ProductService
 */
public class DisplayContractCreationCommand implements AppCommand {

    private final ProductService productService;

    /**
     * Initializing of the command
     *
     * @param productService - service of the product objects
     */
    public DisplayContractCreationCommand(ProductService productService) {

        this.productService = productService;
    }

    /**
     * Executes displaying of the contract creation view
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        List<Product> productList = productService.getAll();
        request.setAttribute(PARAM_PRODUCT_LIST, productList);

        return "contract_creation_view";
    }
}

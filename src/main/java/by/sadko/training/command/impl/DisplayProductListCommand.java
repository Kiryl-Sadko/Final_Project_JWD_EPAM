package by.sadko.training.command.impl;

import by.sadko.training.SecurityContext;
import by.sadko.training.builder.BasicProductDtoBuilder;
import by.sadko.training.builder.ProductDtoBuilder;
import by.sadko.training.command.AppCommand;
import by.sadko.training.dto.ProductDto;
import by.sadko.training.entity.Product;
import by.sadko.training.entity.UserRole;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.TechnologicalProcessService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.sadko.training.command.CommandUtil.*;
import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the displaying product list command with fields <b>productService</b>, <b>materialService</b>,
 * and <b>processService</b>
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ProductService,MaterialService,TechnologicalProcessService
 */
public class DisplayProductListCommand implements AppCommand {

    private final ProductService productService;
    private final MaterialService materialService;
    private final TechnologicalProcessService processService;

    /**
     * Initializing displaying product list command
     *
     * @param productService  - service of the product objects
     * @param materialService - service of the material objects
     * @param processService  - service of the technological process objects
     */
    public DisplayProductListCommand(ProductService productService, MaterialService materialService,
                                     TechnologicalProcessService processService) {

        this.productService = productService;
        this.materialService = materialService;
        this.processService = processService;
    }

    /**
     * Executes displaying product DTO list
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        List<UserRole> userRoles = SecurityContext.getInstance().getCurrentUser().getUserRoles();
        userRoles.forEach(role -> {
            if (UserRole.CEO.equals(role)) {
                request.setAttribute(PARAM_ACCOUNT_ROLE, role);
            }
        });

        List<Product> productList = productService.getAll();

        ProductDtoBuilder productDtoBuilder = new BasicProductDtoBuilder(productService, materialService, processService);

        setPageQuantity(request, productList);

        int pageNumber = setPageNumber(request);

        setProductList(request, productList, productDtoBuilder, pageNumber);

        Optional<String> optionalResult = Optional.ofNullable(request.getParameter(AppConstants.PARAM_COMMAND_RESULT));
        optionalResult.ifPresent(s -> request.setAttribute(PARAM_MESSAGE, s));

        return "product_list_view";
    }

    /**
     * Setting request attribute - product list
     *
     * @param request           - request
     * @param productList       - product list
     * @param productDtoBuilder - builder of the product DTO
     * @param pageNumber        - page number
     */
    private void setProductList(HttpServletRequest request, List<Product> productList,
                                ProductDtoBuilder productDtoBuilder, int pageNumber) throws DAOException {

        List<ProductDto> result = new ArrayList<>();
        for (int i = (pageNumber - 1) * 5; i < pageNumber * 5 && i < productList.size(); i++) {

            Product product = productList.get(i);
            ProductDto productDto = buildProductDto(productDtoBuilder, product);
            result.add(productDto);
        }

        request.setAttribute(PARAM_PRODUCT_DTO_LIST, result);
    }

    /**
     * Building product DTO
     *
     * @param productDtoBuilder - DTO builder
     * @param product           - product with necessary field
     * @return product DTO
     */
    private ProductDto buildProductDto(ProductDtoBuilder productDtoBuilder, Product product) throws DAOException {

        productDtoBuilder.setId(product);
        productDtoBuilder.setName(product);
        productDtoBuilder.setWeight(product);
        productDtoBuilder.setMaterial(product);
        productDtoBuilder.setOperationQueue(product);
        productDtoBuilder.setCost(product);
        return productDtoBuilder.build();
    }
}

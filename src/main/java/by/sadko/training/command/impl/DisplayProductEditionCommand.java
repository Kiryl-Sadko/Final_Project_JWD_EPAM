package by.sadko.training.command.impl;

import by.sadko.training.builder.BasicProductDtoBuilder;
import by.sadko.training.builder.ProductDtoBuilder;
import by.sadko.training.command.AppCommand;
import by.sadko.training.dto.ProductDto;
import by.sadko.training.entity.Material;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.Product;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;
import by.sadko.training.service.OperationService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.TechnologicalProcessService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class of the displaying product edition command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ProductService,MaterialService,TechnologicalProcessService,OperationService
 */
public class DisplayProductEditionCommand implements AppCommand {

    private final ProductService productService;
    private final MaterialService materialService;
    private final TechnologicalProcessService processService;
    private final OperationService operationService;

    /**
     * Initializing of the product edition command
     *
     * @param productService   - service of the product objects
     * @param materialService  - service of the material objects
     * @param processService   - service of the technological process objects
     * @param operationService - service of the operation objects
     */
    public DisplayProductEditionCommand(ProductService productService, MaterialService materialService,
                                        TechnologicalProcessService processService, OperationService operationService) {

        this.productService = productService;
        this.materialService = materialService;
        this.processService = processService;
        this.operationService = operationService;
    }

    /**
     * Executes displaying product edition
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long productId = Long.parseLong(request.getParameter(AppConstants.PARAM_PRODUCT_ID));
        Product product = productService.getById(productId);
        ProductDtoBuilder dtoBuilder = new BasicProductDtoBuilder(productService, materialService, processService);
        ProductDto productDto = buildProductDto(product, dtoBuilder);
        request.setAttribute(AppConstants.PARAM_PRODUCT_DTO, productDto);

        List<Material> materialList = materialService.getAll();
        request.setAttribute(AppConstants.PARAM_MATERIAL_LIST, materialList);

        List<Operation> operationList = operationService.getAll();
        request.setAttribute(AppConstants.PARAM_OPERATION_LIST, operationList);

        return "product_edition_view";
    }

    /**
     * Building product DTO
     *
     * @param product    - product with necessary fields
     * @param dtoBuilder - product DTO builder
     * @return product DTO
     */
    private ProductDto buildProductDto(Product product, ProductDtoBuilder dtoBuilder) throws DAOException {

        dtoBuilder.setId(product);
        dtoBuilder.setName(product);
        dtoBuilder.setWeight(product);
        dtoBuilder.setOperationQueue(product);
        dtoBuilder.setMaterial(product);
        dtoBuilder.setCost(product);
        return dtoBuilder.build();
    }
}

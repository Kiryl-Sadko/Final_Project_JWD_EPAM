package by.sadko.training.builder;

import by.sadko.training.SecurityContext;
import by.sadko.training.dto.ProductDto;
import by.sadko.training.entity.Material;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.Product;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.entity.UserRole;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.TechnologicalProcessService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static by.sadko.training.command.CommandUtil.getProfitRatio;

/**
 * Class of the basic implementation ProductDtoBuilder.
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ProductDtoBuilder
 */
public class BasicProductDtoBuilder implements ProductDtoBuilder {

    private final ProductService productService;
    private final MaterialService materialService;
    private final TechnologicalProcessService processService;

    private ProductDto productDto = new ProductDto();

    /**
     * Constructor initializes new object ContractDtoBuilder
     *
     * @param productService  - service of the product's objects
     * @param materialService -service of the material's objects
     * @param processService  - service of the process's objects
     */
    public BasicProductDtoBuilder(ProductService productService, MaterialService materialService,
                                  TechnologicalProcessService processService) {

        this.productService = productService;
        this.materialService = materialService;
        this.processService = processService;
    }

    /**
     * Sets product id to DTO object
     *
     * @param product - product with desired id
     */
    @Override
    public void setId(Product product) {

        Long id = product.getId();
        productDto.setId(id);
    }

    /**
     * Sets product name to DTO object
     *
     * @param product - product with desired name
     */
    @Override
    public void setName(Product product) {

        String name = product.getName();
        productDto.setName(name);
    }

    /**
     * Sets product weight to DTO object
     *
     * @param product - product with desired weight
     */
    @Override
    public void setWeight(Product product) {

        double weight = product.getWeight();
        productDto.setWeight(weight);
    }

    /**
     * Sets product material to DTO object
     *
     * @param product - product with desired material
     */
    @Override
    public void setMaterial(Product product) throws DAOException {

        Long materialID = product.getMaterialID();
        Material material = materialService.getById(materialID);
        productDto.setMaterialName(material.getName());
    }

    /**
     * Sets product operation queue to DTO object
     *
     * @param product - product with desired operation queue
     */
    @Override
    public void setOperationQueue(Product product) throws DAOException {

        Long processId = product.getTechnologicalProcessID();
        List<Operation> operationQueue = processService.getOperationQueue(processId);
        productDto.setOperationQueue(operationQueue);
    }

    /**
     * Sets product cost to DTO object
     *
     * @param product - product with desired cost
     */
    @Override
    public void setCost(Product product) throws DAOException {

        UserAccount currentUser = SecurityContext.getInstance().getCurrentUser();
        BigDecimal cost = productService.calculateProductCost(product.getId());

        if (currentUser.getUserRoles().contains(UserRole.CEO)) {
            productDto.setCost(cost);
        } else {
            double profitRatio = getProfitRatio();
            BigDecimal price = cost.multiply(BigDecimal.valueOf(profitRatio));
            price = price.setScale(2, RoundingMode.HALF_UP);

            productDto.setCost(price);
        }
    }

    /**
     * Returns copy of the ProductDto object and assigns new value to the productDto field
     *
     * @return copy of the ProductDto with defined fields
     */
    @Override
    public ProductDto build() {

        ProductDto copy = productDto;
        productDto = new ProductDto();
        return copy;
    }
}

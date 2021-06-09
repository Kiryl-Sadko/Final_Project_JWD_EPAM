package by.sadko.training.builder;

import by.sadko.training.dto.ProductDto;
import by.sadko.training.entity.Product;
import by.sadko.training.exception.DAOException;

/**
 * Interface describes creation methods ProductDto objects
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ProductDto,DtoBuilder
 */
public interface ProductDtoBuilder extends DtoBuilder<ProductDto> {

    void setId(Product product);

    void setName(Product product);

    void setWeight(Product product);

    void setMaterial(Product product) throws DAOException;

    void setOperationQueue(Product product) throws DAOException;

    void setCost(Product product) throws DAOException;
}

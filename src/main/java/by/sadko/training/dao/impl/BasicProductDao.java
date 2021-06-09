package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.ProductDao;
import by.sadko.training.entity.Product;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLProductParser;

import java.util.List;

/**
 * Class of the basic implementation ProductDao
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ProductDao,SQLProductParser,CRUDHandler
 */
public class BasicProductDao implements ProductDao {

    private static final String SELECT_ALL = "SELECT * FROM product";
    private static final String SELECT_BY_ID = "SELECT * FROM product WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM product WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO product " +
            "(product_name, product_weight, material_id, technological_process_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE product SET " +
            "product_name=?, product_weight=?, material_id=?, technological_process_id=?  WHERE id=?";

    private final ConnectionManager connectionManager;
    private final SQLProductParser productParser;
    private final CRUDHandler<Product> crudHandler;

    /**
     * Initialization of the command
     *
     * @param connectionManager - connection manager
     * @param productParser     - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicProductDao(ConnectionManager connectionManager, SQLProductParser productParser) {

        this.connectionManager = connectionManager;
        this.productParser = productParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, productParser);
    }

    /**
     * Selection all products from data base
     *
     * @return product list
     */
    @Override
    public List<Product> findAll() throws DAOException {

        return crudHandler.findAll(SELECT_ALL);
    }

    /**
     * Selection product from data base by id
     *
     * @param productId - product id
     * @return product
     */
    @Override
    public Product findById(Long productId) throws DAOException {

        return crudHandler.findById(SELECT_BY_ID, productId);
    }

    /**
     * Deletion product from data base
     *
     * @param productId -product id
     * @return boolean result of the deletion
     */
    @Override
    public boolean delete(Long productId) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, productId);
    }

    /**
     * Insertion product to data base
     *
     * @param product - product
     * @return product id from data base
     */
    @Override
    public Long create(Product product) throws DAOException {

        return crudHandler.create(INSERT_QUERY, product);
    }

    /**
     * Updating product in data base
     *
     * @param product - product
     * @return boolean result of the updating
     */
    @Override
    public boolean update(Product product) throws DAOException {

        return crudHandler.update(UPDATE_QUERY, product);
    }
}

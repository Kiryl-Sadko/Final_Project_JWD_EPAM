package by.sadko.training.service;

import by.sadko.training.entity.Material;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.Product;
import by.sadko.training.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product getById(Long id) throws DAOException;

    List<Product> getAll() throws DAOException;

    Long create(Product product, List<Operation> operationList) throws DAOException;

    boolean edit(Product product) throws DAOException;

    boolean delete(Product product) throws DAOException;

    boolean delete(Long id) throws DAOException;

    BigDecimal calculateProductCost(Long id) throws DAOException;

    double calculateProcessMinutes(Long id) throws DAOException;

    Material getMaterialByProduct(Long productId) throws DAOException;
}

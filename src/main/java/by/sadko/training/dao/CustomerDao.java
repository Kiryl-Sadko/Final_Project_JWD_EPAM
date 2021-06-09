package by.sadko.training.dao;

import by.sadko.training.entity.Customer;
import by.sadko.training.exception.DAOException;

/**
 * Interface describes behavior of the customer DAO
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface CustomerDao extends CRUDDao<Customer> {

    Customer findByName(String name) throws DAOException;
}

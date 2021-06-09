package by.sadko.training.service;

import by.sadko.training.entity.Customer;
import by.sadko.training.exception.DAOException;

public interface CustomerService {

    Long getByNameOrCreate(String name) throws DAOException;

    Customer getById(Long id) throws DAOException;
}

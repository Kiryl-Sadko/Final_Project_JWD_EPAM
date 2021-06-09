package by.sadko.training.service.impl;

import by.sadko.training.dao.CustomerDao;
import by.sadko.training.entity.Customer;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.CustomerService;

public class BasicCustomerService implements CustomerService {

    private final CustomerDao customerDAO;

    public BasicCustomerService(CustomerDao customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public Long getByNameOrCreate(String name) throws DAOException {

        Customer customer = customerDAO.findByName(name);
        if (customer == null) {
            customer = new Customer(name);
            return customerDAO.create(customer);
        }
        return customer.getId();
    }

    @Override
    public Customer getById(Long id) throws DAOException {
        return customerDAO.findById(id);
    }
}

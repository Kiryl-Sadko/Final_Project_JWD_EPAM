package by.sadko.training.service.impl;

import by.sadko.training.dao.impl.BasicOperationDao;
import by.sadko.training.entity.Operation;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.OperationService;

import java.util.List;

public class BasicOperationService implements OperationService {

    private final BasicOperationDao operationDAO;

    public BasicOperationService(BasicOperationDao operationDAO) {
        this.operationDAO = operationDAO;
    }

    @Override
    public List<Operation> getAll() throws DAOException {
        return operationDAO.findAll();
    }

    @Override
    public Operation getById(Long operationId) throws DAOException {
        return operationDAO.findById(operationId);
    }
}

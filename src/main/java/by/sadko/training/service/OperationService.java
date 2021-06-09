package by.sadko.training.service;

import by.sadko.training.entity.Operation;
import by.sadko.training.exception.DAOException;

import java.util.List;

public interface OperationService {

    List<Operation> getAll() throws DAOException;

    Operation getById(Long operationId) throws DAOException;
}

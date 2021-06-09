package by.sadko.training.service;

import by.sadko.training.entity.Operation;
import by.sadko.training.entity.TechnologicalProcess;
import by.sadko.training.exception.DAOException;

import java.util.List;

public interface TechnologicalProcessService {

    Long getByOperationOrCreate(List<Operation> operationQueue) throws DAOException;

    TechnologicalProcess getById(Long processId) throws DAOException;

    List<Operation> getOperationQueue(Long processId) throws DAOException;
}

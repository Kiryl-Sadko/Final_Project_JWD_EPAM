package by.sadko.training.dao;

import by.sadko.training.entity.Operation;
import by.sadko.training.entity.TechnologicalProcess;
import by.sadko.training.exception.DAOException;

import java.util.List;

/**
 * Interface describes behavior of the technological process DAO
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface TechnologicalProcessDao extends CRUDDao<TechnologicalProcess> {

    Long findByOperationList(List<Operation> operationList) throws DAOException;

    List<Operation> selectOperationQueue(Long id) throws DAOException;
}

package by.sadko.training.dao;

import by.sadko.training.entity.Contract;
import by.sadko.training.exception.DAOException;

import java.util.List;

/**
 * Interface describes behavior of the contract DAO
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface ContractDao extends CRUDDao<Contract> {

    List<Contract> findContractListByAccount(Long accountId) throws DAOException;
}

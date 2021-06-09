package by.sadko.training.service;

import by.sadko.training.entity.Contract;
import by.sadko.training.entity.Progress;
import by.sadko.training.exception.DAOException;

import java.util.Calendar;
import java.util.List;

public interface ContractService {

    Calendar calculateCompletionDate(Calendar paymentDate, Long productId, int quantity) throws DAOException;

    List<Contract> getAll() throws DAOException;

    Contract getById(Long id) throws DAOException;

    Long createContract(Long accountId, String customerName, Long productId, int productQuantity, double profitRatio)
            throws DAOException;

    boolean update(Contract contract) throws DAOException;

    boolean delete(Contract contract) throws DAOException;

    boolean delete(Long id) throws DAOException;

    boolean payContract(Long id) throws DAOException;

    boolean makeDiscount(Long contractId, double discount) throws DAOException;

    Progress getProgress(Long contractId) throws DAOException;

    boolean updateProgress(Progress progress) throws DAOException;

    List<Contract> getByAccountId(Long accountId) throws DAOException;
}

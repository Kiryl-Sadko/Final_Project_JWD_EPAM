package by.sadko.training.service;

import by.sadko.training.entity.Wallet;
import by.sadko.training.exception.DAOException;

import java.math.BigDecimal;

public interface WalletService {

    Long create(Wallet wallet) throws DAOException;

    boolean delete(Wallet wallet) throws DAOException;

    Wallet getById(Long id) throws DAOException;

    boolean increaseBalance(Long accountId, BigDecimal amount) throws DAOException;

    boolean reduceBalance(Long accountId, BigDecimal amount) throws DAOException;
}

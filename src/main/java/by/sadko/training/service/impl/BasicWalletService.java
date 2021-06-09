package by.sadko.training.service.impl;

import by.sadko.training.connection.Transactional;
import by.sadko.training.dao.UserAccountDao;
import by.sadko.training.dao.impl.BasicWalletDao;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.entity.Wallet;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class BasicWalletService implements WalletService {

    private static final Logger LOGGER = LogManager.getLogger(BasicWalletService.class);

    private final BasicWalletDao walletDAO;
    private final UserAccountDao userAccountDAO;

    public BasicWalletService(BasicWalletDao walletDAO, UserAccountDao userAccountDAO) {
        this.walletDAO = walletDAO;
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public Long create(Wallet wallet) throws DAOException {
        return walletDAO.create(wallet);
    }

    @Override
    public boolean delete(Wallet wallet) throws DAOException {
        return walletDAO.delete(wallet.getId());
    }

    @Override
    public Wallet getById(Long id) throws DAOException {
        return walletDAO.findById(id);
    }

    @Transactional
    @Override
    public boolean increaseBalance(Long accountId, BigDecimal amount) throws DAOException {

        UserAccount userAccount = userAccountDAO.findById(accountId);
        Long walletID = userAccount.getWalletId();
        Wallet wallet = walletDAO.findById(walletID);

        BigDecimal balance = wallet.getBalance();
        balance = balance.add(amount);
        wallet.setBalance(balance);

        return walletDAO.update(wallet);
    }

    @Transactional
    @Override
    public boolean reduceBalance(Long accountId, BigDecimal amount) throws DAOException {

        UserAccount userAccount = userAccountDAO.findById(accountId);
        Long walletID = userAccount.getWalletId();
        Wallet wallet = walletDAO.findById(walletID);
        BigDecimal balance = wallet.getBalance();

        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            wallet.setBalance(balance);

            return walletDAO.update(wallet);
        }

        LOGGER.info("Not enough money");
        return false;
    }
}

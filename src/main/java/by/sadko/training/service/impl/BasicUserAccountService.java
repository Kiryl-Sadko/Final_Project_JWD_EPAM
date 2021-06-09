package by.sadko.training.service.impl;

import by.sadko.training.connection.Transactional;
import by.sadko.training.dao.UserAccountDao;
import by.sadko.training.dao.WalletDao;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.entity.Wallet;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BasicUserAccountService implements UserAccountService {

    private static final Logger LOGGER = LogManager.getLogger(BasicUserAccountService.class);

    private final UserAccountDao userAccountDAO;
    private final WalletDao walletDAO;

    public BasicUserAccountService(UserAccountDao userAccountDAO, WalletDao walletDAO) {
        this.userAccountDAO = userAccountDAO;
        this.walletDAO = walletDAO;
    }

    @Override
    public UserAccount login(String email, String password) throws DAOException {

        UserAccount byEmail = userAccountDAO.findByEmail(email);
        Optional<UserAccount> optionalAccount = Optional.ofNullable(byEmail);

        if (optionalAccount.isPresent() &&
                optionalAccount.get().getPassword().equals(password)) {

            LOGGER.info("User Email={} is logged in", email);
            return optionalAccount.get();
        }

        LOGGER.info("Invalid email address or password");
        return null;
    }

    @Transactional
    @Override
    public Long signUp(UserAccount userAccount) throws DAOException {

        Long walletID = walletDAO.create(new Wallet());
        userAccount.setWalletID(walletID);
        Long accountId = userAccountDAO.create(userAccount);

        LOGGER.info("An account is created successfully");
        return accountId;
    }

    @Override
    public List<UserAccount> findAll() throws DAOException {
        return userAccountDAO.findAll();
    }

    @Transactional
    @Override
    public boolean deleteAccount(Long id) throws DAOException {

        UserAccount userAccount = userAccountDAO.findById(id);

        if (userAccount == null) {
            return true;
        }

        Long walletID = userAccount.getWalletId();
        walletDAO.delete(walletID);
        return userAccountDAO.delete(userAccount.getId());
    }

    @Override
    public UserAccount findById(Long id) throws DAOException {
        return userAccountDAO.findById(id);
    }

    @Override
    public UserAccount findByEmail(String email) throws DAOException {
        return userAccountDAO.findByEmail(email);
    }
}

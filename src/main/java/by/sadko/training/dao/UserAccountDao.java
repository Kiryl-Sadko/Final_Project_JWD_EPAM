package by.sadko.training.dao;

import by.sadko.training.entity.UserAccount;
import by.sadko.training.exception.DAOException;

/**
 * Interface describes behavior of the user account DAO
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface UserAccountDao extends CRUDDao<UserAccount> {

    UserAccount findByEmail(String login) throws DAOException;

    UserAccount findByWalletID(Long walletID) throws DAOException;
}

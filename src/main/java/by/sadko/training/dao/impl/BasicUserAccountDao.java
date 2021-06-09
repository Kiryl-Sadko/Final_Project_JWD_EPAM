package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.UserAccountDao;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.entity.UserRole;
import by.sadko.training.exception.ConnectionException;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLAccountParser;
import by.sadko.training.parser.SQLRoleParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Class of the basic implementation UserAccountDao
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see UserAccountDao,SQLAccountParser,CRUDHandler
 */
public class BasicUserAccountDao implements UserAccountDao {

    private static final Logger LOGGER = LogManager.getLogger(BasicUserAccountDao.class);

    private static final String SELECT_ALL_QUERY = "SELECT * FROM user_account";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM user_account WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO user_account " +
            "(user_name, user_password, user_email, is_active, wallet_id) VALUES (?,?,?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM user_account WHERE id=?";
    private static final String UPDATE = "UPDATE user_account SET user_name=?, user_password=?, user_email=?, " +
            "is_active=?, wallet_id=? WHERE id=?";
    private static final String SELECT_BY_EMAIL_QUERY = "SELECT * FROM user_account WHERE user_email=?";
    private static final String SELECT_BY_WALLET_ID_QUERY = "SELECT * FROM user_account WHERE wallet_id=?";

    private static final String SELECT_ROLE_QUERY = "SELECT user_role.id, user_role.role_name FROM user_role " +
            "JOIN account_role ON user_role.id=account_role.user_role_id WHERE account_role.user_account_id=?";

    private static final String INSERT_ROLE_QUERY = "INSERT INTO account_role " +
            "(user_account_id, user_role_id) VALUES(?,?)";

    private final ConnectionManager connectionManager;
    private final SQLAccountParser entityParser;
    private final CRUDHandler<UserAccount> crudHandler;

    /**
     * Initialization of the command
     *
     * @param connectionManager - connection manager
     * @param userAccountParser - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicUserAccountDao(ConnectionManager connectionManager, SQLAccountParser userAccountParser) {

        this.connectionManager = connectionManager;
        this.entityParser = userAccountParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, userAccountParser);
    }

    /**
     * Selection user account from data base by email
     *
     * @param email - account email
     * @return userAccount
     */
    @Override
    public UserAccount findByEmail(String email) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL_QUERY)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserAccount> userAccounts = entityParser.parseResultSetToEntityList(resultSet);

            if (!userAccounts.isEmpty()) {
                UserAccount accountByEmail = userAccounts.get(0);
                setUserRoles(accountByEmail);
                return accountByEmail;

            } else {
                LOGGER.info("The account with email={} is not founded", email);
                return null;
            }

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during account's selection by email");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Selection account by wallet id
     *
     * @param walletID = wallet id
     * @return userAccount
     */
    @Override
    public UserAccount findByWalletID(Long walletID) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_WALLET_ID_QUERY)) {

            preparedStatement.setLong(1, walletID);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserAccount> userAccounts = entityParser.parseResultSetToEntityList(resultSet);
            UserAccount userAccount = userAccounts.get(0);

            if (userAccount != null) {
                setUserRoles(userAccount);
                return userAccount;

            } else {
                LOGGER.info("The account with wallet ID={} is not founded", walletID);
                return null;
            }

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during user's selection by wallet");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Selection all user accounts
     *
     * @return List of the user account
     */
    @Override
    public List<UserAccount> findAll() throws DAOException {

        List<UserAccount> all = crudHandler.findAll(SELECT_ALL_QUERY);
        for (UserAccount userAccount : all) {
            setUserRoles(userAccount);
        }
        return all;
    }

    /**
     * Selection account by account id
     *
     * @param id - account id
     * @return userAccount
     */
    @Override
    public UserAccount findById(Long id) throws DAOException {

        Optional<UserAccount> optionalAccount = Optional.ofNullable(crudHandler.findById(SELECT_BY_ID_QUERY, id));
        if (optionalAccount.isPresent()) {
            setUserRoles(optionalAccount.get());
            return optionalAccount.get();
        }
        return null;
    }

    /**
     * Removing user account from data base by user account id
     *
     * @param id - user account id
     * @return boolean result of the removing
     */
    @Override
    public boolean delete(Long id) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, id);
    }

    /**
     * Insertion user account to data base
     *
     * @param entity - user account
     * @return user account id
     */
    @Override
    public Long create(UserAccount entity) throws DAOException {

        List<UserRole> userRoles = entity.getUserRoles();
        Long accountId = crudHandler.create(INSERT_QUERY, entity);
        UserAccount userAccount = findById(accountId);

        for (UserRole role : userRoles) {
            setAccountRole(userAccount.getId(), role.getId());
        }
        return accountId;
    }

    /**
     * Updating user account in data base
     *
     * @param entity - user account
     * @return boolean result of the updating
     */
    @Override
    public boolean update(UserAccount entity) throws DAOException {

        return crudHandler.update(UPDATE, entity);
    }

    /**
     * Setting role from data base to the user account
     *
     * @param accountId - user account id fore setting role
     * @param roleId    - role id
     */
    private void setAccountRole(Long accountId, Long roleId) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE_QUERY)) {

            preparedStatement.setLong(1, accountId);
            preparedStatement.setLong(2, roleId);
            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during setting role to account_role table");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Setting user roles from data base to user account
     *
     * @param entity - user account
     */
    private void setUserRoles(UserAccount entity) throws DAOException {

        List<UserRole> accountRoles = selectUserRoles(entity);
        entity.setUserRoles(accountRoles);
    }

    /**
     * Selection user roles from data base by user account
     *
     * @param entity - user account
     * @return List of the user account roles
     */
    private List<UserRole> selectUserRoles(UserAccount entity) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_QUERY)) {

            preparedStatement.setLong(1, entity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            SQLRoleParser roleParser = new SQLRoleParser();

            return roleParser.parseResultSetToEntityList(resultSet);

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during user's roles selection");
            throw new DAOException(exception.getMessage(), exception);
        }
    }
}

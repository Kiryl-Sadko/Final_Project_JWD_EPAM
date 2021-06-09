package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.WalletDao;
import by.sadko.training.entity.Wallet;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLWalletParser;

import java.util.List;

/**
 * Class of the basic implementation WalletDao
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see WalletDao,SQLWalletParser,CRUDHandler
 */
public class BasicWalletDao implements WalletDao {

    private static final String SELECT_ALL_QUERY = "SELECT * FROM wallet";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM wallet WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO wallet (wallet_balance) VALUES (?)";
    private static final String DELETE_QUERY = "DELETE FROM wallet WHERE id=?";
    private static final String UPDATE = "UPDATE wallet SET wallet_balance=? WHERE id=?";

    private final ConnectionManager connectionManager;
    private final SQLWalletParser entityParser;
    private final CRUDHandler<Wallet> crudHandler;

    /**
     * Initialization of the command
     *
     * @param connectionManager - connection manager
     * @param walletParser      - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicWalletDao(ConnectionManager connectionManager, SQLWalletParser walletParser) {

        this.connectionManager = connectionManager;
        this.entityParser = walletParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, walletParser);
    }

    /**
     * Selection all wallets from data base
     *
     * @return - List of the wallets
     */
    @Override
    public List<Wallet> findAll() throws DAOException {

        return crudHandler.findAll(SELECT_ALL_QUERY);
    }

    /**
     * Selection wallet from data base by id
     *
     * @param id - wallet id
     * @return wallet
     */
    @Override
    public Wallet findById(Long id) throws DAOException {

        return crudHandler.findById(SELECT_BY_ID_QUERY, id);
    }

    /**
     * Removing wallet from data base by id
     *
     * @param id - wallet id
     * @return boolean result of the removing
     */
    @Override
    public boolean delete(Long id) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, id);
    }

    /**
     * Insertion wallet to data base
     *
     * @param wallet - wallet
     * @return wallet id
     */
    @Override
    public Long create(Wallet wallet) throws DAOException {

        return crudHandler.create(INSERT_QUERY, wallet);
    }

    /**
     * Updating wallet in data base
     *
     * @param wallet - wallet
     * @return boolean result of the updating
     */
    @Override
    public boolean update(Wallet wallet) throws DAOException {

        return crudHandler.update(UPDATE, wallet);
    }
}

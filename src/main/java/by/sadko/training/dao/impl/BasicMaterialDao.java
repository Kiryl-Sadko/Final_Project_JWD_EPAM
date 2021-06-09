package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.CustomerDao;
import by.sadko.training.dao.MaterialDao;
import by.sadko.training.entity.Material;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLMaterialParser;

import java.util.List;

/**
 * Class of the basic implementation MaterialDao
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see CustomerDao,SQLMaterialParser,CRUDHandler
 */
public class BasicMaterialDao implements MaterialDao {

    private static final String SELECT_ALL = "SELECT * FROM material";
    private static final String SELECT_BY_ID = "SELECT * FROM material WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM material WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO material (material_name, material_cost, material_delivery_time) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE material SET material_name=?, material_cost=?, material_delivery_time=? WHERE id=?";

    private final ConnectionManager connectionManager;
    private final SQLMaterialParser materialParser;
    private final CRUDHandler<Material> crudHandler;

    /**
     * Initialization of the command
     *
     * @param connectionManager - connection manager
     * @param materialParser    - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicMaterialDao(ConnectionManager connectionManager, SQLMaterialParser materialParser) {
        this.connectionManager = connectionManager;
        this.materialParser = materialParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, materialParser);
    }

    /**
     * Selection all materials from data base
     *
     * @return list of the material
     */
    @Override
    public List<Material> findAll() throws DAOException {

        return crudHandler.findAll(SELECT_ALL);
    }

    /**
     * Selection material by id
     *
     * @param materialId - material id
     * @return material
     */
    @Override
    public Material findById(Long materialId) throws DAOException {

        return crudHandler.findById(SELECT_BY_ID, materialId);
    }

    /**
     * Deletion material from data base by id
     *
     * @param materialId - material id
     * @return boolean result of the deletion
     */
    @Override
    public boolean delete(Long materialId) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, materialId);
    }

    /**
     * Insertion material to data base
     *
     * @param material - material
     * @return material id from data base
     */
    @Override
    public Long create(Material material) throws DAOException {

        return crudHandler.create(INSERT_QUERY, material);
    }

    /**
     * Updating material in data base
     *
     * @param material - material
     * @return boolean result of the updating
     */
    @Override
    public boolean update(Material material) throws DAOException {

        return crudHandler.update(UPDATE_QUERY, material);
    }
}

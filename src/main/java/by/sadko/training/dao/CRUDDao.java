package by.sadko.training.dao;

import by.sadko.training.exception.DAOException;

import java.util.List;

/**
 * Interface describes behavior of the CRUD DAO
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface CRUDDao<T> {

    List<T> findAll() throws DAOException;

    T findById(Long id) throws DAOException;

    boolean delete(Long id) throws DAOException;

    Long create(T entity) throws DAOException;

    boolean update(T entity) throws DAOException;
}

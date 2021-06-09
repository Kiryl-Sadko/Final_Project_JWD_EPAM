package by.sadko.training.service;

import by.sadko.training.entity.Material;
import by.sadko.training.exception.DAOException;

import java.util.List;

public interface MaterialService {

    List<Material> getAll() throws DAOException;

    Material getById(Long materialId) throws DAOException;

    Long create(Material material) throws DAOException;

    boolean delete(Long materialId) throws DAOException;

    boolean edit(Material material) throws DAOException;
}

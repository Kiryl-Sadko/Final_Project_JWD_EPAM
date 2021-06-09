package by.sadko.training.service.impl;

import by.sadko.training.dao.impl.BasicMaterialDao;
import by.sadko.training.entity.Material;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;

import java.util.List;

public class BasicMaterialService implements MaterialService {

    private final BasicMaterialDao materialDAO;

    public BasicMaterialService(BasicMaterialDao materialDAO) {
        this.materialDAO = materialDAO;
    }

    @Override
    public List<Material> getAll() throws DAOException {

        return materialDAO.findAll();
    }

    @Override
    public Material getById(Long materialId) throws DAOException {

        return materialDAO.findById(materialId);
    }

    @Override
    public Long create(Material material) throws DAOException {

        return materialDAO.create(material);
    }

    @Override
    public boolean delete(Long materialId) throws DAOException {

        return materialDAO.delete(materialId);
    }

    @Override
    public boolean edit(Material material) throws DAOException {

        return materialDAO.update(material);
    }
}

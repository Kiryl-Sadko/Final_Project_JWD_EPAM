package by.sadko.training.service.impl;

import by.sadko.training.connection.Transactional;
import by.sadko.training.dao.impl.BasicMaterialDao;
import by.sadko.training.dao.impl.BasicProductDao;
import by.sadko.training.dao.impl.BasicTechnologicalProcessDao;
import by.sadko.training.entity.Material;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.Product;
import by.sadko.training.entity.TechnologicalProcess;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class BasicProductService implements ProductService {

    private static final Logger LOGGER = LogManager.getLogger(BasicProductService.class);

    private final BasicProductDao productDAO;
    private final BasicMaterialDao materialDAO;
    private final BasicTechnologicalProcessDao technologicalProcessDAO;

    public BasicProductService(BasicProductDao productDAO, BasicMaterialDao materialDAO,
                               BasicTechnologicalProcessDao technologicalProcessDAO) {
        this.productDAO = productDAO;
        this.materialDAO = materialDAO;
        this.technologicalProcessDAO = technologicalProcessDAO;
    }

    @Override
    public Product getById(Long id) throws DAOException {
        return productDAO.findById(id);
    }

    @Override
    public List<Product> getAll() throws DAOException {
        return productDAO.findAll();
    }

    @Transactional
    @Override
    public Long create(Product product, List<Operation> operationQueue) throws DAOException {

        Optional<Long> optionalProcessId = Optional.ofNullable
                (technologicalProcessDAO.findByOperationList(operationQueue));

        if (optionalProcessId.isPresent()) {
            product.setTechnologicalProcessID(optionalProcessId.get());

        } else {
            StringBuilder processName = new StringBuilder();

            for (Operation operation : operationQueue) {
                String operationName = operation.getType().getName();
                processName.append(operationName);
            }

            TechnologicalProcess process = new TechnologicalProcess(processName.toString(), operationQueue);
            Long processId = technologicalProcessDAO.create(process);
            product.setTechnologicalProcessID(processId);
        }

        return productDAO.create(product);
    }

    @Override
    public boolean edit(Product product) throws DAOException {
        return productDAO.update(product);
    }

    @Override
    public boolean delete(Product product) throws DAOException {
        return productDAO.delete(product.getId());
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        return productDAO.delete(id);
    }

    @Transactional
    @Override
    public BigDecimal calculateProductCost(Long id) throws DAOException {

        BigDecimal totalCost = new BigDecimal(0);
        Product product = productDAO.findById(id);

        Long materialID = product.getMaterialID();
        Material material = materialDAO.findById(materialID);
        BigDecimal materialCost = material.getCost();
        totalCost = totalCost.add(materialCost);

        Long technologicalProcessID = product.getTechnologicalProcessID();
        TechnologicalProcess technologicalProcess = technologicalProcessDAO.findById(technologicalProcessID);
        List<Operation> operationQueue = technologicalProcess.getOperationQueue();

        for (Operation operation : operationQueue) {
            BigDecimal operationCost = operation.getCost();
            totalCost = totalCost.add(operationCost);
        }

        double weight = product.getWeight();
        totalCost = totalCost.multiply(BigDecimal.valueOf(weight));
        totalCost = totalCost.setScale(2, RoundingMode.HALF_EVEN);

        return totalCost;
    }

    @Transactional
    @Override
    public double calculateProcessMinutes(Long id) throws DAOException {

        double readinessHours = 0;
        Product product = productDAO.findById(id);

        //deliveryTime
        Long materialID = product.getMaterialID();
        Material material = materialDAO.findById(materialID);
        double deliveryTime = material.getDeliveryTime();
        readinessHours += deliveryTime;

        //technological process time
        Long technologicalProcessID = product.getTechnologicalProcessID();
        TechnologicalProcess technologicalProcess = technologicalProcessDAO.findById(technologicalProcessID);
        List<Operation> operationQueue = technologicalProcess.getOperationQueue();
        for (Operation operation : operationQueue) {
            double operationTime = operation.getTime();
            readinessHours += operationTime;
        }

        //convert hours to minutes
        return readinessHours * 60;
    }

    @Transactional
    @Override
    public Material getMaterialByProduct(Long productId) throws DAOException {

        Product product = productDAO.findById(productId);
        Long materialId = product.getMaterialID();
        return materialDAO.findById(materialId);
    }
}

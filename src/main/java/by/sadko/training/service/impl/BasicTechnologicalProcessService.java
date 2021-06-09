package by.sadko.training.service.impl;

import by.sadko.training.dao.TechnologicalProcessDao;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.TechnologicalProcess;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.TechnologicalProcessService;

import java.util.List;
import java.util.Optional;

public class BasicTechnologicalProcessService implements TechnologicalProcessService {

    private final TechnologicalProcessDao technologicalProcessDAO;

    public BasicTechnologicalProcessService(TechnologicalProcessDao technologicalProcessDAO) {
        this.technologicalProcessDAO = technologicalProcessDAO;
    }

    @Override
    public Long getByOperationOrCreate(List<Operation> operationQueue) throws DAOException {

        Optional<Long> optionalId = Optional.ofNullable(technologicalProcessDAO.findByOperationList(operationQueue));

        if (!optionalId.isPresent()) {

            String name = generateName(operationQueue);
            TechnologicalProcess technologicalProcess = new TechnologicalProcess(name, operationQueue);

            return technologicalProcessDAO.create(technologicalProcess);

        } else {
            return optionalId.get();
        }
    }

    @Override
    public TechnologicalProcess getById(Long processId) throws DAOException {
        return technologicalProcessDAO.findById(processId);
    }

    @Override
    public List<Operation> getOperationQueue(Long processId) throws DAOException {
        return technologicalProcessDAO.selectOperationQueue(processId);
    }

    private String generateName(List<Operation> operationList) {

        StringBuilder name = new StringBuilder();

        operationList.forEach(operation -> {
            char firstChar = operation.getType().getName().charAt(0);
            name.append(firstChar);
        });

        return String.valueOf(name);
    }
}

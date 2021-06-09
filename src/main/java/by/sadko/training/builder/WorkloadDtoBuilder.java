package by.sadko.training.builder;

import by.sadko.training.dto.WorkloadDto;
import by.sadko.training.entity.Contract;
import by.sadko.training.exception.DAOException;

/**
 * Interface describes creation methods WorkloadDto objects
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see WorkloadDto,DtoBuilder
 */
public interface WorkloadDtoBuilder extends DtoBuilder<WorkloadDto> {

    void setUserName(Contract contract) throws DAOException;

    void setProgress(Contract contract) throws DAOException;

    void setContractId(Contract contract);

    void setCustomerName(Contract contract) throws DAOException;

    void setProductName(Contract contract) throws DAOException;

    void setProductQuantity(Contract contract);

    void setPaymentDate(Contract contract);

    void setCompletionDate(Contract contract);

    void setCost(Contract contract) throws DAOException;

    void setPrice(Contract contract);

    void setStatus(Contract contract);
}

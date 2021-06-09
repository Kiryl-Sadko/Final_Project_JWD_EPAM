package by.sadko.training.builder;

import by.sadko.training.dto.ContractDto;
import by.sadko.training.entity.Contract;
import by.sadko.training.exception.DAOException;

/**
 * Interface describes creation methods ContractDto objects
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ContractDto,DtoBuilder
 */
public interface ContractDtoBuilder extends DtoBuilder<ContractDto> {

    void setContractId(Contract contract);

    void setCustomerName(Contract contract) throws DAOException;

    void setProductName(Contract contract) throws DAOException;

    void setProductQuantity(Contract contract);

    void setPaymentDate(Contract contract);

    void setCompletionDate(Contract contract);

    void setPrice(Contract contract);

    void setStatus(Contract contract);
}

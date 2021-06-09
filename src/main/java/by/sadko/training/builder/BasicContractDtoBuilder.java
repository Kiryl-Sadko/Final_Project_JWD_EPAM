package by.sadko.training.builder;

import by.sadko.training.dto.ContractDto;
import by.sadko.training.entity.Contract;
import by.sadko.training.entity.ContractStatus;
import by.sadko.training.entity.Customer;
import by.sadko.training.entity.Product;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.CustomerService;
import by.sadko.training.service.ProductService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class of the basic implementation ContractDtoBuilder.
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ContractDtoBuilder
 */
public class BasicContractDtoBuilder implements ContractDtoBuilder {

    private final CustomerService customerService;
    private final ProductService productService;

    private ContractDto contractDto = new ContractDto();

    /**
     * Constructor initializes new object ContractDtoBuilder
     *
     * @param customerService - service of the customer's objects
     * @param productService  - service of the product's objects
     */
    public BasicContractDtoBuilder(CustomerService customerService, ProductService productService) {

        this.customerService = customerService;
        this.productService = productService;
    }

    /**
     * Sets contract id to DTO object
     *
     * @param contract - contract with desired id
     */
    @Override
    public void setContractId(Contract contract) {

        Long contractId = contract.getId();
        contractDto.setContractId(contractId);
    }

    /**
     * Sets customer name to DTO object
     *
     * @param contract - contract with desired customer
     */
    @Override
    public void setCustomerName(Contract contract) throws DAOException {

        Long customerId = contract.getCustomerId();
        Customer customer = customerService.getById(customerId);
        String customerName = customer.getName();
        contractDto.setCustomerName(customerName);
    }

    /**
     * Sets product name to DTO object
     *
     * @param contract - contract with desired product
     */
    @Override
    public void setProductName(Contract contract) throws DAOException {

        Long productId = contract.getProductId();
        Product product = productService.getById(productId);
        String productName = product.getName();
        contractDto.setProductName(productName);
    }

    /**
     * Sets quantity of the product to DTO object
     *
     * @param contract - contract with desired quantity of the product
     */
    @Override
    public void setProductQuantity(Contract contract) {

        int quantity = contract.getQuantity();
        contractDto.setProductQuantity(quantity);
    }

    /**
     * Sets payment date to DTO object
     *
     * @param contract - contract with desired date
     */
    @Override
    public void setPaymentDate(Contract contract) {

        Calendar paymentDate = contract.getPaymentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String paymentTime = dateFormat.format(paymentDate.getTime());
        contractDto.setPaymentDate(paymentTime);
    }

    /**
     * Sets completion date to DTO object
     *
     * @param contract - contract with desired date
     */
    @Override
    public void setCompletionDate(Contract contract) {

        Calendar completionDate = contract.getCompletionDate();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String completionTime = dateFormat.format(completionDate.getTime());
        contractDto.setCompletionDate(completionTime);
    }

    /**
     * Sets contract price to DTO object
     *
     * @param contract - contract with desired price
     */
    @Override
    public void setPrice(Contract contract) {

        BigDecimal price = contract.getPrice();
        contractDto.setPrice(price);
    }

    /**
     * Sets contract status to DTO object
     *
     * @param contract - contract with desired status
     */
    @Override
    public void setStatus(Contract contract) {

        ContractStatus status = contract.getStatus();
        contractDto.setStatus(status.getStatus());
    }

    /**
     * Returns copy of the contractDto object and assigns new value to the contractDto field
     *
     * @return copy of the contractDto with defined fields
     */
    @Override
    public ContractDto build() {

        ContractDto copy = contractDto;
        contractDto = new ContractDto();
        return copy;
    }
}

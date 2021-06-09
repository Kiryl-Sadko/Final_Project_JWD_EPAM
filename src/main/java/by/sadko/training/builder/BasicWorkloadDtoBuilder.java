package by.sadko.training.builder;

import by.sadko.training.dto.WorkloadDto;
import by.sadko.training.entity.Contract;
import by.sadko.training.entity.ContractStatus;
import by.sadko.training.entity.Customer;
import by.sadko.training.entity.Product;
import by.sadko.training.entity.Progress;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ContractService;
import by.sadko.training.service.CustomerService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.UserAccountService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class of the basic implementation WorkloadDtoBuilder.
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see WorkloadDtoBuilder
 */
public class BasicWorkloadDtoBuilder implements WorkloadDtoBuilder {

    private final CustomerService customerService;
    private final ProductService productService;
    private final UserAccountService userAccountService;
    private final ContractService contractService;
    private WorkloadDto workloadDto = new WorkloadDto();


    /**
     * Constructor initializes new object ContractDtoBuilder
     *
     * @param customerService    - service of the customer's objects
     * @param productService     - service of the product's objects
     * @param userAccountService -service of the user account's objects
     * @param contractService    - service of the contract's objects
     */
    public BasicWorkloadDtoBuilder(CustomerService customerService, ProductService productService,
                                   UserAccountService userAccountService, ContractService contractService) {

        this.customerService = customerService;
        this.productService = productService;
        this.userAccountService = userAccountService;
        this.contractService = contractService;
    }

    /**
     * Sets contract name to DTO object
     *
     * @param contract - contract with desired name
     */
    @Override
    public void setUserName(Contract contract) throws DAOException {

        Long accountId = contract.getUserAccountID();
        UserAccount userAccount = userAccountService.findById(accountId);
        String userName = userAccount.getName();
        workloadDto.setUserName(userName);
    }

    /**
     * Sets contract progress to DTO object
     *
     * @param contract - contract with desired progress
     */
    @Override
    public void setProgress(Contract contract) throws DAOException {

        Long contractId = contract.getId();
        Progress progress = contractService.getProgress(contractId);
        int percentages = progress.getStatus();
        String executionProgress = percentages + "%";
        workloadDto.setProgress(executionProgress);
    }

    /**
     * Sets contract id to DTO object
     *
     * @param contract - contract with desired id
     */
    @Override
    public void setContractId(Contract contract) {

        Long contractId = contract.getId();
        workloadDto.setContractId(contractId);
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
        workloadDto.setCustomerName(customerName);
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
        workloadDto.setProductName(productName);
    }

    /**
     * Sets product quantity to DTO object
     *
     * @param contract - contract with desired product
     */
    @Override
    public void setProductQuantity(Contract contract) {

        int quantity = contract.getQuantity();
        workloadDto.setProductQuantity(quantity);
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
        workloadDto.setPaymentDate(paymentTime);
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
        workloadDto.setCompletionDate(completionTime);
    }

    /**
     * Sets contract cost to DTO object
     *
     * @param contract - contract with desired cost
     */
    @Override
    public void setCost(Contract contract) throws DAOException {

        Long productId = contract.getProductId();
        BigDecimal productCost = productService.calculateProductCost(productId);
        int productQuantity = contract.getQuantity();
        BigDecimal totalCost = productCost.multiply(BigDecimal.valueOf(productQuantity));
        workloadDto.setCost(totalCost);
    }

    /**
     * Sets contract price to DTO object
     *
     * @param contract - contract with desired price
     */
    @Override
    public void setPrice(Contract contract) {

        BigDecimal price = contract.getPrice();
        workloadDto.setPrice(price);
    }

    /**
     * Sets contract status to DTO object
     *
     * @param contract - contract with desired status
     */
    @Override
    public void setStatus(Contract contract) {

        ContractStatus status = contract.getStatus();
        workloadDto.setStatus(status.getStatus());
    }

    /**
     * Return copy of the workloadDto object and assigns new value to the workloadDto field
     *
     * @return copy of the workloadDto with defined fields
     */
    @Override
    public WorkloadDto build() {

        WorkloadDto copy = workloadDto;
        workloadDto = new WorkloadDto();
        return copy;
    }
}

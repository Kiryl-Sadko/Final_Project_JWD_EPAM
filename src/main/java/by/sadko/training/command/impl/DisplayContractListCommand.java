package by.sadko.training.command.impl;

import by.sadko.training.SecurityContext;
import by.sadko.training.builder.BasicContractDtoBuilder;
import by.sadko.training.builder.ContractDtoBuilder;
import by.sadko.training.command.AppCommand;
import by.sadko.training.dto.ContractDto;
import by.sadko.training.entity.Contract;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ContractService;
import by.sadko.training.service.CustomerService;
import by.sadko.training.service.ProductService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.sadko.training.command.CommandUtil.*;
import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the displaying contract list command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ContractService,CustomerService,ProductService
 */
public class DisplayContractListCommand implements AppCommand {

    private final ContractService contractService;
    private final CustomerService customerService;
    private final ProductService productService;

    /**
     * Initializing of the command
     *
     * @param contractService - service of the contract objects
     * @param customerService - service of the customer objects
     * @param productService  - service of the product objects
     */
    public DisplayContractListCommand(ContractService contractService, CustomerService customerService,
                                      ProductService productService) {

        this.contractService = contractService;
        this.customerService = customerService;
        this.productService = productService;
    }

    /**
     * Executes displaying contract list
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        UserAccount currentUser = SecurityContext.getInstance().getCurrentUser();
        List<Contract> contractList = contractService.getByAccountId(currentUser.getId());

        ContractDtoBuilder contractBuilder = new BasicContractDtoBuilder(customerService, productService);

        setPageQuantity(request, contractList);

        int pageNumber = setPageNumber(request);

        setContractList(request, contractList, contractBuilder, pageNumber);

        Optional<String> optionalResult = Optional.ofNullable(request.getParameter(AppConstants.PARAM_COMMAND_RESULT));
        optionalResult.ifPresent(s -> request.setAttribute(PARAM_MESSAGE, s));

        return "contract_list_view";
    }

    /**
     * Setting request attribute - contract list
     *
     * @param request         - request
     * @param contractList    - contract list
     * @param contractBuilder - contract DTO builder
     * @param pageNumber      - page number
     */
    private void setContractList(HttpServletRequest request, List<Contract> contractList,
                                 ContractDtoBuilder contractBuilder, int pageNumber) throws DAOException {

        List<ContractDto> result = new ArrayList<>();
        for (int i = (pageNumber - 1) * 5; i < pageNumber * 5 && i < contractList.size(); i++) {

            Contract contract = contractList.get(i);
            ContractDto contractDto = buildContractDto(contractBuilder, contract);
            result.add(contractDto);
        }

        request.setAttribute(PARAM_USER_CONTRACT_LIST, result);
    }

    /**
     * Building contract DTO
     *
     * @param contractBuilder - builder of the contract DTO
     * @param contract        - contract with necessary fields
     * @return contract DTO
     */
    private ContractDto buildContractDto(ContractDtoBuilder contractBuilder, Contract contract) throws DAOException {

        contractBuilder.setContractId(contract);
        contractBuilder.setCustomerName(contract);
        contractBuilder.setProductName(contract);
        contractBuilder.setProductQuantity(contract);
        contractBuilder.setPaymentDate(contract);
        contractBuilder.setCompletionDate(contract);
        contractBuilder.setPrice(contract);
        contractBuilder.setStatus(contract);
        return contractBuilder.build();
    }
}

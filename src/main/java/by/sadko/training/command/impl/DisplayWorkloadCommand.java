package by.sadko.training.command.impl;

import by.sadko.training.builder.BasicWorkloadDtoBuilder;
import by.sadko.training.builder.WorkloadDtoBuilder;
import by.sadko.training.command.AppCommand;
import by.sadko.training.dto.WorkloadDto;
import by.sadko.training.entity.Contract;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ContractService;
import by.sadko.training.service.CustomerService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.UserAccountService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.sadko.training.command.CommandUtil.*;
import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the displaying workload command with fields <b>userAccountService</b>, <b>contractService</b>,
 * <b>customerService</b> and <b>productService</b>
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,UserAccountService,ContractService,CustomerService,ProductService
 */
public class DisplayWorkloadCommand implements AppCommand {

    private final UserAccountService userAccountService;
    private final ContractService contractService;
    private final CustomerService customerService;
    private final ProductService productService;

    /**
     * Initialization of the displaying workload command
     *
     * @param userAccountService - service of the user account objects
     * @param contractService    - service of the contract objects
     * @param customerService    - service of the customer objects
     * @param productService     - service of the product objects
     */
    public DisplayWorkloadCommand(UserAccountService userAccountService, ContractService contractService,
                                  CustomerService customerService, ProductService productService) {

        this.userAccountService = userAccountService;
        this.contractService = contractService;
        this.customerService = customerService;
        this.productService = productService;
    }

    /**
     * Executes displaying list of the workload DTO
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        List<Contract> contractList = contractService.getAll();

        WorkloadDtoBuilder workloadBuilder = new BasicWorkloadDtoBuilder(customerService, productService,
                userAccountService, contractService);

        setPageQuantity(request, contractList);

        int pageNumber = setPageNumber(request);

        setWorkloadList(request, contractList, workloadBuilder, pageNumber);

        Optional<String> optionalResult = Optional.ofNullable(request.getParameter(AppConstants.PARAM_COMMAND_RESULT));
        optionalResult.ifPresent(s -> request.setAttribute(PARAM_MESSAGE, s));

        return "workload_view";
    }

    /**
     * Setting request attribute - contract list
     *
     * @param request            - request
     * @param contractList       - contract list
     * @param workloadDtoBuilder - contract DTO builder
     * @param pageNumber         - page number
     */
    private void setWorkloadList(HttpServletRequest request, List<Contract> contractList,
                                 WorkloadDtoBuilder workloadDtoBuilder, int pageNumber) throws DAOException {

        List<WorkloadDto> result = new ArrayList<>();
        for (int i = (pageNumber - 1) * 5; i < pageNumber * 5 && i < contractList.size(); i++) {

            Contract contract = contractList.get(i);
            WorkloadDto workloadDto = buildWorkloadDto(workloadDtoBuilder, contract);
            result.add(workloadDto);
        }

        request.setAttribute(PARAM_WORKLOAD_LIST, result);
    }

    /**
     * Building workload DTO
     *
     * @param builder  - DTO builder
     * @param contract - contract with necessary fields
     * @return workload DTO
     */
    private WorkloadDto buildWorkloadDto(WorkloadDtoBuilder builder, Contract contract) throws DAOException {

        builder.setUserName(contract);
        builder.setContractId(contract);
        builder.setCompletionDate(contract);
        builder.setCost(contract);
        builder.setStatus(contract);
        builder.setProgress(contract);
        builder.setProductQuantity(contract);
        builder.setProductName(contract);
        builder.setPrice(contract);
        builder.setPaymentDate(contract);
        builder.setCustomerName(contract);
        return builder.build();
    }
}

package by.sadko.training.command.impl;

import by.sadko.training.SecurityContext;
import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ContractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.sadko.training.command.CommandUtil.getProfitRatio;
import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the contract creation command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ContractService
 */
public class CreateContractCommand implements AppCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateContractCommand.class);

    private final ContractService contractService;

    /**
     * Initializing of the command
     *
     * @param contractService - service of the contract objects
     */
    public CreateContractCommand(ContractService contractService) {

        this.contractService = contractService;
    }

    /**
     * Executes contract creation in data base
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        UserAccount currentUser = SecurityContext.getInstance().getCurrentUser();
        Long userId = currentUser.getId();
        String customerName = request.getParameter(PARAM_CUSTOMER_NAME);

        Long productId = Long.parseLong(request.getParameter(PARAM_PRODUCT_ID));
        int productQuantity = Integer.parseInt(request.getParameter(PARAM_PRODUCT_QUANTITY));
        double profitRatio = getProfitRatio();

        Long contractId = contractService.createContract(userId, customerName, productId, productQuantity, profitRatio);

        return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.CONTRACT_DISPLAY_ALL
                + "&" + PARAM_COMMAND_RESULT + "=" + "Contract id=" + contractId + " is created";
    }
}

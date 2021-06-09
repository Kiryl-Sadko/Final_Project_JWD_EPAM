package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ContractService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the discount command with fields <b>contractService</b>
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ContractService
 */
public class MakeDiscountCommand implements AppCommand {

    private final ContractService contractService;

    /**
     * Constructor initializes discount command
     *
     * @param contractService - service of the contract objects
     */
    public MakeDiscountCommand(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * Executes discount to the contract
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long contractId = Long.parseLong(request.getParameter(AppConstants.PARAM_CONTRACT_ID));
        double discount = Double.parseDouble(request.getParameter(AppConstants.PARAM_DISCOUNT));

        contractService.makeDiscount(contractId, discount / 100);

        return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.WORKLOAD_DISPLAY_ALL
                + "&" + PARAM_COMMAND_RESULT + "=" + "A discount is made on the contract ID=" + contractId;
    }
}

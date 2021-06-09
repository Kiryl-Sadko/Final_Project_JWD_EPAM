package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ContractService;
import by.sadko.training.util.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the contract deletion command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ContractService
 */
public class DeleteContractCommand implements AppCommand {

    private static final Logger LOGGER = LogManager.getLogger(DeleteContractCommand.class);

    private final ContractService contractService;

    /**
     * Initializing of the contract deletion command
     *
     * @param contractService - service of the contract objects
     */
    public DeleteContractCommand(ContractService contractService) {

        this.contractService = contractService;
    }

    /**
     * Executes contract deletion from data base
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long contractId = Long.parseLong(request.getParameter(AppConstants.PARAM_CONTRACT_ID));
        boolean isDeleted = contractService.delete(contractId);

        if (isDeleted) {
            LOGGER.info("Contract id={} is deleted", contractId);
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.CONTRACT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=Contract id=" + contractId + " is deleted";

        } else {
            LOGGER.info("Contract id={} isn't deleted", contractId);
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.CONTRACT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=Contract id=" + contractId + " isn't deleted, try letter";
        }
    }
}

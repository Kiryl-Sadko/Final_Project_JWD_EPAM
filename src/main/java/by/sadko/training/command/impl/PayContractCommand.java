package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.Contract;
import by.sadko.training.entity.ContractStatus;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ContractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the contract payment command with fields <b>contractService</b>
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,ContractService
 */
public class PayContractCommand implements AppCommand {

    private static final Logger LOGGER = LogManager.getLogger(PayContractCommand.class);
    private final ContractService contractService;

    /**
     * Constructor initializes contract payment command
     *
     * @param contractService - service of the contract object's
     */
    public PayContractCommand(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * Executes payment of the contract
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long contractId = Long.parseLong(request.getParameter(PARAM_CONTRACT_ID));
        boolean isPayed = contractService.payContract(contractId);

        if (isPayed) {
            Contract contract = contractService.getById(contractId);
            contract.setStatus(ContractStatus.PAYED);
            contractService.update(contract);

            LOGGER.info("Contract id={} is payed", contractId);
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.CONTRACT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=" + "Contract ID=" + contractId + " is payed";

        } else {

            LOGGER.info("Contract id={} isn't payed", contractId);
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.CONTRACT_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=" + "Contract id=" + contractId + " isn't payed. Not enough money";
        }
    }
}

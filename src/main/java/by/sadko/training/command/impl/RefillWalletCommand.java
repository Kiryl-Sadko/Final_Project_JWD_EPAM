package by.sadko.training.command.impl;

import by.sadko.training.SecurityContext;
import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.WalletService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the wallet refilling command with fields <b>walletService</b>
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,WalletService
 */
public class RefillWalletCommand implements AppCommand {

    private final WalletService walletService;

    /**
     * Constructor initializes wallet refilling command
     *
     * @param walletService - service of the wallet objects
     */
    public RefillWalletCommand(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Executes wallet refilling
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        String moneyFromRequest = request.getParameter(AppConstants.PARAM_AMOUNT_OF_MONEY);
        BigDecimal amountOfMoney = new BigDecimal(moneyFromRequest);
        UserAccount currentUser = SecurityContext.getInstance().getCurrentUser();

        walletService.increaseBalance(currentUser.getId(), amountOfMoney);

        return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.INDEX
                + "&" + PARAM_COMMAND_RESULT + "=" + "Wallet replenished";
    }
}

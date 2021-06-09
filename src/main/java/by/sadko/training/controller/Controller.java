package by.sadko.training.controller;

import by.sadko.training.AppContext;
import by.sadko.training.SecurityContext;
import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandProvider;
import by.sadko.training.command.CommandUtil;
import by.sadko.training.connection.TransactionManager;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.entity.Wallet;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static by.sadko.training.util.AppConstants.PARAM_WALLET_BALANCE;

/**
 * Application servlet which handles request and response
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see TransactionManager,AppContext,SecurityContext,AppCommandProvider
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 3019550974669410399L;
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private final SecurityContext securityContext = SecurityContext.getInstance();
    private final AppContext appContext = AppContext.getInstance();
    private final AppCommandProvider commandProvider = appContext.getBean(AppCommandProvider.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        if (securityContext.isLoggedIn()) {
            try {
                setWalletAttribute(req);

            } catch (DAOException daoException) {
                LOGGER.error(daoException.getMessage(), daoException);
                throw new IllegalStateException("Somthing went wrong, try again latter");
            }
        }

        String commandName = CommandUtil.getCommandFromRequest(req);
        AppCommand command = commandProvider.getCommand(commandName);

        try {
            String viewName = command.execute(req, resp);

            if (viewName.startsWith("redirect:")) {
                String redirect = viewName.replace("redirect:", "");
                resp.sendRedirect(redirect);

            } else {
                req.setAttribute("viewName", viewName);
                req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
            }

        } catch (DAOException | ServletException | IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new IllegalStateException("Somthing went wrong, try again latter");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        doGet(req, resp);
    }

    /**
     * Setting wallet balance to attribute
     *
     * @param req - request
     */
    private void setWalletAttribute(HttpServletRequest req) throws DAOException {

        WalletService walletService = appContext.getBean(WalletService.class);
        UserAccount currentUser = securityContext.getCurrentUser();
        Long walletID = currentUser.getWalletId();
        Wallet wallet = walletService.getById(walletID);
        BigDecimal walletBalance = wallet.getBalance();

        req.setAttribute(PARAM_WALLET_BALANCE, walletBalance);
    }
}

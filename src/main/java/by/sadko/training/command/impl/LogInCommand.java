package by.sadko.training.command.impl;

import by.sadko.training.SecurityContext;
import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.UserAccountService;
import by.sadko.training.util.MD5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the logIn command with fields <b>userAccountService</b>
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,UserAccountService
 */
public class LogInCommand implements AppCommand {

    private final UserAccountService userAccountService;

    /**
     * Constructor initializes logIn command
     *
     * @param userAccountService - service of the user account objects
     */
    public LogInCommand(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     * Executes log in of the user
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        String hashedPassword = MD5Util.md5Custom(password);
        Optional<UserAccount> optionalAccount = Optional.ofNullable(userAccountService.login(email, hashedPassword));

        if (!optionalAccount.isPresent()) {

            request.setAttribute(PARAM_MESSAGE, "Invalid email address or password");
            return "login_view";
        }

        UserAccount userAccount = optionalAccount.get();

        SecurityContext.getInstance().logIn(userAccount);
        return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.INDEX;
    }
}

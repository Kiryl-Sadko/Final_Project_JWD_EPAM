package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.UserAccountService;
import by.sadko.training.util.AppConstants;
import by.sadko.training.util.MD5Util;
import by.sadko.training.validation.BeanValidator;
import by.sadko.training.validation.BrokenField;
import by.sadko.training.validation.ValidationResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class of the sign up command with fields <b>accountService</b> and <b>validator</b>
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,UserAccountService,BeanValidator
 */
public class SignUpCommand implements AppCommand {

    private final UserAccountService accountService;
    private final BeanValidator validator;

    /**
     * Constructor initializes sign up command
     *
     * @param accountService - service of the userAccount objects
     * @param validator      - validator for validate input data
     */
    public SignUpCommand(UserAccountService accountService, BeanValidator validator) {
        this.accountService = accountService;
        this.validator = validator;
    }

    /**
     * Executes sign up new user account
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserAccount userAccount = new UserAccount();
        userAccount.setName(name);
        userAccount.setEmail(email);
        userAccount.setPassword(password);

        ValidationResult validationResult = validator.validate(userAccount);
        List<BrokenField> brokenFields = validationResult.getBrokenFields();

        if (brokenFields.isEmpty()) {

            String hashedPassword = MD5Util.md5Custom(password);
            userAccount.setPassword(hashedPassword);

            try {
                accountService.signUp(userAccount);
            } catch (DAOException daoException) {
                request.setAttribute(AppConstants.PARAM_MESSAGE, "This user already exists.");
                return "sign_up_view";
            }

            request.setAttribute(AppConstants.PARAM_MESSAGE, "Your account has been successfully registered. Thank you for registration!");
            return "login_view";

        } else {
            StringBuilder message = new StringBuilder();
            message.append("Invalid input, please check next field(s): ");

            for (int i = 0; i < brokenFields.size(); i++) {

                if (i == brokenFields.size() - 1) {
                    message.append(brokenFields.get(i).getFieldName());
                    break;
                }
                message.append(brokenFields.get(i).getFieldName());
                message.append(", ");
            }
            request.setAttribute(AppConstants.PARAM_MESSAGE, message);
            return "sign_up_view";
        }
    }
}

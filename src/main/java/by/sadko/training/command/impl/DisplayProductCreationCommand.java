package by.sadko.training.command.impl;

import by.sadko.training.SecurityContext;
import by.sadko.training.command.AppCommand;
import by.sadko.training.entity.Material;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.UserRole;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;
import by.sadko.training.service.OperationService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.sadko.training.util.AppConstants.PARAM_ACCOUNT_ROLE;

/**
 * Class of the displaying product creation command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,MaterialService,OperationService
 */
public class DisplayProductCreationCommand implements AppCommand {

    private final MaterialService materialService;
    private final OperationService operationService;

    /**
     * Initializing of the command
     *
     * @param materialService  - service of the material objects
     * @param operationService - service of the operation objects
     */
    public DisplayProductCreationCommand(MaterialService materialService, OperationService operationService) {

        this.materialService = materialService;
        this.operationService = operationService;
    }

    /**
     * Executes displaying product creation
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        List<UserRole> userRoles = SecurityContext.getInstance().getCurrentUser().getUserRoles();
        userRoles.forEach(role -> {
            if (UserRole.CEO.equals(role)) {
                request.setAttribute(PARAM_ACCOUNT_ROLE, role);
            }
        });

        List<Material> materialList = materialService.getAll();
        List<Operation> operationList = operationService.getAll();

        request.setAttribute(AppConstants.PARAM_MATERIAL_LIST, materialList);
        request.setAttribute(AppConstants.PARAM_OPERATION_LIST, operationList);

        return "product_creation_view";
    }
}

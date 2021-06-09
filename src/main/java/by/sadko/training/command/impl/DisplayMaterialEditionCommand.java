package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.entity.Material;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class of the displaying material edition command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,MaterialService
 */
public class DisplayMaterialEditionCommand implements AppCommand {

    private final MaterialService materialService;

    /**
     * Initializing of the command
     *
     * @param materialService - material service
     */
    public DisplayMaterialEditionCommand(MaterialService materialService) {
        this.materialService = materialService;
    }


    /**
     * Executes displaying material edition
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long materialId = Long.parseLong(request.getParameter(AppConstants.PARAM_MATERIAL_ID));
        Material material = materialService.getById(materialId);
        request.setAttribute(AppConstants.PARAM_MATERIAL, material);

        return "material_edition_view";
    }
}

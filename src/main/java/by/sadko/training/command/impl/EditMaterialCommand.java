package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.Material;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Optional;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the material edition command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,MaterialService
 */
public class EditMaterialCommand implements AppCommand {

    private final MaterialService materialService;

    /**
     * Initialization pf the command
     *
     * @param materialService - material service
     */
    public EditMaterialCommand(MaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * Executes material edition
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long materialId = Long.parseLong(request.getParameter(PARAM_MATERIAL_ID));
        Material material = materialService.getById(materialId);

        Optional.ofNullable(request.getParameter(PARAM_MATERIAL_NAME))
                .filter(s -> !s.isEmpty())
                .ifPresent(material::setName);

        Optional.ofNullable(request.getParameter(PARAM_MATERIAL_DELIVERY))
                .filter(s -> !s.isEmpty())
                .ifPresent(s -> material.setDeliveryTime(Double.parseDouble(s)));

        Optional.ofNullable(request.getParameter(PARAM_MATERIAL_COST))
                .filter(s -> !s.isEmpty())
                .ifPresent(s -> material.setCost(new BigDecimal(s)));

        boolean isEdited = materialService.edit(material);

        if (isEdited) {

            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.MATERIAL_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=" + "Material id=" + materialId + " is edited";

        } else {
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.MATERIAL_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=" + "Material with name '" + material.getName() + "' already exists";

        }
    }
}

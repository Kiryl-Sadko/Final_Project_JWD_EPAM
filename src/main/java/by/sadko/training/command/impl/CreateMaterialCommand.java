package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.entity.Material;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the material creation command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand, ContractService
 */
public class CreateMaterialCommand implements AppCommand {

    private final MaterialService materialService;

    /**
     * Initialization of the command
     *
     * @param materialService - material service
     */
    public CreateMaterialCommand(MaterialService materialService) {

        this.materialService = materialService;
    }

    /**
     * Executes material creation in data base
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        String materialName = request.getParameter(PARAM_MATERIAL_NAME);
        String materialCost = request.getParameter(PARAM_MATERIAL_COST);
        BigDecimal cost = new BigDecimal(materialCost);
        double deliveryTime = Double.parseDouble(request.getParameter(PARAM_MATERIAL_DELIVERY));

        Material material = new Material();
        material.setName(materialName);
        material.setCost(cost);
        material.setDeliveryTime(deliveryTime);

        Long materialId = materialService.create(material);

        return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.MATERIAL_DISPLAY_ALL
                + "&" + PARAM_COMMAND_RESULT + "=" + "Material id=" + materialId + " is created";
    }
}

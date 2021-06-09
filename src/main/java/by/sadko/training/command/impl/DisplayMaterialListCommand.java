package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.entity.Material;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;
import by.sadko.training.util.AppConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.sadko.training.command.CommandUtil.*;
import static by.sadko.training.util.AppConstants.PARAM_MESSAGE;

/**
 * Class of the displaying material list command
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see AppCommand,MaterialService
 */
public class DisplayMaterialListCommand implements AppCommand {

    private final MaterialService materialService;

    /**
     * Initialization of the command
     *
     * @param materialService - material service
     */
    public DisplayMaterialListCommand(MaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * Executes displaying material list
     *
     * @param request  - request
     * @param response - response
     * @return name of the jsp view
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        List<Material> materialList = materialService.getAll();

        setPageQuantity(request, materialList);

        int pageNumber = setPageNumber(request);

        setMaterialList(request, materialList, pageNumber);

        Optional<String> optionalResult = Optional.ofNullable(request.getParameter(AppConstants.PARAM_COMMAND_RESULT));
        optionalResult.ifPresent(s -> request.setAttribute(PARAM_MESSAGE, s));

        return "material_list_view";
    }

    /**
     * Setting material list to request attribute
     *
     * @param request      - request
     * @param materialList - material list
     * @param pageNumber   - page number
     */
    private void setMaterialList(HttpServletRequest request, List<Material> materialList, int pageNumber) {

        List<Material> result = new ArrayList<>();
        for (int i = (pageNumber - 1) * 5; i < pageNumber * 5 && i < materialList.size(); i++) {

            Material material = materialList.get(i);
            result.add(material);
        }

        request.setAttribute(AppConstants.PARAM_MATERIAL_LIST, result);
    }
}

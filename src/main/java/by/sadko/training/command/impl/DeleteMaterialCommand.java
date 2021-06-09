package by.sadko.training.command.impl;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.MaterialService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.sadko.training.util.AppConstants.*;

public class DeleteMaterialCommand implements AppCommand {

    private static final Logger LOGGER = LogManager.getLogger(DeleteMaterialCommand.class);

    private final MaterialService materialService;

    /**
     * Initialization of the command
     *
     * @param materialService - material service
     */
    public DeleteMaterialCommand(MaterialService materialService) {

        this.materialService = materialService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {

        Long materialId = Long.parseLong(request.getParameter(PARAM_MATERIAL_ID));
        boolean isDeleted = materialService.delete(materialId);

        if (isDeleted) {
            LOGGER.info("Material id={} is deleted", materialId);
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.MATERIAL_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=Material id=" + materialId + " is deleted";

        } else {
            LOGGER.info("Material id={} isn't deleted", materialId);
            return "redirect:?" + PARAM_COMMAND + "=" + AppCommandName.MATERIAL_DISPLAY_ALL
                    + "&" + PARAM_COMMAND_RESULT + "=Material id=" + materialId + " isn't deleted, try letter";
        }
    }
}

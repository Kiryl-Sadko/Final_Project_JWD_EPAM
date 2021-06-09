package by.sadko.training.command;

import by.sadko.training.exception.DAOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface describes behavior of the command objects
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface AppCommand {

    String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException;
}

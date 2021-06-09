package by.sadko.training.parser;

import by.sadko.training.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface describing behavior of the sql-parser
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ConnectionManager , Entity ,SQLEntityParser,
 */
public interface SQLEntityParser<T> {

    /**
     * Parsing ResultSet from data base to entityList
     *
     * @param resultSet - resultSet
     * @return entity list
     */
    List<T> parseResultSetToEntityList(ResultSet resultSet) throws SQLException;

    /**
     * Parsing entities to sql-statement
     *
     * @param entity    - entity
     * @param statement - sql-statement
     * @return prepared sql-statement
     */
    PreparedStatement parseEntityToStatement(T entity, PreparedStatement statement) throws SQLException;
}

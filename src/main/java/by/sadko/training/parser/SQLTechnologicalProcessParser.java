package by.sadko.training.parser;

import by.sadko.training.entity.TechnologicalProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLTechnologicalProcessParser implements SQLEntityParser<TechnologicalProcess> {

    private static final String ID = "id";
    private static final String NAME = "technological_process_name";

    @Override
    public List<TechnologicalProcess> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<TechnologicalProcess> result = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(ID);
            String name = resultSet.getString(NAME);
            TechnologicalProcess technologicalProcess = new TechnologicalProcess(id, name);
            result.add(technologicalProcess);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(TechnologicalProcess entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setString(++i, entity.getName());
        return statement;
    }
}

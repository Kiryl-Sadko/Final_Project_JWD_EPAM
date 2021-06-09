package by.sadko.training.parser;

import by.sadko.training.entity.Progress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLProgressParser implements SQLEntityParser<Progress> {

    private static final String ID = "id";
    private static final String STATUS = "progress_status";
    private static final String LOG = "progress_log";

    @Override
    public List<Progress> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<Progress> result = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(ID);
            int status = resultSet.getInt(STATUS);
            String log = resultSet.getString(LOG);
            Progress progress = new Progress(id, status, log);
            result.add(progress);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(Progress entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setInt(++i, entity.getStatus());
        statement.setString(++i, entity.getLog());
        return statement;
    }
}

package by.sadko.training.parser;

import by.sadko.training.entity.Operation;
import by.sadko.training.entity.OperationType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLOperationParser implements SQLEntityParser<Operation> {

    private static final String ID = "id";
    private static final String TYPE = "operation_type";
    private static final String COST = "operation_cost";
    private static final String TIME = "operation_time";

    @Override
    public List<Operation> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<Operation> result = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(ID);
            String type = resultSet.getString(TYPE);
            double cost = resultSet.getDouble(COST);
            double time = resultSet.getDouble(TIME);
            Operation operation = new Operation(id, OperationType.fromString(type), BigDecimal.valueOf(cost), time);
            result.add(operation);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(Operation entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setString(++i, String.valueOf(entity.getType()));
        statement.setDouble(++i, entity.getCost().doubleValue());
        statement.setDouble(++i, entity.getTime());
        return statement;
    }
}

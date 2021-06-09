package by.sadko.training.parser;

import by.sadko.training.entity.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLRoleParser implements SQLEntityParser<UserRole> {

    private static final String NAME = "role_name";

    @Override
    public List<UserRole> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<UserRole> result = new ArrayList<>();
        while (resultSet.next()) {
            String role = resultSet.getString(NAME);
            UserRole userRole = UserRole.fromString(role);
            result.add(userRole);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(UserRole entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setString(++i, entity.name());
        return statement;
    }
}

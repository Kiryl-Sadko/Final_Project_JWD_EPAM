package by.sadko.training.parser;

import by.sadko.training.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCustomerParser implements SQLEntityParser<Customer> {

    private static final String ID = "id";
    private static final String NAME = "customer_name";

    @Override
    public List<Customer> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<Customer> result = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(ID);
            String name = resultSet.getString(NAME);
            Customer customer = new Customer(id, name);
            result.add(customer);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(Customer entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setString(++i, entity.getName());
        return statement;
    }
}

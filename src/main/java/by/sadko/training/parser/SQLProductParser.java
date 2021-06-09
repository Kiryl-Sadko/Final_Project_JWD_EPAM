package by.sadko.training.parser;

import by.sadko.training.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLProductParser implements SQLEntityParser<Product> {

    private static final String ID = "id";
    private static final String NAME = "product_name";
    private static final String WEIGHT = "product_weight";
    private static final String MATERIAL_ID = "material_id";
    private static final String PROCESS_ID = "technological_process_id";

    @Override
    public List<Product> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<Product> result = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(ID);
            String name = resultSet.getString(NAME);
            double weight = resultSet.getDouble(WEIGHT);
            long materialId = resultSet.getLong(MATERIAL_ID);
            long technologicalProcessId = resultSet.getLong(PROCESS_ID);
            Product product = new Product(id, name, weight, materialId, technologicalProcessId);
            result.add(product);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(Product entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setString(++i, entity.getName());
        statement.setDouble(++i, entity.getWeight());
        statement.setLong(++i, entity.getMaterialID());
        statement.setLong(++i, entity.getTechnologicalProcessID());
        return statement;
    }
}

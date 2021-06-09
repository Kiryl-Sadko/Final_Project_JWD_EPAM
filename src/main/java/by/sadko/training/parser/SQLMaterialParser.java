package by.sadko.training.parser;

import by.sadko.training.entity.Material;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLMaterialParser implements SQLEntityParser<Material> {

    private static final String ID = "id";
    private static final String NAME = "material_name";
    private static final String COST = "material_cost";
    private static final String DELIVERY_TIME = "material_delivery_time";

    @Override
    public List<Material> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<Material> result = new ArrayList<>();
        while (resultSet.next()) {

            long id = resultSet.getLong(ID);
            String name = resultSet.getString(NAME);
            BigDecimal cost = BigDecimal.valueOf(resultSet.getDouble(COST));
            double deliveryTime = resultSet.getDouble(DELIVERY_TIME);
            Material material = new Material(id, name, cost, deliveryTime);

            result.add(material);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(Material entity, PreparedStatement statement) throws SQLException {

        int i = 0;
        statement.setString(++i, entity.getName());
        statement.setDouble(++i, entity.getCost().doubleValue());
        statement.setDouble(++i, entity.getDeliveryTime());

        return statement;
    }
}

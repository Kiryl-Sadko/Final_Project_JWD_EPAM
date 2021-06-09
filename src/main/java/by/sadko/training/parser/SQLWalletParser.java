package by.sadko.training.parser;

import by.sadko.training.entity.Wallet;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLWalletParser implements SQLEntityParser<Wallet> {

    private static final String ID = "id";
    private static final String BALANCE = "wallet_balance";

    @Override
    public List<Wallet> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<Wallet> result = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(ID);
            BigDecimal balance = BigDecimal.valueOf(resultSet.getDouble(BALANCE));
            Wallet wallet = new Wallet(id, balance);
            result.add(wallet);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(Wallet entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setDouble(++i, entity.getBalance().doubleValue());
        return statement;
    }
}

package by.sadko.training.parser;

import by.sadko.training.entity.UserAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLAccountParser implements SQLEntityParser<UserAccount> {

    private static final String ID = "id";
    private static final String NAME = "user_name";
    private static final String PASSWORD = "user_password";
    private static final String EMAIL = "user_email";
    private static final String IS_ACTIVE = "is_active";
    private static final String WALLET_ID = "wallet_id";

    @Override
    public List<UserAccount> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<UserAccount> result = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong(ID);
            String name = resultSet.getString(NAME);
            String password = resultSet.getString(PASSWORD);
            String email = resultSet.getString(EMAIL);
            boolean isActive = resultSet.getBoolean(IS_ACTIVE);
            Long walletId = resultSet.getLong(WALLET_ID);

            UserAccount userAccount = new UserAccount(id, name, password, email, isActive, walletId, new ArrayList<>());
            result.add(userAccount);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(UserAccount entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setString(++i, entity.getName());
        statement.setString(++i, entity.getPassword());
        statement.setString(++i, entity.getEmail());
        statement.setBoolean(++i, entity.isActive());
        statement.setLong(++i, entity.getWalletId());
        return statement;
    }
}

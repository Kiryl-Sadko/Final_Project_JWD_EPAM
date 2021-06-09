package by.sadko.training.parser;

import by.sadko.training.entity.Contract;
import by.sadko.training.entity.ContractStatus;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SQLContractParser implements SQLEntityParser<Contract> {

    private static final String ID = "id";
    private static final String ACCOUNT_ID = "user_account_id";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_QUANTITY = "contract_product_quantity";
    private static final String CONTRACT_PRICE = "contract_price";
    private static final String CONTRACT_STATUS = "contract_status";
    private static final String PAYMENT_DATE = "contract_payment_date";
    private static final String COMPLETION_DATE = "contract_completion_date";
    private static final String PROGRESS_ID = "progress_progress_id";

    @Override
    public List<Contract> parseResultSetToEntityList(ResultSet resultSet) throws SQLException {

        List<Contract> result = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(ID);
            long accountId = resultSet.getLong(ACCOUNT_ID);
            long customerId = resultSet.getLong(CUSTOMER_ID);
            long productId = resultSet.getLong(PRODUCT_ID);
            int quantity = resultSet.getInt(PRODUCT_QUANTITY);
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(CONTRACT_PRICE));
            String status = resultSet.getString(CONTRACT_STATUS);

            Calendar paymentDate = new GregorianCalendar();
            long paymentTime = resultSet.getTimestamp(PAYMENT_DATE, GregorianCalendar.getInstance()).getTime();
            paymentDate.setTimeInMillis(paymentTime);
            Calendar completionDate = new GregorianCalendar();
            long completionTime = resultSet.getTimestamp(COMPLETION_DATE, GregorianCalendar.getInstance()).getTime();
            completionDate.setTimeInMillis(completionTime);

            long progressId = resultSet.getLong(PROGRESS_ID);
            Contract contract = new Contract(id, accountId, customerId, productId, quantity,
                    price, ContractStatus.fromString(status), paymentDate, completionDate, progressId);
            result.add(contract);
        }
        return result;
    }

    @Override
    public PreparedStatement parseEntityToStatement(Contract entity, PreparedStatement statement) throws SQLException {
        int i = 0;
        statement.setLong(++i, entity.getUserAccountID());
        statement.setLong(++i, entity.getCustomerId());
        statement.setLong(++i, entity.getProductId());
        statement.setInt(++i, entity.getQuantity());
        statement.setDouble(++i, entity.getPrice().doubleValue());
        statement.setString(++i, entity.getStatus().getStatus());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar paymentDate = entity.getPaymentDate();
        String paymentTime = dateFormat.format(paymentDate.getTime());
        statement.setString(++i, paymentTime);
        Calendar completionDate = entity.getCompletionDate();
        String completionTime = dateFormat.format(completionDate.getTime());
        statement.setString(++i, completionTime);

        statement.setLong(++i, entity.getProgressId());
        return statement;
    }
}

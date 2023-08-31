package rs.etf.sab.operations;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface BuyerOperations {
    int	createBuyer(String name, int cityId);
    int	createOrder(int buyerId);
    int	getCity(int buyerId);
    BigDecimal getCredit(int buyerId);
    List<Integer> getOrders(int buyerId);
    BigDecimal	increaseCredit(int buyerId, BigDecimal credit) throws SQLException;
    int	setCity(int buyerId, int cityId);


}

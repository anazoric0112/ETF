package rs.etf.sab.operations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public interface OrderOperations {
    int	addArticle(int orderId, int articleId, int count);
    int	completeOrder(int orderId);
    int	getBuyer(int orderId);
    BigDecimal	getDiscountSum(int orderId);
    BigDecimal getFinalPrice(int orderId);
    List<Integer> getItems(int orderId);
    int	getLocation(int orderId);
    Calendar getRecievedTime(int orderId);
    Calendar	getSentTime(int orderId);
    String	getState(int orderId);
    int	removeArticle(int orderId, int articleId);
}

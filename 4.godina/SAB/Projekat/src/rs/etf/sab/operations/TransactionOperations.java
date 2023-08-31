package rs.etf.sab.operations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public interface TransactionOperations {
    BigDecimal getAmmountThatBuyerPayedForOrder(int orderId);
    BigDecimal	getAmmountThatShopRecievedForOrder(int shopId, int orderId);
    BigDecimal	getBuyerTransactionsAmmount(int buyerId);
    BigDecimal	getShopTransactionsAmmount(int shopId);
    BigDecimal	getSystemProfit();
    Calendar getTimeOfExecution(int transactionId);
    BigDecimal	getTransactionAmount(int transactionId);
    int	getTransactionForBuyersOrder(int orderId);
    int	getTransactionForShopAndOrder(int orderId, int shopId);
    List<Integer> getTransationsForBuyer(int buyerId);
    List<Integer>	getTransationsForShop(int shopId);
}

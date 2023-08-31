package rs.etf.sab.student;

import rs.etf.sab.operations.*;
import rs.etf.sab.student.*;
import rs.etf.sab.tests.*;

public class StudentMain {

    public static void main(String[] args) {

        ArticleOperations articleOperations = new za200009_ArticleOperations(); // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = new za200009_BuyerOperations();
        CityOperations cityOperations = new za200009_CityOperations();
        GeneralOperations generalOperations = new za200009_GeneralOperations();
        OrderOperations orderOperations = new za200009_OrderOperations();
        ShopOperations shopOperations = new za200009_ShopOperations();
        TransactionOperations transactionOperations = new za200009_TransactionOperations();

        TestHandler.createInstance(
                articleOperations,
                buyerOperations,
                cityOperations,
                generalOperations,
                orderOperations,
                shopOperations,
                transactionOperations
        );

        TestRunner.runTests();
    }
}

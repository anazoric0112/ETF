package rs.etf.sab.student;

import rs.etf.sab.operations.TransactionOperations;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class za200009_TransactionOperations implements TransactionOperations {
    @Override
    public BigDecimal getAmmountThatBuyerPayedForOrder(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select price\n" +
                "from transaction_ t join payment p\n" +
                "on t.idT=p.idT and idO=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getBigDecimal("price").setScale(3);
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public BigDecimal getAmmountThatShopRecievedForOrder(int shopId, int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select price\n" +
                "from transaction_ t join payout p\n" +
                "on t.idT=p.idT and idO=? and idS=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            stmt.setInt(2,shopId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getBigDecimal("price").setScale(3);
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public BigDecimal getBuyerTransactionsAmmount(int buyerId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select sum(price) as 'res' from payment p, transaction_ t\n" +
                "where t.idt=p.idt and idB=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,buyerId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getBigDecimal("res").setScale(3);
            }
            return new BigDecimal(0).setScale(3);
        } catch (SQLException ex) {
            return new BigDecimal(-1);
        }
    }

    @Override
    public BigDecimal getShopTransactionsAmmount(int shopId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select sum(price) as 'res' from payout p, transaction_ t\n" +
                "where idS=? and p.idt=t.idt";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,shopId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                BigDecimal bd=rs.getBigDecimal("res");
                if (bd==null) return new BigDecimal(0).setScale(3);
                return rs.getBigDecimal("res").setScale(3);
            }
            return new BigDecimal(0).setScale(3);
        } catch (SQLException ex) {
            int i=0;
            return new BigDecimal(-1).setScale(3);
        }
    }

    @Override
    public BigDecimal getSystemProfit() {
        Connection conn = DB.getInstance().getConnection();
        String query="select case when exists (\n"+
                        "select* from payment p2, transaction_ t\n"+
                            "where p2.idT=t.idT and p2.idB=p1.idB and\n"+
                            "datediff(month, ?, t.time_t)<=30 and t.price>10000)\n"+
                            "then sum(price)*0.03\n"+
                        "else sum(price)*0.05\n"+
                        "end as 'res'\n"+
                    "from payment p1, Transaction_ t, order_ o\n"+
                    "where p1.idT=t.idT and o.idO=t.idO and o.State='arrived'\n"+
                    "group by p1.idB";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1,new Date(za200009_GeneralOperations.my_time.getTimeInMillis()));
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                BigDecimal bd=rs.getBigDecimal("res");
                if (bd==null) return new BigDecimal(0).setScale(3);
                return rs.getBigDecimal("res").setScale(3);
            }
            return new BigDecimal(0).setScale(3);
        } catch (SQLException ex) {
            return new BigDecimal(-1).setScale(3);
        }
    }

    @Override
    public Calendar getTimeOfExecution(int transactionId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select time_t from transaction_ where idt=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,transactionId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                Calendar ret=Calendar.getInstance();
                ret.setTime(rs.getDate("time_t"));
                return ret;
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public BigDecimal getTransactionAmount(int transactionId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select price from transaction_ where idT=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,transactionId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getBigDecimal("price").setScale(3);
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public int getTransactionForBuyersOrder(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select t.idT\n" +
                "from transaction_ t join payment p\n" +
                "on t.idT=p.idT and idO=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("idT");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public int getTransactionForShopAndOrder(int orderId, int shopId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select t.idT\n" +
                "from transaction_ t join payout p\n" +
                "on t.idT=p.idT and idO=? and idS=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            stmt.setInt(2,shopId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("idT");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public List<Integer> getTransationsForBuyer(int buyerId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idT from payment where idB=?";
        List <Integer> transactions=new ArrayList<Integer>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,buyerId);
            ResultSet rs=stmt.executeQuery();
            if (!rs.next()) return null;
            do {
                transactions.add(rs.getInt("idT"));
            } while(rs.next());

            return transactions;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Integer> getTransationsForShop(int shopId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idT from payout where idS=?";
        List <Integer> transactions=new ArrayList<Integer>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,shopId);
            ResultSet rs=stmt.executeQuery();

            if (!rs.next()) return null;
            do {
                transactions.add(rs.getInt("idT"));
            } while(rs.next());

            return transactions;
        } catch (SQLException ex) {
            return null;
        }
    }
}

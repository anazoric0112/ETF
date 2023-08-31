package rs.etf.sab.student;

import rs.etf.sab.operations.BuyerOperations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class za200009_BuyerOperations implements BuyerOperations {
    @Override
    public int createBuyer(String name, int cityId) {

        Connection conn = DB.getInstance().getConnection();
        String query = "insert into Buyer(name,idC,money)\n" +
                "values (?,?,0)";
        try ( PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,name);
            stmt.setInt(2,cityId);
            stmt.executeUpdate();

            PreparedStatement stmt2=conn.prepareStatement("select IDENT_CURRENT('Buyer') as 'num'");
            ResultSet rs=stmt2.executeQuery();
            if (rs.next()){
                return rs.getInt("num");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public int createOrder(int buyerId) {
        Connection conn = DB.getInstance().getConnection();
        String query = "insert into Order_(state,idB,idC)\n" +
                "values ('created',?,?)";

        try ( PreparedStatement stmt = conn.prepareStatement(query)) {

            int cityId=getCity(buyerId);
            if (cityId==-1) return -1;

            stmt.setInt(1, buyerId);
            stmt.setInt(2,cityId);
            stmt.executeUpdate();

            PreparedStatement stmt2=conn.prepareStatement("select IDENT_CURRENT('Order_') as 'num'");
            ResultSet rs=stmt2.executeQuery();
            if (rs.next()){
                return rs.getInt("num");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public int getCity(int buyerId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idC from Buyer where idB=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,buyerId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("idC");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public BigDecimal getCredit(int buyerId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select money from Buyer where idB=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,buyerId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getBigDecimal("Money").setScale(3);
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Integer> getOrders(int buyerId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idO from Order_ where idB=?";
        List <Integer> orders=new ArrayList<Integer>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,buyerId);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                orders.add(rs.getInt("idO"));
            }
            return orders;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public BigDecimal increaseCredit(int buyerId, BigDecimal credit)  {
        Connection conn = DB.getInstance().getConnection();
        String query="select * from Buyer where idB=?";

        try (PreparedStatement stmt = conn.prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {

            stmt.setInt(1, buyerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                BigDecimal new_val=rs.getBigDecimal("Money").add(credit);
                rs.updateBigDecimal("Money",new_val);
                rs.updateRow();
                return new_val.setScale(3);
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int setCity(int buyerId, int cityId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select * from Buyer where idB=?";
        String query_c="select * from City where idC=?";

        try (PreparedStatement stmt = conn.prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             PreparedStatement stmt_c = conn.prepareStatement(query_c)) {

            stmt.setInt(1,buyerId);
            stmt_c.setInt(1,cityId);
            ResultSet rs=stmt.executeQuery();
            ResultSet rs_c=stmt_c.executeQuery();

            if (rs.next() && rs_c.next()){
                rs.updateInt("idC",cityId);
                rs.updateRow();
                return 1;
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }
}

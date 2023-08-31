package rs.etf.sab.student;

import rs.etf.sab.operations.ShopOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class za200009_ShopOperations implements ShopOperations {
    @Override
    public int createShop(String name, String cityName) {
        Connection conn = DB.getInstance().getConnection();
        String query = "insert into shop(name,idc,discount,money)\n" +
                "values (?,?,0,0)";
        String query_city="select idc from city where name=?";
        try ( PreparedStatement stmt = conn.prepareStatement(query);
              PreparedStatement stmt_city = conn.prepareStatement(query_city)) {

            stmt_city.setString(1,cityName);
            ResultSet rs_city=stmt_city.executeQuery();
            if (!rs_city.next()) return -1;

            stmt.setInt(2,rs_city.getInt("idC"));
            stmt.setString(1,name);
            stmt.executeUpdate();

            PreparedStatement stmt2=conn.prepareStatement("select IDENT_CURRENT('Shop') as 'num'");
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
    public int getArticleCount(int articleId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select count from article where idA=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,articleId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("count");
            }
            return 0;
        } catch (SQLException ex) {
            return 0;
        }
    }

    @Override
    public List<Integer> getArticles(int shopId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idA from article where idS=?";
        List <Integer> articles=new ArrayList<Integer>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,shopId);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                articles.add(rs.getInt("idA"));
            }
            return articles;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public int getCity(int shopId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idC from shop where idS=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,shopId);
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
    public int getDiscount(int shopId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select discount from shop where idS=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,shopId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("discount");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public int increaseArticleCount(int articleId, int increment) {
        Connection conn = DB.getInstance().getConnection();
        String query="select * from article where idA=?";

        try (PreparedStatement stmt = conn.prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {

            stmt.setInt(1,articleId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()) {
                Integer new_val=rs.getInt("count")+increment;
                rs.updateInt("count",new_val);
                rs.updateRow();
                return new_val;
            }else {
                return -1;
            }
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public int setCity(int shopId, String cityName) {
        Connection conn = DB.getInstance().getConnection();
        String query="select * from Shop where idS=?";
        String query_city="select idC from city where name=?";

        try (PreparedStatement stmt = conn.prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             PreparedStatement stmt_city = conn.prepareStatement(query_city)) {

            stmt.setInt(1,shopId);
            ResultSet rs=stmt.executeQuery();

            stmt_city.setString(1,cityName);
            ResultSet rs_city=stmt_city.executeQuery();
            if (rs_city.next() && rs.next()){
                rs.updateInt("idC",rs_city.getInt("idC"));
                rs.updateRow();
                return 1;
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public int setDiscount(int shopId, int discountPercentage) {
        Connection conn = DB.getInstance().getConnection();
        String query="select * from Shop where idS=?";

        try (PreparedStatement stmt = conn.prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {

            stmt.setInt(1,shopId);
            ResultSet rs=stmt.executeQuery();

            if (rs.next()){
                rs.updateInt("discount",discountPercentage);
                rs.updateRow();
                return 1;
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }
}

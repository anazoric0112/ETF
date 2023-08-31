package rs.etf.sab.student;

import rs.etf.sab.operations.CityOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class za200009_CityOperations implements CityOperations {
    @Override
    public int connectCities(int cityId1, int cityId2, int distance) {
        Connection conn = DB.getInstance().getConnection();
        String query_check="select idcc from city_connection\n"+
                        "where (idc1=? and idc2=?) or (idc1=? and idc2=?)";
        String query = "insert into city_connection(idc1,idc2,days)\n" +
                "values (?,?,?)";
        String query_c="select * from city where idc=?";
        try ( PreparedStatement stmt = conn.prepareStatement(query);
              PreparedStatement stmt_check = conn.prepareStatement(query_check);
              PreparedStatement stmt_c=conn.prepareStatement(query_c)) {
            stmt_check.setInt(1,cityId1);
            stmt_check.setInt(2,cityId2);
            stmt_check.setInt(3,cityId2);
            stmt_check.setInt(4,cityId1);
            ResultSet rs_check=stmt_check.executeQuery();
            if (rs_check.next()){
                return rs_check.getInt("idcc");
            }
            stmt_c.setInt(1,cityId1);
            rs_check=stmt_c.executeQuery();
            if(!rs_check.next()) return -1;
            stmt_c.setInt(1,cityId2);
            rs_check=stmt_c.executeQuery();
            if(!rs_check.next()) return -1;

            stmt.setInt(1,cityId1);
            stmt.setInt(2,cityId2);
            stmt.setInt(3,distance);
            stmt.executeUpdate();

            PreparedStatement stmt2=conn.prepareStatement("select IDENT_CURRENT('city_connection') as 'num'");
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
    public int createCity(String name) {
        Connection conn = DB.getInstance().getConnection();
        String query_check="select idc from city where name=?";
        String query = "insert into city(name)\n" +
                "values (?)";
        try ( PreparedStatement stmt = conn.prepareStatement(query);
              PreparedStatement stmt_check = conn.prepareStatement(query_check)) {

            stmt_check.setString(1,name);
            ResultSet rs_check=stmt_check.executeQuery();
            if (rs_check.next()){
                return rs_check.getInt("idc");
            }

            stmt.setString(1,name);
            stmt.executeUpdate();

            PreparedStatement stmt2=conn.prepareStatement("select IDENT_CURRENT('City') as 'num'");
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
    public List<Integer> getCities() {
        Connection conn = DB.getInstance().getConnection();
        String query="select * from city";
        List <Integer> cities=new ArrayList<Integer>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                cities.add(rs.getInt("idC"));
            }
            return cities;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Integer> getConnectedCities(int cityId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idc1,idc2 from city_connection where idc1=? or idc2=?";
        List <Integer> cities=new ArrayList<Integer>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,cityId);
            stmt.setInt(2,cityId);

            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                Integer c1=rs.getInt("idC1");
                Integer c2=rs.getInt("idC2");
                if (c1==cityId) cities.add(c2);
                else cities.add(c1);
            }
            return cities;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Integer> getShops(int cityId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select ids from shop where idc=?";
        List <Integer> shops=new ArrayList<Integer>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,cityId);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                shops.add(rs.getInt("idS"));
            }
            return shops;
        } catch (SQLException ex) {
            return null;
        }
    }
}

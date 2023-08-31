package rs.etf.sab.student;

import rs.etf.sab.operations.GeneralOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

public class za200009_GeneralOperations implements GeneralOperations {
    public static Calendar my_time=Calendar.getInstance();
    @Override
    public void eraseAll() {
        Connection conn = DB.getInstance().getConnection();
        String query = "delete from in_order\n"+
                "delete from city_connection\n"+
                "delete from payment\n"+
                "delete from payout\n"+
                "delete from transaction_\n"+
                "delete from article\n"+
                "delete from shop\n"+
                "delete from order_\n"+
                "delete from buyer\n"+
                "delete from city\n";

        try ( PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    @Override
    public Calendar getCurrentTime() {
        return my_time;
    }

    @Override
    public void setInitialTime(Calendar time) {
        my_time.setTime(time.getTime());
    }

    @Override
    public Calendar time(int days) {
        my_time.add(Calendar.DAY_OF_MONTH,days);
        HashMap<Integer,Transit> times=za200009_OrderOperations.times;

        for (Integer order: times.keySet()){
            Transit t=times.get(order);
            if (t.getState()==3) continue;
            t.addDays(days);

            times.put(order,t);
        }
        return my_time;
    }
}

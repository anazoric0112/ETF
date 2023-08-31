package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class Transit {
    private int id;
    private Date date;
    private int days_a, days_b; //days to city_a and from city_a to city_b
    private int city_a, city_b; //c-closest with s&o, a-closest with s, b-buyer
    private Path path_ab;

    private int state; //0-created, 1-sent C->A, 2-sent A->B, 3-arrived


    public Transit(int id, Calendar mytime, int days_a, int days_b, int city_a, int city_b,Path p) {
        this.id=id;
        this.days_a = days_a;
        this.days_b = days_b;
        this.city_a = city_a;
        this.city_b = city_b;
        this.state = 0;
        this.path_ab=p;
        mytime.add(Calendar.DAY_OF_MONTH, days_a+days_b);
        date=new Date(mytime.getTimeInMillis());
    }

    public void addDays(int days) {
        int sub = days;
        if (state == 0 || state == 1) {
            if (days_a <= sub) {
                sub -= days_a;
                days_a = 0;
                state = 2;
            } else {
                days_a -= sub;
                sub = 0;
                state = 1;
            }
        }
        if (sub == 0) return;
        if (state == 2) {
            path_ab.subDays(sub);
            if (path_ab.last()==city_b) {
                state=3;
                arrive();
            }
        }
    }

    public int getState(){
        return state;
    }
    public int getCity(){
        switch (state){
            case 0: return -1;
            case 1: return city_a;
            case 2: return path_ab.last();
            case 3: return city_b;
        }
        return -1;
    }

    private void arrive(){
        Connection conn = DB.getInstance().getConnection();
        String q_state="update order_ set state='arrived', time_arrive=? where idO=?";

        try(PreparedStatement stmt= conn.prepareStatement(q_state)){
            stmt.setDate(1,date);
            stmt.setInt(2,id);
            stmt.executeUpdate();
        }catch (SQLException ex){

        }
    }
}

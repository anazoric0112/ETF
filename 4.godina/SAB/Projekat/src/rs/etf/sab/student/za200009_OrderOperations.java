package rs.etf.sab.student;

import rs.etf.sab.operations.OrderOperations;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class za200009_OrderOperations implements OrderOperations {

    private Calendar mytime=Calendar.getInstance();
    public static HashMap<Integer,Transit> times=new HashMap<>();
    @Override
    public int addArticle(int orderId, int articleId, int count) {
        Connection conn = DB.getInstance().getConnection();
        String query="select * from in_order where idO=? and idA=?";
        String query_cnt="select count from article where idA=?";

        try (PreparedStatement stmt = conn.prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             PreparedStatement stmt_cnt = conn.prepareStatement(query_cnt);) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, articleId);
            stmt_cnt.setInt(1, articleId);
            ResultSet rs = stmt.executeQuery();
            ResultSet rs_cnt=stmt_cnt.executeQuery();

            if (rs.next()) {
                rs_cnt.next();
                Integer new_val=rs.getInt("count")+count;
                if (rs_cnt.getInt("count")<new_val) return -1;

                rs.updateInt("count",new_val);
                rs.updateRow();

                return rs.getInt("idIO");
            }else {
                rs_cnt.next();
                if (rs_cnt.getInt("count")<count) return -1;
                return addNewArticle(orderId,articleId,count);
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override //dodaj provere za sve i svasta
    public int completeOrder(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String q_order="select idB,state,idc from order_ where idO=?";
        String q_ts="update order_ set time_start=? where idO=?";
        String q_state="update order_ set state='sent' where idO=?";
        String q_pay="update buyer set money=money-? where idB=?";
        String q_art_cnt="update article set count=count-? where idA=?";
        String q_articles="select ida, count from in_order io where idO=?";
        String q_payment="insert into payment(idT,idB) values (?,?)";
        String q_t="insert into transaction_(price, time_t, idO) values (?,?,?)";
        String q_c="select idc1,idc2,days from city_connection ";
        String q_c_s="select s.idc from shop s, article a, in_order i\n"+
                        "where i.idO=? and a.idA=i.idA and a.idS=s.idS";
        String check_a="select a.count, io.count from article a, in_order io\n"+
                        "where io.idO=? and io.idA=a.idA";
        String check_b="select money from buyer b, order_ o\n"+
                        "where o.idB=b.idB and o.idO=?";
        mytime=za200009_GeneralOperations.my_time;

        try (PreparedStatement stmt_order = conn.prepareStatement(q_order);
             PreparedStatement stmt_state = conn.prepareStatement(q_state);
             PreparedStatement stmt_ts = conn.prepareStatement(q_ts);
             PreparedStatement stmt_pay = conn.prepareStatement(q_pay);
             PreparedStatement stmt_art_cnt = conn.prepareStatement(q_art_cnt);
             PreparedStatement stmt_articles = conn.prepareStatement(q_articles);
             PreparedStatement stmt_payment= conn.prepareStatement(q_payment);
             PreparedStatement stmt_t = conn.prepareStatement(q_t);
             PreparedStatement stmt_c = conn.prepareStatement(q_c);
             PreparedStatement stmt_c_s = conn.prepareStatement(q_c_s);
             PreparedStatement stmt_check_a = conn.prepareStatement(check_a);
             PreparedStatement stmt_check_b = conn.prepareStatement(check_b);
             ) {

            //getting order - idB, state, idC
            stmt_order.setInt(1,orderId);
            ResultSet rs=stmt_order.executeQuery();
            if (!rs.next()) return -1;

            //checking if there is enough of all articles
            stmt_check_a.setInt(1,orderId);
            ResultSet rs_check_a=stmt_check_a.executeQuery();
            while (rs_check_a.next()){ //1-a.count, 2-io.count
                if (rs_check_a.getInt(1)<rs_check_a.getInt(2)) return -1;
            }

            //checking if the buyer has enough money
            stmt_check_b.setInt(1,orderId);
            ResultSet rs_check_b=stmt_check_b.executeQuery();
            if (!rs_check_b.next()) return -1;
            BigDecimal b_money=rs_check_b.getBigDecimal(1).setScale(3);
            BigDecimal fp=getFinalPrice(orderId).setScale(3);
            if (b_money.compareTo(fp)==-1) return -1;

            //buyer paying money
            stmt_pay.setInt(2,rs.getInt(1));
            stmt_pay.setBigDecimal(1,getFinalPrice(orderId));
            stmt_pay.executeUpdate();

            //setting time_start of order
            stmt_ts.setDate(1,new Date(mytime.getTimeInMillis()));
            stmt_ts.setInt(2,orderId);
            stmt_ts.executeUpdate();
            //setting status 'sent'
            stmt_state.setInt(1,orderId);
            stmt_state.executeUpdate();

            //substracting article count
            stmt_articles.setInt(1,orderId);
            ResultSet rs_articles=stmt_articles.executeQuery();
            while (rs_articles.next()){
                stmt_art_cnt.setInt(1,rs_articles.getInt(2));
                stmt_art_cnt.setInt(2,rs_articles.getInt(1));
                stmt_art_cnt.executeUpdate();
            }
            //adding transaction of payment
            stmt_t.setBigDecimal(1,getFinalPrice(orderId));
            stmt_t.setDate(2,new Date(mytime.getTimeInMillis()));
            stmt_t.setInt(3,orderId);
            stmt_t.executeUpdate();
            PreparedStatement stmt_id=conn.prepareStatement("select IDENT_CURRENT('Transaction_') as 'num'");
            ResultSet rs_id=stmt_id.executeQuery();
            rs_id.next();
            int idt=rs_id.getInt("num");

            stmt_payment.setInt(1,idt);
            stmt_payment.setInt(2,rs.getInt(1));
            stmt_payment.executeUpdate();

            //making a graph out of cities
            ResultSet rs_c=stmt_c.executeQuery();
            HashMap<Integer,List<Integer[]>> graph=makeGraph(rs_c);

            //getting cities where the order shops are
            stmt_c_s.setInt(1,orderId);
            ResultSet rs_c_s=stmt_c_s.executeQuery();
            List<Integer> cities_from=new ArrayList<>();
            while(rs_c_s.next()){
                cities_from.add(rs_c_s.getInt(1));
            }

            //city of the buyer
            Integer city_b=rs.getInt(3);
            //nearest city to the city of the buyer with a shop associated with order
            Integer city_a=city_b,days_b=Integer.MAX_VALUE;
            Integer days_a=0;

            HashMap <Integer,Path> dist_b=b_and_b(city_b,graph);
            Path sp=null; //shortest path from city_a to city_b

            for (Integer c: cities_from){
                Integer dist=dist_b.get(c).dist_sum;
                if (dist<days_b){
                    days_b=dist;
                    city_a=c;
                    sp=dist_b.get(c);
                }
            }

            HashMap<Integer,Integer> dist_a=dijkstra(city_a,graph);
            for (Integer c: cities_from){
                days_a=Integer.max(days_a,dist_a.get(c));
            }
            Transit my_times=new Transit(orderId,mytime,days_a,days_b,city_a,city_b,sp); //phase A + phase B + arrived?
            times.put(orderId,my_times);

            return 1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public int getBuyer(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idB from order_ where idO=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("idB");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public BigDecimal getDiscountSum(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select sum(a.price*i.count*s.discount*1./100) as 'res'\n"+
                        "from article a, in_order i, shop s\n"+
                        "where i.idO=? and i.idA=a.idA and a.idS=s.idS";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getBigDecimal("res").setScale(3);
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public BigDecimal getFinalPrice(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="declare @i decimal(10,3)\n"+
                    "EXECUTE SP_FINAL_PRICE  ?, @sum=@i OUTPUT\n"+
                    "select @i";

        try (PreparedStatement cs=conn.prepareStatement(query)) {
            cs.setInt(1,orderId);
            ResultSet rs=cs.executeQuery();
            if (!rs.next()) return null;
            return rs.getBigDecimal(1).setScale(3);
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Integer> getItems(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select idA from in_order where idO=?";
        List <Integer> items=new ArrayList<Integer>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                items.add(rs.getInt("idA"));
            }
            return items;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public int getLocation(int orderId) {
        return times.get(orderId).getCity();
    }

    @Override
    public Calendar getRecievedTime(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select time_arrive from order_ where idO=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                if (rs.getDate("time_arrive")==null) return null;
                Calendar ret=Calendar.getInstance();
                ret.setTime(rs.getDate("time_arrive"));
                return ret;
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Calendar getSentTime(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select time_start from order_ where idO=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                if (rs.getDate("time_start")==null) return null;
                Calendar ret=Calendar.getInstance();
                ret.setTime(rs.getDate("time_start"));
                return ret;
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public String getState(int orderId) {
        Connection conn = DB.getInstance().getConnection();
        String query="select state from order_ where idO=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,orderId);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                return rs.getString("state");
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public int removeArticle(int orderId, int articleId) {
        Connection conn = DB.getInstance().getConnection();
        String query="delete from in_order where idA=? and idO=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,articleId);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();

            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }

    private int addNewArticle(int orderId, int articleId, int count){
        Connection conn = DB.getInstance().getConnection();
        String query = "insert into in_order(idO,idA,count)\n" +
                "values (?,?,?)";
        try ( PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,orderId);
            stmt.setInt(2,articleId);
            stmt.setInt(3,count);
            stmt.executeUpdate();

            PreparedStatement stmt2=conn.prepareStatement("select IDENT_CURRENT('in_order') as 'num'");
            ResultSet rs=stmt2.executeQuery();
            if (rs.next()){
                return rs.getInt("num");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    private HashMap<Integer,List<Integer[]>> makeGraph(ResultSet rs_c) throws SQLException{
        HashMap<Integer,List<Integer[]>> graph=new HashMap<>();

        while (rs_c.next()){
            int idc1=rs_c.getInt(1);
            int idc2=rs_c.getInt(2);
            int days=rs_c.getInt(3);

            if (graph.getOrDefault(idc1,null)==null){
                graph.put(idc1,new ArrayList<Integer[]>());
            }
            if (graph.getOrDefault(idc2,null)==null){
                graph.put(idc2,new ArrayList<Integer[]>());
            }
            Integer[] pair1={idc2,days},pair2={idc1,days};
            graph.get(idc1).add(pair1);
            graph.get(idc2).add(pair2);
        }
        return graph;
    }

    private HashMap<Integer,Integer> dijkstra(int city_to, HashMap<Integer,List<Integer[]>> graph){
        class Pair implements  Comparable<Pair>{
            public int a,b;
            public Pair(int a,int b){this.a=a;this.b=b;}

            @Override
            public int compareTo(Pair o) {
                return a-o.a;
            }
        }

        HashMap<Integer,Integer> dist=new HashMap<>();
        for (Integer i: graph.keySet())
            dist.put(i,Integer.MAX_VALUE);
        PriorityQueue<Pair> pq=new PriorityQueue<>();
        Pair pair=new Pair(0,city_to);
        pq.add(pair);
        dist.put(city_to,0);

        while(!pq.isEmpty()){
            int u=pq.remove().b;

            for (Integer[] c: graph.get(u)){
                Integer v=c[0], w=c[1];
                if (dist.get(v)>dist.get(u)+w) {
                    dist.put(v,dist.get(u) + w);
                    pair= new Pair(dist.get(v), v);
                    pq.add(pair);
                }
            }
        }
        return dist;
    }

    private HashMap<Integer,Path> b_and_b(int city_to, HashMap<Integer,List<Integer[]>> graph){

        int N=graph.size();
        HashMap<Integer,Path> dist=new HashMap<>();
        for (Integer i: graph.keySet()){
            List<Integer[]> p=new ArrayList<>();
            dist.put(i,new Path(Integer.MAX_VALUE,p));
        }

        PriorityQueue<Path> pq=new PriorityQueue<>();
        List<Integer[]> p0=new ArrayList<>();

        p0.add(new Integer[]{city_to,0});
        Path path0=new Path(0,p0);
        pq.add(path0);
        dist.put(city_to,path0);

        while(!pq.isEmpty()){
            Path path=pq.remove();
            Integer u=path.last();

            for (Integer[] c: graph.get(u)){
                Integer v=c[0], w=c[1];
                if (dist.get(v).dist_sum>dist.get(u).dist_sum+w) {
                    Path np=new Path(dist.get(u).dist_sum+w,dist.get(u).add(v,w));
                    dist.put(v,np);
                    pq.add(np);
                }
            }
        }
        return dist;
    }
}

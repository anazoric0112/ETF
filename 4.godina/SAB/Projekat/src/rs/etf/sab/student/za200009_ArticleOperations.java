package rs.etf.sab.student;

import rs.etf.sab.operations.ArticleOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class za200009_ArticleOperations implements ArticleOperations {
    @Override
    public int createArticle(int shopId, String articleName, int articlePrice) {
        Connection conn = DB.getInstance().getConnection();
        String query = "insert into Article(name,price,idS,count)\n" +
                "values (?,?,?,0)\n";
        try ( PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,articleName);
            stmt.setDouble(2,articlePrice);
            stmt.setInt(3,shopId);
            stmt.executeUpdate();

            PreparedStatement stmt2=conn.prepareStatement("select IDENT_CURRENT('Article') as 'num'");
            ResultSet rs=stmt2.executeQuery();
            if (rs.next()){
                return rs.getInt("num");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }
}

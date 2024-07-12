package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Bill;

public class BillDAL {
   
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
 public List<Bill> getTop3BestSeller() {
    String query = "SELECT TOP 3 pid, SUM(b.amount) AS countAmount " +
                   "FROM Bills b " +
                   "GROUP BY pid " +
                   "ORDER BY countAmount DESC";
    List<Bill> lsBill = new ArrayList<>();
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Bill bill = new Bill();
            bill.setPid(rs.getString("pid"));
            bill.setAmount(rs.getInt("countAmount"));
            lsBill.add(bill);
        }
        ps.close();
        rs.close();
        return lsBill;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    public List<Bill> getTop10Username() {
    String query = "SELECT TOP 10 username, SUM(b.total) AS total " +
                   "FROM Bills b " +
                   "GROUP BY username " +
                   "ORDER BY total DESC";
    List<Bill> lsBill = new ArrayList<>();
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Bill bill = new Bill();
            bill.setUsername(rs.getString("username"));
            bill.setTotal(rs.getFloat("total"));
            lsBill.add(bill);
        }
        ps.close();
        rs.close();
        return lsBill;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    
    public static void main(String[] args) {
        BillDAL dal = new BillDAL(); 
        System.out.println(dal.getTop10Username().get(0).getTotal());
    }
}


package dal;

import model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAL{
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
   public Account getAccountByUsername(String username) {
    String query = "SELECT * FROM Accounts WHERE username=?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Account a = new Account(rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getInt("role"));
            ps.close();
            rs.close();
            return a;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
 
   
    public Account checkAccount(String user, String pass) {
    String query = "SELECT * FROM Accounts WHERE username=? AND password=?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, user);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Account a = new Account(rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getInt("role"));
            ps.close();
            rs.close();
            return a;
        }
        ps.close();
        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    
   public boolean addAccount(String username, String password, String fullname, String email, String phone, String address) {
    String query = "INSERT INTO Accounts (username, password, fullname, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?, 2)";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, fullname);
        ps.setString(4, email);
        ps.setString(5, phone);
        ps.setString(6, address);
        ps.executeUpdate();
        ps.close();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    
   public boolean updateAccount(String username, String email, String phone, String address, String newpass) {
    String query = "UPDATE Accounts SET password=?, email=?, phone=?, address=? WHERE username=?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, newpass);
        ps.setString(2, email);
        ps.setString(3, phone);
        ps.setString(4, address);
        ps.setString(5, username);
        ps.executeUpdate();
        ps.close();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    public static void main(String[] args) {
        AccountDAL dao = new AccountDAL();
        System.out.println(dao.checkAccount("admin", "123"));
        
    }

   public int getRoleByUser(String username, String password) {
    String query = "SELECT role FROM Accounts WHERE username=? AND password=?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int role = rs.getInt("role");
            ps.close();
            rs.close();
            return role;
        }
        ps.close();
        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 2;
}
}

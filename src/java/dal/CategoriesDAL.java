package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Categories;
import model.Product;

public class CategoriesDAL{
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
  public List<Categories> getAllCategories() {
    List<Categories> list = new ArrayList<>();
    String query = "SELECT * FROM Categories";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categories category = new Categories(rs.getString("catid"), rs.getString("name"));
            list.add(category);
        }
        ps.close();
        rs.close();
        return list;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    public static void main(String[] args) {
        CategoriesDAL dal = new CategoriesDAL();
        List<Categories> ls = new ArrayList<Categories>();
        ls  = dal.getAllCategories();
        for (Categories l : ls) {
            System.out.println(l.getCatid());
        }
    }
 
    public void addNewCategory(Categories c) {
    String query = "INSERT INTO Categories (catid, name) VALUES (?, ?)";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, c.getCatid());
        ps.setString(2, c.getName());
        ps.executeUpdate();
        ps.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

  public void deleteCategoriesByCatid(String catid) {
    String query = "DELETE FROM Categories WHERE catid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, catid);
        ps.executeUpdate();
        ps.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}


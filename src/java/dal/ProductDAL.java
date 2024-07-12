package dal;

import model.Account;
import model.Bill;
import model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ProductDAL{
        
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

   public List<Product> getAllProduct() {
    List<Product> list = new ArrayList<>();
    String query = "SELECT * FROM Products";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Product(rs.getString("pid"), rs.getString("name"),
                    rs.getString("description"), rs.getFloat("price"),
                    rs.getInt("quantity"), rs.getString("catid"),
                    rs.getString("image")));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
public List<Product> getProductByCategory(String category) {
    List<Product> list = new ArrayList<>();
    String query = "SELECT * FROM Products WHERE catid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, category);
        rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Product(rs.getString("pid"), rs.getString("name"),
                    rs.getString("description"), rs.getFloat("price"),
                    rs.getInt("quantity"), rs.getString("catid"),
                    rs.getString("image")));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    
  public List<Product> getProductBySearch(String search) {
    List<Product> list = new ArrayList<>();
    String query = "SELECT * FROM Products WHERE name LIKE ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1,"%" + search + "%");
        rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Product(rs.getString("pid"), rs.getString("name"),
                    rs.getString("description"), rs.getFloat("price"),
                    rs.getInt("quantity"), rs.getString("catid"),
                    rs.getString("image")));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
public Product getProductById(String pid) {
    String query = "SELECT * FROM Products WHERE pid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, pid);
        rs = ps.executeQuery();
        while (rs.next()) {
            Product p = new Product(rs.getString("pid"), rs.getString("name"),
                    rs.getString("description"), rs.getFloat("price"),
                    rs.getInt("quantity"), rs.getString("catid"),
                    rs.getString("image"));
            ps.close();
            rs.close();
            return p;
        }
        ps.close();
        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    public void addToCart(String username, String pid, int amount) {
    String query = "INSERT INTO Carts(username, pid, amount) VALUES (?, ?, ?)";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, pid);
        ps.setInt(3, amount);
        ps.executeUpdate();
        ps.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public int getQuantityById(String pid) {
    String query = "SELECT p.quantity FROM products p WHERE p.pid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, pid);
        rs = ps.executeQuery();
        if (rs.next()) {
            int quantity = rs.getInt("quantity");
            ps.close();
            rs.close();
            return quantity;
        }
        ps.close();
        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; // return -1 or any invalid value to indicate that the product was not found
}

    
  public void updateCart(String username, String pid, int amount) {
    String query = "UPDATE Carts SET amount = ? WHERE username = ? AND pid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setInt(1, amount);
        ps.setString(2, username);
        ps.setString(3, pid);
        ps.executeUpdate();
        ps.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
   public HashMap<String, Integer> getCart(String username) {
    HashMap<String, Integer> hashCart = new HashMap<>();
    String query = "SELECT * FROM Carts WHERE username = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        rs = ps.executeQuery();
        while (rs.next()) {
            hashCart.put(rs.getString("pid"), rs.getInt("amount"));
        }
        return hashCart;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return hashCart;
}
    
    public void removeProductCart(String username, String pid) {
    String query = "DELETE FROM Carts WHERE username = ? AND pid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, pid);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  public void removeAllCart(String username) {
    String query = "DELETE FROM Carts WHERE username = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
   public void addToBill(String username, String pid, int amount, float total) {
    String query = "INSERT INTO Bills(bid, username, pid, date, amount, total) VALUES (?, ?, ?, GETDATE(), ?, ?)";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, UUID.randomUUID().toString());
        ps.setString(2, username);
        ps.setString(3, pid);
        ps.setInt(4, amount);
        ps.setFloat(5, total);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}    
public List<Bill> getAllBillByUser(String username) {
    List<Bill> listB = new ArrayList<>();
    String query = "SELECT * FROM Bills b JOIN Products p ON b.pid = p.pid WHERE b.username=?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        rs = ps.executeQuery();
        while (rs.next()) {
            Bill b = new Bill(rs.getString("bid"), rs.getString("username"), rs.getString("pid"), rs.getDate("date"), rs.getInt("amount"), rs.getFloat("total"), rs.getString("name"), rs.getString("description"), rs.getFloat("price"), rs.getString("catid"), rs.getString("image"));
            listB.add(b);
        }
        return listB;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
   public void addNewProduct(Product p) {
    String query = "INSERT INTO Products(pid, name, description, price, quantity, catid, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, p.getPid());
        ps.setString(2, p.getName());
        ps.setString(3, p.getDescription());
        ps.setFloat(4, p.getPrice());
        ps.setInt(5, p.getQuantity());
        ps.setString(6, p.getCatid());
        ps.setString(7, p.getImage());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        ProductDAL dal = new ProductDAL();
        System.out.println(dal.getAllProduct());
    }

public void deleteProductByPid(String pid) {
    String query = "DELETE FROM Products WHERE pid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, pid);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

 public void updateProductById(Product p) {
    String query = "UPDATE Products SET name = ?, description = ?, price = ?, quantity = ?, catid = ?, image = ? WHERE pid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1, p.getName());
        ps.setString(2, p.getDescription());
        ps.setFloat(3, p.getPrice());
        ps.setInt(4, p.getQuantity());
        ps.setString(5, p.getCatid());
        ps.setString(6, p.getImage());
        ps.setString(7, p.getPid());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
   public void DecreaseProduct(Product p, int maxQuantity) {
    String query = "UPDATE Products SET quantity = ? WHERE pid = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        System.out.println("maxQuantity - p.getQuantity(): " + (maxQuantity - p.getQuantity()));
        ps.setInt(1, maxQuantity - p.getQuantity());
        ps.setString(2, p.getPid());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public List<Product> getListByPage(List<Product> list, int start,int end){
        ArrayList<Product> arr=new ArrayList<>();
        for(int i=start;i<end;i++){
            arr.add(list.get(i));
        }
        return arr;
    }

 public List<Product> getProductOrderByPrice(String search) {
    List<Product> list = new ArrayList<>();
    String query = "SELECT * FROM Products ORDER BY price ASC";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product p = new Product(rs.getString("pid"), rs.getString("name"), rs.getString("description"), rs.getFloat("price"), rs.getInt("quantity"), rs.getString("catid"), rs.getString("image"));
            list.add(p);
        }
        return list;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

   public List<Product> getProductOrderByBestSeller(String search) {
    List<Product> list = new ArrayList<>();
    String query = "SELECT p.*, COALESCE(SUM(b.amount), 0) AS countAmount " +
                   "FROM Products p LEFT JOIN Bills b ON p.pid = b.pid " +
                   "GROUP BY p.pid, p.name, p.description, p.price, p.quantity, p.catid, p.image " +
                   "ORDER BY countAmount DESC";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product p = new Product(rs.getString("pid"), rs.getString("name"), rs.getString("description"), rs.getFloat("price"), rs.getInt("quantity"), rs.getString("catid"), rs.getString("image"));
            int quant = rs.getInt("countAmount");
            list.add(p);
        }
        return list;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
   public List<Product> getProductOrderByName(String search) {
    List<Product> list = new ArrayList<>();
    String query = "SELECT * FROM Products ORDER BY name ASC";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product p = new Product(rs.getString("pid"), rs.getString("name"), rs.getString("description"), rs.getFloat("price"), rs.getInt("quantity"), rs.getString("catid"), rs.getString("image"));
            list.add(p);
        }
        return list;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
   
}
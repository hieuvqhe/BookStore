/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package adminController;

import dal.AccountDAL;
import dal.BillDAL;
import dal.ProductDAL;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Bill;
import model.Product;

/**
 *
 * @author Admin
 */
@WebServlet(name="adminStatistic", urlPatterns={"/adminStatistic"})
public class adminStatistic extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet adminStatistic</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet adminStatistic at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Bill> lsBill = new ArrayList<Bill>();
        BillDAL billdal = new BillDAL();
        ProductDAL pDAL = new ProductDAL();
        AccountDAL aDAL = new AccountDAL();
        
        List<Product> lsProduct = new ArrayList<Product>(); 
        List<Account> lsAccount = new ArrayList<Account>(); 
        lsBill = billdal.getTop3BestSeller();
        for (Bill bill : lsBill) {
            String pid = bill.getPid(); 
            Product p = new Product();
            p = pDAL.getProductById(pid);
            lsProduct.add(p);
        }
        lsBill = billdal.getTop10Username();
        System.out.println(lsBill.size());
        for (Bill bill : lsBill) {
            String username = bill.getUsername(); 
            Account acc = new Account();
            acc = aDAL.getAccountByUsername(username);
            lsAccount.add(acc);
        }
        System.out.println("lsAccount.size()" + lsAccount.get(0).getUsername());
        request.setAttribute("top5Product", lsProduct);
        request.setAttribute("top10User", lsAccount);
        request.getRequestDispatcher("statisticAdmin.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

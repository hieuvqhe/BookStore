/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.ProductDAL;
import jakarta.servlet.annotation.WebServlet;


/**
 *
 * @author Admin
 */
@WebServlet(name="CartServlet", urlPatterns={"/cart"})
public class CartServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CartServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath () + "</h1>");
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
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter pr = response.getWriter();
    HttpSession session = request.getSession();
    ProductDAL pDAL = new ProductDAL();
    String rmpid = request.getParameter("pid");
    try {
        HashMap<String, Integer> hashCart = (HashMap< String, Integer>) session.getAttribute("hashCart");
        if (hashCart == null) {
            hashCart = pDAL.getCart((String) session.getAttribute("user"));
        }
        List<Product> listCart = new ArrayList<>();
        HashMap<String, Integer> productQuantities = new HashMap<>();
        for (String pid : hashCart.keySet()) {
            Product pCart = pDAL.getProductById(pid);
            Product pCart_Amout = new Product(pCart.getPid(), pCart.getName(), pCart.getDescription(), pCart.getPrice(), hashCart.get(pid), pCart.getCatid(), pCart.getImage());
            listCart.add(pCart_Amout);
            productQuantities.put(pid, pCart.getQuantity());
        }
        if (rmpid != null) {
            pDAL.removeProductCart((String) session.getAttribute("user"), rmpid);
            session.setAttribute("hashCart", null);
            response.sendRedirect("cart");
        }
        session.setAttribute("listCart", listCart);
        session.setAttribute("productQuantities", productQuantities);
    } catch (Exception e) {
        request.getRequestDispatcher("cart.jsp").include(request, response);
    }

    request.getRequestDispatcher("cart.jsp").include(request, response);

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pr = response.getWriter();
        String pid = request.getParameter("pid");
        HttpSession session = request.getSession();
        List<Product> listCart = (List<Product>) session.getAttribute("listCart");
        for (Product p : listCart) {
            if (p.getPid().equals(pid)) {
                listCart.remove(p);
            }
        }
        session.setAttribute("listCart", listCart);
        
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

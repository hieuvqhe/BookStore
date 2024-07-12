/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAL;

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
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    HashMap<String, Integer> hashCart = new HashMap<String, Integer>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
    AccountDAL aDAL = new AccountDAL();
    ProductDAL pDAL = new ProductDAL();
    HttpSession session = request.getSession();
    //////////////////////////////////////////
    String username = (String) session.getAttribute("user");
    String password = (String) session.getAttribute("pass");
    if ( aDAL.getRoleByUser(username,password) == 1 ) {
        request.getRequestDispatcher("adminlist").include(request, response);
        return; 
    }
    String category = request.getParameter("catid");
    List<Product> bookList = new ArrayList<>();
    //////////////////////////////////////////
    if (category == null || category.equals("")) {
        bookList = pDAL.getAllProduct();
    } else {
        bookList = pDAL.getProductByCategory(category);
    }

    int page, numperpage=6;
    int size=bookList.size();
    int num=(size%6==0?(size/6):((size/6))+1);//so trang
    String xpage=request.getParameter("page");
    if(xpage==null){
        page=1;
    }else{
        page=Integer.parseInt(xpage);
    }
    int start,end;
    start=(page-1)*numperpage;
    end=Math.min(page*numperpage, size);
    List<Product> list=pDAL.getListByPage(bookList, start, end);

    request.setAttribute("page", page);
    request.setAttribute("num", num);
    request.setAttribute("bookList", list);
    session.setAttribute("bookList", list);
    ///////////////////////////////////////////
    try {
        String pid = request.getParameter("pid");
        int amount = Integer.parseInt(request.getParameter("amount"));
        if (pid == null || amount == 0) {
            request.getRequestDispatcher("shop.jsp").include(request, response);
        } else {
            HashMap<String, Integer> hashCart = pDAL.getCart(username);
            if (hashCart.containsKey(pid)) {
                hashCart.put(pid, (hashCart.get(pid) + amount));
                pDAL.updateCart(username, pid, hashCart.get(pid));
                session.setAttribute("hashCart", hashCart);
            } else {
                hashCart.put(pid, amount);
                pDAL.addToCart(username, pid, hashCart.get(pid));
                session.setAttribute("hashCart", hashCart);
            }
            request.getRequestDispatcher("shop.jsp").include(request, response);
        }
    } catch (Exception e) {
        request.getRequestDispatcher("shop.jsp").include(request, response);
    }
}

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

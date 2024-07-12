/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ProductDAL;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author Admin
 */
@WebServlet(name="QuantityCard", urlPatterns={"/quantityCard"})
public class QuantityCard extends HttpServlet {
   
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
            out.println("<title>Servlet QuantityCard</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuantityCard at " + request.getContextPath () + "</h1>");
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
        String pid = request.getParameter("pid"); 
        HashMap<String, Integer> hashCart = new HashMap<String, Integer>();
        ProductDAL pDAL = new ProductDAL();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");
        if (username != null) {
            hashCart = pDAL.getCart(username);
        }
        if (hashCart.containsKey(pid)) {
            hashCart.put(pid, (hashCart.get(pid) -1 ));
            pDAL.updateCart(username, pid, hashCart.get(pid));
            session.setAttribute("hashCart", hashCart);
        }
        response.sendRedirect("cart");
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
        String pid = request.getParameter("pid"); 
        HashMap<String, Integer> hashCart = new HashMap<String, Integer>();
        ProductDAL pDAL = new ProductDAL();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");
        if (username != null) {
            hashCart = pDAL.getCart(username);
        }
        if (hashCart.containsKey(pid)) {
            hashCart.put(pid, (hashCart.get(pid) + 1 ));
            pDAL.updateCart(username, pid, hashCart.get(pid));
            session.setAttribute("hashCart", hashCart);
        }
        response.sendRedirect("cart");
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

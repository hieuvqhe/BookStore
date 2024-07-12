/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import dal.AccountDAL;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SignInServlet", urlPatterns = {"/signin"})
public class SignInServlet extends HttpServlet {

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
            out.println("<title>Servlet SignInServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignInServlet at " + request.getContextPath() + "</h1>");
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
        request.setAttribute("check", "null");
        request.getRequestDispatcher("signIn.jsp").include(request, response);
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
        // Lấy thông tin tên người dùng và mật khẩu từ yêu cầu.
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        // Tạo đối tượng AccountDAL để tương tác với cơ sở dữ liệu.
        AccountDAL accdal = new AccountDAL();

        // Lấy đối tượng session hiện tại.
        HttpSession session = request.getSession();

        // Kiểm tra thông tin đăng nhập của người dùng.
        Account a = accdal.checkAccount(user, pass);

        // Nếu thông tin đăng nhập chính xác (đối tượng Account không phải là null),
        // thực hiện các bước sau:
        if (a != null) {
            // Đặt thuộc tính 'check' với giá trị 'success' vào yêu cầu.
            request.setAttribute("check", "success");

            // Lưu thông tin người dùng vào session.
            session.setAttribute("user", user);
            session.setAttribute("pass", pass);

            // Chuyển tiếp yêu cầu và phản hồi đến trang 'signIn.jsp'.
            request.getRequestDispatcher("signIn.jsp").forward(request, response);
        } else {
            // Nếu thông tin đăng nhập không chính xác, đặt thuộc tính 'check'
            // với giá trị 'fail' vào yêu cầu.
            request.setAttribute("check", "fail");

            // Chuyển tiếp yêu cầu và phản hồi đến trang 'signIn.jsp'.
            request.getRequestDispatcher("signIn.jsp").forward(request, response);
        }
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

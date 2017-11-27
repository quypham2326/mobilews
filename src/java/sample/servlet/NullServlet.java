    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.ws1dao.tblMobileDAO;

/**
 *
 * @author HP
 */
public class NullServlet extends HttpServlet {
private final String loginPage="login.html";
private final String staffPage="staffsearch.jsp";
private final String managerPage="userSearch.jsp";
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
        PrintWriter out = response.getWriter();
        String url=loginPage;
        try  {
            Cookie[] cookie=request.getCookies();
            if(cookie!=null){
                for (Cookie cooky : cookie) {
                    String username=cooky.getName();
                    int password=Integer.parseInt(cooky.getValue());
                    HttpSession session = request.getSession();
                    tblMobileDAO dao= new tblMobileDAO();
                    int role= dao.checkLogin(username, password);
                    if(role==0){
                        url=managerPage;
                        
                        session.setAttribute("USERNAME", username);
                    }else if(role==1||role==2){
                        url=staffPage;
                        session.setAttribute("USERNAME", username);
                    }
                }
            }//end if
        } catch (SQLException ex) {
            log("NULLSERVLET_ERRSQL "+ex.getMessage());
    } catch (NamingException ex) {
            log("NULLSERVLET_ERRNAMING "+ex.getMessage());
    }finally{
            response.sendRedirect(url);
            out.close();
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
        processRequest(request, response);
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
        processRequest(request, response);
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

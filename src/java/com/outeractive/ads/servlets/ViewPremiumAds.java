/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.servlets;


import com.outeractive.ads.entities.PremiumAd;
import com.outeractive.ads.sessions.PremiumAdFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aardvocate
 */
@WebServlet(name = "ViewAds", urlPatterns = {"/viewads"})
public class ViewPremiumAds extends HttpServlet {

    @EJB
    PremiumAdFacade premiumAdFacade;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
        HttpSession session = request.getSession(true);
        if(request.getParameter("action") != null) {
            session.invalidate();
            response.sendRedirect("/lyrics/login.html");
            return;
        }
        
        if(request.getParameter("pause") != null) {
            long id = Long.parseLong(request.getParameter("pause"));
            PremiumAd pa = premiumAdFacade.find(id);
            pa.setIsActive(false);
            premiumAdFacade.edit(pa);
        }
        
        if(request.getParameter("start") != null) {
            long id = Long.parseLong(request.getParameter("start"));
            PremiumAd pa = premiumAdFacade.find(id);
            pa.setIsActive(true);
            premiumAdFacade.edit(pa);
        }        
        
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>NLW Ads: View Your Ads</title>");
            out.println("<link rel=\"stylesheet\" href=\"/lyrics/css/adsadmin.css\" />");
            out.println("</head>");
            out.println("<body>");

            String username = request.getParameter("username");
            String password = request.getParameter("password");            

            if (username == null) {
                if (session.getAttribute("username") != null) {
                    username = session.getAttribute("username").toString();
                    password = session.getAttribute("password").toString();
                }
            } else {
                session.setAttribute("username", username);
                session.setAttribute("password", password);
            }

            if (username == null) {
                out.println("<h1>You are not authorized to view this page</h1>");
            }
            List<PremiumAd> premiumAds = premiumAdFacade.findByUsernameAndPassword(username, password);

            /* TODO output your page here. You may use following sample code. */
            out.println("<h1>Ads for " + username + "</h1>");
            out.println("<table border='1'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Views</td>");
            out.println("<th>Start Date</td>");
            out.println("<th>End Date</td>");
            out.println("<th>Title</td>");
            out.println("<th>Description</th>");
            out.println("<th>Priority</th>");
            out.println("<th>Image URL</th>");
            out.println("<th>Info URL</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");
            out.println("</thead>");
            if (premiumAds.isEmpty()) {
            } else {
                int count = 0;
                for (PremiumAd pa : premiumAds) {
                    String trClass = count % 2 == 0 ? "odd" : "even";
                    out.println("<tr class=" + trClass + ">");
                    out.println("<td>" + pa.getCount() + "</td>");
                    out.println("<td>" + pa.getStartDate() + "</td>");
                    out.println("<td>" + pa.getEndDate() + "</td>");
                    out.println("<td>" + pa.getTitle() + "</td>");
                    out.println("<td>" + pa.getDescription() + "</td>");
                    out.println("<td>" + pa.getPriority() + "</td>");
                    out.println("<td><a href='" + pa.getImgURL() + "' target='_blank'>view</a></td>");
                    out.println("<td><a href='" + pa.getInfoURL() + "' target='_blank'>view</a></td>");
                    String active = pa.isIsActive() ? "active" : "in-active";
                    out.println("<td>" + active + "</td>");
                    String pause = pa.isIsActive() ? "pause" : "start";
                    out.println("<td><a href='/lyrics/viewads?" + pause + "=" + pa.getId() + "'>" + pause + "</td>");
                    out.println("</tr>");
                    count++;
                }
            }            
            out.println("</table>");
            out.println("<br /><br />");
            out.println("<a href='/lyrics/viewads'>Reload</a>");
            out.println("<br /><br />");
            out.println("<a href='/lyrics/viewads?action=logout'>Logout</a>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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

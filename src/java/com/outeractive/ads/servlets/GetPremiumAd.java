/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.servlets;


import com.outeractive.ads.entities.AdUserProfile;
import com.outeractive.ads.entities.PremiumAd;
import com.outeractive.ads.servlets.utils.MStrings;
import com.outeractive.ads.sessions.PremiumAdFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trinisoftinc.json.me.MyJSONException;
import trinisoftinc.json.me.MyJSONObject;

/**
 *
 * @author trinisoftinc
 */
@WebServlet(name = "GetPremiumAd", urlPatterns = {"/premiumad"})
public class GetPremiumAd extends HttpServlet {

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
        try {
            MyJSONObject retval = new MyJSONObject();
            AdUserProfile userProfile = new AdUserProfile();
            userProfile.setAge(request.getParameter("age"));
            userProfile.setIsArtist(Boolean.parseBoolean(request.getParameter("isArtist")));
            userProfile.setLocation(request.getParameter("location"));
            userProfile.setSex(request.getParameter("sex"));
            
            PremiumAd premiumAd = premiumAdFacade.findNextAd(userProfile);
            if (premiumAd == null) {
                retval.put("status_text", "not found");
                retval.put("status_code", 404);
                out.println(retval.toString());
                return;
            }            
            
            retval.put("status_code", 200);
            retval.put("status_text", "Success");
            retval.put("desc", MStrings.encode(premiumAd.getDescription()));
            retval.put("id", premiumAd.getId());
            retval.put("img", MStrings.encode(premiumAd.getImgURL()));
            retval.put("url", MStrings.encode(premiumAd.getInfoURL()));
            retval.put("title", MStrings.encode(premiumAd.getTitle()));
            out.println(retval.toString());
        } catch (MyJSONException ex) {
            Logger.getLogger(GetPremiumAd.class.getName()).log(Level.SEVERE, null, ex);
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.servlets;

import com.outeractive.ads.entities.Ad;
import com.outeractive.ads.entities.AdResUnit;
import com.outeractive.ads.entities.AdUserProfile;
import com.outeractive.ads.sessions.AdFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aardvocate
 */
@WebServlet(name = "HTMLAd", urlPatterns = {"/ad"})
public class GetAd extends HttpServlet {

    @EJB
    AdFacade adFacade;

    private void printAd(PrintWriter out, AdResUnit resUnit, int width, int height) {
        out.println("<div style='border: brown groove medium; width: " + width + "px; height: " + height + "px; margin: 0 auto'>");
        out.println("<a href='" + resUnit.getAdvertiserURL() + "'>");
        out.println("<img width='" + width + "' height='" + height + "' alt='Naija Lyrics Wiki Sponsor' src='" + resUnit.getResImageLink() + "'>");
        out.println("</a>");
        out.println("</div>");
    }

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
            AdUserProfile userProfile = new AdUserProfile();
            userProfile.setAge(request.getParameter("age"));
            userProfile.setIsArtist(Boolean.parseBoolean(request.getParameter("isArtist")));
            userProfile.setLocation(request.getParameter("location"));
            userProfile.setSex(request.getParameter("sex"));
            int width = Integer.parseInt(request.getParameter("w"));
            int height = Integer.parseInt(request.getParameter("h"));
            
            System.err.println("H: " + height);                    

            Ad ad = adFacade.findNextAd(userProfile);
            if (ad == null) {
                out.println("");
            } else {                
                if (width < 240) {
                    printAd(out, ad.getVlowResImage(), width, (height/10)); //50
                } else if (width >= 240 && width < 320) {
                    printAd(out, ad.getLowResImage(), width, (height/10)); //60
                } else if(width >= 320 && width < 480){
                    printAd(out, ad.getMediumResImage(), width, (height/10)); //80
                } else if(width >= 480 && width < 640) {
                    printAd(out, ad.getHighResImage(), width, (height/10)); //100
                } else if(width >= 640 && width < 1024) {
                    printAd(out, ad.getVhighResImage(), width, (height/10)); //120
                } else {
                    printAd(out, ad.getHdResImage(), width, (height/10)); //140
                }                
            }

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

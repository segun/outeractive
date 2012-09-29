/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.servlets;


import com.outeractive.ads.entities.AdUserProfile;
import com.outeractive.ads.entities.PremiumAd;
import com.outeractive.ads.sessions.AdUserProfileFacade;
import com.outeractive.ads.sessions.PremiumAdFacade;
import com.trinisoft.libraries.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AddPremiumAd", urlPatterns = {"/addpremiumad"})
public class AddPremiumAd extends HttpServlet {

    @EJB
    PremiumAdFacade premiumAdFacade;
    @EJB
    AdUserProfileFacade userProfileFacade;

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
            String desc = request.getParameter("desc");
            String imgURL = request.getParameter("img");
            String infoURL = request.getParameter("info");
            String title = request.getParameter("title");
            String priority = request.getParameter("priority");
            String startDateString = request.getParameter("startDate");
            String endDateString = request.getParameter("endDate");
            String loginName = request.getParameter("loginname");
            String loginPass = request.getParameter("loginpass");
            String age = request.getParameter("age");
            String sex = request.getParameter("sex");
            String location = request.getParameter("location");
            boolean isArtist = Boolean.parseBoolean(request.getParameter("isArtist"));                       
            
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(startDateString);
                endDate = new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(endDateString);
            } catch (ParseException ex) {
                Logger.getLogger(AddPremiumAd.class.getName()).log(Level.SEVERE, null, ex);
            }

            PremiumAd pad = new PremiumAd();
            pad.setStartDate(startDate);
            pad.setEndDate(endDate);
            pad.setDescription(desc);
            pad.setImgURL(imgURL);
            pad.setInfoURL(infoURL);
            pad.setIsActive(true);
            pad.setTitle(title);
            pad.setPriority(Integer.parseInt(priority));
            pad.setShown(0);
            pad.setCount(0);
            pad.setUsername(loginName);
            pad.setPassword(Utils.encryptPassword(loginPass, "SHA"));
            pad.setDateAdded(new Date());

            premiumAdFacade.create(pad);

            AdUserProfile userProfile = new AdUserProfile();
            userProfile.setAge(age);
            userProfile.setIsArtist(isArtist);
            userProfile.setLocation(location);
            userProfile.setPremiumAd(pad);
            userProfile.setSex(sex);
            userProfileFacade.create(userProfile);
            
            pad.setUserProfile(userProfile);
            premiumAdFacade.edit(pad);
            
            response.sendRedirect("addpremiumad.html");
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
        //processRequest(request, response);
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

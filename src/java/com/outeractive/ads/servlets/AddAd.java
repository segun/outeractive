/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.servlets;

import com.outeractive.ads.entities.Ad;
import com.outeractive.ads.entities.AdResUnit;
import com.outeractive.ads.entities.AdUserProfile;
import com.outeractive.ads.entities.Advertiser;
import com.outeractive.ads.sessions.AdFacade;
import com.outeractive.ads.sessions.AdResUnitFacade;
import com.outeractive.ads.sessions.AdUserProfileFacade;
import com.outeractive.ads.sessions.AdvertiserFacade;
import com.trinisoft.libraries.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "AddAd", urlPatterns = {"/addad"})
public class AddAd extends HttpServlet {

    @EJB
    AdvertiserFacade advertiserFacade;
    @EJB
    AdResUnitFacade adResUnitFacade;
    @EJB
    AdUserProfileFacade adUserProfileFacade;
    @EJB
    AdFacade adFacade;

    private AdResUnit createAdResUnit(String adURL, String resLink) {
        AdResUnit adResUnit = new AdResUnit();
        adResUnit.setAdvertiserURL(adURL);
        adResUnit.setResImageLink(resLink);
        adResUnitFacade.create(adResUnit);
        return adResUnit;
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
            String adURL = request.getParameter("adURL");
            String vlRes = request.getParameter("vlRes");
            String lRes = request.getParameter("lRes");
            String mRes = request.getParameter("mRes");
            String hRes = request.getParameter("hRes");
            String vhRes = request.getParameter("vhRes");
            String hdRes = request.getParameter("hdRes");
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

            Advertiser advertiser = advertiserFacade.findByEmailAndPassword(loginName, loginPass);
            if (advertiser == null) {
                advertiser = new Advertiser();
                advertiser.setEmail(loginName);
                advertiser.setPassword(Utils.encryptPassword(loginPass, "SHA"));
                advertiserFacade.create(advertiser);
            }

            AdResUnit vlResUnit = createAdResUnit(adURL, vlRes);
            AdResUnit lResUnit = createAdResUnit(adURL, lRes);
            AdResUnit mResUnit = createAdResUnit(adURL, mRes);
            AdResUnit hResUnit = createAdResUnit(adURL, hRes);
            AdResUnit vhResUnit = createAdResUnit(adURL, vhRes);
            AdResUnit hdResUnit = createAdResUnit(adURL, hdRes);

            Ad ad = new Ad();
            ad.setAdvertiser(advertiser);
            ad.setCount(0);
            ad.setShown(0);
            ad.setDateAdded(new Date());
            ad.setStartDate(startDate);
            ad.setEndDate(endDate);
            ad.setHdResImage(hdResUnit);
            ad.setHighResImage(hResUnit);
            ad.setLowResImage(lResUnit);
            ad.setMediumResImage(mResUnit);
            ad.setVhighResImahe(vhResUnit);
            ad.setVlowResImage(vlResUnit);
            ad.setPriority(Integer.parseInt(priority));
            ad.setIsActive(true);
            adFacade.create(ad);

            AdUserProfile adUserProfile = new AdUserProfile();
            adUserProfile.setAge(age);
            adUserProfile.setIsArtist(isArtist);
            adUserProfile.setLocation(location);
            adUserProfile.setSex(sex);
            adUserProfile.setAd(ad);
            adUserProfileFacade.create(adUserProfile);

            ad.setUserProfile(adUserProfile);
            adFacade.edit(ad);

            List<Ad> ads = advertiser.getAds();
            if (ads == null) {
                ads = new ArrayList<Ad>();
            }
            ads.add(ad);
            advertiser.setAds(ads);
            advertiserFacade.edit(advertiser);
            
            response.sendRedirect("addad.html");
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

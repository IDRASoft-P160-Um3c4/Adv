/*
 * DownloaderServlet.java
 *
 * Created on 24 de noviembre de 2003, 03:53 PM
 */

package com.micper.multipart;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author  evalladares
 */
public class DownloaderServlet extends HttpServlet{

    /**
	 *
	 */
	private static final long serialVersionUID = 9122014329420477006L;

	/** Creates a new instance of DownloaderServlet */
    public DownloaderServlet() {
    }
     public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /** Destroys the servlet.
     */
    public void destroy() {

    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        byte[] archivo = null;
        DBManager dbm = new DBManager();
        try{
        System.out.print("DOWNLOADER");
        if(request.getParameter("cExpediente") != null){
        System.out.print("Expediente-"+(String)request.getParameter("cExpediente"));
        System.out.print("iCveClasifExped"+request.getParameter("iCveClasifExped").toString());
        System.out.print("iNumDocto"+request.getParameter("iNumDocto").toString());

        archivo = dbm.getFile((String)request.getParameter("cExpediente"),
                           new Integer(request.getParameter("iCveClasifExped").toString()).intValue(),
                           new Integer(request.getParameter("iNumDocto").toString()).intValue());
        }
        else{
          System.out.print("iAnioBoleta: "+request.getParameter("iAnioBoleta"));
          System.out.print("iNumBoleta: "+request.getParameter("iNumBoleta"));
          System.out.print("iCveEstacion: "+request.getParameter("iCveEstacion"));

          archivo = dbm.getFile(Integer.parseInt(request.getParameter("iAnioBoleta")),
                                Integer.parseInt(request.getParameter("iNumBoleta")),
                                Integer.parseInt(request.getParameter("iCveEstacion")));
        }
        }catch(DBException dbe){
            dbe.printStackTrace();
        }catch(Exception e){
          e.printStackTrace();
        }
        if(request.getParameter("cExpediente") != null){
          response.setContentType("application/octet-stream");
          OutputStream browser = response.getOutputStream();
          browser.write(archivo);
          browser.flush();
          browser.close();
        }
        else{
          if(archivo != null){
            try{
              response.setContentType("application/octet-stream");
              OutputStream browser = response.getOutputStream();
              browser.write(archivo);
              browser.flush();
              browser.close();
            }
            catch(Exception e){
              e.printStackTrace();
            }
          }
          else{
            try{
            response.sendRedirect("pg03060913.jsp");
          }
          catch(Exception e){
            e.printStackTrace();
          }
        }


        }

    }

    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }

}

/*
 * ParserServlet.java
 *
 * Created on 18 de noviembre de 2003, 11:41 AM
 */

package com.micper.multipart;
import com.micper.excepciones.*;
import java.io.*;
//import java.net.*;
//import java.util.*;
//import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.micper.util.*;
//import gob.sct.siirtv.vo.*;
//import gob.sct.sipmm.dao.*;
//import gob.sct.siirtv.bo.*;

/**
 * @author Tecnologia Inred S.A. de C.V.
 * @author Enrique Valladares
 * @version
 */
public class ParserServlet extends HttpServlet {

    /**
	 *
	 */
	private static final long serialVersionUID = 5971026383963104320L;

	/** Initializes the servlet.
     */
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
        HttpServletRequest r = (HttpServletRequest)request;
        MultipartParser mp = new MultipartParser(r,Integer.MAX_VALUE);
        Part p=null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int iNumDocto = 0;
        while((p=mp.readNextPart())!=null) {
          //System.out.print("p.getName()="+p.getName());
          //System.out.print("p.isFile()="+p.isFile());
            if(p.isFile()) {
                FilePart fp = (FilePart)p;
                //System.out.print("fp.getFileName()="+fp.getFileName());
                //System.out.print("fp.getFilePath()="+fp.getFilePath());
                fp.writeTo(baos);
                baos.close();
                request.setAttribute("cRutaArchDig",fp.getFilePath());
            } else if(p.isParam()) {
                ParamPart pp = (ParamPart)p;
                //System.out.print("ELSE-p.getName()=="+p.getName());
                //System.out.print("ELSE-pp.getStringValue()=="+pp.getStringValue());
                request.setAttribute(p.getName(), pp.getStringValue());
            } else {
                throw new InternalError("PART DE TIPO DESCONOCIDO. INCAPAZ PARSEAR REQUEST.");
            }
        }
        //System.out.print("baos.size()="+baos.size());
        byte[] archivo = baos.toByteArray();
        //System.out.print("archivo.length="+archivo.length);
        DBManager dbm = new DBManager();
        try{
           if (request.getAttribute("Accion").toString().compareToIgnoreCase("1")==0){
              //System.out.print("--SAVEFILE PARSERSERVELER--");
              TFechas Fecha    = new TFechas();
//              TDDoctosExp DDoctosExp = new TDDoctosExp();
//              TVDoctosExp VDoctosExp = new TVDoctosExp();

//              VDoctosExp.setcExpediente(request.getAttribute("cExpediente").toString());
//              VDoctosExp.setiCveClasifExped(new Integer(request.getAttribute("iCveClasifExped").toString()));
//              iNumDocto = DDoctosExp.findLast(VDoctosExp) + 1;

              //System.out.print("cExpediente--"+request.getAttribute("cExpediente").toString());
              //System.out.print("iCveClasifExped--"+request.getAttribute("iCveClasifExped").toString());
              //System.out.print("iNumDocto--"+iNumDocto);
              //System.out.print("iNumHojas--"+request.getAttribute("iNumHojas").toString());
              //System.out.print("dtArchivo--"+request.getAttribute("dtArchivo").toString());
              //System.out.print("dtGlosa--"+request.getAttribute("dtGlosa").toString());
              //System.out.print("iNoLegajo--"+request.getAttribute("iNoLegajo").toString());
              //System.out.print("cDscDocumento--"+request.getAttribute("cDscDocumento").toString());
              //System.out.print("iFolioSalida--"+request.getAttribute("iFolioSalida").toString());
              //System.out.print("iNumAnexo--"+request.getAttribute("iNumAnexo").toString());
              //System.out.print("iCveClasTrans--"+request.getAttribute("iCveClasTrans").toString());

              dbm.saveFile(archivo, (String)request.getAttribute("cExpediente"),
                                   new Integer(request.getAttribute("iCveClasifExped").toString()).intValue(),
                                   iNumDocto,
                                   new Integer(request.getAttribute("iNumHojas").toString()).intValue(),
                                   Fecha.getDateSQL(request.getAttribute("dtArchivo").toString()),
                                   Fecha.getDateSQL(request.getAttribute("dtGlosa").toString()),
                                   new Integer(request.getAttribute("iNoLegajo").toString()).intValue(),
                                   (String)request.getAttribute("cDscDocumento"),
                                   new Integer(request.getAttribute("iFolioSalida").toString()).intValue(),
                                   new Integer(request.getAttribute("iNumAnexo").toString()).intValue(),
                                   new Integer(request.getAttribute("iCveClasTrans").toString()).intValue(),
                                   (String)request.getAttribute("cRutaArchDig"));
           }
           else{
             if(request.getAttribute("iCveClasifExped") != null){
               //System.out.print("--UPDATEFILE PARSERSERVELER--");
               //System.out.print("cExpediente--"+request.getAttribute("cExpediente").toString());
               //System.out.print("iCveClasifExped--"+request.getAttribute("iCveClasifExped").toString());
               //System.out.print("iNumDocto--"+request.getAttribute("iNumDocto").toString());
               //System.out.print("iNumHojas--"+request.getAttribute("iNumHojas").toString());
               //System.out.print("dtArchivo--"+request.getAttribute("dtArchivo").toString());
               //System.out.print("dtGlosa--"+request.getAttribute("dtGlosa").toString());
               //System.out.print("iNoLegajo--"+request.getAttribute("iNoLegajo").toString());
               //System.out.print("cDscDocumento--"+request.getAttribute("cDscDocumento").toString());
               //System.out.print("iFolioSalida--"+request.getAttribute("iFolioSalida").toString());
               //System.out.print("iNumAnexo--"+request.getAttribute("iNumAnexo").toString());
               //System.out.print("iCveClasTrans--"+request.getAttribute("iCveClasTrans").toString());
               TFechas Fecha    = new TFechas();
               dbm.updateFile(archivo, (String)request.getAttribute("cExpediente"),
                              new Integer(request.getAttribute("iCveClasifExped").toString()).intValue(),
                              new Integer(request.getAttribute("iNumDocto").toString()).intValue(),
                              new Integer(request.getAttribute("iNumHojas").toString()).intValue(),
                              Fecha.getDateSQL(request.getAttribute("dtArchivo").toString()),
                              Fecha.getDateSQL(request.getAttribute("dtGlosa").toString()),
                              new Integer(request.getAttribute("iNoLegajo").toString()).intValue(),
                              (String)request.getAttribute("cDscDocumento"),
                              new Integer(request.getAttribute("iFolioSalida").toString()).intValue(),
                              new Integer(request.getAttribute("iNumAnexo").toString()).intValue(),
                              new Integer(request.getAttribute("iCveClasTrans").toString()).intValue(),
                              (String)request.getAttribute("cRutaArchDig"));
               iNumDocto = new Integer(request.getAttribute("iNumDocto").toString()).intValue();
             }
             else{
               int iConfirm = 0;
               //System.out.print("iAnioBoleta: "+request.getAttribute("iAnioBoleta").toString());
               //System.out.print("iNumBoleta: "+request.getAttribute("iNumBoleta").toString());
               //System.out.print("iCveEstacion: "+request.getAttribute("iCveEstacion").toString());
               iConfirm = dbm.updateFile2(archivo,
                               Integer.parseInt(request.getAttribute("iAnioBoleta").toString()),
                               Integer.parseInt(request.getAttribute("iNumBoleta").toString()),
                               Integer.parseInt(request.getAttribute("iCveEstacion").toString()));
               if(iConfirm == 0){
                 iConfirm = dbm.saveFile2(archivo,
                                          Integer.parseInt(request.getAttribute("iAnioBoleta").toString()),
                                          Integer.parseInt(request.getAttribute("iNumBoleta").toString()),
                                          Integer.parseInt(request.getAttribute("iCveEstacion").toString()));
               }
               //System.out.print("Accion: "+iConfirm);
             }
           }
        }
        catch(DBException dbe){
          dbe.printStackTrace();
        }
        catch(DAOException doe){
          doe.printStackTrace();
        }
        catch(Exception ex){
          ex.printStackTrace();
        }

        response.getWriter().println("<script language=\"JavaScript\">");
    //        response.getWriter().println("window.opener.fSubmitForm('Actual');");
    if(request.getAttribute("iCveClasifExped") != null){
      response.getWriter().println("window.opener.fSubmite('"+request.getAttribute("cExpediente").toString()+"','"
                                   +request.getAttribute("iCveClasifExped").toString()+"','"+iNumDocto+"');");
    }
    response.getWriter().println("window.close();");
    response.getWriter().println("</script>");
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

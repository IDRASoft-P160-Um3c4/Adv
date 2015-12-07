package gob.sct.sipmm.dao;

import gob.sct.sipmm.cntmgr.CM_GetContent;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.micper.ingsw.TParametro;

import gob.sct.sipmm.dao.*;
import java.util.*;

public class getDocTab extends HttpServlet {

    private static final long serialVersionUID = 1152221805525508141L;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    } 

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String[] keys = { "userid", "password", "entity", "maxResults","queryOP", "lintiCveDocumen" };
        String[] operators = { "", "", "", "", "", "=" };

        CM_GetContent cmImport = new CM_GetContent();
        int iCarga = 0;
        byte[] btArchivo = null;
        TParametro  vParametros = new TParametro("44");	
        String cUsrCM = vParametros.getPropEspecifica("usrCM").toString();
        String cPwdCM = vParametros.getPropEspecifica("pwdCM").toString();
        String cEntidadCM = vParametros.getPropEspecifica("entidadCM").toString();
        try {
            TDGRLPais dCon = new TDGRLPais(); 
            Vector vcCon = dCon.findByCustom("","SELECT IEJERCICIO,INUMSOLICITUD FROM INTTRAMITEDOCS where ICVEDOCDIG = "+request.getParameter("ID")+" and ICVEUSUNOTIFICA = "+request.getParameter("IC"));
            if(vcCon.size() > 0) {
               String[] values = { cUsrCM, cPwdCM, cEntidadCM, "1", "true",request.getParameter("ID") };
               btArchivo = cmImport.connect(keys, values, operators);
            }else
               iCarga = 2; 
        } catch (Exception e) {
            iCarga = 2;
            e.printStackTrace();
        } finally {
            if (iCarga == 0) {          
                response.setContentType("application/pdf");             
                OutputStream browser = response.getOutputStream();
                browser.write(btArchivo);
                browser.flush();
                browser.close();
            } else {
                response
                        .getWriter()
                        .println("<html><body><form name=\"frm1\"></form></body></html><script language=\"JavaScript\">");
                response.getWriter().println("alert('No fue posible descargar el documento.');top.window.close();");
                response.getWriter().println("</script>");
            }
        }
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
package gob.sct.sipmm.dao;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

//import gob.sct.sipmm.dao.DiskFileItemFactory;
//import gob.sct.sipmm.dao.ServletFileUpload;
import gob.sct.sipmm.cntmgr.*;
import gob.sct.sipmm.dao.ws.*;

public class UploadContestaNotifica extends HttpServlet {
    private static final long serialVersionUID = -6391312215178099481L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        TParametro VParametros = new TParametro("44");
        String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
        DbConnection dbConn = null;
        TDTRARegEtapasXModTram etapas = new TDTRARegEtapasXModTram();
        TDTRARegSolicitud solicitud = new TDTRARegSolicitud();
        TDINTSolicitud inSol= new TDINTSolicitud();
        Connection conn = null;
        TDGRLPais pais = new TDGRLPais();
        PreparedStatement lPStmt = null;
        String[] keys = { "userid","password","entity","mimeType","lintiCveDocumen" };
        CM_Import cmImport = new CM_Import();
        boolean bLoad = true;
        try {

            // Generaci贸n de la Conexi贸n
            dbConn = new DbConnection(dataSourceName);
            conn = dbConn.getConnection();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(2);

            // Generaci贸n de la configuraci贸n
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(10 * 1024 * 1024);
            factory.setRepository(new File("."));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(10 * 1024 * 1024);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            String iCveDocDigAnt = "", nombre = "", ICVETRAMITEINT = "", ICVEUSUNOTIFICA = "0", 
                   ICVEESTATUS = "38", COBSERVACION = "", CNOMARCHIVO = "";
            String iEjercicio="",iNumSolicitud="";
            HashMap hmNotifica = (HashMap) request.getSession(true).getAttribute("Notifica");
            //HashMap hmNotifica = (HashMap) request.getSession(true).getAttribute("FIEL");
            
            ICVETRAMITEINT = "" + hmNotifica.get("IIDCITA");
            iEjercicio = ""+hmNotifica.get("IEJERCICIO");
            iNumSolicitud = ""+hmNotifica.get("INUMSOLICITUD");
            
            
            //iNumSolicitud = "" + hmNotifica.get("iNumSolicitud");
            
            iCveDocDigAnt  = "" + hmNotifica.get("ICVEDOCDIG");
            
            int index = 0;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("COBSERVACION"))
                        COBSERVACION = item.getString();
                }
            }
            

            String lSQL = "INSERT INTO INTTRAMITEDOCS (iEjercicio,iNumSolicitud,ICVEDOCDIG,TSREGISTRO,CDOCUMENTO,CNOMARCHIVO,"
                    + "CCAMPO,ICVEUSUNOTIFICA,TSREGNOTIFICA,CTIPO,COBSERVACION,ICVEESTATUS) values "
                    + "(?,?,?,current timestamp,?,?,?,?,current timestamp,?,?,?)";

           
            iter = items.iterator();
            
            String prefijoFile = "fileButonADV";

            while (iter.hasNext()) {
            	 Vector vcSecDoc = pais.findByCustom("","SELECT max(GRLDOCFOLIOCM.IIDGESTORDOCUMENTO)as IIDGESTORDOCUMENTO FROM GRLDOCFOLIOCM where iejercicio= year(current date)");
                 TVDinRep vSecDoc = (TVDinRep) vcSecDoc.get(0);
                 int iICVEVEHDOCDIG = vSecDoc.getInt("IIDGESTORDOCUMENTO") + 1 ;
            	 TVDinRep datos= new TVDinRep();
                 
                FileItem item = (FileItem) iter.next();
                int iEtapa = Integer.parseInt(ICVEESTATUS);
                String cObsTablero=COBSERVACION;
                
                if (!item.isFormField()){
                    nombre = item.getName();
                    index = nombre.lastIndexOf('\\');
                    index = index + 1;
                    nombre = nombre.substring(index);
                    if (nombre.length() > 0) {
                        CNOMARCHIVO = nombre;
                        
                        String[] values = { 
                        		VParametros.getPropEspecifica("usrCM").toString(),
                        		VParametros.getPropEspecifica("pwdCM").toString(),
                        		"ADV",  
                        		"application/octet-stream", 
                        		("" + iICVEVEHDOCDIG) };
                        try {
    						if(cmImport.connect(keys, values, item.get()).compareTo("0") == 0){
    							TDGRLDocFolioCM docFolio = new TDGRLDocFolioCM();
    							TVDinRep vDoc = new TVDinRep();
    							vDoc.put("cTablaCM","ADV");
    							vDoc.put("iEjercicio", iEjercicio);
    							docFolio.insert(vDoc, null);
    							
    							datos.put("ICVEDOCDIG",iICVEVEHDOCDIG);
    							datos.put("tienePNC",1);
    					        datos.put("IEJERCICIO",Integer.parseInt(iEjercicio));
    					        datos.put("iNumSolicitud",Integer.parseInt(iNumSolicitud));
    					        datos.put("CDOCUMENTO", "docContestaNotifInt");
    					        datos.put("CNOMARCHIVO", CNOMARCHIVO);
    					        datos.put("ICVEESTATUS", iEtapa);
    					        datos.put("ICVEREQUISITO",item.getFieldName().substring(prefijoFile.length()));
    					        datos.put("COBSERVACION","Respuesta a la Notificaci髇: " + iCveDocDigAnt + " - " + COBSERVACION);
    					        
    					        inSol.insertDoctoADV(datos, true, null);
    					            							
    						}
                        } catch (Exception ex) {
                           // bLoad = false;
                        }
                        
                    }
                }

        }
                
              
                if(bLoad == false)
                   conn.rollback();
                else
                   conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (lPStmt != null) {
                    lPStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                dbConn.closeConnection();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }

            response.setContentType("text/html");
            response.getWriter().println(
                            "<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + 
                            "CD/componentes.js\"></SCRIPT>\r\n" + 
                            "<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + 
                            "prop.js\"></SCRIPT>\r\n" + 
                            "<script language=\"JavaScript\" SRC=\""+ VParametros.getPropEspecifica("RutaFuncs") + 
                            "pgContestaNota.js\"></SCRIPT>\r\n" + 
                            "<script language=\"JavaScript\">fPagADV();</script>");
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

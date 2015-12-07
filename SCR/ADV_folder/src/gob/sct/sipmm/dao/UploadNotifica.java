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
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.util.TFechas;

import gob.sct.sipmm.cntmgr.*;
import gob.sct.sipmm.dao.ws.*;

public class UploadNotifica extends HttpServlet {
    private static final long serialVersionUID = -6391312215178099481L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        TParametro VParametros = new TParametro("44");
        String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
        TDTRARegEtapasXModTram etapas = new TDTRARegEtapasXModTram();
        TDGRLRegPNC regPNC = new TDGRLRegPNC();
        DbConnection dbConn = null;
        TFechas fecha = new TFechas(); 
        Connection conn = null;
        TDGRLPais pais = new TDGRLPais();
        PreparedStatement lPStmt = null;
        String[] keys = { "userid", "password", "entity", "mimeType",
                "lintiCveDocumen" };
        CM_Import cmImport = new CM_Import();
        boolean bLoad = true;
        

        try {

            // Generación de la Conexión
            dbConn = new DbConnection(dataSourceName);
            conn = dbConn.getConnection();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(2);

            // Generación de la configuración
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(10 * 1024 * 1024);
            factory.setRepository(new File("."));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(10 * 1024 * 1024);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            String cTipoDsc="",iCveTramiteCIS="",nombre = "", iIdCita="",iEjercicio = "", iNumSolicitud = "", ICVEUSUNOTIFICA = "", ICVEESTATUS = "", COBSERVACION = "", CNOMARCHIVO = "",iCveUsuario="";
            int index = 0;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("iEjercicio"))
                        iEjercicio = item.getString();
                    if (item.getFieldName().equals("iNumSolicitud"))
                        iNumSolicitud = item.getString();
                    if (item.getFieldName().equals("IID"))
                        ICVEUSUNOTIFICA = item.getString();
                    if (item.getFieldName().equals("CTIPO"))
                        ICVEESTATUS = item.getString();
                    if (item.getFieldName().equals("CTIPODSC"))
                        cTipoDsc = item.getString();
                    if (item.getFieldName().equals("COBSERVACION"))
                        COBSERVACION = item.getString();
                    if (item.getFieldName().equals("iCveUsuario"))
                        iCveUsuario = item.getString();
                }
            }

            String lSQL = "INSERT INTO INTTRAMITEDOCS (iEjercicio,iNumSolicitud,ICVEDOCDIG,TSREGISTRO,CDOCUMENTO,CNOMARCHIVO," + 
                          "CCAMPO,ICVEUSUNOTIFICA,TSREGNOTIFICA,CTIPO,COBSERVACION,ICVEESTATUS) values " + 
                          "(?,?,?,current timestamp,?,?,?,?,current timestamp,?,?,?)";

            //int iICVEVEHDOCDIG = TablaSecuencia.getSecuencia(conn, "GRLDOCFOLIOCM");
            
            Vector vcSecDoc = pais.findByCustom("","SELECT max(GRLDOCFOLIOCM.IIDGESTORDOCUMENTO)as IIDGESTORDOCUMENTO FROM GRLDOCFOLIOCM where iejercicio= year(current date)");
            TVDinRep vSecDoc = (TVDinRep) vcSecDoc.get(0);
            int iICVEVEHDOCDIG = vSecDoc.getInt("IIDGESTORDOCUMENTO") + 1 ;
            
            iter = items.iterator();	

            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    nombre = item.getName();
                    index = nombre.lastIndexOf('\\');
                    index = index + 1;
                    nombre = nombre.substring(index);
                    if (nombre.length() > 0) {
                        CNOMARCHIVO = nombre;
                        String[] values = {VParametros.getPropEspecifica("usrCM").toString(),
                        		           VParametros.getPropEspecifica("pwdCM").toString(), 
                        		           "ADV", 
                        		           "application/octet-stream", 
                        		           ("" + iICVEVEHDOCDIG) };
                        try {
                            ;
    						
    						if((cmImport.connect(keys, values, item.get()).compareTo("0")) == 0){
    							TDGRLDocFolioCM docFolio = new TDGRLDocFolioCM();
    							TVDinRep vDoc = new TVDinRep();
    							vDoc.put("cTablaCM","ADV");
    							vDoc.put("iEjercicio", iEjercicio);
    							docFolio.insert(vDoc, null);
    						}
                        } catch (Exception ex) {
                            bLoad = false;
                        }                        
                    }
                }
            }
            Vector vcSol = pais.findByCustom("","SELECT S.iIdCita,CT.ICVETRAMITECIS FROM TRAREGSOLICITUD S " +
            		                            "JOIN TRACONFIGURATRAMITE CT ON " +
            		                            "CT.ICVETRAMITE=S.ICVETRAMITE " +
            		                            "AND CT.ICVEMODALIDAD=S.ICVEMODALIDAD " +
            		                            "where iejercicio= "+iEjercicio+
            		                            " AND iNumSolicitud = "+iNumSolicitud);
            if(vcSol.size()>0){
            	TVDinRep vSol = (TVDinRep) vcSol.get(0);
            	iIdCita = vSol.getString("iIdCita");
            	iCveTramiteCIS = vSol.getString("ICVETRAMITECIS");
            }

            if (bLoad == true) {
                int iEtapa = Integer.parseInt(ICVEESTATUS);
                String cObsTablero=COBSERVACION;

                if(!CNOMARCHIVO.equals("")) //Existe documento
                    cObsTablero = "fGetDoc(\""+VParametros.getPropEspecifica("URL_BaseSistema")+"getDocTab?ID=" + iICVEVEHDOCDIG + "&IC=" + ICVEUSUNOTIFICA + "\",\""+VParametros.getPropEspecifica("URL_BaseSistema")+"pgContestaNota.jsp?ID=" + iICVEVEHDOCDIG + "&IC=" + ICVEUSUNOTIFICA + "&hdBoton=Contesta\");" + COBSERVACION;
                else
                    cObsTablero = "fGetDoc(\"\",\""+VParametros.getPropEspecifica("URL_BaseSistema")+"pgContestaNota.jsp?ID=" + iICVEVEHDOCDIG + "&IC=" + ICVEUSUNOTIFICA + "&hdBoton=Contesta\");" + COBSERVACION;
                    
                
                lPStmt = conn.prepareStatement(lSQL);
                lPStmt.setInt(1, Integer.parseInt(iEjercicio));
                lPStmt.setInt(2, Integer.parseInt(iNumSolicitud));
                lPStmt.setInt(3, iICVEVEHDOCDIG);
                lPStmt.setString(4, "");
                lPStmt.setString(5, CNOMARCHIVO);
                lPStmt.setString(6, "");
                lPStmt.setInt(7, Integer.parseInt(ICVEUSUNOTIFICA));
                lPStmt.setString(8, "");
                lPStmt.setString(9, COBSERVACION);
                lPStmt.setInt(10, iEtapa);
                lPStmt.executeUpdate();
                                
                TDCis dCis = new TDCis();
                
                String cEtapas = "SELECT s.IEJERCICIO,s.INUMSOLICITUD,s.ICVETRAMITE,s.ICVEMODALIDAD,20 as iCveEtapa,re.ICVEOFICINA,re.ICVEDEPARTAMENTO " +
                                 "FROM TRAREGSOLICITUD s " +
                                 "join TRAREGETAPASXMODTRAM re on re.IEJERCICIO=s.IEJERCICIO and re.INUMSOLICITUD=s.INUMSOLICITUD and ICVEETAPA=2 " +
                                 "where s.iejercicio="+iEjercicio+" and s.INUMSOLICITUD="+iNumSolicitud;
                
                TDTRARegReqXTram tram = new TDTRARegReqXTram();
                
                
                
                /**
                 * Se actualiza la fecha de notificacion de la solicitud en este punto, 
                 * en realidad esta operacion debe de ser en el momento en que el usuario 
                 * ve el tablero o el dia 15 o 30 posterior a la publicación. 
                 */
                TVDinRep vNotif = new TVDinRep();
                vNotif.put("iEjercicio", iEjercicio);
                vNotif.put("iNumSolicitud", iNumSolicitud);
                vNotif.put("cRecibeNotif", "");
                vNotif.put("dtNotificacion", fecha.getDateSQL(fecha.getThisTime()));
                regPNC.updateRecibe(vNotif, conn);
                
                Vector vcEtapa = pais.findByNessted("", cEtapas, conn);
                if(vcEtapa.size()>0){ 
                	TVDinRep vEtapa = (TVDinRep) vcEtapa.get(0);
                	vEtapa.put("iCveUsuario",iCveUsuario);
                	etapas.cambiarEtapa(vEtapa, false, "Cambio de etapa de Notificacion", false, conn);
                }
                bLoad = dCis.insertaEstadoTramite(Integer.parseInt(iIdCita),
                		Integer.parseInt(iCveTramiteCIS),
                        1,
                        iEtapa,
                        cObsTablero, 
                        Integer.parseInt("93"));                 
                
                if(bLoad == false)
                   conn.rollback();
                else
                   conn.commit();
            }
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
                            "<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs")
                                    + "CD/componentes.js\"></SCRIPT>\r\n"
                                    + "<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs")
                                    + "prop.js\"></SCRIPT>\r\n"
                                    + "<script language=\"JavaScript\" SRC=\""+ VParametros.getPropEspecifica("RutaFuncs")
                                    + "pgNotificaciones.js\"></SCRIPT>\r\n"
                                    + "<script language=\"JavaScript\">fPagADV();</script>");
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

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
		TDINTSolicitud dSol = new TDINTSolicitud();
		
		DbConnection dbConnFiles = null;
		Connection connFiles = null;
		
		DbConnection dbConnCMFolio = null;
		Connection connCMFolio = null;
		
		int maxSizeFileInt = Integer.parseInt(VParametros.getPropEspecifica("maxSizeFile"));
		long maxSizeFileLong = Long.parseLong(VParametros.getPropEspecifica("maxSizeFile"));
		
		PreparedStatement lPStmt = null, lpsfirma = null;

		String[] keys = { "userid", "password", "entity", "mimeType",
				"lintiCveDocumen" };
		CM_Import cmImport = new CM_Import();
		TFechas Fecha = new TFechas();
		int year = Fecha.getIntYear(Fecha.TodaySQL());
		String nombre = "",COBSERVACION = "";
		String iNumSolicitud = "";
		
		String cTipoDsc="",iCveTramiteCIS="",iIdCita="",iEjercicio = "", ICVEUSUNOTIFICA = "", ICVEESTATUS = "", iCveUsuario="";
		boolean bLoad=true; 
		
        try {

        	// Generaci贸n de la Conexi贸n //esta conenxion guarda los archivos asosciados a los requisitos si algo falla se hace rollback y ningun documento es asociado a los requisitos
			dbConnFiles = new DbConnection(dataSourceName);
			connFiles = dbConnFiles.getConnection();
			connFiles.setAutoCommit(false);
			connFiles.setTransactionIsolation(2);
			
			//esta conexion genera los ids que se asignaran a los documentos para el content manager para poder hacer commit y obtener el ultimo ID evitando lecturas sucias
			dbConnCMFolio = new DbConnection(dataSourceName);
			connCMFolio= dbConnCMFolio.getConnection();
			connCMFolio.setAutoCommit(false);
			connCMFolio.setTransactionIsolation(2);

			// Generaci贸n de la configuraci贸n
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxSizeFileInt * 1024 * 1024);//limite de memoria 50m
			factory.setRepository(new File("."));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(factory.getSizeThreshold());//8 megas por el conjunto
			
			List items = upload.parseRequest(request);
			Iterator iterValid = items.iterator();
			Iterator iter = items.iterator();
			
			int index = 0;

			iter = items.iterator();

			String prefijoFile = "fileButonADV";
			
			//validacion de tama駉 en conjunto y tama駉 por archivo
			
			while (iterValid.hasNext()) {
				FileItem item = (FileItem) iterValid.next();
				
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
                }else if(!item.isFormField()&&item.getSize()>maxSizeFileLong* 1024 * 1024){
					nombre = item.getName();
					index = nombre.lastIndexOf('\\');
					index = index + 1;
					nombre = nombre.substring(index);
					loadPag(VParametros, response, "El tama駉 del archivo \""+nombre+"\" es mayor a "+maxSizeFileInt+"mb. Revise el documento e intente nuevamente.");
					return;					
				}
			}
			
			
			while (iter.hasNext()) {			
				FileItem item = (FileItem) iter.next();

				if (!item.isFormField()) {
									
					nombre = item.getName();
					index = nombre.lastIndexOf('\\');
					index = index + 1;
					nombre = nombre.substring(index);
					
					if (nombre.length() > 0) {
						
						String guid = UUID.randomUUID().toString();
						
						TDGRLDocFolioCM docFolio = new TDGRLDocFolioCM();
						TVDinRep vDoc = new TVDinRep();
						vDoc.put("cTablaCM", "ADV");
						vDoc.put("iEjercicio", year);
						vDoc.put("cGuid", guid);
						docFolio.insert(vDoc, connCMFolio);
						
						connCMFolio.commit();//hago commit para guardar el guid para que la consulta que sigue regrese el id generado
												
						Vector vc = dSol.findByCustom("","SELECT IIDGESTORDOCUMENTO FROM GRLDOCFOLIOCM WHERE CGUID = '"+guid+"'");
						TVDinRep vSecDoc = (TVDinRep) vc.get(0);
						Long IIDGESTORDOCUMENTO = vSecDoc.getLong("IIDGESTORDOCUMENTO");
												
							if(IIDGESTORDOCUMENTO!=null&&IIDGESTORDOCUMENTO>0){
								
								String[] values = {
										VParametros.getPropEspecifica("usrCM").toString(),
										VParametros.getPropEspecifica("pwdCM").toString(), "ADV",
										"application/octet-stream",IIDGESTORDOCUMENTO.toString()
										};
								
								if (cmImport.connect(keys, values, item.get()).compareTo("0") == 0) {							
																		
									TVDinRep datos =  new TVDinRep();
									datos.put("ICVEDOCDIG", IIDGESTORDOCUMENTO);
									datos.put("IEJERCICIO", year);
									datos.put("iNumSolicitud",Integer.parseInt(iNumSolicitud));
									datos.put("CDOCUMENTO", "");
									datos.put("CNOMARCHIVO", nombre);
									datos.put("ICVEOFICIO", 0);
									datos.put("COBSERVACION", COBSERVACION);
										
									dSol.insertDoctoADV(datos, false, connFiles);
									
					                String updDoc = "UPDATE INTTRAMITEDOCS SET TSREGNOTIFICA=CURRENT_TIMESTAMP WHERE ICVEDOCDIG = "+IIDGESTORDOCUMENTO;
														
									int iEtapa = Integer.parseInt(ICVEESTATUS);
					                String cObsTablero=COBSERVACION;

					                if(!nombre.equals("")) //Existe documento
					                    cObsTablero = "fGetDoc(\""+VParametros.getPropEspecifica("URL_BaseSistema")+"getDocTab?ID=" + IIDGESTORDOCUMENTO + "&IC=" + ICVEUSUNOTIFICA + "\",\""+VParametros.getPropEspecifica("URL_BaseSistema")+"pgContestaNota.jsp?ID=" + IIDGESTORDOCUMENTO + "&IC=" + ICVEUSUNOTIFICA + "&hdBoton=Contesta\");" + COBSERVACION;
					                else
					                    cObsTablero = "fGetDoc(\"\",\""+VParametros.getPropEspecifica("URL_BaseSistema")+"pgContestaNota.jsp?ID=" + IIDGESTORDOCUMENTO + "&IC=" + ICVEUSUNOTIFICA + "&hdBoton=Contesta\");" + COBSERVACION;
					                
					                TDCis dCis = new TDCis();
					                
					                String cEtapas = "SELECT s.IEJERCICIO,s.INUMSOLICITUD,s.ICVETRAMITE,s.ICVEMODALIDAD,20 as iCveEtapa,re.ICVEOFICINA,re.ICVEDEPARTAMENTO " +
					                                 "FROM TRAREGSOLICITUD s " +
					                                 "join TRAREGETAPASXMODTRAM re on re.IEJERCICIO=s.IEJERCICIO and re.INUMSOLICITUD=s.INUMSOLICITUD and ICVEETAPA=2 " +
					                                 "where s.iejercicio="+iEjercicio+" and s.INUMSOLICITUD="+iNumSolicitud;
					                
					                /**
					                 * Se actualiza la fecha de notificacion de la solicitud en este punto, 
					                 * en realidad esta operacion debe de ser en el momento en que el usuario 
					                 * ve el tablero o el dia 15 o 30 posterior a la publicaci贸n. 
					                 */
					                TDGRLRegPNC regPNC = new TDGRLRegPNC();
					                TFechas fecha = new TFechas(); 
					                TDGRLPais pais = new TDGRLPais();
					                TDTRARegEtapasXModTram etapas = new TDTRARegEtapasXModTram();

					                TVDinRep vNotif = new TVDinRep();
					                vNotif.put("iEjercicio", iEjercicio);
					                vNotif.put("iNumSolicitud", iNumSolicitud);
					                vNotif.put("cRecibeNotif", "");
					                vNotif.put("dtNotificacion", fecha.getDateSQL(fecha.getThisTime()));
					                regPNC.updateRecibe(vNotif, connFiles); 
					                
					                Vector vcEtapa = pais.findByNessted("", cEtapas, connFiles);
					                if(vcEtapa.size()>0){ 
					                	TVDinRep vEtapa = (TVDinRep) vcEtapa.get(0);
					                	vEtapa.put("iCveUsuario",iCveUsuario);
					                	etapas.cambiarEtapa(vEtapa, false, "Cambio de etapa de Notificacion", false, connFiles);
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
					                
					                bLoad = dCis.insertaEstadoTramite(Integer.parseInt(iIdCita),
					                		Integer.parseInt(iCveTramiteCIS),
					                        1,
					                        iEtapa,
					                        cObsTablero, 
					                        Integer.parseInt("93")); 
									
					                
									
									//conn.commit();
								}else{
									System.out.println("el content regreso algo difernte de 0");
									throw new Exception("el content regreso algo difernte de 0");
								}
							}else{
								throw new Exception("error al obtener el ultimo id para el content manager");
							}
					}else{
						System.out.println("no se pudo obtener la informacion del requisito");
						throw new Exception("no se pudo obtener la informacion del requisito");
					}
				}
			}
			
            if(bLoad == false)
                connFiles.rollback();
             else
                connFiles.commit();
        	loadPag(VParametros, response, null);
		} catch (FileUploadBase.SizeLimitExceededException se) {
			se.printStackTrace();
			loadPag(VParametros, response, "El conjunto de archivos excede el limite de "+maxSizeFileInt+" Mb. Revise los archivos e intente nuevamente.");
		}
		catch (Exception e) {
			try {
				connFiles.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			loadPag(VParametros, response, "El servidor ha respondido de forma inesperada. Intente nuevamente.");
		}
		finally {
			try {
				if (lPStmt != null) {
					lPStmt.close();
				}
				if (lpsfirma != null) {
					lpsfirma.close();
				}
				if (connCMFolio != null) {
					connCMFolio.close();
				}
				dbConnCMFolio.closeConnection();
				
				if (connFiles!= null) {
					connFiles.close();
				}
				dbConnFiles.closeConnection();
				
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}
	}  
    
private void loadPag(TParametro VParametros, HttpServletResponse response, String cError){
		
		String pagRet="<script language=\"JavaScript\" SRC=\" " + VParametros.getPropEspecifica("RutaFuncs");
		pagRet+="pgNotificaciones.js\"></SCRIPT>\r\n";
		response.setContentType("text/html");
		
		String strWrite ="<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "prop.js\"></SCRIPT>\r\n" +
                "<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "CD/componentes.js\"></SCRIPT>\r\n" + 
                pagRet; 
		
		if(cError==null)
			strWrite+="<script language=\"JavaScript\">fPagADVError();</script>";
		else
			strWrite+="<script language=\"JavaScript\">fPagADVError('"+cError+"');</script>";
		
		try{	
			response.getWriter().println(strWrite);                         
		}catch(Exception e){
			e.printStackTrace();
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

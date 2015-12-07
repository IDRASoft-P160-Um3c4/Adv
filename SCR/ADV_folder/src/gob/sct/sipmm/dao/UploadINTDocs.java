package gob.sct.sipmm.dao;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import sun.misc.BASE64Encoder;

import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;


import gob.sct.sipmm.cntmgr.*;

public class UploadINTDocs extends HttpServlet {
	private static final long serialVersionUID = -6391312215178099481L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TParametro VParametros = new TParametro("44");
		String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
		DbConnection dbConn = null;
		Connection conn = null;
        PreparedStatement lPStmt = null, lpsfirma = null;
        
		String[] keys = { "userid", "password", "entity", "mimeType","lintiCveDocumen" };
        String cArchivos = "";        
		CM_Import cmImport = new CM_Import();
		String lFiel = "";
		try {
			
			// Generaci�n de la Conexi�n 
			dbConn = new DbConnection(dataSourceName);
			conn = dbConn.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	        
			// Generaci�n de la configuraci�n
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(50 * 1024 * 1024);
			factory.setRepository(new File("."));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(15 * 1024 * 1024);
						
			List items = upload.parseRequest(request);
			
			// Procesamiento de los archivos de carga
			Iterator iter = items.iterator();
			TVDinRep vData = new TVDinRep();
			TVDinRep vDinRep = new TVDinRep();
			TDINTSolicitud dSol = new TDINTSolicitud();
			TDTRARegSolicitud dSolicitud = new TDTRARegSolicitud();
			int iOrden = 10000;
			int k =0;
			
			int index;
			String nombre = "",cConsecutivo="",cEjercicio="",cNumSolicitud="",CCADORIGEN = "", CCADFIRMA = "", CRFCFirma = "",lPtoMarina="",cFirmante="",cCveCertificado="";
			String [] aCampo;		
			
			TVDinRep vDataRepL= new TVDinRep();
			TVDinRep vDataSol= new TVDinRep();
			TFechas tFecha = new TFechas();
			int iEjercicio = tFecha.getIntYear(tFecha.getDateSQL(tFecha.getThisTime(false)));
			int iNumSol = 0;
			
			while (iter.hasNext()) {
	                FileItem item = (FileItem) iter.next();
	                if (item.isFormField()) {
	                      if(item.getFieldName().equals("LFGA")){
	                          lFiel = item.getString();
	                      }
                          if(item.getFieldName().equals("iNumSolicitud")) {                      
                             cNumSolicitud = item.getString();
                             vDinRep.put("iNumSolicitud",item.getString());
                          }
                          
                          if(item.getFieldName().equals("iEjercicio")) {                      
                              cEjercicio = item.getString();
                              vDinRep.put("iEjercicio",item.getString());
                           }
                          if(item.getFieldName().equals("ICONSECUTIVODIN")) {
                             cConsecutivo = item.getString();
                             vDinRep.put("ICONSECUTIVO",item.getString());
                          }
                          if(item.getFieldName().equals("iNumCita")) {   
                             cConsecutivo = item.getString();
                             vDinRep.put("iNumCita",item.getString());
                          }
                          if(item.getFieldName().equals("cRFCRep1")) {   
                              cConsecutivo = item.getString();
                              vDinRep.put("cRFCRep",item.getString());
                           }
                          if(item.getFieldName().equals("cCamSolDIN"))
                             vDinRep.put("cCamSol",item.getString());
                          
                          if(item.getFieldName().equals("CCADORIGEN")){
                              CCADORIGEN = item.getString();
                          }
                          if(item.getFieldName().equals("CCADFIRMA"))
                              CCADFIRMA = item.getString();
                          
                          if(item.getFieldName().equals("CRFCFirma"))
                              CRFCFirma = item.getString();
                          
                          if(item.getFieldName().equals("cFirmante"))
                              cFirmante = item.getString();

                          if(item.getFieldName().equals("cCveCertificado"))
                        	  cCveCertificado = item.getString();
                          
                          if(item.getFieldName().equals("iNumSolicitud")) {                      
                  			iNumSol=Integer.valueOf(item.getString()).intValue();
                              
                           }
                          
                          this.getDatosADV(item, vDataRepL, vDataSol);
	                }
			}
			
			///////ADV verificaicion de la existencia del solicitante
			
			
			
			String rfcRepl = vDataRepL.getString("hdcRFC_RepL");
			
			int cvePersona = dSolicitud.obtenSolicitanteByRFC(rfcRepl, null);
		
			String rfcR = vDataRepL.getString("hdcRFC_RepL");
			String rfcE = vDataSol.getString("hdcRFC_Sol");
			int cveR = dSolicitud.obtenSolicitanteByRFC(rfcR, null);
			int cveE = dSolicitud.obtenSolicitanteByRFC(rfcE, null);
		
			
			int cveRepL=0;
			int cveSol=0;
			int cveDomRepl=0;
			int cveDomSol=0;
			
			
			TDTRAFoliosAdv objSerializable = new TDTRAFoliosAdv();
			
			if(!(cvePersona>0)){ //si el solicitante(repL) no existe se registran y asocian las entidades correspondientes
				
				cveRepL = (objSerializable.insertPersona(1,vDataRepL)).getInt("ICVEPERSONA");//utiliza el objeto por la conexion serializable
				
				if(vDataSol.getString("hdcRFC_Sol").trim() != "")
					cveSol=(objSerializable.insertPersona(2, vDataSol)).getInt("ICVEPERSONA");
				
				if ( cveRepL > 0 && cveSol > 0){

					String upReplEmp = "INSERT INTO GRLREPLEGAL (ICVEEMPRESA,ICVEPERSONA,DTASIGNACION,LPRINCIPAL) VALUES ("+cveSol+","+cveRepL+",current_date,0)";
					
					objSerializable.executeQuery(upReplEmp);
					
					Vector vDomR=objSerializable.findByCustom("", "SELECT ICVEDOMICILIO FROM GRLDOMICILIO WHERE ICVEPERSONA="+cveRepL);

					if(vDomR.size()>0){
						cveDomRepl=((TVDinRep)vDomR.get(0)).getInt("ICVEDOMICILIO");
					}
					
					Vector vDomS=objSerializable.findByCustom("", "SELECT ICVEDOMICILIO FROM GRLDOMICILIO WHERE ICVEPERSONA="+cveSol);

					if(vDomS.size()>0){
						cveDomSol=((TVDinRep)vDomS.get(0)).getInt("ICVEDOMICILIO");
					}
 
					
					String upSolReplEmp = "UPDATE TRAREGSOLICITUD SET ICVESOLICITANTE="+ cveSol +", ICVEDOMICILIOSOLICITANTE="+cveDomSol+", ICVEREPLEGAL="+cveRepL+", ICVEDOMICILIOREPLEGAL="+cveDomRepl+" WHERE IEJERCICIO="+ iEjercicio +" AND INUMSOLICITUD="+iNumSol;
					objSerializable.executeQuery(upSolReplEmp);
					
				}else if ( cveRepL > 0 && !(cveSol > 0)){
					
					Vector vDomR=objSerializable.findByCustom("", "SELECT ICVEDOMICILIO FROM GRLDOMICILIO WHERE ICVEPERSONA="+cveRepL);

					if(vDomR.size()>0){
						cveDomRepl=((TVDinRep)vDomR.get(0)).getInt("ICVEDOMICILIO");
					}
					
					String upSolRepl = "UPDATE TRAREGSOLICITUD SET ICVESOLICITANTE="+ cveRepL +", ICVEDOMICILIOSOLICITANTE="+cveDomRepl+" WHERE IEJERCICIO="+ iEjercicio +" AND INUMSOLICITUD="+iNumSol;

					objSerializable.executeQuery(upSolRepl);
				}
			}else{
					
				if(cveR > 0 && cveE>0){
					
					Vector vDomR=objSerializable.findByCustom("", "SELECT ICVEDOMICILIO FROM GRLDOMICILIO WHERE ICVEPERSONA="+cveR);

					if(vDomR.size()>0){
						cveDomRepl=((TVDinRep)vDomR.get(0)).getInt("ICVEDOMICILIO");
					}
					
					Vector vDomS=objSerializable.findByCustom("", "SELECT ICVEDOMICILIO FROM GRLDOMICILIO WHERE ICVEPERSONA="+cveE);

					if(vDomS.size()>0){
						cveDomSol=((TVDinRep)vDomS.get(0)).getInt("ICVEDOMICILIO");
					}

					
					String upSolReEm = "UPDATE TRAREGSOLICITUD SET ICVESOLICITANTE="+ cveE +", ICVEDOMICILIOSOLICITANTE="+cveDomSol+", ICVEREPLEGAL="+cveR+", ICVEDOMICILIOREPLEGAL="+cveDomRepl+" WHERE IEJERCICIO="+ iEjercicio +" AND INUMSOLICITUD="+iNumSol;
					objSerializable.executeQuery(upSolReEm);
				}
				else if(cveR>0 && !(cveE > 0)){
					
					Vector vDomR=objSerializable.findByCustom("", "SELECT ICVEDOMICILIO FROM GRLDOMICILIO WHERE ICVEPERSONA="+cveR);

					if(vDomR.size()>0){
						cveDomRepl=((TVDinRep)vDomR.get(0)).getInt("ICVEDOMICILIO");
					}
					
					String upSolRep = "UPDATE TRAREGSOLICITUD SET ICVESOLICITANTE="+ cveR +", ICVEDOMICILIOSOLICITANTE="+cveDomRepl+" WHERE IEJERCICIO="+ iEjercicio +" AND INUMSOLICITUD="+iNumSol;
					objSerializable.executeQuery(upSolRep);
				}
				
			}
			
			///////ADV
		
			if(lFiel.equals("1")){
				
		        vDinRep = dSol.insert(vDinRep, null);
				
		        String lSQL = "update INTTRAMITES set lFIEL = 1 where iEjercicio = " + cEjercicio + " AND iNumSolicitud = "+cNumSolicitud;

		        
	            lPStmt = conn.prepareStatement(lSQL);
	            lPStmt.executeUpdate();	            
	            
	            String prefij= "cReq";
		        
				iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {				   
					        
	                    if(item.getFieldName().length() > 10 && item.getFieldName().substring(0,10).equals("CDOCUMENTO"))
					        vData.put("CDOCUMENTO", item.getString());
	                    
	                    if(item.getFieldName().length() > 6 && item.getFieldName().substring(0,6).equals("CCAMPO")){
					        vData.put("CCAMPO", item.getString());
					        vData.put("ICVEREQUISITO", item.getString().substring(prefij.length()).trim());
	                    }
	
					} else {
					    
						k++;
											
						vData.put("iEjercicio",cEjercicio);
						vData.put("iNumSolicitud",cNumSolicitud);
						
						nombre = item.getName();
						index = nombre.lastIndexOf('\\');
						index = index+1;
						nombre = nombre.substring(index);
						
						if(nombre.length()>0){					    
							vData.put("CNOMARCHIVO", nombre);
							cArchivos += "," + nombre;
												
							
							/***
							 * Inserta en la tabla INTTRAMITEDOCS tomando el maximo registro de documentos + 1
							 * 
							 */
		
							dSol.insertDocto(vData, null);				
							
							vData.put("ICVETRAMITE", vData.getInt("ICVETRAMITEINT"));
							
							vData.put("IORDEN", iOrden++);
		                    vData.put("cValor1", vData.getString("CDOCUMENTO"));
		                    vData.put("cValor2", "DOC-"+vData.getString("ICVEDOCDIG"));
		                    vData.put("ICONSECUTIVO",vData.getInt("ICVECONSECUTIVO"));                                   
		                    
							dSol.insertOneRecord(vData, conn);								
		
		                    TParametro  vParametros = new TParametro("44");		
		                    String cUsrCM = vParametros.getPropEspecifica("usrCM").toString();
		                    String cPwdCM = vParametros.getPropEspecifica("pwdCM").toString();
		                    String cEntidadCM = vParametros.getPropEspecifica("entidadCM").toString();
							String[] values = { cUsrCM, cPwdCM, cEntidadCM,"application/octet-stream",vData.getString("ICVEDOCDIG") };
							
							vData = new TVDinRep();
							boolean lCarga = false;
							 
							CM_Import cmImport1 = new CM_Import();
							byte[] archivo = (byte[]) vDinRep.get("bDocAlmacenado");
							if(cmImport1.connect(keys,values,item.get()).compareTo("0") == 0)
			                    lCarga = true;
			                 else
			                    lCarga = false;
							
							
							/***
							 * En caso de que el archivose guarde correctamente...
							 * 		Modifica el contador de archivos
							 * 		Modifica la fecha de compromiso de la solicitud
							 */
							if(lCarga==true){
								TDGRLDocFolioCM docFolio = new TDGRLDocFolioCM();
								TVDinRep vDoc = new TVDinRep();
								vDoc.put("cTablaCM","ADV");
								vDoc.put("iEjercicio", cEjercicio);
								docFolio.insert(vDoc, null);
								TDTRARegSolicitud sol = new TDTRARegSolicitud();
								sol.updateFechaCompromiso(Integer.parseInt(cEjercicio), Integer.parseInt(cNumSolicitud), conn);
							}
						
						}
						
					}
				}			
				
				dSolicitud.updateFechaCompromiso(vData.getInt("iEjercicio"), vData.getInt("iNumSolicitud"), conn);
		        lSQL = "INSERT INTO INTFIRMADO (ICVETRAMITE,ICONSECUTIVO,TSREGISTRO,CRFC,CCADORIGEN,CCADFIRMA,cFirmante,iCveCertificado) VALUES (?,?,{FN NOW()},?,?,?,?,?)";
	             lpsfirma = conn.prepareStatement(lSQL);
	             
	             if(CCADORIGEN.length() > 4999) CCADORIGEN = CCADORIGEN.substring(0,4999);
	             

                 String [] aFirma = CCADFIRMA.split(",");
                 byte [] aRestFirma = new byte[aFirma.length];
                 for (int j=0;j<aFirma.length;j++) {
                     aRestFirma[j] = (byte) Integer.parseInt(aFirma[j]);
                 }
                 BASE64Encoder b64 = new BASE64Encoder();              
                 CCADFIRMA =  b64.encode(aRestFirma);

	             
	             lpsfirma.setInt(1,  vDinRep.getInt("iNumCita"));
	             lpsfirma.setInt(2, 1);
	             lpsfirma.setString(3, CRFCFirma);
	             lpsfirma.setString(4, CCADORIGEN);
	             lpsfirma.setString(5, CCADFIRMA);
	             lpsfirma.setString(6, cFirmante);
	             lpsfirma.setString(7, cCveCertificado);
	             
	             //System.out.print("++++++++++++++++++before lpsfirma.executeUpdate()");
	             lpsfirma.executeUpdate();  
	             //System.out.print("++++++++++++++++++after lpsfirma.executeUpdate()");
				
				
				if(cArchivos.equals("") == false)
				    cArchivos = cArchivos.substring(1);
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
                if (lpsfirma != null) {
                    lpsfirma.close();
                }
				if (conn != null) {
					conn.close();
				}
				dbConn.closeConnection();
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
			
			response.setContentType("text/html");
			response.getWriter().println("<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "prop.js\"></SCRIPT>\r\n" +
					                     "<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "CD/componentes.js\"></SCRIPT>\r\n" + 
			                             "<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "pgINTSol02FIEL.js\"></SCRIPT>\r\n" +
			                             "<script language=\"JavaScript\">fPag();</script>");
			                             
		}
	}
	
	protected void getDatosADV(FileItem item, TVDinRep repL, TVDinRep emp){
        
        if(item.getFieldName().equals("hdcRFC_RepL")) {                      
			repL.put("hdcRFC_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcNomRaz_RepL")) {                      
			repL.put("hdcNomRaz_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcApPat_RepL")) {                      
			repL.put("hdcApPat_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcApMat_RepL")) {                      
			repL.put("hdcApMat_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcMail_RepL")) {                      
			repL.put("hdcMail_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcTel_RepL")) {                      
			repL.put("hdcTel_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcCalle_RepL")) {                      
			repL.put("hdcCalle_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcNumExt_RepL")) {                      
			repL.put("hdcNumExt_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcNumInt_RepL")) {                      
			repL.put("hdcNumInt_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcCol_RepL")) {                      
			repL.put("hdcCol_RepL",item.getString());
         }
        if(item.getFieldName().equals("hdcCP_RepL")) {                      
			repL.put("hdcCP_RepL",item.getString());
         }
        if(item.getFieldName().equals("iCveEntidadFedA")) {                      
			repL.put("iCveEntidadFedA",item.getString());
         }
        if(item.getFieldName().equals("iCveMunicipioA")) {                      
			repL.put("iCveMunicipioA",item.getString());
         }

        
        
        if(item.getFieldName().equals("hdcRFC_Sol")) {                      
        	emp.put("hdcRFC_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcNomRaz_Sol")) {                      
        	emp.put("hdcNomRaz_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcApPat_Sol")) {                      
        	emp.put("hdcApPat_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcApMat_Sol")) {                      
        	emp.put("hdcApMat_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcMail_Sol")) {                      
        	emp.put("hdcMail_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcTel_Sol")) {                      
        	emp.put("hdcTel_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcCalle_Sol")) {                      
        	emp.put("hdcCalle_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcNumExt_Sol")) {                      
        	emp.put("hdcNumExt_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcNumInt_Sol")) {                      
        	emp.put("hdcNumInt_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcCol_Sol")) {                      
        	emp.put("hdcCol_Sol",item.getString());
         }
        if(item.getFieldName().equals("hdcCP_Sol")) {                      
        	emp.put("hdcCP_Sol",item.getString());
         }
        if(item.getFieldName().equals("iCveEntidadFedB")) {                      
        	emp.put("iCveEntidadFedB",item.getString());
         }
        if(item.getFieldName().equals("iCveMunicipioB")) {                      
        	emp.put("iCveMunicipioB",item.getString());
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

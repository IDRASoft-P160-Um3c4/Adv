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

public class UploadDocHistorico extends HttpServlet {

	private static final long serialVersionUID = -3107975626778500403L;

	String cPag = "";

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

		PreparedStatement lPStmt = null, lpsfirma = null;

		String[] keys = { "userid", "password", "entity", "mimeType",
				"lintiCveDocumen" };

		CM_Import cmImport = new CM_Import();
		TFechas Fecha = new TFechas();
		int year = Fecha.getIntYear(Fecha.TodaySQL());
		String nombre = "", COBSERVACION = "";
		String iNumSolicitud = "";
		iNumSolicitud = request.getParameter("paramA");
		cPag = request.getParameter("cPagRet");

		try {

			// Generación de la Conexión //esta conenxion guarda los archivos
			// asosciados a los requisitos si algo falla se hace rollback y
			// ningun documento es asociado a los requisitos
			dbConnFiles = new DbConnection(dataSourceName);
			connFiles = dbConnFiles.getConnection();
			connFiles.setAutoCommit(false);
			connFiles.setTransactionIsolation(2);

			// esta conexion genera los ids que se asignaran a los documentos
			// para el content manager para poder hacer commit y obtener el
			// ultimo ID evitando lecturas sucias
			dbConnCMFolio = new DbConnection(dataSourceName);
			connCMFolio = dbConnCMFolio.getConnection();
			connCMFolio.setAutoCommit(false);
			connCMFolio.setTransactionIsolation(2);

			// Generación de la configuración
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(50 * 1024 * 1024);
			factory.setRepository(new File("."));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(factory.getSizeThreshold());

			List items = upload.parseRequest(request);

			Iterator iterValid = items.iterator();
			Iterator iter = items.iterator();

			String iCveHistADV = "", iCveUsr = "", fName = "";

			iCveHistADV = request.getParameter("paramA");
			iCveUsr = request.getParameter("iCveUsr");

			String prefijoFile = "fileButonADV";

			int index = 0;

			// validacion de tama�o en conjunto y tama�o por archivo
			long fileMaxSize = 1024 * 1024 * 5L;// 5 mb

			while (iterValid.hasNext()) {
				FileItem item = (FileItem) iterValid.next();

				if (!item.isFormField() && item.getSize() > fileMaxSize) {
					nombre = item.getName();
					index = nombre.lastIndexOf('\\');
					index = index + 1;
					nombre = nombre.substring(index);
					loadPag(VParametros,
							response,
							"El tama�o del archivo \""
									+ nombre
									+ "\" es mayor a 5mb. Revise el documento e intente nuevamente.");
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

						connCMFolio.commit();// hago commit para guardar el guid
												// para que la consulta que
												// sigue regrese el id generado

						Vector vc = dSol.findByCustom("",
								"SELECT IIDGESTORDOCUMENTO FROM GRLDOCFOLIOCM WHERE CGUID = '"
										+ guid + "'");
						TVDinRep vSecDoc = (TVDinRep) vc.get(0);
						Long IIDGESTORDOCUMENTO = vSecDoc
								.getLong("IIDGESTORDOCUMENTO");

						if (IIDGESTORDOCUMENTO != null
								&& IIDGESTORDOCUMENTO > 0) {
							String[] values = {
									VParametros.getPropEspecifica("usrCM")
											.toString(),
									VParametros.getPropEspecifica("pwdCM")
											.toString(), "ADV",
									"application/octet-stream",
									IIDGESTORDOCUMENTO.toString() };

							if (cmImport.connect(keys, values, item.get())
									.compareTo("0") == 0) {
								TVDinRep datos = new TVDinRep();
								datos.put("ICVEDOCHIST", IIDGESTORDOCUMENTO);
								datos.put("ICVEUSUARIO", iCveUsr);
								datos.put("fName", item.getFieldName()
										.substring(prefijoFile.length()));
								datos.put("ICVEHISTADV", iCveHistADV);

								dSol.insertDoctoHistoricoADV(datos, connFiles);
							} else {
								System.out
										.println("el content regreso algo difernte de 0");
								throw new Exception(
										"el content regreso algo difernte de 0");
							}
						} else {
							throw new Exception(
									"error al obtener el ultimo id para el content manager");
						}
					} else {
						System.out
								.println("no se pudo obtener la informacion del requisito");
						throw new Exception(
								"no se pudo obtener la informacion de oficio");
					}
				}

			}
			connFiles.commit();
			loadPag(VParametros, response, null);
		} catch (FileUploadBase.SizeLimitExceededException se) {
			se.printStackTrace();
			loadPag(VParametros, response, "El conjunto de archivos excede el limite de 50 Mb. Revise los archivos e intente nuevamente.");
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
		
		if(cPag!=null && !cPag.equals(""))
			pagRet+=cPag +".js\"></SCRIPT>\r\n";

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
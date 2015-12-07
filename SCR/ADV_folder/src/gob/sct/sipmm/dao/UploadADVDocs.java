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

public class UploadADVDocs extends HttpServlet {

	private static final long serialVersionUID = -3107975626778500403L;

	String cPag="";
	boolean actualizaReqs = false;
	String tienePNC="";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TParametro VParametros = new TParametro("44");
		String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
		TDINTSolicitud dSol = new TDINTSolicitud();
		TDTRARegSolicitud solObj = new TDTRARegSolicitud();
		DbConnection dbConn = null;
		Connection conn = null;
		PreparedStatement lPStmt = null, lpsfirma = null;

		String[] keys = { "userid", "password", "entity", "mimeType",
				"lintiCveDocumen" };
		String cArchivos = "";
		CM_Import cmImport = new CM_Import();
		String lFiel = "";
		TFechas Fecha = new TFechas();
		int year = Fecha.getIntYear(Fecha.TodaySQL());

		try {

			// Generación de la Conexión
			dbConn = new DbConnection(dataSourceName);
			conn = dbConn.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(2);

			// Generación de la configuración
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(50 * 1024 * 1024);//50 megas por todo el formulario
			factory.setRepository(new File("."));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(15 * 1024 * 1024);//15 megas por archivo 
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();

			String nombre = "", ICVETRAMITEINT = "", ICVEUSUNOTIFICA = "0", ICVEESTATUS = "0", COBSERVACION = "", CNOMARCHIVO = "";
			String iNumSolicitud = "";



			iNumSolicitud = request.getParameter("paramA");
			cPag = request.getParameter("paramB");
			tienePNC = request.getParameter("paramC");
			
			int index = 0;

			iter = items.iterator();

			String prefijoFile = "fileButonADV";
			
			

			while (iter.hasNext()) {			
				FileItem item = (FileItem) iter.next();

				if (!item.isFormField()) {
					
					Vector vcSecDoc = dSol
							.findByCustom("",
									"SELECT max(GRLDOCFOLIOCM.IIDGESTORDOCUMENTO)as IIDGESTORDOCUMENTO FROM GRLDOCFOLIOCM where iejercicio= year(current date)");
					
					TVDinRep vSecDoc = (TVDinRep) vcSecDoc.get(0);
					
					int iICVEVEHDOCDIG = vSecDoc.getInt("IIDGESTORDOCUMENTO") + 1;

					TVDinRep datos = new TVDinRep();

					
					int iEtapa = Integer.parseInt(ICVEESTATUS);
					
					nombre = item.getName();
					index = nombre.lastIndexOf('\\');
					index = index + 1;
					nombre = nombre.substring(index);
					if (nombre.length() > 0) {

						String[] values = {
								VParametros.getPropEspecifica("usrCM")
										.toString(),
								VParametros.getPropEspecifica("pwdCM")
										.toString(), "ADV",
								"application/octet-stream",
								("" + iICVEVEHDOCDIG) };
						
						
							if (cmImport.connect(keys, values, item.get())
									.compareTo("0") == 0) {
								TDGRLDocFolioCM docFolio = new TDGRLDocFolioCM();
								TVDinRep vDoc = new TVDinRep();
								vDoc.put("cTablaCM", "ADV");
								vDoc.put("iEjercicio", year);
								docFolio.insert(vDoc, conn);
								
								conn.commit();
								
								datos.put("ICVEDOCDIG", iICVEVEHDOCDIG);
								datos.put("IEJERCICIO", year);
								datos.put("iNumSolicitud",Integer.parseInt(iNumSolicitud));
								datos.put("CDOCUMENTO", "");
								datos.put("CNOMARCHIVO", nombre);
								datos.put("ICVEREQUISITO", item.getFieldName()
										.substring(prefijoFile.length()));
								datos.put("COBSERVACION", COBSERVACION);
								
								if(tienePNC != null && tienePNC.equals("1"))
									datos.put("tienePNC", 1);

								dSol.insertDoctoADV(datos, true, conn);
								
								conn.commit();
							}
							
					}
				}
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
			
			
			String pagRet="";
			
			if(cPag!=null && !cPag.equals(""))
				pagRet="<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + cPag +".js\"></SCRIPT>\r\n";
			else
				pagRet="<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "pg111020016ADV.js\"></SCRIPT>\r\n";

			response.setContentType("text/html");
			response.getWriter().println("<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "prop.js\"></SCRIPT>\r\n" +
					                     "<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "CD/componentes.js\"></SCRIPT>\r\n" + 
			                             pagRet+
			                             "<script language=\"JavaScript\">fPag();</script>");

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

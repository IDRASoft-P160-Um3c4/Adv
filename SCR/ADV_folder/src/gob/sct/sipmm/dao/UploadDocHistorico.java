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

	String cPag="";
	
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
			factory.setSizeThreshold(10 * 1024 * 1024);
			factory.setRepository(new File("."));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(10 * 1024 * 1024);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();

			String iCveHistADV="",iCveUsr="", fName="";
					
			iCveHistADV = request.getParameter("paramA");
			fName = request.getParameter("fName");
			iCveUsr =request.getParameter("iCveUsr");

			while (iter.hasNext()) {			
				FileItem item = (FileItem) iter.next();

				if (!item.isFormField()) {
					
					Vector vcSecDoc = dSol
							.findByCustom("",
									"SELECT max(GRLDOCFOLIOCM.IIDGESTORDOCUMENTO)as IIDGESTORDOCUMENTO FROM GRLDOCFOLIOCM where iejercicio= year(current date)");
					
					TVDinRep vSecDoc = (TVDinRep) vcSecDoc.get(0);
					
					int iICVEVEHDOCDIG = vSecDoc.getInt("IIDGESTORDOCUMENTO") + 1;

					TVDinRep datos = new TVDinRep();

						String[] values = {
								VParametros.getPropEspecifica("usrCM")
										.toString(),
								VParametros.getPropEspecifica("pwdCM")
										.toString(), "ADV",
								"application/octet-stream",
								("" + iICVEVEHDOCDIG) };
						
						try {
							if (cmImport.connect(keys, values, item.get())
									.compareTo("0") == 0) {
								TDGRLDocFolioCM docFolio = new TDGRLDocFolioCM();
								TVDinRep vDoc = new TVDinRep();
								vDoc.put("cTablaCM", "ADV");
								vDoc.put("iEjercicio", year);
								docFolio.insert(vDoc, conn);
										
								datos.put("ICVEDOCHIST", iICVEVEHDOCDIG);
								datos.put("ICVEUSUARIO", iCveUsr);
								datos.put("fName", fName);
								datos.put("ICVEHISTADV", iCveHistADV);
								
								dSol.insertDoctoHistoricoADV(datos, conn);
							}	
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if(conn!=null)
							conn.commit();
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
			pagRet="<script language=\"JavaScript\" SRC=\"" + VParametros.getPropEspecifica("RutaFuncs") + "pgSubirDocHist.js\"></SCRIPT>\r\n";

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

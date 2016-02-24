package gob.sct.sipmm.dao;

import gob.sct.sipmm.cntmgr.CM_GetContent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.micper.seguridad.vo.TVDinRep;

public class DownloadDocHistorico extends HttpServlet {

	private static final long serialVersionUID = 1152221805525508141L;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	} 

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] keys = { "userid", "password", "entity", "maxResults","queryOP", "lintiCveDocumen" };
		String[] operators = { "", "", "", "", "", "=" };
		TDINTSolicitud dSol = new TDINTSolicitud();
		Vector vcNomDoc = new Vector();
		TVDinRep vNomDoc =  new TVDinRep();
		
		CM_GetContent cmImport = new CM_GetContent();
		int iCarga = 0;
		byte[] btArchivo = null;
		try {
			
			String icveDoc=request.getParameter("paramA");
			
			//String[] values = { "icmadmin", "lscmv82", "ADV", "1", "true", icveDoc};
			String[] values = { "icmadmin", "lscmv82", "ADV", "1", "true", icveDoc};
														
			btArchivo = cmImport.connect(keys, values, operators);
			
			vcNomDoc = dSol.findByCustom("","SELECT cnomarch FROM TRADOCHIST where ICVEDOCHIST="+icveDoc);
			vNomDoc = (TVDinRep) vcNomDoc.get(0);
			
		} catch (Exception e) {
			iCarga = 2;
			e.printStackTrace();
		} finally {
			
			if (iCarga == 0) {			
				
				response.setContentType("application/force-download");			
				response.setHeader("Content-Disposition","attachment; filename=\""+vNomDoc.getString("cnomarch")+"\"");
				OutputStream browser = response.getOutputStream();
				browser.write(btArchivo);
				browser.flush();
				browser.close();
			} else {
				response
						.getWriter()
						.println("<html><body><form name=\"frm1\"></form></body></html><script language=\"JavaScript\">");
				response.getWriter().println("top.opener.fCarga(false);top.window.close();");
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
package gob.sct.sipmm.dao;

import gob.sct.sipmm.cntmgr.CM_GetContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.micper.seguridad.vo.TVDinRep;
import com.micper.util.logging.TLogger;

public class DownloadFormatoSolicitud extends HttpServlet {

	private static final long serialVersionUID = 1152221805525508141L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nombreDoc = request.getParameter("nombreDoc");
		String path=getServletContext().getResource("/wwwrooting/descargar/"+nombreDoc).getPath();
		
		File f = new File(path);
		FileInputStream is = new FileInputStream(f);
		
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition",
				"attachment; filename=\""+nombreDoc+"\"");
						
		OutputStream browser = response.getOutputStream();
		byte[] bytes = org.apache.poi.util.IOUtils.toByteArray(is);
		browser.write(bytes);
		is.close();
		browser.flush();
		browser.close();
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

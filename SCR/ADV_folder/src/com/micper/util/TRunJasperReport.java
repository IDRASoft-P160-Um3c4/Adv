package com.micper.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 * Forma de llamar:
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;

import com.micper.sql.DbConnection;
import com.micper.ingsw.TParametro;

public class TRunJasperReport
    extends HttpServlet{
  /**
	 *
	 */
	private static final long serialVersionUID = -2033169780628647731L;
private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  protected Connection getConnection(){
    Connection conn = null;
    DbConnection dbConn = null;
    try{
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
    } catch(Exception e){
      e.printStackTrace();
    }
    return conn;
  }

protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    ServletOutputStream servletOutputStream = response.getOutputStream();
    String cReporte = "";
    InputStream reportStream = null;
    HashMap parameterMap = null;
    try{
      if(request != null && request.getSession() != null){
        if(request.getSession().getAttribute("cNombreReporte") != null)
          cReporte = request.getSession().getAttribute("cNombreReporte").toString();
        parameterMap = (HashMap) request.getSession().getAttribute("hashParametros");
      }
      if(parameterMap == null)
        parameterMap = new HashMap();
      parameterMap.put("URLEstaticos", (String)VParametros.getPropEspecifica("URLEstaticos")); //desarrollo
//      parameterMap.put("URLEstaticos", (String)VParametros.getPropEspecifica("URLEstaticosReportesJasper")); //produccion en app.sct.gob.mx
      parameterMap.put("SUBREPORT_DIR", (String)VParametros.getPropEspecifica("SUBREPORT_DIR"));
      parameterMap.put("URLVinculacion",(String)VParametros.getPropEspecifica("URLVinculacion"));
      
  	//estas lineas compilan el fuente .jrxml para generar el .jasper  
  	//JasperCompileManager.compileReportToFile(...ruta\\AcuseEntrega.jrxml",
  	//		"...destino\\AcuseEntrega.jasper");

      reportStream = getServletConfig().getServletContext().getResourceAsStream("/Reportes/" + cReporte + ".jasper");
      Connection conn = this.getConnection();

      if(conn != null){
    	  JasperRunManager.runReportToPdfStream(reportStream,servletOutputStream,parameterMap,conn);
        conn.close();
      }else{
        //System.out.print("Conn es nula");
      }
      request.getSession().removeAttribute("cNombreReporte");
      request.getSession().removeAttribute("hashParametros");
      response.setContentType("application/pdf");
      servletOutputStream.flush();
      servletOutputStream.close();
    } catch(Exception e){
    	request.getSession().removeAttribute("cNombreReporte");
        request.getSession().removeAttribute("hashParametros");
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      printWriter.println("Ocurrió un error al ejecutar el reporte: /Reportes/" + cReporte + ".jasper");
      printWriter.println("Parametros enviados: " + parameterMap.toString());
      printWriter.println("");
      e.printStackTrace(printWriter);
      response.setContentType("text/plain");
      response.getOutputStream().print(stringWriter.toString());
    }
  }
}

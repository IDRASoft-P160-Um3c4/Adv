package gob.sct.sipmm.dao;

import gob.sct.sipmm.cntmgr.CM_GetContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat.Encoding;

import com.micper.excepciones.DAOException;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.util.ModeloConsulta;
import com.micper.util.logging.TLogger;

import net.sf.jxls.transformer.XLSTransformer;

public class ConsultaExcel extends HttpServlet {

	private static final long serialVersionUID = 301616637565521054L;

	private TParametro VParametros = new TParametro("44");

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String filtro = request.getParameter("paramA");
		String orden = request.getParameter("paramB");

		String query = "SELECT " +
				         "       TRAREGSOLICITUD.IEJERCICIO, " + //0
				         "       TRAREGSOLICITUD.INUMSOLICITUD, " +//1
				         "       TRAREGSOLICITUD.ICVETRAMITE, " +//2
				         "       TRACATTRAMITE.CDSCTRAMITE, " +//3
				         "       TRACATTRAMITE.CDSCBREVE AS CDSCBREVETRAMITE, " +//4
				         "       TRAREGSOLICITUD.ICVEMODALIDAD, " +//5
				         "       TRAMODALIDAD.CDSCMODALIDAD, " +//6
				         "       TRAREGSOLICITUD.ICVESOLICITANTE, " +//7
				         "       GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO AS CNOMBRECOMPLETO, " +//8
				         "       GRLPERSONA.CRFC, " +//9
				         "       TRAREGSOLICITUD.TSREGISTRO, " +//10
				         "       TRAREGSOLICITUD.DTESTIMADAENTREGA, " +//11
				         "       TRAREGSOLICITUD.LPAGADO, " +//12
				         "       TRAREGSOLICITUD.DTENTREGA, " +//13
				         "       TRAREGSOLICITUD.LRESOLUCIONPOSITIVA, " + //14
				         "       TRAREGSOLICITUD.ICVEOFICINA, " +//15
				         "       GRLOFICINA.CDSCBREVE AS CDSCBREVEOFICINA, " +//16
				         "       TRAREGSOLICITUD.ICVEDEPARTAMENTO, " +//17
				         "       GRLDEPARTAMENTO.CDSCBREVE AS CDSCBREVEDEPTO, " +//18
				         "       TRAREGSOLICITUD.CDSCBIEN, " +//19
				         "       TREXMT.ICVEETAPA AS ICVEETA, " +//20
				         "       E.CDSCETAPA, " +//21
				         "       TRAREGSOLICITUD.DTENTREGA, " +//22
				         "       O1.CDSCBREVE as COFIRESUELVE, " +//23
				         "       D1.CDSCBREVE AS CDPTORESUELVE, " +//24
				         "       TRAREGSOLICITUD.CENVIARRESOLUCIONA, "+//25
				         " 		 (YEAR(current_date - date(TRAREGSOLICITUD.tsregistro))*365 + MONTH(current_date - date(TRAREGSOLICITUD.tsregistro))*30 + DAY(current_date - date(TRAREGSOLICITUD.tsregistro))) AS DIASTRANS, "+//26
				         "       TRAREGSOLICITUD.LABANDONADA, " +//27
				         "       TRAREGTRAMXSOL.DTCANCELACION, " +//28
				         "		 CAR.CDSCARRETERA, "+//29
				         "       TRAREGSOLICITUD.LTECNICO, " +//30
				         "       TRAREGSOLICITUD.LJURIDICO, " +//31
				        "		 'CAD' AS KM, "+//32
				        "		 'SEN' AS SEN, "+//33
				         "		 DAT.CHECHOS, " +//34
				         "		 CASE VT.LPOSITIVA "+
				         "	 	 WHEN 0 THEN 'NEGATIVO' " +
				         "       WHEN 1 THEN 'POSITIVO' " +
				         "		 ELSE 'PENDIENTE' END AS RESOLVT, " +//35
				         "		 TRAREGSOLICITUD.DTRESOLTRAM," +//36
				        		 "FOL.CFOLPERMISO AS COLF, "+//37
				                  "FOL.DTPERMISO AS COLG, "+//38
				 		 "      (SELECT MAX(ICONSECUTIVOPNC) FROM GRLREGISTROPNC where IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD AND LRESUELTO = 0) AS ICONSECUTIVOPNC, "+//39
				         "       DAT.DTVISITA, "+//40
				         "       DAT.CLATITUD, "+//41
				         "       DAT.CLONGITUD, "+//42
				         "      SUBSTR(DAT.CKMSENTIDO,1,20) as CKMSENTIDO, "+//43
						 " CASE WHEN FOL.DTPERMISO IS NOT NULL THEN (YEAR(FOL.DTPERMISO - date(TRAREGSOLICITUD.tsregistro))*365 + MONTH(FOL.DTPERMISO - date(TRAREGSOLICITUD.tsregistro))*30 + DAY(FOL.DTPERMISO - date(TRAREGSOLICITUD.tsregistro))) ELSE -1 END AS DIASPERM, "+//44"+
				         " CASE WHEN TRAREGTRAMXSOL.DTCANCELACION IS NOT NULL THEN (YEAR(TRAREGTRAMXSOL.DTCANCELACION - DATE(TRAREGSOLICITUD.TSREGISTRO))*365 + MONTH(TRAREGTRAMXSOL.DTCANCELACION - DATE(TRAREGSOLICITUD.TSREGISTRO))*30 + DAY(TRAREGTRAMXSOL.DTCANCELACION - DATE(TRAREGSOLICITUD.TSREGISTRO))) ELSE -1 END AS DIASCANCELA, "+ //45
				         " CASE WHEN TRAREGSOLICITUD.DTRESOLTRAM IS NOT NULL THEN (YEAR(TRAREGSOLICITUD.DTRESOLTRAM - DATE(TRAREGSOLICITUD.TSREGISTRO))*365 + MONTH(TRAREGSOLICITUD.DTRESOLTRAM - DATE(TRAREGSOLICITUD.TSREGISTRO))*30 + DAY(TRAREGSOLICITUD.DTRESOLTRAM - DATE(TRAREGSOLICITUD.TSREGISTRO))) ELSE -1 END AS DIASRESOL"+ //46
				         "	FROM TRAREGSOLICITUD " +
				         "  JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE " +
				         "  JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD " +
				         "  JOIN GRLPERSONA ON GRLPERSONA.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE " +
				         "  JOIN GRLOFICINA ON GRLOFICINA.ICVEOFICINA = TRAREGSOLICITUD.ICVEOFICINA " +
				         "  JOIN GRLDEPARTAMENTO ON GRLDEPARTAMENTO.ICVEDEPARTAMENTO = TRAREGSOLICITUD.ICVEDEPARTAMENTO " +
				         "  LEFT JOIN TRATRAMITEXOFIC TXO ON TXO.ICVEOFICINA=TRAREGSOLICITUD.ICVEOFICINA " +
				         "    AND TXO.ICVETRAMITE= TRAREGSOLICITUD.ICVETRAMITE " +
				         "  JOIN GRLOFICINA O1 ON O1.ICVEOFICINA = txo.ICVEOFICINARESUELVE " +
				         "  JOIN GRLDEPARTAMENTO D1 ON D1.ICVEDEPARTAMENTO = TXO.ICVEDEPTORESUELVE " +
				         "  LEFT JOIN TRAREGETAPASXMODTRAM TREXMT ON TRAREGSOLICITUD.IEJERCICIO = TREXMT.IEJERCICIO " +
				         "    AND TRAREGSOLICITUD.INUMSOLICITUD = TREXMT.INUMSOLICITUD" +
				         "  JOIN TRAETAPA E ON E.ICVEETAPA = TREXMT.ICVEETAPA" +
				         "  LEFT JOIN TRAREGTRAMXSOL ON TRAREGSOLICITUD.IEJERCICIO = TRAREGTRAMXSOL.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = TRAREGTRAMXSOL.INUMSOLICITUD "+
				         "  JOIN TRAREGDATOSADVXSOL DAT ON TRAREGSOLICITUD.IEJERCICIO = DAT.IEJERCICIO  AND TRAREGSOLICITUD.INUMSOLICITUD = DAT.INUMSOLICITUD "+
				         "  JOIN TRACATCARRETERA CAR ON DAT.ICVECARRETERA = CAR.ICVECARRETERA "+ 
				         "  LEFT JOIN TRAREGRESOLVTECXSOL VT ON TRAREGSOLICITUD.IEJERCICIO = VT.IEJERCICIO " +
				         "  AND TRAREGSOLICITUD.INUMSOLICITUD = VT.INUMSOLICITUD " +
				       	 "LEFT JOIN TRADATOSPERM FOL ON TRAREGSOLICITUD.IEJERCICIO=FOL.IEJERCICIO  "+
				   		      "  AND TRAREGSOLICITUD.INUMSOLICITUD=FOL.INUMSOLICITUD     "+
				         "where "+ filtro +
				         " AND (SELECT COUNT(TRAREGETAPASXMODTRAM.IORDEN) FROM TRAREGETAPASXMODTRAM WHERE " +
				         " TRAREGETAPASXMODTRAM.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND " +
				         " TRAREGETAPASXMODTRAM.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD AND " +
				         " TRAREGETAPASXMODTRAM.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE AND " +
				         " TRAREGETAPASXMODTRAM.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD) >= 1 " +
				         " AND TREXMT.iOrden = " +
				         " ( " +
				         "   SELECT " + 
				         "   MAX(EMT.iOrden) " +
				         "   FROM TRARegEtapasXModTram EMT " +
				         "   WHERE TRARegSolicitud.IEJERCICIO = EMT.IEJERCICIO " +
				         "   AND TRARegSolicitud.INUMSOLICITUD = EMT.INUMSOLICITUD " +
				         "   AND TRARegSolicitud.iCveTramite = EMT.iCveTramite " +
				         "   AND TRARegSolicitud.iCveModalidad = EMT.iCveModalidad " +
				         " ) order by " + orden;

		TDTRARegSolicitud objSol = new TDTRARegSolicitud();

		Vector vectorMatriz = null;
		try {
			vectorMatriz = objSol.findByCustom("iEjercicio,iNumSolicitud",
					query);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<TVDinRep> listaDinamicos = new ArrayList<TVDinRep>();

		for (int i = 0; i < vectorMatriz.size(); i++) {
			TVDinRep nuevo = new TVDinRep();
			nuevo = (TVDinRep) vectorMatriz.get(i);
			listaDinamicos.add(nuevo);
		}

		Map<String, List<ModeloConsulta>> beans = new HashMap<String, List<ModeloConsulta>>();

		List<ModeloConsulta> listaRegistros = new ArrayList<ModeloConsulta>();

		for (int j = 0; j < listaDinamicos.size(); j++) {
			ModeloConsulta nuevoReg = new ModeloConsulta(listaDinamicos.get(j));
			listaRegistros.add(nuevoReg);
		}

		beans.put("listaRegistros", listaRegistros);

		try {
			
			boolean realiza=false;
			
			String outPath = "";
			String tmplPath = "";
			
			UUID uuid = UUID.randomUUID();
			String fileNameGen = uuid.toString();
			
			String rutaNAS = VParametros.getPropEspecifica("rutaNAS");
			
			String nomDestNas=rutaNAS + fileNameGen + ".xlsx";

			outPath = nomDestNas;
			
			XLSTransformer transformer = new XLSTransformer();
			
			System.out.print("ruta del tamplate ----->"+getServletContext().getResource("/wwwrooting/plantilla/in.xlsx").getPath());

			try { // NAS
				
				tmplPath = rutaNAS +"in.xlsx";
				
				System.out.print("rutaTemplate--> " + tmplPath);
				System.out.print("rutaDestino--> " + outPath);

				transformer.transformXLS(tmplPath, beans, outPath);
				
				System.out.print("paso del transformXLS NAS ");

			} catch (Exception e) {
				realiza=true;
				System.out.print("\n--------ERROR USANDO LA NAAAAAAS--------\n");
				TLogger.log(this, TLogger.ERROR, "ERROR USANDO LA NAAAAAAS", e);
				System.out.print("errorNASS ->>" + e.getMessage());
			}
			
			
			if(realiza==true){
				try { // 
					
					tmplPath=getServletContext().getResource("/wwwrooting/plantilla/in.xlsx").getPath();
					
					System.out.print("rutaTemplate--> " + tmplPath);
					System.out.print("rutaDestino--> " + outPath);

					transformer.transformXLS(tmplPath, beans, outPath);
					
					System.out.print("paso del transformXLS TEMPLATE");
	
				} catch (Exception e) {
	
					System.out.print("\n--------ERROR USANDO EL PATHHH DEL TEMPLATE--------\n");
					TLogger.log(this, TLogger.ERROR, "ERROR USANDO EL PATHHH DEL TEMPLATE", e);
					System.out.print("errorTEMPLATE ->>" + e.getMessage());
				}
			}

			InputStream is = null;
			is = new FileInputStream(outPath);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition","attachment; filename=\"consultaSolicitudesADV.xlsx\"");

			byte[] bytes = org.apache.poi.util.IOUtils.toByteArray(is);

			OutputStream browser = response.getOutputStream();
			browser.write(bytes);
			browser.flush();
			browser.close();
			is.close();

		} catch (Exception e) {
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
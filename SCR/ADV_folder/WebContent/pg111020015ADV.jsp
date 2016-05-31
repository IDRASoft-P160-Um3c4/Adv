<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pg111020015A1.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon //mbeano && iCaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegSolicitudA1 dTRARegSolicitud = new TDTRARegSolicitudA1();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /*String cEtapas = vParametros.getPropEspecifica("EtapaConcluidoArea") + "," + vParametros.getPropEspecifica("EtapaEntregarVU") + "," +
  vParametros.getPropEspecifica("EtapaEntregarOficialia") + "," + vParametros.getPropEspecifica("EtapaRecibeResolucion") + "," +
  vParametros.getPropEspecifica("EtapaResEnviadaOficialia")+","+vParametros.getPropEspecifica("EtapaEntregaResol")+","+
  vParametros.getPropEspecifica("EtapaConclusionTramite");*/
  
  String cEtapas = vParametros.getPropEspecifica("EtapaEntregaPermiso") + "," +
		           vParametros.getPropEspecifica("EtapaTramiteCancelado");
  
  //TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal");
    try{
      vDinRep = dTRARegSolicitud.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal,iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma,lPrincipal");
    try{
      vDinRep = dTRARegSolicitud.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
    try{
       dTRARegSolicitud.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
 
 
 String strQuery = "";

  if(oAccion.getCAccion().equals("Historico")){
	  
		          strQuery = " (SELECT "+    
		                  "'1' AS COLA, "+
		                  "TRACATTRAMITE.CDSCBREVE AS COLB, "+
		                  "TRAMODALIDAD.CDSCMODALIDAD AS COLC, "+
		                  "TRAREGSOLICITUD.TSREGISTRO AS COLD, "+
		                  "TRAREGSOLICITUD.DTRECEPCION AS COLE, "+
		                  "FOL.CFOLPERMISO AS COLF, "+
		                  "FOL.DTPERMISO AS COLG, "+
		                  "TRAREGTRAMXSOL.DTCANCELACION AS COLH, "+
		                  "GRLMOTIVOCANCELA.CDSCMOTIVO AS COLI, "+
		                  "'' AS COLJ, "+ //DAT.CCEDIDO 
		                  "GRLOFICINA.CDSCBREVE AS COLK, "+
		            	  "GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO AS COLL, "+
		            	  "CAR.CDSCARRETERA AS COLM, "+
		                  "CAR.CCONSECIONARIO AS COLN, "+
		                  "SUBSTR(DAT.CKMSENTIDO,1,50)  AS COLO, "+
		                  "DAT.CHECHOS AS COLP, "+
		                  "'ST' AS COLQ,   "+
		                  "1 AS COLR, "+
		                  "TRAREGSOLICITUD.DTENTREGA AS COLS, "+
		                  "TRAREGSOLICITUD.LRESOLUCIONPOSITIVA AS COLT, "+
		                  "TRAREGSOLICITUD.DTENTREGA AS COLU, "+
		                  "TRAREGSOLICITUD.LABANDONADA AS COLV, "+
		                  "TRAREGSOLICITUD.DTRESOLTRAM AS COLW, "+
		            	  "TRAREGSOLICITUD.IEJERCICIO AS COLX, "+
		                  "TRAREGSOLICITUD.INUMSOLICITUD AS COLY "+
		      "FROM TRAREGSOLICITUD   "+
		      "JOIN TRACATTRAMITE ON TRACATTRAMITE.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE   "+
		      "JOIN TRAMODALIDAD ON TRAMODALIDAD.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD   "+
		      "JOIN GRLPERSONA ON GRLPERSONA.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE   "+
		      "JOIN GRLOFICINA ON GRLOFICINA.ICVEOFICINA = TRAREGSOLICITUD.ICVEOFICINA   "+
		      "LEFT JOIN TRAREGETAPASXMODTRAM TREXMT ON TRAREGSOLICITUD.IEJERCICIO = TREXMT.IEJERCICIO     "+
		      "  AND TRAREGSOLICITUD.INUMSOLICITUD = TREXMT.INUMSOLICITUD  "+
		      "JOIN TRAETAPA E ON E.ICVEETAPA = TREXMT.ICVEETAPA  "+
		      "LEFT JOIN TRAREGTRAMXSOL ON TRAREGSOLICITUD.IEJERCICIO = TRAREGTRAMXSOL.IEJERCICIO  "+
		      "  AND TRAREGSOLICITUD.INUMSOLICITUD = TRAREGTRAMXSOL.INUMSOLICITUD   "+
		      "left join GRLMOTIVOCANCELA ON GRLMOTIVOCANCELA.ICVEMOTIVOCANCELA = TRAREGTRAMXSOL.ICVEMOTIVOCANCELA   "+
		      "JOIN TRAREGDATOSADVXSOL DAT ON TRAREGSOLICITUD.IEJERCICIO = DAT.IEJERCICIO  "+
		      "  AND TRAREGSOLICITUD.INUMSOLICITUD = DAT.INUMSOLICITUD   "+
		      "JOIN TRACATCARRETERA CAR ON DAT.ICVECARRETERA = CAR.ICVECARRETERA   "+
		      "LEFT JOIN TRAREGRESOLVTECXSOL VT ON TRAREGSOLICITUD.IEJERCICIO = VT.IEJERCICIO   "+
		      "  AND TRAREGSOLICITUD.INUMSOLICITUD = VT.INUMSOLICITUD   "+
		     // "LEFT JOIN TRAFOLIOSADVXSOL FOL ON TRAREGSOLICITUD.IEJERCICIO=FOL.IEJERCICIO  "+
		       "LEFT JOIN TRADATOSPERM FOL ON TRAREGSOLICITUD.IEJERCICIO=FOL.IEJERCICIO  "+
		      "  AND TRAREGSOLICITUD.INUMSOLICITUD=FOL.INUMSOLICITUD     "+
		      "WHERE  TRAREGSOLICITUD.LIMPRESO IS NOT NULL  "+
		      "  AND (SELECT COUNT(TRAREGETAPASXMODTRAM.IORDEN) "+
		      "  		FROM TRAREGETAPASXMODTRAM "+
		      "    	WHERE  TRAREGETAPASXMODTRAM.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO "+
		      "      		AND  TRAREGETAPASXMODTRAM.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD "+
		      "      		AND  TRAREGETAPASXMODTRAM.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE "+
		      "      		AND  TRAREGETAPASXMODTRAM.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD) >= 1 "+ 
		      "  			AND TREXMT.IORDEN =  (SELECT MAX(EMT.IORDEN)    "+
		      "     							FROM TRAREGETAPASXMODTRAM EMT  "+  
		      "        					     WHERE TRAREGSOLICITUD.IEJERCICIO = EMT.IEJERCICIO    "+
		      "					               AND TRAREGSOLICITUD.INUMSOLICITUD = EMT.INUMSOLICITUD  "+  
		      "					               AND TRAREGSOLICITUD.ICVETRAMITE = EMT.ICVETRAMITE    "+
		      "					               AND TRAREGSOLICITUD.ICVEMODALIDAD = EMT.ICVEMODALIDAD)  "+ 
		      request.getParameter("cFiltroADV") +
		      
		      ") UNION "+
		          
		      "(SELECT "+
		           "'0' AS COLA, "+
		           "TRAM.CDSCBREVE AS COLB, "+
		           "HIST.CNUEVO AS COLC, "+
		           "HIST.DTREGISTRO AS COLD, "+
		           "HIST.DTINGRESO AS COLE, "+
		           "HIST.CFOLPERMISO AS COLF, "+
		           "HIST.DTOTORGA AS COLG, "+
		           "HIST.DTCONCLUSION AS COLH, "+
		           "HIST.CCONCLUSION AS COLI, "+
		           "HIST.CCEDIDO AS COLJ, "+
		           "OFI.CDSCBREVE AS COLK, "+
		           "HIST.CSOLICITANTE AS COLL, "+
		           "CARR.CDSCARRETERA AS COLM, "+
		           "CARR.CCONSECIONARIO AS COLN, "+
		           "SUBSTR(HIST.CKMSENTIDO,1,45) AS COLO, "+
		           "HIST.CHECHOS AS COLP, "+
		           "HIST.CESTATUS AS COLQ, "+
		           "HIST.ICVEHISTADV AS COLR, "+
		           "NULL AS COLS, "+
		           "NULL AS COLT, "+
		           "NULL AS COLU, "+
		           "NULL AS COLV, "+ 
		           "NULL AS COLW, "+
		           "NULL AS COLX, "+
		           "NULL AS COLY "+
		      "FROM TRADATOSHISTADV HIST "+
		      "LEFT JOIN TRACATTRAMITE TRAM ON TRAM.ICVETRAMITE = HIST.ICVETRAMITE "+
		      "LEFT JOIN TRACATCARRETERA CARR ON CARR.ICVECARRETERA = HIST.ICVEAUTOPISTA "+
		      "LEFT JOIN GRLOFICINA OFI ON OFI.ICVEOFICINA = HIST.ICVEOFICINA "
		      +request.getParameter("cFiltroHist")+") ORDER BY COLD";
  }else if (oAccion.getCAccion().equals("tableroInicial")){
	  
	  
	  strQuery = "("//PENDIENTES DE RESOLUCION
	  			+"SELECT 1 AS IORDEN, 'Solicitudes pendientes de resolución' as ctitulo ,COUNT(SOL.INUMSOLICITUD) as ccuenta FROM TRAREGSOLICITUD SOL "
			  +"LEFT JOIN TRAREGTRAMXSOL CAN ON CAN.IEJERCICIO = SOL.IEJERCICIO AND CAN.INUMSOLICITUD = SOL.INUMSOLICITUD "
			  +"WHERE SOL.DTRESOLTRAM IS NULL "
			  +"AND SOL.LABANDONADA = 0 "
			  +"AND CAN.DTCANCELACION IS NULL"
			  +") "
	  +"UNION( " //SOLICITUDES CON RETRASO
			  +"SELECT 2 AS IORDEN,'Solicitudes con retraso' as ctitulo ,COUNT(SOL.INUMSOLICITUD) as ccuenta FROM TRAREGSOLICITUD SOL "
			  +"INNER JOIN (SELECT DISTINCT(IEJERCICIO) IEJERCICIO, INUMSOLICITUD FROM TRAREGRETRASO) RET ON RET.IEJERCICIO = SOL.IEJERCICIO AND RET.INUMSOLICITUD = SOL.INUMSOLICITUD "
			  +"LEFT JOIN TRAREGTRAMXSOL CAN ON CAN.IEJERCICIO = SOL.IEJERCICIO AND CAN.INUMSOLICITUD = SOL.INUMSOLICITUD "
			  +"WHERE SOL.DTRESOLTRAM IS NULL "
			  +"AND SOL.LABANDONADA = 0 "
			  +"AND CAN.DTCANCELACION IS NULL "
	  		  +") "
	  +"UNION ( "
			  +"SELECT 3 AS IORDEN,'Solicitudes por internet pendientes de inicio' as ctitulo, COUNT(SOL.INUMSOLICITUD) FROM TRAREGSOLICITUD SOL "
			  +"LEFT JOIN TRAREGTRAMXSOL CAN ON CAN.IEJERCICIO = SOL.IEJERCICIO AND CAN.INUMSOLICITUD = SOL.INUMSOLICITUD "
			  +"LEFT JOIN (SELECT DISTINCT(REGET.IEJERCICIO) IEJEETAPA, REGET.INUMSOLICITUD ISOLETAPA FROM TRAREGETAPASXMODTRAM REGET WHERE REGET.ICVEETAPA IN (1,25)) REGE ON REGE.IEJEETAPA = SOL.IEJERCICIO AND REGE.ISOLETAPA = SOL.INUMSOLICITUD "
			  +"WHERE SOL.DTRESOLTRAM IS NULL "
			  +"AND SOL.LABANDONADA = 0 "
			  +"AND CAN.DTCANCELACION IS NULL "
			  +"AND SOL.LTRAMINERNET = 1 "
			  +"AND REGE.IEJEETAPA IS NULL AND  REGE.ISOLETAPA IS NULL "
	  +") "
	  +"ORDER BY IORDEN ";
	  
  }else if (oAccion.getCAccion().equals("ListadoInternet")){
	  
	  
	  strQuery = "SELECT SOL.IEJERCICIO, SOL.INUMSOLICITUD, PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO || ' ' || PER.CAPMATERNO CNOMBRE , TRAM.CDSCBREVE CTRAM, OFI.CDSCBREVE COFI, SOL.TSREGISTRO FROM TRAREGSOLICITUD SOL " 
			  +"LEFT JOIN TRAREGTRAMXSOL CAN ON CAN.IEJERCICIO = SOL.IEJERCICIO AND CAN.INUMSOLICITUD = SOL.INUMSOLICITUD  " 
			  +"LEFT JOIN (SELECT DISTINCT(REGET.IEJERCICIO) IEJEETAPA, REGET.INUMSOLICITUD ISOLETAPA FROM TRAREGETAPASXMODTRAM REGET WHERE REGET.ICVEETAPA IN (1,25)) REGE ON REGE.IEJEETAPA = SOL.IEJERCICIO AND REGE.ISOLETAPA = SOL.INUMSOLICITUD "  
			  +"INNER JOIN GRLOFICINA OFI ON OFI.ICVEOFICINA = SOL.ICVEOFICINA " 
			  +"INNER JOIN TRACATTRAMITE TRAM ON TRAM.ICVETRAMITE  = SOL.ICVETRAMITE " 
			  +"INNER JOIN GRLPERSONA PER ON PER.ICVEPERSONA = SOL.ICVESOLICITANTE  " 
			  +"WHERE SOL.DTRESOLTRAM IS NULL  " 
			  +"AND SOL.LABANDONADA = 0  " 
			  +"AND CAN.DTCANCELACION IS NULL "  
			  +"AND SOL.LTRAMINERNET = 1  " 
			  +"AND REGE.IEJEETAPA IS NULL  " 
			  +"AND REGE.ISOLETAPA IS NULL  " 
			  +"AND SOL.ICVEOFICINA ="+request.getParameter("iCveOficina")+" ORDER BY SOL.IEJERCICIO, SOL.INUMSOLICITUD";
	  
  }else{
	  strQuery = "SELECT " +
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
		         "       0 as ICVEETA, " +//20
		         "       '' as CDSCETAPA, " +//21
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
		         "      SUBSTR(DAT.CKMSENTIDO,1,20) CKMSENTIDO, "+//43
				 " CASE WHEN FOL.DTPERMISO IS NOT NULL THEN (YEAR(FOL.DTPERMISO - date(TRAREGSOLICITUD.tsregistro))*365 + MONTH(FOL.DTPERMISO - date(TRAREGSOLICITUD.tsregistro))*30 + DAY(FOL.DTPERMISO - date(TRAREGSOLICITUD.tsregistro))) ELSE -1 END AS DIASPERM, "+//44"+
		         " CASE WHEN TRAREGTRAMXSOL.DTCANCELACION IS NOT NULL THEN (YEAR(TRAREGTRAMXSOL.DTCANCELACION - DATE(TRAREGSOLICITUD.TSREGISTRO))*365 + MONTH(TRAREGTRAMXSOL.DTCANCELACION - DATE(TRAREGSOLICITUD.TSREGISTRO))*30 + DAY(TRAREGTRAMXSOL.DTCANCELACION - DATE(TRAREGSOLICITUD.TSREGISTRO))) ELSE -1 END AS DIASCANCELA, "+ //45
		         " CASE WHEN TRAREGSOLICITUD.DTRESOLTRAM IS NOT NULL THEN (YEAR(TRAREGSOLICITUD.DTRESOLTRAM - DATE(TRAREGSOLICITUD.TSREGISTRO))*365 + MONTH(TRAREGSOLICITUD.DTRESOLTRAM - DATE(TRAREGSOLICITUD.TSREGISTRO))*30 + DAY(TRAREGSOLICITUD.DTRESOLTRAM - DATE(TRAREGSOLICITUD.TSREGISTRO))) ELSE -1 END AS DIASRESOL, "+ //46
				 " (SELECT COUNT(ICVERETRASO) FROM TRAREGRETRASO WHERE IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD) AS IRETRASO "+//47
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
		         
		         //"  LEFT JOIN TRAREGETAPASXMODTRAM TREXMT ON TRAREGSOLICITUD.IEJERCICIO = TREXMT.IEJERCICIO " +
		         //"    AND TRAREGSOLICITUD.INUMSOLICITUD = TREXMT.INUMSOLICITUD" +
		         //"  JOIN TRAETAPA E ON E.ICVEETAPA = TREXMT.ICVEETAPA" +
		         
		         "  LEFT JOIN TRAREGTRAMXSOL ON TRAREGSOLICITUD.IEJERCICIO = TRAREGTRAMXSOL.IEJERCICIO AND TRAREGSOLICITUD.INUMSOLICITUD = TRAREGTRAMXSOL.INUMSOLICITUD "+
		         "  JOIN TRAREGDATOSADVXSOL DAT ON TRAREGSOLICITUD.IEJERCICIO = DAT.IEJERCICIO  AND TRAREGSOLICITUD.INUMSOLICITUD = DAT.INUMSOLICITUD "+
		         "  JOIN TRACATCARRETERA CAR ON DAT.ICVECARRETERA = CAR.ICVECARRETERA "+ 
		         "  LEFT JOIN TRAREGRESOLVTECXSOL VT ON TRAREGSOLICITUD.IEJERCICIO = VT.IEJERCICIO " +
		         "  AND TRAREGSOLICITUD.INUMSOLICITUD = VT.INUMSOLICITUD " +
		       	 "LEFT JOIN TRADATOSPERM FOL ON TRAREGSOLICITUD.IEJERCICIO=FOL.IEJERCICIO  "+
		   		      "  AND TRAREGSOLICITUD.INUMSOLICITUD=FOL.INUMSOLICITUD     "+
		         oAccion.getCFiltro() +
		         //" AND (SELECT COUNT(TRAREGETAPASXMODTRAM.IORDEN) FROM TRAREGETAPASXMODTRAM WHERE " +
		         //" TRAREGETAPASXMODTRAM.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND " +
		         //" TRAREGETAPASXMODTRAM.INUMSOLICITUD = TRAREGSOLICITUD.INUMSOLICITUD AND " +
		         //" TRAREGETAPASXMODTRAM.ICVETRAMITE = TRAREGSOLICITUD.ICVETRAMITE AND " +
		         //" TRAREGETAPASXMODTRAM.ICVEMODALIDAD = TRAREGSOLICITUD.ICVEMODALIDAD) >= 1 " +
		         //" AND TREXMT.iOrden = " +
		         //" ( " +
		         //"   SELECT " + 
		         //"   MAX(EMT.iOrden) " +
		         //"   FROM TRARegEtapasXModTram EMT " +
		         //"   WHERE TRARegSolicitud.IEJERCICIO = EMT.IEJERCICIO " +
		         //"   AND TRARegSolicitud.INUMSOLICITUD = EMT.INUMSOLICITUD " +
		         //"   AND TRARegSolicitud.iCveTramite = EMT.iCveTramite " +
		         //"   AND TRARegSolicitud.iCveModalidad = EMT.iCveModalidad " +
		         //" ) " +
		         oAccion.getCOrden();
  }
  Vector vcListado;
  String cNavStatus="";
  
         try{
	         vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",strQuery);
	  		 oAccion.navega(vcListado);
	  		 cNavStatus = oAccion.getCNavStatus();
         }catch(Exception e){
        	 cError="consulta";
         }
         
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   out.print(oAccion.getArrayCD());
%>
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>',
                '<%=cEtapas%>');


</script>
<%}%>

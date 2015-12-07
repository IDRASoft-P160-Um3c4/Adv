<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.util.*" %>
<%
/**
 * <p>Title: pgTRARegSolicitud2.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */

  TFechas fecha = new TFechas();
  System.out.print("*****     "+fecha.getThisTime());
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma");
    try{
      vDinRep = dTRARegSolicitud.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
    try{
      vDinRep = dTRARegSolicitud.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  /** Verifica si la Acción a través de hdBotón es igual a "GuardarCambios" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarCambios")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cObsTramite");
    try{
      vDinRep = dTRARegSolicitud.updateAutorizaRec(vDinRep,null);
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

  if(oAccion.getCAccion().equals("UpdateImpresoB")){
	String[] aSolicitudes;  
	TDTRARegEtapasXModTram regEtapasXModTram = new TDTRARegEtapasXModTram();  
    vDinRep = oAccion.setInputs("cFiltroImpreso,idUser");
    boolean control = true;
    try{
      vDinRep = dTRARegSolicitud.updateImpresoB(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    
    aSolicitudes = vDinRep.getString("cFiltroImpreso").split("/");
    String[] aActual;
    
    for(int i = 0; i < aSolicitudes.length; i++){
        aActual = aSolicitudes[i].split(",");
        
        if( (aActual.length == 4) && (control) ){        	
        	regEtapasXModTram.upDateEstadoCita(Integer.parseInt(aActual[0],10),Integer.parseInt(aActual[1],10),14,null);
        	control = false;
        }
    }      
    
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
	String cSQL = "SELECT " +
                     "       RS.IEJERCICIO, " +
                     "       RS.INUMSOLICITUD, " +
                     "       RS.ICVETRAMITE, " +
                     "       RS.ICVEMODALIDAD , " +
                     "       RS.ICVESOLICITANTE, " +
                     "       RS.ICVEREPLEGAL, " +
                     "       RS.CNOMAUTORIZARECOGER, " +
                     "       RS.TSREGISTRO , " +
                     "       RS.DTLIMITEENTREGADOCS, " +
                     "       RS.DTESTIMADAENTREGA, " +
                     "       RS.LPAGADO, " +
                     "       RS.LPRINCIPAL , " +
                     "       RS.LIMPRESO, " +
                     "       RS.CENVIARRESOLUCIONA, " +
                     "       RS.COBSTRAMITE, " +
                     "       T.CCVEINTERNA || ' - ' || T.CDSCBREVE AS CCVEDSCTRAMITE, " +
                     "       D.ICVEDOMICILIO, " +
                     "       D.ICVETIPODOMICILIO, " +
                     "       D.CCALLE, " +
                     "       D.CNUMEXTERIOR , " +
                     "       D.CNUMINTERIOR, " +
                     "       D.CCOLONIA, " +
                     "       D.CCODPOSTAL, " +
                     "       D.CTELEFONO, " +
                     "       D.ICVEPAIS , " +
                     "       LRL.CNOMBRE, " +
                     "       PRL.CNOMBRE AS PAIS, " +
                     "       D.ICVEENTIDADFED, " +
                     "       ERL.CNOMBRE AS ESTADO, " +
                     "       MRL.CNOMBRE AS MUNICIPIO , " +
                     "       D.ICVEMUNICIPIO, " +
                     "       LRL.ICVELOCALIDAD, " +
                     "       TD.CDSCTIPODOMICILIO, " +
                     "       D.LPREDETERMINADO , " +
                     "       D.CCALLE || ' NO. ' || D.CNUMEXTERIOR || ' INT. ' || D.CNUMINTERIOR || ' COL. ' || D.CCOLONIA || ', ' || MRL.CNOMBRE || ' (' || ERL.CABREVIATURA ||'.)' || ' C.P. ' || D.CCODPOSTAL AS CDOMICILIO , " +
                     "       P.CRFC AS CRFCREPLEGAL, " +
                     "       P.ITIPOPERSONA AS ITIPOPERSONAREPLEGAL, " +
                     "       P.CNOMRAZONSOCIAL AS CNOMRAZONSOCIALREPLEGAL, " +
                     "       P.CAPPATERNO AS CAPPATERNOREPLEGAL, " +
                     "       P.CAPMATERNO AS CAPMATERNOREPLEGAL, " +
                     "       PS.CNOMRAZONSOCIAL || ' ' || PS.CAPPATERNO || ' ' || PS.CAPMATERNO AS CNOMBREPRSONA, " +
                     "       DP.CCALLE || ' NO. ' || DP.CNUMEXTERIOR || ' INT. ' || DP.CNUMINTERIOR || ' COL. ' || DP.CCOLONIA || ', ' || MP.CNOMBRE || ' (' || EP.CABREVIATURA ||'.)' || ' C.P. ' || DP.CCODPOSTAL AS CDOMICILIOPERSONA , " +
                     "       PS.CRFC, " +
                     "       DP.CCALLE AS CCALLEPER, " +
                     "       DP.CNUMEXTERIOR AS CNUMENTERIORPER , " +
                     "       DP.CNUMINTERIOR AS CNUMINTERIORPER , " +
                     "       DP.CCOLONIA AS CCOLONIAPER , " +
                     "       MP.CNOMBRE AS CNOMBREMUNPER , " +
                     "       EP.CABREVIATURA AS CABREVMUNPER , " +
                     "       DP.CCODPOSTAL AS CCODPOSTALPER, " +
                     "       PS.CNOMRAZONSOCIAL AS CNOMPERSOLIC, " +
                     "       PS.CAPPATERNO AS CAPPATERNOSOLIC, " +
                     "       PS.CAPMATERNO AS CAPMATERNOSOLIC, " +
                     "       DP.ICVEPAIS AS ICVEPAISSOLIC, " +
                     "       DP.ICVEENTIDADFED AS ICVEENTIDADSOLIC, " +
                     "       DP.ICVEMUNICIPIO AS ICVEMUNICSOLIC, " +
                     "       ( SELECT COUNT(1) FROM TRAREGREFPAGO RP " +
                     "         WHERE RP.IEJERCICIO = RS.IEJERCICIO " +
                     "         AND RP.INUMSOLICITUD = RS.INUMSOLICITUD " +
                     "         AND RP.LPAGADO = 0 " +
                     "       ) AS INUMPENDPAGO, " +
                     "       RS.LRESOLUCIONPOSITIVA, " +
                     "       RS.DUNIDADESCALCULO, " +
                     "       RS.IIDBIEN " +
                     "FROM TRAREGSOLICITUD RS " +
                     "JOIN TRACATTRAMITE T ON T.ICVETRAMITE = RS.ICVETRAMITE " +
                     "LEFT JOIN GRLDOMICILIO D ON D.ICVEPERSONA=RS.ICVEREPLEGAL AND D.ICVEDOMICILIO=RS.ICVEDOMICILIOREPLEGAL " +
                     "LEFT JOIN GRLPAIS PRL ON PRL.ICVEPAIS=D.ICVEPAIS " +
                     "LEFT JOIN GRLENTIDADFED ERL ON ERL.ICVEPAIS=D.ICVEPAIS AND ERL.ICVEENTIDADFED=D.ICVEENTIDADFED " +
                     "LEFT JOIN GRLMUNICIPIO MRL ON MRL.ICVEPAIS=D.ICVEPAIS AND MRL.ICVEENTIDADFED=D.ICVEENTIDADFED AND MRL.ICVEMUNICIPIO=D.ICVEMUNICIPIO " +
                     "LEFT JOIN GRLLOCALIDAD LRL ON LRL.ICVEPAIS=D.ICVEPAIS AND LRL.ICVEENTIDADFED=D.ICVEENTIDADFED AND LRL.ICVEMUNICIPIO=D.ICVEMUNICIPIO AND LRL.ICVELOCALIDAD=D.ICVELOCALIDAD " +
                     "LEFT JOIN GRLTIPODOMICILIO TD ON TD.ICVETIPODOMICILIO=D.ICVETIPODOMICILIO " +
                     "LEFT JOIN GRLPERSONA P ON P.ICVEPERSONA=RS.ICVEREPLEGAL " +
                     "LEFT JOIN GRLPERSONA PS ON PS.ICVEPERSONA=RS.ICVESOLICITANTE " +
                     "LEFT JOIN GRLDOMICILIO DP ON DP.ICVEPERSONA=RS.ICVESOLICITANTE AND DP.ICVEDOMICILIO=RS.ICVEDOMICILIOSOLICITANTE " +
                     "LEFT JOIN GRLPAIS PP ON PP.ICVEPAIS=DP.ICVEPAIS " +
                     "LEFT JOIN GRLENTIDADFED EP ON EP.ICVEPAIS=DP.ICVEPAIS AND EP.ICVEENTIDADFED=DP.ICVEENTIDADFED " +
                     "LEFT JOIN GRLMUNICIPIO MP ON MP.ICVEPAIS=DP.ICVEPAIS AND MP.ICVEENTIDADFED=DP.ICVEENTIDADFED AND MP.ICVEMUNICIPIO=DP.ICVEMUNICIPIO " +
                     "LEFT JOIN GRLLOCALIDAD LP ON LP.ICVEPAIS=DP.ICVEPAIS AND LP.ICVEENTIDADFED=DP.ICVEENTIDADFED AND LP.ICVEMUNICIPIO=DP.ICVEMUNICIPIO AND LP.ICVELOCALIDAD=DP.ICVELOCALIDAD " +
                     "LEFT JOIN GRLTIPODOMICILIO TD1 ON TD1.ICVETIPODOMICILIO=DP.ICVETIPODOMICILIO " +
                     //"LEFT JOIN TRAREGETAPASXMODTRAM ET2 ON ET2.IEJERCICIO = RS.IEJERCICIO AND ET2.INUMSOLICITUD = RS.INUMSOLICITUD AND ET2.ICVETRAMITE = RS.ICVETRAMITE AND ET2.ICVEMODALIDAD = RS.ICVEMODALIDAD AND ET2.ICVEETAPA IN (17,21) " +
                     "LEFT JOIN TRAREGETAPASXMODTRAM ET2 ON ET2.IEJERCICIO = RS.IEJERCICIO AND ET2.INUMSOLICITUD = RS.INUMSOLICITUD AND ET2.ICVETRAMITE = RS.ICVETRAMITE AND ET2.ICVEMODALIDAD = RS.ICVEMODALIDAD AND ET2.ICVEETAPA IN (7) " +
                     "LEFT JOIN TRAREGTRAMXSOL CANC ON CANC.IEJERCICIO=RS.IEJERCICIO AND CANC.INUMSOLICITUD=RS.INUMSOLICITUD "+
                     oAccion.getCFiltro() +
                     "    AND CANC.DTCANCELACION IS NULL " +
                     "    AND ET2.IORDEN = (    SELECT " +
                     "                          MAX(ET3.IORDEN) " +
                     "                          FROM TRAREGETAPASXMODTRAM ET3 " +
                     "                          WHERE ET3.IEJERCICIO = RS.IEJERCICIO " +
                     "                          AND ET3.INUMSOLICITUD = RS.INUMSOLICITUD " +
                     "                          AND ET3.ICVETRAMITE = RS.ICVETRAMITE " +
                     "                          AND ET3.ICVEMODALIDAD = RS.ICVEMODALIDAD "+
                     "                     ) " +
                     "  ORDER BY RS.IEJERCICIO,RS.INUMSOLICITUD ";

  Vector vcListado = new Vector(), vcTemp = new Vector(), vcResultado = new Vector();
  String cSQL2 = "", cSQL3="";

  try{
    vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",cSQL);
    if (vcListado != null && vcListado.size() > 0){
      TVDinRep vDataSol = new TVDinRep();
      for (int i=0; i<vcListado.size(); i++){
        vDataSol = (TVDinRep)vcListado.get(i);
        cSQL2 = "SELECT CT.lRequierePago "+
                " FROM TRAConfiguraTramite CT "+
                " WHERE CT.iCveTramite = " + vDataSol.getString("iCveTramite") +
                "   AND CT.iCvemodalidad = " + vDataSol.getString("iCveModalidad") +
                " ORDER BY CT.dtIniVigencia DESC";
        try{
          vcTemp = dTRARegSolicitud.findByCustom("iCveTramite,iCveModalidad,dtIniVigencia",cSQL2);
          if (vcTemp != null && vcTemp.size()>0 && ((TVDinRep)vcTemp.get(0)).getInt("lRequierePago")==1){
            cSQL3 = "SELECT RP.iRefNumerica, RP.cRefAlfaNum, RP.lPagado, RP.dtPago "+
                    " FROM TRARegRefPago RP "+
                    " WHERE RP.iEjercicio = " + vDataSol.getString("iEjercicio") +
                    "   AND RP.iNumSolicitud = " + vDataSol.getString("iNumSolicitud")+
                    "   AND RP.lPagado = 0 ";
            try{
              vcTemp = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud,iConsecutivo", cSQL3);
              if(vcTemp != null){
                if (vcTemp.size()>0)
                  vDataSol.put("lPagado", 0);
                else
                  vDataSol.put("lPagado", 1);
              }else
                vDataSol.put("lPagado", 1);
            }catch(Exception e){
              vDataSol.put("lPagado", 0);
            }
          }else
            vDataSol.put("lPagado", 1);
        }catch(Exception e){
          vDataSol.put("lPagado", 0);
        }
        vcResultado.add(vDataSol);
      }
      vcListado = vcResultado;
    }
  System.out.print("*****     "+fecha.getThisTime());
  System.out.print("*****     ");
  }catch(Exception e){
    cError = e.getMessage();
  }
  oAccion.setINumReg(vcListado.size());
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();
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
                '<%=oAccion.getBPK()%>');
</script>
<%}%>

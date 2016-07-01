<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
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

	  
  String cSql = "SELECT  RS.iEjercicio, RS.iNumSolicitud, RS.iCveTramite, RS.iCveModalidad, " + //3
                "RS.iCveSolicitante, RS.iCveRepLegal, RS.cNomAutorizaRecoger, RS.tsRegistro, " + //7
                "RS.dtLimiteEntregaDocs, RS.dtEstimadaEntrega, RS.lPagado, RS.lPrincipal, " + //11
                "RS.lImpreso, RS.cEnviarResolucionA, RS.cObsTramite, " + //14
                "T.cCveInterna || ' - ' || T.cDscBreve AS cCveDscTramite, " + //15
                "PNC.ICONSECUTIVOPNC AS cCONPNC, RS.CDSCBIEN, RPNC.lResuelto, RS.DUNIDADESCALCULO, RS.iIdBien, " + //20
                "O.CDSCBREVE, CARR.CDSCARRETERA, DADV.CKMSENTIDO, DADV.DTVISITA "+ //21, 22, 23, 24
                "FROM TRAREGSOLICITUD RS " +
                "JOIN TRACATTRAMITE T ON RS.iCveTramite = T.iCveTramite " +
                "LEFT JOIN TRAREGPNCETAPA PNC ON RS.IEJERCICIO = PNC.IEJERCICIO AND RS.INUMSOLICITUD = PNC.INUMSOLICITUD " +
                "AND RS.ICVETRAMITE = PNC.ICVETRAMITE AND RS.ICVEMODALIDAD = PNC.ICVEMODALIDAD "+
                " LEFT JOIN GRLRegistroPNC RPNC ON RPNC.iEjercicio = PNC.iEjercicioPNC AND RPNC.iConsecutivoPNC = PNC.ICONSECUTIVOPNC "+
                "LEFT JOIN GRLOFICINA O ON O.ICVEOFICINA=RS.ICVEOFICINA"+
                " join TRAREGDATOSADVXSOL DADV on DADV.IEJERCICIO = RS.IEJERCICIO AND DADV.INUMSOLICITUD = RS.INUMSOLICITUD"+ 
  				" join TRACATCARRETERA CARR on DADV.ICVECARRETERA = CARR.ICVECARRETERA";
  if(oAccion.getCFiltro() != ""){
     cSql += oAccion.getCFiltro() + "AND ";
  }else{
     cSql += " Where ";
  }
  cSql +=       " RS.ICVEOFICINA = "+ request.getParameter("hdCveOfic") +
		  		" AND RS.ICVEDEPARTAMENTO = "+ request.getParameter("hdCveDpto") +
		        " and RS.INUMSOLICITUD NOT IN (SELECT TXS.INUMSOLICITUD " + // LEL06092006
                "FROM TRAREGTRAMXSOL TXS " +
                "WHERE TXS.IEJERCICIO = RS.IEJERCICIO " +
                "AND TXS.INUMSOLICITUD = RS.INUMSOLICITUD) " +   //FIN LEL06092006
                " ORDER BY RS.iEjercicio, RS.iNumSolicitud ";
  Vector vcListado = new Vector(), vcTemp = new Vector(), vcResultado = new Vector();
  int lReqPag = 0;
  String cSQL2 = "", cSQL3="", cSQL4="";
  try{
    vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",cSql);
    //System.out.println("*****    Listado.length "+vcListado.size());
    if (vcListado != null && vcListado.size() > 0){
      //System.out.println("*****    Entra en requiere pago");
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
          if (vcTemp != null && vcTemp.size()>0)
            lReqPag = ((TVDinRep)vcTemp.get(0)).getInt("lRequierePago");
          if (vcTemp != null && vcTemp.size()>0 && ((TVDinRep)vcTemp.get(0)).getInt("lRequierePago")==1){
            cSQL4 = "SELECT RS.iEjercicio, RS.iNumSolicitud, RS.iCveTramite, RS.iCveModalidad, RS.lPagado, "+
                    "CTM.iCveConcepto, CTM.lPagoAnticipado, RP.lPagado AS ConceptoPagado, RP.LPAGADO, CG.ICVEGRUPO "+
                    "FROM TRARegSolicitud RS "+
                    "JOIN TRAConceptoXTramMod CTM ON CTM.iEjercicio = RS.iEjercicio AND CTM.iCveTramite = RS.iCveTramite AND CTM.iCveModalidad = RS.iCveModalidad "+
                    "LEFT JOIN TRARegRefPago RP ON RP.iEjercicio = RS.iEjercicio AND RP.iNumSolicitud = RS.iNumSolicitud AND RP.iCveConcepto = CTM.iCveConcepto "+
                    "LEFT JOIN TRACONCEPTOXGRUPO CG ON CG.ICVECONCEPTO = CTM.ICVECONCEPTO "+
                    "WHERE RS.iEjercicio = " + vDataSol.getString("iEjercicio") +
                    "  AND RS.iNumSolicitud = " + vDataSol.getString("iNumSolicitud") +
                    "  AND CTM.lPagoAnticipado = 1 ";
            try{

              vcTemp = dTRARegSolicitud.findByCustom("", cSQL4);
              if(vcTemp != null)
                if(vcTemp.size() > 0){
                  for(int ipa =0; ipa<vcTemp.size();ipa++){
                    TVDinRep vPagoAnticipado = (TVDinRep) vcTemp.get(ipa);
                    if(vPagoAnticipado.getInt("LPAGADO")<1){
                      if(vPagoAnticipado.getString("ICVEGRUPO").equals("null") || vPagoAnticipado.getInt("ICVEGRUPO")<1 )vDataSol.put("lPagoAnticipadoNoPagado", 1);
                      else{
                        int lpagAntic = 1;
                        for(int ipt=0;ipt<vcTemp.size();ipt++){
                          TVDinRep vPagoATemp = (TVDinRep) vcTemp.get(ipt);
                          if(vPagoAnticipado.getInt("ICVEGRUPO")==vPagoATemp.getInt("ICVEGRUPO") && vPagoATemp.getInt("LPAGADO") == 1){
                            lpagAntic = 0;
                          }
                        }
                        vDataSol.put("lPagoAnticipadoNoPagado", lpagAntic);
                      }
                    }
                  }
                }else
                  vDataSol.put("lPagoAnticipadoNoPagado", 0);
              else
                vDataSol.put("lPagoAnticipadoNoPagado", 0);
            }catch(Exception e){
              vDataSol.put("lPagoAnticipadoNoPagado", 0);
            }

            cSQL3 = "SELECT RP.iRefNumerica, RP.cRefAlfaNum, RP.lPagado, RP.dtPago "+
                    " FROM TRARegRefPago RP "+
                    " WHERE RP.iEjercicio = " + vDataSol.getString("iEjercicio") +
                    "   AND RP.iNumSolicitud = " + vDataSol.getString("iNumSolicitud");
            try{
              vcTemp = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud,iConsecutivo", cSQL3);
              if(vcTemp != null){
                if (vcTemp.size()>0)
                  vDataSol.put("lMovGenerados", 1);
                else
                  vDataSol.put("lMovGenerados", 0);
              }else
                vDataSol.put("lMovGenerados", 0);
            }catch(Exception e){
              vDataSol.put("lMovGenerados", 0);
            }
          }else
            vDataSol.put("lMovGenerados", 1);
          //vDataSol.put("lRequierePago", ((TVDinRep)vcTemp.get(0)).getInt("lRequierePago"));
          vDataSol.put("lRequierePago", lReqPag);
        }catch(Exception e){
          vDataSol.put("lPagoAnticipadoNoPagado", 0);
          vDataSol.put("lMovGenerados", 0);
          vDataSol.put("lRequierePago", 0);
        }
        vcResultado.add(vDataSol);
        for(int j = 0;j < vDataSol.getVcKeys().size();j++){
          //System.out.println((String) vDataSol.getVcKeys().get(j));
        //System.out.println(vDataSol.toHashMap().toString());
        }
      }
      vcListado = vcResultado;
    }
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

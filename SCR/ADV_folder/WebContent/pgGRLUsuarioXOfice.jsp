syst<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLUsuarioXOfice.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLUsuarioXOfic</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLUsuarioXOfic dGRLUsuarioXOfic = new TDGRLUsuarioXOfic();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

  //if(oAccion.getCAccion().equals("Guardar")){

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */

 Vector vcListado = new Vector();
 if(oAccion.getCAccion().equals("Oficinas")){
   vcListado = dGRLUsuarioXOfic.findByCustom("iCveOficina,iCveDepartamento,iCveUsuario",
  "SELECT DISTINCT " +
  "  GRLUSUARIOXOFIC.ICVEOFICINA, " +
  "  GRLOFICINA.CDSCOFICINA, " +
  "  GRLOFICINA.CDSCBREVE " +
  "FROM GRLUSUARIOXOFIC " +
  "  JOIN GRLOFICINA ON GRLOFICINA.ICVEOFICINA = GRLUSUARIOXOFIC.ICVEOFICINA " +
   oAccion.getCFiltro() + oAccion.getCOrden());
 }

 if(oAccion.getCAccion().equals("Deptos")){

   Vector vcRegs = new Vector();
   Vector vcTemp = null;
   TVDinRep vDatos = new TVDinRep();
   String cQueryDependencia="", cWhereDepend="";
   String cQuery =    "SELECT " +
   "  GRLUSUARIOXOFIC.ICVEOFICINA, " +
   "  GRLUSUARIOXOFIC.ICVEDEPARTAMENTO, " +
   "  GRLDEPARTAMENTO.CDSCDEPARTAMENTO, " +
   "  GRLDEPARTAMENTO.CDSCBREVE, " +
   "  GRLUSUARIOXOFIC.ICVEUSUARIO " +
   "FROM GRLUSUARIOXOFIC " +
   "JOIN GRLDEPARTAMENTO ON GRLDEPARTAMENTO.ICVEDEPARTAMENTO = GRLUSUARIOXOFIC.ICVEDEPARTAMENTO ";
   vcRegs = dGRLUsuarioXOfic.findByCustom("iCveOficina,iCveDepartamento,iCveUsuario",cQuery + oAccion.getCFiltro() + oAccion.getCOrden());
   for(int i=0; i<vcRegs.size(); i++){
     vcListado.addElement(vcRegs.get(i));
   }
   for(int i=0; i<vcRegs.size(); i++){
    // vcListado.addElement(vcRegs.get(i));
     vDatos = (TVDinRep) vcRegs.get(i);
     cQueryDependencia = "SELECT GRLDEPTODEPEND.ICVEOFICINA, " +
     			"  GRLDEPTODEPEND.ICVEDEPARTAMENTO, " +
			"  GRLDEPARTAMENTO.CDSCDEPARTAMENTO, " +
			"  GRLDEPARTAMENTO.CDSCBREVE, " +
			vDatos.getString("ICVEUSUARIO") + " AS iCveUsuario " +
			"FROM  GRLDEPTODEPEND " +
			"JOIN GRLDEPARTAMENTO ON GRLDEPARTAMENTO.ICVEDEPARTAMENTO = GRLDEPTODEPEND.ICVEDEPARTAMENTO " +
			"WHERE GRLDEPTODEPEND.ICVEOFICINA = "+ vDatos.getString("ICVEOFICINA") +
                        " AND  GRLDEPTODEPEND.ICVEDEPTOHIJO = " + vDatos.getString("ICVEDEPARTAMENTO");
                        //" AND GRLDEPTODEPEND.ICVEDEPARTAMENTO > 0";
     vcTemp = dGRLUsuarioXOfic.findByCustom("iCveOficina,iCveDepartamento,iCveUsuario",cQueryDependencia);
     if(vcTemp != null && vcTemp.size()>0){
       boolean lAgregar = true;
       for(int j=0; j<vcListado.size();j++){
         System.out.print(((TVDinRep)vcListado.get(j)).getInt("ICVEDEPARTAMENTO") + "==" + ((TVDinRep)vcTemp.get(0)).getInt("ICVEDEPARTAMENTO"));
         if(((TVDinRep)vcListado.get(j)).getInt("ICVEDEPARTAMENTO") == ((TVDinRep)vcTemp.get(0)).getInt("ICVEDEPARTAMENTO")){
           lAgregar = false;
           j = vcListado.size();
         }
       }
       if(lAgregar)
         vcListado.addElement(vcTemp.get(0));
     }
   }

 }

 if(oAccion.getCAccion().equals("Deptos1")){

   vcListado = dGRLUsuarioXOfic.findByCustom("iCveOficina,iCveDepartamento,iCveUsuario",
   "SELECT " +
   "  GRLUSUARIOXOFIC.ICVEOFICINA, " +
   "  GRLUSUARIOXOFIC.ICVEDEPARTAMENTO, " +
   "  GRLDEPARTAMENTO.CDSCDEPARTAMENTO, " +
   "  GRLDEPARTAMENTO.CDSCBREVE, " +
   "  GRLUSUARIOXOFIC.ICVEUSUARIO " +
   "FROM GRLUSUARIOXOFIC " +
   "JOIN GRLDEPARTAMENTO ON GRLDEPARTAMENTO.ICVEDEPARTAMENTO = GRLUSUARIOXOFIC.ICVEDEPARTAMENTO " +
   oAccion.getCFiltro() + oAccion.getCOrden());
 }

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

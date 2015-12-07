<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRARegSolicitud3.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegSolicitud</p>
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
     TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
     //System.out.println("CHALEEEEE " + vUsuario.getICveusuario());
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
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma");
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
  String cNavStatus = "";
if(oAccion.getCAccion().equals("Filtro")){
       vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
       String cSql2 = "SELECT * FROM TRAREGTRAMXSOL " +
                   " join TRAREGSOLICITUD on traregtramxsol.ICVETRAMITE = traregsolicitud.ICVETRAMITE " +
                   "and traregtramxsol.ICVEMODALIDAD = traregsolicitud.ICVEMODALIDAD " +
                   "and traregtramxsol.IEJERCICIO = traregsolicitud.IEJERCICIO " +
                   "and traregtramxsol.INUMSOLICITUD = traregsolicitud.INUMSOLICITUD " +
                   "where TRAREGTRAMXSOL.IEJERCICIO = "+vDinRep.getInt("iEjercicio")+" and TRAREGTRAMXSOL.INUMSOLICITUD =  " +vDinRep.getInt("iNumSolicitud")+
                   " and dtcancelacion is not null ";
       Vector vcCancela = dTRARegSolicitud.findByCustom("",cSql2);
       String cSql = "";
       //String cNavStatus ="";
       String cWhere = " AND ";
       if(oAccion.getCFiltro().equals("")==true)
  	    cWhere = " where ";
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */





       if(vcCancela.size()>0){
           cSql = "SELECT * FROM TRAREGTRAMXSOL " +
                   " join TRAREGSOLICITUD on traregtramxsol.ICVETRAMITE = traregsolicitud.ICVETRAMITE " +
                   "and traregtramxsol.ICVEMODALIDAD = traregsolicitud.ICVEMODALIDAD " +
                   "and traregtramxsol.IEJERCICIO = traregsolicitud.IEJERCICIO " +
                   "and traregtramxsol.INUMSOLICITUD = traregsolicitud.INUMSOLICITUD " +
                   "where TRAREGTRAMXSOL.IEJERCICIO = "+vDinRep.getInt("iEjercicio")+" and TRAREGTRAMXSOL.INUMSOLICITUD =  " +vDinRep.getInt("iNumSolicitud")+
                   " and dtcancelacion is null ";
      }else{
           cSql =  "select distinct grldepartamento.cDscBreve,CNOMRAZONSOCIAL || ' ' || CAPPATERNO || ' ' || CAPMATERNO as cNombre, " +
                "dtLimiteEntregaDocs,cdsctramite,iejercicio,inumsolicitud,TRAREGSOLICITUD.icvetramite,TRAREGSOLICITUD.icvemodalidad,icveforma  " +
                "from TRAREGSOLICITUD " +
                        "join GRLUSUARIOXOFIC on traregsolicitud.ICVEUSUREGISTRA = grlusuarioxofic.ICVEUSUARIO " +
                "join GRLDEPARTAMENTO on grlusuarioxofic.ICVEDEPARTAMENTO = grldepartamento.ICVEDEPARTAMENTO " +
                "join GRLPERSONA on traregsolicitud.ICVESOLICITANTE = grlpersona.ICVEPERSONA " +
                "join TRACATTRAMITE on traregsolicitud.ICVETRAMITE = tracattramite.ICVETRAMITE  " +
                "join TRATRAMITEXOFIC on traregsolicitud.ICVEDEPARTAMENTO = tratramitexofic.ICVEDEPTORESUELVE  " +
                 oAccion.getCFiltro()
                 + cWhere
                 + "GRLUsuarioXOfic.iCveUsuario = " + vUsuario.getICveusuario()
                 + oAccion.getCOrden();
      }
      Vector vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",cSql);
      oAccion.navega(vcListado);
      cNavStatus = oAccion.getCNavStatus();
}
if(oAccion.getCAccion().equals("FiltroSol")){
       vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");

       //String cNavStatus ="";
      String cWhere = " AND ";
   if(oAccion.getCFiltro().equals("")==true)
  	cWhere = " where ";
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 String cSql = "select distinct grldepartamento.cDscBreve,CNOMRAZONSOCIAL || ' ' || CAPPATERNO || ' ' || CAPMATERNO as cNombre,   " +
                "dtLimiteEntregaDocs,cdsctramite,iejercicio,inumsolicitud,TRAREGSOLICITUD.icvetramite,TRAREGSOLICITUD.icvemodalidad,icveforma   "+
                "from TRAREGSOLICITUD  "+
                "join GRLUSUARIOXOFIC on traregsolicitud.ICVEUSUREGISTRA = grlusuarioxofic.ICVEUSUARIO   "+
                "join GRLDEPARTAMENTO on grlusuarioxofic.ICVEDEPARTAMENTO = grldepartamento.ICVEDEPARTAMENTO   "+
                "join GRLPERSONA on traregsolicitud.ICVESOLICITANTE = grlpersona.ICVEPERSONA   " +
                "join TRACATTRAMITE on traregsolicitud.ICVETRAMITE = tracattramite.ICVETRAMITE " +
                "join TRATRAMITEXOFIC on traregsolicitud.ICVEDEPARTAMENTO = tratramitexofic.ICVEDEPTORESUELVE    "+
                oAccion.getCFiltro()+ cWhere + "GRLUsuarioXOfic.iCveUsuario = "+vUsuario.getICveusuario()+
                " and TRAREGSOLICITUD.iNumSolicitud not in "+
                "(SELECT TRAREGSOLICITUD.iNumsolicitud FROM TRAREGTRAMXSOL  "+
                "join TRAREGSOLICITUD on traregtramxsol.ICVETRAMITE = traregsolicitud.ICVETRAMITE  "+
                "and traregtramxsol.ICVEMODALIDAD = traregsolicitud.ICVEMODALIDAD  "+
                "and traregtramxsol.IEJERCICIO = traregsolicitud.IEJERCICIO  "+
                "and traregtramxsol.INUMSOLICITUD = traregsolicitud.INUMSOLICITUD  "+
                "where TRAREGTRAMXSOL.IEJERCICIO = " + vDinRep.getInt("iEjercicio") +
                " and dtcancelacion is not null  ) ";

      System.out.println("" + cSql);
      Vector vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",cSql);
      oAccion.navega(vcListado);
      cNavStatus = oAccion.getCNavStatus();
}
if(oAccion.getCAccion().equals("FiltroEjercicio")){
       vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");

      String cWhere = " AND ";
   if(oAccion.getCFiltro().equals("")==true)
  	cWhere = " where ";
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
    String cSql =  "select distinct grldepartamento.cDscBreve,CNOMRAZONSOCIAL || ' ' || CAPPATERNO || ' ' || CAPMATERNO as cNombre,  " +
                   "dtLimiteEntregaDocs,cdsctramite,iejercicio,inumsolicitud,TRAREGSOLICITUD.icvetramite,TRAREGSOLICITUD.icvemodalidad,icveforma   "+
                   "from TRAREGSOLICITUD  "+
                   "join GRLUSUARIOXOFIC on traregsolicitud.ICVEUSUREGISTRA = grlusuarioxofic.ICVEUSUARIO  "+
                   "join GRLDEPARTAMENTO on grlusuarioxofic.ICVEDEPARTAMENTO = grldepartamento.ICVEDEPARTAMENTO  "+
                   "join GRLPERSONA on traregsolicitud.ICVESOLICITANTE = grlpersona.ICVEPERSONA  "+
                   "join TRACATTRAMITE on traregsolicitud.ICVETRAMITE = tracattramite.ICVETRAMITE "+
                   "join TRATRAMITEXOFIC on traregsolicitud.ICVEDEPARTAMENTO = tratramitexofic.ICVEDEPTORESUELVE    "+
                   oAccion.getCFiltro()+ cWhere + "GRLUsuarioXOfic.iCveUsuario = "+vUsuario.getICveusuario()+
                   " and TRAREGSOLICITUD.IEJERCICIO not in " +
                   "(SELECT TRAREGSOLICITUD.IEJERCICIO FROM TRAREGTRAMXSOL  "+
                   "join TRAREGSOLICITUD on traregtramxsol.ICVETRAMITE = traregsolicitud.ICVETRAMITE   " +
                   "and traregtramxsol.ICVEMODALIDAD = traregsolicitud.ICVEMODALIDAD  " +
                   "and traregtramxsol.IEJERCICIO = traregsolicitud.IEJERCICIO  "+
                   "and traregtramxsol.INUMSOLICITUD = traregsolicitud.INUMSOLICITUD  "+
                   "where TRAREGTRAMXSOL.INUMSOLICITUD = "+vDinRep.getInt("iNumSolicitud") +
                    " and dtcancelacion is not null)";
                    //System.out.println("" + cSql);
      Vector vcListado = dTRARegSolicitud.findByCustom("iEjercicio,iNumSolicitud",cSql);
      oAccion.navega(vcListado);
      cNavStatus = oAccion.getCNavStatus();
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
                '<%=oAccion.getBPK()%>');
</script>
<%}%>

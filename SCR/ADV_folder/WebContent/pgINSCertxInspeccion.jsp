<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgINSCertxInspeccion.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad INSCertxInspeccion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDINSCertxInspeccion dINSCertxInspeccion = new TDINSCertxInspeccion();
  TDINSInspeccion dINSInspeccion = new TDINSInspeccion();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "GuardarMatriculadas" */
  if(oAccion.getCAccion().equals("GuardarMat")){
    String cExistente = "SELECT CI.ICVEINSPPROG,CI.iNumCertificado FROM INSPROGINSP PI " +
	"JOIN INSCERTXINSPECCION CI ON CI.ICVEINSPPROG=PI.ICVEINSPPROG " +
	"WHERE PI.IEJERCICIO="+request.getParameter("iEjercicio")+
        " AND PI.INUMSOLICITUD="+request.getParameter("iNumSolicitud")+
        " AND PI.LARQMATRICULDA=1 ";
    Vector vcExistente = dINSCertxInspeccion.findByCustom("",cExistente);

//iNumSolicitud,iCveInspector,iCveVehiculo,iCveOficina,iEjercicio
    vDinRep = oAccion.setInputs("lAprovado,lAutorizado,iCveGrupoCertif,iTipoCertificado");
    TVDinRep vInsert = oAccion.setInputs("lAprovado,lAutorizado,iNumSolicitud,iCveInspector,iCveVehiculo,iCveOficina,iEjercicio,iCveGrupoCertif,iTipoCertificado");
    try{
      if(vcExistente.size()==0)vDinRep = dINSCertxInspeccion.insertMat(vInsert,null);
      if(vcExistente.size()>0){
        TVDinRep vExistente = (TVDinRep) vcExistente.get(0);
        vDinRep.put("iCveInspeccionProg",vExistente.getInt("ICVEINSPPROG"));
        vDinRep.put("iNumCertificado",vExistente.getInt("iNumCertificado"));
        vDinRep = dINSCertxInspeccion.ModificarMat(vDinRep,null);
      }
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveInspProg,iCveGrupoCertif,iTipoCertificado,lAprobada,iCveEmbarcacion,cCampos,cValores,iCveInspeccion,iCveUsuario,cConceptos,cCaracteristicas,iEjercicio,iNumSolicitud,dtIniVigencia,dtFinVigencia,iCveVehiculo,cObses");
    try{
      vDinRep = dINSCertxInspeccion.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Autorizar")){
    vDinRep = oAccion.setInputs("iCveInspProg,iNumCertificado,iCveUsuario");
    try{
      vDinRep = dINSCertxInspeccion.autorizar(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("modSolicitud")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveInspProg,iNumCertificado");
    try{
      vDinRep = dINSCertxInspeccion.modSolicitud(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("finInspeccion")){
    vDinRep = oAccion.setInputs("iCveInspProg,");
    try{
      vDinRep = dINSInspeccion.finInspeccion(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveInspProg,iCveGrupoCertif,iTipoCertificado,lAprobada,iCveEmbarcacion,cCampos,cValores,iCveInspeccion,iCveUsuario,cConceptos,cCaracteristicas,cObses,dtIniVigencia,dtFinVigencia,iConsecutivoCertExp,iCveVehiculo,cObses");
    try{
      vDinRep = dINSCertxInspeccion.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("GuardarC")){
    vDinRep = oAccion.setInputs("iCveInspeccion");
    try{
      vDinRep = dINSCertxInspeccion.update2(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("GuardarB")){
    vDinRep = oAccion.setInputs("iCveInspProg,cConjunto,cConjunto1,");
    try{
      vDinRep = dINSCertxInspeccion.Autoriza(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("Modificar")){
    vDinRep = oAccion.setInputs("iCveInspeccionProg,iNumCertificado,iCveGrupoCertif,iTipoCertificado,");
    try{
      vDinRep = dINSCertxInspeccion.Modificar(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveInspProg,iNumCertificado");
    try{
       dINSCertxInspeccion.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dINSCertxInspeccion.findByCustom("iCveInspProg,iNumCertificado",
         "Select " +
         "    CI.iCveInspProg,CI.iNumCertificado,CI.iEjercicio,CI.iNumSolicitud,CI.iCveGrupoCertif, " +
         "    CI.iTipoCertificado,CI.iCveTipoInsp,CI.lAprobada,CI.lAutorizado,GC.CDSCGRUPOCERTIF, " +
         "    TC.CDSCCERTIFICADO,TI.CDSCINSPECCION, PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO, " +
         "    dom.CCALLE||' #'|| dom.CNUMEXTERIOR||' Col. '|| dom.CCOLONIA ||'  '|| ef.CNOMBRE ||', '|| mun.CNOMBRE, " +
         "    tra.CDSCBREVE,I.LREGISTROFINALIZADO as lAprobInsp,CE.DTINIVIGENCIA,CE.DTFINVIGENCIA,CE.ICONSECUTIVOCERTEXP,ce.iCveOficina,ce.COBSES " +
         "from INSCertxInspeccion CI " +
         "JOIN INSGRUPOCERTIF GC ON CI.ICVEGRUPOCERTIF = GC.ICVEGRUPOCERTIF " +
         "JOIN INSTIPOCERTIF TC ON TC.ICVEGRUPOCERTIF = CI.ICVEGRUPOCERTIF " +
         "  AND TC.ITIPOCERTIFICADO = CI.ITIPOCERTIFICADO " +
         "JOIN INSTIPOINSP TI ON TI.ICVETIPOINSP = CI.ICVETIPOINSP " +
         "JOIN TRAREGSOLICITUD RS ON CI.IEJERCICIO = RS.IEJERCICIO " +
         "  AND RS.INUMSOLICITUD = CI.INUMSOLICITUD " +
         "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = RS.ICVESOLICITANTE " +
         "JOIN GRLDOMICILIO dom ON dom.ICVEPERSONA = per.ICVEPERSONA " +
         "  AND dom.LPREDETERMINADO = 1 " +
         "JOIN GRLENTIDADFED ef ON ef.ICVEENTIDADFED = dom.ICVEENTIDADFED " +
         "  AND ef.ICVEPAIS = dom.ICVEPAIS " +
         "JOIN GRLMUNICIPIO mun ON mun.ICVEMUNICIPIO = dom.ICVEMUNICIPIO " +
         "  AND dom.ICVEENTIDADFED = mun.ICVEENTIDADFED " +
         "  AND dom.ICVEPAIS = mun.ICVEPAIS " +
         "JOIN TRACATTRAMITE tra ON rs.ICVETRAMITE = tra.ICVETRAMITE " +
         "JOIN INSINSPECCION I ON I.ICVEINSPPROG = CI.ICVEINSPPROG "+
         "LEFT JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO=CI.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP=CI.ICONSECUTIVOCERTEXP "+
         oAccion.getCFiltro() + oAccion.getCOrden());
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

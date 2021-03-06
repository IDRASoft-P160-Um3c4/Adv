<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgSEGUsuario.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad SEGUsuario</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Leopoldo Beristain Gonz�lez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDSEGUsuario dSEGUsuario = new TDSEGUsuario();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  int lEsAdministrador=0;
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("dtRegistro,cUsuario,cPassword,cNombre,cApPaterno,cApMaterno,cCalle,cColonia,iCvePais,iCveEntidadFed,iCveMunicipio,iCodigoPostal,cTelefono,iCveUnidadOrg,lBloqueado");
    try{
      vDinRep = dSEGUsuario.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveUsuario,dtRegistro,cUsuario,cPassword,cNombre,cApPaterno,cApMaterno,cCalle,cColonia,iCvePais,iCveEntidadFed,iCveMunicipio,iCodigoPostal,cTelefono,iCveUnidadOrg,lBloqueado");
    try{
      vDinRep = dSEGUsuario.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveUsuario");
    try{
       dSEGUsuario.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
 if(oAccion.getCAccion().equals("usuarioAdministrador")){
    vDinRep = oAccion.setInputs("iCveUsuario");
    try{
      lEsAdministrador = dSEGUsuario.esAdministrador(vDinRep,null);
      vDinRep = new TVDinRep();
      vDinRep.put("lEsAdministrador",lEsAdministrador);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
 }
 else if(oAccion.getCAccion().equals("UsuarioIngADV")){
	   
	   Vector vcListado = new Vector();
	   TVDinRep vecDin = new TVDinRep();
	   
	   vecDin.put("campoX", vParametros.getPropEspecifica("usrIngADV"));
	   vecDin.put("campoY", vParametros.getPropEspecifica("pwdIngADV"));
	   
	   vcListado.addElement(vecDin);
	   oAccion.navega(vcListado);
}else if(oAccion.getCAccion().equals("sincronizaUsuariosADV")){
	try{
	   TDSEGUsuarioA objSeg = new TDSEGUsuarioA();
	   objSeg.upDateA(null, null);
	    }catch(Exception ex){
	      if(ex.getMessage().equals("")==false){
	        cError="sincro";
	      }else
	        cError="sincro";
	    }	  
}
 else{
   Vector vcListado = dSEGUsuario.findByCustom("iCveUsuario","Select iCveUsuario,dtRegistro,cUsuario,cPassword,cNombre,cApPaterno,cApMaterno,cCalle,cColonia,iCvePais,iCveEntidadFed,iCveMunicipio,iCodigoPostal,cTelefono,iCveUnidadOrg,lBloqueado,cApPaterno||' '||cApMaterno||' '||cNombre AS Usuario from SEGUsuario " + oAccion.getCFiltro() + oAccion.getCOrden());
   oAccion.navega(vcListado);
 }
 String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   out.print(oAccion.getArrayCD());
%>
  if('<%=request.getParameter("hdBoton")%>'=="usuarioAdministrador"){
    aRes[0]='<%=lEsAdministrador%>';
  }
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>');
</script>
<%}%>

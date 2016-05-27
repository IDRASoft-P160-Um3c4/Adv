<%@page import="org.apache.poi.hssf.usermodel.examples.RepeatingRowsAndColumns"%>
<%@page import="net.sf.jxls.transformer.XLSTransformer"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
<%@page import="gob.sct.sipmm.dao.reporte.TDVentanilla"%>
<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgConsulta.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad CPAAcreditacionRepLegal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDConsulta dConsulta = new TDConsulta();
  String cError = "";
  String PropEspecifica1 = "";
  String PropEspecifica2 = "";
  String PropEspecifica3 = "";
  boolean cambiarQuery=false;
  boolean personaPago=false;
  boolean verifDGDC=false;
 
  String qrySol="",qeryPerPag="",qeryVerifDGDC="";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  if(request.getParameter("hdPropEspecifica1")!=null){
     PropEspecifica1 = vParametros.getPropEspecifica(request.getParameter("hdPropEspecifica1"));
  }
  if(request.getParameter("hdPropEspecifica2")!=null){
     PropEspecifica2 = vParametros.getPropEspecifica(request.getParameter("hdPropEspecifica2"));
  }
  if(request.getParameter("hdPropEspecifica3")!=null){
     PropEspecifica3 = vParametros.getPropEspecifica(request.getParameter("hdPropEspecifica3"));
  }
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{


 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = new Vector();
  if(request.getParameter("hdSelect")!=null && request.getParameter("hdLlave")!=null && request.getParameter("cId")!=null){
    if(!request.getParameter("hdSelect").equals("")){
      try{
    	  
    	  if(request.getParameter("hdSelect").equals("BUSCA_SOLICITANTE")){
    		  
    		  qrySol= "SELECT REP.CRFC as col1, "+//0
    			  "REP.CNOMRAZONSOCIAL as col2, "+ //1
	    		  "REP.CAPPATERNO as col3, "+//2
	    		  "REP.CAPMATERNO as col4, "+//3
	    		  "DOMREP.CTELEFONO as col5, "+//4
	    		  "DOMREP.CCALLE as col6, "+//5
	    		  "DOMREP.CNUMEXTERIOR as col7, "+//6
	    		  "DOMREP.CNUMINTERIOR as col8, "+//7
	    		  "DOMREP.CCOLONIA as col9, "+//8
	    		  "DOMREP.CCODPOSTAL as col10, "+//9
	    		  "(SELECT CNOMBRE FROM GRLENTIDADFED WHERE ICVEPAIS = DOMREP.ICVEPAIS AND ICVEENTIDADFED= DOMREP.ICVEENTIDADFED) AS NOMENTREP, "+//10
	    		  "(SELECT CNOMBRE FROM GRLMUNICIPIO WHERE ICVEPAIS = DOMREP.ICVEPAIS AND ICVEENTIDADFED= DOMREP.ICVEENTIDADFED AND ICVEMUNICIPIO = DOMREP.ICVEMUNICIPIO) AS NOMMUNREP, "+//11
	    		  "EMP.CRFC, "+//12
	    		  "EMP.CNOMRAZONSOCIAL, "+//13
	    	      "EMP.CAPPATERNO, "+//14
	    	      "EMP.CAPMATERNO, "+//15
	    	   	  "DOMEMP.CTELEFONO, "+//16
	    		  "DOMEMP.CCALLE, "+//17
	    		  "DOMEMP.CNUMEXTERIOR, "+//18
	    		  "DOMEMP.CNUMINTERIOR, "+//19
	    		  "DOMEMP.CCOLONIA, "+//20
	    		  "DOMEMP.CCODPOSTAL, "+//21
	    	      "(SELECT CNOMBRE FROM GRLENTIDADFED WHERE ICVEPAIS = DOMEMP.ICVEPAIS AND ICVEENTIDADFED= DOMEMP.ICVEENTIDADFED) AS NOMENTEMP, "+//22
	    		  "(SELECT CNOMBRE FROM GRLMUNICIPIO WHERE ICVEPAIS = DOMEMP.ICVEPAIS AND ICVEENTIDADFED= DOMEMP.ICVEENTIDADFED AND ICVEMUNICIPIO = DOMEMP.ICVEMUNICIPIO) AS NOMMUNEMP, "+//23
	    		  "REP.CCORREOE AS col11, "+
	    		  "EMP.CCORREOE "+
	    	      "FROM GRLPERSONA REP "+
	    	      "JOIN GRLDOMICILIO DOMREP ON DOMREP.ICVEPERSONA = REP.ICVEPERSONA "+
	    	      "LEFT JOIN GRLREPLEGAL ON GRLREPLEGAL.ICVEPERSONA = REP.ICVEPERSONA AND GRLREPLEGAL.LPRINCIPAL=1 "+
	    	      "JOIN GRLPERSONA EMP ON GRLREPLEGAL.ICVEEMPRESA = EMP.ICVEPERSONA "+
	    	      "JOIN GRLDOMICILIO DOMEMP ON EMP.ICVEPERSONA = DOMEMP.ICVEPERSONA "+
	    		  "WHERE REP.CRFC= '"+request.getParameter("hdValorX") +"'";
    		  	   cambiarQuery=true;
    	  }
    	  else if(request.getParameter("hdSelect").equals("PERSONA_PAGO")){
        		  
    		  qeryPerPag= "SELECT "+
    			       "REGSOL.ICVESOLICITANTE, "+
    			       "SOL.CRFC AS CRFCSOL, "+
    			       "SOL.CRPA AS CRPASOL, "+
    			       "SOL.ITIPOPERSONA AS ITIPOPERSOL, "+
    			       "SOL.CNOMRAZONSOCIAL AS CNOMSOL, "+
    			       "SOL.CAPPATERNO AS CAPATSOL, "+
    			       "SOL.CAPMATERNO AS CAMATSOL, "+
    			       "SOL.CCORREOE AS CMAILSOL, "+
    			       "SOL.CPSEUDONIMOEMP AS CPSEUDSOL, "+
    			       "DOMSOL.ICVEDOMICILIO AS ICVEDOMSOL, "+
    			       "DOMSOL.ICVETIPODOMICILIO AS ITIPODOMSOL, "+
    			       "DOMSOL.CCALLE AS CALLESOL, "+
    			       "DOMSOL.CNUMEXTERIOR AS CEXTSOL, "+
    			       "DOMSOL.CNUMINTERIOR AS CINTSOL, "+
    			       "DOMSOL.CCOLONIA AS COLSOL, "+
    			       "DOMSOL.CCODPOSTAL AS CPSOL, "+
    			       "DOMSOL.CTELEFONO AS CTELSOL, "+
    			       "DOMSOL.ICVEPAIS AS IPSOL, "+
    			       "PAISSOL.CNOMBRE AS CNPSOL, "+
    			       "DOMSOL.ICVEENTIDADFED AS IENTSOL, "+
    			       "ENTSOL.CNOMBRE AS CNESOL, "+
    			       "DOMSOL.ICVEMUNICIPIO AS IMUNSOL, "+
    			       "MUNSOL.CNOMBRE AS CNMSOL, "+
    			       "DOMSOL.ICVELOCALIDAD AS ILOCSOL, "+
    			       "LOCSOL.CNOMBRE AS CNLSOL, "+
    			       "DOMSOL.LPREDETERMINADO AS LDPSOL, "+
    			       "TIPODOMSOL.CDSCTIPODOMICILIO AS CNTDSOL, "+
    			       "'CDSCDOM' AS CDSDOMSOL, "+
    			       "REGSOL.ICVETRAMITE, "+
    			       "REGSOL.ICVEMODALIDAD "+
    			"FROM TRAREGSOLICITUD REGSOL "+
    			  "JOIN GRLPERSONA SOL ON REGSOL.ICVESOLICITANTE = SOL.ICVEPERSONA "+
    			  "JOIN GRLDOMICILIO DOMSOL ON SOL.ICVEPERSONA = DOMSOL.ICVEPERSONA "+
    			  "JOIN GRLTIPODOMICILIO TIPODOMSOL ON DOMSOL.ICVETIPODOMICILIO = TIPODOMSOL.ICVETIPODOMICILIO "+
    			  "JOIN GRLPAIS PAISSOL ON DOMSOL.ICVEPAIS = PAISSOL.ICVEPAIS "+
    			  "JOIN GRLENTIDADFED ENTSOL ON DOMSOL.ICVEPAIS = ENTSOL.ICVEPAIS "+
    			    "AND DOMSOL.ICVEENTIDADFED = ENTSOL.ICVEENTIDADFED "+
    			  "JOIN GRLMUNICIPIO MUNSOL ON DOMSOL.ICVEPAIS = MUNSOL.ICVEPAIS "+
    			    "AND DOMSOL.ICVEENTIDADFED = MUNSOL.ICVEENTIDADFED "+
    			    "AND DOMSOL.ICVEMUNICIPIO = MUNSOL.ICVEMUNICIPIO "+
    			  "LEFT JOIN GRLLOCALIDAD LOCSOL ON DOMSOL.ICVEPAIS = LOCSOL.ICVEPAIS "+
    			    "AND DOMSOL.ICVEENTIDADFED = LOCSOL.ICVEENTIDADFED "+
    			    "AND DOMSOL.ICVEMUNICIPIO = LOCSOL.ICVEMUNICIPIO "+
    			    "AND DOMSOL.ICVELOCALIDAD = LOCSOL.ICVELOCALIDAD "+
    				" WHERE REGSOL.IEJERCICIO ="+request.getParameter("iEjercicio")+" AND REGSOL.INUMSOLICITUD ="+request.getParameter("iNumSolicitud");
        		  	personaPago=true;
    	  }else if(request.getParameter("hdSelect").equals("VERIF_DGDC")){
    		  
    		  qeryVerifDGDC="SELECT 0 AS ALGO FROM GRLUSUARIOXOFIC WHERE GRLUSUARIOXOFIC.ICVEUSUARIO =" + request.getParameter("iCveUsuario") + 
    				        " AND ICVEOFICINA=" + vParametros.getPropEspecifica("OFIC_DGDC"); 
    		  verifDGDC=true;
    	  }
   	  
        TVDinRep vHdSelect = oAccion.setInputs("hdSelect,hdLlave");
		
        String cSelect = "";
        
        if(cambiarQuery==true)
        	cSelect= qrySol;
        else if(personaPago==true)
        	cSelect= qeryPerPag;
        else if(verifDGDC==true)
        	cSelect= qeryVerifDGDC;
        else
        	cSelect=vHdSelect.getString("hdSelect");
		
        String cLlave = vHdSelect.getString("hdLlave");
        cSelect=cSelect.replaceAll("#hdPropEspecifica1#",PropEspecifica1).replaceAll("#hdPropEspecifica2#",PropEspecifica2).replaceAll("#hdPropEspecifica3#",PropEspecifica3);
        vcListado = dConsulta.findByCustom(cLlave,cSelect);
       // vcListado = dConsulta.findByCustom(cLlave,cSelect);
        if(request.getParameter("hdSinSize")==null)
          oAccion.setINumReg(vcListado.size());
      }catch(Exception e){
        cError = e.getMessage();
        e.printStackTrace();
      }
    }
  }
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
	if(oAccion != null)
		out.print(oAccion.getArrayCD());
%>
fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>',
                '<%=PropEspecifica1%>',
                '<%=PropEspecifica2%>',
                '<%=PropEspecifica3%>');
</script>
<%}%>

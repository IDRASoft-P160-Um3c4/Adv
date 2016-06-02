<%@page import="com.micper.util.TFechas"%>
<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  String cError = "";
  TFechas Fecha = new TFechas();
  int year = Fecha.getIntYear(Fecha.TodaySQL());
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  String esTramInt="";
  
  String queryADV ="";
  
  //System.out.print(oAccion.getCAccion());
  
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
 
  if(oAccion.getCAccion().equals("guardarDatosPermisos")){
	    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cFolConce,cFolSCT,cNomDGSCT,cFolPerm,cFolSCTPerm,cNomDGSCTPerm,cVolante,cFolPermiso,cDGDC,cNumeral,cPlazo,cParraf1,cParraf2,cParraf3,cParraf4,cParraf5,cParraf6,cArticulos,cRevDGDC,cCalMat");
	    try{
	       dTRARegSolicitud.insertDatosPermiso(vDinRep, null);
	    }catch(Exception ex){
	      if(ex.getMessage().equals("")==false){
	        cError="Cascada";
	      }else
	        cError="Borrar";
	    }
	  }
 
 
 if(oAccion.getCAccion().equals("oficioMinuta")){
	  
	  queryADV= "SELECT "+
			    " GRLOFICIOADV.ICVEOFICIO as cola, "+
			    " GRLOFICIOADV.CDSCOFICIO as colb, "+
	  			" INTTRAMITEDOCS.ICVEDOCDIG as colc "+
				" FROM INTTRAMITEDOCS "+
				" JOIN GRLOFICIOADV ON GRLOFICIOADV.ICVEOFICIO = INTTRAMITEDOCS.ICVEOFICIO "+
				//" WHERE INTTRAMITEDOCS.IEJERCICIO ="+year+ " AND INTTRAMITEDOCS.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" AND GRLOFICIOADV.ICVEOFICIO = 2"; //
				" WHERE INTTRAMITEDOCS.IEJERCICIO ="+request.getParameter("iEjercicio")+ " AND INTTRAMITEDOCS.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" AND GRLOFICIOADV.ICVEOFICIO = 2"; //
  } else 
	  if(oAccion.getCAccion().equals("requisitosEntregados")){
		queryADV="SELECT "+
		  	    " TRAREQUISITO.CDSCBREVE as cola,"+ //0
		  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
		  		" DTNOTIFICACION as colc,"+ //2
		  		" LVALIDO as cold,"+//3
		  		" LTIENEPNC as cole,"+//4
		  		" DTCOTEJO as colf,"+//5
		  		" DTEVALUACION as colg,"+//6 
		  		" INTTRAMITEDOCS.ICVEDOCDIG as colh, "+//7 
		  		" TRAREGREQXTRAM.LFISICO as coli "+//8 
		  		" FROM TRAREGREQXTRAM"+
		  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
	      " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
		  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
		  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
		  //" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and  TRAREGREQXTRAM.IEJERCICIO ="+year+" and  TRAREGREQXTRAM.DTRECEPCION IS NOT NULL ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
		  " where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and  TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+" and  TRAREGREQXTRAM.DTRECEPCION IS NOT NULL ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
		
  }else  
	  if(oAccion.getCAccion().equals("subirDocsCotejo")){
	  queryADV="SELECT "+
		  	    " TRAREQUISITO.CDSCBREVE as cola,"+ //0
		  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
		  		" DTNOTIFICACION as colc,"+ //2
		  		" LVALIDO as cold,"+//3
		  		" LTIENEPNC as cole,"+//4
		  		" DTCOTEJO as colf,"+//5
		  		" DTEVALUACION as colg,"+//6 
		  		" INTTRAMITEDOCS.ICVEDOCDIG as colh, "+//7 
		  		" TRAREGREQXTRAM.LFISICO as coli "+//8 
		  		" FROM TRAREGREQXTRAM"+
		  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
	      " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
		  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
		  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
		  " AND INTTRAMITEDOCS.ICVEESTATUS IS NULL "+
				  
			//" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and  TRAREGREQXTRAM.IEJERCICIO ="+year+" and (TRAREGREQXTRAM.DTRECEPCION IS NULL OR TRAREGREQXTRAM.DTCOTEJO IS NULL) ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";	  
		  " where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and  TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+" and (TRAREGREQXTRAM.DTRECEPCION IS NULL OR TRAREGREQXTRAM.DTCOTEJO IS NULL) ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
  }else
	  if(oAccion.getCAccion().equals("subirDocsPNCContesta")){
		  queryADV="SELECT "+
			  	    " TRAREQUISITO.CDSCBREVE as cola,"+ //0
			  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
			  		" DTNOTIFICACION as colc,"+ //2
			  		" LVALIDO as cold,"+//3
			  		" LTIENEPNC as cole,"+//4
			  		" DTCOTEJO as colf,"+//5
			  		" DTEVALUACION as colg,"+//6 
			  		" INTTRAMITEDOCS.ICVEDOCDIG as colh, "+//7
			  		" TRAREGREQXTRAM.LFISICO as coli "+//8 
			  		" FROM TRAREGREQXTRAM"+
			  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
		      " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
			  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
			  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
			  " AND INTTRAMITEDOCS.ICVEESTATUS=38" +
			  //" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and  TRAREGREQXTRAM.IEJERCICIO ="+year+
			  " where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and  TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+
			  " and  TRAREGREQXTRAM.LTIENEPNC > 0 AND TRAREGREQXTRAM.LVALIDO = 0 AND TRAREGREQXTRAM.DTCOTEJO IS NULL order by TRAREGREQXTRAM.ICVEREQUISITO";
			  //" and  TRAREGREQXTRAM.LRECNOTIFICADO > 0 AND TRAREGREQXTRAM.DTCOTEJO IS NULL order by TRAREGREQXTRAM.ICVEREQUISITO";
  } if(oAccion.getCAccion().equals("subirDocsPNC")){
	  queryADV="SELECT "+
		  	    " TRAREQUISITO.CDSCBREVE as cola,"+ //0
		  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
		  		" DTNOTIFICACION as colc,"+ //2
		  		" LVALIDO as cold,"+//3
		  		" LTIENEPNC as cole,"+//4
		  		" DTCOTEJO as colf,"+//5
		  		" DTEVALUACION as colg,"+//6 
		  		" INTTRAMITEDOCS.ICVEDOCDIG as colh,"+//7 
				" (SELECT MAX(ICONSECUTIVOPNC) FROM GRLREGISTROPNC where IEJERCICIO ="+year+" AND INUMSOLICITUD = "+request.getParameter("iNumSolicitud")+" AND LRESUELTO = 0) as colI, "+
				" TRAREGREQXTRAM.LFISICO as colj "+//9 
		  		" FROM TRAREGREQXTRAM"+
		  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
	      " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
		  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
		  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
		  " AND INTTRAMITEDOCS.ICVEESTATUS=38" +
		  //" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and  TRAREGREQXTRAM.IEJERCICIO ="+year+
		  " where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and  TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+
		  " and  TRAREGREQXTRAM.LTIENEPNC > 0 AND TRAREGREQXTRAM.LVALIDO = 0 AND TRAREGREQXTRAM.DTCOTEJO IS NULL order by TRAREGREQXTRAM.ICVEREQUISITO";
		  //" and  TRAREGREQXTRAM.LRECNOTIFICADO > 0 AND TRAREGREQXTRAM.DTCOTEJO IS NULL order by TRAREGREQXTRAM.ICVEREQUISITO";
}
	  else  
	  if(oAccion.getCAccion().equals("verDocsCotejo")){ 
	  queryADV="SELECT "+
		  	    " TRAREQUISITO.CDSCBREVE as cola,"+ //0
		  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
		  		" DTNOTIFICACION as colc,"+ //2
		  		" LVALIDO as cold,"+//3
		  		" LTIENEPNC as cole,"+//4
		  		" DTCOTEJO as colf,"+//5
		  		" DTEVALUACION as colg,"+//6 
		  		" INTTRAMITEDOCS.ICVEDOCDIG as colh, "+//7
		  		" TRAREGREQXTRAM.LFISICO as coli "+//8 
		  		" FROM TRAREGREQXTRAM"+
		  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
	      " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
		  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
		  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
		  //" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+year+
		  " where TRAREGREQXTRAM.INUMSOLICITUD ="+ request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+
		  " and INTTRAMITEDOCS.ICVEESTATUS is null ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
		  //+" and  TRAREGREQXTRAM.DTRECEPCION IS NOT NULL and INTTRAMITEDOCS.ICVEESTATUS is null ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
  }else  
	  if(oAccion.getCAccion().equals("verDocsDpto")){ 
	  queryADV="SELECT "+
		  	    " TRAREQUISITO.CDSCBREVE as cola,"+ //0
		  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
		  		" DTNOTIFICACION as colc,"+ //2
		  		" LVALIDO as cold,"+//3
		  		" LTIENEPNC as cole,"+//4
		  		" DTCOTEJO as colf,"+//5
		  		" DTEVALUACION as colg,"+//6 
		  		" INTTRAMITEDOCS.ICVEDOCDIG as colh, "+//7 
		  		" TRAREGREQXTRAM.LFISICO as coli "+//8 
		  		" FROM TRAREGREQXTRAM"+
		  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
	      " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
		  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
		  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
		  //" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+year+
		  " where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+
		  " and ICVEOFICINAEVAL = "+ request.getParameter("iCveOficina") +" and ICVEDEPTOEVAL= "+request.getParameter("iCveDpto")+
		  " and INTTRAMITEDOCS.ICVEESTATUS is null ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
  }else
	  if(oAccion.getCAccion().equals("verDocsCotejoPNC")){ 
	  queryADV="SELECT "+
		  	    " TRAREQUISITO.CDSCBREVE as cola,"+ //0
		  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
		  		" DTNOTIFICACION as colc,"+ //2
		  		" LVALIDO as cold,"+//3
		  		" LTIENEPNC as cole,"+//4
		  		" DTCOTEJO as colf,"+//5
		  		" DTEVALUACION as colg,"+//6 
		  		" INTTRAMITEDOCS.ICVEDOCDIG as colh, "+//7
		  		" TRAREGREQXTRAM.LFISICO as coli "+//8 
		  		" FROM TRAREGREQXTRAM"+
		  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
	      " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
		  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
		  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
		  //" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+year+
		  " where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+
		  "  AND INTTRAMITEDOCS.ICVEESTATUS = 38 ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
		  //+" and  TRAREGREQXTRAM.DTRECEPCION IS NOT NULL and INTTRAMITEDOCS.ICVEESTATUS is null ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
}else
	  if(oAccion.getCAccion().equals("verDocsDptoCotejoPNC")){ 
	  queryADV="SELECT "+
		  	    " TRAREQUISITO.CDSCBREVE as cola,"+ //0
		  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
		  		" DTNOTIFICACION as colc,"+ //2
		  		" LVALIDO as cold,"+//3
		  		" LTIENEPNC as cole,"+//4
		  		" DTCOTEJO as colf,"+//5
		  		" DTEVALUACION as colg,"+//6 
		  		" INTTRAMITEDOCS.ICVEDOCDIG as colh, "+//7
		  		" TRAREGREQXTRAM.LFISICO as coli "+//8 
		  		" FROM TRAREGREQXTRAM"+
		  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
	      " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
		  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
		  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
		  //" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+year+
		  " where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+
		  " and ICVEOFICINAEVAL = "+ request.getParameter("iCveOficina") +" and ICVEDEPTOEVAL= "+request.getParameter("iCveDpto")+
		  "  AND INTTRAMITEDOCS.ICVEESTATUS = 38 ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
}else 
	if(oAccion.getCAccion().equals("verDocsOficios2016")){
		 queryADV= "SELECT "
				 +"OFI.CDSCOFICIO as colA, " 
				 +"REGOF.IIDGESTORDOCUMENTO as colB " 
				 +"FROM TRAREGOFICIOADV REGOF "
				 +"JOIN GRLOFICIOADV OFI ON OFI.ICVEOFICIO = REGOF.ICVEOFICIOADV "
				 +"WHERE REGOF.IEJERCICIO = "+request.getParameter("iEjercicio")+ " AND REGOF.INUMSOLICITUD = "+request.getParameter("iNumSolicitud"); //
}else 
	if(oAccion.getCAccion().equals("verDocsOficios")){
	 queryADV= "SELECT "+
			    " GRLOFICIOADV.ICVEOFICIO as cola, "+
			    " GRLOFICIOADV.CDSCOFICIO as colb, "+
	  			" INTTRAMITEDOCS.ICVEDOCDIG as colc "+
				" FROM GRLOFICIOADV"+
				" LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.ICVEOFICIO=GRLOFICIOADV.ICVEOFICIO"+
				//" WHERE INTTRAMITEDOCS.IEJERCICIO ="+year+ " AND INTTRAMITEDOCS.INUMSOLICITUD ="+request.getParameter("iNumSolicitud"); //
				" WHERE INTTRAMITEDOCS.IEJERCICIO ="+request.getParameter("iEjercicio")+ " AND INTTRAMITEDOCS.INUMSOLICITUD ="+request.getParameter("iNumSolicitud"); //
				
 	 Vector vec=new Vector();
	 vec = dTRARegSolicitud.findByCustom("", "SELECT LTRAMINERNET FROM TRAREGSOLICITUD WHERE INUMSOLICITUD="+request.getParameter("iNumSolicitud")+
			 								 " AND IEJERCICIO="+request.getParameter("iEjercicio"));
			 								//" AND IEJERCICIO="+year);
	 
	 TVDinRep vd= new TVDinRep();
	 
	 vd=(TVDinRep) vec.get(0);
	 
	 if(vd.getInt("LTRAMINERNET")>0)
		 esTramInt="esTramInt";

	}
	else 
		if(oAccion.getCAccion().equals("oficioSeguimiento")){
		 queryADV= "SELECT "+
				    " GRLOFICIOADV.ICVEOFICIO as cola, "+
				    " GRLOFICIOADV.CDSCOFICIO as colb, "+
		  			" INTTRAMITEDOCS.ICVEDOCDIG as colc "+
					" FROM GRLOFICIOADV"+
					" LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.ICVEOFICIO=GRLOFICIOADV.ICVEOFICIO"+
					//" WHERE INTTRAMITEDOCS.IEJERCICIO ="+year+ " AND INTTRAMITEDOCS.INUMSOLICITUD ="+request.getParameter("iNumSolicitud") 
					" WHERE INTTRAMITEDOCS.IEJERCICIO ="+request.getParameter("iEjercicio")+ " AND INTTRAMITEDOCS.INUMSOLICITUD ="+request.getParameter("iNumSolicitud") 
					+" AND GRLOFICIOADV.ICVEOFICIO="+request.getParameter("iCveOfiSeg"); //
	}else
		 if(oAccion.getCAccion().equals("docsContestaNotif")){ 
		 queryADV="SELECT DISTINCT(TRAREQUISITO.CDSCBREVE) as cola,"+ //0
			  		" TRAREGREQXTRAM.ICVEREQUISITO as colb,"+ //1
			  		" DTNOTIFICACION as colc,"+ //2
			  		" LVALIDO as cold,"+//3
			  		" LTIENEPNC as cole,"+//4
			  		" DTCOTEJO as colf,"+//5
			  		" DTEVALUACION as colg,"+//6 
			  		" INTTRAMITEDOCS.ICVEDOCDIG as colh, "+//7 
			  		" TRAREGREQXTRAM.LFISICO as coli "+//8 
			  		" FROM TRAREGREQXTRAM"+
			  " JOIN TRAREQUISITO ON TRAREQUISITO.ICVEREQUISITO=TRAREGREQXTRAM.ICVEREQUISITO"+
		     " LEFT JOIN INTTRAMITEDOCS ON INTTRAMITEDOCS.INUMSOLICITUD = TRAREGREQXTRAM.INUMSOLICITUD"+ 
			  " AND INTTRAMITEDOCS.IEJERCICIO=TRAREGREQXTRAM.IEJERCICIO"+
			  "	AND TRAREGREQXTRAM.ICVEREQUISITO = INTTRAMITEDOCS.ICVEREQUISITO"+
			  //" where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+year+
			  " where TRAREGREQXTRAM.INUMSOLICITUD ="+request.getParameter("iNumSolicitud")+" and TRAREGREQXTRAM.IEJERCICIO ="+request.getParameter("iEjercicio")+
			  "  AND TRAREGREQXTRAM.LTIENEPNC = 1 ORDER BY TRAREGREQXTRAM.ICVEREQUISITO";
	}else
		 if(oAccion.getCAccion().equals("docHistorico")){ 
		 queryADV="SELECT ICVEDOCHIST, cnomarch FROM TRADOCHIST where ICVEDOCHIST  = (select max(ICVEDOCHIST) from TRADOCHIST where icvehistadv ="+request.getParameter("iCveHistADV")+" )";
	}else if(oAccion.getCAccion().equals("buscarDatosPermisos")||oAccion.getCAccion().equals("guardarDatosPermisos")){

			queryADV="SELECT FOLS.CFOLCONCE, "+ //0
						  "FOLS.CFOLSCT, "+
						  "FOLS.CFOLPERM, "+
						  "FOLS.CFOLSCTPERM, "+
						  "FOLS.CNOMDGSCT, "+
						  "FOLS.CNOMDGSCTPERM, "+ 
						  "PERM.CFOLVOLANTE, "+
						  "PERM.CFOLPERMISO, "+
						  "PERM.CDGDC, "+
						  "PERM.CNUMERAL, "+ 
						  "PERM.CPARRAF_1, "+//10
						  "PERM.CPARRAF_2, "+
						  "PERM.CPARRAF_3, "+
						  "PERM.CPARRAF_4, "+
						  "PERM.CPARRAF_5, "+
						  "PERM.CPARRAF_6, "+
						  "PERM.CARTICULOS, "+
						  "PERM.CREVDGDC, "+
						  "PERM.CPLAZO, "+
						  "PERM.CCALMAT "+//19 
					"FROM TRAREGSOLICITUD SOL "+
					"JOIN TRAFOLENVIOS FOLS ON FOLS.IEJERCICIO =  SOL.IEJERCICIO AND FOLS.INUMSOLICITUD = SOL.INUMSOLICITUD "+
					"JOIN TRADATOSPERM PERM ON PERM.IEJERCICIO =  SOL.IEJERCICIO AND PERM.INUMSOLICITUD = SOL.INUMSOLICITUD "+
					"WHERE SOL.IEJERCICIO = "+request.getParameter("iEjercicio")+" AND SOL.INUMSOLICITUD =" + request.getParameter("iNumSolicitud");		
	}
	else if(oAccion.getCAccion().equals("buscaReqInternet")){
		queryADV="SELECT "+
			       "TRAREQXMODTRAMITE.ICVETRAMITE, "+
			       "TRAREQXMODTRAMITE.ICVEREQUISITO, "+
			       "CDSCREQUISITO, "+
			       "TRAREQXMODTRAMITE.IORDEN "+
			"FROM TRAREQXMODTRAMITE "+
			  "JOIN TRAREQUISITO ON TRAREQXMODTRAMITE.ICVEREQUISITO = TRAREQUISITO.ICVEREQUISITO "+
			  "JOIN TRACATTRAMITE ON TRAREQXMODTRAMITE.ICVETRAMITE = TRACATTRAMITE.ICVETRAMITE "+
			  "JOIN TRAMODALIDAD ON TRAREQXMODTRAMITE.ICVEMODALIDAD = TRAMODALIDAD.ICVEMODALIDAD "+
			  "WHERE TRAREQXMODTRAMITE.ICVETRAMITE ="+request.getParameter("iCveTramite")+
			    " AND TRAREQXMODTRAMITE.ICVEMODALIDAD ="+request.getParameter("iCveModalidad")+
			  " ORDER by TRAREQXMODTRAMITE.IORDEN";
	}
   
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTRARegSolicitud.findByCustom("",queryADV);

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
                '',
                '',
                '<%=esTramInt%>');
</script>
<%}%>
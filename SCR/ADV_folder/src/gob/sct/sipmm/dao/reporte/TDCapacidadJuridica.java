package gob.sct.sipmm.dao.reporte;

import gob.sct.sipmm.docDOM4j.IntegracionDOM4jDoc;
import java.io.File;
import java.sql.*;
import java.util.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDOficiosConcesiones.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Ing. Aturo Lopez Peña
 * @author Ing. Sergio Gonzalez Arcos
 * @version 2.0
 */
public class TDCapacidadJuridica extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private String cMexico = VParametros.getPropEspecifica("EntidadRemplazaTexto");
  String[] cMex = cMexico.split(",");
  //TParametro  vParametros = new TParametro("44");
  //private static final String  NOMBRE_REPORTE = "AgenteNavieroGralPerFisica";
    
  public TDCapacidadJuridica(){
  }
    
  public StringBuffer WordOficio(String cQuery){
	  //System.out.print("************** WORD OFICIO *******************");	  
	  IntegracionDOM4jDoc integracion = new IntegracionDOM4jDoc();
	  String plantilla = "";

	  String rutaPlantilla = VParametros.getPropEspecifica("nasPlantillas");
	  String archivoFinal = "";
	  
	  String rutaArchivoFinal = VParametros.getPropEspecifica("nasSIPYMM");
		
	  File dir = new File(VParametros.getPropEspecifica("nasSIPYMM"));	  
	  String[] ficheros = dir.list();
	  
	  /*int i=ficheros.length;	  
	  //System.out.print("::::::Numero de Ficheros:::::" + i);
	  String[] noticias = new String[i];	  
	  if (ficheros == null){		  
	   //System.out.print("No hay ficheros en el directorio especificado");
	  }	  
	  else {		  
	   for (int x=0;x<ficheros.length;x++){
	     //System.out.print(":::::fich::::::" + ficheros[x]);
	     noticias[x]=ficheros[x];
	   }
     }*/
	  	  
	  HashMap<String, String> param = new HashMap<String, String>();						  
	  String cFirmante = "";
	  
	  //System.out.print("*****     "+cFirmante);
	  Vector vcCuerpo = new Vector();
	  Vector vcCopiasPara = new Vector();
	  Vector vRegs = new Vector();
	  Vector vRegs1 = new Vector();
	  Vector vRegs2 = new Vector();
	  TWord rep = new TWord();
	  int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0;
	  String cAsunto="";
	  TFechas fecha1 = new TFechas();

	  String cRazonSocial="",cDomicilio="",cCodigoPost="",cEntiFed="",
	         cRFC="",cCiudadOficioReg="",cPuertoConsigna="",cPuertoResid="";
	  String dtReconosimiento="",cRepresentante="",cDirector="",cNotaria="",
	      cNomNotario="",cPoder="",cColonia="",cMunicipio="",
	      cMandante="",cTipoPersona="",cOficinaAdscrita="",cNumSolicitud="",
	      cHolograma="";
	  String[] cParametros = cQuery.split(",");
	  String cFolioRPMN="";
	  int iFolioRPMN=0, iPartida=0,lNavAltura=0,lNavCabotaje=0;

	  int tamanio =cParametros.length;
	  
	  // Integracion del nombre de la plantilla
	  plantilla = cParametros[tamanio - 1] + ".xml";	  
	  
	  try{
	    vRegs = super.FindByGeneric("",
	         "SELECT " +
	         "CJ.IFOLIORPMN, " +
	         "CJ.IEJERCICIOINS, " +
	         "OFI.CSIGLASOFICINA, " +
	         "CJ.IPARTIDA, " +
	         "PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CRAZONSOCIAL, " +
	         "DOM.CCALLE||' '|| DOM.CNUMEXTERIOR||' '|| DOM.CNUMINTERIOR AS CDOMICILIO, " +
	         "DOM.CCODPOSTAL, " +
	         "EF.CNOMBRE AS CENTIDADFED, " +
	         "PER.CRFC, " +
	         "EFO.CNOMBRE AS CCIUDADOFICREG, " +
	         "PC.CDSCPUERTO AS CPUERTOCONSIGNA, " +
	         "CJ.LNAVEGALTURA, " +
	         "CJ.LNAVEGCABOTAJE, " +
	         "PR.CDSCPUERTO AS CPUERTORESID, " +
	         "CJ.DTRECONOCIMIENTO, " +
	         "REP.CNOMRAZONSOCIAL||' '|| REP.CAPPATERNO||' '|| REP.CAPMATERNO AS CREPRESENTANTE, " +
	         "RS.IEJERCICIO, " +
	         "CJ.INUMSOLICITUD, " +
	         "CJ.CPODER, " +
	         "CJ.CNOTARIA, " +
	         "CJ.CNOMNOTARIO, " +
	         "DOM.CCOLONIA, " +
	         "MUN.CNOMBRE AS CMUNICIPIO, " +
	         "EF.ICVEENTIDADFED, " +
	         "PER.ITIPOPERSONA, " +
	         "OFA.CDSCBREVE AS COFICINAADSCRITA, " +
	         "CJ.IHOLOGRAMA, " +
	         "CJ.IEJERCICIOINS as cEjercicioIns, "+
	         "CJ.cMandantes, "+
	         "date(RS.TSREGISTRO) as dtRegistro, "+
	         "cGarantia,cDirOperacion "+
	         "FROM MYRCAPACIDADJUR CJ " +
	         "  LEFT JOIN TRAREGSOLICITUD RS ON CJ.INUMSOLICITUD= RS.INUMSOLICITUD " +
	         "    AND CJ.IEJERCICIOSOLICITUD = RS.IEJERCICIO " +
	         "  LEFT JOIN GRLPERSONA PER ON PER.ICVEPERSONA = RS.ICVESOLICITANTE " +
	         "  LEFT JOIN GRLDOMICILIO DOM ON DOM.ICVEPERSONA = PER.ICVEPERSONA " +
	         "    AND DOM.ICVEDOMICILIO =  RS.ICVEDOMICILIOSOLICITANTE " +
	         "  LEFT JOIN GRLPAIS PAI ON PAI.ICVEPAIS = DOM.ICVEPAIS " +
	         "  LEFT JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = DOM.ICVEPAIS " +
	         "    AND EF.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
	         "  LEFT JOIN GRLMUNICIPIO MUN ON MUN.ICVEPAIS = DOM.ICVEPAIS " +
	         "    AND MUN.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
	         "    AND MUN.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO " +
	         "  LEFT JOIN GRLOFICINA OF ON OF.ICVEOFICINA = CJ.ICVEOFICINARPMN " +
	         "  LEFT JOIN GRLENTIDADFED EFO ON EFO.ICVEENTIDADFED = OF.ICVEENTIDADFED " +
	         "    AND EFO.ICVEPAIS = OF.ICVEPAIS " +
	         "  LEFT JOIN GRLPUERTO PC ON PC.ICVEPUERTO = CJ.ICVEPUERTOCONSIGNA " +
	         "  LEFT JOIN GRLPUERTO PR ON PR.ICVEPUERTO = CJ.ICVEPUERTORESIDENCIA " +
	         "  LEFT JOIN GRLPERSONA REP ON RS.ICVEREPLEGAL = REP.ICVEPERSONA " +
	         "  LEFT JOIN GRLOFICINA OFA ON OFA.ICVEOFICINA = PC.ICVEOFICINAADSCRITA " +
	         "  LEFT JOIN MYRCAPITANIA OFI ON OFI.ICVEOFICINA = CJ.ICVEOFICINARPMN " +
	         " WHERE CJ.ICONSECRECONOCIM ="+cParametros[0], dataSourceName);
	  }catch(SQLException ex){
	    cMensaje = ex.getMessage();
	  }catch(Exception ex2){
	    ex2.printStackTrace();
	  }
	  try{
	    vRegs1 = super.FindByGeneric("",
	        "SELECT do.CTITULAR FROM GRLDEPTOXOFIC do Join GRLOFICINA O on O.ICVEOFICINA = do.ICVEOFICINA join GRLDEPARTAMENTO d on d.ICVEDEPARTAMENTO = do.ICVEDEPARTAMENTO Where do.ICVEDEPARTAMENTO = 5 and do.ICVEOFICINA=1 ",dataSourceName);
	  }catch(SQLException ex){
	    cMensaje = ex.getMessage();
	  }catch(Exception ex2){
	    ex2.printStackTrace();
	  }
	if(vRegs1.size()>0){
	    TVDinRep vDirector = (TVDinRep) vRegs1.get(0);
	    cDirector=vDirector.getString("cTitular");
	    	    
	    //rep.comRemplaza("[cNombreDirector]",cDirector);
	    param.put("cNombreDirector", cDirector);
	  }
	
	  // imprimios parametros del arreglo cParametros
	  for (int countx = 0; countx < cParametros.length; countx++) {		  
			String valor = cParametros[countx];		
			//System.out.print(">>>>>>>>>>::::::::::Parametro:" + countx + ":: valor :: " + valor);
	  }
	
	  rep.iniciaReporte();
	  //System.out.print("*****  Tamaño del vector  "+vRegs.size());

	  if (vRegs.size() > 0){

	    TVDinRep vDatos = (TVDinRep) vRegs.get(0);
	    TVDinRep vDirector = (TVDinRep) vRegs1.get(0);
	    
	    //rep.comRemplaza("[cNombreAgencia]",vDatos.getString("CRAZONSOCIAL").trim());
	    param.put("cNombreAgencia", vDatos.getString("CRAZONSOCIAL").trim());
	    
	    //rep.comRemplaza("[cNombreDirector]",vDirector.getString("cTitular").trim());
	    param.put("cNombreDirector", vDirector.getString("cTitular").trim());
	    
	    //rep.comRemplaza("[cNombreSolEnc]",vDatos.getString("CRAZONSOCIAL").trim());
	    param.put("cNombreSolEnc", vDatos.getString("CRAZONSOCIAL").trim());
	    
	    //rep.comRemplaza("[cNombreSol]",vDatos.getString("CRAZONSOCIAL").trim());
	    param.put("cNombreSol", vDatos.getString("CRAZONSOCIAL").trim());

	    cDomicilio=vDatos.getString("CDOMICILIO").trim();
	    cCodigoPost=vDatos.getString("CCODPOSTAL").trim();
	    cEntiFed=vDatos.getString("CENTIDADFED").trim();
	    if(Integer.parseInt(vDatos.getString("ICVEENTIDADFED")) == Integer.parseInt(cMex[1]))
	      cMunicipio = "" + cMex[2];
	    else cMunicipio=vDatos.getString("cMunicipio");
	    cColonia=vDatos.getString("cColonia");
	    cRFC=vDatos.getString("CRFC");
	    cTipoPersona=vDatos.getString("iTipoPersona");

	    cCiudadOficioReg=vDatos.getString("cCIUDADOFICREG");
	    cPuertoConsigna=vDatos.getString("cPUERTOCONSIGNA");
	    cPuertoResid=vDatos.getString("cPUERTORESID");
	    dtReconosimiento=vDatos.getString("DTRECONOCIMIENTO");
	    cRepresentante=vDatos.getString("CREPRESENTANTE");
	    cHolograma = vDatos.getString("iHolograma");
	    if(!vDatos.getString("cEjercicioIns").equals("null")){
	      iFolioRPMN = vDatos.getInt("IFOLIORPMN");
	      if(iFolioRPMN > 0 && iFolioRPMN < 10) cFolioRPMN = "000" + iFolioRPMN;
	      if(iFolioRPMN > 9 && iFolioRPMN < 100) cFolioRPMN = "00" + iFolioRPMN;
	      if(iFolioRPMN > 99 && iFolioRPMN < 1000) cFolioRPMN = "0" + iFolioRPMN;
	      if(iFolioRPMN > 999) cFolioRPMN = "" + iFolioRPMN;
	      cFolioRPMN = cFolioRPMN + "-" + vDatos.getString("CSIGLASOFICINA") +
	          "-" + vDatos.getString("IEJERCICIOINS").subSequence(2,4);
	    }
	    iPartida=vDatos.getInt("IPARTIDA");
	    lNavAltura=vDatos.getInt("LNAVEGALTURA");
	    lNavCabotaje=vDatos.getInt("LNAVEGCABOTAJE");
	    cNumSolicitud=vDatos.getInt("iEjercicio") + "/" + vDatos.getInt("INUMSOLICITUD");
	    
	    //Nombre del Archivo final  2011_376  ejemplo: año_noSoliccitud 
	    archivoFinal = String.valueOf(vDatos.getInt("iEjercicio")) + "_" + 
	                   String.valueOf(vDatos.getInt("INUMSOLICITUD") + ".doc");
	    	   
	    cNotaria=vDatos.getString("cNotaria");
	    cPoder=vDatos.getString("cPoder");
	    cNomNotario=vDatos.getString("cNomNotario");
	    cOficinaAdscrita=vDatos.getString("cOficinaAdscrita");

	    cMandante = vDatos.getString("cMandantes");

	    if(Integer.parseInt(cTipoPersona)==1){
	      //rep.comRemplaza("[cAgencia]","AGENTE: ");
	      param.put("cAgencia", "AGENTE: ");
	    }else {
	      //rep.comRemplaza("[cNombreSol]","la empresa " + cRazonSocial);
	      param.put("cNombreSol", "la empresa " + cRazonSocial);	      
	      param.put("cNomRepL", "REPR. LEGAL: " + cRepresentante);	      	     
	      param.put("cAgencia", "AGENCIA: ");
	    }
	    
	    //rep.comRemplaza("[DomicilioSol]",cDomicilio);
	    param.put("DomicilioSol",cDomicilio);
	    
	    //rep.comRemplaza("[CodPosSol]","C.P.  "+cCodigoPost);
	    param.put("CodPosSol",""+cCodigoPost);
	    
	    param.put("MunSolyEntidFedSol", cMunicipio + ", " + cEntiFed);
	    
	    //param.put("EntidFedSol", cEntiFed);
	    	    
	    param.put("MunSol", cMunicipio);
	    
	    //rep.comRemplaza("[RFCSol]",cRFC);
	    param.put("RFCSol", cRFC);
	    
	    if(Integer.parseInt(cTipoPersona)!=1) //rep.comRemplaza("[cNomRepL]","Nombre del Rep. Legal:  "+cRepresentante);  	    	
	          param.put("cNomRepL", "Nombre del Rep. Legal:  "+cRepresentante);
	    
	    else //rep.comRemplaza("[cNomRepL]","");
	       param.put("cNomRepL", "");
	    	    
	    //rep.comRemplaza("[cNombreMandante]",cMandante);
	    param.put("cNombreMandante", cMandante);

	    //rep.comRemplaza("[cNomCesionaria]",cCiudadOficioReg);
	    param.put("cNomCesionaria", cCiudadOficioReg);
	    
	    //rep.comRemplaza("[cPuertoConsigna]",cPuertoConsigna);
	    param.put("cPuertoConsigna", cPuertoConsigna);

	    //rep.comRemplaza("[cPuertoResidencia]",cPuertoResid);
	    param.put("cPuertoResidencia", cPuertoResid);
	    
	    //rep.comRemplaza("[dtReconocimiento]",dtReconosimiento);
	    param.put("dtReconocimiento", dtReconosimiento);
	    
	    //rep.comRemplaza("[cNomCesionaria]",cRepresentante);
	    param.put("cNomCesionaria", cRepresentante);
	    
	    //rep.comRemplaza("[cColonia]",cColonia);//[cCapitania]cOficinaAdscrita
	    param.put("cColonia", cColonia);
	    
	    //rep.comRemplaza("[cCapitania]",cOficinaAdscrita);
	    param.put("cCapitania", cOficinaAdscrita);
	    
	    //rep.comRemplaza("[Folio]",""+cFolioRPMN);
	    param.put("Folio", ""+cFolioRPMN);
	    
	    //rep.comRemplaza("[Partida]",""+iPartida);//iNumSolicitud
	    param.put("Partida",""+iPartida);
	    
	    //rep.comRemplaza("[iNumSolicitud]",""+cNumSolicitud);
	    param.put("iNumSolicitud",""+cNumSolicitud);
	    
	    //rep.comRemplaza("[iHolograma]",cHolograma);
	    param.put("iHolograma", cHolograma);
	    
	    //rep.comRemplaza("[IEJERCICIO]",vDatos.getString("IEJERCICIO"));
	    param.put("IEJERCICIO", vDatos.getString("IEJERCICIO"));
	    
	    //rep.comRemplaza("[INUMSOLICITUD]",vDatos.getString("INUMSOLICITUD"));
	    param.put("INUMSOLICITUD", vDatos.getString("INUMSOLICITUD"));
	    
	    //rep.comRemplaza("[CPODER]",vDatos.getString("CPODER"));
	    param.put("CPODER", vDatos.getString("CPODER"));
	    
	    //rep.comRemplaza("[dtRegistro]",fecha1.getDateSPN(vDatos.getDate("dtRegistro")));
	    param.put("dtRegistro", fecha1.getDateSPN(vDatos.getDate("dtRegistro")));
	    
	    //rep.comRemplaza("[CNOMNOTARIO]",vDatos.getString("CNOMNOTARIO"));
	    param.put("CNOMNOTARIO",vDatos.getString("CNOMNOTARIO"));
	    
	    //rep.comRemplaza("[CNOTARIA]",vDatos.getString("CNOTARIA"));
	    param.put("CNOTARIA", vDatos.getString("CNOTARIA"));

	    //rep.comRemplaza("[cGarantia]",vDatos.getString("cGarantia"));
	    param.put("cGarantia", vDatos.getString("cGarantia"));
	    
	    //rep.comRemplaza("[cDirOperacion]",vDatos.getString("cDirOperacion"));
	    param.put("cDirOperacion", vDatos.getString("cDirOperacion"));
	    
	    //rep.comRemplaza("[cFirmante]",cFirmante);
	    param.put("cFirmante", cFirmante);
	    
	    //rep.comRemplaza("[iConsecReconocim]",cParametros[0]);
		Formatter fmt = new Formatter();
		fmt.format("%03d",Integer.parseInt(cParametros[0])); 
	     param.put("iConsecReconocim", fmt.toString());

	    if(Integer.parseInt(cTipoPersona)!=1)  param.put("cNotaria"," NOTARIO Nº "+cNotaria); else param.put("cNotaria","");
	    if(Integer.parseInt(cTipoPersona)!=1)  param.put("cPoder",""+"PODER Nº  "+cPoder); else param.put("cPoder","");
	    
	    if(Integer.parseInt(cTipoPersona)!=1)
	    	param.put("cNomNotario",""+cNomNotario);
	    	//rep.comRemplaza("[cNomNotario]",""+cNomNotario); 
	    
	     else 	    	 
	    	 param.put("cNomNotario","");
	    
	    if(lNavAltura==1 && lNavCabotaje==0)  
	    	param.put("cTipoNavega", " de altura ");
	        //rep.comRemplaza("[cTipoNavega]"," de altura");
	    
	    if(lNavAltura==0 && lNavCabotaje==1)  
	    	param.put("cTipoNavega", " de cabotaje ");
	    	//rep.comRemplaza("[cTipoNavega]"," de cabotaje ");
	    
	    if(lNavAltura==1 && lNavCabotaje==1)  
	    	param.put("cTipoNavega", " de altura y cabotaje ");
	    	//rep.comRemplaza("[cTipoNavega]"," de altura y cabotaje ");
		
		try {
			//System.out.print(":::: Parametros Antes Reporte ::::" + "platilla " + rutaPlantilla + plantilla + "Archivo Final " + rutaArchivoFinal + archivoFinal);
			//System.out.print("::Resultado de llenado Plantilla Agente Naviero::" + integracion.generaDocSustText(plantilla, rutaPlantilla, archivoFinal, rutaArchivoFinal, param));			
		} catch (Exception e) {		
			e.printStackTrace();
		}		
	  	  	 
       }
	  
	  return rep.getEtiquetas(true);  
   }
}

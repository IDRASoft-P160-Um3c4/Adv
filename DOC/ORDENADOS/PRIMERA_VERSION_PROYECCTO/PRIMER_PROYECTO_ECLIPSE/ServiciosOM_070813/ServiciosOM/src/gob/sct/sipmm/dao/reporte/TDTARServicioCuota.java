package gob.sct.sipmm.dao.reporte;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import com.micper.excepciones.DAOException;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.DAOBase;
import com.micper.util.TExcel;
import com.micper.util.TFechas;

/**
 * <p>Title: TDTARServicioCuota.java</p>
 * <p>Description: DAO con métodos para reportes de OBSERVACIONES POA</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ALopez
 * @author amendoza
 * @version 1.0
*
 */

public class TDTARServicioCuota extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
//  TDTARServicioCuota servicioCuotaDAO = new TDTARServicioCuota();

  TExcel rep = new TExcel();

  public TDTARServicioCuota(){
  }
  public Vector findByCustom(String cKey,String cWhere) throws DAOException{
   Vector vcRecords = new Vector();
   cError = "";
   try{
     String cSQL = cWhere;
     vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
   } catch(Exception e){
     cError = e.toString();
   } finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
   }
   return vcRecords;

  }

  /**
   * 1- Pilotaje
   * @param cFiltro
   * @return
   * @throws Exception
   */
	public StringBuffer fReportePilotaje(String cFiltro) throws Exception{
		System.out.println("*************** fReporteServPortPilotaje");
	    cError = "";
	    TFechas dFechas = new TFechas();
	    StringBuffer sbRetorno = new StringBuffer("");
	      try{
	          rep.iniciaReporte();
	          
	          DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX"));
	          String cFechaFormated = format.format(new Date()).toUpperCase()+"  ";
	          rep.comDespliegaAlineado("J",5,"VIGENCIA AL: "+cFechaFormated, false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());

	          Vector vcData = this.consultaServicio(cFiltro," ORDER BY sc.ICVEPUERTO ");          
	
	            if(vcData.size()>0){            	
	            	
	            	String cPuertoAnterior = "";
	            	int iRenglon=11;	            	
	            	int iConsecutivo = 1;	
	            	int iRenglonIni = iRenglon;
	            	int iColorBlanco = 2;
	            	int iColorFondo = 6;
	            	
	            	//TEST
//	            	for (int i = 0; i < 20; i++, iRenglon+=5) {
//	            		rep.comDespliegaAlineado("B",iRenglon,i+"",	false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
//	            		rep.comBordeRededor("B",iRenglon,"B",iRenglon+3,i,1);
//	            		rep.comBordeTotal("D",iRenglon,"E",iRenglon+3,i);
//	            		rep.comFillCells("B", iRenglon, "B", iRenglon, i);
//	            	}
//	            	
	            	for (int i = 0; i < vcData.size(); i++, iRenglon++) {
	                    TVDinRep vDinRep = (TVDinRep) vcData.get(i);
                
	                    String cNo = iConsecutivo+"";
	                    String cPuerto = vDinRep.getString("CPUERTOENTIDAD").toUpperCase();
	                    if(!vDinRep.getString("CCLASIFICACION").equals("")){
	                    	cPuerto += " ("+vDinRep.getString("CCLASIFICACION")+")";
	                    }
	                    String cTrb = vDinRep.getString("C_TRB").toUpperCase();
	                    String cOficio = vDinRep.getString("CNUMEROTITULO").toUpperCase();	                    
	                    String cFecha = vDinRep.getString("DTOFICIOSOLTARIFA").toUpperCase();	                    

	                    
	                    if(i==0){//La primera vez siempre imprime la linea
	                    	
	                    }else{ //a partir de la segunda	                    	
		                    if(vDinRep.getString("CPUERTOENTIDAD").equals(cPuertoAnterior)){//Si la anterior tiene el mísmo título
		                    	// no hace hada, no imprime y continúa la siguiente línea
		                    	cNo = "";
		                    	cOficio = "";
		                    	cFecha = "";
		                    	
		                    }else{//Si es diferente el título anterior                   			      
		                    	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);		                    	
		                    	rep.comBordeTotal("D",iRenglonIni,"D",iRenglon-1,1);
		                    	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
		                    	rep.comBordeRededor("H",iRenglonIni,"H",iRenglon-1,1,1);
		                    	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);		                    	
		                    	
		                    	rep.comFillCells("A", iRenglon, "K", iRenglon, iColorFondo);
		                    	
		                    	iRenglon++;
		                    	iConsecutivo++;	
		                    	cNo = iConsecutivo+"";
		                    	iRenglonIni = iRenglon;
		                    }
	                    }
	                    
	                    rep.comDespliegaAlineado("B",iRenglon,cNo,		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("D",iRenglon,cPuerto,	false, rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("F",iRenglon,cTrb,		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("H",iRenglon,cOficio,	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    rep.comDespliegaAlineado("J",iRenglon,cFecha,	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    
	                    
	                    rep.comFillCells("A", iRenglon, "A", iRenglon, iColorFondo);
	                    rep.comFillCells("B", iRenglon, "B", iRenglon, iColorBlanco);
	                    rep.comFillCells("C", iRenglon, "C", iRenglon, iColorFondo);
	                    rep.comFillCells("D", iRenglon, "D", iRenglon, iColorBlanco);
	                    rep.comFillCells("E", iRenglon, "E", iRenglon, iColorFondo);
	                    rep.comFillCells("F", iRenglon, "F", iRenglon, iColorBlanco);
	                    rep.comFillCells("G", iRenglon, "G", iRenglon, iColorFondo);
	                    rep.comFillCells("H", iRenglon, "H", iRenglon, iColorBlanco);
	                    rep.comFillCells("I", iRenglon, "I", iRenglon, iColorFondo);
	                    rep.comFillCells("J", iRenglon, "J", iRenglon, iColorBlanco);
	                    rep.comFillCells("K", iRenglon, "K", iRenglon, iColorFondo);
	                    
	                    
	                    cPuertoAnterior = vDinRep.getString("CPUERTOENTIDAD");
	                    
	            	}	            	
	            	
                	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);
                	rep.comBordeTotal("D",iRenglonIni,"D",iRenglon-1,1);
                	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
                	rep.comBordeRededor("H",iRenglonIni,"H",iRenglon-1,1,1);
                	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
	            	
                	rep.comFillCells("A", iRenglon, "K", iRenglon, iColorFondo);
                	
	            	
                	sbRetorno = rep.getSbDatos();
	            }
	
	      }catch(Exception e){
	        e.printStackTrace();
	        cMensaje += e.getMessage();
	      }
	    if(!cMensaje.equals(""))
	      throw new Exception(cMensaje);
	    return sbRetorno;
	}

	/**
	 * 2-Lanchaje
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	public StringBuffer fReporteLanchaje(String cFiltro) throws Exception{
		System.out.println("*************** fReporteLanchaje");
	    cError = "";
	    TFechas dFechas = new TFechas();
	    StringBuffer sbRetorno = new StringBuffer("");
	      try{
	          rep.iniciaReporte();
	          
	          DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX"));
	          String cFechaFormated = format.format(new Date()).toUpperCase()+"  ";
	          rep.comDespliegaAlineado("L",8,"VIGENCIA AL: "+cFechaFormated, false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());

	          Vector vcData = this.consultaServicio(cFiltro," ORDER BY sc.ICVEPUERTO");          
	
	            if(vcData.size()>0){            	
	            	
	            	String cPuertoAnterior = "";
	            	int iRenglon=15;	            	
	            	int iConsecutivo = 1;	
	            	int iRenglonIni = iRenglon;
	            	int iColorBlanco = 2;
	            	int iColorFondo = 8;//8=Azul
	            	
	            	//TEST
//	            	for (int i = 0; i < 20; i++, iRenglon+=5) {
//	            		rep.comDespliegaAlineado("B",iRenglon,i+"",	false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
//	            		rep.comBordeRededor("B",iRenglon,"B",iRenglon+3,i,1);
//	            		rep.comBordeTotal("D",iRenglon,"E",iRenglon+3,i);
//	            		rep.comFillCells("B", iRenglon, "B", iRenglon, i);
//	            	}
//	            	
	            	for (int i = 0; i < vcData.size(); i++, iRenglon++) {
	                    TVDinRep vDinRep = (TVDinRep) vcData.get(i);	                    
                
	                    String cNo = iConsecutivo+"";
	                    String cPuerto = vDinRep.getString("CPUERTOENTIDAD").toUpperCase();
	                    String cPrestador = vDinRep.getString("CSOLICITANTE").toUpperCase();
	                    if(!vDinRep.getString("CCLASIFICACION").equals("")){
	                    	cPrestador += " ("+vDinRep.getString("CCLASIFICACION")+")";
	                    }
	                    String cCuotaServicio = vDinRep.getString("CCUOTAPORSERVICIO").toUpperCase();
	                    String cOficio = vDinRep.getString("CNUMEROTITULO").toUpperCase();
	                    String cFecha = vDinRep.getString("DTOFICIOSOLTARIFA").toUpperCase();

	                    
	                    if(i==0){//La primera vez siempre imprime la linea
	                    	
	                    }else{ //a partir de la segunda	                    	
		                    if(vDinRep.getString("CPUERTOENTIDAD").equals(cPuertoAnterior)){//Si la anterior tiene el mísmo puerto
		                    	// no hace hada, no imprime y continúa la siguiente línea
//		                    	cNo = "";
//		                    	cOficioFecha = "";
		                    	cPuerto = "";
		                    	
		                    }else{//Si es diferente el puerto anterior                   			      
		                    	rep.comBordeTotal("B",iRenglonIni,"B",iRenglon-1,1);		                    	
		                    	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
		                    	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
		                    	rep.comBordeTotal("H",iRenglonIni,"H",iRenglon-1,1);
		                    	rep.comBordeTotal("J",iRenglonIni,"J",iRenglon-1,1);
		                    	rep.comBordeTotal("L",iRenglonIni,"L",iRenglon-1,1);		                    	
		                    	
		                    	rep.comFillCells("A", iRenglon, "M", iRenglon, iColorFondo);
		                    	
		                    	iRenglon++;
//		                    	iConsecutivo++;	
//		                    	cNo = iConsecutivo+"";
		                    	iRenglonIni = iRenglon;
		                    }
	                    }
	                    iConsecutivo++;
	                    
	                    rep.comDespliegaAlineado("B",iRenglon,cNo,			false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("D",iRenglon,cPuerto,		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("F",iRenglon,cPrestador,	false, rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("H",iRenglon,cCuotaServicio,false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("J",iRenglon,cOficio, 		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    rep.comDespliegaAlineado("L",iRenglon,cFecha, 		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    
	                    
	                    rep.comFillCells("A", iRenglon, "A", iRenglon, iColorFondo);
	                    rep.comFillCells("B", iRenglon, "B", iRenglon, iColorBlanco);
	                    rep.comFillCells("C", iRenglon, "C", iRenglon, iColorFondo);
	                    rep.comFillCells("D", iRenglon, "D", iRenglon, iColorBlanco);
	                    rep.comFillCells("E", iRenglon, "E", iRenglon, iColorFondo);
	                    rep.comFillCells("F", iRenglon, "F", iRenglon, iColorBlanco);
	                    rep.comFillCells("G", iRenglon, "G", iRenglon, iColorFondo);
	                    rep.comFillCells("H", iRenglon, "H", iRenglon, iColorBlanco);
	                    rep.comFillCells("I", iRenglon, "I", iRenglon, iColorFondo);
	                    rep.comFillCells("J", iRenglon, "J", iRenglon, iColorBlanco);
	                    rep.comFillCells("K", iRenglon, "K", iRenglon, iColorFondo);	                    
	                    rep.comFillCells("L", iRenglon, "L", iRenglon, iColorBlanco);
	                    rep.comFillCells("M", iRenglon, "M", iRenglon, iColorFondo);	                    
	                    
	                    cPuertoAnterior = vDinRep.getString("CPUERTOENTIDAD");	                    
	            	}	            	
	            	
                	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);
                	//rep.comBordeTotal("B",iRenglon,"B",iRenglon,1);	            		
                	rep.comBordeTotal("D",iRenglonIni,"D",iRenglon-1,1);
                	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
                	rep.comBordeRededor("H",iRenglonIni,"H",iRenglon-1,1,1);
                	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
	            	
                	rep.comFillCells("A", iRenglon, "M", iRenglon, iColorFondo);
                	
	            	
                	sbRetorno = rep.getSbDatos();
	            }
	
	      }catch(Exception e){
	        e.printStackTrace();
	        cMensaje += e.getMessage();
	      }
	    if(!cMensaje.equals(""))
	      throw new Exception(cMensaje);
	    return sbRetorno;
	}


	/**
	 * 3-Amarre y Desamarre
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	public StringBuffer fReporteAmarre(String cFiltro) throws Exception{
		System.out.println("*************** fReporteAmarre");
	    cError = "";
	    TFechas dFechas = new TFechas();
	    StringBuffer sbRetorno = new StringBuffer("");
	      try{
	          rep.iniciaReporte();
	          
	          DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX"));
	          String cFechaFormated = format.format(new Date()).toUpperCase()+"  ";
	          rep.comDespliegaAlineado("L",5,"VIGENCIA AL: "+cFechaFormated, false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());

	          Vector vcData = this.consultaServicioCuota(cFiltro," ORDER BY sc.ICVEPUERTO,t.CNUMEROTITULO,r.IRANGOMINIMO");	          
	
	            if(vcData.size()>0){            	
	            	
	            	String cPuertoAnterior = "";
	            	String cPrestadorAnterior = "";
	            	int iRenglon=11;	            	
	            	int iConsecutivo = 1;	
	            	int iRenglonIni = iRenglon;
	            	int iColorBlanco = 2;
	            	int iColorFondo = 46;//46=Marrón
	            	
	            	//TEST
//	            	for (int i = 0; i < 150; i++, iRenglon+=5) {
//	            		rep.comDespliegaAlineado("B",iRenglon,i+"",	false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
////	            		rep.comBordeRededor("B",iRenglon,"B",iRenglon+3,i,1);
////	            		rep.comBordeTotal("D",iRenglon,"E",iRenglon+3,i);
//	            		rep.comFillCells("B", iRenglon, "B", iRenglon, i);
//	            	}
//	            	
	            	for (int i = 0; i < vcData.size(); i++, iRenglon++) {
	                    TVDinRep vDinRep = (TVDinRep) vcData.get(i);	                    
                
//	                    String cNo = iConsecutivo+"";
	                    String cPuerto = vDinRep.getString("CPUERTOENTIDAD").toUpperCase();
	                    String cPrestador = vDinRep.getString("CSOLICITANTE").toUpperCase();
	                    String cConcepto = "";
	                    
	                    //Si existe Rango es por rango:
	                    if(vDinRep.getInt("ICVECUOTARANGO")!=0){
	                    	cConcepto = "DE "+vDinRep.getString("IRANGOMINIMO")+" HASTA "+vDinRep.getString("IRANGOMAXIMO");
	                    }else{
	                    	cConcepto = vDinRep.getString("CCONCEPTO").toUpperCase();
	                    }
	                    
	                    String cCuota = vDinRep.getString("DCUOTA").toUpperCase();
	                    String cOficio = vDinRep.getString("CNUMEROTITULO").toUpperCase();
	                    String cFecha = vDinRep.getString("DTOFICIOSOLTARIFA").toUpperCase();	                    
	                    
	                    if(i==0){//La primera vez siempre imprime la linea
	                    	
	                    }else{ //a partir de la segunda
	                    	
		                    if(vDinRep.getString("CPUERTOENTIDAD").equals(cPuertoAnterior)){//Si la anterior tiene el mísmo puerto
		                    	// no hace hada, no imprime y continúa la siguiente línea
//		                    	cNo = "";
//		                    	cOficioFecha = "";
		                    	cPuerto = "";
		                    	
		                    	
			                    if(vDinRep.getString("CSOLICITANTE").equals(cPrestadorAnterior)){//Si la anterior tiene el mismo prestador
			                    	// no hace hada, no imprime y continúa la siguiente línea
			                    	cPrestador = "";
			                    	cOficio = "";
			                    	cFecha = "";
			                    		                    	
			                    }else{//Si es diferente el puerto anterior 
			                    	//Debe repetirse igua que abajo
//			                    	rep.comBordeTotal("B",iRenglonIni,"B",iRenglon-1,1);		                    	
			                    	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
			                    	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
			                    	rep.comBordeTotal("H",iRenglonIni,"H",iRenglon-1,1);
			                    	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
			                    	rep.comBordeRededor("L",iRenglonIni,"L",iRenglon-1,1,1);			                    	
			                    	
			                    	rep.comFillCells("D", iRenglon, "M", iRenglon, iColorFondo);
			                    	
			                    	iRenglon++;
//			                    	iConsecutivo++;	
//			                    	cNo = iConsecutivo+"";
			                    	iRenglonIni = iRenglon;
			                    }
		                    	
		                    		                    	
		                    }else{//Si es diferente el puerto anterior 
		                  		//Debe repetirse igua que arriba
		                    	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);		                    	
		                    	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
		                    	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
		                    	rep.comBordeTotal("H",iRenglonIni,"H",iRenglon-1,1);
		                    	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
		                    	rep.comBordeRededor("L",iRenglonIni,"L",iRenglon-1,1,1);
		                    	
		                    	rep.comFillCells("A", iRenglon, "M", iRenglon, iColorFondo);
		                    	
		                    	iRenglon++;
//		                    	iConsecutivo++;	
//		                    	cNo = iConsecutivo+"";
		                    	iRenglonIni = iRenglon;
		                    }
		                    
	                    }
	                    
	                    iConsecutivo++;
	                    
	                    rep.comDespliegaAlineado("B",iRenglon,cPuerto,		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("D",iRenglon,cPrestador,	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("F",iRenglon,cConcepto,	false, rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("H",iRenglon,cCuota,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("J",iRenglon,cOficio, 		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    rep.comDespliegaAlineado("L",iRenglon,cFecha, 		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    
	                    
	                    rep.comFillCells("A", iRenglon, "A", iRenglon, iColorFondo);
	                    rep.comFillCells("B", iRenglon, "B", iRenglon, iColorBlanco);
	                    rep.comFillCells("C", iRenglon, "C", iRenglon, iColorFondo);
	                    rep.comFillCells("D", iRenglon, "D", iRenglon, iColorBlanco);
	                    rep.comFillCells("E", iRenglon, "E", iRenglon, iColorFondo);
	                    rep.comFillCells("F", iRenglon, "F", iRenglon, iColorBlanco);
	                    rep.comFillCells("G", iRenglon, "G", iRenglon, iColorFondo);
	                    rep.comFillCells("H", iRenglon, "H", iRenglon, iColorBlanco);
	                    rep.comFillCells("I", iRenglon, "I", iRenglon, iColorFondo);
	                    rep.comFillCells("J", iRenglon, "J", iRenglon, iColorBlanco);
	                    rep.comFillCells("K", iRenglon, "K", iRenglon, iColorFondo);	                    
	                    rep.comFillCells("L", iRenglon, "L", iRenglon, iColorBlanco);
	                    rep.comFillCells("M", iRenglon, "M", iRenglon, iColorFondo);	                    
	                    
	                    cPuertoAnterior = vDinRep.getString("CPUERTOENTIDAD");
	                    cPrestadorAnterior = vDinRep.getString("CSOLICITANTE");
	            	}
	            	
	            	//Debe repetirse igua que arriba
                	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);
                	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
                	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
                	rep.comBordeTotal("H",iRenglonIni,"H",iRenglon-1,1);
                	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
                	rep.comBordeRededor("L",iRenglonIni,"L",iRenglon-1,1,1);
	            	
                	rep.comFillCells("A", iRenglon, "M", iRenglon, iColorFondo);
                	
	            	
                	sbRetorno = rep.getSbDatos();
	            }
	
	      }catch(Exception e){
	        e.printStackTrace();
	        cMensaje += e.getMessage();
	      }
	    if(!cMensaje.equals(""))
	      throw new Exception(cMensaje);
	    return sbRetorno;
	}
	
	
	/**
	 * 4 - Bascula
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	public StringBuffer fReporteBascula(String cFiltro) throws Exception{	
		return fReporteBasculaAguaRecoleccion(cFiltro,12,50);//50=Verde botella
	}

	/**
	 * 5 - Agua Potable
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	public StringBuffer fReporteAguaPotable(String cFiltro) throws Exception{
		return fReporteBasculaAguaRecoleccion(cFiltro,12,42);//42=Azul Aqua
	}

	/**
	 * 6 - Recolección Basura
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	public StringBuffer fReporteRecolecBasura(String cFiltro) throws Exception{
		return fReporteBasculaAguaRecoleccion(cFiltro,11,15);//15=Verde botella
	}
	

	/**
	 * 4,5,6
	 * @param cFiltro
	 * @param iRenglon
	 * @param iColorFondo
	 * @return
	 * @throws Exception
	 */
	private StringBuffer fReporteBasculaAguaRecoleccion(String cFiltro, int iRenglon, int iColorFondo) throws Exception{
	    cError = "";
	    TFechas dFechas = new TFechas();
	    StringBuffer sbRetorno = new StringBuffer("");
	      try{
	          rep.iniciaReporte();
	          
	          DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX"));
	          String cFechaFormated = format.format(new Date()).toUpperCase()+"  ";
	          rep.comDespliegaAlineado("L",8,"VIGENCIA AL: "+cFechaFormated, false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());

	          Vector vcData = this.consultaServicioCuota(cFiltro," ORDER BY sc.ICVEPUERTO,CNUMEROTITULO");          
	
	            if(vcData.size()>0){            	
	            	
	            	String cPuertoAnterior = "";
	            	String cPrestadorAnterior = "";
//	            	int iRenglon=12;	            	
	            	int iConsecutivo = 1;	
	            	int iRenglonIni = iRenglon;
	            	int iColorBlanco = 2;
//	            	int iColorFondo = 50;//50=Verde botella
	            	
	            	//TEST
//	            	for (int i = 0; i < 150; i++, iRenglon+=5) {
//	            		rep.comDespliegaAlineado("B",iRenglon,i+"",	false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
////	            		rep.comBordeRededor("B",iRenglon,"B",iRenglon+3,i,1);
////	            		rep.comBordeTotal("D",iRenglon,"E",iRenglon+3,i);
//	            		rep.comFillCells("B", iRenglon, "B", iRenglon, i);
//	            	}
	            	
	            	
	            	for (int i = 0; i < vcData.size(); i++, iRenglon++) {
	                    TVDinRep vDinRep = (TVDinRep) vcData.get(i);	                    
                
//	                    String cNo = iConsecutivo+"";
	                    String cPuerto = vDinRep.getString("CPUERTOENTIDAD").toUpperCase();
	                    String cPrestador = vDinRep.getString("CSOLICITANTE").toUpperCase();
	                    if(!vDinRep.getString("CCLASIFICACION").equals("")){
	                    	cPrestador += " ("+vDinRep.getString("CCLASIFICACION")+")";
	                    }

	                    String cConcepto = vDinRep.getString("CCONCEPTO").toUpperCase();
	                    String cCuota = vDinRep.getString("DCUOTA").toUpperCase();
	                    String cOficio = vDinRep.getString("CNUMEROTITULO").toUpperCase();
	                    String cFecha = vDinRep.getString("DTOFICIOSOLTARIFA").toUpperCase();	                    
	                    
	                    if(i==0){//La primera vez siempre imprime la linea
	                    	
	                    }else{ //a partir de la segunda
	                    	
		                    if(vDinRep.getString("CPUERTOENTIDAD").equals(cPuertoAnterior)){//Si la anterior tiene el mísmo puerto
		                    	// no hace hada, no imprime y continúa la siguiente línea
//		                    	cNo = "";
//		                    	cOficioFecha = "";
		                    	cPuerto = "";
		                    	
		                    	
			                    if(vDinRep.getString("CSOLICITANTE").equals(cPrestadorAnterior)){//Si la anterior tiene el mismo prestador
			                    	// no hace hada, no imprime y continúa la siguiente línea
			                    	cPrestador = "";
			                    	cOficio = "";
			                    	cFecha = "";
			                    		                    	
			                    }else{//Si es diferente el puerto anterior 
			                    	//Debe repetirse igua que abajo
//			                    	rep.comBordeTotal("B",iRenglonIni,"B",iRenglon-1,1);		                    	
			                    	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
			                    	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
			                    	rep.comBordeTotal("H",iRenglonIni,"H",iRenglon-1,1);
			                    	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
			                    	rep.comBordeRededor("L",iRenglonIni,"L",iRenglon-1,1,1);			                    	
			                    	
			                    	rep.comFillCells("D", iRenglon, "M", iRenglon, iColorFondo);
			                    	
			                    	iRenglon++;
//			                    	iConsecutivo++;	
//			                    	cNo = iConsecutivo+"";
			                    	iRenglonIni = iRenglon;
			                    }
		                    	
		                    		                    	
		                    }else{//Si es diferente el puerto anterior 
		                  		//Debe repetirse igua que arriba
		                    	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);		                    	
		                    	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
		                    	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
		                    	rep.comBordeTotal("H",iRenglonIni,"H",iRenglon-1,1);
		                    	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
		                    	rep.comBordeRededor("L",iRenglonIni,"L",iRenglon-1,1,1);
		                    	
		                    	rep.comFillCells("A", iRenglon, "M", iRenglon, iColorFondo);
		                    	
		                    	iRenglon++;
//		                    	iConsecutivo++;	
//		                    	cNo = iConsecutivo+"";
		                    	iRenglonIni = iRenglon;
		                    }
		                    
	                    }
	                    
	                    iConsecutivo++;
	                    
	                    rep.comDespliegaAlineado("B",iRenglon,cPuerto,		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("D",iRenglon,cPrestador,	false, rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("F",iRenglon,cConcepto,	false, rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("H",iRenglon,cCuota,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("J",iRenglon,cOficio, 		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    rep.comDespliegaAlineado("L",iRenglon,cFecha, 		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    
	                    
	                    rep.comFillCells("A", iRenglon, "A", iRenglon, iColorFondo);
	                    rep.comFillCells("B", iRenglon, "B", iRenglon, iColorBlanco);
	                    rep.comFillCells("C", iRenglon, "C", iRenglon, iColorFondo);
	                    rep.comFillCells("D", iRenglon, "D", iRenglon, iColorBlanco);
	                    rep.comFillCells("E", iRenglon, "E", iRenglon, iColorFondo);
	                    rep.comFillCells("F", iRenglon, "F", iRenglon, iColorBlanco);
	                    rep.comFillCells("G", iRenglon, "G", iRenglon, iColorFondo);
	                    rep.comFillCells("H", iRenglon, "H", iRenglon, iColorBlanco);
	                    rep.comFillCells("I", iRenglon, "I", iRenglon, iColorFondo);
	                    rep.comFillCells("J", iRenglon, "J", iRenglon, iColorBlanco);
	                    rep.comFillCells("K", iRenglon, "K", iRenglon, iColorFondo);	                    
	                    rep.comFillCells("L", iRenglon, "L", iRenglon, iColorBlanco);
	                    rep.comFillCells("M", iRenglon, "M", iRenglon, iColorFondo);	                    
	                    
	                    cPuertoAnterior = vDinRep.getString("CPUERTOENTIDAD");
	                    cPrestadorAnterior = vDinRep.getString("CSOLICITANTE");
	            	}
	            	
	            	//Debe repetirse igua que arriba
                	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);
                	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
                	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
                	rep.comBordeTotal("H",iRenglonIni,"H",iRenglon-1,1);
                	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
                	rep.comBordeRededor("L",iRenglonIni,"L",iRenglon-1,1,1);
	            	
                	rep.comFillCells("A", iRenglon, "M", iRenglon, iColorFondo);
                	
	            	
                	sbRetorno = rep.getSbDatos();
	            }
	
	      }catch(Exception e){
	        e.printStackTrace();
	        cMensaje += e.getMessage();
	      }
	    if(!cMensaje.equals(""))
	      throw new Exception(cMensaje);
	    return sbRetorno;
	}
	
	
	
	
	/**
	 * 7-Remolque
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	public StringBuffer fReporteRemolque(String cFiltro) throws Exception{
		System.out.println("*************** fReporteRemolque");
	    cError = "";
	    TFechas dFechas = new TFechas();
	    StringBuffer sbRetorno = new StringBuffer("");
	      try{
	          rep.iniciaReporte();
	          
	          DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX"));
	          String cFechaFormated = format.format(new Date()).toUpperCase()+"  ";
	          rep.comDespliegaAlineado("S",6,"VIGENCIA AL: "+cFechaFormated, false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());

	          Vector vcDataAll = this.consultaServicioCuota(cFiltro+" and U.DCUOTA is not null ",
	        		  " ORDER BY sc.ICVEPUERTO,t.CNUMEROTITULO,r.IRANGOMINIMO");          
	
	            if(vcDataAll.size()>0){            	
	            	
	            	String cPuertoAnterior = "";
	            	String cPrestadorAnterior = "";
	            	
	            	
	            	int iRenglon=11;	            	
	            	int iRenglonIni = iRenglon;
	            	int iConsecutivo = 1;	
	            	int iColorBlanco = 2;
	            	int iColorFondo = 45;//45=Naranja
	            	
	            	//TEST
//	            	for (int i = 0; i < 150; i++, iRenglon+=5) {
////	            		rep.comDespliegaAlineado("B",iRenglon,i+"",	false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
////	            		rep.comDespliegaCombinado(cValor, cColIni, iRengIni, cColFin, iRengFin, cAlineacionH, cAlineacionV, lBold, iColorFondo, lBorde, lBordeTotal, iTipoBorde, iColorBorde)
//	            		rep.comDespliegaCombinado(i+"","B",iRenglon,"F",iRenglon+3,rep.getAT_HCENTRO(),rep.getAT_VCENTRO(),i%2==1,i,i%2==1,i%2==1,1,1);
////	            		rep.comBordeRededor("B",iRenglon,"B",iRenglon+3,i,1);
////	            		rep.comBordeTotal("D",iRenglon,"E",iRenglon+3,i);
//	            		rep.comFillCells("B", iRenglon, "B", iRenglon, i);
//	            	}
//	            	
	            	//Procesamos el resultset, buscando:
	            	//Conceptos relacionados diferentes, rangos diferentes, concordancia
	            	String aSolicitante[][] = new String[vcDataAll.size()][5];//[0]=es encabezado, [1]=
	            	aSolicitante[0][0] = "";
	            	TVDinRep mapColumna = new TVDinRep();
	            	TVDinRep mapFila = new TVDinRep();
	            	int iColumna = 1;
	            	int iFila = 1;
	            	
	            	Vector vcData = new Vector();
	            	TVDinRep vPrestadorActual = null;
	            	
	            	
	            	if(vcDataAll.size()>0){
	            		vPrestadorActual = (TVDinRep) vcDataAll.get(0);
	            		cPuertoAnterior = vPrestadorActual.getString("CDSCPUERTO");
	            		cPrestadorAnterior = vPrestadorActual.getString("CSOLICITANTE");
	            	}
	            	for (int i = 0; i < vcDataAll.size(); i++) {	            		
	            		TVDinRep vDinRep = (TVDinRep) vcDataAll.get(i);
	            		
            			if(!vDinRep.getString("CDSCPUERTO").equals(cPuertoAnterior) || 
            				!vDinRep.getString("CSOLICITANTE").equals(cPrestadorAnterior)	){ //CDSCPUERTO
            				
            				vPrestadorActual.put("aArray", aSolicitante);
            				vcData.add(vPrestadorActual);
            				
            				vPrestadorActual = vDinRep;
            				
            				aSolicitante = new String[vcDataAll.size()][5];
            				aSolicitante[0][0] = "";
        	            	mapColumna = new TVDinRep();
        	            	mapFila = new TVDinRep();
        	            	iColumna = 1;
        	            	iFila = 1;    
        	            	System.out.println("********* Nuevo Prestador ="+vDinRep.getString("CSOLICITANTE"));
            			}

	            		
        				String cConcepto = vDinRep.getString("CCONCEPTORELACIONADO");
        				int numColumna = mapColumna.getInt(cConcepto);
        				if(numColumna==0){//Si no existía
        					mapColumna.put(cConcepto, iColumna);
        					numColumna = iColumna;
        					aSolicitante[0][numColumna] = cConcepto;
        					iColumna++;
        				}
        				
        				String cRango = vDinRep.getString("CRANGO");
        				int numFila = mapColumna.getInt(cRango); //mapFila
        				if(numFila==0){//Si no existía
        					mapColumna.put(cRango, iFila);
        					numFila = iFila;
        					aSolicitante[numFila][0] = cRango;
        					iFila++;
        				}
        				
        				aSolicitante[numFila][numColumna]=vDinRep.getString("DCUOTA");	            		
	            		
        				cPuertoAnterior = vDinRep.getString("CDSCPUERTO");
	            		cPrestadorAnterior = vDinRep.getString("CSOLICITANTE");
	            	}
	            	
	            	//ultimo
	            	vPrestadorActual.put("aArray", aSolicitante);
    				vcData.add(vPrestadorActual);
	            	
	            	
	            	//Log del aArray	            	
	            	for (int x = 0; x < vcData.size(); x++) {
	            		System.out.println("===========*** objeto="+x);
	            		
	            		TVDinRep vDinRep = (TVDinRep) vcData.get(x);
	            		String[][] aArray = (String[][])vDinRep.get("aArray");	            		
	            		
		            	for (int i = 0; i < aArray.length; i++) {
							for (int j = 0; j < aArray[i].length; j++) {
								System.out.print(aArray[i][j]+"\t");
							}
							System.out.println();
						}
	            	}
	            	
	            	
	            	
	            	
	            	for (int i = 0; i < vcData.size(); i++, iRenglon++) {
	                    TVDinRep vDinRep = (TVDinRep) vcData.get(i);
                
//	                    String cNo = iConsecutivo+"";
	                    String cPuerto = vDinRep.getString("CPUERTOENTIDAD").toUpperCase();
	                    String cPrestador = vDinRep.getString("CSOLICITANTE").toUpperCase();
	                    String cConcepto = "";
	                    
	                    //Si existe Rango es por rango:
	                    if(vDinRep.getInt("ICVECUOTARANGO")!=0){
	                    	cConcepto = "DE "+vDinRep.getString("IRANGOMINIMO")+" HASTA "+vDinRep.getString("IRANGOMAXIMO");
	                    }else{
	                    	cConcepto = vDinRep.getString("CCONCEPTO").toUpperCase();
	                    }
	                    
	                    String cCuota = vDinRep.getString("DCUOTA").toUpperCase();
	                    String cOficio = vDinRep.getString("CNUMEROTITULO").toUpperCase();
	                    String cFecha = vDinRep.getString("DTOFICIOSOLTARIFA").toUpperCase();	                    
	                    
	                    String[][] aArray = (String[][])vDinRep.get("aArray");
	                    
	                    if(i==0){//La primera vez siempre imprime la linea
	                    	
	                    }else{ //a partir de la segunda
	                    	
		                    if(vDinRep.getString("CPUERTOENTIDAD").equals(cPuertoAnterior)){//Si la anterior tiene el mísmo puerto
		                    	// no hace hada, no imprime y continúa la siguiente línea
//		                    	cNo = "";
//		                    	cOficioFecha = "";
		                    	cPuerto = "";
		                    	
		                    	
			                    if(vDinRep.getString("CSOLICITANTE").equals(cPrestadorAnterior)){//Si la anterior tiene el mismo prestador
			                    	// no hace hada, no imprime y continúa la siguiente línea
			                    	cPrestador = "";
			                    	cOficio = "";
			                    	cFecha = "";
			                    		                    	
			                    }else{//Si es diferente el puerto anterior 
			                    	//Debe repetirse igua que abajo
//			                    	rep.comBordeTotal("B",iRenglonIni,"B",iRenglon-1,1);		                    	
			                    	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
			                    	rep.comBordeTotal("I",iRenglonIni,"M",iRenglon-1,1);
			                    	rep.comBordeTotal("O",iRenglonIni,"O",iRenglon-1,1);
			                    	rep.comBordeRededor("Q",iRenglonIni,"Q",iRenglon-1,1,1);
			                    	rep.comBordeRededor("S",iRenglonIni,"S",iRenglon-1,1,1);			                    	
			                    	
			                    	rep.comFillCells("D", iRenglon, "T", iRenglon, iColorFondo);
			                    	
			                    	iRenglon++;
//			                    	iConsecutivo++;	
//			                    	cNo = iConsecutivo+"";
			                    	iRenglonIni = iRenglon;
			                    }
		                    	
		                    		                    	
		                    }else{//Si es diferente el puerto anterior 
		                  		//Debe repetirse igua que arriba
		                    	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);		                    	
		                    	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
		                    	rep.comBordeTotal("I",iRenglonIni,"M",iRenglon-1,1);
		                    	rep.comBordeTotal("O",iRenglonIni,"O",iRenglon-1,1);
		                    	rep.comBordeRededor("Q",iRenglonIni,"Q",iRenglon-1,1,1);
		                    	rep.comBordeRededor("S",iRenglonIni,"S",iRenglon-1,1,1);
		                    	
		                    	rep.comFillCells("A", iRenglon, "T", iRenglon, iColorFondo);
		                    	
		                    	iRenglon++;
//		                    	iConsecutivo++;	
//		                    	cNo = iConsecutivo+"";
		                    	iRenglonIni = iRenglon;
		                    }
		                    
	                    }
	                    
	                    iConsecutivo++;
	                    
	                    rep.comDespliegaAlineado("B",iRenglon,cPuerto,		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("D",iRenglon,cPrestador,	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
//	                    rep.comDespliegaAlineado("F",iRenglon,cConcepto,	false, rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
//	                    rep.comDespliegaAlineado("H",iRenglon,cCuota,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("Q",iRenglon,cOficio, 		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    rep.comDespliegaAlineado("S",iRenglon,cFecha, 		false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    
	                    
	                    //contar las columnas
	                    int numColumnas = 0;
	                    for (int j = 1; j < aArray[0].length; j++) {
	                    	if(aArray[0][j]==null){	                    		
	                    		break;
	                    	}
	                    	numColumnas++;
	                    }	                    
	                 
	                    
	                    
	                    Vector vcRemolcador = consultaRemolcador(" where r.iCveServicioCuota = "+vDinRep.getString("ICVESERVICIOCUOTA"),"");
	                    
	                    for (int j = 0,iReglonRemolc = iRenglon; j < vcRemolcador.size(); j++,iReglonRemolc++) {//supuesto que siempre hay menos remolcadores
	                    	TVDinRep vRemolcador= (TVDinRep) vcRemolcador.get(j);
	                    	
	                    	String cNombre = vRemolcador.getString("cNombre").toUpperCase();
	                    	String cPotencia = vRemolcador.getString("iPotencia").toUpperCase();
	                    	rep.comDespliegaAlineado("F",iReglonRemolc,cNombre,	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	
	                    	rep.comDespliegaAlineado("G",iReglonRemolc,cPotencia,false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	
	                    }
	                    
		            	for (int j = 0; j < aArray.length; j++) {
//		            		if(aArray[j][0]==null){
//		            			break;
//		            		}
		            		
		            		rep.comDespliegaAlineado("O",iRenglon,aArray[j][0],	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
		            		
							if(numColumnas==1){
								rep.comDespliegaAlineado("K",iRenglon,aArray[j][1],	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
							}
							if(numColumnas==2){
								rep.comDespliegaAlineado("J",iRenglon,aArray[j][1],	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
								rep.comDespliegaAlineado("L",iRenglon,aArray[j][2],	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
							}
							if(numColumnas==3){
								rep.comDespliegaAlineado("I",iRenglon,aArray[j][1],	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
								rep.comDespliegaAlineado("J",iRenglon,aArray[j][2],	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
								rep.comDespliegaAlineado("M",iRenglon,aArray[j][3],	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
							}
							
							rep.comFillCells("A", iRenglon, "A", iRenglon, iColorFondo);
							rep.comFillCells("B", iRenglon, "B", iRenglon, iColorBlanco);
							rep.comFillCells("C", iRenglon, "C", iRenglon, iColorFondo);
							rep.comFillCells("D", iRenglon, "D", iRenglon, iColorBlanco);
							rep.comFillCells("E", iRenglon, "E", iRenglon, iColorFondo);
							rep.comFillCells("F", iRenglon, "G", iRenglon, iColorBlanco);			                    
							rep.comFillCells("H", iRenglon, "H", iRenglon, iColorFondo);			                    
							rep.comFillCells("I", iRenglon, "M", iRenglon, iColorBlanco);			                    
							rep.comFillCells("N", iRenglon, "N", iRenglon, iColorFondo);
							rep.comFillCells("O", iRenglon, "O", iRenglon, iColorBlanco);	                    
							rep.comFillCells("P", iRenglon, "P", iRenglon, iColorFondo);
							rep.comFillCells("Q", iRenglon, "Q", iRenglon, iColorBlanco);	
							rep.comFillCells("R", iRenglon, "R", iRenglon, iColorFondo);
							rep.comFillCells("S", iRenglon, "S", iRenglon, iColorBlanco);	
							rep.comFillCells("T", iRenglon, "T", iRenglon, iColorFondo);
							
							if(aArray[j+1][0]==null){
		            			break;
		            		}
							
							iRenglon++;
						}
	                    
	                    
	                    
						rep.comFillCells("A", iRenglon, "A", iRenglon, iColorFondo);
	                    rep.comFillCells("B", iRenglon, "B", iRenglon, iColorBlanco);
	                    rep.comFillCells("C", iRenglon, "C", iRenglon, iColorFondo);
	                    rep.comFillCells("D", iRenglon, "D", iRenglon, iColorBlanco);
	                    rep.comFillCells("E", iRenglon, "E", iRenglon, iColorFondo);
	                    rep.comFillCells("F", iRenglon, "G", iRenglon, iColorBlanco);			                    
	                    rep.comFillCells("H", iRenglon, "H", iRenglon, iColorFondo);			                    
	                    rep.comFillCells("I", iRenglon, "M", iRenglon, iColorBlanco);			                    
	                    rep.comFillCells("N", iRenglon, "N", iRenglon, iColorFondo);
	                    rep.comFillCells("O", iRenglon, "O", iRenglon, iColorBlanco);	                    
	                    rep.comFillCells("P", iRenglon, "P", iRenglon, iColorFondo);
	                    rep.comFillCells("Q", iRenglon, "Q", iRenglon, iColorBlanco);	
	                    rep.comFillCells("R", iRenglon, "R", iRenglon, iColorFondo);
	                    rep.comFillCells("S", iRenglon, "S", iRenglon, iColorBlanco);	
	                    rep.comFillCells("T", iRenglon, "T", iRenglon, iColorFondo);	
	                    
	                    
	                    cPuertoAnterior = vDinRep.getString("CPUERTOENTIDAD");
	                    cPrestadorAnterior = vDinRep.getString("CSOLICITANTE");
	            	}
	            	
	            	//Debe repetirse igua que arriba
                	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);
                	rep.comBordeRededor("D",iRenglonIni,"D",iRenglon-1,1,1);
                	rep.comBordeTotal("I",iRenglonIni,"M",iRenglon-1,1);
                	rep.comBordeTotal("O",iRenglonIni,"O",iRenglon-1,1);
                	rep.comBordeRededor("Q",iRenglonIni,"Q",iRenglon-1,1,1);
                	rep.comBordeRededor("S",iRenglonIni,"S",iRenglon-1,1,1);
	            	
                	rep.comFillCells("A", iRenglon, "T", iRenglon, iColorFondo);
                	
	            	
                	sbRetorno = rep.getSbDatos();
	            }
	
	      }catch(Exception e){
	        e.printStackTrace();
	        cMensaje += e.getMessage();
	      }
	    if(!cMensaje.equals(""))
	      throw new Exception(cMensaje);
	    return sbRetorno;
	}

	
	
	
	
	
	/**
	 * 8 - Altura
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	public StringBuffer fReporteAltura(String cFiltro) throws Exception{
		return fReporteAlturaCabo(cFiltro);
	}
	
	/**
	 * 9 - Cabotaje
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	public StringBuffer fReporteCabotaje(String cFiltro) throws Exception{
		return fReporteAlturaCabo(cFiltro);
	}
	
	/**
	 * 8,9
	 * @param cFiltro
	 * @return
	 * @throws Exception
	 */
	private StringBuffer fReporteAlturaCabo(String cFiltro) throws Exception{
		System.out.println("*************** fReporteAlturaCabo");
	    cError = "";
	    TFechas dFechas = new TFechas();
	    StringBuffer sbRetorno = new StringBuffer("");
	      try{
	          rep.iniciaReporte();
	          
	          DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX"));
	          String cFechaFormated = format.format(new Date()).toUpperCase()+"  ";
	          rep.comDespliegaAlineado("B",4,"VIGENCIA AL: "+cFechaFormated, false, rep.getAT_VCENTRO(),rep.getAT_VCENTRO());
	          
	          Vector vcData = this.consultaServicio(cFiltro,"");          
	
	            if(vcData.size()>0){            	
	            	
	            	String cOficioAnterior = "";
	            	int iRenglon=10;	            	
	            	int iConsecutivo = 1;	
	            	int iRenglonIni = iRenglon;
	            	int iColorBlanco = 2;
	            	int iColorFondo = 6;
	            	
	            	//TEST
//	            	for (int i = 0; i < 150; i++, iRenglon+=5) {
//	            		rep.comDespliegaAlineado("B",iRenglon,i+"",	false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
////	            		rep.comBordeRededor("B",iRenglon,"B",iRenglon+3,i,1);
////	            		rep.comBordeTotal("D",iRenglon,"E",iRenglon+3,i);
//	            		rep.comFillCells("B", iRenglon, "B", iRenglon, i);
//            		}
	            	
	            	for (int i = 0; i < vcData.size(); i++, iRenglon++) {
	                    TVDinRep vDinRep = (TVDinRep) vcData.get(i);
                
//	                    String cNo = iConsecutivo+"";
	                    String cPuerto = vDinRep.getString("CPUERTOENTIDAD").toUpperCase();
//	                    String cTrb = vDinRep.getString("C_TRB").toUpperCase();
	                    String cOficio = vDinRep.getString("CNUMEROTITULO").toUpperCase();	                    
	                    String cFecha = vDinRep.getString("DTOFICIOSOLTARIFA").toUpperCase();	                    

	                    String dFijaBuque = vDinRep.getString("DCUOTAFIJABUQUE");
	                    String dVariableArqueo = vDinRep.getString("DCUOTAVARIABLEARQUEOBRUTO");
	                    String dDiariaBuque = vDinRep.getString("DCUOTADIARIABUQUE");
	                    String dNoEspecializados = vDinRep.getString("DCUOTANOESPECIALIZADOS");
	                    String dEspecializados = vDinRep.getString("DCUOTAESPECIALIZADOS");
	                    String dArborloadas = vDinRep.getString("CCUOTAABARLOADAS");
	                    String dMuellaje = vDinRep.getString("DCUOTAMUELLAJE");
	                    
	                    
//	                    if(i==0){//La primera vez siempre imprime la linea
//	                    	
//	                    }else{ //a partir de la segunda	                    	
//		                    if(vDinRep.getString("CNUMEROTITULO").equals(cOficioAnterior)){//Si la anterior tiene el mísmo título
//		                    	// no hace hada, no imprime y continúa la siguiente línea
////		                    	cNo = "";
////		                    	cOficio = "";
////		                    	cFecha = "";
//		                    	
//		                    }else{//Si es diferente el título anterior                   			      
//		                    	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);		                    	
//		                    	rep.comBordeTotal("D",iRenglonIni,"D",iRenglon-1,1);
//		                    	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
//		                    	rep.comBordeRededor("H",iRenglonIni,"H",iRenglon-1,1,1);
//		                    	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);		                    	
//		                    	
//		                    	rep.comFillCells("A", iRenglon, "K", iRenglon, iColorFondo);
//		                    	
//		                    	iRenglon++;
//		                    	iConsecutivo++;	
//		                    	cNo = iConsecutivo+"";
//		                    	iRenglonIni = iRenglon;
//		                    }
//	                    }
//	                    
//	                    iRenglon++;
	                    
	                    rep.comDespliegaAlineado("B",iRenglon,cPuerto,		false, rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("C",iRenglon,dFijaBuque,	false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("D",iRenglon,dVariableArqueo,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("E",iRenglon,dDiariaBuque,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("F",iRenglon,dNoEspecializados,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("G",iRenglon,dEspecializados,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("H",iRenglon,dArborloadas,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("I",iRenglon,dMuellaje,		false, rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("J",iRenglon,cOficio,	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    rep.comDespliegaAlineado("K",iRenglon,cFecha,	false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());	  
	                    
	                    
//	                    rep.comFillCells("A", iRenglon, "A", iRenglon, iColorFondo);
//	                    rep.comFillCells("B", iRenglon, "B", iRenglon, iColorBlanco);
//	                    rep.comFillCells("C", iRenglon, "C", iRenglon, iColorFondo);
//	                    rep.comFillCells("D", iRenglon, "D", iRenglon, iColorBlanco);
//	                    rep.comFillCells("E", iRenglon, "E", iRenglon, iColorFondo);
//	                    rep.comFillCells("F", iRenglon, "F", iRenglon, iColorBlanco);
//	                    rep.comFillCells("G", iRenglon, "G", iRenglon, iColorFondo);
//	                    rep.comFillCells("H", iRenglon, "H", iRenglon, iColorBlanco);
//	                    rep.comFillCells("I", iRenglon, "I", iRenglon, iColorFondo);
//	                    rep.comFillCells("J", iRenglon, "J", iRenglon, iColorBlanco);
//	                    rep.comFillCells("K", iRenglon, "K", iRenglon, iColorFondo);
	                    
	                    
//	                    cOficioAnterior = vDinRep.getString("CNUMEROTITULO");
	                    
	            	}	            	
	            	
//                	rep.comBordeRededor("B",iRenglonIni,"B",iRenglon-1,1,1);
//                	rep.comBordeTotal("D",iRenglonIni,"D",iRenglon-1,1);
//                	rep.comBordeTotal("F",iRenglonIni,"F",iRenglon-1,1);
//                	rep.comBordeRededor("H",iRenglonIni,"H",iRenglon-1,1,1);
//                	rep.comBordeRededor("J",iRenglonIni,"J",iRenglon-1,1,1);
	            	
	            	rep.comBordeTotal("B",iRenglonIni,"K",iRenglon-1,1);
	            	
//                	rep.comFillCells("A", iRenglon, "K", iRenglon, iColorFondo);
                	
	            	
                	sbRetorno = rep.getSbDatos();
	            }
	
	      }catch(Exception e){
	        e.printStackTrace();
	        cMensaje += e.getMessage();
	      }
	    if(!cMensaje.equals(""))
	      throw new Exception(cMensaje);
	    return sbRetorno;
	}

	
	
    /**
     * 
     * @param cFiltro
     * @param cOrden
     * @return
     * @throws DAOException
     */
    private Vector consultaServicio(String cFiltro, String cOrden) throws DAOException{
	/** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
	  Vector vcListado = this.findByCustom("iCveServicioCuota",
//    							0				1				2						
			  "SELECT sc.ICVESERVICIOCUOTA,sc.INUMTARIFA,sc.ICVEPUERTO,t.CNUMEROTITULO, t.DTFINTITULO, " +
			  "sc.LAUTORIZACION,sc.LREGISTRO,sc.DCUOTAPORSERVICIO,sc.CCLAVEDESREGULACION,sc.DTDESREGULACION, " +
			  "sc.D_TRB,sc.DTINICIOVIGENCIACUOTA,sc.DTTERMINOVIGENCIACUOTA,p.CDSCPUERTO, p.CDSCPUERTO || ', ' || e.CABREVIATURA as CPUERTOENTIDAD, " +
			  "e.CNOMBRE, t.ISERVICIOPORTUARIO, sc.DCUOTAFIJABUQUE, sc.DCUOTAVARIABLEARQUEOBRUTO, sc.DCUOTADIARIABUQUE, " +
			  "sc.DCUOTANOESPECIALIZADOS, sc.DCUOTAESPECIALIZADOS, sc.DCUOTAABARLOADAS, sc.DCUOTAMUELLAJE, sc.CMONEDATIPO, " +
			  "t.DTOFICIOSOLTARIFA, per.CNOMRAZONSOCIAL || ' ' || per.CAPPATERNO || ' ' || per.CAPMATERNO AS CSOLICITANTE, sc.CCLASIFICACION, t.ICVESOLICITANTE " +
			  "FROM TARSERVICIOCUOTA sc " +
			  "left join GRLPUERTO p on sc.ICVEPUERTO = p.ICVEPUERTO " +
			  "left join GRLENTIDADFED e on p.ICVEENTIDADFED = e.ICVEENTIDADFED AND e.ICVEPAIS = 1 " +
			  "join TARREGTARIFA t on sc.INUMTARIFA = t.INUMTARIFA " +
			  "join TRAREGSOLICITUD s on t.INUMSOLICITUD = s.INUMSOLICITUD and t.IEJERCICIO = s.IEJERCICIO " +
			  "join GRLPERSONA per ON per.iCvePersona=t.ICVESOLICITANTE "+
			cFiltro+cOrden);
	 
		NumberFormat numberFormat = new DecimalFormat("#,##0.00"); 
		NumberFormat numberFormatTRB = new DecimalFormat("#,##0.000"); //Pilotaje: "#,##0.000" (trb)
		
		
		for (int i=0;i<vcListado.size();i++){
			TVDinRep vTemp = (TVDinRep) vcListado.get(i);
			
			vTemp.put("CCUOTAFIJABUQUE",numberFormat.format(vTemp.getDouble("DCUOTAFIJABUQUE")));//27
			vTemp.put("CCUOTAVARIABLEARQUEOBRUTO",numberFormat.format(vTemp.getDouble("DCUOTAVARIABLEARQUEOBRUTO")));
			vTemp.put("CUOTADIARIABUQUE",numberFormat.format(vTemp.getDouble("DCUOTADIARIABUQUE")));
			
			vTemp.put("CCUOTANOESPECIALIZADOS",numberFormat.format(vTemp.getDouble("DCUOTANOESPECIALIZADOS"))); //30			
			vTemp.put("CCUOTAESPECIALIZADOS",numberFormat.format(vTemp.getDouble("DCUOTAESPECIALIZADOS")));		
			vTemp.put("CCUOTAMUELLAJE",numberFormat.format(vTemp.getDouble("DCUOTAMUELLAJE")));
			vTemp.put("CCUOTAPORSERVICIO",numberFormat.format(vTemp.getDouble("DCUOTAPORSERVICIO")));
			vTemp.put("C_TRB",numberFormatTRB.format(vTemp.getDouble("D_TRB")));
			vTemp.put("CCUOTAABARLOADAS",numberFormat.format(vTemp.getDouble("DCUOTAABARLOADAS"))+" %");
			vTemp.put("ICONSECUTIVO",i+1);
//			"CNUMEROTITULO") +"           "+vDinRep.getString("DTOFICIOSOLTARIFA"))
			if(vTemp.getString("DTOFICIOSOLTARIFA").equals("null")){
				vTemp.remove("DTOFICIOSOLTARIFA");
				vTemp.put("DTOFICIOSOLTARIFA", "");				
			}
		}
		
		
		return vcListado;
    }
    
    /**
     * 
     * @param cFiltro
     * @param cOrden
     * @return
     * @throws DAOException
     */
    private Vector consultaServicioCuota(String cFiltro, String cOrden) throws DAOException{
	/** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
	  Vector vcListado = this.findByCustom("iCveServicioCuota",
//    							0				1				2
			"SELECT u.ICVECUOTA,u.ICVESERVICIOCUOTA,u.ICVECUOTACONCEPTO,u.ICVECUOTARANGO,u.CCONCEPTO, " +
			"u.DCUOTA, uConcepto.CCONCEPTO as CCONCEPTORELACIONADO, r.IRANGOMINIMO, r.IRANGOMAXIMO, r.iRangoMinimo || ' - ' || r.iRangoMaximo as cRango, " +

			"sc.ICVESERVICIOCUOTA,sc.INUMTARIFA,sc.ICVEPUERTO,t.CNUMEROTITULO, t.DTFINTITULO, " +
			"sc.LAUTORIZACION,sc.LREGISTRO,sc.DCUOTAPORSERVICIO,sc.CCLAVEDESREGULACION,sc.DTDESREGULACION, " +
			"sc.D_TRB,sc.DTINICIOVIGENCIACUOTA,sc.DTTERMINOVIGENCIACUOTA,p.CDSCPUERTO, p.CDSCPUERTO || ', ' || e.CABREVIATURA as CPUERTOENTIDAD, " +
			"e.CNOMBRE, t.ISERVICIOPORTUARIO, sc.DCUOTAFIJABUQUE, sc.DCUOTAVARIABLEARQUEOBRUTO, sc.DCUOTADIARIABUQUE, " +
			"sc.DCUOTANOESPECIALIZADOS, sc.DCUOTAESPECIALIZADOS, sc.DCUOTAABARLOADAS, sc.DCUOTAMUELLAJE, sc.CMONEDATIPO, " +
			"t.DTOFICIOSOLTARIFA, p.CNOMRAZONSOCIAL || ' ' || p.CAPPATERNO || ' ' || p.CAPMATERNO AS CSOLICITANTE, sc.CCLASIFICACION " +
			"FROM TARCUOTA u " +
			"left join TARCUOTA uConcepto on u.ICVECUOTACONCEPTO = uConcepto.ICVECUOTA " +
			"left join TARCUOTARANGO r on u.ICVECUOTARANGO = r.ICVECUOTARANGO " +
			"left join TARSERVICIOCUOTA sc on u.ICVESERVICIOCUOTA = sc.ICVESERVICIOCUOTA " +
			"left join GRLPUERTO p on sc.ICVEPUERTO = p.ICVEPUERTO " +
			"left join GRLENTIDADFED e on p.ICVEENTIDADFED = e.ICVEENTIDADFED AND e.ICVEPAIS = 1 " +
			"join TARREGTARIFA t on sc.INUMTARIFA = t.INUMTARIFA " +
			"join TRAREGSOLICITUD s on t.INUMSOLICITUD = s.INUMSOLICITUD and t.IEJERCICIO = s.IEJERCICIO " +
			"join GRLPERSONA p ON p.iCvePersona=s.iCveSolicitante " +
			cFiltro+cOrden);
	 
		NumberFormat numberFormat = new DecimalFormat("#,##0.00"); 
		NumberFormat numberFormatTRB = new DecimalFormat("#,##0.000"); //Pilotaje: "#,##0.000" (trb)
		
		
		for (int i=0;i<vcListado.size();i++){
			TVDinRep vTemp = (TVDinRep) vcListado.get(i);
			
			vTemp.put("CCUOTA",numberFormat.format(vTemp.getDouble("DCUOTA")));//40
			vTemp.put("CCUOTAFIJABUQUE",numberFormat.format(vTemp.getDouble("DCUOTAFIJABUQUE")));
			vTemp.put("CCUOTAVARIABLEARQUEOBRUTO",numberFormat.format(vTemp.getDouble("DCUOTAVARIABLEARQUEOBRUTO")));
			vTemp.put("CUOTADIARIABUQUE",numberFormat.format(vTemp.getDouble("DCUOTADIARIABUQUE")));			
			vTemp.put("CCUOTANOESPECIALIZADOS",numberFormat.format(vTemp.getDouble("DCUOTANOESPECIALIZADOS")));
			
			vTemp.put("CCUOTAESPECIALIZADOS",numberFormat.format(vTemp.getDouble("DCUOTAESPECIALIZADOS")));//45		
			vTemp.put("CCUOTAMUELLAJE",numberFormat.format(vTemp.getDouble("DCUOTAMUELLAJE")));
			vTemp.put("CCUOTAPORSERVICIO",numberFormat.format(vTemp.getDouble("DCUOTAPORSERVICIO")));
			vTemp.put("C_TRB",numberFormatTRB.format(vTemp.getDouble("D_TRB")));
			vTemp.put("CCUOTAABARLOADAS",numberFormat.format(vTemp.getDouble("DCUOTAABARLOADAS"))+" %");
			
			vTemp.put("ICONSECUTIVO",i+1);//50
			
			if(vTemp.getString("DTOFICIOSOLTARIFA").equals("null")){
				vTemp.remove("DTOFICIOSOLTARIFA");
				vTemp.put("DTOFICIOSOLTARIFA", "");				
			}
		}
		
		
		return vcListado;
    }
    
    
    /**
     * 
     * @param cFiltro
     * @param cOrden
     * @return
     * @throws DAOException
     */
    private Vector consultaRemolcador(String cFiltro, String cOrden) throws DAOException{
	/** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
	  Vector vcListado = this.findByCustom("iCveServicioCuota",
//    							0				1				2						
			  "SELECT r.iCveRemolcador,r.iCveServicioCuota,r.cNombre,r.iPotencia "+
				"FROM TARREMOLCADOR r "+ 
			cFiltro+cOrden);
		
		return vcListado;
    }
    
    

}

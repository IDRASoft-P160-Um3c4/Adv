package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import com.micper.util.*;
import java.sql.SQLException;
import java.util.Vector;


/**
 * <p>Title: TDTramite.java</p>
 * <p>Description: DAO de reportes generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Armando Barrientos Martínez
 * @version 1.0
 */

public class TDSOC extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  public TDSOC(){
  }

  public StringBuffer repSOC(String cValores)throws Exception{
    TExcel rep = new TExcel();
    TFechas fecha = new TFechas();
    String [] aValores = cValores.split(",");
    //0-Ejercicio supervision, 1-Solicitud Supervision, 2-Num Supervision
    //3-Oficina, 4-Inicio Supervision, 5-Supervisor
    String cEjercicioSup = aValores[0];
    String cCveSolicitudSupervision = aValores[1];
    String cNumSupervision = aValores[2];
    String cOficina = aValores[3];
    String cFecha = aValores[4];
    String cSupervisor = aValores[5];
    String cComentario = "";
    Vector vRegs = new Vector();
    java.sql.Date dtTemp = null;

    int iContCom=0;

    String cSQL = "SELECT ss.IEJERCICIOSUPERVISION, ss.ICVESOLICITUDSUPERVISION, ts.ICVEAREA, ts.ICVETEMA, a.CDSCAREA, t.CDSCTEMA, " +
        "       com.COBSREGISTRADA " +
        "FROM SOCSUPERVISION ss " +
        "JOIN SOCTEMAXSUPERVISION ts ON ts.IEJERCICIOSUPERVISION = ss.IEJERCICIOSUPERVISION AND ts.ICVESOLICITUDSUPERVISION = ss.ICVESOLICITUDSUPERVISION " +
        "JOIN SOCAREA a ON a.ICVEAREA = ts.ICVEAREA " +
        "JOIN SOCTEMA t ON t.ICVETEMA = ts.ICVETEMA " +
        "LEFT JOIN SOCCOMENTARIOXSUP sc ON sc.IEJERCICIOSUPERVISION = ss.IEJERCICIOSUPERVISION AND sc.ICVESOLICITUDSUPERVISION = ss.ICVESOLICITUDSUPERVISION AND sc.ICVEAREA = ts.ICVEAREA AND sc.ICVETEMA = ts.ICVETEMA " +
        "LEFT JOIN GRLOBSERVACION com ON com.IEJERCICIO = sc.IEJERCICIOOBSERVACION AND com.ICVEOBSERVACION = sc.ICVEOBSERVACION " +
        "WHERE ss.IEJERCICIOSUPERVISION = "+cEjercicioSup+" AND ss.ICVESOLICITUDSUPERVISION = "+cCveSolicitudSupervision+" " +
        "ORDER BY ts.ICVEAREA ASC ";

     vRegs = super.FindByGeneric("",cSQL,dataSourceName);

     rep.iniciaReporte();
     rep.comDespliega("E",7,cNumSupervision);
     rep.comDespliega("E",12,cOficina);
     if(!cFecha.equals(""))
       rep.comDespliega("E",15, fecha.getDateSPN(fecha.getDateSQL(cFecha)));
     rep.comDespliega("E",22,cSupervisor);
     int iRenglonInicia=32, iRengTemp=0;
     int iLength=0, iTotalCaracteresReng=50,iMod=0;
     if(vRegs.size()>0){
       TVDinRep vDatos = new TVDinRep();
       vDatos = (TVDinRep)vRegs.get(0);
       int iAreaAnt= 0, iTemaAnt=0;
       for(int iReg=0;iReg<vRegs.size();iReg++){
         vDatos = (TVDinRep) vRegs.get(iReg);
         if(vDatos.getInt("iCveArea")!=iAreaAnt){

           if(iReg>0)
             iRenglonInicia++;
           //Pinta la descripción del área
           rep.comDespliegaCombinado(vDatos.getString("CDSCAREA"),"A",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),
                                     rep.getAT_VCENTRO(),true,48,true,true,1,1);
           iRenglonInicia++;

           //Combina de la A a la E y pinta "CONCEPTO A SUPERVISAR"
           rep.comDespliegaCombinado("CONCEPTO A SUPERVISAR","A",iRenglonInicia,"E",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),
                                     rep.getAT_VCENTRO(),true,15,true,true,1,1);
           //Combina de la F a la K y pinta "COMETARIOS"
           rep.comDespliegaCombinado("COMENTARIOS","F",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),
                                     rep.getAT_VCENTRO(),true,15,true,true,1,1);
           iRenglonInicia++;

         }

         //Pinta la descripción del tema, si hay más de un comentario por tema solo pinta una vez el tema y las siguients combina la celda
         if(vDatos.getInt("iCveTema")!=iTemaAnt){
           iLength = vDatos.getString("cDscTema").length()/iTotalCaracteresReng;
           iMod = vDatos.getString("cDscTema").length()%iTotalCaracteresReng;
           iLength += -1;
           if(iMod>0)
             iLength++;
           if(iLength>0){
             iRengTemp = iRenglonInicia + iLength;
           }
          else
            iRengTemp=iRenglonInicia;
           rep.comDespliegaCombinado(vDatos.getString("cDscTema"),"A",iRenglonInicia,"E",iRengTemp,
                                     rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,true,1,1);
           //Pinta el comentario relacionado con el tema
           if(vDatos.getString("cObsRegistrada").equals("null"))
             cComentario = "";
           else
             cComentario = vDatos.getString("cObsRegistrada");
           //System.out.println("***********Tema: "+vDatos.getInt("iCveTema")+" **********");
           //System.out.println("iRenglonInicia: "+iRenglonInicia+"  iRengTemp: "+iRengTemp);
           //System.out.println(cComentario);
           rep.comDespliegaCombinado(cComentario,"F",iRenglonInicia,"K",iRengTemp,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VSUPERIOR(),false,0,true,true,1,1);

           iContCom++;

         }
         else{
           iContCom++;
           if(iRenglonInicia>iRengTemp){
             rep.comAlineaRango("A",iRenglonInicia - 1,"E",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA());
             rep.comBordeTotal("A",iRenglonInicia - 1,"E",iRenglonInicia,1);
           }
           if(vDatos.getString("cObsRegistrada").equals("null"))
             cComentario = "";
           else
             cComentario = vDatos.getString("cObsRegistrada");
             //System.out.println("--------mas de una obsrvacion del mismo tema "+vDatos.getInt("iCveTema")+" --------------");
             //System.out.println(cComentario);
             //System.out.println("iRenglonInicia: "+iRenglonInicia);
           rep.comDespliegaCombinado(cComentario,"F",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VSUPERIOR(),false,0,true,true,1,1);

         }

         if(iRengTemp>iRenglonInicia)
           iRenglonInicia = iRengTemp;

         iAreaAnt = vDatos.getInt("iCveArea");
         iTemaAnt = vDatos.getInt("iCveTema");
         iRenglonInicia++;
         //System.out.println("iRenglonInicia del fianal del ciclo: "+iRenglonInicia);
         //System.out.println("--                                             --");
       }

       iRenglonInicia++;
       //Agrega tabla de cierre de visita de supervisión
       rep.comDespliegaCombinado("CIERRE DE VISITA DE SUPERVISION","A",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),
                                     rep.getAT_VCENTRO(),true,0,false,false,1,1);
       iRenglonInicia++;
       rep.comDespliegaCombinado(cOficina,"A",iRenglonInicia,"E",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,48,true,true,1,1);
       rep.comDespliegaCombinado("DIRECCIÓN DE SUPERVISIÓN","F",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,48,true,true,1,1);
       iRenglonInicia++;
       rep.comDespliegaCombinado("","A",iRenglonInicia,"E",iRenglonInicia+2,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,0,true,true,1,1);
       rep.comDespliegaCombinado("","F",iRenglonInicia,"K",iRenglonInicia+2,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,0,true,true,1,1);
       /*rep.comBordeRededor("A",iRenglonInicia,"E",iRenglonInicia+2,1,1);
       rep.comBordeRededor("F",iRenglonInicia,"K",iRenglonInicia+2,1,1);*/
       iRenglonInicia+=3;
       rep.comDespliegaCombinado("","A",iRenglonInicia,"E",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,0,true,true,1,1);
       rep.comDespliegaCombinado("","F",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,0,true,true,1,1);
       iRenglonInicia++;
       rep.comDespliegaCombinado("CAPITAN DE PUERTO","A",iRenglonInicia,"E",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,15,true,true,1,1);
       rep.comDespliegaCombinado("SUPERVISOR","F",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,15,true,true,1,1);


       //Inicia lo de casa habitación

       cSQL = "SELECT si.IEJERCICIOSUPERVISION, si.ICVESOLICITUDSUPERVISION, si.ICVEOFICINA, si.ICVEINMUEBLE, " +
           "       si.INUMHABITANTES, si.CPARENTESCOFUNCIONARIO, si.COTROSPAGOSSERV, si.CNOMFUNCIONARIO, " +
           "       si.CCARGOFUNCIONARIO, si.DTRECEPCIONINMUEBLE, si.DTQUEHABITARON, si.CNUMALOJAMIENTOS, " +
           "       si.LTIENEGARAGE, si.LTIENECISTERNA, si.LTIENETINACO, si.LTIENEBODEGA, si.LTIENEJARDIN, " +
           "       si.CCONSTRUIDAEN, si.INUMPISOS, ri.LUBICADOOFICINA " +
           "FROM SOCSUPERVISIONINMUEBLE si " +
           "JOIN RINREGINMUEBLE ri ON ri.ICVEINMUEBLE = si.ICVEINMUEBLE " +
           "WHERE si.IEJERCICIOSUPERVISION = "+cEjercicioSup+" and si.ICVESOLICITUDSUPERVISION = "+cCveSolicitudSupervision+" ";


        vRegs = super.FindByGeneric("",cSQL,dataSourceName);

       iRenglonInicia+=5;

       if(vRegs.size()>0){
         TVDinRep vDatosInm = new TVDinRep();
         Vector vRegsOfic = new Vector();
         for(int contInm=0;contInm<vRegs.size();contInm++){ //inmuebles
           vDatosInm = (TVDinRep)vRegs.get(contInm);
           String cSupCasaHabitacion = "SUPERVISIÓN DE LAS CASAS-HABITACIÓN, PROPIEDAD DE LA SECRETARÍA DE COMUNICACIÓNES Y TRANSPORTES QUE SE ENCUENTRAN EN LAS DIFERENTES CAPITANÍAS Y DELEGACIONES DE CAPITANÍAS DE PUERTO DEL PAÍS.";
           rep.comDespliegaCombinado(cSupCasaHabitacion,"A",iRenglonInicia,"K",iRenglonInicia + 3,
                                     rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,0,true,true,1,1);
           iRenglonInicia += 4;

           //si vDatosInm.getInt("lUbicadoOficina")==1 es que la dirección del inmueble es la misma que la de la capitanía
           if(vDatosInm.getInt("lUbicadoOficina")==1){
             cSQL = "SELECT o.CCALLEYNO, o.CCOLONIA, o.CCODPOSTAL, o.ICVEENTIDADFED, o.ICVEMUNICIPIO, " +
                 "       ef.CNOMBRE as cEntidad " +
                 "FROM GRLOFICINA o " +
                 "JOIN GRLENTIDADFED ef ON ef.ICVEPAIS = 1 AND ef.ICVEENTIDADFED = o.ICVEENTIDADFED " +
                 "WHERE o.ICVEOFICINA = " + vDatosInm.getInt("ICVEOFICINA");
           }
           else{
             cSQL = "SELECT ri.ICVECOMUNDOMICILIO, cd.CCALLE || ' ' || cd.CNUMEXTERIOR as CCALLEYNO, cd.CNUMINTERIOR, " +
                 "       cd.CCOLONIA, cd.CCODPOSTAL,cd.ICVEENTIDADFED, cd.ICVEMUNICIPIO, cd.ICVELOCALIDAD, " +
                 "       ef.CNOMBRE as cEntidad " +
                 "FROM RINDOMXINMUEBLE ri " +
                 "JOIN GRLCOMUNDOMICILIO cd ON cd.ICVECOMUNDOMICILIO = ri.ICVECOMUNDOMICILIO " +
                 "JOIN GRLENTIDADFED ef ON ef.ICVEPAIS = 1 AND ef.ICVEENTIDADFED = cd.ICVEENTIDADFED " +
                 "WHERE ri.LPREDETERMINADO = 1 AND ri.ICVEREGISTROINMUEBLE = " + vDatosInm.getInt("ICVEINMUEBLE");

           }

           vRegsOfic = super.FindByGeneric("",cSQL,dataSourceName);
           if(vRegsOfic.size()>0){
             TVDinRep vDatosOfic = (TVDinRep)vRegsOfic.get(0);
             //----------------------------------------
             rep.comDespliegaCombinado("UBICACIÓN DE LA CASA HABITACIÓN","A",iRenglonInicia,"K",iRenglonInicia,
                                       rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,true,1,1);
             iRenglonInicia++;

             rep.comDespliegaAlineado("A",iRenglonInicia,"CALLE:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             rep.comDespliegaCombinado(vDatosOfic.getString("CCALLEYNO"),"B",iRenglonInicia,"F",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             rep.comDespliegaAlineado("G",iRenglonInicia,"COLONIA:",true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
             rep.comDespliegaCombinado(vDatosOfic.getString("CCOLONIA"),"H",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia+=2;
             rep.comDespliegaAlineado("A",iRenglonInicia,"CIUDAD:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             rep.comDespliegaCombinado("","B",iRenglonInicia,"D",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             rep.comDespliegaCombinado("ENTIDAD FEDERATIVA:","E",iRenglonInicia,"F",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),true,0,false,false,1,1);
             rep.comDespliegaCombinado(vDatosOfic.getString("cEntidad"),"G",iRenglonInicia,"H",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             rep.comDespliegaAlineado("I",iRenglonInicia,"C.P.:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             rep.comDespliegaCombinado(vDatosOfic.getString("CCODPOSTAL"),"J",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia++;
             rep.comBordeRededor("A",iRenglonInicia-4,"K",iRenglonInicia,1,1);
             //----------------------------------------
             iRenglonInicia++;
             rep.comDespliegaCombinado("DATOS DEL FUNCIONARIO QUE TIENE BAJO SU RESGUARDO LA CASA HABITACIÓN.","A",iRenglonInicia,"K",iRenglonInicia,
                                       rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,true,1,1);
             iRenglonInicia++;
             rep.comDespliegaAlineado("A",iRenglonInicia,"NOMBRE:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             rep.comDespliegaCombinado(vDatosInm.getString("CNOMFUNCIONARIO"),"B",iRenglonInicia,"F",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             rep.comDespliegaAlineado("G",iRenglonInicia,"CARGO:",true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
             rep.comDespliegaCombinado(vDatosInm.getString("CCARGOFUNCIONARIO"),"H",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia+=2;
             rep.comDespliegaCombinado("FECHA DE RECEPCIÓN DEL INMUEBLE:","A",iRenglonInicia,"D",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),true,0,false,false,1,1);
             dtTemp = vDatosInm.getDate("DTRECEPCIONINMUEBLE");
             rep.comDespliegaCombinado((dtTemp!=null?fecha.getDateSPN(dtTemp):""),"E",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia++;
             rep.comBordeRededor("A",iRenglonInicia-3 ,"K",iRenglonInicia,1,1);
             //----------------------------------------
             iRenglonInicia++;
             rep.comDespliegaCombinado("PERSONAS QUE HABITAN EN LA CASA.","A",iRenglonInicia,"K",iRenglonInicia,
                                       rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,true,1,1);
             iRenglonInicia++;
             rep.comDespliegaCombinado("NÚMERO DE PERSONAS QUE LA HABITAN:","A",iRenglonInicia,"D",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),true,0,false,false,1,1);
             rep.comDespliegaCombinado(vDatosInm.getString("INUMHABITANTES"),"E",iRenglonInicia,"F",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             rep.comDespliegaCombinado("FECHA EN QUE LA HABITARON:","E",iRenglonInicia,"G",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),true,0,false,false,1,1);
             dtTemp = vDatosInm.getDate("DTQUEHABITARON");
             rep.comDespliegaCombinado((dtTemp!=null?fecha.getDateSPN(dtTemp):""),"H",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia+=2;
             rep.comDespliegaCombinado("PARENTESCO QUE TIENEN CON EL FUNCIONARIO A CARGO:","A",iRenglonInicia,"F",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),true,0,false,false,1,1);
             rep.comDespliegaCombinado(vDatosInm.getString("CPARENTESCOFUNCIONARIO"),"G",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia++;
             rep.comBordeRededor("A",iRenglonInicia-4 ,"K",iRenglonInicia,1,1);
             //----------------------------------------
             iRenglonInicia++;
             rep.comDespliegaCombinado("DESCRIPCIÓN DE LA CASA HABITACIÓN.","A",iRenglonInicia,"K",iRenglonInicia,
                                       rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,true,1,1);
             iRenglonInicia++;
             rep.comDespliegaCombinado("NÚMERO DE ALOJAMIENTOS CON QUE CUENTA (SALA, COMEDOR, RECÁMARAS, BAÑOS, ETC.)","A",iRenglonInicia,"D",iRenglonInicia+1,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),true,0,false,false,1,1);
             iRenglonInicia++;
             rep.comDespliegaCombinado(vDatosInm.getString("CNUMALOJAMIENTOS"),"E",iRenglonInicia,"F",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia+=2;
             String cSiNo="";
             rep.comDespliegaAlineado("A",iRenglonInicia,"GARAGE:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             cSiNo = (vDatosInm.getInt("LTIENEGARAGE")==1?"SI":"NO");
             rep.comDespliegaAlineado("B",iRenglonInicia,cSiNo,false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());

             rep.comDespliegaAlineado("C",iRenglonInicia,"CISTERNA:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             cSiNo = (vDatosInm.getInt("LTIENECISTERNA")==1?"SI":"NO");
             rep.comDespliegaAlineado("D",iRenglonInicia,cSiNo,false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());

             rep.comDespliegaAlineado("E",iRenglonInicia,"TINACO:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             cSiNo = (vDatosInm.getInt("LTIENETINACO")==1?"SI":"NO");
             rep.comDespliegaAlineado("F",iRenglonInicia,cSiNo,false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());

             rep.comDespliegaAlineado("G",iRenglonInicia,"BODEGA:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             cSiNo = (vDatosInm.getInt("LTIENEBODEGA")==1?"SI":"NO");
             rep.comDespliegaAlineado("H",iRenglonInicia,cSiNo,false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());

             rep.comDespliegaAlineado("I",iRenglonInicia,"JARDIN:",true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
             cSiNo = (vDatosInm.getInt("LTIENEJARDIN")==1?"SI":"NO");
             rep.comDespliegaAlineado("K",iRenglonInicia,cSiNo,false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
             iRenglonInicia+=2;
             rep.comDespliegaCombinado("LA CASA ESTA CONSTRUIDA EN:","A",iRenglonInicia,"C",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),true,0,false,false,1,1);
             rep.comDespliegaCombinado(vDatosInm.getString("CCONSTRUIDAEN"),"D",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia+=2;
             rep.comDespliegaCombinado("NÚMERO DE PISOS:","A",iRenglonInicia,"B",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),true,0,false,false,1,1);
             rep.comDespliegaCombinado(vDatosInm.getString("INUMPISOS"),"C",iRenglonInicia,"D",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                       rep.getAT_VCENTRO(),false,0,false,false,1,1);
             iRenglonInicia++;
             rep.comBordeRededor("A",iRenglonInicia-8 ,"K",iRenglonInicia,1,1);

             iRenglonInicia+=3;

             //----Condiciones generales del inmueble
             Vector vRegsCondInm = new Vector();
             cSQL = " Select SOCCondicionXSupervision.iEjercicioSupervision, " +
                 "     SOCCondicionXSupervision.iCveSolicitudSupervision, " +
                 "     SOCCondicionXSupervision.iCveOficina, " +
                 "     SOCCondicionXSupervision.iCveInmueble, " +
                 "     SOCCondicionXSupervision.iCveCondicion, " +
                 "     SOCCondicionesEvaluar.cDscCondicion, " +
                 "     SOCCondicionXSupervision.iCveNivel, " +
                 "     CVHNivelCalificacion.cDscNivel " +
                 "    from SOCCondicionXSupervision " +
                 "    join SOCSupervisionInmueble on SOCCondicionXSupervision.iEjercicioSupervision = SOCSupervisionInmueble.iEjercicioSupervision " +
                 "         and SOCCondicionXSupervision.iCveSolicitudSupervision = SOCSupervisionInmueble.iCveSolicitudSupervision " +
                 "         and SOCCondicionXSupervision.iCveOficina = SOCSupervisionInmueble.iCveOficina " +
                 "         and SOCCondicionXSupervision.iCveInmueble = SOCSupervisionInmueble.iCveInmueble " +
                 "    join SOCCondicionesEvaluar on SOCCondicionXSupervision.iCveCondicion = SOCCondicionesEvaluar.iCveCondicion " +
                 "         and  SOCCondicionesEvaluar.lVigente = 1 " +
                 "  LEFT join CVHNivelCalificacion on SOCCondicionXSupervision.iCveNivel = CVHNivelCalificacion.iCveNivel " +
                 "WHERE SOCCondicionXSupervision.iEjercicioSupervision = "+cEjercicioSup+" AND SOCCondicionXSupervision.iCveSolicitudSupervision = "+cCveSolicitudSupervision+
                 " AND SOCCondicionXSupervision.iCveInmueble = "+vDatosInm.getInt("ICVEINMUEBLE");

             vRegsCondInm = super.FindByGeneric("",cSQL,dataSourceName);
             boolean lBueno =false, lRegular=false, lMalo=false;
             if(vRegsCondInm.size()>0){

               rep.comDespliegaCombinado("CONDICIONES GENERALES DEL INMUEBLE","A",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),
                                     rep.getAT_VCENTRO(),true,48,true,true,1,1);
               iRenglonInicia++;
               for(int contCond=0;contCond<vRegsCondInm.size();contCond++){
                 lBueno =false; lRegular=false; lMalo=false;
                 TVDinRep vDatosCondInm = (TVDinRep) vRegsCondInm.get(contCond);
                 rep.comDespliegaCombinado(vDatosCondInm.getString("cDscCondicion"),"A",iRenglonInicia,"E",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                           rep.getAT_VCENTRO(),false,0,true,true,1,1);
                 if(vDatosCondInm.getInt("iCveNivel")==1)
                   lBueno=true;
                 else if(vDatosCondInm.getInt("iCveNivel")==2)
                   lRegular =true;
                 else if(vDatosCondInm.getInt("iCveNivel")==3)
                   lMalo=true;
                //"BUENO"+(lBueno?"(X):()")
                 rep.comDespliegaCombinado("BUENO"+(lBueno?"(X)":"(  )"),"F",iRenglonInicia,"G",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),
                                           false,0,true,true,1,1);
                 rep.comDespliegaCombinado("REGULAR"+(lRegular?"(X)":"(  )"),"H",iRenglonInicia,"I",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),
                                           false,0,true,true,1,1);
                 rep.comDespliegaCombinado("MALA"+(lMalo?"(X)":"(  )"),"J",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),
                                           false,0,true,true,1,1);
                 iRenglonInicia++;
               }
               iRenglonInicia+=2;

               Vector vRegsPagInm = new Vector();
               cSQL = "SELECT " +
                        "  SOCPAGOSERVXSUPERVISION.IEJERCICIOSUPERVISION, " + //0
                        "  SOCPAGOSERVXSUPERVISION.ICVESOLICITUDSUPERVISION, " +
                        "  SOCPAGOSERVXSUPERVISION.ICVEOFICINA, " +
                        "  SOCPAGOSERVXSUPERVISION.ICVEINMUEBLE, " +
                        "  SOCPAGOSERVXSUPERVISION.ICVESERVICIO, " +
                        "  SOCPAGOSERVXSUPERVISION.LPAGADO, " +  //5
                        "  SOCPAGOSERVICIOREVISAR.CDSCSERVICIO " +
                        "FROM  SOCPAGOSERVXSUPERVISION " +
                        "JOIN SOCPAGOSERVICIOREVISAR ON SOCPAGOSERVICIOREVISAR.ICVESERVICIO = SOCPAGOSERVXSUPERVISION.ICVESERVICIO " +
                        "WHERE SOCPAGOSERVXSUPERVISION.IEJERCICIOSUPERVISION = " + cEjercicioSup +
                        " AND  SOCPAGOSERVXSUPERVISION.ICVESOLICITUDSUPERVISION = " + cCveSolicitudSupervision +
                        " AND  SOCPAGOSERVXSUPERVISION.ICVEINMUEBLE = " + vDatosInm.getInt("ICVEINMUEBLE");

               vRegsPagInm = super.FindByGeneric("",cSQL,dataSourceName);
               boolean lPagado=false;
               if(vRegsPagInm.size()>0){
                 rep.comDespliegaCombinado("PAGO DE SERVICIOS PÚBLICOS:","A",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),
                                     rep.getAT_VCENTRO(),true,48,true,true,1,1);
                 iRenglonInicia++;
                 for(int contPag=0;contPag<vRegsPagInm.size();contPag++){ //pagos
                   lPagado = false;
                   TVDinRep vDatosPag = (TVDinRep)vRegsPagInm.get(contPag);

                   if(vDatosPag.getInt("LPAGADO")==1)
                     lPagado = true;
                   rep.comDespliegaCombinado((lPagado?"SI":""),"A",iRenglonInicia,"B",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),
                                             rep.getAT_VCENTRO(),true,0,true,true,1,1);
                   rep.comDespliegaCombinado((!lPagado?"NO":""),"C",iRenglonInicia,"D",iRenglonInicia,rep.getAT_COMBINA_CENTRO(),
                                             rep.getAT_VCENTRO(),true,0,true,true,1,1);
                   rep.comDespliegaCombinado(vDatosPag.getString("CDSCSERVICIO"),"E",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                             rep.getAT_VCENTRO(),false,0,true,true,1,1);
                   iRenglonInicia++;
                 }
                 iRenglonInicia+=3;
               }//de pagos



             }
           }

         } //de inmuebles
       }

       rep.comDespliegaCombinado("LUGAR Y FECHA:","A",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VCENTRO(),true,48,true,true,1,1);
       iRenglonInicia++;
       rep.comDespliegaCombinado("BENEFICIARIO","A",iRenglonInicia,"E",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VCENTRO(),true,48,true,true,1,1);
       rep.comDespliegaCombinado("DIRECCIÓN DE SUPERVISIÓN OPERATIVA DE CAPITANÍAS","F",iRenglonInicia,"K",iRenglonInicia,rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VCENTRO(),true,48,true,true,1,1);
       iRenglonInicia++;

       rep.comDespliegaCombinado("NOMBRE:","A",iRenglonInicia,"E",iRenglonInicia+3,rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VSUPERIOR(),true,0,true,true,1,1);
       rep.comDespliegaCombinado("NOMBRE:","F",iRenglonInicia,"K",iRenglonInicia+3,rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VSUPERIOR(),true,0,true,true,1,1);
       iRenglonInicia+=4;
       rep.comDespliegaCombinado("CARGO:","A",iRenglonInicia,"E",iRenglonInicia+3,rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VSUPERIOR(),true,0,true,true,1,1);
       rep.comDespliegaCombinado("CARGO:","F",iRenglonInicia,"K",iRenglonInicia+3,rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VSUPERIOR(),true,0,true,true,1,1);


     }
    return rep.getSbDatos();
  }

  public StringBuffer genReqInfODoc(String cValores) throws Exception{
    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TParametro  vParametros = new TParametro("44");
    TWord rep = new TWord();
    rep.iniciaReporte();
    //rep.comRemplaza("[cNombrePropietario]","");
    return rep.getEtiquetas(true);
  }


  public StringBuffer genNotifCumpCapacit(String cValores) throws Exception{
    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TParametro  vParametros = new TParametro("44");
    TWord rep = new TWord();
    rep.iniciaReporte();
    //rep.comRemplaza("[cNombrePropietario]","");
    return rep.getEtiquetas(true);
  }


  public StringBuffer genNombramientoDelHon (String cValores) throws Exception{
    StringBuffer sbResBuscar = new StringBuffer("");
    StringBuffer sbResReemplazar = new StringBuffer("");
    TParametro  vParametros = new TParametro("44");
    TWord rep = new TWord();
    rep.iniciaReporte();
    //rep.comRemplaza("[cNombrePropietario]","");
    return rep.getEtiquetas(true);
  }




}




















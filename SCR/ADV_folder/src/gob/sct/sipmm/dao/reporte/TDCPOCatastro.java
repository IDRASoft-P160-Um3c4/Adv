package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import gob.sct.sipmm.dao.*;
import java.sql.SQLException;
import com.micper.excepciones.DAOException;

/**
 * <p>Title: TDCPOCatastro.java</p>
 * <p>Description: DAO con métodos para reportes de POA</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */

public class TDCPOCatastro extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private int iEjercicio = 0;
  private int iCvePuerto = 0;
  private int lObrasPro = 0;
  private int lAreasAgua = 0;
  private int lObrasAtra = 0;
  private int lAreasAlma = 0;
  private int lEdificios = 0;
  TExcel rep = new TExcel();
  private String cTitulo;

  public TDCPOCatastro(){
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
     return vcRecords;
   }
}
/*Metodo sonfigurado en GRLReporte, ejecuta el reporte por puerto*/
 public StringBuffer GenCatastro(String cSolicitud) throws Exception{
   StringBuffer sbRetorno = new StringBuffer("");
   String cRutaDefUser = "", cPuerto = "", cAbrevEF = "", cLitoral = "";

   String[] aSolicitudes = cSolicitud.split(",");

   iEjercicio = Integer.parseInt(aSolicitudes[0],10);
   iCvePuerto = Integer.parseInt(aSolicitudes[1],10);
   lObrasPro = Integer.parseInt(aSolicitudes[2],10);
   lAreasAgua = Integer.parseInt(aSolicitudes[3],10);
   lObrasAtra = Integer.parseInt(aSolicitudes[4],10);
   lAreasAlma = Integer.parseInt(aSolicitudes[5],10);
   lEdificios = Integer.parseInt(aSolicitudes[6],10);

   if(aSolicitudes.length > 8)
     cRutaDefUser = aSolicitudes[8];




   try{
     rep.iniciaReporte();
     // Titulo de Reporte

     Vector vcData = findByCustom("","SELECT PTO.CDSCPUERTO || ', ' || ENT.CABREVIATURA as Titulo, " +
                                  "PTO.CDSCPUERTO as cPuerto, " +
                                  "ENT.CABREVIATURA as cAbrev, " +
                                  "LIT.CDSCLITORAL as cLit " +
                                  "FROM GRLPUERTO PTO " +
                                  "join GRLENTIDADFED ENT on ENT.ICVEPAIS = ENT.ICVEPAIS and PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED " +
                                  "join GRLLITORAL LIT on PTO.ICVELITORAL = LIT.ICVELITORAL " +
                                  "where ICVEPUERTO =  "+iCvePuerto);

     TVDinRep vTitulo = (TVDinRep) vcData.get(0);
     cTitulo = vTitulo.getString("Titulo");
     cLitoral = vTitulo.getString("cLit");
     cAbrevEF = vTitulo.getString("cAbrev");
     cPuerto = vTitulo.getString("cPuerto");

     if(lObrasPro == 1 && lAreasAgua == 1 && lObrasAtra == 1 && lAreasAlma == 1 && lEdificios == 1){
       sbRetorno.append(GenObras(iEjercicio,iCvePuerto,cTitulo));
       sbRetorno.append(GenAreasAgua(iEjercicio,iCvePuerto,cTitulo));
       sbRetorno.append(GenObrasAtraque(iEjercicio,iCvePuerto,cTitulo));
       sbRetorno.append(GenAreasAlmacenamiento(iEjercicio,iCvePuerto,cTitulo));
       sbRetorno.append(GenEdificios(iEjercicio,iCvePuerto,cTitulo));
       sbRetorno.append(insertaImagen(iCvePuerto,cTitulo));
//       sbRetorno.append(guardaEntidad(iCveEntidadFed));
     }else{
       if(lObrasPro == 1)
         sbRetorno.append(GenObras(iEjercicio,iCvePuerto,cTitulo));
       if(lAreasAgua == 1)
         sbRetorno.append(GenAreasAgua(iEjercicio,iCvePuerto,cTitulo));
       if(lObrasAtra == 1)
         sbRetorno.append(GenObrasAtraque(iEjercicio,iCvePuerto,cTitulo));
       if(lAreasAlma == 1)
         sbRetorno.append(GenAreasAlmacenamiento(iEjercicio,iCvePuerto,cTitulo));
       if(lEdificios == 1)
         sbRetorno.append(GenEdificios(iEjercicio,iCvePuerto,cTitulo));

       sbRetorno.append(insertaImagen(iCvePuerto,cTitulo));
     }

     rep.comGuardarLibro(cRutaDefUser+cLitoral+"_"+cAbrevEF+"_"+cPuerto);
     //Aca va a ir el cerrar el libro
     sbRetorno.append(rep.getSbDatos());

   }catch(Exception e){
     e.printStackTrace();
     cMensaje += e.getMessage();
   }
   if(!cMensaje.equals(""))
     throw new Exception(cMensaje);
   return sbRetorno;
 }
/*Metodo configurado en GRLReporte, ejecuta el reporte por Año*/
 public StringBuffer GenCatastroEjercicio(String cSolicitud) throws Exception{
   StringBuffer sbRetorno = new StringBuffer("");
   int iCveEntidadFed;
   String cLitoral = "", cAbrevEF = "", cPuerto = "", cNombreED = "",cRutaDefUser = "";
   String[] aSolicitudes = cSolicitud.split(",");

   iEjercicio = Integer.parseInt(aSolicitudes[0],10);
   iCvePuerto = Integer.parseInt(aSolicitudes[1],10);
   lObrasPro = Integer.parseInt(aSolicitudes[2],10);
   lAreasAgua = Integer.parseInt(aSolicitudes[3],10);
   lObrasAtra = Integer.parseInt(aSolicitudes[4],10);
   lAreasAlma = Integer.parseInt(aSolicitudes[5],10);
   lEdificios = Integer.parseInt(aSolicitudes[6],10);
   iCveEntidadFed = Integer.parseInt(aSolicitudes[7],10);
   if(aSolicitudes.length > 8)
     cRutaDefUser = aSolicitudes[8];
   try{
     rep.iniciaReporte();
     // Titulo de Reporte
     Vector vcDataEntidades = findByCustom("","SELECT " +
                                           "EF.ICVEENTIDADFED, " +
                                           "EF.CNOMBRE, " +
                                           "EF.CABREVIATURA " +
                                           "FROM GRLENTIDADFEDXLITORAL EFL " +
                                           "join GRLENTIDADFED EF on EFL.ICVEPAIS = EF.ICVEPAIS " +
                                           "and EFL.ICVEENTIDADFED = EF.ICVEENTIDADFED " +
                                           "where EFL.ICVEPAIS = 1 and EFL.iCveEntidadFed > 0 ");

     for(int i = 0; i < vcDataEntidades.size(); i++){

       TVDinRep vEntidades = (TVDinRep) vcDataEntidades.get(i);
       iCveEntidadFed = vEntidades.getInt("ICVEENTIDADFED");
       cAbrevEF = vEntidades.getString("CABREVIATURA");
       cNombreED = vEntidades.getString("CNOMBRE");

       Vector vcPuertoxEntidad = findByCustom("","SELECT " +
                                              "PTO.ICVEPUERTO, " +
                                              "PTO.CDSCPUERTO || ', ' || ENT.CABREVIATURA as Titulo, " +
                                              "LIT.CDSCLITORAL as cLitoral, " +
                                              "PTO.CDSCPUERTO as cPuerto "+
                                              "FROM GRLPUERTO PTO " +
                                              "join GRLENTIDADFED ENT on ENT.ICVEPAIS = ENT.ICVEPAIS and PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED " +
                                              "join GRLLITORAL LIT on PTO.ICVELITORAL = LIT.ICVELITORAL " +
                                              "where PTO.ICVEPAIS = 1 " +
                                              "and PTO.ICVEENTIDADFED = " +iCveEntidadFed+
                                              " order by PTO.CDSCPUERTO ");
       for(int j=0; j < vcPuertoxEntidad.size(); j++){
         rep.iniciaReporte();
         TVDinRep vPuerto = (TVDinRep) vcPuertoxEntidad.get(j);
         cTitulo = vPuerto.getString("Titulo");
         iCvePuerto = vPuerto.getInt("iCvePuerto");
         cLitoral = vPuerto.getString("cLitoral");
         cPuerto = vPuerto.getString("cPuerto");

         if(lObrasPro == 1 && lAreasAgua == 1 && lObrasAtra == 1 &&
            lAreasAlma == 1 && lEdificios == 1){
           rep.comAbreLibro(VParametros.getPropEspecifica("ExcelRutaOrig")+"CPO/CPOCatastroPortuario.xls",true);
           sbRetorno.append(GenObras(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(GenAreasAgua(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(GenObrasAtraque(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(GenAreasAlmacenamiento(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(GenEdificios(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(insertaImagen(iCvePuerto,cTitulo));
         } else{
           rep.comAbreLibro(VParametros.getPropEspecifica("ExcelRutaOrig")+"CPO/CPOCatastroPortuario.xls",true);
           if(lObrasPro == 1)
             sbRetorno.append(GenObras(iEjercicio,iCvePuerto,cTitulo));
           if(lAreasAgua == 1)
             sbRetorno.append(GenAreasAgua(iEjercicio,iCvePuerto,cTitulo));
           if(lObrasAtra == 1)
             sbRetorno.append(GenObrasAtraque(iEjercicio,iCvePuerto,cTitulo));
           if(lAreasAlma == 1)
             sbRetorno.append(GenAreasAlmacenamiento(iEjercicio,iCvePuerto,cTitulo));
           if(lEdificios == 1)
             sbRetorno.append(GenEdificios(iEjercicio,iCvePuerto,cTitulo));

           sbRetorno.append(insertaImagen(iCvePuerto,cTitulo));
         }
         //Guarda el Libro Generado
           rep.comGuardarLibro(cRutaDefUser+cLitoral+"_"+cAbrevEF+"_"+cPuerto);
           //Aca va a ir el cerrar el libro
           sbRetorno.append(rep.getSbDatos());
       }
       //Invoca metodo que guarda Entidad
       sbRetorno.append(guardaEntidad(iCveEntidadFed,cNombreED,cRutaDefUser));
     }

   }catch(Exception e){
     e.printStackTrace();
     cMensaje += e.getMessage();
   }
   if(!cMensaje.equals(""))
     throw new Exception(cMensaje);
   return sbRetorno;
 }
/*Metodo sonfigurado en GRLReporte, ejecuta el reporte por Entidad*/
 public StringBuffer GenCatastroEntidad(String cSolicitud) throws Exception{
   StringBuffer sbRetorno = new StringBuffer("");
   String cRutaDefUser = "", cNombreED = "", cAbrevEF = "", cLitoral = "", cPuerto = "";
   int iCveEntidadFed;


   String[] aSolicitudes = cSolicitud.split(",");

   iEjercicio = Integer.parseInt(aSolicitudes[0],10);
   iCvePuerto = Integer.parseInt(aSolicitudes[1],10);
   lObrasPro = Integer.parseInt(aSolicitudes[2],10);
   lAreasAgua = Integer.parseInt(aSolicitudes[3],10);
   lObrasAtra = Integer.parseInt(aSolicitudes[4],10);
   lAreasAlma = Integer.parseInt(aSolicitudes[5],10);
   lEdificios = Integer.parseInt(aSolicitudes[6],10);
   iCveEntidadFed = Integer.parseInt(aSolicitudes[7],10);
   if(aSolicitudes.length > 8)
     cRutaDefUser = aSolicitudes[8];

   try{
     // Titulo de Reporte

     Vector vcDataEntidades = findByCustom("","SELECT " +
                                           "ICVEENTIDADFED, " +
                                           "CNOMBRE, " +
                                           "CABREVIATURA " +
                                           "FROM GRLENTIDADFED " +
                                           "where ICVEPAIS = 1 and iCveEntidadFed = "+iCveEntidadFed);
     if(vcDataEntidades.size() > 0 ){
       TVDinRep vEntidades = (TVDinRep) vcDataEntidades.get(0);
       cAbrevEF = vEntidades.getString("CABREVIATURA");
       cNombreED = vEntidades.getString("CNOMBRE");
     }

     Vector vcPuertoxEntidad = findByCustom("","SELECT " +
                                              "PTO.ICVEPUERTO, " +
                                              "PTO.CDSCPUERTO || ', ' || ENT.CABREVIATURA as Titulo, " +
                                              "LIT.CDSCLITORAL as cLitoral, " +
                                              "PTO.CDSCPUERTO as cPuerto "+
                                              "FROM GRLPUERTO PTO " +
                                              "join GRLENTIDADFED ENT on ENT.ICVEPAIS = ENT.ICVEPAIS and PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED " +
                                              "join GRLLITORAL LIT on PTO.ICVELITORAL = LIT.ICVELITORAL " +
                                              "where PTO.ICVEPAIS = 1 " +
                                              "and PTO.ICVEENTIDADFED = " +iCveEntidadFed+
                                              " order by PTO.CDSCPUERTO ");
       for(int j=0; j < vcPuertoxEntidad.size(); j++){
         rep.iniciaReporte();
         TVDinRep vPuerto = (TVDinRep) vcPuertoxEntidad.get(j);
         cTitulo = vPuerto.getString("Titulo");
         iCvePuerto = vPuerto.getInt("iCvePuerto");
         cLitoral = vPuerto.getString("cLitoral");
         cPuerto = vPuerto.getString("cPuerto");

         if(lObrasPro == 1 && lAreasAgua == 1 && lObrasAtra == 1 &&
            lAreasAlma == 1 && lEdificios == 1){
           rep.comAbreLibro(VParametros.getPropEspecifica("ExcelRutaOrig")+"CPO/CPOCatastroPortuario.xls",true);
           sbRetorno.append(GenObras(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(GenAreasAgua(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(GenObrasAtraque(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(GenAreasAlmacenamiento(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(GenEdificios(iEjercicio,iCvePuerto,cTitulo));
           sbRetorno.append(insertaImagen(iCvePuerto,cTitulo));
         } else{
           rep.comAbreLibro(VParametros.getPropEspecifica("ExcelRutaOrig")+"CPO/CPOCatastroPortuario.xls",true);
           if(lObrasPro == 1)
             sbRetorno.append(GenObras(iEjercicio,iCvePuerto,cTitulo));
           if(lAreasAgua == 1)
             sbRetorno.append(GenAreasAgua(iEjercicio,iCvePuerto,cTitulo));
           if(lObrasAtra == 1)
             sbRetorno.append(GenObrasAtraque(iEjercicio,iCvePuerto,cTitulo));
           if(lAreasAlma == 1)
             sbRetorno.append(GenAreasAlmacenamiento(iEjercicio,iCvePuerto,cTitulo));
           if(lEdificios == 1)
             sbRetorno.append(GenEdificios(iEjercicio,iCvePuerto,cTitulo));

           sbRetorno.append(insertaImagen(iCvePuerto,cTitulo));
         }
         //Guarda el Libro Generado
           rep.comGuardarLibro(cRutaDefUser+cLitoral+"_"+cAbrevEF+"_"+cPuerto);
           //Aca va a ir el cerrar el libro
           sbRetorno.append(rep.getSbDatos());
       }
       //Invoca método para guardar la entidad
       sbRetorno.append(guardaEntidad(iCveEntidadFed,cNombreED,cRutaDefUser));



   }catch(Exception e){
     e.printStackTrace();
     cMensaje += e.getMessage();
   }
   if(!cMensaje.equals(""))
     throw new Exception(cMensaje);
   return sbRetorno;
 }



 public StringBuffer GenObras(int iEjercicio,int iCvePuerto, String cTitulo) throws Exception{

     cError = "";
     int iReng = 0, iBorde = 1,
         iFondoCeldaPrin = -1;
     String cNombreObraP = "",cLocalizacionOP = "",cNoLocalizacionOP = "",iAnoConstruccionOP = "",
         dLongitudOP = "", dAnchoOP = "", dAlturaOP = "",cEstructuraOP = "";
     StringBuffer sbRetorno = new StringBuffer("");


       try{

//Obras de Protección
           rep.comEligeHoja(2);

//Query para sacar los datos a reemplazar

           Vector vcData = findByCustom("","SELECT IEJERCICIO,ICVEPUERTO,ICONSECUTIVOOP,CNOMOBRAPROTECCION,CLOCALIZACION, "+
                                        "CNOLOCALIZACION,IANIOCONSTRUCCION, " +
                                        "DLONGITUDM,DANCHOCORONAM,DALTURACORONAM,CESTRUCTURA " +
                                        "FROM CPOOBRAPROTECCION " +
                                        "where IEJERCICIO = "+iEjercicio+" and ICVEPUERTO = "+iCvePuerto+" and LACTIVO = 1 "+
                                        "order by ICONSECUTIVOOP ");


//          iRengTemp = 16;

//Bucle para reemplazar todos los datos

           iReng = 16;
           rep.comDespliegaCombinado("CATASTRO PORTUARIO DEL: "+iEjercicio,"A",5,"H",5, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
           rep.comDespliegaCombinado(cTitulo,"A",8,"H",8, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
           for (int i = 0; i<vcData.size(); i++){
             TVDinRep vCatastroOP = (TVDinRep) vcData.get(i);
             cNombreObraP = vCatastroOP.getString("CNOMOBRAPROTECCION");
             cLocalizacionOP = vCatastroOP.getString("CLOCALIZACION");
             cNoLocalizacionOP = vCatastroOP.getString("CNOLOCALIZACION");
             iAnoConstruccionOP = vCatastroOP.getString("IANIOCONSTRUCCION");
             dLongitudOP = vCatastroOP.getString("DLONGITUDM");
             dAnchoOP = vCatastroOP.getString("DANCHOCORONAM");
             dAlturaOP = vCatastroOP.getString("DALTURACORONAM");
             cEstructuraOP = vCatastroOP.getString("CESTRUCTURA");

             rep.comDespliegaAlineado("A",iReng,cNombreObraP,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
             rep.comDespliegaAlineado("B",iReng,cLocalizacionOP,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
             rep.comDespliegaAlineado("C",iReng,cNoLocalizacionOP,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
             rep.comDespliegaAlineado("D",iReng,iAnoConstruccionOP,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
             rep.comDespliegaAlineado("E",iReng,dLongitudOP,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
             rep.comDespliegaAlineado("F",iReng,dAnchoOP,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
             rep.comDespliegaAlineado("G",iReng,dAlturaOP,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
             rep.comDespliegaAlineado("H",iReng,cEstructuraOP,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
             rep.comBordeTotal("A",iReng,"H",iReng,iBorde);
             iReng++;
           }



         sbRetorno = rep.getSbDatos();
       }catch(Exception e){
         e.printStackTrace();
         cMensaje += e.getMessage();
       }

     if(!cMensaje.equals(""))
       throw new Exception(cMensaje);
     return sbRetorno;
    }

 public StringBuffer GenAreasAgua(int iEjercicio,int iCvePuerto, String cTitulo) throws Exception{
        cError = "";
        int iReng = 0, iBorde = 1, iFondoCeldaPrin = -1;
        String cNombreAA,cLocalizacionAA,cNomLocalizacionAA,dLongitudAA,dAreaAA,dAnchoAA,
            dProfundidadAA,dDiaMaxAA,dtSondeoAA,cObsesAA;

        StringBuffer sbRetorno = new StringBuffer("");

          try{

// Áreas Agua
            rep.comEligeHoja(3);

            Vector vcData2 = findByCustom("","SELECT CNOMAREAAGUA,CLOCALIZACION,CNOLOCALIZACION,DLONGITUDM,DAREAM2,"+
                                     "DANCHOPLANTILLAM,DPROFUNDIDADM,DDIAMMAXCIABOGAM,CFECHASONDEO,COBSES " +
                                     "FROM  CPOAREAAGUA " +
                                     "WHERE IEJERCICIO = "+iEjercicio+ " and ICVEPUERTO = "+iCvePuerto+" and LACTIVO = 1 "+
                                     "order by iConsecutivoAA");

            iReng = 16;
            rep.comDespliegaCombinado("CATASTRO PORTUARIO DEL: "+iEjercicio,"A",5,"J",5, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
            rep.comDespliegaCombinado(cTitulo,"A",8,"J",8, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
            for (int i = 0; i<vcData2.size(); i++){
              TVDinRep vAreasAgua = (TVDinRep) vcData2.get(i);
              cNombreAA = vAreasAgua.getString("CNOMAREAAGUA");
              cLocalizacionAA = vAreasAgua.getString("CLOCALIZACION");
              cNomLocalizacionAA = vAreasAgua.getString("CNOLOCALIZACION");
              dLongitudAA = vAreasAgua.getString("DLONGITUDM");
              dAreaAA = vAreasAgua.getString("DAREAM2");
              dAnchoAA = vAreasAgua.getString("DANCHOPLANTILLAM");
              dProfundidadAA = vAreasAgua.getString("DPROFUNDIDADM");
              dDiaMaxAA = vAreasAgua.getString("DPROFUNDIDADM");
              dtSondeoAA = vAreasAgua.getString("CFECHASONDEO");
              cObsesAA = vAreasAgua.getString("COBSES");


              rep.comDespliegaAlineado("A",iReng,cNombreAA,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("B",iReng,cLocalizacionAA,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("C",iReng,cNomLocalizacionAA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("D",iReng,dLongitudAA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("E",iReng,dAreaAA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("F",iReng,dAnchoAA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("G",iReng,dProfundidadAA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("H",iReng,dDiaMaxAA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());


              if(!dtSondeoAA.equals(null) && !dtSondeoAA.equalsIgnoreCase("null") && !dtSondeoAA.equals(""))
                rep.comDespliegaAlineado("I",iReng,dtSondeoAA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());

              rep.comDespliegaAlineado("J",iReng,cObsesAA,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              rep.comBordeTotal("A",iReng,"J",iReng,iBorde);
              iReng++;

            }


            sbRetorno = rep.getSbDatos();
          }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
          }

          if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
          return sbRetorno;
        }

 public StringBuffer GenObrasAtraque(int iEjercicio,int iCvePuerto, String cTitulo) throws Exception{
   cError = "";
   int iReng = 0, iBorde = 1, iFondoCeldaPrin = -1;
   String cNombreOA,cNomLocalizacionOA,iAnioOA,cPropOA,cDisposicionOA,dLongitudOA,dAlturaOA,
      dProfOA,cEstructuraOA,cEntidadOperaOA,cTipoMovOA,cTipoUsoOA,cServiciosOA,dAnchoOA,
      iBandasOA,dLongitudAtraqueOA;

   StringBuffer sbRetorno = new StringBuffer("");

    try{

//Obras de Atraque
        rep.comEligeHoja(4);

        Vector vcData3 = findByCustom("","SELECT CNOMOBRAATRAQUE, CNOLOCALIZACION, IANIOCONSTRUCCION,CPROP,CDISPOSICION,"+
                                      "DLONGITUDM,DANCHOM,IBANDASATRAQUE,DLONGITUDATRAQUE,DALTURAM,DPROFM,CESTRUCTURA,"+
                                      "CENTIDADOPERA,CPOTIPOMOVIMIENTO.CDSCTIPOMOVIMIENTO as TipoMov,CPOTIPOUSO.CDSCTIPOUSO as TipoUso,"+
                                      "CSERVICIOS " +
                                      "FROM CPOOBRAATRAQUE " +
                                     "JOIN CPOTIPOMOVIMIENTO ON CPOOBRAATRAQUE.ICVETIPOMOVIMIENTO = CPOTIPOMOVIMIENTO.ICVETIPOMOVIMIENTO " +
                                     "JOIN CPOTIPOUSO ON CPOOBRAATRAQUE.ICVETIPOUSO = CPOTIPOUSO.ICVETIPOUSO " +
                                     "WHERE IEJERCICIO = "+iEjercicio+" AND ICVEPUERTO = "+iCvePuerto+" AND LACTIVO = 1 "+
                                     "order by iConsecutivoOA");

       iReng = 16;
       rep.comDespliegaCombinado("CATASTRO PORTUARIO DEL: "+iEjercicio,"A",5,"P",5, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
       rep.comDespliegaCombinado(cTitulo,"A",8,"P",8, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
       for (int i = 0; i<vcData3.size(); i++){
          TVDinRep vObrasAtraque = (TVDinRep) vcData3.get(i);
          cNombreOA = vObrasAtraque.getString("CNOMOBRAATRAQUE");
          cNomLocalizacionOA = vObrasAtraque.getString("CNOLOCALIZACION");
          iAnioOA = vObrasAtraque.getString("IANIOCONSTRUCCION");
          cPropOA = vObrasAtraque.getString("CPROP");
          cDisposicionOA = vObrasAtraque.getString("CDISPOSICION");
          dLongitudOA = vObrasAtraque.getString("DLONGITUDM");
          dAnchoOA = vObrasAtraque.getString("DANCHOM");
          iBandasOA = vObrasAtraque.getString("IBANDASATRAQUE");
          dLongitudAtraqueOA = vObrasAtraque.getString("DLONGITUDATRAQUE");
          dAlturaOA = vObrasAtraque.getString("DALTURAM");
          dProfOA = vObrasAtraque.getString("DPROFM");
          cEstructuraOA = vObrasAtraque.getString("CESTRUCTURA");
          cEntidadOperaOA = vObrasAtraque.getString("CENTIDADOPERA");
          cTipoMovOA = vObrasAtraque.getString("TipoMov");
          cTipoUsoOA = vObrasAtraque.getString("TipoUso");
          cServiciosOA = vObrasAtraque.getString("CSERVICIOS");

          rep.comDespliegaAlineado("A",iReng,cNombreOA,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("B",iReng,cNomLocalizacionOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("C",iReng,iAnioOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("D",iReng,cPropOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("E",iReng,cDisposicionOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("F",iReng,dLongitudOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("G",iReng,dAnchoOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("H",iReng,iBandasOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("I",iReng,dLongitudAtraqueOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("J",iReng,dAlturaOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("K",iReng,dProfOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("L",iReng,cEstructuraOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("M",iReng,cEntidadOperaOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("N",iReng,cTipoMovOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("O",iReng,cTipoUsoOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comDespliegaAlineado("P",iReng,cServiciosOA,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          rep.comBordeTotal("A",iReng,"P",iReng,iBorde);
          iReng++;
        }

        sbRetorno = rep.getSbDatos();
      }catch(Exception e){
        e.printStackTrace();
        cMensaje += e.getMessage();
      }

      if(!cMensaje.equals(""))
        throw new Exception(cMensaje);
      return sbRetorno;
    }


  public StringBuffer GenAreasAlmacenamiento(int iEjercicio,int iCvePuerto, String cTitulo) throws Exception{
    cError = "";
    int iReng = 0, iBorde = 1, iFondoCeldaPrin = -1;
    String cNombreAAl,cNomLocalizacionAAl,iAnioAAl,cDimenAAl,cEntidadOperaAAl,cEstructuraAAl,
        dAreaTotalAAl,dAreaUtilAAl,dCapacidadAAl,cUsoAAl,cServiciosAAl;
    StringBuffer sbRetorno = new StringBuffer("");

    try{

      // Áreas de Almacenamiento
      rep.comEligeHoja(5);

      Vector vcData4 = findByCustom("","SELECT CNOMAREAALMACEN,CNOLOCALIZACION,IANIOCONSTRUCCION,CDIMENSIONESM,CESTRUCTURA,"+
                                    "CENTIDADOPERA,DAREATOTALM,DAREAUTILM2,DCAPACIDAD, " +
                                    "CUSO,CSERVICIOS " +
                                    "FROM CPOAREAALMACEN " +
                                    "where IEJERCICIO = " + iEjercicio +
                                    " and ICVEPUERTO = " + iCvePuerto +
                                    " and LACTIVO = 1 " +
                                    "order by iConsecutivoAAL");

      iReng = 16;
      rep.comDespliegaCombinado("CATASTRO PORTUARIO DEL: " + iEjercicio,"A",5,
                                "K",5,rep.getAT_COMBINA_CENTRO(),"",true,
                                iFondoCeldaPrin,false,false,iBorde,1);
      rep.comDespliegaCombinado(cTitulo,"A",8,"K",8,rep.getAT_COMBINA_CENTRO(),
                                "",true,iFondoCeldaPrin,false,false,iBorde,1);
      for(int i = 0;i < vcData4.size();i++){
        TVDinRep vAreasAlmacen = (TVDinRep) vcData4.get(i);
        cNombreAAl = vAreasAlmacen.getString("CNOMAREAALMACEN");
        cNomLocalizacionAAl = vAreasAlmacen.getString("CNOLOCALIZACION");
        iAnioAAl = vAreasAlmacen.getString("IANIOCONSTRUCCION");
        cDimenAAl = vAreasAlmacen.getString("CDIMENSIONESM");
        cEstructuraAAl = vAreasAlmacen.getString("CESTRUCTURA");
        cEntidadOperaAAl = vAreasAlmacen.getString("CENTIDADOPERA");
        dAreaTotalAAl = vAreasAlmacen.getString("DAREATOTALM");
        dAreaUtilAAl = vAreasAlmacen.getString("DAREAUTILM2");
        dCapacidadAAl = vAreasAlmacen.getString("DCAPACIDAD");
        cUsoAAl = vAreasAlmacen.getString("CUSO");
        cServiciosAAl = vAreasAlmacen.getString("CSERVICIOS");

        rep.comDespliegaAlineado("A",iReng,cNombreAAl,false,
                                 rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("B",iReng,cNomLocalizacionAAl,false,
                                 rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("C",iReng,iAnioAAl,false,rep.getAT_HCENTRO(),
                                 rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("D",iReng,cDimenAAl,false,rep.getAT_HCENTRO(),
                                 rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("E",iReng,cEstructuraAAl,false,
                                 rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("F",iReng,cEntidadOperaAAl,false,
                                 rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("G",iReng,dAreaTotalAAl,false,
                                 rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("H",iReng,dAreaUtilAAl,false,rep.getAT_HCENTRO(),
                                 rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("I",iReng,dCapacidadAAl,false,
                                 rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("J",iReng,cUsoAAl,false,rep.getAT_HCENTRO(),
                                 rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("K",iReng,cServiciosAAl,false,
                                 rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        rep.comBordeTotal("A",iReng,"K",iReng,iBorde);
        iReng++;
      }

      sbRetorno = rep.getSbDatos();
    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return sbRetorno;
  }

 public StringBuffer GenEdificios(int iEjercicio,int iCvePuerto, String cTitulo) throws Exception{
   cError = "";
   int iReng = 0, iBorde = 1, iFondoCeldaPrin = -1;
   String cNombreE,cNomLocalizacionE,iAnioE,cPropE,iNivelesE,dAreaTotalE,cEstructuraE,
       cUsoE,cProprAdmE;
   StringBuffer sbRetorno = new StringBuffer("");

   try{

// Áreas de Edificios
     rep.comEligeHoja(6);

     Vector vcData5 = findByCustom("","SELECT CNOMEDIFICIO,CNOLOCALIZACION,IANIOCONSTRUCCION,CPROP,INIVELES,"+
                                   "DAREATOTALM2,CESTRUCTURA,CPROPADMUSU,CUSO " +
                                   "FROM CPOEDIFICIO "+
                                   "where IEJERCICIO = "+iEjercicio+" and ICVEPUERTO = "+iCvePuerto+" and LACTIVO = 1 "+
                                   "order by iConsecutivoE");

     iReng = 16;
     rep.comDespliegaCombinado("CATASTRO PORTUARIO DEL: "+iEjercicio,"A",5,"I",5, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
     rep.comDespliegaCombinado(cTitulo,"A",8,"I",8, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
     for (int i = 0; i<vcData5.size(); i++){
       TVDinRep vEdificio = (TVDinRep) vcData5.get(i);
       cNombreE = vEdificio.getString("CNOMEDIFICIO");
       cNomLocalizacionE = vEdificio.getString("CNOLOCALIZACION");
       iAnioE = vEdificio.getString("IANIOCONSTRUCCION");
       cPropE = vEdificio.getString("CPROP");
       iNivelesE = vEdificio.getString("INIVELES");
       dAreaTotalE = vEdificio.getString("DAREATOTALM2");
       cEstructuraE = vEdificio.getString("CESTRUCTURA");
       cProprAdmE = vEdificio.getString("CPROPADMUSU");
       cUsoE = vEdificio.getString("CUSO");

       rep.comDespliegaAlineado("A",iReng,cNombreE,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
       rep.comDespliegaAlineado("B",iReng,cNomLocalizacionE,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
       rep.comDespliegaAlineado("C",iReng,iAnioE,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
       rep.comDespliegaAlineado("D",iReng,cPropE,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
       rep.comDespliegaAlineado("E",iReng,iNivelesE,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
       rep.comDespliegaAlineado("F",iReng,dAreaTotalE,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
       rep.comDespliegaAlineado("G",iReng,cEstructuraE,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
       rep.comDespliegaAlineado("H",iReng,cProprAdmE,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
       rep.comDespliegaAlineado("I",iReng,cUsoE,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
       rep.comBordeTotal("A",iReng,"I",iReng,iBorde);
       iReng++;
     }

     sbRetorno = rep.getSbDatos();
   }catch(Exception e){
     e.printStackTrace();
     cMensaje += e.getMessage();
   }

   if(!cMensaje.equals(""))
     throw new Exception(cMensaje);
   return sbRetorno;
 }

 public StringBuffer insertaImagen(int iCvePuerto, String cTitulo) throws Exception{
        cError = "";
        String cTempImg = "";
        int iReng = 0, iBorde = 1, iFondoCeldaPrin = -1;
        String cRutaImgReporte = VParametros.getPropEspecifica("ExcelRutaOrig") + VParametros.getPropEspecifica("RutaImgReporteCatastro");

        StringBuffer sbRetorno = new StringBuffer("");

          try{

//            rep.iniciaReporte();
            rep.comEligeHoja(7);
            iReng = 11;
            rep.comDespliegaCombinado(cTitulo,"A",8,"J",8, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,false,false,iBorde,1);
            cTempImg = cRutaImgReporte + "CPOPuerto"+iCvePuerto+".jpg";
            rep.comInsertaImagen("B",iReng,cTempImg,670,420,0,0);



            sbRetorno = rep.getSbDatos();
          }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
          }

          if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
          return sbRetorno;
 }
 public StringBuffer guardaEntidad(int iCveEntidad, String cNombreEntFed, String cRutaDefUser) throws Exception{
        cError = "";
        StringBuffer sbRetorno = new StringBuffer("");

          try{

            rep.iniciaReporte();
            rep.comAbreLibro(VParametros.getPropEspecifica("ExcelRutaOrig")+"CPO/CPOPEntidadFederativa"+iCveEntidad+".xls",false);
            rep.comGuardarLibro(cRutaDefUser+cNombreEntFed);


            sbRetorno = rep.getSbDatos();
          }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
          }

          if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
          return sbRetorno;
 }



}

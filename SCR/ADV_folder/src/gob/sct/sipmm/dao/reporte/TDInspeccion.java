package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import gob.sct.sipmm.dao.*;
import java.sql.SQLException;
import com.micper.excepciones.DAOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * <p>Title: TDInspección.java</p>
 * <p>Description: DAO con métodos para reportes de INSPECCIONES</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ALopez
 * @author Sergio Gonzalez
 * @version 1.0
*
 */

public class TDInspeccion extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);

  TExcel rep = new TExcel();

  public TDInspeccion(){
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

 public StringBuffer fARecSolTram(String iConsecutivoCertExp) throws Exception{
     cError = "";
     TFechas dFechas = new TFechas();
     StringBuffer sbRetorno = new StringBuffer("");


       try{

//Obras de Protección
           rep.iniciaReporte();
String[] aParam = iConsecutivoCertExp.split(",");
           Vector vcData = findByCustom("",
                                        " SELECT " +
                                        "   VE.CNOMEMBARCACION,V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                        "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                        "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                        "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO " +
                                        " FROM VEHEMBARCACION VE " +
                                        " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " +                                         aParam[0] +
                                        " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                        " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                        " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO "+
                                        " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                        " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                        " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                        " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                        " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                        " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                        " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                        " where VE.iCveVehiculo = " + aParam[1]);

               TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
               Vector vcLugar = findByCustom("","SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                                "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                                "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                                "WHERE ICVEOFICINA = "+ aParam[3]);
             TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
             if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("A",16,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",16,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("CDSCPUERTO").equals("null")) rep.comDespliegaAlineado("U",16,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",16,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("CNUMOMI").equals("null")) rep.comDespliegaAlineado("AD",16,vCertifIA.getString("CNUMOMI"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AD",16,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DTCONSTRUCCION").equals("null")) rep.comDespliegaAlineado("AI",16,""+vCertifIA.getDate("DTCONSTRUCCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AI",16,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DESLORA").equals("null")) rep.comDespliegaAlineado("A",30,vCertifIA.getString("DESLORA")+" "+vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",30,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DMANGA").equals("null")) rep.comDespliegaAlineado("O",30,vCertifIA.getString("DMANGA")+" "+vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("O",30,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DPUNTAL").equals("null")) rep.comDespliegaAlineado("Z",30,vCertifIA.getString("DPUNTAL")+" "+vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("Z",30,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("L",35,vCertifIA.getString("DARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("L",35,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DARQUEONETO").equals("null")) rep.comDespliegaAlineado("AB",35,vCertifIA.getString("DARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AB",35,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("cSenialDist").equals("null")) rep.comDespliegaAlineado("K",16,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("k",16,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("S",43,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("S",30,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("W",43,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("W",30,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AD",43,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AD",43,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("H",43,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("H",43,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("Q",49,"FECHA DE VENCIMIENTO: "+dFechas.getStringDay(vCertifIA.getDate("DTFINVIGENCIA"))+" DE "+dFechas.getMonthName(vCertifIA.getDate("DTFINVIGENCIA"))+" DE "+dFechas.getIntYear(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("S",30,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             sbRetorno = rep.getSbDatos();
       }catch(Exception e){
         e.printStackTrace();
         cMensaje += e.getMessage();
       }
     if(!cMensaje.equals(""))
       throw new Exception(cMensaje);
     return sbRetorno;
    }
 public StringBuffer fCertArqueoMenor(String iConsecutivoCertExp) throws Exception{

      cError = "";
      int iRengIni = 10, iReng = 0, iRengTemp, iRengImprocedente,
          //iConsecutivoCertExp = 1,
          iBorde = 1,
          iFondoCeldaPrin = -1;
      String cDscDia = new String("");
      String cDscMes = new String("");
      String cDscAno = new String("");
      TFechas dFechas = new TFechas();
      StringBuffer sbRetorno = new StringBuffer("");


        try{

          //Obras de Protección
          rep.iniciaReporte();
          //rep.comEligeHoja(2);
          String[] aParam = iConsecutivoCertExp.split(",");
          //Query para sacar los datos a reemplazar

          Vector vcData = findByCustom("",
                                       " SELECT " +
                                       "   VE.CNOMEMBARCACION,V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                       "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                       "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                       "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO " +
                                       " FROM VEHEMBARCACION VE " +
                                       " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " +                                         aParam[0] +
                                       " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                       " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                       " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO "+
                                       " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                       " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                       " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                       " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                       " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                       " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                       " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                       " where VE.iCveVehiculo = " + aParam[1]);
          TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
          Vector vcLugar = findByCustom("",
              "SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                        "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                        "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                        "WHERE ICVEOFICINA = " + aParam[3]);
          TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
          if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.
              comDespliegaAlineado("A",14,vCertifIA.getString("CNOMEMBARCACION"),false,
                                   rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
          else rep.comDespliegaAlineado("A",14,"",false,rep.getAT_HIZQUIERDA(),
                                        rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("A",15,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("CDSCPUERTO").equals("null")) rep.comDespliegaAlineado("J",15,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("J",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("CMATRICULA").equals("null")) rep.comDespliegaAlineado("Q",15,vCertifIA.getString("CMATRICULA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("Q",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("cSenialDist").equals("null")) rep.comDespliegaAlineado("W",15,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("W",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DTCONSTRUCCION").equals("null")) rep.comDespliegaAlineado("AD",15,""+vCertifIA.getDate("DTCONSTRUCCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AD",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DESLORA").equals("null")) rep.comDespliegaAlineado("H",24,vCertifIA.getString("DESLORA")+" "+vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("H",24,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DMANGA").equals("null")) rep.comDespliegaAlineado("O",24,vCertifIA.getString("DMANGA")+" "+vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("O",24,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DPUNTAL").equals("null")) rep.comDespliegaAlineado("V",24,vCertifIA.getString("DPUNTAL")+" "+vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("V",24,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("L",33,vCertifIA.getString("DARQUEOBRUTO")+" "+vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("L",33,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DARQUEONETO").equals("null")) rep.comDespliegaAlineado("X",33,vCertifIA.getString("DARQUEONETO")+" "+vCertifIA.getString("UARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("X",33,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("Q",38,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("Q",38,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("U",38,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",38,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AE",38,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AE",38,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("F",38,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("F",38,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              sbRetorno = rep.getSbDatos();
        }catch(Exception e){
          e.printStackTrace();
          cMensaje += e.getMessage();
        }
      if(!cMensaje.equals(""))
        throw new Exception(cMensaje);
      return sbRetorno;


}
 public StringBuffer fCertArqueoIgualMenor(String iConsecutivoCertExp) throws Exception{

         cError = "";
         int iRengIni = 10, iReng = 0, iRengTemp, iRengImprocedente,
             //iConsecutivoCertExp = 1,
             iBorde = 1,
             iFondoCeldaPrin = -1;
         String cDscDia = new String("");
         String cDscMes = new String("");
         String cDscAno = new String("");
         TFechas dFechas = new TFechas();
         StringBuffer sbRetorno = new StringBuffer("");


           try{

             //Obras de Protección
             rep.iniciaReporte();
             //rep.comEligeHoja(2);
             String[] aParam = iConsecutivoCertExp.split(",");
             //Query para sacar los datos a reemplazar

             Vector vcData = findByCustom("",
                                          " SELECT " +
                                          "   VE.CNOMEMBARCACION,V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                          "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                          "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                          "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO " +
                                          " FROM VEHEMBARCACION VE " +
                                          " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " +                                         aParam[0] +
                                          " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                          " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                          " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO "+
                                          " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                          " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                          " where VE.iCveVehiculo = " + aParam[1]);

             TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
             Vector vcLugar = findByCustom("",
                 "SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                           "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                           "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                           "WHERE ICVEOFICINA = " + aParam[3]);
             TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
             if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.
                 comDespliegaAlineado("A",14,vCertifIA.getString("CNOMEMBARCACION"),false,
                                      rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
             else rep.comDespliegaAlineado("A",14,"",false,rep.getAT_HIZQUIERDA(),
                                           rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("A",14,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("CDSCPUERTO").equals("null")) rep.comDespliegaAlineado("D",14,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("D",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("CMATRICULA").equals("null")) rep.comDespliegaAlineado("G",14,vCertifIA.getString("CMATRICULA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("G",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("cSenialDist").equals("null")) rep.comDespliegaAlineado("W",14,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("W",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTCONSTRUCCION").equals("null")) rep.comDespliegaAlineado("I",14,""+vCertifIA.getDate("DTCONSTRUCCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("I",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DESLORA").equals("null")) rep.comDespliegaAlineado("B",21,vCertifIA.getString("DESLORA")+" "+vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("G",21,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DMANGA").equals("null")) rep.comDespliegaAlineado("D",21,vCertifIA.getString("DMANGA")+" "+vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("D",21,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DPUNTAL").equals("null")) rep.comDespliegaAlineado("G",21,vCertifIA.getString("DPUNTAL")+" "+vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("G",21,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("D",31,vCertifIA.getString("DARQUEOBRUTO")+" "+vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("D",31,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DARQUEONETO").equals("null")) rep.comDespliegaAlineado("F",31,vCertifIA.getString("DARQUEONETO")+" "+vCertifIA.getString("UARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("F",31,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("E",35,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("E",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("G",35,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("G",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("J",35,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("J",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("B",35,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("B",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        /*       if(!vCertifIA.getString("UARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("S",35,vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("P",35,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("UARQUEONETO").equals("null")) rep.comDespliegaAlineado("AF",35,vCertifIA.getString("UARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AF",35,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("UESLORA").equals("null")) rep.comDespliegaAlineado("I",30,vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("I",30,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("UMANGA").equals("null")) rep.comDespliegaAlineado("S",30,vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("T",30,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("UPUNTAL").equals("null")) rep.comDespliegaAlineado("AF",30,vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AF",30,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        */       sbRetorno = rep.getSbDatos();
           }catch(Exception e){
             e.printStackTrace();
             cMensaje += e.getMessage();
           }
         if(!cMensaje.equals(""))
           throw new Exception(cMensaje);
         return sbRetorno;


   }
 public StringBuffer fCertArqueoMayor(String iConsecutivoCertExp) throws Exception{

        cError = "";
        int iRengIni = 10, iReng = 0, iRengTemp, iRengImprocedente,
            //iConsecutivoCertExp = 1,
            iBorde = 1,
            iFondoCeldaPrin = -1;
        String cDscDia = new String("");
        String cDscMes = new String("");
        String cDscAno = new String("");
        TFechas dFechas = new TFechas();
        StringBuffer sbRetorno = new StringBuffer("");


          try{

            //Obras de Protección
            rep.iniciaReporte();
            //rep.comEligeHoja(2);
            String[] aParam = iConsecutivoCertExp.split(",");
            //Query para sacar los datos a reemplazar

            Vector vcData = findByCustom("",
                                         " SELECT " +
                                         "   VE.CNOMEMBARCACION,V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                         "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                         "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                         "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO " +
                                         " FROM VEHEMBARCACION VE " +
                                         " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " +                                         aParam[0] +
                                         " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                         " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                         " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO "+
                                         " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                         " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                         " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                         " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                         " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                         " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                         " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                         " where VE.iCveVehiculo = " + aParam[1]);
            TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
            Vector vcLugar = findByCustom("",
                "SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                          "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                          "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                          "WHERE ICVEOFICINA = " + aParam[3]);
            TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
            if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.
                comDespliegaAlineado("A",14,vCertifIA.getString("CNOMEMBARCACION"),false,
                                     rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            else rep.comDespliegaAlineado("A",14,"",false,rep.getAT_HIZQUIERDA(),
                                          rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("A",14,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("CMATRICULA").equals("null")) rep.comDespliegaAlineado("V",14,vCertifIA.getString("CMATRICULA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("V",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("cSenialDist").equals("null")) rep.comDespliegaAlineado("AB",14,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AB",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DTCONSTRUCCION").equals("null")) rep.comDespliegaAlineado("AG",14,""+vCertifIA.getDate("DTCONSTRUCCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AG",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("CDSCPUERTO").equals("null")) rep.comDespliegaAlineado("L",14,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("L",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DESLORA").equals("null")) rep.comDespliegaAlineado("I",23,vCertifIA.getString("DESLORA")+" "+vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("I",23,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DMANGA").equals("null")) rep.comDespliegaAlineado("P",23,vCertifIA.getString("DMANGA")+" "+vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("P",23,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DPUNTAL").equals("null")) rep.comDespliegaAlineado("W",23,vCertifIA.getString("DPUNTAL")+" "+vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("W",23,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("J",28,vCertifIA.getString("DARQUEOBRUTO")+" "+vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("J",28,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DARQUEONETO").equals("null")) rep.comDespliegaAlineado("AA",28,vCertifIA.getString("DARQUEONETO")+" "+vCertifIA.getString("UARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AA",28,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("P",35,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("P",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("T",35,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("T",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AD",35,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AD",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("E",35,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("E",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
       /*       if(!vCertifIA.getString("UARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("S",35,vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("P",35,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("UARQUEONETO").equals("null")) rep.comDespliegaAlineado("AF",35,vCertifIA.getString("UARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AF",35,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("UESLORA").equals("null")) rep.comDespliegaAlineado("I",30,vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("I",30,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("UMANGA").equals("null")) rep.comDespliegaAlineado("S",30,vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("T",30,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("UPUNTAL").equals("null")) rep.comDespliegaAlineado("AF",30,vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AF",30,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
       */       sbRetorno = rep.getSbDatos();
          }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
          }
        if(!cMensaje.equals(""))
          throw new Exception(cMensaje);
        return sbRetorno;


  }
 public StringBuffer fCertIntExencionFrancoBordo(String iConsecutivoCertExp) throws Exception{

         cError = "";
         TFechas dFechas = new TFechas();
         StringBuffer sbRetorno = new StringBuffer("");
           try{
             rep.iniciaReporte();
             String[] aParam = iConsecutivoCertExp.split(",");
             Vector vcData = findByCustom("",
                                          " SELECT " +
                                          "   VE.CNOMEMBARCACION,V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                          "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                          "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                          "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO " +
                                          " FROM VEHEMBARCACION VE " +
                                          " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " +                                         aParam[0] +
                                          " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                          " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                          " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO "+
                                          " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                          " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                          " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                          " where VE.iCveVehiculo = " + aParam[1]);
             TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
             Vector vcLugar = findByCustom("",
                 "SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                           "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                           "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                           "WHERE ICVEOFICINA = " + aParam[3]);
             TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
                 if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("A",15,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 //if(!vCertifIA.getString("CMATRICULA").equals("null")) rep.comDespliegaAlineado("V",15,vCertifIA.getString("CMATRICULA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("V",14,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("cSenialDist").equals("null")) rep.comDespliegaAlineado("H",15,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("H",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 //if(!vCertifIA.getString("DTCONSTRUCCION").equals("null")) rep.comDespliegaAlineado("AG",15,""+vCertifIA.getDate("DTCONSTRUCCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AG",15,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("CDSCPUERTO").equals("null")) rep.comDespliegaAlineado("BC",15,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("BC",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 //if(!vCertifIA.getString("DESLORA").equals("null")) rep.comDespliegaAlineado("I",23,vCertifIA.getString("DESLORA")+" "+vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("I",23,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 //if(!vCertifIA.getString("DMANGA").equals("null")) rep.comDespliegaAlineado("P",23,vCertifIA.getString("DMANGA")+" "+vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("P",23,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 //if(!vCertifIA.getString("DPUNTAL").equals("null")) rep.comDespliegaAlineado("W",23,vCertifIA.getString("DPUNTAL")+" "+vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("W",23,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 //if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("J",28,vCertifIA.getString("DARQUEOBRUTO")+" "+vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("J",28,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 //if(!vCertifIA.getString("DARQUEONETO").equals("null")) rep.comDespliegaAlineado("AA",28,vCertifIA.getString("DARQUEONETO")+" "+vCertifIA.getString("UARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AA",28,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("H",49,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("H",49,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("R",49,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("R",49,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AN",49,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AN",49,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("D",44,""+dFechas.getStringDay(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("D",44,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("H",44,""+dFechas.getMonthName(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("H",44,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("AA",44,""+dFechas.getIntYear(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AA",44,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

                 if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("B",49,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("B",49,"",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 sbRetorno = rep.getSbDatos();
           }catch(Exception e){
             e.printStackTrace();
             cMensaje += e.getMessage();
           }
         if(!cMensaje.equals(""))
           throw new Exception(cMensaje);
         return sbRetorno;


   }
 public StringBuffer fCertIntArqueo(String iConsecutivoCertExp) throws Exception{

           cError = "";
           TFechas dFechas = new TFechas();
           StringBuffer sbRetorno = new StringBuffer("");
             try{
               rep.iniciaReporte();
               String[] aParam = iConsecutivoCertExp.split(",");
               Vector vcData = findByCustom("",
                                            " SELECT " +
                                            "   VE.CNOMEMBARCACION,V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                            "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                            "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                            "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO " +
                                            " FROM VEHEMBARCACION VE " +
                                            " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " +                                         aParam[0] +
                                            " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                            " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                            " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO "+
                                            " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                            " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                            " where VE.iCveVehiculo = " + aParam[1]);
               TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
               Vector vcLugar = findByCustom("",
                   "SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                             "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                             "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                             "WHERE ICVEOFICINA = " + aParam[3]);
               TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
               if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("A",16,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("CDSCPUERTO").equals("null")) rep.comDespliegaAlineado("U",16,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("CNUMOMI").equals("null")) rep.comDespliegaAlineado("AD",16,vCertifIA.getString("CNUMOMI"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AD",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DTCONSTRUCCION").equals("null")) rep.comDespliegaAlineado("AI",16,""+vCertifIA.getDate("DTCONSTRUCCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AI",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DESLORA").equals("null")) rep.comDespliegaAlineado("A",30,vCertifIA.getString("DESLORA")+" "+vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DMANGA").equals("null")) rep.comDespliegaAlineado("O",30,vCertifIA.getString("DMANGA")+" "+vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("O",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DPUNTAL").equals("null")) rep.comDespliegaAlineado("Z",30,vCertifIA.getString("DPUNTAL")+" "+vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("Z",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("L",35,vCertifIA.getString("DARQUEOBRUTO")+" "+vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("L",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DARQUEONETO").equals("null")) rep.comDespliegaAlineado("AB",35,vCertifIA.getString("DARQUEONETO")+" "+vCertifIA.getString("UARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AB",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("cSenialDist").equals("null")) rep.comDespliegaAlineado("K",16,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("k",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("S",43,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("S",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("W",43,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("W",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AD",43,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AD",43,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("H",43,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("H",43,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
               sbRetorno = rep.getSbDatos();
             }catch(Exception e){
               e.printStackTrace();
               cMensaje += e.getMessage();
             }
           if(!cMensaje.equals(""))
             throw new Exception(cMensaje);
           return sbRetorno;


     }
 public StringBuffer fExtencion(String iConsecutivoCertExp) throws Exception{
         cError = "";
         TFechas dFechas = new TFechas();
         StringBuffer sbRetorno = new StringBuffer("");


           try{

//Obras de Protección
               rep.iniciaReporte();

    String[] aParam = iConsecutivoCertExp.split(",");
    Vector vcInspeccion = findByCustom ("",
       "SELECT U.CNOMBRE||' '|| U.CAPPATERNO||' '|| U.CAPMATERNO AS CNOMBREUSUARIO, TC.CDSCCERTIFICADO FROM INSCERTXINSPECCION CI " +
       "JOIN INSPROGINSP PI ON CI.ICVEINSPPROG =  PI.ICVEINSPPROG " +
       "JOIN INSINSPECTOR I ON PI.ICVEINSPECTOR = I.ICVEINSPECTOR " +
       "JOIN SEGUSUARIO U ON U.ICVEUSUARIO = I.ICVEUSUARIO " +
       "JOIN INSTIPOCERTIF TC ON TC.ICVEGRUPOCERTIF = CI.ICVEGRUPOCERTIF AND CI.ITIPOCERTIFICADO = TC.ITIPOCERTIFICADO " +
       "WHERE CI.INUMSOLICITUD = "+aParam[5]+" AND CI.IEJERCICIO = "+aParam[4]);
   TVDinRep vInspeccion = (TVDinRep) vcInspeccion.get(0);
               Vector vcData = findByCustom("",
                                            " SELECT " +
                                            "   VE.CNOMEMBARCACION,V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                            "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                            "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                            "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO " +
                                            " FROM VEHEMBARCACION VE " +
                                            " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " +                                         aParam[0] +
                                            " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                            " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                            " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO "+
                                            " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                            " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                            " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                            " where VE.iCveVehiculo = " + aParam[1]);

                   TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
                   Vector vcLugar = findByCustom("","SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                                    "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                                    "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                                    "WHERE ICVEOFICINA = "+ aParam[3]);
                 TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
                 if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("A",15,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("cSenialDist").equals("null")) rep.comDespliegaAlineado("O",15,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("O",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("CDSCPUERTO").equals("null")) rep.comDespliegaAlineado("U",15,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("CNUMOMI").equals("null")) rep.comDespliegaAlineado("AJ",15,vCertifIA.getString("CNUMOMI"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AJ",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("AD",15,vCertifIA.getString("DARQUEOBRUTO")+" "+vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("L",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("O",38,""+vCertifIA.getDate("DTFINVIGENCIA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("O",38,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("U",44,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",44,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AA",44,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AA",44,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AI",44,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AI",44,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("F",45,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("F",44,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("U",45,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",45,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AA",45,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AA",45,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AI",45,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AI",45,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("F",44,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("F",45,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vInspeccion.getString("CDSCCERTIFICADO").equals("null")) rep.comDespliegaAlineado("K",4,"CERTIFICADO DE "+vInspeccion.getString("CDSCCERTIFICADO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("K",4,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 if(!vInspeccion.getString("CNOMBREUSUARIO").equals("null")) rep.comDespliegaAlineado("T",52,vInspeccion.getString("CNOMBREUSUARIO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("T",52,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                 sbRetorno = rep.getSbDatos();
           }catch(Exception e){
             e.printStackTrace();
             cMensaje += e.getMessage();
           }
         if(!cMensaje.equals(""))
           throw new Exception(cMensaje);
         return sbRetorno;
        }


public StringBuffer fAprobacionDocumentos(String cQuery){
      Vector vcCuerpo = new Vector();
      Vector vcCopiasPara = new Vector();
      Vector vRegs = new Vector();
      Vector vRegs1 = new Vector();
      Vector vRegs2 = new Vector();
      Vector vRegs3 = new Vector();
      TWord rep = new TWord();
      TFechas dtFechas = new TFechas();
      String cNomEmbarcacion="",CDSCDOCTECNICO="",OFICINARESU="",DEPTORESU="",CDSCTIPOEMBARCACION="";
      int iCveVehiculo=0,lRequiereAprovacion=0;
      String[] cParametros = cQuery.split(",");
      String cFecha="",cFecha2="";

      int iFolioRPMN=0, iPartida=0,lNavAltura=0,lNavCabotaje=0,iNumSolicitud=0;

      try{
        vRegs = super.FindByGeneric("",
                                    "SELECT DC.ICVEVEHICULO, DC.ICVEDOCTECNICO, EMB.CNOMEMBARCACION, DT.CDSCDOCTECNICO, OFR.CDSCBREVE AS cOFICINARESU, " +
                                    "DEP.CDSCBREVE AS cDEPTORESU, DC.DTAUTORIZACION, dt.LREQUIEREAPROBACION,TE.CDSCTIPOEMBARCACION "+
                                    "FROM INSEMBARCXDOCTEC DC " +
                                    "JOIN VEHEMBARCACION EMB ON EMB.ICVEVEHICULO = DC.ICVEVEHICULO " +
                                    "LEFT JOIN VEHTIPOEMBARCACION TE ON TE.ICVETIPOEMBARCACION=EMB.ICVETIPOEMBARCACION "+
                                    "JOIN INSDOCTECNICO DT ON DT.ICVEDOCTECNICO = DC.ICVEDOCTECNICO " +
                                    "JOIN TRAREGSOLICITUD S ON S.INUMSOLICITUD = DC.INUMSOLICITUD AND S.IEJERCICIO= DC.IEJERCICIO " +
                                    "JOIN GRLOFICINA OFR ON OFR.ICVEOFICINA = S.ICVEOFICINA " +
                                    "jOIN GRLDEPARTAMENTO DEP ON DEP.ICVEDEPARTAMENTO = S.ICVEDEPARTAMENTO " +
                                    "Where DC.ICVEVEHICULO = "+cParametros[0]+" and dc.ICVEDOCTECNICO = "+cParametros[1]+" and dc.ICONSECUTIVODOCTECEMB= "+
                                    "(SELECT Max( ICONSECUTIVODOCTECEMB ) FROM  INSEMBARCXDOCTEC Where ICVEVEHICULO = "+cParametros[0]+" and ICVEDOCTECNICO ="+cParametros[1]+" )"
                                    , dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        ex2.printStackTrace();
      }

      try{
        //System.out.print("*****    SupDireccionInspeccion  "+VParametros.getPropEspecifica("SupDireccionInspeccion"));
        vRegs1 = super.FindByGeneric("","select ctitular from GRLDEPTOXOFIC where ICVEOFICINA = "+cParametros[2]+
                                     " and ICVEDEPARTAMENTO = "+VParametros.getPropEspecifica("SupDireccionInspeccion"),
                                     dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        ex2.printStackTrace();
      }
      try{
        String cJefe = VParametros.getPropEspecifica("%SubDireccionIngNaval%");
        //System.out.print("*****    SubDireccionIngNaval  "+cJefe);
        vRegs3 = super.FindByGeneric("","select ctitular from GRLDEPTOXOFIC where ICVEOFICINA = "+cParametros[2]+
                                     " and ICVEDEPARTAMENTO = "+VParametros.getPropEspecifica("SubDireccionIngNaval"),
                                     dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        ex2.printStackTrace();
      }


      try{
        String cJefe = VParametros.getPropEspecifica("JefeDeptoInspeccion");
        //System.out.print("*****    JefeDeptoInspeccion  "+cJefe);
        vRegs2 = super.FindByGeneric("","select ctitular from GRLDEPTOXOFIC where ICVEOFICINA = "+cParametros[2]+
                                     " and ICVEDEPARTAMENTO = "+cJefe,
                                     dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        ex2.printStackTrace();
      }
      TVDinRep vDatos1 = new TVDinRep();
      TVDinRep vDatos2 = new TVDinRep();
      TVDinRep vDatos3 = new TVDinRep();
      if(vRegs1.size()>0)
        vDatos1 = (TVDinRep) vRegs1.get(0);
      if(vRegs2.size()>0)
        vDatos2 = (TVDinRep) vRegs2.get(0);
      if(vRegs3.size()>0)
        vDatos3 = (TVDinRep) vRegs3.get(0);


      rep.iniciaReporte();
      if (vRegs.size() > 0){
        TVDinRep vDatos = (TVDinRep) vRegs.get(0);
        iCveVehiculo=vDatos.getInt("ICVEVEHICULO");
        cNomEmbarcacion=vDatos.getString("CNOMEMBARCACION");
        CDSCDOCTECNICO=vDatos.getString("CDSCDOCTECNICO");
        OFICINARESU=vDatos.getString("cOFICINARESU");
        DEPTORESU=vDatos.getString("cDEPTORESU");
        lRequiereAprovacion=vDatos.getInt("LREQUIEREAPROBACION");
        Date dtPrueba = vDatos.getDate("dtAutorizacion");
        CDSCTIPOEMBARCACION = vDatos.getString("CDSCTIPOEMBARCACION");
        cFecha = dtFechas.getFechaDDMMMMMYYYY(dtPrueba,"");
        cFecha2= dtFechas.getFechaTexto(dtPrueba,"");
      }
      rep.comRemplaza("[cNombreEmbarcacion]",cNomEmbarcacion);
      rep.comRemplaza("[cDocumentoTecnico]",CDSCDOCTECNICO);
      String aDocumento[] = CDSCDOCTECNICO.split(" ");
      rep.comRemplaza("[cDocumento]",aDocumento[0]);
      rep.comRemplaza("[cTipoEmbarcacion]",CDSCTIPOEMBARCACION);
      rep.comRemplaza("[cPuertoRes]",OFICINARESU);
      rep.comRemplaza("[cDia]",cFecha.substring(0,2));
      rep.comRemplaza("[cMes]",cFecha.substring(3,cFecha.length()-5));
      rep.comRemplaza("[cAnio]",cFecha.substring(cFecha.length()-4,cFecha.length()));
      rep.comRemplaza("[cFecha]",cFecha2);
      rep.comRemplaza("[cSubDirector]", vDatos1.getString("ctitular"));
      rep.comRemplaza("[cSubDirectorIngNaval]", vDatos3.getString("ctitular"));
      rep.comRemplaza("[cJefeDepto]",   vDatos2.getString("ctitular"));
      if(lRequiereAprovacion==1)rep.comRemplaza("[iTipoDocumento]","APROBACION");
      if(lRequiereAprovacion==0)rep.comRemplaza("[iTipoDocumento]","CUMPLIMIENTO");
      return rep.getEtiquetas(true);
}
public StringBuffer fCertAprobacion(String iConsecutivoCertExp) throws Exception{
        cError = "";
        TFechas dFechas = new TFechas();
        StringBuffer sbRetorno = new StringBuffer("");
        try{
          rep.iniciaReporte();
          String[] aParam = iConsecutivoCertExp.split(",");
              Vector vcData = findByCustom("",
                                           " SELECT " +
                                           "   VE.CNOMEMBARCACION,V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                           "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                           "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                           "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO " +
                                           " FROM VEHEMBARCACION VE " +
                                           " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " +                                         aParam[0] +
                                           " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                           " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                           " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO "+
                                           " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                           " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                           " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                           " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                           " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                           " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                           " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                           " where VE.iCveVehiculo = " + aParam[1]);

                  TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
                  Vector vcLugar = findByCustom("","SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                                   "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                                   "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                                   "WHERE ICVEOFICINA = "+ aParam[3]);
                TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
                if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("A",16,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("CDSCPUERTO").equals("null")) rep.comDespliegaAlineado("U",16,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("CNUMOMI").equals("null")) rep.comDespliegaAlineado("AD",16,vCertifIA.getString("CNUMOMI"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AD",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DTCONSTRUCCION").equals("null")) rep.comDespliegaAlineado("AI",16,""+vCertifIA.getDate("DTCONSTRUCCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AI",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DESLORA").equals("null")) rep.comDespliegaAlineado("A",30,vCertifIA.getString("DESLORA")+" "+vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DMANGA").equals("null")) rep.comDespliegaAlineado("O",30,vCertifIA.getString("DMANGA")+" "+vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("O",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DPUNTAL").equals("null")) rep.comDespliegaAlineado("Z",30,vCertifIA.getString("DPUNTAL")+" "+vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("Z",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("L",35,vCertifIA.getString("DARQUEOBRUTO")+" "+vCertifIA.getString("UARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("L",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DARQUEONETO").equals("null")) rep.comDespliegaAlineado("AB",35,vCertifIA.getString("DARQUEONETO")+" "+vCertifIA.getString("UARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AB",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("cSenialDist").equals("null")) rep.comDespliegaAlineado("K",16,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("k",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("S",43,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("S",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("W",43,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("W",30,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")) rep.comDespliegaAlineado("AD",43,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AD",43,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                if(!vCertiLugar.getString("MUNICIPIO").equals("null")) rep.comDespliegaAlineado("H",43,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("H",43,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                sbRetorno = rep.getSbDatos();
          }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
          }
        if(!cMensaje.equals(""))
          throw new Exception(cMensaje);
        return sbRetorno;
}

public StringBuffer fCertAprovMaritima(String iConsecutivoCertExp) throws Exception{
            cError = "";
            TFechas dFechas = new TFechas();
            StringBuffer sbRetorno = new StringBuffer("");
            String puerto = ""; //puerto de la estacion de servicio
            
       int nombreTecnico = 20;
       int ilongNombre = 0;       
       int iRengEvaluaOb2 = 0;
       
       try{

//Obras de Protección
       rep.iniciaReporte();
       String[] aParam = iConsecutivoCertExp.split(",");
       Vector vcData = findByCustom("",
           "SELECT es.DTREGISTRO, ES.DTAUTORIZACION, ES.CNOMESTACION, ES.ICVEESTACION, TC.IFOLIO, " +
           "PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CSOLISITANTE, PER.CRFC, " +
           "DOM.CCALLE||' '|| CNUMEXTERIOR AS CDOM, DOM.CCOLONIA, PA.CNOMBRE AS CPAIS, EF.CNOMBRE AS CENTFED, MU.CNOMBRE AS CMUN, DOM.CCODPOSTAL, " +
           "TC.CDSCCERTIFICADO, TE.CDSCTIPOESTACION, TE.CABREVIATURA,TE.CNORMA, CE.DTEXPEDICION, CE.DTFINVIGENCIA, CE.DTCANCELACION, " +
           "ES.CNUMESTACION,TE.ICVETIPOESTACION,TE.DTPUBLICNORMA,CE.IEJERCICIO,CE.INUMSOLICITUD,te.COBSERVACIONES,te.CFUNDAMENTO "+
           "FROM INSESTACIONSERV ES " +
           "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = ES.ICVEEMPRESA " +
           "JOIN GRLDOMICILIO DOM ON DOM.ICVEPERSONA = ES.ICVEEMPRESA AND DOM.ICVEDOMICILIO = ES.ICVEDOMICILIO " +
           "JOIN GRLPAIS PA ON PA.ICVEPAIS = DOM.ICVEPAIS " +
           "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = DOM.ICVEPAIS AND EF.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
           "JOIN GRLMUNICIPIO MU ON MU.ICVEPAIS= DOM.ICVEPAIS AND MU.ICVEENTIDADFED= DOM.ICVEENTIDADFED AND MU.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO " +
           "JOIN INSCERTIFXESTACION CE ON CE.ICVEESTACION = ES.ICVEESTACION " +
           "JOIN INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO = CE.ITIPOCERTIFICADO AND TC.ICVEGRUPOCERTIF = CE.ICVEGRUPOCERTIF " +
           "JOIN INSTIPOESTACION TE ON TE.ICVETIPOESTACION = ES.ICVETIPOESTACION  " +
           " WHERE CE.ICVEESTACION = "+ aParam[0] +" AND CE.ICONSECUTIVOTRAMITE = " + aParam[1] +
           " ORDER BY CE.DTEXPEDICION DESC");
       TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
       Vector vcTecnicos = new Vector();


       Vector vcDirector = findByCustom("","SELECT CTITULAR FROM GRLDEPTOXOFIC where ICVEDEPARTAMENTO = 50 ");
       TVDinRep vDirector = (TVDinRep) vcDirector.get(0);
       //if(!vDirector.getString("CTITULAR").equals("null")) rep.comDespliegaAlineado("s",50,vDirector.getString("CTITULAR"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("s",50,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

       String cNumEstacion = "";
       if(vCertifIA.getString("cNumEstacion")!=null && !vCertifIA.getString("cNumEstacion").equals("null"))
         cNumEstacion = vCertifIA.getString("cNumEstacion");

       if (!cNumEstacion.equals("") && !cNumEstacion.equals(null)) {
         vcTecnicos = findByCustom("",
             "SELECT ICVETECNICO, CNOMTECNICO as cNombre, LRESPESTACION,cNumTecnico " +
             "FROM INSTECNICO where ICVEESTACION = " + vCertifIA.getInt("ICVEESTACION")+" Order by LRESPESTACION desc,cNumTecnico asc");

//         rep.comDespliegaCombinado("MARCAS AUTORIZADAS","Q",80,"AE",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
//         rep.comDespliegaCombinado("NOMBRE DEL TECNICO","w",80,"ae",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);

         int iReng=2;
         //System.out.print("::Clave tipo Estacion::::::::" + vCertifIA.getInt("ICVETIPOESTACION"));
         //Caso 1
         if(vCertifIA.getInt("ICVETIPOESTACION")>12 && vCertifIA.getInt("ICVETIPOESTACION")<=27){
        	 rep.comDespliegaCombinado("CERTIFICADO DE REGISTRO","B",10,"AF",10,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,15,true,false,1,1);        	
         }
         else{
        	 rep.comDespliegaCombinado("CERTIFICADO DE APROBACION MARITIMA","B",10,"AF",10,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         }
         
         //rep.comDespliegaCombinado("DGMM-FE-E-01","AD",2,"AE",2,rep.getAT_COMBINA_DERECHA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);         
         
         int rengIni = 57;
         if(vCertifIA.getInt("ICVETIPOESTACION")==2 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==3  ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==13 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==14||
        		 vCertifIA.getInt("ICVETIPOESTACION")==15 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==16 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==17 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==18 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==19 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==20 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==21 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==22 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==23 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==24 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==25 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==26 ||
        		 vCertifIA.getInt("ICVETIPOESTACION")==27 
        		 ){
           rep.comDespliegaCombinado("Nº DE REGISTRO","L",rengIni,"R",rengIni,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
                      
           rep.comDespliegaCombinado("TÉCNICO RESPONSABLE","B",rengIni,"K",rengIni,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
           
           rep.comDespliegaCombinado("MARCAS","S",rengIni,"AE",rengIni,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);

           rep.comDespliegaCombinado("Nº DE REGISTRO","B",rengIni+3,"G",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
           
           rep.comDespliegaCombinado("NOMBRE DEL TECNICO","H",rengIni+3,"R",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
           
           rep.comDespliegaCombinado("MARCAS","S",rengIni+3,"AE",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);

           for(int i = 0;i < vcTecnicos.size();i++){
             TVDinRep vTecnicos = (TVDinRep) vcTecnicos.get(i);
             String cInst = vCertifIA.getString("cNumEstacion");
             String cTec = vTecnicos.getString("cNumTecnico");
             rep.comFontSize("a",rengIni,"az",300,7);
             if(vTecnicos.getInt("LRESPESTACION") == 1){

                 rep.comDespliegaCombinado("DGMM-" + vCertifIA.getString("CABREVIATURA") + "-" + cInst+ "-" + cTec,"L",rengIni+1,"R",(rengIni+1), rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VCENTRO(),false,2,true,false,1,1);
                 rep.comAlineaRango("L",rengIni+1,"R",(rengIni+1),rep.getAT_HJUSTIFICA());

                rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"B",rengIni+1,"K",(rengIni+1),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);                                  
                rep.comAlineaRango("B",rengIni+1,"K",(rengIni+1),rep.getAT_HJUSTIFICA());
             }else{
            	 rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"B",rengIni+1 + iReng,"G",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
            	 rep.comAlineaRango("B",rengIni+1 + iReng,"G",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());
            	 
                 rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"H",rengIni+1 + iReng,"R",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                 rep.comAlineaRango("H",rengIni+1 + iReng,"R",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());
                 
	             //no s
	                           	
                	/* vTecnicos = (TVDinRep) vcTecnicos.get(i);
                     cInst = vCertifIA.getString("cNumEstacion");
                     cTec = vTecnicos.getString("cNumTecnico");
                     
                	 rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"Q",rengIni+1 + iReng,"U",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
                	 rep.comAlineaRango("Q",rengIni+1 + iReng,"U",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());

                     rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"V",rengIni+1 + iReng,"AD",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
                     rep.comAlineaRango("V",rengIni+1 + iReng,"AD",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());*/
                 	
	            //no s
                 
             }
             Vector vcMarcas = findByCustom("",
                                            "SELECT CMARCAS FROM INSMARCAXTECNICO "+
                                            "where ICVEESTACION="+vCertifIA.getInt("ICVEESTACION")+
                                            " and ICVETECNICO="+vTecnicos.getInt("ICVETECNICO")+
                                            " and DTCERTIFICADO='"+vCertifIA.getDate("DTEXPEDICION")+"'");
             String cMarcas = "";
             if(vcMarcas.size()>0){
	             TVDinRep vMarcas = (TVDinRep) vcMarcas.get(0);
	             cMarcas = vMarcas.getString("CMARCAS");
	             if(vTecnicos.getInt("LRESPESTACION") == 1){
	            	 rep.comDespliegaCombinado("MARCAS","S",rengIni,"AE",rengIni,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
	            	 rep.comAlineaRango("S",rengIni,"AE",rengIni,rep.getAT_HJUSTIFICA());
	            	 
		             rep.comDespliegaCombinado(cMarcas,"S",rengIni+1,"AE",rengIni+1,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
		             rep.comAlineaRango("S",rengIni+1,"AE",rengIni+1,rep.getAT_HJUSTIFICA());
	             }else {
	            	 rep.comDespliegaCombinado("MARCAS","S",rengIni+3,"AE",rengIni+3,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
	            	 rep.comAlineaRango("S",rengIni+3,"AE",rengIni+3,rep.getAT_HJUSTIFICA());
	            	 
		             rep.comDespliegaCombinado(cMarcas,"S",rengIni+1 + iReng,"AE",rengIni+1 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
		             rep.comAlineaRango("S",rengIni+1,"AE",rengIni+1,rep.getAT_HJUSTIFICA());
		             	            	 
	             }
             }
             iReng++;
           }
         }
         //Caso 2
         if(vCertifIA.getInt("ICVETIPOESTACION")==1){
             rep.comDespliegaCombinado("Nº DE REGISTRO","Q",rengIni,"Z",rengIni,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
             
             rep.comDespliegaCombinado("TÉCNICO RESPONSABLE","E",rengIni,"P",rengIni,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);

             rep.comDespliegaCombinado("Nº DE REGISTRO","B",rengIni+3,"G",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
             
             rep.comDespliegaCombinado("NOMBRE DEL TECNICO","H",rengIni+3,"R",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);

             for(int i = 0;i < vcTecnicos.size();i++){
                 rep.comDespliegaCombinado("Nº DE REGISTRO","R",rengIni+3,"W",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);                 
                 rep.comDespliegaCombinado("NOMBRE DEL TECNICO","x",rengIni+3,"Ae",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
                 //rep.comAlineaRango("V",rengIni+3,"AD",rengIni+3,rep.getAT_HJUSTIFICA());
                 TVDinRep vTecnicos = (TVDinRep) vcTecnicos.get(i);
                 String cInst = vCertifIA.getString("cNumEstacion");
                 String cTec = vTecnicos.getString("cNumTecnico");
                 rep.comFontSize("a",rengIni,"az",300,7);
                 if(vTecnicos.getInt("LRESPESTACION") == 1){
                	 
                     rep.comDespliegaCombinado("DGMM-" + vCertifIA.getString("CABREVIATURA") + "-" + cInst+ "-" + cTec,"Q",rengIni+1,"Z", (rengIni+1), rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VCENTRO(),false,2,true,false,1,1);
                     rep.comAlineaRango("Q",rengIni+1,"Z",(rengIni+1),rep.getAT_HJUSTIFICA());
                     
                     rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"E",rengIni+1,"P",(rengIni+1),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                     rep.comAlineaRango("E",rengIni+1,"P",(rengIni+1),rep.getAT_HJUSTIFICA());
                 }
                     
                     else{                    	 
                    	 rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"B",(rengIni+1+ iReng),"G",rengIni+1 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                    	 rep.comAlineaRango("B",rengIni+1 + iReng,"G",(rengIni+1+iReng),rep.getAT_HJUSTIFICA());

                         rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"H",rengIni+1 + iReng,"Q",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                         rep.comAlineaRango("H",rengIni+1 + iReng,"Q",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());   
                         
                         if(i<vcTecnicos.size()){
                        	 i++;
                        	 vTecnicos = (TVDinRep) vcTecnicos.get(i);
                             cInst = vCertifIA.getString("cNumEstacion");
                             cTec = vTecnicos.getString("cNumTecnico");
                             
                        	 rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"R",rengIni+1 + iReng,"W",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                        	 rep.comAlineaRango("R",rengIni+1 + iReng,"W",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());

                             rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"X",rengIni+1 + iReng,"AE",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                             rep.comAlineaRango("X",rengIni+1 + iReng,"AE",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());
                         }                         
                     }                     
                 iReng++;
               }
         }
         
         //caso 3
         if(vCertifIA.getInt("ICVETIPOESTACION")==12){
             rep.comDespliegaCombinado("Nº DE REGISTRO","Q",rengIni,"Z",rengIni,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
             
             rep.comDespliegaCombinado("TÉCNICO RESPONSABLE","E",rengIni,"P",rengIni,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);

             rep.comDespliegaCombinado("Nº DE REGISTRO","B",rengIni+3,"G",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
             
             rep.comDespliegaCombinado("NOMBRE DEL TECNICO","H",rengIni+3,"Q",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);             
             
             for(int i = 0;i < vcTecnicos.size();i++){
                 rep.comDespliegaCombinado("Nº DE REGISTRO","R",rengIni+3,"W",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);                 
                 rep.comDespliegaCombinado("NOMBRE DEL TECNICO","X",rengIni+3,"AE",rengIni+3,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
                 
                 TVDinRep vTecnicos = (TVDinRep) vcTecnicos.get(i);
                 String cInst = vCertifIA.getString("cNumEstacion");
                 String cTec = vTecnicos.getString("cNumTecnico");
                 rep.comFontSize("a",rengIni,"az",300,7);
                 if(vTecnicos.getInt("LRESPESTACION") == 1){
                	 
                     rep.comDespliegaCombinado("DGMM-" + vCertifIA.getString("CABREVIATURA") + "-" + cInst+ "-" + cTec,"Q",rengIni+1,"Z", (rengIni+1), rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VCENTRO(),false,2,true,false,1,1);
                     rep.comAlineaRango("Q",rengIni+1,"Z",(rengIni+1),rep.getAT_HJUSTIFICA());

                     rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"E",rengIni+1,"P",(rengIni+1),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                     rep.comAlineaRango("E",rengIni+1,"P",(rengIni+1),rep.getAT_HJUSTIFICA());
                 }else{
                	 
                	 rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"B",(rengIni+1+ iReng),"G",rengIni+1 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                	 rep.comAlineaRango("B",rengIni+1 + iReng,"G",(rengIni+1+iReng),rep.getAT_HJUSTIFICA());

                     rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"H",rengIni+1 + iReng,"Q",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                     rep.comAlineaRango("H",rengIni+1 + iReng,"Q",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());
                	 i++;
                     if(i<vcTecnicos.size()){
                    	 vTecnicos = (TVDinRep) vcTecnicos.get(i);
                         cInst = vCertifIA.getString("cNumEstacion");
                         cTec = vTecnicos.getString("cNumTecnico");
                         
                    	 rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"R",rengIni+1 + iReng,"W",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                    	 rep.comAlineaRango("R",rengIni+1 + iReng,"W",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());

                         rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"X",rengIni+1 + iReng,"AE",(rengIni+1 + iReng),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                         rep.comAlineaRango("X",rengIni+1 + iReng,"AE",(rengIni+1 + iReng),rep.getAT_HJUSTIFICA());
                     }
                 }
                 iReng++;
             }
             Vector vcMarcas = findByCustom("","SELECT " +
            		 "'DGMM-'||te.CABREVIATURA||'-'||es.CNUMESTACION||'-'|| t.CNUMTECNICO as creg, " +
            		 "t.CNOMTECNICO ||' '|| t.CAPPATTECNICO ||' '|| t.CAPMATTECNICO as ctecnico, " +
            		 "mt.CMARCAS " +
            		 "FROM INSMARCAXTECNICO mt " +
            		 "join INSTECNICO t on t.ICVEESTACION = mt.ICVEESTACION and t.ICVETECNICO=mt.ICVETECNICO " +
            		 "join INSESTACIONSERV es on es.ICVEESTACION = mt.ICVEESTACION " +
            		 "join INSTIPOESTACION te on te.ICVETIPOESTACION=es.ICVETIPOESTACION " +
            		 "where mt.ICVEESTACION= " +vCertifIA.getInt("ICVEESTACION")+
                     " and DTCERTIFICADO='"+vCertifIA.getDate("DTEXPEDICION")+"'");
             String cMarcas = "";
             iReng++;
             iReng++;

             rep.comDespliegaCombinado("TÉCNICOS REGISTRADOS PARA REPARACIÓN Y MANTENIMIENTO DE EQUIPOS DE RESPIRACIÓN AUTONOMA","B",rengIni+iReng,"AE",rengIni+iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
             rep.comAlineaRango("B",rengIni+iReng,"AE",rengIni+iReng,rep.getAT_HJUSTIFICA());
             iReng++;
             iReng++;
             
             if(vcMarcas.size()>0){
       			 rep.comDespliegaCombinado("No DE REGISTRO","B",rengIni+iReng-1,"G",rengIni+iReng-1,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
       			 rep.comAlineaRango("B",rengIni+iReng-1,"G",rengIni+iReng-1,rep.getAT_HJUSTIFICA());
       			 
       			 rep.comDespliegaCombinado("NOMBRE DEL TECNICO","H",rengIni+iReng-1,"Q",rengIni+iReng-1,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
       			 rep.comAlineaRango("H",rengIni+iReng-1,"Q",rengIni+iReng-1,rep.getAT_HJUSTIFICA());
       			
       			 rep.comDespliegaCombinado("MARCAS","R",rengIni+iReng-1,"AE",rengIni+iReng-1,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
       			 rep.comAlineaRango("R",rengIni+iReng-1,"AE",rengIni+iReng-1,rep.getAT_HJUSTIFICA());            	 
             }
             
             for(int im=0;im<vcMarcas.size();im++){
            	 TVDinRep vMarcas = (TVDinRep) vcMarcas.get(im);
            	 cMarcas = vMarcas.getString("CMARCAS");
            	 
      			 rep.comDespliegaCombinado(vMarcas.getString("creg"),"B",rengIni + iReng+im,"G",(rengIni+iReng+im),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
      			 rep.comAlineaRango("B",rengIni + iReng+im,"G",(rengIni + iReng+im),rep.getAT_HJUSTIFICA());
      			 
      			 rep.comDespliegaCombinado(vMarcas.getString("ctecnico"),"H",rengIni + iReng+im,"Q",(rengIni+iReng+im),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
      			 rep.comAlineaRango("H",rengIni + iReng+im,"Q",(rengIni + iReng+im),rep.getAT_HJUSTIFICA());
      			 
      			 rep.comDespliegaCombinado(cMarcas,"R",rengIni+iReng+im,"AE",(rengIni+iReng+im),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
      			 rep.comAlineaRango("R",rengIni + iReng+im,"AE",(rengIni + iReng+im),rep.getAT_HJUSTIFICA());
             }                                     
         }
         
         Vector vcInfPuerto = findByCustom("","SELECT est.ICVEESTACION, pto.CDSCPUERTO, ent.CNOMBRE " + 
								" FROM INSESTACIONSERV est " +
								" JOIN grlpuerto pto ON pto.ICVEPUERTO = est.ICVEPUERTO " +
								" JOIN GRLENTIDADFED ent ON ent.ICVEENTIDADFED = pto.ICVEENTIDADFED " +
								" and ent.ICVEPAIS = 1 and icveestacion = "+ vCertifIA.getInt("ICVEESTACION"));
         
         if(vcInfPuerto.size() > 0){
	         for(int countPto=0; countPto<vcInfPuerto.size();countPto++){
	        	 TVDinRep vPuerto = (TVDinRep) vcInfPuerto.get(countPto);
	        	 puerto = vPuerto.getString("CDSCPUERTO") + ", " + vPuerto.getString("CNOMBRE") + " ";
	         }
         }
       }
       
       rep.comDespliegaAlineado("b",8,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
       
        if(!vCertifIA.getString("CSOLISITANTE").equals("null")) 
        	rep.comDespliegaCombinado(vCertifIA.getString("CSOLISITANTE"),"E",12,"AF",12,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,false,false,1,1);
        
        //autorizandose como:
        if(!vCertifIA.getString("COBSERVACIONES").equals("null")){ 
            int ilongCObserv = vCertifIA.getString("COBSERVACIONES").length();
            int iLongRengOb = 63; //Número de caracteres por renglón
        	
            int iRengEvaluaOb = 0;
            while(ilongCObserv > 0){
            	ilongCObserv = ilongCObserv - iLongRengOb;
            	iRengEvaluaOb += 1;
            }
            
        	rep.comDespliegaCombinado(vCertifIA.getString("COBSERVACIONES").replace("[NombrePuerto]",puerto),"B",16,"AE",16 + (iRengEvaluaOb - 2) ,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,false,false,1,1);
        	rep.comAlineaRango("B",16,"AE",16 + (iRengEvaluaOb - 2),rep.getAT_HJUSTIFICA());
        }
        
        int terminaFund=0;
        //FUNDAMENTO...
        if(!vCertifIA.getString("CFUNDAMENTO").equals("null")) {        	
           int ilongEvalua = vCertifIA.getString("CFUNDAMENTO").length();
           int iLongReng = 63; //Número de caracteres por renglón
           
           int iRengEvalua = 0;
           while(ilongEvalua > 0){
             ilongEvalua = ilongEvalua - iLongReng;
             iRengEvalua += 1;
           }
           
           terminaFund = 34 + (iRengEvalua - 1);
           rep.comDespliegaCombinado(vCertifIA.getString("CFUNDAMENTO"),"B",29,"AE",29 + (iRengEvalua - 2),rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,false,false,0,1);
           rep.comAlineaRango("B",29,"AE",29 + iRengEvalua - 2,rep.getAT_HJUSTIFICA());
        }
        	//rep.comDespliegaCombinado(vCertifIA.getString("CFUNDAMENTO"),"B",34,"AF",36,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
                                
        if(!vCertifIA.getString("CRFC").equals("null")) rep.comDespliegaAlineado("X",14,vCertifIA.getString("CRFC"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("X",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        TFechas Fecha = new TFechas();
        String cFechaActual =""+Fecha.aumentaDisminuyeDias(vCertifIA.getDate("DTEXPEDICION"),335);
        String cFechaActual1 = cFechaActual.substring(0,4)+cFechaActual.substring(5,7)+cFechaActual.substring(8,10);
        String cFechaVigencia = "" + vCertifIA.getDate("DTFINVIGENCIA");
        String cFechaVigencia1 = cFechaVigencia.substring(0,4)+cFechaVigencia.substring(5,7)+cFechaVigencia.substring(8,10);
        if(Integer.parseInt(cFechaActual1)>Integer.parseInt(cFechaVigencia1)){
          rep.comDespliegaAlineado("A",10,"PROVISIONAL",false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        }

        if(!vCertifIA.getString("CDOM").equals("null")) rep.comDespliegaAlineado("j",23,vCertifIA.getString("CDOM"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("j",27,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CCOLONIA").equals("null")) rep.comDespliegaAlineado("j",25,""+vCertifIA.getString("CCOLONIA"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("j",29,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CCODPOSTAL").equals("null")) rep.comDespliegaAlineado("j",27,"C.P. "+vCertifIA.getString("CCODPOSTAL")+", "+vCertifIA.getString("CMUN")+", "+vCertifIA.getString("CENTFED"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("j",27,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

        if(!vCertifIA.getString("CDSCTIPOESTACION").equals("null")) rep.comDespliegaAlineado("j",17,vCertifIA.getString("CDSCTIPOESTACION"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("J",17,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!cNumEstacion.equals("null"))rep.comDespliegaAlineado("AC",5,cNumEstacion,false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AC",5,cNumEstacion,false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); 
        if(!vCertifIA.getString("CABREVIATURA").equals("null")) rep.comDespliegaAlineado("Y",5,vCertifIA.getString("CABREVIATURA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("Y",5,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

        if(!vCertifIA.getDate("DTEXPEDICION").equals("null")) {
         java.util.Date hoy = vCertifIA.getDate("DTEXPEDICION"); 
        
        String mesLetra ="Mes";
          int mes = hoy.getMonth();
          if(mes == 0){ mesLetra=" Enero";}	if(mes==1){mesLetra=" Febrero";}	 if(mes==2){mesLetra=" Marzo";}                 
          if(mes==3){mesLetra=" Abril";}	if(mes==4){mesLetra=" Mayo";}	if(mes==5){mesLetra=" Junio";}                
          if(mes==6){mesLetra=" Julio";}	if(mes==7){mesLetra=" Agosto";} 	if(mes==8){mesLetra=" Septiembre";}                
        if(mes==9){mesLetra=" Octubre";}	if(mes==10){mesLetra=" Noviembre";} if(mes==11){mesLetra=" Diciembre";}        
        
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String arrayfecha[] = df.format(hoy).split("-");
        
                           //  19           de    Septiembre   de    2011
          String fechaHoy = hoy.getDate() + " de "+ mesLetra + " de "+ arrayfecha[2]; 
                
        	rep.comDespliegaCombinado("Expedido en México D.F., a " + fechaHoy,"C",terminaFund,"AE",terminaFund,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,false,false,1,1);
        }	
        // 39 ahora terminaFund+2
        if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")){ 
        	
        	rep.comDespliegaCombinado("Que será valido hasta:","C",terminaFund-2,"I",terminaFund-2,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,false,false,1,1);        	
        	rep.comDespliegaAlineado("J",terminaFund-2,""+dFechas.getStringDay(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
        	rep.comDespliegaCombinado((" de "+dFechas.getMonthName(vCertifIA.getDate("DTFINVIGENCIA")) + " de " + dFechas.getIntYear(vCertifIA.getDate("DTFINVIGENCIA"))),"K",terminaFund-2,"R",terminaFund-2,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,false,false,1,1);
        	//rep.comDespliegaAlineado("R",terminaFund+2,""+dFechas.getIntYear(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
        }
        
         rep.comDespliegaAlineado("E",terminaFund+4,"SELLO",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
         rep.comDespliegaAlineado("U",terminaFund+4,"EL DIRECTOR GENERAL",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR()); 
         
         rep.comDespliegaAlineado("Q",terminaFund+9,VParametros.getPropEspecifica("DIR_GENERAL"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        
        sbRetorno = rep.getSbDatos();
       }
       catch(Exception e){
         e.printStackTrace();
         cMensaje += e.getMessage();
       }
       if(!cMensaje.equals(""))
         throw new Exception(cMensaje);
       return sbRetorno;
   }

    public StringBuffer fCertAprobMaritimaTec(String iConsecutivoCertExp, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen) throws Exception{
            cError = "";
            TFechas dFechas = new TFechas();
            StringBuffer sbRetorno = new StringBuffer("");
       try{
//Obras de Protección

       rep.iniciaReporte();
       //System.out.print("*****     "+iConsecutivoCertExp);
       String[] aParam = iConsecutivoCertExp.split(",");
       Vector vcData = this.findByCustom("",
           "SELECT es.DTREGISTRO, ES.DTAUTORIZACION, ES.CNOMESTACION, ES.ICVEESTACION, TC.IFOLIO, " +
           "PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CSOLISITANTE, PER.CRFC, " +
           "DOM.CCALLE||' '|| CNUMEXTERIOR AS CDOM, DOM.CCOLONIA, PA.CNOMBRE AS CPAIS, EF.CNOMBRE AS CENTFED, MU.CNOMBRE AS CMUN, DOM.CCODPOSTAL, " +
           "TC.CDSCCERTIFICADO, TE.CDSCTIPOESTACION, TE.CABREVIATURA,TE.CNORMA, CE.DTEXPEDICION, CE.DTFINVIGENCIA, CE.DTCANCELACION, " +
           "ES.CNUMESTACION,TE.ICVETIPOESTACION,CE.IEJERCICIO,CE.INUMSOLICITUD,TE.DTPUBLICNORMA "+
           "FROM INSESTACIONSERV ES " +
           "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = ES.ICVEEMPRESA " +
           "JOIN GRLDOMICILIO DOM ON DOM.ICVEPERSONA = ES.ICVEEMPRESA AND DOM.ICVEDOMICILIO = ES.ICVEDOMICILIO " +
           "JOIN GRLPAIS PA ON PA.ICVEPAIS = DOM.ICVEPAIS " +
           "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = DOM.ICVEPAIS AND EF.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
           "JOIN GRLMUNICIPIO MU ON MU.ICVEPAIS= DOM.ICVEPAIS AND MU.ICVEENTIDADFED= DOM.ICVEENTIDADFED AND MU.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO " +
           "JOIN INSCERTIFXESTACION CE ON CE.ICVEESTACION = ES.ICVEESTACION " +
           "JOIN INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO = CE.ITIPOCERTIFICADO AND TC.ICVEGRUPOCERTIF = CE.ICVEGRUPOCERTIF " +
           "JOIN INSTIPOESTACION TE ON TE.ICVETIPOESTACION = ES.ICVETIPOESTACION  " +
           " WHERE CE.ICVEESTACION = "+ aParam[0] +" AND CE.ICONSECUTIVOTRAMITE = " + aParam[1] +
           " ORDER BY CE.DTEXPEDICION DESC");
       TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
       Vector vcTecnicos = new Vector();


       Vector vcDirector = findByCustom("","SELECT CTITULAR FROM GRLDEPTOXOFIC where ICVEDEPARTAMENTO = 50 ");
       TVDinRep vDirector = (TVDinRep) vcDirector.get(0);
       if(!vDirector.getString("CTITULAR").equals("null")) rep.comDespliegaAlineado("s",50,vDirector.getString("CTITULAR"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("s",50,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

       int iNumEstacion = 0;
       if(vCertifIA.getString("cNumEstacion")!=null && !vCertifIA.getString("cNumEstacion").equals("null"))
         iNumEstacion = Integer.parseInt(vCertifIA.getString("cNumEstacion"));

       if (iNumEstacion>0) {
         vcTecnicos = findByCustom("",
             "SELECT ICVETECNICO, CNOMTECNICO as cNombre, LRESPESTACION,cNumTecnico " +
             "FROM INSTECNICO where ICVEESTACION = " + vCertifIA.getInt("ICVEESTACION")+" Order by LRESPESTACION desc,int(cNumTecnico) asc");
         /*rep.comDespliegaCombinado("Nº DE REGISTRO","c",80,"G",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         rep.comDespliegaCombinado("NOMBRE DEL TECNICO","H",80,"p",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         rep.comDespliegaCombinado("Nº DE REGISTRO","s",80,"W",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         rep.comDespliegaCombinado("NOMBRE DEL TECNICO","x",80,"af",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         */
//         rep.comDespliegaCombinado("MARCAS AUTORIZADAS","Q",80,"AE",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
//         rep.comDespliegaCombinado("NOMBRE DEL TECNICO","w",80,"ae",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);

         int iReng=1;
         if(true){
           for(int i = 0;i < vcTecnicos.size();i++){
             TVDinRep vTecnicos = (TVDinRep) vcTecnicos.get(i);
             String cInst = "",cTec = "";
             int iNumTecnico = Integer.parseInt(vTecnicos.getString(
                 "cNumTecnico"));
             if(iNumEstacion > 0 && iNumEstacion < 10)
               cInst = "00" + iNumEstacion;
             if(iNumEstacion > 9 && iNumEstacion < 100)
               cInst = "0" + iNumEstacion;
             if(iNumEstacion > 99) cInst = iNumEstacion + "";
             if(iNumTecnico > 0 && iNumTecnico < 10)
               cTec = "00" + iNumTecnico;
             if(iNumTecnico > 9 && iNumTecnico < 100)
               cTec = "0" + iNumTecnico;
             rep.comFontSize("a",80,"az",300,7);
             if( (iNumTecnico == 1) && vTecnicos.getInt("LRESPESTACION") == 1)
                 rep.comDespliegaCombinado("DGMM-" + vCertifIA.getString("CABREVIATURA") + "-" + cInst,"B",30 + iReng,"B", 30 + iReng, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VCENTRO(),true,2,true,false,1,1);
             else rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"B",30 + iReng,"B",30 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
             rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"A",30 + iReng,"A",30 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
             Vector vcMarcas = findByCustom("",
                                            "SELECT M.CDSCMARCA " +
                                            "FROM INSMARCATECNICO MT " +
                                            "JOIN INSMARCASAUT M ON MT.ICVEMARCA = M.ICVEMARCA " +
                                            "WHERE MT.ICVEESTACION = " +
                                            vCertifIA.getInt("ICVEESTACION") +
                                            " AND MT.ICVETECNICO = " +
                                            vTecnicos.getInt("ICVETECNICO"));
             String cMarcas = "";
             for(int k = 0;k < vcMarcas.size();k++){
               TVDinRep vMarcas = (TVDinRep) vcMarcas.get(k);
               cMarcas += vMarcas.getString("CDSCMARCA");
               cMarcas += (k + 1 < vcMarcas.size()) ? ", " : ".";
               //rep.comDespliegaCombinado("MARCAS AUTORIZADAS","Q",80,"AE",80,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
               rep.comDespliegaCombinado(cMarcas,"C",30 + iReng,"C",30 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
             }

             iReng++;
           }
         }
         else{
           for(int i = 0;i < vcTecnicos.size();i++){
             TVDinRep vTecnicos = (TVDinRep) vcTecnicos.get(i);
             String cInst = "",cTec = "";
             int iNumTecnico = Integer.parseInt(vTecnicos.getString("cNumTecnico"));
             if(iNumEstacion > 0 && iNumEstacion < 10)
               cInst = "00" + iNumEstacion;
             if(iNumEstacion > 9 && iNumEstacion < 100)
               cInst = "0" + iNumEstacion;
             if(iNumEstacion > 99) cInst = iNumEstacion + "";
             if(iNumTecnico > 0 && iNumTecnico < 10)
               cTec = "00" + iNumTecnico;
             if(iNumTecnico > 9 && iNumTecnico < 100)
               cTec = "0" + iNumTecnico;
             //rep.comFontSize("a",80,"az",300,7);
             if( (iNumTecnico == 1) && vTecnicos.getInt("LRESPESTACION") == 1)
               rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst,"A",30 + iReng,"A",30 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
             else rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"A",30 + iReng,"A",30 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
             rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"B",30 + iReng,"B",30 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
             iReng++;
           }
         }
        }
        TFechas Fecha = new TFechas();
        String cFechaActual =""+Fecha.aumentaDisminuyeDias(vCertifIA.getDate("DTEXPEDICION"),335);
        String cFechaActual1 = cFechaActual.substring(0,4)+cFechaActual.substring(5,7)+cFechaActual.substring(8,10);
        String cFechaVigencia = "" + vCertifIA.getDate("DTFINVIGENCIA");
        String cFechaVigencia1 = cFechaVigencia.substring(0,4)+cFechaVigencia.substring(5,7)+cFechaVigencia.substring(8,10);
        if(Integer.parseInt(cFechaActual1)>Integer.parseInt(cFechaVigencia1)){
          rep.comDespliegaAlineado("A",1,"PROVISIONAL",false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        }

        if(!vCertifIA.getString("CDOM").equals("null")) rep.comDespliegaAlineado("A",2,vCertifIA.getString("CDOM"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("A",2,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CCOLONIA").equals("null")) rep.comDespliegaAlineado("A",3,""+vCertifIA.getString("CCOLONIA"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("A",3,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CCODPOSTAL").equals("null")) rep.comDespliegaAlineado("A",4,"C.P. "+vCertifIA.getString("CCODPOSTAL")+", "+vCertifIA.getString("CMUN")+", "+vCertifIA.getString("CENTFED"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("A",4,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

        if(!vCertifIA.getString("CDSCTIPOESTACION").equals("null")) rep.comDespliegaAlineado("A",5,vCertifIA.getString("CDSCTIPOESTACION"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("A",5,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CDSCCERTIFICADO").equals("null")) rep.comDespliegaAlineado("A",6,vCertifIA.getString("CDSCCERTIFICADO"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("A",6,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CNORMA").equals("null")) rep.comDespliegaAlineado("A",7,vCertifIA.getString("CNORMA"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("A",7,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(iNumEstacion>0) rep.comDespliegaAlineado("A",8,formatoNum000(iNumEstacion),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",8,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CABREVIATURA").equals("null")) rep.comDespliegaAlineado("A",9,vCertifIA.getString("CABREVIATURA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",9,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CNORMA").equals("null")) rep.comDespliegaAlineado("A",10,"CONFORME A LA NORMA: "+vCertifIA.getString("CNORMA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",10,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CNORMA").equals("null")) rep.comDespliegaAlineado("A",11,vCertifIA.getString("CNORMA"),false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",11,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        //if(!vCertifIA.getString("DTPUBLICNORMA").equals("null")) rep.comDespliegaAlineado("A",12,vCertifIA.getString("DTPUBLICNORMA"),false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",12,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

        if(!vCertifIA.getString("DTEXPEDICION").equals("null")) rep.comDespliegaAlineado("A",13,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICION")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",13,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("DTEXPEDICION").equals("null")) rep.comDespliegaAlineado("A",14,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICION")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("DTEXPEDICION").equals("null")) rep.comDespliegaAlineado("A",15,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICION")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("DTEXPEDICION").equals("null")) rep.comDespliegaAlineado("B",13,""+dFechas.getDateSPN(vCertifIA.getDate("DTEXPEDICION")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("B",13,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

        if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("A",16,""+dFechas.getStringDay(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("A",17,""+dFechas.getMonthName(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",17,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("A",18,""+dFechas.getIntYear(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",18,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("B",16,""+dFechas.getDateSPN(vCertifIA.getDate("DTEXPEDICION")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("B",16,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());


        if(!vCertifIA.getString("IEJERCICIO").equals("null")) rep.comDespliegaAlineado("A",19,""+vCertifIA.getString("IEJERCICIO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",19,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("INUMSOLICITUD").equals("null")) rep.comDespliegaAlineado("A",20,""+vCertifIA.getString("INUMSOLICITUD"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",20,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CSOLISITANTE").equals("null")) rep.comDespliegaAlineado("A",21,vCertifIA.getString("CSOLISITANTE"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("A",21,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CRFC").equals("null")) rep.comDespliegaAlineado("A",22,vCertifIA.getString("CRFC"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",22,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("CABREVIATURA").equals("null")) rep.comDespliegaAlineado("A",23,vCertifIA.getString("CABREVIATURA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",23,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("cNumEstacion").equals("null")) rep.comDespliegaAlineado("A",24,vCertifIA.getString("cNumEstacion"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",24,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("cNumEstacion").equals("null")) rep.comDespliegaAlineado("A",25,"DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + vCertifIA.getString("cNumEstacion"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",25,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(cNumFolio!="") rep.comDespliegaAlineado("A",26,cNumFolio,false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",26,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        if(!vCertifIA.getString("DTPUBLICNORMA").equals("null")) rep.comDespliegaAlineado("A",28,""+dFechas.getDateSPN(vCertifIA.getDate("DTPUBLICNORMA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",28,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        int iCveTipoCert = vCertifIA.getInt("");

        //String cTipoCertTexto = "";
        //if(iCveTipoCert=iContraincendios) cTipoCertTexto = "de equipos y sistemas de contraincendio";
        //if(iCveTipoCert=iContraincendios) cTipoCertTexto = "a balsas salvavidas autoinflables";
        //if(iCveTipoCert=iContraincendios) cTipoCertTexto = "a botes salvavidas totalmente cerrados";
        if(vCertifIA.getString("CDSCTIPOESTACION")!=""){
          String cTexto =
              "El presente Certificado de Aprobación Marítima ampara a un total de " +  (vcTecnicos.size() + 1) +
              " personas, como técnicos de la estación, siendo el primero de ello reconocido por la Dirección General de Marina Mercante como el técnico responsable de los trabajos de reparación y mantenimiento " +
              vCertifIA.getString("CDSCTIPOESTACION").toLowerCase() + " que se realice dicho personal.";

        }

        sbRetorno = rep.getSbDatos();
       }
       catch(Exception e){
         e.printStackTrace();
         cMensaje += e.getMessage();
       }
       if(!cMensaje.equals(""))
         throw new Exception(cMensaje);
       return sbRetorno;
    }


    public StringBuffer fDeficiencias(String cParametros) throws Exception{
            cError = "";
            TFechas dFechas = new TFechas();
            StringBuffer sbRetorno = new StringBuffer("");


              try{

//Obras de Protección
                  rep.iniciaReporte();
       //String[] aParam = cParametros.split(",");
       Vector vcData = findByCustom("",
              "select INSMedXDeficiencia.iCodigoMedida,INSMedAdoptadas.cDscMedidaEsp,INSMedXDeficiencia.dtCumplimiento, " +
              "INSCODDEFICIENCIA.CDSCCODDEFICIENCIAESP,U.CNOMBRE||' '|| U.CAPPATERNO||' '|| U.CAPMATERNO AS CINSPECTOR " +
              "from INSMedXDeficiencia " +
              "left join INSMedAdoptadas on INSMedAdoptadas.iCodigoMedida = INSMedXDeficiencia.iCodigoMedida " +
              "left join INSCODDEFICIENCIA on INSMEDXDEFICIENCIA.ICVECODDEFICIENCIA  = INSCODDEFICIENCIA.ICVECODDEFICIENCIA " +
              "left JOIN INSINSPECCION I ON I.ICVEINSPECCION = INSMEDXDEFICIENCIA.ICVEINSPECCION " +
              "left JOIN INSCERTXINSPECCION CI ON CI.ICVEINSPPROG = I.ICVEINSPECCION " +
              "left JOIN INSPROGINSP PI ON PI.ICVEINSPPROG = I.ICVEINSPPROG " +
              "left JOIN INSINSPECTOR IP ON IP.ICVEINSPECTOR = PI.ICVEINSPECTOR " +
              "left JOIN SEGUSUARIO U ON U.ICVEUSUARIO = IP.ICVEUSUARIO " +
              "where INSMEDXDEFICIENCIA.iCveInspeccion =  "+cParametros);
       TVDinRep vDetalles = (TVDinRep) vcData.get(0);
       Vector vcTecnicos = new Vector();


       Vector vcData1 = findByCustom("",
              "SELECT E.CNOMEMBARCACION,TI.CDSCINSPECCION, V.CMATRICULA,TC.CDSCCERTIFICADO,TS.CDSCTIPOSERV, TN.CDSCTIPONAVEGA, I.DTULTDIQUESECO " +
              "FROM INSPROGINSP PI "+
              "left JOIN VEHEMBARCACION E ON E.ICVEVEHICULO = PI.ICVEEMBARCACION " +
              "left JOIN INSTIPOINSP TI ON TI.ICVETIPOINSP = PI.ICVETIPOINSP " +
              "left JOIN VEHVEHICULO V ON V.ICVEVEHICULO = PI.ICVEEMBARCACION " +
              "left JOIN INSTIPOCERTIF TC ON TC.ICVEGRUPOCERTIF = PI.ICVEGRUPOCERTIF AND TC.ITIPOCERTIFICADO = PI.ITIPOCERTIFICADO " +
              "left JOIN VEHTIPOSERVICIO TS ON TS.ICVETIPOSERV = E.ICVETIPOSERV " +
              "left JOIN VEHTIPONAVEGACION TN ON E.ICVETIPONAVEGA = TN.ICVETIPONAVEGA " +
              "left JOIN INSINSPECCION I ON I.ICVEINSPPROG = PI.ICVEINSPPROG "+
              "WHERE I.ICVEINSPECCION = "+cParametros);
         TVDinRep vDatos = (TVDinRep) vcData1.get(0);

         rep.comDespliegaCombinado("DEFICIENCIA","B",22,"H",22,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         rep.comDespliegaCombinado("MEDIDA","I",22,"O",22,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         rep.comDespliegaCombinado("FECHA DE CUMPLIMIENTO","P",22,"R",22,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         rep.comDespliegaCombinado("VERIFICO","S",22,"W",22,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
         int i = 0;
         if(vcData.size()>0)
         for (i=0;i<vcData.size();i++){
           TVDinRep vDetalle = (TVDinRep) vcData.get(i);
           rep.comDespliegaCombinado(vDetalle.getString("CDSCCODDEFICIENCIAESP"),"b",23+i,"H",23+i,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
           rep.comDespliegaCombinado(vDetalle.getString("cDscMedidaEsp"),"i",23+i,"o",23+i,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
           rep.comDespliegaCombinado("'"+vDetalle.getDate("dtCumplimiento"),"p",23+i,"r",23+i,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
           rep.comDespliegaCombinado(vDetalle.getString("CINSPECTOR"),"S",23+i,"W",23+i,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
         }

    Vector vcData2 = findByCustom("",
         "SELECT IP.INUMCERTIFICADO, TC.CDSCCERTIFICADO, TC.CABREVIATURACERT, TI.CDSCINSPECCION " +
         "FROM INSCERTXINSPECCION IP " +
         "join INSINSPECCION I ON I.ICVEINSPPROG = IP.ICVEINSPPROG " +
         "JOIN INSTIPOCERTIF TC ON IP.ITIPOCERTIFICADO = TC.ITIPOCERTIFICADO AND IP.ICVEGRUPOCERTIF = TC.ICVEGRUPOCERTIF " +
         "JOIN INSTIPOINSP TI ON IP.ICVETIPOINSP = TI.ICVETIPOINSP " +
         "WHERE I.ICVEINSPPROG = "+cParametros);

         int j = 0;
         if(vcData2.size()>0)
         for (j=0;j<vcData2.size();j++){
           TVDinRep vCertif = (TVDinRep) vcData2.get(j);
           rep.comDespliegaCombinado(""+vCertif.getString("CDSCCERTIFICADO"),"b",28+i+j,"K",28+i+j,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
           rep.comDespliegaCombinado(""+vCertif.getString("CABREVIATURACERT"),"L",28+i+j,"O",28+i+j,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
           rep.comDespliegaCombinado(""+vCertif.getString("CDSCINSPECCION"),"p",28+i+j,"U",28+i+j,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
          // rep.comDespliegaCombinado("val","a",1,"z",1,"alineacionH","alineacionH",true,2,true,false,1,1);
        }

         rep.comDespliegaAlineado("D",25+i,"SE HA(N) EXPEDIDO/REFRENDADO EL(LOS) CERTIFICADO(S) CON LAS CONDICIONES INDICADAS ",false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
         rep.comDespliegaCombinado("","B",25+i,"B",25+i,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
         rep.comDespliegaAlineado("D",26+i,"NO SE HA(N) EXPEDIDO/REFRENDADO EL(LOS) CERTIFICADO(S) CON LAS CONDICIONES INDICADAS",false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
         rep.comDespliegaCombinado("","B",26+i,"B",26+i,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
                    if(!vDatos.getString("").equals("null")) rep.comDespliegaAlineado("V",2,vDatos.getString(""),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR()); else rep.comDespliegaAlineado("V",2,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    //if(!vDatos.getString("CDSCCERTIFICADO").equals("null")) rep.comDespliegaAlineado("A",5,vDatos.getString("CDSCCERTIFICADO"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("A",5,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("G",11,vDatos.getString("CNOMEMBARCACION"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("G",11,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("CDSCINSPECCION").equals("null")) rep.comDespliegaAlineado("G",12,vDatos.getString("CDSCINSPECCION"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("G",12,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("CDSCCERTIFICADO").equals("null")) rep.comDespliegaAlineado("G",13,vDatos.getString("CDSCCERTIFICADO"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("G",13,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("").equals("null")) rep.comDespliegaAlineado("G",14,vDatos.getString(""),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("G",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("DTULTDIQUESECO").equals("null")) rep.comDespliegaAlineado("G",15,vDatos.getString("DTULTDIQUESECO"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("G",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("").equals("null")) rep.comDespliegaAlineado("R",11,""+vDatos.getDate(""),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("R",11,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("CMATRICULA").equals("null")) rep.comDespliegaAlineado("R",12,vDatos.getString("CMATRICULA"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("R",12,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("CDSCTIPOSERV").equals("null")) rep.comDespliegaAlineado("R",13,vDatos.getString("CDSCTIPOSERV"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("R",13,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("CDSCTIPONAVEGA").equals("null")) rep.comDespliegaAlineado("R",14,""+vDatos.getString("CDSCTIPONAVEGA"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("R",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    if(!vDatos.getString("").equals("null")) rep.comDespliegaAlineado("R",15,""+vDatos.getDate(""),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("R",15,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
                    sbRetorno = rep.getSbDatos();

                  }catch(Exception e){
                e.printStackTrace();
                cMensaje += e.getMessage();
              }
            if(!cMensaje.equals(""))
              throw new Exception(cMensaje);
            return sbRetorno;
    }

    public StringBuffer fReportGenCertificadoExcel(String iConsecutivoCertExp) throws Exception{
        cError = "";
        TFechas dFechas = new TFechas();
        StringBuffer sbRetorno = new StringBuffer("");
          try{
              rep.iniciaReporte();
              String[] aParam = iConsecutivoCertExp.split(",");
              Vector vcData = findByCustom("",
                                           "SELECT " +
                                           "       V.ICVEVEHICULO, " +
                                           "       VE.CNOMEMBARCACION, " +
                                           "       V.CMATRICULA, " +
                                           "       TE.CDSCTIPOEMBARCACION, " +
                                           "       VE.CNUMOMI, " +
                                           "       (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC ) AS CSENIALDIST, " +
                                           "       VE.DTCONSTRUCCION, " +
                                           "       VE.CLUGARCONSTRUCCION, " +
                                           "       VE.DESLORA, " +
                                           "       VE.DMANGA, " +
                                           "       VE.DPUNTAL, " +
                                           "       VE.DARQUEOBRUTO, " +
                                           "       VE.DARQUEONETO, " +
                                           "       PU.CDSCOFICINA, " +
                                           "       CE.ICONSECUTIVOCERTIFEXP, " +
                                           "       CE.DTEXPEDICIONCERT, " +
                                           "       CE.DTINIVIGENCIA, " +
                                           "       CE.DTFINVIGENCIA, " +
                                           "       TC.CDSCCERTIFICADO, " +
                                           "       UME.CABREVIATURA AS UESLORA, " +
                                           "       UMM.CABREVIATURA AS UMANGA, " +
                                           "       UMP.CABREVIATURA AS UPUNTAL, " +
                                           "       UMAB.CABREVIATURA AS UARQUEOBRUTO, " +
                                           "       UMAN.CABREVIATURA AS UARQUEONETO, " +
                                           "       PU.CDSCOFICINA as cPuertoMatricula, " +
                                           "       PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CPROPIETARIO, " +
                                           "       PER.CRFC AS CPROPRFC, " +
                                           "       DOM.CCALLE||' #'|| DOM.CNUMEXTERIOR AS CPROPDOM, " +
                                           "       'NUM. INT. '|| DOM.CNUMINTERIOR AS CPROPINTERIOR, " +
                                           "       'COL. '|| DOM.CCOLONIA AS CPROPCOLONIA, " +
                                           "       'C.P. '|| DOM.CCODPOSTAL AS CPROPCP, " +
                                           "       PAIS.CNOMBRE AS CPROPPAIS, " +
                                           "       EFED.CNOMBRE AS CPROPENTFED, " +
                                           "       MUN.CNOMBRE AS CPROPMUN, " +
                                           "       USU.CNOMBRE||' '|| USU.CAPPATERNO||' '|| USU.CAPMATERNO AS CINSPECTOR, " +
                                           "       PTOI.CDSCPUERTO AS PTOINSPECCION, " +
                                           "       TN.CDSCTIPONAVEGA, " +
                                           "       ts.ICVETIPOSERV, " +
                                           "       ts.CDSCTIPOSERV, " +
                                           "       tn.ICVETIPONAVEGA, " +
                                           "       tn.CDSCTIPONAVEGA, " +
                                           "       I.INUMTRIPINSPEC, " +
                                           "       MC.CDSCMATERIAL, " +
                                           "       I.ICVEINSPECCION, " +
                                           "       v.IMODELO, " +
                                           "       USU.CNOMBRE||' '|| USU.CAPPATERNO||' '|| USU.CAPMATERNO AS cAutoriza, " +
                                           "       inspecto.CCVEINSPECTOR, " +
                                           "       VE.DPESOMUERTO, " +
                                           "       pai.CNOMBRE as cpainsp, "+
                                           "       efi.CNOMBRE as cefinsp  "+
                                           "FROM VEHEMBARCACION VE " +
                                           "  JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                           "    AND CE.ICONSECUTIVOCERTEXP = " + aParam[0] +
                                           "  JOIN TRAREGSOLICITUD RS ON CE.INUMSOLICITUD = RS.INUMSOLICITUD AND CE.IEJERCICIO = RS.IEJERCICIO " +
                                           "  JOIN INSCERTXINSPECCION CI ON RS.IEJERCICIO = CI.IEJERCICIO AND RS.INUMSOLICITUD = CI.INUMSOLICITUD AND ci.ICONSECUTIVOCERTEXP=ce.ICONSECUTIVOCERTEXP " +
                                           "  JOIN INSPROGINSP PI ON CI.ICVEINSPPROG = PI.ICVEINSPPROG " +
                                           "  LEFT JOIN INSINSPECCION I ON I.ICVEINSPPROG = PI.ICVEINSPPROG " +
                                           "  JOIN GRLPUERTO PTOI ON PI.ICVEPUERTO = PTOI.ICVEPUERTO " +
                                           "  left join GRLPAIS pai on pai.ICVEPAIS=ptoi.ICVEPAIS " +
                                           "  left join GRLENTIDADFED efi on efi.ICVEPAIS=ptoi.ICVEPAIS and efi.ICVEENTIDADFED=ptoi.ICVEENTIDADFED " +
                                           "  LEFT JOIN INSINSPECTOR PINS ON PI.ICVEINSPECTOR = PINS.ICVEINSPECTOR AND PI.ICVEGRUPOCERTIF = PINS.ICVEGRUPOCERTIF " +
                                           "  LEFT JOIN SEGUSUARIO USU ON PINS.ICVEUSUARIO = USU.ICVEUSUARIO " +
                                           "  JOIN INSTIPOCERTIF TC ON CE.ITIPOCERTIFICADO = TC.ITIPOCERTIFICADO " +
                                           "    AND CE.ICVEGRUPOCERTIF = TC.ICVEGRUPOCERTIF " +
                                           "  JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                           "  LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO  and ma.DTBAJA is null " +
                                           "  LEFT JOIN GRLOFICINA PU ON MA.ICVEPUERTOMAT = PU.ICVEOFICINA " +
                                           "  LEFT JOIN MYRREGSENALDIST RSD ON RSD.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                           "    AND RSD.DTBAJASENAL IS NULL " +
                                           "  LEFT JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = RSD.ICONSECUTIVSENAL " +
                                           "  LEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                           "  LEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                           "  LEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                           "  LEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                           "  LEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                           "  LEFT JOIN VEHTIPOEMBARCACION TE ON VE.ICVETIPOEMBARCACION = TE.ICVETIPOEMBARCACION " +
                                           "  LEFT JOIN VEHTIPONAVEGACION TN ON TN.ICVETIPONAVEGA = VE.ICVETIPONAVEGA " +
                                           "  LEFT JOIN GRLPERSONA PER ON VE.ICVEPROPIETARIO = PER.ICVEPERSONA " +
                                           "  LEFT JOIN GRLDOMICILIO DOM ON PER.ICVEPERSONA = DOM.ICVEPERSONA " +
                                           "    AND LPREDETERMINADO = 1 " +
                                           "  LEFT JOIN GRLPAIS PAIS ON DOM.ICVEPAIS = PAIS.ICVEPAIS " +
                                           "  LEFT JOIN GRLENTIDADFED EFED ON DOM.ICVEENTIDADFED = EFED.ICVEENTIDADFED " +
                                           "    AND DOM.ICVEPAIS = EFED.ICVEPAIS " +
                                           "  LEFT JOIN GRLMUNICIPIO MUN ON DOM.ICVEPAIS = MUN.ICVEPAIS " +
                                           "    AND DOM.ICVEENTIDADFED = MUN.ICVEENTIDADFED " +
                                           "    AND DOM.ICVEMUNICIPIO = MUN.ICVEMUNICIPIO " +
                                           "  LEFT JOIN MYRMATRICULA MAT ON VE.ICVEVEHICULO = MAT.ICVEEMBARCACION  and mat.DTBAJA is null" +
                                           "  LEFT JOIN VEHTIPOSERVICIO ts ON ts.ICVETIPOSERV = ve.ICVETIPOSERV " +
                                           "  LEFT JOIN VEHMATERIALCASCO MC ON MC.ICVEMATERIAL=VE.ICVEMATERIAL " +
                                           "  LEFT JOIN SEGUSUARIO USU1 ON ci.ICVEUSUAUTORIZA = USU1.ICVEUSUARIO " +
                                           "  left join INSINSPECTOR inspecto on inspecto.ICVEINSPECTOR=pi.ICVEINSPECTOR " +
                                           "  WHERE VE.ICVEVEHICULO = "+aParam[1]);

                if(vcData.size()>0){
                  TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
                  // vDatos.getString("PTOINSPECCION").substring(0,3)+formatoNum0000(vDatos.getInt("ICONSECUTIVOCERTIFEXP"))+"/"+tFecha.getStringYear(vDatos.getDate("DTEXPEDICIONCERT")).toLowerCase()
                  if(vCertifIA.getInt("ICONSECUTIVOCERTIFEXP") > 0 &&
                     (!vCertifIA.getString("DTEXPEDICIONCERT").equals("null"))){
                    rep.comDespliegaAlineado("B",510,
                                             vCertifIA.getString("CCVEINSPECTOR") + "-" +
                                             formatoNum0000(
                                             vCertifIA.getInt("ICONSECUTIVOCERTIFEXP")),false,
                                             rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("B",499, vCertifIA.getString("cpainsp"), false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("C",499, vCertifIA.getString("cefinsp"), false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  }
                    rep.comDespliegaAlineado("C",510,
                            vCertifIA.getString("CCVEINSPECTOR"),false,
                            rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("D",510,formatoNum0000(vCertifIA.getInt("ICONSECUTIVOCERTIFEXP")),false,
                            rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    
                  if(vCertifIA.getInt("ICVEVEHICULO") > 0) 
                	  rep.comDespliegaAlineado("B",
                      512,vCertifIA.getString("ICVEVEHICULO"),false,rep.getAT_HCENTRO(),
                      rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",512,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CNOMEMBARCACION").equals("null")) rep.
                      comDespliegaAlineado("B",511,vCertifIA.getString("CNOMEMBARCACION"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",511,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CMATRICULA").equals("null")) rep.
                      comDespliegaAlineado("B",513,"'"+vCertifIA.getString("CMATRICULA"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",513,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CDSCTIPOEMBARCACION").equals("null")) rep.
                      comDespliegaAlineado("B",514,
                                           vCertifIA.getString("CDSCTIPOEMBARCACION"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",514,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("cSenialDist").equals("null")) rep.
                      comDespliegaAlineado("B",515,vCertifIA.getString("cSenialDist"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",515,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CNUMOMI").equals("null")) rep.
                      comDespliegaAlineado("B",516,vCertifIA.getString("CNUMOMI"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",516,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DARQUEOBRUTO").equals("null")) rep.
                      comDespliegaAlineado("B",518,vCertifIA.getString("DARQUEOBRUTO"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",518,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DARQUEONETO").equals("null")) rep.
                      comDespliegaAlineado("B",519,vCertifIA.getString("DARQUEONETO"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",519,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DESLORA").equals("null")) rep.
                      comDespliegaAlineado("B",520,
                                           vCertifIA.getString("DESLORA") + " " +
                                           vCertifIA.getString("UESLORA"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",520,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DMANGA").equals("null")) rep.
                      comDespliegaAlineado("B",521,
                                           vCertifIA.getString("DMANGA") + " " +
                                           vCertifIA.getString("UMANGA"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",521,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DPUNTAL").equals("null")) rep.
                      comDespliegaAlineado("B",522,
                                           vCertifIA.getString("DPUNTAL") + " " +
                                           vCertifIA.getString("UPUNTAL"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",522,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DTCONSTRUCCION").equals("null")){
                    rep.comDespliegaAlineado("B",523,
                                             dFechas.getDateSPN(vCertifIA.getDate("DTCONSTRUCCION")).
                                             toLowerCase(),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("C",523,
                                             "" + vCertifIA.getDate("DTCONSTRUCCION"),false,
                                             rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("D",523,
                                             dFechas.getStringDay(vCertifIA.
                        getDate("DTCONSTRUCCION")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("E",523,
                                             dFechas.getMonthName(vCertifIA.
                        getDate("DTCONSTRUCCION")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("F",523,
                                             dFechas.getStringYear(vCertifIA.
                        getDate("DTCONSTRUCCION")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                  } else rep.comDespliegaAlineado("B",523,"",false,rep.getAT_HCENTRO(),
                                                  rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CLUGARCONSTRUCCION").equals("null")) rep.
                      comDespliegaAlineado("B",524,vCertifIA.getString("CLUGARCONSTRUCCION"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",524,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());

                  if(!vCertifIA.getString("CDSCTIPONAVEGA").equals("null")) rep.
                      comDespliegaAlineado("B",525,vCertifIA.getString("CDSCTIPONAVEGA"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",525,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());


                  if(!vCertifIA.getString("CDSCCERTIFICADO").equals("null")) rep.
                      comDespliegaAlineado("B",531,
                                           "" +
                                           dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICIONCERT")),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",531,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("ICONSECUTIVOCERTIFEXP").equals("null")) rep.
                      comDespliegaAlineado("B",532,
                                           formatoNum0000(vCertifIA.getInt("ICONSECUTIVOCERTIFEXP")),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",532,"B",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DTINIVIGENCIA").equals("null")){
                    rep.comDespliegaAlineado("B",535,
                                             dFechas.getDateSPN(vCertifIA.getDate("DTINIVIGENCIA")),false,
                                             rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("C",535,
                                             "" + vCertifIA.getDate("DTINIVIGENCIA"),false,
                                             rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("D",535,
                                             dFechas.getStringDay(vCertifIA.
                        getDate("DTINIVIGENCIA")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("E",535,
                                             dFechas.getMonthName(vCertifIA.
                        getDate("DTINIVIGENCIA")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("F",535,
                                             dFechas.getStringYear(vCertifIA.
                        getDate("DTINIVIGENCIA")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                  } else rep.comDespliegaAlineado("B",535,"",false,rep.getAT_HCENTRO(),
                                                  rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")){
                    rep.comDespliegaAlineado("B",534,
                                             dFechas.getDateSPN(vCertifIA.getDate("DTFINVIGENCIA")),false,
                                             rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("C",534,
                                             "" + vCertifIA.getDate("DTFINVIGENCIA"),false,
                                             rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("D",534,
                                             dFechas.getStringDay(vCertifIA.
                        getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("E",534,
                                             dFechas.getMonthName(vCertifIA.
                        getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("F",534,
                                             dFechas.getStringYear(vCertifIA.
                        getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());

                  } else rep.comDespliegaAlineado("B",534,"",false,rep.getAT_HCENTRO(),
                                                  rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null")){
                    rep.comDespliegaAlineado("B",533,
                                             dFechas.getDateSPN(vCertifIA.getDate("DTEXPEDICIONCERT")),false,
                                             rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("C",533,
                                             "" + vCertifIA.getDate("DTEXPEDICIONCERT"),false,
                                             rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("D",533,
                                             dFechas.getStringDay(vCertifIA.
                        getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("E",533,
                                             dFechas.getMonthName(vCertifIA.
                        getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("F",533,
                                             dFechas.getStringYear(vCertifIA.
                        getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),
                                             rep.getAT_VCENTRO());

                  } else rep.comDespliegaAlineado("B",533,"",false,rep.getAT_HCENTRO(),
                                                  rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("PTOINSPECCION").equals("null")) rep.
                      comDespliegaAlineado("B",536,vCertifIA.getString("PTOINSPECCION"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",536,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("cAutoriza").equals("null")) rep.
                      comDespliegaAlineado("B",537,vCertifIA.getString("cAutoriza"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",537,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());

                  if(!vCertifIA.getString("CPROPIETARIO").equals("null")) rep.
                      comDespliegaAlineado("B",551,vCertifIA.getString("CPROPIETARIO"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",551,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CPROPRFC").equals("null")) rep.
                      comDespliegaAlineado("B",552,vCertifIA.getString("CPROPRFC"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",552,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CPROPDOM").equals("null")) rep.
                      comDespliegaAlineado("B",553,vCertifIA.getString("CPROPDOM"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",553,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CPROPINTERIOR").equals("null")) rep.
                      comDespliegaAlineado("B",554,vCertifIA.getString("CPROPINTERIOR"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",554,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CPROPCOLONIA").equals("null")) rep.
                      comDespliegaAlineado("B",555,vCertifIA.getString("CPROPCOLONIA"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",555,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CPROPENTFED").equals("null")) rep.
                      comDespliegaAlineado("B",556,vCertifIA.getString("CPROPENTFED"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",556,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CPROPMUN").equals("null")) rep.
                      comDespliegaAlineado("B",557,vCertifIA.getString("CPROPMUN"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",557,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CPROPPAIS").equals("null")) rep.
                      comDespliegaAlineado("B",558,vCertifIA.getString("CPROPPAIS"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",558,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CPROPCP").equals("null")) rep.
                      comDespliegaAlineado("B",559,vCertifIA.getString("CPROPCP"),false,
                                           rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",559,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("cPuertoMatricula").equals("null"))
                	  {
                	      String cPtoMat = vCertifIA.getString("cPuertoMatricula").replaceAll("CAPITANIA DE ", "");
                	      cPtoMat = cPtoMat.replaceAll("CENTRO SCT ", "");
                	      rep.comDespliegaAlineado("B",560,"'"+cPtoMat,false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                	  }
                  else rep.comDespliegaAlineado("B",560,"",false,rep.getAT_HCENTRO(),
                                                rep.getAT_VCENTRO());

                  if(!vCertifIA.getString("ICVETIPOSERV").equals("null"))
                    rep.comDespliegaAlineado("b",571,vCertifIA.getString("ICVETIPOSERV"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("b",572,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CDSCTIPOSERV").equals("null"))
                    rep.comDespliegaAlineado("c",571,vCertifIA.getString("CDSCTIPOSERV"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("c",571,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("ICVETIPONAVEGA").equals("null"))
                    rep.comDespliegaAlineado("b",570,vCertifIA.getString("ICVETIPONAVEGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("b",570,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CDSCTIPONAVEGA").equals("null"))
                    rep.comDespliegaAlineado("c",570,vCertifIA.getString("CDSCTIPONAVEGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("c",570,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("INUMTRIPINSPEC").equals("null"))
                    rep.comDespliegaAlineado("B",575,vCertifIA.getString("INUMTRIPINSPEC"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",575,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("CDSCMATERIAL").equals("null"))
                    rep.comDespliegaAlineado("B",576,vCertifIA.getString("CDSCMATERIAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",576,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("IMODELO").equals("null"))
                    rep.comDespliegaAlineado("B",577,vCertifIA.getString("IMODELO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",577,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  rep.comDespliegaAlineado("AA",1,"1",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  if(!vCertifIA.getString("DPESOMUERTO").equals("null"))
                      rep.comDespliegaAlineado("B",578,vCertifIA.getString("DPESOMUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  else rep.comDespliegaAlineado("B",578,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  
                  String cCaracteristicas = "SELECT cc.CVARIABLE,ci.CVALOR FROM INSCARACTXINSP ci " +
                                            "join INSCARACTXCONC cc on ci.ICVECONCEPTOEVAL=cc.ICVECONCEPTOEVAL and ci.ICVECARACTERISITIC=cc.ICVECARACTERISITIC " +
                                            "where icveinspeccion= "+vCertifIA.getInt("ICVEINSPECCION");
                  Vector vcCaract = this.findByNessted("",cCaracteristicas,conn);
                  for(int iCar=0;iCar<vcCaract.size();iCar++){
                    TVDinRep vCaract = (TVDinRep) vcCaract.get(iCar);
                    int var = Integer.parseInt(vCaract.getString("CVARIABLE").substring(1,vCaract.getString("CVARIABLE").length()));
                    rep.comDespliegaAlineado(vCaract.getString("CVARIABLE").substring(0,1),var,vCaract.getString("CVALOR"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  }
                  
                  String cMotores = "SELECT " +
                  		"MOT.ICVEVEHICULO, " +
                  		"MOT.ICONSECUTIVO, " +
                  		"MOT.IPOTENCIA, " +
                  		"MM.CDSCBREVE, " +
                  		"MM.CDSCMARCAMOTOR, " +
                  		"(SELECT SUM(MOT2.IPOTENCIA) FROM VEHMOTOR MOT2 WHERE MOT2.ICVEVEHICULO = " + vCertifIA.getString("ICVEVEHICULO") + ") AS TOTPOT " +
                  		"FROM VEHMOTOR MOT "+
                  		"JOIN VEHMARCAMOTOR MM ON MM.ICVEMARCAMOTOR = MOT.ICVEMARCAMOTOR "+
                  		"WHERE MOT.ICVEVEHICULO = " + vCertifIA.getString("ICVEVEHICULO");

                  Vector vcMotores = this.findByNessted("", cMotores, conn);
                  int iPosDat=0;
                  int iTotPotencia=0;
                  if(vcMotores.size()>0){
                	  for (int iMot=0;iMot<vcMotores.size();iMot++){
                		  TVDinRep vMotores = (TVDinRep) vcMotores.get(iMot);
                		  iPosDat=600+iMot;
                		  rep.comDespliegaAlineado("A",iPosDat,vMotores.getString("CDSCMARCAMOTOR"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                		  rep.comDespliegaAlineado("B",iPosDat,vMotores.getString("IPOTENCIA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                		  if(iMot==(vcMotores.size()-1)){
                			  rep.comDespliegaAlineado("A",606,vcMotores.size()+"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                			  rep.comDespliegaAlineado("B",606,vMotores.getString("TOTPOT"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                		  }
                	  }
                	  
                  }

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


public StringBuffer fReportGenCertificadoWord(String cQuery){
     Vector vcData = new Vector();
     TWord rep = new TWord();
     TFechas tFecha = new TFechas();
     String[] cParametros = cQuery.split(",");
     java.sql.Date dtFechaApr=null,dtFechaVal=null;

     try{
    	 vcData = FindByGeneric("",
                      "SELECT " +
                      "       V.ICVEVEHICULO, " +
                      "       VE.CNOMEMBARCACION, " +
                      "       V.CMATRICULA, " +
                      "       TE.CDSCTIPOEMBARCACION, " +
                      "       VE.CNUMOMI, " +
                      "       (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC ) AS CSENIALDIST, " +
                      "       VE.DTCONSTRUCCION, " +
                      "       VE.CLUGARCONSTRUCCION, " +
                      "       VE.DESLORA, " +
                      "       VE.DMANGA, " +
                      "       VE.DPUNTAL, " +
                      "       VE.DARQUEOBRUTO, " +
                      "       VE.DARQUEONETO, " +
                      "       PU.CDSCOFICINA, " +
                      "       CE.ICONSECUTIVOCERTIFEXP, " +
                      "       CE.DTEXPEDICIONCERT, " +
                      "       CE.DTINIVIGENCIA, " +
                      "       CE.DTFINVIGENCIA, " +
                      "       TC.CDSCCERTIFICADO, " +
                      "       UME.CABREVIATURA AS UESLORA, " +
                      "       UMM.CABREVIATURA AS UMANGA, " +
                      "       UMP.CABREVIATURA AS UPUNTAL, " +
                      "       UMAB.CABREVIATURA AS UARQUEOBRUTO, " +
                      "       UMAN.CABREVIATURA AS UARQUEONETO, " +
                      "       PU.CDSCOFICINA, " +
                      "       PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CPROPIETARIO, " +
                      "       PER.CRFC AS CPROPRFC, " +
                      "       DOM.CCALLE||' #'|| DOM.CNUMEXTERIOR AS CPROPDOM, " +
                      "       'NUM. INT. '|| DOM.CNUMINTERIOR AS CPROPINTERIOR, " +
                      "       'COL. '|| DOM.CCOLONIA AS CPROPCOLONIA, " +
                      "       'C.P. '|| DOM.CCODPOSTAL AS CPROPCP, " +
                      "       PAIS.CNOMBRE AS CPROPPAIS, " +
                      "       EFED.CNOMBRE AS CPROPENTFED, " +
                      "       MUN.CNOMBRE AS CPROPMUN, " +
                      "       USU.CNOMBRE||' '|| USU.CAPPATERNO||' '|| USU.CAPMATERNO AS CINSPECTOR, " +
                      "       PTOI.CDSCPUERTO AS PTOINSPECCION, " +
                      "       ts.ICVETIPOSERV, " +
                      "       ts.CDSCTIPOSERV, " +
                      "       tn.ICVETIPONAVEGA, " +
                      "       tn.CDSCTIPONAVEGA " +
                      "FROM VEHEMBARCACION VE " +
                      "  JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO " +
                      "    AND CE.ICONSECUTIVOCERTEXP = " + cParametros[0] +
                      "  LEFT JOIN TRAREGSOLICITUD RS ON CE.INUMSOLICITUD = RS.INUMSOLICITUD AND CE.IEJERCICIO = RS.IEJERCICIO " +
                      "  LEFT JOIN INSCERTXINSPECCION CI ON RS.IEJERCICIO = CI.IEJERCICIO AND RS.INUMSOLICITUD = CI.INUMSOLICITUD " +
                      "  LEFT JOIN INSPROGINSP PI ON CI.ICVEINSPPROG = PI.ICVEINSPPROG " +
                      "  LEFT JOIN GRLPUERTO PTOI ON PI.ICVEPUERTO = PTOI.ICVEPUERTO " +
                      "  LEFT JOIN INSINSPECTOR PINS ON PI.ICVEINSPECTOR = PINS.ICVEINSPECTOR AND PI.ICVEGRUPOCERTIF = PINS.ICVEGRUPOCERTIF " +
                      "  LEFT JOIN SEGUSUARIO USU ON PINS.ICVEUSUARIO = USU.ICVEUSUARIO " +
                      "  LEFT JOIN INSTIPOCERTIF TC ON CE.ITIPOCERTIFICADO = TC.ITIPOCERTIFICADO " +
                      "    AND CE.ICVEGRUPOCERTIF = TC.ICVEGRUPOCERTIF " +
                      "  LEFT JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                      "  LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                      "  LEFT JOIN GRLOFICINA PU ON MA.ICVEPUERTOMAT = PU.ICVEOFICINA " +
                      "  LEFT JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = VE.ICVEVEHICULO " +
                      "  LEFT JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                      "  LEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                      "  LEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                      "  LEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                      "  LEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                      "  LEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                      "  LEFT JOIN VEHTIPOEMBARCACION TE ON VE.ICVETIPOEMBARCACION = TE.ICVETIPOEMBARCACION " +
                      "  LEFT JOIN GRLPERSONA PER ON VE.ICVEPROPIETARIO = PER.ICVEPERSONA " +
                      "  LEFT JOIN GRLDOMICILIO DOM ON PER.ICVEPERSONA = DOM.ICVEPERSONA " +
                      "    AND LPREDETERMINADO = 1 " +
                      "  LEFT JOIN GRLPAIS PAIS ON DOM.ICVEPAIS = PAIS.ICVEPAIS " +
                      "  LEFT JOIN GRLENTIDADFED EFED ON DOM.ICVEENTIDADFED = EFED.ICVEENTIDADFED " +
                      "    AND DOM.ICVEPAIS = EFED.ICVEPAIS " +
                      "  LEFT JOIN GRLMUNICIPIO MUN ON DOM.ICVEPAIS = MUN.ICVEPAIS " +
                      "    AND DOM.ICVEENTIDADFED = MUN.ICVEENTIDADFED " +
                      "    AND DOM.ICVEMUNICIPIO = MUN.ICVEMUNICIPIO " +
                      "  LEFT JOIN MYRMATRICULA MAT ON VE.ICVEVEHICULO = MAT.ICVEEMBARCACION  and mat.DTBAJA is null" +
                      "  LEFT JOIN VEHTIPONAVEGACION tn ON tn.ICVETIPONAVEGA=ve.ICVETIPONAVEGA " +
                      "  LEFT JOIN VEHTIPOSERVICIO ts ON ts.ICVETIPOSERV = ve.ICVETIPOSERV " +
                      "  WHERE VE.ICVEVEHICULO = "+cParametros[1],dataSourceName);

     }catch(SQLException ex){
    	 cMensaje = ex.getMessage();
     }catch(Exception ex2){
    	 ex2.printStackTrace();
     }

 rep.iniciaReporte();

 if (vcData.size() > 0){
   TVDinRep vDatos = (TVDinRep) vcData.get(0);

   if(!vDatos.getString("CNOMEMBARCACION").equals(""))
 rep.comRemplaza("[NombreEmbarcacion]",vDatos.getString("CNOMEMBARCACION"));
 else rep.comRemplaza("[NombreEmbarcacion]","_______________");
   if(!vDatos.getString("CMATRICULA").equals(""))
 rep.comRemplaza("[Matricula]",vDatos.getString("CMATRICULA"));
 else rep.comRemplaza("[Matricula]","_______________");
   if(!vDatos.getString("CDSCTIPOEMBARCACION").equals(""))
 rep.comRemplaza("[TipoEmbarcacion]",vDatos.getString("CDSCTIPOEMBARCACION"));
 else rep.comRemplaza("[TipoEmbarcacion]","_______________");
   if(!vDatos.getString("CNUMOMI").equals(""))
 rep.comRemplaza("[NumeroOMI]",vDatos.getString("CNUMOMI"));
 else rep.comRemplaza("[NumeroOMI]","_______________");
   if(!vDatos.getString("cSenialDist").equals(""))
 rep.comRemplaza("[LetrasDistintivas]",vDatos.getString("cSenialDist"));
   else rep.comRemplaza("[LetrasDistintivas]","_______________");
   //System.out.print("*****    Fecha de construccion  "+vDatos.getString("DTCONSTRUCCION"));
   if(!vDatos.getString("DTCONSTRUCCION").equals("") && !vDatos.getString("dtConstrucccion").equals("null") && !vDatos.getString("dtConstrucccion").equals(null)){
 rep.comRemplaza("[FechaContruccion]",tFecha.getDateSPN(vDatos.getDate("DTCONSTRUCCION")).toLowerCase());
 rep.comRemplaza("[FechaContruccionDD/MM/AAAA]",(""+vDatos.getDate("DTCONSTRUCCION")).toLowerCase());
 rep.comRemplaza("[FechaContruccionDD]",tFecha.getStringDay(vDatos.getDate("DTCONSTRUCCION")).toLowerCase());
 rep.comRemplaza("[FechaContruccionMMM]",tFecha.getMonthAbrev(vDatos.getDate("DTCONSTRUCCION")).toLowerCase());
 rep.comRemplaza("[FechaContruccionAAAA]",tFecha.getStringYear(vDatos.getDate("DTCONSTRUCCION")).toLowerCase());
   }
   else rep.comRemplaza("[FechaContruccion]","_______________");
   if(!vDatos.getString("CLUGARCONSTRUCCION").equals(""))
 rep.comRemplaza("[LugarConstruccion]",vDatos.getString("CLUGARCONSTRUCCION"));
   else rep.comRemplaza("[LugarConstruccion]","_______________");
   if(!vDatos.getString("DESLORA").equals(""))
 rep.comRemplaza("[Eslora]",vDatos.getString("DESLORA"));
   else rep.comRemplaza("[Eslora]","_______________");
   if(!vDatos.getString("DMANGA").equals(""))
 rep.comRemplaza("[Manga]",vDatos.getString("DMANGA"));
   else rep.comRemplaza("[Manga]","_______________");
   if(!vDatos.getString("DPUNTAL").equals(""))
 rep.comRemplaza("[Puntal]",vDatos.getString("DPUNTAL"));
   else rep.comRemplaza("[Puntal]","_______________");
   if(!vDatos.getString("DARQUEOBRUTO").equals(""))
 rep.comRemplaza("[ArqueoBruto]",vDatos.getString("DARQUEOBRUTO"));
   else rep.comRemplaza("[ArqueoBruto]","_______________");
   if(!vDatos.getString("DARQUEONETO").equals(""))
 rep.comRemplaza("[ArqueoNeto]",vDatos.getString("DARQUEONETO"));
   else rep.comRemplaza("[ArqueoNeto]","_______________");
   if(!vDatos.getString("CDSCPUERTO").equals(""))
 rep.comRemplaza("[PuertoMatricula]",vDatos.getString("CDSCPUERTO"));
   else rep.comRemplaza("[PuertoMatricula]","_______________");
   if(!vDatos.getString("ICONSECUTIVOCERTIFEXP").equals(""))
 rep.comRemplaza("[FolioCertificado]",vDatos.getString("PTOINSPECCION").substring(0,3)+formatoNum0000(vDatos.getInt("ICONSECUTIVOCERTIFEXP"))+"/"+tFecha.getStringYear(vDatos.getDate("DTEXPEDICIONCERT")).toLowerCase());
   else rep.comRemplaza("[FolioCertificado]","_______________");
   if(!vDatos.getString("DTEXPEDICIONCERT").equals("")){
 rep.comRemplaza("[FechaExpedicionCertif]",tFecha.getDateSPN(vDatos.getDate("DTEXPEDICIONCERT")).toLowerCase());
 rep.comRemplaza("[FechaExpedicionCertifDD/MM/AAAA]",(""+vDatos.getDate("DTEXPEDICIONCERT")).toLowerCase());
 rep.comRemplaza("[FechaExpedicionCertifDD]",tFecha.getStringDay(vDatos.getDate("DTEXPEDICIONCERT")).toLowerCase());
 rep.comRemplaza("[FechaExpedicionCertifMMM]",tFecha.getMonthAbrev(vDatos.getDate("DTEXPEDICIONCERT")).toLowerCase());
 rep.comRemplaza("[FechaExpedicionCertifAAAA]",tFecha.getStringYear(vDatos.getDate("DTEXPEDICIONCERT")).toLowerCase());
   }else rep.comRemplaza("[FechaExpedicionCertif]","_______________");
   if(!vDatos.getString("DTINIVIGENCIA").equals("")){
 rep.comRemplaza("[FechaInicioVigencia]",tFecha.getDateSPN(vDatos.getDate("DTINIVIGENCIA")).toLowerCase());
 rep.comRemplaza("[FechaInicioVigenciaDD/MM/AAAA]",(""+vDatos.getDate("DTINIVIGENCIA")).toLowerCase());
 rep.comRemplaza("[FechaInicioVigenciaDD]",tFecha.getStringDay(vDatos.getDate("DTINIVIGENCIA")).toLowerCase());
 rep.comRemplaza("[FechaInicioVigenciaMMM]",tFecha.getMonthAbrev(vDatos.getDate("DTINIVIGENCIA")).toLowerCase());
 rep.comRemplaza("[FechaInicioVigenciaAAAA]",tFecha.getStringYear(vDatos.getDate("DTINIVIGENCIA")).toLowerCase());
   }else rep.comRemplaza("[FechaInicioVigencia]","_______________");
   if(!vDatos.getString("DTFINVIGENCIA").equals("")){
 rep.comRemplaza("[FechaFinVigencia]",tFecha.getDateSPN(vDatos.getDate("DTFINVIGENCIA")).toLowerCase());
 rep.comRemplaza("[FechaFinVigenciaDD/MM/AAAA]",(""+vDatos.getDate("DTFINVIGENCIA")).toLowerCase());
 rep.comRemplaza("[FechaFinVigenciaDD]",tFecha.getStringDay(vDatos.getDate("DTFINVIGENCIA")).toLowerCase());
 rep.comRemplaza("[FechaFinVigenciaMMM]",tFecha.getMonthAbrev(vDatos.getDate("DTFINVIGENCIA")).toLowerCase());
 rep.comRemplaza("[FechaFinVigenciaAAAA]",tFecha.getStringYear(vDatos.getDate("DTFINVIGENCIA")).toLowerCase());
   }else rep.comRemplaza("[FechaFinVigencia]","_______________");
   if(!vDatos.getString("CDSCCERTIFICADO").equals(""))
 rep.comRemplaza("[TipoCertificado]",vDatos.getString("CDSCCERTIFICADO")); //CINSPECTOR
   else rep.comRemplaza("[TipoCertificado]","_______________");
   if(!vDatos.getString("CINSPECTOR").equals(""))
 rep.comRemplaza("[NombreInspector]",vDatos.getString("CINSPECTOR"));
   else rep.comRemplaza("[NombreInspector]","_______________");
   if(!vDatos.getString("CPROPIETARIO").equals(""))
 rep.comRemplaza("[Propietario]",vDatos.getString("CPROPIETARIO"));
   else rep.comRemplaza("[Propietario]","_______________");
   if(!vDatos.getString("CPROPRFC").equals(""))
 rep.comRemplaza("[RFCPropietario]",vDatos.getString("CPROPRFC"));
   else rep.comRemplaza("[RFCPropietario]","_______________");
   if(!vDatos.getString("CPROPDOM").equals(""))
 rep.comRemplaza("[DomicilioPropietario]",vDatos.getString("CPROPDOM"));
   else rep.comRemplaza("[DomicilioPropietario]","_______________");
   if(!vDatos.getString("NumInteriorPropietario").equals(""))
 rep.comRemplaza("[NumInteriorPropietario]",vDatos.getString("CPROPINTERIOR"));
   else rep.comRemplaza("[NumInteriorPropietario]","");
   if(!vDatos.getString("CPROPCOLONIA").equals(""))
 rep.comRemplaza("[ColoniaPropietario]",vDatos.getString("CPROPCOLONIA"));
   else rep.comRemplaza("[ColoniaPropietario]","");
   if(!vDatos.getString("CPROPCP").equals(""))
 rep.comRemplaza("[CodPostalPropietario]",vDatos.getString("CPROPCP"));
   else rep.comRemplaza("[CodPostalPropietario]","");
   if(!vDatos.getString("PTOINSPECCION").equals(""))
 rep.comRemplaza("[PuertoInspeccion]",vDatos.getString("PTOINSPECCION"));
   else rep.comRemplaza("[PuertoInspeccion]","");

   if(vDatos.getInt("ICVETIPONAVEGA")>0){
 rep.comRemplaza("[ICVETIPONAVEGA]","" + vDatos.getInt("ICVETIPONAVEGA"));
 rep.comRemplaza("[CDSCTIPONAVEGA]",vDatos.getString("CDSCTIPONAVEGA"));
   }
   else{
     rep.comRemplaza("[ICVETIPONAVEGA]","_______________");
 rep.comRemplaza("[CDSCTIPONAVEGA]","_______________");
   }

   //System.out.print("*****    Antes de imprimir el tipo servicio!!!!");

   if(vDatos.getInt("ICVETIPOSERV")>0){
 rep.comRemplaza("[ICVETIPOSERV]",vDatos.getString("ICVETIPOSERV"));
 rep.comRemplaza("[CDSCTIPOSERV]",vDatos.getString("CDSCTIPOSERV"));
   }
   else{
     rep.comRemplaza("[ICVETIPOSERV]","_______________");
 rep.comRemplaza("[CDSCTIPOSERV]","_______________");
   }

   //System.out.print("*****    Despues de imprimir los valores");
 }
 else {
   rep.comRemplaza("[_______________]","");
 }
 // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
 return rep.getEtiquetas(true);

}

        public StringBuffer fCertArqueo(String iConsecutivoCertExp) throws Exception{

             cError = "";
             TFechas dFechas = new TFechas();
             StringBuffer sbRetorno = new StringBuffer("");


               try{
                 rep.iniciaReporte();
                 String[] aParam = iConsecutivoCertExp.split(",");
                 //Query para sacar los datos a reemplazar

                 Vector vcData = findByCustom("",
                                              "SELECT " +
                                              "   CE.ICONSECUTIVOCERTEXP, " +
                                              "   VE.CNOMEMBARCACION, V.CMATRICULA, VE.CNUMOMI, VE.DTCONSTRUCCION,VE.DESLORA,VE.DMANGA, VE.DPUNTAL, VE.DARQUEOBRUTO, VE.DARQUEONETO, " +
                                              "   CE.DTEXPEDICIONCERT, CE.DTINIVIGENCIA,CE.DTFINVIGENCIA, (MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC  ) As cSenialDist, " +
                                              "   UME.CABREVIATURA AS UESLORA,UMM.CABREVIATURA AS UMANGA,UMP.CABREVIATURA AS UPUNTAL, " +
                                              "   UMAB.CABREVIATURA AS UARQUEOBRUTO,UMAN.CABREVIATURA AS UARQUEONETO, PU.CDSCPUERTO,VE.CNUMOMI,CE.DTFINVIGENCIA, PUINS.CDSCPUERTO AS PUERTOINSPECCION " +
                                              " FROM VEHEMBARCACION VE " +
                                              " JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO AND CE.ICONSECUTIVOCERTEXP = " + aParam[0] +
                                              " JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                              " LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                              " LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO " +
                                              " Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                              " Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                              " lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                              " lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                              " lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                              " lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                              " lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                              " LEFT JOIN INSCERTXINSPECCION CI ON CE.INUMSOLICITUD = CI.INUMSOLICITUD AND CE.IEJERCICIO = CI.IEJERCICIO " +
                                              " LEFT JOIN INSPROGINSP PI ON CI.ICVEINSPPROG = PI.ICVEINSPPROG " +
                                              " LEFT JOIN GRLPUERTO PUINS ON PI.ICVEPUERTO = PUINS.ICVEPUERTO " +
                                              " where VE.iCveVehiculo = " + aParam[1]);

                 Vector vcLugar = findByCustom("",
                     "SELECT MU.CNOMBRE AS MUNICIPIO, EF.CNOMBRE AS ENTIDAD FROM GRLOFICINA OFI " +
                                               "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS=1 " +
                                               "join GRLMUNICIPIO MU ON MU.ICVEMUNICIPIO = OFI.ICVEMUNICIPIO AND MU.ICVEENTIDADFED = OFI.ICVEENTIDADFED AND OFI.ICVEPAIS = 1 " +
                                               "WHERE ICVEOFICINA = " + aParam[3]);
                 if(vcLugar.size()>0){
                   TVDinRep vCertiLugar = (TVDinRep) vcLugar.get(0);
                   if(!vCertiLugar.getString("MUNICIPIO").equals("null"))         rep.comDespliegaAlineado("B",512,vCertiLugar.getString("MUNICIPIO")+", "+vCertiLugar.getString("ENTIDAD")+".",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                 }
                 if(vcData.size()>0){
                   TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
                   if(!vCertifIA.getString("ICONSECUTIVOCERTEXP").equals("null"))  rep.comDespliegaAlineado("B",501,vCertifIA.getString("ICONSECUTIVOCERTEXP"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("CNOMEMBARCACION").equals("null"))      rep.comDespliegaAlineado("B",502,vCertifIA.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("CDSCPUERTO").equals("null"))           rep.comDespliegaAlineado("B",503,vCertifIA.getString("CDSCPUERTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("CMATRICULA").equals("null"))           rep.comDespliegaAlineado("B",504,vCertifIA.getString("CMATRICULA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("cSenialDist").equals("null"))          rep.comDespliegaAlineado("B",505,vCertifIA.getString("cSenialDist"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("DTCONSTRUCCION").equals("null"))       rep.comDespliegaAlineado("B",506,""+vCertifIA.getDate("DTCONSTRUCCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("DESLORA").equals("null"))              rep.comDespliegaAlineado("B",507,vCertifIA.getString("DESLORA")+" "+vCertifIA.getString("UESLORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("DMANGA").equals("null"))               rep.comDespliegaAlineado("B",508,vCertifIA.getString("DMANGA")+" "+vCertifIA.getString("UMANGA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("DPUNTAL").equals("null"))              rep.comDespliegaAlineado("B",509,vCertifIA.getString("DPUNTAL")+" "+vCertifIA.getString("UPUNTAL"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("DARQUEOBRUTO").equals("null"))         rep.comDespliegaAlineado("B",510,vCertifIA.getString("DARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("DARQUEONETO").equals("null"))          rep.comDespliegaAlineado("B",511,vCertifIA.getString("DARQUEONETO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("DTEXPEDICIONCERT").equals("null"))     rep.comDespliegaAlineado("B",512,dFechas.getDateSPN(vCertifIA.getDate("DTEXPEDICIONCERT")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("CNUMOMI").equals("null"))              rep.comDespliegaAlineado("B",514,vCertifIA.getString("CNUMOMI"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("DTFINVIGENCIA").equals("null"))        rep.comDespliegaAlineado("B",515,dFechas.getDateSPN(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                   if(!vCertifIA.getString("PUERTOINSPECCION").equals("null"))     rep.comDespliegaAlineado("B",516,vCertifIA.getString("PUERTOINSPECCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
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




        public String formatoNum0000(int num){
          String Resp = "";
          if(num >= 0 && num < 10) Resp = "000" + num;
          if(num >= 10 && num < 100) Resp = "00" + num;
          if(num >= 100 && num < 1000) Resp = "0" + num;
          if(num >= 1000 && num < 10000) Resp = "" + num;
          return Resp;
        }
        public String formatoNum000(int num){
          String Resp = "";
          if(num >= 0 && num < 10) Resp = "00" + num;
          if(num >= 10 && num < 100) Resp = "0" + num;
          if(num >= 100 && num < 1000) Resp = "" + num;
          return Resp;
        }


  public StringBuffer fCertAprovMar(String iConsecutivoCertExp) throws Exception{
	  cError = "";
	  TFechas dFechas = new TFechas();
	  StringBuffer sbRetorno = new StringBuffer("");
	  try{
		  rep.iniciaReporte();
		  String[] aParam = iConsecutivoCertExp.split(",");
		  Vector vcData = findByCustom("",
				  "SELECT es.DTREGISTRO, ES.DTAUTORIZACION, ES.CNOMESTACION, ES.ICVEESTACION, TC.IFOLIO, " +
				  "PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CSOLISITANTE, PER.CRFC, " +
				  "DOM.CCALLE||' '|| CNUMEXTERIOR AS CDOM, DOM.CCOLONIA, PA.CNOMBRE AS CPAIS, EF.CNOMBRE AS CENTFED, MU.CNOMBRE AS CMUN, DOM.CCODPOSTAL, " +
				  "TC.CDSCCERTIFICADO, TE.CDSCTIPOESTACION, TE.CABREVIATURA,TE.CNORMA, CE.DTEXPEDICION, CE.DTFINVIGENCIA, CE.DTCANCELACION, " +
				  "ES.CNUMESTACION,TE.ICVETIPOESTACION,TE.DTPUBLICNORMA,CE.IEJERCICIO,CE.INUMSOLICITUD "+
				  "FROM INSESTACIONSERV ES " +				  
				  "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = ES.ICVEEMPRESA " +
				  "JOIN GRLDOMICILIO DOM ON DOM.ICVEPERSONA = ES.ICVEEMPRESA AND DOM.ICVEDOMICILIO = ES.ICVEDOMICILIO " +
				  "JOIN GRLPAIS PA ON PA.ICVEPAIS = DOM.ICVEPAIS " +
				  "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = DOM.ICVEPAIS AND EF.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
				  "JOIN GRLMUNICIPIO MU ON MU.ICVEPAIS= DOM.ICVEPAIS AND MU.ICVEENTIDADFED= DOM.ICVEENTIDADFED AND MU.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO " +
				  "JOIN INSCERTIFXESTACION CE ON CE.ICVEESTACION = ES.ICVEESTACION " +
				  "JOIN INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO = CE.ITIPOCERTIFICADO AND TC.ICVEGRUPOCERTIF = CE.ICVEGRUPOCERTIF " +
				  "JOIN INSTIPOESTACION TE ON TE.ICVETIPOESTACION = ES.ICVETIPOESTACION  " +
				  " WHERE CE.ICVEESTACION = "+ aParam[0] +" AND CE.ICONSECUTIVOTRAMITE = " + aParam[1] +
				  " ORDER BY CE.DTEXPEDICION DESC");
		  TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
		  Vector vcTecnicos = new Vector();
		  Vector vcDirector = findByCustom("","SELECT CTITULAR FROM GRLDEPTOXOFIC where ICVEDEPARTAMENTO = 50 ");
		  TVDinRep vDirector = (TVDinRep) vcDirector.get(0);
		  if(!vDirector.getString("CTITULAR").equals("null")) rep.comDespliegaAlineado("s",50,vDirector.getString("CTITULAR"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("s",50,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
		  int iNumEstacion = 0;
		  if(vCertifIA.getString("cNumEstacion")!=null && !vCertifIA.getString("cNumEstacion").equals("null"))
			  iNumEstacion = Integer.parseInt(vCertifIA.getString("cNumEstacion"));
		  if (iNumEstacion>0) {
			  vcTecnicos = findByCustom("",
					  "SELECT ICVETECNICO, CNOMTECNICO||' '|| CAPPATTECNICO||' '|| CAPMATTECNICO as cNombre, LRESPESTACION,cNumTecnico " +
					  "FROM INSTECNICO where ICVEESTACION = " + vCertifIA.getInt("ICVEESTACION")+" Order by LRESPESTACION desc,cNumTecnico asc");
			  rep.comDespliegaCombinado("Nº DE REGISTRO","c",80,"G",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
			  rep.comDespliegaCombinado("NOMBRE DEL TECNICO","H",80,"p",80,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
			  int iReng=1;
			  if(true){
				  for(int i = 0;i < vcTecnicos.size();i++){
					  TVDinRep vTecnicos = (TVDinRep) vcTecnicos.get(i);
					  String cInst = "",cTec = "";
					  int iNumTecnico = 0;
					  if(!vTecnicos.getString("cNumTecnico").equals("null") && vTecnicos.getString("cNumTecnico")!=null)
						  iNumTecnico = Integer.parseInt(vTecnicos.getString("cNumTecnico"));
					  if(iNumEstacion >= 0 && iNumEstacion < 10)
						  cInst = "00" + iNumEstacion;
					  if(iNumEstacion > 9 && iNumEstacion < 100)
						  cInst = "0" + iNumEstacion;
					  if(iNumEstacion > 99) cInst = iNumEstacion + "";
					  if(iNumTecnico > 0 && iNumTecnico < 10)
						  cTec = "00" + iNumTecnico;
					  if(iNumTecnico > 9 && iNumTecnico < 100)
						  cTec = "0" + iNumTecnico;
					  rep.comFontSize("a",80,"az",300,7);
					  if( (iNumTecnico == 1) && vTecnicos.getInt("LRESPESTACION") == 1)
						  rep.comDespliegaCombinado("DGMM-" + vCertifIA.getString("CABREVIATURA") + "-" + cInst,"c",81 + iReng,"G", 81 + iReng, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VCENTRO(),true,2,true,false,1,1);
					  else rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"c",81 + iReng,"G",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
					  rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"H",81 + iReng,"P",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
					  Vector vcMarcas = findByCustom("",
							  "SELECT M.CDSCMARCA " +
							  "FROM INSMARCATECNICO MT " +
							  "JOIN INSMARCASAUT M ON MT.ICVEMARCA = M.ICVEMARCA " +
							  "WHERE MT.ICVEESTACION = " +
							  vCertifIA.getInt("ICVEESTACION") +
							  " AND MT.ICVETECNICO = " +
							  vTecnicos.getInt("ICVETECNICO"));
					  String cMarcas = "";
					  for(int k = 0;k < vcMarcas.size();k++){
						  TVDinRep vMarcas = (TVDinRep) vcMarcas.get(k);
						  cMarcas += vMarcas.getString("CDSCMARCA");
						  cMarcas += (k + 1 < vcMarcas.size()) ? ", " : ".";
						  rep.comDespliegaCombinado("MARCAS AUTORIZADAS","Q",80,"AE",80,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,true,false,1,1);
						  rep.comDespliegaCombinado(cMarcas,"Q",81 + iReng,"AE",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
					  }
					  iReng++;
				  }
			  }
			  else{
				  for(int i = 0;i < vcTecnicos.size();i+=2){
					  TVDinRep vTecnicos = (TVDinRep) vcTecnicos.get(i);
					  String cInst = "",cTec = "";
					  int iNumTecnico = Integer.parseInt(vTecnicos.getString("cNumTecnico"));
					  if(iNumEstacion > 0 && iNumEstacion < 10)
						  cInst = "00" + iNumEstacion;
					  if(iNumEstacion > 9 && iNumEstacion < 100)
						  cInst = "0" + iNumEstacion;
					  if(iNumEstacion > 99) cInst = iNumEstacion + "";
					  if(iNumTecnico > 0 && iNumTecnico < 10)
						  cTec = "00" + iNumTecnico;
					  if(iNumTecnico > 9 && iNumTecnico < 100)
						  cTec = "0" + iNumTecnico;
					  rep.comFontSize("a",80,"az",300,7);
					  if( (iNumTecnico == 1) && vTecnicos.getInt("LRESPESTACION") == 1)
						  rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst,"c",81 + iReng,"G",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
					  else rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"c",81 + iReng,"G",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
					  rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"H",81 + iReng,"P",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
					  if((i+1)<vcTecnicos.size()){
						  i++;
						  vTecnicos = (TVDinRep) vcTecnicos.get(i);
						  cInst = "";
						  cTec = "";
						  iNumTecnico = Integer.parseInt(vTecnicos.getString("cNumTecnico"));
						  if(iNumEstacion > 0 && iNumEstacion < 10)
							  cInst = "00" + iNumEstacion;
						  if(iNumEstacion > 9 && iNumEstacion < 100)
							  cInst = "0" + iNumEstacion;
						  if(iNumEstacion > 99) cInst = iNumEstacion + "";
						  if(iNumTecnico > 0 && iNumTecnico < 10)
							  cTec = "00" + iNumTecnico;
						  if(iNumTecnico > 9 && iNumTecnico < 100)
							  cTec = "0" + iNumTecnico;
						  rep.comFontSize("a",80,"az",300,7);
						  if( (iNumTecnico == 1) && vTecnicos.getInt("LRESPESTACION") == 1)
							  rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst,"S",81 + iReng,"W",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
						  else rep.comDespliegaCombinado("DGMM-" +vCertifIA.getString("CABREVIATURA") +"-" + cInst + "-" + cTec,"S",81 + iReng,"W",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
						  rep.comDespliegaCombinado(vTecnicos.getString("cNombre"),"X",81 + iReng,"AF",81 + iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,2,true,false,1,1);
					  }
					  iReng++;
                  }
              }
          }
          if(!vCertifIA.getString("CSOLISITANTE").equals("null")) rep.comDespliegaAlineado("J",12,vCertifIA.getString("CSOLISITANTE"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO()); else rep.comDespliegaAlineado("J",12,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CRFC").equals("null")) rep.comDespliegaAlineado("X",14,vCertifIA.getString("CRFC"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("X",14,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          TFechas Fecha = new TFechas();
          String cFechaActual =""+Fecha.aumentaDisminuyeDias(vCertifIA.getDate("DTEXPEDICION"),335);
          String cFechaActual1 = cFechaActual.substring(0,4)+cFechaActual.substring(5,7)+cFechaActual.substring(8,10);
          String cFechaVigencia = "" + vCertifIA.getDate("DTFINVIGENCIA");
          String cFechaVigencia1 = cFechaVigencia.substring(0,4)+cFechaVigencia.substring(5,7)+cFechaVigencia.substring(8,10);
          if(Integer.parseInt(cFechaActual1)>Integer.parseInt(cFechaVigencia1)){
        	  rep.comDespliegaAlineado("A",10,"PROVISIONAL",false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
          }
          if(!vCertifIA.getString("CDOM").equals("null")) rep.comDespliegaAlineado("j",27,vCertifIA.getString("CDOM"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("j",27,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CCOLONIA").equals("null")) rep.comDespliegaAlineado("j",29,""+vCertifIA.getString("CCOLONIA"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("j",29,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CCODPOSTAL").equals("null")) rep.comDespliegaAlineado("j",31,"C.P. "+vCertifIA.getString("CCODPOSTAL")+", "+vCertifIA.getString("CMUN")+", "+vCertifIA.getString("CENTFED"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("j",31,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CDSCTIPOESTACION").equals("null")) rep.comDespliegaAlineado("j",17,vCertifIA.getString("CDSCTIPOESTACION"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("J",17,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CDSCCERTIFICADO").equals("null")) rep.comDespliegaAlineado("j",18,vCertifIA.getString("CDSCCERTIFICADO"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("J",18,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CNORMA").equals("null")) rep.comDespliegaAlineado("j",19,vCertifIA.getString("CNORMA"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());else rep.comDespliegaAlineado("J",19,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(iNumEstacion>0) rep.comDespliegaAlineado("AC",5,formatoNum000(iNumEstacion),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("AC",5,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CABREVIATURA").equals("null")) rep.comDespliegaAlineado("Y",5,vCertifIA.getString("CABREVIATURA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("Y",5,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CNORMA").equals("null")) rep.comDespliegaAlineado("L",24,"CONFORME A LA NORMA: "+vCertifIA.getString("CNORMA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("L",24,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("CNORMA").equals("null")) rep.comDespliegaAlineado("I",56,vCertifIA.getString("CNORMA"),false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("I",56,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("DTEXPEDICION").equals("null")) rep.comDespliegaAlineado("P",37,""+dFechas.getStringDay(vCertifIA.getDate("DTEXPEDICION")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("Q",37,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("DTEXPEDICION").equals("null")) rep.comDespliegaAlineado("R",37,""+dFechas.getMonthName(vCertifIA.getDate("DTEXPEDICION")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",37,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("DTEXPEDICION").equals("null")) rep.comDespliegaAlineado("Y",37,""+dFechas.getIntYear(vCertifIA.getDate("DTEXPEDICION")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("Y",37,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("I",35,""+dFechas.getStringDay(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("J",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("L",35,""+dFechas.getMonthName(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("O",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("U",35,""+dFechas.getIntYear(vCertifIA.getDate("DTFINVIGENCIA")),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("U",35,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("IEJERCICIO").equals("null")) rep.comDespliegaAlineado("A",1,""+vCertifIA.getInt("IEJERCICIO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",2,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          if(!vCertifIA.getString("INUMSOLICITUD").equals("null")) rep.comDespliegaAlineado("A",2,""+vCertifIA.getInt("INUMSOLICITUD"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("A",2,"--",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
          sbRetorno = rep.getSbDatos();
      }
      catch(Exception e){
    	  e.printStackTrace();
    	  cMensaje += e.getMessage();
      }
      if(!cMensaje.equals(""))
    	  throw new Exception(cMensaje);
      return sbRetorno;
  }
      
}

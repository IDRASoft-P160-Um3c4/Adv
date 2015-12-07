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

public class TDRINInconformidadesE extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
//  private TDTramite tTramite = new TDTramite();
  TExcel rep = new TExcel();

  public TDRINInconformidadesE(){
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
  public StringBuffer fReporteGen(String cFiltro) throws Exception{
      cError = "";
      TFechas dFechas = new TFechas();
      StringBuffer sbRetorno = new StringBuffer("");
      int Reng = 10;
        try{

//Obras de Protección
            rep.iniciaReporte();
 String cSQL =
             "SELECT "+
             "I.ICVEINCONFORMIDAD,JR.CDSCJUICIO,I.CNUMEXPEDIENTE,I.CAUTORIDADRESOLUTORA,EF.CNOMBRE AS CESTADO, "+
             "MU.CNOMBRE AS CMUNICIPIO, I.CACTORECLAMADO,I.COBSINCONFORMIDAD " +
             "FROM RININCONFORMIDAD I " +
             "JOIN RINJUICIOREC JR ON JR.ICVEJUICIOREC = I.ICVEJUICIOREC " +
             "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS= I.ICVEPAIS AND EF.ICVEENTIDADFED= I.ICVEENTIDADFED " +
             "JOIN GRLMUNICIPIO MU ON MU.ICVEPAIS= I.ICVEPAIS AND MU.ICVEENTIDADFED= I.ICVEENTIDADFED AND MU.ICVEMUNICIPIO= I.ICVEMUNICIPIO "+
             " Where "+ cFiltro+
             " Order By I.ICVEINCONFORMIDAD";
             //"Where I.ICVEINCONFORMIDAD in ("+cFiltro+")";

            Vector vcData = findByCustom("",cSQL);

            for(int i=0;i<vcData.size();i++){
              String cPromoventes="";
              String cSuspencion="";
              String cResolucion="";
              TFechas Fecha = new TFechas();

               TVDinRep vInconformidad = (TVDinRep) vcData.get(i);
               Vector vcPromovente = findByCustom("",
                      "SELECT P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CPERSONA " +
                      "FROM RININCONFORMES I " +
                      "join GRLPERSONA P ON I.ICVEINCONFORME = P.ICVEPERSONA " +
                      "WHERE I.ICVEINCONFORMIDAD = "+vInconformidad.getString("ICVEINCONFORMIDAD"));
               Vector vcSuspencion = findByCustom("",
                  "SELECT S.CDSCSUSPENSION,ISUS.DTSUSPENSION,ISUS.LSECONCEDE,ISUS.COBSCONCEDE,ISUS.LSENIEGA,ISUS.COBSNIEGA " +
                  "FROM RININCONFORMIDADSUSPENSION ISUS " +
                  "join RINSUSPENSION S on S.ICVESUSPENSION = ISUS.ICVESUSPENSION " +
                  "WHERE ISUS.ICVEINCONFORMIDAD = "+vInconformidad.getString("ICVEINCONFORMIDAD"));
               Vector vcResolucion = findByCustom("",
                  "SELECT R.CDSCRESOLUCION,IR.DTRESOLUCION,COBSRESOLUCION " +
                  "FROM RININCONFORMIDADRESOLUCION IR " +
                  "JOIN RINRESOLUCION R ON IR.ICVERESOLUCION = R.ICVERESOLUCION "+
                  "WHERE IR.ICVEINCONFORMIDAD = "+vInconformidad.getString("ICVEINCONFORMIDAD"));


               for(int j=0;j<vcPromovente.size();j++){
                 TVDinRep vPromovente = (TVDinRep) vcPromovente.get(j);
                 cPromoventes += vPromovente.getString("CPERSONA");
                 if(j<vcPromovente.size()-1)cPromoventes+=", ";
                 else cPromoventes+=".";
               }
               for(int j=0;j<vcSuspencion.size();j++){
                 TVDinRep vSuspencion = (TVDinRep) vcSuspencion.get(j);
                 cSuspencion += vSuspencion.getString("CDSCSUSPENSION")+": "+
                                 Fecha.getFechaDDMMMMMYYYY(vSuspencion.getDate("DTSUSPENSION")," de ");
                 if(vSuspencion.getInt("LSECONCEDE")==1)cSuspencion+=" SE CONCEDE "+vSuspencion.getString("COBSCONCEDE");
                 if(vSuspencion.getInt("LSENIEGA")==1)cSuspencion+=" SE CONCEDE "+vSuspencion.getString("COBSNIEGA");
                 cSuspencion += "  ";
               }
               for(int j=0;j<vcResolucion.size();j++){
                 TVDinRep vResolucion = (TVDinRep) vcResolucion.get(j);
                 cResolucion += vResolucion.getString("CDSCRESOLUCION")+": "+
                                Fecha.getFechaDDMMMMMYYYY(vResolucion.getDate("DTRESOLUCION")," de ")+ "  " +
                                vResolucion.getString("COBSRESOLUCION") ;
                 cResolucion += "  ";
               }


               if(!vInconformidad.getString("ICVEINCONFORMIDAD").equals("null"))
                 rep.comDespliegaAlineado("A",Reng+i,vInconformidad.getString("ICVEINCONFORMIDAD"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              // else rep.comDespliegaAlineado("A",(Reng+i),"",false,rep.getAT_HCENTRO(), rep.getAT_VCENTRO());
               rep.comDespliegaAlineado("B",Reng+i,cPromoventes,false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               if(!vInconformidad.getString("CDSCJUICIO").equals("null"))
                 rep.comDespliegaAlineado("C",Reng+i,vInconformidad.getString("CDSCJUICIO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               //else rep.comDespliegaAlineado("C",Reng+i,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               if(!vInconformidad.getString("CNUMEXPEDIENTE").equals("null"))
                 rep.comDespliegaAlineado("D",Reng+i,vInconformidad.getString("CNUMEXPEDIENTE"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               //else rep.comDespliegaAlineado("D",Reng+i,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               if(!vInconformidad.getString("CAUTORIDADRESOLUTORA").equals("null"))
                   rep.comDespliegaAlineado("E",Reng+i,"" + vInconformidad.getString("CAUTORIDADRESOLUTORA"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               //else rep.comDespliegaAlineado("E",Reng+i,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               if(!vInconformidad.getString("CESTADO").equals("null"))
                 rep.comDespliegaAlineado("F",Reng+i,vInconformidad.getString("CESTADO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               //else rep.comDespliegaAlineado("F",Reng+i,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               if(!vInconformidad.getString("CMUNICIPIO").equals("null"))
                 rep.comDespliegaAlineado("G",Reng+i,vInconformidad.getString("CMUNICIPIO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               //else rep.comDespliegaAlineado("G",Reng+i,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               if(!vInconformidad.getString("CACTORECLAMADO").equals("null"))
                 rep.comDespliegaAlineado("H",Reng+i,vInconformidad.getString("CACTORECLAMADO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               rep.comDespliegaAlineado("I",Reng+i,cSuspencion,false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               rep.comDespliegaAlineado("J",Reng+i,cResolucion,false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               //else rep.comDespliegaAlineado("H",Reng+i,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               if(!vInconformidad.getString("COBSINCONFORMIDAD").equals("null"))
                 rep.comDespliegaAlineado("K",Reng+i,vInconformidad.getString("COBSINCONFORMIDAD"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
               //else rep.comDespliegaAlineado("K",Reng+i,"",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
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

}

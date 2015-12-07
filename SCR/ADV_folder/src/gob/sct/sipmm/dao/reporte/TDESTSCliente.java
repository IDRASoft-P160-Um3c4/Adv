package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import com.micper.excepciones.DAOException;

public class TDESTSCliente extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TExcel rep = new TExcel();

  public TDESTSCliente(){
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
      StringBuffer sbRetorno = new StringBuffer("");
      String [] Filtro = cFiltro.split(",");
      int count=0;
      TVDinRep vContratados = new TVDinRep();
      TVDinRep vConcepto = new TVDinRep();
      int reng=11;

        try{
            rep.iniciaReporte();

              String cSQL =
                  "Select " +
                  " SC.cTipoQueja, " +
                  " SC.iPresentadas, " +
                  " SC.iAtendidas, " +
                  " OL.cDscObsLarga " +
                  " from ESTSatisfaccionCte SC " +
                  " left join GRLObservaLarga OL ON OL.iEjercicioObsLarga = SC.iEjercicioObsLarga and " +
                  " OL.iCveObsLarga = SC.iCveObsLarga Where SC.iEjercicioSeguimiento = " +Filtro[0]+
                  " AND SC.iNumMovtoSegto = "+Filtro[1];
              Vector vcData = findByCustom("",cSQL);

              for(int j=0;j<vcData.size();j++){
                vContratados = (TVDinRep) vcData.get(j);
                rep.comDespliegaAlineado("A",reng+j,vContratados.getString("cTipoQueja"),true,rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
                rep.comDespliegaAlineado("B",reng+j,vContratados.getString("iPresentadas"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                rep.comDespliegaAlineado("C",reng+j,vContratados.getString("iAtendidas"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                rep.comDespliegaAlineado("D",reng+j,vContratados.getString("cDscObsLarga"),true,rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
                rep.comBordeTotal("A",reng+j,"D",reng+j,1);
              }

            rep.comDespliegaAlineado("A",7,""+Filtro[2],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            rep.comDespliegaAlineado("A",8,"Correspondiente a " + Filtro[3] + " del " +Filtro[4],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());

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

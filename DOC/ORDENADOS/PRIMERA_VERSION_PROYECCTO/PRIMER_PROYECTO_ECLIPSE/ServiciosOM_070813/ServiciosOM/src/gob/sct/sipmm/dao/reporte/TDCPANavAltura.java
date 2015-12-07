package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;

import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDInconformidadesW.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Ing. Arturo López Peña
 * @version 1.0
 */

public class TDCPANavAltura extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  public TDCPANavAltura(){
  }
  public Vector GenOficio(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
  Vector vRegs = new Vector();
  Vector vInconformes = new Vector();
  Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
  TWord rep = new TWord();
  TFechas tFecha = new TFechas();
  String[] cParametros = cQuery.split(",");

/************* /
  try{
    vRegs = super.FindByGeneric("","***********query********", dataSourceName);
  }catch(SQLException ex){
    cMensaje = ex.getMessage();
  }catch(Exception ex2){
    ex2.printStackTrace();
  }
/***********/

rep.iniciaReporte();




/************** /
  if(vRegs.size()>0){
    rep.comRemplaza("[ETIQUETA]","VALOR");
    }else {
      rep.comRemplaza("[ETIQUETA]","VALOR");
    }
/***************/
    rep.comRemplaza("[ETIQUETA]","VALOR");
    Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
        Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
        0,0,
        Integer.parseInt(cParametros[0]),Integer.parseInt(cParametros[1]),Integer.parseInt(cParametros[2]),
        "","",
        "","",
        true,"cCuerpo",vcCuerpo,
        true,vcCopiasPara,
        rep.getEtiquetasBuscar(),rep.getEtiquetasRemplazar());

  return vRetorno;
  }

  // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo


  public Vector genOpnInt(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
  Vector vRegs = new Vector();
  Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
  TWord rep = new TWord();
  TFechas tFecha = new TFechas();



  int iCveSegtoEntidad=0,iCveOpinionEntidad, iCveDepto=0, iCveOficina=0 ;
  String[] cParametros = cQuery.split(",");
  TDObtenDatosOpiniones datosOpinion = new TDObtenDatosOpiniones();
  datosOpinion.dDatosOpnTra.setFiltrosOpn(cQuery);
  iCveSegtoEntidad = datosOpinion.dDatosOpnTra.getiCveSegtoEntidad();
  iCveOpinionEntidad = datosOpinion.dDatosOpnTra.getiCveOpinionEntidad();
  try{
    datosOpinion.dDatosFolioOpn.setFolio(iCveSegtoEntidad,cNumFolio);
  }catch(Exception e){
    e.printStackTrace();
    cMensaje += e.getMessage();
  }
  datosOpinion.dDatosOficPer.setFiltroOpnEnt(iCveOpinionEntidad);
  iCveDepto = datosOpinion.dDatosOficPer.getiCveDepto();
  iCveOficina = datosOpinion.dDatosOficPer.getiCveOficina();

/************* /
  try{
    vRegs = super.FindByGeneric("","***********query********", dataSourceName);
  }catch(SQLException ex){
    cMensaje = ex.getMessage();
  }catch(Exception ex2){
    ex2.printStackTrace();
  }
/***********/

rep.iniciaReporte();
/************** /
  if(vRegs.size()>0){
    rep.comRemplaza("[ETIQUETA]","VALOR");
    }else {
      rep.comRemplaza("[ETIQUETA]","VALOR");
    }
/***************/
    rep.comRemplaza("[ETIQUETA]","VALOR");
    Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
        Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
        iCveOficina,iCveDepto,
        0,0,0,
        "","",
        "","",
        true,"cCuerpo",vcCuerpo,
        true,vcCopiasPara,
        rep.getEtiquetasBuscar(),rep.getEtiquetasRemplazar());

  return vRetorno;
  }



}


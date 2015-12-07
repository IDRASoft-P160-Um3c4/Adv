package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;
import gob.sct.sipmm.dao.*;

public class TDCYSSinEfecto
    extends DAOBase{
  private int iEjercicioOficio = 0;
  private String cDigitosFolio = "";
  private int iConsecutivo = 0;

  public TDCYSSinEfecto(){
  }

  public Vector genSinEfectos(String cQuery,String cNumFolio,
                              String cCveOficinaOrigen,String cCveDeptoOrigen){

    Vector vcCuerpo = new Vector();
    Vector vcCopiasPara = new Vector();
    Vector vRegs = new Vector();
    TWord rep = new TWord();
    TFechas Fechas = new TFechas("44");
    TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();

    int iCveOficinaDest = 0,iCveDeptoDest = 0;
    String cAsunto = "Se deja sin efectos el Procedimiento Administrativo de ";
    String[] aFiltros = cQuery.split(",");
    int iCveTitulo = Integer.parseInt(aFiltros[0]);
    int iEjercicio = Integer.parseInt(aFiltros[1]);
    int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
    int iMovFolioProc = Integer.parseInt(aFiltros[3]);
    int tipoPersona=0;
    String cNumEscrito = aFiltros[4];
    String cFechaEscrito = aFiltros[5];
    Vector V1 = new Vector();
    Vector V2 = new Vector();
    Vector V3 = new Vector();
    TVDinRep vTitulo = new TVDinRep();
    TVDinRep vProcedimiento = new TVDinRep();
    TVDinRep vUbicacion = new TVDinRep();

    this.setDatosFolio(cNumFolio);
    TDCYSSegProcedimiento P1 = new TDCYSSegProcedimiento();
    TDCPATitulo1 T1 = new TDCPATitulo1(); //
    try{
      V2 = P1.findByOficioNotRec(iEjercicio,iMovProcedimiento,0);
    } catch(Exception e){
      e.printStackTrace();
    }

    try{
      V1 = T1.findByInfTitulo(iCveTitulo);
    } catch(Exception e){
      e.printStackTrace();
    }

    try{
      V3 = T1.findByInfTituloUbicacion(iCveTitulo);
    } catch(Exception e){
      e.printStackTrace();
    }
    
    if(V1.size() > 0)
      vTitulo = (TVDinRep) V1.get(0);
    
    if(V2.size() > 0)
      vProcedimiento = (TVDinRep) V2.get(0);
    
    if(V3.size() > 0)
      vUbicacion = (TVDinRep) V3.get(0);
    
    Date iniVigencia = vTitulo.getDate("dtIniVigenciaTitulo");
    String objetoTitulo = vTitulo.getString("cObjetoTitulo");
    String cveUsoTitulo = vTitulo.getString("cDscUsoTitulo");
    Date finVigencia = vTitulo.getDate("dtVigenciaTitulo");
    String puerto = vUbicacion.getString("cDscPuerto");
    int iAniosDuracion = Fechas.getDaysBetweenDates(vTitulo.getDate(
        "dtIniVigenciaTitulo"),vTitulo.getDate("dtVigenciaTitulo"));
    iAniosDuracion = iAniosDuracion / 365;
    int iCveRepLegal = 0;
    if(vTitulo.getInt("iCveTitular") > 0){
      String cSql = "SELECT P.CNOMRAZONSOCIAL,P.ITIPOPERSONA, P1.ICVEPERSONA as iRepLegal FROM GRLPERSONA P " +
          "LEFT JOIN GRLREPLEGAL RL ON RL.ICVEEMPRESA = P.ICVEPERSONA " +
          "LEFT JOIN GRLPERSONA P1 ON P1.ICVEPERSONA = RL.ICVEPERSONA " +
          "WHERE P.ICVEPERSONA = " + vTitulo.getInt("iCveTitular");
      Vector vcData = new Vector();
      try{
        vcData = this.FindByGeneric("",cSql,dataSourceName);
      } catch(Exception e){
        e.printStackTrace();
      }
      TVDinRep vPersona = (TVDinRep) vcData.get(0);

      iCveRepLegal = vPersona.getInt("ITIPOPERSONA") == 1 ? 0 : vPersona.getInt("iRepLegal");
      tipoPersona = vPersona.getInt("ITIPOPERSONA");
    }
    if(tipoPersona != 1)rep.comRemplaza("[cRepresentada]","(a su representada)");
    else rep.comRemplaza("[cRepresentada]","");
    rep.comRemplaza("[dtIniVigenciaTitulo]",
                    Fechas.getFechaLetras(vTitulo.getDate("dtIniVigenciaTitulo")).
                    toLowerCase());
    rep.comRemplaza("[cObjetoTitulo]",objetoTitulo.toLowerCase());
    rep.comRemplaza("[cDscUsoTitulo]","" + cveUsoTitulo.toLowerCase());
    rep.comRemplaza("[CalcularAñosConVigencia]",iAniosDuracion + " años");
    rep.comRemplaza("[cDscPuerto]",puerto.toLowerCase());
    rep.comRemplaza("[cNumEscrito]",
                    cNumEscrito + " del " +
                    Fechas.getFechaLetras(Fechas.getDateSQL(cFechaEscrito)).
                    toLowerCase());
    rep.comRemplaza("[NoOficio]",
                    (vProcedimiento.getString("cDigitosFolio") + "." +
                      vProcedimiento.getString("iConsecutivo") + "/" +
                      vProcedimiento.getString("iEjercicioFol")).toLowerCase());
    rep.comRemplaza("[fechaOficioConLetra]",
                    Fechas.getFechaLetras(vProcedimiento.getDate("dtAsignacion")).
                    toLowerCase());

    iCveOficinaDest = 0;
    iCveDeptoDest = 0;
    
    //return ;
    // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
    Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
        Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
        iCveOficinaDest,iCveDeptoDest,
        vTitulo.getInt("iCveTitular"),vTitulo.getInt("iCveDomicilio"),
        iCveRepLegal,
        "",cAsunto,
        "","",
        true,"cCuerpo",vcCuerpo,
        true,vcCopiasPara,
        rep.getEtiquetasBuscar(),rep.getEtiquetasRemplazar());

    TVDinRep vDinRep = new TVDinRep();
    vDinRep.put("iEjercicio",iEjercicio);
    vDinRep.put("iMovProcedimiento",iMovProcedimiento);
    vDinRep.put("iMovFolioProc",iMovFolioProc);
    vDinRep.put("iEjercicioFol",iEjercicioOficio);
    vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
    vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
    vDinRep.put("cDigitosFolio",cDigitosFolio);
    vDinRep.put("iConsecutivo",iConsecutivo);
    vDinRep.put("lEsProrroga",0);
    vDinRep.put("lEsAlegato",0);
    vDinRep.put("lEsSinEfecto",1);

    try{
      vDinRep = dCYSFolioProc.updateSinEfectos(vDinRep,null);
    } catch(Exception ex){
      ex.printStackTrace();
    }

    return vRetorno;

  }

  private void setDatosFolio(String cNumFolio){
    String[] cDatosFolio = cNumFolio.split("/");
    String cTemp = cDatosFolio[0].replace('.','/');
    String[] cDigitosTemp = cTemp.split("/");
    int Ejercicio = Integer.parseInt(cDatosFolio[cDatosFolio.length - 1],10);
    int Consecutivo = Integer.parseInt(cDigitosTemp[cDigitosTemp.length - 1],10);
    String Digitos = "";
    for(int i = 0;i < cDigitosTemp.length - 1;i++){
      Digitos += cDigitosTemp[i];
      if(i < cDigitosTemp.length - 2)
        Digitos += ".";
    }
    iEjercicioOficio = Ejercicio;
    cDigitosFolio = Digitos;
    iConsecutivo = Consecutivo;
  }

}

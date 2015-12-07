package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;

/**
 * <p>Title: TDOficio.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @author LSC. Armando Barrientos Martínez
 * @version 1.0
 */

public class TDOficio extends DAOBase{
  public TDOficio(){
  }
public StringBuffer WordOficio(String cQuery, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen){
  Vector vcCuerpo = new Vector();
  Vector vcCopiasPara = new Vector();
  TWord rep = new TWord();
/*
  vcCuerpo.add(new StringBuffer("Parrafo 1"));
  vcCuerpo.add(new StringBuffer("Parrafo 2"));
  vcCuerpo.add(new StringBuffer("Parrafo 3"));
  vcCuerpo.add(new StringBuffer("Parrafo 4"));
  vcCuerpo.add(new StringBuffer("Parrafo 5"));
*/
/*
  vcCopiasPara.add("Gaby");
  vcCopiasPara.add("Armando");
  vcCopiasPara.add("Oscar");
*/
/*
  rep.comRemplaza("[cPruebas]","DATO 1");
  rep.comRemplaza("[cDatosMas]", "Dato XXXXX");
*/
  /* Ejemplo de llamado 1  empleado para oficina y depto por parametro, destino interno
  return new TDGeneral().generaOficioWord(cNumFolio,
                                  Integer.parseInt(iCveOficinaOrigen,10),
                                  Integer.parseInt(iCveDeptoOrigen,10),
                                  1,50,
                                  0,0,0,
                                  "OFICIO","Prueba de Generacion de oficio", "", "",
                                  true,"cCuerpo",vcCuerpo,
                                  true, vcCopiasPara,
                                  rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());*/
  /* Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
  return new TDGeneral().generaOficioWord(cNumFolio,
                                  1,67,
                                  0,0,
                                  1,2,2,
                                  "MEMO","Prueba de Generacion de oficio", "", "",
                                  true,"cCuerpo",vcCuerpo,
                                  true, vcCopiasPara,
                                  new StringBuffer(), new StringBuffer());*/
  /* Ejemplo de llamado 3 empleado para oficina y depto fijo, destino interno
  return new TDGeneral().generaOficioWord(cNumFolio,
                                  1,67,
                                  0,0,
                                  1,2,2,
                                  "NOTA INFORMATIVA","Prueba de Generacion de oficio", "", "",
                                  true,"cCuerpo",vcCuerpo,
                                  true, vcCopiasPara,
                                  new StringBuffer(), new StringBuffer());*/
  return new StringBuffer();
}

public StringBuffer ejemploReporteWord(String cQuery) throws Exception{
  Vector vRegs = new Vector();

  TWord rep = new TWord();
  rep.iniciaReporte();

  try{
    vRegs = super.FindByGeneric("", cQuery, dataSourceName);
  }catch(SQLException ex){
    cMensaje = ex.getMessage();
  }catch(Exception ex2){
    cMensaje += ex2.getMessage();
  }

  if (vRegs.size() > 0){
       TVDinRep vDatos = (TVDinRep) vRegs.get(0);

       rep.comRemplaza("[NoCertificado]",vDatos.get("iNumCertificado").toString());
       rep.comRemplaza("[NombreBuque]",vDatos.get("cNomEmbarcacion").toString());
       rep.comRemplaza("[FechaExpedicion]",vDatos.get("dtInicioVigencia").toString());
       rep.comRemplaza("[NoOMI]",vDatos.get("cNumOMI").toString());
       rep.comRemplaza("[ArqueoBruto]",vDatos.get("dArqueoBruto").toString());
       rep.comRemplaza("[NombreDireccion]",vDatos.get("nombre").toString());
       rep.comRemplaza("[Vigencia]",vDatos.get("dtFinVigencia").toString());

  }
  if(!cMensaje.equals(""))
    throw new Exception(cMensaje);
  return rep.getEtiquetas(true);
}

}

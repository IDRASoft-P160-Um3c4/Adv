package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import java.sql.SQLException;

/**
 * <p>Title: TDCPACatalogos.java</p>
 * <p>Description: DAO con métodos generales para excel y word</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDINSCatalogos extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas    tFecha      = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  public TDINSCatalogos(){
  }

  public StringBuffer exportaExcel(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    StringBuffer sbRes = new StringBuffer("");
    cError = "";
    int iRengIni = 10;
    char cColIni = 'A', cColAct;
    TExcel rep = new TExcel();

    rep.iniciaReporte();
    if(cQuery != null && !cQuery.equals(""))
      if (!cQuery.toUpperCase().startsWith("SELECT "))
        rep.comDespliega(String.valueOf(cColIni), iRengIni - 2, "ESTE METODO REQUIERE UN QUERY COMPLETO, NO SOLO FILTRO PARA EJECUCION");
      else{
        rep.comDespliega(String.valueOf(cColIni),iRengIni - 2,cQuery);
      }
    else
      rep.comDespliega(String.valueOf(cColIni), iRengIni - 2, "NO SE PROPORCIONO QUERY PARA EJECUCION");

//        rep.comDespliega(String.valueOf(cColIni),iRengIni - 2,"");
    // El try-catch no debe cambiar en cada método, siempre es el mismo
    try{
      vRegs = super.FindByGeneric("", cQuery, dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }

rep.comDespliega(String.valueOf(cColIni),iRengIni - 2,"");
    if (vRegs.size() > 0){
      TVDinRep regAct = (TVDinRep) vRegs.get(0);
      Object[] objTitulos = regAct.toHashMap().keySet().toArray();
      cColAct = cColIni;
      for (int i = 1; i < objTitulos.length; i++, cColAct++);
      cColAct = (cColAct < 'K')?'K':cColAct;
      rep.comAlineaRango(String.valueOf(cColIni), iRengIni-2, String.valueOf(cColAct), iRengIni-2, rep.getAT_COMBINA_IZQUIERDA());
      rep.comAlineaRangoVer(String.valueOf(cColIni), iRengIni-2, String.valueOf(cColAct), iRengIni-2, rep.getAT_VCENTRO());
      rep.comAlineaRangoVer(String.valueOf(cColIni), iRengIni-2, String.valueOf(cColAct), iRengIni-2, rep.getAT_VAJUSTAR());
      sbRes = rep.getSbDatos();
      sbRes.append(exportaExcel(vRegs, objTitulos, iRengIni,cColIni,true,true,true));
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return sbRes;
  }

  public StringBuffer exportaExcel(Vector vRegs, Object[] oTitulos, int iRengIni, char cColIni, boolean lTitulos, boolean lBordes, boolean lFormatos){
    TVDinRep regAct;
    TExcel rep = new TExcel();

    rep.iniciaReporte();
    if (vRegs.size() > 0){
      char cColAct = cColIni;
      String sCol = String.valueOf(cColAct), cDato, cTitulo;
      int iReng = iRengIni + 1;
      // Inicia procesamiento de despliegue de datos en reporte
      for (int i = 0; i < vRegs.size(); i++, iReng++){ // Despliegue de datos de registros
        cColAct = cColIni;
        regAct = (TVDinRep) vRegs.get(i);
        for (int j=0; j < regAct.size(); j++, cColAct++){
          cTitulo = (String)oTitulos[j];
          cDato = regAct.get(cTitulo).toString();
          if (cTitulo.toUpperCase().startsWith("L"))
            cDato = cDato.equals("1")?"SI":"NO";
          sCol = String.valueOf(cColAct);
          rep.comDespliega(sCol,iReng,cDato);
        }
      }
      cColAct = cColIni;
      cTitulo = "";
      for (int i = 0; i < oTitulos.length; i++, cColAct++){ // Despliegue de etiquetas, alineación de columnas
        sCol = String.valueOf(cColAct);
        cTitulo = (String)oTitulos[i];
        if (lTitulos){
          rep.comDespliega(sCol,iRengIni,cTitulo);
          rep.comAlineaRango(sCol,iRengIni,sCol,iRengIni,rep.getAT_HCENTRO());
          rep.comFontBold(sCol,iRengIni,sCol,iRengIni);
          rep.comFillCells(sCol,iRengIni,sCol,iRengIni,15);
          rep.comAlineaRangoVer(sCol, iRengIni, sCol, iRengIni, rep.getAT_VAJUSTAR());
        }
        if (lFormatos){
          rep.comAlineaRangoVer(sCol,iRengIni + 1,sCol,iReng-1,rep.getAT_VAJUSTAR());
          switch(super.getITipo(cTitulo)){
            case 1: // Entero o lógico
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1,rep.getAT_HCENTRO());
              break;
            case 2: // Caracter
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1, rep.getAT_HJUSTIFICA());
              break;
            case 3: // Fecha
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1,rep.getAT_HCENTRO());
              break;
            case 4: // Decimal
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1, rep.getAT_HDERECHA());
              break;
            case 6: // TimeStamp
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1,rep.getAT_HCENTRO());
              break;
            default: // Tipo de default interpretadoc como cadena
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng, rep.getAT_HJUSTIFICA());
              break;
          }
        }
      }
      cColAct = cColIni;
      for (int i = 1; i < oTitulos.length; i++, cColAct++);
      sCol = String.valueOf(cColAct);
      if (lBordes)
        if (lTitulos)
          rep.comBordeTotal(String.valueOf(cColIni), iRengIni, sCol, iReng-1, 1);
        else
          rep.comBordeTotal(String.valueOf(cColIni), iRengIni+1, sCol, iReng-1, 1);
      // Termina procesamiento de despliegue de datos en reporte
    }
    return rep.getSbDatos();
  }
}

package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import java.util.Vector;
import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDOficiosConcesiones.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author LSC. Arturo L�pez Pe�a
 * @version 1.0
 */

public class TDDEMDerrotero extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  public TDDEMDerrotero(){
  }

  // M�todo para ejecuci�n de Resumen de Boletines, Notas y Avisos
  public HashMap boletinAvisoNotaJasper(String cFiltro) throws Exception{
    HashMap hParametros = new HashMap();
    hParametros.put("iEjercicio",new Integer(cFiltro));
    return hParametros;
  }

  // M�todo para ejecuci�n del Bolet�n Meteorologico
  public HashMap boletinMetJasper(String cFiltro) throws Exception{
    HashMap hParametros = new HashMap();
    String[] cDatos = cFiltro.split(",");
    hParametros.put("iEjercicio",new Integer(cDatos[0]));
    hParametros.put("iNumBoletin",new Integer(cDatos[1]));
    return hParametros;
  }

  // M�todo para ejecuci�n de Notas Informativas
  public HashMap notaInformativaJasper(String cFiltro) throws Exception{
    HashMap hParametros = new HashMap();
    String[] cDatos = cFiltro.split(",");
    System.out.println("*****     Ejercicio:     "+new Integer(cDatos[0]));
    System.out.println("*****     NumNota:       "+new Integer(cDatos[1]));
    System.out.println("*****     CveLitoral:    "+new Integer(cDatos[2]));
    hParametros.put("iEjercicio",new Integer(cDatos[0]));
    hParametros.put("iNumNota",new Integer(cDatos[1]));
    hParametros.put("iCveLitoral",new Integer(cDatos[2]));
    return hParametros;
  }

  // M�todo para ejecuci�n de Avisos por Fen�meno Meteorol�gico
  public HashMap avisoFenomenoJasper(String cFiltro) throws Exception{
    HashMap hParametros = new HashMap();
    String[] cDatos = cFiltro.split(",");
    hParametros.put("iEjercicio",new Integer(cDatos[0]));
    hParametros.put("iNumFenomeno",new Integer(cDatos[1]));
    hParametros.put("iNumAviso",new Integer(cDatos[2]));
    return hParametros;
  }
}

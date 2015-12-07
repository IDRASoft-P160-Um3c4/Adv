package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;
import gob.sct.sipmm.dao.ws.TDCis;

/**
 * <p>Title: TDTRARelacionCIS.java</p>
 * <p>Description: DAO que interactúa con los webServices del CIS y</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TDTRARelacionCIS
  extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  TDCis dCIS = new TDCis();
  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepción de tipo DAO
   * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
   */
  public Vector findByCustom(String cKey, String cWhere) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey, cSQL, dataSourceName);
    } catch(SQLException e){
      cError = super.getSQLMessages(e);
    } catch(Exception e){
      cError = e.getMessage();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
  }

  public Vector buscaCitasCIS(TVDinRep vdBuscar) throws DAOException{
    Vector vCitas = new Vector();
    try{
      vCitas = dCIS.consultaCitas(vdBuscar.getString("dtInicial"), vdBuscar.getString("dtFinal"), "");
    } catch(Exception e){
      e.printStackTrace();
      cMensaje = e.getMessage();
    } finally{
      if(!cMensaje.equals(""))
        throw new DAOException(cError);
      return vCitas;
    }
  }

  public Vector buscaUsuarioCIS(TVDinRep vdBuscar) throws DAOException{
    Vector vUsuarios = new Vector();
    try{
      if(!vdBuscar.getString("iCvePersonaCIS").equals(""))
        vUsuarios = dCIS.consultaInteresado(1, vdBuscar.getString("iCvePersonaCIS"));
      else if(!vdBuscar.getString("cRFC").equals(""))
        vUsuarios = dCIS.consultaInteresado(2, vdBuscar.getString("cRFC"));
      else if(!vdBuscar.getString("cCURP").equals(""))
        vUsuarios = dCIS.consultaInteresado(3, vdBuscar.getString("cCURP"));
    } catch(Exception e){
      e.printStackTrace();
      cMensaje = e.getMessage();
    } finally{
      if(!cMensaje.equals(""))
        throw new DAOException(cError);
      return vUsuarios;
    }
  }

  public Vector insertaUsuarioCIS(TVDinRep vdDatos) throws DAOException{
    Vector vUsuario = new Vector();
    String cDomicilio = "", cTelefono = "";
    TVDinRep vdDatosRegreso = vdDatos;
    int iCveUsuarioCIS = 0;
    try{
      if(!vdDatos.getString("cRFC").equals("") && !vdDatos.getString("cNombre").equals("") &&
         !vdDatos.getString("iCvePais").equals("") && !vdDatos.getString("iCveEntidadFed").equals("") &&
         !vdDatos.getString("iCveMunicipio").equals("") && !vdDatos.getString("cColonia").equals("") &&
         !vdDatos.getString("cCalle").equals(""))
        cDomicilio = vdDatos.getString("cCalle");
        cTelefono = vdDatos.getString("cTelefono");
        cDomicilio += (!vdDatos.getString("cNumExterior").equals(""))?" No. " + vdDatos.getString("cNumExterior"):"";
        cDomicilio += (!vdDatos.getString("cNumInterior").equals(""))?" Int. " + vdDatos.getString("cNumInterior"):"";
        iCveUsuarioCIS = dCIS.insertaInteresado(vdDatos.getString("cRFC"),
                               vdDatos.getString("cCURP"), vdDatos.getString("cNombre"),
                               vdDatos.getString("cApPateno"), vdDatos.getString("cApMaterno"),
                               vdDatos.getInt("iCvePais"), vdDatos.getInt("iCveEntidadFed"),
                               vdDatos.getInt("iCveMunicipio"), vdDatos.getString("cColonia"),
                               cDomicilio, vdDatos.getString("cCodPostal"),cTelefono,
                               vdDatos.getString("cCorreoE"));
    } catch(Exception e){
      e.printStackTrace();
      cMensaje = e.getMessage();
    } finally{
      if(!cMensaje.equals(""))
        throw new DAOException(cError);
      vdDatosRegreso.put("iCvePersonaCIS",iCveUsuarioCIS);
      vUsuario.addElement(vdDatosRegreso);
      return vUsuario;
    }
  }

}

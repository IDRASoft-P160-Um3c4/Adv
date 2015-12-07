package com.micper.seguridad.dao;

//Java imports
import java.sql.*;
import java.util.*;

import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

public class TDPermisos {
  private String dataSrcName = "";
  private Vector vcMenuUsuario = new Vector();
  private HashMap hmPUsuario;

  private TParametro vParametros;

  public TDPermisos() {
  }

public void menuUsuario(String cNumModulo, int iCveUsuario){
     vParametros = new TParametro(cNumModulo);
     Vector vcMenu = TDMenu.getVSystemMenu(cNumModulo);
     hmPUsuario = this.permisosUsuario(iCveUsuario,Integer.parseInt(cNumModulo,10));
     HashMap hmAuxPadres = new HashMap();

     TVMenu vMenu;

     for(int i = 0; i < vcMenu.size(); i++){
       vMenu = (TVMenu) vcMenu.get(i);
       if(hmPUsuario.containsKey(vMenu.getCNomPagina())){
          if(vMenu.getIOpcPadre() == 0 || hmAuxPadres.containsKey(""+vMenu.getIOpcPadre()) ){
            StringTokenizer stActualizacion = new StringTokenizer((String)hmPUsuario.get(vMenu.getCNomPagina()),"|");
            if(stActualizacion.countTokens() == 2){
              stActualizacion.nextElement();
              vMenu.setLActualizacion(Integer.parseInt(""+stActualizacion.nextElement()));
            }else{
              vMenu.setLActualizacion(0);
            }
            vcMenuUsuario.add(vMenu);
            hmAuxPadres.put(""+vMenu.getIOrden(),""+vMenu.getIOrden());
          }
        }
     }
  }

private HashMap permisosUsuario(int iCveUsuario, int iCveSistema) {
    DbConnection dbConn = null;
    Connection conn = null;
    PreparedStatement lPStmt = null;
    ResultSet lRSet = null;
    HashMap vPermisos = new HashMap();

    try {
      dataSrcName = vParametros.getPropEspecifica("ConDB");
      dbConn = new DbConnection(dataSrcName);
      conn = dbConn.getConnection();
      conn.setTransactionIsolation(2);

      String lSQL =
          "select segprograma.cNombre, segprograma.cdscprograma, segpermisoxgpo.lactualizacion "+
          "from segusuario  "+
          "join seggpoxusr on segusuario.icveusuario = seggpoxusr.icveusuario "+
          "and  seggpoxusr.icvesistema = ? "+
          "join seggrupo on seggpoxusr.icvesistema = seggrupo.icvesistema "+
          "and  seggpoxusr.icvegrupo = seggrupo.icvegrupo  "+
          "and  seggrupo.lbloqueado = 0 "+
          "join segpermisoxgpo on seggpoxusr.icvesistema = segpermisoxgpo.icvesistema "+
          "and  seggpoxusr.icvegrupo = segpermisoxgpo.icvegrupo "+
          "and  segpermisoxgpo.lejecucion = 1 "+
          "join segprograma on segpermisoxgpo.icvesistema = segprograma.icvesistema "+
          "and  segpermisoxgpo.icveprograma = segprograma.icveprograma  "+
          "and  segprograma.lbloqueado = 0 "+
          "where segusuario.icveusuario = ? "+
          "and   segusuario.lbloqueado = 0 "+
          "order by segprograma.icveprograma, segpermisoxgpo.lactualizacion ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1, iCveSistema);
      lPStmt.setInt(2, iCveUsuario);
      lRSet = lPStmt.executeQuery();

      while (lRSet.next()) {
         vPermisos.put(lRSet.getString(1), lRSet.getString(2) + "|" + lRSet.getInt(3));
      }
    }
    catch (Exception ex) {
      System.out.println("permisosUsuario");
      ex.printStackTrace();
    }
    finally {
      try {
        if (lRSet != null) {
          lRSet.close();
        }
        if (lPStmt != null) {
          lPStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
        dbConn.closeConnection();
      }
      catch (Exception ex2) {
        System.out.println("permisosUsuario.Close");
      }
      return vPermisos;
    }
  }

  public HashMap getHmPUsuario() {
    return hmPUsuario;
  }
  public Vector getVcMenuUsuario() {
    return vcMenuUsuario;
  }

public TVUsuario accesoUsuario(String cUsuario, String cContrasenia, String cNumModulo) {
    vParametros = new TParametro(cNumModulo);
    DbConnection dbConn = null;
    Connection conn = null;
    PreparedStatement lPStmt = null;
    ResultSet lRSet = null;
    TVUsuario vUsuario = null;
    try {
      dataSrcName = vParametros.getPropEspecifica("ConDB");
      dbConn = new DbConnection(dataSrcName);
      conn = dbConn.getConnection();
      conn.setTransactionIsolation(2);

      String lSQL =
          "select segusuario.*, grlpais.cnombre as cdscpais, grlentidadfed.cnombre as cdscentidadfed, grlmunicipio.cnombre as cdscmunicipio "+
          "from segusuario "+
          "join grlpais on segusuario.icvepais = grlpais.icvepais "+
          "join grlentidadfed on segusuario.icvepais = grlentidadfed.icvepais "+
          "and segusuario.icveentidadfed = grlentidadfed.icveentidadfed "+
          "join grlmunicipio on segusuario.icvepais = grlmunicipio.icvepais "+
          "and segusuario.icveentidadfed = grlmunicipio.icveentidadfed "+
          "and segusuario.icvemunicipio = grlmunicipio.icvemunicipio "+
          "where cUsuario = ? "+
          "and   cPassword = ? "+
          "and   lBloqueado = 0";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setString(1, cUsuario);
      lPStmt.setString(2, cContrasenia);
      lRSet = lPStmt.executeQuery();

      while (lRSet.next()) {
        vUsuario = new TVUsuario();
        vUsuario.setICveusuario(lRSet.getInt("iCveUsuario"));
        vUsuario.setDtRegistro(lRSet.getDate("dtRegistro"));
        vUsuario.setCUsuario(lRSet.getString("cUsuario"));
        vUsuario.setCPassword(lRSet.getString("cPassword"));
        vUsuario.setCNombre(lRSet.getString("cNombre"));
        vUsuario.setCApPaterno(lRSet.getString("cApPaterno"));
        vUsuario.setCApMaterno(lRSet.getString("cApMaterno"));
        vUsuario.setCCalle(lRSet.getString("cCalle"));
        vUsuario.setCColonia(lRSet.getString("cColonia"));
        vUsuario.setICvePais(lRSet.getInt("iCvePais"));
        vUsuario.setICveEntidadFed(lRSet.getInt("iCveEntidadFed"));
        vUsuario.setICveMunicipio(lRSet.getInt("iCveMunicipio"));
        vUsuario.setICodigoPostal(lRSet.getInt("iCodigoPostal"));
        vUsuario.setCTelefono(lRSet.getString("cTelefono"));
        vUsuario.setICveUnidadOrg(lRSet.getInt("iCveUnidadOrg"));
        vUsuario.setLBloqueado(lRSet.getInt("lBloqueado"));
        vUsuario.setCDscPais(lRSet.getString("cdscpais"));
        vUsuario.setCDscEntidadFed(lRSet.getString("cdscentidadfed"));
        vUsuario.setCDscMunicipio(lRSet.getString("cdscmunicipio"));
        vUsuario.setID(""+new java.util.Date().getTime());
      }
    }
    catch (Exception ex) {
      System.out.println("accesoUsuario");
      ex.printStackTrace();
    }
    finally {
      try {
        if (lRSet != null) {
          lRSet.close();
        }
        if (lPStmt != null) {
          lPStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
        dbConn.closeConnection();
      }
      catch (Exception ex2) {
        System.out.println("accesoUsuario.Close");
      }
      return vUsuario;
    }
  }

public boolean cambioContrasenia(String cUsuario, String cNvaContrasenia, String cNumModulo) {
    vParametros = new TParametro(cNumModulo);
    DbConnection dbConn = null;
    Connection conn = null;
    PreparedStatement lPStmt = null;
    ResultSet lRSet = null;
    boolean lSuccess = true;

    try {
      dataSrcName = vParametros.getPropEspecifica("ConDB");
      dbConn = new DbConnection(dataSrcName);
      conn = dbConn.getConnection();
      conn.setTransactionIsolation(2);

      String lSQL =
          "update segusuario "+
          "set cPassword = '"+cNvaContrasenia+"'"+
          "where cUsuario = ? ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setString(1, cUsuario);
      lPStmt.executeUpdate();
    }
    catch (Exception ex) {
      lSuccess = false;
      System.out.println("cambioContrasenia");
      ex.printStackTrace();
    }
    finally {
      try {
        if (lRSet != null) {
          lRSet.close();
        }
        if (lPStmt != null) {
          lPStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
        dbConn.closeConnection();
      }
      catch (Exception ex2) {
        System.out.println("accesoUsuario.Close");
      }
      return lSuccess;
    }
  }


}

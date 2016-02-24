package com.micper.seguridad.dao;

//Java imports
import java.sql.*;
import java.util.*;

import com.micper.seguridad.vo.*;
import com.micper.ingsw.*;
import com.micper.sql.*;

public class TDMenu {
  private static Vector vSystemMenu = new Vector();
  private static String dataSrcName = "";

  private static TParametro vParametros;

  public TDMenu() {
  }

  public static synchronized void loadMenu(String cNumModulo) {
    try {
      vSystemMenu = new Vector();
      menuToHM(0, Integer.parseInt(cNumModulo,10), 0);
    }
    catch (Exception e) {
      System.out.print("loadMenu");
    }
  }

private static void menuToHM(int iOpcPadre,
                               int iCveSistema,
                               int iNivel) {
    DbConnection dbConn = null;
    Connection conn = null;
    PreparedStatement lPStmt = null;
    ResultSet lRSet = null;
    TVMenu vMenu;
    Vector vActual = new Vector();
    try {
      dataSrcName = vParametros.getPropEspecifica("ConDB");
      dbConn = new DbConnection(dataSrcName);
      conn = dbConn.getConnection();
      conn.setTransactionIsolation(2);

      String lSQL = "select " +
          "ICVESISTEMA, " +
          "IORDEN, " +
          "CDSCMENU, " +
          "CREFERENCIA, " +
          "CNOMPAGINA, " +
          "IOPCPADRE, " +
          "LBLOQUEADO " +
          "from SEGMENU " +
          "where icvesistema = ? " +
          "and iopcpadre = ? " +
          "and lbloqueado = 0 " +
          "order by iorden ";
      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1, iCveSistema);
      lPStmt.setInt(2, iOpcPadre);
      lRSet = lPStmt.executeQuery();

      while (lRSet.next()) {
        vMenu = new TVMenu();
        vMenu.setICveSistema(lRSet.getInt(1));
        vMenu.setIOrden(lRSet.getInt(2));
        vMenu.setCDscMenu(lRSet.getString(3));
        vMenu.setCReferencia(lRSet.getString(4));
        vMenu.setCNomPagina(lRSet.getString(5));
        vMenu.setIOpcPadre(lRSet.getInt(6));
        vMenu.setLBloqueado(lRSet.getInt(7));
        vMenu.setINivel(iNivel);
        vActual.add(vMenu);
      }

      try {
        if (lPStmt != null) {
          lPStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
        if (lRSet != null) {
          lRSet.close();
        }
        dbConn.closeConnection();
      }
      catch (Exception ex2) {
        System.out.print("menuToHM.Close:" + iNivel);
      }

      for (int i = 0; i < vActual.size(); i++) {
        vMenu = (TVMenu) vActual.get(i);
        vSystemMenu.add(vMenu);
        menuToHM(vMenu.getIOrden(), iCveSistema, iNivel + 1);
      }
    }
    catch (Exception ex) {
      System.out.print("menuToHM:" + iOpcPadre);
    }
    finally {
    }
  }

  public static Vector getVSystemMenu(String cNumModulo) {
    vParametros = new TParametro(cNumModulo);
    if (vSystemMenu.size() == 0) {
      loadMenu(cNumModulo);
    }
    return vSystemMenu;
  }

private static void permisosUsuario(int iCveUsuario, int iCveSistema) {
    DbConnection dbConn = null;
    Connection conn = null;
    PreparedStatement lPStmt = null;
    ResultSet lRSet = null;
    TVMenu vProgramas;
    Vector vPermisos = new Vector();

    try {
      dataSrcName = vParametros.getPropEspecifica("ConDB");
      dbConn = new DbConnection(dataSrcName);
      conn = dbConn.getConnection();
      conn.setTransactionIsolation(2);

      String lSQL =
          "select segprograma.cNombre, segpermisoxgpo.lactualizacion " +
          "from segusuario  " +
          "join seggpoxusr on segusuario.icveusuario = seggpoxusr.icveusuario " +
          "and  seggpoxusr.icvesistema = ? " +
          "join seggrupo on seggpoxusr.icvesistema = seggrupo.icvesistema " +
          "and  seggpoxusr.icvegrupo = seggrupo.icvegrupo  " +
          "and  seggrupo.lbloqueado = 0 " +
          "join segpermisoxgpo on seggpoxusr.icvesistema = segpermisoxgpo.icvesistema " +
          "and  seggpoxusr.icvegrupo = segpermisoxgpo.icvegrupo " +
          "and  segpermisoxgpo.lejecucion = 1 " +
          "join segprograma on segpermisoxgpo.icvesistema = segprograma.icvesistema " +
          "and  segpermisoxgpo.icveprograma = segprograma.icveprograma  " +
          "and  segprograma.lbloqueado = 0 " +
          "where segusuario.icveusuario = ? " +
          "and   segusuario.lbloqueado = 0 " +
          "order by icveprograma  ";

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1, iCveSistema);
      lPStmt.setInt(2, iCveUsuario);
      lRSet = lPStmt.executeQuery();

      while (lRSet.next()) {
        vProgramas = new TVMenu();
        vProgramas.setCNomPagina(lRSet.getString(1));
        vProgramas.setLActualizacion(lRSet.getInt(2));
        vPermisos.add(vProgramas);
      }
    }
    catch (Exception ex) {
      System.out.print("permisosUsuario:");
    }
    finally {
      try {
        if (lRSet != null) {
          lRSet.close();
        }
      }
      catch (Exception ex2) {
        System.out.print("permisosUsuario.Close");
      }
    }
  }

}

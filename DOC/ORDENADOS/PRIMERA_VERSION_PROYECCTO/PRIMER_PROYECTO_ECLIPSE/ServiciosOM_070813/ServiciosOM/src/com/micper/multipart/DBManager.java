/*
 * DBManager.java
 *
 * Created on 24 de noviembre de 2003, 02:46 PM
 */

package com.micper.multipart;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import java.util.*;
import java.sql.*;
import com.micper.sql.*;

/**
 *
 * @author  evalladares
 */
public class DBManager {
//    Connection DBConn;

  private TParametro vParametros = null;
  private String dataSourceName = null;
  private DbConnection DBConn;
  private Connection conn;
  /** Creates a new instance of DBManager */
    public DBManager() {
//        Properties props = new Properties();
//        InputStream fileProps = this.getClass().getClassLoader().getResourceAsStream("DBProperties.properties");
        try{
           // props.load(fileProps);
           // Class.forName(props.getProperty("DBDriver")).newInstance();
           // DBConn = DriverManager.getConnection(props.getProperty("DBURL"), props.getProperty("DBUser"), props.getProperty("DBPassword"));
           vParametros = new TParametro("03");
           dataSourceName = vParametros.getPropEspecifica("ConDBModulo");

            DBConn = new DbConnection(dataSourceName);
            conn = DBConn.getConnection();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveFile(byte[] file, String cExpediente, int iCveClasifExped, int iNumDocto, int iNumHojas,  java.sql.Date dtArchivo, java.sql.Date dtGlosa, int iNoLegajo, String cDscDocumento,int iFolioSalida,int iNumAnexo,int iCveClasTrans,String cRutaArchDig) throws DBException,DAOException{
      PreparedStatement pstmt = null;
        if (null == conn)
            throw new DBException("NO EXISTE CONEXION CON LA BASE DE DATOS");
        StringBuffer query = new StringBuffer("INSERT INTO RTVDOCTOSEXP ");
        query.append("(CEXPEDIENTE,ICVECLASIFEXPED,INUMDOCTO,INUMHOJAS,DTARCHIVO,DTGLOSA,INOLEGAJO,CDSCDOCUMENTO,IFOLIOSALIDA,INUMANEXO,ICVECLASTRANS,CRUTAARCHDIG,BLMGDOCTOSEXP)");
        query.append("VALUES");
        query.append("(?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?, ");
        query.append("?)");
        String instruccion = query.toString();
        System.out.println("DBMANAGER, SAVEFILE, INSTRUCCION: "+instruccion);

        try{
            pstmt = conn.prepareStatement(instruccion);
//            PreparedStatement pstmt = DBConn.prepareStatement(instruccion);
            System.out.print("iNumDocto-"+iNumDocto);
            pstmt.setString(1, cExpediente);
            pstmt.setInt(2, iCveClasifExped);
            pstmt.setInt(3, iNumDocto);
            pstmt.setInt(4, iNumHojas);
            pstmt.setDate(5, dtArchivo);
            pstmt.setDate(6, dtGlosa);
            pstmt.setInt(7, iNoLegajo);
            pstmt.setString(8, cDscDocumento);
            pstmt.setInt(9, iFolioSalida);
            pstmt.setInt(10, iNumAnexo);
            pstmt.setInt(11, iCveClasTrans);
            pstmt.setString(12, cRutaArchDig);
            pstmt.setBytes(13, file);

            pstmt.execute();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            throw new DBException(sqle.getMessage());
        }catch(Exception ex){
          ex.printStackTrace();
        }
    }

    public void updateFile(byte[] file, String cExpediente, int iCveClasifExped, int iNumDocto, int iNumHojas,  java.sql.Date dtArchivo, java.sql.Date dtGlosa, int iNoLegajo, String cDscDocumento,int iFolioSalida,int iNumAnexo,int iCveClasTrans,String cRutaArchDig) throws DBException,DAOException{
        if (null == conn)
            throw new DBException("NO EXISTE CONEXION CON LA BASE DE DATOS");
        StringBuffer query = new StringBuffer("UPDATE RTVDOCTOSEXP ");
        System.out.print("cExpediente--"+cExpediente);
        System.out.print("iCveClasifExped--"+iCveClasifExped);
        System.out.print("iNumDocto--"+iNumDocto);
        System.out.print("iNumHojas--"+iNumHojas);
        System.out.print("cRutaArchDig--"+cRutaArchDig);

        query.append(" SET INUMHOJAS = ?,");
        query.append(" DTARCHIVO = ?,");
        query.append(" DTGLOSA = ?,");
        query.append(" INOLEGAJO = ?,");
        query.append(" CDSCDOCUMENTO = ?,");
        query.append(" IFOLIOSALIDA = ?,");
        query.append(" INUMANEXO = ?,");
        query.append(" ICVECLASTRANS = ?,");
        query.append(" CRUTAARCHDIG = ?,");
        query.append(" BLMGDOCTOSEXP = ? ");
        query.append(" WHERE CEXPEDIENTE = ? ");
        query.append(" AND ICVECLASIFEXPED = ? ");
        query.append(" AND INUMDOCTO = ? ");
        String instruccion = query.toString();
        System.out.println("DBMANAGER, UPDATEFILE, INSTRUCCION: "+instruccion);
        try{
            PreparedStatement pstmt = conn.prepareStatement(instruccion);

            pstmt.setInt(1, iNumHojas);
            pstmt.setDate(2, dtArchivo);
            pstmt.setDate(3, dtGlosa);
            pstmt.setInt(4, iNoLegajo);
            pstmt.setString(5, cDscDocumento);
            pstmt.setInt(6, iFolioSalida);
            pstmt.setInt(7, iNumAnexo);
            pstmt.setInt(8, iCveClasTrans);
            pstmt.setString(9, cRutaArchDig);
            pstmt.setBytes(10, file);
            pstmt.setString(11, cExpediente);
            pstmt.setInt(12, iCveClasifExped);
            pstmt.setInt(13, iNumDocto);

            pstmt.execute();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            throw new DBException(sqle.getMessage());
        }catch(Exception ex){
          ex.printStackTrace();
        }
    }
    public int updateFile2(byte[] file, int iAnioBoleta, int iNumBoleta, int iCveEstacion ) throws DBException,DAOException{
        System.out.println("updateFile2");
//    if (DBConn == null)
//        throw new DBException("NO EXISTE CONEXION CON LA BASE DE DATOS");
      int iReturn = 0;
      PreparedStatement pstmt = null;
      StringBuffer query = new StringBuffer("UPDATE RTVFRMAnexo ");
      query.append(" SET blArchivo = ?");
      query.append(" WHERE iAnioBoleta = ? ");
      query.append(" AND iNumBoleta = ? ");
      query.append(" AND iCveEstacion = ? ");
      String instruccion = query.toString();
      System.out.println("DBMANAGER, UPDATEFILE, INSTRUCCION: "+instruccion);
      try{
        pstmt = conn.prepareStatement(instruccion);

        pstmt.setBytes(1, file);
        pstmt.setInt(2, iAnioBoleta);
        pstmt.setInt(3, iNumBoleta);
        pstmt.setInt(4, iCveEstacion);
        iReturn = pstmt.executeUpdate();
      }
      catch(SQLException sqle){
        sqle.printStackTrace();
        throw new DBException(sqle.getMessage());
      }
      catch(Exception ex){
        ex.printStackTrace();
      }
      finally{
        if(pstmt != null ) pstmt = null;
      }
      return iReturn;
    }
    public int saveFile2(byte[] file, int iAnioBoleta, int iNumBoleta, int iCveEstacion ) throws DBException,DAOException{
        System.out.println("saveFile2");
      if (DBConn == null)
        System.out.println("DBConn == null");
//        throw new DBException("NO EXISTE CONEXION CON LA BASE DE DATOS");
      int iReturn = 0;
      PreparedStatement pstmt = null;
      StringBuffer query = new StringBuffer("INSERT INTO RTVFRMAnexo ");
      query.append("(iAnioBoleta, iNumBoleta, iCveEstacion, blArchivo )");
      query.append("VALUES( ?, ?, ?, ? )");
      String instruccion = query.toString();
      System.out.println("DBMANAGER, SAVEFILE, INSTRUCCION: "+instruccion);

        try{
          pstmt = conn.prepareStatement(instruccion);
          pstmt.setInt(1, iAnioBoleta);
          pstmt.setInt(2, iNumBoleta);
          pstmt.setInt(3, iCveEstacion);
          pstmt.setBytes(4, file);

          iReturn = pstmt.executeUpdate();
        }
        catch(SQLException sqle){
          sqle.printStackTrace();
          throw new DBException(sqle.getMessage());
        }
        catch(Exception ex){
          ex.printStackTrace();
        }
        finally{
          if(pstmt != null ) pstmt = null;
        }
        return iReturn;
      }



	public List getFilesName() throws DBException{
        if (null == DBConn)
            throw new DBException("No existe conexion con base de datos alguna");

        StringBuffer query = new StringBuffer("SELECT NAME from RESEARCH1");
        String instruccion = query.toString();
        System.out.println("DBManager, getFile, instruccion: "+instruccion);
        List nombres = new ArrayList();
        try{
            Statement stmt = conn.createStatement();
            ResultSet names = stmt.executeQuery(instruccion);
            while(names.next()){
                nombres.add(names.getString(1));
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
            throw new DBException(sqle.getMessage());
        }
        return nombres;
    }

    public byte[] getFile(String cExpediente,int iCveClasifExped,int iNumDocto) throws DBException{
        if (null == conn)
            throw new DBException("NO EXISTE CONEXION CON LA BASE DE DATOS!");
        StringBuffer query = new StringBuffer("SELECT BLMGDOCTOSEXP FROM RTVDOCTOSEXP ");
        query.append(" WHERE CEXPEDIENTE='"+cExpediente+"'");
        query.append("   AND ICVECLASIFEXPED="+iCveClasifExped);
        query.append("   AND INUMDOCTO="+iNumDocto);
        String instruccion = query.toString();
        System.out.println("DBMANAGER, GETFILE, INSTRUCCION: "+instruccion);
        byte[] archivo = null;
        try{
          Statement stmt = conn.createStatement();
          ResultSet file = stmt.executeQuery(instruccion);
          if(file.next()){
              archivo = file.getBytes(1);
          }
        }catch(SQLException sqle){
            sqle.printStackTrace();
            throw new DBException(sqle.getMessage());
        }
        return archivo;
    }
    public byte[] getFile(int iAnioBoleta, int iNumBoleta, int iCveEstacion) throws DBException{
        if ( conn == null)
            throw new DBException("NO EXISTE CONEXION CON LA BASE DE DATOS!");
        StringBuffer query = new StringBuffer("SELECT blArchivo FROM RTVFRMAnexo ");
        query.append(" WHERE iAnioBoleta = "+iAnioBoleta);
        query.append(" AND iNumBoleta = "+iNumBoleta);
        query.append(" AND iCveEstacion = "+iCveEstacion);
        String instruccion = query.toString();
        System.out.println("DBMANAGER, GETFILE, INSTRUCCION: "+instruccion);
        byte[] archivo = null;
        try{
          Statement stmt = conn.createStatement();
          ResultSet file = stmt.executeQuery(instruccion);
          if(file.next()){
              archivo = file.getBytes(1);
          }
        }catch(SQLException sqle){
            sqle.printStackTrace();
            throw new DBException(sqle.getMessage());
        }
        return archivo;
    }

}

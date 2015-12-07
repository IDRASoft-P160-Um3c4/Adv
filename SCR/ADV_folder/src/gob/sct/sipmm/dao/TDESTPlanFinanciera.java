package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDESTPlanFinanciera.java</p>
 * <p>Description: DAO de la entidad ESTPlanFinanciera</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
public class TDESTPlanFinanciera extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private Vector vArbolArmado = new Vector();
  private int iNivel = 1;
  private String cLineasNivel = "-----";
  private String cNivel = "";
  private int iCaractAQuitar = 5;

  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepción de tipo DAO
   * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
   */
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


 public Vector regresaArbol(TVDinRep vDatos) throws DAOException{
   Vector vArbol = new Vector(), vCvesPadres = new Vector();
   Vector vTemp = new Vector();
   TVDinRep vPrimerNivel = null;
   StringBuffer cSQL =  new StringBuffer();

   cSQL.append("SELECT pf.ICVECONCEPTOCONTABLE,pf.ICVECONCEPTOCONTABLEPADRE, cc.CDSCCONCEPTOCONTABLE, cc.LVIGENTE,"+iNivel+" AS iNivel, '' AS cLineas, ");  //5
   cSQL.append("       pf.IEJERCICIOSEGUIMIENTO,pf.INUMMOVTOSEGTO,pf.DPROGRAMADOACUMULADO, "); //8
   cSQL.append("       pf.DREALACUMULADO,pf.DPROGRAMADOMES,pf.DREALMES, pf.DESTIMADOCIERREANUAL, '' AS cVacio ,CC.ICVETIPOMOVCONTABLE "); //13
   cSQL.append("FROM  ESTPLANFINANCIERA pf ");
   cSQL.append("JOIN ESTCONCEPTOCONTABLE cc ON cc.ICVECONCEPTOCONTABLE = pf.ICVECONCEPTOCONTABLE ");
   cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = pf.ICVECONCEPTOCONTABLEPADRE ");
   cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = 0 ");
   cSQL.append("                    AND cc.LVIGENTE = 1 ");
   cSQL.append("WHERE pf.IEJERCICIOSEGUIMIENTO ="+ vDatos.getInt("iEjercicioSeguimiento") +" and pf.INUMMOVTOSEGTO ="+ vDatos.getInt("iNumMovToSegto") );

   try{
     vArbol = this.FindByGeneric("",cSQL.toString(),dataSourceName);

     if(vArbol.size()>0){
       for(int cont=0;cont<vArbol.size();cont++){
         iNivel = 1;
         cNivel = "";
         vPrimerNivel = (TVDinRep) vArbol.get(cont);
         if(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE")==0){
           vArbolArmado.addElement(vArbol.get(cont));
         }
         else if(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE")>0){
           vArbolArmado.addElement(vArbol.get(cont));
           this.agregaHijos(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE"),vDatos.getInt("iEjercicioSeguimiento"),vDatos.getInt("iNumMovToSegto"),"");
         }

       }

     }

   }catch(Exception e){
      cError = e.toString();
   }finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vArbolArmado;
   }
 }
 public Vector regresaArbol2(TVDinRep vDatos) throws DAOException{
   Vector vArbol = new Vector(), vCvesPadres = new Vector();
   Vector vTemp = new Vector();
   TVDinRep vPrimerNivel = null;
   StringBuffer cSQL =  new StringBuffer();
   StringBuffer cSQL1 =  new StringBuffer();

   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DPROGRAMADOMES) AS DPROGACUMULADOANUAL ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.IPERIODOREPORTADO <= " + vDatos.getInt("iCvePeriodo"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("), ");
   cSQL1.append("( ");
   cSQL1.append("SELECT ");
   cSQL1.append("       SUM(EPF.DPROGRAMADOMES) AS DPROGRAMADOCIERREANUAL ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append(") ");


   cSQL.append("SELECT pf.ICVECONCEPTOCONTABLE,pf.ICVECONCEPTOCONTABLEPADRE, cc.CDSCCONCEPTOCONTABLE, cc.LVIGENTE,"+iNivel+" AS iNivel, '' AS cLineas, ");  //5
   cSQL.append("       pf.IEJERCICIOSEGUIMIENTO,pf.INUMMOVTOSEGTO,pf.DPROGRAMADOACUMULADO, "); //8
   cSQL.append("       pf.DREALACUMULADO,pf.DPROGRAMADOMES,pf.DREALMES, pf.DESTIMADOCIERREANUAL, '' AS cVacio ,CC.ICVETIPOMOVCONTABLE,pf.DPROGRAMADOMODIFICADO, "); //15
   cSQL.append(cSQL1.toString());
   cSQL.append("FROM  ESTPLANFINANCIERA pf ");
   cSQL.append("JOIN ESTCONCEPTOCONTABLE cc ON cc.ICVECONCEPTOCONTABLE = pf.ICVECONCEPTOCONTABLE ");
   cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = pf.ICVECONCEPTOCONTABLEPADRE ");
   cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = 0 ");
   cSQL.append("                    AND cc.LVIGENTE = 1 ");
   cSQL.append("WHERE pf.IEJERCICIOSEGUIMIENTO ="+ vDatos.getInt("iEjercicioSeguimiento") +" and pf.INUMMOVTOSEGTO ="+ vDatos.getInt("iNumMovToSegto") );

   try{
     vArbol = this.FindByGeneric("",cSQL.toString(),dataSourceName);

     if(vArbol.size()>0){
       for(int cont=0;cont<vArbol.size();cont++){
         iNivel = 1;
         cNivel = "";
         vPrimerNivel = (TVDinRep) vArbol.get(cont);
         if(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE")==0){
           vArbolArmado.addElement(vArbol.get(cont));
         }
         else if(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE")>0){
           vArbolArmado.addElement(vArbol.get(cont));
           this.agregaHijos(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE"),vDatos.getInt("iEjercicioSeguimiento"),vDatos.getInt("iNumMovToSegto"),cSQL1.toString());
         }

       }

     }
/*     for(int i = 0; i < vArbolArmado.size();i++){
       TVDinRep vArbolTemp = (TVDinRep) vArbolArmado.get(i);
       String cProgEstimadoAnual =
           "SELECT SUM(EPF.DPROGRAMADOMES) AS dProgramadoCierreAnual FROM ESTSEGTOFORMULARIO ESF " +
           "JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO " +
           "AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO " +
           "WHERE ESF.IEJERCICIOREPORTADO = " + vDatos.getInt("iEjercicioReportado") +
           "  AND ESF.ICVETITULO = " + vDatos.getInt("iCveTitulo") +
           "  AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular") +
           "  AND ESF.ICVEFORMULARIO = 19 " +
           "  AND ICVECONCEPTOCONTABLE = " + vArbolTemp.getInt("ICVECONCEPTOCONTABLE");
       String cProgAcumulado =
           "SELECT SUM(EPF.DPROGRAMADOMES) AS dProgAcumuladoAnual FROM ESTSEGTOFORMULARIO ESF " +
           "JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO " +
           "AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO " +
           "WHERE ESF.IEJERCICIOREPORTADO = " + vDatos.getInt("iEjercicioReportado") +
           "  AND ESF.ICVETITULO = " + vDatos.getInt("iCveTitulo") +
           "  AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular") +
           "  AND ESF.ICVEFORMULARIO = 19 " +
           "  AND ICVECONCEPTOCONTABLE = " + vArbolTemp.getInt("ICVECONCEPTOCONTABLE") +
           "  AND ESF.IPERIODOREPORTADO <= " + vDatos.getInt("iPeriodoReportado");

       Vector vcProgAcumulado = this.findByCustom("",cProgAcumulado);
       if(vcProgAcumulado.size()>0){
         TVDinRep vProgAcumulado = (TVDinRep) vcProgAcumulado.get(0);
         vArbolTemp.put("dProgAcumuladoAnual",vProgAcumulado.getDouble("dProgAcumuladoAnual"));

       }

       Vector vcProgEstimadoAnual = this.findByCustom("",cProgEstimadoAnual);
       if(vcProgEstimadoAnual.size() > 0){
         TVDinRep vEstimadoAnual = (TVDinRep) vcProgEstimadoAnual.get(0);
         vArbolTemp.put("dProgramadoCierreAnual",vEstimadoAnual.getDouble("dProgramadoCierreAnual"));
       }
     }
*/

   }catch(Exception e){
      cError = e.toString();
   }finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vArbolArmado;
   }
 }

 public Vector regresaArbol3(TVDinRep vDatos) throws DAOException{
   Vector vArbol = new Vector(), vCvesPadres = new Vector();
   Vector vTemp = new Vector();
   TVDinRep vPrimerNivel = null;
   StringBuffer cSQL =  new StringBuffer();
   StringBuffer cSQL1 =  new StringBuffer();


   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DPROGRAMADOMODIFICADO) AS DPROGACUMULADOANUAL ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.IPERIODOREPORTADO <= " + vDatos.getInt("iCvePeriodo"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("), ");
   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DPROGRAMADOMODIFICADO) AS dProgAnioParcial ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  LEFT JOIN  ESTSEGTOFORMULARIO ESF2 ");
   cSQL1.append("     ON ESF.IEJERCICIOREPORTADO = ESF2.IEJERCICIOREPORTADO ");
   cSQL1.append("    AND ESF.IPERIODOREPORTADO = ESF2.IPERIODOREPORTADO ");
   cSQL1.append("    AND ESF.ICVETITULO = ESF2.ICVETITULO ");
   cSQL1.append("    AND ESF.ICVETITULAR = ESF2.ICVETITULAR ");
   cSQL1.append("    AND ESF2.ICVEFORMULARIO = 7 ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("    AND esf2.DTENVIODGFYDP is null ");
   cSQL1.append("), ");
   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DREALMES) AS dRealAnioParcial ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  LEFT JOIN  ESTSEGTOFORMULARIO ESF2 ");
   cSQL1.append("     ON ESF.IEJERCICIOREPORTADO = ESF2.IEJERCICIOREPORTADO ");
   cSQL1.append("    AND ESF.IPERIODOREPORTADO = ESF2.IPERIODOREPORTADO ");
   cSQL1.append("    AND ESF.ICVETITULO = ESF2.ICVETITULO ");
   cSQL1.append("    AND ESF.ICVETITULAR = ESF2.ICVETITULAR ");
   cSQL1.append("    AND ESF2.ICVEFORMULARIO = 7 ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("    AND ESF2.DTENVIODGFYDP is not null ");
   cSQL1.append(") ");


   cSQL.append("SELECT pf.ICVECONCEPTOCONTABLE,pf.ICVECONCEPTOCONTABLEPADRE, cc.CDSCCONCEPTOCONTABLE, cc.LVIGENTE,"+iNivel+" AS iNivel, '' AS cLineas, ");  //5
   cSQL.append("       pf.IEJERCICIOSEGUIMIENTO,pf.INUMMOVTOSEGTO,pf.DPROGRAMADOACUMULADO, "); //8
   cSQL.append("       pf.DREALACUMULADO,pf.DPROGRAMADOMES,pf.DREALMES, pf.DESTIMADOCIERREANUAL, '' AS cVacio ,CC.ICVETIPOMOVCONTABLE,pf.DPROGRAMADOMODIFICADO, "); //15
   cSQL.append(cSQL1.toString());
   cSQL.append("FROM  ESTPLANFINANCIERA pf ");
   cSQL.append("JOIN ESTCONCEPTOCONTABLE cc ON cc.ICVECONCEPTOCONTABLE = pf.ICVECONCEPTOCONTABLE ");
   cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = pf.ICVECONCEPTOCONTABLEPADRE ");
   cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = 0 ");
   cSQL.append("                    AND cc.LVIGENTE = 1 ");
   cSQL.append("WHERE pf.IEJERCICIOSEGUIMIENTO ="+ vDatos.getInt("iEjercicioSeguimiento") +" and pf.INUMMOVTOSEGTO ="+ vDatos.getInt("iNumMovToSegto") );




   try{
     vArbol = this.FindByGeneric("",cSQL.toString(),dataSourceName);

     if(vArbol.size()>0){
       for(int cont=0;cont<vArbol.size();cont++){
         iNivel = 1;
         cNivel = "";
         vPrimerNivel = (TVDinRep) vArbol.get(cont);
         if(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE")==0){
           vArbolArmado.addElement(vArbol.get(cont));
         }
         else if(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE")>0){
           vArbolArmado.addElement(vArbol.get(cont));
           this.agregaHijos(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE"),vDatos.getInt("iEjercicioSeguimiento"),vDatos.getInt("iNumMovToSegto"),cSQL1.toString());
         }

       }

     }/*
     for(int i = 0; i < vArbolArmado.size();i++){
       TVDinRep vArbolTemp = (TVDinRep) vArbolArmado.get(i);
       String cProgEstimadoAnual =
           "SELECT SUM(EPF.DPROGRAMADOMODIFICADO) AS dProgramadoCierreAnual FROM ESTSEGTOFORMULARIO ESF " +
           "JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO " +
           "AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO " +
           "WHERE ESF.IEJERCICIOREPORTADO = " + vDatos.getInt("iEjercicioReportado") +
           "  AND ESF.ICVETITULO = " + vDatos.getInt("iCveTitulo") +
           "  AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular") +
           "  AND ESF.ICVEFORMULARIO = 19 " +
           "  AND ICVECONCEPTOCONTABLE = " + vArbolTemp.getInt("ICVECONCEPTOCONTABLE");
       String cProgAcumulado =
           "SELECT SUM(EPF.DPROGRAMADOMODIFICADO) AS dProgAcumuladoAnual FROM ESTSEGTOFORMULARIO ESF " +
           "JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO " +
           "AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO " +
           "WHERE ESF.IEJERCICIOREPORTADO = " + vDatos.getInt("iEjercicioReportado") +
           "  AND ESF.IPERIODOREPORTADO <= " + vDatos.getInt("iPeriodoReportado")+
           "  AND ESF.ICVETITULO = " + vDatos.getInt("iCveTitulo") +
           "  AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular") +
           "  AND ESF.ICVEFORMULARIO = 19 " +
           "  AND ICVECONCEPTOCONTABLE = " + vArbolTemp.getInt("ICVECONCEPTOCONTABLE");

       Vector vcProgAcumulado = this.findByCustom ("",cProgAcumulado);
       if(vcProgAcumulado.size()>0){
         TVDinRep vProgAcumulado = (TVDinRep) vcProgAcumulado.get(0);
         vArbolTemp.put("dProgAcumuladoAnual",vProgAcumulado.getDouble("dProgAcumuladoAnual"));

       }

       Vector vcProgEstimadoAnual = this.findByCustom("",cProgEstimadoAnual);
       if(vcProgEstimadoAnual.size() > 0){
         TVDinRep vEstimadoAnual = (TVDinRep) vcProgEstimadoAnual.get(0);
         vArbolTemp.put("dProgramadoCierreAnual",vEstimadoAnual.getDouble("dProgramadoCierreAnual"));
       }
       vArbolTemp.put("dProgAcumuladoAnual",666);
       vArbolTemp.put("dProgramadoCierreAnual",666);
     }*/


   }catch(Exception e){
      cError = e.toString();
   }finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vArbolArmado;
   }
 }

 public Vector regresaArbol4(TVDinRep vDatos) throws DAOException{
   Vector vArbol = new Vector(), vCvesPadres = new Vector();
   Vector vTemp = new Vector();
   TVDinRep vPrimerNivel = null;
   StringBuffer cSQL =  new StringBuffer();
   StringBuffer cSQL1 =  new StringBuffer();


   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DPROGRAMADOMES) AS dProgModAlMes ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.IPERIODOREPORTADO <= " + vDatos.getInt("iCvePeriodo"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("), ");
   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DREALMES) AS dRealAlMes ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.IPERIODOREPORTADO <= " + vDatos.getInt("iCvePeriodo"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("), ");
   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DPROGRAMADOMES) AS dRealProgModAlAnio ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("), ");
   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DPROGRAMADOMODIFICADO) AS dProgAnioParcial ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  LEFT JOIN  ESTSEGTOFORMULARIO ESF2 ");
   cSQL1.append("     ON ESF.IEJERCICIOREPORTADO = ESF2.IEJERCICIOREPORTADO ");
   cSQL1.append("    AND ESF.IPERIODOREPORTADO = ESF2.IPERIODOREPORTADO ");
   cSQL1.append("    AND ESF.ICVETITULO = ESF2.ICVETITULO ");
   cSQL1.append("    AND ESF.ICVETITULAR = ESF2.ICVETITULAR ");
   cSQL1.append("    AND ESF2.ICVEFORMULARIO = 7 ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("    AND ESF2.IEJERCICIOSEGUIMIENTO is null ");
   cSQL1.append("), ");
   cSQL1.append("(SELECT ");
   cSQL1.append("       SUM(EPF.DREALMES) AS dRealAnioParcial ");
   cSQL1.append("FROM ESTSEGTOFORMULARIO ESF ");
   cSQL1.append("  JOIN ESTPLANFINANCIERA EPF ON ESF.IEJERCICIOSEGUIMIENTO = EPF.IEJERCICIOSEGUIMIENTO ");
   cSQL1.append("    AND ESF.INUMMOVTOSEGTO = EPF.INUMMOVTOSEGTO ");
   cSQL1.append("  LEFT JOIN  ESTSEGTOFORMULARIO ESF2 ");
   cSQL1.append("     ON ESF.IEJERCICIOREPORTADO = ESF2.IEJERCICIOREPORTADO ");
   cSQL1.append("    AND ESF.IPERIODOREPORTADO = ESF2.IPERIODOREPORTADO ");
   cSQL1.append("    AND ESF.ICVETITULO = ESF2.ICVETITULO ");
   cSQL1.append("    AND ESF.ICVETITULAR = ESF2.ICVETITULAR ");
   cSQL1.append("    AND ESF2.ICVEFORMULARIO = 7 ");
   cSQL1.append("  WHERE ESF.IEJERCICIOREPORTADO = "+vDatos.getInt("iEjercicioReportado"));
   cSQL1.append("    AND ESF.ICVETITULO = "+vDatos.getInt("iCveTitulo"));
   cSQL1.append("    AND ESF.ICVETITULAR = " + vDatos.getInt("iCveTitular"));
   cSQL1.append("    AND ESF.ICVEFORMULARIO = 19 ");
   cSQL1.append("    AND ICVECONCEPTOCONTABLE = PF.ICVECONCEPTOCONTABLE ");
   cSQL1.append("    AND ESF2.IEJERCICIOSEGUIMIENTO is not null ");
   cSQL1.append(") ");


   cSQL.append("SELECT pf.ICVECONCEPTOCONTABLE,pf.ICVECONCEPTOCONTABLEPADRE, cc.CDSCCONCEPTOCONTABLE, cc.LVIGENTE,"+iNivel+" AS iNivel, '' AS cLineas, ");  //5
   cSQL.append("       pf.IEJERCICIOSEGUIMIENTO,pf.INUMMOVTOSEGTO,pf.DPROGRAMADOMODIFICADO, "); //8
   cSQL.append("       pf.DREALACUMULADO,pf.DPROGRAMADOMES,pf.DREALMES, pf.DESTIMADOCIERREANUAL, '' AS cVacio ,CC.ICVETIPOMOVCONTABLE,pf.DPROGRAMADOMODIFICADO, "); //15
   cSQL.append(cSQL1.toString());
   cSQL.append("FROM  ESTPLANFINANCIERA pf ");
   cSQL.append("JOIN ESTCONCEPTOCONTABLE cc ON cc.ICVECONCEPTOCONTABLE = pf.ICVECONCEPTOCONTABLE ");
   cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = pf.ICVECONCEPTOCONTABLEPADRE ");
   cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = 0 ");
   cSQL.append("                    AND cc.LVIGENTE = 1 ");
   cSQL.append("WHERE pf.IEJERCICIOSEGUIMIENTO ="+ vDatos.getInt("iEjercicioSeguimiento") +" and pf.INUMMOVTOSEGTO ="+ vDatos.getInt("iNumMovToSegto") );




   try{
     vArbol = this.FindByGeneric("",cSQL.toString(),dataSourceName);

     if(vArbol.size()>0){
       for(int cont=0;cont<vArbol.size();cont++){
         iNivel = 1;
         cNivel = "";
         vPrimerNivel = (TVDinRep) vArbol.get(cont);
         if(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE")==0){
           vArbolArmado.addElement(vArbol.get(cont));
         }
         else if(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE")>0){
           vArbolArmado.addElement(vArbol.get(cont));
           this.agregaHijos(vPrimerNivel.getInt("ICVECONCEPTOCONTABLE"),vDatos.getInt("iEjercicioSeguimiento"),vDatos.getInt("iNumMovToSegto"),cSQL1.toString());
         }

       }

     }
   }catch(Exception e){
      cError = e.toString();
   }finally{
     if(!cError.equals(""))
       throw new DAOException(cError);
     return vArbolArmado;
   }
 }




 public boolean agregaHijos(int iCveCptoCont, int iEjerSeg, int iNumMov, String cSQL1) throws DAOException{
   Vector vHijos = new Vector();
   iNivel += 1;
   cNivel += cLineasNivel;
   StringBuffer cSQL =  new StringBuffer();
   try{

     cSQL.append("SELECT pf.ICVECONCEPTOCONTABLE,pf.ICVECONCEPTOCONTABLEPADRE, cc.CDSCCONCEPTOCONTABLE, cc.LVIGENTE,"+iNivel+" AS iNivel, '"+cNivel+"'  AS cLineas, ");
     cSQL.append("       pf.IEJERCICIOSEGUIMIENTO,pf.INUMMOVTOSEGTO,pf.DPROGRAMADOACUMULADO, ");
     cSQL.append("       pf.DREALACUMULADO,pf.DPROGRAMADOMES,pf.DREALMES, pf.DESTIMADOCIERREANUAL,'' as vasio,CC.ICVETIPOMOVCONTABLE,pf.DPROGRAMADOMODIFICADO ");
     if(!cSQL1.equals(""))cSQL.append(","+cSQL1);
     cSQL.append("FROM  ESTPLANFINANCIERA pf ");
     cSQL.append("JOIN ESTCONCEPTOCONTABLE cc ON cc.ICVECONCEPTOCONTABLE = pf.ICVECONCEPTOCONTABLE ");
     cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = pf.ICVECONCEPTOCONTABLEPADRE ");
     cSQL.append("                    AND cc.ICVECONCEPTOCONTABLEPADRE = "+iCveCptoCont);
     cSQL.append(" WHERE pf.IEJERCICIOSEGUIMIENTO = "+iEjerSeg+" and pf.INUMMOVTOSEGTO = "+iNumMov);
     cSQL.append(" ORDER BY CC.ICVECONCEPTOCONTABLE");


       vHijos = this.FindByGeneric("",cSQL.toString() ,dataSourceName);

       if(vHijos.size()>0){
         TVDinRep vDinMasHijos = new TVDinRep();

         for(int iContHijos=0;iContHijos<vHijos.size();iContHijos++){
           vDinMasHijos = (TVDinRep) vHijos.get(iContHijos);
           vArbolArmado.addElement(vHijos.get(iContHijos));

           this.agregaHijos(vDinMasHijos.getInt("ICVECONCEPTOCONTABLE"),iEjerSeg,iNumMov,cSQL1);
           cNivel = cNivel.substring(0,(cNivel.length()-iCaractAQuitar));
           iNivel -= 1;
         }
       }

   }catch(Exception e){
      cError = e.toString();
    }finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return true;
    }
 }


  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into ESTPlanFinanciera(iEjercicioSeguimiento,iNumMovtoSegto,iCveConceptoContable,iCveConceptoContablePadre,dProgramadoAcumulado,dRealAcumulado,dProgramadoMes,dRealMes,dEstimadoCierreAnual) values (?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iCveConceptoContable, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String lSQL =
          "insert into ESTPlanFinanciera(iEjercicioSeguimiento,iNumMovtoSegto,iCveConceptoContable,iCveConceptoContablePadre,dProgramadoAcumulado,dRealAcumulado,dProgramadoMes,dRealMes,dEstimadoCierreAnual) values (?,?,?,?,?,?,?,?,?)";

      //AGREGAR AL ULTIMO ...
      Vector vcData = findByCustom("","select MAX(iEjercicioSeguimiento) AS iEjercicioSeguimiento from ESTPlanFinanciera");
      if(vcData.size() > 0){
         TVDinRep vUltimo = (TVDinRep) vcData.get(0);
         vData.put("iEjercicioSeguimiento",vUltimo.getInt("iEjercicioSeguimiento") + 1);
      }else
         vData.put("iEjercicioSeguimiento",1);
      vData.addPK(vData.getString("iEjercicioSeguimiento"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt.setInt(3,vData.getInt("iCveConceptoContable"));
      lPStmt.setInt(4,vData.getInt("iCveConceptoContablePadre"));
      lPStmt.setFloat(5,vData.getFloat("dProgramadoAcumulado"));
      lPStmt.setFloat(6,vData.getFloat("dRealAcumulado"));
      lPStmt.setFloat(7,vData.getFloat("dProgramadoMes"));
      lPStmt.setFloat(8,vData.getFloat("dRealMes"));
      lPStmt.setFloat(9,vData.getFloat("dEstimadoCierreAnual"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("insert.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");
      return vData;
    }
  }
  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from ESTPlanFinanciera where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iCveConceptoContable = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iCveConceptoContable, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested)throws
    DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    String cMsg = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from ESTPlanFinanciera where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iCveConceptoContable = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicioSeguimiento"));
      lPStmt.setInt(2,vData.getInt("iNumMovtoSegto"));
      lPStmt.setInt(3,vData.getInt("iCveConceptoContable"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = ""+sqle.getErrorCode();
    } catch(Exception ex){
      warn("delete",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("delete.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("delete.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cMsg);

      return lSuccess;
    }
  }
  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update ESTPlanFinanciera set iCveConceptoContablePadre=?, dProgramadoAcumulado=?, dRealAcumulado=?, dProgramadoMes=?, dRealMes=?, dEstimadoCierreAnual=? where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iCveConceptoContable = ?  </b></p>
   * <p><b> Campos Llave: iEjercicioSeguimiento,iNumMovtoSegto,iCveConceptoContable, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep updateElectronico(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    String[] aCptoCont = vData.getString("iCveConceptoContable").split(",");
    String[] aCptoContPadre = vData.getString("iCveConceptoContablePadre").split(",");
    String[] aRealMes = vData.getString("dRealMes").split(",");
    TVDinRep vFormul = null;
    Vector vPlanAnt = new Vector();


    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }


      StringBuffer lSQL = new StringBuffer();
      int iPeriodo = Integer.parseInt(vData.getString("iPeriodoReportado"));

      for(int iCont=0;iCont<aCptoCont.length;iCont++){
        lSQL = new StringBuffer();
        lSQL.append("update ESTPlanFinanciera set ")
            .append("dRealMes=? ")
            .append("where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iCveConceptoContable = ? AND iCveConceptoContablePadre = ?  ");

        vData.addPK(vData.getString("iEjercicioSeguimiento"));
        vData.addPK(vData.getString("iNumMovtoSegto"));
        vData.addPK(vData.getString("iCveConceptoContable"));

        lPStmt = conn.prepareStatement(lSQL.toString());
        lPStmt.setDouble(1,Double.parseDouble(aRealMes[iCont]));
        lPStmt.setInt(2,vData.getInt("iEjercicioSeguimiento"));
        lPStmt.setInt(3,vData.getInt("iNumMovtoSegto"));
        lPStmt.setInt(4,Integer.parseInt(aCptoCont[iCont]));
        lPStmt.setInt(5,Integer.parseInt(aCptoContPadre[iCont]));
        lPStmt.executeUpdate();

        if(lPStmt != null){
          lPStmt.close();
        }

      }
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("update",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");

      return vData;
    }
 }
 public TVDinRep update2(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   boolean lSuccess = true;
   String[] aCptoCont = vData.getString("iCveConceptoContable").split(",");
   String[] aCptoContPadre = vData.getString("iCveConceptoContablePadre").split(",");
   String[] aProgMes = vData.getString("dProgramadoMes").split(",");
   String[] aEstimCierre = vData.getString("dEstimadoCierreAnual").split(",");
   TVDinRep vFormul = null;
   Vector vPlanAnt = new Vector();


   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }


     StringBuffer lSQL = new StringBuffer();
     int iPeriodo = Integer.parseInt(vData.getString("iPeriodoReportado"));
     if(iPeriodo > 0){
       //iPeriodo -= 1;
       lSQL.append("SELECT sf.IEJERCICIOSEGUIMIENTO, sf.INUMMOVTOSEGTO ")
           .append("FROM ESTSEGTOFORMULARIO sf ")
           .append("WHERE sf.IEJERCICIOREPORTADO = " + vData.getString("iEjercicioReportado"))
           .append(" AND sf.IPERIODOREPORTADO = " + (iPeriodo - 1) + " AND sf.ICVETITULO =  " + vData.getString("iCveTitulo"))
           .append(" AND sf.ICVETITULAR = " + vData.getString("iCveTitular") + " AND sf.ICVEFORMULARIO = " + vData.getString("iCveFormulario"));

       String cSql =
           "SELECT IEJERCICIOSEGUIMIENTO,INUMMOVTOSEGTO FROM ESTSEGTOFORMULARIO " +
           "where IEJERCICIOREPORTADO = " + vData.getString("iEjercicioReportado") +
           " and ICVETITULO = " + vData.getString("iCveTitulo") +
           " and ICVETITULAR = " + vData.getString("iCveTitular") +
           " and ICVEFORMULARIO = 19 "+
           " and IPERIODOREPORTADO = " + (iPeriodo);

       //vPlanAnt = this.findByCustom("",lSQL.toString());
       vPlanAnt = this.findByCustom("",cSql);
       if(vPlanAnt.size() > 0){
         vFormul = new TVDinRep();
         vFormul = (TVDinRep) vPlanAnt.get(0);
       }
     }

     for(int iCont=0;iCont<aCptoCont.length;iCont++){

       vData.addPK(vData.getString("iEjercicioSeguimiento"));
       vData.addPK(vData.getString("iNumMovtoSegto"));
       vData.addPK(vData.getString("iCveConceptoContable"));

       lSQL = new StringBuffer();
       lSQL.append("update ESTPlanFinanciera set ")
           .append("dProgramadoMes = ? ")
           .append("where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iCveConceptoContable = ? ");

       lPStmt = conn.prepareStatement(lSQL.toString());
       lPStmt.setDouble(1,Double.parseDouble(aProgMes[iCont]));
       lPStmt.setInt(2,vFormul.getInt("iEjercicioSeguimiento"));
       lPStmt.setInt(3,vFormul.getInt("iNumMovtoSegto"));
       lPStmt.setInt(4,Integer.parseInt(aCptoCont[iCont]));

       lPStmt.executeUpdate();

       if(lPStmt != null){
         lPStmt.close();
       }

     }
     if(cnNested == null){
       conn.commit();
     }
   } catch(Exception ex){
     warn("update",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("update.rollback",e);
       }
     }
     lSuccess = false;
   } finally{
     try{
       if(lPStmt != null){
         lPStmt.close();
       }
       if(cnNested == null){
         if(conn != null){
           conn.close();
         }
         dbConn.closeConnection();
       }
     } catch(Exception ex2){
       warn("update.close",ex2);
     }
     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }
}

 public TVDinRep update3(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   boolean lSuccess = true;
   String[] aCptoCont = vData.getString("iCveConceptoContable").split(",");
   String[] aCptoContPadre = vData.getString("iCveConceptoContablePadre").split(",");
   String[] aProgMes = vData.getString("dProgramadoMes").split(",");
   String[] aEstimCierre = vData.getString("dEstimadoCierreAnual").split(",");
   TVDinRep vFormul = null;
   Vector vPlanAnt = new Vector();


   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }


     StringBuffer lSQL = new StringBuffer();
     int iPeriodo = Integer.parseInt(vData.getString("iPeriodoReportado"));
     if(iPeriodo > 0){
       //iPeriodo -= 1;
       lSQL.append("SELECT sf.IEJERCICIOSEGUIMIENTO, sf.INUMMOVTOSEGTO ")
           .append("FROM ESTSEGTOFORMULARIO sf ")
           .append("WHERE sf.IEJERCICIOREPORTADO = " + vData.getString("iEjercicioReportado"))
           .append(" AND sf.IPERIODOREPORTADO = " + (iPeriodo - 1) +
                   " AND sf.ICVETITULO =  " + vData.getString("iCveTitulo"))
           .append(" AND sf.ICVETITULAR = " + vData.getString("iCveTitular") +
                   " AND sf.ICVEFORMULARIO = " + vData.getString("iCveFormulario"));

       String cSql =
           "SELECT IEJERCICIOSEGUIMIENTO,INUMMOVTOSEGTO FROM ESTSEGTOFORMULARIO " +
           "where IEJERCICIOREPORTADO = " + vData.getString("iEjercicioReportado") +
           " and ICVETITULO = " + vData.getString("iCveTitulo") +
           " and ICVETITULAR = " + vData.getString("iCveTitular") +
           " and ICVEFORMULARIO = 19 "+
           " and IPERIODOREPORTADO = " + (iPeriodo);

       //vPlanAnt = this.findByCustom("",lSQL.toString());
       vPlanAnt = this.findByCustom("",cSql);
       if(vPlanAnt.size() > 0){
         vFormul = new TVDinRep();
         vFormul = (TVDinRep) vPlanAnt.get(0);
       }
     }

     for(int iCont=0;iCont<aCptoCont.length;iCont++){

       vData.addPK(vData.getString("iEjercicioSeguimiento"));
       vData.addPK(vData.getString("iNumMovtoSegto"));
       vData.addPK(vData.getString("iCveConceptoContable"));

       lSQL = new StringBuffer();
       lSQL.append("update ESTPlanFinanciera set ")
           .append("DPROGRAMADOMODIFICADO = ? ")
           .append("where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iCveConceptoContable = ? ");

       lPStmt = conn.prepareStatement(lSQL.toString());
       lPStmt.setDouble(1,Double.parseDouble(aProgMes[iCont]));
       lPStmt.setInt(2,vFormul.getInt("iEjercicioSeguimiento"));
       lPStmt.setInt(3,vFormul.getInt("iNumMovtoSegto"));
       lPStmt.setInt(4,Integer.parseInt(aCptoCont[iCont]));

       lPStmt.executeUpdate();

       if(lPStmt != null){
         lPStmt.close();
       }

     }
     if(cnNested == null){
       conn.commit();
     }
   } catch(Exception ex){
     warn("update",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("update.rollback",e);
       }
     }
     lSuccess = false;
   } finally{
     try{
       if(lPStmt != null){
         lPStmt.close();
       }
       if(cnNested == null){
         if(conn != null){
           conn.close();
         }
         dbConn.closeConnection();
       }
     } catch(Exception ex2){
       warn("update.close",ex2);
     }
     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }
}

 public TVDinRep update4(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   boolean lSuccess = true;
   String[] aCptoCont = vData.getString("iCveConceptoContable").split(",");
   String[] aRealMes = vData.getString("dRealMes").split(",");
   TVDinRep vFormul = null;
   Vector vPlanAnt = new Vector();


   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }


     StringBuffer lSQL = new StringBuffer();
     int iPeriodo = Integer.parseInt(vData.getString("iPeriodoReportado"));
     if(iPeriodo > 0){
       //iPeriodo -= 1;
       lSQL.append("SELECT sf.IEJERCICIOSEGUIMIENTO, sf.INUMMOVTOSEGTO ")
           .append("FROM ESTSEGTOFORMULARIO sf ")
           .append("WHERE sf.IEJERCICIOREPORTADO = " + vData.getString("iEjercicioReportado"))
           .append(" AND sf.IPERIODOREPORTADO = " + (iPeriodo - 1) +
                   " AND sf.ICVETITULO =  " + vData.getString("iCveTitulo"))
           .append(" AND sf.ICVETITULAR = " + vData.getString("iCveTitular") +
                   " AND sf.ICVEFORMULARIO = " + vData.getString("iCveFormulario"));

       String cSql =
           "SELECT IEJERCICIOSEGUIMIENTO,INUMMOVTOSEGTO FROM ESTSEGTOFORMULARIO " +
           "where IEJERCICIOREPORTADO = " + vData.getString("iEjercicioReportado") +
           " and ICVETITULO = " + vData.getString("iCveTitulo") +
           " and ICVETITULAR = " + vData.getString("iCveTitular") +
           " and ICVEFORMULARIO = 19 "+
           " and IPERIODOREPORTADO = " + (iPeriodo);

       //vPlanAnt = this.findByCustom("",lSQL.toString());
       vPlanAnt = this.findByCustom("",cSql);
       if(vPlanAnt.size() > 0){
         vFormul = new TVDinRep();
         vFormul = (TVDinRep) vPlanAnt.get(0);
       }
     }

     for(int iCont=0;iCont<aCptoCont.length;iCont++){

       vData.addPK(vData.getString("iEjercicioSeguimiento"));
       vData.addPK(vData.getString("iNumMovtoSegto"));
       vData.addPK(vData.getString("iCveConceptoContable"));

       lSQL = new StringBuffer();
       lSQL.append("update ESTPlanFinanciera set ")
           .append("dRealMes = ? ")
           .append("where iEjercicioSeguimiento = ? AND iNumMovtoSegto = ? AND iCveConceptoContable = ? ");


       lPStmt = conn.prepareStatement(lSQL.toString());
       lPStmt.setDouble(1,Double.parseDouble(aRealMes[iCont]));
       lPStmt.setInt(2,vFormul.getInt("iEjercicioSeguimiento"));
       lPStmt.setInt(3,vFormul.getInt("iNumMovtoSegto"));
       lPStmt.setInt(4,Integer.parseInt(aCptoCont[iCont]));

       lPStmt.executeUpdate();

       if(lPStmt != null){
         lPStmt.close();
       }

     }
     if(cnNested == null){
       conn.commit();
     }
   } catch(Exception ex){
     warn("update",ex);
     if(cnNested == null){
       try{
         conn.rollback();
       } catch(Exception e){
         fatal("update.rollback",e);
       }
     }
     lSuccess = false;
   } finally{
     try{
       if(lPStmt != null){
         lPStmt.close();
       }
       if(cnNested == null){
         if(conn != null){
           conn.close();
         }
         dbConn.closeConnection();
       }
     } catch(Exception ex2){
       warn("update.close",ex2);
     }
     if(lSuccess == false)
       throw new DAOException("");

     return vData;
   }
}



}

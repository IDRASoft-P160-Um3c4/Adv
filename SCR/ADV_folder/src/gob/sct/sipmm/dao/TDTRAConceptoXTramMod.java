package gob.sct.sipmm.dao;
import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.TFechas;
import gob.sct.sipmm.dao.ws.TDIngresos;
import java.text.*;
/**
 * <p>Title: TDTRAConceptoXTramMod.java</p>
 * <p>Description: DAO de la entidad TRAConceptoXTramMod</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @author ABarrientos
 * @version 1.0
 */
public class TDTRAConceptoXTramMod extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
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

 /**
  * Busca los conceptos correspondientes a un trámite y modalidad identificando los pertenecientes a un grupo
  * @param cKey String
  * @param cWhere String
  * @throws DAOException
  * @return Vector
  */
 public Vector regresaConceptosDeTramYMod(int iCveTramite, int iCveModalidad, int lPagoAnticipado, Vector vImportesCalculados,int iEjercicio,int iNumSolicitud) throws DAOException{
   Vector vcRecords = new Vector();
   Vector vcRecords2 = new Vector();
   Vector vcRecords3 = new Vector();
  TVDinRep vDatos = new TVDinRep();
  String cGrupos = "";
  cError = "";
  boolean lHayGrupos = false;
  TFechas dtFecha = new TFechas();
   int year = dtFecha.getIntYear(dtFecha.TodaySQL());
  try{
    String cSQL = "SELECT DISTINCT (TRACONCEPTOXGRUPO.iCveGrupo), " + //0
        "0 as iCveConcepto,  " +
        "0 AS iCveTramite, " +
        "0 AS iCveModalidad, " +
        "0 AS lPagoAnticipado, " +
        "TRAGRUPOCONCEPTO.CDSCGRUPO AS cDscConcepto,  " + //5
        "0 AS cObjeto, " +
        " 0 as LESTARIFA, " +
        " 0 as LESPORCENTAJE, " +
        " 0 as DIMPORTESINAJUSTE, " +
        " 0 as DIMPORTEAJUSTADO, " + //10
        " 0 as IREFNUMERICAINGRESOS, " +
        " TRAGRUPOCONCEPTO.LAPLICAFACTORDIRECTO " +
        "FROM  TRACONCEPTOXGRUPO " +
        "JOIN TRACONCEPTOXTRAMMOD ON TRACONCEPTOXTRAMMOD.ICVECONCEPTO = TRACONCEPTOXGRUPO.ICVECONCEPTO " +
        "                         AND TRACONCEPTOXTRAMMOD.ICVETRAMITE = " + iCveTramite +
        "                         AND TRACONCEPTOXTRAMMOD.ICVEMODALIDAD = " + iCveModalidad +
        "                         AND TRACONCEPTOXTRAMMOD.lPagoAnticipado = " + lPagoAnticipado +
        "                         AND TRACONCEPTOXTRAMMOD.iEjercicio = "+year+
        " JOIN TRAGRUPOCONCEPTO ON TRAGRUPOCONCEPTO.ICVEGRUPO = TRACONCEPTOXGRUPO.iCveGrupo " +
        " JOIN TRAREFERENCIACONCEPTO ON TRAREFERENCIACONCEPTO.IEJERCICIO = " + year +
        "                            AND TRAREFERENCIACONCEPTO.ICVECONCEPTO = TRACONCEPTOXGRUPO.ICVECONCEPTO " +
        "                            AND '" + dtFecha.TodaySQL() + "' BETWEEN TRAREFERENCIACONCEPTO.DTINIVIGENCIA and TRAREFERENCIACONCEPTO.DTFINVIGENCIA ";

    vcRecords = this.FindByGeneric("",cSQL,dataSourceName);

    if (vcRecords.size() > 0){
      lHayGrupos = true;
      //Se guardan los grupos y en el sig query se valida q no regrese los conceptos q esten en ese grupo.
      for (int i=0;i<vcRecords.size();i++){
        vDatos = (TVDinRep) vcRecords.get(i);
        if (cGrupos == "")
          cGrupos = vDatos.getInt("iCveGrupo")+"";
        else
          cGrupos += ","+vDatos.getInt("iCveGrupo");
      }
    }

    cSQL = "SELECT 0 as iCveGrupo, " + //0
//    "SELECT 1 as iCveGrupo, " + //0
    "  TRACONCEPTOXTRAMMOD.ICVECONCEPTO, " +
    "  TRACONCEPTOXTRAMMOD.ICVETRAMITE, " +
    "  TRACONCEPTOXTRAMMOD.ICVEMODALIDAD, " +
    "  TRACONCEPTOXTRAMMOD.LPAGOANTICIPADO, " +
    "  TRACONCEPTOPAGO.CDSCCONCEPTO, " +  //5
    "  0 AS cObjeto, " +
    "  trc.LESTARIFA, " +
    "  trc.LESPORCENTAJE, " +
    "  trc.DIMPORTESINAJUSTE, " +
    "  trc.DIMPORTEAJUSTADO, " +  //10
    "  trc.IREFNUMERICAINGRESOS, " +
    "  0 AS LAPLICAFACTORDIRECTO " +
    "FROM  TRACONCEPTOXTRAMMOD " +
    "JOIN TRACONCEPTOPAGO ON TRACONCEPTOPAGO.ICVECONCEPTO = TRACONCEPTOXTRAMMOD.ICVECONCEPTO " +
    "JOIN TRAREFERENCIACONCEPTO trc on trc.IEJERCICIO = " + year + " and trc.ICVECONCEPTO = TRACONCEPTOXTRAMMOD.ICVECONCEPTO " +
    "     AND '" + dtFecha.TodaySQL() + "' BETWEEN trc.DTINIVIGENCIA AND trc.DTFINVIGENCIA " +
    "WHERE TRACONCEPTOXTRAMMOD.ICVETRAMITE = " + iCveTramite +
    "  AND TRACONCEPTOXTRAMMOD.ICVEMODALIDAD = " + iCveModalidad +
    "  AND TRACONCEPTOXTRAMMOD.lPagoAnticipado = " + lPagoAnticipado+
    "  AND TRACONCEPTOXTRAMMOD.iEjercicio = "+year;
    if (lHayGrupos)
        cSQL += " AND TRACONCEPTOXTRAMMOD.ICVECONCEPTO NOT IN (SELECT TRACONCEPTOXGRUPO.ICVECONCEPTO FROM TRACONCEPTOXGRUPO WHERE TRACONCEPTOXGRUPO.ICVEGRUPO IN (" + cGrupos + ")) ";
    cSQL += " and trc.DTINIVIGENCIA = (select max(trc2.DTINIVIGENCIA) from TRAREFERENCIACONCEPTO trc2 " +
    "                          where trc2.IEJERCICIO = " + year +" and trc2.ICVECONCEPTO = TRACONCEPTOXTRAMMOD.ICVECONCEPTO) ";


    vcRecords2 = this.FindByGeneric("",cSQL,dataSourceName);

    if (vcRecords2.size()>0)
      for(int x=0;x<vcRecords2.size();x++){
        vcRecords.addElement(vcRecords2.get(x));
      }
/*
    String cSQLCert =  "SELECT " +
                       "                CG.ICVEGRUPO, " +
                       "                TC.ICVECONCEPTO, " +
                       "                0 AS ICVETRAMITE, " +
                       "                0 AS ICVEMODALIDAD, " +
                       "                0 AS LPAGOANTICIPADO, " +
                       "                CP.CDSCCONCEPTO AS CDSCCONCEPTO, " +
                       "                PI.ICVEEMBARCACION AS COBJETO, " +
                       "                TRC.LESTARIFA, " +
                       "                TRC.LESPORCENTAJE, " +
                       "                TRC.DIMPORTESINAJUSTE, " +
                       "                TRC.DIMPORTEAJUSTADO, " +
                       "                TRC.IREFNUMERICAINGRESOS, " +
                       "                0 AS LAPLICAFACTORDIRECTO , " +
                       "              CI.INUMSOLICITUD " +
                       "FROM  TRACONCEPTOXTIPOIN TC " +
                       "  JOIN INSCERTXINSPECCION CI ON CI.ICVEGRUPOCERTIF=TC.ICVEGRUPOCERTIF " +
                       "    AND CI.ITIPOCERTIFICADO=TC.ITIPOCERTIFICADO " +
                       "  JOIN INSPROGINSP PI ON PI.ICVEINSPPROG=CI.ICVEINSPPROG " +
                       "  JOIN VEHEMBARCACION E ON E.ICVEVEHICULO=PI.ICVEEMBARCACION " +
                       "  JOIN TRACONCEPTOPAGO CP ON CP.ICVECONCEPTO = TC.ICVECONCEPTO " +
                       "  JOIN TRACONCEPTOXGRUPO CG ON CG.ICVECONCEPTO=CP.ICVECONCEPTO " +
                       "  JOIN TRAREFERENCIACONCEPTO TRC ON TRC.IEJERCICIO = " + year +
                       "    AND TRC.ICVECONCEPTO = TC.ICVECONCEPTO " +
                       "    AND '" + dtFecha.TodaySQL() + "' BETWEEN TRC.DTINIVIGENCIA " +
                       "    AND TRC.DTFINVIGENCIA " +
                       "  WHERE CI.IEJERCICIO = "+ iEjercicio +
                       "    AND CI.INUMSOLICITUD = "+ iNumSolicitud ; 
    	
    vcRecords3 = this.FindByGeneric("",cSQLCert,dataSourceName);

    if (vcRecords3.size()>0)
      for(int x=0;x<vcRecords3.size();x++){
        vcRecords.addElement(vcRecords3.get(x));
      }
*/

  } catch(Exception e){
    cError = e.toString();
    e.printStackTrace();
  } finally{
    if(!cError.equals(""))
      throw new DAOException(cError);
    return vcRecords;
  }
}

public Vector calculaConceptosDeTramYMod(TVDinRep vData) throws DAOException{
 Vector vcRecords = new Vector();

 TVDinRep vDatos = new TVDinRep();
 TVDinRep vDatos2 = new TVDinRep();
 String[] aCveGrupo,aCveConcepto,aUnidades, aEsTarifa, aEsPorcentaje,
          aImporteSinAjuste, aImporteAjustado, aDscConcepto, aRefNumerica, aAplicaFactorDirecto;
 String cCveGrupo = "", cCveConcepto = "", cCveUnidades = "", cLimiteInferior = "", cLimiteSuperior = "",
        cEsTarifa = "", cEsPorcentaje = "", cImporteSinAjuste = "", cImporteAjustado = "", cDscConcepto = "",
        cRefNumerica = "", cAplicaFactorDirecto="";

 //System.out.print("**************vData.getString(iCveGrupo):"+vData.getString("iCveGrupo")+"****");
  aCveGrupo         = vData.getString("iCveGrupo").split(",");
 aCveConcepto      = vData.getString("iCveConcepto").split(",");
 aUnidades         = vData.getString("iUnidades").split(",");
 aEsTarifa         = vData.getString("lEsTarifa").split(",");
 aEsPorcentaje     = vData.getString("lEsPorcentaje").split(",");
 aImporteSinAjuste = vData.getString("dImporteSinAjuste").split(",");
 aImporteAjustado  = vData.getString("dImporteAjustado").split(",");
 aDscConcepto      = vData.getString("cDscConcepto").split("¨");
 aRefNumerica      = vData.getString("iRefNumerica").split(",");
 aAplicaFactorDirecto = vData.getString("lAplicaFactorDirecto").split(",");
 String cSQL = "";
 cError = "";
 TFechas dtFecha = new TFechas();
 int year = dtFecha.getIntYear(dtFecha.TodaySQL());
 try{
   for (int x=0;x<aCveGrupo.length;x++){
	   //System.out.print("--------------x:"+x+"-----");
     
	   if(new Integer(aCveGrupo[x]).intValue()>0){
       cSQL = "SELECT " +
              "  TRACONCEPTOXGRUPO.ICVEGRUPO, " +
              "  TRACONCEPTOXGRUPO.ICVECONCEPTO, " +
              "  TRACONCEPTOXGRUPO.DLIMITEINFERIOR, " +
              "  TRACONCEPTOXGRUPO.DLIMITESUPERIOR, " +
              "  trc.LESTARIFA, " +
              "  trc.LESPORCENTAJE, " +
              "  trc.DIMPORTESINAJUSTE, " +
              "  trc.DIMPORTEAJUSTADO, " +
              "  tcp.cDscConcepto, " +
              "  trc.IREFNUMERICAINGRESOS " +
              "FROM TRACONCEPTOXGRUPO " +
              "join TRAREFERENCIACONCEPTO trc ON trc.IEJERCICIO = " + year + "  AND trc.ICVECONCEPTO =  TRACONCEPTOXGRUPO.ICVECONCEPTO " +
              "     AND '" + dtFecha.TodaySQL() + "' BETWEEN trc.DTINIVIGENCIA AND trc.DTFINVIGENCIA " +
              "join TRAConceptoPago tcp ON tcp.iCveConcepto = TRACONCEPTOXGRUPO.ICVECONCEPTO " +
              "WHERE TRACONCEPTOXGRUPO.ICVEGRUPO = " + aCveGrupo[x] +
              " ORDER BY TRACONCEPTOXGRUPO.ICVEGRUPO,TRACONCEPTOXGRUPO.ICVECONCEPTO ";


          vcRecords = this.FindByGeneric("",cSQL,dataSourceName);

          if (vcRecords.size() > 0){
            for (int y=0;y<vcRecords.size();y++){
              vDatos = (TVDinRep) vcRecords.get(y);
              if(cLimiteInferior==""){
                cLimiteInferior   = vDatos.getString("dLimiteInferior");
                cLimiteSuperior   = vDatos.getString("dLimiteSuperior");
                cCveConcepto      = vDatos.getString("iCveConcepto");
                cCveUnidades      = aUnidades[x];
                cCveGrupo         = aCveGrupo[x];
                cEsTarifa         = vDatos.getString("lEsTarifa");
                cEsPorcentaje     = vDatos.getString("lEsPorcentaje");
                cImporteSinAjuste = vDatos.getString("dImporteSinAjuste");
                cImporteAjustado  = vDatos.getString("dImporteAjustado");
                cDscConcepto      = vDatos.getString("cDscConcepto");
                cRefNumerica      = vDatos.getString("iRefNumericaIngresos");
                cAplicaFactorDirecto = aAplicaFactorDirecto[x];
              }
              else{
                cLimiteInferior   += ","+vDatos.getString("dLimiteInferior");
                cLimiteSuperior   += ","+vDatos.getString("dLimiteSuperior");
                cCveConcepto      += ","+vDatos.getString("iCveConcepto");
                cCveUnidades      += ","+aUnidades[x];
                cCveGrupo         += ","+aCveGrupo[x];
                cEsTarifa         += ","+vDatos.getString("lEsTarifa");
                cEsPorcentaje     += ","+vDatos.getString("lEsPorcentaje");
                cImporteSinAjuste += ","+vDatos.getString("dImporteSinAjuste");
                cImporteAjustado  += ","+vDatos.getString("dImporteAjustado");
                cDscConcepto      += "¨"+vDatos.getString("cDscConcepto");
                cRefNumerica      += ","+vDatos.getString("iRefNumericaIngresos");
                cAplicaFactorDirecto += ","+aAplicaFactorDirecto[x];
              }
            }
          }

     }
     else{
//       if(cLimiteInferior==""){
    	 cLimiteInferior+=cLimiteInferior.equals("")?"0":",0";
    	 
    	 cLimiteSuperior+=cLimiteSuperior.equals("")?"0":",0";
    	 
    	 cCveConcepto+=cCveConcepto.equals("")?aCveConcepto[x]:","+aCveConcepto[x];
    	 
    	 cCveUnidades+=cCveUnidades.equals("")?aUnidades[x]:","+aUnidades[x];
    	 
    	 cCveGrupo+=cCveGrupo.equals("")?"0":",0";
    	 
    	 cEsTarifa+=cEsTarifa.equals("")?aEsTarifa[x]:","+aEsTarifa[x];
    	 
    	 cEsPorcentaje+=cEsPorcentaje.equals("")?aEsPorcentaje[x]:","+aEsPorcentaje[x];
    	 
    	 cImporteSinAjuste+=cImporteSinAjuste.equals("")?aImporteSinAjuste[x]:","+aImporteSinAjuste[x];
    	 
    	 cImporteAjustado+=cImporteAjustado.equals("")?aImporteAjustado[x]:","+aImporteAjustado[x];
    	 
    	 cDscConcepto+=cDscConcepto.equals("")?aDscConcepto[x]:","+aDscConcepto[x];
    	 
    	 cRefNumerica+=cRefNumerica.equals("")?aRefNumerica[x]:","+aRefNumerica[x];
    	 
    	 cAplicaFactorDirecto+=cAplicaFactorDirecto.equals("")?"0":",0";
//       }
//       else{
//         cLimiteInferior += ",0";
//         cLimiteSuperior += ",0";
//         cCveConcepto    += ","+aCveConcepto[x];
//         cCveUnidades    += ",1";
//         cCveGrupo       += ",0";
//         cEsTarifa         += ","+aEsTarifa[x];
//         cEsPorcentaje     += ","+aEsPorcentaje[x];
//         cImporteSinAjuste += ","+aImporteSinAjuste[x];
//         cImporteAjustado  += ","+aImporteAjustado[x];
//         cDscConcepto      += "¨"+aDscConcepto[x];
//         cRefNumerica      += ","+aRefNumerica[x];
//         cAplicaFactorDirecto += ",0";
//
//       }

     }

   }

   vDatos2.put("cCveGrupo",cCveGrupo);
   vDatos2.put("cCveConcepto",cCveConcepto);
   vDatos2.put("cCveUnidades",cCveUnidades);
   vDatos2.put("cLimiteInferior",cLimiteInferior);
   vDatos2.put("cLimiteSuperior",cLimiteSuperior);
   vDatos2.put("lEsTarifa",cEsTarifa);
   vDatos2.put("lEsPorcentaje",cEsPorcentaje);
   vDatos2.put("dImporteSinAjuste",cImporteSinAjuste);
   vDatos2.put("dImporteAjustado",cImporteAjustado);
   vDatos2.put("cDscConcepto",cDscConcepto);
   vDatos2.put("iRefNumericaIngresos",cRefNumerica);
   vDatos2.put("lAplicaFactorDirecto",cAplicaFactorDirecto);

   vcRecords = calculaConceptosDeTramYMod2(vDatos2);

 } catch(Exception e){
   cError = e.toString();
   e.printStackTrace();
 } finally{
   if(!cError.equals(""))
     throw new DAOException(cError);
   return vcRecords;
 }
}

public Vector calculaConceptosDeTramYMod2(TVDinRep vDatosParaCalc)  throws DAOException{

  Vector vcRecords = new Vector();
  TVDinRep vDatos = new TVDinRep();
  double dResultadoTemp = 0;
  Double dResultado = new Double(0), dDifUnidades = new Double(0);
  String cSQL = "";
  String[] aCveGrupo, aCveConcepto, aUnidades, aLimiteInferior, aLimiteSuperior,
           aEsTarifa, aEsPorcentaje, aImporteSinAjuste, aImporteAjustado, aDscConcepto,
           aRefNumerica, aAplicaFactorDirecto;
  TFechas dtFecha = new TFechas();
  int year = dtFecha.getIntYear(dtFecha.TodaySQL());
  aCveGrupo = vDatosParaCalc.getString("cCveGrupo").split(",");
  aCveConcepto = vDatosParaCalc.getString("cCveConcepto").split(",");
  aUnidades = vDatosParaCalc.getString("cCveUnidades").split(",");
  aLimiteInferior = vDatosParaCalc.getString("cLimiteInferior").split(",");
  aLimiteSuperior = vDatosParaCalc.getString("cLimiteSuperior").split(",");
  aEsTarifa       = vDatosParaCalc.getString("lEsTarifa").split(",");
  aEsPorcentaje   = vDatosParaCalc.getString("lEsPorcentaje").split(",");
  aImporteSinAjuste    = vDatosParaCalc.getString("dImporteSinAjuste").split(",");
  aImporteAjustado     = vDatosParaCalc.getString("dImporteAjustado").split(",");
//  aDscConcepto         = vDatosParaCalc.getString("cDscConcepto").split("¨");
  aDscConcepto         = vDatosParaCalc.getString("cDscConcepto").split(",");
  aAplicaFactorDirecto = vDatosParaCalc.getString("lAplicaFactorDirecto").split(",");

  aRefNumerica = vDatosParaCalc.getString("iRefNumericaIngresos").split(",");

 try{
    for(int i=0;i<aCveGrupo.length;i++){
      dResultado = new Double(0);
      vDatos = new TVDinRep();
      //Si no pertenece a ningun grupo se busca directamente el concepto y se pone el importe correspondiente
      if(new Integer(aCveGrupo[i]).intValue()==0){
//        dResultado = new Double(aImporteAjustado[i]);
        dResultado = new Double(aImporteAjustado[i]) * new Double(aUnidades[i]);
        //El método vDatosCalculados nos regresa un objeto TVDinRep con los cálculos del importe para agregarlos al vector
        vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i]),
                                Integer.parseInt(aUnidades[i]), aDscConcepto[i],dResultado,aRefNumerica[i]);
        if(vDatos!=null)
          vcRecords.addElement(vDatos);
      }

      if(new Integer(aCveGrupo[i]).intValue()>0){
        if (new Float(aUnidades[i]).doubleValue() >= new Float(aLimiteInferior[i]).doubleValue()
            && (new Float(aUnidades[i]).doubleValue() <= new Float(aLimiteSuperior[i]).doubleValue() || new Float(aLimiteSuperior[i]).doubleValue()<=0 ) ){
              //Si es parte de un grupo pero es tarifa, aplica directo el importe
              if(aEsTarifa[i].equals("1")){
                dResultado = new Double(aImporteAjustado[i]);

                vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i]),
                                1, aDscConcepto[i],dResultado,aRefNumerica[i]);
                if(vDatos!=null)
                   vcRecords.addElement(vDatos);

              }
              //si pertenedce a un grupo y es porcentaje y aplica factor directo
              else if(aEsPorcentaje[i].equals("1") && Integer.parseInt(aAplicaFactorDirecto[i]) == 1){
                dResultado = new Double(new Double(aUnidades[i]).doubleValue() * new Double(aImporteSinAjuste[i]).doubleValue());
                vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i]),
                		      new Double(aUnidades[i]).doubleValue(), aDscConcepto[i],dResultado,aRefNumerica[i]);
                              //Integer.parseInt(aUnidades[i]), aDscConcepto[i],dResultado,aRefNumerica[i]);
                if(vDatos!=null)
                  vcRecords.addElement(vDatos);
              }
              //Si pertenece a un grupo y es porcentaje hay dos opciones
              else if(aEsPorcentaje[i].equals("1")){
                //si i > 0 significa que no esta dentro del primer rango del grupo
                if(i>0){
                  //Si el concepto correspondiente pertenece al mismo grupo del concepto anterior
                  if(aCveGrupo[i].equals(aCveGrupo[i-1])){
                    dDifUnidades = new Double(new Double(aUnidades[i]).doubleValue() - new Double(aLimiteSuperior[i-1]).doubleValue());
                    //Si es concepto anterior es tarifa aplica la tarifa del concepto anterior
                    //mas la diferencia de la unidades ingresadas por el usuario menos el limite superior del concepto anterior por el factor
                    if(aEsTarifa[i-1].equals("1")){
                      dResultadoTemp = new Double(aImporteAjustado[i-1]).doubleValue();
                      vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i-1]),
                                1, aDscConcepto[i-1],new Double(dResultadoTemp),aRefNumerica[i-1]);
                      if(vDatos!=null)
                        vcRecords.addElement(vDatos);

                      //dResultado = new Double(dResultadoTemp + (dDifUnidades.doubleValue() * new Double(aImporteSinAjuste[i]).doubleValue()));
                      dResultado = new Double(dDifUnidades.doubleValue() * new Double(aImporteSinAjuste[i]).doubleValue());
                      vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i]),
                                1, aDscConcepto[i],dResultado,aRefNumerica[i]);
                      if(vDatos!=null)
                        vcRecords.addElement(vDatos);

                    }
                    //Si el concepto anterior es porcentaje se multiplica el limite superior del concepto anterior por su correspondiente factor
                    //mas la diferencia de las unidades ingresadas por el usuario menos el limite superior del concepto anterior por el factor
                    else{ //Si es porcentaje
                      dResultadoTemp = new Double(aLimiteSuperior[i-1]).doubleValue() * new Double(aImporteSinAjuste[i-1]).doubleValue();
                      vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i-1]),
                                Double.parseDouble(aUnidades[i]), aDscConcepto[i-1],new Double(dResultadoTemp),aRefNumerica[i-1]);
                      if(vDatos!=null)
                        vcRecords.addElement(vDatos);

                      //dResultado = new Double(dResultadoTemp + (dDifUnidades.doubleValue() *  new Double(aImporteSinAjuste[i]).doubleValue()));
                      dResultado = new Double(dDifUnidades.doubleValue() *  new Double(aImporteSinAjuste[i]).doubleValue());
                      vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i]),
                    		  Double.parseDouble(aUnidades[i]), aDscConcepto[i],dResultado,aRefNumerica[i]);
                      if(vDatos!=null)
                        vcRecords.addElement(vDatos);

                    }
                  }
                  else{
                    dResultado = new Double(new Double(aUnidades[i]).doubleValue() * new Double(aImporteSinAjuste[i]).doubleValue());
                    vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i]),
                    		Double.parseDouble(aUnidades[i]), aDscConcepto[i],dResultado,aRefNumerica[i]);
                    if(vDatos!=null)
                      vcRecords.addElement(vDatos);

                  }

                }
                //Si i  es 0 se multiplica las unidades por el factor
                else{
                  String s1[] = aUnidades[i].split(".");
                  dResultado = new Double(new Double(aUnidades[i]).doubleValue() * new Double(aImporteSinAjuste[i]).doubleValue());
                  if(aUnidades[i].indexOf(".") > 0)
                    vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i]),
                                Double.parseDouble(aUnidades[i]), aDscConcepto[i],dResultado,aRefNumerica[i]);
                  else vDatos = this.vDatosCalculados(Integer.parseInt(aCveGrupo[i]),Integer.parseInt(aCveConcepto[i]),
                          Integer.parseInt(aUnidades[i]), aDscConcepto[i],dResultado,aRefNumerica[i]);
                  if(vDatos!=null)
                    vcRecords.addElement(vDatos);

                }
              }
        }

      }

    }

 } catch(Exception e){
   cError = e.toString();
   e.printStackTrace();
 } finally{
   if(!cError.equals(""))
     throw new DAOException(cError);
   return vcRecords;
 }
}


public TVDinRep vDatosCalculados(int iCveGrupo, int iCveConcepto, int iUnidades, String cDscConcepto, Double dResultado, String cRefNumerica){
  TVDinRep vDatosCalculados = new TVDinRep();

  DecimalFormat df = new DecimalFormat("$ ########0.00");
     vDatosCalculados.put("iCveGrupo",iCveGrupo);
     vDatosCalculados.put("iCveConcepto",iCveConcepto);
     vDatosCalculados.put("iCveTramite",0);
     vDatosCalculados.put("iCveModalidad",0);
     vDatosCalculados.put("iUnidades",iUnidades);
     vDatosCalculados.put("cDscConcepto",cDscConcepto.replaceAll(",",""));

     double dResTemp=dResultado.doubleValue();
     double fracc = (dResTemp - (int) dResTemp);
     if (fracc == 0.50) {
        dResTemp -= fracc;
     }
     else {
        dResTemp = Math.round(dResTemp);
     }

     vDatosCalculados.put("dImporteTota",df.format(dResTemp));
     vDatosCalculados.put("iRefNumericaIngresos",cRefNumerica);
     vDatosCalculados.put("dImporteSinFormato",dResTemp);
     if (dResTemp>0)
       return vDatosCalculados;
     else
       return null;
}
public TVDinRep vDatosCalculados(int iCveGrupo, int iCveConcepto, double iUnidades, String cDscConcepto, Double dResultado, String cRefNumerica){
	  TVDinRep vDatosCalculados = new TVDinRep();

	  DecimalFormat df = new DecimalFormat("$ ########0.00");
	     vDatosCalculados.put("iCveGrupo",iCveGrupo);
	     vDatosCalculados.put("iCveConcepto",iCveConcepto);
	     vDatosCalculados.put("iCveTramite",0);
	     vDatosCalculados.put("iCveModalidad",0);
	     vDatosCalculados.put("iUnidades",iUnidades);
	     vDatosCalculados.put("cDscConcepto",cDscConcepto.replaceAll(",",""));

	     double dResTemp=dResultado.doubleValue();
	     double fracc = (dResTemp - (int) dResTemp);
	     if (fracc == 0.50) {
	        dResTemp -= fracc;
	     }
	     else {
	        dResTemp = Math.round(dResTemp);
	     }

	     vDatosCalculados.put("dImporteTota",df.format(dResTemp));
	     vDatosCalculados.put("iRefNumericaIngresos",cRefNumerica);
	     vDatosCalculados.put("dImporteSinFormato",dResTemp);
	     if (dResTemp>0)
	       return vDatosCalculados;
	     else
	       return null;
	}

public Vector generaMov(TVDinRep vDatos){
  TVDinRep vdMovGenerado;

  Vector vMovsGenerados = new Vector();
  String[] aaReg, aRegs;
  TDIngresos dIngresos = new TDIngresos();

  int iCveUsuarioIng = Integer.parseInt(vDatos.getString("iCveUsuarioIng"));
  aaReg = vDatos.getString("hdaRes").split("'");
  String cAreaRec  = VParametros.getPropEspecifica("AreaRecIngresos");
  String cSucursal = VParametros.getPropEspecifica("SucursalBanamex");
  String cCuenta  = VParametros.getPropEspecifica("CuentaBanamex");
  for(int regs=0;regs<aaReg.length;regs++){
    vdMovGenerado = new TVDinRep();
    aRegs = aaReg[regs].split(",");
    vdMovGenerado.put("cDeposito",regs+1); //0
    vdMovGenerado.put("iRefNumerica",aRegs[7]);   //1

    vdMovGenerado.put("cRefAlfanumerica", dIngresos.generaMov(Integer.parseInt(aRegs[7]),Integer.parseInt(aRegs[4]),iCveUsuarioIng,"",vDatos.getInt("iCveUnidAdmva"),vDatos.getInt("iCveOficIngresos") )); //2
    vdMovGenerado.put("iImporte",aRegs[6]);  //3
    vdMovGenerado.put("cAreaRec",cAreaRec);  //4
    vdMovGenerado.put("cSucursal",cSucursal);  //5
    vdMovGenerado.put("cCuenta",cCuenta);  //6
    vdMovGenerado.put("iImporteSinFormato",aRegs[8]); //7
    vdMovGenerado.put("iCveConcepto",aRegs[1]);  //8
    vdMovGenerado.put("iCveUsuarioIng",iCveUsuarioIng); //9
    vMovsGenerados.addElement(vdMovGenerado);
  }

  return vMovsGenerados;
}

  public Vector buscaUsuIng(TVDinRep vdRfc){
    Vector vUsuario = new Vector();
    TDIngresos dIngresos =  new TDIngresos();
    vUsuario = (Vector)dIngresos.findRFC(vdRfc.getString("cRFC"),1);
    return vUsuario;
  }

  public Vector registraUsuIng(TVDinRep vdDatosSol){
    Vector viCvePersona = new Vector();
    TVDinRep vdSolReg = new TVDinRep();
    String cCveUsuario = "";
    TDIngresos dIngresos =  new TDIngresos();
    cCveUsuario = (String) dIngresos.insertUsr(vdDatosSol);
    vdSolReg.put("iCveUsuario",cCveUsuario);
    viCvePersona.addElement(vdSolReg);
    return viCvePersona;
  }

  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into TRAConceptoXTramMod(iEjercicio,iCveTramite,iCveModalidad,iCveConcepto,lPagoAnticipado) values (?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveTramite,iCveModalidad,iCveConcepto, </b></p>
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
          "insert into TRAConceptoXTramMod(iEjercicio,iCveTramite,iCveModalidad,iCveConcepto,lPagoAnticipado) values (?,?,?,?,0)";

      //AGREGAR AL ULTIMO ...

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveConcepto1"));
      //...

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveTramite"));
      lPStmt.setInt(3,vData.getInt("iCveModalidad"));
      lPStmt.setInt(4,vData.getInt("iCveConcepto1"));
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

  public TVDinRep Cambia(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    PreparedStatement lPStmt1 = null;
    boolean lSuccess = true;
    String[] cConjunto;
    String cMsg = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

    cConjunto = vData.getString("cConjunto").split(",");
      String lSQL1 = "update TRAConceptoXTramMod set lPagoAnticipado = 0 where iCveTRamite = ? AND iCveModalidad = ? AND iEjercicio=?";
      lPStmt1 = conn.prepareStatement(lSQL1);
      lPStmt1.setInt(1,vData.getInt("iCveTramite"));
      lPStmt1.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt1.setInt(3,vData.getInt("iEjercicio"));
      lPStmt1.executeUpdate();

    for(int tm=0;tm<cConjunto.length;tm++){
      String lSQL =
          "update TRAConceptoXTramMod set lPagoAnticipado = 1 where iCveTRamite = ? AND iCveModalidad = ? AND iEjercicio= ? AND iCveConcepto = ?";
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iCveTramite"));
      lPStmt.setInt(2,vData.getInt("iCveModalidad"));
      lPStmt.setInt(3,vData.getInt("iEjercicio"));
      lPStmt.setInt(4,Integer.parseInt(cConjunto[tm]));


      lPStmt.executeUpdate();
      lPStmt.close();
    }
      if(cnNested == null){
        conn.commit();
      }
     } catch(SQLException sqle){
       lSuccess = false;
       cMsg = "" + sqle.getErrorCode();

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
          lPStmt1.close();
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
        throw new DAOException(cMsg);
      return vData;
    }
  }

  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from TRAConceptoXTramMod where iEjercicio = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveConcepto = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveTramite,iCveModalidad,iCveConcepto, </b></p>
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
      String lSQL = "delete from TRAConceptoXTramMod where iEjercicio = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveConcepto = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveTramite"));
      lPStmt.setInt(3,vData.getInt("iCveModalidad"));
      lPStmt.setInt(4,vData.getInt("iCveConcepto"));

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
   * <p><b> update TRAConceptoXTramMod set lPagoAnticipado=? where iEjercicio = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveConcepto = ?  </b></p>
   * <p><b> Campos Llave: iEjercicio,iCveTramite,iCveModalidad,iCveConcepto, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws
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
      String lSQL = "update TRAConceptoXTramMod set lPagoAnticipado=? where iEjercicio = ? AND iCveTramite = ? AND iCveModalidad = ? AND iCveConcepto = ? ";

      vData.addPK(vData.getString("iEjercicio"));
      vData.addPK(vData.getString("iCveTramite"));
      vData.addPK(vData.getString("iCveModalidad"));
      vData.addPK(vData.getString("iCveConcepto"));
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("lPagoAnticipado"));
      lPStmt.setInt(2,vData.getInt("iEjercicio"));
      lPStmt.setInt(3,vData.getInt("iCveTramite"));
      lPStmt.setInt(4,vData.getInt("iCveModalidad"));
      lPStmt.setInt(5,vData.getInt("iCveConcepto"));
      lPStmt.executeUpdate();
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

 /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> Campos Llave: iEjercicio,iCveTramite,iCveModalidad,iCveConcepto, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   * Autor: iCaballero
   * 15.Nov.2006
   */

 public TVDinRep actualizaRefAlfaNum(TVDinRep vData,Connection cnNested) throws
     DAOException{
   DbConnection dbConn = null;
   Connection conn = cnNested;
   PreparedStatement lPStmt = null;
   boolean lSuccess = true;
   String[] aReferencias = vData.getString("cRefAlfaNumerica").split(",");
   TVDinRep vdConsecutivos;
   String cMsg = "";
   try{
     if(cnNested == null){
       dbConn = new DbConnection(dataSourceName);
       conn = dbConn.getConnection();
       conn.setAutoCommit(false);
       conn.setTransactionIsolation(2);
     }
     String cQuery = "SELECT " +
         "iConsecutivo " +
         "FROM TRAREGREFPAGO " +
         "where IEJERCICIO = " + vData.getString("iEjercicio") +
         " and INUMSOLICITUD = " + vData.getString("iNumSolicitud") +
         " and CREFALFANUM like ' ' " +
         "order by ICONSECUTIVO";

     Vector vConsecutivos = this.findByCustom("",cQuery);

     if(aReferencias.length == vConsecutivos.size())
       for(int i = 0; i < vConsecutivos.size(); i++){
         vdConsecutivos = (TVDinRep) vConsecutivos.get(i);
         String lSQL = "update TRAREGREFPAGO set cRefAlfaNum = ?, dtMovimiento = current date where iEjercicio = ? AND iNumSolicitud = ? and iConsecutivo = ? ";
         lPStmt = conn.prepareStatement(lSQL);
         lPStmt.setString(1,aReferencias[i]);
         lPStmt.setInt(2,vData.getInt("iEjercicio"));
         lPStmt.setInt(3,vData.getInt("iNumSolicitud"));
         lPStmt.setInt(4,vdConsecutivos.getInt("iConsecutivo"));
         lPStmt.executeUpdate();
         lPStmt.close();
       }
     else
       cError = "El número de parámetros es incorrecto";

     if(cnNested == null)
       conn.commit();

   } catch(SQLException sqle){
     lSuccess = false;
     cMsg = "" + sqle.getErrorCode();

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
       throw new DAOException(cMsg);
     return vData;
   }
 }
 public Vector regresaConceptosXCert(int GrupoCert, int TipoCert) throws DAOException{
  Vector vcRecords = new Vector();
  Vector vcRecords2 = new Vector();
  TVDinRep vDatos = new TVDinRep();
  String cGrupos = "";
  cError = "";
  boolean lHayGrupos = false;
  TFechas dtFecha = new TFechas();
  int year = dtFecha.getIntYear(dtFecha.TodaySQL());
  try{
    String cSQL = "SELECT " +
                  "       DISTINCT (TRACONCEPTOXGRUPO.ICVEGRUPO), " +
                  "       0 AS ICVECONCEPTO, " +
                  "       0 AS ICVETRAMITE, " +
                  "       0 AS ICVEMODALIDAD, " +
                  "       0 AS LPAGOANTICIPADO, " +
                  "       TRAGRUPOCONCEPTO.CDSCGRUPO AS CDSCCONCEPTO, " +
                  "       0 AS COBJETO, " +
                  "       0 AS LESTARIFA, " +
                  "       0 AS LESPORCENTAJE, " +
                  "       0 AS DIMPORTESINAJUSTE, " +
                  "       0 AS DIMPORTEAJUSTADO, " +
                  "       0 AS IREFNUMERICAINGRESOS, " +
                  "       TRAGRUPOCONCEPTO.LAPLICAFACTORDIRECTO " +
                  "FROM  TRACONCEPTOXGRUPO " +
                  "  JOIN INSCONCXTIPOCERT ON TRACONCEPTOXGRUPO.ICVECONCEPTO = INSCONCXTIPOCERT.ICVECONCEPTOEVAL " +
                  "    AND INSCONCXTIPOCERT.ICVEGRUPOCERTIF = " +GrupoCert+
                  "    AND INSCONCXTIPOCERT.ITIPOCERTIFICADO = " +TipoCert+
                  "  JOIN TRAGRUPOCONCEPTO ON TRAGRUPOCONCEPTO.ICVEGRUPO = TRACONCEPTOXGRUPO.ICVEGRUPO " +
                  "  JOIN TRAREFERENCIACONCEPTO ON TRAREFERENCIACONCEPTO.IEJERCICIO = " + year +
                  "    AND TRAREFERENCIACONCEPTO.ICVECONCEPTO = TRACONCEPTOXGRUPO.ICVECONCEPTO " +
                  "    AND '" + dtFecha.TodaySQL() + "' BETWEEN TRAREFERENCIACONCEPTO.DTINIVIGENCIA " +
                  "    AND TRAREFERENCIACONCEPTO.DTFINVIGENCIA ";

    vcRecords = this.FindByGeneric("",cSQL,dataSourceName);

    if (vcRecords.size() > 0){
      lHayGrupos = true;
      //Se guardan los grupos y en el sig query se valida q no regrese los conceptos q esten en ese grupo.
      for (int i=0;i<vcRecords.size();i++){
        vDatos = (TVDinRep) vcRecords.get(i);
        if (cGrupos == "")
          cGrupos = vDatos.getInt("iCveGrupo")+"";
        else
          cGrupos += ","+vDatos.getInt("iCveGrupo");
      }
    }
    cSQL = "SELECT 0 as iCveGrupo, " +
           "  INSCONCXTIPOCERT.ICVECONCEPTOEVAL, " +
           "  0, " +
           "  0, " +
           "  0, " +
           "  TRACONCEPTOPAGO.CDSCCONCEPTO, " +
           "  0 AS cObjeto, " +
           "  trc.LESTARIFA, " +
           "  trc.LESPORCENTAJE, " +
           "  trc.DIMPORTESINAJUSTE, " +
           "  trc.DIMPORTEAJUSTADO, " +
           "  trc.IREFNUMERICAINGRESOS, " +
           "  0 AS LAPLICAFACTORDIRECTO " +
           "FROM  INSCONCXTIPOCERT " +
           "JOIN TRACONCEPTOPAGO ON TRACONCEPTOPAGO.ICVECONCEPTO = INSCONCXTIPOCERT.ICVECONCEPTOEVAL " +
           "JOIN TRAREFERENCIACONCEPTO trc on trc.IEJERCICIO = " +year +
           " and trc.ICVECONCEPTO = INSCONCXTIPOCERT.ICVECONCEPTOEVAL " +
           "     AND '"+dtFecha.getDateSQL(dtFecha.getThisTime())+"' BETWEEN trc.DTINIVIGENCIA AND trc.DTFINVIGENCIA " +
           "WHERE INSCONCXTIPOCERT.ICVEGRUPOCERTIF = " +GrupoCert+
           "  AND INSCONCXTIPOCERT.ITIPOCERTIFICADO = "+TipoCert;
    if (lHayGrupos)
        cSQL += " AND INSCONCXTIPOCERT.ICVECONCEPTOEVAL NOT IN (SELECT TRACONCEPTOXGRUPO.ICVECONCEPTO FROM TRACONCEPTOXGRUPO WHERE TRACONCEPTOXGRUPO.ICVEGRUPO IN (" + cGrupos + ")) ";
        cSQL += " and trc.DTINIVIGENCIA = (select max(trc2.DTINIVIGENCIA) from TRAREFERENCIACONCEPTO trc2 " +
                "                          where trc2.IEJERCICIO = " + year +" and trc2.ICVECONCEPTO = INSCONCXTIPOCERT.ICVECONCEPTOEVAL) ";


    vcRecords2 = this.FindByGeneric("",cSQL,dataSourceName);

    if (vcRecords2.size()>0)
      for(int x=0;x<vcRecords2.size();x++){
        vcRecords.addElement(vcRecords2.get(x));
      }


  } catch(Exception e){
    cError = e.toString();
    e.printStackTrace();
  } finally{
    if(!cError.equals(""))
      throw new DAOException(cError);
    return vcRecords;
  }
}


}

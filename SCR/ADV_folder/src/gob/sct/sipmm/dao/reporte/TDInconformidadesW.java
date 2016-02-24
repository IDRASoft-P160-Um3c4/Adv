package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;

import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDInconformidadesW.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Ing. Arturo López Peña
 * @version 1.0
 */

public class TDInconformidadesW extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  public TDInconformidadesW(){
  }
  public Vector fReporte(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
       Vector vcCuerpo = new Vector();
       Vector vcCopiasPara = new Vector();
  Vector vRegs = new Vector();
  Vector vInconformes = new Vector();
  TWord rep = new TWord();
  TFechas tFecha = new TFechas();
  int IFOLIO;
  int iCveInconforme = 0, iCveDomicilio = 0;
  String CINCONFORMIDAD="",CFECHAREG="",CEXPEDIENTE= "",CSOL="",CACTORECLAMADO="",CAUTORIDADRESOLUTORIA="",
         CSOLICITASUSPENCION="",COBSINCONFORMIDAD="",CJUICIO="",CENTIDADFEDERAL="",CPAIS="",CMUNICIPIO="";
  String[] cParametros = cQuery.split(",");
  System.out.print("*****  "+cQuery);
  java.sql.Date dtFechaApr=null,dtFechaVal=null;

  try{
    vRegs = super.FindByGeneric("",
                    "SELECT I.ICVEINCONFORMIDAD,I.DTREGISTRO,I.CNUMEXPEDIENTE,I.CACTORECLAMADO,I.CAUTORIDADRESOLUTORA,I.LSOLICITASUSPENSION, " +
                    "I.COBSINCONFORMIDAD,J.CDSCJUICIO, P.CNOMBRE AS CPAIS, EF.CNOMBRE AS CENTIDADFED, M.CNOMBRE AS CMUNICIPIO " +
                    "FROM RININCONFORMIDAD I " +
                    "JOIN RINJUICIOREC J ON I.ICVEJUICIOREC = J.ICVEJUICIOREC " +
                    "JOIN GRLPAIS P ON P.ICVEPAIS = I.ICVEPAIS " +
                    "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = I.ICVEPAIS AND EF.ICVEENTIDADFED = I.ICVEENTIDADFED " +
                    "JOIN GRLMUNICIPIO M ON M.ICVEPAIS = I.ICVEPAIS AND M.ICVEENTIDADFED = I.ICVEENTIDADFED AND M.ICVEMUNICIPIO = I.ICVEMUNICIPIO " +
                    "where ICVEINCONFORMIDAD = "+cParametros[0], dataSourceName);
  }catch(SQLException ex){
    cMensaje = ex.getMessage();
  }catch(Exception ex2){
    ex2.printStackTrace();
  }

  try{
    vInconformes = super.FindByGeneric("",
                    "SELECT I.ICVEINCONFORME, P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CPERSONA, P.ITIPOPERSONA, " +
                    "D.CCALLE||' #'|| D.CNUMEXTERIOR AS CDOMICILIO, D.CNUMINTERIOR as cNumIntPromovente,D.CCOLONIA as cColPoromovente,D.CCODPOSTAL as ccp,D.CTELEFONO,EF.CNOMBRE AS CESTADO, " +
                    "M.CNOMBRE AS CMUNICIPIO, L.CNOMBRE AS CLOCALIDAD, " +
                    "R.CNOMRAZONSOCIAL||' '|| R.CAPPATERNO||' '|| R.CAPMATERNO AS CREPRESENTANTE, " +
                    "DR.CCALLE||' #'|| DR.CNUMEXTERIOR AS DOMREPRESENTANTE, DR.CNUMINTERIOR,DR.CCOLONIA,DR.CCODPOSTAL,DR.CTELEFONO, " +
                    "I.ICVEINCONFORME,D.iCveDomicilio "+
                    "FROM RININCONFORMES I " +
                    "JOIN GRLPERSONA P ON P.ICVEPERSONA = I.ICVEINCONFORME " +
                    "JOIN GRLDOMICILIO D ON D.ICVEPERSONA = P.ICVEPERSONA " +
                    "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = D.ICVEPAIS AND EF.ICVEENTIDADFED= D.ICVEENTIDADFED " +
                    "JOIN GRLMUNICIPIO M ON M.ICVEPAIS = D.ICVEPAIS AND M.ICVEENTIDADFED= D.ICVEENTIDADFED AND M.ICVEMUNICIPIO = D.ICVEMUNICIPIO " +
                    "JOIN GRLLOCALIDAD L ON L.ICVEPAIS = D.ICVEPAIS AND L.ICVEENTIDADFED = D.ICVEENTIDADFED " +
                    "   AND L.ICVEMUNICIPIO = D.ICVEMUNICIPIO AND L.ICVELOCALIDAD = D.ICVELOCALIDAD " +
                    "LEFT JOIN GRLREPLEGAL REP ON REP.ICVEEMPRESA = P.ICVEPERSONA AND REP.LPRINCIPAL=1 " +
                    "LEFT JOIN GRLPERSONA R ON REP.ICVEPERSONA = R.ICVEPERSONA " +
                    "LEFT JOIN GRLDOMICILIO DR ON  DR.ICVEPERSONA = R.ICVEPERSONA " +
                    "where i.ICVEINCONFORMIDAD = " + cParametros[0]+" And I.lTitular = 1 ", dataSourceName);
  }catch(SQLException ex){
    cMensaje = ex.getMessage();
  }catch(Exception ex2){
    ex2.printStackTrace();
  }

  rep.iniciaReporte();
  rep.comRemplaza("[dtFechaActual]",tFecha.getDateSPN(tFecha.getDateSQL(tFecha.getThisTime()))) ;

  if(vInconformes.size()>0){
    TVDinRep vcInconformes = (TVDinRep)vInconformes.get(0);
    String Promovente = vcInconformes.getString("CPERSONA");
    iCveInconforme = vcInconformes.getInt("ICVEINCONFORME");
    iCveDomicilio = vcInconformes.getInt("iCveDomicilio");
    if(Promovente!="") rep.comRemplaza("[cPromovente]",Promovente); else rep.comRemplaza("[cPromovente]","_______________________________");
    String DomPersona = vcInconformes.getString("CDOMICILIO");
    if(vcInconformes.getString("iNumIntPromovente").equalsIgnoreCase(""))DomPersona+=" INT "+vcInconformes.getString("iNumIntPromovente");
    if(vcInconformes.getString("cColPoromovente").equalsIgnoreCase(""))DomPersona += ", COLONIA "+vcInconformes.getString("cColPoromovente");
    if(DomPersona!="") rep.comRemplaza("[cDireccionPromovente]",DomPersona);else rep.comRemplaza("[cDireccionPromovente]","__________________________________________________________________________");
    String localidadPersona = vcInconformes.getString("CLOCALIDAD");
    if(localidadPersona!="") rep.comRemplaza("[cMunicipioPromovente]",localidadPersona); else rep.comRemplaza("[cMunicipioPromovente]","_________");
    String codigoPostal = vcInconformes.getString("ccp");
    if(codigoPostal!="") rep.comRemplaza("[cCodigoPostal]",codigoPostal); else rep.comRemplaza("[cCodigoPostal]","_________");
    String entidadFed = vcInconformes.getString("CESTADO");
    if(entidadFed!="") rep.comRemplaza("[cEntidadFedProm]",entidadFed); else rep.comRemplaza("[cEntidadFedProm]","_________");
    rep.comRemplaza("[cPaisPromovente]","México");
    if(!vcInconformes.getString("CREPRESENTANTE").equalsIgnoreCase("")&&!vcInconformes.getString("CREPRESENTANTE").equalsIgnoreCase("NULL")&&vcInconformes.getString("CREPRESENTANTE")!=null){
      String CREPRESENTANTE = vcInconformes.getString("CREPRESENTANTE");
      rep.comRemplaza("[cRepresentanteLegal]",CREPRESENTANTE);
      rep.comRemplaza("[cRepLeyenda]","Apoderado General y Representante Legal de ");
    }else {
      rep.comRemplaza("[cRepresentanteLegal]","");
      rep.comRemplaza("[cRepLeyenda]","");
    }
  }

  if (vRegs.size() > 0){
    TVDinRep vDatos       = (TVDinRep) vRegs.get(0);
    CINCONFORMIDAD        = vDatos.getString("ICVEINCONFORMIDAD");
    CFECHAREG             = (""+vDatos.getDate("DTREGISTRO")!=""&&vDatos.getDate("DTREGISTRO")!=null)?""+tFecha.getFechaTexto(vDatos.getDate("DTREGISTRO")," DE "):"";
    CEXPEDIENTE           = vDatos.getString("CNUMEXPEDIENTE");
    CACTORECLAMADO        = vDatos.getString("CACTORECLAMADO");
    CAUTORIDADRESOLUTORIA = vDatos.getString("CAUTORIDADRESOLUTORA");
    CSOLICITASUSPENCION   = vDatos.getInt("LSOLICITASUSPENSION")==1?"SÍ":"NO";
    COBSINCONFORMIDAD     = vDatos.getString("COBSINCONFORMIDAD");
    CJUICIO               = vDatos.getString("CDSCJUICIO");
    CPAIS                 = vDatos.getString("CPAIS");
    CENTIDADFEDERAL       = vDatos.getString("CENTIDADFED");
    CMUNICIPIO            = vDatos.getString("CMUNICIPIO");
    /*******/
    String cSeparador = "/";
    if(CINCONFORMIDAD!="")         rep.comRemplaza("[CINCONFORMIDAD]",CINCONFORMIDAD);                            else rep.comRemplaza("[CINCONFORMIDAD]","_________");
    if(CFECHAREG!="")              rep.comRemplaza("[cFechaRegistro]",CFECHAREG);                                      else rep.comRemplaza("[CFECHAREG]","___________________________");
    if(CEXPEDIENTE!="")            rep.comRemplaza("[CEXPEDIENTE]",CEXPEDIENTE);                                  else rep.comRemplaza("[CEXPEDIENTE]","_______________________");
    if(CACTORECLAMADO!="")         rep.comRemplaza("[CACTORECLAMADO]",CACTORECLAMADO);                            else rep.comRemplaza("[CACTORECLAMADO]","");
    if(CAUTORIDADRESOLUTORIA!="")  rep.comRemplaza("[CAUTORIDADRESOLUTORIA]",CAUTORIDADRESOLUTORIA);              else rep.comRemplaza("[CAUTORIDADRESOLUTORIA]","_____________________________________________________________________");
    if(CSOLICITASUSPENCION!="")    rep.comRemplaza("[CSOLICITASUSPENCION]",CSOLICITASUSPENCION);                  else rep.comRemplaza("[CSOLICITASUSPENCION]","");
    if(COBSINCONFORMIDAD!="")      rep.comRemplaza("[COBSINCONFORMIDAD]",COBSINCONFORMIDAD);                      else rep.comRemplaza("[COBSINCONFORMIDAD]","     <<OBSERVACIÓN DE INCONFORMIDAD>>     ");
    if(CJUICIO!="")                rep.comRemplaza("[CJUICIO]",CJUICIO);                                          else rep.comRemplaza("[CJUICIO]","");
    if(CPAIS!="")                  rep.comRemplaza("[CPAIS]",CPAIS);                                              else rep.comRemplaza("[CPAIS]","_____________________");
    if(CENTIDADFEDERAL!="")        rep.comRemplaza("[CENTIDADFEDERAL]",CENTIDADFEDERAL);                          else rep.comRemplaza("[CENTIDADFEDERAL]","_____________________________________");
    if(CMUNICIPIO!="")             rep.comRemplaza("[CMUNICIPIO]",CMUNICIPIO);                                    else rep.comRemplaza("[CMUNICIPIO]","___________________________");

  } else {
    rep.comRemplaza("[CINCONFORMIDAD]","_________");
    rep.comRemplaza("[CFECHAREG]","___________________________");
    rep.comRemplaza("[CEXPEDIENTE]","_______________________");
    rep.comRemplaza("[CACTORECLAMADO]","");
    rep.comRemplaza("[CAUTORIDADRESOLUTORIA]","_____________________________________________________________________");
    rep.comRemplaza("[CSOLICITASUSPENCION]","______");
    rep.comRemplaza("[COBSINCONFORMIDAD]","     <<OBSERVACIÓN DE INCONFORMIDAD>>     ");
    rep.comRemplaza("[CJUICIO]","");
    rep.comRemplaza("[CPAIS]","_____________________");
    rep.comRemplaza("[CENTIDADFEDERAL]","__________________________________");
    rep.comRemplaza("[CMUNICIPIO]","___________________________");
  }

  Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                       Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                       0,0,
                       iCveInconforme,iCveDomicilio,0,
                       "","",
                       "", "",
                       true,"cCuerpo",vcCuerpo,
                       true, vcCopiasPara,
                       rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

  // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
  return vRetorno;

 }
}

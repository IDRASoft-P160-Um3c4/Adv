package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDOficiosConcesiones.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Aturo Lopez Peña
 * @version 1.0
 */

public class TDCapasidadJuridica extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private String cMexico = VParametros.getPropEspecifica("EntidadRemplazaTexto");
  String[] cMex = cMexico.split(",");
  public TDCapasidadJuridica(){
  }
  public StringBuffer WordOficio(String cQuery){
  Vector vcCuerpo = new Vector();
  Vector vcCopiasPara = new Vector();
  Vector vRegs = new Vector();
  Vector vRegs1 = new Vector();
  Vector vRegs2 = new Vector();
  TWord rep = new TWord();
  int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0;
  String cAsunto="";

  String cRazonSolical="_____",cDomicilio="_____",cCodigoPost="_____",cEntiFed="_____",cRFC="_____",
                       cCiudadOficioReg="_____",cPuertoConsigna="_____",cPuertoResid="_____";
  String dtReconosimiento="_____",cRepresentante="_____",cDirector="_____",cNotaria="_____",
                          cNomNotario="_____",cPoder="_____",cColonia="_____",cMunicipio="_____",
                          cMandante="_____",cTipoPersona="_____",cOficinaAdscrita="_____",cNumSolicitud="_____";
  String[] cParametros = cQuery.split(",");
  int iFolioRPMN=0, iPartida=0,lNavAltura=0,lNavCabotaje=0;

  try{
    vRegs = super.FindByGeneric("",
        "SELECT " +
        "  CJ.IFOLIORPMN,CJ.IPARTIDA,PER.CNOMRAZONSOCIAL||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CRAZONSOCIAL, " +
        "  DOM.CCALLE||' '|| DOM.CNUMEXTERIOR||' '|| DOM.CNUMINTERIOR AS CDOMICILIO, DOM.CCODPOSTAL, EF.CNOMBRE AS CENTIDADFED, " +
        "  PER.CRFC,EFO.CNOMBRE AS cCIUDADOFICREG, PC.CDSCPUERTO AS cPUERTOCONSIGNA, CJ.LNAVEGALTURA,CJ.LNAVEGCABOTAJE,PR.CDSCPUERTO AS cPUERTORESID, " +
        "  cj.DTRECONOCIMIENTO, REP.CNOMRAZONSOCIAL||' '|| REP.CAPPATERNO||' '|| REP.CAPMATERNO AS CREPRESENTANTE, rs.iEjercicio, CJ.INUMSOLICITUD, " +
        "  cj.cPoder, cj.cNotaria, cj.cNomNotario, dom.cColonia, mun.CNOMBRE as cMunicipio,EF.ICVEENTIDADFED, per.iTipoPersona, ofa.CDSCBREVE as cOficinaAdscrita, CJ.cMandantes  "+
        "FROM MYRCAPACIDADJUR CJ " +
        "JOIN TRAREGSOLICITUD RS ON CJ.INUMSOLICITUD= RS.INUMSOLICITUD and CJ.IEJERCICIOSOLICITUD = RS.IEJERCICIO " +
        "JOIN GRLPERSONA PER ON PER.ICVEPERSONA = RS.ICVESOLICITANTE " +
        "JOIN GRLDOMICILIO DOM ON DOM.ICVEPERSONA = PER.ICVEPERSONA AND DOM.ICVEDOMICILIO =  RS.ICVEDOMICILIOSOLICITANTE " +
        "JOIN GRLPAIS PAI ON PAI.ICVEPAIS = DOM.ICVEPAIS " +
        "JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = DOM.ICVEPAIS and EF.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
        "JOIN GRLMUNICIPIO MUN ON MUN.ICVEPAIS = DOM.ICVEPAIS and MUN.ICVEENTIDADFED = DOM.ICVEENTIDADFED AND MUN.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO " +
        "JOIN GRLOFICINA OF ON OF.ICVEOFICINA = CJ.ICVEOFICINARPMN " +
        "JOIN GRLENTIDADFED EFO ON EFO.ICVEENTIDADFED = OF.ICVEENTIDADFED and EFO.ICVEPAIS = OF.ICVEPAIS " +
        "JOIN GRLPUERTO PC ON PC.ICVEPUERTO = CJ.ICVEPUERTOCONSIGNA " +
        "JOIN GRLPUERTO PR ON PR.ICVEPUERTO = CJ.ICVEPUERTORESIDENCIA " +
        "JOIN GRLPERSONA REP ON RS.ICVEREPLEGAL = REP.ICVEPERSONA " +
        "JOIN GRLOFICINA ofa on ofa.ICVEOFICINA = pc.ICVEOFICINAADSCRITA "+
        "WHERE CJ.ICONSECRECONOCIM ="+cParametros[0], dataSourceName);
  }catch(SQLException ex){
    cMensaje = ex.getMessage();
  }catch(Exception ex2){
    ex2.printStackTrace();
  }
  System.out.println("*****     dime que si entra!!");
  try{
    vRegs1 = super.FindByGeneric("",
        "SELECT do.CTITULAR FROM GRLDEPTOXOFIC do Join GRLOFICINA O on O.ICVEOFICINA = do.ICVEOFICINA join GRLDEPARTAMENTO d on d.ICVEDEPARTAMENTO = do.ICVEDEPARTAMENTO Where do.ICVEDEPARTAMENTO = 5 and do.ICVEOFICINA=1 ",dataSourceName);
  }catch(SQLException ex){
    cMensaje = ex.getMessage();
  }catch(Exception ex2){
    ex2.printStackTrace();
  }
if(vRegs1.size()>0){
    TVDinRep vDirector = (TVDinRep) vRegs1.get(0);
    cDirector=vDirector.getString("cTitular");
    rep.comRemplaza("[cNombreDirector]",cDirector);
  }
  rep.iniciaReporte();
  if (vRegs.size() > 0){
    TVDinRep vDatos = (TVDinRep) vRegs.get(0);
    TVDinRep vDirector = (TVDinRep) vRegs1.get(0);
    cDirector=vDirector.getString("cTitular");
    rep.comRemplaza("[cNombreDirector]",cDirector);
    cRazonSolical=vDatos.getString("CRAZONSOCIAL");
    cDomicilio=vDatos.getString("CDOMICILIO");
    cCodigoPost=vDatos.getString("CCODPOSTAL");
    cEntiFed=vDatos.getString("CENTIDADFED");
    if(Integer.parseInt(vDatos.getString("ICVEENTIDADFED")) == Integer.parseInt(cMex[1]))
      cMunicipio = "" + cMex[2];
    else cMunicipio=vDatos.getString("cMunicipio");
    cColonia=vDatos.getString("cColonia");
    cRFC=vDatos.getString("CRFC");
    cTipoPersona=vDatos.getString("iTipoPersona");

    cCiudadOficioReg=vDatos.getString("cCIUDADOFICREG");
    cPuertoConsigna=vDatos.getString("cPUERTOCONSIGNA");
    cPuertoResid=vDatos.getString("cPUERTORESID");
    dtReconosimiento=vDatos.getString("DTRECONOCIMIENTO");
    cRepresentante=vDatos.getString("CREPRESENTANTE");
    System.out.println("*****     1");
    if(!vDatos.getString("IFOLIORPMN").equals("null")){
      System.out.println("*****     "+vDatos.getString("IFOLIORPMN"));
      System.out.println("*****     "+vDatos.getInt("IFOLIORPMN"));
      iFolioRPMN = vDatos.getInt("IFOLIORPMN");
      iPartida = vDatos.getInt("IPARTIDA");
    }
    System.out.println("*****     2");
    lNavAltura=vDatos.getInt("LNAVEGALTURA");
    lNavCabotaje=vDatos.getInt("LNAVEGCABOTAJE");
    cNumSolicitud=vDatos.getInt("iEjercicio") + "/" + vDatos.getInt("INUMSOLICITUD");
    cNotaria=vDatos.getString("cNotaria");
    cPoder=vDatos.getString("cPoder");
    cNomNotario=vDatos.getString("cNomNotario");
    cOficinaAdscrita=vDatos.getString("cOficinaAdscrita");
/*
    try{
      vRegs2 = super.FindByGeneric("",
                           "SELECT per.CNOMRAZONSOCIAL||' '|| per.CAPPATERNO||' '|| per.CAPMATERNO As Mandnte FROM MYRMANDATOS man " +
                           "join grlpersona per On man.ICVEPERSONA= per.ICVEPERSONA " +
                           "Where man.ICONSECRECONOCIM = "+cParametros[0] ,dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      ex2.printStackTrace();
    }
  if(vRegs2.size()>0){
    int i=0;
    String cEspacio="";
    for(i = 0;i < vRegs2.size();i++){
      TVDinRep vMandante = (TVDinRep) vRegs2.get(i);
      if(i==vRegs2.size()-1) cEspacio=".";
      if(i==vRegs2.size()-2) cEspacio=" y ";
      if(i<vRegs2.size()-2) cEspacio=", ";
      cMandante += vMandante.getString("Mandnte")+cEspacio;
    }
  }
*/
    cMandante = vDatos.getString("cMandante");

    System.out.println("*****     Mandantes:"+vDatos.getString("cMandante"));

    rep.comRemplaza("[cNombreSol]",cRazonSolical);
    if(Integer.parseInt(cTipoPersona)==1){
      rep.comRemplaza("[cNombreSol]",cRepresentante);
      rep.comRemplaza("[cAgencia]","AGENTE: ");
    }else {
      rep.comRemplaza("[cNomRepL]"," la empresa  " + cRepresentante);
      rep.comRemplaza("[cAgencia]","AGENCIA: ");
    }
    rep.comRemplaza("[DomicilioSol]",cDomicilio);
    rep.comRemplaza("[CodPosSol]","C.P.  "+cCodigoPost);
    rep.comRemplaza("[EntidFedSol]",cEntiFed);
    rep.comRemplaza("[MunSol]",cMunicipio);
    rep.comRemplaza("[RFCSol]",cRFC);
    if(Integer.parseInt(cTipoPersona)!=1)  rep.comRemplaza("[cNomRepL]","Nombre del Rep. Legal:  "+cRepresentante);else rep.comRemplaza("[cNomRepL]","");
    rep.comRemplaza("[cNombreMandante]",cMandante);

    rep.comRemplaza("[cNomCesionaria]",cCiudadOficioReg);
    rep.comRemplaza("[cPuertoConsigna]",cPuertoConsigna);
    rep.comRemplaza("[cPuertoRes]",cPuertoResid);
    rep.comRemplaza("[dtReconosimiento]",dtReconosimiento);
    rep.comRemplaza("[cNomCesionaria]",cRepresentante);
    rep.comRemplaza("[cColonia]",cColonia);//[cCapitania]cOficinaAdscrita
    rep.comRemplaza("[cCapitania]",cOficinaAdscrita);
    rep.comRemplaza("[Folio]",""+iFolioRPMN);
    rep.comRemplaza("[Partida]",""+iPartida);//iNumSolicitud
    rep.comRemplaza("[iNumSolicitud]",""+cNumSolicitud);
    if(Integer.parseInt(cTipoPersona)!=1)  rep.comRemplaza("[cNotaria]","NOTARIOS ASOCIADOS: "+cNotaria);else rep.comRemplaza("[cNotaria]","");
    if(Integer.parseInt(cTipoPersona)!=1)  rep.comRemplaza("[cPoder]",""+"Poder No.  "+cPoder);else rep.comRemplaza("[cPoder]","");
    if(Integer.parseInt(cTipoPersona)!=1)  rep.comRemplaza("[cNomNotario]",""+cNomNotario);else rep.comRemplaza("[cNomNotario]","");
    if(lNavAltura==1 && lNavCabotaje==0)  rep.comRemplaza("[cTipoNavega]"," de altura");
    if(lNavAltura==0 && lNavCabotaje==1)  rep.comRemplaza("[cTipoNavega]"," de cabotaje ");
    if(lNavAltura==1 && lNavCabotaje==1)  rep.comRemplaza("[cTipoNavega]"," de altura y cabotaje ");
  }
  return rep.getEtiquetas(true);
 }
}

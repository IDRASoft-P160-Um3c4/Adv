package gob.sct.sipmm.dao.reporte;

import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.ingsw.TParametro;
import java.sql.Date;

/**
 * <p>Title: TDObtenDatos.java</p>
 * <p>Description: DAO de de consultas empleadas en generación de documentos generales</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDObtenDatos{
  String cSistema = "44";

  public TDDatosOficina dOficina;
  public TDDatosOficinaDepto dOficDepto;
  public TDDatosFolio dFolio;
  public TDDatosPersona dPersona;
  public TDDatosDeptoPadre dPadre;
  public TDDatosPMDP dPMDP;

  public TDObtenDatos(){
    dOficina = new TDDatosOficina();
    dOficDepto = new TDDatosOficinaDepto();
    dFolio = new TDDatosFolio();
    dPersona = new TDDatosPersona();
    dPadre = new TDDatosDeptoPadre();
    dPMDP = new TDDatosPMDP();
  }

  public Vector getOrganigrama(int iCveOficina, int iCveDepartamento, Vector vOrganigrama){
    TDDatosOficinaDepto dDatosOficDepto = new TDDatosOficinaDepto();
    TDDatosDeptoPadre   dDatosPadre     = new TDDatosDeptoPadre();

    dDatosOficDepto.setOficinaDepto(iCveOficina,iCveDepartamento);
    vOrganigrama.add(0, dDatosOficDepto.getDscDepartamento());
    dDatosPadre.setOficinaDeptoHijo(iCveOficina, iCveDepartamento);
    if (dDatosPadre.getCveDepartamento() > 0)
      vOrganigrama = getOrganigrama(iCveOficina, dDatosPadre.getCveDepartamento(), vOrganigrama);
    return vOrganigrama;
  }

  public class TDDatosDeptoPadre extends DAOBase{
    private TVDinRep vDato;
    private int iCveOficina, iCveDeptoHijo;

    public TDDatosDeptoPadre(){
      super.setSistema(cSistema);
    }

    private TVDinRep getDeptoPadre(){
      Vector vRegsDatos = new Vector();
      String cSql = "SELECT iCveOficina, iCveDepartamento, iCveDeptoHijo"+
                    " FROM GRLDEPTODEPEND"+
                    " WHERE ICVEOFICINA = " + iCveOficina +
                    "   AND ICVEDEPTOHIJO = " + iCveDeptoHijo;
      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }
    public void setOficinaDeptoHijo(int Oficina, int DeptoHijo){
      iCveOficina = Oficina;
      iCveDeptoHijo = DeptoHijo;
      vDato = this.getDeptoPadre();
    }
    public int getCveDepartamento(){
      return vDato.getInt("iCveDepartamento");
    }
  }

  public class TDDatosFolio extends DAOBase{
    private TVDinRep vDato;
    private int iEjercicio;
    private String cDigitosFolio;
    private int iConsecutivo;

    public TDDatosFolio(){
      super.setSistema(cSistema);
    }

    private TVDinRep getDatosFolio(){
      Vector vRegsDatos = new Vector();
      String cSql = "Select iEjercicio, cDigitosFolio, iConsecutivo, dtAsignacion, iCveUsuAsigna, "+
                    "cDirigidoA, cAsunto, cTitularFirma, dtEnvio, dtRecepcion, cNumOficialiaPartes, "+
                    "cNumControlGestion,dtCancelacion,iCveUsuCancela,cMotivoCancela,iCveOficina,iCveDepartamento "+
                    "from GRLFolio "+
                    "where iEjercicio = " + iEjercicio +
                    " and cDigitosFolio = '" + cDigitosFolio+
                    "' and iConsecutivo = " + iConsecutivo;
      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }
    public void setDatosFolio(String cNumFolio){
      String[] cDatosFolio = cNumFolio.split("/");
      String cTemp = cDatosFolio[0].replace('.', '/');
      String[] cDigitosTemp = cTemp.split("/");
      int Ejercicio = Integer.parseInt(cDatosFolio[cDatosFolio.length-1], 10);
      int Consecutivo = Integer.parseInt(cDigitosTemp[cDigitosTemp.length-1], 10);
      String Digitos = "";
      for(int i=0; i<cDigitosTemp.length-1; i++){
        Digitos += cDigitosTemp[i];
        if (i<cDigitosTemp.length-2)
          Digitos += ".";
      }
      iEjercicio = Ejercicio;
      cDigitosFolio = Digitos;
      iConsecutivo = Consecutivo;
      vDato = this.getDatosFolio();
    }
    public int getCveEjercicio(){
      return vDato.getInt("iEjercicio");
    }
    public String getCveDigitosFolio(){
      return vDato.getString("cDigitosFolio");
    }
    public int getCveConsecutivo(){
          return vDato.getInt("iConsecutivo");
    }
    public java.sql.Date getAsignacion(){
      return vDato.getDate("dtAsignacion");
    }
    public int getCveUsuAsigna(){
      return vDato.getInt("iCveUsuAsigna");
    }
    public String getDirigidoA(){
      return vDato.getString("cDirigidoA");
    }
    public String getAsunto(){
      return vDato.getString("cAsunto");
    }
    public String getTitularFirma(){
      return vDato.getString("cTitularFirma");
    }
    public java.sql.Date getEnvio(){
      return vDato.getDate("dtEnvio");
    }
    public java.sql.Date getRecepcion(){
      return vDato.getDate("dtRecepcion");
    }
    public String getNumOficialiaPartes(){
      return vDato.getString("cNumOficialiaPartes");
    }
    public String getNumControlGestion(){
      return vDato.getString("cNumControlGestion");
    }
    public java.sql.Date getCancelacion(){
      return vDato.getDate("dtCancelacion");
    }
    public int getUsuCancela(){
      return vDato.getInt("iCveUsuCancela");
    }
    public String getMotivoCancela(){
      return vDato.getString("cMotivoCancela");
    }
    public int getCveOficina(){
      return vDato.getInt("iCveOficina");
    }
    public int getCveDepartamento(){
      return vDato.getInt("iCveDepartamento");
    }
  }

  public class TDDatosOficina extends DAOBase{
    private TVDinRep vDato;
    private int iCveOficina;

    public TDDatosOficina(){
      super.setSistema(cSistema);
    }
    private TVDinRep getDatosOficina(){
      Vector vRegsDatos = new Vector();
      String cSql = "SELECT OF.cDscOficina,OF.cDscBreve,OF.iCveCategoria,OF.iCvePais,OF.iCveEntidadFed,"+
                    "  OF.iCveMunicipio,OF.cCalleYNo,OF.cColonia,OF.cCodPostal,OF.cTitular,OF.cTelefono,"+
                    "  OF.cCorreoE,OF.lVigente,P.cNombre AS cNomPais, P.cAbreviatura AS cAbrevPais,"+
                    "  EF.cNombre AS cNomEntidad, EF.cAbreviatura AS cAbrevEntidad,"+
                    "  MUN.cNombre AS cNomMunicipio, MUN.cAbreviatura AS cAbrevMunicipio,"+
                    "  CAT.cDscCategoria, OF.cCalleYNo || ' COL. ' || OF.cColonia || ', ' || MUN.cNombre || ' (' || EF.cAbreviatura ||'.)' || ' C.P. ' || OF.cCodPostal AS cDomicilio "+
                    "from GRLOficina OF "+
                    "  JOIN GRLCategoriaOfic CAT ON CAt.iCveCategoria = OF.iCveCategoria "+
                    "  JOIN GRLPais P ON P.iCvePais = OF.iCvePais "+
                    "  JOIN GRLEntidadFed EF ON EF.iCvePais = OF.iCvePais AND EF.iCveEntidadFed = OF.iCveEntidadFed"+
                    "  JOIN GRLMunicipio MUN ON MUN.iCvePais = OF.iCvePais AND MUN.iCveEntidadFed = OF.iCveEntidadFed AND MUN.iCveMunicipio = OF.iCveMunicipio "+
                    "where iCveOficina = " + iCveOficina;
      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }
    public void setOficina(int iOficina){
      iCveOficina = iOficina;
      vDato = this.getDatosOficina();
    }
    public String getDscOficina(){
      return (vDato == null)?"":(!vDato.getString("cDscOficina").equalsIgnoreCase("null"))?vDato.getString("cDscOficina"):"";
    }
    public String getDscBreve(){
      return (vDato == null)?"":(!vDato.getString("cDscBreve").equalsIgnoreCase("null"))?vDato.getString("cDscBreve"):"";
    }
    public int getCveCategoria(){
      return (vDato == null)?0:vDato.getInt("iCveCategoria");
    }
    public int getCvePais(){
      return (vDato == null)?0:vDato.getInt("iCvePais");
    }
    public int getCveEntidadFed(){
      return (vDato == null)?0:vDato.getInt("iCveEntidadFed");
    }
    public int getCveMunicipio(){
      return (vDato == null)?0:vDato.getInt("iCveMunicipio");
    }
    public String getCalleYNo(){
      return (vDato == null)?"":(!vDato.getString("cCalleYNo").equalsIgnoreCase("null"))?vDato.getString("cCalleYNo"):"";
    }
    public String getColonia(){
      return (vDato == null)?"":(!vDato.getString("cColonia").equalsIgnoreCase("null"))?vDato.getString("cColonia"):"";
    }
    public String getCodPostal(){
      return (vDato == null)?"":(!vDato.getString("cCodPostal").equalsIgnoreCase("null"))?vDato.getString("cCodPostal"):"";
    }
    public String getTitular(){
      return (vDato == null)?"":(!vDato.getString("cTitular").equalsIgnoreCase("null"))?vDato.getString("cTitular"):"";
    }
    public String getTelefono(){
      return (vDato == null)?"":(!vDato.getString("cTelefono").equalsIgnoreCase("null"))?vDato.getString("cTelefono"):"";
    }
    public String getCorreoE(){
      return (vDato == null)?"":(!vDato.getString("cCorreoE").equalsIgnoreCase("null"))?vDato.getString("cCorreoE"):"";
    }
    public int getVigente(){
      return (vDato == null)?0:vDato.getInt("lVigente");
    }
    public String getNomPais(){
      return (vDato == null)?"":(!vDato.getString("cNomPais").equalsIgnoreCase("null"))?vDato.getString("cNomPais"):"";
    }
    public String getAbrevPais(){
      return (vDato == null)?"":(!vDato.getString("cAbrevPais").equalsIgnoreCase("null"))?vDato.getString("cAbrevPais"):"";
    }
    public String getNomEntidad(){
      return (vDato == null)?"":(!vDato.getString("cNomEntidad").equalsIgnoreCase("null"))?vDato.getString("cNomEntidad"):"";
    }
    public String getAbrevEntidad(){
      return (vDato == null)?"":(!vDato.getString("cAbrevEntidad").equalsIgnoreCase("null"))?vDato.getString("cAbrevEntidad"):"";
    }
    public String getNomMunicipio(){
      return (vDato == null)?"":(!vDato.getString("cNomMunicipio").equalsIgnoreCase("null"))?vDato.getString("cNomMunicipio"):"";
    }
    public String getAbrevMunicipio(){
      return (vDato == null)?"":(!vDato.getString("cAbrevMunicipio").equalsIgnoreCase("null"))?vDato.getString("cAbrevMunicipio"):"";
    }
    public String getDscCategoria(){
      return (vDato == null)?"":(!vDato.getString("cDscCategoria").equalsIgnoreCase("null"))?vDato.getString("cDscCategoria"):"";
    }
    public String getDomicilio(){
      return (vDato == null)?"":(!vDato.getString("cDomicilio").equalsIgnoreCase("null"))?vDato.getString("cDomicilio"):"";
    }
}

  public class TDDatosOficinaDepto extends DAOBase{
    private TVDinRep vDato;
    public TDDatosOficina vDatoOfic;
    private int iCveOficina;
    private int iCveDepto;

    public TDDatosOficinaDepto(){
      super.setSistema(cSistema);
      vDatoOfic = new TDDatosOficina();
    }
    private TVDinRep getDatosOficinaDepto(){
      Vector vRegsDatos = new Vector();
      String cSql = "SELECT cTitular, cTelefono, cCorreoE, cPuestoTitular, cDscDepartamento, cDscBreve "+
                    " FROM GRLDeptoXOfic "+
                    " JOIN GRLDepartamento ON GRLDepartamento.iCveDepartamento = GRLDeptoXOfic.iCveDepartamento "+
                    " WHERE GRLDeptoXOfic.iCveOficina = " + iCveOficina + " "+
                    "   AND GRLDeptoXOfic.iCveDepartamento = " + iCveDepto;
      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return null;
    }
    public void setOficinaDepto(int iOficina, int iDepto){
      iCveOficina = iOficina;
      iCveDepto   = iDepto;
      vDatoOfic.setOficina(iCveOficina);
      vDato = this.getDatosOficinaDepto();
    }
    public String getTitular(){
      return (vDato == null)?"":(!vDato.getString("cTitular").equalsIgnoreCase("null"))?vDato.getString("cTitular"):"";
    }
    public String getTelefono(){
      return (vDato == null)?"":(!vDato.getString("cTelefono").equalsIgnoreCase("null"))?vDato.getString("cTelefono"):"";
    }
    public String getCorreoE(){
      return (vDato == null)?"":(!vDato.getString("cCorreoE").equalsIgnoreCase("null"))?vDato.getString("cCorreoE"):"";
    }
    public String getPuestoTitular(){
      return (vDato == null)?"":(!vDato.getString("cPuestoTitular").equalsIgnoreCase("null"))?vDato.getString("cPuestoTitular"):"";
    }
    public String getDscDepartamento(){
      return (vDato == null)?"":(!vDato.getString("cDscDepartamento").equalsIgnoreCase("null"))?vDato.getString("cDscDepartamento"):"";
    }
    public String getDscBreve(){
      return (vDato == null)?"":(!vDato.getString("cDscBreve").equalsIgnoreCase("null"))?vDato.getString("cDscBreve"):"";
    }
  }

  public class TDDatosPersona extends DAOBase{
    private TVDinRep vDato;
    private int iCvePersona;
    private int iCveDomicilio;

    public TDDatosPersona(){
      super.setSistema(cSistema);
    }
    private TVDinRep getDatosPersona(){
      Vector vRegsDatos = new Vector();
      String cSql = "SELECT P.iCvePersona, P.cRFC, P.cRPA, P.iTipoPersona, P.cNomRazonSocial, P.cApPaterno, P.cApMaterno, P.cCorreoE,"+
                    " P.cPseudonimoEmp, P.cNomRazonSocial || ' ' || P.cApPaterno  || ' ' || P.cApMaterno AS cNomCompleto";
      if (iCveDomicilio > 0)
        cSql +=     ", D.iCveDomicilio, D.iCveTipoDomicilio, D.cCalle, D.cNumExterior, D.cNumInterior, D.cColonia,"+
                    " D.cCodPostal, D.cTelefono, D.iCvePais, D.iCveEntidadFed, D.iCveMunicipio, D.iCveLocalidad, D.lPredeterminado,"+
                    " PA.cNombre AS cNomPais, PA.cAbreviatura AS cAbrevPais,"+
                    " EF.cNombre AS cNomEntidad, EF.cAbreviatura AS cAbrevEntidad,"+
                    " MUN.cNombre AS cNomMunicipio, MUN.cAbreviatura AS cAbrevMunicipio,"+
                    " LOC.cNombre AS cNomLocalidad, LOC.cAbreviatura AS cAbrevLocalidad,"+
                    " TD.cDscTipoDomicilio ";
      cSql +=       " FROM GRLPersona P ";
      if (iCveDomicilio > 0)
        cSql +=     " JOIN GRLDomicilio D ON D.iCvePersona = P.iCvePersona "+
                    " JOIN GRLPais PA ON PA.iCvePais = D.iCvePais "+
                    " JOIN GRLEntidadFed EF ON EF.iCvePais = D.iCvePais AND EF.iCveEntidadFed = D.iCveEntidadFed "+
                    " JOIN GRLMunicipio MUN ON MUN.iCvePais = D.iCvePais AND MUN.iCveEntidadFed = D.iCveEntidadFed AND MUN.iCveMunicipio = D.iCveMunicipio "+
                    " left JOIN GRLLocalidad LOC ON LOC.iCvePais = D.iCvePais AND LOC.iCveEntidadFed = D.iCveEntidadFed AND LOC.iCveMunicipio = D.iCveMunicipio AND LOC.iCveLocalidad = d.iCveLocalidad "+
                    " JOIN GRLTipoDomicilio TD ON TD.iCveTipoDomicilio = D.iCveTipoDomicilio";
      cSql+=        " Where P.iCvePersona = " + iCvePersona;
      if (iCveDomicilio > 0)
        cSql +=     "   AND D.iCveDomicilio = " + iCveDomicilio;
      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }
    public void setPersona(int iPersona, int iDomicilio){
      iCvePersona = iPersona;
      iCveDomicilio = iDomicilio;
      vDato = getDatosPersona();
    }
    public String getRFC(){
      return vDato.getString("cRFC");
    }
    public String getRPA(){
      return vDato.getString("cRPA");
    }
    public int getTipoPersona(){
      return vDato.getInt("iTipoPersona");
    }
    public String getNomRazonSocial(){
      return vDato.getString("cNomRazonSocial");
    }
    public String getApPaterno(){
      return vDato.getString("cApPaterno");
    }
    public String getApMaterno(){
      return vDato.getString("cApMaterno");
    }
    public String getCorreoE(){
      return vDato.getString("cCorreoE");
    }
    public String getPseudonimoEmp(){
      return vDato.getString("cPseudonimoEmp");
    }
    public String getNomCompleto(){
      return vDato.getString("cNomCompleto");
    }
    public int getCveTipoDomicilio(){
      if (iCveDomicilio > 0)
        return vDato.getInt("iCveTipoDomicilio");
      else
        return 0;
    }
    public String getCalle(){
      if (iCveDomicilio > 0)
        return vDato.getString("cCalle");
      else
        return "";
    }
    public String getNumExterior(){
      if (iCveDomicilio > 0)
        return vDato.getString("cNumExterior");
      else
        return "";
    }
    public String getNumInterior(){
      if (iCveDomicilio > 0)
        return vDato.getString("cNumInterior");
      else
        return "";
    }
    public String getColonia(){
      if (iCveDomicilio > 0)
        return vDato.getString("cColonia");
      else
        return "";
    }
    public String getCodPostal(){
      if (iCveDomicilio > 0)
        return vDato.getString("cCodPostal");
      else
        return "";
    }
    public String getTelefono(){
      if (iCveDomicilio > 0)
        return vDato.getString("cTelefono");
      else
        return "";
    }
    public int getCvePais(){
      if (iCveDomicilio > 0)
        return vDato.getInt("iCvePais");
      else
        return 0;
    }
    public int getCveEntidadFed(){
      if (iCveDomicilio > 0)
        return vDato.getInt("iCveEntidadFed");
      else
        return 0;
    }
    public int getCveMunicipio(){
      if (iCveDomicilio > 0)
        return vDato.getInt("iCveMunicipio");
      else
        return 0;
    }
    public int getCveLocalidad(){
      if (iCveDomicilio > 0)
        return vDato.getInt("iCveLocalidad");
      else
        return 0;
    }
    public int getPredeterminado(){
      if (iCveDomicilio > 0)
        return vDato.getInt("lPredeterminado");
      else
        return 0;
    }
    public String getNomPais(){
      if (iCveDomicilio > 0)
        return vDato.getString("cNomPais");
      else
        return "";
    }
    public String getAbrevPais(){
      if (iCveDomicilio > 0)
        return vDato.getString("cAbrevPais");
      else
        return "";
    }
    public String getNomEntidad(){
      if (iCveDomicilio > 0)
        return vDato.getString("cNomEntidad");
      else
        return "";
    }
    public String getAbrevEntidad(){
      if (iCveDomicilio > 0)
        return vDato.getString("cAbrevEntidad");
      else
        return "";
    }
    public String getNomMunicipio(){
      if (iCveDomicilio > 0)
        return vDato.getString("cNomMunicipio");
      else
        return "";
    }
    public String getAbrevMunicipio(){
      if (iCveDomicilio > 0)
        return vDato.getString("cAbrevMunicipio");
      else
        return "";
    }
    public String getNomLocalidad(){
      if (iCveDomicilio > 0)
        return vDato.getString("cNomLocalidad");
      else
        return "";
    }
    public String getAbrevLocalidad(){
      if (iCveDomicilio > 0)
        return vDato.getString("cAbrevLocalidad");
      else
        return "";
    }
    public String getDscTipoDomicilio(){
      if (iCveDomicilio > 0)
        return vDato.getString("cDscTipoDomicilio");
      else
        return "";
    }
  }

  public class TDDatosPMDP extends DAOBase{
    private TVDinRep vDato;
    private int iCvePMDP;

    public TDDatosPMDP(){
      super.setSistema(cSistema);
    }

    private TVDinRep getDatosPMDP(){
      Vector vRegsDatos = new Vector();
      String cSql = "SELECT " +
          "PMDP.ICVEPMDP as iPMDP, " +
          "PMDP.IEJERCICIOSOLICITUD as iEjercicioSol, " +
          "PMDP.INUMSOLICITUD as iSolicitud, " +
          "PMDP.CNUMOFICIOSOLICITUDPMDP as cNumOficioSol, " +
          "PMDP.DTOFICIOSOLICITUDPMDP as dtOficioSol, " +
          "PMDP.DTINIVIGENCIAPMDP as dtIniVig, " +
          "PMDP.DTFINVIGENCIAPMDP as dtFinVig, " +
          "PMDP.CNUMOFICIOAUTORIZACIONPMDP as cNumOficioAutorizacion, " +
          "PMDP.DTAUTORIZACIONPMDP as dtAutorizacion, " +
          "PMDP.CCOMENTARIOSBREVESPMDP as cComentarios, " +
          "PMDP.DTPUBLICACIONDOF as dtPublicacion, " +
          "PMDP.CNUMOFICIALIASOLICITUD as cNumOficSol, " +
          "TRAREG.ICVESOLICITANTE as iSolicitante, " +
          "TRAREG.ICVEDOMICILIOSOLICITANTE as iDomSolicitante, " +
          "TRAREG.ICVEREPLEGAL as iRepLegal, " +
          "TRAREG.ICVEDOMICILIOREPLEGAL as iDomRepLegal, " +
          "TRAREG.ICVEOFICINA as iOficina, " +
          "TRAREG.ICVEDEPARTAMENTO as iDepto " +
          "FROM DPOPROGMAESTRODESPORT PMDP " +
          "JOIN TRAREGSOLICITUD TRAREG ON PMDP.IEJERCICIOSOLICITUD = TRAREG.IEJERCICIO " +
          "AND PMDP.INUMSOLICITUD = TRAREG.INUMSOLICITUD " +
          "where ICVEPMDP = "+iCvePMDP;

      try{
         vRegsDatos=super.FindByGeneric("",cSql,super.dataSourceName);
      }catch(SQLException ex){
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        cMensaje += ex2.getMessage();
      }
      if (vRegsDatos.size() > 0)
        return (TVDinRep) vRegsDatos.get(0);
      else
        return new TVDinRep();
    }
    public void setCvePMDP(int iCvePMDPFiltro){
      iCvePMDP = iCvePMDPFiltro;
      vDato = this.getDatosPMDP();
    }
    public int getEjercicio(){
      return vDato.getInt("iEjercicioSol");
    }
    public int getSolicitud(){
      return vDato.getInt("iSolicitud");
    }
    public String getNumOficioSolicitud(){
      return vDato.getString("cNumOficioSol");
    }
    public Date getDateOficioSolicitud(){
      return vDato.getDate("dtOficioSol");
    }
    public Date getDateIniVigencia(){
      return vDato.getDate("dtIniVig");
    }
    public Date getDateFinVigencia(){
      return vDato.getDate("dtFinVig");
    }
    public String getNumOficioAutorizacion(){
      return vDato.getString("cNumOficioAutorizacion");
    }
    public Date getDateAutorizacion(){
      return vDato.getDate("dtAutorizacion");
    }
    public String getComentarios(){
      return vDato.getString("cComentarios");
    }
    public Date getDatePublicacion(){
      return vDato.getDate("dtPublicacion");
    }
    public String getNumOficialiaSol(){
      return vDato.getString("cNumOficSol");
    }
    public int getCveSolicitante(){
      return vDato.getInt("iSolicitante");
    }
    public int getCveDomSolicitante(){
      return vDato.getInt("iDomSolicitante");
    }
    public int getCveRepLegal(){
      return vDato.getInt("iRepLegal");
    }
    public int getCveDomRepLegal(){
      return vDato.getInt("iDomRepLegal");
    }
    public int getCveOficina(){
      return vDato.getInt("iOficina");
    }
    public int getCveDepto(){
      return vDato.getInt("iDepto");
    }
  }

}

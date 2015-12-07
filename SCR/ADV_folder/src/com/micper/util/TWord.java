package com.micper.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author LSC. Armando Barrientos Martínez
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 * Forma de llamar:    TWord tWord = new TWord();
 *                     tWord.comRemplazaComun(Strin cadenaBuscar, String cadenaRemplazarPor);
 *                     tWord.getEtiquetas(true/false);
 */
import java.util.*;

public class TWord{
  private int iMaxLen = 250;
  private String cDelim = "|";
  private String cNuevaLinea = "";
  private StringBuffer sbBuscar = null;
  private StringBuffer sbRemplazar = null;
  private Vector vDatosTablas = null;

  public TWord(){
    this.sbBuscar = new StringBuffer();
    this.sbRemplazar = new StringBuffer();
  }

  public void iniciaReporte(){
    this.sbBuscar = new StringBuffer("");
    this.sbRemplazar = new StringBuffer("");
    this.vDatosTablas = new Vector();
  }

  public StringBuffer getEtiquetas(boolean lPiePagina){
    return this.getEtiquetas(lPiePagina,"01");
  }

public Vector getVectorDatos(boolean lPiePagina){
    Vector vDatos = new Vector();
    vDatos.add( (StringBuffer)this.getEtiquetas(lPiePagina));
    vDatos.add( (Vector)this.getDatosTablas());
    return vDatos;
  }

  public StringBuffer getEtiquetasBuscar(){
    return this.sbBuscar;
  }

  public StringBuffer getEtiquetasRemplazar(){
    return this.sbRemplazar;
  }

  public Vector getDatosTablas(){
    return this.vDatosTablas;
  }

  public void anexaEtiquetas(StringBuffer sbBuscaAdicional,StringBuffer sbRemplazaAdicional){
    sbBuscar.append(sbBuscaAdicional);
    sbRemplazar.append(sbRemplazaAdicional);
  }

  public StringBuffer getEtiquetas(boolean lPiePagina,String paramNum){
    StringBuffer sbRes = new StringBuffer("");
    sbRes.append("\n<PARAM NAME=\"TEXTOB" + paramNum + "\" VALUE=\"" + this.sbBuscar + "\">")
        .append("\n<PARAM NAME=\"TEXTOR" + paramNum + "\" VALUE=\"" + this.sbRemplazar + "\">");
    if(lPiePagina)
      sbRes.append("\n<PARAM NAME=\"URUN\" VALUE=\"YES\">");
    return sbRes;
  }

 public Vector getVectorDatos(boolean lPiePagina,String paramNum){
    Vector vDatos = new Vector();
    vDatos.add( (StringBuffer)this.getEtiquetas(lPiePagina, paramNum));
    vDatos.add( (Vector)this.getDatosTablas());
    return vDatos;
  }

  public void comRemplaza(String cEtiqueta,String cValor){
    this.comRemplazaVerifica(cEtiqueta,cValor,true);
  }

public void comRemplazaVerifica(String cEtiqueta,String cValor,boolean lVerificaLong){
    if(cValor.length() > iMaxLen && lVerificaLong){
      String cEtiquetaTemp = "",cValTemp = cValor;
      Vector vValores = new Vector();

      while(cValTemp.length() > iMaxLen){
        vValores.add(cValTemp.substring(0,iMaxLen));
        cValTemp = cValTemp.substring(iMaxLen);
      }
      vValores.add(cValTemp);

      for(int i = 0;i < vValores.size();i++){
        cEtiquetaTemp += cEtiqueta.substring(0,cEtiqueta.length() - 1) + "_" + i + cEtiqueta.substring(cEtiqueta.length() - 1);
      }
      this.comRemplazaVerifica(cEtiqueta,cEtiquetaTemp,false);

      for(int i = 0;i < vValores.size();i++)
        this.comRemplazaVerifica(cEtiqueta.substring(0,cEtiqueta.length() - 1) + "_" + i + cEtiqueta.substring(cEtiqueta.length() - 1),vValores.get(i).toString(),false);
    } else{
      this.sbBuscar.append(cEtiqueta + cDelim);
      this.sbRemplazar.append(cValor + cDelim);
    }
  }

  public void comRemplaza(String cEtiqueta,String cValor,boolean lNuevaLinea){
    if(lNuevaLinea)
      this.comRemplaza(cEtiqueta,cValor + cNuevaLinea);
    else
      this.comRemplaza(cEtiqueta,cValor);
  }

  public void escribeParrafos(String cEtiquetaBase,Vector vcParrafos,String cTextoAnterior,String cTextoPosterior){
    String cEtiquetaTemp = "";

    if(vcParrafos != null)
      if(vcParrafos.size() > 0){
        if(cTextoAnterior != null && !cTextoAnterior.equals(""))
          cEtiquetaTemp += "[" + cEtiquetaBase + "Pre]";
        for(int i = 0;i < vcParrafos.size();i++)
          cEtiquetaTemp += "[" + cEtiquetaBase + i + "] ";
        if(cTextoPosterior != null && !cTextoPosterior.equals(""))
          cEtiquetaTemp += "[" + cEtiquetaBase + "Pos]";
        this.comRemplaza("[" + cEtiquetaBase + "]",cEtiquetaTemp);

        if(cTextoAnterior != null && !cTextoAnterior.equals(""))
          this.comRemplaza("[" + cEtiquetaBase + "Pre]",cTextoAnterior,true);
        for(int i = 0;i < vcParrafos.size();i++)
          this.comRemplaza("[" + cEtiquetaBase + i + "]",vcParrafos.get(i).toString(),true);
        if(cTextoPosterior != null && !cTextoPosterior.equals(""))
          this.comRemplaza("[" + cEtiquetaBase + "Pos]",cTextoPosterior,true);
      } else
        this.comRemplaza("[" + cEtiquetaBase + "]","");
    else
      this.comRemplaza("[" + cEtiquetaBase + "]","");
  }

public void comEligeTabla(String cIdTabla){
    this.vDatosTablas.add("SELBOOKMARK('" + cIdTabla + "')");
  }

public void comAgregaRenglonTabla(int iNumRenglones){
    this.vDatosTablas.add("ADDTABLEROWS(" + iNumRenglones + ")");
  }

public void comDespliegaCelda(String cValorCelda){
    this.vDatosTablas.add("ADDCELL('" + cValorCelda + "')");
  }

public void comDespliegaCeldaApostrofe(String cValorCelda){
    this.vDatosTablas.add("ADDCELL(\"" + cValorCelda + "\")");
  }
}

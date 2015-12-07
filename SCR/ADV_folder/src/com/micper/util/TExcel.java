package com.micper.util;

// por implementar: Saltos de Página | Inserta Imagen | etc.

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 * Forma de llamar:    TExcel vExcel = new TExcel();
 *                     vExcel.comAbreLibro();
 *                     vExcel.comAlineaRango();
 *                     vExcel.comAlineaRangoVer();
 *                     vExcel.comAltoReng();
 *                     vExcel.comBordeRededor();
 *                     vExcel.comBordeTotal();
 *                     vExcel.comCellFormat();
 *                     vExcel.comCopiaHoja();
 *                     vExcel.comCreaFormula();
 *                     vExcel.comDeleteCols();
 *                     vExcel.comDespliega();
 *                     vExcel.comEligeHoja();
 *                     vExcel.comFillCells();
 *                     vExcel.comFontBold();
 *                     vExcel.comFontColor();
 *                     vExcel.comFontNormal();
 *                     vExcel.comFontSize();
 *                     vExcel.comReplace();
 *                     vExcel.comTamanioFontGral();
 */

import com.micper.ingsw.*;

public class TExcel{
  private String cDelim = "|";
  private StringBuffer sbDespliega = null;
  private String AT_HIZQUIERDA = "IZQUIERDA";
  private String AT_HCENTRO = "CENTRO";
  private String AT_HDERECHA = "DERECHA";
  private String AT_HJUSTIFICA = "JUSTIFICA";
  private String AT_HCENTRO_SELECCION = "CENTRO_SELECCION";
  private String AT_COMBINA_CENTRO = "COMBINA_CENTRO";
  private String AT_COMBINA_IZQUIERDA = "COMBINA_IZQUIERDA";
  private String AT_COMBINA_DERECHA = "COMBINA_DERECHA";
  private String AT_DESCOMBINA = "DESCOMBINA";
  private String AT_VSUPERIOR = "SUPERIOR";
  private String AT_VCENTRO = "CENTRO";
  private String AT_VINFERIOR = "INFERIOR";
  private String AT_VJUSTIFICAR = "JUSTIFICAR";
  private String AT_VAJUSTAR = "AJUSTAR";
  private String AT_POSINICIAL = "Inicio";
  private String AT_POSFINAL = "Final";
  private String excel_cRutaDest = "";

  public TExcel(){
    this.sbDespliega = new StringBuffer("");
    TParametro vParametros = new TParametro("44");
    this.excel_cRutaDest = vParametros.getPropEspecifica("ExcelRutaDest");
  }

  public void iniciaReporte(){
    this.sbDespliega = new StringBuffer("");
  }

  public void comImprimir(int iNumCopias,int iTiempoEspera){
    if(iNumCopias < 1)
      iNumCopias = 1;
    for(int i = 1;i <= iNumCopias;i++){
      this.sbDespliega.append("\nIMPRIMIR=SI");
      if(iTiempoEspera > 0)
        this.comDelay(iTiempoEspera);
    }
  }

  public void comImprimir(int iNumCopias){
    this.comImprimir(iNumCopias,0);
  }

  public void comDelay(int iTiempoEspera){
    if(iTiempoEspera > 0)
      this.sbDespliega.append("\nDELAY=" + iTiempoEspera);
  }

  public void comDespliega(String cCol,int iReng,String cValor){
    this.sbDespliega.append("\nDESPLIEGA=" + cCol + cDelim + iReng + cDelim + cValor);
  }

  public void comAlineaRango(String cColIni,int iRengIni,String cColFin,int iRengFin,String cAlineacion){
    // IZQUIERDA|CENTRO|DERECHA|JUSTIFICA|CENTRO_SELECCION|DESCOMBINA|
    // COMBINA_CENTRO|COMBINA_IZQUIERDA|COMBINA_DERECHA|COMBINA_JUSTIFICA
    this.sbDespliega.append("\nALINEARANGO=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + cAlineacion);
  }

  public void comDespliegaAlineado(String cCol, int iReng, String cValor, boolean lBold, String cAlineaH, String cAlineaV){
    this.comDespliegaAlineado(cCol, iReng, cCol, iReng, (cValor==null)?"null":cValor, lBold, cAlineaH, cAlineaV);
  }

  public void comDespliegaAlineado(String cColIni, int iRengIni, String cColFin, int iRengFin, String cValor, boolean lBold, String cAlineaH, String cAlineaV){
    if (!cAlineaH.equals(""))
      this.comAlineaRango(cColIni, iRengIni, cColFin, iRengFin, cAlineaH);
    if (!cAlineaV.equals(""))
      this.comAlineaRangoVer(cColIni, iRengIni, cColFin, iRengFin, cAlineaV);
    if (lBold)
      this.comFontBold(cColIni, iRengIni, cColFin, iRengFin);
    this.comDespliega(cColIni, iRengIni, (cValor==null)?"null":cValor);
  }

  public void comDespliegaCombinado(String cValor, String cColIni, int iRengIni, String cColFin, int iRengFin, String cAlineacionH, String cAlineacionV, boolean lBold, int iColorFondo, boolean lBorde, boolean lBordeTotal, int iTipoBorde, int iColorBorde){
    this.comAlineaRango(cColIni, iRengIni, cColFin, iRengFin, cAlineacionH);
    if (!cAlineacionV.equals(""))
      this.comAlineaRangoVer(cColIni, iRengIni, cColFin, iRengFin, cAlineacionV);
    if (lBold)
      this.comFontBold(cColIni, iRengIni, cColFin, iRengFin);
    if (iColorFondo >= 0)
      this.comFillCells(cColIni, iRengIni, cColFin, iRengFin, iColorFondo);
    if (lBorde)
      if (lBordeTotal)
        this.comBordeTotal(cColIni, iRengIni, cColFin, iRengFin, iTipoBorde);
      else
        this.comBordeRededor(cColIni, iRengIni, cColFin, iRengFin, iTipoBorde, iColorBorde);
    this.comDespliega(cColIni, iRengIni, (cValor==null)?"null":cValor);
  }

  public void comAlineaRangoVer(String cColIni,int iRengIni,String cColFin, int iRengFin,String cAlineacion){
    // SUPERIOR|CENTRO|INFERIOR|JUSTIFICAR|AJUSTAR
    this.sbDespliega.append("\nALINEARANGOVER=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + cAlineacion);
  }

  public void comReplace(String cValBuscar,String cValAsignar){
    this.sbDespliega.append("\nREPLACE=" + cValBuscar + cDelim + cValAsignar);
  }

  public void comAltoReng(String cCol,int iReng,float fAlto){
    this.sbDespliega.append("\nALTORENGLON=" + cCol + cDelim + iReng + cDelim + fAlto);
  }

  public void comBordeRededor(String cColIni,int iRengIni,String cColFin, int iRengFin,int iTipo,int iColor){
    this.sbDespliega.append("\nBORDEREDEDOR=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + iTipo + cDelim + iColor);
  }

  public void comBorde(String cColIni,int iRengIni,String cColFin,int iRengFin, int iPosicion,int iEstiloLinea,int iPeso,int iColor){
//    this.sbDespliega.append("\nBORDES=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + iPosicion + cDelim + iEstiloLinea + cDelim + iPeso + cDelim + iColor );
  }

  public void comBordeTotal(String cColIni,int iRengIni,String cColFin, int iRengFin,int iTipo){
    this.sbDespliega.append("\nBORDETOTAL=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + iTipo);
  }

  public void comCellFormat(String cColIni,int iRengIni,String cColFin, int iRengFin,String cFormato){
    this.sbDespliega.append("\nCELLFORMAT=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + cFormato);
  }

  public void comCreaFormula(String cCol,int iReng,String cFormula){
    this.sbDespliega.append("\nCREAFORMULA=" + cCol + cDelim + iReng + cDelim + cFormula);
  }

  public void comDeleteCols(String cColIni,int iRengIni,String cColFin, int iRengFin){
    this.sbDespliega.append("\nDELETECELDAS=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin);
  }

  public void comAbreLibro(String cArchivo,boolean lVisible){
    this.sbDespliega.append("\nABRELIBRO=" + cArchivo);
    this.sbDespliega.append(cDelim).append((lVisible)?"1":"0");
  }

  public void comGuardarLibro(String cNomArchivo){
    this.sbDespliega.append("\nGUARDARCOMO=" + excel_cRutaDest + cNomArchivo + ".xls");
  }

  public void comCopiaHoja(int iNumHoja,String cPosicion,String cNombre){
    String cValor = (cNombre.length() > 30)?cNombre.substring(1,30):cNombre;
    this.sbDespliega.append("\nCOPIAHOJA=" + iNumHoja + cDelim + cPosicion + cDelim + cValor);
  }

  public void comEligeHoja(int iNumHoja){
    this.sbDespliega.append("\nELIGEHOJA=" + iNumHoja);
  }

  public void comTamanioFontGral(int iTamanio){
    this.sbDespliega.append("\nTAMANIOFONTGRAL=" + iTamanio);
  }

  public void comFillCells(String cColIni,int iRengIni,String cColFin,int iRengFin,int iColor){
    this.sbDespliega.append("\nFILLCELLS=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + iColor);
  }

  public void comFontBold(String cColIni,int iRengIni,String cColFin,int iRengFin){
    this.sbDespliega.append("\nFONTBOLD=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin);
  }

  public void comFontColor(String cColIni,int iRengIni,String cColFin, int iRengFin,int iColor){
    this.sbDespliega.append("\nFONTCOLOR=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + iColor);
  }

  public void comFontNormal(String cColIni,int iRengIni,String cColFin, int iRengFin){
    this.sbDespliega.append("\nFONTNORMAL=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin);
  }

  public void comFontSize(String cColIni,int iRengIni,String cColFin, int iRengFin,float dTamanio){
    this.sbDespliega.append("\nFONTSIZE=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + String.valueOf(dTamanio));
  }

  public void comRotar(String cColIni,int iRengIni,String cColFin,int iRengFin, int iGrados){
    this.sbDespliega.append("\nROTAR=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + iGrados);
  }

  public void comSaltosPagina(String cSaltosPag){
    //this.sbDespliega.append("\nSALTOSPAGINA=" + cSaltosPag);
  }

  public void comInsertaImagen(String cCol, int iReng, String cRutaURL, int iAnchoPixel, int iAltoPixel, int iColorBorde, int iAnchoBorde){
    this.comInsertaImagen(cCol, iReng, cCol, iReng, cRutaURL, iAnchoPixel, iAltoPixel, iColorBorde, iAnchoBorde);
  }
  public void comInsertaImagen(String cColIni, int iRengIni, String cColFin, int iRengFin, String cRutaURL, int iAnchoPixel, int iAltoPixel, int iColorBorde, int iAnchoBorde){
    this.sbDespliega.append("\nINSERTAIMAGEN=" + cColIni + cDelim + iRengIni + cDelim + cColFin + cDelim + iRengFin + cDelim + cRutaURL + cDelim + iAnchoPixel + cDelim + iAltoPixel + cDelim + iColorBorde + cDelim + iAnchoBorde + cDelim);
  }

  public String getAT_COMBINA_CENTRO(){
    return AT_COMBINA_CENTRO;
  }

  public String getAT_COMBINA_DERECHA(){
    return AT_COMBINA_DERECHA;
  }

  public String getAT_COMBINA_IZQUIERDA(){
    return AT_COMBINA_IZQUIERDA;
  }

  public String getAT_DESCOMBINA(){
    return AT_DESCOMBINA;
  }

  public String getAT_HCENTRO(){
    return AT_HCENTRO;
  }

  public String getAT_HDERECHA(){
    return AT_HDERECHA;
  }

  public String getAT_HIZQUIERDA(){
    return AT_HIZQUIERDA;
  }

  public String getAT_HJUSTIFICA(){
    return AT_HJUSTIFICA;
  }

  public String getAT_HCENTRO_SELECCION(){
    return AT_HCENTRO_SELECCION;
  }

  public String getAT_VCENTRO(){
    return AT_VCENTRO;
  }

  public String getAT_VSUPERIOR(){
    return AT_VSUPERIOR;
  }

  public String getAT_VINFERIOR(){
    return AT_VINFERIOR;
  }

  public String getAT_VAJUSTAR(){
    return AT_VAJUSTAR;
  }

  public String getAT_VJUSTIFICAR(){
    return AT_VJUSTIFICAR;
  }

  public String getAT_POSFINAL(){
    return AT_POSFINAL;
  }

  public String getAT_POSINICIAL(){
    return AT_POSINICIAL;
  }

  public StringBuffer getSbDatos(){
    StringBuffer sbTemp = new StringBuffer(sbDespliega.toString());
    sbDespliega = new StringBuffer("");
    return sbTemp;
  }
}

package com.micper.util;

import java.text.*;

/**
 * Clase para manejo de fechas: generación, today, y cadenas de despliegue.
 * <p>Ingeniería de Software generada en JAVA (J2EE).
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>LSC. Rafael Miranda Blumenkron
 * <p>
 * @see <small><a href="./../ingsample/pg9902041CFG.html">com.micper.ingsample.pg9902041CFG</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="TFechas.png">
 */

public class TNumeros {

  /** Constructor por omisión de la clase. */
  public TNumeros(){
  }

  /**
   * Método encargado de regresar un número formateado como cadena para mostrar x Número de dígitos.
   * @param iNumero Número que se desea obtener con formato.
   * @param iNumDigitos Número de dígitos que se desea obtener en el resultado.
   * @return Cadena de texto correspondiente al número con formato
   */
  public String getNumeroSinSeparador(Integer iNumero, int iNumDigitos){
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMinimumIntegerDigits(iNumDigitos);
    nf.setMaximumIntegerDigits(iNumDigitos);
    nf.setGroupingUsed(false);
    return nf.format(iNumero);
  }

  public String getNumeroDecimal(Double dNumero, int iNumDecimales, boolean lPesos){
    return this.getNumeroDecimal(dNumero.doubleValue(), iNumDecimales, lPesos);
  }

  public String getNumeroDecimal(long dNumero, int iNumDecimales, boolean lPesos){
    return this.getNumeroDecimal(Double.valueOf(dNumero + ""), iNumDecimales, lPesos);
  }

  public String getNumeroDecimal(double dNumero, int iNumDecimales, boolean lPesos){
    return getNumeroDecimal(dNumero, iNumDecimales, lPesos, 0);
  }

  public String getNumeroDecimal(double dNumero, int iNumDecimales, boolean lPesos, int iLongCad){
    String cFormato = "#,##0", cResultado;
    if (iNumDecimales > 0)
      cFormato += ".";
    for (int i=0; i< iNumDecimales; i++)
      cFormato += "0";
    DecimalFormat df = new DecimalFormat(cFormato);
    cResultado = df.format(dNumero);
    int iCarAdd = iLongCad-cResultado.length();
    if (iLongCad > 0){
      if(cResultado.length() < iLongCad)
        for (int i=0; i<iCarAdd; i++){
          cResultado = " " + cResultado;
        }
    }
    if (lPesos)
      cResultado = "$ " + cResultado;
    return cResultado;
  }
}

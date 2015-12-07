package com.micper.util;

import java.text.*;

/**
 * Clase para manejo de fechas: generaci�n, today, y cadenas de despliegue.
 * <p>Ingenier�a de Software generada en JAVA (J2EE).
 * @version 1.0
 * <p>
 * @author Tecnolog�a Inred S.A. de C.V.
 * @author <dd>LSC. Rafael Miranda Blumenkron
 * <p>
 * @see <small><a href="./../ingsample/pg9902041CFG.html">com.micper.ingsample.pg9902041CFG</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="TFechas.png">
 */

public class TNumeros {

  /** Constructor por omisi�n de la clase. */
  public TNumeros(){
  }

  /**
   * M�todo encargado de regresar un n�mero formateado como cadena para mostrar x N�mero de d�gitos.
   * @param iNumero N�mero que se desea obtener con formato.
   * @param iNumDigitos N�mero de d�gitos que se desea obtener en el resultado.
   * @return Cadena de texto correspondiente al n�mero con formato
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

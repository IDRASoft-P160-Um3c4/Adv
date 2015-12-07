package com.micper.util;

import java.util.*;
import com.micper.ingsw.*;
import java.text.*;
import java.io.*;

/**
 * Clase para manejo de fechas: generaci�n, today, y cadenas de despliegue.
 * <p>Ingenier�a de Software generada en JAVA (J2EE).
 * @version 1.0
 * <p>
 * @author Tecnolog�a Inred S.A. de C.V.
 * @author <dd>LSC. Rafael Miranda Blumenkron <dd>LSC. Jaime Enrique Su�rez Romero
 * <p>
 * @see <small><a href="./../ingsample/pg9902041CFG.html">com.micper.ingsample.pg9902041CFG</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="TFechas.png">
 */

public class TFechas implements Serializable {
  /**
	 *
	 */
	private static final long serialVersionUID = 6940125879940131815L;
/** Atributo que contiene las propiedades del archivo globales.properties para configuraci�n de meses. */
  private TParametro  vParametros = null;
  /** Atributo que contiene el n�mero del m�dulo para asignar al archivo de propiedades de consulta. */
private String NumModulo    = "";
  /** Atributo que contiene la lista de nombre de meses tal como se escribi� en archivo de par�metros. */
  private String NombreMeses = "";
  /** Atributo que contiene la lista de abreviaturas de meses tal como se escribi� en archivo de par�metros. */
  private String AbrevMeses   = "";
  private String NombreDias = "";
  /** Atributo que contiene el vector con los nombres de meses. */
  private Vector vNombresMes = new Vector();
  /** Atributo que contiene el vector con las abreviaturas de meses. */
  private Vector vAbrevMes = new Vector();
  private Vector vcNombreDias = new Vector();
  /** Constructor por omisi�n de la clase. */
  public TFechas(){
    setUp("");
  }

  /**
   * Constructor de la clase que permite asignar el n�mero de m�dulo para consulta de par�metros.
   * @param cNumModulo N�mero de m�dulo del cual deseamos obtener los par�metros.
   */
  public TFechas(String cNumModulo) {
    setUp(cNumModulo);
  }

private void setUp(String cNumModulo){
    this.NumModulo = cNumModulo;
    try{
      this.vParametros = new TParametro(cNumModulo);
      this.NombreMeses = vParametros.getPropEspecifica("NombresMes");
      this.AbrevMeses = vParametros.getPropEspecifica("AbrevMes");
      this.NombreDias = vParametros.getPropEspecifica("NombreDias");
    }catch(Exception e){
      this.NombreMeses = "";
      this.AbrevMeses = "";
      this.NombreDias = "";
    }
    // Genera vector de meses
    this.vNombresMes = new Vector();
    String cMeses = this.NombreMeses;
    if (cMeses == null)
      cMeses = "";
    StringTokenizer stNMes = new StringTokenizer(cMeses,",");
    if (cMeses.compareToIgnoreCase("") == 0 || stNMes.countTokens()!=12)
      cMeses = "Enero,Febrero,Marzo,Abril,Mayo,Junio,Julio,Agosto,Septiembre,Octubre,Noviembre,Diciembre";
    stNMes = new StringTokenizer(cMeses,",");
    while(stNMes.hasMoreTokens())
      vNombresMes.add(stNMes.nextToken());
    // Genera vector de abreviaturas
    this.vAbrevMes = new Vector();
    String cAbrevs = this.AbrevMeses;
    if (cAbrevs == null)
      cAbrevs = "";
    StringTokenizer stAbrevMes = new StringTokenizer(cAbrevs,",");
    if (cAbrevs.compareToIgnoreCase("") == 0 || stAbrevMes.countTokens()!= 12)
      cAbrevs = "Ene,Feb,Mar,Abr,May,Jun,Jul,Ago,Sep,Oct,Nov,Dic";
    stAbrevMes = new StringTokenizer(cAbrevs,",");
    while(stAbrevMes.hasMoreTokens())
      vAbrevMes.add(stAbrevMes.nextToken());
    // Genera vector de nombre de d�as
    String cNombreDias = NombreDias;
    if(NombreDias.equals(""))
      cNombreDias = "Lunes,Martes,Mi�rcoles,Jueves,Viernes,S�bado,Domingo";
    StringTokenizer stNDias = new StringTokenizer(cNombreDias,",");
    while(stNDias.hasMoreTokens())
      vcNombreDias.add(stNDias.nextToken());
  }
  /**
   * M�todo para obtener un time stamp a partir de datos b�sicos como a�o, mes d�a hora minuto y segundo.
   * @param iAnio int A�o para generar time stamp.
   * @param iMes int Mes para generar time stamp.
   * @param iDia int D�a para generar time stamp.
   * @param iHora int Hora para generar time stamp.
   * @param iMinuto int Minuto para generar time stamp.
   * @param iSegundo int Segundo para generar time stamp.
   * @return Timestamp TimeStamp generado con par�metros proporcionados, regresa null en caso de error de datos.
   */
  public java.sql.Timestamp getTimeStamp(int iAnio, int iMes, int iDia, int iHora, int iMinuto, int iSegundo, int iNanos){
    if ((iMes > 12) || (iDia > 31) || (iHora > 24) || (iMinuto > 60) || (iSegundo > 60))
      return null;
    GregorianCalendar gTime = new GregorianCalendar();
    gTime.set(iAnio,(iMes-1),iDia,iHora,iMinuto,iSegundo);
    java.sql.Timestamp tsHora = new java.sql.Timestamp(gTime.getTimeInMillis());
    tsHora.setNanos(iNanos);
    return tsHora;
  }

  /**
   * M�todo encargado de regresar el n�mero de horas en formato de 12 horas y con dos d�gitos.
   * @param tsFecha Timestamp  Atributo del cual se desea obtener la hora.
   * @return String Hora en formato de 12 horas con dos d�gitos.
   */
public String getStringHora12H(java.sql.Timestamp tsFecha){
  return new TNumeros().getNumeroSinSeparador(new Integer(this.getHora12H(tsFecha)),2);
}

/**
 * M�todo encargado de regresar el n�mero de horas en formato de 24 horas y con dos d�gitos.
 * @param tsFecha Timestamp  Atributo del cual se desea obtener la hora.
 * @return String Hora en formato de 24 horas con dos d�gitos.
 */
public String getStringHora24H(java.sql.Timestamp tsFecha){
  return new TNumeros().getNumeroSinSeparador(new Integer(this.getHora24H(tsFecha)),2);
}

/**
 * M�todo encargado de regresar el n�mero de minutos con dos d�gitos.
 * @param tsFecha Timestamp  Atributo del cual se desea obtener los minutos.
 * @return String Minutos con formato de dos digitos.
 */
public String getStringMinuto(java.sql.Timestamp tsFecha){
  return new TNumeros().getNumeroSinSeparador(new Integer(this.getMinuto(tsFecha)),2);
}

/**
 * M�todo encargado de regresar el n�mero de segundos con dos d�gitos.
 * @param tsFecha Timestamp  Atributo del cual se desea obtener los segundos.
 * @return String Segundos con formato de dos digitos.
 */
public String getStringSegundo(java.sql.Timestamp tsFecha){
  return new TNumeros().getNumeroSinSeparador(new Integer(this.getSegundo(tsFecha)),2);
}

/**
 * M�todo encargado de regresar una hora formato de 24 horas con formato de HH:MM, el separador puede cambiarse.
 * @param tsFecha Timestamp  Atributo del cual se desea obtener el formato.
 * @param cSeparador String Separador que desea emplearse en lugar de los dos puntos.
 * @return String Cadena con la hora en formato HH:MM.
 */
public String getStringHoraHHMM_24(java.sql.Timestamp tsFecha, String cSeparador){
  String cSep = cSeparador;
  if (cSep == null)
    cSep = ":";
  if (cSep.equals(""))
    cSep = ":";
  return getStringHora24H(tsFecha) + cSep + getStringMinuto(tsFecha);
}

/**
 * Metodo encargado de regresar una hora de 12 horas con formato de HH:MM AM/PM.
 * @param tsFecha Timestamp  Atributo del cual se desea obtener el formato.
 * @param cSeparador String Separador que desea emplearse en lugar de los dos puntos.
 * @param lAMPM boolean Indica si deseamos que concatene el AM/PM o no.
 * @return String Cadena con la hora en formato HH:MM AM/PM.
 */
public String getStringHoraHHMM_12(java.sql.Timestamp tsFecha, String cSeparador, boolean lAMPM){
  String cSep = cSeparador, cTemp;
  if (cSep == null)
    cSep = ":";
  if (cSep.equals(""))
    cSep = ":";
  cTemp = getStringHora12H(tsFecha) + cSep + getStringMinuto(tsFecha);
  if (lAMPM)
    cTemp += " " + this.getAM_PM(tsFecha);
  return cTemp;
}

/**
 * Metodo encargado de regresar una hora de 24 horas con formato de HH:MM:SS.
 * @param tsFecha Timestamp  Atributo del cual se desea obtener el formato.
 * @param cSeparador String Separador que desea emplearse en lugar de los dos puntos.
 * @return String Cadena con la hora en formato HH:MM:SS.
 */
public String getStringHoraHHMMSS_24(java.sql.Timestamp tsFecha, String cSeparador){
  String cSep = cSeparador;
  if (cSep == null)
    cSep = ":";
  if (cSep.equals(""))
    cSep = ":";
  return getStringHoraHHMM_24(tsFecha, cSep) + cSep + getStringSegundo(tsFecha);
}

/**
 * Metodo encargado de regresar una hora de 12 horas con formato de HH:MM:SS AM/PM.
 * @param tsFecha Timestamp  Atributo del cual se desea obtener el formato.
 * @param cSeparador String Separador que desea emplearse en lugar de los dos puntos.
 * @param lAMPM boolean Indica si deseamos que concatene el AM/PM o no.
 * @return String Cadena con la hora en formato HH:MM:SS AM/PM.
 */
public String getStringHoraHHMMSS_12(java.sql.Timestamp tsFecha, String cSeparador, boolean lAMPM){
  String cSep = cSeparador, cTemp;
  if (cSep == null)
    cSep = ":";
  if (cSep.equals(""))
    cSep = ":";
  cTemp = getStringHoraHHMM_12(tsFecha, cSep, false) + cSep + getStringSegundo(tsFecha);
  if (lAMPM)
    cTemp += " " + this.getAM_PM(tsFecha);
  return cTemp;
}

/**
 * M�todo encargado de obtener la fecha y hora actuales.
 * @param lNanos boolean Indica si deseamos obtener los nano segundos o no.
 * @return Timestamp Fecha y hora actuales.
 */
public java.sql.Timestamp getThisTime(boolean lNanos){
  GregorianCalendar gTime = new GregorianCalendar();
  java.sql.Timestamp tsHora = new java.sql.Timestamp(gTime.getTimeInMillis());
  if (!lNanos)
    tsHora.setNanos(0);
  return tsHora;
}

/**
 * M�todo encargado de obtener la fecha y hora actuales sin nano segundos.
 * @return Timestamp Fecha y hora actuales.
 */
public java.sql.Timestamp getThisTime(){
  return getThisTime(false);
}

/**
 * M�todo encargado de obtener un Timestamp a partir de los par�metros proporcionados.
 * @param iAnio int A�o del cual se desea armar el TimeStamp.
 * @param iMes int Mes del cual se desea armar el TimeStamp.
 * @param iDia int D�a del cual se desea armar el TimeStamp.
 * @param iHora int Hora del cual se desea armar el TimeStamp.
 * @param iMinuto int Minuto del cual se desea armar el TimeStamp.
 * @param iSegundo int Segundo del cual se desea armar el TimeStamp.
 * @return Timestamp Timestamp generado en base a los par�metros.
 */
public java.sql.Timestamp getTimeStamp(int iAnio, int iMes, int iDia, int iHora, int iMinuto, int iSegundo){
    return this.getTimeStamp(iAnio, iMes, iDia, iHora, iMinuto, iSegundo, 0);
  }

  /**
   * M�todo encargado de obtener un Timestamp a partir de los par�metros proporcionados.
   * @param iAnio int A�o del cual se desea armar el TimeStamp.
   * @param iMes int Mes del cual se desea armar el TimeStamp.
   * @param iDia int D�a del cual se desea armar el TimeStamp.
   * @param iHora int Hora del cual se desea armar el TimeStamp.
   * @param iMinuto int Minuto del cual se desea armar el TimeStamp.
   * @return Timestamp Timestamp generado en base a los par�metros.
   */
  public java.sql.Timestamp getTimeStamp(int iAnio, int iMes, int iDia, int iHora, int iMinuto){
    return this.getTimeStamp(iAnio, iMes, iDia, iHora, iMinuto, 0, 0);
  }

  /**
   * M�todo encargado de obtener un Timestamp a partir de los par�metros proporcionados.
   * @param iAnio int A�o del cual se desea armar el TimeStamp.
   * @param iMes int Mes del cual se desea armar el TimeStamp.
   * @param iDia int D�a del cual se desea armar el TimeStamp.
   * @param iHora int Hora del cual se desea armar el TimeStamp.
   * @return Timestamp Timestamp generado en base a los par�metros.
   */
  public java.sql.Timestamp getTimeStamp(int iAnio, int iMes, int iDia, int iHora){
    return this.getTimeStamp(iAnio, iMes, iDia, iHora, 0, 0, 0);
  }

  /**
   * M�todo encargado de obtener un Timestamp a partir de los par�metros proporcionados.
   * @param iAnio int A�o del cual se desea armar el TimeStamp.
   * @param iMes int Mes del cual se desea armar el TimeStamp.
   * @param iDia int D�a del cual se desea armar el TimeStamp.
   * @return Timestamp Timestamp generado en base a los par�metros.
   */
  public java.sql.Timestamp getTimeStamp(int iAnio, int iMes, int iDia){
    return this.getTimeStamp(iAnio, iMes, iDia, 0, 0, 0, 0);
  }

  /**
   * M�todo para obtener la fecha en formato SQLDate de un time stamp proporcionado.
   * @param tsFechaHora Timestamp TimeStamp del cual se desea conocer la fecha.
   * @return Date Fecha obtenida del time stamp proporcionado.
   */
public java.sql.Date getDateSQL(java.sql.Timestamp tsFechaHora){
    GregorianCalendar gTime = new GregorianCalendar();
    gTime.setTimeInMillis(tsFechaHora.getTime());
    return this.getDateSQL(new Integer(gTime.get(gTime.DAY_OF_MONTH)), new Integer(gTime.get(gTime.MONTH)+1), new Integer(gTime.get(gTime.YEAR)));
  }

  /**
   * M�todo para obtener la hora de un timestamp proporcionado.
   * @param tsFechaHora Timestamp timeStamp del cual se desea conocer la hora (formato de 24hrs).
   * @return int Hora obtenida del time stamp proporcionado.
   */
public int getHora24H(java.sql.Timestamp tsFechaHora){
    GregorianCalendar gTime = new GregorianCalendar();
    gTime.setTimeInMillis(tsFechaHora.getTime());
    return gTime.get(gTime.HOUR_OF_DAY);
  }

  /**
   * M�todo para obtener la hora de un timestamp proporcionado.
   * @param tsFechaHora Timestamp timeStamp del cual se desea conocer la hora (formato de 12hrs).
   * @return int Hora obtenida del time stamp proporcionado.
   */
public int getHora12H(java.sql.Timestamp tsFechaHora){
    GregorianCalendar gTime = new GregorianCalendar();
    gTime.setTimeInMillis(tsFechaHora.getTime());
    return gTime.get(gTime.HOUR);
  }

  /**
   * M�todo para obtener AM o PM de un timestamp proporcionado.
   * @param tsFechaHora Timestamp timeStamp del cual se desea conocer si es de AM o PM.
   * @return String AM o PM obtenido del time stamp proporcionado.
   */
public String getAM_PM(java.sql.Timestamp tsFechaHora){
    GregorianCalendar gTime = new GregorianCalendar();
    gTime.setTimeInMillis(tsFechaHora.getTime());
    if (gTime.get(gTime.AM_PM) == 0)
      return "AM";
    else
      return "PM";
  }

  /**
   * M�todo para obtener los minutos de un timestamp proporcionado.
   * @param tsFechaHora Timestamp timeStamp del cual se desea conocer los minutos.
   * @return int Minutos obtenidos del time stamp proporcionado.
   */
public int getMinuto(java.sql.Timestamp tsFechaHora){
    GregorianCalendar gTime = new GregorianCalendar();
    gTime.setTimeInMillis(tsFechaHora.getTime());
    return gTime.get(gTime.MINUTE);
  }

  /**
   * M�todo para obtener los segundos de un timestamp proporcionado.
   * @param tsFechaHora Timestamp timeStamp del cual se desea conocer los segundos.
   * @return int Segundos obtenidos del time stamp proporcionado.
   */
public int getSegundo(java.sql.Timestamp tsFechaHora){
    GregorianCalendar gTime = new GregorianCalendar();
    gTime.setTimeInMillis(tsFechaHora.getTime());
    return gTime.get(gTime.SECOND);
  }

  /**
   * M�todo para obtener el d�a de la semana 1 = Domingo, 7 = S�bado.
   * @param dtFecha Date Fecha de la cual se desea saber el n�mero de d�a.
   * @return int Valor del n�mero de d�a 1=Domingo, 7=S�bado.
   */
public int getGregorianDayOfWeek(java.sql.Date dtFecha) {
    GregorianCalendar gDate = new GregorianCalendar();
    java.util.Date actualDate = new java.util.Date(dtFecha.getTime());
    gDate.setTime(actualDate);
    return gDate.get(gDate.DAY_OF_WEEK);
  }

  /**
   * M�todo para obtener un vector con las fechas que coinciden con el d�a de la semana proporcionado.
   * @param dtInicial Date Fecha inicial para realizar la b�squeda.
   * @param dtFinal Date Fecha final para realizar la b�squeda
   * @param iDayOfWeek int D�a de la semana que desea buscarse.
   * @return Vector Resultado de fechas que coinciden con el d�a proporcionado.
   */
public Vector getDaysNumBetween(java.sql.Date dtInicial, java.sql.Date dtFinal, int iDayOfWeek){
    Vector dtFechaRegresa = new Vector();
    while(!dtInicial.equals(dtFinal)){
      if(this.getGregorianDayOfWeek(dtInicial) == iDayOfWeek)
        dtFechaRegresa.add(dtInicial);
      dtInicial = this.aumentaDisminuyeDias(dtInicial,1);
    }
    if(this.getGregorianDayOfWeek(dtInicial) == iDayOfWeek)
      dtFechaRegresa.add(dtInicial);
    return dtFechaRegresa;
  }

  /**
   * M�todo para obtener un entero con el numero de dias que han transcurrido a partir de dos fechas dadas.
   * @param dtInicial Date Fecha inicial para realizar el conteo.
   * @param dtFinal Date Fecha final para realizar el conteo.
   * @return Entero con los n�meros de dias transcurridos entre las dos fechas dadas.
   */
  public int getDaysBetweenDates(java.sql.Date dtInicial, java.sql.Date dtFinal){
    int iNumDias = 0;
    while(dtInicial.compareTo(dtFinal) < 0){
      dtInicial = this.aumentaDisminuyeDias(dtInicial,1);
      iNumDias++;
    }
    return iNumDias;
  }

  /**
   * M�todo encargado de regresar la fecha actual en un objeto de tipo long.
   * @return Fecha actual en formato long.
   */
  public long TodayLONG(){
    return new java.util.Date().getTime();
  }

  /**
   * M�todo encargado de regresar la fecha actual en un objeto de tipo sql.Date.
   * @return Fecha actual en formato sql.Date.
   */
  public java.sql.Date TodaySQL(){
    return new java.sql.Date(this.TodayLONG());
  }

  /**
   * M�todo encargado de devolver el n�mero de d�a de una fecha proporcionada de tipo sql.Date.
   * @param dtFecha Fecha de la cual se desea obtener el d�a.
   * @return Entero correspondiente al n�mero de d�a de la fecha proporcionada.
   */
  public int getIntDay(java.sql.Date dtFecha){
    java.util.Date utilFecha = new java.util.Date(dtFecha.getTime());
    SimpleDateFormat sdfFecha = null;
    sdfFecha = new SimpleDateFormat("dd");
    return new Integer(sdfFecha.format(utilFecha)).intValue();
  }

  /**
   * M�todo encargado de devolver el n�mero de mes de una fecha proporcionada de tipo sql.Date.
   * @param dtFecha Fecha de la cual se desea obtener el mes.
   * @return Entero correspondiente al n�mero de mes de la fecha proporcionada.
   */
  public int getIntMonth(java.sql.Date dtFecha){
    java.util.Date utilFecha = new java.util.Date(dtFecha.getTime());
    SimpleDateFormat sdfFecha = null;
    sdfFecha = new SimpleDateFormat("MM");
    return new Integer(sdfFecha.format(utilFecha)).intValue();
  }

  /**
   * M�todo encargado de devolver el n�mero de a�o de una fecha proporcionada de tipo sql.Date.
   * @param dtFecha Fecha de la cual se desea obtener el a�o.
   * @return Entero correspondiente al n�mero de a�o de la fecha proporcionada.
   */
  public int getIntYear(java.sql.Date dtFecha){
    java.util.Date utilFecha = new java.util.Date(dtFecha.getTime());
    SimpleDateFormat sdfFecha = null;
    sdfFecha = new SimpleDateFormat("yyyy");
    return new Integer(sdfFecha.format(utilFecha)).intValue();
  }

  public String getDayOfWeek(java.sql.Date dtFecha){
    switch (this.getGregorianDayOfWeek(dtFecha)){
      case 1: return ""+vcNombreDias.get(6);
      case 2: return ""+vcNombreDias.get(0);
      case 3: return ""+vcNombreDias.get(1);
      case 4: return ""+vcNombreDias.get(2);
      case 5: return ""+vcNombreDias.get(3);
      case 6: return ""+vcNombreDias.get(4);
      case 7: return ""+vcNombreDias.get(5);
    }
    return "";
  }

  public int getDayOfWeekInt(java.sql.Date dtFecha){
    switch (this.getGregorianDayOfWeek(dtFecha)){
      case 1: return 0;
      case 2: return 1;
      case 3: return 2;
      case 4: return 3;
      case 5: return 4;
      case 6: return 5;
      case 7: return 6;
    }
    return 0;
  }

  public String getDateSPN(java.sql.Date dtFecha){
     return getStringDay(dtFecha) + " de " + getMonthName(dtFecha) + " de " + getStringYear(dtFecha);
  }

  public String getDateSPNWithDay(java.sql.Date dtFecha){
     return getDayOfWeek(dtFecha) + " " + getStringDay(dtFecha) + " de " + getMonthName(dtFecha) + " de " + getStringYear(dtFecha);
  }


  /**
   * M�todo encargado de devolver el n�mero de d�a de una fecha proporcionada de tipo sql.Date.
   * @param dtFecha Fecha de la cual se desea obtener el d�a.
   * @return Cadena correspondiente al n�mero de d�a de la fecha proporcionada.
   */
  public String getStringDay(java.sql.Date dtFecha){
    return new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
  }

  /**
   * M�todo encargado de devolver el n�mero de mes de una fecha proporcionada de tipo sql.Date.
   * @param dtFecha Fecha de la cual se desea obtener el mes.
   * @return Cadena correspondiente al n�mero de mes de la fecha proporcionada.
   */
  public String getStringMonth(java.sql.Date dtFecha){
    return new TNumeros().getNumeroSinSeparador(new Integer(this.getIntMonth(dtFecha)), 2);
  }

  /**
   * M�todo encargado de devolver el n�mero de a�o de una fecha proporcionada de tipo sql.Date.
   * @param dtFecha Fecha de la cual se desea obtener el a�o.
   * @return Cadena correspondiente al n�mero de a�o de la fecha proporcionada.
   */
  public String getStringYear(java.sql.Date dtFecha){
    return new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
  }

  /**
   * M�todo encargado de devolver el nombre completo del mes correspondiente a una fecha proporcionada.
   * @param dtFecha Fecha de la cual se desea obtener el nombre del mes, de tipo sql.Date.
   * @return Cadena correspondiente al nombre del mes, de acuerdo a los proporcionados en el archivo de propiedades.
   */
  public String getMonthName(java.sql.Date dtFecha){
    return this.vNombresMes.elementAt(this.getIntMonth(dtFecha)-1).toString();
  }

  /**
   * M�todo encargado de devolver la abreviatura del mes correspondiente a una fecha proporcionada.
   * @param dtFecha Fecha de la cual se desea obtener la abreviatura del mes, de tipo sql.Date.
   * @return Cadena correspondiente a la abreviatura del mes, de acuerdo a los proporcionados en el archivo de propiedades.
   */
  public String getMonthAbrev(java.sql.Date dtFecha){
    return this.vAbrevMes.elementAt(this.getIntMonth(dtFecha) - 1).toString();
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de d�a / mes / a�o con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaDDMMYYYY(java.sql.Date dtFecha, String cSeparador){
    java.util.Date utilFecha = new java.util.Date(dtFecha.getTime());
    SimpleDateFormat sdfFecha = null;
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    String cFormato = "dd" + cSeparado + "MM" + cSeparado + "yyyy";
    sdfFecha = new SimpleDateFormat(cFormato);
    return sdfFecha.format(utilFecha);
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada en formato d�a, mes a�o sin separador.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaDDMMYYYYSinSep(java.sql.Date dtFecha){
    return this.getStringDay(dtFecha) + this.getStringMonth(dtFecha) + this.getStringYear(dtFecha);
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada en formato mes, d�a, a�o sin separador.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaMMDDYYYYSinSep(java.sql.Date dtFecha){
    return this.getStringMonth(dtFecha) + this.getStringDay(dtFecha) + this.getStringYear(dtFecha);
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada en formato a�o, mes, d�a, sin separador.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaYYYYMMDDSinSep(java.sql.Date dtFecha){
    return this.getStringYear(dtFecha) + this.getStringMonth(dtFecha) + this.getStringDay(dtFecha);
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de mes / d�a / a�o con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaMMDDYYYY(java.sql.Date dtFecha, String cSeparador){
    java.util.Date utilFecha = new java.util.Date(dtFecha.getTime());
    SimpleDateFormat sdfFecha = null;
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    String cFormato = "MM" + cSeparado + "dd" + cSeparado + "yyyy";
    sdfFecha = new SimpleDateFormat(cFormato);
    return sdfFecha.format(utilFecha);
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de a�o / mes / d�a con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaYYYYMMDD(java.sql.Date dtFecha, String cSeparador){
    java.util.Date utilFecha = new java.util.Date(dtFecha.getTime());
    SimpleDateFormat sdfFecha = null;
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    String cFormato = "yyyy" + cSeparado + "MM" + cSeparado + "dd";
    sdfFecha = new SimpleDateFormat(cFormato);
    return sdfFecha.format(utilFecha);
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de d�a / Abreviatura de mes / a�o con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaDDMMMYYYY(java.sql.Date dtFecha, String cSeparador){
    String cFecha = "";
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + this.getMonthAbrev(dtFecha);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
    return cFecha;
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de d�a / Abreviatura de mes (Mayusculas o Min�sculas) / a�o con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @param lMayusculas Par�metro l�gico para saber si el nombre del mes se regresa en may�sculas o min�sculas
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaDDMMMYYYY(java.sql.Date dtFecha, String cSeparador, boolean lMayusculas){
	    String cFecha = "";
	    String cSeparado = cSeparador;
	    if (cSeparado == null)
	      cSeparado = "/";
	    if (cSeparado.compareToIgnoreCase("") == 0)
	      cSeparado = "/";
	    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
	    cFecha = cFecha + cSeparado;
	    cFecha = cFecha + ((lMayusculas)?this.getMonthAbrev(dtFecha).toUpperCase():this.getMonthAbrev(dtFecha));
	    cFecha = cFecha + cSeparado;
	    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
	    return cFecha;
	  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de Abreviatura de mes / d�a / a�o con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaMMMDDYYYY(java.sql.Date dtFecha, String cSeparador){
    String cFecha = "";
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    cFecha = cFecha + this.getMonthAbrev(dtFecha);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
    return cFecha;
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de a�o / Abreviatura de mes / d�a con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaYYYYMMMDD(java.sql.Date dtFecha, String cSeparador){
    String cFecha = "";
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + this.getMonthAbrev(dtFecha);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
    return cFecha;
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de d�a / Nombre de mes / a�o con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaDDMMMMMYYYY(java.sql.Date dtFecha, String cSeparador){
    String cFecha = "";
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + this.getMonthName(dtFecha);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
    return cFecha;
  }
  public String getFechaTexto(java.sql.Date dtFecha, String cSeparador){
    String cFecha = "";
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
    cFecha = cFecha + " de ";
    cFecha = cFecha + this.getMonthName(dtFecha);
    cFecha = cFecha + " del ";
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
    return cFecha;
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de Nombre de mes / d�a / a�o con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaMMMMMDDYYYY(java.sql.Date dtFecha, String cSeparador){
    String cFecha = "";
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    cFecha = cFecha + this.getMonthName(dtFecha);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
    return cFecha;
  }

  /**
   * M�todo encargado de devolver una fecha proporcionada con un formato de a�o / Nombre de mes / d�a con el separador proporcionado.
   * @param dtFecha Fecha que deseamos obtener con separador espec�fico.
   * @param cSeparador Separador que se desea emplear en la fecha.
   * @return Cadena correspondiente a la fecha con el separador proporcionado.
   */
  public String getFechaYYYYMMMMMDD(java.sql.Date dtFecha, String cSeparador){
    String cFecha = "";
    String cSeparado = cSeparador;
    if (cSeparado == null)
      cSeparado = "/";
    if (cSeparado.compareToIgnoreCase("") == 0)
      cSeparado = "/";
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntYear(dtFecha)), 4);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + this.getMonthName(dtFecha);
    cFecha = cFecha + cSeparado;
    cFecha = cFecha + new TNumeros().getNumeroSinSeparador(new Integer(this.getIntDay(dtFecha)), 2);
    return cFecha;
  }

  /**
   * M�todo encargado de devolver una fecha de tipo sql Date generada a partir de los par�metros proporcionados.
   * @param iDia Entero correspondiente al d�a de la fecha a generar.
   * @param iMes Entero correspondiente al mes de la fecha a generar.
   * @param iAnio Entero correspondiente al a�o de la fecha a generar.
   * @return Fecha de tipo sql Date generada con los par�metros proporcionados.
   */
  public java.sql.Date getDateSQL(Integer iDia, Integer iMes, Integer iAnio){
    java.util.Calendar calendar= new java.util.GregorianCalendar(iAnio.intValue(),iMes.intValue()-1,iDia.intValue());
    java.sql.Date sDate=new java.sql.Date(calendar.getTimeInMillis());
     return sDate;
  }

  /**
   * M�todo encargado de devolver una fecha de tipo sql Date generada a partir de los par�metros proporcionados.
   * @param cDia Cadena correspondiente al n�mero de d�a para generar la fecha.
   * @param cMes Cadena correspondiente al n�mero de mes para generar la fecha.
   * @param cAnio Cadena correspondiente al n�mero de a�o para generar la fecha.
   * @return Fecha de tipo sql Date generada.
   */
  public java.sql.Date getDateSQL(String cDia, String cMes, String cAnio){
    return this.getDateSQL(new Integer(cDia), new Integer(cMes), new Integer(cAnio));
  }

  /**
   * M�todo encargado de devolver una fecha de tipo sql Date generada a partir de una cadena.
   * @param cFecha Cadena de la cual se desea obtener una fecha, con formato dd/mm/aaaa.
   * @return Fecha de tipo sql Date generada.
   */
  public java.sql.Date getDateSQL(String cFecha){
   // return this.TodaySQL();  // codificar
   //dd/mm/aaaa
  java.util.StringTokenizer st = new java.util.StringTokenizer(cFecha,"/");
   return getDateSQL(st.nextToken(), st.nextToken(), st.nextToken());
  }

  /**
   * M�todo encargado de devolver una fecha de tipo util Date generada a partir de los par�metros proporcionados.
   * @param iDia Entero correspondiente al d�a de la fecha a generar.
   * @param iMes Entero correspondiente al mes de la fecha a generar.
   * @param iAnio Entero correspondiente al a�o de la fecha a generar.
   * @return Fecha de tipo util Date generada con los par�metros proporcionados.
   */
  public java.util.Date getDateLONG(Integer iDia, Integer iMes, Integer iAnio){
      java.util.Calendar calendar= new java.util.GregorianCalendar(iAnio.intValue(),iMes.intValue()-1,iDia.intValue());
      java.util.Date date=new java.util.Date(calendar.getTimeInMillis());
     return date;
  }
  /**
   * M�todo encargado de devolver una fecha de tipo util Date generada a partir de los par�metros proporcionados.
   * @param cDia Cadena correspondiente al n�mero de d�a para generar la fecha.
   * @param cMes Cadena correspondiente al n�mero de mes para generar la fecha.
   * @param cAnio Cadena correspondiente al n�mero de a�o para generar la fecha.
   * @return Fecha de tipo util Date generada.
   */
  public java.util.Date getDateLONG(String cDia, String cMes, String cAnio){
    return this.getDateLONG(new Integer(cDia), new Integer(cMes), new Integer(cAnio));
 }

  /**
   * M�todo encargado de devolver una fecha de tipo util Date generada a partir de una cadena.
   * @param cFecha Cadena de la cual se desea obtener una fecha, con formato dd/mm/aaaa.
   * @return Fecha de tipo util Date generada.
   */
  public java.util.Date getDateLONG(String cFecha){
    java.util.StringTokenizer st = new java.util.StringTokenizer(cFecha,"/");
    return getDateLONG(st.nextToken(), st.nextToken(), st.nextToken());
  }

  /**
   * M�todo encargado de devolver el vector de abreviaturas de mes.
   * @return Vector con los valores de abreviaturas de mes.
   */
  public Vector getVAbrevMes() {
    return vAbrevMes;
  }

  /**
   * M�todo encargado de devolver el vector de nombres de mes.
   * @return Vector con los valores de nombres de mes.
   */
  public Vector getVNombresMes() {
    return vNombresMes;
  }

  /**
   * M�todo encargado de aumentar o disminuir x n�mero de d�as a la fecha proporcionada.
   * @param dtFecha Fecha a la cual se le desean aumentar o disminuir d�as.
   * @param iNumDias N�mero de d�as a aumentar o disminu�r de la fecha original.
   * @return Fecha en formato SQL date con la fecha resultante.
   */
  public java.sql.Date aumentaDisminuyeDias(java.sql.Date dtFecha, int iNumDias) {
    Calendar calendar = new GregorianCalendar();
    java.util.Date trialTime = new java.util.Date(dtFecha.getTime());
    calendar.setTime(trialTime);
    calendar.add(Calendar.DATE, iNumDias);
    trialTime = calendar.getTime();
    return new java.sql.Date(trialTime.getTime());
  }

  /**
   * M�todo encargado de devolver una fecha de tipo sql Date generada a partir de una cadena formato SQL.
   * @param cFecha Cadena de la cual se desea obtener una fecha, con formato aaaa-mm-dd.
   * @return Fecha de tipo sql Date generada.
   */
  public java.sql.Date getSQLDatefromSQLString(String cFecha){
  String cAnio = "";
  String cMes  = "";
  String cDia  = "";
  java.util.StringTokenizer st = new java.util.StringTokenizer(cFecha,"-");
  cAnio = st.nextToken();
  cMes  = st.nextToken();
  cDia  = st.nextToken();
   return getDateSQL(cDia, cMes, cAnio);
  }

  /**
   * M�todo encargado de regresar un SQL Date a partir de los milisegundos proporcionados.
   * @param miliSegundos long Milisegundos de los cuales se desea obtener una fecha.
   * @return Date Fecha resultante de los milisegundos proporcionados.
   */
  public java.sql.Date getSQLDatefromLong(long miliSegundos){
    return new java.sql.Date(miliSegundos);
  }

  /**
   * M�todo para obtener un time stamp a partir de los milisegundos proporiconados.
   * @param miliSegundos long Milisegundos de los cuales se desea un time stamp.
   * @return Timestamp Timestamp correspondiente a los milisegundos proporcionados.
   */
  public java.sql.Timestamp getTimeStampfromLong(long miliSegundos){
    return new java.sql.Timestamp(miliSegundos);
  }

  public java.sql.Timestamp getTimeStamp(String cCadena){
   // dd/mm/aaaa hh:mm:ss
   int iAnio, iMes, iDia, iHora, iMinuto, iSegundo;
   java.util.StringTokenizer stPartes = new java.util.StringTokenizer(cCadena," ");
   java.util.StringTokenizer stFecha = new java.util.StringTokenizer(stPartes.nextToken(),"/");
   java.util.StringTokenizer stHora = new java.util.StringTokenizer(stPartes.nextToken(),":");

   iDia = Integer.valueOf(stFecha.nextToken()).intValue();
   iMes = Integer.valueOf(stFecha.nextToken()).intValue();
   iAnio = Integer.valueOf(stFecha.nextToken()).intValue();

   iHora    = Integer.valueOf(stHora.nextToken()).intValue();
   iMinuto  = Integer.valueOf(stHora.nextToken()).intValue();
   iSegundo = Integer.valueOf(stHora.nextToken()).intValue();

   return this.getTimeStamp(iAnio, iMes, iDia, iHora, iMinuto, iSegundo);
  }

  public java.sql.Timestamp getTimeStampTC(String cCadena){
   // aaaa/mm/dd/hh/mm
   int iAnio, iMes, iDia, iHora, iMinuto;
   java.util.StringTokenizer stFecha = new java.util.StringTokenizer(cCadena,"/");

   iAnio = Integer.valueOf(stFecha.nextToken()).intValue();
   iMes = Integer.valueOf(stFecha.nextToken()).intValue();
   iDia = Integer.valueOf(stFecha.nextToken()).intValue();
   iHora    = Integer.valueOf(stFecha.nextToken()).intValue();
   iMinuto  = Integer.valueOf(stFecha.nextToken()).intValue();

   return this.getTimeStamp(iAnio, iMes, iDia, iHora, iMinuto);
  }

  public String getSQLTimeStamp(String cCadena){
   // aaaa/mm/dd/hh/mm
   String cAnio, cMes, cDia, cHora, cMinuto;
   java.util.StringTokenizer stFecha = new java.util.StringTokenizer(cCadena,"/");

   cAnio = stFecha.nextToken();
   cMes = stFecha.nextToken();
   cDia = stFecha.nextToken();
   cHora    = stFecha.nextToken();
   cMinuto  = stFecha.nextToken();

   return cAnio + "-" + cMes + "-" + cDia + " " + cHora + ":" + cMinuto + ":00";
  }

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
  public String getFechaLetras(java.sql.Date dtFecha){
    String cResultado = "";
    TLetterNumberFormat numFmt = new TLetterNumberFormat();
    try{
      cResultado += numFmt.getCantidad( (long)this.getIntDay(dtFecha));
      cResultado += " DE " + this.getMonthName(dtFecha) + " DE ";
      cResultado += numFmt.getCantidad((long)this.getIntYear(dtFecha));
    }catch(Exception e){}
    return cResultado;
  }
}

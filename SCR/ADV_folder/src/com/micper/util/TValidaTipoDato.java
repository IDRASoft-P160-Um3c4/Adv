/*
 * TValidaTipoDato.java
 *
 * Created on 18 de Enero de 2007
 */

package com.micper.util;
import java.util.*;

/**
 *
 * @author  ABarrientos
 */
public class TValidaTipoDato {

	private HashMap letras;

    public TValidaTipoDato() {
    }


    public boolean lEsLogico_1_0(String cValor){
      if( cValor == "0" || cValor == "1" )
        return true;
      else
        return false;
    }

    public boolean lEsBoolean(String cValor){
	boolean lValor;
      try{
        lValor =  Boolean.valueOf(cValor).booleanValue();

      }catch(Exception ex){
          return false;
      }
      return true;
    }

    public boolean lEsInt(String cValor){
 	int iValor;
      try{
        iValor =  Integer.parseInt(cValor);
      }catch(Exception ex){
          return false;
      }
      return true;
    }

    public boolean lEsFloat(String cValor){
 	float iValor;
      try{
        iValor =  Float.valueOf(cValor).floatValue();
      }catch(Exception ex){
          return false;
      }
      return true;

    }

    public boolean lEsDouble(String cValor){
 	double iValor;
      try{
        iValor =  Double.valueOf(cValor).doubleValue();
      }catch(Exception ex){
          return false;
      }
      return true;

    }

    public boolean lEsDate(String cValor){
 	java.sql.Date dtValor;
      TFechas fecha = new TFechas();
      try{
        dtValor = fecha.getDateSQL(cValor);
      }catch(Exception ex){
        return false;
      }
      return true;
    }

    public boolean lEsHora(String cValor){
      String cHora,cMin;
        if(cValor.indexOf(":") != -1){
          cHora = cValor.substring(0,cValor.indexOf(":"));
          cMin =  cValor.substring(cValor.indexOf(":")+1);
          if(cMin.length() < 2 ||  !this.lEsInt(cMin))
            return false;
          if(cHora.length() < 2 || !this.lEsInt(cHora))
            return false;
          if(Integer.parseInt(cHora,10) < 0 && Integer.parseInt(cHora,10) > 23)
            return false;
          if(Integer.parseInt(cMin,10) < 0 && Integer.parseInt(cMin,10) > 59)
            return false;
          return true;
        }
        return false;

    }

}

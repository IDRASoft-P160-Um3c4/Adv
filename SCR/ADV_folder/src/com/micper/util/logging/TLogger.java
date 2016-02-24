package com.micper.util.logging;

import com.micper.ingsw.*;

public class TLogger{

  public static String DEBUG = "DEBUG";
  public static String WARN = "WARN";
  public static String ERROR = "ERROR";
  public static String FATAL = "FATAL";
  public static String INFO = "INFO";
  public static boolean initialized = false;

  private static String cModulo = "";

  private static boolean lInfo = false;
  private static boolean lDebug = false;
  private static boolean lWarn = false;
  private static boolean lWarnST = true;
  private static boolean lError = false;
  private static boolean lErrorST = true;
  private static boolean lFatal = false;
  private static boolean lFatalST = true;

  public static void setSistema(String cMod){
    if(cMod.compareTo(cModulo) != 0){
      cModulo = cMod;
      TParametro vParametros = new TParametro(cModulo);

      System.out.print(cModulo + " El Logger ha sido Configurado...");
      lInfo = Boolean.valueOf(vParametros.getPropEspecifica("lInfo")).
          booleanValue();
      System.out.print(cModulo + " - lInfo: " + lInfo);
      lDebug = Boolean.valueOf(vParametros.getPropEspecifica("lDebug")).
          booleanValue();
      System.out.print(cModulo + " - lDebug: " + lDebug);
      lError = Boolean.valueOf(vParametros.getPropEspecifica("lError")).
          booleanValue();
      System.out.print(cModulo + " - lError: " + lError);
      lFatal = Boolean.valueOf(vParametros.getPropEspecifica("lFatal")).
          booleanValue();
      System.out.print(cModulo + " - lFatal: " + lFatal);
      lWarn = Boolean.valueOf(vParametros.getPropEspecifica("lWarn")).
          booleanValue();
      System.out.print(cModulo + " - lWarn: " + lWarn);
      lWarnST = Boolean.valueOf(vParametros.getPropEspecifica("lWarnST")).
          booleanValue();
      System.out.print(cModulo + " - lWarnST: " + lWarnST);
      lErrorST = Boolean.valueOf(vParametros.getPropEspecifica("lErrorST")).
          booleanValue();
      System.out.print(cModulo + " - lErrorST: " + lErrorST);
      lFatalST = Boolean.valueOf(vParametros.getPropEspecifica("lFatalST")).
          booleanValue();
      System.out.print(cModulo + " - lFatalST: " + lFatalST);
    }
  }

  static void setup(){
    initialized = true;
  }

  public static void log(Object obj,String level,String message,Throwable t){
    if(!initialized){
      setup();
    }
    if(lDebug){
      if(level.equalsIgnoreCase(DEBUG)){
        System.out.print(" -DEBUG- " + cModulo + "." + obj + ": " + message);
      }
      if(lInfo){
        if(level.equalsIgnoreCase(INFO)){
          System.out.print(" -INFO- " + cModulo + "." + obj + ": " + message);
        }
      }
      if(lError){
        if(level.equalsIgnoreCase(ERROR)){
          System.out.print(" -ERROR- " + cModulo + "." + obj + ": " + message);
          if(lErrorST){
            t.printStackTrace();
          }
        }
      }
      if(lWarn){
        if(level.equalsIgnoreCase(WARN)){
          System.out.print(" -WARN- " + cModulo + "." + obj + ": " + message);
          if(lWarnST){
            t.printStackTrace();
          }
        }
      }
      if(lFatal){
        if(level.equalsIgnoreCase(FATAL)){
          System.out.print(" -FATAL- " + cModulo + "." + obj + ": " + message);
          if(lFatalST){
            t.printStackTrace();
          }
        }
      }
    }

  }
}

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

      System.out.println(cModulo + " El Logger ha sido Configurado...");
      lInfo = Boolean.valueOf(vParametros.getPropEspecifica("lInfo")).
          booleanValue();
      System.out.println(cModulo + " - lInfo: " + lInfo);
      lDebug = Boolean.valueOf(vParametros.getPropEspecifica("lDebug")).
          booleanValue();
      System.out.println(cModulo + " - lDebug: " + lDebug);
      lError = Boolean.valueOf(vParametros.getPropEspecifica("lError")).
          booleanValue();
      System.out.println(cModulo + " - lError: " + lError);
      lFatal = Boolean.valueOf(vParametros.getPropEspecifica("lFatal")).
          booleanValue();
      System.out.println(cModulo + " - lFatal: " + lFatal);
      lWarn = Boolean.valueOf(vParametros.getPropEspecifica("lWarn")).
          booleanValue();
      System.out.println(cModulo + " - lWarn: " + lWarn);
      lWarnST = Boolean.valueOf(vParametros.getPropEspecifica("lWarnST")).
          booleanValue();
      System.out.println(cModulo + " - lWarnST: " + lWarnST);
      lErrorST = Boolean.valueOf(vParametros.getPropEspecifica("lErrorST")).
          booleanValue();
      System.out.println(cModulo + " - lErrorST: " + lErrorST);
      lFatalST = Boolean.valueOf(vParametros.getPropEspecifica("lFatalST")).
          booleanValue();
      System.out.println(cModulo + " - lFatalST: " + lFatalST);
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
        System.out.println(" -DEBUG- " + cModulo + "." + obj + ": " + message);
      }
      if(lInfo){
        if(level.equalsIgnoreCase(INFO)){
          System.out.println(" -INFO- " + cModulo + "." + obj + ": " + message);
        }
      }
      if(lError){
        if(level.equalsIgnoreCase(ERROR)){
          System.out.println(" -ERROR- " + cModulo + "." + obj + ": " + message);
          if(lErrorST){
            t.printStackTrace();
          }
        }
      }
      if(lWarn){
        if(level.equalsIgnoreCase(WARN)){
          System.out.println(" -WARN- " + cModulo + "." + obj + ": " + message);
          if(lWarnST){
            t.printStackTrace();
          }
        }
      }
      if(lFatal){
        if(level.equalsIgnoreCase(FATAL)){
          System.out.println(" -FATAL- " + cModulo + "." + obj + ": " + message);
          if(lFatalST){
            t.printStackTrace();
          }
        }
      }
    }

  }
}

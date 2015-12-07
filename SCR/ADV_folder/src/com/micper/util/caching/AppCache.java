package com.micper.util.caching;
/**
 * <p>Title: AppCache</p>
 * <p>Description: Clase que almacena a cualquier objeto a través de un nombre, llave y caducidad</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Tecnología Inred S.A. de C.V.</p>
 * @author Lic. Jaime Enrique Suárez Romero
 * @version 1.0
 */

public class AppCache{
  private String  cCollName;
  private String  cKey;
  private Object  oColl;
private long dtInicio, dtFin;

  public AppCache(String cCollName, String ckey, Object oColl, String cCaducidad) {
    this.cCollName = cCollName;
    this.cKey = cKey;
    this.oColl = oColl;
    this.dtInicio = new java.util.Date().getTime();
    Float dCaducidad = new Float((1000 * 3600) * (new Float(cCaducidad).floatValue()));
    this.dtFin    = new java.util.Date().getTime() + dCaducidad.longValue();
  }

  public Object getColl(){
    return oColl;
  }

  public String getCollName(){
    return cCollName;
  }

  public String getCollkey(){
    return cKey + cCollName;
  }

  public boolean expires(){
    boolean lSuccess = true;
    if(new java.util.Date().getTime() < dtFin){
      lSuccess = false;
    }
    return lSuccess;
  }

}
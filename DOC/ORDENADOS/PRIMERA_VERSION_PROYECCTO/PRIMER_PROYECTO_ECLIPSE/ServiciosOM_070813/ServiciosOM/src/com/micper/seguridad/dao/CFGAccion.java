package com.micper.seguridad.dao;

import java.util.*;
import javax.servlet.*;

import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.ingsw.*;
import com.micper.util.*;

public class CFGAccion{ 
  private PageBeanScroller bs;
  private String cNavStatus = "";  
  private int iRowPag;
  private String cAccion;
  private String cOrden;
  private String cFiltro;
  private int iNumReg;
  private ServletRequest request;
  private BeanPK bPK;
  private String iNoRes;

  public CFGAccion(ServletRequest HttpRequest){
    request = HttpRequest;
    iNoRes = "";
    try{
      if(request.getCharacterEncoding() == null)
        request.setCharacterEncoding("UTF-8");
      cAccion = "" + request.getParameter("hdBoton");
      cOrden = "" + request.getParameter("hdOrden");
      cFiltro = "" + request.getParameter("hdFiltro");
      iNumReg = Integer.parseInt(request.getParameter("hdNumReg").toString());
      iRowPag = Integer.parseInt(request.getParameter("hdRowPag").toString());
    } catch(Exception e){
      iNumReg = 10;
    }
    if(!cOrden.equals("") && !cOrden.equals("null")) cOrden = " Order by " +
        cOrden;
    else cOrden = "";
    if(!cFiltro.equals("") && !cFiltro.equals("null")) cFiltro = " where " +
        cFiltro;
    else cFiltro = "";
  }

  public void setINoRes(String cPar){
    iNoRes = cPar;
  }

  public void setINumReg(int iNumRegP){
        iNumReg = iNumRegP;
  }

  public void setBeanPK(BeanPK pk){
    this.bPK = pk;
  }

  public void navega(Vector vcDatos){
    if(vcDatos != null && vcDatos.size() != 0){
      bs = new PageBeanScroller(vcDatos,iNumReg);
      if(bs != null){ //bs=null
        if(cAccion.equals("")||(
           !cAccion.equals("Actual")&&!cAccion.equals("Anterior")&&
           !cAccion.equals("Siguiente")&&!cAccion.equals("Ultimo"))
        )
        cAccion = "Primero";
        if(bPK != null){
          bs.setPageByRowPK(bPK);
          iRowPag = bs.pageNo();
        } else{
          if(cAccion.equals("Actual")){
            try{
              bs.setPageIdx(iRowPag);
            } catch(Exception e){
              cAccion = "Primero";
            }
          }
          if(cAccion.equals("Primero"))
            bs.firstPage();
          if(cAccion.equals("Anterior")){
            if(iRowPag != -1)
              bs.prevPageTo(iRowPag);
          }
          if(cAccion.equals("Siguiente")){
            if(iRowPag != -1)
              bs.nextPageTo(iRowPag);
          }
          if(cAccion.equals("Ultimo"))
            bs.lastPage();
          iRowPag = bs.pageNo();
        }
        if(bs.hasPrevPage() && bs.hasNextPage())
          cNavStatus = "Record";
        if(!bs.hasPrevPage() && bs.hasNextPage())
          cNavStatus = "FirstRecord";
        if(bs.hasPrevPage() && !bs.hasNextPage())
          cNavStatus = "LastRecord";
        if(!bs.hasPrevPage() && !bs.hasNextPage())
          cNavStatus = "Disabled";
      } else //bs=null
        iRowPag = -1;
    } else{
      iRowPag = -1;
    }
  }

  public TVDinRep setInputs(String cCampos){
    TVDinRep vDinRep = new TVDinRep();
    String cNombre="";
    int i = 0;
    
    StringTokenizer stCampos = new StringTokenizer(cCampos,",");

    while(stCampos.hasMoreTokens()){    	
      cNombre="";
      try{
        cNombre = stCampos.nextToken();
        vDinRep.put(cNombre,request.getParameter(cNombre).toString());
      } catch(Exception e){    	  
        System.out.println("Error Parametro: "+cNombre+", cCampos["+i+"]");
        e.printStackTrace();
      }
      i++;
    }
    return vDinRep;
  }

  public String getCNavStatus(){
    return cNavStatus;
  }

  public int getIRowPag(){
    return iRowPag;
  }

  public PageBeanScroller getBs(){
    return bs;
  }

  public String getCFiltro(){
    return cFiltro;
  }

  public String getCOrden(){
    return cOrden;
  }

  public String getCAccion(){
    return cAccion;
  }

  public void setCAccion(String cAccion){
    this.cAccion = cAccion;
  }

  public String getBPK(){
    String cPK = "";
    if(bPK != null){
      for(int i = 0;i < bPK.size();i++){
        cPK = cPK + "," + bPK.getField(i);
      }
      if(cPK.length() > 0)
        cPK = cPK.substring(1);
    }
    return cPK;
  }

  public String getArrayCD(){
    String cDato = "", cLlave = "";
    StringBuffer sbArray = new StringBuffer("");
    if(bs != null){
      Vector vcListado = bs.getPageKeys();
      Vector vcKeys = new Vector();
      if(vcListado.size() > 0){
        TVDinRep vDinRep = (TVDinRep) vcListado.get(0);
        vcKeys = vDinRep.getVcKeys();
      }
      int i = 0;
      boolean lNormal;
      bs.start();
      while(bs.nextRow()){
        cDato = "";
        for(int j = 0;j < vcKeys.size();j++){
          cLlave = (String) vcKeys.get(j);
          lNormal = true;
          if(cLlave.substring(0,2).toLowerCase().equals("dt")){
            try{
              cLlave = new TFechas().getFechaDDMMYYYY((java.sql.Date) bs.getFieldValue(cLlave ,""),"/");
              cDato += ",'" + cLlave + "'";
            }catch(Exception ex){
              cDato += ",''";
            }
            lNormal = false;
          }
          if(cLlave.substring(0,2).toLowerCase().equals("ts")){
            if(bs.getFieldValue(cLlave) == null){
              cDato += ",''";
            }else{
              try{
                TFechas tfFecha = new TFechas();
                java.sql.Timestamp tsVer = (java.sql.Timestamp) bs.getFieldValue(cLlave);
                String cFecha = "" + tfFecha.getFechaDDMMYYYY(new java.sql.Date(tsVer.getTime()),"/");
                String cHora = bs.getFieldValue(cLlave).toString().substring(11,16);
                cDato += ",'" + cFecha + "-" + cHora + "'";
              } catch(Exception ex){
                cDato += ",''";
                System.out.print(cLlave);
                ex.printStackTrace();
              }
            }
            lNormal = false;
          }
          if(lNormal == true)
            cDato += ",'" + bs.getFieldValue(cLlave ,"") + "'";
        }
        sbArray.append("aRes"+iNoRes+"[" + i + "]=[" + cDato.substring(1) + "];\n");
        i++;
      }
    }
    return sbArray.toString();
  }
  public String getTableCD(){
    String cDato = "", cLlave = "";
    StringBuffer sbArray = new StringBuffer("");
    sbArray.append("var cTablaJSP='';\n");
    if(bs != null){
      Vector vcListado = bs.getPageKeys();
      Vector vcKeys = new Vector();
      if(vcListado.size() > 0){
        TVDinRep vDinRep = (TVDinRep) vcListado.get(0);
        vcKeys = vDinRep.getVcKeys();
      }
      int i = 0;
      bs.start();
      while(bs.nextRow()){
        cDato = "";
        for(int j = 0;j < vcKeys.size();j++){
          cLlave = (String) vcKeys.get(j);
          if(cLlave.substring(0,2).toLowerCase().equals("dt")){
            try{
              cLlave = new TFechas().getFechaDDMMYYYY((java.sql.Date) bs.getFieldValue(cLlave ,""),"/");
              cDato += "<TD>" + cLlave + "</TD>";
            }catch(Exception ex){
              cDato += "<TD>-</TD>";
            }
          }else
            cDato += "<TD>" + bs.getFieldValue(cLlave ,"-") + "</TD>";
        }
        sbArray.append("cTablaJSP+='<TR>"+cDato+"</TR>';\n");
        i++;
      }
    }
    return sbArray.toString();
  }
  public boolean unaSesion(TParametro vParametros, CFGSesiones cfgSesion, TVUsuario vUsuario){
     String cVerificar = vParametros.getPropEspecifica("UnaSesion");
     if(cVerificar.toUpperCase().equals("TRUE")){
       if(cfgSesion != null && vUsuario != null)
          return cfgSesion.verUnaSesion(vUsuario.getCUsuario(),vUsuario.getID());
       else
          return true;
     }else
       return true;
  }
  public String getErrorSesion(String cRutaFuncs){
    StringBuffer sbArray = new StringBuffer("");
    sbArray.append("<SCRIPT LANGUAGE=\"JavaScript\" SRC=\""+cRutaFuncs+"CD/ineng.js\"></SCRIPT>");
    sbArray.append("<script language=\"JavaScript\">");
    sbArray.append("fEngResultado('"+request.getParameter("cNombreFRM")+"',");
    sbArray.append("'"+request.getParameter("cId")+"',");
    sbArray.append("'ErrorSesion');");
    sbArray.append("</script>");
    return sbArray.toString();
  }

}

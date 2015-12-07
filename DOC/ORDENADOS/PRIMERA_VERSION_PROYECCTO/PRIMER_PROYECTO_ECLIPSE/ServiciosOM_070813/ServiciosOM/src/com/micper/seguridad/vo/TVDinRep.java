package com.micper.seguridad.vo;

import java.util.*;

import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: Value Object Dinamico</p>
 * <p>Description: Value Object con campos dinamicos </p>
 * <p>Copyright: Copyright (c) 2004 </p>
 * <p>Company: Micros Personales S.A. de C.V. </p>
 * @author Jaime Enrique Suárez Romero
 * @version 1.0
 */
public class TVDinRep
    implements HashBeanInterface{
  /**
	 *
	 */
	private static final long serialVersionUID = -4252927758197256062L;
private BeanPK pk;
  private HashMap hmFieldsTable;
  private Vector vcKeys = new Vector();
  private String cLlave;

  public TVDinRep(){
    pk = new BeanPK();
    hmFieldsTable = new HashMap();
  }

  public BeanPK getPK(){
    if(pk.size() == 0 && cLlave != null){
      StringTokenizer stLlave = new StringTokenizer(cLlave,",");
      while(stLlave.hasMoreTokens()){
        addPK(getString(stLlave.nextToken()));
      }
    }
    return pk;
  }

  public HashMap toHashMap(){
    return hmFieldsTable;
  }

  public void setLlave(String cKey){
    cLlave = cKey.toUpperCase();
  }

  public void addPK(Object objValue){
    pk.add("" + objValue);
  }

  public int size(){
    return hmFieldsTable.size();
  }

  public boolean containsKey(Object objKey){
    return hmFieldsTable.containsKey(objKey);
  }

  public Set keySet(){
    return hmFieldsTable.keySet();
  }

  public Object get(String sKey){
    return hmFieldsTable.get(sKey.toUpperCase());
  }

  public int getInt(String sKey){
    try{
      return Integer.parseInt("" + hmFieldsTable.get(sKey.toUpperCase()));
    } catch(Exception e){
      return 0;
    }
  }

  public java.sql.Date getDate(String sKey){
    java.sql.Date dtVer;
    try{
      dtVer = (java.sql.Date) hmFieldsTable.get(sKey.toUpperCase());
    } catch(Exception ex){
      try{
        dtVer = new TFechas().getDateSQL( (String) hmFieldsTable.get(sKey.
            toUpperCase()));
      } catch(Exception e){
        dtVer = null;
      }
    }
    return dtVer;
  }

  public String getDateStringSQL(String sKey){
    String cDateSQL = "";
    try{
      cDateSQL = (String) hmFieldsTable.get(sKey.toUpperCase());
      cDateSQL = "{ D '" + cDateSQL.substring(3,5) + "-" +
          cDateSQL.substring(0,2) + "-" + cDateSQL.substring(6,10) + "' }";
    } catch(Exception e){
      cDateSQL = "";
    }
    return cDateSQL;
  }

  public java.sql.Timestamp getTimeStamp(String sKey){
    java.sql.Timestamp tsVer;
    try{
      tsVer = (java.sql.Timestamp) hmFieldsTable.get(sKey.toUpperCase());
    }catch(Exception et){
        String cAnio,cMes,cDia,cHora,cMinuto,cSegundo;
        try{
            StringTokenizer stTS = new StringTokenizer( (String) hmFieldsTable.get(sKey.toUpperCase()),"/");
            try{cAnio = stTS.nextToken();} catch(Exception e){cAnio = "0000";}
            try{cMes = stTS.nextToken();} catch(Exception e){cMes = "00";}
            try{cDia = stTS.nextToken();} catch(Exception e){cDia = "00";}
            try{cHora = stTS.nextToken();} catch(Exception e){cHora = "00";}
            try{cMinuto = stTS.nextToken();} catch(Exception e){cMinuto = "00";}
            try{cSegundo = stTS.nextToken();} catch(Exception e){cSegundo = "00";}
            if(cHora.length()==1) cHora = "0"+cHora;
            if(Integer.parseInt(cAnio) > 0){
              tsVer = java.sql.Timestamp.valueOf(cAnio+"-"+cMes+"-"+cDia+" "+cHora+":"+cMinuto+":"+cSegundo);
            }else
              tsVer = null;
        }catch(Exception ex){
            tsVer = null;
        }
    }
        return tsVer;
    }

    public float getFloat(String sKey){
      float dTmp;
      try{
        dTmp = Float.parseFloat("" + hmFieldsTable.get(sKey.toUpperCase()));
      } catch(Exception e){
        dTmp = 0;
      }
      return dTmp;
    }

    public double getDouble(String sKey){
      double dTmp;
      try{
        dTmp = Double.parseDouble("" + hmFieldsTable.get(sKey.toUpperCase()));
      } catch(Exception e){
        dTmp = 0;
      }
      return dTmp;
    }

    public String getString(String sKey){
      return("" + hmFieldsTable.get(sKey.toUpperCase()));
    }

    public boolean getBoolean(String sKey){
      return Boolean.getBoolean("" + hmFieldsTable.get(sKey.toUpperCase()));
    }

    public long getLong(String sKey){
      return Long.parseLong("" + hmFieldsTable.get(sKey.toUpperCase()));
    }

 	public Object put(String sKey,Object objValue){
      vcKeys.add(sKey.toUpperCase());
      return hmFieldsTable.put(sKey.toUpperCase(),objValue);
    }

	public Byte put(String sKey,byte btValue){
      vcKeys.add(sKey.toUpperCase());
      return(Byte) hmFieldsTable.put(sKey.toUpperCase(),new Byte(btValue));
    }

	public Short put(String sKey,short shValue){
      vcKeys.add(sKey.toUpperCase());
      return(Short) hmFieldsTable.put(sKey.toUpperCase(),new Short(shValue));
    }

	public Integer put(String sKey,int iValue){
      vcKeys.add(sKey.toUpperCase());
      return(Integer) hmFieldsTable.put(sKey.toUpperCase(),new Integer(iValue));
    }

	public Long put(String sKey,long lnValue){
      vcKeys.add(sKey.toUpperCase());
      return(Long) hmFieldsTable.put(sKey.toUpperCase(),new Long(lnValue));
    }

	public Float put(String sKey,float fValue){
      vcKeys.add(sKey.toUpperCase());
      return(Float) hmFieldsTable.put(sKey.toUpperCase(),new Float(fValue));
    }

	public Double put(String sKey,double dbValue){
      vcKeys.add(sKey.toUpperCase());
      return(Double) hmFieldsTable.put(sKey.toUpperCase(),new Double(dbValue));
    }

 	public Boolean put(String sKey,boolean bValue){
      vcKeys.add(sKey.toUpperCase());
      return(Boolean) hmFieldsTable.put(sKey.toUpperCase(),
                                        Boolean.valueOf(bValue));
    }

	public java.sql.Date put(String sKey,java.sql.Date dtValue){
      vcKeys.add(sKey.toUpperCase());
      return(java.sql.Date) hmFieldsTable.put(sKey.toUpperCase(),dtValue);
    }

 	public java.sql.Timestamp put(String sKey,java.sql.Timestamp tsValue){
      vcKeys.add(sKey.toUpperCase());
      return(java.sql.Timestamp) hmFieldsTable.put(sKey.toUpperCase(),tsValue);
    }

 	public Character put(String sKey,char cValue){
      vcKeys.add(sKey.toUpperCase());
      return(Character) hmFieldsTable.put(sKey.toUpperCase(),
                                          new Character(cValue));
    }

    public Vector getVcKeys(){
      return vcKeys;
    }
    public void remove(Object objKey){
      hmFieldsTable.remove(objKey.toString().toUpperCase());
    }
  }

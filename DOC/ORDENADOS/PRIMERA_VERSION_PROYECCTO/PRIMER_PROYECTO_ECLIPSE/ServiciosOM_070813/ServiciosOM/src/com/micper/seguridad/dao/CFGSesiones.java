package com.micper.seguridad.dao;

import java.util.*;
import java.io.*;

public class CFGSesiones implements Serializable{
  /**
	 *
	 */
	private static final long serialVersionUID = 8805794077032357509L;
private HashMap hmUsuarios = new HashMap();
  private boolean lConsulta = true;

  public boolean verUnaSesion(String cUsuario, String cIdUser){
     boolean lUnaSesion = false;
     String cId;
     if(lConsulta == true){
       try{
         cId = "" + hmUsuarios.get(cUsuario);
         if(cId.equals(cIdUser))
           lUnaSesion = true;
       }catch(Exception e){
         lUnaSesion = true;
       }
     }else
        lUnaSesion = true;
     return lUnaSesion;
  }

  public synchronized void delUsr(String cUsuario){
     lConsulta = false;
     try{
       if(hmUsuarios.containsKey(cUsuario) == true)
          hmUsuarios.remove(cUsuario);
     }catch(Exception e){e.printStackTrace();}
     lConsulta = true;
  }

public synchronized boolean setSesion(String cUsuario, String cID, boolean lSobreEscribe){
     lConsulta = false;
     boolean lSesion = false;
     if(lSobreEscribe==false){
       try{
         if(hmUsuarios.containsKey(cUsuario) == true){
           hmUsuarios.remove(cUsuario);
         }else{
           hmUsuarios.put(cUsuario,cID);
           lSesion = true;
         }
       }catch(Exception e){
         lSesion = false;
       }
     }else{
       try{
         if(hmUsuarios.containsKey(cUsuario) == true)
            hmUsuarios.remove(cUsuario);
         hmUsuarios.put(cUsuario,cID);
         lSesion = true;
       }catch(Exception e){
         lSesion = false;
       }
     }
     lConsulta = true;
     return lSesion;
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

}

package gob.sct.sipmm.dao;

import java.sql.*;
import java.util.*;
import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;

public class Test1 extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  public Vector findByCustom(String cKey,String cWhere) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }

 public void pendienteEtapaNotificacion(){
   Vector vcPendientes = new Vector();
   TDTRARegTramXSol TDCanc = new TDTRARegTramXSol();
   try{
     vcPendientes = this.findByCustom("",
                    "SELECT DISTINCT(S.INUMSOLICITUD),S.IEJERCICIO,RT.DTRECEPCION,RT.DTNOTIFICACION, " +
                    "DAYS(CURRENT DATE) - DAYS(RT.DTNOTIFICACION) AS IDIASATRAZO,CT.IDIASDESPUESNOTIF " +
                    "FROM TRAREGSOLICITUD S " +
                    "JOIN TRAREGREQXTRAM RT ON RT.IEJERCICIO=S.IEJERCICIO AND RT.INUMSOLICITUD=S.INUMSOLICITUD " +
                    "JOIN TRAREGETAPASXMODTRAM ES ON ES.IEJERCICIO=S.IEJERCICIO AND ES.INUMSOLICITUD=S.INUMSOLICITUD AND ES.ICVEETAPA=20 " +
                    "JOIN TRACONFIGURATRAMITE CT ON CT.ICVETRAMITE=S.ICVETRAMITE AND CT.ICVEMODALIDAD=S.ICVEMODALIDAD " +
                    "LEFT JOIN TRAREGTRAMXSOL TS ON TS.IEJERCICIO=S.IEJERCICIO AND TS.INUMSOLICITUD=S.INUMSOLICITUD " +
                    "WHERE ES.IORDEN = (SELECT MAX(IORDEN) FROM TRAREGETAPASXMODTRAM WHERE IEJERCICIO=S.IEJERCICIO AND INUMSOLICITUD=S.INUMSOLICITUD) " +
                    "AND CT.DTINIVIGENCIA=(SELECT MAX(DTINIVIGENCIA) FROM TRACONFIGURATRAMITE WHERE ICVETRAMITE=CT.ICVETRAMITE AND ICVEMODALIDAD=CT.ICVEMODALIDAD) " +
                    "AND RT.DTNOTIFICACION IS NOT NULL AND (DAYS(CURRENT DATE) - DAYS(RT.DTNOTIFICACION))>CT.IDIASDESPUESNOTIF " +
                    "AND TS.DTCANCELACION IS NULL ");
   }
   catch(Exception e){
     e.printStackTrace();
   }
   if(vcPendientes.size()>0){
     for(int i=0;i<vcPendientes.size();i++){
       TVDinRep vPendiente = (TVDinRep) vcPendientes.get(i);
       int iEjercicio = vPendiente.getInt("IEJERCICIO");
       int iNumSolicitud = vPendiente.getInt("iNumSolicitud");
       System.out.print("*****     "+i+"  Solicitudes a Cancelar: "+iEjercicio+"/"+iNumSolicitud);
       TVDinRep vDatosCanc = new TVDinRep();
       vDatosCanc.put("iEjercicio",iEjercicio);
       vDatosCanc.put("iNumSolicitud",iNumSolicitud);
       vDatosCanc.put("iCveMotivoCancela",3);
       vDatosCanc.put("iCveUsuario",1);
       vDatosCanc.put("cObs","Cancelada por tiempo maxmo de espera de documentos.");
       if(vPendiente.getInt("IDIASATRAZO")>vPendiente.getInt("IDIASDESPUESNOTIF")){
         /***************** /
         try{
           TDCanc.insert(vDatosCanc,null);
         }catch(Exception e){
           e.printStackTrace();
           System.out.print("*****     No se pudo guardar la solicitud");
         }
         /***************/
       }
     }
   }
 }

}

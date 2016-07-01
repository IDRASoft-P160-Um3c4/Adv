package gob.sct.sipmm.dao.ws;

//Java imports
import java.util.*;
import com.micper.ingsw.*;
import com.micper.sql.DAOBase;
import java.sql.*;
import java.util.*;
import com.micper.ingsw.*;
import com.micper.sql.*;
import gob.sct.recargos.ws.*;
import com.micper.seguridad.vo.TVDinRep;

/**
 * <p>Title: TDActualizaRecargo</p>
 * <p>Description: Clase que llama a los métodos de los WebServices de Consulta y que se utiliza
 * como interfaz para evitar el llamado de primitivas, regresando colecciones con Value Objects
 * de TVCiudadano</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Tecnología Inred S.A. de C.V.</p>
 * @author
 * @version 1.0
 */

public class TDActualizaRecargo extends DAOBase {
  private TParametro parametros = null;
  private String actRecargoWSURL = "";

  /**
       * Constructor que configura a la clase por sistema, para el llamado del Logger
   */
  public TDActualizaRecargo() {
    this.setSistema("44");
    parametros = new TParametro("44");
    try{
      actRecargoWSURL = parametros.getPropEspecifica("URLWSActualizacionRecargo");
    } catch(Exception ex){
      warn("findActRecargo-WSConRecargo",ex);
    }
  }


  public TVDinRep findActRecargo(java.sql.Date dtAdeudo,java.sql.Date dtPago,double dImporte) {
    TVDinRep vDinRep = new TVDinRep();

    try {
      if (actRecargoWSURL.compareTo("") != 0) {
        // binding al WebService
        String wsdlUrl = actRecargoWSURL;
        ConRecargo service = new ConRecargo_Impl( wsdlUrl );
        ConRecargoPort conActRecargo = service.getConRecargoPort();
        IngresosRecargoIn vActRecargoIn = new IngresosRecargoIn();
        IngresosRecargoOut vActRecargoOut = new IngresosRecargoOut();
        Calendar calAdeudo =  Calendar.getInstance();
        Calendar calPago =  Calendar.getInstance();

        calAdeudo.setTime(dtAdeudo);
        calPago.setTime(dtPago);
        vActRecargoIn.setFechaAdeudo(calAdeudo);
        vActRecargoIn.setFechaPago(calPago);
        vActRecargoIn.setMontoOriginal(dImporte);
        vActRecargoOut = conActRecargo.calculaRecargo(vActRecargoIn);
        vDinRep.put("ImporteActualizado",vActRecargoOut.getMontoActualizado());
        vDinRep.put("DifActualizacion",vActRecargoOut.getDiferencia());
        vDinRep.put("ImporteRecargo",vActRecargoOut.getMontoRecargos());
        vDinRep.put("ImporteTotal",vActRecargoOut.getTotal());

//System.out.print("Valor de Importe Actualizado: " + vActRecargoOut.getMontoActualizado());
//System.out.print("Valor de Recargos: " + vActRecargoOut.getMontoRecargos());
//System.out.print("Valor de la Direrencia: " + vActRecargoOut.getDiferencia());
//System.out.print("Valor del Total: " + vActRecargoOut.getTotal());

      }
      else {
        info("findActRecargo-WSConRecargo no cuenta con URLWSActualizacionRecargo.");
      }
    }
    catch (Exception ex) {
      warn("findActRecargo-WSConRecargo", ex);
    }
    finally {
    }
    return vDinRep;
  }

  public Vector findActRecargoMultiple(Vector vActRecargo) {
     Vector vResultado = new Vector();

     try {
       if (actRecargoWSURL.compareTo("") != 0) {
         // binding al WebService
         String wsdlUrl = actRecargoWSURL;
         ConRecargo service = new ConRecargo_Impl( wsdlUrl );
         ConRecargoPort conActRecargo = service.getConRecargoPort();

         Object[] objActRecargoIn,objActRecargoOut;
         if (vActRecargo.size()>0){
           objActRecargoIn = new Object[vActRecargo.size()];
           for(int i=0;i<vActRecargo.size();i++){
             TVDinRep vDinRep = new TVDinRep();
             vDinRep = (TVDinRep) vActRecargo.get(i);
             IngresosRecargoIn vActRecargoIn = new IngresosRecargoIn();
             Calendar calAdeudo =  Calendar.getInstance();
             Calendar calPago =  Calendar.getInstance();
             calAdeudo.setTime(vDinRep.getDate("dtAdeudo"));
             calPago.setTime(vDinRep.getDate("dtPago"));
             vActRecargoIn.setFechaAdeudo(calAdeudo);
             vActRecargoIn.setFechaPago(calPago);
             vActRecargoIn.setMontoOriginal(vDinRep.getDouble("dImporte"));
             objActRecargoIn[i] = vActRecargoIn;
           }

           //objActRecargoOut = new Object[vActRecargo.size()];
           objActRecargoOut = conActRecargo.calculaRecargoMultiple(objActRecargoIn);
           if (objActRecargoOut.length>0){
             for(int i = 0;i < objActRecargoOut.length;i++){
               TVDinRep vDinRep = new TVDinRep();
               IngresosRecargoOut vActRecargoOut = new IngresosRecargoOut();
               vActRecargoOut = (IngresosRecargoOut) objActRecargoOut[i];
               vDinRep.put("ImporteActualizado",vActRecargoOut.getMontoActualizado());
               vDinRep.put("DifActualizacion",vActRecargoOut.getDiferencia());
               vDinRep.put("ImporteRecargo",vActRecargoOut.getMontoRecargos());
               vDinRep.put("ImporteTotal",vActRecargoOut.getTotal());
               vResultado.add(vDinRep);
               //System.out.print("Valor del Indice: " + i);
               //System.out.print("Valor de Importe Actualizado: " + vActRecargoOut.getMontoActualizado());
               //System.out.print("Valor de Recargos: " + vActRecargoOut.getMontoRecargos());
               //System.out.print("Valor de la Direrencia: " + vActRecargoOut.getDiferencia());
               //System.out.print("Valor del Total: " + vActRecargoOut.getTotal());
             }
           }
         }
       }
       else {
         info("findActRecargoMultiple-WSConRecargo no cuenta con URLWSActualizacionRecargo.");
       }
     }
     catch (Exception ex) {
       warn("findActRecargoMultiple-WSConRecargo", ex);
     }
     finally {
     }
     return vResultado;
   }
}

package gob.sct.sipmm.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.multipart.*;
import gob.sct.sipmm.dao.TDGRLDocRegistrado;
import gob.sct.sipmm.dao.TDGRLDocFolioCM;
import gob.sct.sipmm.dao.TDGRLMimeType;
import gob.sct.sipmm.dao.TDGRLDocumento;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.ingsw.TParametro;
import gob.sct.sipmm.cntmgr.CM_Import;
import gob.sct.sipmm.cntmgr.CM_GetContent;
import com.micper.util.TNumeros;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Tecnolog�a InRed S.A. de C.V.<dd>Oscar Castrej�n Adame<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TSGRLDocRegistrado extends HttpServlet {
  private static final String CONTENT_TYPE = "multipart/form-data";

  //Initialize global variables
  public void init() throws ServletException {
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    boolean lCarga = false;
    boolean lCargado = false;
    TVDinRep vDinRep = new TVDinRep();
    TDGRLDocRegistrado dGRLDocRegistrado = new TDGRLDocRegistrado();
    TDGRLDocFolioCM dGRLDocFolioCM = new TDGRLDocFolioCM();
    TDGRLMimeType dGRLMimeType = new TDGRLMimeType();
    TDGRLDocumento dGRLDocumento = new TDGRLDocumento();
    TParametro  vParametros = new TParametro("44");
    String cAccion = "";
    int iEjercicio = 0;
    int iIdGestorDocumento = 0;
    int iCveTipoDocumento = 0;
    int iIdDocumento = 0;

    Vector VArchivo = new Vector();
    Vector VMimeType = new Vector();
    StringBuffer sbArchivo = new StringBuffer();
    byte[] btArchivo = null;
    String cNomArchivo = "";
    String cExtensionArchivo = "";
    int iUsoWebService = Integer.parseInt(vParametros.getPropEspecifica("GestorDocWS").toString()); // ACTIVAR EN SERVIDOR PL
    //int iUsoWebService = 0; // ACTIVAR EN DESARROLLO
    int iBaseGestor = 0;
    String cUsrCM = "";
    String cPwdCM = "";
    String cEntidadCM = "";
    String cTipoArchivo = "";
    String cMimeType = "";
    String cFolioGestor = "";
    int iTamanoArchivo = 0;
    TNumeros Numeros = new TNumeros();
    TNumeros Numeritos = new TNumeros();
    int iDigitos = Integer.parseInt(vParametros.getPropEspecifica("DigitosCM").toString());

    if (iUsoWebService == 1){
      iBaseGestor = 1;
      iTamanoArchivo = Integer.parseInt(vParametros.getPropEspecifica("TamArchivoCM").toString())*1024*1024;
    }
    else {
      iBaseGestor = 0;
      iTamanoArchivo = Integer.parseInt(vParametros.getPropEspecifica("TamArchivoDB").toString())*1024*1024;
    }
    //System.out.print("..............TamArchivoDB--->"+iTamanoArchivo);
    cUsrCM = vParametros.getPropEspecifica("usrCM").toString();
    cPwdCM = vParametros.getPropEspecifica("pwdCM").toString();
    cEntidadCM = vParametros.getPropEspecifica("entidadCM").toString();



    int iUpdate = 0;
    boolean lBorrado = false;
    try{
      if(request.getContentType() != null){

        MultipartParser mp;
        Part p = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mp = new MultipartParser(request, iTamanoArchivo);

        while( (p = mp.readNextPart()) != null){
          if(p.isFile()){
            FilePart fp = (FilePart)p;
            cNomArchivo = fp.getFileName();

            StringTokenizer stExtension = new StringTokenizer(cNomArchivo,".");
            while(stExtension.hasMoreTokens()){
              try{
                cExtensionArchivo = stExtension.nextToken();
              } catch(Exception e){
              }
            }

            fp.writeTo(baos);
            byte[] archivo = baos.toByteArray();
            vDinRep.put("bDocAlmacenado", (Object) archivo);
          }
          if(p.isParam()){
            if (p.getName().compareTo("iEjercicio") == 0){
                ParamPart pp = (ParamPart) p;
                vDinRep.put("iEjercicio",Integer.parseInt(pp.getStringValue()));
                iEjercicio = Integer.parseInt(pp.getStringValue());
            }

            if (p.getName().compareTo("iIdGestorDocumento") == 0){
                ParamPart pp = (ParamPart) p;
                if (pp.getStringValue().compareTo("") != 0){
                  iIdGestorDocumento = Integer.parseInt(pp.getStringValue());
                  vDinRep.put("iIdGestorDocumento",Integer.parseInt(pp.getStringValue()));
                }
            }

            if (p.getName().compareTo("cMimeType") == 0){
                ParamPart pp = (ParamPart) p;
                if (pp.getStringValue().compareTo("") != 0){
                  cMimeType = pp.getStringValue();
                  //System.out.print("----------------pp.getStringValue(): "+pp.getStringValue());
                  vDinRep.put("cMimeType",pp.getStringValue());
                }
            }

            if (p.getName().compareTo("hdBoton") == 0){
                ParamPart pp = (ParamPart) p;
                cAccion = pp.getStringValue();
            }
          }
        }

        /* inicia la carga de los datos a sistema*/
        if(vDinRep != null && vDinRep.size() > 0 ){

          try{
            if (cAccion.compareTo("Guardar") == 0){

              if (cExtensionArchivo.compareTo("")!= 0){
                VMimeType = dGRLMimeType.findByCustom("cMimeType",
                            "select cMimeType, " +
                            "cExtensiones " +
                            "from GRLMimeType ");
              }

              if (!VMimeType.isEmpty()){
                 for (int i=0;i<VMimeType.size();i++){
                   TVDinRep vMimes = (TVDinRep) VMimeType.get(i);
                   String cExtensiones = vMimes.getString("cExtensiones");

                   StringTokenizer stLlave = new StringTokenizer(cExtensiones,",");
                   while(stLlave.hasMoreTokens()){
                     String cToken = stLlave.nextToken();
                     if (cToken.compareTo(cExtensionArchivo) == 0)
                       cMimeType =  vMimes.getString("cMimeType");
                   }

                 }
              }

              if (cMimeType.compareTo("") != 0)
                vDinRep.put("cMimeType",cMimeType);

              if (cEntidadCM.compareTo("") != 0)
                vDinRep.put("cTablaCM",cEntidadCM);

              if (cExtensionArchivo.compareTo("")!= 0)
                vDinRep.put("cExtensionArchivo",cExtensionArchivo);

              if (iUsoWebService == 0){
                vDinRep = dGRLDocRegistrado.insert(vDinRep,null);
                iIdGestorDocumento = vDinRep.getInt("iIdGestorDocumento");
                iEjercicio = vDinRep.getInt("iEjercicio");
                lCarga = true;
              } else {

                vDinRep = dGRLDocFolioCM.insert(vDinRep,null);
                iIdGestorDocumento = vDinRep.getInt("iIdGestorDocumento");
                iEjercicio = vDinRep.getInt("iEjercicio");
                Integer idGestor = new Integer(iIdGestorDocumento);
                Integer iEjerGestor = new Integer(iEjercicio - 2000);

//                cFolioGestor = "" + Numeros.getNumeroSinSeparador(iEjerGestor,2) +
//                               "" + Numeros.getNumeroSinSeparador(idGestor,iDigitos);
                cFolioGestor = idGestor.toString();

                //Adjuntar Documentos al Content Manager.
                String [] keys = {"userid", "password", "entity", "mimeType", "lintiCveDocumen"};
                //System.out.print("++++++++++++---------String [] values: cMimeType: "+cMimeType);
                //String [] values = {cUsrCM, cPwdCM, cEntidadCM, cMimeType, cFolioGestor};
                String [] values = {cUsrCM, cPwdCM, cEntidadCM, "application/octet-stream", cFolioGestor};
                String [] operators = {"", "", "", "", "", "="};

                 CM_Import cmImport = new CM_Import();
                 byte[] archivo = (byte[]) vDinRep.get("bDocAlmacenado");
                 if(cmImport.connect(keys,values,archivo).compareTo("0") == 0)
                    lCarga = true;
                 else
                    lCarga = false;
              }
            }

            if (cAccion.compareTo("GuardarA") == 0){
              if (iUsoWebService == 0){
                vDinRep = dGRLDocRegistrado.update(vDinRep,null);
                iIdGestorDocumento = vDinRep.getInt("iIdGestorDocumento");
                iEjercicio = vDinRep.getInt("iEjercicio");
                lCarga = true;
              } else {

                String [] keys = {"userid", "password", "entity", "mimeType", "lintiCveDocumen"};
                //System.out.print("++++++++++++---------String [] values 2 ----: cMimeType: "+cMimeType);
                //String [] values = {cUsrCM, cPwdCM, cEntidadCM, cMimeType, cFolioGestor };
                String [] values = {cUsrCM, cPwdCM, cEntidadCM, "application/octet-stream", cFolioGestor };
                String [] operators = {"", "", "", "", "", "="};

                CM_Import cmImport = new CM_Import();
                byte[] archivo = (byte[]) vDinRep.get("bDocAlmacenado");
                cmImport.connect(keys,values,archivo);
                lCarga = true;
              }

            }

            if (cAccion.compareTo("Borrar")  == 0){
              if (iUsoWebService == 0){
                lBorrado = dGRLDocRegistrado.delete(vDinRep,null);
              } else {
                //System.out.print("Falta Agregar el Uso del Web Service para Borrar Doctos");
              }
            }

          } catch (Exception ex){
              lCarga = false;
              ex.printStackTrace();
            }

        }
        if(lCargado && iUpdate > 0){
          lCarga = true;
        }
      }
    }
    catch(Exception e){
    }

    if (cAccion.compareTo("Consultar") == 0){
       if (iUsoWebService == 0){
         try{
           VArchivo = dGRLDocRegistrado.findByCustom(
               "iEjercicio,iIdGestorDocumento",
               " select iEjercicio, iIdGestorDocumento, bDocAlmacenado, lActivo " +
               " from GRLDocRegistrado " +
               " where GRLDocRegistrado.iEjercicio         = " + iEjercicio +
               " and   GRLDocRegistrado.iIdGestorDocumento = " +
               iIdGestorDocumento);

           if(VArchivo.size() > 0){
             TVDinRep vArchivo = (TVDinRep) VArchivo.get(0);
             sbArchivo.append(vArchivo.get("bDocAlmacenado"));
             btArchivo = (byte[]) vArchivo.get("bDocAlmacenado");
           }
         } catch(Exception ex){
           btArchivo = null;
         }
       } else {

             Integer iEjerGestor = new Integer(iEjercicio - 2000);
             Integer idGestor = new Integer(iIdGestorDocumento);

//             cFolioGestor = "" + Numeros.getNumeroSinSeparador(iEjerGestor,2) +
//                            "" + Numeros.getNumeroSinSeparador(idGestor,iDigitos);
             cFolioGestor = idGestor.toString();

         CM_GetContent CM_GC = new CM_GetContent();

         //System.out.print("--------->cFolioGestor: "+cFolioGestor);
         //Recuperar el Documento del Content Manager.
         String[] keys = {"userid", "password", "entity", "maxResults", "queryOP","lintiCveDocumen"};
         String[] values = {cUsrCM,cPwdCM,cEntidadCM,"1","true",cFolioGestor};
         String[] operators = {"","","","","","="};
         try{
           //System.out.print("---------------------> keys: "+keys.toString()+"    values: "+values.toString());
           btArchivo = CM_GC.connect(keys, values, operators);
         }catch(Exception e){
           e.printStackTrace();
           btArchivo = null;
         }
       }
    }

//    String cRutaFuncs = vParametros.getPropEspecifica("RutaFuncs");

    if (cAccion.compareTo("Consultar") == 0){
      //System.out.print("Archivo existe=" + ((btArchivo != null)?"SI":"NO") + " MimeType="+cMimeType);
       if (btArchivo != null){
         if(cMimeType.equals(""))
           cMimeType = "multipart/form-data";
         response.setContentType(cMimeType);
         response.addHeader("Title","Consulta de documentos - Cerrar ventana por favor...");
         OutputStream osSalida = response.getOutputStream();
         osSalida.write(btArchivo);
         osSalida.flush();
         osSalida.close();
       } else {
         response.setContentType("text/html");
         PrintWriter out = response.getWriter();
         out.println("<html><head><title>Consulta de documentos</title></head><body>");
         out.println("<form name='form1' method='post' action=''>");
         out.println("<script languaje='JavaScript'>");
         out.println("  if (top.opener) ");
         out.println("    top.opener.fSetArchNoEncontrado(top);");
         out.println("</script>");
         out.println("</form></body></html>");
       }
    } else {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      if(!lCarga){
        out.println("<html><head><title>TSGRLDocRegistrado</title></head><body>");
        out.println("<form name='form1' method='post' action=''>");
        out.println("<script languaje='JavaScript'>");
        out.println("  if (top.opener) ");
        out.println("    top.opener.fSetArchNoAdjuntado(top);");
        out.println("</script>");
        out.println("</form></body></html>");
      } else {
        out.println("<html><head><title>TSGRLDocRegistrado</title></head><body>");
        out.println("<form name='form1' method='post' action=''>");
        out.println("<script languaje='JavaScript'>");
        out.println("  if (top.opener) ");
        out.println("    top.opener.fSetiIdGestorDocumento("+ iEjercicio + ',' + iIdGestorDocumento + ",top,'" + cNomArchivo + "','" + cExtensionArchivo + "','" + cMimeType + "');");
        out.println("</script>");
        out.println("</form></body></html>");
      }
    }
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

  //Process the HTTP Put request
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  }

  //Process the HTTP Delete request
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //System.out.print("Borrando el Servlet");
  }

  //Clean up resources
  public void destroy() {
    //System.out.print("Destruyendo el Servlet");
  }
}

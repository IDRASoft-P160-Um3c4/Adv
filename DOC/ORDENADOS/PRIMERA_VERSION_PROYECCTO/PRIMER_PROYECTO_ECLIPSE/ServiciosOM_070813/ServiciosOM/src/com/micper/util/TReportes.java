package com.micper.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 * Forma de llamar:    TReportes tRep = new TReportes("99");
 *                     out.println(this.creaExcelActiveX("Listado", "Listado_Salida", false, 1, 0, false, true, new StringBuffer()));
 *                     out.println(this.creaWordActiveX("Documento", "Documento_Salida", new StringBuffer()));
 */
import com.micper.ingsw.*;
import java.util.Vector;

public class TReportes{
  private String cNumModulo = "44";

  private String excel_cClassID = "";
  private String excel_cCodeBase = "";
  private String excel_cRutaOrig = "";
  private String excel_cRutaDest = "";
  private String excel_cDelim = "|";

  private String word_cClassID = "";
  private String word_cCodeBase = "";
  private String word_cRutaOrig = "";
  private String word_cRutaDest = "";

  TParametro vParametros = new TParametro(this.cNumModulo);

  public TReportes(String cModulo){
    this.cNumModulo = cModulo;

    this.excel_cClassID = vParametros.getPropEspecifica("ExcelClassID");
    this.excel_cCodeBase = vParametros.getPropEspecifica("ExcelCodeBase");
    this.excel_cRutaOrig = vParametros.getPropEspecifica("ExcelRutaOrig");
    this.excel_cRutaDest = vParametros.getPropEspecifica("ExcelRutaDest");

    this.word_cClassID = vParametros.getPropEspecifica("WordClassID");
    this.word_cCodeBase = vParametros.getPropEspecifica("WordCodeBase");
    this.word_cRutaOrig = vParametros.getPropEspecifica("WordRutaOrig");
    this.word_cRutaDest = vParametros.getPropEspecifica("WordRutaDest");
  }

  public StringBuffer creaExcelActiveX(String cArchivoOrig,String cArchivoDest,boolean lAutoImprimir,int iNumCopias,int iTiempoEspera,boolean lCerrarAlFinal,boolean lVisible, StringBuffer sbDatos){
    StringBuffer sbRes = new StringBuffer();
    sbRes.append("<OBJECT");
    sbRes.append(" classid=\"" + excel_cClassID + "\" ");
    sbRes.append(" codebase=\"" + excel_cCodeBase + "\" ");
    sbRes.append(" width=1 height=1 ").append(" align=center ").append(" hspace=0 vspace=0 >");
    sbRes.append("\n<PARAM NAME=\"CF\" VALUE=\"FILENAME=" + excel_cRutaOrig + cArchivoOrig + ".xls");
    sbRes.append(excel_cDelim).append( (lVisible ? "1" : "0"));
    sbRes.append(sbDatos);
    if(!cArchivoDest.equals(""))
      sbRes.append("\nGUARDARCOMO=" + excel_cRutaDest + cArchivoDest + ".xls");
    if(lAutoImprimir){
      if(iNumCopias < 1)
        iNumCopias = 1;
      for(int i = 1;i <= iNumCopias;i++){
        sbRes.append("\nIMPRIMIR=SI");
        if(iTiempoEspera > 0)
          sbRes.append("\nDELAY=" + iTiempoEspera);
      }
    }
    if(lCerrarAlFinal)
      sbRes.append("\nCERRAR=SI");
    sbRes.append("\n\">").append("\n</OBJECT>");
    return sbRes;
  }

  public StringBuffer creaWordActiveX(String cArchivoOrig, String cArchivoDest, boolean lAutoImprimir,int iNumCopias, boolean lVisible, StringBuffer sbDatos, Vector vDatosAdicionales){
    StringBuffer sbRes = new StringBuffer("");
    String cObjName = "objAX001";
    sbRes.append("<OBJECT id=\"" + cObjName + "\"");
    sbRes.append(" classid=\"" + word_cClassID + "\" ");
    sbRes.append("\n codebase=\"" + word_cCodeBase + "\" ");
    sbRes.append(" width=\"0\" height=\"0\" ").append(" align=\"center\" ").append(" hspace=\"0\" vspace=\"0\" >");
    sbRes.append("\n<PARAM NAME=\"FNAME\" VALUE=\"" + word_cRutaOrig + cArchivoOrig + ".doc" + "\">");
    sbRes.append(sbDatos);
    sbRes.append("\n</OBJECT>");
    sbRes.append("\n<script language=\"JavaScript\">");
    sbRes.append("\nfunction fDatosAdicionalesAX(){");

    if(vDatosAdicionales != null)
      for(int i=0; i<vDatosAdicionales.size(); i++)
        sbRes.append("\n" + cObjName + "." + vDatosAdicionales.get(i) + ";");

    if(!cArchivoDest.equals(""))
      sbRes.append("\n" + cObjName + ".GUARDARCOMO='" + word_cRutaDest + cArchivoDest + ".doc';");
    if(lAutoImprimir)
      sbRes.append("\n" + cObjName + ".IMPRIMIR(" + ((iNumCopias > 0)?iNumCopias:1) + ");");
    if(lVisible)
      sbRes.append("\n" + cObjName + ".ISVISIBLE='YES';");
    sbRes.append("\n}");
    sbRes.append("\nfDatosAdicionalesAX();");
    sbRes.append("</script>");
    return sbRes;
  }

  public StringBuffer creaWordXMLActiveX(String cArchivoOrig, String cArchivoDest, boolean lAutoImprimir,int iNumCopias, boolean lVisible, StringBuffer sbDatos, Vector vDatosAdicionales){
	    StringBuffer sbRes = new StringBuffer("");
	    String cObjName = "objAX001";
	    sbRes.append("<OBJECT id=\"" + cObjName + "\"");
	    sbRes.append(" classid=\"" + word_cClassID + "\" ");
	    sbRes.append("\n codebase=\"" + word_cCodeBase + "\" ");
	    sbRes.append(" width=\"0\" height=\"0\" ").append(" align=\"center\" ").append(" hspace=\"0\" vspace=\"0\" >");
	    sbRes.append("\n<PARAM NAME=\"FNAME\" VALUE=\"" +"http://servidorimagenes.sct.gob.mx/sipymm/" + cArchivoOrig + ".doc" + "\">");
	    sbRes.append(sbDatos);
	    sbRes.append("\n</OBJECT>");
	    sbRes.append("\n<script language=\"JavaScript\">");
	    sbRes.append("\nfunction fDatosAdicionalesAX(){");

	    if(vDatosAdicionales != null)
	      for(int i=0; i<vDatosAdicionales.size(); i++)
	        sbRes.append("\n" + cObjName + "." + vDatosAdicionales.get(i) + ";");

	    if(!cArchivoDest.equals(""))
	      sbRes.append("\n" + cObjName + ".GUARDARCOMO='" + word_cRutaDest + cArchivoDest + ".doc';");
	    if(lAutoImprimir)
	      sbRes.append("\n" + cObjName + ".IMPRIMIR(" + ((iNumCopias > 0)?iNumCopias:1) + ");");
	    if(lVisible)
	      sbRes.append("\n" + cObjName + ".ISVISIBLE='YES';");
	    sbRes.append("\n}");
	    sbRes.append("\nfDatosAdicionalesAX();");
	    sbRes.append("</script>");
	    return sbRes;
	  }

  public StringBuffer creaPDFObject(){
    StringBuffer sbRes = new StringBuffer("");
    sbRes.append("\n<script language=\"JavaScript\">");
    sbRes.append("\nfunction fAbrePDF(){");
    sbRes.append("\n  var cConds = \"alwaysRaised=yes,dependent=yes,width=950,height=700,location=no,menubar=no,resizable=no,scrollbars=yes,titlebar=yes,statusbar=yes,toolbar=no\";");
    sbRes.append("\n  hWinMovimiento = window.open(\"" + vParametros.getPropEspecifica("URL_BaseSistema") + "TRunJasperReport.pdf\", \"FRMPDFReport\", cConds);");
    sbRes.append("\n  if(hWinMovimiento){");
    sbRes.append("\n    hWinMovimiento.moveTo((screen.availWidth - 950) / 2, (screen.availHeight - 700) / 2);");
    sbRes.append("\n  }");
    sbRes.append("\n}");
    sbRes.append("\nfAbrePDF();");
    sbRes.append("</script>");
    return sbRes;
  }
}

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String cOutputPaths,cCadFirmada,cNombre,cCURP,cRFC,cFirmNum,cLocSerialNumber,cLocIssuerDN,cLocSubjectDN,iCveCertificado;
try{
	
	
	//System.out.print("++++++++++++++  Entra en el try");
   cOutputPaths = ""+request.getParameter("cOutputPaths");
   cCadFirmada = ""+request.getParameter("cCadFirmada");
   cNombre = ""+request.getParameter("cNombre");
   cCURP = ""+request.getParameter("cCURP");
   cRFC = ""+request.getParameter("cRFC");
   
   cFirmNum = ""+request.getParameter("cFirmNum");
   cLocSerialNumber = ""+request.getParameter("cLocSerialNumber");
   cLocIssuerDN = ""+request.getParameter("cLocIssuerDN");
   cLocSubjectDN = ""+request.getParameter("cLocSubjectDN");
   iCveCertificado = ""+request.getParameter("iCveCertificado");
   //System.out.print("++++++++++++++  sale del try");
}catch(Exception e){
	//System.out.print("------------------Cayo en el catch");
    cOutputPaths = "";
    cCadFirmada = "";
    cNombre = "";
    cCURP = "";
    cRFC = "";
    
    cFirmNum = "";
    cLocSerialNumber = "";
    cLocIssuerDN = "";
    cLocSubjectDN = "";
    iCveCertificado = "";
}

%>
<html>
<body>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
window.parent.parent.firmaSuccess('<%=cOutputPaths%>','<%=cCadFirmada%>','<%=cNombre%>','<%=cCURP%>','<%=cRFC%>','<%=cFirmNum%>','<%=cLocSerialNumber%>','<%=cLocIssuerDN%>','<%=cLocSubjectDN%>','<%=iCveCertificado%>');
</SCRIPT>

// MetaCD=1.0
var frm;

function fBefLoad(){
}

function fDefPag(){
    cGPD='<HTML><BODY bgcolor="' + cColorGenJS + '" onLoad="fOnLoad();">'+ '<FORM ACTION="ConsultaExcel" METHOD="POST" ENCTYPE="multipart/form-data">';
    Hidden("paramFiltro","");
    Hidden("paramOrden","");
    fFinPagina();
}

function fOnLoad(){
  frm = document.forms[0]; 
  fSubmit();      
}

function fSubmit(){
	
  if(top.opener){
	  if(top.opener.getFiltroADVExcel && top.opener.getOrdenADVExcel){
		  frm.paramFiltro.value=top.opener.getFiltroADVExcel();
		  frm.paramOrden.value=top.opener.getOrdenADVExcel();
		  
		  frm.action = "ConsultaExcel?paramA=" + frm.paramFiltro.value + "&paramB=" + frm.paramOrden.value;               
		  frm.submit();
	  }
  }else{
	  fAlert("\nNo ha sido posible obtener datos de la consulta. Intente nuevamente.");
  }
	
} 
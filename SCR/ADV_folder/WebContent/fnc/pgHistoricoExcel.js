// MetaCD=1.0
var frm;

var valA=-1;

function fBefLoad(){
}

function fDefPag(){
    cGPD='<HTML><BODY bgcolor="' + cColorGenJS + '" onLoad="fOnLoad();">'+ '<FORM ACTION="DownloadHistoricoExcel" METHOD="POST" ENCTYPE="multipart/form-data">';
    fFinPagina();
}

function fOnLoad(){
  frm = document.forms[0]; 
  fSubmit();      
}

function fSubmit(){
	
  if(top.opener){
	  if(top.opener.getCveDoc){
		  valA=top.opener.getCveDoc();
		  if(valA>0){
			  frm.action = "DownloadDocHistorico?paramA="+valA;               
			  frm.submit();
		  }else
			  fAlert("\nNo ha sido posible obtener datos. Intente nuevamente.");
	  }else
		  fAlert("\nNo ha sido posible obtener datos. Intente nuevamente.");
  }else{
	  fAlert("\nNo ha sido posible obtener datos. Intente nuevamente.");
  }
}
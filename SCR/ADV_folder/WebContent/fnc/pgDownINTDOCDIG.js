// MetaCD=1.0
var frm;
var docto = "";

function fBefLoad(){
}

function fDefPag(){
    cGPD='<HTML><BODY bgcolor="' + cColorGenJS + '" onLoad="fOnLoad();">'+ '<FORM ACTION="DownloadINTDOCDIG" METHOD="POST" ENCTYPE="multipart/form-data">';              
    Hidden("ICVEVEHDOCDIG",top.opener.fGetINTDOCDIG());
    //docto = top.opener.fGetINTDOCTO();
  fFinPagina();
}
function fOnLoad(){
  frm = document.forms[0]; 
  fSubmit();      
}
function fSubmit(){
  //alert("ICVEVEHDOCDIG-->"+frm.ICVEVEHDOCDIG.value + " CDOCTO-->" + docto);
  frm.action = "DownloadINTDOCDIG?ICVEVEHDOCDIG=" + frm.ICVEVEHDOCDIG.value + "&CDOCTO=" + docto;               
  frm.submit();
} 

var aListado='';
var aEtiqueta;
var aValor;
var iRR = 0;
var tBdy;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
 }
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){
   cGPD='<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'+
        '<form name="form1" enctype="multipart/form-data" method="post" action="UploadINTDocs">';
   LinkSource();
   InicioTabla("ETablaInfo",0,"100%","100%","","","1");
     ITRTD("ESTitulo",0,"100%","1","center");
       TextoSimple("Documentos Digitales a Adjuntar");
     FTDTR();
     ITRTD("",0,"","","top");
        DinTabla("TDBdy","",0,"75%","100%","center","","",".1",".1");
     FTDTR();
     FinTabla();
   fFinPagina();
 }
 function fDelTBdy(){
	   for(i=0;tBdy.rows.length;i++){
	      tBdy.deleteRow(0);
	   }
 }
 function fOnLoad(){
   frm = document.forms[0];
   theTable = (document.all) ? document.all.TDBdy:document.getElementById("TDBdy");
   tBdy = theTable.tBodies[0]; 
   fDelTBdy();
 }
 
 function fLoadDocs(iCveTramiteINT,iCveConsecutivo,cPListado,cPEtiqueta,cPValor,lValores){
   //alert(''+iCveTramiteINT+'.'+iCveConsecutivo+'.'+cPListado+'.'+cPEtiqueta+'.'+cPValor+'.'+lValores);
   fDelTBdy();
   aListado  = cPListado.split('||'); 
   aEtiqueta = cPEtiqueta.split('||');
   aValor    = cPValor.split('||');
   iRR = 0;
   if(lValores == false){
      for(i=0;i<aListado.length;i++){
   	     newRow  = tBdy.insertRow(iRR++);
         newCell = newRow.insertCell(0);
         newCell.className = "EEtiqueta";                                                          
         newCell.innerHTML = aEtiqueta[i]+":";
         newCell = newRow.insertCell(1);
         newCell.className = "ECampo";    
         newCell.innerHTML = Hidden("hF"+aListado[i],""+iCveTramiteINT+","+iCveConsecutivo+","+aEtiqueta[i]+","+aListado[i]) + fInput("file",aListado[i],"",100,100,aEtiqueta[i]);	                
      }
   }else{
	   
   }
 } 
 function fGetDocs(){
	 cFiles = "";
	 for (i=0; i<frm.elements.length;i++){
	     obj = frm.elements[i]; 
	     if (obj.type == 'file'){
	    	 cFiles += obj.value.replace(/\\/g,'/') + '||';
	     }
	 }
	 return cFiles;
 }
 function fGuardarDocs(){
	 if(aListado.length > 0){
	    frm = document.forms[0];
	    frm.submit(); 
	 }
 }

	// MetaCD=1.0 
var tBdy;
var aSelect;
var aAT;
var iSel;
var iAT;
var fScripts = "";
var iCont1 = 0;
var guardado = 0;
var iAdjuntar = 0;
var aListado="";
var aEtiqueta="";
var aValor="";
var lValores = false;
var lGuardar = false;
var cCADOriginal = "";
var cCADFirmada  = "";
var cNOMCOM = "";
var aObj = "";
var iOrdHF=0;
var lFin=0;
var aIngresos;

function fBefLoad(){ // Carga información antes de que la página sea mostrada.
   cPaginaWebJS = "pgINTSol02FIEL.js";
   cTitulo = "SOLICITUD DE TRÁMITES POR INTERNET CON F.I.E.L. DE LA D.G.P.M.M.";
}
function fDefPag(){
    guardado = 0;
    cGPD+='<SCRIPT LANGUAGE="JavaScript" SRC="http://servidorimagenes.sct.gob.mx/siiaf/vehmarcacat.js"></SCRIPT>';
    cGPD+='<html><body bgcolor="" topmargin="0" leftmargin="0" onLoad="fOnLoad();">'+
    '<form name="form1" enctype="multipart/form-data" method="post" action="UploadINTDocs">';
	fInicioPagina("E4E4E4");
		Estilo("A:Link","COLOR:BLACK;font-family:Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
		Estilo("A:Visited","COLOR:BLACK;font-family: Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");
		Estilo(".ELIGA","COLOR:BLACK;font-family: Verdana;font-size:10pt;font-weight:bold;TEXT-DECORATION:none");   
		Estilo(".ESTitulo1","COLOR:BLACK;font-family: Verdana;font-size:10pt;font-weight:bold;");  
		Estilo(".EFirma","COLOR:BLACK;font-family:Lucida Console;font-size:10pt;font-weight:normal;text-align:left;");

		InicioTabla("",0,"100%","","","","1");//InicioTabla("",0,"100%","100%","center","",".1",".1");
			ITRTD("EEtiquetaC",7,"","","center");//ITRTD("ESTitulo",2,"","1","","center");
				TextoSimple(cTitulo);
			FTDTR();
 			ITRTD("",0,"","","top"); 
   			InicioTabla("",0,"100%","100%","center","",".1",".1"); 
   				ITRTD("ESTitulo1",0,"100%","100%","center","top");    
					InicioTabla("",0,"100%","1","center"); 
						ITRTD("EEtiquetaC",0,"100%","25","center","top");
							TextoSimple("TRÁMITE NÚMERO <span id='_icvetramite'></span>");
						FTDTR(); 
						ITRTD("",0,"","","center");
							DinTabla("TDBdy","",0,"90%","","center","","",".1",".1");
						FTDTR();
						
						ITRTD("EEtiquetaC",0,"100%","","center","top");
						    //IFrame("IADJUNTAR","0px","0px","pgINTSolAdjuntos.js");
						    //DinTabla("TDADJUNTAR","",0,"90%","","center","middle","","","");						    						
						FTDTR(); 
						
						ITRTD("EEtiquetaC",0,"100%","25","center","top");
							TextoSimple("Declaro bajo protesta de decir la verdad que la documentación presentada para acreditar el cumplimiento de todos y cada uno de los requisitos son auténticos y ciertas las características del vehículo que se describe, dejando a salvo la facultad de la Secretaría de Comunicaciones y Transportes para constatar lo manifestado, aceptando que de comprobarse lo contrario dará motivo a la revocación del permiso que en su caso se genere");
						FTDTR(); 
						ITRTD();
							InicioTabla("",0,"85%","","center","",1); 
								ITRTD("EEtiquetaC",0,"33%","","center","top");
										SP();
									FITD("EEtiquetaC",0,"33%","","center","top");
										Etiqueta("","", "<BR>FIRMA DIGITAL DEL REPRESENTANTE LEGAL"); 
									FITD("EEtiquetaC",0,"33%","","center","top");
										SP();
									FTD();
								FTR();
                               //
								ITRTD("",5,"","","center");
								  DinTabla("TDFIEL","",0,"90%","","center","middle","","","");
			    				FTDTR();			    							    			
								//
							FinTabla();	
						FTDTR();
						ITRTD("","","","40","center");
							IFrame("IPanel","515","34","Paneles.js");
						FTDTR();
					FinTabla();
					FTDTR(); 
				FinTabla();                     
 			FTDTR();     
 		FinTabla(); 
		Hidden("ICVETRAMITE","");
		Hidden("ICONSECUTIVO","");
		Hidden("ICVETIPOTRAMITE",""); 
		Hidden("LFINALIZADO","0");
		Hidden("cCVE","");  
		Hidden("LFGA","0");
		Hidden("cDSC","");
		Hidden("cTABLA","");  
		Hidden("cOBJ","");
		Hidden("cCamSol","");
		Hidden("iGRLDepto","");
		Hidden("cVD","");
		Hidden("iCveSol","");
		Hidden("ICVETIPOPERMISIONATRAMITE"); //es el ICVETIPOPERMISIONA del trámite que se está registrando, 
		//para saber los tipos de filtros que se apliquen a algunos catalogos configurables.
		Hidden("CTIPOPERMISIONARIO");
		Hidden("C2012");
		Hidden("iNumCita")
		Hidden("iCveTramite");
		Hidden("iCveModalidad");
		Hidden("iEjercicio");
		Hidden("iNumSolicitud");
		Hidden("cRequisitos");
		Hidden("cRFCRep1","");
		Hidden("cCveCertificado");
		Hidden("cFirmante");
	fFinPagina();
}

function fOnLoad(){ // Carga información al mostrar la página.
   frm = document.forms[0];
   theTable = (document.all) ? document.all.TDBdy:document.getElementById("TDBdy");
   tBdy = theTable.tBodies[0]; 
   theTableFIEL = (document.all) ? document.all.TDFIEL:document.getElementById("TDFIEL");
   tBdyFIEL = theTableFIEL.tBodies[0];
   theTableADJUNTA = (document.all) ? document.all.TDADJUNTAR:document.getElementById("TDADJUNTAR");
   tBdyADJUNTA = theTableFIEL.tBodies[0];
   
   fDelTBdy();  
   fDelTBdyFIEL();   
   //fGenADJUNTA();
   FRMPanel = fBuscaFrame("IPanel"); 
   FRMPanel.fSetControl(self,cPaginaWebJS); 
   FRMPanel.fShow("Tra,");
   guardado = 0;
   frm.LFGA.value = "0";
   frm.LFGA.value = "0";
   fNewTram();
}

/****
 * se modicia las funciones de adjuntar archivos por medio de esta funcion
 * @return
 */
/*function fGenADJUNTA(){
	
	fDelTBdyADJUNTA();
    newRow  = theTableADJUNTA.insertRow(0);
    newCell = newRow.insertCell(0);
    newCell.className = "EEtiquetaC";                                                          
    newCell.innerHTML = IFrame("IADJUNTAR","0px","0px","pgINTSolAdjuntos.js"); 
}*/

function setValues(nodo){
     frm.iNumCita.value = nodo.ICONSECUTIVO;
     frm.iCveTramite.value = nodo.ICVETRAMITE;
     frm.iCveModalidad.value = nodo.ICVEMODALIDAD;
     //frm.iNumCita.value = iNumCita;
     //frm.iCveTramite.value = icveTramite;
     //frm.iCveModalidad.value = iCveModalidad;
    frm.LFGA.value = "0";
	FRMTramites = fBuscaFrame("INT1");
    /*fGO("_icvetramite").innerHTML = FRMTramites.fGetEjercicio() + "/" + FRMTramites.fGetSolicitud() +
        "<BR><BR> Tramite: "+FRMTramites.getCTIPOPERMISIONARIO() +"<BR>Modalidad: "+FRMTramites.getCDSCTIPOTRAMITE()+
        "<BR><BR> Los documentos digitales que se anexen deberán encontrarse digitalizados bajo formato PDF,<BR>y no deberán de exceder de 2 MBytes por archivo." + 
	"<BR><BR>Deberá adjuntar los documentos que se indican como obligatorios (*), en caso de no adjuntar los<BR>documentos requeridos será sujeto a una notificación de prevención o rechazo.";*/
}


function fNewTram(){
    FRMTramites = fBuscaFrame("INT1");	
    if(FRMTramites.fGetEjercicio()>0){
        fGO("_icvetramite").innerHTML = FRMTramites.fGetEjercicio() + "/" + FRMTramites.fGetSolicitud() +
        "<BR><BR> Trámite: "+FRMTramites.getCTIPOPERMISIONARIO() +"<BR>Modalidad: "+FRMTramites.getCDSCTIPOTRAMITE()+
        "<BR><BR> Los documentos digitales que se anexen deberán encontrarse digitalizados bajo formato PDF,<BR>y no deberán de exceder de 2 MBytes por archivo." + 
    	"<BR><BR>Deberá adjuntar los documentos que se indican como obligatorios (*), en caso de no adjuntar los<BR>documentos requeridos será sujeto a una notificación de prevención o rechazo.";
        frm.iNumCita.value = FRMTramites.frm.iNumCita.value;
        frm.iCveTramite.value = FRMTramites.frm.iCveTramiteTmp.value;
        frm.iCveModalidad.value = FRMTramites.frm.iCveModalidadTmp.value;
 	frm.cRFCRep1.value = window.parent.fGetRFCRep();
        if(FRMTramites.frm.iCveTramiteTmp.value>0 && FRMTramites.frm.iCveModalidadTmp.value>0){
            frm.hdFiltro.value = "INTTRAMXCAMPO.ICVETRAMITE = "+frm.iCveTramite.value+
            " and INTTRAMXCAMPO.ICVEMODALIDAD = "+frm.iCveModalidad.value;
            frm.hdOrden.value = "ICVEREQUISITO,IORDEN";
            frm.hdNumReg.value  = "10000";
            fEngSubmite("pgINTShow.jsp","IDShow"); 	
        }
    }
}

function fResultado(aRes,cId,cError,cNavStatus,cSol,aRes2,cOrigen,cFirma,cFirmante){
    if(cError!="")fAlert("Error\n"+cError);
   if(cId == "IDShow" && cError == ""){
      if(aRes.length > 0){
        if(aRes2.length > 0)
          guardado = 1;
        else
          guardado = 0;
        aObj = fCopiaArreglo(aRes);
        //fGenSol(aRes,cSol,aRes2,cOrigen,cFirma);
        fGenSol(aRes,cSol,aRes2,cOrigen,cFirma,cFirmante.replace(/~/g,'<BR>'));
        if(frm.cRFCRep){
   	  frm.cRFCRep.value = window.parent.fGetRFCRep();
   	  frm.cRFCRep.disabled = true;
        }
        if(frm.cRFC){
      	  frm.cRFC.value = window.parent.fGetRFCRep();
      	}
        
        if(lGuardar == true){
           lGuardar = false;
           fAbreSubWindow(true,'IntReciboINT','yes','yes','yes','yes','976','800',50,50);
        }
      }else{
         fAlert("\n - La solicitud o el trámite no fueron encontrados.");
         fCancelar();
      }          
   }  
   if(cId.substring(0,4) == "IDCS" && cError == ""){
      for(sdf=0;sdf<frm.elements.length;sdf++){
         obj = frm.elements[sdf];
         if(obj.name == cNavStatus){
         	if(obj.name == "ICVEPAIS1" || obj.name == "ICVEPAIS2"){
         		fFillSelect(obj,aRes,false,1);
         	}else{
            	fFillSelect(obj,aRes,false,cSol);        
            }
         }         
      }
   }   
}
function fDelTBdy(){
	   for(i=0;tBdy.rows.length;i++){
	      tBdy.deleteRow(0);
	   }
}
function fDelTBdyFIEL(){
	   for(i=0;tBdyFIEL.rows.length;i++){
	      tBdyFIEL.deleteRow(0);
	   }
}
function fDelTBdyADJUNTA(){
	   for(i=0;tBdyADJUNTA.rows.length;i++){
		   tBdyADJUNTA.deleteRow(0);
	   }
}
/**
 * @param _cFiltro es el filtro de la consulta prefabricado con el cCVE
 */
function fSetOnchange(cCampos, cCampoFiltro,_cObj, _vd, _cFiltro){
	if(cCampos != "" && cCampos != "NOLLENAR"){
		aChange = cCampos.split(",");
		
		frm.cTABLA.value = aChange[0];                   
		frm.cCVE.value   = aChange[1];
		frm.cDSC.value   = aChange[2];  
		frm.cOBJ.value   = aChange[3]; 
		frm.cVD.value    = _vd;
		frm.hdNumReg.value = 10000;
		frm.hdFiltro.value = cCampoFiltro + " = " + eval("frm." + _cObj + ".value");
		frm.hdOrden.value = frm.cDSC.value;        
		fEngSubmite("pgINTCatShow.jsp","IDCS" + _cObj); 
	}
}

function fGenSol(aRes,cSol,aRes2,cOrigen,cFirma,cFirmante){
   fDelTBdy();
   fScripts = "";
   if(aRes.length > 0){
      aAT = new Array();
      iAT=0;     
      aSelect=new Array();
      iSel = 0;   
      iCell = 0;     
      iHdrAnt = 0;
      fCallback = "";
      cTabla = InicioTabla("ETablaInfo",0,"100%","","","",".1",".1");
      cIntroTabla = '';cTitTabla='';
      lValores = false;

      if(aRes2.length > 0)
         lFin = 1;
      else
    	 lFin=0;
      
      for(i=0;i<aRes.length;i++){
         if(aRes[i][10] == 0){
            if(cIntroTabla == '')
               cTabla += ITR()+ITD("EEtiqueta")+fSetField(aRes[i],true,aRes2)+FTR();
            else{
               cTabla += ITRTD("",20,"","","center")+InicioTabla("ETablaInfo",0)+
                         ITR()+cTitTabla+FTR()+               
                         ITR()+cIntroTabla+FTR()+
                         FinTabla()+FTDTR();
               cIntroTabla = '';
               cTitTabla='';             
            }            
         }else{
            cCampoTit = '';
            cIntroTabla += ITD("EEtiqueta")+TextoSimple(aRes[i][14])+fSetField(aRes[i],false,aRes2); 
            cTitTabla += ITD("ESTitulo")+aRes[i][2]+FTD();    
            if(aRes[i][4] == 5) cTitTabla = '';                             
         }                                 
         iHdrAnt = aRes[i][10];
      }
      
      
      if(cIntroTabla != '')
         cTabla += ITRTD("",20,"","","center")+InicioTabla("ETablaInfo",0)+
                   ITR()+cTitTabla+FTR()+               
                   ITR()+cIntroTabla+FTR()+
                   FinTabla()+FTDTR();
      iRR=0;                   
      newRow  = tBdy.insertRow(iRR++);
      newCell = newRow.insertCell(0);                                                        
      newCell.innerHTML = Hidden("ICVETRAMITEDIN",""+frm.ICVETRAMITE.value)+
                          Hidden("ICONSECUTIVODIN",""+frm.ICONSECUTIVO.value)+
                          Hidden("cCamSolDIN","");            
      if(cTabla != ''){      
         if(cSol != ''){
            newRow  = tBdy.insertRow(iRR++);
            newCell = newRow.insertCell(0);
            newCell.className = "ESTitulo";                                                          
            newCell.innerHTML = "SOLICITUD: "+cSol;   
            frm.iCveSol.value = cSol;     
         }      
         newRow  = tBdy.insertRow(iRR++);
         newCell = newRow.insertCell(0);                                                         
         newCell.innerHTML = cTabla+FinTabla();
      }      

      /****
       * Campos de Ingresos
       */
      FRMTramites = fBuscaFrame("INT1");
      aIngresos = FRMTramites.fGetIngresos();
      if(aIngresos.length > 0){
    	  cTabla = InicioTabla("ETablaInfo","95%");
    	  cTabla += ITRTD("ESTitulo",10) + "Conceptos de Ingresos ligados a este trámite." + FTDTR();
    	  cTabla += ITRTD("ESTitulo") + "Ejercicio" + FITD("ESTitulo") + "Inicio Vigencia" + FITD("ESTitulo") + "Fin Vigencia" + FITD("ESTitulo") + "Sin Ajuste" + FITD("ESTitulo") + "Con Ajuste" + FITD("ESTitulo") + "Trámite o Concepto" + FTDTR();
          for(h=0;h<aIngresos.length;h++){
        	  cTabla +=  ITRTD("EEtiquetaC") + aIngresos[h][0] + FITD("EEtiquetaC") + aIngresos[h][2]+ FITD("EEtiquetaC") + aIngresos[h][3] + FITD("EEtiquetaC") + aIngresos[h][6] + FITD("EEtiquetaC") + aIngresos[h][7] + FITD("EEtiquetaL") + aIngresos[h][1] + " - " + aIngresos[h][8] + "..." + FTDTR();           
          }
          cTabla += ITRTD("EEtiquetaC",10) + Liga("Si desea generar el formato de ayuda a través del sistema e5cinco de clic aquí.","fLlamae5();") + FTDTR();          
          cTabla += FinTabla();
          newRow  = tBdy.insertRow(iRR++);
          newCell = newRow.insertCell(0);  
          newCell.colSpan = 20; 
          newCell.className = "EEtiquetaC";     
          newCell.innerHTML = cTabla;        
      }
      if(cOrigen != "null"){
	newRow  = tBdyFIEL.insertRow(0);
  	newCell = newRow.insertCell(0);
  	newRow.className = "EEtiquetaL";
  	newRow.className = "EFirma";
  	
  	
  	newCell.innerHTML = '<BR><p align="center" class="Efirma">CADENA ORIGINAL:(/p>' + cOrigen ;
  	    
        cFirma = cFirma.replace(/~/g,'<BR>');
       
       
  	//newCell.innerHTML = '<BR><p align="center">CADENA ORIGINAL:</p>' + cOrigen ;

	newRow  = tBdyFIEL.insertRow(1);
  	newCell = newRow.insertCell(0);
  	newRow.className = "EEtiquetaL";
  	newRow.className = "EFirma";
  	//newCell.innerHTML = '<BR><p align="center">CADENA DE FIRMADO:</p>' + cFirma;
  	newCell.innerHTML = '<BR><p align="center" class="EEtiquetaC">CADENA DE FIRMADO:</p>' + InicioTabla("",1,"600px") + ITRTD("EFirma",0,"1px") + cFirma + FTDTR() + FinTabla() + 
  	                    '<BR><p align="center" class="EFirma">CERTIFICADO:</p><p align="JUSTIFY">' + cFirmante + "</p>";
      }
          // Genera Campos de Captura
          newRow  = tBdy.insertRow(iRR++);
	  newCell = newRow.insertCell(0);
	  newRow.className = "EEtiquetaL";
	  newCell.innerHTML = Hidden("CCADORIGEN","") + Hidden("CCADFIRMA","") + Hidden("CRFCFirma","");
	  
      for(it=0;it<aAT.length;it++){
        fGetObj(aAT[it][0],aAT[it][1]);                      
      }      
      for(it=0; it<aSelect.length;it++){
      	fLlenaCombos(aSelect[it]);
      }
      if(aSelect.length > 1){
      	fLlenaCombos(aSelect[1]);
      }
      if(aSelect.length > 2){		//NO SE PORQUE PERO HAY QUE HACER ESTO SI NO NI SE LLENAN LOS COMBOS
      	fLlenaCombos(aSelect[2]);
      }
      if(aSelect.length > 3){		//NO SE PORQUE PERO HAY QUE HACER ESTO SI NO NI SE LLENAN LOS COMBOS
			fLlenaCombos(aSelect[3]);
      }
      FRMPanel.fSetTraStatus("UpdateBegin");   
      if(aRes2.length > 0){
      	if(frm.LFINALIZADO.value == 0){
         	FRMPanel.fSetTraStatus(",");
         }else{
         	FRMPanel.fSetTraStatus("Can,");
         }
         fDisabled(true); 
      }
   }
}

function fLlenaCombos(aRowCombo){
	frm.cTABLA.value = aRowCombo[0];                   
	frm.cCVE.value   = aRowCombo[1];
	frm.cDSC.value   = aRowCombo[2];  
	frm.cOBJ.value   = aRowCombo[3]; 
	frm.cVD.value    = aRowCombo[4];
	if(frm.cTABLA.value == "ACUSTOM"){
		iLength = fNumEntries(frm.cCVE.value,",");
		aCustom = new Array();
		if(iLength > 0){
    		for(i=0;i<=iLength;i++){
				aCustom[i] = [fEntry(i+1,frm.cCVE.value,","),fEntry(i+1,frm.cDSC.value,",")];
    		}
    	}
		fFillSelect(eval("frm." + frm.cOBJ.value),aCustom,false,frm.cVD.value,0,1);
	}else if(frm.cTABLA.value == "ANUMERIC"){
		iInicio = parseInt(frm.cCVE.value,10);
		iFin = parseInt(frm.cDSC.value,10);
		aNumeric = new Array();
		iIndex = 0
		for(iI = iInicio; iI <= iFin; iI++){
			aNumeric[iIndex] = [iI,iI];
			iIndex++;
		}
		fFillSelect(eval("frm." + frm.cOBJ.value),aNumeric,false,frm.cVD.value,0,1);
	}else if(frm.cTABLA.value == "ALOGICAL"){
		aLogical = new Array([0,"No"],[1,"Si"]);
		fFillSelect(eval("frm." + frm.cOBJ.value),aLogical,false,frm.cVD.value,0,1);
	}else if(frm.cTABLA.value == "VEHMARCA"){
		fFillSelect(eval("frm." + frm.cOBJ.value),aMarca,false,frm.cVD.value,0,1);
	}else if(frm.cTABLA.value == "PERTIPOPERMISIONAR"){
		fAsignaSelect(eval("frm." + frm.cOBJ.value),frm.ICVETIPOPERMISIONATRAMITE.value, frm.CTIPOPERMISIONARIO.value);
	}else{
		frm.hdNumReg.value = 10000;
		if(frm.cTABLA.value == "PERTIPOSERVICIO"){
			frm.hdFiltro.value = 'ICVETIPOPERMISIONA = ' + frm.ICVETIPOPERMISIONATRAMITE.value;
		}else{
			frm.hdFiltro.value = '';
		}
		if(aRowCombo[5] != "NOLLENAR"){
			frm.hdOrden.value = frm.cDSC.value;
			fEngSubmite("pgINTCatShow.jsp","IDCS");
		}
	}
}

function fGetObj(cNombre,cValor){
   for(xc=0;xc<frm.elements.length;xc++){
      obj=frm.elements[xc];
      if(obj.name == cNombre){
         obj.value = cValor;
//         break;
      }
   }
}
function fSetField(aField,lEtiqueta,aRes2){
     cMan='';cCampo='';cAst='';                  
     if(aField[8] == 1){
        cMan = Hidden('HDMF-1-'+aField[1],aField[2]);
        cAst='*';     
     } 
     if(lEtiqueta == true && aField[4] != 7)
        cCampo = ITD("EEtiqueta")+TextoSimple(cAst + aField[2]+":"+SP())+FTD();
      
     if(aField[4] == 0){ // Agrupador
        cCampo = ITD("ESTitulo",20)+aField[2]+FTD();
     }
     if(aField[4] == 1){ // AreaTexto
        cValor = '';       
        iSelRec = fGetValue(aRes2,aField[1]);
        if(iSelRec != -1)
           cValor = aRes2[iSelRec][3]; 
        aAT[iAT++] = [aField[1],cValor];                           
        cCampo += ITD()+cMan+AreaTexto(false,40,3,aField[1],"",aField[2],"","fMayus(this);",'onkeydown="fMxTx(this,'+aField[3]+');"')+FTD();                                 
     }
     if(aField[4] == 2){ // Texto
        cValor = '';       
        iSelRec = fGetValue(aRes2,aField[1]);
        if(iSelRec != -1)
           cValor = aRes2[iSelRec][3];           
        cCampo += ITD()+cMan+Text(false,aField[1],cValor,aField[3],aField[3],aField[2],"fMayus(this);")+FTD();
     }
     if(aField[4] == 3){ // Select
        cValor = '';       
        iSelRec = fGetValue(aRes2,aField[1]);
        if(iSelRec != -1) cValor = aRes2[iSelRec][3];     
        cCampo += ITD()+Select(aField[1],"fSetOnchange('" + aField[12] + "', '" + aField[6] +"', '" + aField[1] + "', '" + cValor + "');")+FTD();
        aSelect[iSel]=[aField[5],aField[6],aField[7],aField[1],cValor,aField[12]];
        iSel++;
     }
     if(aField[4] == 4){ // Image
        cCampo = ITD("EEtiquetaC",20)+Img(aField[1],aField[2])+FTD();                                          
     }
     if(aField[4] == 5){ // CAMBIO DE RENGLÓN
        cCampo = FITR();                                          
     }
     if(aField[4] == 6){ // LIGA
     iAdjuntar = 1;
        cValor = '';       
             iSelRec = fGetValue(aRes2,aField[1]);
             if(iSelRec != -1)
                cValor = aRes2[iSelRec][3]; 
        aAT[iAT++] = [aField[1],cValor]; 
             cCampo += ITD()+Liga("[ADJUNTAR]","fLiga1('"+aField[12]+"');","Integrar Trámite","lnkinternet");  
     }  
     if(aField[4] == 7){ // Adjuntar
         cValor = '';
         iSelRec = fGetValue(aRes2,aField[1]);
         if(iSelRec != -1)
             cValor = aRes2[iSelRec][3];
         if(lFin == 0){
             cCampo += ITD("EEtiquetaL",0)+TextoSimple(aField[2]+":")+FITD()+cMan+
             Hidden("CDOCUMENTO"+(iOrdHF++),aField[2])+
             Hidden("CCAMPO"+(iOrdHF++),aField[1])+
             '<input type="file" name="'+aField[1]+'" size="50">'+
             FTDTR();
         }else{
             try{
        	 cLiga = aRes2[iSelRec][4];
             }catch(e){
        	 cLiga = "";
             }
    	if(cLiga.length > 4){
    	    cLiga = Liga("[Ver Documento]","fShowIntDocDig("+cLiga.substring(4)+");");
    	    
    	}
    	else
    		cLiga = TextoSimple("No se digitalizó el documento.");
    	cCampo += ITD()+ITRTD("EEtiqueta",0,"","20")+ITD("EEtiqueta",0,"","20")+TextoSimple(aField[2])+FITD("EEtiquetaC")+SP()+cLiga+FTDTR();        	 
     }
     aEtiqueta += "||" + aField[2];
     aListado  += "||" + aField[1];         
     aValor    += "||" + cValor;
     if(cValor != "")
    	 lValores = true;
  }
     if(aField[13] != ""){
        fScripts += aField[13];
     }
     

     return cCampo;         
}

function fGetValue(aRes2,cNCam){
   for(tyu=0;tyu<aRes2.length;tyu++){
      if(aRes2[tyu][2] == cNCam){
         return tyu;          
      }
   }
   return -1;
}

function fNuevo(){
  if(fSoloNumeros(frm.iCveTipoTramBus.value)==true && frm.iCveTipoTramBus.value != ''){
     frm.iCveSol.value = '';  
     //frm.cBusca.value = '';
     fNewTram();
     //frm.cBusca.disabled=true;
  }else{
     fAlert("\n - No ha seleccionado él trámite a precapturar.");  
  }
}
function firmaSuccess(outputPaths,cCadFirmada,cNombre,cCURP,cRFC,cFirmNum,cLocSerialNumber,cLocIssuerDN,cLocSubjectDN,iCveCertificado){ 
//firmaSuccess(iCvetramite,outputPaths,cCadFirmada,cNombre,cCURP,cRFC){
        frm.cFirmante.value = 'Asunto:~'+cLocSubjectDN+'~~Emisor:~'+cLocIssuerDN+'~~Número Serial:~'+cLocSerialNumber;
        frm.cCveCertificado.value = iCveCertificado;
	cCADFirmada = cFirmNum;
	frm.CCADFIRMA.value = cFirmNum;
	frm.CRFCFirma.value = cRFC;
	cNOMCOM = cRFC + ' - ' + cNombre;
	FRMPanel.fSetTraStatus("UpdateComplete");
	lGuardar = true;
	frm.hdBoton.value = 'Guardar';
	fDelTBdyFIEL();	
	frm.LFGA.value = 1;
	frm.submit();
}

function fGetICVETRAMITEINT() { return frm.iNumCita.value; }
function fGetICONSECUTIVO() { return frm.ICONSECUTIVO.value; }
function fGetCCADORIGINAL() { return cCADOriginal;}
function fGetCCADFIRMADA() { return cCADFirmada; }
function fGetCNOMPERSONA() { return cNOMCOM; }

function firmaCancel(){
	fCancelar();
	fDelTBdyFIEL();
}
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}
function fGetCadOriginal(){	
    cRequisitos = "";
	cCadena = "";
    for(iQ=0; iQ<form.elements.length;iQ++){
       obj = form.elements[iQ];
       if (obj.type == 'text' || obj.type == 'textarea' || obj.type == 'select-one'){
           cValor=obj.value;           
           if(obj.type == 'select-one' && cValor != ""){
               cValor2 = "";
               if(obj[obj.selectedIndex].text != ""){
        	   cValor2 = obj[obj.selectedIndex].text;
               }
               cCadena += "/"+obj.name+"-"+cValor.trim()+"-"+cValor2.trim();
           }else{
               cCadena += "/"+obj.name+"-"+cValor.trim();
           }           
       }
       for (i=0;i<aObj.length;i++){
	   if(obj.name==aObj[i][1]){
	       cRequisitos+=aObj[i][14]+",";
	   }
       }
    }
    var aReq = new Array();
    var aNuevo = new Array();
    aReq = cRequisitos.split(",");
    for (i=0;i<aReq.length;i++){
	var lEncontrado=false;
	for (j=0;j<aNuevo.length;j++){
	    if(aReq[i]==aNuevo[j])lEncontrado=true;
	}
	if(lEncontrado==false){
	    aNuevo[aNuevo.length] = aReq[i];
	}
    }
    frm.cRequisitos.value = "";
    for(i=0;i<aNuevo.length;i++){
      frm.cRequisitos.value += aNuevo[i] + ((i+1)<aNuevo.length?",":"");
    }
    
    if(cCadena.length > 0)
    	cCadena = cCadena.substring(1);
    cCADOriginal = cCadena;
	return cCADOriginal;
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
function fGuardar(){
   if(fValidaTodo()==true){
       var cMsg1 = '¿Todos los datos ya se encuentran revisados? \n\nDado que se firman digitalmente los archivos junto con el formulario, \nuna vez guardado no podrá realizar modificaciones a la solicitud: ' + frm.iNumCita.value;
     if(confirm(cMsg1) == true){
    	cCadenaOriginal = fGetCadOriginal();
     	lGuardar = false;
    	iRow = 0;
    	newRow  = tBdyFIEL.insertRow(iRow++);
    	newCell = newRow.insertCell(0);
    	newRow.className = "EEtiquetaC";
    	newCell.align = "center";

	FRMPanel.fSetTraStatus("UpdateComplete");
	lGuardar = true;
	frm.hdBoton.value = 'Guardar';
	FRMTramites = fBuscaFrame("INT1");
	frm.iEjercicio.value = FRMTramites.fGetEjercicio();
	frm.iNumSolicitud.value = FRMTramites.fGetSolicitud();	
	
    	//FRMAdj = fBuscaFrame("IADJUNTAR");    	
    	newCell.innerHTML = '<iframe name="FIEL" width="550px" height="350px" frameborder=0 scrolling=no SRC="'+cRutaFIEL+'?cListado='+fGetDocs()+'&cCadena=' + cCadenaOriginal + '&cRutaJSPFIEL='+cRutaSipmm+'&ICVETRAMITE='+frm.iNumCita.value+'&cArea='+cAreaCISFIEL+'"></iframe>';
    	frm.CCADORIGEN.value = cCadenaOriginal;
    	FRMPanel.fSetTraStatus(",");
     }
   }   
}

function fGuardarA(){
   fGuardar();
}

function fModificar(){
   fDisabled(false);
   FRMPanel.fSetTraStatus("UpdateBegin");
}

function fCancelar(){
   if(frm.LFINALIZADO.value == 0){
		FRMPanel.fSetTraStatus(",");
   }else{
		FRMPanel.fSetTraStatus("Can,");
		parent.fPagFolder(1);
   }
   fDisabled(true);
}

/**
 * Valida los Scripts registrados en la tabla de los campos por trámite.
 */
function fValScripts(){
	cMsg = "";
	var patronRFC = /^([A-Z]{3,4}[0-9]{6}[A-Z]{3})$/;
	var patronRFCRP = /^([A-Z]{3,4}[0-9]{6}[A-Z]{0,3})$/;
	var patronNombre = /^([A-Z]*[a-z]*)$/;
	
	if(fScripts != ""){
		cScriptList = fScripts.split(";");
	}else{
		cScriptList = new Array();
	}
	
	for(s=0; s<cScriptList.length;s++){
		cScript = cScriptList[s];
		if(cScript.indexOf("@") >= 0){
			cScript = cScript.replace(/@/g,'"').replace(/@/g,'"');			
		}
		if(cScript.indexOf("#") >= 0){
			cScript = cScript.replace(/#/g,'+').replace(/#/g,'+');
		}
		
		if(cScript.length > 0){
			eval(cScript);
		}
	}
	return cMsg;
}

// FUNCIÓN donde se generan las validaciones de los datos ingresados
function fValidaTodo(){ 
   cMsg = fValElements();
   cMsg += fValScripts();
   if(cMsg != ""){ 
      fAlert(cMsg); 
      return false; 
   }else{
      frm.cCamSol.value = '';
      frm.iGRLDepto.value = '0';      
      for(iQ=0; iQ<form.elements.length;iQ++){
         obj = form.elements[iQ];
         if (obj.type == 'text' || obj.type == 'textarea' || obj.type == 'select-one'){
             cValor=obj.value;
             if(cValor == '') cValor=' ';
             
             if(obj.type == 'select-one' && cValor != " "){
             	 cValor2 = " ";
					 if(obj[obj.selectedIndex].text != ""){
					 	cValor2 = obj[obj.selectedIndex].text;
					 }
					 frm.cCamSol.value += "^"+obj.name+"="+cValor+"="+cValor2+"=";
             }else{
                frm.cCamSol.value += "^"+obj.name+"="+cValor+"= =";
             }
             frm.cCamSol.value += iQ + "=";
             if(obj.name.toUpperCase() == "ICVEDEPARTAMENTO"){
                frm.iGRLDepto.value = obj.value;
             }             
         }
      }
      if(frm.cCamSol.value.length > 0)
         frm.cCamSol.value = frm.cCamSol.value.substring(1);
      frm.cCamSolDIN.value = frm.cCamSol.value;
   } 
   return true; 
} 
function fImprimir(){
   self.focus();
   window.print();   
}

function fGetGuardado()
{
  return guardado;
}

 function fLiga1(cpag){
     //var indice = frm.TIPODOCDIG.selectedIndex;
    // frm.CTIPODOC.value = frm.TIPODOCDIG.options[indice].text;
   
  	fAbreSubWindow(true,cpag,'no','yes','yes','yes','500','600',10,10); 
 }
 
 function fGetICVETRAMITE(){
 	return frm.ICVETRAMITE.value;
}

function fFirmaElectr() {
	frm.C2012.value = "firmaXML.html";
	fAbreSubWindow(true,"pgFirmaXML",'no','yes','yes','yes','500','600',10,10);
}

function fFirmaElectr2() {
	frm.C2012.value = "firmaXML.html";
	fAbrePaginaHTML("firmaXML.html");
	//fAbreSubWindow(true,pgFirma2012.js,'no','yes','yes','yes','500','600',10,10);
}

function fCarga(lSuccess,cArchivos){
	if(lSuccess == false)
            fAlert("\n- La captura u obtención de los documentos presentó un problema, consulte a su administrador de sistemas.");
	else{
   	    iFrm = document.getElementById("IADJUNTAR");
        iFrm.style.width = '0%';
        iFrm.style.height = '0%'; 
        //fGenADJUNTA();
	    fAlert("\n- La captura u obtención de los documentos se realizó correctamente.");   
	}    
}

function getCTIPOPERMISIONARIO(){
	FRMSol = fBuscaFrame("INT1");
	return FRMSol.getCTIPOPERMISIONARIO();	
}

function getCDSCTIPOTRAMITE(){
	FRMSol = fBuscaFrame("INT1");
	return FRMSol.getCDSCTIPOTRAMITE();
}


function fLlamae5(){
	fAbreWindowHTML(false,"http://aplicaciones.sct.gob.mx/e5Cinco/","yes","yes","yes","yes","yes",800,600,50,50);	
}
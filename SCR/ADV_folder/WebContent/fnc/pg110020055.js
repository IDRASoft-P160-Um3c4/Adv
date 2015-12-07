// MetaCD=1.0
// Title: pg116010014.js
// Description: JS Consulta General de Embarcación
// Company: S.C.T.
// Author: Arturo López Peña
var cTitulo = "";
var FRMListado = "";
var frm;
var cUser = fGetIdUsrSesion();
var lDestOrigen = true;
var lActualizacion = false;
var lPrimeraAct = false;

// SEGMENTO antes de cargar la página (Definición Mandatoria)
function fBefLoad(){
  cPaginaWebJS = "pg110020055.js";
  if(top.fGetTituloPagina){;
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase();
  }
}
// SEGMENTO Definición de la página (Definición Mandatoria)
function fDefPag(){
  fInicioPagina(cColorGenJS);
  InicioTabla("",1,"100%","","","","1");
    ITRTD("",0,"","","top");
      InicioTabla("",0,"100%","100%","center");
        ITRTD("",0,"","","top");
        InicioTabla("",0,"100%","100%","center");
            InicioTabla("",0,"100%","100%","center");
              ITRTD("ETablaST",10,"","","center");
                 Liga("Persona Destino","fGetDestino();");
                FTD();
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Nombre:","cNombreRazonSocial_D","",30,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL");
              //FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Ap. Paterno:","cApPaterno_D","",30,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Ap. Materno:","cApMaterno_D","",30,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL");
              //FITR();
                TDEtiCampo(true,"EEtiqueta",0,"RFC:","cRFC_D","",30,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL");
              FITR();
            FinTabla();
          ITRTD("",0,"","","top");
            InicioTabla("",0,"100%","100%","center");
              ITRTD("ETablaST",10,"","","center");
              	//TextoSimple("Representantes Legales Destino");
              	Liga("Integrar Representantes","fIntegraRep();")
              FTD();
              ITRTD("",0,"","105","center","top");
               	IFrame("IListadoRepD","95%","100","Listado.js","yes",true);
              FTDTR();
            FinTabla();
          ITRTD("",0,"","","top");
            InicioTabla("",0,"100%","100%","center");
              ITRTD("ETablaST",10,"","","center");
              	Liga("Integrar Solicitud","fIntegraSol();")
              FTD();
              ITRTD("",0,"","105","center","top");
               	IFrame("IListadoSolPD","95%","100","Listado.js","yes",true);
              FTDTR();
            FinTabla();
          ITRTD("",0,"","","top");
            InicioTabla("",0,"100%","100%","center");
              ITRTD("ETablaST",10,"","","center");
              	Liga("Integrar Solicitud","fIntegraRPM();")
              FTD();
              ITRTD("",0,"","105","center","top");
               	IFrame("IListadoRPMD","95%","100","Listado.js","yes",true);
              FTDTR();
            FinTabla();
            FITR();
            FITR();
            FITR();
        FinTabla();
        FITD();    
        InicioTabla("",0,"100%","100%","center");
        ITRTD("",0,"","","top");
            InicioTabla("",0,"100%","100%","center");
              ITRTD("ETablaST",10,"","","center");
                Liga("Persona Origen","fGetOrigen();");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Nombre:","cNombreRazonSocial_O","",30,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL");
              //FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Ap. Paterno:","cApPaterno_O","",30,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL");
              FITR();
                TDEtiCampo(true,"EEtiqueta",0,"Ap. Materno:","cApMaterno_O","",30,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL");
              //FITR();
                TDEtiCampo(true,"EEtiqueta",0,"RFC:","cRFC_O","",30,0,"ClaveEspecialidad","fMayus(this);","","","","EEtiquetaL");
             FinTabla();
             ITRTD("",0,"","","top");
                  InicioTabla("",0,"100%","100%","center");
                    ITRTD("ETablaST",10,"","","center");
                      TextoSimple("Representante Legal Origen");
                    FTD();
                    ITRTD("",0,"","105","center","top");
                      IFrame("IListadoRepO","95%","100","Listado.js","yes",true);
                    FTDTR();
                FinTabla();
                ITRTD("",0,"","","top");
                InicioTabla("",0,"100%","100%","center");
                  ITRTD("ETablaST",10,"","","center");
                    TextoSimple("Solicitudes Origen");
                  FTD();
                  ITRTD("",0,"","105","center","top");
                    IFrame("IListadoSolPO","95%","100","Listado.js","yes",true);
                  FTDTR();
              FinTabla();
              ITRTD("",0,"","","top");
              InicioTabla("",0,"100%","100%","center");
                ITRTD("ETablaST",10,"","","center");
                  TextoSimple("RPMN Origen");
                FTD();
                ITRTD("",0,"","105","center","top");
                  IFrame("IListadoRPMO","95%","100","Listado.js","yes",true);
                FTDTR();
            FinTabla();
            FinTabla();
        FITD();
      
    FinTabla();
    Hidden("iCvePersonaO");
    Hidden("iCvePersonaD");
    Hidden("iCvePersona");
    Hidden("iCveRepLegal");
    Hidden("iEjercicioOri");
    Hidden("iNumSolicitudOri");
    Hidden("");
  fFinPagina();
}
// SEGMENTO Después de Cargar la página (Definición Mandatoria)
function fOnLoad(){
    frm = document.forms[0];
    
    FRMListadoRepD = fBuscaFrame("IListadoRepD"); 
    FRMListadoRepD.fSetControl(self); 
    FRMListadoRepD.fSetTitulo("ID, Nombre, RFC,");  
    FRMListadoRepD.fSetAlinea("left,left,center,center,center,");
    FRMListadoRepD.fSetCampos("0,1,2,");
    FRMListadoRepD.fSetSelReg(8);
    FRMListadoRepO = fBuscaFrame("IListadoRepO"); 
    FRMListadoRepO.fSetControl(self); 
    FRMListadoRepO.fSetTitulo("ID, Nombre, RFC,");  
    FRMListadoRepO.fSetAlinea("left,left,center,center,center,");
    FRMListadoRepO.fSetCampos("0,1,2,");
    FRMListadoRepO.fSetSelReg(1);
    
    FRMListadoSolPD = fBuscaFrame("IListadoSolPD"); 
    FRMListadoSolPD.fSetControl(self); 
    FRMListadoSolPD.fSetTitulo("Ejercicio,Solicitud,Trámite,Modalidad,Fecha Alta,");
    FRMListadoSolPD.fSetCampos("0,1,2,3,4,");
    FRMListadoSolPD.fSetSelReg(8);
    FRMListadoSolPO = fBuscaFrame("IListadoSolPO"); 
    FRMListadoSolPO.fSetControl(self); 
    FRMListadoSolPO.fSetTitulo("Ejercicio,Solicitud,Trámite,Modalidad,Fecha Alta,");
    FRMListadoSolPO.fSetCampos("0,1,2,3,4,");
    FRMListadoSolPO.fSetSelReg(2);
    
    FRMListadoRPMD = fBuscaFrame("IListadoRPMD"); 
    FRMListadoRPMD.fSetControl(self); 
    FRMListadoRPMD.fSetTitulo("RPMN,Partida,Registro,Histórico,Folio Ante,");
    FRMListadoRPMD.fSetCampos("10,3,4,5,6,");
    FRMListadoRPMD.fSetSelReg(8);
    FRMListadoRPMO = fBuscaFrame("IListadoRPMO"); 
    FRMListadoRPMO.fSetControl(self); 
    FRMListadoRPMO.fSetTitulo("RPMN,Partida,Registro,Histórico,Folio Ante,");
    FRMListadoRPMO.fSetCampos("10,3,4,5,6,");
    FRMListadoRPMO.fSetSelReg(3);
    
    frm.hdNumReg.value = 10000;
    fDisabled(true);

}

 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){
   if(cError=="Guardar")
     fAlert("Existió un error en el Guardado!");
   if(cError=="Borrar")
     fAlert("Existió un error en el Borrado!");
   if(cError=="Cascada"){
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!");
     return;
   }
   if(cError=="Datos")
     fAlert("Existe un conflicto de Datos.");


   if(cId == "cIdListRep" && cError==""){
       if(lDestOrigen == false){
           FRMListadoRepD.fSetListado(aRes); 
           FRMListadoRepD.fShow(); 
           FRMListadoRepD.fSetLlave(cLlave);
           if(lActualizacion==true && lPrimeraAct==true){
               lDestOrigen = true;
               lPrimeraAct = false;
    	       frm.iCvePersona.value = frm.iCvePersonaO.value;
               frm.hdBoton.value = "BuscaRepresentante";
               fEngSubmite("pgBuscaPersonas.jsp","cIdListRep");
               return;
           }
       }
       if(lDestOrigen == true){
           FRMListadoRepO.fSetListado(aRes); 
           FRMListadoRepO.fShow(); 
           FRMListadoRepO.fSetLlave(cLlave);
       }
       if(lActualizacion==false) {
	   frm.hdBoton.value = "BuscarSolicitud";
	   fEngSubmite("pgBuscaPersonas.jsp","cIdListSol");
       }
   }

   if(cId == "cIdListSol" && cError==""){
       if(lDestOrigen == false){
           FRMListadoSolPD.fSetListado(aRes); 
           FRMListadoSolPD.fShow(); 
           FRMListadoSolPD.fSetLlave(cLlave);
           if(lActualizacion==true && lPrimeraAct==true){
               lDestOrigen = true;
               lPrimeraAct = false;
    	       frm.iCvePersona.value = frm.iCvePersonaO.value;
               frm.hdBoton.value = "BuscarSolicitud";
               fEngSubmite("pgBuscaPersonas.jsp","cIdListSol");
               return;
           }
       }
       if(lDestOrigen == true){
           FRMListadoSolPO.fSetListado(aRes); 
           FRMListadoSolPO.fShow(); 
           FRMListadoSolPO.fSetLlave(cLlave);
       }
       if(lActualizacion==false) {
	   frm.hdBoton.value = "BuscarRPMN";
	   fEngSubmite("pgBuscaPersonas.jsp","cIdListRPM");
       }
   }   
   if(cId == "cIdListRPM" && cError==""){
       for(i=0;i<aRes.length;i++){
	   var iFolio=parseInt(aRes[i][0],10);
	   var cFolio="";
	   cFolio=iFolio>999?""+iFolio:iFolio>99?"0"+iFolio:iFolio>9?"00"+iFolio:"000"+iFolio;
	   aRes[i][10]=cFolio+"-"+aRes[i][1]+"-"+aRes[i][2].substr(2,4);
       }
       if(lDestOrigen == false){
           FRMListadoRPMD.fSetListado(aRes); 
           FRMListadoRPMD.fShow(); 
           FRMListadoRPMD.fSetLlave(cLlave);
       }
       if(lDestOrigen == true){
           FRMListadoRPMO.fSetListado(aRes); 
           FRMListadoRPMO.fShow(); 
           FRMListadoRPMO.fSetLlave(cLlave);
       }
       if(lActualizacion==false) {
	   //frm.hdBoton.value = "BuscarRPMN";
	   //fEngSubmite("pgBuscaPersonas.jsp","cIdListRPM")
       }
   }
   if(cId=="cIdIntegraRep" && cError == ""){
       lDestOrigen == false;
       frm.iCvePersona.value = frm.iCvePersonaD.value;
       frm.hdBoton.value = "BuscaRepresentante";
       fEngSubmite("pgBuscaPersonas.jsp","cIdListRep");       
   }
   if(cId=="cIdIntegraSol" && cError == ""){
       lDestOrigen == false;
       frm.iCvePersona.value = frm.iCvePersonaD.value;
       frm.hdBoton.value = "BuscarSolicitud";
       fEngSubmite("pgBuscaPersonas.jsp","cIdListSol");       
   }

 }

//Definir en paginas que requieran datos de persona o persona y representante legal
 function fGetParametrosConsulta(frmDestino){
   var lShowPersona     = true;
   var lShowRepLegal    = false;
   var lEditPersona     = true;
   var lEditDomPersona  = true;
   var lEditRepLegal    = false;
   var lEditDomRepLegal = false;
   if (frmDestino){
     if (frmDestino.setShowValues)
       frmDestino.setShowValues(lShowPersona, lShowRepLegal, "");
     if (frmDestino.setEditValues)
       frmDestino.setEditValues(lEditPersona, lEditDomPersona, lEditRepLegal, lEditDomRepLegal);
   }
 }

function fGetDestino(){
    lDestOrigen = false;
    lActualizacion = false;
    fAbreSolicitante();
}

function fGetOrigen(){
    lDestOrigen = true;
    lActualizacion = false;
    fAbreSolicitante();
}




function fSelReg(aDato){
    frm.iCveRepLegal.value = aDato[0];
}
function fSelReg2(aDato){
    frm.iEjercicioOri.value = aDato[0];
    frm.iNumSolicitudOri.value = aDato[1];
}
function fSelReg3(aDato){
    
}


function fValoresPersona(iCvePersona,cRFC,cRPA,iTipoPersona,cNomRazonSocial,cApPaterno,cApMaterno){
  if(parseInt(iCvePersona,10)>0){
    if(lDestOrigen == false){
        frm.cNombreRazonSocial_D.value = cNomRazonSocial;
        frm.cApPaterno_D.value = cApPaterno;
        frm.cApMaterno_D.value = cApMaterno;
        frm.cRFC_D.value = cRFC;
        frm.iCvePersonaD.value = iCvePersona;
        frm.iCvePersona.value = iCvePersona;
        frm.hdBoton.value = "BuscaRepresentante";
        fEngSubmite("pgBuscaPersonas.jsp","cIdListRep");
    }
    if(lDestOrigen == true){
        frm.cNombreRazonSocial_O.value = cNomRazonSocial;
        frm.cApPaterno_O.value = cApPaterno;
        frm.cApMaterno_O.value = cApMaterno;
        frm.cRFC_O.value = cRFC;
        frm.iCvePersonaO.value = iCvePersona;
        frm.iCvePersona.value = iCvePersona;
        frm.hdBoton.value = "BuscaRepresentante";
        fEngSubmite("pgBuscaPersonas.jsp","cIdListRep");
    }
  }
}







function fIntegraRep(){
    if(frm.iCvePersonaD.value > 0 && frm.iCvePersonaO.value >0){
        lActualizacion = true;
        lDestOrigen = false;
        lPrimeraAct = true;
        frm.hdBoton.value = "IntegraRepresentante";
        fEngSubmite("pgGRLRepLegal.jsp","cIdIntegraRep");
    }
}function fIntegraRPM(){
    fAlert("Aun no esta definida la integración con el RPMN");
    /*
    if(frm.iCvePersonaD.value > 0 && frm.iCvePersonaO.value >0){
        lActualizacion = true;
        lDestOrigen = false;
        lPrimeraAct = true;
        frm.hdBoton.value = "IntegraEmpresa";
        fEngSubmite("pgMYREmpresa.jsp","cIdIntegraRep"); 
    }
    */
}
function fIntegraSol(){
    if(frm.iCvePersonaD.value > 0 && frm.iCvePersonaO.value >0){
        lActualizacion = true;
        lDestOrigen = false;
        lPrimeraAct = true;
        frm.hdBoton.value = "IntegraSolicitante";
        fEngSubmite("pgTRARegSolicitud.jsp","cIdIntegraSol");
    }
}
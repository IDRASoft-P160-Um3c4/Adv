// MetaCD=1.0 
// Title: pg113020031.js
// Description: JS "Cat�logo" de la entidad CYSTituloObligacion
// Company: Tecnolog�a InRed S.A. de C.V. 
// Author: ICE Arturo L�pez Pe�a
var cTitulo = ""; 
var FRMListado = ""; 
var frm; 
// SEGMENTO antes de cargar la p�gina (Definici�n Mandatoria)
function fBefLoad(){ 
  cPaginaWebJS = "pg111010034.js";
  if(top.fGetTituloPagina){; 
    cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
  } 
} 
// SEGMENTO Definici�n de la p�gina (Definici�n Mandatoria)
function fDefPag(){ 
  fInicioPagina(cColorGenJS); 
  InicioTabla("",0,"100%","","","","1"); 
    ITRTD("ESTitulo",0,"100%","","center"); 
      TextoSimple(cTitulo); 
    FTDTR(); 
    ITRTD("",0,"","","top"); 
    InicioTabla("",0,"100%","","center"); 
           ITR(); 
              TDEtiSelect(true,"EEtiqueta",0,"Tramite:","iCveTramite","fNavega();"); 
          FTR(); 
      ITRTD("",2,"","175","center","top"); 
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","1","center"); 
       FTDTR(); 
         ITRTD("",0,"","","center",""); 
           IFrame("IListado34A","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");        
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fAgregar();");
             ITRTD("",0,"","100%","center","");       
               BtnImg("Buscar","btnquitar","fBorrar();");
             FTDTR();                    
           FinTabla();
         ITD("",0,"","","center","");           
           IFrame("IListado34","95%","220","Listado.js","yes",true);           
       FTDTR();  
         FinTabla(); 
        InicioTabla("ETablaInfo",0,"75%","","","",1); 
          ITRTD("ETablaST",5,"","","center"); 
            TextoSimple("Resuelve"); 
          FTDTR(); 
          ITR(); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficinaResuelve","fOficinaOnChange();"); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDeptoResuelve",""); 
           FTR(); 
        FinTabla(); 
      FTDTR(); 
      ITRTD("",2,"","1","center"); 
        Hidden("iFlag","");
        Hidden("hdBotonAux","");
        Hidden("var1");
        Hidden("hdLlave");
        Hidden("hdSelect");
        Hidden("iCveOficina");
        Hidden("iCveOficina1");
        Hidden("iCveTitulo");
      FTDTR(); 
      FinTabla();
       
    FTDTR(); 
      ITRTD("",0,"","40","center","bottom"); 
        IFrame("IPanel","95%","34","Paneles.js"); 
      FTDTR(); 
    FinTabla(); 
  fFinPagina(); 
} 
function fOnLoad(){ 
  frm = document.forms[0]; 
  FRMPanel = fBuscaFrame("IPanel"); 
  FRMPanel.fSetControl(self,cPaginaWebJS); 
  FRMPanel.fShow("Tra,"); 
  FRMListado = fBuscaFrame("IListado34"); 
  FRMListado.fSetControl(self); 
  FRMListado.fSetTitulo("Oficinas Asignadas,"); 
  FRMListado.fSetCampos("4,"); 
  FRMListado.fSetSelReg(1); 
   FRMListadoA = fBuscaFrame("IListado34A"); 
   FRMListadoA.fSetControl(self); 
   FRMListadoA.fSetTitulo("Oficinas Disponibles,");
   FRMListadoA.fSetCampos("1,");
   FRMListadoA.fSetSelReg(2);  
  fCancelar();
  frm.hdBoton.value="Primero";  
  frm.hdLlave.value ="iCveOficina,iCveTramite";
  frm.hdSelect.value = "Select iCveTramite,cDscBreve from TraCatTramite";
  fEngSubmite("pgConsulta.jsp","Tramite"); 
} 
function fNavega(){ 
  frm.hdFiltro.value = "TT.iCveTramite = " + frm.iCveTramite.value; 
  frm.hdOrden.value = "oficina1"; 
  frm.hdNumReg.value = 100000;
  return fEngSubmite("pgTRATramiteXOfic.jsp","Listado"); 
} 
function fNavega2(){ 
  frm.hdLlave.value = "iCveOficina";
  frm.hdSelect.value = "Select iCveOficina, cDscBreve From GRLOficina where iCveOficina not in (Select iCveOficina from TRATramiteXOfic Where iCveTramite = "+frm.iCveTramite.value+" ) order by cDscBreve"
  frm.hdNumReg.value = 100000;
  return fEngSubmite("pgConsulta.jsp","Listado2"); 
}
function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){ 
  if(cError=="Guardar") 
    fAlert("Existi� un error en el Guardado!"); 
  if(cError=="Borrar") 
    fAlert("Existi� un error en el Borrado!"); 
  if(cError=="Cascada"){ 
    fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!"); 
    return; 
  } 
  if(cId == "Listado" && cError==""){ 
    frm.hdRowPag.value = iRowPag; 
    FRMListado.fSetListado(aRes); 
    FRMListado.fShow(); 
    FRMListado.fSetLlave(cLlave); 
    fNavega2();
  } 
  if(cId == "Listado2" && cError==""){
    frm.hdRowPag.value = iRowPag; 
    FRMListadoA.fSetListado(aRes); 
    FRMListadoA.fShow(); 
    FRMListadoA.fSetLlave(cLlave); 
  }
  if(cId == "Tramite" && cError==""){ 
    fFillSelect(frm.iCveTramite,aRes,true,frm.iCveTramite.value,0,1);
  }
  if(cId == "CIDOficina" && cError==""){ 
    fFillSelect(frm.iCveOficinaResuelve,aRes,true,frm.iCveOficinaResuelve.value,0,1);
  }
  if(cId == "CIDDepartamento" && cError==""){ 
    fFillSelect(frm.iCveDeptoResuelve,aRes,true,frm.iCveDeptoResuelve.value,0,1);
  }
} 
function fAgregar(){
  frm.hdBoton.value = "Agregar";
  fNavega();
}
function fGuardar(){ 
alert ("Oficina="+frm.iCveoficina.value+"  Tramite="+frm.iCveTramite.value+"/n  OficinaRes"+frm.iCveOficinaResuelve.value+"  DeptoRes="+frm.iCveOficinaResuelve.value)
   if(fValidaTodo()==true){
      if(fNavega()==true){ 
        FRMPanel.fSetTraStatus("Mod,"); 
        fDisabled(true,"iCveTramite,"); 
        FRMListado.fSetDisabled(false); 
      }
   }
} 
function fGuardarA(){ 
alert ("Oficina="+frm.iCveOficina.value+"  Tramite="+frm.iCveTramite.value+"/n  OficinaRes"+frm.iCveOficinaResuelve.value+"  DeptoRes="+frm.iCveDeptoResuelve.value)
   if(fValidaTodo()==true){
      if(fNavega()==true){ 
        FRMPanel.fSetTraStatus("Mod,"); 
        fDisabled(true,"iCveTramite,"); 
        FRMListado.fSetDisabled(false); 
      }
   }
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Modificar
function fModificar(){ 
   frm.hdLlave.value =""
   frm.hdSelect.value = "Select iCveOficina,cDscOficina from GRLOficina Order By cDscOficina"
   fEngSubmite("pgConsulta.jsp","CIDOficina");
   FRMPanel.fSetTraStatus("UpdateBegin"); 
   fDisabled(false,""); 
   //FRMListado.fSetDisabled(true); 
} 
// LLAMADO desde el Panel cada vez que se presiona al bot�n Cancelar
function fCancelar(){ 
     FRMPanel.fSetTraStatus("Mod,"); 
   fDisabled(true,"iCveTramite,"); 
   FRMListado.fSetDisabled(false); 
} 
function fBorrar(){ 
   if(confirm(cAlertMsgGen + "\n\n �Desea usted eliminar el registro?")){ 
      alert(frm.iCveOficina.value+ "  "+ frm.iCveTramite.value);
      frm.hdBoton.value = "Borrar";
      fNavega(); 
   } 
} 
function fSelReg(aDato){
   frm.iCveOficina.value = aDato[0];
   fAsignaSelect(frm.iCveOficinaResuelve,aDato[2],aDato[5]); 
   fAsignaSelect(frm.iCveDeptoResuelve,aDato[3],aDato[6]); 
}
function fSelReg2(aDato){
  frm.iCveOficina1.value = aDato[0];
}

function fOficinaOnChange(){
  frm.hdLlave.value = "iCveDepartamento";
  frm.hdSelect.value = "Select Distinct iCveDepartamento,cDscDepartamento from GRLDepartamento Where iCveDepartamento in (Select Distinct iCveDepartamento From GRLDeptoXOfic Where iCveOficina = "+
                       frm.iCveOficinaResuelve.value + ")Order By cDscDepartamento";
  fEngSubmite("pgConsulta.jsp","CIDDepartamento");
}
function fValidaTodo(){ 
   cMsg = fValElements(); 

   if(cMsg != ""){ 
      fAlert(cMsg); 
      return false; 
   } 
   return true; 
} 
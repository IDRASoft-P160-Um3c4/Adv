// MetaCD=1.0 
 // Title: pg110010022.js
 // Description: JS "Catálogo" de la entidad GRLDeptoXOfic
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: Ramón Montes Rodríguez
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg110010022.js";
   if(top.fGetTituloPagina){; 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   } 
 } 
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){ 
  JSSource("pais.js");
  JSSource("estados.js");
   fInicioPagina(cColorGenJS); 
   InicioTabla("",0,"100%","","","","1"); 
     ITRTD("",0,"","","top");
     InicioTabla("",0,"100%","","","","1"); 
     InicioTabla("ETablaInfo",0,"95%","","","",1); 
          ITRTD("ETablaST",5,"","","center"); 
            TextoSimple(cTitulo); 
          FTDTR(); 
          ITR(); 
           FITR(); 
              TDEtiSelect(true,"EEtiqueta",0,"Oficinas:","iCveOficina","fOficinaOnchange();");
           FITR(); 
           Hidden("hdSelect"); 
           Hidden("hdLlave");
           Hidden("iCveDepartamento1");
           Hidden("iCveDepartamento2");
           Hidden("flag1",0);
           Hidden("flag2",0);
           FITR();
     FinTabla();
   FinTabla();
               
     InicioTabla("",0,"100%","","center"); 
       ITRTD("",0,"","1","center"); 
       FTDTR(); 
         ITRTD("",0,"","","center",""); 
           IFrame("IListado22A","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");        
             ITRTD("",0,"","100%","center","");
               //Liga("     Agregar  >>> ","fAgregar();","Agrega un Registro");
               BtnImg("Buscar","btnagregar","fAgregar();");
             ITRTD("",0,"","100%","center","");       
               //Liga(" <<< Eliminar     ","fEliminar();","Elimina un Registro");
               BtnImg("Buscar","btnquitar","fEliminar();");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");  
               //Liga("     Subir ","fSubir();","Agrega un Registro");
               //BtnImg("Buscar","ir2.gif","");
             ITRTD("",0,"","100%","center","");                        
               //Liga(" Bajar     ","fBajar();","Elimina un Registro");
               //BtnImg("Buscar","ir1","");            
             FTDTR();                    
           FinTabla();
               
         ITD("",0,"","","center","");           
           IFrame("IListado22","95%","220","Listado.js","yes",true);           
       FTDTR();  
         FinTabla(); 
       FTDTR();
       FinTabla(); 
     FTDTR(); 
     FinTabla();
   
     InicioTabla("ETablaInfo",0,"75%","","center","",1); 
           ITRTD("ETablaST",5,"","","center"); 
             TextoSimple(cTitulo); 
           FTDTR(); 
           ITR(); 
              TDEtiCampo(false,"EEtiqueta",0,"Titular","cTitular","",75,50,"Titular:Descrip_100","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(false,"EEtiqueta",0,"Puesto:","cPuestoTitular","",75,50,"PuestoTitular:Descrip_100","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(false,"EEtiqueta",0,"Teléfono:","cTelefono","",75,50,"Telefono:Descrip_050","fMayus(this);"); 
           FITR(); 
              TDEtiCampo(false,"EEtiqueta",0,"Correo Electrónico:","cCorreoE","",75,50,"CorreoE:Descrip_050","");
           FTR(); 
FinTabla(); 
  //botones de la parte inferior  
    FTDTR(); 
      ITRTD("",0,"","40","center","bottom"); 
        IFrame("IPanel22","95%","34","Paneles.js"); 
      FTDTR(); 
  fFinPagina();
 }

 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
 //botenes parte inferior fBuscaFrame hasta fShow 
   FRMPanel = fBuscaFrame("IPanel22"); 
   FRMPanel.fSetControl(self,cPaginaWebJS); 
   FRMPanel.fShow("Tra,"); 
   FRMListadoA = fBuscaFrame("IListado22A"); 
   FRMListadoA.fSetControl(self); 
   FRMListadoA.fSetTitulo("Consecutivo,Departamentos Disponibles,");
   FRMListadoA.fSetAlinea("center,left,");
   FRMListadoA.fSetCampos("0,1,");   
   FRMListado = fBuscaFrame("IListado22");
   FRMListado.fSetTitulo("Consecutivo,Departamentos Asignados,");
   FRMListado.fSetAlinea("center,left,");
   FRMListado.fSetCampos("1,6,"); 
   FRMListado.fSetControl(self);
   FRMListado.fSetSelReg(1); 
   FRMListadoA.fSetSelReg(2);
   fDisabled(true,"iCveOficina,"); 
   frm.hdBoton.value="Primero";
   fNavega2();                                       
 } 
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
   
   //alert(frm.iCveOficina.value)
  if (frm.iCveOficina.value!=-1){  
   frm.hdFiltro.value =  "iCveOficina = " + frm.iCveOficina.value; 
   frm.hdOrden.value =  "cDscBreve";
   frm.hdNumReg.value =  100000; 
   fEngSubmite("pgGRLDeptoXOficLRMR.jsp","Listado");
   FRMPanel.fSetTraStatus("UpdateBegin"); 
  }
 
 } 
 // RECEPCIÓN de Datos de provenientes del Servidor
 function fResultado(aRes,cId,cError,cNavStatus,iRowPag,cLlave){ 
   if(cError=="Guardar") 
     fAlert("Existió un error en el Guardado!"); 
   if(cError=="Borrar") 
     fAlert("Existió un error en el Borrado!"); 
   if(cError=="Cascada"){ 
     fAlert("El registro es utilizado por otra entidad, no es posible eliminarlo!"); 
     return; 
   } 
 
   if(cId == "Listado" && cError==""){ 
     frm.hdRowPag.value = iRowPag; 
     FRMListado.fSetListado(aRes); 
     FRMListado.fShow();
     if(FRMListado.fGetLength() > 0){
      frm.flag1.value = 1;      
     }
     else{
      frm.flag1.value = 0; 
     }
     FRMListado.fSetLlave(); 
     fCancelar(); 
     fNavega1(); 
   } 
    if(cId == "Listado2" && cError == ""){ 
     frm.hdRowPag.value = iRowPag; 
     FRMListadoA.fSetListado(aRes); 
     FRMListadoA.fShow();
     if(FRMListadoA.fGetLength() > 0){
      frm.flag2.value = 1;
     }
     else{
      frm.flag2.value = 0;
     }  
   } 
    if(cId == "CIDOficina" && cError==""){
      fFillSelect(frm.iCveOficina,aRes,true,frm.iCveOficina.value,0,2);
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){ 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar  insert
 function fGuardar(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
          FRMPanel.fSetTraStatus("UpdateBegin");  
          fDisabled(true); 
          FRMListado.fSetDisabled(false); 
          FRMListadoA.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar" update
 function fGuardarA(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
         FRMListadoA.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){ 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fDisabled(false); 
    FRMListado.fSetDisabled(true); 
    FRMListadoA.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){ 
    fDisabled(true,"iCveOficina,"); 
    FRMListado.fSetDisabled(false);
    FRMListadoA.fSetDisabled(false);
    if(frm.flag1.value==1) FRMPanel.fSetTraStatus("Mod,");
    else FRMPanel.fSetTraStatus("");  
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){ 
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){ 
       fNavega(); 
    } 
 } 
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){ 
    frm.iCveDepartamento2.value = aDato[1]; //hace el delete de la lista derecha
    frm.cTitular.value = aDato[2];
    frm.cPuestoTitular.value = aDato[5];
    frm.cTelefono.value = aDato[3];
    frm.cCorreoE.value = aDato[4];
    if(frm.iCveDepartamento2.value!="")FRMPanel.fSetTraStatus("Mod,");
    else  FRMPanel.fSetTraStatus("Mod,");
 } 
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){ 
    cMsg = fValElements(); 
 
    if(cMsg != ""){ 
       fAlert(cMsg); 
       return false; 
    } 
    return true; 
 } 
 function fImprimir(){ 
    self.focus();
    window.print(); 
 } 

 function fAgregar(){
 if(frm.flag2.value == 1)
   {
    frm.hdBoton.value = "Guardar";
    fNavega();
   }
  else
    alert("No hay 'departamentos disponibles' en el listado izquierdo"); 
 }
 function fEliminar(){
   if(frm.flag1.value == 1){
    frm.hdBoton.value = "Borrar";
    fNavega();
    }
   else
    alert("No hay 'departamentos asignados' en el listado derecho");
 } 
 function fNavega1(){
       frm.hdLlave.value="iCveDepartamento";//tabla izquierda
       frm.hdSelect.value="select icvedepartamento, cdscbreve  "+
                          "from grldepartamento "+
                          "where icvedepartamento not in "+
                          "(select icvedepartamento from grldeptoxofic where "+
                          "iCveOficina = "+frm.iCveOficina.value+")order by cDscBreve"; 
   fEngSubmite("pgConsulta.jsp","Listado2"); 
 }  
 function fSelReg2(aDato){ 
   frm.iCveDepartamento1.value = aDato[0]; 
 }                    
 function fNavega2(){
   frm.hdFiltro.value =  ""; 
   frm.hdOrden.value =  "cDscBreve"; 
   frm.hdNumReg.value = 10000; 
   fEngSubmite("pgGRLOficina.jsp","CIDOficina");
 }
 function fOficinaOnchange(){
   FRMListadoA.fSetListado(new Array); 
   FRMListadoA.fShow();      
   FRMListado.fSetListado(new Array); 
   FRMListado.fShow();
   if(frm.iCveOficina.value == -1)// Deshabilita el boton de modificar cuando le das selecciones 
     FRMPanel.fSetTraStatus("");
   frm.flag2.value = 0;
   frm.flag1.value = 0;
   fNavega();
 }
    
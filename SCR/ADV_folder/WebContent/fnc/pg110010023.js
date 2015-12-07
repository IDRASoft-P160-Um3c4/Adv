// MetaCD=1.0 
 // Title: pg110010023.js
 // Description: JS "Catálogo" de la entidad GRLDeptoDepen
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: Ramón Montes Rodríguez
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg110010023.js";
   if(top.fGetTituloPagina){; 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   } 
 } 
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){ 
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
              TDEtiSelect(true,"EEtiqueta",0,"Oficinas:","iCveOficina","fOficina();");
           FITR(); 
             TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDepartamento","fNavega1();fNavega();");
           FITR(); 
           Hidden("hdSelect"); 
           Hidden("hdLlave");
           Hidden("iCveDeptoHijo1");
           Hidden("iCveDeptoHijo2");
           Hidden("iCveDepartamento1");
           Hidden("iCveDepartamento2");
           Hidden("iflag");
           Hidden("iflag2");
           FITR();
     FinTabla();
   FinTabla();
               
     InicioTabla("",0,"100%","","center"); 
       ITRTD("",0,"","1","center"); 
       FTDTR(); 
         ITRTD("",0,"","","center",""); 
           IFrame("IListado23izq","95%","220","Listado.js","yes",true);
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
           IFrame("IListado23der","95%","220","Listado.js","yes",true);           
       FTDTR();  
         FinTabla(); 
       FTDTR();
       FinTabla(); 
     FTDTR(); 
     FinTabla();
   
    
  //botones de la parte inferior  
    FTDTR(); 
      ITRTD("",0,"","0","center","bottom"); 
        IFrame("IPanel23","0%","0","Paneles.js"); 
      FTDTR();    
  fFinPagina();
 }

 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
 //botenes parte inferior fBuscaFrame hasta fShow 
   FRMPanel = fBuscaFrame("IPanel23"); 
   FRMPanel.fSetControl(self,cPaginaWebJS); 
   //FRMPanel.fShow("Tra,"); 
   FRMListadoA = fBuscaFrame("IListado23izq"); 
   FRMListadoA.fSetControl(self); 
   FRMListadoA.fSetTitulo("Consecutivo,Departamentos Asignados a la Oficina,");
   FRMListadoA.fSetAlinea("center,left,");
   FRMListadoA.fSetCampos("0,1,");   
   FRMListado = fBuscaFrame("IListado23der");
   FRMListado.fSetTitulo("Consecutivo,Departamentos Dependientes,");
   FRMListado.fSetAlinea("center,left,");
   FRMListado.fSetCampos("2,4,"); 
   FRMListado.fSetControl(self); 
   FRMListadoA.fSetSelReg(2);
   fDisabled(true,"iCveOficina,iCveDepartamento,"); 
   frm.hdBoton.value="Primero";
   fNavega2();
   
    
 } 
//Segundo Formulario 
 function fOficina(){
        FRMListadoA.fSetListado(new Array); 
        FRMListadoA.fShow();      
        FRMListado.fSetListado(new Array); 
        FRMListado.fShow();
        frm.iflag.value=0;
        frm.iflag2.value=0;
  fSelectSetIndexFromValue(frm.iCveDepartamento, -1);
  frm.hdLlave.value = "iCveDepartamento";
  frm.hdSelect.value = "select deo.icvedepartamento,dep.cdscbreve from grldeptoxofic deo " +
                       "join grldepartamento dep on dep.icvedepartamento=deo.icvedepartamento "+ 
                        "where icveoficina= "+ frm.iCveOficina.value + " order by cDscBreve ";
                                              
  fEngSubmite("pgConsulta.jsp","CIDDepartamento");                      
 }
 //Listado del lado derecho
 function fNavega(){ 
  // alert();
   frm.hdFiltro.value =  "GRLDeptoDepend.iCveOficina = " + frm.iCveOficina.value+" AND GRLDeptoDepend.iCveDepartamento = "+frm.iCveDepartamento.value;
   frm.hdOrden.value =  "hijo";
   frm.hdNumReg.value =  10000; 
   
   fEngSubmite("pgGRLDeptoDepend.jsp","Listado");
   FRMPanel.fSetTraStatus("UpdateBegin"); 
   
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
 //Listado del lado Derecho
   if(cId == "Listado" && cError==""){ 
     frm.hdRowPag.value = iRowPag; 
     FRMListado.fSetListado(aRes); 
     FRMListado.fShow(); 
     frm.iflag.value = 0;
     if(FRMListado.fGetLength() > 0)
      frm.iflag.value = 1;
     else
      frm.iflag.value = 0;
     FRMListado.fSetLlave(); 
     fCancelar();     
     fNavega1();  
     //fAlert(aRes);
   } 
//Listado dek lado izquierdo    
    if(cId == "Listado2" && cError == ""){ 
    //alert(aRes);
     frm.hdRowPag.value = iRowPag; 
     FRMListadoA.fSetListado(aRes); 
     FRMListadoA.fShow();
     frm.iflag2.value = 0; 
     //fAlert("fGetLength " + FRMListadoA.fGetLength() );     
     if(FRMListadoA.fGetLength() > 0)
      frm.iflag2.value = 1;
     else
      frm.iflag2.value = 0; 
     fCancelar();  
     
   } 
    if(cId == "CIDOficina" && cError==""){
      fFillSelect(frm.iCveOficina,aRes,true,frm.iCveOficina.value,0,2);
    }
 
    if(cId == "CIDDepartamento" && cError==""){
      fFillSelect(frm.iCveDepartamento,aRes,true,frm.iCveDepartamento.value,0,1);
    }  
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar  insert
 function fGuardar(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
          FRMPanel.fSetTraStatus("UpdateBegin");  
          fDisabled(true); 
          FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar" update
 function fGuardarA(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){ 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fDisabled(false,"iCveOficina,"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){ 
    fDisabled(true,"iCveOficina,iCveDepartamento,"); 
    FRMListado.fSetDisabled(false);
    FRMPanel.fSetTraStatus("Mod,");  
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){ 
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){ 
       fNavega(); 
    } 
 }    
//hace el delete de la lista derecha
 function fSelReg(aDato){ 
    frm.iCveDeptoHijo2.value = aDato[2]; 
    
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
function fPaisOnChange() {
   frm.hdFiltro.value =  ""; 
   frm.hdOrden.value =  ""; 
   frm.hdNumReg.value =  100;
   fFilterSelect(frm.iCveEntidadFed,aEstados,frm.iCvePais.value,false,frm.iCveEntidadFed.value,1,2);
   return true;// fEstadoOnChange();
}
 function fSubir(){
   if (frm.iOrden.value>0){
     frm.hdBoton.value = "Subir";
     fNavega();
   }
 }
 function fBajar(){
   if(frm.iOrden.value<FRMListado.fGetLength())
   frm.hdBoton.value = "Bajar";
   fNavega();
 }
 function fAgregar(){
 //fAlert("iflag2 " + frm.iflag2.value ); 
 if(frm.iflag2.value==1){ 
  frm.hdBoton.value = "Guardar";
  fNavega();
 }
 else
  alert("No hay 'Departamentos Asignados' del lado izquierdo");
 }
 function fEliminar(){
  if(frm.iflag.value==1){
   frm.hdBoton.value = "Borrar";
   fNavega();
  }
  else
  alert("No hay 'Departamentos Dependientes' del lado derecho");  
 } 
//Listado del lado izquierdo
 function fNavega1(){
if(frm.iCveDepartamento.value==-1){
        FRMListadoA.fSetListado(new Array); 
        FRMListadoA.fShow();      
        FRMListado.fSetListado(new Array); 
        FRMListado.fShow();
}    else{
       frm.hdLlave.value="iCveDepartamento";
       frm.hdSelect.value="select grldeptoxofic.icvedepartamento," + 
                          "grldepartamento.cdscdepartamento from "  +    
                          "grldeptoxofic join grldepartamento on "  + 
                          "grldeptoxofic.icvedepartamento = "  +         
                          "grldepartamento.icvedepartamento " +
                          "where icveoficina= " + frm.iCveOficina.value +
                          " and grldepartamento.icvedepartamento not in " +
                          " ( " + frm.iCveDepartamento.value + " ) " +
                          " and grldepartamento.icvedepartamento not in (select " +
                          " icvedeptohijo from grldeptodepend where icveoficina= " +
                           + frm.iCveOficina.value + " and  icvedepartamento= " + frm.iCveDepartamento.value + " ) order by cDscBreve ";
   fEngSubmite("pgConsulta.jsp","Listado2"); 
  }
 }//Tabla Izquierda  
 function fSelReg2(aDato){ 
   frm.iCveDeptoHijo1.value = aDato[0];
//   if(aDato[0]!="")frm.iflag2.value = 1;
//   else frm.iflag2.value = 0;
 }                    
//primer formulario
 function fNavega2(){
   frm.hdFiltro.value =  ""; 
   frm.hdOrden.value =  "cDscBreve"; 
   frm.hdNumReg.value = 10000; 
   fEngSubmite("pgGRLOficina.jsp","CIDOficina");
   //fNavega();
 }
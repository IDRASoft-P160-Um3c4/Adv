// MetaCD=1.0 
 // Title: pg110010024.js
 // Description: JS "Catálogo" de la entidad ALMAreaAlmacen
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: Ithamar Caballero Altamirano
 var cTitulo = ""; 
 var FRMListadoAlmReg = "", FRMListadoAlmDisp = ""; 
 var frm;
 var cPermisoPag;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg110010024.js";
   if(top.fGetTituloPagina){; 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   }
   cPermisoPag = fGetPermisos(cPaginaWebJS);
 } 
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){ 
   fInicioPagina(cColorGenJS); 
   InicioTabla("",0,"100%","","","","1"); 
     ITRTD("",0,"","","center");
     InicioTabla("",0,"100%","center","","","1"); 
     InicioTabla("ETablaInfo",0,"95%","","","",1); 
          ITRTD("ETablaST",5,"","","center"); 
            TextoSimple(cTitulo); 
          FTDTR();
          InicioTabla("ETablaInfo",0,"95%","","","",1); 
            fDefOficXUsr(true,false,true);
          FinTabla;
     FinTabla();
   FinTabla();
               
     InicioTabla("",0,"100%","","center"); 
       ITRTD("",0,"","1","center"); 
       FTDTR(); 
         ITRTD("",0,"","","center",""); 
           IFrame("IListado24izq","95%","220","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");        
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fAgregar();");
             ITRTD("",0,"","100%","center","");       
               BtnImg("Buscar","btnquitar","fEliminar();");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");
               TextoSimple("<br>");
             ITRTD("",0,"","100%","center","");  
             ITRTD("",0,"","100%","center","");                        
             FTDTR();                    
           FinTabla();
               
         ITD("",0,"","","center","");           
           IFrame("IListado24der","95%","220","Listado.js","yes",true);           
       FTDTR();  
         FinTabla(); 
       FTDTR();
       FinTabla(); 
     FTDTR(); 
     FinTabla();
  //botones de la parte inferior  
    FTDTR(); 
    ITRTD("",0,"","0","center","bottom"); 
      IFrame("IPanel24","0","0","Paneles.js"); 
    FTDTR();
    Hidden("hdLlave");
    Hidden("hdSelect");
    Hidden("hdCveAlmacen");
  fFinPagina();
 }

 function fTodos(theCheck){
  fSeleccionaTodosEnListado(FRMListadoAlmDisp, 0, theCheck);
 }
 
 function fTodos2(theCheck){
  fSeleccionaTodosEnListado(FRMListadoAlmReg, 0, theCheck);
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   FRMPanel = fBuscaFrame("IPanel24"); 
   FRMPanel.fSetControl(self,cPaginaWebJS); 
   FRMPanel.fShow(","); 
   FRMListadoAlmDisp = fBuscaFrame("IListado24izq"); 
   FRMListadoAlmDisp.fSetControl(self); 
   FRMListadoAlmDisp.fSetTitulo(fCheckBoxTodos("cbTodos", "fTodos")+",Almacenes Disponibles,");
   FRMListadoAlmDisp.fSetObjs(0,"Caja");
   FRMListadoAlmDisp.fSetAlinea("center,");
   FRMListadoAlmDisp.fSetCampos("1,");
   FRMListadoAlmDisp.fSetSelReg(2);
      
   FRMListadoAlmReg = fBuscaFrame("IListado24der");
   FRMListadoAlmReg.fSetTitulo(fCheckBoxTodos("cbTodos", "fTodos2")+",Almacenes Asignados,");
   FRMListadoAlmReg.fSetObjs(0,"Caja");
   FRMListadoAlmReg.fSetAlinea("center,");
   FRMListadoAlmReg.fSetCampos("1,"); 
   FRMListadoAlmReg.fSetControl(self); 
   
   fDisabled(true,"iCveOficinaUsr,iCveDeptoUsr,"); 
   frm.hdBoton.value="Primero";
   
 } 

 //Listado del lado derecho
 function fNavegaAlmReg(){ 
   frm.hdFiltro.value =  " AAM.iCveOficina = " + frm.iCveOficinaUsr.value +
   " and AAM.iCveDepartamento = " + frm.iCveDeptoUsr.value;
   frm.hdOrden.value =  "CDSCALMACEN";
   frm.hdNumReg.value =  10000; 
   
   fEngSubmite("pgALMAreaAlmacen.jsp","ListadoAlmReg");
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
   if(cId == "ListadoAlmReg" && cError==""){
     aResReg = fCopiaArregloBidim(aRes);
     frm.hdRowPag.value = iRowPag; 
     FRMListadoAlmReg.fSetListado(aRes); 
     FRMListadoAlmReg.fShow(); 
     FRMListadoAlmReg.fSetLlave(); 
     fCancelar();     
     fNavegaAlmDisp();  
   } 
//Listado dek lado izquierdo    
   if(cId == "ListadoAlmDisp" && cError == ""){ 
    //alert(aRes);
     aResDisp = fCopiaArregloBidim(aRes);
     frm.hdRowPag.value = iRowPag; 
     FRMListadoAlmDisp.fSetListado(aRes); 
     FRMListadoAlmDisp.fShow();
     fCancelar();  
   }
   
   fResOficDeptoUsr(aRes,cId,cError,true);
 } 
 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){ 
    fDisabled(true,"iCveOficinaUsr,iCveDeptoUsr,"); 
    FRMListadoAlmReg.fSetDisabled(false);
    FRMPanel.fSetTraStatus("Mod,");  
 } 
  
//hace el delete de la lista derecha
 function fSelReg(aDato){ 
 //   frm.iCveDeptoHijo2.value = aDato[2]; 
    
 } 

 function fImprimir(){ 
    self.focus();
    window.print(); 
 } 
 function fAgregar(){
   if (cPermisoPag != 1){
     fAlert("No tiene Permiso de ejecutar esta acción");
     return;
   }
   frm.hdCveAlmacen.value = "";
   
   aAlmDisp = FRMListadoAlmDisp.fGetObjs(0);
   for(cont=0;cont < aAlmDisp.length;cont++)
    if(aAlmDisp[cont]){
      if (frm.hdCveAlmacen.value=="") frm.hdCveAlmacen.value=aResDisp[cont][0]; else frm.hdCveAlmacen.value+=","+aResDisp[cont][0];
      }
   
   if(frm.hdCveAlmacen.value == ""){
     fAlert("\n - Selecione al menos un registro para realizar esta operación.");
     return;
   }
   
   frm.hdBoton.value = "Guardar";
   fNavegaAlmReg();
 }
 function fEliminar(){
   if (cPermisoPag != 1){
     fAlert("No tiene Permiso de ejecutar esta acción");
     return;
   }
   frm.hdCveAlmacen.value = "";
   
   aAlmDisp = FRMListadoAlmReg.fGetObjs(0);
   for(cont=0;cont < aAlmDisp.length;cont++)
    if(aAlmDisp[cont]){
      if (frm.hdCveAlmacen.value=="") frm.hdCveAlmacen.value=aResReg[cont][0]; else frm.hdCveAlmacen.value+=","+aResReg[cont][0];
      }
   
   if(frm.hdCveAlmacen.value == ""){
     fAlert("\n - Selecione al menos un registro para realizar esta operación.");
     return;
   }
   
   frm.hdBoton.value = "Borrar";
   fNavegaAlmReg();
 } 
//Listado del lado izquierdo
 function fNavegaAlmDisp(){
   frm.hdLlave.value="iCveAlmacen";
   frm.hdSelect.value="SELECT " +
					"ALM.ICVEALMACEN AS ICVEALMACEN, " +
					"ALM.CDSCALMACEN AS CDSCALMACEN " +
					"FROM ALMALMACEN ALM " +
					"WHERE ALM.ICVEALMACEN NOT IN " +
					"( " +
					"  SELECT ICVEALMACEN FROM ALMAREAALMACEN "+
					"WHERE ICVEOFICINA = " +frm.iCveOficinaUsr.value +
					" AND ICVEDEPARTAMENTO = " +frm.iCveDeptoUsr.value +
					") ";
   fEngSubmite("pgConsulta.jsp","ListadoAlmDisp"); 
  
 }//Tabla Izquierda  
 function fSelReg2(aDato){ 
//   frm.iCveDeptoHijo1.value = aDato[0];
 }
 function fDeptoUsrOnChangeLocal(){
   fNavegaAlmReg();
 }

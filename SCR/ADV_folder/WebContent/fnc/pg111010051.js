// MetaCD=1.0 
 // Title: pg10000101040.js
 // Description: JS "Catálogo" de la entidad INTCAMPOS
 // Company: Tecnología InRed S.A. de C.V. 
 // Author: JESR
 var cTitulo = ""; 
 var FRMListado = ""; 
 var frm; 
 var aTipo = new Array();
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){ 
   cPaginaWebJS = "pg111010051.js";
   if(top.fGetTituloPagina){; 
     cTitulo = top.fGetTituloPagina(cPaginaWebJS).toUpperCase(); 
   } 
   aTipo[0]=['0','AGRUPADOR'];
   aTipo[1]=['1','ÁREA TEXTO'];
   aTipo[2]=['2','TEXTO'];
   aTipo[3]=['3','LISTA DESPLEGABLE'];
   aTipo[4]=['4','IMAGEN'];
   aTipo[5]=['5','RENGLÓN'];
   aTipo[6]=['6','LIGA'];
   aTipo[7]=['7','ADJUNTAR']   
 } 
 // SEGMENTO Definición de la página (Definición Mandatoria)
 function fDefPag(){ 
   fInicioPagina(cColorGenJS); 
   Estilo(".ESTitulo1","COLOR:BLACK;font-family: Verdana;font-size:8pt;");   
   InicioTabla("",0,"100%","100%","","","1"); 
     ITRTD("ESTitulo",0,"100%","1","center"); 
       TextoSimple(cTitulo); 
     FTDTR(); 
     ITRTD("",0,"","100%","top"); 
     InicioTabla("",0,"100%","100%","center"); 
       ITRTD("",0,"","34","center","top"); 
         IFrame("IFiltro","95%","34","Filtros.js"); 
       FTDTR(); 
       ITRTD("",0,"","100%","center","top"); 
         IFrame("IListado","95%","200","Listado.js","yes",true); 
       FTDTR(); 
       ITRTD("",0,"","1","center"); 
         InicioTabla("ETablaInfo",0,"95%","","","",".1",".1"); 
           ITRTD("ETablaST",5,"","","center"); 
             TextoSimple(cTitulo); 
           FTDTR(); 
           ITRTD("ESTitulo1",5,"","","left");
             Hidden("ICVECAMPO","");            
             TextoSimple("-- Salvo los campos de Agrupación o Cambio de Línea los nombres de los campos no deben repetirse.<br>"); 
             TextoSimple('-- Las Etiquetas no deben contener comas (,) ni comillas dobles (") o simples '+"(').");
           FTDTR();                
           ITR(); 
              TDEtiCampo(true,"EEtiqueta",0,"Campo:","CCAMPO","",50,75,"Campo","fSinMayus(this);");  
              TDEtiCampo(true,"EEtiqueta",0,"Etiqueta:","CETIQUETA","",50,75,"Etiqueta","fSinMayus(this);");
           FTR();                
           ITRTD("ESTitulo1",5,"","","left"); 
             TextoSimple("-- El tipo de campo RENGLÓN es utilizado para asignar un cambio de línea en tablas internas."); 
           FTDTR();                
           ITR();
              TDEtiCampo(true,"EEtiqueta",0,"Largo:","ILARGO","",3,3,"Largo","fSinMayus(this);");             
              TDEtiSelect(true,"EEtiqueta",0,"Tipo de Campo:","ICVETIPOCAMPO",""); 
           FTR();
           ITRTD("ESTitulo1",5,"","","left"); 
              TextoSimple("-- Tabla = ANUMERIC donde Cve.Tabla=No.Inicial y Dsc.Tabla=No.Final.<BR>");
              TextoSimple("-- Tabla = ALOGICAL donde 0=No y 1=Si.<BR>");
              TextoSimple("-- Tabla = ACUSTOM  donde Cve.Tabla=Lista separada por comas con los valores y Dsc.Tabla=Lista separada por comas con las descripciones.<BR>");             
           FTDTR();
           ITR();                
              TDEtiCampo(false,"EEtiqueta",0,"Tabla:","CTABLA","",50,75,"Tabla","fMayus(this);");  
              TDEtiCampo(false,"EEtiqueta",0,"Cve.Tabla:","CCVE","",50,255,"Cve.Tabla","fMayus(this);");
           FITR();                
              TDEtiCampo(false,"EEtiqueta",0,"Dsc.Tabla:","CDSC","",50,511,"Dsc.Tabla","fMayus(this);");
              ITD("",2);SP();FTD(); 
           ITR(); 
           ITRTD("ESTitulo1",5,"","","left");                         
              TextoSimple("-- Lista Ligada a = Lista que al ser seleccionada ejecutará un query a otra tabla tomando como filtro el valor de la lista desplegable actual.<br>");
              TextoSimple(SP()+SP()+"El valor debe ser Tabla,Campo Clave,Campo Dsc, de la tabla a ligar.<br>");                          
              TextoSimple("-- Lista Ligada a = NOLLENAR se coloca en la lista que espera ser llenada a través de la lista a seleccionar.");                          
           FTDTR();                
           ITR();                          
              TDEtiCampo(false,"EEtiqueta",0,"Lista Ligada a:","CLIGADO","",50,100,"Liga","fMayus(this);");
              TDEtiCampo(false,"EEtiqueta",0,"CAMPO DEL TRÁMITE:","CCAMPOCOP","",50,100,"Campo del Trámite","");               
           FTDTR();
           ITR();  
					ITRTD("ESTitulo1",5,"","","left"); 
						TextoSimple("-- Validaciones = {CAMPO} != ''; //Para campos de texto<br />");
						TextoSimple("-- Validaciones = {CAMPO} >= 1 && {CAMPO} <= 10; //Para listas desplegables con valores numéricos<br />");
					FTDTR();
	  ITR();                            
                                                    //lMandatorioM,cEstiloEM,iColExtiendeEM,cEtiquetaEM,iColM,iRengM,cNombreM,cValueM,cToolTip,cOnChange,cOnBlur,cOnAnyEvent,lSelectonFocus,lActivo,lContador,cEstiloCM,iColExtiendeCM			  
					TDEtiAreaTexto(false,"EEtiqueta",3,"Validaciones:",45,5,"CSCRIPT","","Validaciones","","fSinMayus(this);");  
           FTR();
         FinTabla(); 
         InicioTabla("ETablaInfo",0,"95%","","","",1); 
           ITR();  
              TDEtiCheckBox("EEtiqueta",0,"Lista Seleccionar:","LSELECCIONARBOX","1",true,"Seleccionar"); 
              Hidden("LSELECCIONAR","");
              TDEtiCheckBox("EEtiqueta",0,"Mandatorio:","LMANDATORIOBOX","1",true,"Mandatorio"); 
              Hidden("LMANDATORIO",""); 
              TDEtiCheckBox("EEtiqueta",0,"Repetible:","LFIJOBOX","1",true,"Repetible"); 
              Hidden("LFIJO",""); 
              TDEtiCheckBox("EEtiqueta",0,"Sig.Línea:","LENCABEZADOBOX","1",true,"Sig.Línea"); 
              Hidden("LENCABEZADO","");
           FTDTR();
         FinTabla(); 
         InicioTabla("ETablaInfo",0,"95%","","","",1); 
           ITRTD("EEtiquetaC",5);  
              Liga("COPIAR CAMPO","fCopiar();","Copiar");
           FTDTR();  
         FinTabla(); 
       FTDTR(); 
       FinTabla(); 
     FTDTR(); 
       ITRTD("",0,"","40","center","bottom"); 
         IFrame("IPanel","95%","34","Paneles.js"); 
       FTDTR(); 
     FinTabla(); 
   fFinPagina(); 
 } 
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){ 
   frm = document.forms[0]; 
   FRMPanel = fBuscaFrame("IPanel"); 
   FRMPanel.fSetControl(self,cPaginaWebJS); 
   FRMPanel.fShow("Tra,"); 
   FRMListado = fBuscaFrame("IListado"); 
   FRMListado.fSetControl(self); 
   FRMListado.fSetTitulo("Clave,Campo,Etiqueta,Largo,Tabla,Cve.Tabla,Dsc.Tabla,");   
   FRMListado.fSetCampos("0,1,2,10,4,5,6,"); 
   FRMFiltro = fBuscaFrame("IFiltro"); 
   FRMFiltro.fSetControl(self); 
   FRMFiltro.fShow(); 
   FRMFiltro.fSetFiltra("Clave,ICVECAMPO,Campo,CCAMPO,"); 
   FRMFiltro.fSetOrdena("Clave,ICVECAMPO,Campo,CCAMPO,"); 
   fDisabled(true); 
   frm.hdBoton.value="Primero"; 
   fNavega(); 
   fFillSelect(frm.ICVETIPOCAMPO,aTipo,false);   
 } 
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){ 
   frm.hdFiltro.value =  FRMFiltro.fGetFiltro(); 
   frm.hdOrden.value =  FRMFiltro.fGetOrden(); 
   frm.hdNumReg.value =  FRMFiltro.fGetNumReg(); 
   return fEngSubmite("pgINTCampos.jsp","Listado"); 
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
   if(cError!="") 
     FRMFiltro.fSetNavStatus("Record"); 
  
   if(cId == "Listado" && cError==""){ 
     frm.hdRowPag.value = iRowPag; 
     FRMListado.fSetListado(aRes); 
     FRMListado.fShow(); 
     FRMListado.fSetLlave(cLlave); 
     fCancelar(); 
     FRMFiltro.fSetNavStatus(cNavStatus); 
   } 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fCopiar(){
    fFillSelect(frm.ICVETIPOCAMPO,aTipo,false,frm.ICVETIPOCAMPO.value);
    FRMPanel.fSetTraStatus("UpdateBegin");  
    fDisabled(false,"ICVECAMPO,","--"); 
    FRMListado.fSetDisabled(true);  
 }
 function fNuevo(){ 
    fFillSelect(frm.ICVETIPOCAMPO,aTipo,false);
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fBlanked(); 
    fDisabled(false,"ICVECAMPO,","--"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
          FRMPanel.fSetTraStatus("UpdateComplete"); 
          fDisabled(true); 
          FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){ 
    if(fValidaTodo()==true){
       if(fNavega()==true){ 
         FRMPanel.fSetTraStatus("UpdateComplete"); 
         fDisabled(true); 
         FRMListado.fSetDisabled(false); 
       }
    }
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){ 
    fFillSelect(frm.ICVETIPOCAMPO,aTipo,false,frm.ICVETIPOCAMPO.value); 
    FRMPanel.fSetTraStatus("UpdateBegin"); 
    fDisabled(false,"ICVECAMPO,"); 
    FRMListado.fSetDisabled(true); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){ 
    FRMFiltro.fSetNavStatus("ReposRecord"); 
    if(FRMListado.fGetLength() > 0) 
      FRMPanel.fSetTraStatus("UpdateComplete"); 
    else 
      FRMPanel.fSetTraStatus("AddOnly"); 
    fDisabled(true); 
    FRMListado.fSetDisabled(false); 
 } 
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){ 
    if(confirm(cAlertMsgGen + "\n\n ¿Desea usted eliminar el registro?")){ 
       fNavega(); 
    } 
 } 
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){ 
    frm.ICVECAMPO.value = aDato[0]; 
    frm.CCAMPO.value = aDato[1]; 
    frm.CETIQUETA.value = aDato[2]; 
    frm.ICVETIPOCAMPO.value = aDato[3]; 
    frm.CTABLA.value = aDato[4]; 
    frm.CCVE.value = aDato[5]; 
    frm.CDSC.value = aDato[6]; 
    fAsignaCheckBox(frm.LMANDATORIOBOX,aDato[7]); 
    fAsignaCheckBox(frm.LFIJOBOX,aDato[8]); 
    fAsignaCheckBox(frm.LENCABEZADOBOX,aDato[9]); 
    frm.ILARGO.value = aDato[10];
    fAsignaCheckBox(frm.LSELECCIONARBOX,aDato[11]);
    frm.CLIGADO.value = aDato[12];  
    frm.CCAMPOCOP.value = aDato[13];
    frm.CSCRIPT.value = aDato[14];
 } 
 // FUNCIÓN donde se generan las validaciones de los datos ingresados
 function fValidaTodo(){ 
    cMsg = fValElements(); 
    frm.LMANDATORIO.value = frm.LMANDATORIOBOX.checked==true?1:0;   
    frm.LFIJO.value = frm.LFIJOBOX.checked==true?1:0;
    frm.LENCABEZADO.value = frm.LENCABEZADOBOX.checked==true?1:0;     
    frm.LSELECCIONAR.value = frm.LSELECCIONARBOX.checked==true?1:0;
     
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

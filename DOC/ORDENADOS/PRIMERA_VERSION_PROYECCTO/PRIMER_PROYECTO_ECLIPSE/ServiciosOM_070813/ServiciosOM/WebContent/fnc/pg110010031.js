// MetaCD=1.0
 // Title: pg110010031.js
 // Description: JS "Catálogo" de la entidad GRLUsuarioXOfic
 // Company: Tecnología InRed S.A. de C.V.
 // Author: Arturo Lopez Peña
 var cTitulo = "";
 var FRMListado = "";
 var frm;
 var aTotal     = new Array;
 var aParcial   = new Array;
 var aExistentes= new Array;
 // SEGMENTO antes de cargar la página (Definición Mandatoria)
 function fBefLoad(){
   cPaginaWebJS = "g110010031.js";
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
     ITRTD("",0,"","","top");
     InicioTabla("ETablaInfo",0,"95%","","","",1);
          ITRTD("ETablaST",5,"","","center");
            TextoSimple(cTitulo);
          FTDTR();
          ITR();
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Oficina:","iCveOficina","OficinaChange();");
           FITR();
              TDEtiSelect(true,"EEtiqueta",0,"Departamento:","iCveDepartamento","fNavega();");
           FITR();
     FinTabla();
   FinTabla();
     InicioTabla("",0,"100%","","center");
       ITRTD("",0,"","","center","");
         IFrame("IFiltro31","0","0","Filtros.js");
       FTDTR();
       ITRTD("",0,"","1","center");
       FTDTR();
         ITRTD("",0,"","","center","");
           IFrame("IListado31A","95%","350","Listado.js","yes",true);
         ITD("",0,"","","center","center");
           InicioTabla("",0,"100%","","");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnagregar","fGuardar();");
             ITRTD("",0,"","100%","center","");
               BtnImg("Buscar","btnquitar","fBorrar();");
             FTDTR();
           FinTabla();
         ITD("",0,"","","center","");
           IFrame("IListado31","95%","350","Listado.js","yes",true);
       FTDTR();
     FinTabla();
     FTDTR();
     ITRTD("",0,"","","center");
       InicioTabla("ETablaInfo",0,"75%","","");
         ITRTD("ETablaST",5,"","","center");
           TextoSimple("Datos del Usuario");
         FTDTR();
         ITR();
            TDEtiCampo(true,"EEtiqueta",0,"Teléfono:","cTelefono","",50,50,"Teléfono","fMayus(this);");
         FITR();
            TDEtiCampo(true,"EEtiqueta",0,"Correo Electrónico:","cCorreoE","",50,50,"Correo Electrónico","");
         FITR();
           TDEtiCheckBox("EEtiqueta",0,"¿Es Firmante?:","lFirmanteBOX","1",true,"Firmante de la oficina");
           Hidden("lFirmante");
         FTR();
       FinTabla();
     FTDTR();
       ITRTD("",0,"","40","center","bottom");
         IFrame("IPanel31","95%","34","Paneles.js");
       FTDTR();
     FinTabla();
   Hidden ("iCveUsuario");
   Hidden ("iCveUsuario1");
   Hidden ("iCveOficina1");
   Hidden ("iCveDepartamento1");
   fFinPagina();
 }
 // SEGMENTO Después de Cargar la página (Definición Mandatoria)
 function fOnLoad(){
   frm = document.forms[0];
   FRMPanel = fBuscaFrame("IPanel31");
   FRMPanel.fSetControl(self,cPaginaWebJS);
   FRMPanel.fShow("Tra,");
   FRMListado = fBuscaFrame("IListado31");
   FRMListado.fSetControl(self);
   FRMListado.fSetTitulo("Usuario X Oficina X Departamento,");
   FRMListado.fSetCampos("5,");
   FRMListadoA = fBuscaFrame("IListado31A");
   FRMListadoA.fSetControl(self);
   FRMListadoA.fSetTitulo("Usuario,");
   FRMListadoA.fSetCampos("16,");
   FRMFiltro = fBuscaFrame("IFiltro31");
   FRMFiltro.fSetControl(self);
   FRMFiltro.fShow();
   FRMFiltro.fSetFiltra("Oficina,iCveOficina,Departamento,iCveDepartamento,");
   FRMFiltro.fSetOrdena("Oficina,iCveOficina,Departamento,iCveDepartamento,");
   FRMListadoA.fSetSelReg(2);
   frm.hdBoton.value="Primero";
   fNavega1();
 }
 // LLAMADO al JSP específico para la navegación de la página
 function fNavega(){
  if (frm.iCveOficina.value != "" && frm.iCveDepartamento.value != ""){
   frm.hdFiltro.value =  " GRLUsuarioXOfic.iCveOficina = " + frm.iCveOficina.value + " And GRLUsuarioXOfic.iCveDepartamento = " + frm.iCveDepartamento.value;
   frm.hdOrden.value =  "cNom";
   frm.hdNumReg.value = 10000;
   return fEngSubmite("pgGRLUsuarioXOficB1.jsp","Listado");
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
   if(cError!="")
     FRMFiltro.fSetNavStatus("Record");

   if(cId == "Listado" && cError==""){
     aExistentes = new Array;
     frm.hdRowPag.value = iRowPag;
     FRMListado.fSetListado(aRes);
     FRMListado.fShow();
     for(i=0;i<aRes.length;i++){
       aExistentes[i] = aRes[i][2];
     }
     FRMListado.fSetLlave(cLlave);
     FRMFiltro.fSetNavStatus(cNavStatus);
     fCancelar();
     fListado1();
   }
    if(cId == "Listado2" && cError == ""){
     frm.hdRowPag.value = iRowPag;
     aTotal = fCopiaArregloBidim(aRes);
     frm.hdFiltro.value =  "";
     frm.hdOrden.value =  "cDscOficina";
     frm.hdNumReg.value = 10000;
     fEngSubmite("pgGRLOficina.jsp","CIDOficina");
   }
   if(cId == "CIDOficina" && cError == ""){
     fFillSelect(frm.iCveOficina,aRes,true,frm.iCveOficina.value,0,1);
   }
   if(cId == "CIDDepartamento" && cError == ""){
     fFillSelect(frm.iCveDepartamento,aRes,true,frm.iCveDepartamento.value,1,7);
   }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Nuevo
 function fNuevo(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fBlanked();
    fDisabled(false,"iCveOficina,iCveDepartamento,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Guardar
 function fGuardar(){
   frm.lFirmante.value = frm.lFirmanteBOX.checked == true?1:0;
   if (FRMListadoA.fGetLength() > 0){
     frm.hdBoton.value = "Guardar";
     fNavega();
   }  else alert("No hay Usuarios del lado izquierdo");
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón GuardarA "Actualizar"
 function fGuardarA(){
   frm.lFirmante.value = frm.lFirmanteBOX.checked == true?1:0;
    if(fValidaTodo()==true){
       if(fNavega()==true){
         FRMPanel.fSetTraStatus("Mod,");
         fDisabled(true,"iCveOficina,iCveDepartamento,");
         FRMListado.fSetDisabled(false);
       }
    }
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Modificar
 function fModificar(){
    FRMPanel.fSetTraStatus("UpdateBegin");
    fDisabled(false,"iCveOficina,iCveDepartamento,");
    FRMListado.fSetDisabled(true);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Cancelar
 function fCancelar(){
    FRMFiltro.fSetNavStatus("ReposRecord");
    if(FRMListado.fGetLength() > 0)
      FRMPanel.fSetTraStatus("Mod,");
    else
      FRMPanel.fSetTraStatus("");
    fDisabled(true,"iCveOficina,iCveDepartamento,");
    FRMListado.fSetDisabled(false);
 }
 // LLAMADO desde el Panel cada vez que se presiona al botón Borrar
 function fBorrar(){
   if (FRMListado.fGetLength() > 0){
     frm.hdBoton.value = "Borrar";
     fNavega();
   } else alert("No hay Usuarios del lado derecho");
 }
 // LLAMADO desde el Listado cada vez que se selecciona un renglón
 function fSelReg(aDato){
    frm.iCveUsuario.value = aDato[2];
    frm.cTelefono.value = aDato[3];
    frm.cCorreoE.value = aDato[4];
    frm.lFirmante.value = aDato[6];
    frm.lFirmanteBOX.checked = parseInt(frm.lFirmante.value,10)==1?true:false;
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
 function fNavega1(){
    if(frm.iCveDepartamento.value==-1){
        FRMListadoA.fSetListado(new Array);
        FRMListadoA.fShow();
        FRMListado.fSetListado(new Array);
        FRMListado.fShow();
    }
    else{
      frm.hdFiltro.value = "";
      frm.hdOrden.value =  "Usuario";
      frm.hdNumReg.value =  10000;
      fEngSubmite("pgSEGUsuario.jsp","Listado2");
    }
 }
 function fSelReg2(aDato){
   frm.iCveUsuario1.value=aDato[0];
 }
 function OficinaChange(){
        FRMListadoA.fSetListado(new Array);
        FRMListadoA.fShow();
        FRMListado.fSetListado(new Array);
        FRMListado.fShow();
  fSelectSetIndexFromValue(frm.iCveDepartamento, -1);
   frm.hdFiltro.value = " GRLDeptoXOfic.iCveOficina = " + frm.iCveOficina.value;
   frm.hdOrden.value =  "GRLDepartamento.cDscDepartamento";
   frm.hdNumReg.value = 10000;
   fEngSubmite("pgGRLDeptoXOfic.jsp","CIDDepartamento");
 }

function fListado1(){
  k=0;
  l=0;
  for(i=0;i<aTotal.length;i++){
    lEncontrada = 0;
    for (j=0;(j<aExistentes.length && l < aExistentes.length);j++){
      if(aTotal[i][0] == aExistentes[j]){

        lEncontrada = 1;
        l++;
      }
    }
    if(lEncontrada == 0){
      aParcial[k] = fCopiaArreglo(aTotal[i]);
      k++;
    }
  }
  FRMListadoA.fSetListado(aParcial);
  FRMListadoA.fShow();
}

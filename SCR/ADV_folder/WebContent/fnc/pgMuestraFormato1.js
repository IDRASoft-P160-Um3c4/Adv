// funciones empleadas al desplegar dinámicamente el formato No. 1
function fEstadoCampos1(){
  frm = document.forms[0];
  if(frm.iTipoPersona){
    if(fGetRadioValue(frm.iTipoPersona) == 1){
      if(frm.cApPaterno){
        frm.cApPaterno.value = "";
        frm.cApPaterno.disabled = true;
      }
      if(frm.cApMaterno){
        frm.cApMaterno.value = "";
        frm.cApMaterno.disabled = true;
      }
    }else{
      if(frm.cApPaterno)
        frm.cApPaterno.disabled = false;
      if(frm.cApMaterno)
        frm.cApMaterno.disabled = false;
    }
  }
}

function fGetParametrosConsulta(frmDestino){
  var lShowPersona     = true;
  var lShowRepLegal    = true;
  var lEditPersona     = false;
  var lEditDomPersona  = false;
  var lEditRepLegal    = false;
  var lEditDomRepLegal = false;
  if (frmDestino){
    if (frmDestino.setShowValues)
      frmDestino.setShowValues(lShowPersona, lShowRepLegal, "");
    if (frmDestino.setEditValues)
      frmDestino.setEditValues(lEditPersona, lEditDomPersona, lEditRepLegal, lEditDomRepLegal);
  }
}

function fValoresPersona(Persona_iCvePersona,Persona_cRFC,Persona_cRPA,Persona_iTipoPersona,
                         Persona_cNomRazonSocial,Persona_cApPaterno,Persona_cApMaterno,Persona_cCorreoE,
                         Persona_cPseudonimoEmp,Persona_iCveDomicilio,Persona_iCveTipoDomicilio,Persona_cCalle,
                         Persona_cNumExterior,Persona_cNumInterior,Persona_cColonia,Persona_cCodPostal,Persona_cTelefono,
                         Persona_iCvePais,Persona_cDscPais,Persona_iCveEntidadFed,Persona_cDscEntidadFed,Persona_iCveMunicipio,
                         Persona_cDscMunicipio,Persona_iCveLocalidad,Persona_cDscLocalidad,Persona_lPredeterminado,
                         Persona_cDscTipoDomicilio,Persona_cDscDomicilio){
  frm = document.forms[0];
  if(frm.iCvePersona)
    frm.iCvePersona.value = Persona_iCvePersona;

}

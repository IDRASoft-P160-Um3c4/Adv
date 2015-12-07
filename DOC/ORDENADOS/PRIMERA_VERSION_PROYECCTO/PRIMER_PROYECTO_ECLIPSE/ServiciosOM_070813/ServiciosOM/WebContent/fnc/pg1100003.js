// MetaCD=1.0
function fBefLoad(){ // Carga información antes de que la página sea mostrada.
}

function fDefPag(){ // Define la página a ser mostrada
    aPags = new Array();
    aPags[0] = [true,'pg'+iNDSADM+'00004.js','no','FRMTitulo','0',0,'margin: 0px; padding: 0px'];
    aPags[1] = [true,'pg'+iNDSADM+'00005.js','no','FRMDatos','0',0,'margin: 0px; padding: 0px'];
    DefFrameRow(aPags,"45,*",false,0,0);
}

function fOnLoad(){ // Carga información al mostrar la página.
}

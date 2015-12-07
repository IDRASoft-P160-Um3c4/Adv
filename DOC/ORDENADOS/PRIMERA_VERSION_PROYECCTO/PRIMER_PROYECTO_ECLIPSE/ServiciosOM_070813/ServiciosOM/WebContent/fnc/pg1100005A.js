// MetaCD=1.0
function fBefLoad(){ // Carga información antes de que la página sea mostrada.
}

function fDefPag(){ // Define la página a ser mostrada
    aPags = new Array();
    aPags[0] = [true,'pg'+iNDSADM+'00006A.js','yes','FRMMenu','0',0,'margin: 0px; padding: 0px'];
    cBar='yes';
    if(screen.availWidth <= 800)
      cBar='yes';
    aPags[1] = [true,'pg'+iNDSADM+'00009A.js',cBar,'FRMPagina','0',0,'margin: 0px; padding: 0px'];
    DefFrameCol(aPags,"300,*",true,0,0);
}

function fOnLoad(){ // Carga información al mostrar la página.
}

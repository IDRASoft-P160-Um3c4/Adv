  // Definición del manejo de Imágenes (Locales o Remotas)
  var SiAcvx = true;

  function getIEVersion(){
    var ua = navigator.userAgent
    var IEOffset = ua.indexOf('MSIE ')
    return parseFloat(ua.substring(IEOffset + 5, ua.indexOf(';', IEOffset)))
  }

  function fVerLocalImages(cLocal) {
      try{
        fsObj = new ActiveXObject("Scripting.FileSystemObject")
        SiAcvx = true;
        return fsObj.FolderExists(cLocal);
      }catch(e){
        if(SiAcvx == true){
          SiAcvx = false;
        }
        return false;
      }
  }

  function fVerRutaImg(cValor,cLocal) {
    cSuccess = cValor;
    if(navigator.appName == 'Microsoft Internet Explorer' && parseInt(getIEVersion()) > 4 && fVerLocalImages(cLocal)){
      cSuccess = cLocal;
    }else{
      cSuccess = cValor;
    }
    return cSuccess;
  }

  function fEncuentraObjeto(n, d) {
    var p,i,x;
    if(!d)
      d=vDocument;
    if((p=n.indexOf("?"))>0&&parent.frames.length) {
      d=parent.frames[n.substring(p+1)].document;
      n=n.substring(0,p);
    }
    if(!(x=d[n])&&d.all)
      x=d.all[n];
    if (d.forms){
      for (i=0;!x&&i<d.forms.length;i++)
        x=d.forms[i][n];
    }
    for(i=0;!x&&d.layers&&i<d.layers.length;i++)
      x=fEncuentraObjeto(n,d.layers[i].document);
    return x;
  }

  function fCambiaImagen() {
    var l,z=0,x,a=fCambiaImagen.arguments;
    var doc = a[0];
    vDocument = doc;
    doc.MM_sr=new Array;
    for(l=1;l<(a.length-2);l+=3)
    if ((x=fEncuentraObjeto(a[l]))!=null){
        doc.MM_sr[z++]=x;
        if(!x.oSrc)
          x.oSrc=x.src;
        cImg = a[l+2]+'/';
        cImg = fEntry(fNumEntries(cImg,"/"),cImg,"/");
        x.src=fVerRutaImg(cRutaImgServer,cRutaImgLocal) + cImg;
    }
  }

  function fPreCargarImagen(doc) {
     var d=doc;
     if(d.images){
       if(!d.MM_p)
         d.MM_p=new Array();
       var i,j=d.MM_p.length,a=fPreCargarImagen.arguments;
       for(i=1; i<a.length; i++)
         if (a[i].indexOf("#")!=0){
           d.MM_p[j]=new Image;
           d.MM_p[j++].src=a[i];
         }
     }
  }

  function fSrc(objImagen, cEstado){
    var cSourceTemp = objImagen.src;
    var cImageTemp  = cSourceTemp.substr(0, cSourceTemp.length - 5);
    var cExtTemp    = cSourceTemp.substr(cSourceTemp.length - 3, cSourceTemp.length);
    cImg = cImageTemp +'/';
    cImg = fEntry(fNumEntries(cImg,"/"),cImg,"/");
    objImagen.src   = fVerRutaImg(cRutaImgServer,cRutaImgLocal) + cImg + cEstado + '.' + cExtTemp;
  }

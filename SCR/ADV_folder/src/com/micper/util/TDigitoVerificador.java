package com.micper.util;

/**
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDigitoVerificador {
  private int iDigito = 0;
  public TDigitoVerificador(String cDig ) {
    this.setCDigito(cDig);
  }
  public TDigitoVerificador(int iDig ) {
    this.setCDigito(String.valueOf(iDig));
  }


  /**
   * Método encargado de obtener el dígito verificador de la matrícula
   * @param cCadenaOrigen	Cadena de dígitos de la cual se desea obtener el verificador
   * @return	Valor del dígito verificador.
   */
  public void setCDigito(String cCadenaOrigen) {
	//Integer iDigito = new Integer(0);
	Long lCadenaValida = null;
	int iAcumProd = 0;
	int iRecalculo = 0;
	int iSubProducto = 10;
	String cSubCadena = "";
	/*try{
		lCadenaValida = new Long(cCadenaOrigen);
	}catch(Exception e) {
		e.printStackTrace();
		lCadenaValida = null;
	}*/
	/*if(lCadenaValida == null)
		System.out.print("Cadena de matrícula no válida: " + cCadenaOrigen);
	else {*/
	System.out.print(">>>  Cadena Original:   "+cCadenaOrigen);
		for(int i=0; i<cCadenaOrigen.length(); i++) {
			String cTmp = cCadenaOrigen.substring(i, i+1).toUpperCase();
			int iTmp = 0;
			if(cTmp.equals("0")) iTmp=0;
			if(cTmp.equals("1")) iTmp=1;
			if(cTmp.equals("2")) iTmp=2;
			if(cTmp.equals("3")) iTmp=3;
			if(cTmp.equals("4")) iTmp=4;
			if(cTmp.equals("5")) iTmp=5;
			if(cTmp.equals("6")) iTmp=6;
			if(cTmp.equals("7")) iTmp=7;
			if(cTmp.equals("8")) iTmp=8;
			if(cTmp.equals("9")) iTmp=9;
			if(cTmp.equals("A")) iTmp=0;
			if(cTmp.equals("B")) iTmp=1;
			if(cTmp.equals("C")) iTmp=2;
			if(cTmp.equals("D")) iTmp=3;
			if(cTmp.equals("E")) iTmp=4;
			if(cTmp.equals("F")) iTmp=5;
			if(cTmp.equals("G")) iTmp=6;
			if(cTmp.equals("H")) iTmp=7;
			if(cTmp.equals("I")) iTmp=8;
			if(cTmp.equals("J")) iTmp=9;
			if(cTmp.equals("K")) iTmp=0;
			if(cTmp.equals("L")) iTmp=1;
			if(cTmp.equals("M")) iTmp=2;
			if(cTmp.equals("N")) iTmp=3;
			if(cTmp.equals("Ñ")) iTmp=4;
			if(cTmp.equals("O")) iTmp=5;
			if(cTmp.equals("P")) iTmp=6;
			if(cTmp.equals("Q")) iTmp=7;
			if(cTmp.equals("R")) iTmp=8;
			if(cTmp.equals("S")) iTmp=9;
			if(cTmp.equals("T")) iTmp=0;
			if(cTmp.equals("U")) iTmp=1;
			if(cTmp.equals("V")) iTmp=2;
			if(cTmp.equals("W")) iTmp=3;
			if(cTmp.equals("X")) iTmp=4;
			if(cTmp.equals("Y")) iTmp=5;
			if(cTmp.equals("Z")) iTmp=6;
			
			int iValorAct = new Integer(iTmp).intValue();
			System.out.print(">>>  VALOR DE SERIE: "+iValorAct);
			
			iAcumProd += iValorAct * (iValorAct + 1);
		}
		System.out.print("iAcumProd=" + iAcumProd);
		iRecalculo = (iAcumProd * iAcumProd) + (2*(100 * iAcumProd)) + (100 * 100);
		System.out.print("iRecalculo=" + iRecalculo);
		cSubCadena = (new Integer(iRecalculo)).toString().substring(1);
		System.out.print("cSubCadena=" + cSubCadena);
		while (iSubProducto >= 10) {
			iSubProducto = 0;
			for (int i=0; i<cSubCadena.length(); i++) {
				int iValorAct = new Integer(cSubCadena.substring(i, i+1)).intValue();
				iSubProducto += iValorAct;
			}
			System.out.print("iSubProducto=" + iSubProducto);
			cSubCadena = new Integer(iSubProducto).toString();
		}
		this.iDigito = new Integer(iSubProducto).intValue();
		System.out.print("Matrícula completa: " + cCadenaOrigen + "-" + this.iDigito);
	//}
	//return iDigito;
  }


  /*
  //  public static void main(String args[]){
  public void setCDigito(String valores){
    //String valores = "0123456789abcdefghijklmnopkrstuvwwxyz";
    //String valores = "l804k436";
System.out.print(valores);
    if(valores != null && valores.length() > 7 ){
      valores = valores.substring(0,8);
      char[] dIni = valores.toCharArray();
      int[] iIni = new int[valores.length()];
      int[] iFinal = new int[valores.length()];
      for(int i = 0; i < dIni.length;i++){
        iFinal[i] = ((int)dIni[i] - 48);
        if(iFinal[i] > 9)
          iFinal[i] = iFinal[i] - 49;
        if(iFinal[i] > 9)
          iFinal[i] = iFinal[i] - 10;
        if(iFinal[i] > 9)
          iFinal[i] = iFinal[i] - 10;
//        System.out.print(iFinal[i]+" - "+dIni[i]);
      }

      for(int j = 0; j < iFinal.length;j++){
        iIni[j] = iFinal[j];
        iFinal[j] = iFinal[j] + 1;
        if(iFinal[j] > 9)
          iFinal[j] = iFinal[j] - 10;
//        System.out.print(iFinal[j]);
      }
      int iSuma = 0;
      for(int k = 0; k < iFinal.length; k++ ){
        iSuma += iFinal[k] * iIni[k];
      }
//      System.out.print("iSuma : "+iSuma);
      //Recalcular Suma
      iSuma = (iSuma * iSuma) + (2 *(100 * iSuma)) + 10000;
      //int iNSuma = String.valueOf(iSuma).length();
      String cNSuma = String.valueOf(iSuma);
//      System.out.print("Recalcular iSuma : "+iSuma);
//      System.out.print("iNSuma : "+iNSuma);
      String cASuma = cNSuma.substring(1,cNSuma.length());
//      System.out.print("cASuma : "+cASuma);

      char[] dcAsuma = cASuma.toCharArray();
      int iDPreVerif = 0;
      for(int l = 0; l < dcAsuma.length; l++){
        iDPreVerif +=  Integer.parseInt(String.valueOf(dcAsuma[l]));
      }
//      System.out.print("iDPreVerif : "+iDPreVerif);
      int iDigitoverificador = 0;
      char[] dDIgverrif = String.valueOf(iDPreVerif).toCharArray();

      for(int m = 0; m < dDIgverrif.length; m++){
        iDigitoverificador +=  Integer.parseInt(String.valueOf(dDIgverrif[m]));
        if(iDigitoverificador > 9){
                      String tem="";
                      tem = String.valueOf(iDigitoverificador);
                      iDigitoverificador = Integer.parseInt(String.valueOf(tem.charAt(0)))+ Integer.parseInt(String.valueOf( tem.charAt(1)));
              }
      }
//      System.out.print("valores : "+valores);
//      System.out.print("iDigitoverificador : "+iDigitoverificador);
      this.iDigito = iDigitoverificador;
    }
  }
*/
  public int getiDigito() {
    return iDigito;
  }
}

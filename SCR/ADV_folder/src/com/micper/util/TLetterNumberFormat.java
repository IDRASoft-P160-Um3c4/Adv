/*
 * numberLetterFormat.java
 *
 * Created on 1 de diciembre de 2003, 04:21 PM
 */

package com.micper.util;
import java.util.*;
import java.io.*;
import com.micper.excepciones.*;

/**
 *
 * @author  evalladares
 */
public class TLetterNumberFormat implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = -9157309956809535774L;
	/** Creates a new instance of numberLetterFormat */
    private HashMap unidades;
    private HashMap centenas;

	public TLetterNumberFormat() {
        unidades = new HashMap();
        centenas = new HashMap();
        unidades.put("0", "");
        unidades.put("00", "");
        unidades.put("000", "");
        unidades.put("01", "uno");
        unidades.put("02", "dos");
        unidades.put("03", "tres");
        unidades.put("04", "cuatro");
        unidades.put("05", "cinco");
        unidades.put("06", "seis");
        unidades.put("07", "siete");
        unidades.put("08", "ocho");
        unidades.put("09", "nueve");
        unidades.put("1", "uno");
        unidades.put("2", "dos");
        unidades.put("3", "tres");
        unidades.put("4", "cuatro");
        unidades.put("5", "cinco");
        unidades.put("6", "seis");
        unidades.put("7", "siete");
        unidades.put("8", "ocho");
        unidades.put("9", "nueve");
        unidades.put("10", "diez");
        unidades.put("11", "once");
        unidades.put("12", "doce");
        unidades.put("13", "trece");
        unidades.put("14", "catorce");
        unidades.put("15", "quince");
        unidades.put("16", "dieciseis");
        unidades.put("17", "diecisiete");
        unidades.put("18", "dieciocho");
        unidades.put("19", "diecinueve");
        unidades.put("20", "veinte");
        unidades.put("21", "veintiuno");
        unidades.put("22", "veintidos");
        unidades.put("23", "veintitres");
        unidades.put("24", "veinticuatro");
        unidades.put("25", "veinticinco");
        unidades.put("26", "veintiseis");
        unidades.put("27", "veintisiete");
        unidades.put("28", "veintiocho");
        unidades.put("29", "veintinueve");
        unidades.put("30", "treinta");
        unidades.put("31", "treinta y uno");
        unidades.put("32", "treinta y dos");
        unidades.put("33", "treinta y tres");
        unidades.put("34", "treinta y cuatro");
        unidades.put("35", "treinta y cinco");
        unidades.put("36", "treinta y seis");
        unidades.put("37", "treinta y siete");
        unidades.put("38", "treinta y ocho");
        unidades.put("39", "treinta y nueve");
        unidades.put("40", "cuarenta");
        unidades.put("41", "cuarenta y uno");
        unidades.put("42", "cuarenta y dos");
        unidades.put("43", "cuarenta y tres");
        unidades.put("44", "cuarenta y cuatro");
        unidades.put("45", "cuarenta y cinco");
        unidades.put("46", "cuarenta y seis");
        unidades.put("47", "cuarenta y siete");
        unidades.put("48", "cuarenta y ocho");
        unidades.put("49", "cuarenta y nueve");
        unidades.put("50", "cincuenta");
        unidades.put("51", "cincuenta y uno");
        unidades.put("52", "cincuenta y dos");
        unidades.put("53", "cincuenta y tres");
        unidades.put("54", "cincuenta y cuatro");
        unidades.put("55", "cincuenta y cinco");
        unidades.put("56", "cincuenta y seis");
        unidades.put("57", "cincuenta y siete");
        unidades.put("58", "cincuenta y ocho");
        unidades.put("59", "cincuenta y nueve");
        unidades.put("60", "sesenta");
        unidades.put("61", "sesenta y uno");
        unidades.put("62", "sesenta y dos");
        unidades.put("63", "sesenta y tres");
        unidades.put("64", "sesenta y cuatro");
        unidades.put("65", "sesenta y cinco");
        unidades.put("66", "sesenta y seis");
        unidades.put("67", "sesenta y siete");
        unidades.put("68", "sesenta y ocho");
        unidades.put("69", "sesenta y nueve");
        unidades.put("70", "setenta");
        unidades.put("71", "setenta y uno");
        unidades.put("72", "setenta y dos");
        unidades.put("73", "setenta y tres");
        unidades.put("74", "setenta y cuatro");
        unidades.put("75", "setenta y cinco");
        unidades.put("76", "setenta y seis");
        unidades.put("77", "setenta y siete");
        unidades.put("78", "setenta y ocho");
        unidades.put("79", "setenta y nueve");
        unidades.put("80", "ochenta");
        unidades.put("81", "ochenta y uno");
        unidades.put("82", "ochenta y dos");
        unidades.put("83", "ochenta y tres");
        unidades.put("84", "ochenta y cuatro");
        unidades.put("85", "ochenta y cinco");
        unidades.put("86", "ochenta y seis");
        unidades.put("87", "ochenta y siete");
        unidades.put("88", "ochenta y ocho");
        unidades.put("89", "ochenta y nueve");
        unidades.put("90", "noventa");
        unidades.put("91", "noventa y uno");
        unidades.put("92", "noventa y dos");
        unidades.put("93", "noventa y tres");
        unidades.put("94", "noventa y cuatro");
        unidades.put("95", "noventa y cinco");
        unidades.put("96", "noventa y seis");
        unidades.put("97", "noventa y siete");
        unidades.put("98", "noventa y ocho");
        unidades.put("99", "noventa y nueve");
        centenas.put("0", "");
        centenas.put("1", "cien");
        centenas.put("2", "doscientos");
        centenas.put("3", "trescientos");
        centenas.put("4", "cuatrocientos");
        centenas.put("5", "quinientos");
        centenas.put("6", "seiscientos");
        centenas.put("7", "setecientos");
        centenas.put("8", "ochocientos");
        centenas.put("9", "novecientos");
    }
    /**
     *Metodo para obtener un numero expresado en letra. (Hasta Mil millones)
     *arg numero, un numero tipo long que desea ser representado por una cantidad con
     *letra.
     *throws FormatException en caso de que la cantidad que se desea representar no se
     *ajuste a las dimensiones adecuadas.
     *
     */
    public String getCantidad(long numero)throws TFormatException{

        String cadena = new Long(numero).toString();
        String cantidad = null;
        String unidadesStr = null;
        String centenasStr = null;
        String millarStr = null;
        String cMillarStr = null;
        String millonStr = null;
        String cMillonStr = null;

        int size = cadena.length();
        boolean set = false;
        StringBuffer numeroSB = new StringBuffer(cadena);
        StringBuffer numeroSBR = numeroSB.reverse();
        switch(size){

            case 1: cantidad = (String)unidades.get(cadena);
            set = true;

            break;

            case 2: cantidad = (String)unidades.get(cadena);
            set = true;

            break;

            case 3: unidadesStr = numeroSBR.substring(0,2);
            centenasStr = numeroSBR.substring(2);

            break;

            case 4: unidadesStr = numeroSBR.substring(0,2);
            centenasStr = numeroSBR.substring(2,3);
            millarStr = numeroSBR.substring(3);

            break;

            case 5: unidadesStr = numeroSBR.substring(0,2);
            centenasStr = numeroSBR.substring(2,3);
            millarStr = numeroSBR.substring(3);

            break;

            case 6: unidadesStr = numeroSBR.substring(0,2);
            centenasStr = numeroSBR.substring(2,3);
            millarStr = numeroSBR.substring(3,5);
            cMillarStr = numeroSBR.substring(5);

            break;

            case 7: unidadesStr = numeroSBR.substring(0,2);
            centenasStr = numeroSBR.substring(2,3);
            millarStr = numeroSBR.substring(3,5);
            cMillarStr = numeroSBR.substring(5,6);
            millonStr = numeroSBR.substring(6);

            break;

            case 8: unidadesStr = numeroSBR.substring(0,2);
            centenasStr = numeroSBR.substring(2,3);
            millarStr = numeroSBR.substring(3,5);
            cMillarStr = numeroSBR.substring(5,6);
            millonStr = numeroSBR.substring(6);

            break;

            case 9: unidadesStr = numeroSBR.substring(0,2);
            centenasStr = numeroSBR.substring(2,3);
            millarStr = numeroSBR.substring(3,5);
            cMillarStr = numeroSBR.substring(5,6);
            millonStr = numeroSBR.substring(6,8);
            cMillonStr = numeroSBR.substring(8);

            break;
            default: throw new TFormatException("Dimensiones invalidas para la cantidad");

        }
        StringBuffer total = new StringBuffer();
        boolean cMill = false;
        boolean cMil = false;
        boolean cien = false;

        if(cMillonStr!=null){
            String cllstr = new StringBuffer(cMillonStr).reverse().toString();
            String cllcnt = (String)centenas.get(cllstr);
            if(cllcnt.equals("cien")){
            cMill = true;
            }
            total.append(cllcnt);
        }

        if(millonStr!=null){
            String llstr = new StringBuffer(millonStr).reverse().toString();
            String prefijo = "";
            String sufijo = "millones";
            String infijo = (String)unidades.get(llstr);

            if(cMill & !infijo.equals("")){
                prefijo = "to";
            }
            if(infijo.indexOf("uno")!=-1 & infijo.indexOf("uno")!=0){
                infijo = infijo.replaceAll("uno", "un");
                sufijo = "millones";
            }
            if(infijo.equals("uno") & !cMill){
                infijo = "un";
                sufijo = "millon";
            }
            if(infijo.equals("uno") & cMill){
                infijo = "un";
                sufijo = "millones";
            }
            total.append(prefijo+" "+infijo+" "+sufijo);
        }
        if(cMillarStr!=null){
            String cMstr = new StringBuffer(cMillarStr).reverse().toString();
            String cmcnt = (String)centenas.get(cMstr);
            if(cmcnt.equals("cien")){
            cMil = true;
            }
            total.append(" "+cmcnt);
        }

        if(millarStr!=null){
            String mstr = new StringBuffer(millarStr).reverse().toString();
            String prefijo = "";
            String infijo = (String)unidades.get(mstr);
            String sufijo = "mil";

            if(infijo.equals("")){
                sufijo =" ";
            }
            if(mstr.equals("00")){
              sufijo = "mil";
            }

            if(cMil && !infijo.equals("")){
                prefijo = "to";
            }

            if(infijo.indexOf("uno")!=-1){
                infijo = infijo.replaceAll("uno", "un");
            }
            total.append(prefijo+" "+infijo+" "+ sufijo);

        }


        if(centenasStr!=null){
            String cstr = new StringBuffer(centenasStr).reverse().toString();
            String ccant = (String)centenas.get(cstr);
            if(ccant.equals("cien")){
                cien = true;
            }
            total.append(" "+ccant);

        }
        if(unidadesStr!=null){
            String ustr = new StringBuffer(unidadesStr).reverse().toString();
            String sufijo = (String)unidades.get(ustr);
            String prefijo = "";
            if(!sufijo.equals("") && cien)
                prefijo = "to";
            total.append(prefijo+" "+sufijo);
        }
        if(!set)
        cantidad = total.toString();
        cantidad = cantidad.replaceAll("  ", " ");
        cantidad = cantidad.replaceAll("   ", " ");
        cantidad = cantidad.replaceAll("    ", " ");
        return cantidad.trim();

    }
    public static void main(String args[]) throws Exception{
 		TLetterNumberFormat lnf = new TLetterNumberFormat();
//        debug("Cantidad total: ");
//        debug(lnf.getCantidad(Long.parseLong(args[0])));
    }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

}

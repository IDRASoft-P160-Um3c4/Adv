package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.excepciones.DAOException;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;
import gob.sct.sipmm.dao.*;

public class TDRAIAseguramiento extends DAOBase{
	private int iEjercicioOficio = 0;
	private String cDigitosFolio = "";
	private int iConsecutivo = 0;

	public TDRAIAseguramiento(){
	}

	public Vector resolucionAseguramiento(String cQuery,String cNumFolio,
			String cCveOficinaOrigen,String cCveDeptoOrigen){

		Vector vcCuerpo = new Vector();
		Vector vcCopiasPara = new Vector();
		Vector vRegs = new Vector();
		TWord rep = new TWord();
		TFechas Fechas = new TFechas("44");
		int iCveOficinaDest=0,iCveDeptoDest=0,iCveTitular=0,iCveDomicilio=1,iCveRepLegal=0;
		String cParametros [] = cQuery.split(",");
		int iCveRegularizacion = Integer.parseInt(cParametros[0]);
		iCveTitular = Integer.parseInt(cParametros[1]);

		System.out.print("*****  "+cParametros[2]);
		//if(cParametros[2]!="")iCveDomicilio = Integer.parseInt(cParametros[2]);else iCveDomicilio = 1;
		String cAsunto = "";
		String cSql =
                    "SELECT R.ICVEREGULARIZACION,P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS COCUPANTE, " +
                    "U.LDENTRORECINTOPORTUARIO, U.CUBICACION, PU.CDSCPUERTO, " +
                    "U.COBRAENCONTRADA||', Colinda con: '|| U.CPREDIOCOLINDANTE AS COBRAENCONTRADA, " +
                    "F.DTASIGNACION AS DTOFICIOREQUERIMIENTO, RF.CDIGITOSFOLIO cDigitosReq,RF.ICONSECUTIVOFOLIO as iConsReq,RF.IEJERCICIO as iEjerReq, " +
                    "CN.TSFECHANOTIFICACION, " +
                    "R.TSCOMISIONREAL, " +
                    "F1.DTASIGNACION AS DTOFICIOSANCION, RF1.CDIGITOSFOLIO,RF1.ICONSECUTIVOFOLIO,RF1.IEJERCICIO,rf1.ICONSECUTIVO, " +
                    "SA.IDIASSMGDF "+
                    "FROM RAIREGULARIZACION R " +
                    "JOIN GRLPERSONA P ON R.ICVEOCUPANTE = P.ICVEPERSONA " +
                    "LEFT JOIN RAIUBICACION U ON R.ICVEREGULARIZACION = U.ICVEREGULARIZACION " +
                    "LEFT JOIN GRLPUERTO PU ON  U.ICVEPUERTO = PU.ICVEPUERTO " +
                    "LEFT JOIN RAIFOLIO RF  ON  R.ICVEREGULARIZACION = RF.ICVEREGULARIZACION AND RF.ICVETIPODOCTO = 1 " +
                    "LEFT JOIN GRLFOLIO F   ON  RF.IEJERCICIO = F.IEJERCICIO AND RF.ICVEOFICINA = F.ICVEOFICINA " +
                    "                       AND RF.ICVEDEPARTAMENTO = F.ICVEDEPARTAMENTO AND RF.CDIGITOSFOLIO = F.CDIGITOSFOLIO " +
                    "                       AND RF.ICONSECUTIVOFOLIO = F.ICONSECUTIVO " +
                    "LEFT JOIN RAINOTOFICIO RNO ON RF.ICVEREGULARIZACION = RNO.ICVEREGULARIZACION AND RF.ICONSECUTIVO = rno.ICONSECUTIVO " +
                    "LEFT JOIN CYSCITANOTIFICACION CN ON RNO.IMOVCITANOTIFICACION = CN.IMOVCITANOTIFICACION " +
                    "LEFT JOIN RAIFOLIO RF1 ON  R.ICVEREGULARIZACION = RF1.ICVEREGULARIZACION AND RF1.ICVETIPODOCTO = 2 " +
                    "LEFT JOIN GRLFOLIO F1  ON  RF1.IEJERCICIO = F1.IEJERCICIO AND RF1.ICVEOFICINA = F1.ICVEOFICINA " +
                    "                       AND RF1.ICVEDEPARTAMENTO = F1.ICVEDEPARTAMENTO AND RF1.CDIGITOSFOLIO = F1.CDIGITOSFOLIO " +
                    "                       AND RF1.ICONSECUTIVOFOLIO = F1.ICONSECUTIVO " +
                    "LEFT JOIN RAISANCION SA ON R.ICVEREGULARIZACION = SA.ICVEREGULARIZACION "+
                    "WHERE R.ICVEREGULARIZACION  = "+iCveRegularizacion+" " +
                    "ORDER BY rf.ICONSECUTIVO,rf1.ICONSECUTIVO DESC ";

		Vector vcData = new Vector();
		TDRAIFolio dFolio = new TDRAIFolio();
		TDObtenDatos dObten = new TDObtenDatos();
		dObten.dFolio.setDatosFolio(cNumFolio);

                    try{
                      vcData = this.FindByGeneric("",cSql,dataSourceName);
                    } catch(Exception e){
                      e.printStackTrace();
                    }
                rep.iniciaReporte();
                if(vcData.size()>0){
                  TVDinRep vData = (TVDinRep) vcData.get(0);
                  rep.comRemplaza("[cOcupanteIrregular]",vData.getString("COCUPANTE"));
                  rep.comRemplaza("[cUbicacionPuerto]",vData.getInt("LDENTRORECINTOPORTUARIO")==1? vData.getString("CDSCPUERTO"):vData.getString("CUBICACION"));
                  rep.comRemplaza("[cFechaOficioRequerimiento]",Fechas.getDateSPN(vData.getDate("DTOFICIOREQUERIMIENTO")));
                  rep.comRemplaza("[cNumOficioRequerimiento]",vData.getString("cDigitosReq")+"."+vData.getInt("iConsReq")+"/"+vData.getInt("iEjerReq"));
                  rep.comRemplaza("[cFechaNotificacionRequerimiento]",Fechas.getDateSPN(Fechas.getDateSQL(vData.getTimeStamp("TSFECHANOTIFICACION"))));
                  rep.comRemplaza("[cFechaRealDiligencia]",Fechas.getDateSPN(Fechas.getDateSQL(vData.getTimeStamp("TSCOMISIONREAL"))));
                  rep.comRemplaza("[cUbicacion]",vData.getString("COBRAENCONTRADA"));
                  rep.comRemplaza("[cNumOficioAseguramientoSancion]",vData.getString("CDIGITOSFOLIO")+"."+vData.getInt("ICONSECUTIVOFOLIO")+"/"+vData.getInt("IEJERCICIO"));
                  rep.comRemplaza("[cFechaOficioSancionAseguramiento]",Fechas.getDateSPN(vData.getDate("DTOFICIOSANCION")));
                  rep.comRemplaza("[cDiasSancion]",vData.getString("IDIASSMGDF"));//////??????????
                }

		//return ;
		// Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
		Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
				Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
				0,0,
				iCveTitular,iCveDomicilio,iCveRepLegal,
				"",cAsunto,
				"","",
				true,"cCuerpo",vcCuerpo,
				true,vcCopiasPara,
				rep.getEtiquetasBuscar(),rep.getEtiquetasRemplazar());

		TVDinRep vInsertaFolio = new TVDinRep();
		vInsertaFolio.put("iCveRegularizacion", iCveRegularizacion);
		vInsertaFolio.put("iEjercicio", dObten.dFolio.getCveEjercicio());
		vInsertaFolio.put("iCveOficina", dObten.dFolio.getCveOficina());
		vInsertaFolio.put("iCveDepartamento", dObten.dFolio.getCveDepartamento());
		vInsertaFolio.put("cDigitosFolio", dObten.dFolio.getCveDigitosFolio());
		vInsertaFolio.put("iConsecutivoFolio", dObten.dFolio.getCveConsecutivo());
		vInsertaFolio.put("iCveTipoDocto", 3);

		try {
			dFolio.insert(vInsertaFolio, null);
		} catch (DAOException e) {
			e.printStackTrace();
		}


		return vRetorno;

	}

}

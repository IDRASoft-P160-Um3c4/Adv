package gob.sct.sipmm.dao.reporte;

import java.sql.*;
import java.util.*;

import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.sql.*;
import com.micper.util.*;

/**
 * <p>Title: TDTARDoctos.java</p>
 * <p>Description: DAO de reportes de tarifas empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Angel Zamora Portugal
 * @author iCaballero
 * @version 1.0
 */

public class TDTARDoctos extends DAOBase{
    private TParametro VParametros = new TParametro("44");
    private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
    private String cTipoAutorizacion="";
    private String cCuerpoServicioPortuario = "";
    private String cLeyendaEnc = VParametros.getPropEspecifica("LeyendaEnc");

    public TDTARDoctos(){
    }

  /*Métodos que hacen llamado, a GenAutorizaRegistra, que es el encargado de hacer los oficios de autorización,
   registro, acuerdo, maniobras, documentos complementarios e improcedencia para servicios portuarios y conexos.*/

    public Vector GenAutorizacion(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE AUTORIZAN BASE DE REGULACIÓN TARIFARIA APLICABLES AL ";
        int lAutorizacion = 1;
        int iNumReporte = 1;
        int lManiobra = 0;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistro(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE REGISTRAN BASE DE REGULACIÓN TARIFARIA APLICABLES AL ";
        int lAutorizacion = 0;
        int iNumReporte = 2;
        int lManiobra = 0;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionManiobras(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE AUTORIZAN BASE DE REGULACIÓN TARIFARIA DE MANIOBRAS APLICABLES AL ";
        int lAutorizacion = 1;
        int iNumReporte = 11;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroManiobras(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE REGISTRAN BASE DE REGULACIÓN TARIFARIA  DE MANIOBRAS APLICABLE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 12;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionPosteriores(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE AUTORIZA INCREMENTO A LA TARIFA APLICABLE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 13;
        int lManiobra = 0;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroPosteriores(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE REGISTRAN INCREMENTO A LA TARIFA APLICABLE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 14;
        int lManiobra = 0;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionManiobrasPosteriores(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE AUTORIZAN INCREMENTO A LA TARIFA DE MANIOBRAS APLICABLE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 15;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroManiobrasPosteriores(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE REGISTRAN INCREMENTO A LA TARIFA DE MANIOBRAS APLICABLE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 16;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionConexo(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE AUTORIZAN BASE DE REGULACIÓN TARIFARIA APLICABLES AL ";
        int lAutorizacion = 1;
        int iNumReporte = 4;
        int lManiobra = 0;
        int lServicioPortuario = 0;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroConexo(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE REGISTRAN BASE DE REGULACIÓN TARIFARIA APLICABLES AL ";
        int lAutorizacion = 0;
        int iNumReporte = 5;
        int lManiobra = 0;
        int lServicioPortuario = 0;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionConexoPosteriores(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE AUTORIZA INCREMENTO A LA TARIFA APLICABLE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 17;
        int lManiobra = 0;
        int lServicioPortuario = 0;
        int lAcuerdo = 0;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroConexoPosteriores(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE REGISTRAN INCREMENTO A LA TARIFA APLICABLE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 18;
        int lManiobra = 0;
        int lServicioPortuario = 0;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAcuerdoAutorizacionSPortuario(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "ACUERDO AUTORIZACIÓN A LA TARIFA APLICABLE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 6;
        int lManiobra = 0;
        int lServicioPortuario = 1;
        int lAcuerdo = 1;
        cTipoAutorizacion = " autorizar ";

        try{
            rep.iniciaReporte();
            vDatosRegresa = GenAutorizaRegistra(cFiltro,"","","",cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAcuerdoRegistroSPortuario(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "ACUERDO REGISTRO A LA TARIFA APLICABLE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 7;
        int lManiobra = 0;
        int lServicioPortuario = 1;
        int lAcuerdo = 1;
        cTipoAutorizacion = " registrar ";

        try{
            rep.iniciaReporte();
            vDatosRegresa = GenAutorizaRegistra(cFiltro,"","","",cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAcuerdoAutoManiobras(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "ACUERDO AUTORIZACIÓN A LA TARIFA APLICABLE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 19;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 1;
        cTipoAutorizacion = " autorizar ";

        try{
            rep.iniciaReporte();
            vDatosRegresa = GenAutorizaRegistra(cFiltro,"","","",cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAcuerdoRegManiobras(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "ACUERDO REGISTRO A LA TARIFA APLICABLE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 20;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 1;
        cTipoAutorizacion = " registrar ";

        try{
            rep.iniciaReporte();
            vDatosRegresa = GenAutorizaRegistra(cFiltro,"","","",cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAcuerdoAutorizacionSConexo(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE AUTORIZAN BASE DE REGULACIÓN TARIFARIA APLICABLES AL ";
        int lAutorizacion = 1;
        int iNumReporte = 21;
        int lManiobra = 0;
        int lServicioPortuario = 0;
        int lAcuerdo = 1;
        cTipoAutorizacion = " autorizar ";


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,"","","",cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAcuerdoRegistroSConexo(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE REGISTRAN BASE DE REGULACIÓN TARIFARIA APLICABLES AL ";
        int lAutorizacion = 0;
        int iNumReporte = 22;
        int lManiobra = 0;
        int lServicioPortuario = 0;
        int lAcuerdo = 1;
        cTipoAutorizacion = " registrar ";


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,"","","",cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionInfoComplementaria(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SOLICITUD DE INFORMACIÓN COMPLEMENTARIA CORRESPONDIENTE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 23;
        int lManiobra = 2;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionInfoComplementariaManiobra(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SOLICITUD DE INFORMACIÓN COMPLEMENTARIA CORRESPONDIENTE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 43;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroInfoComplementaria(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SOLICITUD DE INFORMACIÓN COMPLEMENTARIA CORRESPONDIENTE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 24;
        int lManiobra = 2;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroInfoComplementariaManiobra(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SOLICITUD DE INFORMACIÓN COMPLEMENTARIA CORRESPONDIENTE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 45;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionNoProcede(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "NO PROCEDE LA AUTORIZACIÓN DE LA TARIFA APLICABLE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 25;
        int lManiobra = 2;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;
        cCuerpoServicioPortuario = "Adjunto al presente encontrara el oficio 115.364.2003 (00944) de fecha 30 de enero de 2003,"+
                " en el cual se establecen los requisitos que deberá cumplir para efectuar el trámite  de autorización de tarifa de servicios portuarios.";


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionNoProcedeManiobras(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "NO PROCEDE LA AUTORIZACIÓN DE LA TARIFA APLICABLE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 44;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;
        cCuerpoServicioPortuario = "Adjunto al presente encontrara el oficio 115.364.2003 (00944) de fecha 30 de enero de 2003,"+
                " en el cual se establecen los requisitos que deberá cumplir para efectuar el trámite  de autorización de tarifa de servicios portuarios.";


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroNoProcede(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "No procede el registro de la tarifa aplicable al ";
        int lAutorizacion = 0;
        int iNumReporte = 26;
        int lManiobra = 2;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;
        cCuerpoServicioPortuario = "Adjunto al presente encontrara el oficio 115.364.2003 (00944) de fecha 30 de enero de 2003,"+
                " en el cual se establecen los requisitos que deberá cumplir para efectuar el trámite  de registro de tarifa de servicios portuarios.";

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroNoProcedeManiobras(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "No procede el registro de la tarifa aplicable al ";
        int lAutorizacion = 0;
        int iNumReporte = 46;
        int lManiobra = 1;
        int lServicioPortuario = 1;
        int lAcuerdo = 0;
        cCuerpoServicioPortuario = "Adjunto al presente encontrara el oficio 115.364.2003 (00944) de fecha 30 de enero de 2003,"+
                " en el cual se establecen los requisitos que deberá cumplir para efectuar el trámite  de registro de tarifa de servicios portuarios.";

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizaInfoComSConexo(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SOLICITUD DE INFORMACIÓN COMPLEMENTARIA CORRESPONDIENTE AL ";
        int lAutorizacion = 1;
        int iNumReporte = 27;
        int lManiobra = 2;
        int lServicioPortuario = 0;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroInfoComSConexo(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SOLICITUD DE INFORMACIÓN COMPLEMENTARIA CORRESPONDIENTE AL ";
        int lAutorizacion = 0;
        int iNumReporte = 28;
        int lManiobra = 2;
        int lServicioPortuario = 0;
        int lAcuerdo = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionNoProcedeSConexo(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "No procede la autorización de la tarifa aplicable al ";
        int lAutorizacion = 1;
        int iNumReporte = 29;
        int lManiobra = 2;
        int lServicioPortuario = 0;
        int lAcuerdo = 0;
        cCuerpoServicioPortuario = "  ";


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroNoProcedeSConexo(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "No procede el registro de la tarifa aplicable al ";
        int lAutorizacion = 0;
        int iNumReporte = 30;
        int lManiobra = 2;
        int lServicioPortuario = 0;
        int lAcuerdo = 0;
        cCuerpoServicioPortuario = "  ";

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistra(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte,lManiobra,lServicioPortuario,lAcuerdo);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizaRegistra(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto, String cAsuntoR, int lAutoriza,
            int iReporte, int lManiobra, int lServicioPortuario, int lAcuerdo) throws Exception{
        Vector vRegs = new Vector();
        Vector vRegs2 = new Vector();
        Vector vRegs3 = new Vector();
        Vector vRegs4 = new Vector();
        String aFiltro[] = cFiltro.split(",");

        int iEjercicio = Integer.parseInt(aFiltro[3]);
        int iNumSolicitud = Integer.parseInt(aFiltro[4]);
        int iNumTarifa = Integer.parseInt(aFiltro[5]);
        int iNumTituloContrato = Integer.parseInt(aFiltro[6]);
        int iCveContratoTitulo = 0;
        int lContrato = 0;
        if(aFiltro.length >= 6){
            iCveContratoTitulo = Integer.parseInt(aFiltro[7]);//Clave de Título o de Contrato segun la bandera de abajo
            lContrato = Integer.parseInt(aFiltro[8]);// 1 --> Es contrato.  0 --> No es Contrato
        }


        int iCveSolicitante=0, iCveRepLegal = 0, iCveDomicilio = 0;
        java.sql.Date dtFechaOfSol = null;
        String cRef = "", cInt = "", cServiciosTemp ="", cServiciosPto = "", cFecha="",
                cCapitania = "", cDireccionCapitania = "";
        String cAsunto = cAsuntoR;
        int lAutorizacion = lAutoriza;
        int iNumReporte = iReporte;
        TWord rep = new TWord();
        TFechas tFecha = new TFechas();
        TDDPOPMDP dPMDP = new TDDPOPMDP();
        TDObtenDatos dObten  = new TDObtenDatos();
        TDObtenDatos dObten2 = new TDObtenDatos();
        TVDinRep vData = new TVDinRep();
        TVDinRep vDatos = new TVDinRep();
        TVDinRep vDatosTarifa = new TVDinRep();
        TVDinRep vDatosRecintos = new TVDinRep();
        TVDinRep vDatosPto = new TVDinRep();

        rep.iniciaReporte();

        try{
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ");
            sb.append("ICVESOLICITANTE, ");
            sb.append("ICVEREPLEGAL, ");
            sb.append("ICVEDOMICILIOSOLICITANTE ");
            sb.append("FROM TRAREGSOLICITUD ");
            sb.append("where IEJERCICIO = "+iEjercicio);
            sb.append(" and INUMSOLICITUD = "+iNumSolicitud);

            vRegs = GenSQLGenerico(vRegs,sb.toString());
            if(vRegs.size() > 0){
                vDatos = (TVDinRep) vRegs.get(0);
                iCveSolicitante = vDatos.getInt("ICVESOLICITANTE");
                iCveRepLegal = vDatos.getInt("ICVEREPLEGAL");
                iCveDomicilio = vDatos.getInt("ICVEDOMICILIOSOLICITANTE");
            }

            vRegs2 = getDatosTarifa(iNumTarifa);
            if(vRegs2.size() > 0){
                vDatosTarifa = (TVDinRep) vRegs2.get(0);
                cInt = vDatosTarifa.getString("CNUMENTRADAOFICIALIA");
                cRef= vDatosTarifa.getString("CNUMOFICIOSOLTARIFA");
                dtFechaOfSol = vDatosTarifa.getDate("DTOFICIOSOLTARIFA");
                cCapitania = vDatosTarifa.getString("CDSCOFICINA");
                cDireccionCapitania = vDatosTarifa.getString("CDOMICILIO");
            }
            /*Para ver si es servicio portuario o conexo*/
            if(lServicioPortuario == 1){
                StringBuffer sb3 = new StringBuffer();
                sb3.append("SELECT ");
                sb3.append("DISTINCT(TAR.ICVEPUERTO) AS IPUERTO, ");
                sb3.append("PTO.CDSCPUERTO as cPuerto, ");
                sb3.append("ENT.CABREVIATURA as cEntidadAbrev ");
                sb3.append("FROM TARTARIFASERVICIOS TAR ");
                sb3.append("JOIN CPASERVICIOPORTUARIO SERPOR ON TAR.ICVESERVICIOPORTUARIO = SERPOR.ICVESERVICIOPORTUARIO ");
                sb3.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
                sb3.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
                sb3.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
                sb3.append("where TAR.iNumTarifa = " + iNumTarifa);
                sb3.append(" and TAR.iNumTituloContrato = " + iNumTituloContrato);
                sb3.append(" and TAR.lServicioPortuario = 1 ");
                sb3.append("and TAR.lAutorizacion = " + lAutorizacion);
                if(lManiobra == 0)
                    sb3.append(" and SERPOR.ICVETIPOSERVICIOPORTUARIO <> 3 ");
                else if (lManiobra == 1)
                    sb3.append(" and SERPOR.ICVETIPOSERVICIOPORTUARIO = 3 ");
                sb3.append(" Order by cPuerto ");

                vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());

                for(int i = 0;i < vRegs3.size();i++){
                    cServiciosTemp = "";
                    vDatosPto = (TVDinRep) vRegs3.get(i);

                    StringBuffer sb4 = new StringBuffer();

                    sb4.append("SELECT ");
                    sb4.append("TAR.ICVESERVICIOPORTUARIO as iCveServicioPort, ");
                    sb4.append("SER.CDSCSERVICIOPORTUARIO as cServicioPortuario ");
                    sb4.append("FROM TARTARIFASERVICIOS TAR ");
                    sb4.append("JOIN CPASERVICIOPORTUARIO SER ON TAR.ICVESERVICIOPORTUARIO = SER.ICVESERVICIOPORTUARIO ");
                    sb4.append("WHERE TAR.INUMTARIFA = " + iNumTarifa);
                    sb4.append(" AND TAR.INUMTITULOCONTRATO = " + iNumTituloContrato);
                    sb4.append(" and TAR.lServicioPortuario = 1 ");
                    sb4.append("and TAR.lAutorizacion = " + lAutorizacion);
                    if(lManiobra == 0)
                        sb4.append(" and SER.ICVETIPOSERVICIOPORTUARIO <> 3 ");
                    else if (lManiobra == 1)
                        sb4.append(" and SER.ICVETIPOSERVICIOPORTUARIO = 3 ");
                    sb4.append(" AND TAR.ICVEPUERTO = " + vDatosPto.getInt("IPUERTO"));
                    sb4.append(" ORDER BY cServicioPortuario ");

                    vRegs4 = GenSQLGenerico(vRegs4,sb4.toString());

                    for(int j = 0;j < vRegs4.size();j++){
                        vDatosRecintos = (TVDinRep) vRegs4.get(j);
                        if(cServiciosTemp.equalsIgnoreCase(""))
                            cServiciosTemp = vDatosRecintos.getString("cServicioPortuario");
                        else
                            cServiciosTemp += ", " +
                                    vDatosRecintos.getString("cServicioPortuario");
                    }
                    if(cServiciosPto.equalsIgnoreCase(""))
                        cServiciosPto = "SERVICIO PORTUARIO DE " + cServiciosTemp +
                                " QUE PROPORCIONA EN " + vDatosPto.getString("cPuerto") + ", " +
                                vDatosPto.getString("cEntidadAbrev");
                    else
                        cServiciosPto += "; " + cServiciosTemp + " QUE PROPORCIONA EN " +
                                vDatosPto.getString("cPuerto") + ", " +
                                vDatosPto.getString("cEntidadAbrev");
                }
            }else if(lServicioPortuario == 0){
                StringBuffer sb3 = new StringBuffer();
                sb3.append("SELECT ");
                sb3.append("DISTINCT(TAR.ICVEPUERTO) AS IPUERTO, ");
                sb3.append("PTO.CDSCPUERTO as cPuerto, ");
                sb3.append("ENT.CABREVIATURA as cEntidadAbrev ");
                sb3.append("FROM TARTARIFASERVICIOS TAR ");
                sb3.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
                sb3.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
                sb3.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
                sb3.append("where TAR.iNumTarifa = " + iNumTarifa);
                sb3.append(" and TAR.iNumTituloContrato = " + iNumTituloContrato);
                sb3.append(" and TAR.lServicioPortuario = 0 ");
                sb3.append("and TAR.lAutorizacion = " + lAutorizacion);
                sb3.append(" Order by cPuerto ");

                vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());

                for(int i = 0;i < vRegs3.size();i++){
                    cServiciosTemp = "";
                    vDatosPto = (TVDinRep) vRegs3.get(i);

                    StringBuffer sb4 = new StringBuffer();

                    sb4.append("SELECT ");
                    sb4.append("TAR.ICVESERVICIOCONEXO as iCveServicioCon, ");
                    sb4.append("SER.CDSCSERVCONEXO as cServicioCon ");
                    sb4.append("FROM TARTARIFASERVICIOS TAR ");
                    sb4.append("JOIN TARSERVICIOCONEXO SER ON TAR.ICVESERVICIOCONEXO = SER.ICVESERVICIOCONEXO ");
                    sb4.append("where TAR.iNumTarifa = "+iNumTarifa);
                    sb4.append(" AND TAR.INUMTITULOCONTRATO = " + iNumTituloContrato);
                    sb4.append(" and TAR.lServicioPortuario = 0 ");
                    sb4.append("and TAR.lAutorizacion = " + lAutorizacion);
                    sb4.append(" AND TAR.ICVEPUERTO = " + vDatosPto.getInt("IPUERTO"));
                    sb4.append(" ORDER BY cServicioCon ");

                    vRegs4 = GenSQLGenerico(vRegs4,sb4.toString());

                    for(int j = 0;j < vRegs4.size();j++){
                        vDatosRecintos = (TVDinRep) vRegs4.get(j);
                        if(cServiciosTemp.equalsIgnoreCase(""))
                            cServiciosTemp = vDatosRecintos.getString("cServicioCon");
                        else
                            cServiciosTemp += ", " +
                                    vDatosRecintos.getString("cServicioCon");
                    }
                    if(cServiciosPto.equalsIgnoreCase(""))
                        cServiciosPto = "SERVICIO CONEXO DE " + cServiciosTemp +
                                " QUE PROPORCIONA EN " + vDatosPto.getString("cPuerto") + ", " +
                                vDatosPto.getString("cEntidadAbrev");
                    else
                        cServiciosPto += "; " + cServiciosTemp + " QUE PROPORCIONA EN " +
                                vDatosPto.getString("cPuerto") + ", " +
                                vDatosPto.getString("cEntidadAbrev");
                }
            }

            if(lAcuerdo == 0){
                String cParrafoContrato = "en las oficinas de esa empresa";
                String cCopiaContrato = "  ";
                String cTitularContrato =  " ",cTitularContrato1aVez =  "[cTitularContrato1aVez]", cFechaContrato = "[cFechaContrato]",
                        cNumContrato = "[cNumContrato]", cParrafoContrato1aVez = "en las oficinas del prestador de servicio";
                //Para ver los contratos
                if(lContrato > 0){
                    Vector vRegsTitCon = new Vector();
                    TVDinRep vDatosTitCon = new TVDinRep();
                    vRegsTitCon = getDatosTitularContrato(iCveContratoTitulo);
                    if(vRegsTitCon.size() > 0){
                        vDatosTitCon = (TVDinRep) vRegsTitCon.get(0);
                        dObten.dPersona.setPersona(iCveSolicitante,0);
                        cParrafoContrato = "tanto en las oficinas del prestador de servicio " +dObten.dPersona.getNomCompleto() +
                                " como en los de la " + vDatosTitCon.getString("CTITULAR");
                        cCopiaContrato = "C.  Director General de la "+ vDatosTitCon.getString("CTITULAR") +
                                ".- " + vDatosTitCon.getString("CDOMICILIO") + "- Presente";


                        cTitularContrato = " que celebro con la "+ vDatosTitCon.getString("CTITULAR");
                        cTitularContrato1aVez = vDatosTitCon.getString("CTITULAR");
                        cFechaContrato = vDatosTitCon.getString("DTREGISTRO");
                        cNumContrato = vDatosTitCon.getString("CNUMCONTRATO");
                        cParrafoContrato1aVez = "tanto en las oficinas del prestador de servicios "+dObten.dPersona.getNomCompleto() +
                                " como en las de la " + vDatosTitCon.getString("CTITULAR");

                    }else{
                        cParrafoContrato =
                                "tanto en las oficinas del prestador de servicio [cNombreEmpresaDest] " +
                                "como en los de la API";
                        cCopiaContrato = "C.  Director General de la API .- Presente";
                    }
                    //Para los Títulos de Concesión o de Permisos
                }else{
                    Vector vRegsTitTitulo = new Vector();
                    TVDinRep vDatosTitCon = new TVDinRep();
                    vRegsTitTitulo = this.getDatosTitularTitulo(iCveContratoTitulo);
                    if(vRegsTitTitulo.size() > 0){
                        vDatosTitCon = (TVDinRep) vRegsTitTitulo.get(0);
                        if(vDatosTitCon.getInt("ICVETIPOTITULO") == 1)
                            rep.comRemplaza("[cTitular]",vDatosTitCon.getString("CTITULAR"));
                        else
                            rep.comRemplaza("[cFechaPermiso]",vDatosTitCon.getString("DTFECHAINI"));
                    }
                }

                String cAsuntoTemp = "";
                cAsuntoTemp = cAsunto + " " + cServiciosPto;

                if(cAsuntoTemp.length() > 199)
                    cAsuntoTemp = cAsuntoTemp.substring(0,199);

                vData.put("iEjercicioSolicitud",iEjercicio);
                vData.put("iNumSolicitud",iNumSolicitud);
                vData.put("iCveSistema",11);
                vData.put("iCveModulo",7);
                vData.put("iNumReporte",iNumReporte);
                vData.put("cFolio",cFolio);
                vData.put("cAsunto",cAsuntoTemp);

                dPMDP.insertReportexFolio(vData, conn);

                rep.comRemplaza("[cFechaOfSolicitud]",
                        tFecha.getFechaDDMMMMMYYYY(dtFechaOfSol," de "));
                rep.comRemplaza("[cServicioPortuario]",cServiciosPto);
                rep.comRemplaza("[cAsuntoLargo]",cAsunto + " " + cServiciosPto);
                rep.comRemplaza("[cParrafoContrato]",cParrafoContrato);
                rep.comRemplaza("[cTitularContrato]",cTitularContrato);
                rep.comRemplaza("[cCapitania]",cCapitania);
                rep.comRemplaza("[cDireccionCapitania]",cDireccionCapitania);
                rep.comRemplaza("[cCopiaContrato]",cCopiaContrato);
                rep.comRemplaza("[cTitularContrato1aVez]",cTitularContrato1aVez);
                rep.comRemplaza("[cFechaContrato]",cFechaContrato);
                rep.comRemplaza("[cNumContrato]",cNumContrato);
                rep.comRemplaza("[cParrafoContrato1aVez]",cParrafoContrato1aVez);

                if(!cCuerpoServicioPortuario.equalsIgnoreCase(null) && !cCuerpoServicioPortuario.equalsIgnoreCase("null")
                && !cCuerpoServicioPortuario.equalsIgnoreCase(""))
                    rep.comRemplaza("[cCuerpoServicioPortuario]",cCuerpoServicioPortuario);

                return new TDGeneral().generaOficioWord(cFolio,
                        Integer.parseInt(cCveOfic,10),
                        Integer.parseInt(cCveDepto,10),
                        0,0,
                        iCveSolicitante,iCveDomicilio,
                        iCveRepLegal,
                        "","","REF: " + cRef,
                        "INT: " + cInt,
                        false,"",new Vector(),
                        false,new Vector(),
                        rep.getEtiquetasBuscar(),
                        rep.getEtiquetasRemplazar());
            }else{
                int iCveDeptoTarifa = Integer.parseInt(VParametros.getPropEspecifica("DireccionTarifas"));
                int iCveOficina = Integer.parseInt(VParametros.getPropEspecifica("OficinaCentral"));
                int iDifAnios = 0;
                String cRemitente = "", cPuestoRem = "", cTipoContrato = " ";
                Vector vRegsDestinatario = new Vector();
                TVDinRep vDatosDestinatario = new TVDinRep();
                dObten.dPersona.setPersona(iCveSolicitante,0);
                dObten2.dPersona.setPersona(iCveRepLegal,0);

                StringBuffer sb5 = new StringBuffer();

                sb5.append("SELECT ");
                sb5.append("CTITULAR ");
                sb5.append("FROM GRLDEPTOXOFIC ");
                sb5.append("where ICVEOFICINA = "+iCveOficina);
                sb5.append(" and ICVEDEPARTAMENTO = "+iCveDeptoTarifa);

                vRegsDestinatario = GenSQLGenerico(vRegsDestinatario,sb5.toString());
                if(vRegsDestinatario.size() > 0){
                    vDatosDestinatario = (TVDinRep) vRegsDestinatario.get(0);
                    cRemitente = vDatosDestinatario.getString("CTITULAR");
                }
                //Para Acuerdos que sean de Contratos
                if(lContrato > 0){
                    Vector vRegsTitCon = new Vector();
                    TVDinRep vDatosTitCon = new TVDinRep();
                    vRegsTitCon = getDatosTitularContrato(iCveContratoTitulo);
                    if(vRegsTitCon.size() > 0){
                        vDatosTitCon = (TVDinRep) vRegsTitCon.get(0);
                        if(vDatosTitCon.getInt("ICVETIPOCONTRATO") == 1)
                            cTipoContrato = "de cesión parcial de derechos";

                        rep.comRemplaza("[cFechaContratoCesion]",tFecha.getFechaDDMMMMMYYYY(vDatosTitCon.getDate("DTCONTRATO")," de "));
                        rep.comRemplaza("[cTitularConcesionario]",vDatosTitCon.getString("CTITULAR"));
                        rep.comRemplaza("[cNumContratoCesion]",vDatosTitCon.getString("CNUMCONTRATO"));
                        rep.comRemplaza("[cFechaRegistroCesion]",vDatosTitCon.getString("DTREGISTRO"));

                        iDifAnios = tFecha.getIntYear(vDatosTitCon.getDate("DTINIVIGENCIA")) -
                                tFecha.getIntYear(vDatosTitCon.getDate("DTVENCIMIENTO"));
                        rep.comRemplaza("[cNumAnios]","" + iDifAnios);
                    }
                    //Para Acuerdos que sean Títulos de Concesión o de Permisos
                }else{
                    Vector vRegsTitTitulo = new Vector();
                    TVDinRep vDatosTitCon = new TVDinRep();
                    vRegsTitTitulo = this.getDatosTitularTitulo(iCveContratoTitulo);
                    if(vRegsTitTitulo.size() > 0){
                        vDatosTitCon = (TVDinRep) vRegsTitTitulo.get(0);
                        if(vDatosTitCon.getInt("ICVETIPOTITULO") == 1){
                            rep.comRemplaza("[cFechaConcesion]",tFecha.getFechaDDMMMMMYYYY(vDatosTitCon.getDate("DTFECHAINI")," de "));
                            rep.comRemplaza("[cTitular]",vDatosTitCon.getString("CTITULAR"));
                        }else{
                            rep.comRemplaza("[cFechaPermisos]",vDatosTitCon.getString("DTFECHAINI"));
                            iDifAnios = tFecha.getIntYear(vDatosTitCon.getDate("DTFECHAINI")) -
                                    tFecha.getIntYear(vDatosTitCon.getDate("DTVIGENCIATITULO"));
                            rep.comRemplaza("[cNumAniosPermiso]","" + iDifAnios);
                        }
                    }
                }
                Vector vRegsDatosOpn = new Vector();
                TVDinRep vDatosOpn = new TVDinRep();
                StringBuffer sbOpn = new StringBuffer();
                String cFechaOpinion = " ";

                sbOpn.append("SELECT ");
                sbOpn.append("TOE.ICVESEGTOENTIDAD as ICVESEGTOENTIDAD, ");
                sbOpn.append("FXS.ICONSECUTIVOSEGTOREF AS ICONSECUTIVOSEGTOREF, ");
                sbOpn.append("FOL.DTASIGNACION AS DTASIGNACION ");
                sbOpn.append("FROM TRAOPNENTTRAMITE TOE ");
                sbOpn.append("JOIN GRLSEGTOENTIDAD SEG ON TOE.ICVESEGTOENTIDAD = SEG.ICVESEGTOENTIDAD ");
                sbOpn.append("AND SEG.LESCONTESTACION = 0 ");
                sbOpn.append("JOIN GRLFOLIOXSEGTOENT FXS ON SEG.ICVESEGTOENTIDAD = FXS.ICVESEGTOENTIDAD ");
                sbOpn.append("AND FXS.IEJERCICIOFOLIO IS NOT NULL ");
                sbOpn.append("JOIN GRLFOLIO FOL ON FXS.IEJERCICIOFOLIO = FOL.IEJERCICIO ");
                sbOpn.append("AND FXS.ICVEOFICINA = FOL.ICVEOFICINA ");
                sbOpn.append("AND FXS.ICVEDEPARTAMENTO = FOL.ICVEDEPARTAMENTO ");
                sbOpn.append("AND FXS.CDIGITOSFOLIO = FOL.CDIGITOSFOLIO ");
                sbOpn.append("AND FXS.ICONSECUTIVO = FOL.ICONSECUTIVO ");
                sbOpn.append("where TOE.IEJERCICIOSOLICITUD = "+iEjercicio);
                sbOpn.append(" and TOE.INUMSOLICITUD = "+iNumSolicitud);
                sbOpn.append(" ORDER BY ICVESEGTOENTIDAD DESC,ICONSECUTIVOSEGTOREF DESC ");

                vRegsDatosOpn = GenSQLGenerico(vRegsDatosOpn,sbOpn.toString());
                if(vRegsDatosOpn.size() > 0){
                    vDatosOpn = (TVDinRep) vRegsDatosOpn.get(0);
                    cFechaOpinion = (!vDatosOpn.getString("DTASIGNACION").equalsIgnoreCase(null) &&
                            !vDatosOpn.getString("DTASIGNACION").equalsIgnoreCase("null"))?
                                tFecha.getFechaDDMMMMMYYYY(vDatosOpn.getDate("DTASIGNACION")," de "):" ";
                }

                StringBuffer sbOpnPer = new StringBuffer();
                TVDinRep vDatosOpnPer = new TVDinRep();
                Vector vRegsDatosOpnPer = new Vector();
                String cNombreOpinion = "";

                sbOpnPer.append("SELECT ");
                sbOpnPer.append("TOE.ICVEOPINIONENTIDAD AS ICVEOPINIONENTIDAD, ");
                sbOpnPer.append("OPE.ICVEPERSONA AS ICVEPERSONA ");
                sbOpnPer.append("FROM TRAOPNENTTRAMITE TOE ");
                sbOpnPer.append("JOIN GRLOPINIONENTIDAD OPE ON TOE.ICVEOPINIONENTIDAD = OPE.ICVEOPINIONENTIDAD ");
                sbOpnPer.append("where TOE.IEJERCICIOSOLICITUD = "+iEjercicio);
                sbOpnPer.append(" and TOE.INUMSOLICITUD = "+iNumSolicitud);
                sbOpnPer.append(" AND ICVEPERSONA IS NOT NULL ");
                sbOpnPer.append("ORDER BY ICVEOPINIONENTIDAD DESC ");

                vRegsDatosOpnPer = GenSQLGenerico(vRegsDatosOpnPer,sbOpnPer.toString());
                if(vRegsDatosOpnPer.size() > 0){
                    vDatosOpnPer = (TVDinRep) vRegsDatosOpnPer.get(0);
                    dObten.dPersona.setPersona(vDatosOpnPer.getInt("ICVEPERSONA"),0);
                    cNombreOpinion = dObten.dPersona.getNomCompleto();
                }

                rep.comRemplaza("[cTipoContrato]",cTipoContrato);
                rep.comRemplaza("[cFechaOpinion]",cFechaOpinion);
                rep.comRemplaza("[cNombreOpinion]",cNombreOpinion);
                rep.comRemplaza("[cFecha]",tFecha.getFechaDDMMMMMYYYY(tFecha.TodaySQL()," de "));
                rep.comRemplaza("[cFechaOfSolicitud]",
                        tFecha.getFechaDDMMMMMYYYY(dtFechaOfSol," de "));
                rep.comRemplaza("[cFolioOfPartes]",cRef);
                rep.comRemplaza("[cTipoAutorizacion]",cTipoAutorizacion);
                rep.comRemplaza("[cUltimoTipoAutorizacion]",(lAutorizacion == 1)?"la última autorización":"el último registro");
                rep.comRemplaza("[cServicioPortuario]",cServiciosPto);
                rep.comRemplaza("[cNombrePersonaDest]",dObten2.dPersona.getNomCompleto());
                rep.comRemplaza("[cPuestoDest]","REPRESENTANTE LEGAL DE");
                rep.comRemplaza("[cNombreEmpresaDest]",dObten.dPersona.getNomCompleto());
                rep.comRemplaza("[cNombreRemitente]",(!cRemitente.equalsIgnoreCase(null) && !cRemitente.equalsIgnoreCase("null") &&
                        !cRemitente.equalsIgnoreCase(""))?cRemitente:" -- ");
            }

        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getVectorDatos(true);

    }

    /*Método para generar Oficio de Análisis de Sensibilidad de Tarifas.*/
    public StringBuffer GenAnalisisSensibilidad(String cFiltro) throws Exception{
        Vector vRegs  = new Vector();
        Vector vRegs2 = new Vector();
        Vector vRegs3 = new Vector();
        String aFiltro[] = cFiltro.split(",");

        String cEjercicio      = aFiltro[0];
        String cNumSolicitud   = aFiltro[1];
        String cNumTarifa      = aFiltro[2];

        int iCveOficina = Integer.parseInt(VParametros.getPropEspecifica("OficinaCentral"));
        int iCveDeptoPuerto = Integer.parseInt(VParametros.getPropEspecifica("DireccionGeneralPuertos"));
        int iCveDeptoTarifa = Integer.parseInt(VParametros.getPropEspecifica("DireccionTarifas"));
        String cFecha = "";
        String cCuerpo = "";
        String cReviso = "", cAprobo = "", cDestinatario = "";

        TWord rep = new TWord();
        TVDinRep vDatos            = new TVDinRep();
        TVDinRep vDatosReviso      = new TVDinRep();
        TVDinRep vDatosComentarios = new TVDinRep();
        rep.iniciaReporte();

        try{
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ");
            sb.append("CTITULAR ");
            sb.append("FROM GRLDEPTOXOFIC ");
            sb.append("where ICVEOFICINA = "+iCveOficina);
            sb.append(" and ICVEDEPARTAMENTO = "+iCveDeptoPuerto);

            vRegs = GenSQLGenerico(vRegs,sb.toString());
            if(vRegs.size() > 0){
                vDatos = (TVDinRep) vRegs.get(0);
                cDestinatario = vDatos.getString("CTITULAR");
                cAprobo =  vDatos.getString("CTITULAR");
            }

            StringBuffer sb2 = new StringBuffer();
            sb2.append("SELECT ");
            sb2.append("CTITULAR ");
            sb2.append("FROM GRLDEPTOXOFIC ");
            sb2.append("where ICVEOFICINA = "+iCveOficina);
            sb2.append(" and ICVEDEPARTAMENTO = "+iCveDeptoTarifa);

            vRegs2 = GenSQLGenerico(vRegs,sb2.toString());
            if(vRegs2.size() > 0){
                vDatosReviso = (TVDinRep) vRegs2.get(0);
                cReviso = vDatosReviso.getString("CTITULAR");
            }

            StringBuffer sb3 = new StringBuffer();
            sb3.append("SELECT ");
            sb3.append("DTANALISIS, COBSREGISTRADA ");
            sb3.append("FROM TARANSENSIBILIDAD ");
            sb3.append("JOIN GRLOBSERVACION ON ");
            sb3.append("GRLOBSERVACION.IEJERCICIO = TARANSENSIBILIDAD.IEJERCICIO AND ");
            sb3.append("GRLOBSERVACION.ICVEOBSERVACION = TARANSENSIBILIDAD.ICVEOBSERVACION ");
            sb3.append("WHERE TARANSENSIBILIDAD.INUMTARIFA = "+cNumTarifa);

            vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());
            if(vRegs3.size() > 0){
                vDatosComentarios = (TVDinRep) vRegs3.get(0);
                cFecha  = vDatosComentarios.getString("DTANALISIS");
                cCuerpo = vDatosComentarios.getString("COBSREGISTRADA");
            }

            rep.comRemplaza("[cEjSolicitud]",cEjercicio+"-"+cNumSolicitud);
            rep.comRemplaza("[cFecha]",cFecha);
            rep.comRemplaza("[cDestinatario]",cDestinatario);
            rep.comRemplaza("[cCuerpo]",cCuerpo);
            rep.comRemplaza("[cReviso]",cReviso);
            rep.comRemplaza("[cAutorizo]",cAprobo);

        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getEtiquetas(true);

    }
    /*Método encargado de generar oficio de Encuesta de Satisfacción*/
    public Vector GenEncuestaSatisfaccion(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vRegs = new Vector();
        String aFiltro[] = cFiltro.split(",");

        int iEjercicio = Integer.parseInt(aFiltro[3]);
        int iNumSolicitud = Integer.parseInt(aFiltro[4]);
        int iCveSolicitante=0, iCveRepLegal=0, iCveDomicilio=0;
        String cFoliosFiltro = aFiltro[1];
        String cFechasFiltro = aFiltro[2];
        String aFoliosFiltro[] = cFoliosFiltro.replace('|',',').split(",");
        String aFechasFiltro[] = cFechasFiltro.replace('|',',').split(",");
        String cOficiosFechas = "";
        String cFecha = "";
        TWord rep = new TWord();
        TFechas tFecha = new TFechas();
        TDDPOPMDP dPMDP = new TDDPOPMDP();
        TVDinRep vDatos = new TVDinRep();
        TVDinRep vData = new TVDinRep();
        rep.iniciaReporte();

        try{
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ");
            sb.append("ICVESOLICITANTE, ");
            sb.append("ICVEREPLEGAL, ");
            sb.append("ICVEDOMICILIOSOLICITANTE ");
            sb.append("FROM TRAREGSOLICITUD ");
            sb.append("where IEJERCICIO = "+iEjercicio);
            sb.append(" and INUMSOLICITUD = "+iNumSolicitud);


            vRegs = GenSQLGenerico(vRegs,sb.toString());
            if(vRegs.size() > 0){
                vDatos = (TVDinRep) vRegs.get(0);
                iCveSolicitante = vDatos.getInt("ICVESOLICITANTE");
                iCveRepLegal = vDatos.getInt("ICVEREPLEGAL");
                iCveDomicilio = vDatos.getInt("ICVEDOMICILIOSOLICITANTE");
            }

            /*Para oficios refrenciados y fechas seleccionados*/
            for (int i = 0; i < aFoliosFiltro.length; i++) {
                if(!aFechasFiltro[i].equalsIgnoreCase(null) && !aFechasFiltro[i].equalsIgnoreCase("")){
                    if(tFecha.getIntYear(tFecha.TodaySQL()) !=
                            tFecha.getIntYear(tFecha.getDateSQL(aFechasFiltro[i])))
                        cFecha = tFecha.getFechaDDMMMMMYYYY(tFecha.getDateSQL(aFechasFiltro[
                                i])," de ");
                    else
                        cFecha = tFecha.getStringDay(tFecha.getDateSQL(aFechasFiltro[i])) +
                                " de " + tFecha.getMonthName(tFecha.getDateSQL(aFechasFiltro[i])) +
                                " del año en curso";
                }
                if(!aFoliosFiltro[i].equalsIgnoreCase(null) && !aFoliosFiltro[i].equalsIgnoreCase("")){
                    if(cOficiosFechas.equalsIgnoreCase(""))
                        cOficiosFechas = aFoliosFiltro[i] + " de fecha " + cFecha;
                    else
                        cOficiosFechas += "; " + aFoliosFiltro[i] + " de fecha " + cFecha;
                }
            }

            rep.comRemplaza("[cNumOficiosYcFechas]",cOficiosFechas);

            vData.put("iEjercicioSolicitud",iEjercicio);
            vData.put("iNumSolicitud",iNumSolicitud);
            vData.put("iCveSistema",11);
            vData.put("iCveModulo",7);
            vData.put("iNumReporte",10);
            vData.put("cFolio",cFolio);
            vData.put("cAsunto","ENCUESTA DE SATISFACIÓN");

            dPMDP.insertReportexFolio(vData, conn);


            return new TDGeneral().generaOficioWord(cFolio,
                    Integer.parseInt(cCveOfic,10),
                    Integer.parseInt(cCveDepto,10),
                    0,0,
                    iCveSolicitante,iCveDomicilio,iCveRepLegal,
                    "","","","",
                    false,"",new Vector(),
                    false,new Vector(),
                    rep.getEtiquetasBuscar(),
                    rep.getEtiquetasRemplazar());



        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getVectorDatos(true);

    }
    /*Métodos para ejecutar el Oficio de Caratula de Fax*/
    public Vector GenCaratulaFaxAutorizacion(String cFiltro) throws Exception{

        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        cTipoAutorizacion = "autoriza";
        try{
            rep.iniciaReporte();
            vDatosRegresa = GenCaratulaFax(cFiltro,cTipoAutorizacion);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenCaratulaFaxRegistro(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        cTipoAutorizacion = "registra";

        try{
            rep.iniciaReporte();
            vDatosRegresa = GenCaratulaFax(cFiltro,cTipoAutorizacion);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }


    public Vector GenCaratulaFax(String cFiltro, String cTipoAutor) throws Exception{
        Vector vRegs = new Vector();
        Vector vRegs2 = new Vector();
        Vector vRegs3 = new Vector();
        Vector vRegs4 = new Vector();
        Vector vRegs5 = new Vector();
        Vector vRegs6 = new Vector();

        String aFiltro[] = cFiltro.split(",");

        int iEjercicio = Integer.parseInt(aFiltro[3]);
        int iNumSolicitud = Integer.parseInt(aFiltro[4]);
        int iNumTarifa = Integer.parseInt(aFiltro[5]);
        int iNumTituloContrato = Integer.parseInt(aFiltro[6]);
        int iCveSolicitante=0, iCveRepLegal=0;
        int iCveOficina = Integer.parseInt(VParametros.getPropEspecifica("OficinaCentral"));
        int iCveDeptoTarifa = Integer.parseInt(VParametros.getPropEspecifica("DireccionTarifas"));

        String cFoliosFiltro = aFiltro[1];
        String cFechasFiltro = aFiltro[2];

        String aFoliosFiltro[] = cFoliosFiltro.replace('|',',').split(",");
        String aFechasFiltro[] = cFechasFiltro.replace('|',',').split(",");
        String cOficiosFechas = "";
        String cFecha;
        String cTelefono= "";
        String cDestinatario = "",cPuestoTitular = "";
        String cServiciosPto = "", cServiciosTemp = "",cServiciosPtoCon="";
        TWord rep = new TWord();
        TDObtenDatos dObten  = new TDObtenDatos();
        TDObtenDatos dObten2 = new TDObtenDatos();
        TFechas tFecha = new TFechas();
        TVDinRep vDatos = new TVDinRep();
        TVDinRep vDatosTit = new TVDinRep();
        TVDinRep vDatosPto = new TVDinRep();
        TVDinRep vDatosRecintos = new TVDinRep();
        rep.iniciaReporte();

        try{
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ");
            sb.append("ICVESOLICITANTE, ");
            sb.append("ICVEREPLEGAL, ");
            sb.append("CTELEFONO ");
            sb.append("FROM TRAREGSOLICITUD ");
            sb.append("join GRLDOMICILIO on GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE ");
            sb.append("and GRLDOMICILIO.ICVEDOMICILIO = TRAREGSOLICITUD.ICVEDOMICILIOSOLICITANTE ");
            sb.append("where IEJERCICIO = "+iEjercicio);
            sb.append(" and INUMSOLICITUD = "+iNumSolicitud);

            vRegs = GenSQLGenerico(vRegs,sb.toString());
            if(vRegs.size() > 0){
                vDatos = (TVDinRep) vRegs.get(0);
                iCveSolicitante = vDatos.getInt("ICVESOLICITANTE");
                iCveRepLegal = vDatos.getInt("ICVEREPLEGAL");
                cTelefono = vDatos.getString("CTELEFONO");
            }

            StringBuffer sb2 = new StringBuffer();

            sb2.append("SELECT ");
            sb2.append("CTITULAR, ");
            sb2.append("CPUESTOTITULAR ");
            sb2.append("FROM GRLDEPTOXOFIC ");
            sb2.append("where ICVEOFICINA = "+iCveOficina);
            sb2.append(" and ICVEDEPARTAMENTO = "+iCveDeptoTarifa);

            vRegs2 = GenSQLGenerico(vRegs2,sb2.toString());
            if(vRegs2.size() > 0){
                vDatosTit = (TVDinRep) vRegs2.get(0);
                cDestinatario = vDatosTit.getString("CTITULAR");
                cPuestoTitular = vDatosTit.getString("CPUESTOTITULAR");
            }
            StringBuffer sb3 = new StringBuffer();
            sb3.append("SELECT ");
            sb3.append("DISTINCT(TAR.ICVEPUERTO) AS IPUERTO, ");
            sb3.append("PTO.CDSCPUERTO as cPuerto, ");
            sb3.append("ENT.CABREVIATURA as cEntidadAbrev ");
            sb3.append("FROM TARTARIFASERVICIOS TAR ");
            sb3.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
            sb3.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
            sb3.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
            sb3.append("where TAR.iNumTarifa = "+iNumTarifa);
            sb3.append(" and TAR.iNumTituloContrato = "+iNumTituloContrato);
            sb3.append(" and TAR.lServicioPortuario = 1 ");
            sb3.append("Order by cPuerto ");

            vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());

            for(int i = 0; i < vRegs3.size(); i++){
                cServiciosTemp = "";
                vDatosPto = (TVDinRep) vRegs3.get(i);

                StringBuffer sb4 = new StringBuffer();

                sb4.append("SELECT ");
                sb4.append("TAR.ICVESERVICIOPORTUARIO as iCveServicioPort, ");
                sb4.append("SER.CDSCSERVICIOPORTUARIO as cServicioPortuario ");
                sb4.append("FROM TARTARIFASERVICIOS TAR ");
                sb4.append("JOIN CPASERVICIOPORTUARIO SER ON TAR.ICVESERVICIOPORTUARIO = SER.ICVESERVICIOPORTUARIO ");
                sb4.append("WHERE TAR.INUMTARIFA = "+iNumTarifa);
                sb4.append(" AND TAR.INUMTITULOCONTRATO = "+iNumTituloContrato);
                sb4.append(" AND TAR.ICVEPUERTO = "+vDatosPto.getInt("IPUERTO"));
                sb4.append(" ORDER BY cServicioPortuario ");

                vRegs4 = GenSQLGenerico(vRegs4,sb4.toString());

                for(int j=0; j < vRegs4.size(); j++){
                    vDatosRecintos = (TVDinRep) vRegs4.get(j);
                    if(cServiciosTemp.equalsIgnoreCase(""))
                        cServiciosTemp = vDatosRecintos.getString("cServicioPortuario");
                    else
                        cServiciosTemp += ", "+vDatosRecintos.getString("cServicioPortuario");
                }
                if(cServiciosPto.equalsIgnoreCase(""))
                    cServiciosPto = "servicio portuario de "+cServiciosTemp + " que proporciona en " + vDatosPto.getString("cPuerto")+", " +vDatosPto.getString("cEntidadAbrev");
                else
                    cServiciosPto += "; "+cServiciosTemp + " que proporciona en " + vDatosPto.getString("cPuerto")+", " +vDatosPto.getString("cEntidadAbrev");
            }
            /*Para los servicios conexos*/

            StringBuffer sb5 = new StringBuffer();
            sb5.append("SELECT ");
            sb5.append("DISTINCT(TAR.ICVEPUERTO) AS IPUERTO, ");
            sb5.append("PTO.CDSCPUERTO as cPuerto, ");
            sb5.append("ENT.CABREVIATURA as cEntidadAbrev ");
            sb5.append("FROM TARTARIFASERVICIOS TAR ");
            sb5.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
            sb5.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
            sb5.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
            sb5.append("where TAR.iNumTarifa = "+iNumTarifa);
            sb5.append(" and TAR.iNumTituloContrato = "+iNumTituloContrato);
            sb5.append(" and TAR.lServicioPortuario = 0 ");
            sb5.append("Order by cPuerto ");

            vRegs5 = GenSQLGenerico(vRegs5,sb5.toString());

            for(int i = 0; i < vRegs5.size(); i++){
                cServiciosTemp = "";
                vDatosPto = (TVDinRep) vRegs5.get(i);

                StringBuffer sb6 = new StringBuffer();

                sb6.append("SELECT ");
                sb6.append("TAR.ICVESERVICIOCONEXO as iCveServicioPort, ");
                sb6.append("SER.CDSCSERVCONEXO as cServicioConexo ");
                sb6.append("FROM TARTARIFASERVICIOS TAR ");
                sb6.append("JOIN TARSERVICIOCONEXO SER ON TAR.ICVESERVICIOCONEXO = SER.ICVESERVICIOCONEXO ");
                sb6.append("WHERE TAR.INUMTARIFA = "+iNumTarifa);
                sb6.append(" AND TAR.INUMTITULOCONTRATO = "+iNumTituloContrato);
                sb6.append(" AND TAR.ICVEPUERTO = "+vDatosPto.getInt("IPUERTO"));
                sb6.append(" ORDER BY cServicioConexo ");


                vRegs6 = GenSQLGenerico(vRegs6,sb6.toString());

                for(int j=0; j < vRegs6.size(); j++){
                    vDatosRecintos = (TVDinRep) vRegs6.get(j);
                    if(cServiciosTemp.equalsIgnoreCase(""))
                        cServiciosTemp = vDatosRecintos.getString("cServicioConexo");
                    else
                        cServiciosTemp += ", "+vDatosRecintos.getString("cServicioConexo");
                }
                if(cServiciosPtoCon.equalsIgnoreCase(""))
                    cServiciosPtoCon = " Así como al servicio conexo de "+cServiciosTemp + " que proporciona en " + vDatosPto.getString("cPuerto")+", " +vDatosPto.getString("cEntidadAbrev");
                else
                    cServiciosPtoCon += "; "+cServiciosTemp + " que proporciona en " + vDatosPto.getString("cPuerto")+", " +vDatosPto.getString("cEntidadAbrev");
            }

            /*Para las fechas*/
            for(int i = 0; i < aFoliosFiltro.length; i++) {

                if(aFoliosFiltro[i] != ""){
                    if(tFecha.getIntYear(tFecha.TodaySQL()) != tFecha.getIntYear(tFecha.getDateSQL(aFechasFiltro[i])))
                        cFecha = tFecha.getFechaDDMMMMMYYYY(tFecha.getDateSQL(aFechasFiltro[i])," de ");
                    else
                        cFecha = tFecha.getStringDay(tFecha.getDateSQL(aFechasFiltro[i])) +" de "+ tFecha.getMonthName(tFecha.getDateSQL(aFechasFiltro[i]))+ " del año en curso";

                    if(cOficiosFechas.equalsIgnoreCase(""))
                        cOficiosFechas = aFoliosFiltro[i] + " fechado el "+cFecha;
                    else
                        cOficiosFechas += "; "+aFoliosFiltro[i] + " fechado el "+cFecha;
                }
            }

            dObten.dPersona.setPersona(iCveSolicitante,0);
            dObten2.dPersona.setPersona(iCveRepLegal,0);

            rep.comRemplaza("[cMunicipioEmision]","México");
            rep.comRemplaza("[cEntidadEmision]","D.F.");
            rep.comRemplaza("[cFechaEmision]",tFecha.getFechaDDMMMMMYYYY(tFecha.TodaySQL()," de "));
            rep.comRemplaza("[cNombrePersonaDest]",dObten2.dPersona.getNomCompleto());
            rep.comRemplaza("[cPuestoDest]","REPRESENTANTE LEGAL DE");
            rep.comRemplaza("[cNombreEmpresaDest]",dObten.dPersona.getNomCompleto());
            rep.comRemplaza("[cTelefono]",cTelefono);
            rep.comRemplaza("[cOficioYFecha]",cOficiosFechas);
            rep.comRemplaza("[cTipoAutorizacion]",cTipoAutor);
            rep.comRemplaza("[cDirectorTarifa]",cDestinatario);
            rep.comRemplaza("[cPuestoDirector]",cPuestoTitular);
            rep.comRemplaza("[cServiciosPortuariosPuertoYEntFedAbr]",cServiciosPto+ " " +cServiciosPtoCon);

        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getVectorDatos(true);

    }
    /*Métofo encargado de generar oficio de Entrega de Oficios*/
    public Vector GenEntregaOficios(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vRegs = new Vector();
        Vector vRegs2 = new Vector();
        Vector vRegs3 = new Vector();
        Vector vRegs4 = new Vector();
        Vector vRegs5 = new Vector();
        Vector vRegs6 = new Vector();
        String aFiltro[] = cFiltro.split(",");

        int iEjercicio = Integer.parseInt(aFiltro[3]);
        int iNumSolicitud = Integer.parseInt(aFiltro[4]);
        int iNumTarifa = Integer.parseInt(aFiltro[5]);
        int iNumTituloContrato = Integer.parseInt(aFiltro[6]);
        int iCveSolicitante=0, iCveRepLegal=0;
        String cOficialiaFiltro = aFiltro[0];
        String cFechasFiltro = aFiltro[2];

        String aOficialiaFiltro[] = cOficialiaFiltro.replace('|',',').split(",");
        String aFechasFiltro[] = cFechasFiltro.replace('|',',').split(",");
        String cFecha = "", cOficio= "";
        String cRef = "",cInt = "", cCapitan = "",cOficinaCapitan = "", cDomicilio= "";
        String cServiciosPto = "", cServiciosTemp = "", cServiciosPtoCon = "";
        TWord rep = new TWord();
        TDDPOPMDP dPMDP = new TDDPOPMDP();
        TFechas tFecha = new TFechas();
        TVDinRep vData = new TVDinRep();
        TVDinRep vDatos = new TVDinRep();
        TVDinRep vDatosTit = new TVDinRep();
        TVDinRep vDatosPto = new TVDinRep();
        TVDinRep vDatosRecintos = new TVDinRep();
        rep.iniciaReporte();

        try{
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ");
            sb.append("ICVESOLICITANTE, ");
            sb.append("ICVEREPLEGAL ");
            sb.append("FROM TRAREGSOLICITUD ");
            sb.append("join GRLDOMICILIO on GRLDOMICILIO.ICVEPERSONA = TRAREGSOLICITUD.ICVESOLICITANTE ");
            sb.append("and GRLDOMICILIO.ICVEDOMICILIO = TRAREGSOLICITUD.ICVEDOMICILIOSOLICITANTE ");
            sb.append("where IEJERCICIO = "+iEjercicio);
            sb.append(" and INUMSOLICITUD = "+iNumSolicitud);

            vRegs = GenSQLGenerico(vRegs,sb.toString());
            if(vRegs.size() > 0){
                vDatos = (TVDinRep) vRegs.get(0);
                iCveSolicitante = vDatos.getInt("ICVESOLICITANTE");
                iCveRepLegal = vDatos.getInt("ICVEREPLEGAL");
            }

            vRegs2 = getDatosTarifa(iNumTarifa);
            if(vRegs2.size() > 0){
                vDatosTit = (TVDinRep) vRegs2.get(0);
                cInt = vDatosTit.getString("CNUMENTRADAOFICIALIA");
                cRef = vDatosTit.getString("CNUMOFICIOSOLTARIFA");
                cCapitan = vDatosTit.getString("CTITULAR");
                cOficinaCapitan = vDatosTit.getString("CDSCOFICINA");
                cDomicilio = vDatosTit.getString("CDOMICILIO");
            }

            StringBuffer sb3 = new StringBuffer();
            sb3.append("SELECT ");
            sb3.append("DISTINCT(TAR.ICVEPUERTO) AS IPUERTO, ");
            sb3.append("PTO.CDSCPUERTO as cPuerto, ");
            sb3.append("ENT.CABREVIATURA as cEntidadAbrev ");
            sb3.append("FROM TARTARIFASERVICIOS TAR ");
            sb3.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
            sb3.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
            sb3.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
            sb3.append("where TAR.iNumTarifa = "+iNumTarifa);
            sb3.append(" and TAR.iNumTituloContrato = "+iNumTituloContrato);
            sb3.append(" and TAR.lServicioPortuario = 1 ");
            sb3.append("Order by cPuerto ");

            vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());

            for(int i = 0; i < vRegs3.size(); i++){
                cServiciosTemp = "";
                vDatosPto = (TVDinRep) vRegs3.get(i);

                StringBuffer sb4 = new StringBuffer();

                sb4.append("SELECT ");
                sb4.append("TAR.ICVESERVICIOPORTUARIO as iCveServicioPort, ");
                sb4.append("SER.CDSCSERVICIOPORTUARIO as cServicioPortuario ");
                sb4.append("FROM TARTARIFASERVICIOS TAR ");
                sb4.append("JOIN CPASERVICIOPORTUARIO SER ON TAR.ICVESERVICIOPORTUARIO = SER.ICVESERVICIOPORTUARIO ");
                sb4.append("WHERE TAR.INUMTARIFA = "+iNumTarifa);
                sb4.append(" AND TAR.INUMTITULOCONTRATO = "+iNumTituloContrato);
                sb4.append(" AND TAR.ICVEPUERTO = "+vDatosPto.getInt("IPUERTO"));
                sb4.append(" ORDER BY cServicioPortuario ");

                vRegs4 = GenSQLGenerico(vRegs4,sb4.toString());

                for(int j=0; j < vRegs4.size(); j++){
                    vDatosRecintos = (TVDinRep) vRegs4.get(j);
                    if(cServiciosTemp.equalsIgnoreCase(""))
                        cServiciosTemp = vDatosRecintos.getString("cServicioPortuario");
                    else
                        cServiciosTemp += ", "+vDatosRecintos.getString("cServicioPortuario");
                }
                if(cServiciosPto.equalsIgnoreCase(""))
                    cServiciosPto = "servicio portuario de " + cServiciosTemp + " que proporciona en " + vDatosPto.getString("cPuerto")+", " +vDatosPto.getString("cEntidadAbrev");
                else
                    cServiciosPto += "; "+cServiciosTemp + " que proporciona en " + vDatosPto.getString("cPuerto")+", " +vDatosPto.getString("cEntidadAbrev");
            }

            /*Para los servicios conexos*/
            StringBuffer sb5 = new StringBuffer();
            sb5.append("SELECT ");
            sb5.append("DISTINCT(TAR.ICVEPUERTO) AS IPUERTO, ");
            sb5.append("PTO.CDSCPUERTO as cPuerto, ");
            sb5.append("ENT.CABREVIATURA as cEntidadAbrev ");
            sb5.append("FROM TARTARIFASERVICIOS TAR ");
            sb5.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
            sb5.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
            sb5.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
            sb5.append("where TAR.iNumTarifa = "+iNumTarifa);
            sb5.append(" and TAR.iNumTituloContrato = "+iNumTituloContrato);
            sb5.append(" and TAR.lServicioPortuario = 0 ");
            sb5.append("Order by cPuerto ");

            vRegs5 = GenSQLGenerico(vRegs5,sb5.toString());

            for(int i = 0; i < vRegs5.size(); i++){
                cServiciosTemp = "";
                vDatosPto = (TVDinRep) vRegs5.get(i);

                StringBuffer sb6 = new StringBuffer();

                sb6.append("SELECT ");
                sb6.append("TAR.ICVESERVICIOCONEXO as iCveServicioPort, ");
                sb6.append("SER.CDSCSERVCONEXO as cServicioConexo ");
                sb6.append("FROM TARTARIFASERVICIOS TAR ");
                sb6.append("JOIN TARSERVICIOCONEXO SER ON TAR.ICVESERVICIOCONEXO = SER.ICVESERVICIOCONEXO ");
                sb6.append("WHERE TAR.INUMTARIFA = "+iNumTarifa);
                sb6.append(" AND TAR.INUMTITULOCONTRATO = "+iNumTituloContrato);
                sb6.append(" AND TAR.ICVEPUERTO = "+vDatosPto.getInt("IPUERTO"));
                sb6.append(" ORDER BY cServicioConexo ");

                vRegs6 = GenSQLGenerico(vRegs6,sb6.toString());

                for(int j=0; j < vRegs6.size(); j++){
                    vDatosRecintos = (TVDinRep) vRegs6.get(j);
                    if(cServiciosTemp.equalsIgnoreCase(""))
                        cServiciosTemp = vDatosRecintos.getString("cServicioConexo");
                    else
                        cServiciosTemp += ", "+vDatosRecintos.getString("cServicioConexo");
                }
                if(cServiciosPtoCon.equalsIgnoreCase(""))
                    cServiciosPtoCon = " Así como al servicio conexo de "+cServiciosTemp + " que proporciona en " + vDatosPto.getString("cPuerto")+", " +vDatosPto.getString("cEntidadAbrev");
                else
                    cServiciosPtoCon += "; "+cServiciosTemp + " que proporciona en " + vDatosPto.getString("cPuerto")+", " +vDatosPto.getString("cEntidadAbrev");
            }
            /*Para las fechas y oficios con oficialia*/
            for (int i = 0; i < aOficialiaFiltro.length; i++) {
                if(cFecha.equalsIgnoreCase(""))
                    cFecha = tFecha.getFechaDDMMMMMYYYY(tFecha.getDateSQL(aFechasFiltro[i])," de ");
                else
                    cFecha += "; "+tFecha.getFechaDDMMMMMYYYY(tFecha.getDateSQL(aFechasFiltro[i])," de ");

                if(cOficio.equalsIgnoreCase(""))
                    cOficio = aOficialiaFiltro[i];
                else
                    cOficio += "; "+aOficialiaFiltro[i];

            }

            rep.comRemplaza("[cNumeros]",cOficio);
            rep.comRemplaza("[cFechas]",cFecha);
            rep.comRemplaza("[cCapitan]",cCapitan);
            rep.comRemplaza("[cOficinaCapitan]",cOficinaCapitan);
            rep.comRemplaza("[cDomicilio]",cDomicilio);
            rep.comRemplaza("[cServiciosPortYPuertoEntidad]",cServiciosPto + cServiciosPtoCon);

            vData.put("iEjercicioSolicitud",iEjercicio);
            vData.put("iNumSolicitud",iNumSolicitud);
            vData.put("iCveSistema",11);
            vData.put("iCveModulo",7);
            vData.put("iNumReporte",8);
            vData.put("cFolio",cFolio);
            vData.put("cAsunto","ENTREGA DE OFICIOS");

            dPMDP.insertReportexFolio(vData, conn);


            return new TDGeneral().generaOficioWord(cFolio,
                    Integer.parseInt(cCveOfic,10),
                    Integer.parseInt(cCveDepto,10),
                    0,0,
                    iCveSolicitante,0,iCveRepLegal,
                    "","","REF: "+cRef,"INT: "+cInt,
                    false,"",new Vector(),
                    false,new Vector(),
                    rep.getEtiquetasBuscar(),
                    rep.getEtiquetasRemplazar());


        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getVectorDatos(true);

    }

   /*Métodos que mandan a llamar a GenAutorizaRegistraInfraestructura para ejecutar los reportes de autorización,
    registro, no procede aut y reg. para los servicios portuarios de uso de infraestructura*/
    public Vector GenAutorizacionInfraestructura(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "OFICIO DE AUTORIZACIÓN DE TARIFA POR USO DE INFRAESTRUCTURA PORTUARIA ";
        int lAutorizacion = 1;
        int iNumReporte = 31;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistraInfraestructura(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroInfraestructura(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "OFICIO DE REGISTRO DE TARIFA POR USO DE INFRAESTRUCTURA PORTUARIA ";
        int lAutorizacion = 0;
        int iNumReporte = 32;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistraInfraestructura(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizacionNoProcedeInfraestructura(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "NO PROCEDE LA AUTORIZACIÓN DE LA TARIFA.";
        int lAutorizacion = 1;
        int iNumReporte = 33;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistraInfraestructura(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroNoProcedeInfraestructura(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "NO PROCEDE EL REGISTRO DE LA TARIFA.";
        int lAutorizacion = 0;
        int iNumReporte = 34;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistraInfraestructura(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenAutorizaRegistraInfraestructura(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto, String cAsuntoR, int lAutoriza,
            int iReporte) throws Exception{
        Vector vRegs = new Vector();
        Vector vRegs2 = new Vector();
        Vector vRegs3 = new Vector();
        String aFiltro[] = cFiltro.split(",");

        int iEjercicio = Integer.parseInt(aFiltro[3]);
        int iNumSolicitud = Integer.parseInt(aFiltro[4]);
        int iNumTarifa = Integer.parseInt(aFiltro[5]);
        int iNumTituloContrato = Integer.parseInt(aFiltro[6]);
        int iCveSolicitante=0, iCveRepLegal = 0, iCveDomicilio = 0;
        java.sql.Date dtFechaOfSol = null;
        String cRef = "", cInt = "", cRecintos = "", cRecintosTemp="";
        String cAsunto = cAsuntoR;
        int lAutorizacion = lAutoriza;
        int iNumReporte = iReporte;
        TWord rep = new TWord();
        TFechas tFecha = new TFechas();
        TDDPOPMDP dPMDP = new TDDPOPMDP();
        TVDinRep vData = new TVDinRep();
        TVDinRep vDatos = new TVDinRep();
        TVDinRep vDatosTarifa = new TVDinRep();
        TVDinRep vDatosPtoTemp = new TVDinRep();
        TVDinRep vDatosPto = new TVDinRep();

        rep.iniciaReporte();

        try{
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ");
            sb.append("ICVESOLICITANTE, ");
            sb.append("ICVEREPLEGAL, ");
            sb.append("ICVEDOMICILIOSOLICITANTE ");
            sb.append("FROM TRAREGSOLICITUD ");
            sb.append("where IEJERCICIO = "+iEjercicio);
            sb.append(" and INUMSOLICITUD = "+iNumSolicitud);

            vRegs = GenSQLGenerico(vRegs,sb.toString());
            if(vRegs.size() > 0){
                vDatos = (TVDinRep) vRegs.get(0);
                iCveSolicitante = vDatos.getInt("ICVESOLICITANTE");
                iCveRepLegal = vDatos.getInt("ICVEREPLEGAL");
                iCveDomicilio = vDatos.getInt("ICVEDOMICILIOSOLICITANTE");
            }

            vRegs2 = getDatosTarifa(iNumTarifa);
            if(vRegs2.size() > 0){
                vDatosTarifa = (TVDinRep) vRegs2.get(0);
                cInt = vDatosTarifa.getString("CNUMENTRADAOFICIALIA");
                cRef = vDatosTarifa.getString("CNUMOFICIOSOLTARIFA");
                dtFechaOfSol = vDatosTarifa.getDate("DTOFICIOSOLTARIFA");
            }

            StringBuffer sb3 = new StringBuffer();
            sb3.append("SELECT ");
            sb3.append("PTO.CDSCPUERTO AS CPUERTO, ");
            sb3.append("ENT.ICVEENTIDADFED AS ICVEENT, ");
            sb3.append("ENT.CNOMBRE AS CENTIDAD ");
            sb3.append("FROM TARTARIFASERVICIOS TAR ");
            sb3.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
            sb3.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
            sb3.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
            sb3.append("where TAR.iNumTarifa = "+iNumTarifa);
            sb3.append(" and TAR.iNumTituloContrato = "+iNumTituloContrato);
            sb3.append(" and TAR.lServicioPortuario = 0 ");
            sb3.append(" and TAR.lAutorizacion = " + lAutorizacion);
            sb3.append(" Order by ICVEENT ");

            vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());

            int iCveEntidadAnt = 0;

            for(int i = 0; i < vRegs3.size(); i++){
                vDatosPto = (TVDinRep) vRegs3.get(i);
                cRecintos += (cRecintos.equalsIgnoreCase("")?vDatosPto.getString("CPUERTO"):", "+vDatosPto.getString("CPUERTO"));
                if(vRegs3.size() == 1)
                    cRecintos+= " en " + vDatosPto.getString("CENTIDAD");
                else{

                    if(vDatosPto.getInt("ICVEENT") == iCveEntidadAnt)
                        cRecintos +=
                                (cRecintos == "" ? vDatosPto.getString("CPUERTO") :
                                    "," + vDatosPto.getString("CPUERTO"));
                    else
                        cRecintos += " en " + vDatosPto.getString("CENTIDAD");

                    if(i > 0){
                        vDatosPtoTemp = (TVDinRep) vRegs3.get(i - 1);
                        iCveEntidadAnt = vDatosPtoTemp.getInt("ICVEENT");
                    } else{
                        vDatosPtoTemp = (TVDinRep) vRegs3.get(i);
                        iCveEntidadAnt = vDatosPtoTemp.getInt("ICVEENT");
                    }
                }
            }

            String cAsuntoTemp = "";
            cAsuntoTemp = cAsunto;

            if(cAsuntoTemp.length() > 199)
                cAsuntoTemp = cAsuntoTemp.substring(0,199);

            vData.put("iEjercicioSolicitud",iEjercicio);
            vData.put("iNumSolicitud",iNumSolicitud);
            vData.put("iCveSistema",11);
            vData.put("iCveModulo",7);
            vData.put("iNumReporte",iNumReporte);
            vData.put("cFolio",cFolio);
            vData.put("cAsunto",cAsuntoTemp);

            dPMDP.insertReportexFolio(vData, conn);

            rep.comRemplaza("[cFechaOfSolicitud]",
                    tFecha.getFechaDDMMMMMYYYY(dtFechaOfSol," de "));
            if(lAutoriza == 1)
                rep.comRemplaza("[lAutoriza]","autorización");
            else
                rep.comRemplaza("[lAutoriza]","registro");

            if(vRegs3.size() > 1)
                rep.comRemplaza("[cRecintosPtoEF]","los  recintos portuarios de "+cRecintos);
            else
                rep.comRemplaza("[cRecintosPtoEF]","el recinto portuario de "+cRecintos);


            return new TDGeneral().generaOficioWord(cFolio,
                    Integer.parseInt(cCveOfic,10),
                    Integer.parseInt(cCveDepto,10),
                    0,0,
                    iCveSolicitante,iCveDomicilio,
                    iCveRepLegal,
                    "","","REF: " + cRef,
                    "INT: " + cInt,
                    false,"",new Vector(),
                    false,new Vector(),
                    rep.getEtiquetasBuscar(),
                    rep.getEtiquetasRemplazar());

        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getVectorDatos(true);

    }

   /*Métodos que mandan a llamar a GenAutorizaRegistraPilotaje para hacer los reportes
    de Autorización y Registro para Pilotaje*/
    public Vector GenAutorizacionPilotaje(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE ACTUALIZAN LAS BASES DE REGULACIÓN TARIFARIA DEL SERVICIO PORTUARIO DE PILOTAJE EN LOS PUERTOS QUE SE INDICAN.";
        int lAutorizacion = 1;
        int iNumReporte = 35;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistraPilotaje(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegistroPilotaje(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();
        String cAsunto = "SE ACTUALIZAN LAS BASES DE REGULACIÓN TARIFARIA DEL SERVICIO PORTUARIO DE PILOTAJE EN LOS PUERTOS QUE SE INDICAN.";
        int lAutorizacion = 0;
        int iNumReporte = 36;

        try{
            rep.iniciaReporte();

            vDatosRegresa = GenAutorizaRegistraPilotaje(cFiltro,cFolio,cCveOfic,cCveDepto,cAsunto,
                    lAutorizacion,iNumReporte);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }


    public Vector GenAutorizaRegistraPilotaje(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto, String cAsuntoR, int lAutoriza,
            int iReporte) throws Exception{
        Vector vRegs = new Vector();
        Vector vRegs2 = new Vector();
        Vector vRegs3 = new Vector();
        String aFiltro[] = cFiltro.split(",");

        int iEjercicio = Integer.parseInt(aFiltro[3]);
        int iNumSolicitud = Integer.parseInt(aFiltro[4]);
        int iNumTarifa = Integer.parseInt(aFiltro[5]);
        int iNumTituloContrato = Integer.parseInt(aFiltro[6]);
        int iCveSolicitante=0, iCveRepLegal = 0, iCveDomicilio = 0;
        String cRef = "", cInt = "";
        String cAsunto = cAsuntoR;
        int lAutorizacion = lAutoriza;
        int iNumReporte = iReporte;
        TWord rep = new TWord();
        TFechas tFecha = new TFechas();
        TDDPOPMDP dPMDP = new TDDPOPMDP();
        TVDinRep vData = new TVDinRep();
        TVDinRep vDatosTarifa = new TVDinRep();
        TVDinRep vDatosPto = new TVDinRep();

        rep.iniciaReporte();

        try{

            vRegs2 = getDatosTarifa(iNumTarifa);
            if(vRegs2.size() > 0){
                vDatosTarifa = (TVDinRep) vRegs2.get(0);
                cInt = vDatosTarifa.getString("CNUMENTRADAOFICIALIA");
                cRef = vDatosTarifa.getString("CNUMOFICIOSOLTARIFA");
            }

            StringBuffer sb3 = new StringBuffer();
            sb3.append("SELECT ");
            sb3.append("count(ICVEPUERTO) as iNumPtos ");
            sb3.append("FROM TARTARIFASERVICIOS TAR ");
            sb3.append("where TAR.iNumTarifa = "+iNumTarifa);
            sb3.append(" and TAR.iNumTituloContrato = " + iNumTituloContrato);
            sb3.append(" and TAR.lServicioPortuario = 1 ");
            sb3.append(" and TAR.lAutorizacion = " + lAutorizacion);

            vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());

            vDatosPto = (TVDinRep) vRegs3.get(0);
            if(vDatosPto.getInt("iNumPtos") > 1)
                rep.comRemplaza("[cNumPuertos]","los " + vDatosPto.getInt("iNumPtos") + " puertos");
            else
                rep.comRemplaza("[cNumPuertos]"," un puerto");

            String cAsuntoTemp = "";
            cAsuntoTemp = cAsunto;

            if(cAsuntoTemp.length() > 199)
                cAsuntoTemp = cAsuntoTemp.substring(0,199);

            vData.put("iEjercicioSolicitud",iEjercicio);
            vData.put("iNumSolicitud",iNumSolicitud);
            vData.put("iCveSistema",11);
            vData.put("iCveModulo",7);
            vData.put("iNumReporte",iNumReporte);
            vData.put("cFolio",cFolio);
            vData.put("cAsunto",cAsuntoTemp);

            dPMDP.insertReportexFolio(vData, conn);

            return new TDGeneral().generaOficioWord(cFolio,
                    Integer.parseInt(cCveOfic,10),
                    Integer.parseInt(cCveDepto,10),
                    0,0,
                    iCveSolicitante,iCveDomicilio,
                    iCveRepLegal,
                    "","","REF: " + cRef,
                    "INT: " + cInt,
                    false,"",new Vector(),
                    false,new Vector(),
                    rep.getEtiquetasBuscar(),
                    rep.getEtiquetasRemplazar());

        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getVectorDatos(true);

    }

    public Vector GenRegulacionTarifariaPilotaje(String cFiltro,String cFolio,String cCveOfic,
            String cCveDepto) throws Exception{
        Vector vRegs = new Vector();
        Vector vRegsTitular = new Vector();
        Vector vRegs3 = new Vector();
        String aFiltro[] = cFiltro.split(",");

        int iEjercicio = Integer.parseInt(aFiltro[3]);
        int iNumSolicitud = Integer.parseInt(aFiltro[4]);
        int iNumTarifa = Integer.parseInt(aFiltro[5]);
        int iNumTituloContrato = Integer.parseInt(aFiltro[6]);
        int iCvePto = Integer.parseInt(aFiltro[7]);

        int iCveSolicitante=0, iCveRepLegal=0, iCveDomicilio=0;
        String cFoliosFiltro = aFiltro[1];
        String cFechasFiltro = aFiltro[2];
        String aFoliosFiltro[] = cFoliosFiltro.replace('|',',').split(",");
        String aFechasFiltro[] = cFechasFiltro.replace('|',',').split(",");
        String cOficiosFechas = "", cCapitan ="",cOficinaCapitan="",cFecha = "",
                cRecintos="", cInt="",cRef="",cDomicilio="";
        TWord rep = new TWord();
        TFechas tFecha = new TFechas();
        TDDPOPMDP dPMDP = new TDDPOPMDP();
        TVDinRep vDatosTit = new TVDinRep();
        TVDinRep vDatosTititular = new TVDinRep();
        TVDinRep vData = new TVDinRep();
        TVDinRep vDatosPto = new TVDinRep();
        rep.iniciaReporte();

        try{

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ");
            sb.append("GRLOFICINA.CTITULAR AS CTITULAR, ");
            sb.append("GRLOFICINA.CDSCOFICINA AS CDSCOFICINA, ");
            sb.append("CCALLEYNO || ' COL. ' || CCOLONIA || ' C.P. ' || CCODPOSTAL || ' ' || GRLMUNICIPIO.CNOMBRE || ', ' || GRLENTIDADFED.CNOMBRE as cDomicilio ");
            sb.append("FROM GRLPUERTO ");
            sb.append("JOIN GRLOFICINA ON GRLPUERTO.ICVEOFICINAADSCRITA = GRLOFICINA.ICVEOFICINA ");
            sb.append("join GRLENTIDADFED on GRLOFICINA.ICVEPAIS = GRLENTIDADFED.ICVEPAIS ");
            sb.append("and GRLOFICINA.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED ");
            sb.append("join GRLMUNICIPIO on GRLOFICINA.ICVEPAIS = GRLMUNICIPIO.ICVEPAIS ");
            sb.append("and GRLOFICINA.ICVEENTIDADFED = GRLMUNICIPIO.ICVEENTIDADFED ");
            sb.append("and GRLOFICINA.ICVEMUNICIPIO = GRLMUNICIPIO.ICVEMUNICIPIO ");
            sb.append("where GRLPUERTO.icvepuerto = "+iCvePto);

            vRegsTitular = GenSQLGenerico(vRegsTitular,sb.toString());
            if(vRegsTitular.size() > 0){
                vDatosTititular = (TVDinRep) vRegsTitular.get(0);
                cCapitan = vDatosTititular.getString("CTITULAR");
                cOficinaCapitan = vDatosTititular.getString("CDSCOFICINA");
                cDomicilio = vDatosTititular.getString("cDomicilio");
            }

            vRegs = getDatosTarifa(iNumTarifa);
            if(vRegs.size() > 0){
                vDatosTit = (TVDinRep) vRegs.get(0);
                cInt = vDatosTit.getString("CNUMENTRADAOFICIALIA");
                cRef = vDatosTit.getString("CNUMOFICIOSOLTARIFA");
            }


            /*Para oficios refrenciados y fechas seleccionados*/
            for (int i = 0; i < aFoliosFiltro.length; i++) {
                if(!aFechasFiltro[i].equalsIgnoreCase(null) && !aFechasFiltro[i].equalsIgnoreCase("")){
                    if(tFecha.getIntYear(tFecha.TodaySQL()) !=
                            tFecha.getIntYear(tFecha.getDateSQL(aFechasFiltro[i])))
                        cFecha = tFecha.getFechaDDMMMMMYYYY(tFecha.getDateSQL(aFechasFiltro[
                                i])," de ");
                    else
                        cFecha = tFecha.getStringDay(tFecha.getDateSQL(aFechasFiltro[i])) +
                                " de " + tFecha.getMonthName(tFecha.getDateSQL(aFechasFiltro[i])) +
                                " del año en curso";
                }
                if(!aFoliosFiltro[i].equalsIgnoreCase(null) && !aFoliosFiltro[i].equalsIgnoreCase("")){
                    if(cOficiosFechas.equalsIgnoreCase(""))
                        cOficiosFechas = aFoliosFiltro[i] + " de fecha " + cFecha;
                    else
                        cOficiosFechas += "; " + aFoliosFiltro[i] + " de fecha " + cFecha;
                }
            }

            StringBuffer sb3 = new StringBuffer();
            sb3.append("SELECT ");
            sb3.append("PTO.CDSCPUERTO AS CPUERTO, ");
            sb3.append("ENT.ICVEENTIDADFED AS ICVEENT, ");
            sb3.append("ENT.CABREVIATURA AS CENTIDAD ");
            sb3.append("FROM TARTARIFASERVICIOS TAR ");
            sb3.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
            sb3.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
            sb3.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
            sb3.append("where TAR.iNumTarifa = "+iNumTarifa);
            sb3.append(" and TAR.iNumTituloContrato = "+iNumTituloContrato);
            sb3.append(" and TAR.lServicioPortuario = 1 ");
            sb3.append(" and TAR.ICVEPUERTO = "+iCvePto);
            sb3.append(" Order by ICVEENT ");

            vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());


            vDatosPto = (TVDinRep) vRegs3.get(0);
            cRecintos+= vDatosPto.getString("CPUERTO") + ", " + vDatosPto.getString("CENTIDAD");

            String cAsunto = "SE REMITEN LAS BASES DE REGULACIÓN TARIFARIA ACTUALIZADAS DEL "+
                    "SERVICIO PORTUARIO DE PILOTAJE PARA EL PUERTO DE "+cRecintos;

            String cAsuntoTemp = "";
            cAsuntoTemp = cAsunto;

            if(cAsuntoTemp.length() > 199)
                cAsuntoTemp = cAsuntoTemp.substring(0,199);


            vData.put("iEjercicioSolicitud",iEjercicio);
            vData.put("iNumSolicitud",iNumSolicitud);
            vData.put("iCveSistema",11);
            vData.put("iCveModulo",7);
            vData.put("iNumReporte",37);
            vData.put("cFolio",cFolio);
            vData.put("cAsunto",cAsuntoTemp);

            dPMDP.insertReportexFolio(vData, conn);

            rep.comRemplaza("[cCapitan]",cCapitan);
            rep.comRemplaza("[cOficinaCapitan]",cOficinaCapitan);
            rep.comRemplaza("[cDomicilio]",cDomicilio);
            rep.comRemplaza("[cOficioSelYFecha]",cOficiosFechas);
            rep.comRemplaza("[cAsuntoLargo]",cAsunto);

            rep.comRemplaza("[MesAno]",tFecha.getMonthAbrev(tFecha.TodaySQL()) +
                    (tFecha.getStringYear(tFecha.TodaySQL()).substring(2,4)));



            return new TDGeneral().generaOficioWord(cFolio,
                    Integer.parseInt(cCveOfic,10),
                    Integer.parseInt(cCveDepto,10),
                    0,0,
                    iCveSolicitante,iCveDomicilio,iCveRepLegal,
                    "","","REF: " + cRef,
                    "INT: " + cInt,
                    false,"",new Vector(),
                    false,new Vector(),
                    rep.getEtiquetasBuscar(),
                    rep.getEtiquetasRemplazar());



        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getVectorDatos(true);

    }

    public Vector getDatosTarifa(int iNumTarifa) throws Exception{
        Vector vRegs = new Vector();
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("SELECT ");
            sb.append("CNUMENTRADAOFICIALIA, ");
            sb.append("CNUMOFICIOSOLTARIFA, ");
            sb.append("DTOFICIOSOLTARIFA, ");
            sb.append("CCONCEPTO, ");
            sb.append("ICVEOFNANOTIFICA, ");
            sb.append("CTITULAR, ");
            sb.append("CDSCOFICINA, ");
            sb.append("CCALLEYNO || ' COL. ' || CCOLONIA || ' C.P. ' || CCODPOSTAL || ' ' || GRLMUNICIPIO.CNOMBRE || ', ' || GRLENTIDADFED.CNOMBRE as CDOMICILIO ");
            sb.append("FROM TARREGTARIFA ");
            sb.append("join GRLOFICINA on  TARREGTARIFA.ICVEOFNANOTIFICA = GRLOFICINA.ICVEOFICINA ");
            sb.append("join GRLENTIDADFED on GRLOFICINA.ICVEPAIS = GRLENTIDADFED.ICVEPAIS ");
            sb.append("and GRLOFICINA.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED ");
            sb.append("join GRLMUNICIPIO on GRLOFICINA.ICVEPAIS = GRLMUNICIPIO.ICVEPAIS ");
            sb.append("and GRLOFICINA.ICVEENTIDADFED = GRLMUNICIPIO.ICVEENTIDADFED ");
            sb.append("and GRLOFICINA.ICVEMUNICIPIO = GRLMUNICIPIO.ICVEMUNICIPIO ");
            sb.append("where INUMTARIFA = "+iNumTarifa);

            vRegs = this.GenSQLGenerico(vRegs,sb.toString());

        }catch(Exception ex2){
            ex2.printStackTrace();
            cMensaje += ex2.getMessage();
        }

        return vRegs;
    }

    public Vector getDatosTitularContrato(int iCveContrato) throws Exception{
        Vector vRegs = new Vector();
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("SELECT ");
            sb.append("PER.CNOMRAZONSOCIAL AS CTITULAR, ");
            sb.append("CCALLE || ' NO.' || CNUMEXTERIOR || ' INT. '  || CNUMINTERIOR || ' COL. ' || CCOLONIA || ' C.P. ' || CCODPOSTAL || ' ' || MUN.CNOMBRE || ', ' || EFE.CNOMBRE as CDOMICILIO,");
            sb.append("RGC.DTREGISTRO as DTREGISTRO,");
            sb.append("RGC.CNUMCONTRATO as CNUMCONTRATO,");
            sb.append("RGC.DTCONTRATO AS DTCONTRATO, ");
            sb.append("RGC.ICVETIPOCONTRATO AS ICVETIPOCONTRATO, ");
            sb.append("RGC.DTINIVIGENCIA AS DTINIVIGENCIA, ");
            sb.append("RGC.DTVENCIMIENTO AS DTVENCIMIENTO ");
            sb.append("FROM RGCCONTRATO RGC ");
            sb.append("JOIN CPATITULAR CPA ON RGC.ICVETITULO = CPA.ICVETITULO ");
            sb.append("JOIN GRLPERSONA PER ON CPA.ICVETITULAR = PER.ICVEPERSONA ");
            sb.append("JOIN GRLDOMICILIO DOM ON PER.ICVEPERSONA = DOM.ICVEPERSONA AND DOM.LPREDETERMINADO = 1 ");
            sb.append("JOIN GRLENTIDADFED EFE ON DOM.ICVEPAIS = EFE.ICVEPAIS ");
            sb.append("AND DOM.ICVEENTIDADFED = EFE.ICVEENTIDADFED ");
            sb.append("JOIN GRLMUNICIPIO MUN ON DOM.ICVEPAIS = MUN.ICVEPAIS ");
            sb.append("AND DOM.ICVEENTIDADFED = MUN.ICVEENTIDADFED ");
            sb.append("AND DOM.ICVEMUNICIPIO = MUN.ICVEMUNICIPIO ");
            sb.append("where RGC.ICVECONTRATO = "+iCveContrato);

            vRegs = this.GenSQLGenerico(vRegs,sb.toString());

        }catch(Exception ex2){
            ex2.printStackTrace();
            cMensaje += ex2.getMessage();
        }

        return vRegs;
    }

    public Vector getDatosTitularTitulo(int iCveTitulo) throws Exception{
        Vector vRegs = new Vector();
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("SELECT ");
            sb.append("tit.ICVETIPOTITULO as ICVETIPOTITULO, ");
            sb.append("per.CNOMRAZONSOCIAL as CTITULAR, ");
            sb.append("tit.DTINIVIGENCIATITULO as DTFECHAINI, ");
            sb.append("tit.DTVIGENCIATITULO as DTVIGENCIATITULO ");
            sb.append("FROM CPATITULO tit ");
            sb.append("join cpatitular cpa on tit.ICVETITULO = cpa.ICVETITULO ");
            sb.append("join GRLPERSONA per on cpa.ICVETITULAR = per.ICVEPERSONA ");
            sb.append("where tit.ICVETITULO = "+iCveTitulo);

            vRegs = this.GenSQLGenerico(vRegs,sb.toString());

        }catch(Exception ex2){
            ex2.printStackTrace();
            cMensaje += ex2.getMessage();
        }

        return vRegs;
    }



    public Vector GenSQLGenerico(Vector vRegistros, String SentenciaSQL) throws Exception{
        try{
            vRegistros = super.FindByGeneric("", SentenciaSQL, dataSourceName);
        }catch(SQLException ex){
            ex.printStackTrace();
            cMensaje = ex.getMessage();
        }catch(Exception ex2){
            ex2.printStackTrace();
            cMensaje += ex2.getMessage();
        }

        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);

        return vRegistros;
    }

    public Vector GenRegAplicAutorizaSPortuario(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();

        int lAutorizacion = 1;
        int lServicioPortuario = 1;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenReglasAplicacion(cFiltro,lAutorizacion,lServicioPortuario);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegAplicRegistraSPortuario(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();

        int lAutorizacion = 0;
        int lServicioPortuario = 1;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenReglasAplicacion(cFiltro,lAutorizacion,lServicioPortuario);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegAplicAutorizaSConexo(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();

        int lAutorizacion = 1;
        int lServicioPortuario = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenReglasAplicacion(cFiltro,lAutorizacion,lServicioPortuario);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenRegAplicRegistraSConexo(String cFiltro) throws Exception{
        Vector vDatosRegresa = new Vector();
        TWord rep = new TWord();

        int lAutorizacion = 0;
        int lServicioPortuario = 0;


        try{
            rep.iniciaReporte();

            vDatosRegresa = GenReglasAplicacion(cFiltro,lAutorizacion,lServicioPortuario);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return vDatosRegresa;
    }

    public Vector GenReglasAplicacion(String cFiltro, int lAutoriza, int lServicioPortuario) throws Exception{
        TFechas tFecha = new TFechas();
        TDObtenDatos dObtenDatos  = new TDObtenDatos();

        Vector vRegs  = new Vector();
        Vector vRegs2 = new Vector();
        Vector vRegs3 = new Vector();
        Vector vRegs4 = new Vector();

        TVDinRep vDatos = new TVDinRep();
        TVDinRep vDatosTarifa = new TVDinRep();
        TVDinRep vDatosPto = new TVDinRep();
        TVDinRep vDatosRecintos = new TVDinRep();

        String cServiciosTemp, cServiciosPto, cCapitania, cDireccionCapitania, cCopiaContrato, cOficios;
        String aFiltro[]       = cFiltro.split(",");
        String cFolioFiltro    = aFiltro[1];
        String aFoliosFiltro[] = cFolioFiltro.replace('|',',').split(",");

        cServiciosTemp = cServiciosPto = cCapitania = cDireccionCapitania = cCopiaContrato = cOficios = "";

        int iCveSolicitante = 0;
        int iEjercicio = Integer.parseInt(aFiltro[3]);
        int iNumSolicitud = Integer.parseInt(aFiltro[4]);
        int iNumTarifa = Integer.parseInt(aFiltro[5]);
        int iNumTituloContrato = Integer.parseInt(aFiltro[6]);

        int iCveOficinaOficio  = 0;
        int iCveDeptoOficio    = 0;

        int iCveContratoTitulo = 0;
        int lContrato = 0;
        if(aFiltro.length >= 6){
            iCveContratoTitulo = Integer.parseInt(aFiltro[7]);//Clave de Título o de Contrato segun la bandera de abajo
            lContrato = Integer.parseInt(aFiltro[8]);// 1 --> Es contrato.  0 --> No es Contrato
        }

        TWord rep = new TWord();

        try{
            rep.iniciaReporte();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ");
            sb.append("ICVESOLICITANTE ");
            sb.append("FROM TRAREGSOLICITUD ");
            sb.append("where IEJERCICIO = "+iEjercicio);
            sb.append(" and INUMSOLICITUD = "+iNumSolicitud);

            vRegs = GenSQLGenerico(vRegs,sb.toString());
            if(vRegs.size() > 0){
                vDatos = (TVDinRep) vRegs.get(0);
                iCveSolicitante = vDatos.getInt("ICVESOLICITANTE");
            }

            vRegs2 = getDatosTarifa(iNumTarifa);
            if(vRegs2.size() > 0){
                vDatosTarifa = (TVDinRep) vRegs2.get(0);

                cCapitania = vDatosTarifa.getString("CDSCOFICINA");
                cDireccionCapitania = vDatosTarifa.getString("CDOMICILIO");
            }

            dObtenDatos.dPersona.setPersona(iCveSolicitante,0);

            rep.comRemplaza("[cLeyendaAlusiva]",cLeyendaEnc);

            /*Para oficios referenciados */
            for (int i = 0; i < aFoliosFiltro.length; i++) {
                if(!aFoliosFiltro[i].equalsIgnoreCase(null) && !aFoliosFiltro[i].equalsIgnoreCase("")){
                    if(cOficios.equalsIgnoreCase(""))
                        cOficios = aFoliosFiltro[i];
                    else
                        cOficios += ", " + aFoliosFiltro[i];
                }
            }

            if(aFoliosFiltro.length > 0)
              if(!aFoliosFiltro[0].equalsIgnoreCase(null) && !aFoliosFiltro[0].equalsIgnoreCase("")){
                dObtenDatos.dFolio.setDatosFolio(cFolioFiltro);
                iCveOficinaOficio  = dObtenDatos.dFolio.getCveOficina();
                iCveDeptoOficio    = dObtenDatos.dFolio.getCveDepartamento();

                // Método que pone valores a las etiquetas [cLeyendaPie], [cPuestoTitular] y [cNombreRemitente]
                new TDGeneral().etiquetasPie(rep,iCveOficinaOficio,iCveDeptoOficio);

              }

            rep.comRemplaza("[cNumOficioRef]",cOficios);
            rep.comRemplaza("[cFecha]",tFecha.getFechaDDMMMMMYYYY(tFecha.TodaySQL()," de "));
            rep.comRemplaza("[cNombreEmpresaDest]",dObtenDatos.dPersona.getNomCompleto());
            rep.comRemplaza("[cCapitania]",cCapitania);
            rep.comRemplaza("[cDireccionCapitania]",cDireccionCapitania);

            if(lServicioPortuario == 1){ // Sección para Servicios Portuarios
                StringBuffer sb3 = new StringBuffer();
                sb3.append("SELECT ");
                sb3.append("DISTINCT(TAR.ICVEPUERTO) AS IPUERTO, ");
                sb3.append("PTO.CDSCPUERTO as cPuerto, ");
                sb3.append("ENT.CABREVIATURA as cEntidadAbrev ");
                sb3.append("FROM TARTARIFASERVICIOS TAR ");
                sb3.append("JOIN CPASERVICIOPORTUARIO SERPOR ON TAR.ICVESERVICIOPORTUARIO = SERPOR.ICVESERVICIOPORTUARIO ");
                sb3.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
                sb3.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
                sb3.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
                sb3.append("where TAR.iNumTarifa = " + iNumTarifa);
                sb3.append(" and TAR.iNumTituloContrato = " + iNumTituloContrato);
                sb3.append(" and TAR.lServicioPortuario = 1 ");
                sb3.append("and TAR.lAutorizacion = " + lAutoriza);
                sb3.append(" Order by cPuerto ");

                vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());

                for(int i = 0;i < vRegs3.size();i++){
                    cServiciosTemp = "";
                    vDatosPto = (TVDinRep) vRegs3.get(i);

                    StringBuffer sb4 = new StringBuffer();

                    sb4.append("SELECT ");
                    sb4.append("TAR.ICVESERVICIOPORTUARIO as iCveServicioPort, ");
                    sb4.append("SER.CDSCSERVICIOPORTUARIO as cServicioPortuario ");
                    sb4.append("FROM TARTARIFASERVICIOS TAR ");
                    sb4.append("JOIN CPASERVICIOPORTUARIO SER ON TAR.ICVESERVICIOPORTUARIO = SER.ICVESERVICIOPORTUARIO ");
                    sb4.append("WHERE TAR.INUMTARIFA = " + iNumTarifa);
                    sb4.append(" AND TAR.INUMTITULOCONTRATO = " + iNumTituloContrato);
                    sb4.append(" and TAR.lServicioPortuario = 1 ");
                    sb4.append("and TAR.lAutorizacion = " + lAutoriza);
                    sb4.append(" AND TAR.ICVEPUERTO = " + vDatosPto.getInt("IPUERTO"));
                    sb4.append(" ORDER BY cServicioPortuario ");

                    vRegs4 = GenSQLGenerico(vRegs4,sb4.toString());

                    for(int j = 0;j < vRegs4.size();j++){
                        vDatosRecintos = (TVDinRep) vRegs4.get(j);
                        if(cServiciosTemp.equalsIgnoreCase(""))
                            cServiciosTemp = vDatosRecintos.getString("cServicioPortuario");
                        else
                            cServiciosTemp += ", " +
                                    vDatosRecintos.getString("cServicioPortuario");
                    }
                    if(cServiciosPto.equalsIgnoreCase(""))
                        cServiciosPto = "SERVICIO PORTUARIO DE " + cServiciosTemp +
                                " QUE PROPORCIONA EN " + vDatosPto.getString("cPuerto") + ", " +
                                vDatosPto.getString("cEntidadAbrev");
                    else
                        cServiciosPto += "; " + cServiciosTemp + " QUE PROPORCIONA EN " +
                                vDatosPto.getString("cPuerto") + ", " +
                                vDatosPto.getString("cEntidadAbrev");
                }
            }else if(lServicioPortuario == 0){ // Sección para Servicios Conexos
                StringBuffer sb3 = new StringBuffer();
                sb3.append("SELECT ");
                sb3.append("DISTINCT(TAR.ICVEPUERTO) AS IPUERTO, ");
                sb3.append("PTO.CDSCPUERTO as cPuerto, ");
                sb3.append("ENT.CABREVIATURA as cEntidadAbrev ");
                sb3.append("FROM TARTARIFASERVICIOS TAR ");
                sb3.append("JOIN GRLPUERTO PTO ON TAR.ICVEPUERTO = PTO.ICVEPUERTO ");
                sb3.append("JOIN GRLENTIDADFED ENT ON PTO.ICVEPAIS = ENT.ICVEPAIS ");
                sb3.append("AND PTO.ICVEENTIDADFED = ENT.ICVEENTIDADFED ");
                sb3.append("where TAR.iNumTarifa = " + iNumTarifa);
                sb3.append(" and TAR.iNumTituloContrato = " + iNumTituloContrato);
                sb3.append(" and TAR.lServicioPortuario = 0 ");
                sb3.append("and TAR.lAutorizacion = " + lAutoriza);
                sb3.append(" Order by cPuerto ");

                vRegs3 = GenSQLGenerico(vRegs3,sb3.toString());

                for(int i = 0;i < vRegs3.size();i++){
                    cServiciosTemp = "";
                    vDatosPto = (TVDinRep) vRegs3.get(i);

                    StringBuffer sb4 = new StringBuffer();

                    sb4.append("SELECT ");
                    sb4.append("TAR.ICVESERVICIOCONEXO as iCveServicioCon, ");
                    sb4.append("SER.CDSCSERVCONEXO as cServicioCon ");
                    sb4.append("FROM TARTARIFASERVICIOS TAR ");
                    sb4.append("JOIN TARSERVICIOCONEXO SER ON TAR.ICVESERVICIOCONEXO = SER.ICVESERVICIOCONEXO ");
                    sb4.append("where TAR.iNumTarifa = "+iNumTarifa);
                    sb4.append(" AND TAR.INUMTITULOCONTRATO = " + iNumTituloContrato);
                    sb4.append(" and TAR.lServicioPortuario = 0 ");
                    sb4.append("and TAR.lAutorizacion = " + lAutoriza);
                    sb4.append(" AND TAR.ICVEPUERTO = " + vDatosPto.getInt("IPUERTO"));
                    sb4.append(" ORDER BY cServicioCon ");

                    vRegs4 = GenSQLGenerico(vRegs4,sb4.toString());

                    for(int j = 0;j < vRegs4.size();j++){
                        vDatosRecintos = (TVDinRep) vRegs4.get(j);
                        if(cServiciosTemp.equalsIgnoreCase(""))
                            cServiciosTemp = vDatosRecintos.getString("cServicioCon");
                        else
                            cServiciosTemp += ", " +
                                    vDatosRecintos.getString("cServicioCon");
                    }
                    if(cServiciosPto.equalsIgnoreCase(""))
                        cServiciosPto = "SERVICIO CONEXO DE " + cServiciosTemp +
                                " QUE PROPORCIONA EN " + vDatosPto.getString("cPuerto") + ", " +
                                vDatosPto.getString("cEntidadAbrev");
                    else
                        cServiciosPto += "; " + cServiciosTemp + " QUE PROPORCIONA EN " +
                                vDatosPto.getString("cPuerto") + ", " +
                                vDatosPto.getString("cEntidadAbrev");
                }
            }

            rep.comRemplaza("[cServicioPortuario]",cServiciosPto);

            //Para ver los contratos
            if(lContrato > 0){
                Vector vRegsTitCon = new Vector();
                TVDinRep vDatosTitCon = new TVDinRep();
                vRegsTitCon = getDatosTitularContrato(iCveContratoTitulo);
                if(vRegsTitCon.size() > 0){
                    vDatosTitCon = (TVDinRep) vRegsTitCon.get(0);
                    dObtenDatos.dPersona.setPersona(iCveSolicitante,0);
                    cCopiaContrato = "C.  Director General de la "+ vDatosTitCon.getString("CTITULAR") +
                            ".- " + vDatosTitCon.getString("CDOMICILIO") + "- Presente";
                }else
                    cCopiaContrato = "C.  Director General de la API .- Presente";
            }

            rep.comRemplaza("[cCopiaContrato]",cCopiaContrato);

        }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
        }
        if(!cMensaje.equals(""))
            throw new Exception(cMensaje);
        return rep.getVectorDatos(true);

    }

}

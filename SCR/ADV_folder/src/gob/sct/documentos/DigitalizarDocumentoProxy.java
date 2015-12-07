package gob.sct.documentos;

public class DigitalizarDocumentoProxy implements gob.sct.documentos.DigitalizarDocumento {
  private String _endpoint = null;
  private gob.sct.documentos.DigitalizarDocumento digitalizarDocumento = null;
  
  public DigitalizarDocumentoProxy() {
    _initDigitalizarDocumentoProxy();
  }
  
  public DigitalizarDocumentoProxy(String endpoint) {
    _endpoint = endpoint;
    _initDigitalizarDocumentoProxy();
  }
  
  private void _initDigitalizarDocumentoProxy() {
    try {
      digitalizarDocumento = (new gob.sct.documentos.DigitalizarDocumentoServiceLocator()).getDigitalizarDocumentoSoapPort();
      if (digitalizarDocumento != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)digitalizarDocumento)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)digitalizarDocumento)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (digitalizarDocumento != null)
      ((javax.xml.rpc.Stub)digitalizarDocumento)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gob.sct.documentos.DigitalizarDocumento getDigitalizarDocumento() {
    if (digitalizarDocumento == null)
      _initDigitalizarDocumentoProxy();
    return digitalizarDocumento;
  }
  
  public byte[] obtener(java.lang.String cUsr, java.lang.String cPwd, java.lang.String cEsquema, java.lang.String[] keys, java.lang.String[] values, java.lang.String[] operators) throws java.rmi.RemoteException{
    if (digitalizarDocumento == null)
      _initDigitalizarDocumentoProxy();
    return digitalizarDocumento.obtener(cUsr, cPwd, cEsquema, keys, values, operators);
  }
  
  public java.lang.String asignar(java.lang.String cUsr, java.lang.String cPwd, java.lang.String cEsquema, java.lang.String[] keys, java.lang.String[] values, byte[] bytes) throws java.rmi.RemoteException{
    if (digitalizarDocumento == null)
      _initDigitalizarDocumentoProxy();
    return digitalizarDocumento.asignar(cUsr, cPwd, cEsquema, keys, values, bytes);
  }
  
  
}
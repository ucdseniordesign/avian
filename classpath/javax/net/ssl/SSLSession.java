package javax.net.ssl;

import java.security.cert.Certificate;
import java.security.Principal;
import javax.net.ssl.SSLSessionContext;
import java.security.cert.X509Certificate;

public interface SSLSession {

  int getApplicationBufferSize();
  String getCipherSuite();
  long getCreationTime();
  byte[] getId();
  long getLastAccessedTime();
  Certificate[] getLocalCertificates();
  Principal getLocalPrincipal();
  int getPacketBufferSize();
  X509Certificate[] getPeerCertificateChain();
  Certificate[] getPeerCertificates();
  String getPeerHost();
  int getPeerPort();
  Principal getPeerPrincipal();
  String getProtocol();
  SSLSessionContext getSessionContext();
  Object getValue(String name);
  String[] getValueNames();
  void invalidate();
  boolean isValid();
  void putValue(String name, Object value);
  void removeValue(String name);
}

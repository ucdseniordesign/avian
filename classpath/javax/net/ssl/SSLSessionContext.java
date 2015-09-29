package javax.net.ssl;

import java.util.Enumeration;

public interface SSLSessionContext {

  Enumeration<byte[]>   getIds();
  SSLSession  getSession(byte[] sessionId);
  int   getSessionCacheSize();
  int   getSessionTimeout();
  void  setSessionCacheSize(int size);
  void  setSessionTimeout(int seconds);

}

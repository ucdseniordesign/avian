package javax.net.ssl;

import javax.net.ssl.SSLSession;

public interface HostnameVerifier {

  boolean verify(String hostname, SSLSession session);

}

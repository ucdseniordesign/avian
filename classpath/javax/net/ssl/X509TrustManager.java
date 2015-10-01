package javax.net.ssl;

import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;

public interface X509TrustManager extends TrustManager {

  void checkClientTrusted(X509Certificate[] chain, String authType);
  void checkServerTrusted(X509Certificate[] chain, String authType);
  X509Certificate[] getAcceptedIssuers();

}

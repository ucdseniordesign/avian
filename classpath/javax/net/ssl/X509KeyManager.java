package javax.net.ssl;

import java.security.cert.X509Certificate;
import java.security.Principal;
import java.net.Socket;
public interface X509KeyManager extends KeyManager {

String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket);
String chooseServerAlias(String keyType, Principal[] issuers, Socket socket);
X509Certificate[] getCertificateChain(String alias);
String[] getClientAliases(String keyType, Principal[] issuers);
PrivateKey getPrivateKey(String alias);
String[] getServerAliases(String keyType, Principal[] issuers);

}

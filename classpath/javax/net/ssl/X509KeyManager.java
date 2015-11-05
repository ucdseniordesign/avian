package javax.net.ssl;

// import library for interface X509KeyManager
import java.security.cert.X509Certificate;
import java.security.Principal;
import java.net.Socket;
import java.security.PrivateKey;

/* Instances of this interface manage which X509 certificate-based key pairs are used to authenticate the local side of a secure socket.

During secure socket negotiations, implentations call methods in this interface to:

- determine the set of aliases that are available for negotiations based on the criteria presented,
-  select the best alias based on the criteria presented, and
-  obtain the corresponding key material for given aliases.  */
public interface X509KeyManager extends KeyManager {

	String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket);

	String chooseServerAlias(String keyType, Principal[] issuers, Socket socket);

	X509Certificate[] getCertificateChain(String alias);

	String[] getClientAliases(String keyType, Principal[] issuers);

	PrivateKey getPrivateKey(String alias);

	String[] getServerAliases(String keyType, Principal[] issuers);

}

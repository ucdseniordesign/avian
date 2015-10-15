package javax.net.ssl;

import java.util.EventObject;
import java.security.cert.Certificate;
import java.security.Principal;
import java.security.cert.X509Certificate;

public class HandshakeCompletedEvent extends EventObject {
	private final SSLSocket sock;
	private final SSLSession s;

	public 	HandshakeCompletedEvent(SSLSocket sock, SSLSession s){
		super(sock);
		this.sock = sock;
		this.s = s;
	}
	public SSLSession getSession(){
		return s;
	}
	public String getCipherSuite(){
		return null;
	}

	public Certificate[] getLocalCertificates(){
		return null;
	}

	public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
	return null;	
	}

	public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
		return null;
	}
	public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
		return null;
	}
	public Principal getLocalPrincipal() {
		return null;	
	}
	public SSLSocket getSocket(){
		return sock;
	}
}

package javax.net.ssl;

import java.util.EventObject;
import java.security.cert.Certificate;
import java.security.Principal;
import java.security.cert.X509Certificate;

public class HandshakeCompletedEvent extends EventObject {
	private final SSLSocket sock;
	private final SSLSession session;

	public 	HandshakeCompletedEvent(SSLSocket sock, SSLSession s){
		super(sock);
		this.sock = sock;
		this.session = s;
	}
	public SSLSession getSession(){
		return session;
	}
	public String getCipherSuite(){
		return session.getCipherSuite();
	}

	public Certificate[] getLocalCertificates(){
		return session.getLocalCertificates();
	}

	public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
	return session.getPeerCertificates();	
	}

	public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
		return session.getPeerCertificateChain();
	}
	public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
		Principal principal;
		try {
			principal = session.getPeerPrincipal();
		    }
		catch (AbstractMethodError e) {
			Certificate[] certs = getPeerCertificates();
			principal = (X500Principal)
				((X509Certificate)certs[0].getSubjectX500Principal();
		    }
			return principal;
	}
	//stop right here
	public Principal getLocalPrincipal() {
		return null;	
	}
	public SSLSocket getSocket(){
		return sock;
	}
}

package javax.net.ssl;

import java.net.Socket;
import java.net.InetAddress;
import java.lang.String;

public abstract class SSLSocket extends Socket{

	protected SSLSocket();
	protected SSLSocket(String host, int port) throws IOException;
	protected SSLSocket(InetAddress address, int port) throws IOException;
	protected SSLSocket(String host, int port, InetAddress clientAddress, int clientPort) throws IOException;
	protected SSLSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException;

public abstract String[] getSupportedCipherSuites(){
		private final String suites;
		return suites;
	}	
public abstract String[] getEnabledCipherSuites(){
		return suites;	
	}
public abstract void setEnabledCipherSuites(String[] suites){
	}
public abstract String[] getSupportedProtocols(){
		private final protocols = protocols;
	}
public abstract String[] getEnabledProtocols(){
		return protocols;
	}	
public abstract void setEnabledProtocols(String[] protocols){
		return protocols;
	} 
public abstract SSLSession getSession(){
		return Session;
	}
public abstract void addHandshakeCompletedListener(HandshakeCompletedListener listener){
		
	}
public abstract void removeHandshakeCompletedListener(HandshakeCompletedListener listener){
		
	}
public abstract void startHandshake() throws IOException;
public abstract void setUseClientMode(boolean mode){
		
	}
public abstract boolean getUseClientMode(){
		return true;
	}
public abstract void setNeedClientAuth(boolean need){
		
	}
public abstract void setWantClientAuth(boolean want){
		
	}
public abstract void setEnableSessionCreation(boolean flag){
		
	}
public abstract boolean getEnableSessionCreation(){
		return true;
	}
public SSLParameters getSSLParameters(){
		
	}
public void setSSLParameters(SSLParameters params){
		
	}

}

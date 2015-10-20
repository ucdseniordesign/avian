package javax.net.ssl;

//import library for SSLSocket class.
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public abstract class SSLSocket extends Socket{
	//Used only for subclass. Constructs an uninitialized, unconnected TCP socket
	protected SSLSocket(){
		super();
		}
	//Used only by subclasses. Constructs a TCP connection to a named host at a specified port. This acts as the SSL client.
	protected SSLSocket(String host, int port) throws IOException, UnknownHostException {
		super(host, port);
		}
	//Used only by subclasses. Constructs a TCP connection to a server at a specified address and port. This acts as the SSL client. 
	protected SSLSocket(InetAddress address, int port) throws IOException {
		super(address, port);
		}
	//Used only by subclasses. Constructs an SSL connection to a named host at a specified port, binding the client side of the connection a given address and port. This acts as the SSL client. 
	protected SSLSocket(String host, int port, InetAddress clientAddress, int clientPort) throws IOException, UnknownHostException {
		super(host, port, clientAddress, clientPort);
		}		
	//Used only by subclasses. Constructs an SSL connection to a server at a specified address and TCP port, binding the client side of the connection a given address and port. This acts as the SSL client. 
	protected SSLSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException {
		super(address, port, clientAddress, clientPort);
		}

//Returns the names of the cipher suites which could be enabled for use on this connection.
public abstract String[] getSupportedCipherSuites();
//Returns the names of the SSL cipher suites which are currently enabled for use on this connection.
public abstract String[] getEnabledCipherSuites();
//Sets the cipher suites enabled for use on this connection. 
public abstract void setEnabledCipherSuites(String suites []);
//Returns the names of the protocols which could be enabled for use on an SSL connection. 
public abstract String[] getSupportedProtocols();
//Returns the names of the protocol versions which are currently enabled for use on this connection. 
public abstract String[] getEnabledProtocols();
//Sets the protocol versions enabled for use on this connection. 
public abstract void setEnabledProtocols(String protocols []);
//Returns the SSL Session in use by this connection.
public abstract SSLSession getSession();
//Registers an event listener to receive notifications that an SSL handshake has completed on this connection. 
public abstract void addHandshakeCompletedListener(HandshakeCompletedListener listener);
//Removes a previously registered handshake completion listener. 
public abstract void removeHandshakeCompletedListener(HandshakeCompletedListener listener);
//Starts an SSL handshake on this connection.
public abstract void startHandshake() throws IOException;
//Configures the socket to use client (or server) mode when handshaking. 
public abstract void setUseClientMode(boolean mode);
//Returns true if the socket is set to use client mode when handshaking. 
public abstract boolean getUseClientMode();
//Configures the socket to require client authentication.
public abstract void setNeedClientAuth(boolean need);
//Returns true if the socket will require client authentication
public abstract boolean getNeedClientAuth();
//Configures the socket to request client authentication. This option is only useful for sockets in the server mode. 
public abstract void setWantClientAuth(boolean want);
//Returns true if the socket will request client authentication. This option is only useful for sockets in the server mode. 
public abstract boolean getWantClientAuth();
//Controls whether new SSL sessions may be established by this socket
public abstract void setEnableSessionCreation(boolean flag);
//Returns true if new SSL sessions may be established by this socket. 
public abstract boolean getEnableSessionCreation();
//Returns the SSLParameters in effect for this SSLSocket. The ciphersuites and protocols of the returned SSLParameters are always non-null. 
public SSLParameters getSSLParameters(){
		SSLParameters params = new SSLParameters();
		params.setCipherSuites(getEnabledCipherSuites());
		params.setProtocols(getEnabledProtocols());
		if (getNeedClientAuth()) {
			params.setNeedClientAuth(true);
		} else if (getWantClientAuth()) {
			params.setWantClientAuth(true);
		}
		return params;

	}
//Applies SSLParameters to this socket. 
public void setSSLParameters(SSLParameters params){
	String[] s;
	s = params.getCipherSuites();
	if (s != null) {
		setEnabledCipherSuites(s);
	}
	s = params.getProtocols();
	if (s != null) {
		setEnabledProtocols(s);
	}
	if (params.getNeedClientAuth()) {
		setNeedClientAuth(true);
	} else if (params.getWantClientAuth()) {
		setWantClientAuth(true);
	} else {
		setWantClientAuth(false);
	}
    
	}

}

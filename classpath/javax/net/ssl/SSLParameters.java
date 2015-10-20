package javax.net.ssl;

import java.security.AlgorithmConstraints;

public class SSLParameters extends Object {
    public SSLParameters();
    public SSLParameters(String[] cipherSuites);
    public SSLParameters(String[] cipherSuites, String[] protocols);
    
    AlgorithmConstraints getAlgorithmConstraints();
    String[] getCipherSuites();
    String getEndpointIdentificationAlgorithm();
    boolean GetNeedClientAuth();
    String[] getProtocols();
    boolean getWantClientAuth();
    void setAlgorithmConstraints(AlgorithmConstraints constraints);
    void setCipherSuites(String[] cipherSuites);
    void setEndpointIdntificationAlgorithm(String algorithm);
    void setNeedClientAuth(boolean needClientAuth);
    void setProtocols(String[] protocols);
    void setWantClientAuth(boolean wantClientAuth);
}

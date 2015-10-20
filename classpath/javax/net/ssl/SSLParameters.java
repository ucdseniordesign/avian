package javax.net.ssl;

import java.security.AlgorithmConstraints;

public class SSLParameters {

    private String[] cipherSuites;
    private String[] protocols;
    private boolean wantClientAuth;
    private boolean needClientAuth;
    private String identificationAlgorithm;
    private AlgorithmConstraints algorithmConstraints;

    public SSLParameters(){}
    public SSLParameters(String[] cipherSuites) {
        setCipherSuites(cipherSuites);
    }
    public SSLParameters(String[] cipherSuites, String[] protocols) {
        setCipherSuites(cipherSuites);
        setProtocols(protocols);
    }    
    
    AlgorithmConstraints getAlgorithmConstraints() {
        return algorithmConstraints;
    }
    String[] getCipherSuites() {
        return cipherSuites;
    }
    String getEndpointIdentificationAlgorithm() {
        return identificationAlgorithm;
    }
    boolean getNeedClientAuth() {
        return needClientAuth;
    }
    String[] getProtocols() {
        return protocols;
    }
    boolean getWantClientAuth() {
        return wantClientAuth;
    }

    void setAlgorithmConstraints(AlgorithmConstraints constraints) {
        this.algorithmConstraints = constraints;
    }
    void setCipherSuites(String[] cipherSuites) {
        this.cipherSuites = cipherSuites;
    }
    void setEndpointIdntificationAlgorithm(String algorithm) {
        this.identificationAlgorithm = algorithm;
    }
    void setNeedClientAuth(boolean needClientAuth) {
        this.needClientAuth = needClientAuth;
    }
    void setProtocols(String[] protocols) {
        this.protocols = protocols;
    }
    void setWantClientAuth(boolean wantClientAuth) {
        this.wantClientAuth = wantClientAuth;
    }
}

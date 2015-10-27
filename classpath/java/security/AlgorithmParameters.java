package java.security;

import java.security.spec.AlgorithmParameterSpec;
public class AlgorithmParameters {
    
    private String algorithm;
    private byte[] encoded;
    private Provider provider;

    protected AlgorithmParameters(AlgorithmParametersSpi paramSpi, Provider provider, String algorithm) {}

    String getAlgorithm() {}
    byte[] getEncoded() {}
    byte getEncoded(String format) {}
    static AlgorithmParameters getInstance(String algorithm) {}
    static AlgorithmParameters getInstance(String algorithm, Provider provider) {}
    static AlgorithmParameters getInstance(String algorithm, String provider) {}
    <T extends AlgorithmParameterSpec> T getParametersSpec(Class<T> paramSpec) {}
    Provider getProvider() {}
    void init(AlgorithmParameterSpec paramSpec) {}
    void init(byte[] params) {}
    void init(byte[] params, String format) {}
    String toString() {}


}

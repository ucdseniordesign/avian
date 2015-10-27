package java.security;

public abstract class AlgorithmParametersSpi {
    public AlgorithmParametersSpi() {}
    protected abstract byte[] engineGetEncoded();
    protected abstract byte[] engineGetEncoded(String format);
    protected abstract <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> paramSpec);
    protected abstract void engineInit(AlgorithmParameterSpec paramSpec);
    protected abstract void engineInit(byte[] params);
    protected abstract void engineInit(byte[] params, String format);
    protected abstract String engineToString();
}

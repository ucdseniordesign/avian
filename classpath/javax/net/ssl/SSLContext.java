package javax.net.ssl;


public class SSLContext {
    private static native void initCTX();
    private static native long createCTX(String protocol);
    private static native void setKeyAndCert(long ctx, String keyPath, String certPath);
    private static native long createEngine(long ctx);
    
    static {
        initCTX();
    }
    
    private final long sslCtxPtr;
    
    protected SSLContext(String protocol) {  
        // initCTX();          
        sslCtxPtr = createCTX(protocol);
    }
    
    public void loadPemCertAndKey(String keyPath, String certPath) {
        setKeyAndCert(sslCtxPtr, keyPath, certPath);
    }
    
    public SSLEngine createSSLEngine() {
        return new SSLEngine(createEngine(sslCtxPtr));
    }
    
    public static SSLContext getInstance(String protocol) {
        return new SSLContext(protocol);
    }
}

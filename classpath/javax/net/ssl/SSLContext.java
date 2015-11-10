package javax.net.ssl;

public class SSLContext {
    private static native /* SSLContext */long create();
    private static native void initSSL();
    
    private long ssl_ctx;
    public SSLContext() {            
        SSLContext.initSSL();
        ssl_ctx = create();
    }
    public native void loadCertificates(String certFile, String testFile);
}

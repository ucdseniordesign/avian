package javax.net.ssl;

public class SSLContext {
    private static native /* SSLContext */long create();
    private static native void initSSL();
    
    private long ssl_ctx;
    public SSLContext() {            
        SSLContext.initSSL();
        ssl_ctx = create();
    }
<<<<<<< HEAD
    public native void loadCertificates(String certFile, String testFile);
=======
>>>>>>> 04743c406fc7142999e45247996b9e9b1be1e9aa
}

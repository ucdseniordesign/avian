package javax.net.ssl;

public class SSLContext {
    private static native /* SSLContext */long create();
    public static native void init();
    private long ssl_ctx;
    public SSLContext() {            
        SSLContext.init();
        ssl_ctx = create();
    }
}

package javax.net.ssl;

public class SSLContext {
    private static native /* SSLContext */long create();
    private long ssl_ctx;
    public SSLContext() {
        ssl_ctx = create();
    }
}

import javax.net.ssl.SSLContext;

public class SSLContextTest {

    public static void main(String[] args) {
        System.out.println("---------SSL----------");
        SSLContext sslCtx = SSLContext.getInstance("SSLw");
        sslCtx.loadPemCertAndKey("/tmp/test.pem", "/tmp/test.pem");
        sslCtx.createSSLEngine();
        System.out.println("---------SSL----------");
    }
}

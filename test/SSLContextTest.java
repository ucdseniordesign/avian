import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class SSLContextTest {

    public static void main(String[] args) {
        System.out.println("---------SSL----------");
        SSLContext sslCtx = SSLContext.getInstance("SSLv3");
        sslCtx.loadPemCertAndKey("/home/jason/sample/test.pem", "/home/jason/sample/test.pem");
        SSLEngine sslEng = sslCtx.createSSLEngine();
        
        System.out.println("---------SSL----------");
    }
}

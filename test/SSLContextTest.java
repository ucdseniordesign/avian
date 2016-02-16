import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.nio.ByteBuffer;

public class SSLContextTest {

    public static void main(String[] args) {
        System.out.println("---------SSLContext----------");
        SSLContext sslCtx = SSLContext.getInstance("SSLv3");
        sslCtx.loadPemCertAndKey("/home/jason/AvianSSLsample/test.pem", "/home/jason/AvianSSLsample/test.pem");
        SSLEngine sslEng = sslCtx.createSSLEngine();
        System.out.println("---------SSLContext----------");
        
    }
}
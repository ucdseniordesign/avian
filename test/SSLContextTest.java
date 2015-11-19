import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.nio.ByteBuffer;

public class SSLContextTest {

    public static void main(String[] args) {
        System.out.println("---------SSL----------");
        SSLContext sslCtx = SSLContext.getInstance("SSLv3");
        sslCtx.loadPemCertAndKey("/home/jason/sample/test.pem", "/home/jason/sample/test.pem");
        SSLEngine sslEng = sslCtx.createSSLEngine();

        String plaintxt = "Hello World";
        ByteBuffer ptBB = ByteBuffer.wrap(plaintxt.getBytes());
        ByteBuffer ctBB = ByteBuffer.allocateDirect(ptBB.capacity());

        sslEng.wrap(ptBB, ctBB);
        System.out.println(ctBB.capacity());
        //String ciphertxt = new String(ctBB.array());
        System.out.println("---------SSL----------");
    }
}

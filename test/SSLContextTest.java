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
        byte[] ptArr = new byte[ptBB.remaining()];
       
        sslEng.wrap(ptBB, ctBB);
        ptBB.clear();
        sslEng.unwrap(ctBB, ptBB);

        /*
        byte[] ptArr = new byte[ptBB.remaining()];
        ptBB.get(ptArr);
        String plaintxt_ = new String(ptArr);
        System.out.println(plaintxt_);

        byte[] ctArr = new byte[ctBB.remaining()];
        ctBB.get(ctArr);
        String ciphertxt = new String(ctArr);
        System.out.println(ciphertxt);
        */
        System.out.println("---------SSL----------");
        
    }
}

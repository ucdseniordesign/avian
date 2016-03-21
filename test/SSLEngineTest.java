import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.nio.ByteBuffer;

public class SSLEngineTest {

    public static void main(String[] args) {
        System.out.println("---Java----SSLEngine----------");
        // Create SSL Context using SSL version 3 
        SSLContext sslCtx = SSLContext.getInstance("SSLv3");

        // Load Key and Certificate using .pem format
        sslCtx.loadPemCertAndKey("/home/jason/AvianSSLsample/test.pem", "/home/jason/AvianSSLsample/test.pem");

        SSLEngine serverEng = sslCtx.createSSLEngine();
        serverEng.setUseClientMode(false);
        SSLEngine clientEng = sslCtx.createSSLEngine();
        clientEng.setUseClientMode(true);
        
        clientEng.beginHandshake();

        String plaintxt = "Hello World";
        ByteBuffer ptBB = ByteBuffer.wrap(plaintxt.getBytes());
        ByteBuffer ctBB = ByteBuffer.allocate(1024);

        byte[] ptArr = ptBB.array();

        /** test contents of byte array created from ByteBuffer **/
        for(int i=0; i<ptArr.length; i++)
            System.out.println(ptArr[i]);
        System.out.println(ptArr.length);
        
        // encrypt plain text byte buffer, encrypted ciphertxt buffer is the result
        clientEng.wrap(ptBB, ctBB);

        byte[] ctArr = ctBB.array();

        for(int j=0; j<512; j++)
            System.out.println(ctBB.getLong(j));

        
        ptBB.clear();
        // sslEng.unwrap(ctBB, ptBB);

        
        //byte[] ptArr = new byte[ptBB.remaining()];
        // ptBB.get(ptArr);
        // String plaintxt_ = new String(ptArr);
        // System.out.println(plaintxt_);

        // byte[] ctArr = new byte[ctBB.remaining()];
        // ctBB.get(ctArr);
        // String ciphertxt = new String(ctArr);
        // System.out.println(ciphertxt);
        
        System.out.println("---Java----SSLEngine----------");
        
    }
}

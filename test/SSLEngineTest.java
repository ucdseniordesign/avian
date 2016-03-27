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
        serverEng.beginHandshake();

        ByteBuffer clientIn = ByteBuffer.allocate(1024);
        ByteBuffer serverIn = ByteBuffer.allocate(1024);

        ByteBuffer cToS = ByteBuffer.allocate(16384);
        ByteBuffer sToc = ByteBuffer.allocate(16384);

        ByteBuffer clientOut = ByteBuffer.wrap("Hello server, I'm client".getBytes());
        ByteBuffer serverOut = ByteBuffer.wrap("Hello client, nice to meet you".getBytes());

        byte[] cOutArr = clientOut.array();

        /** test contents of byte array created from ByteBuffer **/
        for(int i=0; i<cOutArr.length; i++)
            System.out.println(cOutArr[i]);
        System.out.println(cOutArr.length);
        
        // encrypt plain text byte buffer, encrypted ciphertxt buffer is the result
        
        System.out.println("Results of wrap-client: " + clientEng.wrap(clientOut, cToS));
        // System.out.println("Results of wrap2: " + clientEng.wrap(clientOut, cToS));

        // System.out.println("Results of wrap-server: " + serverEng.wrap(serverOut, sToc));

        // sToc.flip();
        cToS.flip();

        System.out.println("Results of unwrap-client: " + clientEng.unwrap(cToS, clientIn));

        byte[] c_sArr = cToS.array();

        // for(int j=0; j<512; j++)
        //     System.out.println(cToS.getLong(j));

        
        clientOut.clear();
        // sslEng.unwrap(cToS, clientOut);

        
        //byte[] cOutArr = new byte[clientOut.remaining()];
        // clientOut.get(cOutArr);
        // String plaintxt_ = new String(cOutArr);
        // System.out.println(plaintxt_);

        // byte[] c_sArr = new byte[cToS.remaining()];
        // cToS.get(c_sArr);
        // String ciphertxt = new String(c_sArr);
        // System.out.println(ciphertxt);
        
        System.out.println("---Java----SSLEngine----------");
        
    }
}

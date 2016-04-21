import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import java.nio.ByteBuffer;

public class SSLEngineTest {

    public static void main(String[] args) {

        int one_k = 1024;
        int sixteen_k = 16384;
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

        ByteBuffer clientIn = ByteBuffer.allocate(sixteen_k);
        ByteBuffer serverIn = ByteBuffer.allocate(sixteen_k);

        ByteBuffer cToS = ByteBuffer.allocate(sixteen_k);
        ByteBuffer sToc = ByteBuffer.allocate(sixteen_k);

        ByteBuffer clientOut = ByteBuffer.wrap("Hello server, I'm client".getBytes());
        ByteBuffer serverOut = ByteBuffer.wrap("Hello client, nice to meet you".getBytes());

        SSLEngineResult clientResult;
        SSLEngineResult serverResult;        


        clientResult = clientEng.wrap(clientOut, cToS);
        System.out.println("clientResult:\n" + clientResult + '\n');

        // serverResult = serverEng.wrap(serverOut, sToc);        
        // System.out.println("serverResult:\n" + serverResult + '\n');
        
        
        cToS.flip();
        System.out.println(cToS);
        System.out.println(clientOut);

        // cToS.compact();
        // sToc.flip();

        // clientResult = clientEng.unwrap(sToc, clientIn);
        // System.out.println("clientResult:\n" + clientResult + '\n');

        serverResult = serverEng.unwrap(cToS, serverIn);        
        System.out.println("serverResult:\n" + serverResult + '\n');
        System.out.println(cToS);
        System.out.println(serverIn);



        /*
        clientEng.wrap(ByteBuffer.allocate(0), cToS);
        //loop. Handshake. loop until both wraps are no longer returning anything
        cToS.flip();
        if(cToS.hasRemaining()) {
            serverEng.unwrap(cToS, serverIn);
            serverIn.clear();
            
            serverEng.wrap(ByteBuffer.allocate(0), sToc);
            sToc.flip();

            if(sToc.hasRemaining()) {
                clientEng.unwrap(sToc, clientIn);
                clientIn.clear();
            }
            
        }
        */       

        // sToc.flip();
        // cToS.flip();
        
        // clientOut.clear();
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

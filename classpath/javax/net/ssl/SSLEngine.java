package javax.net.ssl;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import java.nio.ByteBuffer;

public class SSLEngine {
    private static native void startClientHandShake(long sslep);
    private static native void startServerHandShake(long sslep);
    private native int wrapData(long sslep, byte[] srcbuf, byte[] dstbuf);
    private native int unwrapData(long sslep, byte[] srcbuf, byte[] dstbuf);

    private final long sslePtr;
    private volatile boolean clientMode = false;
    private volatile boolean handShakeStarted = false;
    private volatile HandshakeStatus hsState = HandshakeStatus.NOT_HANDSHAKING;
    //private boolean noEncryptMode = false;
    
    protected SSLEngine(long sslePtr) {
        this.sslePtr = sslePtr;
    }
    
    public void setUseClientMode(boolean clientMode) {
        if(!this.handShakeStarted) {
            this.clientMode = clientMode;
        } else {
            //TODO: throw
        }
    }
    
    public void beginHandshake() {
        if(!this.handShakeStarted) {
            this.handShakeStarted = true;
            if(this.clientMode) {
                startClientHandShake(sslePtr);
                hsState = HandshakeStatus.NEED_WRAP;
            } else {
                startServerHandShake(sslePtr);
                hsState = HandshakeStatus.NEED_UNWRAP;
            }
        }
    }
    
    public HandshakeStatus getHandshakeStatus() {
        return hsState;
    }   
    
    public int wrap(ByteBuffer src, ByteBuffer dst) {
        // if(this.hsState == HandshakeStatus.NOT_HANDSHAKING)
        //     beginHandshake();
        // else if(this.hsState == HandshakeStatus.NEED_WRAP) {
            byte[] srcArr = new byte[src.remaining()];
            byte[] dstArr = new byte[dst.remaining()];
            src.get(srcArr);
            int result = wrapData(sslePtr, srcArr, dstArr); 
            
            dst.put(dstArr);
            return result;
        // }
        // else {
        //     //TODO: throw
        //     System.out.println("made it passed handshake status");
        // }
    }

    public int unwrap(ByteBuffer src, ByteBuffer dst) {
        // if(this.hsState == HandshakeStatus.NOT_HANDSHAKING)
        //     beginHandshake();
        // else if(this.hsState == HandshakeStatus.NEED_UNWRAP) {
            byte[] srcArr = new byte[src.remaining()];
            byte[] dstArr = new byte[dst.remaining()];
            src.get(srcArr);
            int result = unwrapData(sslePtr, srcArr, dstArr);
            dst.put(dstArr);
            return result;
        // }
        // else {
        //     //TODO: throw
        // }

    }
}

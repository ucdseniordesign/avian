package javax.net.ssl;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import java.nio.ByteBuffer;

public class SSLEngine {
    private static native void startClientHandShake(long sslep);
    private static native void startServerHandShake(long sslep);
    private native int[] wrapData(long sslep, byte[] srcbuf, byte[] dstbuf);
    private native int[] unwrapData(long sslep, byte[] srcbuf, byte[] dstbuf);

    private final long sslePtr;
    private volatile long ssleResPtr;
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
    
    public SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) {
        // Check that destination is large enough
        if(dst.remaining() < 16384) {
            return new SSLEngineResult(Status.BUFFER_UNDERFLOW, this.getHandshakeStatus(), 0, 0);
        }

        // else if  "outbound done"
        //      return CLOSED, getHsStatus, 0, 0
        
        else if(this.getHandshakeStatus() == HandshakeStatus.NEED_UNWRAP) {
            return new SSLEngineResult(Status.OK, this.getHandshakeStatus(), 0, 0);
        }

        // Convert to usable data types
        byte[] srcArr = new byte[src.remaining()];
        byte[] dstArr = new byte[dst.remaining()];
        src.get(srcArr);

        // Send to native code
        int[] native_result = wrapData(sslePtr, srcArr, dstArr);        
        
        // Place wrapped data into destination buffer        
        dst.put(dstArr);

        // Contruct and return SSLEngineResult based on results of wrapData
        SSLEngineResult result = new SSLEngineResult(native_result);
        hsState = result.getHandshakeStatus();
        return result;
       
    }

    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) {
        // if "inbound done"
        //      return CLOSED, getHsStatus, 0, 0

        if(this.getHandshakeStatus() == HandshakeStatus.NEED_WRAP) {
            return new SSLEngineResult(Status.OK, this.getHandshakeStatus(), 0, 0);
        }

        // convert to usable data types
        byte[] srcArr = new byte[src.remaining()];
        byte[] dstArr = new byte[dst.remaining()];
        src.get(srcArr);

        // Send to native code
        int[] native_result = unwrapData(sslePtr, srcArr, dstArr);

        // Place unwrapped data into destination buffer
        dst.put(dstArr);

        // Construct and return SSLEngineResult based on results of unwrapData
        SSLEngineResult result = new SSLEngineResult(native_result);
        hsState = result.getHandshakeStatus();
        return result;
    }
}

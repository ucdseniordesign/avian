package javax.net.ssl;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;

public class SSLEngine {
    private static native void startClientHandShake(long sslep);
    private static native void startServerHandShake(long sslep);
    private static native int getHSstatus();
    
    private final long sslePtr;
    private volatile boolean clientMode = false;
    private volatile boolean handShakeStarted = false;
    private volatile HandshakeStatus hsState = HandshakeStatus.NOT_HANDSHAKING;
    
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
        if (handShakeStarted == false)
            hsState = HandshakeStatus.NOT_HANDSHAKING;      
        else if(getHSstatus() == 0)
            hsState = HandshakeStatus.NEED_UNWRAP;
        else if(getHSstatus() == 1)
            hsState = HandshakeStatus.NEED_WRAP;
        else
            hsState = HandshakeStatus.FINISHED;
        return hsState;
    }
}

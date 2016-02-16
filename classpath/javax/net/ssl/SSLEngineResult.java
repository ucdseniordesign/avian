package javax.net.ssl;

public class SSLEngineResult {

    static enum HandshakeStatus {
        FINISHED,
        NEED_TASK,
        NEED_UNWRAP,
        NEED_WRAP,
        NOT_HANDSHAKING
    }

    HandshakeStatus getHandshakeStatus() {
        return hsStatus;
    }
    private HandshakeStatus hsStatus;
    
}

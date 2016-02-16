package javax.net.ssl;

public class SSLEngineResult {
    public static enum HandshakeStatus {
        FINISHED,
        NEED_TASK,
        NEED_UNWRAP,
        NEED_WRAP,
        NOT_HANDSHAKING
    }
}

package javax.net.ssl;

public class SSLEngineResult {

    public static enum HandshakeStatus {
        FINISHED,
        NEED_TASK,
        NEED_UNWRAP,
        NEED_WRAP,
        NOT_HANDSHAKING
    }
    public static enum Status {
    	BUFFER_OVERFLOW,
    	BUFFER_UNDERFLOW,
    	CLOSED,
    	OK
    }

    private final int _bytesConsumed;
	private final int _bytesProduced;
	private final HandshakeStatus _hsStatus;
	private final Status _status;

    public SSLEngineResult(SSLEngineResult.Status status,
    		SSLEngineResult.HandshakeStatus hsStatus,
    		int bytesConsumed, int bytesProduced) {
    	_bytesConsumed = bytesConsumed;
    	_bytesProduced = bytesProduced;
    	_hsStatus = hsStatus;
    	_status = status;
    }

    public final int bytesConsumed() {
    	return _bytesConsumed;
    }

    public final int bytesProduced() {
    	return _bytesProduced;
    }

    public final SSLEngineResult.HandshakeStatus getHandshakeStatus() {
    	return _hsStatus;
    }

    public final SSLEngineResult.Status getStatus() {
    	return _status;
    }

}

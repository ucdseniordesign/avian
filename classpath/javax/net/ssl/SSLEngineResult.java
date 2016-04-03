package javax.net.ssl;

public class SSLEngineResult {
	// private static native void constructSSLEngineResult(long res_ptr);
	
    public static enum HandshakeStatus {
        FINISHED,
        NEED_TASK,
        NEED_UNWRAP,
        NEED_WRAP,
        NOT_HANDSHAKING;

        private HandshakeStatus(){}
    }
    public static enum Status {
    	BUFFER_OVERFLOW,
    	BUFFER_UNDERFLOW,
    	CLOSED,
    	OK;

    	private Status(){}
    }

    private final int _bytesConsumed;
	private final int _bytesProduced;
	private final HandshakeStatus _hsStatus;
	private final Status _status;

    public SSLEngineResult(SSLEngineResult.Status status,
    		SSLEngineResult.HandshakeStatus hsStatus,
    		int bytesConsumed, int bytesProduced) {
    	if(status != null && hsStatus != null && bytesConsumed >= 0 && bytesProduced >= 0) {

	    	this._bytesConsumed = bytesConsumed;
	    	this._bytesProduced = bytesProduced;
	    	this._hsStatus = hsStatus;
	    	this._status = status;
	    } else {
	    	throw new IllegalArgumentException("Invalid argument(s)");
	    }
    }

    public final int bytesConsumed() {
    	return this._bytesConsumed;
    }

    public final int bytesProduced() {
    	return this._bytesProduced;
    }

    public final SSLEngineResult.HandshakeStatus getHandshakeStatus() {
    	return this._hsStatus;
    }

    public final SSLEngineResult.Status getStatus() {
    	return this._status;
    }

    public String toString() {
    	return "Status = " + this._status + "\nHandshakeStatus = " + this._hsStatus + "\nbytesConsumed = " + this._bytesConsumed + "\nbytesProduced = " + this._bytesProduced;
    }

}

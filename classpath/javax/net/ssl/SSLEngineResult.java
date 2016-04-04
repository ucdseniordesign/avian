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

    public SSLEngineResult(int[] args) {
    	SSLEngineResult.Status status;
    	SSLEngineResult.HandshakeStatus hsStatus;
    	int bytesConsumed, bytesProduced;

    	switch (args[0]) {
    		case 6: status = Status.BUFFER_OVERFLOW;
    			break; 
    		case 7: status = Status.BUFFER_UNDERFLOW;
    			break;
    		case 8: status = Status.CLOSED;
    			break;
    		case 9: status = Status.OK;
    			break;
    		default: status = null;
    			break;
    	}

    	switch (args[1]) {
    		case 1: hsStatus = HandshakeStatus.FINISHED;
    			break;
    		case 2:	hsStatus = HandshakeStatus.NEED_TASK;
    			break;
    		case 3: hsStatus = HandshakeStatus.NEED_WRAP;
    			break;
    		case 4: hsStatus = HandshakeStatus.NEED_UNWRAP;
    			break;
    		case 5: hsStatus = HandshakeStatus.NOT_HANDSHAKING;
    			break;
    		default: hsStatus = null;
    			break;
    	}

    	if(status != null && hsStatus != null && args[2] >= 0 && args[3] >= 0) {

	    	this._bytesConsumed = args[2];
	    	this._bytesProduced = args[3];
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

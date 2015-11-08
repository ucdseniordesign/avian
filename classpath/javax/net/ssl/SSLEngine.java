package javax.net.sll;

import java.nio.ByteBuffer;

public class SSLEngine {
    public native void wrap(ByteBuffer src, ByteBuffer dst);
    public native void unwrap(ByteBuffer src, ByteBuffer dst);
}

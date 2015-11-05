#include <openssl/ssl.h>
#include "jni.h"
#include "jni-util.h"
#include "avian/common.h"

namespace avian {
namespace classpath {
namespace sslcontext {
    static SSL_CTX* create(/*JNIEnv**/);

    //static void init();
}
}
}

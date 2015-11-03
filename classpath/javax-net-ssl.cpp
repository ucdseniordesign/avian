// javax-net-ssl.cpp
#include "jni.h"
#include "sslcontext.h"

extern "C" JNIEXPORT SSLCONTEXT JNICALL Java_javax_net_ssl_SSLContext_create(JNIEnv* env, jclass) {
    return create(env);
}

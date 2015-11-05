// javax-net-ssl.cpp
#include "jni.h"
#include "jni-util.h"
#include <openssl/ssl.h>
#include <openssl/err.h>

extern "C" JNIEXPORT void Java_javax_net_ssl_SSLContext_initSSL(JNIEnv*, jclass) {
    SSL_library_init();}



extern "C" JNIEXPORT jlong JNICALL Java_javax_net_ssl_SSLContext_create(/*JNIEnv* env, jclass*/) {

    SSL_CTX* ctx = SSL_CTX_new(SSLv23_method());

    if(ctx == NULL) {
        printf("Error making context\n");
        exit(1);
    }
    return (jlong)ctx;
}

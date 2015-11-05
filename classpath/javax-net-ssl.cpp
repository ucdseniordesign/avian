// javax-net-ssl.cpp
#include "jni.h"
#include "jni-util.h"

#include "sslcontext.h"
using namespace avian::classpath::sslcontext;
extern "C" JNIEXPORT SSLCONTEXT JNICALL Java_javax_net_ssl_SSLContext_create(JNIEnv* env, jclass) {
    return create();
}/*
extern "C" JNIEXPORT void JNICALL Java_javax_net_ssl_SSLContext_init(JNIEnv* env, jclass) {
    init(env);
}  */

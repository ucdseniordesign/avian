// javax-net-ssl.cpp
#include "jni.h"
#include "jni-util.h"
#include <openssl/ssl.h>
#include <string.h>
#include <openssl/err.h>

struct SSLEngineState {
  SSL* sslEngine;
  BIO* inputBuffer;
  BIO* outputBuffer;
};

extern "C" JNIEXPORT void Java_javax_net_ssl_SSLContext_initCTX(JNIEnv*, jclass) {
    SSL_load_error_strings();
    SSL_library_init();
//    OpenSSL_add_all_algorithms();
}

extern "C" JNIEXPORT jlong JNICALL Java_javax_net_ssl_SSLContext_createCTX(JNIEnv* env, jclass, jstring protocol) {
    const char *SSL = "SSL";
    const char *SSLV2 = "SSLv2";
    const char *SSLV3 = "SSLv3";
    const char *TLS = "TLS";
    const char *TLSV1 = "TLSv1";
    const char *TLSV11 = "TLSv1.1";
    const char *TLSV12 = "TLSv1.2";


    const char *pro = env->GetStringUTFChars(protocol, JNI_FALSE);

    SSL_CTX* ctx = NULL;
    if(strcmp(pro, SSL)  == 0|| strcmp(pro, SSLV2) == 0) {
        ctx = SSL_CTX_new(SSLv23_method());
    } else if(strcmp(pro, SSLV3) == 0) {
        ctx = SSL_CTX_new(SSLv3_method());
    } else if(strcmp(pro, TLS) == 0 || strcmp(pro, TLSV1) == 0) {
        ctx = SSL_CTX_new(TLSv1_method());
    } else if(strcmp(pro, TLSV11) == 0) {
        ctx = SSL_CTX_new(TLSv1_1_method());
    } else if(strcmp(pro, TLSV12) == 0 ) {
        ctx = SSL_CTX_new(TLSv1_2_method());
    } else {
        const char *className = "java/security/NoSuchAlgorithmException";
        jclass exClass = env->FindClass(className);
        return env->ThrowNew(exClass, "ERROR" );
    } 

    if(ctx == NULL) {
        printf("Error making context\n");
        exit(1);
    }

    env->ReleaseStringUTFChars(protocol, pro);
    return (long)ctx;
}

extern "C" JNIEXPORT void JNICALL Java_javax_net_ssl_SSLContext_setKeyAndCert(JNIEnv* env, jclass, jlong jctxPtr, jstring keyPath, jstring certPath) {

    SSL_CTX* ctx = (SSL_CTX*)jctxPtr;
    const char *kp = env->GetStringUTFChars(keyPath, JNI_FALSE);
    const char *cp = env->GetStringUTFChars(certPath, JNI_FALSE);
    
    if ( SSL_CTX_use_certificate_file(ctx, cp, SSL_FILETYPE_PEM) <= 0 ) {
      printf("Error loading certFile\n");
      exit(1);
    }
    printf("TEST SETTING CERT2\n");
    /* set the private key from KeyFile (may be the same as CertFile) */
    if ( SSL_CTX_use_PrivateKey_file(ctx, kp, SSL_FILETYPE_PEM) <= 0 ) {
      printf("Error loading keyFile\n");
      exit(1);
    }
    /* verify private key */
    if ( !SSL_CTX_check_private_key(ctx) ) {
      printf("Error with Key\n");
      exit(1);
    }
    env->ReleaseStringUTFChars(keyPath, kp);
    env->ReleaseStringUTFChars(certPath, cp);
}


extern "C" JNIEXPORT jlong JNICALL Java_javax_net_ssl_SSLContext_createEngine(JNIEnv*, jclass, jlong jctxPtr) {
    SSL_CTX* ctx = (SSL_CTX*)jctxPtr;
    SSLEngineState* ssleState = (SSLEngineState*)malloc(sizeof(struct SSLEngineState));
    ssleState->sslEngine = SSL_new(ctx);
    ssleState->inputBuffer = BIO_new(BIO_s_mem());
    ssleState->outputBuffer = BIO_new(BIO_s_mem());
    SSL_set_bio(ssleState->sslEngine, ssleState->inputBuffer, ssleState->outputBuffer);
    return (jlong) ssleState;
}

extern "C" JNIEXPORT void JNICALL Java_javax_net_ssl_SSLContext_startClientHandShake(JNIEnv*, jclass, jlong sslep) {
    SSLEngineState* ssleState = (SSLEngineState*)sslep;
    SSL_connect(ssleState->sslEngine);
}

extern "C" JNIEXPORT void JNICALL Java_javax_net_ssl_SSLContext_startServerHandShake(JNIEnv*, jclass, jlong sslep) {
    SSLEngineState* ssleState = (SSLEngineState*)sslep;
    SSL_accept(ssleState->sslEngine);
}


extern "C" JNIEXPORT int JNICALL Java_javax_net_ssl_SSLContext_getHSstatus(JNIEnv*, jclass, jlong sslep) {
    SSLEngineState* ssleState = (SSLEngineState*)sslep;
    if(BIO_eof(ssleState->outputBuffer) == 0)
        return 0;
    else if(BIO_eof(ssleState->inputBuffer) == 0) 
        return 1;
    else  
        return 2;  
}


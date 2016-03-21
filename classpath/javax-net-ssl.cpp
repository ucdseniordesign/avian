// javax-net-ssl.cpp

#include "jni.h"
#include "jni-util.h"
#include <openssl/ssl.h>
#include <string.h>
#include <stdlib.h>
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
    //const char *noEncrypt = "noEncrypt";


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

extern "C" JNIEXPORT void JNICALL Java_javax_net_ssl_SSLEngine_startClientHandShake(JNIEnv*, jclass, jlong sslep) {
    SSLEngineState* ssleState = (SSLEngineState*)sslep;
    SSL_connect(ssleState->sslEngine);
}

extern "C" JNIEXPORT void JNICALL Java_javax_net_ssl_SSLEngine_startServerHandShake(JNIEnv*, jclass, jlong sslep) {
    SSLEngineState* ssleState = (SSLEngineState*)sslep;
    SSL_accept(ssleState->sslEngine);
}


extern "C" JNIEXPORT int JNICALL Java_javax_net_ssl_SSLEngine_getHandshakeStatus(JNIEnv*, jclass, jlong) {
    //SSLEngineState* ssleState = (SSLEngineState*)sslep;
    return 1;
}

extern "C" JNIEXPORT jint JNICALL
Java_javax_net_ssl_SSLEngine_wrapData(JNIEnv* env, jclass, jlong sslep,
        jbyteArray src, jbyteArray dst) {

    printf("---C---javax-net-ssl_wrapData----\n");

    /** Create SSLEngine structure **/
    SSLEngineState* ssleState = (SSLEngineState*)sslep;   
   
    /** Point to Java ByteBuffers **/
    jbyte* src_ptr = env->GetByteArrayElements(src, NULL);
    jbyte* dst_ptr = env->GetByteArrayElements(dst, NULL);
    
    jsize src_len = env->GetArrayLength(src);
    jsize dst_len = env->GetArrayLength(dst);

    // jint read_result = BIO_read()
    /* Test src_ptr ********************************/
    // printf("src_len is %d long, dst_len is %d long\n", src_len, dst_len);
    // for(int i=0; i<src_len; i++)
    //     printf("contents of element %d in source:%d\n", i, src_ptr[i]);

    int bytes_encrypted = 0;
    jint bytes_SSL_written = 0;  

    // what needs to be done before SSL_write?
    // check if ther is data on the engine first
    
    ERR_print_errors_fp(stdout);
    printf("is there anything in the engine? <in> <out>%lu , %lu\n", BIO_ctrl_pending(ssleState->inputBuffer), BIO_ctrl_pending(ssleState->outputBuffer));
    bytes_encrypted = BIO_read(ssleState->outputBuffer, dst_ptr, dst_len);
    
    printf("results of BIO_read = %d\n", SSL_get_error(ssleState->sslEngine, bytes_encrypted));

    bytes_SSL_written = SSL_write(ssleState->sslEngine, src_ptr, src_len);

    printf("results of SSL_write = %d\n", SSL_get_error(ssleState->sslEngine, bytes_SSL_written));
    ERR_print_errors_fp(stdout);


    printf("---C---javax-net-ssl_wrapData----\n");
    return SSL_get_error(ssleState->sslEngine, bytes_SSL_written);
/*
    printf("Error code (result from SSL_write): %d\n", SSL_get_error(ssleState->sslEngine, bytes_SSL_written));
    bytes_encrypted = BIO_read(ssleState->outputBuffer, dst_ptr, dst_len);

    printf("---SSLEngine_wrapData----\n");

    return bytes_encrypted; 
*/
}

extern "C" JNIEXPORT jint JNICALL
Java_javax_net_ssl_SSLEngine_unwrapData(JNIEnv* env, jclass, jlong sslep,
        jbyteArray src, jbyteArray dst) {

    SSLEngineState* ssleState = (SSLEngineState*)sslep;

    jbyte* src_ptr = env->GetByteArrayElements(src, NULL);
    jbyte* dst_ptr = env->GetByteArrayElements(dst, NULL);

    jsize src_len = env->GetArrayLength(src);
    jsize dst_len = env->GetArrayLength(dst);

    jint bytes_decrypted = 0;

    bytes_decrypted = BIO_write(ssleState->inputBuffer, src_ptr, src_len);

    SSL_read(ssleState->sslEngine, dst_ptr, dst_len);

    return bytes_decrypted;
}

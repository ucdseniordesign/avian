// javax-net-ssl.cpp

#include "jni.h"
#include "jni-util.h"
#include <openssl/ssl.h>
#include <string.h>
#include <stdlib.h>
#include <openssl/err.h>

// SSLEngineResult values
#define FINISHED            1
#define NEED_TASK           2
#define NEED_WRAP           3
#define NEED_UNWRAP         4
#define NOT_HANDSHAKING     5
#define BUFFER_OVERFLOW     6
#define BUFFER_UNDERFLOW    7
#define CLOSED              8
#define OK                  9

// Keys for SSLEngineResult array
#define _status_            0
#define _hsStatus_          1
#define _bytesConsumed_     2
#define _bytesProduced_     3


struct SSLEngineState {
  SSL* sslEngine;
  BIO* inputBuffer; /* rbio */
  BIO* outputBuffer; /* wbio */
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
    SSL_connect(ssleState->sslEngine); /* Initiate TLS/SSL handshake with a server */
}

extern "C" JNIEXPORT void JNICALL Java_javax_net_ssl_SSLEngine_startServerHandShake(JNIEnv*, jclass, jlong sslep) {
    SSLEngineState* ssleState = (SSLEngineState*)sslep;
    SSL_accept(ssleState->sslEngine); /* Wait for a TLS/SSL client to initiate the TLS/SSL handshake */
}


extern "C" JNIEXPORT int JNICALL Java_javax_net_ssl_SSLEngine_getHandshakeStatus(JNIEnv*, jclass, jlong) {
    //SSLEngineState* ssleState = (SSLEngineState*)sslep;
    return 1;
}

extern "C" JNIEXPORT jintArray JNICALL
Java_javax_net_ssl_SSLEngine_wrapData(JNIEnv* env, jclass, jlong sslep,
        jbyteArray /*src*/, jbyteArray dst) {

    printf("---C---javax-net-ssl_wrapData----\n");

    /** Create SSLEngine structure **/
    SSLEngineState* ssleState = (SSLEngineState*)sslep;   
   
    /** Point to Java ByteBuffers **/
    // jbyte* src_ptr = env->GetByteArrayElements(src, NULL);
    jbyte* dst_ptr = env->GetByteArrayElements(dst, NULL);
    
    // jsize src_len = env->GetArrayLength(src);
    // jsize dst_len = env->GetArrayLength(dst);

    jintArray engine_result = env->NewIntArray(4);  
    
    jint *e_res_ptr = env->GetIntArrayElements(engine_result, NULL);

    jint write_result = 0;
    // jint error_result = 0;
            
    if (BIO_ctrl_pending(ssleState->outputBuffer) > 0) { // Check for data in the engine
        
        write_result = BIO_write(ssleState->outputBuffer, dst_ptr, BIO_ctrl_pending(ssleState->outputBuffer));
        /*error_result =*/ SSL_get_error(ssleState->sslEngine, write_result);
        // BIO_write returns either the amount of data successfully read 
        // or written (if the return value is positive) or that no data 
        // was successfully read or written if the result is 0 or -1.

        // return write_result;
        e_res_ptr[_bytesProduced_] = write_result;

        env->ReleaseIntArrayElements(engine_result, e_res_ptr, 0);
        return engine_result;
    }

    e_res_ptr[_status_] = BUFFER_UNDERFLOW;
    e_res_ptr[_hsStatus_] = NEED_UNWRAP;
    e_res_ptr[_bytesConsumed_] = 42;
    e_res_ptr[_bytesProduced_] = 17;
    env->ReleaseIntArrayElements(engine_result, e_res_ptr, 0);
    return engine_result;    

    // ERR_print_errors_fp(stdout);
    
    

    printf("---C---javax-net-ssl_wrapData----\n");
    
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

    printf("---C---javax-net-ssl_unwrapData----\n");

    SSLEngineState* ssleState = (SSLEngineState*)sslep;

    jbyte* src_ptr = env->GetByteArrayElements(src, NULL);
    jbyte* dst_ptr = env->GetByteArrayElements(dst, NULL);

    jsize src_len = env->GetArrayLength(src);
    jsize dst_len = env->GetArrayLength(dst);

    jint bytes_decrypted = 0;

    printf("Is there anything in the engine? <in> <out> %lu , %lu\n", BIO_ctrl_pending(ssleState->inputBuffer), BIO_ctrl_pending(ssleState->outputBuffer));
    

    bytes_decrypted = BIO_write(ssleState->inputBuffer, src_ptr, src_len);

    SSL_read(ssleState->sslEngine, dst_ptr, dst_len);

    return bytes_decrypted;
}

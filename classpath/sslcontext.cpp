#include "sslcontext.h"

static SSL_CTX* create(JNIEnv* e) {
    SSL_CTX* ctx = SSL_CTX_new(SSLv23_method());

    if(ctx == NULL) {
        printf("Error Making Context\n");
        exit(1);
    }
    return ctx;
}
static void init() {
    SSL_library_init();
    SSL_load_error_strings();
    
}

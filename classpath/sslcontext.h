#include <openssl/ssl.h>

static SSL_CTX* create() {
    SSL_load_error_strings();
    SSL_library_init();
    OpenSSL_add_all_algorithms();
    SSL_CTX* ctx = SSL_CTX_new(SSLv2_method());

    if(ctx == NULL) {
        printf("ERROR Making Context\n");
        exit(1);
    }
    return ctx;
}

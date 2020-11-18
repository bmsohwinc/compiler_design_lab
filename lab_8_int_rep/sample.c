#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int varcnt = 1;

struct threeac {
    char code[200];
    char addr[10];
};

void getvarname(char *vart) {
    char currcnt[10];
    memset(vart, 0, 10);
    memset(currcnt, 0, 10);
    vart[0] = 't';
    sprintf(currcnt, "%d", varcnt);
    strcat(vart, currcnt);
    varcnt++;
}


int main() {

    struct threeac foo;
    strcpy(foo.code, "asfd");
    // addr
    char vart[10];
    getvarname(vart);
    strcpy(foo.addr, vart);

    printf("foo.code: %s\n", foo.code);
    printf("foo.addr: %s\n", foo.addr);
    getvarname(vart);
    printf("vart: %s\n", vart);
    getvarname(vart);
    printf("vart: %s\n", vart);
    getvarname(vart);
    printf("foo.addr: %s\n", vart);
    return 0;
}
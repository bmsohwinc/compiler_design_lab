%{
#include<stdio.h>    
%}

/* Patterns */
mathop [+|*]
brackets [(|)]
letter [A-Za-z]
var {letter}+
whitespaces [ \t\n]

%%

{mathop} {
    if (!strcmp(yytext, "+")) {printf("___+\n");};
    if (!strcmp(yytext, "*")) {printf("___*\n");};
    return 1;
}

{brackets} {
    if (!strcmp(yytext, "(")) {printf("___(\n");};
    if (!strcmp(yytext, ")")) {printf("___)\n");};
    return 4;
}


{var} {
    printf("__id\n");
    return 14;
}

{whitespaces} {}

. {
    printf("ERR\n");
    return 0;
}

%%

int yywrap(void) {
    return 1;
}

int main() {
    // printf("Enter strings...\n");
    int token_type_id;
    // Just comment the below line to take live input
    yyin = fopen("lex_input_1.txt", "r");
    while((token_type_id = yylex()) != 0) {
        // printf("\tTokenID = %3d\tTokenTypeID = %2d\tlexeme = %s\n", token_id, token_type_id, yytext);
    }
    
    return 0;
}
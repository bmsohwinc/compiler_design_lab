%{
#include <stdio.h>
// RELOPS
#define R_LT  1
#define R_LE  2
#define R_GT  3
#define R_GE  4
#define R_EQ  5
#define R_NE  6
// ARITHMETIC 
#define A_AD  7
#define A_SU  8
#define A_ML  9
#define A_DV  10
// SEPARATORS
#define S_SC  11
#define S_CM  12
#define S_PRO 13
#define S_PRC 14
#define S_CBO 15
#define S_CBC 16
// DATATYPE
#define D_INT 17
#define D_FLT 18
// IOS
#define I_RD  19
#define I_PRT 20
// LOOPER
#define L_FOR 21
#define L_WHL 22
// MAINFUNC
#define F_MAIN 23
// CONDITIONALS
#define C_IF  24
#define C_ELS 25
// GENERAL
#define NUMBER      26
#define IDENTIFIER  27

// IDs
int token_id = 0;
int line_number = 1;
%}

/* Patterns */
conditionals if|else
datatypes (int)|(float)
digit [0-9]
digits {digit}+
ios read|print
letter [A-Za-z]
loopers for|while
main (main)
number {digits}(.{digits})?(E[+|-]?{digits})?
operators [+|-|*|/]
relops [<|>|=|<=|>=|!=]
separators [;|,|(|)|{|}]
var {letter}({letter}|{digits})*
whitespaces [ \t]

%%
{conditionals} {
    printf("Found %12s: ", "CONDITIONAL");
    token_id++;
    if (!strcmp(yytext, "if")) return C_IF;
    if (!strcmp(yytext, "else")) return C_ELS;
}

{datatypes} {
    printf("Found %12s: ", "DATATYPE");
    token_id++;
    if (!strcmp(yytext, "int")) return D_INT;
    if (!strcmp(yytext, "float")) return D_FLT;
}

{ios} {
    printf("Found %12s: ", "IN/OUTPUT");
    token_id++;
    if (!strcmp(yytext, "read")) return I_RD;
    if (!strcmp(yytext, "print")) return I_PRT;
}

{loopers} {
    printf("Found %12s: ", "LOOPER");
    token_id++;
    if (!strcmp(yytext, "for")) return L_FOR;
    if (!strcmp(yytext, "while")) return L_WHL;
}

{main} {
    printf("Found %12s: ", "MAIN");
    token_id++;
    return F_MAIN;
}

{relops} {
    printf("Found %12s: ", "RELOP");
    token_id++;
    if (!strcmp(yytext, "<")) return R_LT;
    if (!strcmp(yytext, "<=")) return R_LE;
    if (!strcmp(yytext, ">")) return R_GT;
    if (!strcmp(yytext, ">=")) return R_GE;
    if (!strcmp(yytext, "=")) return R_EQ;
    if (!strcmp(yytext, "!=")) return R_NE;
}

{operators} {
    printf("Found %12s: ", "ARITHMETIC");
    token_id++;
    if (!strcmp(yytext, "+")) return A_AD;
    if (!strcmp(yytext, "-")) return A_SU;
    if (!strcmp(yytext, "*")) return A_ML;
    if (!strcmp(yytext, "/")) return A_DV;
}

{separators} {
    printf("Found %12s: ", "SEPARATOR");
    token_id++;
    if (!strcmp(yytext, ";")) return S_SC;
    if (!strcmp(yytext, ",")) return S_CM;
    if (!strcmp(yytext, "(")) return S_PRO;
    if (!strcmp(yytext, ")")) return S_PRC;
    if (!strcmp(yytext, "{")) return S_CBO;
    if (!strcmp(yytext, "}")) return S_CBC;
}

{number} {
    printf("Found %12s: ", "NUMBER");
    token_id++;
    return NUMBER;
}

{var} {
    printf("Found %12s: ", "IDENTIFIER");
    token_id++;
    return IDENTIFIER;
}

{whitespaces} {}

\n {
    // printf("Done with line #%2d\n", line_number++);
    line_number++;
}

. {
    printf("ERR\n");
}
%%

int yywrap(void) {
    printf("Done with %2d lines\n", line_number++);
    return 1;
}

int main() {
    printf("Enter strings...\n");
    int token_type_id;
    // Just comment the below line to take live input
    yyin = fopen("myfile.txt", "r");
    while((token_type_id = yylex()) != 0) {
        printf("\tTokenID = %3d\tTokenTypeID = %2d\tlexeme = %s\n", token_id, token_type_id, yytext);
    }
    
    return 0;
}
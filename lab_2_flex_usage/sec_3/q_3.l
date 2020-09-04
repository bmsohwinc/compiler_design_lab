%{
#include <stdio.h>
// GENERAL
#define CONDITIONAL 0
#define DATATYPE    1
#define IOS         2
#define LOOPER      3
#define MAINFUNC    4
#define RELOP       5
#define ARITHMETIC  6
#define SEPARATOR   7
#define NUMBER      8
#define IDENTIFIER  9
// RELOPS
#define R_LT  0
#define R_LE  1
#define R_GT  2
#define R_GE  3
#define R_EQ  4
#define R_NE  5
// ARITHMETIC 
#define A_AD  6
#define A_SU  7
#define A_ML  8
#define A_DV  9
// SEPARATORS
#define S_SC  10
#define S_CM  11
#define S_PRO 12
#define S_PRC 13
#define S_CBO 14
#define S_CBC 15
// IDs
int token_id = 1;
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
    printf("Found CONDITIONAL: ");
    return token_id++;
}

{datatypes} {
    printf("Found DATATYPE: ");
    return token_id++;
}

{ios} {
    printf("Found INPUT/OUTPUT: ");
    return token_id++;
}

{loopers} {
    printf("Found LOOPER: ");
    return token_id++;
}

{main} {
    printf("Found MAIN: ");
    return token_id++;
}

{relops} {
    printf("Found RELOP: ");
    return token_id++;
}

{operators} {
    printf("Found ARITHMETIC: ");
    return token_id++;
}

{separators} {
    printf("Found SEPARATOR: ");
    return token_id++;
}

{number} {
    printf("Found NUMBER: ");
    return token_id++;
}

{var} {
    printf("Found IDENTIFIER: ");
    return token_id++;
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
    int token;
    // Just comment the below line to take live input
    yyin = fopen("myfile.txt", "r");
    while((token = yylex()) != 0) {
        printf("Token_ID = %3d\tlexeme = %s\n", token, yytext);
    }
    
    return 0;
}



// switch(yytext) {
    //     case "<":
    //         return R_LT;
    //     case "<=":
    //         return R_LE;
    //     case ">":
    //         return R_GT;
    //     case ">=":
    //         return R_GE;
    //     case "=":
    //         return R_EQ;
    //     case "!=":
    //         return R_NE;
    // }

    // switch(yytext) {
    //     case "+":
    //         return A_AD;
    //     case "-":
    //         return A_SU;
    //     case "*":
    //         return A_ML;
    //     case "/":
    //         return A_DV;
    // }

// put_to_table(token_id++, yytext);
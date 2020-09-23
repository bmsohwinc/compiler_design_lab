%{
#include<stdio.h>    
%}

/* Patterns */
mathop [+|\-|*|/]
assign (:=)
relop [=|<|>]
brackets [(|)|{|}]
semicolon ;
conditional if|else|and|or|not|then
looper while|do|end
dtype int|float
ios print|scan
prog prog
digit [0-9]
ic {digit}+
fc {ic}.{ic}
letter [A-Za-z]
var {letter}({letter}|{ic})*
whitespaces [ \t\n]

%%

{mathop} {
    if (!strcmp(yytext, "+")) {printf("___+\n");};
    if (!strcmp(yytext, "-")) {printf("___-\n");};
    if (!strcmp(yytext, "*")) {printf("___*\n");};
    if (!strcmp(yytext, "/")) {printf("___/\n");};
    return 1;
}

{assign} {
    if (!strcmp(yytext, ":=")) {printf("__:=\n");};
    return 2;
}

{relop} {
    if (!strcmp(yytext, "<")) {printf("___<\n");};
    if (!strcmp(yytext, "=")) {printf("___=\n");};
    if (!strcmp(yytext, ">")) {printf("___>\n");};
    return 3;
}

{brackets} {
    if (!strcmp(yytext, "(")) {printf("___(\n");};
    if (!strcmp(yytext, ")")) {printf("___)\n");};
    if (!strcmp(yytext, "{")) {printf("___{\n");};
    if (!strcmp(yytext, "}")) {printf("___}\n");};
    return 4;
}

{semicolon} {
    if (!strcmp(yytext, ";")) {printf("___;\n");};
    return 5;
}

{conditional} {
    if (!strcmp(yytext, "if")) {printf("__if\n");};
    if (!strcmp(yytext, "then")) {printf("_thn\n");};
    if (!strcmp(yytext, "else")) {printf("_els\n");};
    if (!strcmp(yytext, "and")) {printf("_and\n");};
    if (!strcmp(yytext, "or")) {printf("__or\n");};
    if (!strcmp(yytext, "not")) {printf("_not\n");};
    return 6;
}

{looper} {
    if (!strcmp(yytext, "while")) {printf("_whl\n");};
    if (!strcmp(yytext, "do")) {printf("__do\n");};
    if (!strcmp(yytext, "end")) {printf("_end\n");};
    return 7;
}

{dtype} {
    if (!strcmp(yytext, "int")) {printf("_int\n");};
    if (!strcmp(yytext, "float")) {printf("_flt\n");};
    return 8;
}

{ios} {
    if (!strcmp(yytext, "print")) {printf("_prt\n");};
    if (!strcmp(yytext, "scan")) {printf("_scn\n");};
    return 9;
}

{prog} {
    if (!strcmp(yytext, "prog")) {printf("_prg\n");};
    return 10;
}

{ic} {
    printf("__ic\n");   
    return 11;
}

{fc} {
    printf("__fc\n");   
    return 12;
}

{var} {
    printf("__id\n");
    return 14;
}

{whitespaces} {}

. {
    printf("ERR\n");
}

%%

int yywrap(void) {
    return 1;
}

int main() {
    // printf("Enter strings...\n");
    int token_type_id;
    // Just comment the below line to take live input
    yyin = fopen("lex_input_2.txt", "r");
    while((token_type_id = yylex()) != 0) {
        // printf("\tTokenID = %3d\tTokenTypeID = %2d\tlexeme = %s\n", token_id, token_type_id, yytext);
    }
    
    return 0;
}
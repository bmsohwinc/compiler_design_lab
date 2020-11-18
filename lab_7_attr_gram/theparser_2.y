%{
    #include <stdio.h>
    #include <stdlib.h>
    #include "y.tab.h"
    #include "lex.yy.c"
    int yylex();
    void yyerror(char *);
%}

%token PLUS_SIGN MINUS_SIGN MULT_SIGN DIV_SIGN INT_CONSTANT OPEN_BRACE CLOSE_BRACE
%start mystart

%left PLUS_SIGN MINUS_SIGN
%left MULT_SIGN DIV_SIGN
%left OPEN_BRACE CLOSE_BRACE

%%

mystart : expr0 { printf("Successfully evaluated the value as %d \n", $$); return 0; }


expr0   : expr0 PLUS_SIGN term0 { $$ = $1 + $3; }
        | expr1
        ;

expr1   : expr1 MINUS_SIGN term0 { $$ = $1 - $3; }
        | term0
        ;

term0   : term0 MULT_SIGN fact { $$ = $1 * $3; }
        | term1
        ;

term1   : term1 DIV_SIGN fact { $$ = $1 / $3; }
        | fact
        ;

fact    : INT_CONSTANT
        | OPEN_BRACE expr0 CLOSE_BRACE { $$ = $2; }
        ;

%%

extern int yylex();
extern int yyparse();

void yyerror(char *s){
	printf("Error | Line: %d\n%s\n",yylineno,s);
	exit(-1);
}

int main(){
	yyparse();
	return 0;
}

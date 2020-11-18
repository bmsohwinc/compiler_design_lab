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

mystart : expr { printf("Successfully evaluated the value as %d \n", $$); return 0; }


expr    : expr PLUS_SIGN term { $$ = $1 + $3; }
        | expr MINUS_SIGN term { $$ = $1 - $3; }
        | term { $$ = $1; }
        ;

term    : term MULT_SIGN fact { $$ = $1 * $3; }
        | term DIV_SIGN fact { $$ = $1 / $3; }
        | fact { $$ = $1; }
        ;

fact    : INT_CONSTANT { $$ = $1; }
        | OPEN_BRACE expr CLOSE_BRACE { $$ = $2; }
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

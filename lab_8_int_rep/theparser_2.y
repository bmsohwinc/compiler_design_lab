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

mystart : expr { printf("\nDone building the AST\n"); return 0; }


expr   : expr PLUS_SIGN term { printf(" +"); }
        | expr MINUS_SIGN term { printf(" -"); }
        | term
        ;

term   : term MULT_SIGN fact { printf(" *"); }
        | term DIV_SIGN fact { printf(" /"); }
        | fact
        ;

fact    : INT_CONSTANT { printf(" %d", $$); }
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

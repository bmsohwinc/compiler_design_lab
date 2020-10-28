%{
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

int yylex();
void yyerror(char *s);

%}

%token DING DONG DELL
%start rhyme

%%

rhyme 
    :   sound place '\n' {printf("string valid\n"); exit(0);}
    ;
sound
    :   DING DONG
    ;
place
    :   DELL
    ;

%%

extern int yylex();
extern int yyparse();
extern int line_num;

void yyerror(char *s) {
    printf("\nError: %s  at line number %d\n", s, line_num);
    exit(-1);
}

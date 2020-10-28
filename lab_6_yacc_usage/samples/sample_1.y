%{
    #include <stdio.h>
    #include <stdlib.h>
    int yylex(void);
    int yyerror(void);
%}


%token DING DONG DELL
%start rhyme

%%

rhyme : sound place '\n' {
    printf("string valid\n");
    exit(0);
}
sound : DING DONG;
place : DELL;

%%

#include "lex.yy.c"

int yywrap() {
    return 1;
}

yyerror(char *s) {
    printf("%s\n", s);
}

main() {
    yyparse();
}
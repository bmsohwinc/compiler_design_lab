/* FIRST THE YACC. THEN DO FLEX */

%{
    #include <stdio.h>
    #include <stdlib.h>
    extern int yylex();
    extern int yyparse();
    extern FILE* yyin;
    void yyerror(char const *);
%}

%token DING DONG DELL NEWL
%start mystart

%%
mystart : mystart rhyme NEWL
        |
        ;

rhyme   : | okgo place  { printf("string is valid :)\n"); }
        ;
okgo    :   DING DONG 
        ;
place   :   DELL
        ;
%%

int main() {
    yyin = fopen("input.txt", "r");
    // yyin = stdin;
    // while(1) {
    //     printf("Enter string: ");
    //     yyparse();
    // }

    // |   NEWL { printf("newline found.\n"); }

    return yyparse();

    // do {
    //     yyparse();
    // } while(!feof(yyin));

    // return 0;
    //  exit(0);
}

void yyerror(char const* s) {
    printf("\n**********\n");
    printf("%s\n", s);
    printf("**********\n");
    // exit(1);
}
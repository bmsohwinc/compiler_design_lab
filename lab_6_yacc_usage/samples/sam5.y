%{
    #include <stdio.h>
    #include <stdlib.h>
    extern int yylex();
    extern int yyparse();
    void yyerror(char const *);
    extern int line_num;
%}

%token NUM NEWL PLUS MINUS CROSS DIV
%start mystart

%%
mystart : 
        | mystart expr NEWL { printf("Result: %d\n", $2); }
        | NEWL { printf("New line found\n"); }
        ;

expr    : expr PLUS term  { $$ = $1 + $3; } 
        | expr MINUS term  { $$ = $1 - $3; }
        | term { $$ = $1; }
        ;

term    : term CROSS fact  { $$ = $1 * $3; }
        | term DIV fact  { $$ = $1 / $3; }
        | fact { $$ = $1; }
        ;

fact    : NUM
        ;
%%

int main() {
    // yyin 
    return yyparse();
}

void yyerror(char const* s) {
    printf("\n**********\n");
    printf("%s at line number : %d\n", s, line_num);
    printf("**********\n");
    // exit(1);
}
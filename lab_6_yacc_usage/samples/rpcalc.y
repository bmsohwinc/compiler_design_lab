/* RP CALC */

%{
    #define YYSTYPE double
    #include <math.h>
    #include <stdio.h>
    #include <ctype.h>
    int yylex(void);
    void yyerror(char const *);    
%}

%token NUM

%%
input:
    | input line
;

line: '\n'
    | exp '\n' {printf("\t%.10g\n", $1);}
;
exp: NUM
    | exp exp '+' {$$ = $1 + $2;}
    | exp exp '-' {$$ = $1 - $2;}
;
%%


/* Lex analyzer here onwards */

int yylex(void) {
    int c;
    while((c = getchar()) == ' ' || c == '\t')
        ;
    if (c == '.' || isdigit(c)) {
        ungetc (c, stdin);
        scanf ("%lf", &yylval);
        return NUM;
    }

    if (c == EOF) {
        return 0;
    }

    return c;
}

void yyerror(char const *s) {
    fprintf(stderr, "%s\n", s);
}

int main(void) {
    return yyparse();
}
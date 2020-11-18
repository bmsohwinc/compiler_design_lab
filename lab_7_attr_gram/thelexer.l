%{
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    extern int yylval;
%}

%option yylineno
digit [0-9]+
float digit.digit

%%

[ ]*	{}
\n      { return 0; }
"+"             { return PLUS_SIGN;    }
"-"             { return MINUS_SIGN;   }
"*"             { return MULT_SIGN;    }
"/"             { return DIV_SIGN;     } 
"("             { return OPEN_BRACE; }
")"             { return CLOSE_BRACE; }
{digit}  { yylval = atoi(yytext); return INT_CONSTANT; }

%%

int yywrap(void) { return 1; }

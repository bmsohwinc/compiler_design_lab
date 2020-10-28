%{
    #include <stdio.h>
    #include <stdlib.h>
    #include "y.tab.h"
    int line_num = 1;
%}

digit [0-9]
number digit+

%%
[ ]*        {}
{number}    { return NUM; }
\n          { line_num++; return NEWL;  }
"+"          { return PLUS; }
"-"           { return MINUS; }
"*"          { return CROSS; }
"/"          { return DIV; }
.          { return yytext[0]; }
%%

int yywrap() { return 1; }
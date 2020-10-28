/* %option noyywrap */

%{
    #include <stdio.h>
    #include "y.tab.h"    
%}

%%
"ding"    { return DING; }
"dong"    { return DONG; }
"dell"    { return DELL; }
[ ]*      {  }
\n        { return NEWL; }
.         { return yytext[0]; }
%%

int yywrap() { return 1; }
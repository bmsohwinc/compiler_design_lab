%{
    #include<stdio.h>
%}

regex1 (01*0)*
div_by_3 (0|(1{regex1}1))*
white_spaces [ \t]

%%
{div_by_3} {
    printf("String divisible by 3 detected for %s\n", yytext);
    ;
}

{white_spaces} {
    printf("Whitespace Detected\n");
}

. {
    printf("ERR for string: %s\n", yytext);
    ;
}
%%

int yywrap(void) {
    return 1;
}

int main() {
    printf("-- DETECT BINARY STRINGS WHOSE INTEGER EQUI IS DIVISIBLE BY 3 --\n");
    printf("Enter Binary strings...\n");
    yylex();
    return 0;
}
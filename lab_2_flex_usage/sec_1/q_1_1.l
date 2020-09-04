%{
    #include <stdio.h>
%}
even_zeros (1|01*0)*
white_spaces [ \t]

%%
{even_zeros} {
    printf("Even Zeros Detected for string %s!\n", yytext);
}

{white_spaces} {
    printf("Whitespace Detected\n");
}

. {
    printf("ERR for string: %s\n", yytext);
}

%%

int yywrap(void) {
    return 1;
}

int main() {
    printf("-- DETECT BINARY STRINGS WITH EVEN NUMBER OF 0s --\n");
    printf("Enter Binary strings...\n");
    yylex();
    return 0;
}
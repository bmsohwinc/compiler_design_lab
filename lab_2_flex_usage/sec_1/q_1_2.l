%{
    #include<stdio.h>
%}

regex1 (00|(01(11)*01))
regex2 (1|(01(11)*0))
regex3 (0(11)*0)*
regex4 (1|(0(11)*01))
even0_even1 ({regex1}|({regex2}{regex3}{regex4}))*
e0e1 (00|11|(01|10)(00|11)*(01|10))*
white_spaces [ \t]

%%
{e0e1} {
    printf("Even 0 Even 1 detected for %s\n", yytext);
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
    printf("-- DETECT BINARY STRINGS WITH EVEN NUMBER OF 0s AND EVEN NUMBER OF 1s --\n");
    printf("Enter Binary strings...\n");
    yylex();
    return 0;
}
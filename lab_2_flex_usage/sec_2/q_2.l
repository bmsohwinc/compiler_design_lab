%{
    #include<stdio.h>
    int line_number = 1;
    #define ERR -1
%}

/* Patterns */
delim [ \t\n]
whitespace [ \t]
digit [0-9]
digits ({digit})+
number {digits}(.{digits})?(E[+|-]?{digits})?
letter [A-Za-z]
id {letter}({letter}|{digits})*
if if
then then
else else
relop <|>|<=|>=|=|<>

%%
{number} {
    return 1;
}
{if}|{then}|{else} {
    
    return 2;
}
{id} {
    
    return 3;
}
{relop} {
    
    return 4;
}

{whitespace} {}

\n {
    printf("Done with line_number: %d\n", line_number++);
}

. {
    printf("ERR\n");
    return ERR;
}
%%

/*
printf("Number found: %s\n", yytext);
printf("Keyword found: %s\n", yytext);
printf("Identifier found: %s\n", yytext);
printf("Relop found: %s\n", yytext);
*/

int yywrap(void) {
    printf("Done with line_number: %d\n", line_number++);
    return 1;
}

int main() {
    printf("Enter strings...\n");
    char tokentype[][20] = {
        "UNDEFINED",
        "JUNK",
        "NUMBER",
        "KEYWORD",
        "IDENTIFIER",
        "RELOP"
    };
    
    int token;
    // Just comment this line to take live input
    yyin = fopen("myfile.txt", "r");
    while((token = yylex()) != 0) {
        printf("Found token = %2d %10s |\tlexeme = %s\n", token, tokentype[token+1], yytext);
    }
    
    return 0;
}
/*
{newline} {
    printf("Done with line_number: %d\n", line_number++);
}*/
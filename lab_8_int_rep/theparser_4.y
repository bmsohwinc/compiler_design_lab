%{
    /**
    *   SEMANTICALLY BUILDING A 3-ADDRESS CODE.
    */

    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    #include "y.tab.h"
    #include "lex.yy.c"

    #define NICE printf("\n=================================\n")
    #define PIPES " || "
    #define NEWL "\n"

    int var_count = 1;

    void getNewVar(char * temp);
    void makeNewCode(char *, char *, char *, char *, char *, char *, char *);
    void printThreeAC(char *);

    int yylex();
    void yyerror(char *);
%}

%token PLUS_SIGN MINUS_SIGN MULT_SIGN DIV_SIGN OPEN_BRACE CLOSE_BRACE INT_CONSTANT
%start mystart

%left PLUS_SIGN MINUS_SIGN
%left MULT_SIGN DIV_SIGN
%left OPEN_BRACE CLOSE_BRACE

%union {
    struct threeac {
        char the_code[300];
        char the_addr[10];
    } tac;

    int val;
};

%type <tac> mystart expr term fact
%type <val> INT_CONSTANT

%%

mystart : expr { printf("\nDone generating the 3 AC\n"); printThreeAC($1.the_code); return 0; }

expr   : expr PLUS_SIGN term {  
            char temp[10], newC[300];
            getNewVar(temp);
            strcpy($$.the_addr, temp);
            makeNewCode(newC, temp, " + ", $1.the_addr, $3.the_addr, $1.the_code, $3.the_code);
            strcpy($$.the_code, newC);
        }
        | expr MINUS_SIGN term { 
            char temp[10], newC[300];
            getNewVar(temp);
            strcpy($$.the_addr, temp);
            makeNewCode(newC, temp, " - ", $1.the_addr, $3.the_addr, $1.the_code, $3.the_code);
            strcpy($$.the_code, newC);
        }
        | term { strcpy($$.the_addr, $1.the_addr); strcpy($$.the_code, $1.the_code); }
        ;

term   : term MULT_SIGN fact { 
            char temp[10], newC[300];
            getNewVar(temp);
            strcpy($$.the_addr, temp);
            makeNewCode(newC, temp, " * ", $1.the_addr, $3.the_addr, $1.the_code, $3.the_code);
            strcpy($$.the_code, newC);
        }
        | term DIV_SIGN fact { 
            char temp[10], newC[300];
            getNewVar(temp);
            strcpy($$.the_addr, temp);
            makeNewCode(newC, temp, " / ", $1.the_addr, $3.the_addr, $1.the_code, $3.the_code);
            strcpy($$.the_code, newC);
        }
        | fact { strcpy($$.the_addr, $1.the_addr); strcpy($$.the_code, $1.the_code); }
        ;

fact    : INT_CONSTANT { 
            strcpy($$.the_code, "");
            char temp[10];
            memset(temp, 0, 10);
            sprintf(temp, "%d", $1);
            strcpy($$.the_addr, temp);
        }
        | OPEN_BRACE expr CLOSE_BRACE { strcpy($$.the_addr, $2.the_addr); strcpy($$.the_code, $2.the_code); }
        ;
%%

extern int yylex();
extern int yyparse();

void printThreeAC(char *theCode) {
    NICE;
    printf("%s\n", theCode);
    NICE;
}

void makeNewCode(char *newC, char *temp, char *op, char *left_ad, char *right_ad, char *left_code, char *right_code ) {
    char newCode[300];
    memset(newCode, 0, 300);
    strcpy(newCode, temp);
    strcat(newCode, " = ");
    strcat(newCode, left_ad);
    strcat(newCode, op);
    strcat(newCode, right_ad);

    memset(newC, 0, 300);
    strcpy(newC, left_code);
    if (strcmp(left_code, "")) {
        // strcat(newC, PIPES);
        strcat(newC, NEWL);
    }
    strcat(newC, right_code);
    if (strcmp(right_code, "")) {
        // strcat(newC, PIPES);
        strcat(newC, NEWL);
    }
    strcat(newC, newCode);
    strcat(newC, NEWL);
}

void getNewVar(char * temp) {
    char currcnt[10];
    memset(temp, 0, 10);
    memset(currcnt, 0, 10);
    sprintf(currcnt, "%d", var_count);
    temp[0] = 't';
    strcat(temp, currcnt);
    var_count++;
}


void yyerror(char *s) {
	printf("Error | Line: %d\n%s\n",yylineno,s);
	exit(-1);
}

int main() {
	yyparse();
	return 0;
}

/*
REFERENCES:
1. https://stackoverflow.com/questions/4287883/use-yylval-in-bison-to-recover-a-string
2. https://stackoverflow.com/questions/1014619/how-to-solve-bison-warning-has-no-declared-type
3. https://cs.gmu.edu/~white/CS540/Slides/Semantic/CS540-2-lecture6.pdf
4. https://stackoverflow.com/a/8257728
5. https://stackoverflow.com/questions/21610637/declaration-does-not-declare-anything-warning

*/
%{
    /**
    *   SEMANTICALLY BUILDING AN AST.
    */

    #include <stdio.h>
    #include <stdlib.h>
    #include "y.tab.h"
    #include "lex.yy.c"

    #define NICE printf("\n=================================\n")
    #define MAXNODES 100
    
    int node_data[MAXNODES][4]; /* data - left - right - ischar */
    int node_count = 1;

    void printAST();
    void addNode(int data, int left, int right, int isChar);

    int yylex();
    void yyerror(char *);
%}

%token PLUS_SIGN MINUS_SIGN MULT_SIGN DIV_SIGN INT_CONSTANT OPEN_BRACE CLOSE_BRACE
%start mystart

%left PLUS_SIGN MINUS_SIGN
%left MULT_SIGN DIV_SIGN
%left OPEN_BRACE CLOSE_BRACE

%%

mystart : expr { printf("\nDone building the AST\n"); printAST(); return 0; }


expr   : expr PLUS_SIGN term { $$ = node_count; addNode((int)'+', $1, $3, 1);}
        | expr MINUS_SIGN term { $$ = node_count; addNode((int)'-', $1, $3, 1);}
        | term { $$ = $1; }
        ;

term   : term MULT_SIGN fact { $$ = node_count; addNode((int)'*', $1, $3, 1); }
        | term DIV_SIGN fact { $$ = node_count; addNode((int)'/', $1, $3, 1); }
        | fact { $$ = $1; }
        ;

fact    : INT_CONSTANT { $$ = node_count; addNode($1, 0, 0, 0); }
        | OPEN_BRACE expr CLOSE_BRACE { $$ = $2; }
        ;

%%

extern int yylex();
extern int yyparse();

void addNode(int data, int left, int right, int isChar) {
    node_data[node_count][0] = data;
    node_data[node_count][1] = left;
    node_data[node_count][2] = right;
    node_data[node_count][3] = isChar;
    
    if (isChar) {
        printf(" %c (node: %d) ", data, node_count);
    }
    else {
        printf(" %d (node: %d) ", data, node_count);
    }
    
    node_count++;
}

void printAST() {
    NICE;
    printf("The AST is: \n");
    for (int i = 1; i < node_count; i++) {
        printf("Node #%d:\n", i);
        if (node_data[i][3]) {
            printf("\tData: %c\n", node_data[i][0]);
        }
        else {
            printf("\tData: %d\n", node_data[i][0]);
        }
        printf("\tLeft: %2d\tRight: %2d\n", node_data[i][1], node_data[i][2]);
    }
    NICE;
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
expr   : expr PLUS_SIGN term { $$ = node_count; node_data[node_count][0] = (int)'+'; node_data[node_count][1] = $1; node_data[node_count][2] = $3; node_data[node_count][3] = 1; printf(" + (node: %d) ", node_count); node_count++;}
        | expr MINUS_SIGN term { $$ = node_count; node_data[node_count][0] = (int)'-'; node_data[node_count][1] = $1; node_data[node_count][2] = $3; node_data[node_count][3] = 1; printf(" - (node: %d) ", node_count); node_count++;}
        | term { $$ = $1; }
        ;

term   : term MULT_SIGN fact { $$ = node_count; node_data[node_count][0] = (int)'*'; node_data[node_count][1] = $1; node_data[node_count][2] = $3; node_data[node_count][3] = 1; printf(" * (node: %d) ", node_count); node_count++; }
        | term DIV_SIGN fact { $$ = node_count; node_data[node_count][0] = (int)'/'; node_data[node_count][1] = $1; node_data[node_count][2] = $3; node_data[node_count][3] = 1; printf(" / (node: %d) ", node_count); node_count++; }
        | fact { $$ = $1; }
        ;

fact    : INT_CONSTANT { $$ = node_count; node_data[node_count][0] = $1; node_data[node_count][1] = node_data[node_count][2] = 0; node_data[node_count][3] = 0; printf(" %d (node: %d) ", $1, node_count); node_count++; }
        | OPEN_BRACE expr CLOSE_BRACE { $$ = $2; }
        ;

*/
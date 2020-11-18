%{
    /**
    *   SEMANTICALLY BUILDING A DAG.
    */

    #include <stdio.h>
    #include <stdlib.h>
    #include "y.tab.h"
    #include "lex.yy.c"

    #define NICE printf("\n=================================\n")
    #define MAXNODES 100
    
    int node_data[MAXNODES][4]; /* data - left - right - ischar */
    int node_count = 0;
    int time_stamp = 1;

    int addNode(int data, int left, int right, int isChar);
    int nodeIsPresent(int data, int left, int right);
    void printDAG();
    void justPrint(int prodNum, char x, int l, int r);
    

    int yylex();
    void yyerror(char *);
%}

%token PLUS_SIGN MINUS_SIGN MULT_SIGN DIV_SIGN INT_CONSTANT OPEN_BRACE CLOSE_BRACE
%start mystart

%left PLUS_SIGN MINUS_SIGN
%left MULT_SIGN DIV_SIGN
%left OPEN_BRACE CLOSE_BRACE

%%

mystart : expr { printf("\nDone building the DAG\n"); printDAG(); return 0; }


expr   : expr PLUS_SIGN term { justPrint(1, '+', $1, $3); $$ = addNode((int)'+', $1, $3, 1); }
        | expr MINUS_SIGN term { justPrint(2, '-', $1, $3); $$ = addNode((int)'-', $1, $3, 1); }
        | term { justPrint(3, '.', $1, $1); $$ = $1; }
        ;

term   : term MULT_SIGN fact { justPrint(4, '*', $1, $3); $$ = addNode((int)'*', $1, $3, 1); }
        | term DIV_SIGN fact { justPrint(5, '/', $1, $3); $$ = addNode((int)'/', $1, $3, 1); }
        | fact { justPrint(6, '.', $1, $1); $$ = $1; }
        ;

fact    : INT_CONSTANT { justPrint(7, '.', $1, $1); $$ = addNode($1, 0, 0, 0); }
        | OPEN_BRACE expr CLOSE_BRACE { justPrint(8, '.', $1, $1); $$ = $2; }
        ;

%%

extern int yylex();
extern int yyparse();

void justPrint(int prodNum, char x, int l, int r) {
    switch(prodNum) {
        case 1:
            printf("TS: %2d\t", time_stamp++);
            printf("expr --> expr + term; %c, %d, %d\n", x, l, r); 
            break;  
        case 2:
            printf("TS: %2d\t", time_stamp++);
            printf("expr --> expr - term; %c, %d, %d\n", x, l, r); 
            break;
        case 3:
            printf("TS: %2d\t", time_stamp++);
            printf("expr --> term; %d\n", l); 
            break;
        case 4:
            printf("TS: %2d\t", time_stamp++);
            printf("term --> term * fact; %c, %d, %d\n", x, l, r); 
            break;
        case 5:
            printf("TS: %2d\t", time_stamp++);
            printf("term --> term / fact; %c, %d, %d\n", x, l, r); 
            break;
        case 6:
            printf("TS: %2d\t", time_stamp++);
            printf("term --> fact; %d\n", l); 
            break;
        case 7:
            printf("TS: %2d\t", time_stamp++);
            printf("fact --> INT; %d\n", l); 
            break;
        case 8:
            printf("TS: %2d\t", time_stamp++);
            printf("fact --> (expr); %d\n", l); 
            break;
    }
}


int nodeIsPresent(int data, int left, int right) {
    for (int i = 1; i <= node_count; i++) {
        if ((node_data[i][0] == data) && (node_data[i][1] == left) && (node_data[i][2] == right)) {
            return i;
        }
    }

    return 0;
}

int addNode(int data, int left, int right, int isChar) {

    int tempId = nodeIsPresent(data, left, right);
    if (tempId) {
        printf("--- Node already found @ Node #%d, for data: %2d, left = %2d, right = %2d\n", tempId, data, left, right);
        return tempId;
    }

    node_count++;

    node_data[node_count][0] = data;
    node_data[node_count][1] = left;
    node_data[node_count][2] = right;
    node_data[node_count][3] = isChar;
    
    // if (isChar) {
    //     printf(" %c (node: %d) ", data, node_count);
    // }
    // else {
    //     printf(" %d (node: %d) ", data, node_count);
    // }
    return node_count;
}

void printDAG() {
    NICE;
    printf("The DAG is: \n");
    for (int i = 1; i <= node_count; i++) {
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
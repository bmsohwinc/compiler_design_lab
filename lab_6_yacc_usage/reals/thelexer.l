%{
	#include<stdio.h>
	#include<stdlib.h>
	#include<string.h>
%}

%option yylineno 

%%

"and"						{return AND;}
":="						{return ASSIGN;}
":"							{return COLON;}
","							{return COMMA;}
"def"						{return DEF;}
"/"							{return DIV;}
"."							{return DOT;}
"else"						{return ELSE;}
"end"						{return END;}
"="							{return EQ;}
"exit"						{return EXITLOOP;}
"float"						{return FLOAT;}
"from"						{return FROM;}
"fun"						{return FUN;}
">="						{return GE;}
"global"					{return GLOBAL;}
">"							{return GT;}
"if"						{return IF;}
"int"						{return INT;}
"("							{return LEFT_PAREN;}
"["							{return LEFT_SQ_BKT;}
"<="						{return LE;}
"<"							{return LT;}
"-"							{return MINUS;}
"mod"						{return MOD;}
"*"							{return MULT;}
"<>"						{return NE;}
"not"						{return NOT;}
"null"						{return NUL;}
"or"						{return OR;}
"+"							{return PLUS;}
"print"						{return PRINT;}
"product"					{return PRODUCT;}
"read"						{return READ;}
"return"					{return RETURN;}
"->"						{return RETURNS;}
")"							{return RIGHT_PAREN;}
"]"							{return RIGHT_SQ_BKT;}
";"							{return SEMICOLON;}
"skip"						{return SKIP;}
"step"						{return STEP;}
"to"						{return TO;}
"while"						{return WHILE;}
[a-zA-Z][a-zA-Z0-9]+		{return ID;}
\".*\"						{return STRING;}
[0-9]+						{return INT_CONST;}
[0-9]+[.][0-9]+				{return FLOAT_CONST;}
"%d"|"%f"|"%s"				{return FORMAT;}
"//".*						{}
[" ""\n""\t"]				{}

%%

int yywrap(void) {return 1;}
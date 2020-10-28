## Steps to write a simple parser with Bison

### Write the Bison file first
1. Declarations Section
    - Include the headers
    - declare yylex()
    - declare yyerror

2. Token and Start
    - Enter the %token and %start 
    - Define precedences with the %left, etc options

3. Rules Section
    - Terminal : Productions {Actions}

4. Extras Section
    - Declare yylex(), yyparse() as externs
    - Define yyerror()
    - Define main(): simply put the yyparse() 

### Write the Flex file next
1. Declarations Section
    - Include headers
    - Include "y.tab.h" file
    - define any var you want

2. Rules Section
    - Define the pattern and return type (return TOKENs)

3. Extras Section
    - Define the yywrap()

### Running them
1. Run: flex file.l
2. Run: bison -dv file.y
3. Rename tab.c and tab.h files
4. Run: gcc lex.yy.c y.tab.c
5. Run: ./a.out



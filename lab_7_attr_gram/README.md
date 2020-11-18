# ATTRIBUTED GRAMMAR USING FLEX-BISON

## File Structure:
1. `thelexer.l`
    - Has all the pattern definitions for the tokens
    - Provides the tokens to the Parser during the execution

2. `theparser_2.y`
    - Has the CFG Rules in YACC format
    - All tokens are mentioned
    - Has the main function as the controller
    - Has the definitions for yyerror(), 

3. `runner.sh`
    - Automates the running of `lex`, `yacc` and final linking and evaluation

## Executing
1. Just run `./runner.sh` in a terminal
    - Provide the input in the line according to the grammar:
    ```
    E  -->  E  +  T 
    E  -->  E  -  T
    E  -->  T
    
    T  -->  T  *  F
    T  -->  T  /  F
    T  -->  F

    F  -->  (E)
    F  -->  NUM
    ```
    - Outputs the calculated result in case of correct syntax, OR
    - `Syntax Error` in case of an error in input

## Test Runs
1.  Input: `5+(9/3)*26`

    Output: `Successfully evaluated the value as 83`

2.  Input: `1  - 23`

    Output: `Successfully evaluated the value as -22`

3.  Input : `11 ** 2`

    Output: `Error | Line: 1 syntax error`

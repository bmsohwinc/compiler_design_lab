# BASIC LEX AND YACC TOOL USAGE    

## File Structure:
1. `thelexer.l`
    - Has all the pattern definitions for the tokens
    - Provides the tokens to the Parser during the execution

2. `theparser.y`
    - Has the CFG Rules in YACC format
    - All tokens are mentioned
    - Has the main function as the controller
    - Has the definitions for yyerror(), 

3. `inputx.txt`
    - Sample input files to test the Lex+Parser upon

4. `runner.sh`
    - Automates the running of `lex`, `yacc` and final linking and evaluation

## Executing
1. Provide the desired input file's name in the main function in the `theparser.y` file
2. Then just run `./runner.sh` in a terminal and it will display   
    - `String valid` in case of correct syntax, OR
    - `Syntax Error` along with line number where it occured

## Test Runs
1. `input1.txt` had correct syntax
2. `input2.txt` had incorrect syntax at line number `13`
    - Unnecessary `semicolon` at the end of statement
3. `input3.txt` had incorrect syntax at line number `34`
    - Missing `colon` in the assignment symbol
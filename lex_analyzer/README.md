# BUILD A LEXICAL ANALYZER

## Description
1. Build a simple lexical analyzer based on the following patterns:
    - **Digit** : *[0 - 9]*
    - **Digits**: *Digit+*
    - **Number**: *Digits(.Digits)?(E[+-]?digits)?*
    - **Letter**: *[A-Za-z]*
    - **Identifier**: *Letter(Letter|Digit)**
    - **Keywords**
        - **if**: *if*
        - **then**: *then*
        - **else**: *else*
    - **Relop**: *(< | > | <= | >= | = | <>)*

2. Given a statement, the program should output the list of tokens associated with each *lexeme*

## File Description
1. **LexAnalyzer.java**
    - The main code that tokenizes the given statement into the lexemes and their categories.
2. **sample_inputs.txt/outputs.txt**
    - A sample set of inputs and corresponding outputs
3. **runner.sh**
    - A script to compile and run the Java code

## Running
1. Using the *runnser.sh* file, 
    - in the file, uncomment the appropriate line as needed
    - then just give the execution permissions as `chmod +x runner.sh` 
    - and then, run `./runner.sh` and the code will be automatically compiled and executed, prompting for input.
2. If not for the script, then simply run the below commands and it will start the prompt
```
    $ javac LexAnalyzer.java
    $ java LexAnalyzer
```
3. Once code starts running, it prompts for a statement.
4. And given the statement, the code tokenizes the statement into the above mentioned groups and reports the same

## Sample Run
- Input:
```
    if i.23>90 then out2=12E+34
```
- Output:
```
-------- RUN #1 --------
Your statment (Enter `!` (exclamation) to exit): if i.23>90 then out2=12E+34
[ T ] Keyword detected
[ T ] Identifier detected
[ T ] Number detected
[ T ] Relop detected
[ T ] Number detected
[ T ] Keyword detected
[ T ] Identifier detected
[ T ] Relop detected
[ T ] Number detected
Tokens for the statement [ if i.23>90 then out2=12E+34 ] are: 
[ keyword_if, if ]
[ identifier, i ]
[ undefined, . ]
[ number, 23 ]
[ relop_GT, > ]
[ number, 90 ]
[ keyword_then, then ]
[ identifier, out2 ]
[ relop_EQ, = ]
[ number, 12E+34 ]
====================================

Your statment (Enter `!` (exclamation) to exit): !
```
# INTERMEDIATE REPRESENTATIONS (IR)

### File Descriptions
1. `thelexer.l`
    - Tokenizer for IRs AST and DAG

2. `thelexer_4.l`
    - Tokenizer for IR 3AC

3. `theparser_x.y`
    - `x = 2`
        - A trial parser that prints the AST IR in postfix notation
    - `x = 3`
        - Prints the AST IR in node structure
    - `x = 4`
        - Prints the 3AC IR
    - `x = 5`
        - Prints the DAG IR

4. `runner.sh`  
    - Auto runs the lexer and parser

### Running
Just execute the script in the directory as `./runner.sh`.

It provides options to choose the IR to be tested. Once chosen, the respective lexer and parser is compiled and run, prompting you to enter the expression to be parsed.

The grammar to be respected by the expression is:
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

### Sample Runs
1. For AST IR:
```
ir$ ./runner.sh 
Available InterRep:
        1. AST
        2. 3AC
        3. DAG

Enter the InterRep number you want to see: 
1
======== Done with Lex
======== Done with Yacc
======== Done renaming the generated .h and .c files
======== Done with GCC
Enter the expression now: 
1*2+1*2+(1*2)
 1 (node: 1)  2 (node: 2)  * (node: 3)  1 (node: 4)  2 (node: 5)  * (node: 6)  + (node: 7)  1 (node: 8)  2 (node: 9)  * (node: 10)  + (node: 11) 
Done building the AST

=================================
The AST is: 
Node #1:
        Data: 1
        Left:  0        Right:  0
Node #2:
        Data: 2
        Left:  0        Right:  0
Node #3:
        Data: *
        Left:  1        Right:  2
Node #4:
        Data: 1
        Left:  0        Right:  0
Node #5:
        Data: 2
        Left:  0        Right:  0
Node #6:
        Data: *
        Left:  4        Right:  5
Node #7:
        Data: +
        Left:  3        Right:  6
Node #8:
        Data: 1
        Left:  0        Right:  0
Node #9:
        Data: 2
        Left:  0        Right:  0
Node #10:
        Data: *
        Left:  8        Right:  9
Node #11:
        Data: +
        Left:  7        Right: 10

=================================
ir$ 
```

2. For 3AC IR
```
ir$ ./runner.sh 
Available InterRep:
        1. AST
        2. 3AC
        3. DAG

Enter the InterRep number you want to see: 
2
======== Done with Lex
======== Done with Yacc
======== Done renaming the generated .h and .c files
======== Done with GCC
Enter the expression now: 
1*2+1*2+(1*2)

Done generating the 3 AC

=================================
t1 = 1 * 2

t2 = 1 * 2

t3 = t1 + t2

t4 = 1 * 2

t5 = t3 + t4


=================================
ir$ 
```

3. For DAG IR
```
ir$ ./runner.sh 
Available InterRep:
        1. AST
        2. 3AC
        3. DAG

Enter the InterRep number you want to see: 
2
======== Done with Lex
======== Done with Yacc
======== Done renaming the generated .h and .c files
======== Done with GCC
Enter the expression now: 
1*2+1*2+(1*2)

Done generating the 3 AC

=================================
t1 = 1 * 2

t2 = 1 * 2

t3 = t1 + t2

t4 = 1 * 2

t5 = t3 + t4


=================================
ir$ 
```
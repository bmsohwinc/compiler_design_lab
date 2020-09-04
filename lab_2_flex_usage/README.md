# USE FLEX TOOL TO BUILD LEXICAL ANALYZER

## File Description
1. **sec_1/**
    - Contains the basic programs of flex usage
    
        a. **even0.l**

        - Generates LexA to tokenize binary strings containing even number of 0s

        b. **even0_even1.l**

        - Generates LexA to tokenize binary strings containing even number of 0s and even number of 1s

        c. **div_by_3.l**
    
        - Generates LexA to tokenize binary strings that in the decimal form are divisible by 3

2. **sec_2**/
    - Contains basic flex usage for identifying and tokeninzing keywords, numbers, identifiers, etc

3. **sec_3**/
    - Contains basic flex usage for tokenizing strings which are subset of C language
    - To recognize
        - main() function
        - separators
        - data types : int, float
        - loops : for, while
        - input-output : read, print
        - numbers
        - identifiers
        - arithmetic and relational operators
4. Both **sec_2** and **sec_3** contain
    - **main.l** : has the overall lex code
    - **myfile.txt** : sample input set
    - **output.txt** : sample output set
5. **runner.sh**
    - To run and test the lex programs
6. **cleaner.sh**
    - To remove residual and auto generated files

## Running
1. Just run the `runner.sh` script
2. It asks for the section and subsection numbers. And they can be any of:
    - 1, 1
    - 1, 2
    - 1, 3
    - 2, 1
    - 2, 1
3. After entering, the corresponding lex code is executed and the Analyzer begins
4. For **Section 1**, user can give inputs thru command line itself.
5. But for **Section 2** and **3**, user can give inputs in the `myfile.txt` files in the respective dirs. Or, the user can comment apt lines in the `main.l` file and then provide input thru command line

## Sample Run
### Section 1 LexA
1. **Even # of 0s** 
```
Enter section(1, 2, ..) and subsection(1, 2, ...): 1 1
You entered: 1 and 1
-- DETECT BINARY STRINGS WITH EVEN NUMBER OF 0s --
Enter Binary strings...
1
Even Zeros Detected for string 1!

0101
Even Zeros Detected for string 0101!

100000
Even Zeros Detected for string 10000!
ERR for string: 0

0
ERR for string: 0

10
Even Zeros Detected for string 1!
ERR for string: 0

00
Even Zeros Detected for string 00!
```
2. **Even # of 0s and Even # of 1s** 
```
Enter section(1, 2, ..) and subsection(1, 2, ...): 1 2
You entered: 1 and 2
-- DETECT BINARY STRINGS WITH EVEN NUMBER OF 0s AND EVEN NUMBER OF 1s --
Enter Binary strings...
1010
Even 0 Even 1 detected for 1010

10101010
Even 0 Even 1 detected for 10101010

00
Even 0 Even 1 detected for 00

11
Even 0 Even 1 detected for 11

1
ERR for string: 1

0
ERR for string: 0

10100000
Even 0 Even 1 detected for 10100000

100101
Even 0 Even 1 detected for 1001
ERR for string: 0
ERR for string: 1
```
3. **Decimal format divisible by 3**
```
Enter section(1, 2, ..) and subsection(1, 2, ...): 1 3
You entered: 1 and 3
-- DETECT BINARY STRINGS WHOSE INTEGER EQUI IS DIVISIBLE BY 3 --
Enter Binary strings...
0
String divisible by 3 detected for 0

1
ERR for string: 1

1001
String divisible by 3 detected for 1001

11011
String divisible by 3 detected for 11011

10
ERR for string: 1
String divisible by 3 detected for 0

100000000
ERR for string: 1
String divisible by 3 detected for 00000000

10011001
String divisible by 3 detected for 10011001

101
ERR for string: 1
String divisible by 3 detected for 0
ERR for string: 1
```

### Section 2 LexA
1. **Simple branching statements**
- Input
```
if not this then45
this
then
taht1
if input = 12.1E then output = 2E2
else if input = 9 then output = 87
```
- Output
```
Enter strings...
Found token =  2    KEYWORD |	lexeme = if
Found token =  3 IDENTIFIER |	lexeme = not
Found token =  3 IDENTIFIER |	lexeme = this
Found token =  3 IDENTIFIER |	lexeme = then45
Done with line_number: 1
Found token =  3 IDENTIFIER |	lexeme = this
Done with line_number: 2
Found token =  2    KEYWORD |	lexeme = then
Done with line_number: 3
Found token =  3 IDENTIFIER |	lexeme = taht1
Done with line_number: 4
Found token =  2    KEYWORD |	lexeme = if
Found token =  3 IDENTIFIER |	lexeme = input
Found token =  4      RELOP |	lexeme = =
Found token =  1     NUMBER |	lexeme = 12.1
Found token =  3 IDENTIFIER |	lexeme = E
Found token =  2    KEYWORD |	lexeme = then
Found token =  3 IDENTIFIER |	lexeme = output
Found token =  4      RELOP |	lexeme = =
Found token =  1     NUMBER |	lexeme = 2E2
Done with line_number: 5
Found token =  2    KEYWORD |	lexeme = else
Found token =  2    KEYWORD |	lexeme = if
Found token =  3 IDENTIFIER |	lexeme = input
Found token =  4      RELOP |	lexeme = =
Found token =  1     NUMBER |	lexeme = 9
Found token =  2    KEYWORD |	lexeme = then
Found token =  3 IDENTIFIER |	lexeme = output
Found token =  4      RELOP |	lexeme = =
Found token =  1     NUMBER |	lexeme = 87
Done with line_number: 6
```

### Section 3 LexA
1. **C Language Subset LexA**
- Input
```
main() {
    int i = 0;
    int n = 10;
    int sum = 0;
    float op = 1.5;

    for (i = 0; i < n; i++) {
        read(x);
        sum += x;
        op /= (2.1E-5*        op);
    }

    print(x);
}
```
- Output
```
Enter strings...
Found MAIN: Token_ID =   1	lexeme = main
Found SEPARATOR: Token_ID =   2	lexeme = (
Found SEPARATOR: Token_ID =   3	lexeme = )
Found SEPARATOR: Token_ID =   4	lexeme = {
Found DATATYPE: Token_ID =   5	lexeme = int
Found IDENTIFIER: Token_ID =   6	lexeme = i
Found RELOP: Token_ID =   7	lexeme = =
Found NUMBER: Token_ID =   8	lexeme = 0
Found SEPARATOR: Token_ID =   9	lexeme = ;
Found DATATYPE: Token_ID =  10	lexeme = int
Found IDENTIFIER: Token_ID =  11	lexeme = n
Found RELOP: Token_ID =  12	lexeme = =
Found NUMBER: Token_ID =  13	lexeme = 10
Found SEPARATOR: Token_ID =  14	lexeme = ;
Found DATATYPE: Token_ID =  15	lexeme = int
Found IDENTIFIER: Token_ID =  16	lexeme = sum
Found RELOP: Token_ID =  17	lexeme = =
Found NUMBER: Token_ID =  18	lexeme = 0
Found SEPARATOR: Token_ID =  19	lexeme = ;
Found DATATYPE: Token_ID =  20	lexeme = float
Found IDENTIFIER: Token_ID =  21	lexeme = op
Found RELOP: Token_ID =  22	lexeme = =
Found NUMBER: Token_ID =  23	lexeme = 1.5
Found SEPARATOR: Token_ID =  24	lexeme = ;
Found LOOPER: Token_ID =  25	lexeme = for
Found SEPARATOR: Token_ID =  26	lexeme = (
Found IDENTIFIER: Token_ID =  27	lexeme = i
Found RELOP: Token_ID =  28	lexeme = =
Found NUMBER: Token_ID =  29	lexeme = 0
Found SEPARATOR: Token_ID =  30	lexeme = ;
Found IDENTIFIER: Token_ID =  31	lexeme = i
Found RELOP: Token_ID =  32	lexeme = <
Found IDENTIFIER: Token_ID =  33	lexeme = n
Found SEPARATOR: Token_ID =  34	lexeme = ;
Found IDENTIFIER: Token_ID =  35	lexeme = i
Found ARITHMETIC: Token_ID =  36	lexeme = +
Found ARITHMETIC: Token_ID =  37	lexeme = +
Found SEPARATOR: Token_ID =  38	lexeme = )
Found SEPARATOR: Token_ID =  39	lexeme = {
Found INPUT/OUTPUT: Token_ID =  40	lexeme = read
Found SEPARATOR: Token_ID =  41	lexeme = (
Found IDENTIFIER: Token_ID =  42	lexeme = x
Found SEPARATOR: Token_ID =  43	lexeme = )
Found SEPARATOR: Token_ID =  44	lexeme = ;
Found IDENTIFIER: Token_ID =  45	lexeme = sum
Found ARITHMETIC: Token_ID =  46	lexeme = +
Found RELOP: Token_ID =  47	lexeme = =
Found IDENTIFIER: Token_ID =  48	lexeme = x
Found SEPARATOR: Token_ID =  49	lexeme = ;
Found IDENTIFIER: Token_ID =  50	lexeme = op
Found ARITHMETIC: Token_ID =  51	lexeme = /
Found RELOP: Token_ID =  52	lexeme = =
Found SEPARATOR: Token_ID =  53	lexeme = (
Found NUMBER: Token_ID =  54	lexeme = 2.1E-5
Found ARITHMETIC: Token_ID =  55	lexeme = *
Found IDENTIFIER: Token_ID =  56	lexeme = op
Found SEPARATOR: Token_ID =  57	lexeme = )
Found SEPARATOR: Token_ID =  58	lexeme = ;
Found SEPARATOR: Token_ID =  59	lexeme = }
Found INPUT/OUTPUT: Token_ID =  60	lexeme = print
Found SEPARATOR: Token_ID =  61	lexeme = (
Found IDENTIFIER: Token_ID =  62	lexeme = x
Found SEPARATOR: Token_ID =  63	lexeme = )
Found SEPARATOR: Token_ID =  64	lexeme = ;
Found SEPARATOR: Token_ID =  65	lexeme = }
Done with 14 lines
```
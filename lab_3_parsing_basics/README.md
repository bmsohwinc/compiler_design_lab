# PARSING BASICS (Left Recursion, Left Factoring, Recursive Descent Parser)

## File Description
1. `Main.java`
    - The source code containing all 3 solutions
2. `runner.sh`
    - Script to run the codes and evaluate
3. `input_1/2/3.txt`
    - Inputs given to respective question 1, 2, 3
4. `cleaner.sh`
    - To remove the compiled Java classes

## Running
- In a command line, run `./runner.sh x` where x can be [1, 2, 3] depending on the question to be tested

## Sample Run
### Left Recursion Removal
1. Currently removes only direct left recursion and indirect left recursion if the productions are properly ordered
2. Command : `./runner.sh 1`
3. Input:
```
E
E+T T
T
T*F F
F
(E) id
-1
```
4. Output:
```
Left Recursion Detected! Symbol: T Proc: T*F
Left Recursion Detected! Symbol: E Proc: E+T

>> The corrected Grammar...
E' --> [+TE', e]
T' --> [*FT', e]
T --> [FT']
E --> [TE']
F --> [(E), id]
```

### Left factoring
1. Checks the longest match prefix for a set of productions and splits the same
2. Command: `./runner.sh 2`
3. Input:
```
S
aAd aB
A
a ab
B
ccd ddc
-1
```
4. Output:
```
--- FINAL ---
A --> [aC]
B --> [ccd, ddc]
S --> [aD]
C --> [_epsilon, b]
D --> [Ad, B]
```

### Recursive Descent Parsing
1. Checks if a given string belongs to the given Non-Left-Recursive Grammar
2. Command: `./runner.sh 3`
3. Input:
```
S
aABx
A
bC
C
bcC e
B
d
-1
S
abdx
```
4. Output:
```
Taking S as start symbol and abdx as inputString
------- Using prod : aABx of symbol: S
------- Using prod : bC of symbol: A
------- Using prod : bcC of symbol: C
## Mismatch for aProd: bcC and inputChar = d ... backtrack now!
------- Using prod : e of symbol: C
Making an epsilon transition for symbol: C and aProd: e
------- Using prod : d of symbol: B
>> PARSING SUCCESSFUL <<
```
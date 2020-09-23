# IMPLEMENTING A PREDICTIVE PARSER

## File Descriptions
1. `lexer/`
    - `lexa1.l` and `lexa2.l`
        - To break the respective inputs to tokens and store in a file
    - `*_input_*`
        - Store the inputs to be tokenized and parsed
    - `token_*`
        - Stores the processed tokens
2. `parser/`
    - `firsts_follows.py`
        - Contains manually calculated Firsts and Follows of the given Grammars
    - `gram_reducer.py`
        - Code to transform the given grammar to a standard 4-character grammar, making it suitable for parsing, etc
    - `parse_table.py`
        - To build the parse tables from given grammar and firsts and follows
    - `main.py`
        - The prime part for the entire process
3. `runner.sh`
    - To run the lexer and parser automatically
4. `cleaner.sh`
    - To remove redundant generated codes

## Running the code
0. Give the input text to be tokenized and parsed in the `lex_input*` files
1. Just run `./runner.sh x` where x in 1 or 2 for grammars 1 and 2, respectively.
2. It will automatically tokenize the given input and start the parser
3. Then it prompts the user to input *QNo* and *Start symbol* of the Grammar
4. With that, the ***Predictive Parsing*** is performed and output is written to `full_output.txt` and also displayed in the terminal

## Sample inputs and outputs
- `./lexer/lex_input_1.txt` and `./lexer/lex_input_2.txt` bear the inputs
- `./parser/full_output.txt` bears the corresponding outputs

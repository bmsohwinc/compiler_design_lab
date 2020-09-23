cd lexer/
flex "lexa${1}.l"
gcc lex.yy.c
./a.out > "token_input_${1}.txt"
cd ../parser
python3 main.py | tee full_output.txt
cd ..
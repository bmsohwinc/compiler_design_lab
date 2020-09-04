read -p "Enter section(1, 2, ..) and subsection(1, 2, ...): " section subsection
echo "You entered: ${section} and ${subsection}"

cd "sec_"${section}/
if [[ $section -eq 1 ]]
then
    filename="q_${section}_${subsection}.l"
    flex ${filename}
    gcc lex.yy.c
    ./a.out
else
    flex "q_${section}.l"
    gcc lex.yy.c
    ./a.out | tee output.txt
fi
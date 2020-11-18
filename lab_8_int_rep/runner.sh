echo -e "Available InterRep:\n\t1. AST\n\t2. 3AC\n\t3. DAG\n"
echo -e "Enter the InterRep number you want to see: "
read filenum
filenum=$((filenum+2))

if [ ${filenum} -eq 4 ]
then
    flex thelexer_4.l
else
    flex thelexer.l
fi
echo "======== Done with Lex"
bison -d -v theparser_${filenum}.y
echo "======== Done with Yacc"
mv theparser_${filenum}.tab.h y.tab.h
mv theparser_${filenum}.tab.c y.tab.c
echo "======== Done renaming the generated .h and .c files"
# Compile
gcc y.tab.c
echo "======== Done with GCC"
echo "Enter the expression now: "
./a.out

# Clean env
rm *yy.c *.h *tab.c a.out *.output
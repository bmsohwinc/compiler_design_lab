# Clean env
rm *yy.c *.h *tab.c a.out *.output

# COMMENT THE BELOW PART IN CASE OF ABOVE CONDITION
flex thelexer.l
echo "======== Done with Lex"
bison -d -v theparser_2.y
echo "======== Done with Yacc"
mv theparser_2.tab.h y.tab.h
mv theparser_2.tab.c y.tab.c
echo "======== Done renaming the generated .h and .c files"
# Compile
gcc y.tab.c
echo "======== Done with GCC"
./a.out

# Clean env
rm *yy.c *.h *tab.c a.out *.output
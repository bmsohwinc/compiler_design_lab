rm *yy.c
rm *.h
rm *tab.c
rm a.out

# flex sample_1.l
# bison sample_1.y
# gcc sample_1.tab.c
# ./a.out

# flex sam2.l
# bison -dy sam2.y
# mv sam2.tab.c y.tab.c
# gcc lex.yy.c y.tab.c 
# ./a.out

# flex sam3.l
# bison -dy sam3.y
# # sleep 1
# # mv sam3.tab.c y.tab.c
# gcc lex.yy.c y.tab.c 
# ./a.out

# flex sam4.l
# echo "======== Done with Flex"
# bison -d -v sam4.y
# echo "======== Done with Bison"
# mv sam4.tab.h y.tab.h
# mv sam4.tab.c y.tab.c
# echo "======== Done renaming the .h"
# gcc lex.yy.c y.tab.c -o sam4e
# echo "======== Done with GCC"
# ./sam4e

flex sam5.l
echo "======== Done with Flex"
bison -d -v sam5.y
echo "======== Done with Bison"
mv sam5.tab.h y.tab.h
mv sam5.tab.c y.tab.c
echo "======== Done renaming the .h"
gcc lex.yy.c y.tab.c -o sam5e
echo "======== Done with GCC"
./sam5e
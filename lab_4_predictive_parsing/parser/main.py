# THE PREDICTIVE PARSER
#   Given a grammar, its Terminals and Non Terminals,
#   and an input, this parses the input and reports whether
#   it is syntactically correct or not

import textwrap as TXW
import gram_reducer as GR
import parse_table as PT
import firsts_follows as FSFL
import collections

def printGram(ip):
    for k in ip.keys():
        print(k, '--->', ip[k])


def parseTheTokens(theParseTable, theSSym, theTokens, Terms):
    # some inits
    theTokens.append('___$')
    theParseStack = ['___$', theSSym]
    cti = 0

    # Parse until stack is empty
    print('\n')
    print('%50s %50s %50s' % ('STACK', 'INPUT', 'ACTION'))
    print('%50s %50s %50s' % (theParseStack, theTokens, '---'))

    while theParseStack[-1] != '___$':

        # # input exhausted!
        # if cti == len(theTokens) - 1:
        #     print('!! PARSE ERROR. Input Exhausted!')
        #     return False

        # current token
        curr_token = theTokens[cti]

        # pop the element 
        stack_top = theParseStack.pop()

        if stack_top == curr_token:
            cti += 1
            print('%50s %50s %50s' % (str(theParseStack), str(theTokens[cti:]), 'Matched: ' + curr_token))
        elif stack_top in Terms:
            print('!! PARSE ERROR. Stack_top = %s didn\'t match the curr_token %s' % (stack_top, curr_token))
            return False
        else:
            # get the table entry
            table_entry = theParseTable[stack_top][curr_token]

            # Nothing in the table cell
            if table_entry == None:
                print('!! PARSE ERROR. Table Entry is None for stack_top: %s and curr_token: %s' %  (stack_top, curr_token))
                return False
            else:
                if table_entry == '___#':
                    print('%50s %50s %50s' % (str(theParseStack), str(theTokens[cti:]), 'Epsilon Push: ' + stack_top + ' --> ' + table_entry))
                else:
                    eles = TXW.wrap(table_entry, 4)
                    eles.reverse()
                    theParseStack += eles
                    print('%50s %50s %50s' % (str(theParseStack), str(theTokens[cti:]), 'Push: ' + stack_top + ' --> ' + table_entry))

    if (cti == len(theTokens) - 1) and (theParseStack[-1] == '___$'):
        print('%50s %50s %50s' % (str(theParseStack), str(theTokens[cti:]), 'Parsing Successful!'))
        return True
    else:
        print('%50s %50s %50s' % (str(theParseStack), str(theTokens[cti:]), '!! PARSE ERROR. Stack exhausted. But inputs exist'))
        return False


def main():
    # print('This is Main')
    Qno = int(input('Enter Q.No: '))
    theGrammar = []
    SSym = input('Enter Start Symbol: ')

    # 1st line    : Non Terminals [ CSV ]
    # 2nd line    : Terminals     [ CSV ]
    # 3rd onwards : Productions   [ Space separated; First token is the Non Terminal; Followed by its productions ]
    with open('q' + str(Qno) + '_grammar.txt') as f:
        theGrammar = f.readlines()

    # Get NTerms and Terms from the file 
    NTerms = theGrammar[0].rstrip('\n').split(',')
    Terms = theGrammar[1].rstrip('\n').split(',')
    Prods = {}

    # Get the Productions from the file
    for x in theGrammar[2:]:
        l = x.rstrip('\n').split(' ')
        Prods[l[0]] = l[1:]

    # Reduce the Grammar to 4-chars for NTerms and Terms
    theGrammar, nTermMap, termMap = GR.gramReducer(Prods, NTerms, Terms, Qno)
    print('reduced Grammar is: ')
    theGrammar = collections.OrderedDict(sorted(theGrammar.items()))    
    printGram(theGrammar)
    print('nTermMap: ')
    nTermMap = collections.OrderedDict(sorted(nTermMap.items()))
    printGram(nTermMap)
    print('termMap: ')
    termMap = collections.OrderedDict(sorted(termMap.items()))
    printGram(termMap)
    
    # map the start symbol too
    SSym = nTermMap[SSym]

    # read the firsts and follows
    theFirsts = collections.OrderedDict(FSFL.Firsts)
    theFollows = collections.OrderedDict(FSFL.Follows)

    if Qno == 1:
        theFirsts = collections.OrderedDict(FSFL.Firsts0)
        theFollows = collections.OrderedDict(FSFL.Follows0)

    # map them to the new standard
    theFirsts = {nTermMap[x] : theFirsts[x] for x in theFirsts.keys()}
    theFollows = {nTermMap[x] : theFollows[x] for x in theFollows.keys()}
    for k in theFirsts:
        theFirsts[k] = [termMap[x] for x in theFirsts[k]]
    for k in theFollows:
        theFollows[k] = [termMap[x] for x in theFollows[k]]

    print('Firsts: ')
    printGram(theFirsts)
    print('Follows: ')
    printGram(theFollows)

    # Build Parse Table
    theParseTable = PT.run(theGrammar, list(termMap.values()), theFirsts, theFollows)

    # Parse the tokens
    # theTokens = ['__id', '___*', '__id']
    theTokens = []
    with open('../lexer/token_input_' + str(Qno) + '.txt') as f:
        theTokens = f.readlines()
    theTokens = [x.rstrip('\n') for x in theTokens]

    for x in theTokens:
        print(x)

    if 'ERR' in theTokens:
        print('SOME LEX ERROR DETECTED... EXITING')
        return

    if parseTheTokens(theParseTable, SSym, theTokens.copy(), list(termMap.values())):
        print('Great! 8)')
    else: 
        print('Oh No! %(')

main()
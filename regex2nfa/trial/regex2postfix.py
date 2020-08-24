import copy

ALPHABETS = ['1', '0']
SYMBOLS = ['*', '+', '(', ')', '.']

thePriority = {
    '*' : 2,
    '.' : 1,
    '+' : 0
}

uniqueNodeId = 0

# each NFA is a dictionary, with each key being a unique node id and having a list of 4 cols:
# 1. whether start node or final node or normal node - [1, 2, 0]
# 2. where does `e` transition lead to -> list
# 3. where does `1` transition lead to -> list
# 4. where does `0` transition lead to -> list


def buildCharNFA(theChar):
    global uniqueNodeId

    tempNFA = dict()
    if theChar == '1':
        tempNFA[uniqueNodeId] = [1, '-', uniqueNodeId + 1, '-']
    else:
        tempNFA[uniqueNodeId] = [1, '-', '-', uniqueNodeId + 1]
    uniqueNodeId += 1
    tempNFA[uniqueNodeId] = [2, '-', '-', '-']
    uniqueNodeId += 1
    return tempNFA


def buildKleeneNFA(theNFA):
    global uniqueNodeId

    # Refer: https://stackoverflow.com/a/2465951/9247555
    tempNFA = copy.deepcopy(theNFA)
    start = [key for key in theNFA if theNFA[key][0] == 1][0]
    end = [key for key in theNFA if theNFA[key][0] == 2][0]

    # add new start and end states
    tempNFA[uniqueNodeId] = [1, [start, uniqueNodeId + 1], '-', '-']
    uniqueNodeId += 1
    tempNFA[uniqueNodeId] = [2, '-', '-', '-']
    uniqueNodeId += 1

    # change intermediate connections
    tempNFA[start][0] = 0
    tempNFA[end][0] = 0
    tempNFA[end][1] = [start, uniqueNodeId - 1]

    return tempNFA


def buildConcatNFA(theFirstNFA, theSecondNFA):   
    global uniqueNodeId

    start1 = [key for key in theFirstNFA if theFirstNFA[key][0] == 1][0]
    end1 = [key for key in theFirstNFA if theFirstNFA[key][0] == 2][0]
    start2 = [key for key in theSecondNFA if theSecondNFA[key][0] == 1][0]
    end2 = [key for key in theSecondNFA if theSecondNFA[key][0] == 2][0]

    # merge the 2 NFA
    tempNFA = {**theFirstNFA, **theSecondNFA}

    # add new start state and end state
    tempNFA[uniqueNodeId] = [1, start1, '-', '-']
    uniqueNodeId += 1
    tempNFA[uniqueNodeId] = [2, '-', '-', '-']
    uniqueNodeId += 1

    # change intermediate connections
    tempNFA[start1][0] = 0
    tempNFA[start2][0] = 0
    tempNFA[end1][0] = 0
    tempNFA[end2][0] = 0
    tempNFA[end1][1] = start2
    tempNFA[end2][1] = uniqueNodeId - 1

    return tempNFA


def buildUnionNFA(theFirstNFA, theSecondNFA):
    global uniqueNodeId

    start1 = [key for key in theFirstNFA if theFirstNFA[key][0] == 1][0]
    end1 = [key for key in theFirstNFA if theFirstNFA[key][0] == 2][0]
    start2 = [key for key in theSecondNFA if theSecondNFA[key][0] == 1][0]
    end2 = [key for key in theSecondNFA if theSecondNFA[key][0] == 2][0]

    # merge the 2 NFA
    tempNFA = {**theFirstNFA, **theSecondNFA}

    # add new start state and end state
    tempNFA[uniqueNodeId] = [1, [start1, start2], '-', '-']
    uniqueNodeId += 1
    tempNFA[uniqueNodeId] = [2, '-', '-', '-']
    uniqueNodeId += 1

    # change intermediate connections
    tempNFA[start1][0] = 0
    tempNFA[start2][0] = 0
    tempNFA[end1][0] = 0
    tempNFA[end2][0] = 0
    tempNFA[end1][1] = uniqueNodeId - 1
    tempNFA[end2][1] = uniqueNodeId - 1

    return tempNFA


def buildTheNFA(thePostfix):
    theNFAStack = []
    for x in thePostfix:
        tempNFA = dict()
        if x in ALPHABETS:
            tempNFA = buildCharNFA(x)
        else:
            if x == '*':
                tempNFA = buildKleeneNFA(theNFAStack[-1])
                theNFAStack.pop()
            if x == '.':
                tempNFA = buildConcatNFA(theNFAStack[-2], theNFAStack[-1])
                theNFAStack.pop()
                theNFAStack.pop()
            if x == '+':
                tempNFA = buildUnionNFA(theNFAStack[-2], theNFAStack[-1])
                theNFAStack.pop()
                theNFAStack.pop()
        theNFAStack.append(tempNFA)
    
    return theNFAStack.pop()
    pass


def regex2postfix(regex):
    # insert extra char between concatenated letters
    regex2 = ''
    regex2 += regex[0]

    for i in range(1, len(regex)):
        if regex[i] in ALPHABETS or regex[i] == '(':
            if regex2[-1] != '+' and regex2[-1] != '(':
                regex2 += '.'
        regex2 += regex[i]
    print('Regex2 is : ', regex2)
    regex = regex2
    thePostfix = ''
    theStack = []
    for x in regex:
        if x == ' ':
            continue
        elif x in ALPHABETS:
            thePostfix += x
        elif x in SYMBOLS:
            if x == '(':
                theStack.append(x)
            elif x == ')':
                while len(theStack) != 0:
                    if theStack[-1] == '(':
                        theStack.pop()
                        break
                    else:
                        topChar = theStack[-1]
                        theStack.pop()
                        thePostfix += topChar
            else:
                while 1:
                    if len(theStack) == 0:
                        theStack.append(x)
                        break
                    else:
                        topChar = theStack[-1]
                        if topChar == '(':
                            theStack.append(x)
                            break
                        else:
                            if thePriority[topChar] >= thePriority[x]:
                                theStack.pop()
                                thePostfix += topChar
                            else:
                                theStack.append(x)
                                break
        else:
            print('Unkown symbol detected. Exiting...')
            return '-1'
    
    while len(theStack) != 0:
        thePostfix += theStack[-1]
        theStack.pop()
    
    return thePostfix


def main():
    # 0. Prompt for Regex
    print('Allowed Alphabets are: ', ALPHABETS)
    print('Allowed Symbols are: ', SYMBOLS)

    while 1:
        s = input('Enter the Regex: ')

        # 1. Get the Postfix notation of the Regex
        thePostfix = regex2postfix(s)
        if thePostfix == '-1':
            return
        print('The postfix of %s is %s' % (s, thePostfix))

        # 2. Build the e-NFA now
        theNFA = buildTheNFA(thePostfix)

        # 3. Print the e-NFA
        print('And the e-NFA is...')
        print('[ Node | NodeType | e-Trx | 1-Trx | 0-Trx ]')
        for key in theNFA:
            print(key, ' -->', end='')
            for obj in theNFA[key]:
                print('\t', obj, end='')
            print('')
        print('')


main()
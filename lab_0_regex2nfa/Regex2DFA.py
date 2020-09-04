import copy

ALPHABETS = ['1', '0']
SYMBOLS = ['*', '+', '(', ')', '.']

def bound1():
    s = ''
    for i in range(10): 
        s += '-'
    print(s)

def bound2():
    s = ''
    for i in range(20):
        s += '=' 
    print(s)

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


def simulate(thexp, theDFA):
    currState = 'A'
    for x in thexp:
        if x not in ALPHABETS:
            print('Unkown symbol detected... Exiting')
            return False
        print('\tGoing from %s to' % (currState), end=' ')
        if x == '1':
            currState = theDFA[currState][1]
        else:
            currState = theDFA[currState][2]
        print(currState)
        if currState == '*':
            return False

    if theDFA[currState][0] == 2:
        return True
    else:
        return False


def getEClosures(theNFA):
    eclosure = dict()
    # a map of visited nodes
    visi = dict()
    for key in theNFA:
        visi[key] = 0
    for key in theNFA:
        # reset the map
        visi = {k : 0 for k in visi}

        visi[key] = 1
        eclosure[key] = [key]
        myq = [key]
        while len(myq) != 0:
            frontchar = myq[0]
            myq.pop(0)
            eTrxElement = theNFA[frontchar][1]
            if type(eTrxElement) == str:
                continue
            elif type(eTrxElement) == int:
                if visi[eTrxElement] == 0:
                    myq.append(eTrxElement)
                    visi[eTrxElement] = 1
                    eclosure[key].append(eTrxElement)
            else:
                for node in eTrxElement:
                    if visi[node] == 0:
                        myq.append(node)
                        visi[node] = 1
                        eclosure[key].append(node)
    return eclosure


def beautifyNodes(theDFA):
    betterNames = dict()
    startNode = 'A'
    i = 0
    for key in theDFA.keys():
        if key == ():
            betterNames[key] = '*'
        else:
            betterNames[key] = chr(ord(startNode) + i)
            i += 1

    # print('Some better names:')
    # for key in betterNames:
    #     print(key, ' --> ', betterNames[key])  
    
    for key in theDFA.keys():
        theDFA[key][1] = betterNames[theDFA[key][1]]
        theDFA[key][2] = betterNames[theDFA[key][2]]
        # theDFA[betterNames[key]] = theDFA.pop(key)

    theDFA = {betterNames[key] : theDFA.pop(key) for key in betterNames.keys()}

    return theDFA


def buildTheDFA(theNFA):
    eclosures = getEClosures(theNFA)
    print('The epsilon closures are: ')
    for key in eclosures:
        print(key, ' --> ', eclosures[key])
    print('')

    # start the DFA build
    start = [key for key in theNFA if theNFA[key][0] == 1][0]
    end = [key for key in theNFA if theNFA[key][0] == 2][0]

    # add the start node
    initNode = tuple(sorted(eclosures[start]))
    # print('initNode:', initNode)
    myq = []
    theDFA = dict()
    theDFA[initNode] = [1, '-', '-']
    if end in initNode:
        theDFA[initNode][0] = [1, 2]
    
    # add the 1-Trx and 0-Trx nodes, if unique
    visits1 = set()
    visits0 = set()
    # finiding target node upon trx over `1` and also over `0`
    for node in initNode:
        if theNFA[node][2] != '-':
            visits1.update(eclosures[theNFA[node][2]])
        if theNFA[node][3] != '-':
            visits0.update(eclosures[theNFA[node][3]])

    visits0 = tuple(sorted(list(visits0)))
    visits1 = tuple(sorted(list(visits1)))

    theDFA[initNode][1] = visits1
    theDFA[initNode][2] = visits0

    if visits0 not in theDFA.keys():
        myq.append(visits0)
    if visits1 not in theDFA.keys():
        myq.append(visits1)

    # while I keep getting new states
    while len(myq) != 0:
        frontnode = myq[0]
        myq.pop(0)
        visits1 = set()
        visits0 = set()
        # find states for 1 and 0 transistions
        for node in frontnode:
            if theNFA[node][2] != '-':
                visits1.update(eclosures[theNFA[node][2]])
            if theNFA[node][3] != '-':
                visits0.update(eclosures[theNFA[node][3]])
        # maintain a sorted list for the state
        visits1 = tuple(sorted(list(visits1)))
        visits0 = tuple(sorted(list(visits0)))
        # create the DFA node
        theDFA[frontnode] = [0, visits1, visits0]
        # check if it is a final node
        if end in frontnode:
            theDFA[frontnode][0] = 2
        # check if it is a new state
        if visits0 not in theDFA.keys():
            myq.append(visits0)
        if visits1 not in theDFA.keys():
            myq.append(visits1)
    return beautifyNodes(theDFA)


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
        if x in ALPHABETS:
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
        global uniqueNodeId
        uniqueNodeId = 0
        theRegex = input('Enter the Regex: ')
        theRegex = theRegex.replace(' ', '')

        # 1. Get the Postfix notation of the Regex
        thePostfix = regex2postfix(theRegex)
        if thePostfix == '-1':
            return
        print('\nPOSTFIX:\nThe postfix of %s is %s' % (theRegex, thePostfix))

        # 2. Build the e-NFA now
        theNFA = buildTheNFA(thePostfix)

        # 3. Print the e-NFA
        bound1()
        print('\nEPSILON NFA:\nAnd the e-NFA is...')
        print('[ Node | NodeType | e-Trx | 1-Trx | 0-Trx ]')
        for key in theNFA:
            print(key, ' -->', end='')
            for obj in theNFA[key]:
                print('\t', obj, end='')
            print('')
        print('')
        
        bound1()
        theDFA = buildTheDFA(theNFA)
        bound1()
        print('\DFA:\nAnd the DFA is...\n')
        for key in theDFA.keys():
            print(key, ' --> ', theDFA[key])
        print('')
        bound1()
        print('\nSIMULATION:\n')
        while 1:
            thex = input('Enter a word for simulation (-1 to exit): ')
            if thex == '-1':
                break
            if simulate(thex, theDFA):
                print('GREAT! Word %s belongs to the Regex %s' % (thex, theRegex))
            else:
                print('OH NO! Word %s doesnot belong to the Regex %s' % (thex, theRegex))
            print('')
        bound2()
        print('')


main()
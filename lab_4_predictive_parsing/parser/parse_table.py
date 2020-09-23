# GIVEN A GRAMMAR, SET OF TERMINALS, SET OF FIRSTS AND FOLLOWS,
# THIS BUILDS A PARSE TABLE AND RETURNS THE SAME

def printTable(tt, trms):
    print('%20s'%'', end='')
    for t in trms:
        print('%20s' % t, end='')
    print('')
    for k in tt.keys():
        print('%20s' % k, end='')
        for j in tt[k]:
            print('%20s' % tt[k][j], end='')
        print('')


def run(Gram, Terms, Firsts, Follows):
    theParseTable = {}

    for key in Gram.keys():
        theParseTable[key] = {}
        for term in Terms:
            theParseTable[key][term] = None
        # theParseTable[key]['___$'] = None

    print('The Parse Table:')
    # print(theParseTable)
    printTable(theParseTable, Terms)

    for key in Gram.keys():
        for prod in Gram[key]:
            # if this is epsilon production, directly update the table
            if prod == '___#':
                for fls in Follows[key]:
                    theParseTable[key][fls] = '___#'
            else:
                if prod[:4] in Terms:
                    theParseTable[key][prod[:4]] = prod
                else:
                    for frs in Firsts[prod[:4]]:
                        if frs == '___#':
                            for fls in Follows[key]:
                                theParseTable[key][fls] = '___#'
                        else:
                            theParseTable[key][frs] = prod

    print('The Final Parse Table...')
    printTable(theParseTable, Terms)
    return theParseTable
            
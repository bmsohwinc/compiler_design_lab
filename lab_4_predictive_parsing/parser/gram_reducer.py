# MAPS THE MULTICHARACTER GRAMMAR INTO 4 CHARACTER GRAMMAR
# FOR TERMINALS AND NON-TERMINALS

all_caps = []
all_smls = []
NTERM_MAP = {}
TERM_MAP = {}

def prepareMapForQ1():  
    q1_map = {
        'id' : '__id',
        '*'  : '___*',
        '('  : '___(',
        ')'  : '___)',
        '+'  : '___+',
        '_eps' : '___#',
        '$' : '___$'
    }
    return q1_map

def prepareMapForQ2():
    q2_map = {
        '+'     : '___+', 
        '-'     : '___-', 
        '*'     : '___*', 
        '/'     : '___/', 
        '='     : '___=', 
        '<'     : '___<', 
        '>'     : '___>', 
        '('     : '___(', 
        ')'     : '___)', 
        '{'     : '___{', 
        '}'     : '___{', 
        ':='    : '__:=', 
        ';'     : '___;', 
        'and'   : '_and', 
        'else'  : '_els', 
        'end'   : '_end',
        'ic'    : '__ic', 
        'id'    : '__id', 
        'if'    : '__if', 
        'int'   : '_int', 
        'do'    : '__do', 
        'fc'    : '__fc',
        'float' : '_flt', 
        'not'   : '_not', 
        'or'    : '__or', 
        'print' : '_prt', 
        'prog'  : '_prg', 
        'scan'  : '_scn', 
        'str'   : '_str', 
        'then'  : '_thn', 
        'while' : '_whl',
        '_eps'  : '___#',
        '$' : '___$'
    }
    return q2_map


# Given NTerms and Terms, initMaps returns the mapped lists,
#   where each N/Terms is mapped to a standard notation,
#   Axx for NTerm --> xx represents the symbol names in case of left-factoring and left-recur-removal
#   _xxx for Term --> xxx represents the symbols for Terms
def initMaps(NTerms, Terms, Qno):
    # Prepare the NTERM map
    nterm_map = {}
    fc, lc = ['A', 'A']
    for x in NTerms:
        nterm_map[x] = fc + lc + '00'
        if fc == 'Z' and lc == 'Z':
            print('MAX NUMBER OF SYMBOLS CONSUMED... EXITING')
            exit()
        elif lc == 'Z':
            fc = chr(ord(fc) + 1)
            lc = 'A'
        else:
            lc = chr(ord(lc) + 1)
    
    term_map = {}
    # TERM MAPPER for Q 1
    if Qno == 1:
        term_map = prepareMapForQ1()
    # TERM MAPPER for Q 2
    else:
        term_map = prepareMapForQ2()

    return [nterm_map, term_map]
    

def gramReducer(Gram, NTerms, Terms, qno):
    # Just ack
    print('Hi, I am the gram Reducer')
    print('Got G: ', Gram)
    print('Got NTs: ', NTerms)
    print('Got Ts: ', Terms)

    # Initialize Maps
    [NTERM_MAP, TERM_MAP] = initMaps(NTerms, Terms, qno)

    # Mapping old NTerms alphabetically
    ntermalpha = {}
    for x in NTerms:
        ntermalpha[x[0]] = []
    for x in NTerms:
        ntermalpha[x[0]].append(x)

    # Mapping old Terms alphabetically
    termalpha = {}
    for x in Terms:
        termalpha[x[0]] = []
    for x in Terms:
        termalpha[x[0]].append(x)

    # Sort the mappings
    ntermalpha = {x : sorted(ntermalpha[x]) for x in ntermalpha.keys()}
    termalpha = {x : sorted(termalpha[x]) for x in termalpha.keys()}

    print('.... ntermalphas: ', ntermalpha)
    print('.... NTERM_MAP: ', NTERM_MAP)
    print('.... termalphas: ', termalpha)
    print('.... TERM_MAP: ', TERM_MAP)

    # New-Keyed Grammar
    Gram = {NTERM_MAP[x] : Gram[x] for x in Gram.keys()}
    uniGram = {x : [] for x in Gram.keys()}

    # For each key in the Gram
    for key in Gram.keys():
        # For each production for that key
        for prod in Gram[key]:
            # For each char in that Production
            n = len(prod)
            i = 0
            uniformProd = ''
            previ = 0
            while i < n:
                c = prod[i]
                if c in ntermalpha.keys():
                    print('NTERM:', c)
                    for p in reversed(ntermalpha[c]):
                        if len(p) > n - i:
                            continue
                        else:
                            templen = len(p)
                            ss = prod[i:i+templen]
                            print('Checking ss : ', ss, ' and p : ', p)
                            if ss == p:
                                i += templen
                                uniformProd += NTERM_MAP[ss]
                                print('------ MATCHED!!')
                                break
                else:
                    print('TERM:', c)
                    # print('termalpha :', termalpha)
                    for p in reversed(termalpha[c]):
                        if len(p) > n - i:
                            continue
                        else:
                            templen = len(p)
                            ss = prod[i:i+templen]
                            print('Checking ss : ', ss, ' and p : ', p)
                            if ss == p:
                                i += templen
                                uniformProd += TERM_MAP[ss]
                                print('------ MATCHED!!')
                                break
                
                if previ == i:
                    break
                previ = i
            # Replace prod by its uniform format
            uniGram[key].append(uniformProd)
    print('Uniform Grammar: \n', uniGram)
    return [uniGram, NTERM_MAP, TERM_MAP]
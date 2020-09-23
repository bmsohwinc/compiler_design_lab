Firsts = {
    'P' : ['prog'],
    'DL' : ['int', 'float', '_eps'],
    'D' : ['int', 'float'],
    'TY' : ['int', 'float'],
    'VL' : ['id'],
    'VX' : ['id', '_eps'],
    'SL' : ['id', 'if', 'while', 'print', 'scan', '_eps'],
    'S' : ['id', 'if', 'while', 'print', 'scan'],
    'EH' : ['id'],
    'IS' : ['if'],
    'IX' : ['end', 'else'],
    'WS' : ['while'],
    'IOS' : ['print', 'scan'],
    'PE' : ['str', ],
    'BE' : ['not', '{', '(', 'id', 'ic', 'fc'],
    'BX' : ['or', '_eps'],
    'AE' : ['not', '{', '(', 'id', 'ic', 'fc'],
    'AX' : ['and', '_eps'],
    'NE' : ['not', '{', '(', 'id', 'ic', 'fc'],
    'RE' : ['(', 'id', 'ic', 'fc'],
    'RX' : ['=', '<', '>'],
    'E' : ['(', 'id', 'ic', 'fc'],
    'EY' : ['+', '-', '_eps'],
    'T' : ['(', 'id', 'ic', 'fc'],
    'TX' : ['*', '/', '_eps'],
    'F' : ['(', 'id', 'ic', 'fc']
}

Follows = {
    'DL' : ['end', 'if', 'id', 'while', 'print', 'scan'],
    'SL' : ['end', 'else'],
    'VX' : [';'],
    'AX' : ['do', 'then', '}', 'or'],
    'BX' : ['then', '}', 'do'],
    'EY' : ['id', 'if', 'while', 'print', 'scan', 'end', ')', '=', '<', '>', 'and', 'then', 'do', ';'],
    'TX' : ['id', 'if', 'while', 'print', 'scan', 'end', ')', '=', '<', '>', 'and', 'then', 'do', '+', '-', ';']
}

Firsts0 = {
    'E' : ['(', 'id'],
    'EX' : ['+', '_eps'],
    'T' : ['(', 'id'],
    'TX' : ['*', '_eps'],
    'F' : ['(', 'id']
}

Follows0 = {
    'EX' : ['$', ')'],
    'TX' : ['$', '+', ')']
}

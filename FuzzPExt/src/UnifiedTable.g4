grammar UnifiedTable;

prog : tableDcl+;

tableDcl
    : ID '=' '{' simpleCellLine '}'  #OneXOneTable
    | ID '=' '{' doubleCellLine '}'  #OneXTwoTable
    | ID '=' '{' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine '}' #TwoXOneWithoutPhi
    | ID '=' '@'op'@ {' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine '}' #TwoXOneWithoutPhiWithOp
    | ID '=' '{' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine'}' #TwoXOneWithPhi
    | ID '=' '@'op'@ {' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine'}' #TwoXOneWithPhiWithOp
    | ID '=' '{' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine '}' #TwoXTwoWithoutPhi
    | ID '=' '@'op'@ {' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine '}' #TwoXTwoWithoutPhiWithOp
    | ID '=' '{' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine'}' #TwoXTwoWithPhi
    | ID '=' '@'op'@ {' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine'}' #TwoXTwoWithPhiWithOp
    ;

simpleCellLine :'[' simpleCell  simpleCell  simpleCell  simpleCell  simpleCell ']'
               |'[' simpleCell  simpleCell  simpleCell  simpleCell  simpleCell simpleCell']'
               ;

doubleCellLine :'[' doubleCell  doubleCell  doubleCell  doubleCell  doubleCell ']'
               |'[' doubleCell  doubleCell  doubleCell  doubleCell  doubleCell doubleCell']'
               ;

simpleCell : '<' fv '>' ;
doubleCell : '<' fv ',' fv '>' ;

fv: type=(NL|NM|ZR|PM|PL|FF) ;

op: type=(PLUS|MINUS|DIV|MULTI);

PLUS:'+';
MINUS:'-';
DIV:'/';
MULTI:'*';

NL: 'nl' |'NL' | '-2';
NM: 'nm' |'NM' | '-1';
ZR: 'zr' |'ZR' | '0';
PM: 'pm' |'PM' | '1';
PL: 'pl' |'PL' | '2';
FF: 'ff' |'FF';

ID: [a-zA-Z]+ ;
WS :[ \t\r\n]+ -> skip;

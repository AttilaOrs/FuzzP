grammar UnifiedTable;

prog : tableDcl+;

tableDcl
    : ID '=' '{' simpleCellLine '}'  #OneXOneTable
    | ID '=' '{' doubleCellLine '}'  #OneXTwoTable
    | ID '=' '{' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine '}' #TwoXOneWithoutPhi
    | ID '=' '@'op'@' '{' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine '}' #TwoXOneWithoutPhiWithOp
    | ID '=' '{' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine'}' #TwoXOneWithPhi
    | ID '=' '@'op'@' '{' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine'}' #TwoXOneWithPhiWithOp
    | ID '=' '{' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine '}' #TwoXTwoWithoutPhi
    | ID '=' '@'op'@' '{' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine '}' #TwoXTwoWithoutPhiWithOp
    | ID '=' '{' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine'}' #TwoXTwoWithPhi
    | ID '=' '@'op'@' '{' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine'}' #TwoXTwoWithPhiWithOp
    ;

simpleCellLine :'[' simpleCell  simpleCell  simpleCell  simpleCell  simpleCell ']'
               |'[' simpleCell  simpleCell  simpleCell  simpleCell  simpleCell simpleCell']'
               ;

doubleCellLine :'[' doubleCell  doubleCell  doubleCell  doubleCell  doubleCell ']'
               |'[' doubleCell  doubleCell  doubleCell  doubleCell  doubleCell doubleCell']'
               ;

simpleCell : '<' fv '>' ;
doubleCell : '<' fv ',' fv '>' ;

fv: fv_classic
  | poz_neg_int
   ;
fv_classic : type=(NL|NM|ZR|PM|PL|FF ) ;

op: type=(PLUS|MINUS|DIV|MULTI);

PLUS:'+';
MINUS:'-';
DIV:'/';
MULTI:'*';

NL: 'nl' |'NL'  ;
NM: 'nm' |'NM' ;
ZR: 'zr' |'ZR' ;
PM: 'pm' |'PM' ;
PL: 'pl' |'PL' ;
FF: 'ff' |'FF';

ID: [a-zA-Z]+ ;
WS :[ \t\r\n]+ -> skip;

poz_neg_int : INT
			| '-'INT
			;
INT : [0-9]+ ;

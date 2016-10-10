grammar FuzzyTable;

prog : tableDcl+;

tableDcl
    : ID '=' '{' simpleCellLine '}'  #OneXOneTable
    | ID '=' '{' doubleCellLine '}'  #OneXTwoTable
    | ID '=' '{' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine '}' #TwoXOneWithoutPhi
    | ID '=' '{' simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine simpleCellLine'}' #TwoXOneWithPhi
    | ID '=' '{' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine '}' #TwoXTwoWithoutPhi
    | ID '=' '{' doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine doubleCellLine'}' #TwoXTwoWithPhi
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

NL: 'nl' |'NL';
NM: 'nm' |'NM';
ZR: 'zr' |'ZR';
PM: 'pm' |'PM';
PL: 'pl' |'PL';
FF: 'ff' |'FF';

ID: [a-zA-Z]+ ;
WS :[ \t\r\n]+ -> skip;

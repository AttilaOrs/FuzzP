grammar FuzzyPLang;
import FuzzyTable;

prog: stateMent+;

stateMent : tableDcl ';'
          | petriLine ';'
          | putInitToken ';'
          ;
putInitToken : (inpPlace | place ) '<=' token ;

petriLine : (inpPlace | place| tranz | otranz ) lineCont ;

lineCont :(arcOp (inpPlace | place| tranz | otranz ))+;

arcOp
   : '->'
   | '-(' number ')->'
   ;

inpPlace : 'iP' INT
         | 'ip' INT
         ;

place : 'P' INT
         | 'p' INT
         ;
tranz : 't' INT tranzSpec?
      | 'T' INT tranzSpec?
      ;

otranz : 'ot' INT tranzSpec?
       | 'oT' INT tranzSpec?
        ;
tranzSpec : '[' ID ']'
          | '[' ID ',' INT ']'
          | '[' INT ']'
          | '['  INT ',' ID ']'
          ;
token : '<' number ',' number ',' number ',' number ',' number '>'
      | '<phi>'
      ;
number : INT
       | INT'.'INT
       ;

COMMENT
   : '/*' .*? '*/' -> skip
   ;

LINE_COMMENT
   : '//' .*? '\r'? '\n' -> skip
   ;

INT : [0-9]+ ;





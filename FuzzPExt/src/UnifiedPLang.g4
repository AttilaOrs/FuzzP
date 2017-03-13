
grammar UnifiedPLang;
import UnifiedTable;

prog: stateMent+;

stateMent : tableDcl ';'
          | petriLine ';'
          | putInitToken ';'
          | subCompDcl
          | newSubComp ';'
          ;
          
subCompDcl : 'sub' ID  '{' stateMent+ '}';
newSubComp : ID '=' 'new ' ID ;
subCompRef : (ID'.')+ (inpPlace | otranz) ;

putInitToken : (inpPlace | place ) '<=' token ;

petriLine : (inpPlace | place| tranz | otranz | subCompRef ) lineCont ;

lineCont :(arcOp (inpPlace | place| tranz | otranz| subCompRef ))+;

arcOp : '->';

inpPlace : 'iP' INT '('number ')'
         | 'ip' INT  '(' number ')'
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
token : '<' number '>'
      | '<phi>'
      ;
number : poz_neg_int
       | poz_neg_double
       ;


COMMENT
   : '/*' .*? '*/' -> skip
   ;

LINE_COMMENT
   : '//' .*? '\r'? '\n' -> skip
   ;
poz_neg_double : INT.INT
			   | '-'INT.INT
			   ;			

grammar UETPNLisp;


prog: '(' op subexp subexp ')';


subexp: '(' op subexp subexp ')'
      | leaf
      ;


op: '@' #Seq
  | '&' #Conc
  | '?' #Selc
  | '#' #Loop
  | '+' #Add
  | '*' #Multi
  | '%' #PosNegSplit 
  ;
leaf:'d' SEP nr #Delay
    |'i' SEP inpType SEP nr #Input
    |'o' SEP outType SEP nr #Output  
    |'b' #Block
    |'m' SEP nr  #Memory
    |'c' SEP poz_neg_double #Const
    |'v' #Inv
    |'n' #Negate
    ;
nr: INT;

    
    
outType: 'c' #CopyOutType;

inpType: 'br' #BlockingReader
       | 'nr' #NonblockingReader
       | 'eip' #EnableIfPhi
       | 'enp' #EnableIfNotPhi
       | 'su' #ShiftUpIfEventTable
       | 'sd' #ShiftDownIfEventTable;




SEP : ':';

INT : [0-9]+ ;

WS :[ \t\r\n]+ -> skip;

poz_neg_double : INT.INT
			   | '-'INT.INT
			   | INT
			   | '-'INT
			   ;			




grammar LowLevelGrammar;

map :		(connect)? (';' positions)? (';' block)* ;

connect :	destinations (':' destinations)* ;

destinations : pos '%' pos ('%' pos)* ; //primer valor es nodo origen el resto son los destinos

positions :	dec (':' dec)* ; // tantas veces como habitaciones!!!

block :		positions ';' content ;

content :	content ':' content
		|	ID
		;

dec :		(NUM)+ ;
pos :		(NUM)+ ;
	
/*
room_connections locals[int i] :
	{$i = 2;} BIN {$BIN.getText().length() == $i}? ({$i++;} ':' BIN {$BIN.getText().length() == $i}?)* ;
*/

NUM : [0-9] ; 

ID : [a-z][a-z0-9]* ;
WS : [ \t\r\n]+ -> skip ;
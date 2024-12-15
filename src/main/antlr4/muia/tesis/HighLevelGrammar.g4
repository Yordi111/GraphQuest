grammar HighLevelGrammar;

map :		contents (';' connect ':' contents)* ;
connect :	NUM ;
contents :	cont (':' cont)* ;
cont :		NUM ;

NUM :		[0-9]+ ;
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{
	private boolean errorDetected;

	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn);
	}

	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn, value);
	}

	private void report_lexical_error() {
		errorDetected = true;
		System.err.println(
			"Leksicka greska (" + yytext() + ") u liniji "
			+ (yyline + 1) + " kolona " + yycolumn
		);
	}

	public boolean hasErrors() {
		return errorDetected;
	}
%}

%cup
%line
%column
%unicode

%eofval{
	return new_symbol(sym.EOF);
%eofval}

LineTerminator = \r\n|\r|\n
WhiteSpace = {LineTerminator}|[ \t\b\f]
LineComment = "//"[^\r\n]*
Identifier = [A-Za-z][A-Za-z0-9_]*
Number = [0-9]+
CharConstant = "'"[ -~]"'"

%%

{WhiteSpace}+	{ }
{LineComment}	{ }

"program"		{ return new_symbol(sym.PROG, yytext()); }
"break"		{ return new_symbol(sym.BREAK, yytext()); }
"enum"			{ return new_symbol(sym.ENUM, yytext()); }
"class"		{ return new_symbol(sym.CLASS, yytext()); }
"abstract"		{ return new_symbol(sym.ABSTRACT, yytext()); }
"else"			{ return new_symbol(sym.ELSE, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"if"			{ return new_symbol(sym.IF, yytext()); }
"new"			{ return new_symbol(sym.NEW, yytext()); }
"print"		{ return new_symbol(sym.PRINT, yytext()); }
"read"			{ return new_symbol(sym.READ, yytext()); }
"return"		{ return new_symbol(sym.RETURN, yytext()); }
"void"			{ return new_symbol(sym.VOID, yytext()); }
"extends"		{ return new_symbol(sym.EXTENDS, yytext()); }
"continue"		{ return new_symbol(sym.CONTINUE, yytext()); }
"for"			{ return new_symbol(sym.FOR, yytext()); }
"foreach"		{ return new_symbol(sym.FOREACH, yytext()); }
"length"		{ return new_symbol(sym.LENGTH, yytext()); }
"switch"		{ return new_symbol(sym.SWITCH, yytext()); }
"case"			{ return new_symbol(sym.CASE, yytext()); }
"findAny"		{ return new_symbol(sym.FINDANY, yytext()); }
"map"			{ return new_symbol(sym.MAP, yytext()); }

"true"			{ return new_symbol(sym.BOOL, yytext()); }
"false"		{ return new_symbol(sym.BOOL, yytext()); }
{CharConstant}	{ return new_symbol(sym.CHAR, yytext()); }
{Number}		{
					try {
						return new_symbol(sym.NUMBER, Integer.valueOf(yytext()));
					} catch (NumberFormatException exception) {
						report_lexical_error();
					}
				}

"++"			{ return new_symbol(sym.PLUSPLUS, yytext()); }
"--"			{ return new_symbol(sym.MINUSMINUS, yytext()); }
"=="			{ return new_symbol(sym.EQUALSTO, yytext()); }
"!="			{ return new_symbol(sym.DIFFERENT, yytext()); }
">="			{ return new_symbol(sym.EGREATER, yytext()); }
"<="			{ return new_symbol(sym.ELESS, yytext()); }
"&&"			{ return new_symbol(sym.AND, yytext()); }
"||"			{ return new_symbol(sym.OR, yytext()); }
"=>"			{ return new_symbol(sym.ARROW, yytext()); }

"+"			{ return new_symbol(sym.PLUS, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.MUL, yytext()); }
"/"			{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
">"			{ return new_symbol(sym.GREATER, yytext()); }
"<"			{ return new_symbol(sym.LESS, yytext()); }
"="			{ return new_symbol(sym.EQUAL, yytext()); }

";"			{ return new_symbol(sym.SEMI, yytext()); }
","			{ return new_symbol(sym.COMMA, yytext()); }
"."			{ return new_symbol(sym.DOT, yytext()); }
"("			{ return new_symbol(sym.LPAREN, yytext()); }
")"			{ return new_symbol(sym.RPAREN, yytext()); }
"{"			{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"["			{ return new_symbol(sym.LSQBRACE, yytext()); }
"]"			{ return new_symbol(sym.RSQBRACE, yytext()); }
"?"			{ return new_symbol(sym.QUESTION, yytext()); }
":"			{ return new_symbol(sym.DOUBLEDOT, yytext()); }

{Identifier}	{ return new_symbol(sym.IDENT, yytext()); }

.				{ report_lexical_error(); }

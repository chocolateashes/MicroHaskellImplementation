
// File:   MH_Lexer.java
// Date:   October 2015

// Java template file for lexer component of Informatics 2A Assignment 1 (2015).
// Concerns lexical classes and lexer for the language MH (`Micro-Haskell').


import java.io.* ;

class MH_Lexer extends GenLexer implements LEX_TOKEN_STREAM {

static class VarAcceptor extends GenAcceptor implements DFA {
	public String lexClass() {return "VAR" ;};
	public int numberOfStates() {return 3;};
		
	int nextState (int state, char c) {
	switch (state) {
	case 0: if (CharTypes.isSmall(c)) return 1; else return 2;
	case 1: if (CharTypes.isSmall(c) || CharTypes.isLarge(c) || CharTypes.isDigit(c)) return 1; else return 2;
		default: return 2;
		
	}
	}
	
	boolean accepting(int state) {return state == 1;}
	boolean dead(int state) {return state == 2;}
	}


static class NumAcceptor extends GenAcceptor implements DFA {
    public String lexClass() {return "NUM" ;};
    public int numberOfStates() {return 3;};
    
    int nextState (int state, char c) {
    switch (state) {
    case 0 : if (CharTypes.isDigit(c)) return 1; else return 2;
    case 1: if (CharTypes.isDigit(c)) return 1; else return 2;
    	default: return 2;
    }
    }
    
    boolean accepting(int state) {return state == 1;}
    boolean dead(int state) {return state == 2;}
}

static class BooleanAcceptor extends GenAcceptor implements DFA {
	public String lexClass() {return "BOOLEAN";};
	public int numberOfStates() {return 10;};
	
	int nextState (int state, char c){
	switch (state) {
	case 0: if (c=='T') return 1; else if (c=='F') return 5; else return 9;
	case 1: if (c=='r') return 2; else return 9;
	case 2: if (c=='u') return 3; else return 9;
	case 3: if (c=='e') return 4; else return 9;
	case 5: if (c=='a') return 6; else return 9;
	case 6: if (c=='l') return 7; else return 9;
	case 7: if (c=='s') return 8; else return 9;
	case 8: if (c=='e') return 4; else return 9;
		default: return 9;
	}
	}
	
	boolean accepting(int state) {return state == 4;}
	boolean dead(int state) {return state == 9;}
}

static class SymAcceptor extends GenAcceptor implements DFA {
	public String lexClass() {return "SYM" ;};
	public int numberOfStates() {return 3;};
	
	int nextState (int state, char c) {
	switch (state) {
	case 0: if (CharTypes.isSymbolic(c)) return 1; else return 2;
	case 1: if (CharTypes.isSymbolic(c)) return 1; else return 2;
		default: return 2;
	}
	}
	
	boolean accepting(int state) {return state == 1;}
	boolean dead(int state) {return state == 2;}
 }

static class WhitespaceAcceptor extends GenAcceptor implements DFA {
    public String lexClass() {return "" ;};
    public int numberOfStates() {return 3;};
    
    int nextState (int state, char c) {
    switch (state) {
    case 0: if (CharTypes.isWhitespace(c)) return 1; else return 2;
    case 1: if (CharTypes.isWhitespace(c)) return 1; else return 2;
    	default: return 2; 
    }
    }
    
    boolean accepting(int state) {return state == 1;}
    boolean dead(int state) {return state == 2;}
}

static class CommentAcceptor extends GenAcceptor implements DFA {
	public String lexClass() {return "" ;};
	public int numberOfStates() {return 5;};
	
	int nextState(int state, char c) {
	switch (state) {
	case 0: if (c=='-') return 1; else return 4;
	case 1: if (c=='-') return 2; else return 4;
	case 2: if (CharTypes.isSymbolic(c) || c == '\n' ) return 4; else return 3;
	case 3: if (c == '\n') return 4; else return 3;
		default: return 4;
	}
	}
	
	boolean accepting(int state) {return state == 3;}
	boolean dead(int state) {return state == 4;}
}

static class TokAcceptor extends GenAcceptor implements DFA {

    String tok ;
    int tokLen ;

    
    TokAcceptor (String tok) {this.tok = tok ; tokLen = tok.length() ;}
    public String lexClass() {return tok;};
    public int numberOfStates() {return tokLen+2;};
    
    int nextState(int state, char c) 
    {

	    if(state >= tokLen)
	    {
	    	return state = tokLen+1;
	    }
	    	
	    else if(c == tok.charAt(state)) {
	    	state ++;
	    }
	    	
	    else{
	    	state = tokLen+1;
	    }
	    return state;
	    
    }

    boolean accepting(int state) {return state == tokLen;}
    boolean dead(int state) {return state == tokLen+1;}



}


    // add definition of MH_acceptors here

	static DFA commaccept = new CommentAcceptor();
	static DFA whiteaccept = new WhitespaceAcceptor();
	static DFA symaccept = new SymAcceptor();
	static DFA numaccept = new NumAcceptor();
	static DFA varaccept = new VarAcceptor();
	static DFA boolaccept = new BooleanAcceptor();
	static DFA tokacceptint = new TokAcceptor("Integer");
	static DFA tokacceptbool = new TokAcceptor("Bool");
	static DFA tokacceptif = new TokAcceptor("if");
	static DFA tokacceptthen = new TokAcceptor("then");
	static DFA tokacceptelse = new TokAcceptor("else");
	static DFA tokacceptoparenth = new TokAcceptor("(");
	static DFA tokacceptcparenth = new TokAcceptor(")");
	static DFA tokacceptsemi = new TokAcceptor(";");
	
	static DFA[] MH_acceptors = new DFA[] {whiteaccept, tokacceptoparenth,
		tokacceptint, tokacceptbool, tokacceptif, tokacceptthen, tokacceptelse,
		tokacceptcparenth, tokacceptsemi, commaccept, boolaccept, varaccept, numaccept, symaccept, 
		};
			
	

	

    MH_Lexer (Reader reader) {
	super(reader,MH_acceptors) ;
	
    }

}

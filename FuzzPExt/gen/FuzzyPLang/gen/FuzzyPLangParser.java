// Generated from FuzzyPLang.g4 by ANTLR 4.5
package FuzzyPLang.gen;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FuzzyPLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, COMMENT=26, LINE_COMMENT=27, INT=28, NL=29, NM=30, ZR=31, PM=32, 
		PL=33, FF=34, ID=35, WS=36;
	public static final int
		RULE_prog = 0, RULE_stateMent = 1, RULE_subCompDcl = 2, RULE_newSubComp = 3, 
		RULE_subCompRef = 4, RULE_putInitToken = 5, RULE_petriLine = 6, RULE_lineCont = 7, 
		RULE_arcOp = 8, RULE_inpPlace = 9, RULE_place = 10, RULE_tranz = 11, RULE_otranz = 12, 
		RULE_tranzSpec = 13, RULE_token = 14, RULE_number = 15, RULE_tableDcl = 16, 
		RULE_simpleCellLine = 17, RULE_doubleCellLine = 18, RULE_simpleCell = 19, 
		RULE_doubleCell = 20, RULE_fv = 21;
	public static final String[] ruleNames = {
		"prog", "stateMent", "subCompDcl", "newSubComp", "subCompRef", "putInitToken", 
		"petriLine", "lineCont", "arcOp", "inpPlace", "place", "tranz", "otranz", 
		"tranzSpec", "token", "number", "tableDcl", "simpleCellLine", "doubleCellLine", 
		"simpleCell", "doubleCell", "fv"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'sub'", "'{'", "'}'", "'='", "'new '", "'.'", "'<='", "'->'", 
		"'-('", "')->'", "'iP'", "'ip'", "'P'", "'p'", "'t'", "'T'", "'ot'", "'oT'", 
		"'['", "']'", "','", "'<'", "'>'", "'<phi>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "COMMENT", "LINE_COMMENT", "INT", "NL", "NM", "ZR", "PM", 
		"PL", "FF", "ID", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "FuzzyPLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FuzzyPLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<StateMentContext> stateMent() {
			return getRuleContexts(StateMentContext.class);
		}
		public StateMentContext stateMent(int i) {
			return getRuleContext(StateMentContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(44);
				stateMent();
				}
				}
				setState(47); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << ID))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StateMentContext extends ParserRuleContext {
		public TableDclContext tableDcl() {
			return getRuleContext(TableDclContext.class,0);
		}
		public PetriLineContext petriLine() {
			return getRuleContext(PetriLineContext.class,0);
		}
		public PutInitTokenContext putInitToken() {
			return getRuleContext(PutInitTokenContext.class,0);
		}
		public SubCompDclContext subCompDcl() {
			return getRuleContext(SubCompDclContext.class,0);
		}
		public NewSubCompContext newSubComp() {
			return getRuleContext(NewSubCompContext.class,0);
		}
		public StateMentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stateMent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitStateMent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StateMentContext stateMent() throws RecognitionException {
		StateMentContext _localctx = new StateMentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stateMent);
		try {
			setState(62);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				tableDcl();
				setState(50);
				match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				petriLine();
				setState(53);
				match(T__0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(55);
				putInitToken();
				setState(56);
				match(T__0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(58);
				subCompDcl();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(59);
				newSubComp();
				setState(60);
				match(T__0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubCompDclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(FuzzyPLangParser.ID, 0); }
		public List<StateMentContext> stateMent() {
			return getRuleContexts(StateMentContext.class);
		}
		public StateMentContext stateMent(int i) {
			return getRuleContext(StateMentContext.class,i);
		}
		public SubCompDclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subCompDcl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitSubCompDcl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubCompDclContext subCompDcl() throws RecognitionException {
		SubCompDclContext _localctx = new SubCompDclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_subCompDcl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(T__1);
			setState(65);
			match(ID);
			setState(66);
			match(T__2);
			setState(68); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(67);
				stateMent();
				}
				}
				setState(70); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << ID))) != 0) );
			setState(72);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewSubCompContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(FuzzyPLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(FuzzyPLangParser.ID, i);
		}
		public NewSubCompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newSubComp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitNewSubComp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewSubCompContext newSubComp() throws RecognitionException {
		NewSubCompContext _localctx = new NewSubCompContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_newSubComp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(ID);
			setState(75);
			match(T__4);
			setState(76);
			match(T__5);
			setState(77);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubCompRefContext extends ParserRuleContext {
		public InpPlaceContext inpPlace() {
			return getRuleContext(InpPlaceContext.class,0);
		}
		public OtranzContext otranz() {
			return getRuleContext(OtranzContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(FuzzyPLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(FuzzyPLangParser.ID, i);
		}
		public SubCompRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subCompRef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitSubCompRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubCompRefContext subCompRef() throws RecognitionException {
		SubCompRefContext _localctx = new SubCompRefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_subCompRef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(79);
				match(ID);
				setState(80);
				match(T__6);
				}
				}
				setState(83); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			setState(87);
			switch (_input.LA(1)) {
			case T__11:
			case T__12:
				{
				setState(85);
				inpPlace();
				}
				break;
			case T__17:
			case T__18:
				{
				setState(86);
				otranz();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PutInitTokenContext extends ParserRuleContext {
		public TokenContext token() {
			return getRuleContext(TokenContext.class,0);
		}
		public InpPlaceContext inpPlace() {
			return getRuleContext(InpPlaceContext.class,0);
		}
		public PlaceContext place() {
			return getRuleContext(PlaceContext.class,0);
		}
		public PutInitTokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_putInitToken; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitPutInitToken(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PutInitTokenContext putInitToken() throws RecognitionException {
		PutInitTokenContext _localctx = new PutInitTokenContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_putInitToken);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			switch (_input.LA(1)) {
			case T__11:
			case T__12:
				{
				setState(89);
				inpPlace();
				}
				break;
			case T__13:
			case T__14:
				{
				setState(90);
				place();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(93);
			match(T__7);
			setState(94);
			token();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PetriLineContext extends ParserRuleContext {
		public LineContContext lineCont() {
			return getRuleContext(LineContContext.class,0);
		}
		public InpPlaceContext inpPlace() {
			return getRuleContext(InpPlaceContext.class,0);
		}
		public PlaceContext place() {
			return getRuleContext(PlaceContext.class,0);
		}
		public TranzContext tranz() {
			return getRuleContext(TranzContext.class,0);
		}
		public OtranzContext otranz() {
			return getRuleContext(OtranzContext.class,0);
		}
		public SubCompRefContext subCompRef() {
			return getRuleContext(SubCompRefContext.class,0);
		}
		public PetriLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_petriLine; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitPetriLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PetriLineContext petriLine() throws RecognitionException {
		PetriLineContext _localctx = new PetriLineContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_petriLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			switch (_input.LA(1)) {
			case T__11:
			case T__12:
				{
				setState(96);
				inpPlace();
				}
				break;
			case T__13:
			case T__14:
				{
				setState(97);
				place();
				}
				break;
			case T__15:
			case T__16:
				{
				setState(98);
				tranz();
				}
				break;
			case T__17:
			case T__18:
				{
				setState(99);
				otranz();
				}
				break;
			case ID:
				{
				setState(100);
				subCompRef();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(103);
			lineCont();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContContext extends ParserRuleContext {
		public List<ArcOpContext> arcOp() {
			return getRuleContexts(ArcOpContext.class);
		}
		public ArcOpContext arcOp(int i) {
			return getRuleContext(ArcOpContext.class,i);
		}
		public List<InpPlaceContext> inpPlace() {
			return getRuleContexts(InpPlaceContext.class);
		}
		public InpPlaceContext inpPlace(int i) {
			return getRuleContext(InpPlaceContext.class,i);
		}
		public List<PlaceContext> place() {
			return getRuleContexts(PlaceContext.class);
		}
		public PlaceContext place(int i) {
			return getRuleContext(PlaceContext.class,i);
		}
		public List<TranzContext> tranz() {
			return getRuleContexts(TranzContext.class);
		}
		public TranzContext tranz(int i) {
			return getRuleContext(TranzContext.class,i);
		}
		public List<OtranzContext> otranz() {
			return getRuleContexts(OtranzContext.class);
		}
		public OtranzContext otranz(int i) {
			return getRuleContext(OtranzContext.class,i);
		}
		public List<SubCompRefContext> subCompRef() {
			return getRuleContexts(SubCompRefContext.class);
		}
		public SubCompRefContext subCompRef(int i) {
			return getRuleContext(SubCompRefContext.class,i);
		}
		public LineContContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lineCont; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitLineCont(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContContext lineCont() throws RecognitionException {
		LineContContext _localctx = new LineContContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_lineCont);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(105);
				arcOp();
				setState(111);
				switch (_input.LA(1)) {
				case T__11:
				case T__12:
					{
					setState(106);
					inpPlace();
					}
					break;
				case T__13:
				case T__14:
					{
					setState(107);
					place();
					}
					break;
				case T__15:
				case T__16:
					{
					setState(108);
					tranz();
					}
					break;
				case T__17:
				case T__18:
					{
					setState(109);
					otranz();
					}
					break;
				case ID:
					{
					setState(110);
					subCompRef();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				setState(115); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__8 || _la==T__9 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArcOpContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public ArcOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arcOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitArcOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArcOpContext arcOp() throws RecognitionException {
		ArcOpContext _localctx = new ArcOpContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_arcOp);
		try {
			setState(122);
			switch (_input.LA(1)) {
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(117);
				match(T__8);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				match(T__9);
				setState(119);
				number();
				setState(120);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InpPlaceContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(FuzzyPLangParser.INT, 0); }
		public InpPlaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inpPlace; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitInpPlace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InpPlaceContext inpPlace() throws RecognitionException {
		InpPlaceContext _localctx = new InpPlaceContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_inpPlace);
		try {
			setState(128);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				match(T__11);
				setState(125);
				match(INT);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				match(T__12);
				setState(127);
				match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PlaceContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(FuzzyPLangParser.INT, 0); }
		public PlaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_place; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitPlace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PlaceContext place() throws RecognitionException {
		PlaceContext _localctx = new PlaceContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_place);
		try {
			setState(134);
			switch (_input.LA(1)) {
			case T__13:
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				match(T__13);
				setState(131);
				match(INT);
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				match(T__14);
				setState(133);
				match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TranzContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(FuzzyPLangParser.INT, 0); }
		public TranzSpecContext tranzSpec() {
			return getRuleContext(TranzSpecContext.class,0);
		}
		public TranzContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tranz; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitTranz(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TranzContext tranz() throws RecognitionException {
		TranzContext _localctx = new TranzContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_tranz);
		int _la;
		try {
			setState(146);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				match(T__15);
				setState(137);
				match(INT);
				setState(139);
				_la = _input.LA(1);
				if (_la==T__19) {
					{
					setState(138);
					tranzSpec();
					}
				}

				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(141);
				match(T__16);
				setState(142);
				match(INT);
				setState(144);
				_la = _input.LA(1);
				if (_la==T__19) {
					{
					setState(143);
					tranzSpec();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OtranzContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(FuzzyPLangParser.INT, 0); }
		public TranzSpecContext tranzSpec() {
			return getRuleContext(TranzSpecContext.class,0);
		}
		public OtranzContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_otranz; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitOtranz(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OtranzContext otranz() throws RecognitionException {
		OtranzContext _localctx = new OtranzContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_otranz);
		int _la;
		try {
			setState(158);
			switch (_input.LA(1)) {
			case T__17:
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				match(T__17);
				setState(149);
				match(INT);
				setState(151);
				_la = _input.LA(1);
				if (_la==T__19) {
					{
					setState(150);
					tranzSpec();
					}
				}

				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 2);
				{
				setState(153);
				match(T__18);
				setState(154);
				match(INT);
				setState(156);
				_la = _input.LA(1);
				if (_la==T__19) {
					{
					setState(155);
					tranzSpec();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TranzSpecContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(FuzzyPLangParser.ID, 0); }
		public TerminalNode INT() { return getToken(FuzzyPLangParser.INT, 0); }
		public TranzSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tranzSpec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitTranzSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TranzSpecContext tranzSpec() throws RecognitionException {
		TranzSpecContext _localctx = new TranzSpecContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_tranzSpec);
		try {
			setState(176);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				match(T__19);
				setState(161);
				match(ID);
				setState(162);
				match(T__20);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(163);
				match(T__19);
				setState(164);
				match(ID);
				setState(165);
				match(T__21);
				setState(166);
				match(INT);
				setState(167);
				match(T__20);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(168);
				match(T__19);
				setState(169);
				match(INT);
				setState(170);
				match(T__20);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(171);
				match(T__19);
				setState(172);
				match(INT);
				setState(173);
				match(T__21);
				setState(174);
				match(ID);
				setState(175);
				match(T__20);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TokenContext extends ParserRuleContext {
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public TokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_token; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitToken(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TokenContext token() throws RecognitionException {
		TokenContext _localctx = new TokenContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_token);
		try {
			setState(191);
			switch (_input.LA(1)) {
			case T__22:
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				match(T__22);
				setState(179);
				number();
				setState(180);
				match(T__21);
				setState(181);
				number();
				setState(182);
				match(T__21);
				setState(183);
				number();
				setState(184);
				match(T__21);
				setState(185);
				number();
				setState(186);
				match(T__21);
				setState(187);
				number();
				setState(188);
				match(T__23);
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
				match(T__24);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(FuzzyPLangParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(FuzzyPLangParser.INT, i);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_number);
		try {
			setState(197);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(193);
				match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(194);
				match(INT);
				setState(195);
				match(T__6);
				setState(196);
				match(INT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableDclContext extends ParserRuleContext {
		public TableDclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableDcl; }
	 
		public TableDclContext() { }
		public void copyFrom(TableDclContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TwoXTwoWithPhiContext extends TableDclContext {
		public TerminalNode ID() { return getToken(FuzzyPLangParser.ID, 0); }
		public List<DoubleCellLineContext> doubleCellLine() {
			return getRuleContexts(DoubleCellLineContext.class);
		}
		public DoubleCellLineContext doubleCellLine(int i) {
			return getRuleContext(DoubleCellLineContext.class,i);
		}
		public TwoXTwoWithPhiContext(TableDclContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitTwoXTwoWithPhi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OneXTwoTableContext extends TableDclContext {
		public TerminalNode ID() { return getToken(FuzzyPLangParser.ID, 0); }
		public DoubleCellLineContext doubleCellLine() {
			return getRuleContext(DoubleCellLineContext.class,0);
		}
		public OneXTwoTableContext(TableDclContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitOneXTwoTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TwoXOneWithoutPhiContext extends TableDclContext {
		public TerminalNode ID() { return getToken(FuzzyPLangParser.ID, 0); }
		public List<SimpleCellLineContext> simpleCellLine() {
			return getRuleContexts(SimpleCellLineContext.class);
		}
		public SimpleCellLineContext simpleCellLine(int i) {
			return getRuleContext(SimpleCellLineContext.class,i);
		}
		public TwoXOneWithoutPhiContext(TableDclContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitTwoXOneWithoutPhi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TwoXTwoWithoutPhiContext extends TableDclContext {
		public TerminalNode ID() { return getToken(FuzzyPLangParser.ID, 0); }
		public List<DoubleCellLineContext> doubleCellLine() {
			return getRuleContexts(DoubleCellLineContext.class);
		}
		public DoubleCellLineContext doubleCellLine(int i) {
			return getRuleContext(DoubleCellLineContext.class,i);
		}
		public TwoXTwoWithoutPhiContext(TableDclContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitTwoXTwoWithoutPhi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TwoXOneWithPhiContext extends TableDclContext {
		public TerminalNode ID() { return getToken(FuzzyPLangParser.ID, 0); }
		public List<SimpleCellLineContext> simpleCellLine() {
			return getRuleContexts(SimpleCellLineContext.class);
		}
		public SimpleCellLineContext simpleCellLine(int i) {
			return getRuleContext(SimpleCellLineContext.class,i);
		}
		public TwoXOneWithPhiContext(TableDclContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitTwoXOneWithPhi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OneXOneTableContext extends TableDclContext {
		public TerminalNode ID() { return getToken(FuzzyPLangParser.ID, 0); }
		public SimpleCellLineContext simpleCellLine() {
			return getRuleContext(SimpleCellLineContext.class,0);
		}
		public OneXOneTableContext(TableDclContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitOneXOneTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableDclContext tableDcl() throws RecognitionException {
		TableDclContext _localctx = new TableDclContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_tableDcl);
		try {
			setState(253);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new OneXOneTableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(199);
				match(ID);
				setState(200);
				match(T__4);
				setState(201);
				match(T__2);
				setState(202);
				simpleCellLine();
				setState(203);
				match(T__3);
				}
				break;
			case 2:
				_localctx = new OneXTwoTableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(205);
				match(ID);
				setState(206);
				match(T__4);
				setState(207);
				match(T__2);
				setState(208);
				doubleCellLine();
				setState(209);
				match(T__3);
				}
				break;
			case 3:
				_localctx = new TwoXOneWithoutPhiContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(211);
				match(ID);
				setState(212);
				match(T__4);
				setState(213);
				match(T__2);
				setState(214);
				simpleCellLine();
				setState(215);
				simpleCellLine();
				setState(216);
				simpleCellLine();
				setState(217);
				simpleCellLine();
				setState(218);
				simpleCellLine();
				setState(219);
				match(T__3);
				}
				break;
			case 4:
				_localctx = new TwoXOneWithPhiContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(221);
				match(ID);
				setState(222);
				match(T__4);
				setState(223);
				match(T__2);
				setState(224);
				simpleCellLine();
				setState(225);
				simpleCellLine();
				setState(226);
				simpleCellLine();
				setState(227);
				simpleCellLine();
				setState(228);
				simpleCellLine();
				setState(229);
				simpleCellLine();
				setState(230);
				match(T__3);
				}
				break;
			case 5:
				_localctx = new TwoXTwoWithoutPhiContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(232);
				match(ID);
				setState(233);
				match(T__4);
				setState(234);
				match(T__2);
				setState(235);
				doubleCellLine();
				setState(236);
				doubleCellLine();
				setState(237);
				doubleCellLine();
				setState(238);
				doubleCellLine();
				setState(239);
				doubleCellLine();
				setState(240);
				match(T__3);
				}
				break;
			case 6:
				_localctx = new TwoXTwoWithPhiContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(242);
				match(ID);
				setState(243);
				match(T__4);
				setState(244);
				match(T__2);
				setState(245);
				doubleCellLine();
				setState(246);
				doubleCellLine();
				setState(247);
				doubleCellLine();
				setState(248);
				doubleCellLine();
				setState(249);
				doubleCellLine();
				setState(250);
				doubleCellLine();
				setState(251);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleCellLineContext extends ParserRuleContext {
		public List<SimpleCellContext> simpleCell() {
			return getRuleContexts(SimpleCellContext.class);
		}
		public SimpleCellContext simpleCell(int i) {
			return getRuleContext(SimpleCellContext.class,i);
		}
		public SimpleCellLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleCellLine; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitSimpleCellLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleCellLineContext simpleCellLine() throws RecognitionException {
		SimpleCellLineContext _localctx = new SimpleCellLineContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_simpleCellLine);
		try {
			setState(272);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(255);
				match(T__19);
				setState(256);
				simpleCell();
				setState(257);
				simpleCell();
				setState(258);
				simpleCell();
				setState(259);
				simpleCell();
				setState(260);
				simpleCell();
				setState(261);
				match(T__20);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(263);
				match(T__19);
				setState(264);
				simpleCell();
				setState(265);
				simpleCell();
				setState(266);
				simpleCell();
				setState(267);
				simpleCell();
				setState(268);
				simpleCell();
				setState(269);
				simpleCell();
				setState(270);
				match(T__20);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoubleCellLineContext extends ParserRuleContext {
		public List<DoubleCellContext> doubleCell() {
			return getRuleContexts(DoubleCellContext.class);
		}
		public DoubleCellContext doubleCell(int i) {
			return getRuleContext(DoubleCellContext.class,i);
		}
		public DoubleCellLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doubleCellLine; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitDoubleCellLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoubleCellLineContext doubleCellLine() throws RecognitionException {
		DoubleCellLineContext _localctx = new DoubleCellLineContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_doubleCellLine);
		try {
			setState(291);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(274);
				match(T__19);
				setState(275);
				doubleCell();
				setState(276);
				doubleCell();
				setState(277);
				doubleCell();
				setState(278);
				doubleCell();
				setState(279);
				doubleCell();
				setState(280);
				match(T__20);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(282);
				match(T__19);
				setState(283);
				doubleCell();
				setState(284);
				doubleCell();
				setState(285);
				doubleCell();
				setState(286);
				doubleCell();
				setState(287);
				doubleCell();
				setState(288);
				doubleCell();
				setState(289);
				match(T__20);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleCellContext extends ParserRuleContext {
		public FvContext fv() {
			return getRuleContext(FvContext.class,0);
		}
		public SimpleCellContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleCell; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitSimpleCell(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleCellContext simpleCell() throws RecognitionException {
		SimpleCellContext _localctx = new SimpleCellContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_simpleCell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
			match(T__22);
			setState(294);
			fv();
			setState(295);
			match(T__23);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoubleCellContext extends ParserRuleContext {
		public List<FvContext> fv() {
			return getRuleContexts(FvContext.class);
		}
		public FvContext fv(int i) {
			return getRuleContext(FvContext.class,i);
		}
		public DoubleCellContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doubleCell; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitDoubleCell(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoubleCellContext doubleCell() throws RecognitionException {
		DoubleCellContext _localctx = new DoubleCellContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_doubleCell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			match(T__22);
			setState(298);
			fv();
			setState(299);
			match(T__21);
			setState(300);
			fv();
			setState(301);
			match(T__23);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FvContext extends ParserRuleContext {
		public Token type;
		public TerminalNode NL() { return getToken(FuzzyPLangParser.NL, 0); }
		public TerminalNode NM() { return getToken(FuzzyPLangParser.NM, 0); }
		public TerminalNode ZR() { return getToken(FuzzyPLangParser.ZR, 0); }
		public TerminalNode PM() { return getToken(FuzzyPLangParser.PM, 0); }
		public TerminalNode PL() { return getToken(FuzzyPLangParser.PL, 0); }
		public TerminalNode FF() { return getToken(FuzzyPLangParser.FF, 0); }
		public FvContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fv; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FuzzyPLangVisitor ) return ((FuzzyPLangVisitor<? extends T>)visitor).visitFv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FvContext fv() throws RecognitionException {
		FvContext _localctx = new FvContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_fv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			((FvContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NL) | (1L << NM) | (1L << ZR) | (1L << PM) | (1L << PL) | (1L << FF))) != 0)) ) {
				((FvContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3&\u0134\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\6\2\60\n\2\r\2"+
		"\16\2\61\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3A\n\3"+
		"\3\4\3\4\3\4\3\4\6\4G\n\4\r\4\16\4H\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3"+
		"\6\6\6T\n\6\r\6\16\6U\3\6\3\6\5\6Z\n\6\3\7\3\7\5\7^\n\7\3\7\3\7\3\7\3"+
		"\b\3\b\3\b\3\b\3\b\5\bh\n\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\5\tr\n\t\6"+
		"\tt\n\t\r\t\16\tu\3\n\3\n\3\n\3\n\3\n\5\n}\n\n\3\13\3\13\3\13\3\13\5\13"+
		"\u0083\n\13\3\f\3\f\3\f\3\f\5\f\u0089\n\f\3\r\3\r\3\r\5\r\u008e\n\r\3"+
		"\r\3\r\3\r\5\r\u0093\n\r\5\r\u0095\n\r\3\16\3\16\3\16\5\16\u009a\n\16"+
		"\3\16\3\16\3\16\5\16\u009f\n\16\5\16\u00a1\n\16\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00b3"+
		"\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\5\20\u00c2\n\20\3\21\3\21\3\21\3\21\5\21\u00c8\n\21\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u0100\n\22\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\5\23\u0113\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0126\n\24\3\25\3\25\3\25\3\25"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\2\2\30\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,\2\3\3\2\37$\u0144\2/\3\2\2\2\4@\3\2\2\2"+
		"\6B\3\2\2\2\bL\3\2\2\2\nS\3\2\2\2\f]\3\2\2\2\16g\3\2\2\2\20s\3\2\2\2\22"+
		"|\3\2\2\2\24\u0082\3\2\2\2\26\u0088\3\2\2\2\30\u0094\3\2\2\2\32\u00a0"+
		"\3\2\2\2\34\u00b2\3\2\2\2\36\u00c1\3\2\2\2 \u00c7\3\2\2\2\"\u00ff\3\2"+
		"\2\2$\u0112\3\2\2\2&\u0125\3\2\2\2(\u0127\3\2\2\2*\u012b\3\2\2\2,\u0131"+
		"\3\2\2\2.\60\5\4\3\2/.\3\2\2\2\60\61\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2"+
		"\62\3\3\2\2\2\63\64\5\"\22\2\64\65\7\3\2\2\65A\3\2\2\2\66\67\5\16\b\2"+
		"\678\7\3\2\28A\3\2\2\29:\5\f\7\2:;\7\3\2\2;A\3\2\2\2<A\5\6\4\2=>\5\b\5"+
		"\2>?\7\3\2\2?A\3\2\2\2@\63\3\2\2\2@\66\3\2\2\2@9\3\2\2\2@<\3\2\2\2@=\3"+
		"\2\2\2A\5\3\2\2\2BC\7\4\2\2CD\7%\2\2DF\7\5\2\2EG\5\4\3\2FE\3\2\2\2GH\3"+
		"\2\2\2HF\3\2\2\2HI\3\2\2\2IJ\3\2\2\2JK\7\6\2\2K\7\3\2\2\2LM\7%\2\2MN\7"+
		"\7\2\2NO\7\b\2\2OP\7%\2\2P\t\3\2\2\2QR\7%\2\2RT\7\t\2\2SQ\3\2\2\2TU\3"+
		"\2\2\2US\3\2\2\2UV\3\2\2\2VY\3\2\2\2WZ\5\24\13\2XZ\5\32\16\2YW\3\2\2\2"+
		"YX\3\2\2\2Z\13\3\2\2\2[^\5\24\13\2\\^\5\26\f\2][\3\2\2\2]\\\3\2\2\2^_"+
		"\3\2\2\2_`\7\n\2\2`a\5\36\20\2a\r\3\2\2\2bh\5\24\13\2ch\5\26\f\2dh\5\30"+
		"\r\2eh\5\32\16\2fh\5\n\6\2gb\3\2\2\2gc\3\2\2\2gd\3\2\2\2ge\3\2\2\2gf\3"+
		"\2\2\2hi\3\2\2\2ij\5\20\t\2j\17\3\2\2\2kq\5\22\n\2lr\5\24\13\2mr\5\26"+
		"\f\2nr\5\30\r\2or\5\32\16\2pr\5\n\6\2ql\3\2\2\2qm\3\2\2\2qn\3\2\2\2qo"+
		"\3\2\2\2qp\3\2\2\2rt\3\2\2\2sk\3\2\2\2tu\3\2\2\2us\3\2\2\2uv\3\2\2\2v"+
		"\21\3\2\2\2w}\7\13\2\2xy\7\f\2\2yz\5 \21\2z{\7\r\2\2{}\3\2\2\2|w\3\2\2"+
		"\2|x\3\2\2\2}\23\3\2\2\2~\177\7\16\2\2\177\u0083\7\36\2\2\u0080\u0081"+
		"\7\17\2\2\u0081\u0083\7\36\2\2\u0082~\3\2\2\2\u0082\u0080\3\2\2\2\u0083"+
		"\25\3\2\2\2\u0084\u0085\7\20\2\2\u0085\u0089\7\36\2\2\u0086\u0087\7\21"+
		"\2\2\u0087\u0089\7\36\2\2\u0088\u0084\3\2\2\2\u0088\u0086\3\2\2\2\u0089"+
		"\27\3\2\2\2\u008a\u008b\7\22\2\2\u008b\u008d\7\36\2\2\u008c\u008e\5\34"+
		"\17\2\u008d\u008c\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0095\3\2\2\2\u008f"+
		"\u0090\7\23\2\2\u0090\u0092\7\36\2\2\u0091\u0093\5\34\17\2\u0092\u0091"+
		"\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0095\3\2\2\2\u0094\u008a\3\2\2\2\u0094"+
		"\u008f\3\2\2\2\u0095\31\3\2\2\2\u0096\u0097\7\24\2\2\u0097\u0099\7\36"+
		"\2\2\u0098\u009a\5\34\17\2\u0099\u0098\3\2\2\2\u0099\u009a\3\2\2\2\u009a"+
		"\u00a1\3\2\2\2\u009b\u009c\7\25\2\2\u009c\u009e\7\36\2\2\u009d\u009f\5"+
		"\34\17\2\u009e\u009d\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0"+
		"\u0096\3\2\2\2\u00a0\u009b\3\2\2\2\u00a1\33\3\2\2\2\u00a2\u00a3\7\26\2"+
		"\2\u00a3\u00a4\7%\2\2\u00a4\u00b3\7\27\2\2\u00a5\u00a6\7\26\2\2\u00a6"+
		"\u00a7\7%\2\2\u00a7\u00a8\7\30\2\2\u00a8\u00a9\7\36\2\2\u00a9\u00b3\7"+
		"\27\2\2\u00aa\u00ab\7\26\2\2\u00ab\u00ac\7\36\2\2\u00ac\u00b3\7\27\2\2"+
		"\u00ad\u00ae\7\26\2\2\u00ae\u00af\7\36\2\2\u00af\u00b0\7\30\2\2\u00b0"+
		"\u00b1\7%\2\2\u00b1\u00b3\7\27\2\2\u00b2\u00a2\3\2\2\2\u00b2\u00a5\3\2"+
		"\2\2\u00b2\u00aa\3\2\2\2\u00b2\u00ad\3\2\2\2\u00b3\35\3\2\2\2\u00b4\u00b5"+
		"\7\31\2\2\u00b5\u00b6\5 \21\2\u00b6\u00b7\7\30\2\2\u00b7\u00b8\5 \21\2"+
		"\u00b8\u00b9\7\30\2\2\u00b9\u00ba\5 \21\2\u00ba\u00bb\7\30\2\2\u00bb\u00bc"+
		"\5 \21\2\u00bc\u00bd\7\30\2\2\u00bd\u00be\5 \21\2\u00be\u00bf\7\32\2\2"+
		"\u00bf\u00c2\3\2\2\2\u00c0\u00c2\7\33\2\2\u00c1\u00b4\3\2\2\2\u00c1\u00c0"+
		"\3\2\2\2\u00c2\37\3\2\2\2\u00c3\u00c8\7\36\2\2\u00c4\u00c5\7\36\2\2\u00c5"+
		"\u00c6\7\t\2\2\u00c6\u00c8\7\36\2\2\u00c7\u00c3\3\2\2\2\u00c7\u00c4\3"+
		"\2\2\2\u00c8!\3\2\2\2\u00c9\u00ca\7%\2\2\u00ca\u00cb\7\7\2\2\u00cb\u00cc"+
		"\7\5\2\2\u00cc\u00cd\5$\23\2\u00cd\u00ce\7\6\2\2\u00ce\u0100\3\2\2\2\u00cf"+
		"\u00d0\7%\2\2\u00d0\u00d1\7\7\2\2\u00d1\u00d2\7\5\2\2\u00d2\u00d3\5&\24"+
		"\2\u00d3\u00d4\7\6\2\2\u00d4\u0100\3\2\2\2\u00d5\u00d6\7%\2\2\u00d6\u00d7"+
		"\7\7\2\2\u00d7\u00d8\7\5\2\2\u00d8\u00d9\5$\23\2\u00d9\u00da\5$\23\2\u00da"+
		"\u00db\5$\23\2\u00db\u00dc\5$\23\2\u00dc\u00dd\5$\23\2\u00dd\u00de\7\6"+
		"\2\2\u00de\u0100\3\2\2\2\u00df\u00e0\7%\2\2\u00e0\u00e1\7\7\2\2\u00e1"+
		"\u00e2\7\5\2\2\u00e2\u00e3\5$\23\2\u00e3\u00e4\5$\23\2\u00e4\u00e5\5$"+
		"\23\2\u00e5\u00e6\5$\23\2\u00e6\u00e7\5$\23\2\u00e7\u00e8\5$\23\2\u00e8"+
		"\u00e9\7\6\2\2\u00e9\u0100\3\2\2\2\u00ea\u00eb\7%\2\2\u00eb\u00ec\7\7"+
		"\2\2\u00ec\u00ed\7\5\2\2\u00ed\u00ee\5&\24\2\u00ee\u00ef\5&\24\2\u00ef"+
		"\u00f0\5&\24\2\u00f0\u00f1\5&\24\2\u00f1\u00f2\5&\24\2\u00f2\u00f3\7\6"+
		"\2\2\u00f3\u0100\3\2\2\2\u00f4\u00f5\7%\2\2\u00f5\u00f6\7\7\2\2\u00f6"+
		"\u00f7\7\5\2\2\u00f7\u00f8\5&\24\2\u00f8\u00f9\5&\24\2\u00f9\u00fa\5&"+
		"\24\2\u00fa\u00fb\5&\24\2\u00fb\u00fc\5&\24\2\u00fc\u00fd\5&\24\2\u00fd"+
		"\u00fe\7\6\2\2\u00fe\u0100\3\2\2\2\u00ff\u00c9\3\2\2\2\u00ff\u00cf\3\2"+
		"\2\2\u00ff\u00d5\3\2\2\2\u00ff\u00df\3\2\2\2\u00ff\u00ea\3\2\2\2\u00ff"+
		"\u00f4\3\2\2\2\u0100#\3\2\2\2\u0101\u0102\7\26\2\2\u0102\u0103\5(\25\2"+
		"\u0103\u0104\5(\25\2\u0104\u0105\5(\25\2\u0105\u0106\5(\25\2\u0106\u0107"+
		"\5(\25\2\u0107\u0108\7\27\2\2\u0108\u0113\3\2\2\2\u0109\u010a\7\26\2\2"+
		"\u010a\u010b\5(\25\2\u010b\u010c\5(\25\2\u010c\u010d\5(\25\2\u010d\u010e"+
		"\5(\25\2\u010e\u010f\5(\25\2\u010f\u0110\5(\25\2\u0110\u0111\7\27\2\2"+
		"\u0111\u0113\3\2\2\2\u0112\u0101\3\2\2\2\u0112\u0109\3\2\2\2\u0113%\3"+
		"\2\2\2\u0114\u0115\7\26\2\2\u0115\u0116\5*\26\2\u0116\u0117\5*\26\2\u0117"+
		"\u0118\5*\26\2\u0118\u0119\5*\26\2\u0119\u011a\5*\26\2\u011a\u011b\7\27"+
		"\2\2\u011b\u0126\3\2\2\2\u011c\u011d\7\26\2\2\u011d\u011e\5*\26\2\u011e"+
		"\u011f\5*\26\2\u011f\u0120\5*\26\2\u0120\u0121\5*\26\2\u0121\u0122\5*"+
		"\26\2\u0122\u0123\5*\26\2\u0123\u0124\7\27\2\2\u0124\u0126\3\2\2\2\u0125"+
		"\u0114\3\2\2\2\u0125\u011c\3\2\2\2\u0126\'\3\2\2\2\u0127\u0128\7\31\2"+
		"\2\u0128\u0129\5,\27\2\u0129\u012a\7\32\2\2\u012a)\3\2\2\2\u012b\u012c"+
		"\7\31\2\2\u012c\u012d\5,\27\2\u012d\u012e\7\30\2\2\u012e\u012f\5,\27\2"+
		"\u012f\u0130\7\32\2\2\u0130+\3\2\2\2\u0131\u0132\t\2\2\2\u0132-\3\2\2"+
		"\2\32\61@HUY]gqu|\u0082\u0088\u008d\u0092\u0094\u0099\u009e\u00a0\u00b2"+
		"\u00c1\u00c7\u00ff\u0112\u0125";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
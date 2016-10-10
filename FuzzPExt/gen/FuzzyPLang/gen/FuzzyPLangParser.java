// Generated from /home/ors/IdeaProjects/proba/src/FuzzyPLang.g4 by ANTLR 4.5.3
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
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, COMMENT=24, 
		LINE_COMMENT=25, INT=26, NL=27, NM=28, ZR=29, PM=30, PL=31, FF=32, ID=33, 
		WS=34;
	public static final int
		RULE_prog = 0, RULE_stateMent = 1, RULE_putInitToken = 2, RULE_petriLine = 3, 
		RULE_lineCont = 4, RULE_arcOp = 5, RULE_inpPlace = 6, RULE_place = 7, 
		RULE_tranz = 8, RULE_otranz = 9, RULE_tranzSpec = 10, RULE_token = 11, 
		RULE_number = 12, RULE_tableDcl = 13, RULE_simpleCellLine = 14, RULE_doubleCellLine = 15, 
		RULE_simpleCell = 16, RULE_doubleCell = 17, RULE_fv = 18;
	public static final String[] ruleNames = {
		"prog", "stateMent", "putInitToken", "petriLine", "lineCont", "arcOp", 
		"inpPlace", "place", "tranz", "otranz", "tranzSpec", "token", "number", 
		"tableDcl", "simpleCellLine", "doubleCellLine", "simpleCell", "doubleCell", 
		"fv"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'<='", "'->'", "'-('", "')->'", "'iP'", "'ip'", "'P'", "'p'", 
		"'t'", "'T'", "'ot'", "'oT'", "'['", "']'", "','", "'<'", "'>'", "'<phi>'", 
		"'.'", "'='", "'{'", "'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"COMMENT", "LINE_COMMENT", "INT", "NL", "NM", "ZR", "PM", "PL", "FF", 
		"ID", "WS"
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
			setState(39); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(38);
				stateMent();
				}
				}
				setState(41); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << ID))) != 0) );
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
			setState(52);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				tableDcl();
				setState(44);
				match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				petriLine();
				setState(47);
				match(T__0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(49);
				putInitToken();
				setState(50);
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
		enterRule(_localctx, 4, RULE_putInitToken);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			switch (_input.LA(1)) {
			case T__5:
			case T__6:
				{
				setState(54);
				inpPlace();
				}
				break;
			case T__7:
			case T__8:
				{
				setState(55);
				place();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(58);
			match(T__1);
			setState(59);
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
		enterRule(_localctx, 6, RULE_petriLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			switch (_input.LA(1)) {
			case T__5:
			case T__6:
				{
				setState(61);
				inpPlace();
				}
				break;
			case T__7:
			case T__8:
				{
				setState(62);
				place();
				}
				break;
			case T__9:
			case T__10:
				{
				setState(63);
				tranz();
				}
				break;
			case T__11:
			case T__12:
				{
				setState(64);
				otranz();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(67);
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
		enterRule(_localctx, 8, RULE_lineCont);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(69);
				arcOp();
				setState(74);
				switch (_input.LA(1)) {
				case T__5:
				case T__6:
					{
					setState(70);
					inpPlace();
					}
					break;
				case T__7:
				case T__8:
					{
					setState(71);
					place();
					}
					break;
				case T__9:
				case T__10:
					{
					setState(72);
					tranz();
					}
					break;
				case T__11:
				case T__12:
					{
					setState(73);
					otranz();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				setState(78); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__2 || _la==T__3 );
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
		enterRule(_localctx, 10, RULE_arcOp);
		try {
			setState(85);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				match(T__2);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				match(T__3);
				setState(82);
				number();
				setState(83);
				match(T__4);
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
		enterRule(_localctx, 12, RULE_inpPlace);
		try {
			setState(91);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				match(T__5);
				setState(88);
				match(INT);
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				match(T__6);
				setState(90);
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
		enterRule(_localctx, 14, RULE_place);
		try {
			setState(97);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(93);
				match(T__7);
				setState(94);
				match(INT);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				match(T__8);
				setState(96);
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
		enterRule(_localctx, 16, RULE_tranz);
		int _la;
		try {
			setState(109);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(99);
				match(T__9);
				setState(100);
				match(INT);
				setState(102);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(101);
					tranzSpec();
					}
				}

				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(104);
				match(T__10);
				setState(105);
				match(INT);
				setState(107);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(106);
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
		enterRule(_localctx, 18, RULE_otranz);
		int _la;
		try {
			setState(121);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(111);
				match(T__11);
				setState(112);
				match(INT);
				setState(114);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(113);
					tranzSpec();
					}
				}

				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(116);
				match(T__12);
				setState(117);
				match(INT);
				setState(119);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(118);
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
		enterRule(_localctx, 20, RULE_tranzSpec);
		try {
			setState(139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(123);
				match(T__13);
				setState(124);
				match(ID);
				setState(125);
				match(T__14);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				match(T__13);
				setState(127);
				match(ID);
				setState(128);
				match(T__15);
				setState(129);
				match(INT);
				setState(130);
				match(T__14);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(131);
				match(T__13);
				setState(132);
				match(INT);
				setState(133);
				match(T__14);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(134);
				match(T__13);
				setState(135);
				match(INT);
				setState(136);
				match(T__15);
				setState(137);
				match(ID);
				setState(138);
				match(T__14);
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
		enterRule(_localctx, 22, RULE_token);
		try {
			setState(154);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(141);
				match(T__16);
				setState(142);
				number();
				setState(143);
				match(T__15);
				setState(144);
				number();
				setState(145);
				match(T__15);
				setState(146);
				number();
				setState(147);
				match(T__15);
				setState(148);
				number();
				setState(149);
				match(T__15);
				setState(150);
				number();
				setState(151);
				match(T__17);
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 2);
				{
				setState(153);
				match(T__18);
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
		enterRule(_localctx, 24, RULE_number);
		try {
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(156);
				match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(157);
				match(INT);
				setState(158);
				match(T__19);
				setState(159);
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
		enterRule(_localctx, 26, RULE_tableDcl);
		try {
			setState(216);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new OneXOneTableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(162);
				match(ID);
				setState(163);
				match(T__20);
				setState(164);
				match(T__21);
				setState(165);
				simpleCellLine();
				setState(166);
				match(T__22);
				}
				break;
			case 2:
				_localctx = new OneXTwoTableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(168);
				match(ID);
				setState(169);
				match(T__20);
				setState(170);
				match(T__21);
				setState(171);
				doubleCellLine();
				setState(172);
				match(T__22);
				}
				break;
			case 3:
				_localctx = new TwoXOneWithoutPhiContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(174);
				match(ID);
				setState(175);
				match(T__20);
				setState(176);
				match(T__21);
				setState(177);
				simpleCellLine();
				setState(178);
				simpleCellLine();
				setState(179);
				simpleCellLine();
				setState(180);
				simpleCellLine();
				setState(181);
				simpleCellLine();
				setState(182);
				match(T__22);
				}
				break;
			case 4:
				_localctx = new TwoXOneWithPhiContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(184);
				match(ID);
				setState(185);
				match(T__20);
				setState(186);
				match(T__21);
				setState(187);
				simpleCellLine();
				setState(188);
				simpleCellLine();
				setState(189);
				simpleCellLine();
				setState(190);
				simpleCellLine();
				setState(191);
				simpleCellLine();
				setState(192);
				simpleCellLine();
				setState(193);
				match(T__22);
				}
				break;
			case 5:
				_localctx = new TwoXTwoWithoutPhiContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(195);
				match(ID);
				setState(196);
				match(T__20);
				setState(197);
				match(T__21);
				setState(198);
				doubleCellLine();
				setState(199);
				doubleCellLine();
				setState(200);
				doubleCellLine();
				setState(201);
				doubleCellLine();
				setState(202);
				doubleCellLine();
				setState(203);
				match(T__22);
				}
				break;
			case 6:
				_localctx = new TwoXTwoWithPhiContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(205);
				match(ID);
				setState(206);
				match(T__20);
				setState(207);
				match(T__21);
				setState(208);
				doubleCellLine();
				setState(209);
				doubleCellLine();
				setState(210);
				doubleCellLine();
				setState(211);
				doubleCellLine();
				setState(212);
				doubleCellLine();
				setState(213);
				doubleCellLine();
				setState(214);
				match(T__22);
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
		enterRule(_localctx, 28, RULE_simpleCellLine);
		try {
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(218);
				match(T__13);
				setState(219);
				simpleCell();
				setState(220);
				simpleCell();
				setState(221);
				simpleCell();
				setState(222);
				simpleCell();
				setState(223);
				simpleCell();
				setState(224);
				match(T__14);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(226);
				match(T__13);
				setState(227);
				simpleCell();
				setState(228);
				simpleCell();
				setState(229);
				simpleCell();
				setState(230);
				simpleCell();
				setState(231);
				simpleCell();
				setState(232);
				simpleCell();
				setState(233);
				match(T__14);
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
		enterRule(_localctx, 30, RULE_doubleCellLine);
		try {
			setState(254);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(237);
				match(T__13);
				setState(238);
				doubleCell();
				setState(239);
				doubleCell();
				setState(240);
				doubleCell();
				setState(241);
				doubleCell();
				setState(242);
				doubleCell();
				setState(243);
				match(T__14);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
				match(T__13);
				setState(246);
				doubleCell();
				setState(247);
				doubleCell();
				setState(248);
				doubleCell();
				setState(249);
				doubleCell();
				setState(250);
				doubleCell();
				setState(251);
				doubleCell();
				setState(252);
				match(T__14);
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
		enterRule(_localctx, 32, RULE_simpleCell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			match(T__16);
			setState(257);
			fv();
			setState(258);
			match(T__17);
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
		enterRule(_localctx, 34, RULE_doubleCell);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(T__16);
			setState(261);
			fv();
			setState(262);
			match(T__15);
			setState(263);
			fv();
			setState(264);
			match(T__17);
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
		enterRule(_localctx, 36, RULE_fv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3$\u010f\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\6\2*\n\2\r\2\16\2+\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\5\3\67\n\3\3\4\3\4\5\4;\n\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5"+
		"D\n\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\5\6M\n\6\6\6O\n\6\r\6\16\6P\3\7\3\7"+
		"\3\7\3\7\3\7\5\7X\n\7\3\b\3\b\3\b\3\b\5\b^\n\b\3\t\3\t\3\t\3\t\5\td\n"+
		"\t\3\n\3\n\3\n\5\ni\n\n\3\n\3\n\3\n\5\nn\n\n\5\np\n\n\3\13\3\13\3\13\5"+
		"\13u\n\13\3\13\3\13\3\13\5\13z\n\13\5\13|\n\13\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u008e\n\f\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u009d\n\r\3\16\3\16\3\16\3\16"+
		"\5\16\u00a3\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\5\17\u00db\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00ee\n\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21"+
		"\u0101\n\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\2\2\25\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&\2\3\3\2\35\""+
		"\u011b\2)\3\2\2\2\4\66\3\2\2\2\6:\3\2\2\2\bC\3\2\2\2\nN\3\2\2\2\fW\3\2"+
		"\2\2\16]\3\2\2\2\20c\3\2\2\2\22o\3\2\2\2\24{\3\2\2\2\26\u008d\3\2\2\2"+
		"\30\u009c\3\2\2\2\32\u00a2\3\2\2\2\34\u00da\3\2\2\2\36\u00ed\3\2\2\2 "+
		"\u0100\3\2\2\2\"\u0102\3\2\2\2$\u0106\3\2\2\2&\u010c\3\2\2\2(*\5\4\3\2"+
		")(\3\2\2\2*+\3\2\2\2+)\3\2\2\2+,\3\2\2\2,\3\3\2\2\2-.\5\34\17\2./\7\3"+
		"\2\2/\67\3\2\2\2\60\61\5\b\5\2\61\62\7\3\2\2\62\67\3\2\2\2\63\64\5\6\4"+
		"\2\64\65\7\3\2\2\65\67\3\2\2\2\66-\3\2\2\2\66\60\3\2\2\2\66\63\3\2\2\2"+
		"\67\5\3\2\2\28;\5\16\b\29;\5\20\t\2:8\3\2\2\2:9\3\2\2\2;<\3\2\2\2<=\7"+
		"\4\2\2=>\5\30\r\2>\7\3\2\2\2?D\5\16\b\2@D\5\20\t\2AD\5\22\n\2BD\5\24\13"+
		"\2C?\3\2\2\2C@\3\2\2\2CA\3\2\2\2CB\3\2\2\2DE\3\2\2\2EF\5\n\6\2F\t\3\2"+
		"\2\2GL\5\f\7\2HM\5\16\b\2IM\5\20\t\2JM\5\22\n\2KM\5\24\13\2LH\3\2\2\2"+
		"LI\3\2\2\2LJ\3\2\2\2LK\3\2\2\2MO\3\2\2\2NG\3\2\2\2OP\3\2\2\2PN\3\2\2\2"+
		"PQ\3\2\2\2Q\13\3\2\2\2RX\7\5\2\2ST\7\6\2\2TU\5\32\16\2UV\7\7\2\2VX\3\2"+
		"\2\2WR\3\2\2\2WS\3\2\2\2X\r\3\2\2\2YZ\7\b\2\2Z^\7\34\2\2[\\\7\t\2\2\\"+
		"^\7\34\2\2]Y\3\2\2\2][\3\2\2\2^\17\3\2\2\2_`\7\n\2\2`d\7\34\2\2ab\7\13"+
		"\2\2bd\7\34\2\2c_\3\2\2\2ca\3\2\2\2d\21\3\2\2\2ef\7\f\2\2fh\7\34\2\2g"+
		"i\5\26\f\2hg\3\2\2\2hi\3\2\2\2ip\3\2\2\2jk\7\r\2\2km\7\34\2\2ln\5\26\f"+
		"\2ml\3\2\2\2mn\3\2\2\2np\3\2\2\2oe\3\2\2\2oj\3\2\2\2p\23\3\2\2\2qr\7\16"+
		"\2\2rt\7\34\2\2su\5\26\f\2ts\3\2\2\2tu\3\2\2\2u|\3\2\2\2vw\7\17\2\2wy"+
		"\7\34\2\2xz\5\26\f\2yx\3\2\2\2yz\3\2\2\2z|\3\2\2\2{q\3\2\2\2{v\3\2\2\2"+
		"|\25\3\2\2\2}~\7\20\2\2~\177\7#\2\2\177\u008e\7\21\2\2\u0080\u0081\7\20"+
		"\2\2\u0081\u0082\7#\2\2\u0082\u0083\7\22\2\2\u0083\u0084\7\34\2\2\u0084"+
		"\u008e\7\21\2\2\u0085\u0086\7\20\2\2\u0086\u0087\7\34\2\2\u0087\u008e"+
		"\7\21\2\2\u0088\u0089\7\20\2\2\u0089\u008a\7\34\2\2\u008a\u008b\7\22\2"+
		"\2\u008b\u008c\7#\2\2\u008c\u008e\7\21\2\2\u008d}\3\2\2\2\u008d\u0080"+
		"\3\2\2\2\u008d\u0085\3\2\2\2\u008d\u0088\3\2\2\2\u008e\27\3\2\2\2\u008f"+
		"\u0090\7\23\2\2\u0090\u0091\5\32\16\2\u0091\u0092\7\22\2\2\u0092\u0093"+
		"\5\32\16\2\u0093\u0094\7\22\2\2\u0094\u0095\5\32\16\2\u0095\u0096\7\22"+
		"\2\2\u0096\u0097\5\32\16\2\u0097\u0098\7\22\2\2\u0098\u0099\5\32\16\2"+
		"\u0099\u009a\7\24\2\2\u009a\u009d\3\2\2\2\u009b\u009d\7\25\2\2\u009c\u008f"+
		"\3\2\2\2\u009c\u009b\3\2\2\2\u009d\31\3\2\2\2\u009e\u00a3\7\34\2\2\u009f"+
		"\u00a0\7\34\2\2\u00a0\u00a1\7\26\2\2\u00a1\u00a3\7\34\2\2\u00a2\u009e"+
		"\3\2\2\2\u00a2\u009f\3\2\2\2\u00a3\33\3\2\2\2\u00a4\u00a5\7#\2\2\u00a5"+
		"\u00a6\7\27\2\2\u00a6\u00a7\7\30\2\2\u00a7\u00a8\5\36\20\2\u00a8\u00a9"+
		"\7\31\2\2\u00a9\u00db\3\2\2\2\u00aa\u00ab\7#\2\2\u00ab\u00ac\7\27\2\2"+
		"\u00ac\u00ad\7\30\2\2\u00ad\u00ae\5 \21\2\u00ae\u00af\7\31\2\2\u00af\u00db"+
		"\3\2\2\2\u00b0\u00b1\7#\2\2\u00b1\u00b2\7\27\2\2\u00b2\u00b3\7\30\2\2"+
		"\u00b3\u00b4\5\36\20\2\u00b4\u00b5\5\36\20\2\u00b5\u00b6\5\36\20\2\u00b6"+
		"\u00b7\5\36\20\2\u00b7\u00b8\5\36\20\2\u00b8\u00b9\7\31\2\2\u00b9\u00db"+
		"\3\2\2\2\u00ba\u00bb\7#\2\2\u00bb\u00bc\7\27\2\2\u00bc\u00bd\7\30\2\2"+
		"\u00bd\u00be\5\36\20\2\u00be\u00bf\5\36\20\2\u00bf\u00c0\5\36\20\2\u00c0"+
		"\u00c1\5\36\20\2\u00c1\u00c2\5\36\20\2\u00c2\u00c3\5\36\20\2\u00c3\u00c4"+
		"\7\31\2\2\u00c4\u00db\3\2\2\2\u00c5\u00c6\7#\2\2\u00c6\u00c7\7\27\2\2"+
		"\u00c7\u00c8\7\30\2\2\u00c8\u00c9\5 \21\2\u00c9\u00ca\5 \21\2\u00ca\u00cb"+
		"\5 \21\2\u00cb\u00cc\5 \21\2\u00cc\u00cd\5 \21\2\u00cd\u00ce\7\31\2\2"+
		"\u00ce\u00db\3\2\2\2\u00cf\u00d0\7#\2\2\u00d0\u00d1\7\27\2\2\u00d1\u00d2"+
		"\7\30\2\2\u00d2\u00d3\5 \21\2\u00d3\u00d4\5 \21\2\u00d4\u00d5\5 \21\2"+
		"\u00d5\u00d6\5 \21\2\u00d6\u00d7\5 \21\2\u00d7\u00d8\5 \21\2\u00d8\u00d9"+
		"\7\31\2\2\u00d9\u00db\3\2\2\2\u00da\u00a4\3\2\2\2\u00da\u00aa\3\2\2\2"+
		"\u00da\u00b0\3\2\2\2\u00da\u00ba\3\2\2\2\u00da\u00c5\3\2\2\2\u00da\u00cf"+
		"\3\2\2\2\u00db\35\3\2\2\2\u00dc\u00dd\7\20\2\2\u00dd\u00de\5\"\22\2\u00de"+
		"\u00df\5\"\22\2\u00df\u00e0\5\"\22\2\u00e0\u00e1\5\"\22\2\u00e1\u00e2"+
		"\5\"\22\2\u00e2\u00e3\7\21\2\2\u00e3\u00ee\3\2\2\2\u00e4\u00e5\7\20\2"+
		"\2\u00e5\u00e6\5\"\22\2\u00e6\u00e7\5\"\22\2\u00e7\u00e8\5\"\22\2\u00e8"+
		"\u00e9\5\"\22\2\u00e9\u00ea\5\"\22\2\u00ea\u00eb\5\"\22\2\u00eb\u00ec"+
		"\7\21\2\2\u00ec\u00ee\3\2\2\2\u00ed\u00dc\3\2\2\2\u00ed\u00e4\3\2\2\2"+
		"\u00ee\37\3\2\2\2\u00ef\u00f0\7\20\2\2\u00f0\u00f1\5$\23\2\u00f1\u00f2"+
		"\5$\23\2\u00f2\u00f3\5$\23\2\u00f3\u00f4\5$\23\2\u00f4\u00f5\5$\23\2\u00f5"+
		"\u00f6\7\21\2\2\u00f6\u0101\3\2\2\2\u00f7\u00f8\7\20\2\2\u00f8\u00f9\5"+
		"$\23\2\u00f9\u00fa\5$\23\2\u00fa\u00fb\5$\23\2\u00fb\u00fc\5$\23\2\u00fc"+
		"\u00fd\5$\23\2\u00fd\u00fe\5$\23\2\u00fe\u00ff\7\21\2\2\u00ff\u0101\3"+
		"\2\2\2\u0100\u00ef\3\2\2\2\u0100\u00f7\3\2\2\2\u0101!\3\2\2\2\u0102\u0103"+
		"\7\23\2\2\u0103\u0104\5&\24\2\u0104\u0105\7\24\2\2\u0105#\3\2\2\2\u0106"+
		"\u0107\7\23\2\2\u0107\u0108\5&\24\2\u0108\u0109\7\22\2\2\u0109\u010a\5"+
		"&\24\2\u010a\u010b\7\24\2\2\u010b%\3\2\2\2\u010c\u010d\t\2\2\2\u010d\'"+
		"\3\2\2\2\27+\66:CLPW]chmoty{\u008d\u009c\u00a2\u00da\u00ed\u0100";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
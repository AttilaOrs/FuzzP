// Generated from UETPNLisp.g4 by ANTLR 4.5
package UETPNLisp.gen;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UETPNLispParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		SEP=25, INT=26;
	public static final int
		RULE_prog = 0, RULE_subexp = 1, RULE_op = 2, RULE_leaf = 3, RULE_nr = 4, 
		RULE_outType = 5, RULE_inpType = 6, RULE_poz_neg_double = 7;
	public static final String[] ruleNames = {
		"prog", "subexp", "op", "leaf", "nr", "outType", "inpType", "poz_neg_double"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'@'", "'&'", "'?'", "'#'", "'+'", "'*'", "'%'", "'d'", 
		"'i'", "'o'", "'b'", "'m'", "'c'", "'v'", "'n'", "'br'", "'nr'", "'eip'", 
		"'enp'", "'su'", "'sd'", "'-'", "':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "SEP", "INT"
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
	public String getGrammarFileName() { return "UETPNLisp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public UETPNLispParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public OpContext op() {
			return getRuleContext(OpContext.class,0);
		}
		public List<SubexpContext> subexp() {
			return getRuleContexts(SubexpContext.class);
		}
		public SubexpContext subexp(int i) {
			return getRuleContext(SubexpContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			match(T__0);
			setState(17);
			op();
			setState(18);
			subexp();
			setState(19);
			subexp();
			setState(20);
			match(T__1);
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

	public static class SubexpContext extends ParserRuleContext {
		public OpContext op() {
			return getRuleContext(OpContext.class,0);
		}
		public List<SubexpContext> subexp() {
			return getRuleContexts(SubexpContext.class);
		}
		public SubexpContext subexp(int i) {
			return getRuleContext(SubexpContext.class,i);
		}
		public LeafContext leaf() {
			return getRuleContext(LeafContext.class,0);
		}
		public SubexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subexp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitSubexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubexpContext subexp() throws RecognitionException {
		SubexpContext _localctx = new SubexpContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_subexp);
		try {
			setState(29);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(22);
				match(T__0);
				setState(23);
				op();
				setState(24);
				subexp();
				setState(25);
				subexp();
				setState(26);
				match(T__1);
				}
				break;
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(28);
				leaf();
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

	public static class OpContext extends ParserRuleContext {
		public OpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_op; }
	 
		public OpContext() { }
		public void copyFrom(OpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LoopContext extends OpContext {
		public LoopContext(OpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitLoop(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddContext extends OpContext {
		public AddContext(OpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConcContext extends OpContext {
		public ConcContext(OpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitConc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiContext extends OpContext {
		public MultiContext(OpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitMulti(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SeqContext extends OpContext {
		public SeqContext(OpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitSeq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SelcContext extends OpContext {
		public SelcContext(OpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitSelc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PosNegSpliContext extends OpContext {
		public PosNegSpliContext(OpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitPosNegSpli(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpContext op() throws RecognitionException {
		OpContext _localctx = new OpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_op);
		try {
			setState(38);
			switch (_input.LA(1)) {
			case T__2:
				_localctx = new SeqContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(31);
				match(T__2);
				}
				break;
			case T__3:
				_localctx = new ConcContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(32);
				match(T__3);
				}
				break;
			case T__4:
				_localctx = new SelcContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(33);
				match(T__4);
				}
				break;
			case T__5:
				_localctx = new LoopContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(34);
				match(T__5);
				}
				break;
			case T__6:
				_localctx = new AddContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(35);
				match(T__6);
				}
				break;
			case T__7:
				_localctx = new MultiContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(36);
				match(T__7);
				}
				break;
			case T__8:
				_localctx = new PosNegSpliContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(37);
				match(T__8);
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

	public static class LeafContext extends ParserRuleContext {
		public LeafContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_leaf; }
	 
		public LeafContext() { }
		public void copyFrom(LeafContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class InvContext extends LeafContext {
		public InvContext(LeafContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitInv(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InputContext extends LeafContext {
		public List<TerminalNode> SEP() { return getTokens(UETPNLispParser.SEP); }
		public TerminalNode SEP(int i) {
			return getToken(UETPNLispParser.SEP, i);
		}
		public InpTypeContext inpType() {
			return getRuleContext(InpTypeContext.class,0);
		}
		public NrContext nr() {
			return getRuleContext(NrContext.class,0);
		}
		public InputContext(LeafContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitInput(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MemoryContext extends LeafContext {
		public TerminalNode SEP() { return getToken(UETPNLispParser.SEP, 0); }
		public NrContext nr() {
			return getRuleContext(NrContext.class,0);
		}
		public MemoryContext(LeafContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitMemory(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PosNegSplitContext extends LeafContext {
		public TerminalNode SEP() { return getToken(UETPNLispParser.SEP, 0); }
		public Poz_neg_doubleContext poz_neg_double() {
			return getRuleContext(Poz_neg_doubleContext.class,0);
		}
		public PosNegSplitContext(LeafContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitPosNegSplit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OutputContext extends LeafContext {
		public List<TerminalNode> SEP() { return getTokens(UETPNLispParser.SEP); }
		public TerminalNode SEP(int i) {
			return getToken(UETPNLispParser.SEP, i);
		}
		public OutTypeContext outType() {
			return getRuleContext(OutTypeContext.class,0);
		}
		public NrContext nr() {
			return getRuleContext(NrContext.class,0);
		}
		public OutputContext(LeafContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitOutput(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlockContext extends LeafContext {
		public BlockContext(LeafContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegateContext extends LeafContext {
		public NegateContext(LeafContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitNegate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DelayContext extends LeafContext {
		public TerminalNode SEP() { return getToken(UETPNLispParser.SEP, 0); }
		public NrContext nr() {
			return getRuleContext(NrContext.class,0);
		}
		public DelayContext(LeafContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitDelay(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LeafContext leaf() throws RecognitionException {
		LeafContext _localctx = new LeafContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_leaf);
		try {
			setState(64);
			switch (_input.LA(1)) {
			case T__9:
				_localctx = new DelayContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				match(T__9);
				setState(41);
				match(SEP);
				setState(42);
				nr();
				}
				break;
			case T__10:
				_localctx = new InputContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
				match(T__10);
				setState(44);
				match(SEP);
				setState(45);
				inpType();
				setState(46);
				match(SEP);
				setState(47);
				nr();
				}
				break;
			case T__11:
				_localctx = new OutputContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(49);
				match(T__11);
				setState(50);
				match(SEP);
				setState(51);
				outType();
				setState(52);
				match(SEP);
				setState(53);
				nr();
				}
				break;
			case T__12:
				_localctx = new BlockContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(55);
				match(T__12);
				}
				break;
			case T__13:
				_localctx = new MemoryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(56);
				match(T__13);
				setState(57);
				match(SEP);
				setState(58);
				nr();
				}
				break;
			case T__14:
				_localctx = new PosNegSplitContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(59);
				match(T__14);
				setState(60);
				match(SEP);
				setState(61);
				poz_neg_double();
				}
				break;
			case T__15:
				_localctx = new InvContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(62);
				match(T__15);
				}
				break;
			case T__16:
				_localctx = new NegateContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(63);
				match(T__16);
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

	public static class NrContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(UETPNLispParser.INT, 0); }
		public NrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitNr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NrContext nr() throws RecognitionException {
		NrContext _localctx = new NrContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_nr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(INT);
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

	public static class OutTypeContext extends ParserRuleContext {
		public OutTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outType; }
	 
		public OutTypeContext() { }
		public void copyFrom(OutTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CopyOutTypeContext extends OutTypeContext {
		public CopyOutTypeContext(OutTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitCopyOutType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutTypeContext outType() throws RecognitionException {
		OutTypeContext _localctx = new OutTypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_outType);
		try {
			_localctx = new CopyOutTypeContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(T__14);
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

	public static class InpTypeContext extends ParserRuleContext {
		public InpTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inpType; }
	 
		public InpTypeContext() { }
		public void copyFrom(InpTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EnableIfPhiContext extends InpTypeContext {
		public EnableIfPhiContext(InpTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitEnableIfPhi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlockingReaderContext extends InpTypeContext {
		public BlockingReaderContext(InpTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitBlockingReader(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EnableIfNotPhiContext extends InpTypeContext {
		public EnableIfNotPhiContext(InpTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitEnableIfNotPhi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NonblockingReaderContext extends InpTypeContext {
		public NonblockingReaderContext(InpTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitNonblockingReader(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShiftUpIfEventTableContext extends InpTypeContext {
		public ShiftUpIfEventTableContext(InpTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitShiftUpIfEventTable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShiftDownIfEventTableContext extends InpTypeContext {
		public ShiftDownIfEventTableContext(InpTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitShiftDownIfEventTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InpTypeContext inpType() throws RecognitionException {
		InpTypeContext _localctx = new InpTypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_inpType);
		try {
			setState(76);
			switch (_input.LA(1)) {
			case T__17:
				_localctx = new BlockingReaderContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				match(T__17);
				}
				break;
			case T__18:
				_localctx = new NonblockingReaderContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(71);
				match(T__18);
				}
				break;
			case T__19:
				_localctx = new EnableIfPhiContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(72);
				match(T__19);
				}
				break;
			case T__20:
				_localctx = new EnableIfNotPhiContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(73);
				match(T__20);
				}
				break;
			case T__21:
				_localctx = new ShiftUpIfEventTableContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(74);
				match(T__21);
				}
				break;
			case T__22:
				_localctx = new ShiftDownIfEventTableContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(75);
				match(T__22);
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

	public static class Poz_neg_doubleContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(UETPNLispParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(UETPNLispParser.INT, i);
		}
		public Poz_neg_doubleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_poz_neg_double; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UETPNLispVisitor ) return ((UETPNLispVisitor<? extends T>)visitor).visitPoz_neg_double(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Poz_neg_doubleContext poz_neg_double() throws RecognitionException {
		Poz_neg_doubleContext _localctx = new Poz_neg_doubleContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_poz_neg_double);
		try {
			setState(88);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				match(INT);
				setState(79);
				matchWildcard();
				setState(80);
				match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				match(T__23);
				setState(82);
				match(INT);
				setState(83);
				matchWildcard();
				setState(84);
				match(INT);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(85);
				match(INT);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(86);
				match(T__23);
				setState(87);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\34]\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3 \n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\5\4)\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5C\n\5\3\6\3\6\3\7\3\7\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\5\bO\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t"+
		"[\n\t\3\t\2\2\n\2\4\6\b\n\f\16\20\2\2j\2\22\3\2\2\2\4\37\3\2\2\2\6(\3"+
		"\2\2\2\bB\3\2\2\2\nD\3\2\2\2\fF\3\2\2\2\16N\3\2\2\2\20Z\3\2\2\2\22\23"+
		"\7\3\2\2\23\24\5\6\4\2\24\25\5\4\3\2\25\26\5\4\3\2\26\27\7\4\2\2\27\3"+
		"\3\2\2\2\30\31\7\3\2\2\31\32\5\6\4\2\32\33\5\4\3\2\33\34\5\4\3\2\34\35"+
		"\7\4\2\2\35 \3\2\2\2\36 \5\b\5\2\37\30\3\2\2\2\37\36\3\2\2\2 \5\3\2\2"+
		"\2!)\7\5\2\2\")\7\6\2\2#)\7\7\2\2$)\7\b\2\2%)\7\t\2\2&)\7\n\2\2\')\7\13"+
		"\2\2(!\3\2\2\2(\"\3\2\2\2(#\3\2\2\2($\3\2\2\2(%\3\2\2\2(&\3\2\2\2(\'\3"+
		"\2\2\2)\7\3\2\2\2*+\7\f\2\2+,\7\33\2\2,C\5\n\6\2-.\7\r\2\2./\7\33\2\2"+
		"/\60\5\16\b\2\60\61\7\33\2\2\61\62\5\n\6\2\62C\3\2\2\2\63\64\7\16\2\2"+
		"\64\65\7\33\2\2\65\66\5\f\7\2\66\67\7\33\2\2\678\5\n\6\28C\3\2\2\29C\7"+
		"\17\2\2:;\7\20\2\2;<\7\33\2\2<C\5\n\6\2=>\7\21\2\2>?\7\33\2\2?C\5\20\t"+
		"\2@C\7\22\2\2AC\7\23\2\2B*\3\2\2\2B-\3\2\2\2B\63\3\2\2\2B9\3\2\2\2B:\3"+
		"\2\2\2B=\3\2\2\2B@\3\2\2\2BA\3\2\2\2C\t\3\2\2\2DE\7\34\2\2E\13\3\2\2\2"+
		"FG\7\21\2\2G\r\3\2\2\2HO\7\24\2\2IO\7\25\2\2JO\7\26\2\2KO\7\27\2\2LO\7"+
		"\30\2\2MO\7\31\2\2NH\3\2\2\2NI\3\2\2\2NJ\3\2\2\2NK\3\2\2\2NL\3\2\2\2N"+
		"M\3\2\2\2O\17\3\2\2\2PQ\7\34\2\2QR\13\2\2\2R[\7\34\2\2ST\7\32\2\2TU\7"+
		"\34\2\2UV\13\2\2\2V[\7\34\2\2W[\7\34\2\2XY\7\32\2\2Y[\7\34\2\2ZP\3\2\2"+
		"\2ZS\3\2\2\2ZW\3\2\2\2ZX\3\2\2\2[\21\3\2\2\2\7\37(BNZ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
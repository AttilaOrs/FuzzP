// Generated from UnifiedPLang.g4 by ANTLR 4.5
package UnifiedPLang.gen;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UnifiedPLangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, COMMENT=28, LINE_COMMENT=29, INT=30, PLUS=31, 
		MINUS=32, DIV=33, MULTI=34, NL=35, NM=36, ZR=37, PM=38, PL=39, FF=40, 
		ID=41, WS=42;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "COMMENT", "LINE_COMMENT", "INT", "PLUS", "MINUS", "DIV", 
		"MULTI", "NL", "NM", "ZR", "PM", "PL", "FF", "ID", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'sub'", "'{'", "'}'", "'='", "'new '", "'.'", "'<='", "'->'", 
		"'iP'", "'('", "')'", "'ip'", "'P'", "'p'", "'t'", "'T'", "'ot'", "'oT'", 
		"'['", "']'", "','", "'<'", "'>'", "'<phi>'", "'@'", "'@ {'", null, null, 
		null, "'+'", "'-'", "'/'", "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "COMMENT", "LINE_COMMENT", "INT", "PLUS", "MINUS", 
		"DIV", "MULTI", "NL", "NM", "ZR", "PM", "PL", "FF", "ID", "WS"
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


	public UnifiedPLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "UnifiedPLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2,\u0100\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16"+
		"\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\7\35"+
		"\u00a3\n\35\f\35\16\35\u00a6\13\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36"+
		"\3\36\3\36\7\36\u00b1\n\36\f\36\16\36\u00b4\13\36\3\36\5\36\u00b7\n\36"+
		"\3\36\3\36\3\36\3\36\3\37\6\37\u00be\n\37\r\37\16\37\u00bf\3 \3 \3!\3"+
		"!\3\"\3\"\3#\3#\3$\3$\3$\3$\3$\3$\5$\u00d0\n$\3%\3%\3%\3%\3%\3%\5%\u00d8"+
		"\n%\3&\3&\3&\3&\3&\5&\u00df\n&\3\'\3\'\3\'\3\'\3\'\5\'\u00e6\n\'\3(\3"+
		"(\3(\3(\3(\5(\u00ed\n(\3)\3)\3)\3)\5)\u00f3\n)\3*\6*\u00f6\n*\r*\16*\u00f7"+
		"\3+\6+\u00fb\n+\r+\16+\u00fc\3+\3+\4\u00a4\u00b2\2,\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'"+
		"\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'"+
		"M(O)Q*S+U,\3\2\5\3\2\62;\4\2C\\c|\5\2\13\f\17\17\"\"\u0110\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2"+
		"\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2"+
		"\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2"+
		"\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2"+
		"\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\3W"+
		"\3\2\2\2\5Y\3\2\2\2\7]\3\2\2\2\t_\3\2\2\2\13a\3\2\2\2\rc\3\2\2\2\17h\3"+
		"\2\2\2\21j\3\2\2\2\23m\3\2\2\2\25p\3\2\2\2\27s\3\2\2\2\31u\3\2\2\2\33"+
		"w\3\2\2\2\35z\3\2\2\2\37|\3\2\2\2!~\3\2\2\2#\u0080\3\2\2\2%\u0082\3\2"+
		"\2\2\'\u0085\3\2\2\2)\u0088\3\2\2\2+\u008a\3\2\2\2-\u008c\3\2\2\2/\u008e"+
		"\3\2\2\2\61\u0090\3\2\2\2\63\u0092\3\2\2\2\65\u0098\3\2\2\2\67\u009a\3"+
		"\2\2\29\u009e\3\2\2\2;\u00ac\3\2\2\2=\u00bd\3\2\2\2?\u00c1\3\2\2\2A\u00c3"+
		"\3\2\2\2C\u00c5\3\2\2\2E\u00c7\3\2\2\2G\u00cf\3\2\2\2I\u00d7\3\2\2\2K"+
		"\u00de\3\2\2\2M\u00e5\3\2\2\2O\u00ec\3\2\2\2Q\u00f2\3\2\2\2S\u00f5\3\2"+
		"\2\2U\u00fa\3\2\2\2WX\7=\2\2X\4\3\2\2\2YZ\7u\2\2Z[\7w\2\2[\\\7d\2\2\\"+
		"\6\3\2\2\2]^\7}\2\2^\b\3\2\2\2_`\7\177\2\2`\n\3\2\2\2ab\7?\2\2b\f\3\2"+
		"\2\2cd\7p\2\2de\7g\2\2ef\7y\2\2fg\7\"\2\2g\16\3\2\2\2hi\7\60\2\2i\20\3"+
		"\2\2\2jk\7>\2\2kl\7?\2\2l\22\3\2\2\2mn\7/\2\2no\7@\2\2o\24\3\2\2\2pq\7"+
		"k\2\2qr\7R\2\2r\26\3\2\2\2st\7*\2\2t\30\3\2\2\2uv\7+\2\2v\32\3\2\2\2w"+
		"x\7k\2\2xy\7r\2\2y\34\3\2\2\2z{\7R\2\2{\36\3\2\2\2|}\7r\2\2} \3\2\2\2"+
		"~\177\7v\2\2\177\"\3\2\2\2\u0080\u0081\7V\2\2\u0081$\3\2\2\2\u0082\u0083"+
		"\7q\2\2\u0083\u0084\7v\2\2\u0084&\3\2\2\2\u0085\u0086\7q\2\2\u0086\u0087"+
		"\7V\2\2\u0087(\3\2\2\2\u0088\u0089\7]\2\2\u0089*\3\2\2\2\u008a\u008b\7"+
		"_\2\2\u008b,\3\2\2\2\u008c\u008d\7.\2\2\u008d.\3\2\2\2\u008e\u008f\7>"+
		"\2\2\u008f\60\3\2\2\2\u0090\u0091\7@\2\2\u0091\62\3\2\2\2\u0092\u0093"+
		"\7>\2\2\u0093\u0094\7r\2\2\u0094\u0095\7j\2\2\u0095\u0096\7k\2\2\u0096"+
		"\u0097\7@\2\2\u0097\64\3\2\2\2\u0098\u0099\7B\2\2\u0099\66\3\2\2\2\u009a"+
		"\u009b\7B\2\2\u009b\u009c\7\"\2\2\u009c\u009d\7}\2\2\u009d8\3\2\2\2\u009e"+
		"\u009f\7\61\2\2\u009f\u00a0\7,\2\2\u00a0\u00a4\3\2\2\2\u00a1\u00a3\13"+
		"\2\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a4"+
		"\u00a2\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a8\7,"+
		"\2\2\u00a8\u00a9\7\61\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\b\35\2\2\u00ab"+
		":\3\2\2\2\u00ac\u00ad\7\61\2\2\u00ad\u00ae\7\61\2\2\u00ae\u00b2\3\2\2"+
		"\2\u00af\u00b1\13\2\2\2\u00b0\u00af\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4\u00b2\3\2"+
		"\2\2\u00b5\u00b7\7\17\2\2\u00b6\u00b5\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7"+
		"\u00b8\3\2\2\2\u00b8\u00b9\7\f\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\b\36"+
		"\2\2\u00bb<\3\2\2\2\u00bc\u00be\t\2\2\2\u00bd\u00bc\3\2\2\2\u00be\u00bf"+
		"\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0>\3\2\2\2\u00c1"+
		"\u00c2\7-\2\2\u00c2@\3\2\2\2\u00c3\u00c4\7/\2\2\u00c4B\3\2\2\2\u00c5\u00c6"+
		"\7\61\2\2\u00c6D\3\2\2\2\u00c7\u00c8\7,\2\2\u00c8F\3\2\2\2\u00c9\u00ca"+
		"\7p\2\2\u00ca\u00d0\7n\2\2\u00cb\u00cc\7P\2\2\u00cc\u00d0\7N\2\2\u00cd"+
		"\u00ce\7/\2\2\u00ce\u00d0\7\64\2\2\u00cf\u00c9\3\2\2\2\u00cf\u00cb\3\2"+
		"\2\2\u00cf\u00cd\3\2\2\2\u00d0H\3\2\2\2\u00d1\u00d2\7p\2\2\u00d2\u00d8"+
		"\7o\2\2\u00d3\u00d4\7P\2\2\u00d4\u00d8\7O\2\2\u00d5\u00d6\7/\2\2\u00d6"+
		"\u00d8\7\63\2\2\u00d7\u00d1\3\2\2\2\u00d7\u00d3\3\2\2\2\u00d7\u00d5\3"+
		"\2\2\2\u00d8J\3\2\2\2\u00d9\u00da\7|\2\2\u00da\u00df\7t\2\2\u00db\u00dc"+
		"\7\\\2\2\u00dc\u00df\7T\2\2\u00dd\u00df\7\62\2\2\u00de\u00d9\3\2\2\2\u00de"+
		"\u00db\3\2\2\2\u00de\u00dd\3\2\2\2\u00dfL\3\2\2\2\u00e0\u00e1\7r\2\2\u00e1"+
		"\u00e6\7o\2\2\u00e2\u00e3\7R\2\2\u00e3\u00e6\7O\2\2\u00e4\u00e6\7\63\2"+
		"\2\u00e5\u00e0\3\2\2\2\u00e5\u00e2\3\2\2\2\u00e5\u00e4\3\2\2\2\u00e6N"+
		"\3\2\2\2\u00e7\u00e8\7r\2\2\u00e8\u00ed\7n\2\2\u00e9\u00ea\7R\2\2\u00ea"+
		"\u00ed\7N\2\2\u00eb\u00ed\7\64\2\2\u00ec\u00e7\3\2\2\2\u00ec\u00e9\3\2"+
		"\2\2\u00ec\u00eb\3\2\2\2\u00edP\3\2\2\2\u00ee\u00ef\7h\2\2\u00ef\u00f3"+
		"\7h\2\2\u00f0\u00f1\7H\2\2\u00f1\u00f3\7H\2\2\u00f2\u00ee\3\2\2\2\u00f2"+
		"\u00f0\3\2\2\2\u00f3R\3\2\2\2\u00f4\u00f6\t\3\2\2\u00f5\u00f4\3\2\2\2"+
		"\u00f6\u00f7\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8T\3"+
		"\2\2\2\u00f9\u00fb\t\4\2\2\u00fa\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc"+
		"\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00ff\b+"+
		"\2\2\u00ffV\3\2\2\2\17\2\u00a4\u00b2\u00b6\u00bf\u00cf\u00d7\u00de\u00e5"+
		"\u00ec\u00f2\u00f7\u00fc\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
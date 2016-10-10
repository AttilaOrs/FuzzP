// Generated from /home/ors/IdeaProjects/proba/src/FuzzyPLang.g4 by ANTLR 4.5.3
package FuzzyPLang.gen;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FuzzyPLangLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "COMMENT", "LINE_COMMENT", 
		"INT", "NL", "NM", "ZR", "PM", "PL", "FF", "ID", "WS"
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


	public FuzzyPLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FuzzyPLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2$\u00d5\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r"+
		"\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3"+
		"\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3"+
		"\30\3\31\3\31\3\31\3\31\7\31\u0087\n\31\f\31\16\31\u008a\13\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\7\32\u0095\n\32\f\32\16\32\u0098"+
		"\13\32\3\32\5\32\u009b\n\32\3\32\3\32\3\32\3\32\3\33\6\33\u00a2\n\33\r"+
		"\33\16\33\u00a3\3\34\3\34\3\34\3\34\5\34\u00aa\n\34\3\35\3\35\3\35\3\35"+
		"\5\35\u00b0\n\35\3\36\3\36\3\36\3\36\5\36\u00b6\n\36\3\37\3\37\3\37\3"+
		"\37\5\37\u00bc\n\37\3 \3 \3 \3 \5 \u00c2\n \3!\3!\3!\3!\5!\u00c8\n!\3"+
		"\"\6\"\u00cb\n\"\r\"\16\"\u00cc\3#\6#\u00d0\n#\r#\16#\u00d1\3#\3#\4\u0088"+
		"\u0096\2$\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67"+
		"\359\36;\37= ?!A\"C#E$\3\2\5\3\2\62;\4\2C\\c|\5\2\13\f\17\17\"\"\u00e0"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\3G\3\2\2"+
		"\2\5I\3\2\2\2\7L\3\2\2\2\tO\3\2\2\2\13R\3\2\2\2\rV\3\2\2\2\17Y\3\2\2\2"+
		"\21\\\3\2\2\2\23^\3\2\2\2\25`\3\2\2\2\27b\3\2\2\2\31d\3\2\2\2\33g\3\2"+
		"\2\2\35j\3\2\2\2\37l\3\2\2\2!n\3\2\2\2#p\3\2\2\2%r\3\2\2\2\'t\3\2\2\2"+
		")z\3\2\2\2+|\3\2\2\2-~\3\2\2\2/\u0080\3\2\2\2\61\u0082\3\2\2\2\63\u0090"+
		"\3\2\2\2\65\u00a1\3\2\2\2\67\u00a9\3\2\2\29\u00af\3\2\2\2;\u00b5\3\2\2"+
		"\2=\u00bb\3\2\2\2?\u00c1\3\2\2\2A\u00c7\3\2\2\2C\u00ca\3\2\2\2E\u00cf"+
		"\3\2\2\2GH\7=\2\2H\4\3\2\2\2IJ\7>\2\2JK\7?\2\2K\6\3\2\2\2LM\7/\2\2MN\7"+
		"@\2\2N\b\3\2\2\2OP\7/\2\2PQ\7*\2\2Q\n\3\2\2\2RS\7+\2\2ST\7/\2\2TU\7@\2"+
		"\2U\f\3\2\2\2VW\7k\2\2WX\7R\2\2X\16\3\2\2\2YZ\7k\2\2Z[\7r\2\2[\20\3\2"+
		"\2\2\\]\7R\2\2]\22\3\2\2\2^_\7r\2\2_\24\3\2\2\2`a\7v\2\2a\26\3\2\2\2b"+
		"c\7V\2\2c\30\3\2\2\2de\7q\2\2ef\7v\2\2f\32\3\2\2\2gh\7q\2\2hi\7V\2\2i"+
		"\34\3\2\2\2jk\7]\2\2k\36\3\2\2\2lm\7_\2\2m \3\2\2\2no\7.\2\2o\"\3\2\2"+
		"\2pq\7>\2\2q$\3\2\2\2rs\7@\2\2s&\3\2\2\2tu\7>\2\2uv\7r\2\2vw\7j\2\2wx"+
		"\7k\2\2xy\7@\2\2y(\3\2\2\2z{\7\60\2\2{*\3\2\2\2|}\7?\2\2},\3\2\2\2~\177"+
		"\7}\2\2\177.\3\2\2\2\u0080\u0081\7\177\2\2\u0081\60\3\2\2\2\u0082\u0083"+
		"\7\61\2\2\u0083\u0084\7,\2\2\u0084\u0088\3\2\2\2\u0085\u0087\13\2\2\2"+
		"\u0086\u0085\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0089\3\2\2\2\u0088\u0086"+
		"\3\2\2\2\u0089\u008b\3\2\2\2\u008a\u0088\3\2\2\2\u008b\u008c\7,\2\2\u008c"+
		"\u008d\7\61\2\2\u008d\u008e\3\2\2\2\u008e\u008f\b\31\2\2\u008f\62\3\2"+
		"\2\2\u0090\u0091\7\61\2\2\u0091\u0092\7\61\2\2\u0092\u0096\3\2\2\2\u0093"+
		"\u0095\13\2\2\2\u0094\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0097\3"+
		"\2\2\2\u0096\u0094\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0099"+
		"\u009b\7\17\2\2\u009a\u0099\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009c\3"+
		"\2\2\2\u009c\u009d\7\f\2\2\u009d\u009e\3\2\2\2\u009e\u009f\b\32\2\2\u009f"+
		"\64\3\2\2\2\u00a0\u00a2\t\2\2\2\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2"+
		"\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\66\3\2\2\2\u00a5\u00a6"+
		"\7p\2\2\u00a6\u00aa\7n\2\2\u00a7\u00a8\7P\2\2\u00a8\u00aa\7N\2\2\u00a9"+
		"\u00a5\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa8\3\2\2\2\u00ab\u00ac\7p\2\2\u00ac"+
		"\u00b0\7o\2\2\u00ad\u00ae\7P\2\2\u00ae\u00b0\7O\2\2\u00af\u00ab\3\2\2"+
		"\2\u00af\u00ad\3\2\2\2\u00b0:\3\2\2\2\u00b1\u00b2\7|\2\2\u00b2\u00b6\7"+
		"t\2\2\u00b3\u00b4\7\\\2\2\u00b4\u00b6\7T\2\2\u00b5\u00b1\3\2\2\2\u00b5"+
		"\u00b3\3\2\2\2\u00b6<\3\2\2\2\u00b7\u00b8\7r\2\2\u00b8\u00bc\7o\2\2\u00b9"+
		"\u00ba\7R\2\2\u00ba\u00bc\7O\2\2\u00bb\u00b7\3\2\2\2\u00bb\u00b9\3\2\2"+
		"\2\u00bc>\3\2\2\2\u00bd\u00be\7r\2\2\u00be\u00c2\7n\2\2\u00bf\u00c0\7"+
		"R\2\2\u00c0\u00c2\7N\2\2\u00c1\u00bd\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2"+
		"@\3\2\2\2\u00c3\u00c4\7h\2\2\u00c4\u00c8\7h\2\2\u00c5\u00c6\7H\2\2\u00c6"+
		"\u00c8\7H\2\2\u00c7\u00c3\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c8B\3\2\2\2\u00c9"+
		"\u00cb\t\3\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ca\3\2"+
		"\2\2\u00cc\u00cd\3\2\2\2\u00cdD\3\2\2\2\u00ce\u00d0\t\4\2\2\u00cf\u00ce"+
		"\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2"+
		"\u00d3\3\2\2\2\u00d3\u00d4\b#\2\2\u00d4F\3\2\2\2\17\2\u0088\u0096\u009a"+
		"\u00a3\u00a9\u00af\u00b5\u00bb\u00c1\u00c7\u00cc\u00d1\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
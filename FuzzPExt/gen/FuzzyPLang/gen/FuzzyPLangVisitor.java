// Generated from /home/ors/IdeaProjects/proba/src/FuzzyPLang.g4 by ANTLR 4.5.3
package FuzzyPLang.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FuzzyPLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FuzzyPLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(FuzzyPLangParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#stateMent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStateMent(FuzzyPLangParser.StateMentContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#putInitToken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPutInitToken(FuzzyPLangParser.PutInitTokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#petriLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPetriLine(FuzzyPLangParser.PetriLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#lineCont}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineCont(FuzzyPLangParser.LineContContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#arcOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArcOp(FuzzyPLangParser.ArcOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#inpPlace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInpPlace(FuzzyPLangParser.InpPlaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#place}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlace(FuzzyPLangParser.PlaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#tranz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranz(FuzzyPLangParser.TranzContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#otranz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOtranz(FuzzyPLangParser.OtranzContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#tranzSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranzSpec(FuzzyPLangParser.TranzSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#token}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToken(FuzzyPLangParser.TokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(FuzzyPLangParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OneXOneTable}
	 * labeled alternative in {@link FuzzyPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneXOneTable(FuzzyPLangParser.OneXOneTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OneXTwoTable}
	 * labeled alternative in {@link FuzzyPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneXTwoTable(FuzzyPLangParser.OneXTwoTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithoutPhi}
	 * labeled alternative in {@link FuzzyPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithoutPhi(FuzzyPLangParser.TwoXOneWithoutPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithPhi}
	 * labeled alternative in {@link FuzzyPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithPhi(FuzzyPLangParser.TwoXOneWithPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithoutPhi}
	 * labeled alternative in {@link FuzzyPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithoutPhi(FuzzyPLangParser.TwoXTwoWithoutPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithPhi}
	 * labeled alternative in {@link FuzzyPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithPhi(FuzzyPLangParser.TwoXTwoWithPhiContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#simpleCellLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCellLine(FuzzyPLangParser.SimpleCellLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#doubleCellLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleCellLine(FuzzyPLangParser.DoubleCellLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#simpleCell}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCell(FuzzyPLangParser.SimpleCellContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#doubleCell}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleCell(FuzzyPLangParser.DoubleCellContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuzzyPLangParser#fv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFv(FuzzyPLangParser.FvContext ctx);
}